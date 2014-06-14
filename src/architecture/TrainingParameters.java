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
	
	

}
