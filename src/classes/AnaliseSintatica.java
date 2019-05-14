package classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import utils.OperadoresLogicos;
import utils.OperadoresRelacionaisUtils;
import utils.PalavraReservadaUtils;
import utils.SimbolosLexicos;

public class AnaliseSintatica {

	private Stack<String> tokens = new Stack();
	private ArrayList<Token> tks;
	private ArrayList<Token> bloco = new ArrayList<Token>();

	public AnaliseSintatica(ArrayList<Token> tks) throws Exception {
		this.tks = tks;
		B();
	}

	private boolean biblioteca() throws Exception {
		try {

			String key = tks.get(0).getKey();
			System.out.println(key);
			if (key.charAt(key.length() - 2) == '.' && key.charAt(key.length() - 1) == 'h') {
				System.out.println(key);
				tks.remove(0);
				return true;

			} else {
				if (tks.get(1).getKey().equals(">")) {
					throw new Exception("[ERRO] :: Talvez vc quis dizer : " + tks.get(0).getKey() + ".h ?");
				}

			}
			return false;

		} catch (StringIndexOutOfBoundsException s) {
			try {
				throw new Exception("[ERRO] :: Estava esperando uma biblioteca na linha " + tks.get(0).getLinha());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

	}

	public String B() throws Exception {
		tokens.push("$");
		tokens.push("#");

		System.out.println(tokens.peek());

		verificaToken();
		tokens.push("include");
		verificaToken();
		tokens.push("<");
		verificaToken();
		System.out.println(tokens.get(0) + " | " + tks.get(0).getKey());
		biblioteca();
		tokens.push(">");
		verificaToken();

		if (tks.get(0).getKey().equals("#")) {
			B();
		}

		A();

		return "";
	}

	private void verificaToken() throws Exception {

		if (tks.get(0).getKey().equals("{")) {
			if (!checkBlock()) {
				throw new Exception("Está faltando fechar as chaves do bloco da linha " + bloco.get(0).getLinha());
			}else {
				tokens.pop();
			}
		}
		
		if (tokens.peek().equals(tks.get(0).getKey())) {
			System.out.println("Oh ai deu bom." + tks.get(0).getKey());
			tokens.pop();
			tks.remove(0);
		} else if(!tokens.peek().equals("$")){
			
			throw new Exception(
					"[ERRO SINTÁTICO] :: Estava esperando " + tokens.peek() + " e recebi " + tks.get(0).getKey());
		}
	}

	private boolean checkBlock() throws Exception {
		Iterator<Token> iteratorToken = tks.iterator();
		while (iteratorToken.hasNext()) {
			Token t = iteratorToken.next();
			System.out.println(t.getKey());
			if (t.getKey().equals("{")) {
				bloco.add(t);
				iteratorToken.remove();
			} else if (t.getKey().equals("}")) {
				try {
					if (bloco.get(bloco.size() - 1).getKey().equals("{")) {

						bloco.remove(bloco.size() - 1);
						iteratorToken.remove();
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new Exception("[ERRO] ::: [LINHA] : " + t.getLinha() + " . Existe um '}' sem '{' ");
				}

			}
		}

		return bloco.isEmpty();
	}

	int counter = 0;

	private void A() throws Exception {

		System.out.println(tokens.get(0));
		if (!tks.isEmpty()) {

			if (tks.get(0).getKey().equals("int")) {

				if (tks.get(1).getKey().equals("main")) {

					if (tks.get(2).getKey().equals("(")) {
						tokens.push("int");
						verificaToken();

						tokens.push("main");
						verificaToken();

						tokens.push("(");
						verificaToken();

						tokens.push(")");
						verificaToken();

						tokens.push("{");
						verificaToken();

					} else if (tks.get(2).getKey().equals("=")) {
						throw new Exception("[LINHA] " + tks.get(1).getLinha()
								+ " ::: main eh uma palavra reservada da linguagem e nao pode ser usada como variavel ");
					} else {
						throw new Exception("[LINHA]" + tks.get(1).getLinha() + " ::: estava esperando ( e recebi "
								+ tks.get(2).getKey());
					}

				} else if (Character.isAlphabetic(tks.get(1).getKey().charAt(0))) {
					if (Character.isAlphabetic(tks.get(1).getKey().charAt(0))) {
						for (String key : PalavraReservadaUtils.reservedWords.keySet()) {
							if (key.equals(tks.get(1).getKey())) {
								throw new Exception("[LINHA] " + tks.get(1).getLinha() + "::: Palavra reservada '"
										+ tks.get(1).getKey() + "' nao pode ser usada como nome de variavel");
							}
						}
					}

					if (tks.get(2).getKey().equals("=")) {
						INT();
					}

					// APARENTEMENTE DA PRA RECONHECER VARIAVEIS AQUI, MAS NÃO ACHO QUE AQUI SEJA O
					// PONTO, MAS ENFIM..
				}

			} else if (tks.get(0).getKey().equals("main")) {
				tokens.push("main");
				verificaToken();
			} else if (tks.get(0).getKey().equals("float")) {
				if (Character.isAlphabetic(tks.get(1).getKey().charAt(0))) {
					for (String key : PalavraReservadaUtils.reservedWords.keySet()) {
						if (key.equals(tks.get(1).getKey())) {
							throw new Exception("[LINHA] " + tks.get(1).getLinha() + "::: Palavra reservada '"
									+ tks.get(1).getKey() + "' nao pode ser usada como nome de variavel");
						}
					}

					if (tks.get(2).getKey().equals("=")) {

						FLOAT();

					}
				}

			} else if (tks.get(0).getKey().equals("if")) {
				IF();
			} else {
				System.out.println("algo de errado nao esta certo : " + tks.get(0).getKey());
			}
		}

	}

	private void INT() throws Exception {
		tokens.push("int");
		verificaToken();

		tokens.push(tks.get(0).getKey());
		verificaToken();

		tokens.push("=");
		verificaToken();

		tokens.push("\"");
		verificaToken();

		System.out.println("--> " + tks.get(0).getKey());

		tokens.push(tks.get(0).getKey());
		verificaToken();

		tokens.push("\"");
		verificaToken();
//	
		tokens.push(";");
		verificaToken();
////		
		A();
//		
	}

	private void FLOAT() throws Exception {
		tokens.push("float");
		verificaToken();

		M();
		P();

		A();

		System.out.println("ANALISE SINTATICA COMPLETADA COM SUCESSO.");

	}

	private void M() throws Exception {

		if (N(0, tks.get(0).getKey(), tks.get(0).getKey().length() - 1)) {
			tokens.add(tks.get(0).getKey());
			verificaToken();
		}
	}

	private boolean N(int pos, String keyArray, int j) throws Exception {

		if (pos > j)
			return true;

		if (Character.isAlphabetic(keyArray.charAt(pos)) || keyArray.charAt(pos) == '_') {
			pos += 1;
			System.out.println(pos + " | " + j);
			return N(pos, keyArray, j);
		} else if (Character.isDigit(keyArray.charAt(pos))) {
			return O(pos, keyArray, j);
		}

		return false;

	}

	private boolean O(int pos, String keyArray, int j) throws Exception {

		if (pos > j)
			return true;

		if (Character.isDigit(keyArray.charAt(pos))) {
			pos += 1;
			System.out.println(pos + " | " + j);
			return O(pos, keyArray, j);
		} else if (Character.isAlphabetic(keyArray.charAt(pos)) || keyArray.charAt(pos) == '_') {
			return N(pos, keyArray, j);
		}

		return false;

	}

	private void P() throws Exception {

		tokens.push("=");
		verificaToken();

		tokens.push(tks.get(0).getKey());
		verificaToken();

		tokens.push(";");
		verificaToken();

	}

	private void IF() throws Exception {
		F();

	}

	private void F() throws Exception {

		tokens.add("if");
		verificaToken();

		D();

	}

	private void H() throws Exception {

		tokens.add("else if");
		D();
		E();

		tokens.add("else");
		E();
	}

	private void C() throws Exception {
		A();
	}

	private void E() throws Exception {
		C();
	}

	private void D() throws Exception {

		tokens.push("(");
		verificaToken();
		opl();
		tokens.push(")");
		verificaToken();
		E();

	}

	private void G() throws Exception {
		opl();
	}

	private void opl() throws Exception {

		Y();

	}

	private void Z() throws Exception {

		if (OperadoresLogicos.operadoresLogicos.containsKey(tks.get(0).getKey())) {

			String valueOperadorLogico = OperadoresLogicos.operadoresLogicos.get(tks.get(0).getKey());
			System.out.println(valueOperadorLogico);
			switch (valueOperadorLogico) {
			case "AND":
				tokens.add("&&");
				verificaToken();

				Ti();

				break;
			case "OR":
				tokens.add("||");
				verificaToken();
				Ti();

				break;
			default:
				System.out.println("é o que?");
			}
		} else {
			if (tks.get(0).getKey().equals(")")) {
				return;
			}

			throw new Exception("ERRO ::: Estava esperando && ou || mas recebi --> " + tks.get(0).getKey());
		}

	}

	private void Ti() throws Exception {

		Ui();
		Z();

	}

	private void Ui() throws Exception {
		if (Character.isAlphabetic(tks.get(0).getKey().charAt(0))) {
			if (N(0, tks.get(0).getKey(), tks.get(0).getKey().length() - 1)) {

				tokens.push(tks.get(0).getKey());
				verificaToken();

				if (OperadoresRelacionaisUtils.operadoresRelacionais.containsKey(tks.get(0).getKey())) {

					tokens.push(tks.get(0).getKey());
					verificaToken();

					if (tks.get(0).getKey().equals("\"")) {
						tokens.push(tks.get(0).getKey());
						verificaToken();

						tokens.push(tks.get(0).getKey());
						verificaToken();

						tokens.push(tks.get(0).getKey());
						verificaToken();

					} else {
						tokens.push(tks.get(0).getKey());
						verificaToken();
					}

				}

			}
		}
	}

	private void Y() throws Exception {

		Ti();
		Z();
	}

	public String pilha() {
		for (String string : tokens) {
			System.out.println(string);
		}

		return "";
	}

}
