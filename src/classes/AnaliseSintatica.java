package classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import utils.PalavraReservadaUtils;
import utils.SimbolosLexicos;

public class AnaliseSintatica {
	
	private Stack<String> tokens = new Stack(); 
	private ArrayList<Token> tks;
	private ArrayList<Token> bloco = new ArrayList<Token>();
 	
	
	public AnaliseSintatica(ArrayList<Token> tks) throws Exception{
		this.tks = tks;
		B();
	}
	
	private boolean biblioteca() throws Exception {
		try {
			
			String key = tks.get(0).getKey();
			System.out.println(key);
			if(key.charAt(key.length()-2) == '.' && key.charAt(key.length()-1) == 'h') {
				System.out.println(key);
				tks.remove(0);
				return true;
				
			}else {
				if(tks.get(1).getKey().equals(">")) {
					throw new Exception("[ERRO] :: Talvez vc quis dizer : " + tks.get(0).getKey()+".h ?" );
				}
				
			}
			return false;
			
		}catch(StringIndexOutOfBoundsException s) {
			try {
				throw new Exception("[ERRO] :: Estava esperando uma biblioteca na linha " + tks.get(0).getLinha() );
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
		
		if(tks.get(0).getKey().equals("#")) {
			B();
		}
		
		A();
		
		return "";
	}
	private void verificaToken() throws Exception {
		
		if(tks.get(0).getKey().equals("{")) {
			if(!checkBlock()) {
				throw new Exception("Está faltando fechar as chaves do bloco da linha "+bloco.get(0).getLinha());
			}
		}
	
		if(tokens.peek().equals(tks.get(0).getKey())){
			System.out.println("Oh ai deu bom." + tks.get(0).getKey());
			tokens.pop();
			tks.remove(0);
		}else {
			try {
				throw new Exception("[ERRO SINTÁTICO] :: Estava esperando "+tokens.get(0)+" e recebi "+tks.get(0).getKey());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private boolean checkBlock() {
		Iterator<Token> iteratorToken = tks.iterator();
		while(iteratorToken.hasNext()) {
			Token t = iteratorToken.next();
			if(t.getKey().equals("{")) {
				bloco.add(t);
			}else if (t.getKey().equals("}")) {
				if(bloco.get(bloco.size()-1).getKey().equals("{")){
					
					bloco.remove(bloco.size()-1);
				}
			}
		}
		

		return bloco.isEmpty();
	}
	
	
	int counter = 0;
	private void A() throws Exception {
		
		System.out.println(tokens.get(0));
		
		if(tks.get(0).getKey().equals("int")) {
			
			if(tks.get(1).getKey().equals("main")) {
				
				if(tks.get(2).getKey().equals("(")) {
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
					
					
				}else if(tks.get(2).getKey().equals("=")) {
					throw new Exception("[LINHA] " + tks.get(1).getLinha() + " ::: main eh uma palavra reservada da linguagem e nao pode ser usada como variavel ");
				}else{
					throw new Exception("[LINHA]" + tks.get(1).getLinha()+ " ::: estava esperando ( e recebi "+tks.get(2).getKey());
				}
				
			}else if(Character.isAlphabetic(tks.get(1).getKey().charAt(0))) {
				if(Character.isAlphabetic(tks.get(1).getKey().charAt(0))) {
					for(String key : PalavraReservadaUtils.reservedWords.keySet()) {
						if(key.equals(tks.get(1).getKey())) {
							throw new Exception("[LINHA] " + tks.get(1).getLinha() + "::: Palavra reservada '"+tks.get(1).getKey()+"' nao pode ser usada como nome de variavel");
						}
					}
				}
				
				if(tks.get(2).getKey().equals("=")){
					INT();
				}
				
				// APARENTEMENTE DA PRA RECONHECER VARIAVEIS AQUI, MAS NÃO ACHO QUE AQUI SEJA O PONTO, MAS ENFIM..
			}
			
			
		}else if(tks.get(0).getKey().equals("main")) {
			tokens.push("main");
			verificaToken();
		}else if(tks.get(0).getKey().equals("float")) {
			if(Character.isAlphabetic(tks.get(1).getKey().charAt(0))) {
				for(String key : PalavraReservadaUtils.reservedWords.keySet()) {
					if(key.equals(tks.get(1).getKey())) {
						throw new Exception("[LINHA] " + tks.get(1).getLinha() + "::: Palavra reservada '"+tks.get(1).getKey()+"' nao pode ser usada como nome de variavel");
					}
				}
				
				if(tks.get(2).getKey().equals("=")) {
					
					FLOAT();
					
				}
			}
				
			
		}else {
			System.out.println("algo de errado nao esta certo : "+ tks.get(0).getKey());
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
		
		
		System.out.println("--> "+tks.get(0).getKey());
		
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
//		P();
//		
//		tokens.push(";");
//		verificaToken();
//		
//		A();
//		
		System.out.println("ANALISE SINTATICA COMPLETADA COM SUCESSO.");
	
		
	}
	
	private void M() throws Exception {
		
		N(0,tks.get(0).getKey());
	}
	
	

	private String N(int pos, String keyArray) throws Exception {
		
		int size = keyArray.length();
		System.out.println("== : "+ size + " : ---> "+ keyArray);
		if (size <= pos) return keyArray;
		
		if(Character.isAlphabetic(keyArray.toCharArray()[pos]) || keyArray.toCharArray()[pos] == '_') {
			System.out.println("opa : " + keyArray.toCharArray()[pos]);
		    N(pos+1 , keyArray.substring(pos, size));
			
		}else if(Character.isDigit(keyArray.toCharArray()[pos])) {
			O(pos,keyArray);
		}
		
		return null;
		
		
	}
		
//	
//		
//		
//		tokens.push(tks.get(0).getKey());
//		verificaToken();
//		
		
	
	private void O(int pos, String keyArray) throws Exception {
		int size = keyArray.length();
		
		if(Character.isDigit(keyArray.toCharArray()[pos])) {
			System.out.println("opa : " + keyArray.toCharArray()[pos]);
		    O(pos+1 , keyArray.substring(pos, size));
			
		}else if(Character.isAlphabetic(keyArray.toCharArray()[pos]) || keyArray.toCharArray()[pos] == '_') {
			N(pos,keyArray);
		}
		
		
	}
	
	
	private void P() throws Exception {
		
		tokens.push("=");
		verificaToken();
		
		
		
	}

	private void IF() {
		F();
		
	}

	private void F() {
		boolean a = true;
		if(a) {
			tokens.add("if");

			D();
			E();
		}else {
			F();
			H();	
		}
		
		
	}

	private void H() {
		
		tokens.add("else if");
		D();
		E();
		
		tokens.add("else");
		E();
	}

	private void C() throws Exception {
		A();	
	}
	
	private void E() {
		tokens.add("{");
		//C();
		tokens.add("}");	
	}

	private String D() {
		
		if(opl() == null) {
			tokens.add("(");
			G();
			tokens.add(")");	
		}else {
			tokens.add("(");
			tokens.add(opl());
			G();
			tokens.add(")");
				
		}
		
		return null;
	}

	private void G() {
		opl();
	}

	private String opl() {
		// TODO Auto-generated method stub
		return null;
		//Y();
		
		
		
	}
	
	

	private void Z() {
		tokens.add("&&");
		Ti();
		
//		tokens.add("||");
//		Ti();
//		tokens.add("~");
//		Ti();
		
		
	}

	private void Ti() {
		
		Ui();
		tokens.add("(");
		Y();
		tokens.add(")");
	
	}

	private void Ui() {
		tokens.add("0");
		//tokens.add("1");
	}

	private void Y() {
		
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
