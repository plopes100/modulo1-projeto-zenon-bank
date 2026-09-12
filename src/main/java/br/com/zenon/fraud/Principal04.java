package br.com.zenon.fraud;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class Principal04 {

	public static void main(String[] args) throws Exception {
		String pastaAtual = System.getProperty("user.dir");
		Path nomeArquivo = Path.of(pastaAtual+"/data/paysim_with_bad_data.csv");
		
		try (var reader = Files.newBufferedReader(nomeArquivo)) {
			//Pular a primeira linha
        	reader.readLine();
        	
        	String linha;
        	List<Transacao> transacoes = new LinkedList<Transacao>();
           	List<String> erros = new LinkedList<String>();
        	
        	
        	while ((linha = reader.readLine()) != null) {
        		try {
        			Transacao transacao = Principal02.getTransacao(linha);
        			transacoes.add(transacao);
        		}
        		catch (Exception ex) {
        			String erro = "Erro: " + linha + " | " + ex.getClass().getName();
        			if (!ex.getClass().getName().contains("NumberFormatException")) {
        				erro += ": " + ex.getMessage(); 
        			}
        			erros.add(erro);
        		}
        	}
        	for (String erro : erros) {
        		System.err.println(erro);
        	}
        	System.out.println(transacoes.size());
        	for (Transacao transacaoOk : transacoes) {
        		System.out.println(transacaoOk);
        	}
        }
	}

}
