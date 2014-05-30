package architecture;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.apache.log4j.Logger;



public class Neuron {
	
	private BigDecimal outValue;
	private ArrayList<Connection> connections; //tanto las conexiones hacia delante como las que tenga hacia atr�s
	private boolean bias;
	
	private static Logger log = Logger.getLogger(Neuron.class);
	
	
	
	
	public Neuron() {
		super();
		this.outValue = new BigDecimal(0);
		this.connections = new ArrayList<>();
		this.bias = false; 
	}
	
	
	public Neuron (BigDecimal outValue, boolean bias) {
		super();
		log.debug("Creando neurona con valor: " + outValue + "; Bias: "+ bias);
		this.outValue = outValue;
		this.connections = new ArrayList<>();
		this.bias = bias;
	}
	

	public BigDecimal getOutValue() {
		return outValue;
	}
	public void setOutValue(BigDecimal outValue) {
		this.outValue = outValue;
	}
	
	public ArrayList<Connection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}

	public boolean isBias() {
		return bias;
	}
	public void setBias(boolean bias) {
		this.bias = bias;
	}
	
	
    // Function to calculate output of this neuron
    // Output is sum of all inputs*weight of connections
	//Calcula el valor de salida de una neurona, evidentemente esto ser� para las de las capas ocultas o salida,
	//a las entradas les damos directamente el valor 
	//Si la neurona es bias (hay bias tb en la capa oculta) ignora sus conexiones, y su valor de salida es 1
    public void calculateOutValue() {
    	//System.out.println("Looking through " + connections.size() + " connections");
    	log.debug("Nº de conexiones:  " + connections.size()+"\n");
    	if (this.bias){
    		log.debug("Esta neurona es bias, su valor de salida es 1. Ignoramos sus conexiones");
    		outValue =  new BigDecimal(1);
    	}
    	else{ // La neurona no es bias, calculamos su valor
	    	BigDecimal acum = new BigDecimal(0);
	        for (int i = 0; i < connections.size(); i++) {
	        	Connection c = (Connection) connections.get(i);
	        	Neuron from = c.getFrom();
	        	Neuron to = c.getTo();
	        	//Is this connection moving forward to us
	        	// Ignore connections that we send our output to
	        	if (to == this) { //CONEXIONES HACIA DELANTE, "TO" is our neuron
	        		log.debug ("Valor de from neuron: "+ from.getOutValue());
	        		BigDecimal aux = c.getWeight();
	        		aux = aux.multiply(from.getOutValue());  //value neuron * connexion weight
	        		acum = acum.add(aux);
	                     //sum = sum + input_from*weight
	        		// Output is result of sigmoid function o la que sea
	        		//output = f(bias+sum);
	        	} 	
	        }
	        acum = acum.setScale(NetworkManager.PRECISION, RoundingMode.HALF_UP);
	        outValue = acum;
    	}
    	
        log.debug("Valor de salida de la neurona: " + outValue+" See neurons from values on above \n");
    }

    
    public void addConnection(Connection c) {
        connections.add(c);
    }

    
    public void printConnections (){
    	for (Connection c: connections){
    		System.out.print("\n From: " + c.getFrom().getOutValue()+ " to " + c.getTo().getOutValue()+ "\n "
    				+ " Weight connection: " + c.getWeight());
    	}
    	
    	
    }

    // Sigmoid function
    public static float f(float x) {
        return 1.0f / (1.0f + (float) Math.exp(-x));
    }


    
    
    

}
	
	
	


