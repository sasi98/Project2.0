package outsFiles;

import java.math.BigDecimal;
import java.util.ArrayList;

import utilities.WeightMatrix;
import architecture.Network;

public class CalculateOutputsOuts extends WriteFile {

	public CalculateOutputsOuts() {
		// TODO Auto-generated constructor stub
	}


	public CalculateOutputsOuts(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public void results(String name, WeightMatrix matrices, ArrayList<BigDecimal[]> inputs, 
			ArrayList<BigDecimal[]> desiredOutput, ArrayList<BigDecimal[]> outputs){
		wr.append("Calculamos las salidas para la red: "+name+ "\n");
		wr.append("Matrices de pesos utilizadas: \n");
		writeMatrices(matrices);
		wr.append("\nComprobamos los resultados");
		writeInputsOuDesiredOuObtained(inputs, outputs, desiredOutput);
		closeFile();
	}

}
