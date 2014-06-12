package utilities;



//Clase que representa las matrices de pesos de la red (W y V)
public class WeightMatrix {
	
	private Matrix W; //Matrix de pesos entre la capa de entrada y la capa oculta
	private Matrix V; //Matrix de pesos entre la capa oculta y la capa de salida 
	

	
	public WeightMatrix(Matrix w, Matrix v) {
		super();
		W = w;
		V = v;
	}
	
	public WeightMatrix(Matrix w) {
		super();
		W = w;
	}
	
	public Matrix getW() {
		return W;
	}
	public void setW(Matrix w) {
		W = w;
	}
	public Matrix getV() {
		return V;
	}
	public void setV(Matrix v) {
		V = v;
	}
	


}
