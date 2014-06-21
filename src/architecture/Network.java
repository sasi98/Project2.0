package architecture;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import utilities.Matrix;
import valueset.Value;

public class Network {
	

	
	private int 					numNeuronsE, /*Número de neuronas de entrada por patrón, bias incluido en el caso*/
									numNeuronsS, /*Número de neuronas de salida*/
									numNeuronsO; /*Número de neuronas ocultas, bias incluido en el caso*/
							
	//private double				learningCNT; 
	
	
	private Neuron[] 				inputLayer,  //Vector que contiene las neuronas de entrada de la red (bias incluido en el caso)
									hiddenLayer; //Vector que contiene las neuronas oculta de la red (bias incluido en el caso) 
	private OutputNeuron[] 			outputLayer; //Vector que contiene las neuronas de salida de la red
	
	private Matrix W, V; 
	
	private BigDecimal[] desiredOutputLayer; //No es una neurona, es solo un valor q compararemos con el obtenido
	
	private String FuntionType;

	private static Logger log = Logger.getLogger(Network.class);
	

	
	//Referente al training: Con el primer patr�n las matrices ser�n aleatorias, pero con el siguiente patr�n, 
	//ser�n las anteriores por lo que debemos parametrizarlo
	//Crea todas las capas, le da los valores iniciales a los inputs, establece las conexiones y los pesos entre ellas 
	//y a�ade los valores deseados
	
	public Network() {
		super();
	}


	
	//pre: W, V valuesInputLayer y desiredOutputLayer deben de estar inicializados
	//numNeuronsO = Número de neuronas en la capa oculta
	//valuesInputLayer =  vector que contiene los valores de las neuronas de entrada del patrón
	//desiredOutputLayer = contiene los valores de salida deseados
	//W tiene que tener dimensiones: Nº ocultas X Nº de Entradas
	//V tiene que tener dimensiones: Nº de salidas X Nº ocultas
	//post: 
	
