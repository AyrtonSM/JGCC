package classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import utils.Delimitadores;
import utils.OperadoresLogicos;
import utils.OperadoresRelacionaisUtils;
import utils.PalavraReservadaUtils;
import utils.SimbolosLexicos;
import utils.TabelaSimbolos;

public class AnaliseSintatica {

	private Stack<String> tokens = new Stack();
	private ArrayList<Token> tks;
	private ArrayList<Token> bloco = new ArrayList<Token>();
	//private String tokenAnterior;
	private boolean blocksChecked = false;
	
	
	public AnaliseSintatica(ArrayList<Token> tks) throws Exception {
		this.tks = tks;
		B();
	}

	private boolean biblioteca() throws Exception {
		try {

			String key = tks.get(0).getKey();
			//System.out.println(key);
			if (key.charAt(key.length() - 2) == '.' && key.charAt(key.length() - 1) == 'h') {
				//System.out.println(key);
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

	public void B() throws Exception {
		tokens.push("$");
		tokens.push("#");
		
		//System.out.println(tokens.peek());

		verificaToken();
		tokens.push("include");
		verificaToken();
		tokens.push("<");
		verificaToken();
		//System.out.println(tokens.peek() + " | " + tks.get(0).getKey());
		biblioteca();
		tokens.push(">");
		verificaToken();

		if (tks.get(0).getKey().equals("#")) {
			B();
		}

		A();
		
		

	}

	private void verificaToken() throws Exception {
		
	System.out.println("Topo da Pilha : " + tokens.peek() + " | Na cabeça da lista tenho : " +  tks.get(0).getKey());
		
//		if(tokens.peek().equals(")")) {
//			tokenAnterior = tokens.peek();
//		}
//		
		if (tks.get(0).getKey().equals("{")) {
			
			if (!checkBlock()) {
				throw new Exception("Está faltando fechar as chaves do bloco da linha " + bloco.get(0).getLinha());
			}else {
				//System.out.println("deveria apagar : "+ tokens.peek());
				
				tokens.pop();
				tks.remove(0);
			}
		}
		
		if (tokens.peek().equals(tks.get(0).getKey())) {
			//System.out.println("Oh ai deu bom." + tks.get(0).getKey());
			tokens.pop();
			tks.remove(0);
		} else if(!tokens.peek().equals("$")){
			
			throw new Exception(
					"[ERRO SINTÁTICO] :: Estava esperando " + tokens.peek() + " e recebi " + tks.get(0).getKey());
		}
	}

	private boolean checkBlock() throws Exception {
		if(!blocksChecked) {
			Iterator<Token> iteratorToken = tks.iterator();
			while (iteratorToken.hasNext()) {
				Token t = iteratorToken.next();
			
				if (t.getKey().equals("{")) {
					
					bloco.add(t);
					//iteratorToken.remove();
				} else if (t.getKey().equals("}")) {
					try {
						if (bloco.get(bloco.size() - 1).getKey().equals("{")) {

							bloco.remove(bloco.size() - 1);
						//	iteratorToken.remove();
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						throw new Exception("[ERRO] ::: [LINHA] : " + t.getLinha() + " . Existe um '}' sem '{' ");
					}

				}
			}
			blocksChecked = true;
			return bloco.isEmpty();	
		}else {
			return true;	
		}
		
	}

	int counter = 0;

	private void A() throws Exception {

		//System.out.println(tokens.get(0));
		if (!tks.isEmpty()) {
		
		if(Delimitadores.delimitadores.containsKey(tks.get(0).getKey())) {
			tks.remove(0);
		}else {
		
				
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

				}else if(tks.get(0).getKey().equals("char")) {
					System.out.println("--------------------------------------Aqui e um char");
					if (Character.isAlphabetic(tks.get(1).getKey().charAt(0))) {
						for (String key : PalavraReservadaUtils.reservedWords.keySet()) {
							if (key.equals(tks.get(1).getKey())) {
								throw new Exception("[LINHA] " + tks.get(1).getLinha() + "::: Palavra reservada '"
										+ tks.get(1).getKey() + "' nao pode ser usada como nome de variavel");
							}
						}

						if (tks.get(2).getKey().equals("=")) {

							CHAR();

						}
					
						
					}
				} else if (tks.get(0).getKey().equals("if")) {
					IF();
				} else {
					throw new Exception("ERRO ::: [LINHA] : "+tks.get(0).getLinha()+" >> Token '"+tks.get(0).getKey()+"' não está definido OU não está sendo usado corretamente ");
					
				}
			}
		}
		

	}

	private void CHAR() throws Exception {
		
		tokens.push("char");
		verificaToken();
		
		if(PalavraReservadaUtils.reservedWords.containsKey(tks.get(0).getKey()) || PalavraReservadaUtils.reservedTypeWords.containsKey(tks.get(0).getKey()) ) {
			throw new Exception("ERRO ::: '"+ tks.get(0).getKey() + "' não pode ser referenciado como nome de variavel pois é uma palavra reservada");
		}
		
		TabelaSimbolos.tiposDeclarados.put(tks.get(0).getKey(), "char");
		//String tmp = tks.get(0).getKey();
		
		tokens.push(tks.get(0).getKey());
		verificaToken();

		tokens.push("=");
		verificaToken();
		
		if(tks.get(0).getKey().equals("'")) {
			
			
			tokens.push("'");
			verificaToken();

			//System.out.println("--> " + tks.get(0).getKey());
			if(tks.get(0).getKey().length() == 1) {
				tokens.push(tks.get(0).getKey());
				verificaToken();
			}else {
				throw new Exception ("[LINHA]: "+ tks.get(0).getLinha() + " [ERRO] ::: Tamanho de char invalido");
			}
			

			tokens.push("'");
			verificaToken();
			
			tokens.push(";");
			verificaToken();
			
		}
	}
	
	private void INT() throws Exception {
		tokens.push("int");
		verificaToken();
		
		if(PalavraReservadaUtils.reservedWords.containsKey(tks.get(0).getKey()) || PalavraReservadaUtils.reservedTypeWords.containsKey(tks.get(0).getKey()) ) {
			throw new Exception("ERRO ::: '"+ tks.get(0).getKey() + "' não pode ser referenciado como nome de variavel pois é uma palavra reservada");
		}
		
		TabelaSimbolos.tiposDeclarados.put(tks.get(0).getKey(), "int");
		//String tmp = tks.get(0).getKey();
		
		tokens.push(tks.get(0).getKey());
		verificaToken();

		tokens.push("=");
		verificaToken();
		if(tks.get(0).getKey().equals("\"")) {
			
			throw new Exception("[LINHA]: "+tks.get(0).getLinha()+" [ERRO SEMANTICO] ::: O tipo do dado informado nao eh compativel. \n ");

//			tokens.push("\"");
//			verificaToken();
//
//			//System.out.println("--> " + tks.get(0).getKey());
//			try {
//				Integer.parseInt(tks.get(0).getKey());
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//		
//			
//			
//			tokens.push(tks.get(0).getKey());
//			verificaToken();
//
//			tokens.push("\"");
//			verificaToken();
			
			
		}else {
			
				if(TabelaSimbolos.tiposDeclarados.containsKey(tks.get(0).getKey())){
					String typeVariable = TabelaSimbolos.tiposDeclarados.get(tks.get(0).getKey());
					//String tipoRecebido = TabelaSimbolos.tiposDeclarados.get(tks.get(0).getKey());
					if(!typeVariable.equals("int")) {
						throw new Exception("[LINHA]:"+tks.get(0).getLinha()+" [ERRO SEMANTICO] ::: O tipo da variavel '"+tks.get(0).getKey()+ "' esta sendo associada incorretamente");
					}
					
					
				}else {
					try {
						
						Float.parseFloat(tks.get(0).getKey());
						//Integer.parseInt(tks.get(0).getKey());
						
					}catch (NumberFormatException e) {
						throw new Exception("O tipo do dado informado nao eh compativel. \n A variavel '"+tks.get(0).getKey() + "' nao foi definida no escopo da aplicacao");
					}
					
				}
			
			
			if(OperadoresLogicos.operadoresLogicos.containsKey(tks.get(0).getKey()) || 
					OperadoresRelacionaisUtils.operadoresRelacionais.containsKey(tks.get(0).getKey()) || 
					PalavraReservadaUtils.reservedWords.containsKey(tks.get(0).getKey()) || 
					PalavraReservadaUtils.reservedTypeWords.containsKey(tks.get(0).getKey()) || 
					Delimitadores.delimitadores.containsKey(tks.get(0).getKey())) {
				
				throw new Exception("[LINHA] ::: " + tks.get(0).getLinha() + " >> ERRO : '"+tks.get(0).getKey()+"' não deve ser definido como valor de uma variavel");
				
			}
			
			tokens.push(tks.get(0).getKey());
			verificaToken();
		}
		
	
		tokens.push(";");
		verificaToken();
	
		A();
		
	}

	private void FLOAT() throws Exception {
		tokens.push("float");
		verificaToken();

		M();
		P();
		A();

		
	}

	private void M() throws Exception {
		if(PalavraReservadaUtils.reservedWords.containsKey(tks.get(0).getKey()) || PalavraReservadaUtils.reservedTypeWords.containsKey(tks.get(0).getKey()) ) {
			throw new Exception("[LINHA] : "+tks.get(0).getLinha()+" >> ERRO ::: '"+ tks.get(0).getKey() + "' não pode ser referenciado como nome de variavel pois é uma palavra reservada");
		}
		if (N(0, tks.get(0).getKey(), tks.get(0).getKey().length() - 1)) {
			TabelaSimbolos.tiposDeclarados.put(tks.get(0).getKey(), "float");
			tokens.add(tks.get(0).getKey());
			verificaToken();
		}
	}

	private boolean N(int pos, String keyArray, int j) throws Exception {

		if (pos > j)
			return true;

		if (Character.isAlphabetic(keyArray.charAt(pos)) || keyArray.charAt(pos) == '_') {
			pos += 1;
			//System.out.println(pos + " | " + j);
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
			//System.out.println(pos + " | " + j);
			return O(pos, keyArray, j);
		} else if (Character.isAlphabetic(keyArray.charAt(pos)) || keyArray.charAt(pos) == '_') {
			return N(pos, keyArray, j);
		}

		return false;

	}

	private void P() throws Exception {

		tokens.push("=");
		verificaToken();
		
		
		if(TabelaSimbolos.tiposDeclarados.containsKey(tks.get(0).getKey())){
			String typeVariable = TabelaSimbolos.tiposDeclarados.get(tks.get(0).getKey());
			//String tipoRecebido = TabelaSimbolos.tiposDeclarados.get(tks.get(0).getKey());
			if(!typeVariable.equals("float")) {
				throw new Exception("[LINHA]:"+tks.get(0).getLinha()+" [ERRO SEMANTICO] ::: O tipo da variavel '"+tks.get(0).getKey()+ "' esta sendo associada incorretamente");

			}
			
			
		}else {
			try {
				
				Float.parseFloat(tks.get(0).getKey());
				//Integer.parseInt(tks.get(0).getKey());
				
			}catch (NumberFormatException e) {
				throw new Exception("[LINHA]: "+tks.get(0).getLinha()+" [ERRO SEMANTICO] ::: O tipo do dado informado nao eh compativel. \n ");
			}
			
		}
		
		tokens.push(tks.get(0).getKey());
		verificaToken();

		tokens.push(";");
		verificaToken();

	}

	private void IF() throws Exception {
		F();

	}

	private void F() throws Exception {

		tokens.push("if");
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
		tokens.push("{");	
		verificaToken();
		E();
		tokens.push("}");	
		verificaToken();
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
			//System.out.println(valueOperadorLogico);
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
				//System.out.println("é o que?");
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

	

}
