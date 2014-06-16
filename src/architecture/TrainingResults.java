package architecture;

import gui.MainWindow;

import java.math.BigDecimal;

import utilities.WeightMatrix;

public class TrainingResults {
	
	private int iteration,
				state;
	private BigDecimal error;  
	private WeightMatrix matrices; 
	
	
	public TrainingResults(int iteration, BigDecimal error, int state, 
			WeightMatrix matrices) {
		super();
		this.iteration = iteration;
		this.error = error;
		this.state = state;
		this.matrices = matrices;
	}
	
	public String getContentWindowBox(){
		String msgSalida = "";
		switch (state) {
			case 0:{
				msgSalida = "El entrenamiento ha alcanzado el número máximo de iteraciones permitidas \n";
			}
			break;
			case 1:{
				msgSalida = "El entrenamiento ha sido cancelado en la iteración "+ iteration+ "\n";
			}
			break;
			case 2:{
				msgSalida = "El error ha alcanzado la cota en la iteración "+ iteration+ "\n";
			}
			break;
	
			default:
				break;
			}
		return (msgSalida + "Error obtenido = " + error);
	}
	
	public String getTitleWindowBox(){
		String msgSalida = "";
		switch (state) {
			case 0:{
				msgSalida = "Entrenamiento finalizado";
			}
			break;
			case 1:{
				msgSalida = "Entrenamiento cancelado";
			}
			break;
			case 2:{
				msgSalida = "Entrenamiento finalizado";
			}
			break;
	
			default:
				break;
			}
		return msgSalida;
	}
		
}
