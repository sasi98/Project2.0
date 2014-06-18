package architecture;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

import utilities.Matrix;

public class SimplyNetwork {
		

		
		private int 					numNeuronsE, /*NÃºmero de neuronas de entrada por patrÃ³n, bias incluido en el caso*/
										numNeuronsS; /*NÃºmero de neuronas de salida*/
		
		//private double				learningCNT; 
									 //Lo utilizaremos para evitar minimos locales
		
		private Neuron[] 				inputLayer;  //Vector que contiene las neuronas de entrada de la red (bias incluido en el caso) 
		private OutputNeuron[] 			outputLayer; //Vector que contiene las neuronas de salida de la red
		
		private Matrix W; 
		
		private BigDecimal[] desiredOutputLayer; //No es una neurona, es solo un valor q compararemos con el obtenido
		
		private String FuntionType;

		private static Logger log = Logger.getLogger(SimplyNetwork.class);
		

		
		//Referente al training: Con el primer patrï¿½n las matrices serï¿½n aleatorias, pero con el siguiente patrï¿½n, 
		//serï¿½n las anteriores por lo que debemos parametrizarlo
		//Crea todas las capas, le da los valores iniciales a los inputs, establece las conexiones y los pesos entre ellas 
		//y aï¿½ade los valores deseados
		
		public SimplyNetwork() {
			super();
		}


		
		//pre: W, V valuesInputLayer y desiredOutputLayer deben de estar inicializados
		//numNeuronsO = NÃºmero de neuronas en la capa oculta
		//valuesInputLayer =  vector que contiene los valores de las neuronas de entrada del patrÃ³n
		//desiredOutputLayer = contiene los valores de salida deseados
		//W tiene que tener dimensiones: NÂº ocultas X NÂº de Entradas
		//V tiene que tener dimensiones: NÂº de salidas X NÂº ocultas
		//post: 
		
		public void setUpPatronWithoutBias (BigDecimal[] valuesInputLayer, BigDecimal [] desiredOutputLayer, Matrix W, String funtion)
		{	
			log.debug ("Entrando en SetUpPatronWithoutBias. Número de neuronas de entrada y de salida: "+ valuesInputLayer.length);
			log.debug("Dimensiones de W (Filas X Columnas): (" + W.getRow()+ " X " + W.getColumn() +" )\n");
			
			this.numNeuronsE = valuesInputLayer.length;
			this.numNeuronsS = valuesInputLayer.length;
			if ( (W.getRow()  == numNeuronsS) && (W.getColumn() == numNeuronsE) ){
				this.desiredOutputLayer = desiredOutputLayer;
			//	this.learningCNT = learningCNT;
				this.W = W;
				this.FuntionType = funtion;
				
				//Creamos las 2 capas
				this.inputLayer = new Neuron[this.numNeuronsE];
				this.outputLayer = new OutputNeuron[this.numNeuronsS];
				
				//Creamos las neuronas de la capa de entrada
				for (int i = 0; i < valuesInputLayer.length; i++){
					Neuron n = new Neuron(valuesInputLayer[i], false, funtion);
					this.inputLayer[i] = n;
				}
				
				for (int i = 0; i < valuesInputLayer.length; i++){
					OutputNeuron n = new OutputNeuron(funtion);
					this.outputLayer[i] = n;
					n.setDesiredOut(desiredOutputLayer[i]);
				}
		
				// Connect input layer to output layer
		        for (int i = 0; i < outputLayer.length; i++) {
		            for (int j = 0; j < inputLayer.length; j++) {
		                // Create the connection object and put it in both neurons, and give it the weight from the matriz W            	
		                Connection c = new Connection(inputLayer[j],outputLayer[i],W.getValuePos(i, j));
		                log.debug("Creando conexiÃ³n: From: "+ inputLayer[j] +" to " + outputLayer[i] + " con peso " + W.getValuePos(i, j));
		                this.outputLayer[i].addConnection(c);
		                this.inputLayer[j].addConnection(c);
		            }
		        }
		            this.W.printMatrix();
			}
			else{
				log.error("Las dimensiones de W no coinciden con el nÃºmero de neuronas del patrón \n");			
			}
		}
		
		
			//La red contiene bias
			//pre: W, V valuesInputLayer y desiredOutputLayer deben de estar inicializados
			//numNeuronsO = NÃºmero de neuronas en la capa oculta, bias incluido
			//valuesInputLayer =  vector que contiene los valores de las neuronas de entrada del patrÃ³n
			//desiredOutputLayer = contiene los valores de salida deseados
			///W tiene que tener dimensiones: NÂº ocultas X NÂº de Entradas
			//V tiene que tener dimensiones: NÂº de salidas X NÂº ocultas
			//post: 
			//post: 
			
