package utils;

import java.util.HashMap;

public class AritmeticosUtils {
	
	public static final String SUM_DESCRIPTION = "OPERADOR DE SOMA";
	public static final String SUB_DESCRIPTION = "OPERADOR DE SUBTRACAO";
	public static final String MULT_DESCRIPTION = "OPERADOR DE MULTIPLICACAO / PONTEIRO";
	public static final String DIV_DESCRIPTION = "OPERADOR DE DIVISAO";
	public static final String INC_DESCRIPTION = "OPERADOR DE INCREMENTO";
	public static final String DEC_DESCRIPTION = "OPERADOR DE DECREMENTO";
	
	public static final String SUM = "+";
	public static final String SUB = "-";
	public static final String MULT = "*"; 
	public static final String DIV = "/";
	public static final String INC = "++";
	public static final String DEC = "--";
	
	
	
	public static final HashMap<String, String> aritmeticosMapping = new HashMap<String, String>();
	
	public static void createHashMapping() {
		
		aritmeticosMapping.put(SUM, SUM_DESCRIPTION);
		aritmeticosMapping.put(SUB, SUB_DESCRIPTION);
		aritmeticosMapping.put(MULT, MULT_DESCRIPTION);
		aritmeticosMapping.put(DIV, DIV_DESCRIPTION);
		aritmeticosMapping.put(INC, INC_DESCRIPTION);
		aritmeticosMapping.put(DEC, DEC_DESCRIPTION);
		
	}
	
	
	

}
