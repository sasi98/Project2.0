package dataManager;

import java.math.BigDecimal;
import java.util.ArrayList;

public class NewNetworkWindowOuts extends WriteExcel{

	public NewNetworkWindowOuts (String nameFile) {
		super(nameFile);
	}
		
	public void createNewNetworkingOut (String idcomp, int numES, int numO, int numP, boolean bias, 
		String kindData, String name, ArrayList<BigDecimal[]> inputs, ArrayList<BigDecimal[]> desiredOutput){
		
		wr.append("Creada Red de datos "+ name+ "\n"+ "Nº Neuronas E/S sin tener en cuenta el bias: "+ numES+
			"\nNº de Neuronas en la capa oculta: "+ numO+ "\n"+ "Nº de Patrones: "+ numP+"\nLos datos son "	+kindData+"\n");
		if (bias)
			wr.append("Esta red contendrá Bias");
		else
			wr.append("Esta red no va a contener Bias");
		
		wr.append("\n\nMostramos los datos de la red: \n");
		for (int i = 0; i<inputs.size(); i++){
			wr.append("PATRÓN: "+ i+ "\n");
			for (int j = 0; j< inputs.get(i).length; j++){
				wr.append("Rm: " +inputs.get(i)[j]+ "     Ri: " + desiredOutput.get(i)[j]+ "\n");
			}
			wr.append("\n");
		}
		this.closeFile();
	}
		
		
		
	

}
	