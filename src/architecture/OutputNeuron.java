package architecture;

import java.math.BigDecimal;

public class OutputNeuron extends Neuron {
	
	BigDecimal deltaError; //Representa el delta de error de esta neurona de salida. 
						   // Salida deseada - salida obtenida
	BigDecimal desiredOut;
	

	public OutputNeuron(String funtion) {
		// TODO Auto-generated constructor stub
		super(funtion);
		
	}

	public OutputNeuron (BigDecimal outValue, boolean bias, String funtion) {
		super(outValue, bias, funtion);
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getDeltaError() {
		return deltaError;
	}

	public void setDeltaError(BigDecimal deltaError) {
		this.deltaError = deltaError;
	}

	public BigDecimal getDesiredOut() {
		return desiredOut;
	}

	public void setDesiredOut(BigDecimal desiredOut) {
		this.desiredOut = desiredOut;
	}

}
