package architecture;

import java.math.BigDecimal;

public class OutputNeuron extends Neuron {
	
	BigDecimal deltaError; 
	

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

}
