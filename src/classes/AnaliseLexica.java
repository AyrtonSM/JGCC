package classes;


import utils.AritmeticosUtils;

import java.util.ArrayList;
import java.util.HashMap;
import utils.Delimitadores;
import utils.OperadoresLogicos;
import utils.OperadoresRelacionaisUtils;
import utils.PalavraReservadaUtils;
import utils.SimbolosLexicos;
import utils.TabelaSimbolos;

public class AnaliseLexica {
	
	public void analisys(String text,int lineCount) throws Exception {
		ArrayList<String> literal = new ArrayList<>();
		boolean aspa_aberta = false;
		
		String token = "";
		char anterior = 0;
		String classe_token_anterior = "";
		for (Character a : text.toCharArray()) {
		
			if(Character.isLetter(a) || a.equals('.')) {
				
				if(anterior == '>' || anterior == '<' || anterior == '=' || anterior == '!') {
					anterior = 0;
					token = "";
				}
					
				token += String.valueOf(a);
				
			
				if(PalavraReservadaUtils.reservedTypeWords.containsKey(token)) {
					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.TYPE)!=null) {
						Token t = new Token();
						t.setKey(token);
						t.setValue(PalavraReservadaUtils.reservedTypeWords.get(token));
						t.setLinha(lineCount);
						t.setTipo(SimbolosLexicos.TYPE);
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.TYPE).add(t);
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
						classe_token_anterior = SimbolosLexicos.TYPE;
//						System.out.println(token);
					}
					token = "";
				}
				else if(PalavraReservadaUtils.reservedWords.containsKey(token)) {
					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.PALAVRAS_RESERVADAS)!=null) {
						Token t = new Token();
						t.setKey(token);
						t.setValue(PalavraReservadaUtils.reservedWords.get(token));
						t.setLinha(lineCount);
						t.setTipo(SimbolosLexicos.PALAVRAS_RESERVADAS);
						
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.PALAVRAS_RESERVADAS).add(t);
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
					}
					token = "";
				}
				
				
			}
			else if(a.equals('>') || a.equals('<') || a.equals('=') || a.equals('!')) {
				classe_token_anterior = "";
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
						t.setLinha(lineCount);
						t.setTipo(SimbolosLexicos.OP_RELACIONAL);
						
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_RELACIONAL).add(t);
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
					}
					token = "";
					
				}
			} // END ELSE IF OP_RELACIONAL
			else if(a.equals('+') || a.equals('-') || a.equals('*') || a.equals('/')) {
				
 				token = String.valueOf(a);
				
				String label = "";
				switch (String.valueOf(a)) {
				
					case (AritmeticosUtils.SUM):
						label = AritmeticosUtils.SUM_DESCRIPTION;
						break;
					case (AritmeticosUtils.SUB):
						label = AritmeticosUtils.SUB_DESCRIPTION;
						break;
					case (AritmeticosUtils.MULT):
						label = AritmeticosUtils.MULT_DESCRIPTION;
						break;
					case (AritmeticosUtils.DIV):
						label = AritmeticosUtils.DIV_DESCRIPTION;
						break;
						
					default:
						break;
				}
				
				
				if(AritmeticosUtils.aritmeticosMapping.containsKey(label)) {
					
					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_ARITMETICO) != null ) {
						Token t = new Token();
						t.setKey(label);
						t.setValue(AritmeticosUtils.aritmeticosMapping.get(label));
						t.setLinha(lineCount);
						t.setTipo(SimbolosLexicos.OP_ARITMETICO);
						
						
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_ARITMETICO).add(t);
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
					}
				}
			
			} else if(a.equals('#') || a.equals(',') || a.equals(';') || a.equals('.') || a.equals(':') || a.equals('(') || a.equals(')') || a.equals('{') || a.equals('}') || a.equals('\'') || a.equals('\"') ){
				
	
				if (a.equals(';') || a.equals(')')) {
					classe_token_anterior = "";
					
				}
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
					case (Delimitadores.VIRGULA):
						label = Delimitadores.VIRGULA_DESCRICAO;
						break;
					case (Delimitadores.HASH):
						label = Delimitadores.HASH_DESCRICAO;
						break;
				}
			
				
				if(Delimitadores.delimitadores.containsKey(label)) {
					
					anterior = a.charValue();
					
					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.SIMB_ESPECIAIS) != null ) {
						Token t = new Token();
						t.setKey(label);
						t.setValue(Delimitadores.delimitadores.get(label));
						t.setLinha(lineCount);
						t.setTipo(SimbolosLexicos.SIMB_ESPECIAIS);
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.SIMB_ESPECIAIS).add(t);
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
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
						t.setLinha(lineCount);
						t.setTipo(SimbolosLexicos.OP_LOGICO);
					
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_LOGICO).add(t);
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
					}
					token = "";
				}
			} 
			
			else if(Character.isDigit(a) || a.equals('.') || Character.isDigit(anterior)) { //NUMERAIS
				if(classe_token_anterior.equals(SimbolosLexicos.TYPE)) {
					TabelaSimbolos.symbolTable.get(SimbolosLexicos.ERRO).add(new Token("Erro Lexico : "+a+" nao pode iniciar um ID",String.valueOf(lineCount)));
					for (Token t : TabelaSimbolos.symbolTable.get(SimbolosLexicos.ERRO)) {
						throw new Exception( "LINHA : " + t.getValue() + ":::  [DESCRICAO] :" + t.getKey() );
//						System.err.println();
//						System.exit(1);
					}
					
				}
				if(!a.equals(' ') || a.equals('f')) {
					token += String.valueOf(a);
					anterior = a.charValue();
				}else {
					
					anterior = a.charValue(); 
					
					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.NUMERAL)!=null) {
						Token t = new Token();
						t.setKey(token);
						t.setValue(token);
						t.setLinha(lineCount);
						t.setTipo(SimbolosLexicos.NUMERAL);
						
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.NUMERAL).add(t);
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
					}
					token = "";
				}			
			}else if(classe_token_anterior.equals(SimbolosLexicos.TYPE)){
				
				if(!Character.isSpaceChar(a) || a.equals(')')) {
					
					token += String.valueOf(a);
//					System.out.println(token + " | " + token.toCharArray()[0]);
					if(Character.isDigit(token.toCharArray()[0])) {
						
						
					}
			
				}else {
					if (!token.isEmpty()) {
							if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.ID) != null ) {
								
								Token t = new Token();
								t.setKey(token);
								t.setValue(token);
								t.setLinha(lineCount);
								t.setTipo(SimbolosLexicos.ID);
								
								TabelaSimbolos.symbolTable.get(SimbolosLexicos.ID).add(t);
								TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
						
						}
					}
				}
			
		} 		
			

		}
		
	}
}
