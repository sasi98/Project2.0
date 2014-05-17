package architecture;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;



//Layer de neuronas,  corresponde a un vector de neuronas

public class Layer {
	
	private Neuron neuronVector[];
	private int numNeuron;
	//private boolean bias; //Si este patrón contiene una neurona Bias o no.
	
	
	public Layer () {
		super();
	}
	
	public Layer (Neuron[] neuronVector) {
		super();
		this.neuronVector = neuronVector;
		this.numNeuron = neuronVector.length;
		//this.bias = bias;
	}

	public Neuron[] getNeuronVector() {
		return neuronVector;
	}

	public void setNeuronVector(Neuron[] neuronVector) {
		this.neuronVector = neuronVector;
	}
	
	public int getNumNeuron() {
		return numNeuron;
	}

	public void setNumNeuron(int numNeuron) {
		this.numNeuron = numNeuron;
	}

	public void createPatronWithBias (BigDecimal[] values){
		Neuron aux[] = new Neuron[values.length+1];
		aux[0] = new Neuron (new BigDecimal(1), true);
		for (int i = 0; i < values.length; i++){	
			aux[i+1] = new Neuron(values[i], false);
		}
		this.neuronVector = aux;
	}

	public void createPatron (BigDecimal[] values){
		Neuron aux[] = new Neuron[values.length];
		for (int i = 0; i < values.length; i++){	
			aux[i+1] = new Neuron(values[i], false);
		}
		this.neuronVector = aux;
	}

		
	}
	
	
	
	

//	public boolean isBias() {
//		return bias;
//	}
//
//	public void setBias(boolean bias) {
//		this.bias = bias;
//	}
	
	
//	//numdays = número de neuronas 
//	//cnt = constante por la q multiplicar los datos
//	public void createPatron (Iterator<BigDecimal> it, int numDays, BigDecimal cnt){
//		int cont = 0, 
//			i = 0;
//		Neuron [] neuronVector = new Neuron[numDays];
//		while(it.hasNext() && cont < numDays){
//			BigDecimal b = it.next();
//			BigDecimal aux = b.multiply(cnt);
//			aux = aux.setScale (Network.PRECISION, RoundingMode.HALF_UP);
//			Neuron n = new Neuron (aux, false);
//			neuronVector[i] = n;
//			i++;
//			cont++;
//		}
//		this.neuronVector = neuronVector;
//	}
//	
//	
//	public void createPatronWithBiasNeuron (Iterator<BigDecimal> it, int numDays, BigDecimal cnt){
//		int cont = 0, 
//			i = 1;
//		Neuron [] neuronVector = new Neuron[numDays+1];
//		neuronVector[0] = new Neuron(new BigDecimal(1), true); 
//		while(it.hasNext() && cont < numDays){
//			BigDecimal b = it.next();
//			BigDecimal aux = b.multiply(cnt);
//			aux = aux.setScale (Network.PRECISION, RoundingMode.HALF_UP);
//			Neuron n = new Neuron (aux, false);
//			neuronVector[i] = n;
//			i++;
//			cont++;
//		}
//		this.neuronVector = neuronVector;
//	}
//	
//
//	public void printPatron (){
//		System.out.print ("Nº de neuronas:  " + this.getNeuronVector().length + "\n"
//				+ "Valor de las neuronas: ");
//		for (Neuron d: this.getNeuronVector()){
//			System.out.print(d.getValue()+ " ");
//		}
//		System.out.print("\n");
//	}
	
	
	
	