	public void setUpPatronWithoutBias (int numNeuronsO, BigDecimal[] valuesInputLayer, BigDecimal [] desiredOutputLayer, Matrix W, Matrix V, String funtion)
	{	
		log.debug ("Entrando en SetUpPatronWithoutBias. N�mero de neuronas de entrada y de salida: "+ valuesInputLayer.length + 
					"N�mero de neuronas ocultas: "+ numNeuronsO);
		log.debug("Dimensiones de W (Filas X Columnas): (" + W.getRow()+ " X " + W.getColumn() +" )\n");
		log.debug("Dimensiones de V (Filas X Columnas): (" + V.getRow()+ " X " + V.getColumn() +" )\n");
		
		if ( (W.getRow()  == numNeuronsO) && (W.getColumn() == valuesInputLayer.length) &&
			(V.getRow() == valuesInputLayer.length) && (V.getColumn() == numNeuronsO) ) {
			this.desiredOutputLayer = desiredOutputLayer;
			this.numNeuronsE = valuesInputLayer.length;
			this.numNeuronsS = valuesInputLayer.length;
		//	this.learningCNT = learningCNT;
			this.numNeuronsO = numNeuronsO;
			this.W = W;
			this.V = V;
			this.FuntionType = funtion;
			
			//Creamos las 3 capas
			this.inputLayer = new Neuron[this.numNeuronsE];
			this.hiddenLayer = new Neuron[this.numNeuronsO];
			this.outputLayer = new OutputNeuron[this.numNeuronsS];
			
			
			//Creamos las neuronas de la capa de entrada
			for (int i = 0; i < valuesInputLayer.length; i++){
				Neuron n = new Neuron(valuesInputLayer[i], false, funtion);
				this.inputLayer[i] = n;
			}
		
			//Creamos las neuronas de la capa oculta y de salida (inicializadas a cero)
			for (int i = 0; i < numNeuronsO; i++){
				Neuron n = new Neuron(funtion);
				this.hiddenLayer[i] = n;
			}		
			
			for (int i = 0; i < valuesInputLayer.length; i++){
				OutputNeuron n = new OutputNeuron(funtion);
				this.outputLayer[i] = n;
				n.setDesiredOut(desiredOutputLayer[i]);
			}
	
			// Connect input layer to hidden layer
	        for (int i = 0; i < hiddenLayer.length; i++) {
	            for (int j = 0; j < inputLayer.length; j++) {
	                // Create the connection object and put it in both neurons, and give it the weight from the matriz W            	
	                Connection c = new Connection(inputLayer[j],hiddenLayer[i],W.getValuePos(i, j));
	                log.debug("Creando conexión: From: "+ inputLayer[j] +" to " + hiddenLayer[i] + " con peso " + W.getValuePos(i, j));
	                this.hiddenLayer[i].addConnection(c);
	                this.inputLayer[j].addConnection(c);
	            }
	        }
	        
	        // Connect the hidden layer to the output neuron, and give the weight from the matriz V
	        for (int i = 0; i < outputLayer.length; i++) {
	        	  for (int j = 0; j < hiddenLayer.length; j++) {
	        		  Connection c = new Connection(hiddenLayer[j],outputLayer[i], V.getValuePos(i, j));
	        		  log.debug("Creando conexión: From: "+ hiddenLayer[j] + " to " + outputLayer[i] + " con peso " + V.getValuePos(i, j));
	        		  this.hiddenLayer[j].addConnection(c);
	        		  this.outputLayer[i].addConnection(c);
	        	  }	  
	        }
	        this.W.printMatrix();
			this.V.printMatrix();
		}
		else{
			log.error("Las dimensiones de W y/o V no coinciden con el número de neuronas del patrón \n");			
		}
	}
	
	
		//La red contiene bias
		//pre: W, V valuesInputLayer y desiredOutputLayer deben de estar inicializados
		//numNeuronsO = Número de neuronas en la capa oculta, bias incluido
		//valuesInputLayer =  vector que contiene los valores de las neuronas de entrada del patrón
		//desiredOutputLayer = contiene los valores de salida deseados
		///W tiene que tener dimensiones: Nº ocultas X Nº de Entradas
		//V tiene que tener dimensiones: Nº de salidas X Nº ocultas
		//post: 
		//post: 
		
