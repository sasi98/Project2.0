package dataManager;

import java.math.BigDecimal;
import java.util.ArrayList;

import utilities.WeightMatrix;
import architecture.Network;

public class CalculateOutputsWindowOuts extends WriteExcel {

	public CalculateOutputsWindowOuts() {
		// TODO Auto-generated constructor stub
	}


	public CalculateOutputsWindowOuts(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public void results(String name, WeightMatrix matrices, ArrayList<BigDecimal[]> inputs, 
			ArrayList<BigDecimal[]> desiredOutput, ArrayList<BigDecimal[]> outputs){
		wr.append("Calculamos las salidas para la red: "+name);
		wr.append("Matrices de pesos utilizadas: \n");
		writeMatrices(matrices);
		wr.append("\nComprobamos los resultados");
		writeInputsOuDesiredOuObtained(inputs, outputs, desiredOutput);
		closeFile();
	}

}
