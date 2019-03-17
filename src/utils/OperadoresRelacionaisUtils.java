package utils;

import java.util.HashMap;

public class OperadoresRelacionaisUtils {
	
	public static final String MAIOR_DESCRICAO = "MAIOR";
	public  static final String MENOR_DESCRICAO = "MENOR";
	public  static final String ATRIBUICAO_DESCRICAO = "ATRIBUIÇÃO";
	public  static final String NEG_DESCRICAO = "NEGAÇÃO";
	public  static final String MENOR_IGUAL_DESCRICAO = "MENOR IGUAL";
	public static final String MAIOR_IGUAL_DESCRICAO = "MAIOR IGUAL";
	public  static final String DIFERENTE_DESCRICAO = "DIFERENTE";
	public  static final String IGUAL_DESCRICAO = "IGUAL";
	
	public  static final String MAIOR = ">";
	public  static final String MENOR = "<";
	public  static final String ATRIBUICAO = "=";
	public  static final String NEG = "!";
	public  static final String MENOR_IGUAL = "<=";
	public  static final String MAIOR_IGUAL = ">=";
	public  static final String DIFERENTE = "!=";
	public  static final String IGUAL = "==";
	
	public static HashMap<String, String> operadoresRelacionais = new HashMap<String, String>();
	public static void createHashMapping() {
		operadoresRelacionais.put(MAIOR_DESCRICAO,MAIOR);
		operadoresRelacionais.put(MENOR_DESCRICAO,MENOR);
		operadoresRelacionais.put(ATRIBUICAO_DESCRICAO,ATRIBUICAO);
		operadoresRelacionais.put(IGUAL_DESCRICAO,IGUAL);
		operadoresRelacionais.put(NEG_DESCRICAO,NEG);
		operadoresRelacionais.put(MAIOR_IGUAL_DESCRICAO,MAIOR_IGUAL);
		operadoresRelacionais.put(MENOR_IGUAL_DESCRICAO,MENOR_IGUAL);
		operadoresRelacionais.put(DIFERENTE_DESCRICAO,DIFERENTE);
	}
	
}
