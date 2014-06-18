/**
 * @author Esther GC
 */
package utilities;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Clase Matrix
 * Clase que define objetos Matrix simples para realizar operaciones bï¿½sicas sobre matrices y vectores. 
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

									/** Mï¿½TODOS */

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
 * Aï¿½adir valor en posicion [r][c]
 * @param r ï¿½ndice de fila
 * @param c ï¿½ndice de columna
 * @param value Valor a aï¿½adir a la matriz
*/
public void setValuePos (int r,int c, BigDecimal value){
	this.mValues[r][c] = value;
}


/**
 * Obtener valor en posiciï¿½n[f][c]
 * @param f ï¿½ndice fila
 * @param c ï¿½ndice columna
 * @return BigDecimal valor
 */
public BigDecimal getValuePos (int r,int c){
	return this.mValues[r][c];
}


/**
 * Transponer matriz. Transpone la matriz entrada por parï¿½metro
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
		log.error("El nï¿½mero de columnas de mA es distinto al nï¿½mero de filas de mB, por lo que las matrices no se pueden multiplicar");
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
 * Obtiene la matrix correspondiente al nï¿½ de columna pasado por parï¿½metro 
 * @param index ï¿½ndice de la columna a obtener
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
 * Obtiene el vector correspondiente al nï¿½ de columna pasado por parï¿½metro 
 * @param index ï¿½ndice de la columna a obtener
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
 * Obtiene el vector correspondiente al nï¿½ de fila pasado por parï¿½metro 
 * @param index ï¿½ndice de la fila a obtener
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
 * @param precision Nï¿½ mï¿½ximo de decimales que puede tener nuestros valores
 * @post La matriz de la clase estï¿½ ahora truncada y redondeada a la alza
*/
public void truncarMatrixUP (int precision)
{
	for (int i = 0; i < row ; i++){
		for (int j= 0; j < column; j++){
			BigDecimal auxBD = this.getValuePos(i, j);
			auxBD = auxBD.setScale(precision,RoundingMode.HALF_DOWN);
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
		//System.out.print("(");
		 for (int j= 0; j < column; j++){
			//System.out.print(this.getValuePos(i, j) +  " ");
			 log.trace(this.getValuePos(i, j) +  " ");
		 }
		 //System.out.print(") \n");
		 log.trace("// \n");
	}
}

public void printMatrixConsole ()
{
	System.out.println("Dimensiones de la matriz: Filas:" + row +"Columnas: "+ column );
	 for (int i = 0; i < row; i++){
		System.out.print("(");
		 for (int j= 0; j < column; j++){
			System.out.print(this.getValuePos(i, j) +  " ");
		 }
		 System.out.print(") \n");
	}
}

//Crea una matriz de dimensiones d, con valores aleatorios situados entre min y mï¿½s. 
//precisiï¿½n: nï¿½ de decimales que tendrï¿½ cada dato, los datos aletorios generados serï¿½n truncados con este valor
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

//Genera una matrix (filas = nï¿½ de arrays, columnas = tamaï¿½o de los arrays) a partir 
//del array de arrays introducido por parï¿½metros.
public static Matrix createMatrixFromArrays (ArrayList<ArrayList<BigDecimal>> arrayMatrix){
	int numRow = arrayMatrix.size();
	int numColum = arrayMatrix.get(0).size();
	Matrix newM = new Matrix (new BigDecimal[numRow][numColum]);
	for (int i = 0; i<newM.getRow(); i++){
		ArrayList<BigDecimal> aux = arrayMatrix.get(i);
		for (int j = 0; j < newM.getColumn(); j++){
			newM.setValuePos(i, j,aux.get(j));
		}
	}
	return newM;
	
}

//Genera una matrix (filas = nº de vectores , columnas = tamaño de los arrays) a partir 
//del vector de BigDecimal introducido por parámetrs
public static Matrix createMatrixFromArrayOfVectors (ArrayList<BigDecimal[]> arrayMatrix){
	int numRow = arrayMatrix.size();
	int numColum = arrayMatrix.get(0).length;
	Matrix newM = new Matrix (new BigDecimal[numRow][numColum]);
	for (int i = 0; i<newM.getRow(); i++){
		BigDecimal[] aux = arrayMatrix.get(i);
		for (int j = 0; j < newM.getColumn(); j++){
			newM.setValuePos(i, j,aux[j]);
		}
	}
	return newM;
	
}


//Pre: la matriz tiene que ser cuadrada
//Return el máximo de la diagonal 
public BigDecimal getMaxDiagonal (){
	ArrayList<BigDecimal> diagonal = new ArrayList<>();
	if (this.column != this.row){
		log.error("No se puede obtener el máximo de la diagonal, pues las matrices no son cuadradas."+
				"Dimensiones: Filas: "+this.row+" Columnas: " + this.column);
	return null;
	}
	else{
		//Get the diagonal
		for (int i = 0; i< this.row; i++){
			for (int j = 0; j<this.column; j++){
				if (i == j)
					diagonal.add(this.getValuePos(i, j));
			}
		}
		//Get the max of the diagonal
		BigDecimal max = diagonal.get(0);
		for (BigDecimal a: diagonal){
			if(a.compareTo(max) == 1)
				max = a;
		}
		return max;
	}
}



	
	

}
