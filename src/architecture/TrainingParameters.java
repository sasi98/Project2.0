package architecture;

import java.math.BigDecimal;

import utilities.WeightMatrix;

public class TrainingParameters {
	
	private int iterMax;
	private int currentIt; 
	private BigDecimal ErrorIt; 
	private BigDecimal cuote;
	private double learningCNT;
	private WeightMatrix matrices;
	private boolean momentoB;
	private int status; 

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public TrainingParameters() {
		// TODO Auto-generated constructor stub
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

	public double getLearningCNT() {
		return learningCNT;
	}

	public void setLearningCNT(double learningCNT) {
		this.learningCNT = learningCNT;
	}

	public WeightMatrix getMatrices() {
		return matrices;
	}

	public void setMatrices(WeightMatrix matrices) {
		this.matrices = matrices;
	}

	public boolean isMomentoB() {
		return momentoB;
	}

	public void setMomentoB(boolean momentoB) {
		this.momentoB = momentoB;
	}
	
	

}