			public void setUpPatronWithBias (BigDecimal[] valuesInputLayer, BigDecimal [] desiredOutputLayer, Matrix W,String funtion)
			{	
				int numNeuronsE = valuesInputLayer.length+1;
				int numNeuronsS = valuesInputLayer.length;
				
				log.debug ("Entrando en SetUpPatronWithoutBias. Número de neuronas de entrada: "+ 
						numNeuronsE + " Número de neuronas de salida: "+ valuesInputLayer.length);
				log.debug("Dimensiones de W (Filas X Columnas): (" + W.getRow()+ " X " + W.getColumn() +" )\n");
				
				if ( (W.getRow()  == numNeuronsS) && (W.getColumn() == numNeuronsE) ){
					
					this.desiredOutputLayer = desiredOutputLayer;
					this.numNeuronsE = valuesInputLayer.length + 1;
					this.numNeuronsS = valuesInputLayer.length;
					//this.learningCNT = learningCNT;
					this.W = W;
					this.FuntionType = funtion;
					
					//Tratamiento de la red con bias (el bias serÃ¡ algo propio de las capas de entrada y ocultas)
					//La red tiene bias,aÃ±adimos una neurona de valor uno en la capa de entrada y a la oculta 
			

					//Creamos las 3 capas
					this.inputLayer = new Neuron[numNeuronsE];
					this.outputLayer = new OutputNeuron[numNeuronsS];
					
					//Creamos las neuronas de la capa de entrada y le damos los valores del vector introducido por parÃ¡metros
					Neuron n = new Neuron (new BigDecimal(1), true, funtion);
					inputLayer[0] = n;
					for (int i = 0; i < valuesInputLayer.length; i++){
						n = new Neuron(valuesInputLayer[i], false, funtion);
						this.inputLayer[i+1] = n;
					}
				
					for (int i = 0; i < valuesInputLayer.length; i++){
						OutputNeuron nO = new OutputNeuron(funtion);
						this.outputLayer[i] = nO;
						nO.setDesiredOut(desiredOutputLayer[i]);
					}
			
					// Connect input layer to hidden layer
			        for (int i = 0; i < outputLayer.length; i++) {
			            for (int j = 0; j < inputLayer.length; j++) {
			                // Create the connection object and put it in both neurons, and give it the weight from the matriz W            	
			                Connection c = new Connection(inputLayer[j],outputLayer[i],W.getValuePos(i, j));
			                log.debug("Creando conexión: From: "+ inputLayer[j] +" to " + outputLayer[i] + " con peso " + W.getValuePos(i, j));
			                this.outputLayer[i].addConnection(c);
			                this.inputLayer[j].addConnection(c);
			            }
			        }
			        this.W.printMatrix();
					
				}
				else{
					log.error("Las dimensiones de W no coinciden con el número de neuronas del patrón \n");
				}
			}
		
		
		//Pre: Las neuronas de entrada deben de estar inicializados con valores vï¿½lidos
		//Pre: ejecuciï¿½n de setUpPatron
		public void feedForward (){
			log.trace("Ejecutando módulo feedForward() \n");
	        // Calculate the output of the output neuron
	       for (int i = 0; i < outputLayer.length; i++){
	    	   log.trace("Salidas capa salida");
	    	   outputLayer[i].calculateOutValue();   
	       }
			
		}

		//pre: Realizar el setup antes
		
