package architecture;

import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import dataManager.ReadExcel;
import dataManager.WriteExcel;
import utilities.Matrix;

public class Network {
	

	
	private int 					numNeuronsES, /*Número de neuronas de entrada o salida por patrón*/
									numNeuronsO,  /*Número de neuronas en la capa oculta*/
									numPatrones;  /*Número de patrones utilizados en el entrenamiento*/ 
	
	
	private double				learningCNT;
	
	
	private Neuron[] 				inputLayer,
									hiddenLayer;
	private OutputNeuron[] 			outputLayer; 
	
	private Matrix W, V; 
	
	private BigDecimal[] desiredOutputLayer; //No es una neurona, es solo un valor q compararemos con el obtenido

	private static Logger log = Logger.getLogger(Network.class);
	
	
	
	
	
	//Referente al training: Con el primer patrón las matrices serán aleatorias, pero con el siguiente patrón, 
	//serán las anteriores por lo que debemos parametrizarlo
	//Crea todas las capas, le da los valores iniciales a los inputs, establece las conexiones y los pesos entre ellas 
	//y añade los valores deseados
	
	public Network() {
		super();
	}


	
	
	

	//pre: W, V valuesInputLayer y desiredOutputLayer deben de estar inicializados
	//numNeuronsO = número de neuronas en la capa oculta
	//valuesInputLayer =  vector que contiene los valores de las neuronas de entrada del patrón
	//desiredOutputLayer = contiene los valores de salida deseados
	//W tiene que tener dimensiones: nº ocultas X nª entradas
	//V tiene que tener dimensiones: nº salidas X nº ocultas
	//post: 
	
