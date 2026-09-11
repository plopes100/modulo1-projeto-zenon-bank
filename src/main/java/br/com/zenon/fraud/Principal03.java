package br.com.zenon.fraud;

import java.nio.file.Path;
import java.util.List;

public class Principal03 {

	public static void main(String[] args) throws Exception {
		String pastaAtual = System.getProperty("user.dir");
		Path nomeArquivo = Path.of(pastaAtual+"/data/PS_20174392719_1491204439457_log.csv");
		try {
		
			GestorTransacao gestorTransacao = new GestorTransacao();
			List<Transacao> lista = gestorTransacao.getTransacoes(nomeArquivo);
			for (int i = 0; i < 10; i++) {
				IO.println(lista.get(i));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
