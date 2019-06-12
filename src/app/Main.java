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
                analiseLexica.listarElementos(linha, lineCount);
                lineCount++;
            }   
    		if(analiseLexica.comentario) {
    					
    					TabelaSimbolos.symbolTable.get(SimbolosLexicos.ERRO).add(new Token("Erro Lexico : "+"/*"+" comentario abre, mas não fecha",String.valueOf(analiseLexica.linhaDoErro)));
    					for (Token t : TabelaSimbolos.symbolTable.get(SimbolosLexicos.ERRO)) {
    						throw new Exception( "LINHA : " + t.getValue() + ":::  [DESCRICAO] :" + t.getKey() );
    					}
    				}
            bufferedReader.close();         
        }
        catch(FileNotFoundException e) {
            System.out.println("Não foi possível abrir o arquivo : '" + nomeDoArquivo  + "'");                
        }
        catch(IOException e) {
           e.printStackTrace();
        }
        
        for(Token t : TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL)) {
        	System.out.println("TIPO : " + t.getTipo() + "  |  VALOR: " + t.getKey() + "  |  LEXEMA: " + t.getValue() + "	|  LINHA: "+ t.getLinha());
        	System.out.println("");
        }
        System.out.println("ANALISE LÉXICA COMPLETADA COM SUCESSO.");
        
        System.out.println("\n===============================================================================================\n");
        
        System.out.println("\nINICIANDO ANALISE SINTATICA ... \n");
        
        
        AnaliseSintatica as = new AnaliseSintatica(TabelaSimbolos.symbolTable.get(SimbolosLexicos.TABELA_GERAL));
        System.out.println("ANALISE SINTATICA COMPLETADA COM SUCESSO.");
        
        
        System.out.println("\nTIPOS DECLARADOS ");
        for (String a : TabelaSimbolos.tiposDeclaradosmain.keySet()) {
        	System.out.println(a + "--> " + TabelaSimbolos.tiposDeclaradosmain.get(a));
        }
        
        
	}
}
