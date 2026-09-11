package br.com.zenon.fraud;

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class GestorTransacao {

	private static final int LIMITE = 1000;
	
	public List<Transacao> getTransacoes(Path nomeArquivo) throws Exception {
		
		List<Transacao> listaTransacao = new LinkedList<Transacao>();

		try (var reader = Files.newBufferedReader(nomeArquivo)) {
			//Pular a primeira linha
        	reader.readLine();
        	
        	String linha;
        	int conta = 0;
        	
        	while ((linha = reader.readLine()) != null && conta < LIMITE) {
        		Transacao transacao = Principal02.getTransacao(linha);
        		listaTransacao.add(transacao);
        	}
        }
		return listaTransacao;
	}
	
	
	
	
	
}
