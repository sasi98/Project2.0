package loadFiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.StringTokenizer;

import utilities.Matrix;
import utilities.WeightMatrix;
import architecture.NetworkManager;
import architecture.StructureParameters;

public class StructureParametersLoad extends ReadFile {

	public StructureParametersLoad(String fileName) throws FileNotFoundException {
		super(fileName);
	}
	
	//Return: Devuelve una instancia de Structure Parameters con los datos cargados de fichero. 
	public StructureParameters loadStructureParameters(){
		String name, typeNet, typeData;
		int nE, nS, nO, nP;
		boolean bias = false;
		ArrayList<BigDecimal[]> inputs = new ArrayList<BigDecimal[]>();
		ArrayList<BigDecimal[]> desiredOutputs = new ArrayList<BigDecimal[]>();
		
		String row;
		try {
			row = br.readLine();
			StringTokenizer st = new StringTokenizer (row,";" );
			name = (String) st.nextElement();
			typeNet = (String) st.nextElement();
			typeData = (String) st.nextElement();
			String nE_str = (String) st.nextElement();
			nE = Integer.parseInt(nE_str);
			String nS_str = (String) st.nextElement();
			nS = Integer.parseInt(nS_str);
			String nO_str = (String) st.nextElement();
			nO = Integer.parseInt(nO_str);
			String nP_str = (String) st.nextElement();
			nP = Integer.parseInt(nP_str);
			String bias_str = (String) st.nextElement();
			if (bias_str == "true")
				bias = true;
			
			row = br.readLine();
			int cont = 0;
			while ((row != null) && (cont<nP)){	//Ahora cada fila es un patrón
				BigDecimal[] patron = new BigDecimal[nS];
				int i = 0;
				while(st.hasMoreTokens()){  
					String strValue = (String) st.nextElement();
					//strValue = strValue.replace(",", ".");
					BigDecimal value = new BigDecimal(strValue);
					patron[i] = value;
					i++;
				}
				cont++;
				inputs.add(patron);
				row = br.readLine();
			}
			
			cont = 0;
			while ((row != null) && (cont<nP)){	//Ahora cada fila es un patrón
				BigDecimal[] patron = new BigDecimal[nS];
				int i = 0;
				while(st.hasMoreTokens()){  
					String strValue = (String) st.nextElement();
					//strValue = strValue.replace(",", ".");
					BigDecimal value = new BigDecimal(strValue);
					patron[i] = value;
					i++;
				}
				cont++;
				inputs.add(patron);
				desiredOutputs.add(patron);
				row = br.readLine();
			}			
			return new StructureParameters (name, typeNet, typeData, nP, nS, nO, inputs, desiredOutputs, bias);
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
