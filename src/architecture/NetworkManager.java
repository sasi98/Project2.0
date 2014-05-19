package architecture;

import java.math.BigDecimal;
import java.util.ArrayList;

import utilities.Matrix;

public class NetworkManager {
	
	private int 					numPatrones,
									numNeuronsES, 
									iterMax;
	
	private BigDecimal 				cuote,
									learningCNT;
	
	ArrayList<BigDecimal[]> 		inputs, 
									desiredOutputs;
	
	
	private ArrayList<BigDecimal>   listError;
	
	 
	

	public NetworkManager() {
		// TODO Auto-generated constructor stub
	}

}
