package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import classes.AnaliseLexica;
import classes.AnaliseSintatica;
import classes.Token;

import utils.AritmeticosUtils;
import utils.Delimitadores;
import utils.OperadoresLogicos;
import utils.OperadoresRelacionaisUtils;
import utils.PalavraReservadaUtils;
import utils.PilhaSintatica;
import utils.SimbolosLexicos;
import utils.TabelaSimbolos;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		TabelaSimbolos.createSymbleTable();
		OperadoresRelacionaisUtils.createHashMapping();
		PalavraReservadaUtils.createHashMapping();
		AritmeticosUtils.createHashMapping();
		Delimitadores.createHashMapping();
		OperadoresLogicos.createHashMapping();
		
		
		
		AnaliseLexica analiseLexica = new AnaliseLexica();
		
		
		// The name of the file to open.
        String nomeDoArquivo = "leitura.c";

        // This will reference one line at a time
        String linha = null;

        try {
            FileReader fileReader = new FileReader(nomeDoArquivo);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int lineCount = 1;
            while((linha = bufferedReader.readLine()) != null) {
                analiseLexica.analisys(linha,lineCount);
                lineCount++;
            }   

            bufferedReader.close();         
        }
        catch(FileNotFoundException e) {
            System.out.println("Não foi possível abrir o arquivo : '" + nomeDoArquivo  + "'");                
        }
        catch(IOException e) {
           e.printStackTrace();
        }
        AnaliseSintatica as = new AnaliseSintatica();
		as.analisys(PilhaSintatica.tokenList);
//        System.out.println(TabelaSimbolos.symbolTable.keySet());
        for(Token t : TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL)) {
        	System.out.println("(TIPO) : " + t.getTipo() + "	|	(VALOR): " + t.getKey() + "	|	(LEXEMA): " + t.getValue() + "	|	(LINHAS): "+ t.getLinha());
        	System.out.println("");
        }
        
//        for(String key : TabelaSimbolos.symbolTable.keySet()) {
//        	
//        	 for(Token t : TabelaSimbolos.symbolTable.get(key)) {
//     				System.out.println(key + "		"+ t.getKey() + " --> " + t.getValue() + " / linha : "+t.getLinha());
//     		}
//        }
	}
}
