package architecture;

import java.math.BigDecimal;
import java.util.ArrayList;

import valueset.Value;

public class StructureParameters {
	
	private String     				name,
							  	   	typeNet,
							  	   	typeData; 
				  	   
	private int 	 	            numNeuronsE,
									numNeuronsS,
									numNeuronsO, 
									numPatterns;
				
	private ArrayList<BigDecimal[]> inputs, 
									desiredOutputs;
	private boolean 	bias;
	


	// pre: inputs and desiredOutputs deben ser arrays válidos (creados por la
	// clase PatronData, en la interfaz)
	// sizeNetwork: tamaño de la red, representa el número de neuronas de
	// entrada o salida que tendrá la red, sin incluir
	// neurona bias en el caso
	// numNeuronsO: Número de neuronas en la capa oculta, de nuevo sin incluir
	// la neurona bias en el caso
	public StructureParameters (String name, String typeNet, String typeData, int numPatrones, int sizeNetwork,
			int numNeuronsO, ArrayList<BigDecimal[]> inputs, ArrayList<BigDecimal[]> desiredOutputs, boolean bias) {
		
		super();
		this.typeNet = typeNet;
		this.typeData = typeData;
		this.name = name;
		this.numPatterns = numPatrones;
		this.numNeuronsS = sizeNetwork;
		if (bias) {
			this.numNeuronsE = sizeNetwork + 1;
			if (typeNet.compareTo(Value.RedType.MULTICAPA) == 0)
				this.numNeuronsO = numNeuronsO + 1;
		} else {
			this.numNeuronsE = sizeNetwork;
			this.numNeuronsO = numNeuronsO;
		}
		this.inputs = inputs;
		this.desiredOutputs = desiredOutputs;
		this.bias = bias;
	}


	/**GETTERS*/
	
	public String getName() {
		return name;
	}
	public String getTypeNet() {
		return typeNet;
	}
	public String getTypeData() {
		return typeData;
	}
	public int getNumNeuronsE() {
		return numNeuronsE;
	}
	public int getNumNeuronsS() {
		return numNeuronsS;
	}
	public int getNumNeuronsO() {
		return numNeuronsO;
	}
	public int getNumPatterns() {
		return numPatterns;
	}
	public ArrayList<BigDecimal[]> getInputs() {
		return inputs;
	}
	public ArrayList<BigDecimal[]> getDesiredOutputs() {
		return desiredOutputs;
	}
	
	/**SETTERS*/
	
	public void setTypeNet(String typeNet) {
		this.typeNet = typeNet;
	}
	public void setTypeData(String typeData) {
		this.typeData = typeData;
	}
	public void setNumNeuronsE(int numNeuronsE) {
		this.numNeuronsE = numNeuronsE;
	}
	public void setNumNeuronsS(int numNeuronsS) {
		this.numNeuronsS = numNeuronsS;
	}
	public void setNumNeuronsO(int numNeuronsO) {
		this.numNeuronsO = numNeuronsO;
	}
	public void setNumPatterns(int numPatterns) {
		this.numPatterns = numPatterns;
	}
	public void setInputs(ArrayList<BigDecimal[]> inputs) {
		this.inputs = inputs;
	}
	public void setDesiredOutputs(ArrayList<BigDecimal[]> desiredOutputs) {
		this.desiredOutputs = desiredOutputs;
	}
	public boolean hasBias() {
		return bias;
	}
	public void setBias(boolean bias) {
		this.bias = bias;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	 public String toString() {
	  return "          StructureParameters " + "\nname:" + name + ""
	    + "\ntypeNet: " + typeNet + "" + "\ntypeData: " + typeData + ""
	    + "\nnumNeuronsE: " + numNeuronsE + "" + "\nnumNeuronsS: "
	    + numNeuronsS + "\nnumNeuronsO: " + numNeuronsO
	    + "\nnumPatterns: " + numPatterns + "\ninputs: " + inputs
	    + "\ndesiredOutputs: " + desiredOutputs + "\nbias: " + bias
	    + "";
	 }
	
	public void print(){
		System.out.print("Nombre: " +name+ " "+" Tipo de red: "+typeNet+" Tipo de datos: "+typeData+" Bias: "+ bias+
				" NE "+numNeuronsE+" NS "+numNeuronsS+" NO "+numNeuronsO+"\n");
		System.out.print("Inputs: \n");
		for (BigDecimal[] pattern: inputs){
			for (BigDecimal a: pattern){
				System.out.print(a+ " ");
			}
			System.out.print("\n");
		}
		System.out.print("Desired outputs: \n");
		for (BigDecimal[] pattern: desiredOutputs){
			for (BigDecimal a: pattern){
				System.out.print(a+ " ");
			}
			System.out.print("\n");
		}		

	}
	
	

}
