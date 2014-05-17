package utilities;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

public class BigDecRand {
	
	
	private BigDecimal value;
	private double max; //Rango de los números
	private double min;
	private int precision; 
	
	
	
	

	
	//Si lo trunco no necesito estas funciones, basta con el contenido del main
	
	
	
	public BigDecRand(BigDecimal value, double max, double min, int precision) {
		super();
		this.value = value;
		this.max = max;
		this.min = min;
		this.precision = precision;
	}




	public BigDecRand() {
		super();
	}




	public BigDecRand (double max, double min, int precision) {
		super();
		this.max = max;
		this.min = min;
		this.precision = precision;
		
		BigDecimal maxBig= new BigDecimal (max);
		BigDecimal minBig= new BigDecimal (min);
		
		BigDecimal range = maxBig.subtract(minBig); //rango = max-min
        //aleatorio = min + (rango * random)  
        value = minBig.add(range.multiply(newRandomBigDecimal(new Random(), 4))); 
        value = value.setScale(precision, RoundingMode.HALF_UP);
        //value = min.add(range.multiply(new BigDecimal(Math.random()))); 
	}
	
	
	

	public BigDecimal getValue() {
		return value;
	}




	public void setValue(BigDecimal value) {
		this.value = value;
	}




	public double getMax() {
		return max;
	}




	public void setMax(int max) {
		this.max = max;
	}




	public double getMin() {
		return min;
	}




	public void setMin(int min) {
		this.min = min;
	}




	public int getPrecision() {
		return precision;
	}




	public void setPrecision(int precision) {
		this.precision = precision;
	}




	private static BigDecimal newRandomBigDecimal(Random r, int precision) {
	    BigInteger n = BigInteger.TEN.pow(precision);
	    return new BigDecimal(newRandomBigInteger(n, r), precision);
	}

	private static BigInteger newRandomBigInteger(BigInteger n, Random rnd) {
	    BigInteger r;
	    do {
	        r = new BigInteger(n.bitLength(), rnd);
	    } while (r.compareTo(n) >= 0);

	    return r;
	}
	
	
	
	
    public static void main(String[] args) {
//        String range = args[0];
//        BigDecimal max = new BigDecimal(range + ".0");
//        BigDecimal randFromDouble = new BigDecimal(Math.random());
//        BigDecimal actualRandomDec = randFromDouble.divide(max,BigDecimal.ROUND_DOWN);
//
//       
//        BigInteger actualRandom = actualRandomDec.toBigInteger();
        
        
//        float d = (float) Math.random()*2-1;
//        BigDecimal a = new BigDecimal(d);
//        BigDecimal b = new BigDecimal(Math.random());
//        System.out.print("BigDecimal"+ b);
//        
    
    	
    	//Generar un BigDecimal random entre un rango de valores. 
    	//Deberiamos indicarle tb la precisión, es decir, número de decimale

    	
    	
    	
    	
	    
        BigDecimal max = new BigDecimal("0.009"); 	
        BigDecimal min = new BigDecimal("-0.009"); 	
        BigDecimal range = max.subtract(min); //rango = max-min
        System.out.println("Range: " +range);
        //aleatorio = min + (rango * random)  
        BigDecimal result = min.add(range.multiply(newRandomBigDecimal(new Random(), 4))); 
        //BigDecimal result = min.add(range.multiply(new BigDecimal(Math.random()))); 
        System.out.println("RESULTADO: "+result);     
    	
    	
    	
    	
    	
    	
    	
        
        
        
        
        
    }
}