package classes;

import java.util.LinkedList;
import java.util.Stack;

public class AnaliseSintatica {
	public void analisys(LinkedList<Token> tokenList) throws Exception {
		Stack<String> stack = new Stack<String>();
		System.out.println("oi");
		stack.add("$");
		
		for (Token t : tokenList){
			if(stack.get(stack.size()-1).equals("include")) {
				if(!t.getValue().equals("<")) {
					throw new Exception("erro sintatico em " + t.getLinha() +". Estava esperando < recebi "+t.getValue());
				}
			}else if(stack.get(stack.size()-1).equals("math.h") || stack.get(stack.size()-1).equals("stdio.h")) {
				if(!t.getValue().equals(">")) {
					throw new Exception("erro sintatico em " + t.getLinha() +". Estava esperando < recebi "+t.getValue());
				}else {
					while(!stack.get(stack.size()-1).equals("$")) {
						
						stack.pop();
					}
					System.out.println("linha tal deu bom");
				}
			}
			stack.add(t.getValue());
			
		}
		
		for (String s : stack) {
			System.out.println(s);
		}
		
	}
}
