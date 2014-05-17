package dataManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

import architecture.Network;


public class PatronData {
	
	
	public class Patron {
		
		private BigDecimal dataVector[];
		
		
		public Patron () {
			super();
		}
		
		public Patron (BigDecimal[] dataVector) {
			super();
			this.dataVector = dataVector;
		}

		public BigDecimal[] getDataVector() {
			return dataVector;
		}

		public void setNeuronVector(BigDecimal[] dataVector) {
			this.dataVector = dataVector;
		}
		
		
		//numdays = número de días de rango a seleccionar 
		//cnt = constante por la q multiplicar los datos
		public BigDecimal[] createPatronDataOrden (Iterator<BigDecimal> it, int numDays, BigDecimal cnt){
			int cont = 0, 
				i = 0;
			BigDecimal [] dataVector = new BigDecimal[numDays];
			while(it.hasNext() && cont < numDays){
				BigDecimal b = it.next();
				BigDecimal aux = b.multiply(cnt);
				aux = aux.setScale (Network.PRECISION, RoundingMode.HALF_UP);
				dataVector[i] = aux;
				i++;
				cont++;
			}
			return dataVector;
		}
		
		
			
		}
	
	
	
	

}
