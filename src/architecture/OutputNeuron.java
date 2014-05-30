package architecture;

import java.math.BigDecimal;

public class OutputNeuron extends Neuron {
	
	BigDecimal deltaError; //Representa el delta de error de esta neurona de salida. 
						   // Salida deseada - salida obtenida
	BigDecimal desiredOut;
	

	public OutputNeuron() {
		// TODO Auto-generated constructor stub
	}

	public OutputNeuron (BigDecimal outValue, boolean bias) {
		super(outValue, bias);
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
