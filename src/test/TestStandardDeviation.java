package test;

import java.math.BigDecimal;

import utilities.StandardDeviation;

public class TestStandardDeviation {

	public void testingStandarDeviation(){
		System.out.print("Usaremos el mismo tipo de datos que los utilizados en la red. \n");
		BigDecimal b1  = new BigDecimal (-2.0720950000);
		BigDecimal b2  = new BigDecimal (-1.6703820000);
		BigDecimal b3  = new BigDecimal (-1.2157090000);
		BigDecimal b4  = new BigDecimal (0.8359022000);
		BigDecimal b5  = new BigDecimal (3.1527010000);
		BigDecimal b6  = new BigDecimal (-0.0539163500);
		BigDecimal b7  = new BigDecimal (2.4934970000);
		BigDecimal b8  = new BigDecimal (-1.7527320000);
		
		
		BigDecimal[] values;
		values = new BigDecimal[8];
		values[0] = b1;
		values[1] = b2;
		values[2] = b3;
		values[3] = b4;
		values[4] = b5;
		values[5] = b6;
		values[6] = b7;
		values[7] = b8;
		
		System.out.print ("Mostramos valores: \n");
		for (BigDecimal b: values){
			System.out.print (b+ "\n");
		}
		BigDecimal media = StandardDeviation.calculateAverage(values);
		BigDecimal varianza = StandardDeviation.calculateVariance(media, values);
		BigDecimal desviacion = StandardDeviation.calculateDeviation(media, values);
		System.out.print("Media: " +media+ "; Varianza: "+varianza+ " ; Desviación típica: "+ desviacion);
	
	}
	
	

	public static void main(String[] args) {
		TestStandardDeviation test = new TestStandardDeviation();
		test.testingStandarDeviation();

	}

}
