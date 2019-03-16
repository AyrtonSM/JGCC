package classes;

import utils.PalavraReservadaUtils;
import utils.SimbolosLexicos;
import utils.TabelaSimbolos;

public class AnaliseLexica {
	
	public void analisys(String text) {
		String token = "";
		for (Character a : text.toCharArray()) {
			if(Character.isLetter(a)) {
				token += String.valueOf(a);
				System.out.println(token);
				if(PalavraReservadaUtils.reservedWords.containsKey(token)) {
					if(TabelaSimbolos.symbolTable.get(SimbolosLexicos.PALAVRAS_RESERVADAS)!=null) {
						Token t = new Token();
						t.setKey(token);
						t.setValue(PalavraReservadaUtils.reservedWords.get(token));
						System.out.println("opa");
						TabelaSimbolos.symbolTable.get(SimbolosLexicos.PALAVRAS_RESERVADAS).add(t);
					}
					token = "";
				}
			}
			
		}
	}
}
