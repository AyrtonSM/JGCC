package utils;

import java.util.ArrayList;
import java.util.HashMap;

import classes.Token;
import utils.SimbolosLexicos;

public class TabelaSimbolos {
		
	public static HashMap<String , ArrayList<Token>> symbolTable = new HashMap<String, ArrayList<Token>>();
	public static HashMap<String, String> tiposDeclaradosmain = new HashMap<String, String>();
	public static HashMap<String, HashMap<String, String>> bloco = new HashMap<String, HashMap<String, String>>();
	
	
	public static void createSymbleTable() {
		
		symbolTable.put(SimbolosLexicos.ID, new ArrayList<Token>());
		symbolTable.put(SimbolosLexicos.LITERAL, new ArrayList<Token>());
		symbolTable.put(SimbolosLexicos.NUMERAL, new ArrayList<Token>());
		symbolTable.put(SimbolosLexicos.OP_ARITMETICO, new ArrayList<Token>());
		symbolTable.put(SimbolosLexicos.OP_LOGICO, new ArrayList<Token>());
		symbolTable.put(SimbolosLexicos.OP_RELACIONAL, new ArrayList<Token>());
		symbolTable.put(SimbolosLexicos.SIMB_ESPECIAIS, new ArrayList<Token>());
		symbolTable.put(SimbolosLexicos.PALAVRAS_RESERVADAS, new ArrayList<Token>());
		symbolTable.put(SimbolosLexicos.TYPE, new ArrayList<Token>()); 
		symbolTable.put(SimbolosLexicos.TABELA_GERAL, new ArrayList<Token>());
		symbolTable.put(SimbolosLexicos.ERRO, new ArrayList<Token>());
	}
	
}
