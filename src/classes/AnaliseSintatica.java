package classes;

import java.util.Stack;

public class AnaliseSintatica {
	
	private Stack<String> tokens = new Stack(); 
	
	public String B() {
		tokens.add("main");
		tokens.add("(");
		tokens.add(")");
		tokens.add("{");
		A();
		tokens.add("}");
		return "";
	}
	int counter = 0;
	private void A() {
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
	
	public static void main(String[] args) {
		AnaliseSintatica as = new AnaliseSintatica();
		
		as.B();
		as.pilha();
	}
}
