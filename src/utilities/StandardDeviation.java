package utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import architecture.NetworkManager;

public class StandardDeviation {
	
	private BigDecimal average;
	private BigDecimal variance; 
	private BigDecimal deviation; 
	
	

	public StandardDeviation() {
		super();
	}
	
	
	//Devuelve la media ponderada de un vector 
	public static BigDecimal calculateAverage (BigDecimal[] values){
		BigDecimal acum = new BigDecimal(0);
		for (BigDecimal b: values){
			acum = acum.add(b);
		}
		BigDecimal result = acum.divide(new BigDecimal(values.length));
		result = result.setScale(NetworkManager.PRECISION, RoundingMode.HALF_DOWN);
		return result;
	}
	
	
	//Calcula la varianza de los datos con respecto a la media introducida por parámetros.
	public static BigDecimal calculateVariance (BigDecimal average, BigDecimal[] values){
		BigDecimal acum = new BigDecimal(0);
		for (BigDecimal b: values){
			BigDecimal aux = average.subtract(b);
			acum = acum.add(aux.pow(2));
		}
		BigDecimal result = acum.divide(new BigDecimal(values.length));
		result = result.setScale(NetworkManager.PRECISION, RoundingMode.HALF_DOWN);
		return result;
	}
	
	//Calcula la desviación típica de los datos con respecto a la media introducida por parámetros.
	public static BigDecimal calculateDeviation (BigDecimal average, BigDecimal[] values){
		double aux = calculateVariance(average, values).doubleValue();
		BigDecimal deviation = new BigDecimal(Math.sqrt(aux));
		deviation = deviation.setScale(NetworkManager.PRECISION, RoundingMode.HALF_DOWN);
		return deviation;
		
	}
	
	
}
