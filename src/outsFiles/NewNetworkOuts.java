package outsFiles;
import java.math.BigDecimal;
import java.util.ArrayList;

public class NewNetworkOuts extends WriteExcel{

	public NewNetworkOuts (String nameFile) {
		super(nameFile);
	}
		
	public void createNewNetworkingOut (String idcomp, int numES, int numO, int numP, boolean bias, 
		String kindData, String name, ArrayList<BigDecimal[]> inputs, ArrayList<BigDecimal[]> desiredOutput){
		
		if (numO == 0){
			wr.append("Creada Red de datos simple "+ name+ "\n"+ "N� Neuronas E/S sin tener en cuenta el bias: "+ numES+
					"\n"+ "N� de Patrones: "+ numP+"\nLos datos son "	+kindData+"\n");
		}else{
			wr.append("Creada Red de datos con capa oculta "+ name+ "\n"+ "N� Neuronas E/S sin tener en cuenta el bias: "+ numES+
					"\nN� de Neuronas en la capa oculta: "+ numO+ "\n"+ "N� de Patrones: "+ numP+"\nLos datos son "	+kindData+"\n");
		}
		if (bias)
			wr.append("Esta red contendr� Bias");
		else
			wr.append("Esta red no va a contener Bias");
		
		wr.append("\n\nMostramos los datos de la red: \n");
		for (int i = 0; i<inputs.size(); i++){
			wr.append("PATR�N: "+ i+ "\n");
			for (int j = 0; j< inputs.get(i).length; j++){
				wr.append("Rm: " +inputs.get(i)[j]+ "     Ri: " + desiredOutput.get(i)[j]+ "\n");
			}
			wr.append("\n");
		}
		this.closeFile();
	}
		
	
	
		
		
	

}
	