package utils;

import java.util.HashMap;

public class AritmeticosUtils {
	
	public static final String SUM_DESCRIPTION = "OPERADOR DE SOMA";
	public static final String SUB_DESCRIPTION = "OPERADOR DE SUBTRACAO";
	public static final String MULT_DESCRIPTION = "OPERADOR DE MULTIPLICACAO / PONTEIRO";
	public static final String DIV_DESCRIPTION = "OPERADOR DE DIVISAO";
	
	public static final String SUM = "+";
	public static final String SUB = "-";
	public static final String MULT = "*";
	public static final String DIV = "/";
	
	
	public static final HashMap<String, String> aritmeticosMapping = new HashMap<String, String>();
	
	public static void createHashMapping() {
		
		aritmeticosMapping.put(SUM_DESCRIPTION, SUM);
		aritmeticosMapping.put(SUB_DESCRIPTION, SUB);
		aritmeticosMapping.put(MULT_DESCRIPTION, MULT);
		aritmeticosMapping.put(DIV_DESCRIPTION, DIV);
		
	}
	
	
	

}
