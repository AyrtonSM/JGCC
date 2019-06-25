package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import utils.AritmeticosUtils;
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
	public final String BLOCO = "BLOCO";
	//private String tokenAnterior;
	boolean isBlock = false;
	public String ultimoBlocoVisto = "";
	private boolean blocksChecked = false;
	private ArrayList<Bloco> blocosDeclarados = new ArrayList<Bloco>();

	public AnaliseSintatica(ArrayList<Token> tks) throws Exception {
		this.tks = tks;
		B();
		blocosDeclarados.get(0).setClosed(true);
		MathTree m = new MathTree();
		m.insert("/");
		m.insert("+");
		m.insert("a");
		m.insert("b");
		m.insert("c");
		//m.printall(m.getRoot());
		
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

		if (tks.get(0).getKey().equals("{")) {

			if (!checkBlock()) {
				throw new Exception("Está faltando fechar as chaves do bloco da linha " + bloco.get(0).getLinha());
			}else {
				//System.out.println("=============================================> deveria apagar : ");
				
				System.out.println("**** 1 ****");
				for (Token k : tks) {
					System.out.print(k.getKey());
					System.out.print(" | ");
				}
				
				
				tokens.pop();
				tks.remove(0);
				
				System.out.println("**** 2 ****\n");
				for (Token k : tks) {
					System.out.print(k.getKey());
					System.out.print(" | ");
				}
				System.out.println("**** 3 ****\n");
				for (String t : tokens) {
					System.out.print(t + " | ");
				}
				
				System.out.println("-------------------------------------------------------------");
				
			}
		}

		if (tokens.peek().equals(tks.get(0).getKey())) {
			//System.out.println("Oh ai deu bom." + tks.get(0).getKey());
			tokens.pop();
			tks.remove(0);
		}else if(!tokens.peek().equals("$")){

			throw new Exception(
					"[LINHA]: " + tks.get(0).getLinha() + " [ERRO SINTÁTICO] :: Estava esperando " + tokens.peek() + " e recebi " + tks.get(0).getKey());
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
				if(!tks.get(0).getKey().equals("}")) {
					tks.remove(0);
				}
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
							
							Bloco b = new Bloco();
							b.setId("BLOCO"+blocosDeclarados.size());
							blocosDeclarados.add(b);
							
							TabelaSimbolos.bloco.put(b.getId(), new HashMap<String, String>());

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
		
		verifyAllBlocks("char");
		
		tokens.push(tks.get(0).getKey());
		verificaToken();

		tokens.push("=");
		verificaToken();

		if(tks.get(0).getKey().equals("'")) {


			tokens.push("'");
			verificaToken();

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


		}else {
			
			lookfor("char");
			
			tokens.push(tks.get(0).getKey());
			verificaToken();
			

		}

	
		A();
	}

	private void INT() throws Exception {
		tokens.push("int");
		verificaToken();

		if(PalavraReservadaUtils.reservedWords.containsKey(tks.get(0).getKey()) || PalavraReservadaUtils.reservedTypeWords.containsKey(tks.get(0).getKey()) ) {
			throw new Exception("ERRO ::: '"+ tks.get(0).getKey() + "' não pode ser referenciado como nome de variavel pois é uma palavra reservada");
		}
		
		
		verifyAllBlocks("int");

		tokens.push(tks.get(0).getKey());
		verificaToken();

		tokens.push("=");
		verificaToken();
		if(tks.get(0).getKey().equals("\"")) {

			throw new Exception("[LINHA]: "+tks.get(0).getLinha()+" [ERRO SEMANTICO] ::: O tipo do dado informado nao eh compativel. \n ");

		}else {
			
			if(Character.isAlphabetic(tks.get(0).getKey().charAt(0))) {
				lookfor("int");	
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
		
		if(AritmeticosUtils.aritmeticosMapping.containsKey(tks.get(0).getKey())) {
			opa("int");
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
			
			verifyAllBlocks("float");
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

		if(Character.isAlphabetic(tks.get(0).getKey().charAt(0)))
			
			lookfor("float");
		
		else{
			
			tokens.push(tks.get(0).getKey());
			verificaToken();
			
			if (AritmeticosUtils.aritmeticosMapping.containsKey(tks.get(0).getKey())) {
				opa("float");
			}
			
			tokens.push(";");
			verificaToken();

		}
		
	}
	private void opa(String type) throws Exception {
		tokens.push(tks.get(0).getKey());
		verificaToken();
		
		if(Character.isAlphabetic(tks.get(0).getKey().charAt(0)))
			
			lookfor(type);
		
		else{
			
			tokens.push(tks.get(0).getKey());
			verificaToken();
			
			if (AritmeticosUtils.aritmeticosMapping.containsKey(tks.get(0).getKey())) {
				opa(type);
			}
			
		}
		
		
	}

	private void IF() throws Exception {
		F();

	}

	private void F() throws Exception {

		tokens.push("if");
		verificaToken();
		
		D();
		A();

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
	
	int counterblocks = 1;
	private void D() throws Exception {

		tokens.push("(");
		verificaToken();
		opl();
		tokens.push(")");	
		verificaToken();
		tokens.push("{");	
		verificaToken();
		
		//TabelaSimbolos.tiposDeclaradosBloco.put(BLOCO + TabelaSimbolos.tiposDeclaradosBloco.size(), null);
		String nomeBloco = BLOCO + blocosDeclarados.size();
		System.out.println(nomeBloco);
		Bloco b = new Bloco();
		b.setId(nomeBloco);
		blocosDeclarados.add(b);
		
		TabelaSimbolos.bloco.put(b.getId(), new HashMap<String, String>());
		
		E();
		
		tokens.push("}");		
		verificaToken();
		
		blocosDeclarados.get(blocosDeclarados.size()-counterblocks).setClosed(true);
		counterblocks++;
		
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

	
	
	
	private boolean verifyExistence(String key) throws Exception {
	
		for(int i = blocosDeclarados.size() -  1 ; i >= 0 ; i--) {
			
			if(!blocosDeclarados.get(i).isClosed()) {
				HashMap<String, String> map = TabelaSimbolos.bloco.get(blocosDeclarados.get(i).getId());
				if(!map.containsKey(key)) {
					throw new Exception("[LINHA] : "+tks.get(0).getLinha()+" | [ERRO SEMÂNTICO] ::: Variavel '"+key+"' nunca criada antereriormente ao seu uso");
				}else {
					return true;
				}
			}

		}
		return false;
		
	}
	
	/**
	 * Verifica todos os blocos de tras pra frente, e adiciona na hash de tipos se não existe  
	 * @param type
	 * @throws Exception
	 */
	private void verifyAllBlocks(String type) throws Exception {
		boolean allClose = false;
		for(int i = blocosDeclarados.size() -  1 ; i >= 0 ; i--) {
			
			if(!blocosDeclarados.get(i).isClosed()) {
				HashMap<String, String> map = TabelaSimbolos.bloco.get(blocosDeclarados.get(i).getId());
				if(map.containsKey(tks.get(0).getKey())) {
					throw new Exception("[LINHA] : "+tks.get(0).getLinha()+" | [ERRO SEMÂNTICO] ::: Variavel '"+tks.get(0).getKey()+"' ja esta declarada nesse bloco.");
				}else {
					map.put(tks.get(0).getKey(), type);
					break;
				}
			}
			allClose = true;
		}
		
		
	}
	
	
	/***
	 * Verifica se a variavel ja foi declarada em algum ponto de algum bloco.
	 * @param type
	 * @throws Exception
	 */
	private void lookfor(String type) throws Exception {
		
		if(type.equals("int")) {
			
			try {
				for(int i = blocosDeclarados.size() -  1 ; i >= 0 ; i--) {
					if(!blocosDeclarados.get(i).isClosed()) {
						HashMap<String, String> map = TabelaSimbolos.bloco.get(blocosDeclarados.get(i).getId());
						if(!map.containsKey(tks.get(0).getKey())) {
							throw new Exception("[LINHA]:" + tks.get(0).getLinha() + "  ::: A variavel '"+tks.get(0).getKey()+ "' nao foi declarada na aplicacao");
						}else {
							String typeVariable = map.get(tks.get(0).getKey());

							if(!typeVariable.equals(type)) {
								
								if(typeVariable.equals("float")) {
									Float.parseFloat(tks.get(0).getKey());
									break;
								}else
									throw new Exception("[LINHA]:"+tks.get(0).getLinha()+" [ERRO SEMANTICO] ::: O tipo da variavel '"+tks.get(0).getKey()+ "' esta sendo associada incorretamente");
							}	
							
						}
					}
				}
			}catch (NumberFormatException e) {
				throw new Exception("O tipo do dado informado nao eh compativel. \n A variavel '"+tks.get(0).getKey() + "' nao foi definida no escopo da aplicacao");
			}
		}else if(type.equals("float")){
			
			
			
			try {
				for(int i = blocosDeclarados.size() -  1 ; i >= 0 ; i--) {
					if(!blocosDeclarados.get(i).isClosed()) {
						HashMap<String, String> map = TabelaSimbolos.bloco.get(blocosDeclarados.get(i).getId());
						if(!map.containsKey(tks.get(0).getKey())) {
							throw new Exception("[LINHA]:" + tks.get(0).getLinha() + "  ::: A variavel '"+tks.get(0).getKey()+ "' nao foi declarada na aplicacao");
						}else {
							String typeVariable = map.get(tks.get(0).getKey());

							if(!typeVariable.equals(type)) {
								
								if(typeVariable.equals("int")) {
									Integer.parseInt(tks.get(0).getKey());
									break;
								}else
									throw new Exception("[LINHA]:"+tks.get(0).getLinha()+" [ERRO SEMANTICO] ::: O tipo da variavel '"+tks.get(0).getKey()+ "' esta sendo associada incorretamente");
							}	
							
						}
					}
				}
			}catch (NumberFormatException e) {
				throw new Exception("[LINHA]:"+tks.get(0).getLinha()+" [ERRO SEMANTICO] ::: O tipo da variavel '"+tks.get(0).getKey()+ "' esta sendo associada incorretamente");
			}
			
		
		}else {
			
			for(int i = blocosDeclarados.size() -  1 ; i >= 0 ; i--) {
				if(!blocosDeclarados.get(i).isClosed()) {
					HashMap<String, String> map = TabelaSimbolos.bloco.get(blocosDeclarados.get(i).getId());
					if(!map.containsKey(tks.get(0).getKey())) {
						throw new Exception("[LINHA]:" + tks.get(0).getLinha() + "  ::: A variavel '"+tks.get(0).getKey()+ "' nao foi declarada na aplicacao");
					}else {
						String typeVariable = map.get(tks.get(0).getKey());

						if(!typeVariable.equals(type)) {
							throw new Exception("[LINHA]:"+tks.get(0).getLinha()+" [ERRO SEMANTICO] ::: O tipo da variavel '"+tks.get(0).getKey()+ "' esta sendo associada incorretamente");
						}	
						break;
					}
				}
			}
			
		}
	
	}

}