		public void setUpPatronWithBias (int numNeuronsO, BigDecimal[] valuesInputLayer, BigDecimal [] desiredOutputLayer, Matrix W, Matrix V, String funtion)
		{	
			int numNeuronsE = valuesInputLayer.length+1;
			int numNeuronsS = valuesInputLayer.length;
			
			log.debug ("Entrando en SetUpPatronWithoutBias. N�mero de neuronas de entrada: "+ 
					numNeuronsE + " N�mero de neuronas de salida: "+ valuesInputLayer.length + 
					"Número de neuronas ocultas: "+ numNeuronsO);
			log.debug("Dimensiones de W (Filas X Columnas): (" + W.getRow()+ " X " + W.getColumn() +" )\n");
			log.debug("Dimensiones de V (Filas X Columnas): (" + V.getRow()+ " X " + V.getColumn() +" )\n");
			
			if ( (W.getRow()  == numNeuronsO) && (W.getColumn() == numNeuronsE) &&
				(V.getRow() == numNeuronsS) && (V.getColumn() == numNeuronsO) ){
				
				this.desiredOutputLayer = desiredOutputLayer;
				this.numNeuronsE = valuesInputLayer.length + 1;
				this.numNeuronsS = valuesInputLayer.length;
				//this.learningCNT = learningCNT;
				this.numNeuronsO = numNeuronsO;
				this.W = W;
				this.V = V;
				this.FuntionType = funtion;
				
				//Tratamiento de la red con bias (el bias será algo propio de las capas de entrada y ocultas)
				//La red tiene bias,añadimos una neurona de valor uno en la capa de entrada y a la oculta 
		

				//Creamos las 3 capas
				this.inputLayer = new Neuron[numNeuronsE];
				this.hiddenLayer = new Neuron[numNeuronsO];
				this.outputLayer = new OutputNeuron[numNeuronsS];
				
				//Creamos las neuronas de la capa de entrada y le damos los valores del vector introducido por parámetros
				Neuron n = new Neuron (new BigDecimal(1), true, funtion);
				inputLayer[0] = n;
				for (int i = 0; i < valuesInputLayer.length; i++){
					n = new Neuron(valuesInputLayer[i], false, funtion);
					this.inputLayer[i+1] = n;
				}
			
				//Creamos las neuronas de la capa oculta y de salida (inicializadas a cero)
				Neuron nH = new Neuron (new BigDecimal(1), true, funtion);
				hiddenLayer[0] = nH;
				for (int i = 1; i < numNeuronsO; i++){
					nH = new Neuron(funtion);
					this.hiddenLayer[i] = nH;
				}		
				for (int i = 0; i < valuesInputLayer.length; i++){
					OutputNeuron nO = new OutputNeuron(funtion);
					this.outputLayer[i] = nO;
					nO.setDesiredOut(desiredOutputLayer[i]);
				}
		
				// Connect input layer to hidden layer
		        for (int i = 0; i < hiddenLayer.length; i++) {
		            for (int j = 0; j < inputLayer.length; j++) {
		                // Create the connection object and put it in both neurons, and give it the weight from the matriz W            	
		                Connection c = new Connection(inputLayer[j],hiddenLayer[i],W.getValuePos(i, j));
		                log.debug("Creando conexi�n: From: "+ inputLayer[j] +" to " + hiddenLayer[i] + " con peso " + W.getValuePos(i, j));
		                this.hiddenLayer[i].addConnection(c);
		                this.inputLayer[j].addConnection(c);
		            }
		        }
		        
		        // Connect the hidden layer to the output neuron, and give the weight from the matriz V
		        for (int i = 0; i < outputLayer.length; i++) {
		        	  for (int j = 0; j < hiddenLayer.length; j++) {
		        		  Connection c = new Connection(hiddenLayer[j],outputLayer[i], V.getValuePos(i, j));
		        		  log.debug("Creando conexi�n: From: "+ hiddenLayer[j] + " to " + outputLayer[i] + " con peso " + V.getValuePos(i, j));
		        		  this.hiddenLayer[j].addConnection(c);
		        		  this.outputLayer[i].addConnection(c);
		        	  }	  
		        }
		        this.W.printMatrix();
				this.V.printMatrix();
			}
			else{
				log.error("Las dimensiones de W y/o V no coinciden con el número de neuronas del patrón \n");
				
				
			}
		}
	
	
	//Pre: Las neuronas de entrada deben de estar inicializados con valores v�lidos
	//Pre: ejecuci�n de setUpPatron
	public void feedForward (){
		log.trace("Ejecutando m�dulo feedForward() \n");
		
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
	
	public void train (int idPatron, double learningCNT) {
		
		log.info("Entro en train e imprimo W y V");
		this.V.printMatrix();
		this.W.printMatrix();
		
        feedForward();
        log.trace("Ejecutando modulo train() after feedForward \n");

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
        	//Si estamos usando la tangencial los deltan son multiplicados por la derivada de la funcion. 
            if (FuntionType == Value.Funtion.TANGENCIAL){
            	System.out.print("Entrenando con tangencial");
            	BigDecimal aux = derivative(outputLayer[i].getOutValue());
            	deltaE = deltaE.multiply(aux);   	
            }
            deltaE.setScale(Manager.PRECISION, RoundingMode.HALF_UP);
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
        			aux = aux.multiply(c.getWeight()); //multiplicada por el peso de la conexi�n
        			deltaE =  deltaE.add(aux);  
        			
        			if (FuntionType == Value.Funtion.TANGENCIAL){
        				deltaE = deltaE.multiply(derivative(hiddenLayer[i].getOutValue()));
        			}
        		}
        	}
        	deltaE.setScale(Manager.PRECISION, RoundingMode.HALF_UP);
        	deltaHidden[i] = deltaE;   	
        }
        log.debug("Mostrando delta de la capa oculta");
        for (int i = 0; i< hiddenLayer.length; ++i){
        	log.debug("Salida obtenida: "+ hiddenLayer[i].getOutValue()
        			+ " Delta de error "+ deltaHidden[i]);
        }
       
            
        /**Actualizaci�n de matrices: C�lculo de deltaV y deltaW*/
        
        //Matriz V: Salidas de la oculta por delta de error de la salida
        
        BigDecimal aux[] = new BigDecimal[hiddenLayer.length];
         for (int i = 0; i< hiddenLayer.length; i++){
               aux[i] = hiddenLayer[i].getOutValue();
        }

        Matrix mHiddenOuts = new Matrix(aux);
        Matrix mDeltaOutput = new Matrix(deltaOutput);
        mDeltaOutput = Matrix.transponer(mDeltaOutput);
        
        log.debug("Showing mDeltaOut");
        mDeltaOutput.printMatrix();
        
        log.debug("Showing mHiddenOuts");
        mHiddenOuts.printMatrix();
        
        Matrix deltaV = Matrix.product(mDeltaOutput, mHiddenOuts);
        
        
        log.debug("Muestro deltaV antes de multiplicarla por learningCNT");
        deltaV.printMatrix();
        
        deltaV = deltaV.multEscalar(learningCNT);
        log.debug("Coeficiente de aprendizaje: "+ learningCNT);
        log.debug("Muestro deltaV tr�s multiplicarla por el coeficiente de aprendizaje");
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
       log.debug("Muestro deltaW tr�s multiplicarla por el coeficiente de aprendizaje");
       deltaW.printMatrix();
       deltaW.printMatrix();

       mDeltaOutput.truncarMatrixUP(Manager.PRECISION);
       mDeltaHidden.truncarMatrixUP(Manager.PRECISION);
       deltaV.truncarMatrixUP(Manager.PRECISION);
       deltaW.truncarMatrixUP(Manager.PRECISION);

       //Actualizamos las matrices con los deltas calculados
       this.W = Matrix.addition(this.W, deltaW);
	   this.V = Matrix.addition(this.V, deltaV);
	  	   
	   this.W.truncarMatrixUP(Manager.PRECISION);
	   this.V.truncarMatrixUP(Manager.PRECISION);
	   
	}
	
      
	//pre: Realizar el setup antes
	//previousW, previousV: matrices (t - 1) utilizadas en el momento beta  
	public void trainWithMomentB (int idPatron, double momentoB, double learningCNT) {
		
		log.info("Entrenando con momento Beta. Patr�n: "+ idPatron);
		log.info("Momento B: "+ momentoB);
        feedForward();
        
        //This is what backpropagation starts
        
      //Calculo los deltas de error de la capa de salida
        BigDecimal[] deltaOutput = new BigDecimal[desiredOutputLayer.length];
        for (int i = 0; i<outputLayer.length; i++){
        	BigDecimal deltaE = desiredOutputLayer[i];
        	deltaE = deltaE.subtract(outputLayer[i].getOutValue());
        	deltaE.setScale(Manager.PRECISION, RoundingMode.HALF_UP);
        	
        	 if (FuntionType == Value.Funtion.TANGENCIAL){
             	BigDecimal aux = derivative(outputLayer[i].getOutValue());
             	deltaE = deltaE.multiply(aux);   	
             }
        	 deltaE.setScale(Manager.PRECISION, RoundingMode.HALF_UP);
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
        			aux = aux.multiply(c.getWeight()); //multiplicada por el peso de la conexi�n
        			deltaE =  deltaE.add(aux);   
        			if (FuntionType == Value.Funtion.TANGENCIAL){
        				deltaE = deltaE.multiply(derivative(hiddenLayer[i].getOutValue()));
        			}
        		}
        	}
        	deltaE.setScale(Manager.PRECISION, RoundingMode.HALF_UP);
        	deltaHidden[i] = deltaE;   	
        }
 
        
        /**Actualizaci�n de matrices: c�lculo de deltaV y deltaW*/
        
        //Matriz V: Salidas de la oculta por delta de error de la salida
        
        BigDecimal aux[] = new BigDecimal[hiddenLayer.length];
         for (int i = 0; i< hiddenLayer.length; i++){
               aux[i] = hiddenLayer[i].getOutValue();
        }

        Matrix mHiddenOuts = new Matrix(aux);
        Matrix mDeltaOutput = new Matrix(deltaOutput);
        mDeltaOutput = Matrix.transponer(mDeltaOutput);
        Matrix deltaV = Matrix.product(mDeltaOutput, mHiddenOuts);
        deltaV = deltaV.multEscalar(learningCNT);

        //Matriz W: Delta de error de la oculta por salidas de la capa de entrada (inputs)
        
        BigDecimal aux2[] = new BigDecimal[inputLayer.length];
        for (int i = 0; i< inputLayer.length; i++){
              aux2[i] = inputLayer[i].getOutValue();
       }
        
       Matrix mInputOuts = new Matrix(aux2);
       Matrix mDeltaHidden = new Matrix(deltaHidden);
       mDeltaHidden = Matrix.transponer(mDeltaHidden);

  
       
       Matrix deltaW = Matrix.product(mDeltaHidden,mInputOuts);
    
       deltaW = deltaW.multEscalar(learningCNT);
       mDeltaOutput.truncarMatrixUP(Manager.PRECISION);
       mDeltaHidden.truncarMatrixUP(Manager.PRECISION);
       deltaV.truncarMatrixUP(Manager.PRECISION);
       deltaW.truncarMatrixUP(Manager.PRECISION);
       
       //Antes de actualizar, guardamos estas matrices.
       Matrix momentW = this.W;
       Matrix momentV = this.V;
       
       
       //And for update I'll use the previous of the previous instance (the parameter's one)
       momentW = Matrix.subtraction(momentW, Manager.previousW);
       momentV = Matrix.subtraction(momentV, Manager.previousV);
       
       momentW = momentW.multEscalar(momentoB);
       momentV = momentV.multEscalar(momentoB);
       
       log.trace("showing (t-1):");
       Manager.previousW.printMatrix();
       Manager.previousV.printMatrix();
       
       Manager.previousW = this.W;
       Manager.previousV = this.V; //We save them before update
       
       log.trace("showing (t):");
       this.W.printMatrix();
       this.V.printMatrix();
       
       //Actualizamos las matrices con los deltas calculados
       this.W = Matrix.addition(this.W, deltaW);
	   this.V = Matrix.addition(this.V, deltaV);
	   //Le sumamos el momento B
	   this.W = Matrix.addition(this.W, momentW);
	   this.V = Matrix.addition(this.V, momentV);
	   
	   this.W.truncarMatrixUP(Manager.PRECISION);
	   this.V.truncarMatrixUP(Manager.PRECISION);
	   
	   log.trace("showing (t + 1 )");
       this.W.printMatrix();
       this.V.printMatrix();
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
	//Si a�adimos m�s hidden layer solo tendr�amos q hacer esto por cada hidden layer
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
	
	
	
	
	
	//Calculamos el error en un patr�n, con las matrices de pesos resultantes
	//W: matriz W obtenida en el �ltimo patron entrenado
	//V: matriz V obtenida en el �ltimo patron entrenado
	//pre: W y V deben de ser matrices v�lidas 
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
	
	
	public static BigDecimal derivative (BigDecimal x){
		BigDecimal aux = new BigDecimal (1);
		aux = aux.subtract(x.pow(2));
		aux = aux.setScale(Manager.PRECISION, RoundingMode.HALF_DOWN);
		return aux;
	}
	
	
	public void connectLayers (Neuron[] from, Neuron[] to, Matrix W){
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < from.length; j++) {
				//Create the connection object and put it in both neurons, and give it the weight from the matriz W            	
	            Connection c = new Connection(from[j],to[i],W.getValuePos(i, j));
	            log.debug("Creando conexi�n: From: "+ from[j] +" to " + to[i] + " con peso " + W.getValuePos(i, j));
	            to[i].addConnection(c);
	            from[j].addConnection(c);
	        }
		}
		
		
	}
	
	
	
	

}
