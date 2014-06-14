package architecture;

import java.math.BigDecimal;

import utilities.WeightMatrix;
import valueset.LearningConstant;

public class TrainingParameters {
	
	private StructureParameters redFeatures;
	private int iterMax;
	private int currentIt; 
	private BigDecimal ErrorIt; 
	private BigDecimal cuote;
	private LearningConstant learning;
	private WeightMatrix matrices;
	private double momentoBvalue;
	private boolean momentoB;
	
	public TrainingParameters() {
		super();
	}

	public int getIterMax() {
		return iterMax;
	}

	public void setIterMax(int iterMax) {
		this.iterMax = iterMax;
	}

	public int getCurrentIt() {
		return currentIt;
	}

	public void setCurrentIt(int currentIt) {
		this.currentIt = currentIt;
	}

	public BigDecimal getErrorIt() {
		return ErrorIt;
	}

	public void setErrorIt(BigDecimal errorIt) {
		ErrorIt = errorIt;
	}

	public BigDecimal getCuote() {
		return cuote;
	}

	public void setCuote(BigDecimal cuote) {
		this.cuote = cuote;
	}

	

	public WeightMatrix getMatrices() {
		return matrices;
	}

	public void setMatrices(WeightMatrix matrices) {
		this.matrices = matrices;
	}

	
	

}
