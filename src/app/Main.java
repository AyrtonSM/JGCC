package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import classes.AnaliseLexica;
import classes.Token;
import utils.PalavraReservadaUtils;
import utils.SimbolosLexicos;
import utils.TabelaSimbolos;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TabelaSimbolos.createSymbleTable();
		PalavraReservadaUtils.createHashMapping();
		AnaliseLexica analiseLexica = new AnaliseLexica();
		
		
		// The name of the file to open.
        String nomeDoArquivo = "/home/ayrton/Documentos/count.c";

        // This will reference one line at a time
        String linha = null;

        try {
            FileReader fileReader = new FileReader(nomeDoArquivo);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((linha = bufferedReader.readLine()) != null) {
                analiseLexica.analisys(linha);
            }   

            bufferedReader.close();         
        }
        catch(FileNotFoundException e) {
            System.out.println("Não foi possível abrir o arquivo : '" + nomeDoArquivo  + "'");                
        }
        catch(IOException e) {
           e.printStackTrace();
        }
		
        System.out.println(TabelaSimbolos.symbolTable.keySet());
		
		for(ArrayList<Token> t : TabelaSimbolos.symbolTable.values()) {
			for (Token tok : t) {
				System.out.println(tok.getKey() + " | " + tok.getValue());
			}
		}
		
//		System.out.println();
	}

}