	public void setUpPatron (int numNeuronsO, BigDecimal[] valuesInputLayer, double learningCNT, 
			BigDecimal [] desiredOutputLayer, Matrix W, Matrix V)
	{	
		log.debug ("Entrando en SetUpPatron. Número de neuronas E/S: "+ valuesInputLayer.length + 
					"Número de neuronas ocultas: "+ numNeuronsO);
		//log.debug("Dimensiones de W (Filas X Columnas): (" + W.getRow()+ " X " + W.getColumn() +" )\n");
		//log.debug("Dimensiones de V (Filas X Columnas): (" + V.getRow()+ " X " + V.getColumn() +" )\n");
		
		if ( (W.getRow()  == numNeuronsO) && (W.getColumn() == valuesInputLayer.length) &&
			(V.getRow() == valuesInputLayer.length) && (V.getColumn() == numNeuronsO) ){
			
			this.desiredOutputLayer = desiredOutputLayer;
			this.numNeuronsES = valuesInputLayer.length;
			this.learningCNT = learningCNT;
			this.numNeuronsO = numNeuronsO;
			this.W = W;
			this.V = V;
			this.W.printMatrix();
			this.V.printMatrix();
			//Creamos las 3 capas
			this.inputLayer = new Neuron[numNeuronsES];
			this.hiddenLayer = new Neuron[numNeuronsO];
			this.outputLayer = new OutputNeuron[numNeuronsES];
			
			
			//Creamos las neuronas de la capa de entrada y le damos los valores del vector introducido por parámetros
			for (int i = 0; i < valuesInputLayer.length; i++){
				Neuron n = new Neuron(valuesInputLayer[i], false);
				inputLayer[i] = n;
			}
	
			//Creamos las neuronas de la capa oculta y de salida ( inicializadas a cero)
			for (int i = 0; i < numNeuronsO; i++){
				Neuron n = new Neuron();
				hiddenLayer[i] = n;
			}
			
			for (int i = 0; i < valuesInputLayer.length; i++){
				OutputNeuron n = new OutputNeuron();
				outputLayer[i] = n;
				n.setDesiredOut(desiredOutputLayer[i]);
			}
	
			// Connect input layer to hidden layer
	        for (int i = 0; i < hiddenLayer.length; i++) {
	            for (int j = 0; j < inputLayer.length; j++) {
	                // Create the connection object and put it in both neurons, and give it the weight from the matriz W            	
	                Connection c = new Connection(inputLayer[j],hiddenLayer[i],W.getValuePos(i, j));
	                log.debug("Creando conexión: From: "+ inputLayer[j] +" to " + hiddenLayer[i] + " con peso " + W.getValuePos(i, j));
	                hiddenLayer[i].addConnection(c);
	                inputLayer[j].addConnection(c);
	            }
	        }
	        
	        // Connect the hidden layer to the output neuron, and give the weight from the matriz V
	        for (int i = 0; i < outputLayer.length; i++) {
	        	  for (int j = 0; j < hiddenLayer.length; j++) {
	        		  Connection c = new Connection(hiddenLayer[j],outputLayer[i], V.getValuePos(i, j));
	        		  log.debug("Creando conexión: From: "+ hiddenLayer[j] + " to " + outputLayer[i] + " con peso " + V.getValuePos(i, j));
	        		  hiddenLayer[j].addConnection(c);
	        		  outputLayer[i].addConnection(c);
	        	  }	  
	        }
		}
		else{
			log.error("Las dimensiones de W y/o V no coinciden con el número de neuronas del patrón \n");
			
			
		}
	}
	
	
	//Pre: Las neuronas de entrada deben de estar inicializados con valores válidos
	//Pre: ejecución de setUpPatron
	public void feedForward (){
		log.trace("Ejecutando módulo feedForward() \n");
		
		// Have the hidden layer calculate its output
        for (int i = 0; i < hiddenLayer.length; i++) {
        	log.trace("Salidas capa oculta");
            hiddenLayer[i].calculateOutValue();
        }

        // Calculate the output of the output neuron (recuerda q solo hay una en este ejemplo)
       for (int i = 0; i < outputLayer.length; i++){
    	   log.trace("Salidas capa salida");
    	   outputLayer[i].calculateOutValue();   
       }
		
	}

	
	//pre: Realizar el setup antes
	//
	public void train (WriteExcel writer) {
		
        feedForward();
        log.trace("Ejecutando módulo train() after feedForward \n");

    	log.trace("Just for make sure, we are showing the inputs that we are training:  \n");
        for (Neuron i: inputLayer){
        	log.trace(i.getOutValue());
        }
        
        //This is what backpropagation starts
        
      //Calculo los deltas de error de la capa de salida
        BigDecimal[] deltaOutput = new BigDecimal[desiredOutputLayer.length];
        for (int i = 0; i<outputLayer.length; i++){
        	BigDecimal deltaE = desiredOutputLayer[i];
        	deltaE = deltaE.subtract(outputLayer[i].getOutValue()); 
        	
        	outputLayer[i].setDeltaError(deltaE); //Le metemos su delta de error correspondiente
        	deltaOutput[i] = deltaE;
        }
        
        log.debug("Mostrando delta de la capa de salida");
        for (int i = 0; i< outputLayer.length; ++i){
        	log.debug("Salida obtenida: "+ outputLayer[i].getOutValue()+ " Salida deseada: "+desiredOutputLayer[i] 
        			+ " Delta de error "+ outputLayer[i].getDeltaError());
        	
        }
        
        //Calculo los deltas de la capa oculta
        BigDecimal[] deltaHidden = new BigDecimal[numNeuronsO];
        
        for (int i = 0; i<hiddenLayer.length; i++){
        	ArrayList <Connection> connections = hiddenLayer[i].getConnections(); //Obtenemos sus conexiones
        	//Utilizamos solo las conexiones hacia delante (hidden with output layers)
        	BigDecimal deltaE = new BigDecimal(0);
        	for (Connection c: connections){
        		if (c.getFrom() == hiddenLayer[i]){ 
        			OutputNeuron o =  (OutputNeuron) c.getTo();
        			BigDecimal aux = o.getDeltaError();
        			System.out.print("Value: "+ c.getWeight()+ "X" + "Delta:" + aux+ "\n");
        			aux = aux.multiply(c.getWeight()); //multiplicada por el peso de la conexión
        			deltaE =  deltaE.add(aux);   			
        		}
        	}
        	deltaHidden[i] = deltaE;   	
        }
        
        log.debug("Mostrando delta de la capa oculta");
        for (int i = 0; i< hiddenLayer.length; ++i){
        	log.debug("Salida obtenida: "+ hiddenLayer[i].getOutValue()
        			+ " Delta de error "+ deltaHidden[i]);
        	
        }
        
    
//        System.out.println ("Muestro los delta de salida: \n ");
//        for (BigDecimal b: deltaOutput){
//        	System.out.print(b + " ");
//        }
//        
//        System.out.println ("Muestro los delta de la capa oculta: \n ");
//        for (BigDecimal b: deltaHidden){
//        	System.out.print(b + " ");
//        }
        
        //Actualización de matrices: Cálculo de deltaV y deltaW
        
        
        //Matriz V: Salidas de la oculta por delta de error de la salida
        
        BigDecimal aux[] = new BigDecimal[hiddenLayer.length];
         for (int i = 0; i< hiddenLayer.length; i++){
               aux[i] = hiddenLayer[i].getOutValue();
        }

        Matrix mHiddenOuts = new Matrix(aux);
        Matrix mDeltaOutput = new Matrix(deltaOutput);
        mDeltaOutput = Matrix.transponer(mDeltaOutput);

        Matrix deltaV = Matrix.product(mDeltaOutput, mHiddenOuts);
        
        
        log.debug("Muestro deltaV antes de multiplicarla por learningCNT");
        deltaV.printMatrix();
        
        deltaV = deltaV.multEscalar(learningCNT);
        log.debug("Coeficiente de aprendizaje: "+ learningCNT);
        log.debug("Muestro deltaV trás multiplicarla por el coeficiente de aprendizaje");
        deltaV.printMatrix();
        
        
      //Matriz W: Delta de error de la oculta por salidas de la capa de entrada (inputs)
        
        BigDecimal aux2[] = new BigDecimal[inputLayer.length];
        for (int i = 0; i< inputLayer.length; i++){
              aux2[i] = inputLayer[i].getOutValue();
       }
        
       Matrix mInputOuts = new Matrix(aux2);
       mInputOuts.printMatrix();
       Matrix mDeltaHidden = new Matrix(deltaHidden);
       mDeltaHidden = Matrix.transponer(mDeltaHidden);
       mDeltaHidden.printMatrix();
       Matrix deltaW = Matrix.product(mDeltaHidden,mInputOuts);
       
       log.debug("Muestro deltaW antes de multiplicarla por learningCNT");
       deltaW.printMatrix();
       
       deltaW = deltaW.multEscalar(learningCNT);
       log.debug("Coeficiente de aprendizaje: "+ learningCNT);
       log.debug("Muestro deltaW trás multiplicarla por el coeficiente de aprendizaje");
       deltaW.printMatrix();
      
       
       
       
       deltaW.printMatrix();

       
       writer.writeInfPatron(W, V, inputLayer, desiredOutputLayer, hiddenLayer, outputLayer, 
    		   mDeltaOutput, mDeltaHidden, deltaW, deltaV);
       
       
       
       //Actualizamos las matrices con los deltas calculados
       this.W = Matrix.addition(this.W, deltaW);
	   this.V = Matrix.addition(this.V, deltaV);
	   
	   this.W.truncarMatrixUP(NetworkManager.PRECISION);
	   this.V.truncarMatrixUP(NetworkManager.PRECISION);
	   
	   //Actualizamos las conexiones con las nuevas matrices (no es necesario en el trainnig)
       //updateConnections(W, V);
       
       
     
       
	}
	
      
        


       
        

	
	
	


