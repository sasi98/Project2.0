package valueset;

public class LearningConstant{
	public static int IT_LIMIT_TO_MODIFY = 20; /**Entero q indica el número de iteraciones que deben transcurrir 
	para q se compruebe cómo va el algoritmo en orden de incrementar/decrementar el coeficiente de aprendizaje*/
	

	
	private String 				tipologia; 
	private double 				value,
	 							previousValue, 
								maxCuote,
								increment;
	private boolean 			acotado; 
	
	
	
	public LearningConstant (double value, String tipologia, boolean acotado, double maxCuote) {
		this.value = value; 
		this.previousValue = value; 
		this.tipologia = tipologia;
		this.acotado = acotado;
		this.maxCuote = maxCuote;
	}

	//añade el incremento al valor actual
	//post: modifica la clase
	public void incValue (){
		this.value = this.value + this.increment;
	}
	
	public void decValue (){
		this.value = this.value - this.increment;
	}

	
	/**Getters*/
	public double getValue() {
		return value;
	}
	public double getPreviousValue() {
		return previousValue;
	}
	public double getMaxCuote() {
		return maxCuote;
	}
	public double getIncrement() {
		return increment;
	}

	/**Setters*/
	public void setPreviousValue(double previousValue) {
		this.previousValue = previousValue;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public void setMaxCuote(double maxCuote) {
		this.maxCuote = maxCuote;
	}
	public void setIncrement(double increment) {
		this.increment = increment;
	}


}