		public void train (int idPatron, double learningCNT) {
			
			log.info("Entro en train e imprimo W y V");
			this.W.printMatrix();
			Matrix aux1W = this.W;
	        feedForward();
	        log.trace("Ejecutando mï¿½dulo train() after feedForward \n");

	    	log.trace("Just for make sure, we are showing the inputs that we are training:  \n");
	        for (Neuron i: inputLayer){
	        	log.trace(i.getOutValue());
	        }
	        
	       //This is where backpropagation starts
	      //Calculo los deltas de error de la capa de salida
	        BigDecimal[] deltaOutput = new BigDecimal[desiredOutputLayer.length];
	        for (int i = 0; i<outputLayer.length; i++){
	        	BigDecimal deltaE = desiredOutputLayer[i];
	        	deltaE = deltaE.subtract(outputLayer[i].getOutValue());
	        	//Si estamos usando la tangencial los deltan son multiplicados por la derivada de la funcion. 
	            if (FuntionType == "Tangencial"){
	            	BigDecimal aux = derivative(outputLayer[i].getOutValue());
	            	deltaE = deltaE.multiply(aux);   	
	            }
	            deltaE.setScale(Manager.PRECISION, RoundingMode.HALF_UP);
	            outputLayer[i].setDeltaError(deltaE); //Le metemos su delta de error correspondiente
	        	deltaOutput[i] = deltaE;
	        }
	        
	        //Pasamos esos deltas a un matriz para trabajar con ella
	        Matrix mDeltaOutput = new Matrix(deltaOutput);
		    mDeltaOutput = Matrix.transponer(mDeltaOutput); 
	        
	        log.debug("Mostrando delta de la capa de salida");
	        for (int i = 0; i< outputLayer.length; ++i){
	        	log.debug("Salida obtenida: "+ outputLayer[i].getOutValue()+ " Salida deseada: "+desiredOutputLayer[i] 
	        			+ " Delta de error "+ outputLayer[i].getDeltaError());	
	        }
	        //Actualización de matriz: Cálculo de deltaW
	        BigDecimal aux2[] = new BigDecimal[inputLayer.length];
	        for (int i = 0; i< inputLayer.length; i++){
	              aux2[i] = inputLayer[i].getOutValue();
	        }
	       Matrix mInputOuts = new Matrix(aux2);
	       mInputOuts.printMatrix();
	       
	  
	       Matrix deltaW = Matrix.product(mDeltaOutput, mInputOuts);
	 
	       
	       log.debug("Muestro deltaW antes de multiplicarla por learningCNT");
	       deltaW.printMatrix();
	       
	       deltaW = deltaW.multEscalar(learningCNT);
	       log.debug("Coeficiente de aprendizaje: "+ learningCNT);
	       log.debug("Muestro deltaW trás multiplicarla por el coeficiente de aprendizaje");
	       deltaW.printMatrix();

	       deltaW.truncarMatrixUP(Manager.PRECISION);
//	       writer.writeInfPatron(idPatron, W, V, inputLayer, desiredOutputLayer, hiddenLayer, outputLayer, 
//	    		   mDeltaOutput, mDeltaHidden, deltaW, deltaV);
//	       
	      
	       //Actualizamos las matrices con los deltas calculados
	       this.W = Matrix.addition(this.W, deltaW);		  	   
		   this.W.truncarMatrixUP(Manager.PRECISION);

		   
		   //Actualizamos las conexiones con las nuevas matrices (no es necesario en el trainnig)
	       //updateConnections(W, V);

	       
		}
		
	      
		//pre: Realizar el setup antes
		//previousW, previousV: matrices (t - 1) utilizadas en el momento beta  
		public void trainWithMomentB (int idPatron, double momentoB, double learningCNT) {
			log.info("Entro en train e imprimo W y V");
			this.W.printMatrix();
			Matrix aux1W = this.W;
	        feedForward();
	        log.trace("Ejecutando mï¿½dulo train() after feedForward \n");

	    	log.trace("Just for make sure, we are showing the inputs that we are training:  \n");
	        for (Neuron i: inputLayer){
	        	log.trace(i.getOutValue());
	        }
	        
	       //This is where backpropagation starts
	      //Calculo los deltas de error de la capa de salida
	        BigDecimal[] deltaOutput = new BigDecimal[desiredOutputLayer.length];
	        for (int i = 0; i<outputLayer.length; i++){
	        	BigDecimal deltaE = desiredOutputLayer[i];
	        	deltaE = deltaE.subtract(outputLayer[i].getOutValue());
	        	//Si estamos usando la tangencial los deltan son multiplicados por la derivada de la funcion. 
	            if (FuntionType == "Tangencial"){
	            	BigDecimal aux = derivative(outputLayer[i].getOutValue());
	            	deltaE = deltaE.multiply(aux);   	
	            }
	            deltaE.setScale(Manager.PRECISION, RoundingMode.HALF_UP);
	            outputLayer[i].setDeltaError(deltaE); //Le metemos su delta de error correspondiente
	        	deltaOutput[i] = deltaE;
	        }
	        
	        //Pasamos esos deltas a un matriz para trabajar con ella
	        Matrix mDeltaOutput = new Matrix(deltaOutput);
		    mDeltaOutput = Matrix.transponer(mDeltaOutput); 
	        
	        log.debug("Mostrando delta de la capa de salida");
	        for (int i = 0; i< outputLayer.length; ++i){
	        	log.debug("Salida obtenida: "+ outputLayer[i].getOutValue()+ " Salida deseada: "+desiredOutputLayer[i] 
	        			+ " Delta de error "+ outputLayer[i].getDeltaError());	
	        }
	        //Actualización de matriz: Cálculo de deltaW
	        BigDecimal aux2[] = new BigDecimal[inputLayer.length];
	        for (int i = 0; i< inputLayer.length; i++){
	              aux2[i] = inputLayer[i].getOutValue();
	        }
	       Matrix mInputOuts = new Matrix(aux2);
	       mInputOuts.printMatrix();
	       
	  
	       Matrix deltaW = Matrix.product(mDeltaOutput, mInputOuts);
	 
	       
	       log.debug("Muestro deltaW antes de multiplicarla por learningCNT");
	       deltaW.printMatrix();
	       
	       deltaW = deltaW.multEscalar(learningCNT);
	       log.debug("Coeficiente de aprendizaje: "+ learningCNT);
	       log.debug("Muestro deltaW trás multiplicarla por el coeficiente de aprendizaje");
	       deltaW.printMatrix();

	       deltaW.truncarMatrixUP(Manager.PRECISION);

		   //Antes de actualizar, guardamos estas matrices.
	       
	       Matrix momentW = this.W;
	       //And for update I'll use the previous of the previous instance (the parameter's one)
	       momentW = Matrix.subtraction(momentW, Manager.previousW);
	       momentW = momentW.multEscalar(momentoB);
	       
	       log.trace("showing (t-1):");
	       Manager.previousW.printMatrix();
	       
	       Manager.previousW = this.W;  //We save them before update
	       log.trace("showing (t):");
	       this.W.printMatrix();
	       
	       //Actualizamos las matrices con los deltas calculados
	       this.W = Matrix.addition(this.W, deltaW);
		   //Le sumamos el momento B
		   this.W = Matrix.addition(this.W, momentW);
		   this.W.truncarMatrixUP(Manager.PRECISION);
		   
		   log.trace("showing (t + 1 )");
	       this.W.printMatrix();
		}


