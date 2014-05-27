//package dataManager;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.Writer;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Iterator;
//
//
//import architecture.NetworkManager;
//import utilities.Matrix;
//
//public class WriteOutcomes {
//	private String name; //Nombre archivo
//	private File outFile; //crear un nuevo archivo, si un archivo con el mismo nombre ya existe podríamos sin querer escribir contenido sobre el mismo
//	private FileWriter write;
//	private BufferedWriter bw;
//	private PrintWriter wr;
//	private NetworkManager network;
//	
//	
//	
//	
//	public WriteOutcomes() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public WriteOutcomes (String name, NetworkManager ne) {
//		super(); 
//		this.name = name;
//		this.outFile = new File (name);
//		this.network = ne;
//		openFile();
//	}
//	
//
//	public void openFile()
//	{
//		try {
//			write = new FileWriter(this.outFile);// objeto que reserva un espacio en memoria donde se guarda la información antes de ser escrita en un archivo.
//			bw = new BufferedWriter(write); //Es el objeto que utilizamos para escribir directamente sobre el archivo de texto.
//			wr = new PrintWriter(bw); //Objeto que tiene como función escribir datos en un archivo. 
//		}
//			catch (IOException e) {
//				e.printStackTrace();
//			}  
//	}
//	
//	
//	public void closeFile()
//	{
//		try {
//			wr.close();
//			bw.close();
//		}
//			catch (IOException e) {
//				e.printStackTrace();
//			} 
//		
//	}
//	
//	
//	//Getters and Setters
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public File getOutFile() {
//		return outFile;
//	}
//	public void setOutFile(File outFile) {
//		this.outFile = outFile;
//	}
//	
//	public FileWriter getWrite() {
//		return write;
//	}
//	public void setWrite(FileWriter write) {
//		this.write = write;
//	}
//	public BufferedWriter getBw() {
//		return bw;
//	}
//	public void setBw(BufferedWriter bw) {
//		this.bw = bw;
//	}
//	public PrintWriter getWr() {
//		return wr;
//	}
//	public void setWr(PrintWriter wr) {
//		this.wr = wr;
//	}
//	
//	public void writeStringAppend (String cadena){
//		wr.append(cadena);
//		wr.append("\n");
//	}
//	
//	public void writeBigDecimal (BigDecimal b){
//		wr.append(b.toString());
//		
//	}
//	
//	
//	//Escribe el input correspondiente al id q le pasamos por parámetro
////	public void writeInput (int id){
////		Neuron aux = network.getInput(id);
////		wr.append ("\n");
////	//	wr.append ("Iteración número "+ network.getIteration()+ "\n\n");
////	    wr.append ("Input "+ id+" (");
////		for (BigDecimal b: aux.getValues()){
////			wr.append(b+ " ");	
////		}
////		wr.append(") \n");
////	}
////	
////	
////		
////	//Escribe el output correspondiente al id q le pasamos por parámetro
////	public void writeDesiredOutput (int id){
////		Neuron aux = network.getDesiredOutput(id);
////		wr.append ("\n");
////	    wr.append ("Desired Output "+ id+" (");
////		for (BigDecimal b: aux.getValues()){
////			wr.append(b+ " ");	
////		}
////		wr.append(") \n");
////	}
////	
////	
//	
//	
//	public void writeDesiredOutputs (){
//		for (BigDecimal[] aux: network.getDesiredOutputs()){
//			wr.append ("\n");
//		    wr.append ("Desired Output: (");
//			for (BigDecimal b: aux){
//				wr.append(b+ " ");	
//			}
//			wr.append(") \n");
//		}
//	}
//	
//	public void writeMatrix (Matrix MWeight)
//	{
//		for (int i = 0; i < MWeight.getRow(); i++){
//			wr.append("(");
//			for (int j= 0; j < MWeight.getColumn(); j++)
//				wr.append(MWeight.getValuePos(i, j) +  " ");
//			 wr.append(") \n");}
//	}
//	
//	
//	public void writeErrorInf (ArrayList<BigDecimal> errorList, BigDecimal error, int it)
//	{
//		wr.append ("\n");
//		wr.append("En iteracción "+ it+ " los errores parciales obtenidos son: \n");
//		for (BigDecimal b: errorList){
//			wr.append (b + "\n");
//		}
//			wr.append ("\n");
//			wr.append ("Error ponderado = "+ error);	
//
//	}
//	
//	
//	public void writeBasicInformation(){
//		
//		wr.append("Entrenamiento con "+ network.getNumPatrones()+ " patrones \n"+ 
//				  "Coeficiente de aprendizaje: "+ network.getLearningCNT()+ "\n" +
//				  "Nº de neuronas en la capa de entrada: "+ network.getNumNeuronsES()+ "\n" +
//				  "Nº de neuronas en la capa de salida: "+ network.getNumNeuronsES()+ "\n" +
//		          "Nº de neuronas en la capa oculta: "+ network.getNumNeuronsO()+ "\n" +
//		          //"Bias: "+ network.getBias()+ "\n" +
//				  "Cota error: "+ network.getCuote()+ "\n"+
//				  "Máximo número de iteracciones permitidas: "+ network.getIterMax() + "\n");
//	}
//	
//
//
//
//}
