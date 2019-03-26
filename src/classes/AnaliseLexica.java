package classes;

import utils.AritmeticosUtils;
import utils.OperadoresRelacionaisUtils;
import utils.PalavraReservadaUtils;
import utils.SimbolosLexicos;
import utils.TabelaSimbolos;

public class AnaliseLexica {
	
	public void analisys(String text) {
		String token = "";
		char anterior = 0;
		for (Character a : text.toCharArray()) {
			if(Character.isLetter(a) || a.equals('.')) {
				
				if(anterior == '>' || anterior == '<' || anterior == '=' || anterior == '!') {
					anterior = 0;
					token = "";
				}
					
				token += String.valueOf(a);
				System.out.println(token);
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
						
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_ARITMETICO).add(t);
					}
				}
			}
			
		}
	}
}
