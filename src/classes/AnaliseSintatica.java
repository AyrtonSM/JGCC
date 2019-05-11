package classes;

import java.util.ArrayList;
import java.util.Stack;

import utils.PalavraReservadaUtils;
import utils.SimbolosLexicos;

public class AnaliseSintatica {
	
	private Stack<String> tokens = new Stack(); 
	private ArrayList<Token> tks;
	
	
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
		}else {
			
		}

		return "";
	}
	private void verificaToken() {
		
		if(tokens.peek().equals(tks.get(0).getKey())){
			System.out.println("Oh ai deu bom.");
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
	int counter = 0;
	private void A() {
		if(tks.get(0).equals("int")) {
			if(tks.get(1).equals("main")) {
				tokens.push("int");
				verificaToken();
			}
		}
		
		
		if (counter == 0) {
			IF();
			counter++;
		}
		
		
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

	private void C() {
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
