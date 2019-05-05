package utils;

import java.util.HashMap;

public class OperadoresLogicos {
	
	public static final String AND_DESCRICAO = "AND";
	public static final String OR_DESCRICAO = "OR";
	
	public static final String AND = "&&";
	public static final String OR = "||";
	
	public static HashMap<String , String> operadoresLogicos = new HashMap<String, String>();
	
	public static void createHashMapping() {
		operadoresLogicos.put(AND, AND_DESCRICAO);
		operadoresLogicos.put(OR, OR_DESCRICAO); 
	}
}
