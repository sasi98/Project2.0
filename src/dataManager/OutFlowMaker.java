//package dataManager;
//
//import architecture.Network;
//
//public class OutFlowMaker {
//	
//	private WriteExcel printInputs,
//					   printOutputs,
//					   printWeightMatrix, 
//					   printErrorProgress;
//
//	public OutFlowMaker(WriteExcel printInputs, WriteExcel printOutputs,
//			WriteExcel printWeightMatrix, WriteExcel printErrorProgress) {
//		super();
//		this.printInputs = printInputs;
//		this.printOutputs = printOutputs;
//		this.printWeightMatrix = printWeightMatrix;
//		this.printErrorProgress = printErrorProgress;
//	}
//
//	public OutFlowMaker() {
//		super();
//		printInputs = new WriteExcel ("C:\\Users\\Esther\\Desktop\\Historial log\\actual\\inputs.csv", Network.getInstance());
//		printOutputs = new WriteExcel ("C:\\Users\\Esther\\Desktop\\Historial log\\actual\\outputs.csv", Network.getInstance());
//		printWeightMatrix = new WriteExcel ("C:\\Users\\Esther\\Desktop\\Historial log\\actual\\weightMatrix.csv", Network.getInstance());
//		printErrorProgress = new WriteExcel ("C:\\Users\\Esther\\Desktop\\Historial log\\actual\\errorProgress.csv", Network.getInstance());	
//		
//	}
//	
//	public void trainingResults()
//	{
//		printInputs.writeInputs();
//		printOutputs.writeDesiredOutputs();
//		printWeightMatrix.writeWeightMatrix();
//		printErrorProgress.writeErrorProgress();
//	}
//	
//	public void closeFiles()
//	{
//		printInputs.closeFile();
//		printOutputs.closeFile();
//		printWeightMatrix.closeFile();
//		printErrorProgress.closeFile();
//	}
//	
//	
//	
//	
//	
//}
