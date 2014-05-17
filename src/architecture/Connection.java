package architecture;

import java.math.BigDecimal;


public class Connection {

    private Neuron from;     // Connection goes from. . .
    private Neuron to;       // To. . .
    private BigDecimal weight;   // Weight of the connection. . .

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

    // Changing the weight of the connection
    // Former weight plus (increment*learning constant) 
    public void adjustWeight (BigDecimal deltaWeight) {
    	weight.add(deltaWeight);
    }


}
