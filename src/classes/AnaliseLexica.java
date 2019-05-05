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

	public boolean comentario = false;
	public boolean comentarioDeLinha = false;
	public int numLinhaComentario;
	public int linhaDoErro;

	public void listarElementos(String linha, int linhaCount) throws Exception {

		String[] str = linha.split(" ");

		for (int i = 0; i < str.length; i++) {

			if (str[i].length() > 0) {

				analisys(str[i], linhaCount);

			}
		}
	}

	public static boolean verificaIdentificador(String s) {

		if (Character.isAlphabetic(s.charAt(0))) {

			return true;
		} else {

			return false;
		}
	}

	public static boolean verificarFloat(String s) {

		for (int i = 0; i < s.length(); i++) {

			if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != '.') {

				return false;
			}
		}

		return true;
	}

	public void analisys(String text, int lineCount) throws Exception {

		String token = text;
		
		if(token.equals("//")) {
			comentarioDeLinha = true;
			numLinhaComentario = lineCount;
		}
		if(numLinhaComentario == lineCount) {
			token = "";
			return;
		}
		
		if (token.equals("/*")) {

			comentario = true;
			linhaDoErro = lineCount;

//			if (TabelaSimbolos.symbolTable.get(SimbolosLexicos.SIMB_ESPECIAIS) != null) {
//
//				Token t = new Token();
//				t.setKey(token);
//				t.setValue(Delimitadores.delimitadores.get(token));
//				t.setLinha(lineCount);
//				t.setTipo(SimbolosLexicos.SIMB_ESPECIAIS);
//
//				TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
//			}
			token = "";
			return;

		}

		if (token.equals("*/")) {

			comentario = false;

//			if (TabelaSimbolos.symbolTable.get(SimbolosLexicos.SIMB_ESPECIAIS) != null) {
//
//				Token t = new Token();
//				t.setKey(token);
//				t.setValue(Delimitadores.delimitadores.get(token));
//				t.setLinha(lineCount);
//				t.setTipo(SimbolosLexicos.SIMB_ESPECIAIS);
//				TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
//			}

			token = "";
			return;
		}

		if (comentario) {

			token = "";
			return;
		}

		if (PalavraReservadaUtils.reservedTypeWords.containsKey(token)) {

			if (TabelaSimbolos.symbolTable.get(SimbolosLexicos.TYPE) != null) {
				Token t = new Token();
				t.setKey(token);
				t.setValue(PalavraReservadaUtils.reservedTypeWords.get(token));
				t.setLinha(lineCount);
				t.setTipo(SimbolosLexicos.TYPE);
				TabelaSimbolos.symbolTable.get(SimbolosLexicos.TYPE).add(t);
				TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);

			}
			token = "";

		} else if (PalavraReservadaUtils.reservedWords.containsKey(token)) {

			if (TabelaSimbolos.symbolTable.get(SimbolosLexicos.PALAVRAS_RESERVADAS) != null) {
				Token t = new Token();
				t.setKey(token);
				t.setValue(PalavraReservadaUtils.reservedWords.get(token));
				t.setLinha(lineCount);
				t.setTipo(SimbolosLexicos.PALAVRAS_RESERVADAS);

				TabelaSimbolos.symbolTable.get(SimbolosLexicos.PALAVRAS_RESERVADAS).add(t);
				TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
			}
			token = "";
			
		} else if (OperadoresRelacionaisUtils.operadoresRelacionais.containsKey(token)) {

			if (TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_RELACIONAL) != null) {
				Token t = new Token();
				t.setKey(token);
				t.setValue(OperadoresRelacionaisUtils.operadoresRelacionais.get(token));
				t.setLinha(lineCount);
				t.setTipo(SimbolosLexicos.OP_RELACIONAL);

				TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_RELACIONAL).add(t);
				TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
			}
			token = "";

		} else if (AritmeticosUtils.aritmeticosMapping.containsKey(token)) {

			if (TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_ARITMETICO) != null) {
				Token t = new Token();
				t.setKey(token);
				t.setValue(AritmeticosUtils.aritmeticosMapping.get(token));
				t.setLinha(lineCount);
				t.setTipo(SimbolosLexicos.OP_ARITMETICO);

				TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_ARITMETICO).add(t);
				TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
			}

			token = "";
			
		} else if (Delimitadores.delimitadores.containsKey(token)) {

			if (TabelaSimbolos.symbolTable.get(SimbolosLexicos.SIMB_ESPECIAIS) != null) {
				Token t = new Token();
				t.setKey(token);
				t.setValue(Delimitadores.delimitadores.get(token));
				t.setLinha(lineCount);
				t.setTipo(SimbolosLexicos.SIMB_ESPECIAIS);
				TabelaSimbolos.symbolTable.get(SimbolosLexicos.SIMB_ESPECIAIS).add(t);
				TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
			}
			token = "";

		} else if (OperadoresLogicos.operadoresLogicos.containsKey(token)) {

			if (TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_LOGICO) != null) {
				Token t = new Token();
				t.setKey(token);
				t.setValue(OperadoresLogicos.operadoresLogicos.get(token));
				t.setLinha(lineCount);
				t.setTipo(SimbolosLexicos.OP_LOGICO);

				TabelaSimbolos.symbolTable.get(SimbolosLexicos.OP_LOGICO).add(t);
				TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
			}
			token = "";

		}else { // NUMERAIS E IDS E ERROS
			if (verificarFloat(token)) {
				if (TabelaSimbolos.symbolTable.get(SimbolosLexicos.NUMERAL) != null) {
					Token t = new Token();
					t.setKey(token);
					t.setValue(token);
					t.setLinha(lineCount);
					t.setTipo(SimbolosLexicos.NUMERAL);

					TabelaSimbolos.symbolTable.get(SimbolosLexicos.NUMERAL).add(t);
					TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
				}
				token = "";
				
			} else if (verificaIdentificador(token)) {
				if (TabelaSimbolos.symbolTable.get(SimbolosLexicos.ID) != null) {

					Token t = new Token();
					t.setKey(token);
					t.setValue(token);
					t.setLinha(lineCount);
					t.setTipo(SimbolosLexicos.ID);

					TabelaSimbolos.symbolTable.get(SimbolosLexicos.ID).add(t);
					TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL).add(t);
				}
				token = "";
				
			} else if (!verificaIdentificador(token)) {
				TabelaSimbolos.symbolTable.get(SimbolosLexicos.ERRO).add(new Token(
						"Erro Lexico : " + token + " simbolo não pertence a linguagem", String.valueOf(lineCount)));
				for (Token t : TabelaSimbolos.symbolTable.get(SimbolosLexicos.ERRO)) {
					throw new Exception("LINHA : " + t.getValue() + ":::  [DESCRICAO] :" + t.getKey());
				}

			}
			token = "";
		}
		
	}

}
