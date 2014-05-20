package architecture;

import java.math.BigDecimal;

import org.apache.log4j.Logger;


public class Connection {

    private Neuron from;     // Connection goes from. . .
    private Neuron to;       // To. . .
    private BigDecimal weight;   // Weight of the connection. . .
    
    private static Logger log = Logger.getLogger(Connection.class);

    // Constructor  builds a connection with a random weight
    public Connection (Neuron a, Neuron b) {
        from = a;
        to = b;
        weight = new BigDecimal (Math.random()*2-1);
    }
    
    // In case I want to set the weights manually, using this for testing
    public Connection(Neuron a, Neuron b, BigDecimal w) {
        from = a;
        to = b;
        weight = w;
    }

    public Neuron getFrom() {
        return from;
    }
    
    public Neuron getTo() {
        return to;
    }  
    
    public BigDecimal getWeight() {
        return weight;
    }
    

    public void setWeight(BigDecimal weight) {
    	System.out.print("Updating weight: "+ weight + "\n");
		this.weight = weight;
	}

	// Changing the weight of the connection
    // Former weight plus (increment*learning constant) 
    public void adjustWeight (BigDecimal deltaWeight) {
    	weight.add(deltaWeight);
    }
    
  


}
