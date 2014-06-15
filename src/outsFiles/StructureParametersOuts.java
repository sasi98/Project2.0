package outsFiles;
import gui.MainWindow;

import java.math.BigDecimal;
import java.util.ArrayList;

import valueset.Value;
import architecture.Manager;
import architecture.StructureParameters;

public class StructureParametersOuts extends WriteFile{

	public StructureParametersOuts (String nameFile) {
		super(nameFile);
	}
		
	public void consoleOut (StructureParameters red){
		wr.append("Creada Red de datos de las siguientes caracter�sticas:\n\n");
		wr.append("Nombre: " +red.getName()+"\n");
		wr.append("Tipo de red: " +red.getTypeNet()+"\n");
		wr.append("Tipo de datos: " +red.getTypeData()+"\n");
		wr.append("N� de neuronas de entrada: "+red.getNumNeuronsE()+ "\n");
		wr.append("N� de neuronas de salida: "+red.getNumNeuronsS()+ "\n");
		if (red.getTypeNet() == Value.RedType.SIMPLE)
			wr.append("N� de neuronas ocultas: "+red.getNumNeuronsO()+ "\n");
		wr.append("N� de patrones: "+red.getNumPatterns()+ "\n");
		if (red.hasBias())
			wr.append("Bias: S�\n");
		else
			wr.append("Bias: No\n");
		wr.append("\nMostramos los datos de la red: \n");
		for (int i = 0; i<red.getInputs().size(); i++){
			wr.append("PATR�N: "+ i+ "\n");
			for (int j = 0; j< red.getInputs().get(i).length; j++){
				wr.append("Rm: " +red.getInputs().get(i)[j]+ "     Ri: " + red.getDesiredOutputs().get(i)[j]+ "\n");
			}
			wr.append("\n");
		}
		this.closeFile();
	}
	

	//Crea un fichero de texto simple con los par�metros de la clase Structure Parameters introducida por par�metros
	//Los par�metros est�n separados por el token ; 
	//Pre: Debe contener el mismo formato que el fichero de carga de structure Parameters 
	public void saveStructureParameters (StructureParameters red){
		wr.append(red.getName()+";"+red.getTypeNet()+";"+red.getTypeData()+";"+red.getNumNeuronsE()
				+";"+red.getNumNeuronsS()+";"+red.getNumNeuronsO()+";"+red.getNumPatterns()+
				";"+red.hasBias()+"\n");
		for (BigDecimal[] pattern: red.getInputs()){
			for (BigDecimal a: pattern){
				wr.append(a+";");
			}
			wr.append("\n");
		}
		for (BigDecimal[] pattern: red.getDesiredOutputs()){
			for (BigDecimal a: pattern){
				wr.append(a+";");
			}
			wr.append("\n");
		}	
		this.closeFile();
	}
	
	
		
		
	

}
	