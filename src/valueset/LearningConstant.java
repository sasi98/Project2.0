package valueset;

public class LearningConstant{
	public static int IT_LIMIT_TO_MODIFY = 20; /**Entero q indica el número de iteraciones que deben transcurrir 
	para q se compruebe cómo va el algoritmo en orden de incrementar/decrementar el coeficiente de aprendizaje*/
	
	
	private double value;
	private double previousValue; 
	private double maxCuote;
	private double increment;
	
	
	

	public LearningConstant (double value, double increment) {
		this.value = value; 
		this.increment = increment;
		this.previousValue = value; 
	}

	//añade el incremento al valor actual
	//post: modifica la clase
	public void incValue (){
		this.value = this.value + this.increment;
	}
	
	public void decValue (){
		this.value = this.value - this.increment;
	}


	public double getValue() {
		return value;
	}




	public void setValue(double value) {
		this.value = value;
	}




	public double getPreviousValue() {
		return previousValue;
	}




	public void setPreviousValue(double previousValue) {
		this.previousValue = previousValue;
	}




	public double getMaxCuote() {
		return maxCuote;
	}




	public void setMaxCuote(double maxCuote) {
		this.maxCuote = maxCuote;
	}




	public double getIncrement() {
		return increment;
	}




	public void setIncrement(double increment) {
		this.increment = increment;
	}
	
	
	
	
	
	
	


}