		public Neuron[] getInputLayer() {
			return inputLayer;
		}



		public void setInputLayer(Neuron[] inputLayer) {
			this.inputLayer = inputLayer;
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
		
		public void setDesiredOutputLayer(BigDecimal[] desiredOutputLayer) {
			this.desiredOutputLayer = desiredOutputLayer;
		}
		
		
		//Las conexiones son creadas una vez,no dejan de ser punteros (aunq estemos en java)
		//si las modificamos en un Layer no necesitamos cambiarlas en los otros,
		//por lo q cogeremos solo hidden layer y asi modificaremos todos los pesos de la red, 
		//Si aï¿½adimos mï¿½s hidden layer solo tendrï¿½amos q hacer esto por cada hidden layer
		//post: modifica la clase network (vector hiddenLayer, inputLayer y outputLayer
//		public void updateConnections (Matrix W, Matrix V){
//			 for (int i = 0; i < hiddenLayer.length; i++) {
//				 for (Connection c: hiddenLayer[i].getConnections()){
//					 if (c.getTo() == hiddenLayer[i]){
//						 for (int j = 0; j < inputLayer.length; j++){
//							 if (inputLayer[j] == c.getFrom())
//								 c.setWeight(W.getValuePos(i, j));
//						 }
//					 }
//					 else{ //c.getFrom == hiddenLayer
//						 for (int j = 0; j < outputLayer.length; j++){
//							 if (outputLayer[j] == c.getTo())
//								 c.setWeight(V.getValuePos(j,i));
//						 }
//					 }
//				 }
//			}
//		}
//		
//		
		
		
		
		//Calculamos el error en un patrï¿½n, con las matrices de pesos resultantes
		//W: matriz W obtenida en el ï¿½ltimo patron entrenado
		//V: matriz V obtenida en el ï¿½ltimo patron entrenado
		//pre: W y V deben de ser matrices vï¿½lidas 
		public BigDecimal calculateError ()
		{
//			this.W = W;
//			this.V = V;
//			updateConnections(W, V);  //debemos actualizar el peso de las conexiones
			
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
		
		
		
		
		
		
		

	}

