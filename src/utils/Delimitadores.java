package utils;

import java.util.HashMap;

public class Delimitadores {
	// ; | . | : | ( | ) | { | } |“ | ”|‘ | ’ 
	
	public static final String PONTO_VIRGULA_DESCRICAO = "PONTO E VIRGULA";
	public static final String VIRGULA_DESCRICAO = "VIRGULA";
	public static final String PONTO_DESCRICAO = "PONTO";
	public static final String DOIS_PONTO_DESCRICAO = "DOIS PONTOS";
	public static final String PARENTESE_ESQ_DESCRICAO = "PARENTESE ESQUERDO";
	public static final String PARENTESE_DIR_DESCRICAO = "PARENTESE DIREITO";
	public static final String CHAVE_ESQ_DESCRICAO = "CHAVE ESQUERDA";
	public static final String CHAVE_DIR_DESCRICAO = "CHAVE DIREITA";
	public static final String ASPA_DUPLA_DESCRICAO = "ASPA DUPLA";
	public static final String ASPA_SIMPLES_DESCRICAO = "ASPA SIMPLES";
	public static final String HASH_DESCRICAO = "HASH";
	public static final String COMENTARIO_INI_DESCRICAO = "COMENTARIO INICIAL";
	public static final String COMENTARIO_FINAL_DESCRICAO = "COMENTARIO FINAL";
	
	public static final String VIRGULA = ",";
	public static final String PONTO_VIRGULA = ";";
	public static final String PONTO = ".";
	public static final String DOIS_PONTOS = ":";
	public static final String PARENTESE_ESQ = "(";
	public static final String PARENTESE_DIR = ")";
	public static final String CHAVE_ESQ = "{";
	public static final String CHAVE_DIR = "}";
	public static final String ASPA_DUPLA = "\"";
	public static final String ASPA_SIMPLES = "\'";
	public static final String HASH = "#";
	public static final String COMENTARIO_INI = "/*";
	public static final String COMENTARIO_FINAL = "*/";
	
	
	public static HashMap<String , String> delimitadores = new HashMap<String, String>();
	
	public static void createHashMapping() {
		delimitadores.put(PONTO_VIRGULA, PONTO_VIRGULA_DESCRICAO);
		delimitadores.put(VIRGULA, VIRGULA_DESCRICAO);
		delimitadores.put(PONTO, PONTO_DESCRICAO);
		delimitadores.put(DOIS_PONTOS, DOIS_PONTO_DESCRICAO);
		delimitadores.put(PARENTESE_ESQ, PARENTESE_ESQ_DESCRICAO);
		delimitadores.put(PARENTESE_DIR, PARENTESE_DIR_DESCRICAO);
		delimitadores.put(CHAVE_ESQ, CHAVE_ESQ_DESCRICAO);
		delimitadores.put(CHAVE_DIR, CHAVE_DIR_DESCRICAO);
		delimitadores.put(ASPA_DUPLA, ASPA_DUPLA_DESCRICAO);
		delimitadores.put(ASPA_SIMPLES, ASPA_SIMPLES_DESCRICAO);
		delimitadores.put(HASH, HASH_DESCRICAO);
		delimitadores.put(COMENTARIO_INI, COMENTARIO_INI_DESCRICAO);
		delimitadores.put(COMENTARIO_FINAL, COMENTARIO_FINAL_DESCRICAO);
	}
}
