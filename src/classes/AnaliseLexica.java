package classes;

import java.util.ArrayList;
import java.util.HashMap;

import utils.Delimitadores;
import utils.OperadoresLogicos;
import utils.OperadoresRelacionaisUtils;
import utils.PalavraReservadaUtils;
import utils.SimbolosLexicos;
import utils.TabelaSimbolos;

public class AnaliseLexica {
	
	public void analisys(String text) {
		ArrayList<String> literal = new ArrayList<>();
		boolean aspa_aberta = false;
		
		String token = "";
		char anterior = 0;
		for (Character a : text.toCharArray()) {
		
			if(Character.isLetter(a) || a.equals('.')) {
				
				if(anterior == '>' || anterior == '<' || anterior == '=' || anterior == '!') {
					anterior = 0;
					token = "";
				}
					
				token += String.valueOf(a);
				
				if(PalavraReservadaUtils.reservedWords.containsKey(token)) {
					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.PALAVRAS_RESERVADAS)!=null) {
						Token t = new Token();
						t.setKey(token);
						t.setValue(PalavraReservadaUtils.reservedWords.get(token));
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.PALAVRAS_RESERVADAS).add(t);
					}
					token = "";
				}
			}
			else if(a.equals('>') || a.equals('<') || a.equals('=') || a.equals('!')) {
				
				if(anterior == '>' || anterior == '<' || anterior == '=' || anterior == '!') {
					token = String.valueOf(anterior) + a;	
				}else {
					token = String.valueOf(a);
				}
				
				String label = "";
				switch(token) {
					case (OperadoresRelacionaisUtils.ATRIBUICAO):
						label = OperadoresRelacionaisUtils.ATRIBUICAO_DESCRICAO;
						break;
					case (OperadoresRelacionaisUtils.DIFERENTE):
						label = OperadoresRelacionaisUtils.DIFERENTE_DESCRICAO;
						break;
					case (OperadoresRelacionaisUtils.IGUAL):
						label = OperadoresRelacionaisUtils.IGUAL_DESCRICAO;
						break;
					case (OperadoresRelacionaisUtils.MAIOR):
						label = OperadoresRelacionaisUtils.MAIOR_DESCRICAO;
						break;
					case (OperadoresRelacionaisUtils.MENOR):
						label = OperadoresRelacionaisUtils.MENOR_DESCRICAO;
						break;
					case (OperadoresRelacionaisUtils.MENOR_IGUAL):
						label = OperadoresRelacionaisUtils.MENOR_IGUAL_DESCRICAO;
						break;
					case (OperadoresRelacionaisUtils.MAIOR_IGUAL):
						label = OperadoresRelacionaisUtils.MAIOR_IGUAL_DESCRICAO;
						break;
					case (OperadoresRelacionaisUtils.NEG):
						label = OperadoresRelacionaisUtils.NEG_DESCRICAO;
					
				}
				
				if(OperadoresRelacionaisUtils.operadoresRelacionais.containsKey(label)) {
					anterior = a.charValue();
					
					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_RELACIONAL) != null ) {
						Token t = new Token();
						t.setKey(label);
						t.setValue(OperadoresRelacionaisUtils.operadoresRelacionais.get(label));
						
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_RELACIONAL).add(t);
					}
					token = "";
					
				}
			} 
			else if(a.equals(';') || a.equals('.') || a.equals(':') || a.equals('(') || a.equals(')') || a.equals('{') || a.equals('}') || a.equals('\'') || a.equals('\"') ){
				
				token = String.valueOf(a);
				
				String label = "";
				switch(token) {
					case (Delimitadores.PONTO_VIRGULA):
						label = Delimitadores.PONTO_VIRGULA_DESCRICAO;
						break;
					case (Delimitadores.PONTO):
						label = Delimitadores.PONTO_DESCRICAO;
						break;
					case (Delimitadores.DOIS_PONTOS):
						label = Delimitadores.DOIS_PONTO_DESCRICAO;
						break;
					case (Delimitadores.PARENTESE_ESQ):
						label = Delimitadores.PARENTESE_ESQ_DESCRICAO;
						break;
					case (Delimitadores.PARENTESE_DIR):
						label = Delimitadores.PARENTESE_DIR_DESCRICAO;
						break;
					case (Delimitadores.CHAVE_ESQ):
						label = Delimitadores.CHAVE_ESQ_DESCRICAO;
						break;
					case (Delimitadores.CHAVE_DIR):
						label = Delimitadores.CHAVE_DIR_DESCRICAO;
						break;
					case (Delimitadores.ASPA_DUPLA):
						label = Delimitadores.ASPA_DUPLA_DESCRICAO;
						break;
					case (Delimitadores.ASPA_SIMPLES):
						label = Delimitadores.ASPA_SIMPLES_DESCRICAO;
						
				}
			
				
				if(Delimitadores.delimitadores.containsKey(label)) {
					
					anterior = a.charValue();
					
					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.SIMB_ESPECIAIS) != null ) {
						Token t = new Token();
						t.setKey(label);
						t.setValue(Delimitadores.delimitadores.get(label));
						
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.SIMB_ESPECIAIS).add(t);
					}
					token = "";
					
				}
			} 
			else if(a.equals('&') || a.equals('|')){
				
				if(anterior == '&' || anterior == '|' ) {
					
					token = String.valueOf(anterior) + a;	
				}else {
					token = String.valueOf(a);
					anterior = a.charValue();
				}
				
				String label = "";
				switch(token) {
					case (OperadoresLogicos.AND):
						label = OperadoresLogicos.AND_DESCRICAO;
						break;
					case (OperadoresLogicos.OR):
						label = OperadoresLogicos.OR_DESCRICAO;
				}
			
				
				if(OperadoresLogicos.operadoresLogicos.containsKey(label)) {
					
					anterior = a.charValue();
					
					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_LOGICO) != null ) {
						Token t = new Token();
						t.setKey(label);
						t.setValue(OperadoresLogicos.operadoresLogicos.get(label));
						
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_LOGICO).add(t);
					}
					token = "";
				}
			} 
