/**
 * @author Esther GC
 */
package utilities;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Clase Matrix
 * Clase que define objetos Matrix simples para realizar operaciones básicas sobre matrices y vectores. 
 * 
*/
public class Matrix {

	private int row;
	private int column;
	private BigDecimal mValues[][];
	private static final Logger log = Logger.getLogger(Matrix.class);


			/** CONSTRUCTORES */
	
/**
 * Contructor Parametrizado Matrix
 * @param m Array bidimensional de BigDecimal
*/
public Matrix (BigDecimal m[][]){
	//PropertyConfigurator.configure("files\\log4j.properties");
	this.mValues = m;
	this.row = m.length;
	this.column = m[0].length;
	log.info("Creando matriz de dimensiones: Filas: "+ this.row+ "Columnas: " + this.column);
}


/**
 * Constructor Matrix
 * @param m Array unidimensional de BigDecimal (1 Fila)
*/
public Matrix(BigDecimal m[]){
	this.mValues = new BigDecimal[1][m.length];
	for (int i = 0; i < m.length ; i++){
		this.mValues[0][i] = m[i];
		this.row = 1;
		this.column =m.length;
	}
}	

									/** MÉTODOS */

/**Getters*/

public int getRow() {
	return row;
}


public int getColumn() {
	return column;
}


public BigDecimal[][] getmValues() {
	return mValues;
}


/**Setters*/

public void setRow(int row) {
	this.row = row;
}


public void setColumn(int column) {
	this.column = column;
}


public void setmValues(BigDecimal[][] mValues) {
	this.mValues = mValues;
}


/**
 * Añadir valor en posicion [r][c]
 * @param r índice de fila
 * @param c índice de columna
 * @param value Valor a añadir a la matriz
*/
public void setValuePos (int r,int c, BigDecimal value){
	this.mValues[r][c] = value;
}


/**
 * Obtener valor en posición[f][c]
 * @param f índice fila
 * @param c índice columna
 * @return BigDecimal valor
 */
public BigDecimal getValuePos (int r,int c){
	return this.mValues[r][c];
}


/**
 * Transponer matriz. Transpone la matriz entrada por parámetro
 * @param m
 * @return m' 
*/
public static Matrix transponer (Matrix m){
	BigDecimal retorno[][];
	retorno = new BigDecimal [m.getColumn()][m.getRow()];
	for (int i = 0; i < m.getRow(); i++){
        for (int j = 0; j < m.getColumn(); j++){
            retorno[j][i] = m.getValuePos(i, j);
        }
	}
	Matrix ret = new Matrix(retorno);
	return ret;
}


/**
 * Sumar matrices
 * @param mA sumando A
 * @param mB sumando B
 * @return Matriz (mA + mB)
*/
public static Matrix addition (Matrix mA, Matrix mB){
	if ( (mA.getColumn() != mB.getColumn()) || (mA.getRow() != mB.getRow()) ){
		log.error("Las matrices no pueden ser sumadas. Tienen diferentes dimensiones");
	}
	else{
		Matrix aux = new Matrix (new BigDecimal [mB.getRow()][mB.getColumn()]);
		for (int i = 0; i < mA.getRow(); i++){
			for (int j = 0; j < mA.getColumn(); j++){
				BigDecimal b = mA.getValuePos(i, j);
				b = b.add(mB.getValuePos(i, j));
				aux.setValuePos(i, j, b);
			}
		}
		return aux;
	}
	return null;
}


/**
 * Restar matrices
 * @param mA Minuendo
 * @param mB Sustraendo
 * @return Matriz diferencia = mA - mB
 */
public static Matrix subtraction (Matrix mA, Matrix mB){
	if ( (mA.getColumn() != mB.getColumn()) || (mA.getRow() != mB.getRow()) ){
		log.error("Las matrices no pueden ser restadas. Tienen diferentes dimensiones");
	}
	else{
		Matrix aux = new Matrix (new BigDecimal [mB.getRow()][mB.getColumn()]);
		for (int i = 0; i < mA.getRow(); i++){
			for (int j = 0; j < mA.getColumn(); j++){
				BigDecimal b = mA.getValuePos(i, j);
				b = b.subtract(mB.getValuePos(i, j));
				aux.setValuePos(i, j, b);
			}
		}
		return aux;
	}
	return null;
}


/**
 * Multiplicar matrices
 * @param mA factor A
 * @param mB factor B
 * @return mA x mB. La matriz de retorno tiene dimensiones [nFilas(A)]x[nCol(B)]
*/
public static Matrix product (Matrix mA, Matrix mB){
	
	if (mA.getColumn() != mB.getRow()){
		log.error("El número de columnas de mA es distinto al número de filas de mB, por lo que las matrices no se pueden multiplicar");
		log.error("Columnas de mA: "+ mA.getColumn()+ " Filas de mB: "+ mB.getRow());
	}
	else{
		Matrix aux = new Matrix(new BigDecimal [mA.getRow()][mB.getColumn()]);
	
		for (int i = 0; i < mA.getRow(); i++){
			for (int j=0; j < mB.getColumn(); j++){
				BigDecimal acum = new BigDecimal (0);
				for (int k = 0; k < mA.getColumn(); k++){
                	BigDecimal auxBD = mA.getValuePos(i, k);
                	auxBD = auxBD.multiply(mB.getValuePos(k, j)); 
                	acum = acum.add(auxBD);
                }
                aux.setValuePos(i, j, acum);
			}
		}
		return aux;
	}
	return null;
}


/**
 * Obtiene la matrix correspondiente al nº de columna pasado por parámetro 
 * @param index índice de la columna a obtener
 * @return Matrix Columna con todos los valores de la columna indicada
*/
public Matrix getColumn (int index){
	BigDecimal aux[][]= new BigDecimal[this.getRow()][1];
    for (int i = 0 ; i < this.getRow(); i++){
    	aux[i][0] = this.getValuePos(i, index);
    }
    return new Matrix(aux);
}


/**
 * Obtiene el vector correspondiente al nº de columna pasado por parámetro 
 * @param index índice de la columna a obtener
 * @return BigDecimal[] Columna con todos los valores de la columna indicada
*/
public BigDecimal[] getColumVector (int index){
	BigDecimal aux[] = new BigDecimal[this.getRow()];
    for (int i = 0 ; i < this.getRow(); i++){
    	aux[i] = this.getValuePos(i, index);
    }
    return aux;
}


/**
 * Obtiene el vector correspondiente al nº de fila pasado por parámetro 
 * @param index índice de la fila a obtener
 * @return BigDecimal[] Fila con todos los valores de la columna indicada
*/
public BigDecimal[] getRowVector (int index){
    BigDecimal aux[] = new BigDecimal[this.getRow()];
    for (int j = 0 ; j < this.getRow(); j++){
    	aux[j] = this.getValuePos(index, j);
        }
     return aux;	
}

/**
 * Trunca todos los valores de la matrix de la clase
 * @param precision Nº máximo de decimales que puede tener nuestros valores
 * @post La matriz de la clase está ahora truncada y redondeada a la alza
*/
public void truncarMatrixUP (int precision)
{
	for (int i = 0; i < row ; i++){
		for (int j= 0; j < column; j++){
			BigDecimal auxBD = this.getValuePos(i, j);
			auxBD = auxBD.setScale(precision,RoundingMode.HALF_UP);
			this.mValues[i][j] = auxBD;
		}
	}
}



public Matrix square ()
{
	Matrix aux = new Matrix(new BigDecimal[this.row][this.column] );
	for (int i = 0; i < row ; i++){
		for (int j= 0; j < column; j++){
			BigDecimal auxBD = this.getValuePos(i, j);
			auxBD = auxBD.pow(2);
			aux.mValues[i][j] = auxBD;
		}
	}
	return aux;
}


public BigDecimal getSumatorioMatrix(){
	BigDecimal acum = new BigDecimal(0); 
	for (int i = 0; i < row ; i++){
		for (int j= 0; j < column; j++){
			acum = acum.add(this.getValuePos(i, j));
		}
	}
	return acum;
}


/**
 * Multiplicar Matriz por escalar
 * @param esc factor escalar
 * @return Matriz escalada
*/
public Matrix multEscalar (double esc){
	BigDecimal[][] aux = new BigDecimal [row][column];
	for (int i = 0; i < row ; i++){
		for (int j= 0; j < column; j++){
			BigDecimal auxBD = this.getValuePos(i, j);
			auxBD = auxBD.multiply(new BigDecimal(esc));
			aux[i][j] = auxBD;
		}
	}
	return new Matrix (aux);
}


/**
 * Imprimir Matriz, 
*/
public void printMatrix ()
{
	log.debug ("Imprimiendo matriz de dimensiones: Filas:" + row +"Columnas: "+ column );
	//System.out.println("Dimensiones de la matriz: Filas:" + row +"Columnas: "+ column );
	 for (int i = 0; i < row; i++){
		// System.out.print("(");
		 for (int j= 0; j < column; j++){
			// System.out.print(this.getValuePos(i, j) +  " ");
			 log.trace(this.getValuePos(i, j) +  " ");
		 }
		 //System.out.print(") \n");
		 log.trace(") \n");
	}
}


//Crea una matriz de dimensiones d, con valores aleatorios situados entre min y más. 
//precisión: nº de decimales que tendrá cada dato, los datos aletorios generados serán truncados con este valor
public static Matrix createRandomMatrix (double min, double max, Dimension d, int precision)
{
	log.info("Creating WeightMatrix Random Values");
	BigDecimal[][] aux = new BigDecimal[d.width][d.height];
	Matrix m = new Matrix(aux);
	for (int i = 0; i < d.width; i++){
		for (int j = 0; j < d.height; j++){
			BigDecRand t = new BigDecRand(max, min, precision);
			BigDecimal a = t.getValue();
			m.setValuePos(i, j, a);
		}
	}
	return m;
}



}
