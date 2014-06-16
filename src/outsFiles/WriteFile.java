package outsFiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import utilities.Matrix;
import utilities.WeightMatrix;
import architecture.Network;
import architecture.Manager;
import architecture.Neuron;
import architecture.OutputNeuron;

public class WriteFile {
		protected String name; //Nombre archivo
		protected File outFile; //crear un nuevo archivo, si un archivo con el mismo nombre ya existe podríamos sin querer escribir contenido sobre el mismo
		protected FileWriter write;
		protected BufferedWriter bw;
		protected PrintWriter wr;
		private static final Logger log = Logger.getLogger(WriteFile.class);
				
		
		public WriteFile() {
			super();
			// TODO Auto-generated constructor stub
		}
		public WriteFile (String name, Network ne) {
			super(); 
			this.name = name;
			this.outFile = new File (name);
			openFile();
		}
		
		public WriteFile (String name) {
			super(); 
			log.debug("Creando WriteExcel: "+ name+ "Puntero: "+this);
			this.name = name;
			this.outFile = new File (name);
			openFile();
		}
		

		public void openFile()
		{
			try {
				log.trace("Abriendo archivo, listo para escribir ");
				write = new FileWriter(this.outFile);// objeto que reserva un espacio en memoria donde se guarda la información antes de ser escrita en un archivo.
				bw = new BufferedWriter(write); //Es el objeto que utilizamos para escribir directamente sobre el archivo de texto.
				wr = new PrintWriter(bw); //Objeto que tiene como función escribir datos en un archivo. 
			}
				catch (IOException e) {
					e.printStackTrace();
				}  
		}
		
		
		public void closeFile()
		{
			try {
				wr.close();
				bw.close();
			}
				catch (IOException e) {
					e.printStackTrace();
				} 
			
		}
		
		
		//Getters and Setters
//		public String getName() {
//			return name;
//		}
//		public void setName(String name) {
//			this.name = name;
//		}
//		public File getOutFile() {
//			return outFile;
//		}
//		public void setOutFile(File outFile) {
//			this.outFile = outFile;
//		}
//		
//		public FileWriter getWrite() {
//			return write;
//		}
//		public void setWrite(FileWriter write) {
//			this.write = write;
//		}
//		public BufferedWriter getBw() {
//			return bw;
//		}
//		public void setBw(BufferedWriter bw) {
//			this.bw = bw;
//		}
//		public PrintWriter getWr() {
//			return wr;
//		}
//		public void setWr(PrintWriter wr) {
//			this.wr = wr;
//		}
		
//		public void writeStringAppend (String cadena){
//			wr.append(cadena);
//			wr.append("\n");
//		}
//		
//		public void writeBigDecimal (BigDecimal b){
//			wr.append(b.toString());
//			
//		}
//		
//		public void writeNeuron (Neuron n){
//			for (BigDecimal b: n.getValues()){
//				wr.append(b+ " ");	
//			}
//				wr.append(") \n");
//		}
//		
		
		
//		
//		
//		//Escribe el input correspondiente al id q le pasamos por parámetro
//		public void writeInput (int id){
//			Neuron aux = network.getInput(id);
//		
//		    wr.append ("\n Input:  ;"+ id+"\n");
//			for (BigDecimal b: aux.getValues()){
//				String valueStr = b.toString();
//				valueStr = valueStr.replace(".", ",");
//				wr.append(valueStr+ ";");	
//			}
//			wr.append("\n");
//		}
//		
//	
		
		
		
			
//		//Escribe el output correspondiente al id q le pasamos por parámetro
//		public void writeDesiredOutput (int id){
//			Neuron aux = network.getDesiredOutput(id);
//			wr.append ("\n");
//		    wr.append ("Desired Output "+ id+" (");
//			for (BigDecimal b: aux.getValues()){
//				wr.append(b+ " ");	
//			}
//			wr.append(") \n");
//		}
//		
//		
//		public void writeOutputs (){
//			for (Neuron aux: network.getOutputs()){
//				wr.append ("\n");
//			    wr.append ("Output: (");
//				for (BigDecimal b: aux.getValues()){
//					wr.append(b+ " ");	
//				}
//				wr.append(") \n");
//			}
//		}
//		
//		public void writeInputs (){
//			for (Neuron o: network.getInputs()){
//				for (BigDecimal b: o.getValues()){
//					String valueStr = b.toString();
//					valueStr = valueStr.replace(".", ",");
//					wr.append(valueStr+ ";");	
//				}
//				wr.append("\n");
//			}
//		}
//		
//		public void writeDesiredOutputs (){
//			for (Neuron o: network.getDesiredOutputs()){
//				for (BigDecimal b: o.getValues()){
//					String valueStr = b.toString();
//					valueStr = valueStr.replace(".", ",");
//					wr.append(valueStr+ ";");		
//				}
//				wr.append("\n");
//			}
//		}
//		
//		
//		
//		
//		
//		public void writeWeightMatrix()
//		{
//		    Matrix aux = network.getMatrixWeight();
//			 for (int i = 0; i < aux.getRow(); i++){
//				 for (int j= 0; j < aux.getColumn(); j++){
//					 String valueStr = aux.getValuePos(i, j).toString();
//					 valueStr = valueStr.replace(".", ",");
//					 wr.append(valueStr+ ";");
//				 }
//				 wr.append("\n");}
//		}
//		
//		public void writeErrorProgress(){
//			ArrayList<BigDecimal> aux = network.getErrorTraining();
//			for (BigDecimal e: aux){
//				log.trace("writeErrorProgres Bigdecimal: " + e);
//				String valueStr = e.toString();
//				valueStr = valueStr.replace(".", ",");
//				log.trace("writeErrorProgres en cadena: " + valueStr);
//				wr.append(valueStr+ "\n");	
//			}
//		}
		
		
//		public void writeErrorInf(int it)
//		{
//			wr.append ("\n");
//			wr.append("En iteracción "+ it+ " los errores parciales obtenidos son: \n");
//			for (BigDecimal b: network.getSaveError()){
//				wr.append (b + "\n");
//			}
//				wr.append ("\n");
//				wr.append ("Error ponderado = "+ network.getCurrentE());	
//
//		}
//		
//		
//		public void writeBasicInformation(){
//			
//			wr.append("Entrenamiento con "+ network.getInputs().size()+ " patrones. Los datos corresponden a la empresa "+ network.getCompany().getName() + "\n");
//			wr.append("Todos los datos han sido normalizados \n");
//			wr.append("Coeficiente de aprendizaje: "+ Network.LEARNING_CONSTANT+ "\n");
//			wr.append("Cota error: "+ Network.COTA+ "\n");
//			wr.append("Máximo número de iteracciones: "+ Network.ITERATION_MAX+ "\n");
//		}
//		
//		public void writeSolutionFound(int it){
//			wr.append("\n El entrenamiento ha llegado a una solución en la iteración "+ it+ "\n");
//			wr.append("Matriz de pesos resultante:\n" );
//			writeWeightMatrix();
//		}
//
//		
//		
//		public void writeCompareOutputs (Neuron d, Neuron o){
//			BigDecimal aux[] = d.getValues();
//			wr.append("Deseada: (");
//			for (BigDecimal b: d.getValues()){
//				wr.append(b+ " ");	
//			}
//			wr.append(") \nObtenida: (");
//			int i = 0;
//			for (BigDecimal b: o.getValues()){
//				wr.append(b+ " ");	
//				aux[i] = aux[i].abs().subtract(b.abs());
//				i++;
//			}
//			wr.append(") \nDiferencia: (");
//			for (BigDecimal b: aux){
//				wr.append(b+ " ");	
//			}
//			wr.append(") \n\n");
//			
//		}

		
//		public void writeGivenArrayNeuron (ArrayList<Neuron> arrayNeuron, String dataTipe){
//			wr.append(dataTipe+ ": "+ "; ");
//			for (Neuron o: arrayNeuron){
//				for (BigDecimal b: o.getValues()){
//					String valueStr = b.toString();
//					valueStr = valueStr.replace(".", ",");
//					wr.append(valueStr+ ";");	
//				}
//				wr.append("\n");
//			}
//			wr.append("\n");
//		}
		
		
		public File getOutFile() {
			return outFile;
		}
		public void setOutFile(File outFile) {
			this.outFile = outFile;
		}
		//pre: el fichero debe de existir y estar abierto e inicializado
		//Escribe en un fichero excel la información global obtenida en una iteración. 
		//iteration: entero que identifica la iteración en la que estamos
		//Matrices resultantes de la iteración (con las que hemos calculado el error)
		//error: error ponderado de la iteración
		public void writeOneIterationInf (int iteration, BigDecimal error, Matrix W, Matrix V){
			
			wr.append("Iteration: ;" + iteration+"\n");
			wr.append("Matriz W resultante: \n");
			 for (int i = 0; i < W.getRow(); i++){
				 for (int j= 0; j < W.getColumn(); j++){
					 String valueStr = W.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";"); //Siguiente celda
				 }
				 
				 wr.append("\n"); //Salto de fila en excel
			 }
			 wr.append("Matriz V resultante: \n");
			 for (int i = 0; i < V.getRow(); i++){
				 for (int j= 0; j < V.getColumn(); j++){
					 String valueStr = V.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";");
				 }
				 wr.append("\n");
			 }
			 wr.append("Error ponderado: ;"+ error+"\n\n");
			 
		}
			
			
		//Escribe la información correspondiente al entrenamiento de un patrón.
		//idPatron: identificador del patrón que estamos entrenando actualmente
		//W y V iniciales antes de la actualización
		//inputLayer: Vector que contiene las neuronas de la capa de entrada del patrón
		//hiddenLayer: Vector que contiene las neuronas de capa oculta del patrón
		//outputLayer: Vector que contiene las neuronas de capa de salida del patrón
		//desiredOutputLayer: Vector que contiene los VALORES que representan a las salidas deseadas
		//mDeltaOutput: Matriz que representa a los delta de la capa de salida
		//mDeltaHidden: Matriz que representa a los delta de la capa oculta
		//deltaW: Matriz que representa el incremento a añadir a la matriz W (multiplicado ya por el coeficiente de aprendizaje)
		//deltaV: Matriz que representa el incremento a añadir a la matriz V 
		public void writeInfPatron(int idPatron, Matrix W, Matrix V, Neuron[] inputLayer, BigDecimal[] desiredOutputLayer, 
				Neuron[] hiddenLayer, OutputNeuron[] outputLayer, 
	    		   Matrix mDeltaOutput, Matrix mDeltaHidden, Matrix deltaW, Matrix deltaV){
			
			wr.append("\nPatrón número :" + idPatron +"\n");
			wr.append("\nInputs: \n");
			for (Neuron i: inputLayer){
				wr.append(i.getOutValue()+ ";");
			}
			wr.append("\n Matriz W: \n");
			 for (int i = 0; i < W.getRow(); i++){
				 for (int j= 0; j < W.getColumn(); j++){
					 String valueStr = W.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";"); //Siguiente celda
				 }
				 
				 wr.append("\n"); //Salto de fila en excel
			 }
			 wr.append("Matriz V: \n");
			 for (int i = 0; i < V.getRow(); i++){
				 for (int j= 0; j < V.getColumn(); j++){
					 String valueStr = V.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";");
				 }
				 wr.append("\n");
			 }
			 wr.append("\n Valores ocultas: \n");
			 for (Neuron i: hiddenLayer){
				wr.append(i.getOutValue().setScale(Manager.PRECISION, RoundingMode.HALF_UP)+ ";");
			 }
			 wr.append("\n Valores salidas: \n");
			 for (Neuron i: outputLayer){
				wr.append(i.getOutValue().setScale(Manager.PRECISION, RoundingMode.HALF_UP)+ ";");
			} 
			 wr.append("\n Salidas deseadas: \n");
			 for (BigDecimal b: desiredOutputLayer){
				wr.append(b + ";");
			}
			 wr.append("\n DeltaE capa de salida: \n");
			 for (int i = 0; i < mDeltaOutput.getRow(); i++){
				 for (int j= 0; j < mDeltaOutput.getColumn(); j++){
					 String valueStr = mDeltaOutput.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";"); //Siguiente celda
				 }
				 
				 wr.append("\n"); //Salto de fila en excel
			 }
			 wr.append("DeltaE capa oculta: ");
			 for (int i = 0; i < mDeltaHidden.getRow(); i++){
				 for (int j= 0; j < mDeltaHidden.getColumn(); j++){
					 String valueStr = mDeltaHidden.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";");
				 }
				 wr.append("\n");
			 }
			 
			 wr.append("Delta W: \n");
			 for (int i = 0; i < deltaW.getRow(); i++){
				 for (int j= 0; j < deltaW.getColumn(); j++){
					 String valueStr = deltaW.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";"); //Siguiente celda
				 }
				 
				 wr.append("\n"); //Salto de fila en excel
			 }
			 wr.append("Delta V:  ");
			 for (int i = 0; i < deltaV.getRow(); i++){
				 for (int j= 0; j < deltaV.getColumn(); j++){
					 String valueStr = deltaV.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";");
				 }
				 wr.append("\n");
			 }
			 
		}
	
		
		
			 
		public void writeError (BigDecimal errorIt, int iteration){
			wr.append("Iteration: ; "+ iteration+ ";");
			wr.append("Error: ;" + errorIt+"\n");
			
		}
		
		public void printMatrixIntoFile (Matrix matrix){
			for (int i = 0; i < matrix.getRow(); i++){
				 for (int j= 0; j < matrix.getColumn(); j++){
					 BigDecimal b = matrix.getValuePos(i, j);
					 wr.append(b+ ";");
					// String valueStr = matrix.getValuePos(i, j).toString();
					// valueStr = valueStr.replace(".", ",");
					// wr.append(valueStr+ ";"); //Siguiente celda
				 }
				 wr.append("\n"); //Salto de fila en excel
			 }
		}
		
		public void printMatrixIntoCSV (PrintWriter wr, Matrix matrix){
			for (int i = 0; i < matrix.getRow(); i++){
				 for (int j= 0; j < matrix.getColumn(); j++){
					 String valueStr = matrix.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";"); //Siguiente celda
				 }
				 wr.append("\n"); //Salto de fila en excel
			 }
		}
		
		public void checkingMomentB (int idPatron, Matrix W, Matrix V, Matrix previousW, Matrix previousV){
			wr.append("Id Patrón: "+ idPatron+"\n");
			wr.append("W (t) Current: \n");
			printMatrixIntoCSV(wr, W);
			wr.append("V (t) Current: \n");
			printMatrixIntoCSV(wr,V);
			wr.append("W (t-1) Previous: \n");
			printMatrixIntoCSV(wr, previousW);
			wr.append("V (t-1) Previous:  \n");
			printMatrixIntoCSV(wr, previousV);
			wr.append("\n");
		}
		
		
			
		//it must write in the same format than readWeightMatrix from ReadFile class
		public void writeMatrices (WeightMatrix matrices){
			Matrix W = matrices.getW();
			Matrix V = matrices.getV();
			wr.append("Matrix W:\n");
			 for (int i = 0; i < W.getRow(); i++){
				 for (int j= 0; j < W.getColumn(); j++){
					 String valueStr = W.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";"); //Siguiente celda
				 }
				 
				 wr.append("\n"); //Salto de fila en excel
			 }
			 if (V != null){ 
				 wr.append("Matrix V:\n");
				 for (int i = 0; i < V.getRow(); i++){
					 for (int j= 0; j < V.getColumn(); j++){
						 String valueStr = V.getValuePos(i, j).toString();
						 valueStr = valueStr.replace(".", ",");
						 wr.append(valueStr+ ";");
					 }
					 wr.append("\n");
				 }
			 }
		}
		
		public void writeMatriz (Matrix W){
			wr.append("Matrix W:\n");
			 for (int i = 0; i < W.getRow(); i++){
				 for (int j= 0; j < W.getColumn(); j++){
					 String valueStr = W.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";"); //Siguiente celda
				 }
				 
				 wr.append("\n"); //Salto de fila en excel
			 }
		}
		
		
		//Escribe en el fichero los dos vectores introducidos por parámetrosb
		public void writeInputsOutputs(ArrayList<BigDecimal[]> inputs, ArrayList<BigDecimal[]> desiredOutput){
			for (int i = 0; i<inputs.size(); i++){
				wr.append("Patrón: "+ i+ "\n");
				for (int j = 0; j< inputs.get(i).length; j++){
					wr.append("Rm: " +inputs.get(i)[j]+ " Ri: " + desiredOutput.get(i)[j]+ "\n");
				}
				wr.append("\n");
			}
		}
		
		public void writeOuDesiredOuObtained(ArrayList<BigDecimal[]> outputs, ArrayList<BigDecimal[]> desiredOutput){
			for (int i = 0; i<outputs.size(); i++){
				wr.append("Patrón: "+ i+ "\n");
				for (int j = 0; j< outputs.get(i).length; j++){
					wr.append("Obtenida:  " +outputs.get(i)[j]+ " Deseada  " + desiredOutput.get(i)[j]+ "\n");
				}
				wr.append("\n");
			}
		}
		
		public void writeInputsOuDesiredOuObtained(ArrayList<BigDecimal[]> inputs, ArrayList<BigDecimal[]> outputs, ArrayList<BigDecimal[]> desiredOutput){
			for (int i = 0; i<outputs.size(); i++){
				wr.append("Patrón: "+ i+ "\n");
				for (int j = 0; j< outputs.get(i).length; j++){
					wr.append("RM:  "+ inputs.get(i)[j]+ "  RI Obtenida:  " +outputs.get(i)[j]+ "  RI Deseada:  " + desiredOutput.get(i)[j]+ "\n");
				}
				wr.append("\n");
			}
		}
		
		
		
}