//			else if(anterior == '\"' || aspa_aberta){
//				
//				aspa_aberta = true;
//				
//				if(!a.equals('\"')) {
//					token += String.valueOf(a);
//					anterior = a.charValue();
//				}else {
//					
//					anterior = a.charValue();
//					
//					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.LITERAL) != null ) {
//						Token t = new Token();
//						t.setKey(token);
//						t.setValue(token);
//						
//						TabelaSimbolos.symbolTable.get(SimbolosLexicos.LITERAL).add(t);
//					}
//					token = "";
//					aspa_aberta = false;
//				}
//				
//			}
			else if(Character.isDigit(a) || a.equals('.') || Character.isDigit(anterior)) { //NUMERAIS
				
				if(!a.equals(' ') || a.equals('f')) {
					token += String.valueOf(a);
					anterior = a.charValue();
				}else {
					
					anterior = a.charValue(); 
					
					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.NUMERAL)!=null) {
						Token t = new Token();
						t.setKey(token);
						t.setValue(token);
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.NUMERAL).add(t);
					}
					token = "";
				}			
			}//NUMERAIS
//			else if(Character.isLetter(a) || a.equals('_')) {// ID
//				
//				if(!a.equals(' ')) {
//					
//					token += String.valueOf(a);
//					anterior = a.charValue();
//				}else {
//					anterior = a.charValue(); 
//					
//					if(Character.isDigit(token.charAt(0))) {
//						System.out.println("Erro Lexixo!!!!");
//						
//						System.exit(1);
//						
//					}else if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.ID)!=null) {
//						Token t = new Token();
//						t.setKey(token);
//						t.setValue(token);
//						TabelaSimbolos.symbolTable.get(SimbolosLexicos.ID).add(t);
//					}
//					token = "";
//				}			
//				
//			}//ID
		}
	}
}