	public Neuron[] getInputLayer() {
		return inputLayer;
	}



	public void setInputLayer(Neuron[] inputLayer) {
		this.inputLayer = inputLayer;
	}



	public Neuron[] getHiddenLayer() {
		return hiddenLayer;
	}



	public void setHiddenLayer(Neuron[] hiddenLayer) {
		this.hiddenLayer = hiddenLayer;
	}



	public OutputNeuron[] getOutputLayer() {
		return outputLayer;
	}



	public void setOutputLayer(OutputNeuron[] outputLayer) {
		this.outputLayer = outputLayer;
	}



	public BigDecimal[] getDesiredOutputLayer() {
		return desiredOutputLayer;
	}



	public Matrix getW() {
		return W;
	}



	public void setW(Matrix w) {
		W = w;
	}






	public Matrix getV() {
		return V;
	}






	public void setV(Matrix v) {
		V = v;
	}






	public void setDesiredOutputLayer(BigDecimal[] desiredOutputLayer) {
		this.desiredOutputLayer = desiredOutputLayer;
	}
	
	
	//Las conexiones son creadas una vez,no dejan de ser punteros (aunq estemos en java)
	//si las modificamos en un Layer no necesitamos cambiarlas en los otros,
	//por lo q cogeremos solo hidden layer y asi modificaremos todos los pesos de la red, 
	//Si añadimos más hidden layer solo tendríamos q hacer esto por cada hidden layer
	//post: modifica la clase network (vector hiddenLayer, inputLayer y outputLayer
	public void updateConnections (Matrix W, Matrix V){
		 for (int i = 0; i < hiddenLayer.length; i++) {
			 for (Connection c: hiddenLayer[i].getConnections()){
				 if (c.getTo() == hiddenLayer[i]){
					 for (int j = 0; j < inputLayer.length; j++){
						 if (inputLayer[j] == c.getFrom())
							 c.setWeight(W.getValuePos(i, j));
					 }
				 }
				 else{ //c.getFrom == hiddenLayer
					 for (int j = 0; j < outputLayer.length; j++){
						 if (outputLayer[j] == c.getTo())
							 c.setWeight(V.getValuePos(j,i));
					 }
				 }
			 }
		}
	}
	
	
	
	
	
	//Calculamos el error en un patrón, con las matrices de pesos resultantes
	//W: matriz W obtenida en el último patron entrenado
	//V: matriz V obtenida en el último patron entrenado
	//pre: W y V deben de ser matrices válidas 
	public BigDecimal calculateError ()
	{
//		this.W = W;
//		this.V = V;
//		updateConnections(W, V);  //debemos actualizar el peso de las conexiones
		
		log.trace("Entrando en calculateError");
		feedForward(); //Se calculan las nuevas salidas con las conexiones ya actualizadas
		
		
		BigDecimal acum = new BigDecimal(0);
	
		for (OutputNeuron out: this.getOutputLayer()){
			BigDecimal sub = out.getDesiredOut();
			sub = sub.subtract(out.getOutValue());
			acum = acum.add(sub);
		}
		
		acum= acum.pow(2);
		acum = acum.multiply(new BigDecimal (0.5));
		log.debug("Error en este patron: " + acum);
		return acum;
	}
	
	
	
	
	
	
	
	

}
