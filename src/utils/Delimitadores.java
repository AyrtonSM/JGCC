package utils;

import java.util.HashMap;

public class Delimitadores {
	// ; | . | : | ( | ) | { | } |“ | ”|‘ | ’
	
	public static final String PONTO_VIRGULA_DESCRICAO = "PONTO E VIRGULA";
	public static final String PONTO_DESCRICAO = "PONTO";
	public static final String DOIS_PONTO_DESCRICAO = "DOIS PONTOS";
	public static final String PARENTESE_ESQ_DESCRICAO = "PARENTESE ESQUERDO";
	public static final String PARENTESE_DIR_DESCRICAO = "PARENTESE DIREITO";
	public static final String CHAVE_ESQ_DESCRICAO = "CHAVE ESQUERDA";
	public static final String CHAVE_DIR_DESCRICAO = "CHAVE DIREITA";
	public static final String ASPA_DUPLA_DESCRICAO = "ASPA DUPLA";
	public static final String ASPA_SIMPLES_DESCRICAO = "ASPA SIMPLES";
	
	public static final String PONTO_VIRGULA = ";";
	public static final String PONTO = ".";
	public static final String DOIS_PONTOS = ":";
	public static final String PARENTESE_ESQ = "(";
	public static final String PARENTESE_DIR = ")";
	public static final String CHAVE_ESQ = "{";
	public static final String CHAVE_DIR = "}";
	public static final String ASPA_DUPLA = "\"";
	public static final String ASPA_SIMPLES = "\'";
	
	public static HashMap<String , String> delimitadores = new HashMap<String, String>();
	
	public static void createHashMapping() {
		delimitadores.put(PONTO_VIRGULA_DESCRICAO, PONTO_VIRGULA);
		delimitadores.put(PONTO_DESCRICAO, PONTO);
		delimitadores.put(DOIS_PONTO_DESCRICAO, DOIS_PONTOS);
		delimitadores.put(PARENTESE_ESQ_DESCRICAO, PARENTESE_ESQ);
		delimitadores.put(PARENTESE_DIR_DESCRICAO, PARENTESE_DIR);
		delimitadores.put(CHAVE_ESQ_DESCRICAO, CHAVE_ESQ);
		delimitadores.put(CHAVE_DIR_DESCRICAO, CHAVE_DIR);
		delimitadores.put(ASPA_DUPLA_DESCRICAO, ASPA_DUPLA);
		delimitadores.put(ASPA_SIMPLES_DESCRICAO, ASPA_SIMPLES);
	}
}
