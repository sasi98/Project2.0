package test;





public class TestingMathFuntion {

	
	
	public TestingMathFuntion() {
		// TODO Auto-generated constructor stub
	}
	
	public static double derivativeFunction(final double x) {
		  return (1.0 - x * x);
		}
	public static void main(String[] args){
		double x = 5;
		double tang = Math.tanh(x);
		System.out.print("Tangencial hiperbólica: "+ tang);
		//System.out.print ("Derivada de la tangencial: "+ Math.);
		derivativeFunction (x);
		System.out.print("derivada "+ derivativeFunction (x));
		
		
	}
	
	
	

}
