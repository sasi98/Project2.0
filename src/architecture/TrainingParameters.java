package architecture;

import java.math.BigDecimal;

import utilities.WeightMatrix;
import valueset.LearningConstant;

public class TrainingParameters {
	
//	private StructureParameters redFeatures;
	private String 				funcion;
	private int 				iterMax;  
	private BigDecimal 			cuoteError;
	private LearningConstant 	learning;
	private WeightMatrix 		matrices;
	private double				momentoBvalue;
	private boolean 			momentoB;
	
	public TrainingParameters() {
		super();
	}

	public TrainingParameters(String funcion, int iterMax,
			BigDecimal cuoteError, LearningConstant learning,
			WeightMatrix matrices, double momentoBvalue, boolean momentoB) {
		super();
		this.funcion = funcion;
		this.iterMax = iterMax;
		this.cuoteError = cuoteError;
		this.learning = learning;
		this.matrices = matrices;
		this.momentoBvalue = momentoBvalue;
		this.momentoB = momentoB;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public int getIterMax() {
		return iterMax;
	}

	public void setIterMax(int iterMax) {
		this.iterMax = iterMax;
	}

	public BigDecimal getCuoteError() {
		return cuoteError;
	}

	public void setCuoteError(BigDecimal cuoteError) {
		this.cuoteError = cuoteError;
	}

	public LearningConstant getLearning() {
		return learning;
	}

	public void setLearning(LearningConstant learning) {
		this.learning = learning;
	}

	public WeightMatrix getMatrices() {
		return matrices;
	}

	public void setMatrices(WeightMatrix matrices) {
		this.matrices = matrices;
	}

	public double getMomentoBvalue() {
		return momentoBvalue;
	}

	public void setMomentoBvalue(double momentoBvalue) {
		this.momentoBvalue = momentoBvalue;
	}

	public boolean isMomentoB() {
		return momentoB;
	}

	public void setMomentoB(boolean momentoB) {
		this.momentoB = momentoB;
	}
	
	

}
