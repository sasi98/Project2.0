package architecture;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import utilities.Matrix;

public class Network {
	
	
	public static final int PRECISION = 10; //variable pública la usaremos por toda la red

	private int numNeuronsES, /*Número de neuronas de entrada o salida por patrón*/
				numNeuronsO,  /*Número de neuronas en la capa oculta*/
				numPatrones;  /*Número de patrones utilizados en el entrenamiento*/ 
	
	
	
	private double 		learningCNT;
	private int 		iterationMAX;
	private BigDecimal 	cota;
	
	
	
	//No se aún si lo dejaremos así o con herencia o los pondremos en la otra clase layer
	
	private Neuron[] inputLayer;
	private Neuron[] hiddenLayer;
	private Neuron[] outputLayer; 
	
	
//	private Layer inputLayer;
//	private Layer hiddenLayer;
//	private Layer outputLayer;
//	
	Matrix M, V; 
	
	private BigDecimal[] desiredOutputLayer; //No es una neurona, es solo un valor q compararemos con el obtenido
	
	
	
	
	
	
	
	
	//Referente al training: Con el primer patrón las matrices serán aleatorias, pero con el siguiente patrón, 
	//serán las anteriores por lo que debemos parametrizarlo
	//Crea todas las capas, le da los valores iniciales a los inputs, establece las conexiones y los pesos entre ellas 
	//y añade los valores deseados
	
	public Network() {
		super();
		

	}



	public void setUpPatron (int numNeuronsO, BigDecimal[] valuesInputLayer, 
			BigDecimal [] desiredOutputLayer, Matrix W, Matrix V)
	{	
		
	//W tiene que tener dimensiones: nº ocultas X nª entradas
	// V tiene que tener dimensiones: nº salidas X nº ocultas
		
		
		this.desiredOutputLayer = desiredOutputLayer;
		this.numNeuronsES = valuesInputLayer.length;
		this.numNeuronsO = numNeuronsO;
//		
		
		
		//Creamos las 3 capas
		this.inputLayer = new Neuron[valuesInputLayer.length];
		this.hiddenLayer = new Neuron[numNeuronsO];
		this.outputLayer = new Neuron[valuesInputLayer.length];
		
		
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
			Neuron n = new Neuron();
			outputLayer[i] = n;
		}

		// Connect input layer to hidden layer
        for (int i = 0; i < hiddenLayer.length; i++) {
            for (int j = 0; j < inputLayer.length; j++) {
                // Create the connection object and put it in both neurons, and give it the weight from the matriz W            	
                Connection c = new Connection(inputLayer[j],hiddenLayer[i],W.getValuePos(i, j));
                hiddenLayer[i].addConnection(c);
                inputLayer[j].addConnection(c);
            }
        }
        
        // Connect the hidden layer to the output neuron, and give the weight from the matriz V
        for (int i = 0; i < outputLayer.length; i++) {
        	  for (int j = 0; j < hiddenLayer.length; j++) {
        		  Connection c = new Connection(hiddenLayer[j],outputLayer[i], V.getValuePos(i, j));
        		  hiddenLayer[j].addConnection(c);
        		  outputLayer[i].addConnection(c);
        	  }	  
        }
		
	}
	
	
	//Los inputs ya tienen valores (dados en la inicialización) 
	public void feedForward (){
		
		// Have the hidden layer calculate its output
        for (int i = 0; i < hiddenLayer.length; i++) {
            hiddenLayer[i].calculateOutValue();
        }

        // Calculate the output of the output neuron (recuerda q solo hay una en este ejemplo)
       for (int i = 0; i < outputLayer.length; i++){
    	   outputLayer[i].calculateOutValue();   
       }
		
	}

	
	//pre: Realizar el setup antes
	//
	public void train () {
        feedForward();
        //This is what backpropagation starts
        
      //Calculo los deltas de error de la capa de salida
        BigDecimal[] deltaOutputs = new BigDecimal[desiredOutputLayer.length];
        for (int i = 0; i<outputLayer.length; i++){
        	BigDecimal deltaE = desiredOutputLayer[i];
        	deltaE = deltaE.subtract(outputLayer[i].getOutValue());
        	deltaOutputs[i] = deltaE;
        }
        
        //Calculo los deltas de la capa oculta
        BigDecimal[] deltaHiddens = new BigDecimal[numNeuronsO];
        
        for (int i = 0; i<hiddenLayer.length; i++){
        	ArrayList <Connection> connections = hiddenLayer[i].getConnections(); //Obtenemos sus conexiones
        	//Utilizamos solo las conexiones hacia delante (hidden with output layers)
        	BigDecimal deltaE = new BigDecimal(0);
        	for (Connection c: connections){
        		if (c.getFrom() == hiddenLayer[i]){ 
        			BigDecimal aux = c.getTo().getOutValue();//Valor de la salida a la q está conectada (outValue) 
        			aux.multiply(c.getWeight()); //multiplicada por el peso de la conexión
        			deltaE = deltaE.add(aux);   			
        		}
        	}
        	deltaHiddens[i] = deltaE;   	
        }
        
	}
	
        
        


       
        
//        // ADJUST HIDDEN WEIGHTS
//        for (int i = 0; i < hidden.length; i++) {
//            connections = hidden[i].getConnections();
//            float sum  = 0;
//            // Sum output delta * hidden layer connections (just one output)
//            for (int j = 0; j < connections.size(); j++) {
//                Connection c = (Connection) connections.get(j);
//                // Is this a connection from hidden layer to next layer (output)?
//                if (c.getFrom() == hidden[i]) {
//                    sum += c.getWeight()*deltaOutput;
//                }
//            }    
//            // Then adjust the weights coming in based:
//            // Above sum * derivative of sigmoid output function for hidden neurons
//            for (int j = 0; j < connections.size(); j++) {
//                Connection c = (Connection) connections.get(j);
//                // Is this a connection from previous layer (input) to hidden layer?
//                if (c.getTo() == hidden[i]) {
//                    float output = hidden[i].getOutput();
//                    float deltaHidden = output * (1 - output);  // Derivative of sigmoid(x)
//                    deltaHidden *= sum;   // Would sum for all outputs if more than one output
//                    Neuron neuron = c.getFrom();
//                    float deltaWeight = neuron.getOutput()*deltaHidden;
//                    c.adjustWeight(LEARNING_CONSTANT*deltaWeight);
//                }
//            } 
//        }
//
//        return result;
//    }
	
	
	


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



	public Neuron[] getOutputLayer() {
		return outputLayer;
	}



	public void setOutputLayer(Neuron[] outputLayer) {
		this.outputLayer = outputLayer;
	}



	public BigDecimal[] getDesiredOutputLayer() {
		return desiredOutputLayer;
	}



	public void setDesiredOutputLayer(BigDecimal[] desiredOutputLayer) {
		this.desiredOutputLayer = desiredOutputLayer;
	}
	
	
	
	
	
	
	
	
	

}
