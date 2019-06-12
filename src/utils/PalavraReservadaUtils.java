package utils;

import java.util.HashMap;

public class PalavraReservadaUtils {
	
	private static final String FOR = "for";
	private static final String CONST = "const";
	private static final String INT = "int";
	private static final String DOUBLE = "double";
	private static final String FLOAT = "float";
	private static final String LONG = "long";
	private static final String CHAR = "char";
	private static final String WHILE = "while";
	private static final String DO = "do";
	private static final String IF = "if";
	private static final String SWITCH = "switch";
	private static final String PRINTF = "printf";
	private static final String SCANF = "scanf";
	private static final String STRUCT = "struct";
	private static final String MAIN = "main";
	private static final String INCLUDE = "include";
	private static final String STDIO = "stdio.h";
	private static final String STDLIB = "stdlib.h";
	private static final String RETURN = "return";
	
	public static HashMap<String , String> reservedWords = new HashMap<String, String>();
	public static HashMap<String , String> reservedTypeWords = new HashMap<String, String>();
	
	public static void createHashMapping() {
			
			reservedWords.put(FOR, FOR);
			reservedWords.put(WHILE,WHILE);
			reservedWords.put(DO,DO);
			reservedWords.put(IF,IF);
			reservedWords.put(SWITCH,SWITCH);
			reservedWords.put(PRINTF,PRINTF);
			reservedWords.put(SCANF,SCANF);
			reservedWords.put(STRUCT,STRUCT);
			reservedWords.put(MAIN,MAIN);
			
			reservedWords.put(CONST,CONST);
			reservedWords.put(INCLUDE,INCLUDE);
			reservedWords.put(STDIO,STDIO);
			reservedWords.put(STDLIB,STDLIB);
			reservedWords.put(RETURN, RETURN);
			
			reservedTypeWords.put(INT, INT);
			reservedTypeWords.put(DOUBLE,DOUBLE);
			reservedTypeWords.put(FLOAT,FLOAT);
			reservedTypeWords.put(LONG,LONG);
			reservedTypeWords.put(CHAR,CHAR);
	}
	
}
