package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Principal02 {

	public static void main(String[] args) {
		String pastaAtual = System.getProperty("user.dir");
		
		Path nomeArquivo = Path.of(pastaAtual+"/data/PS_20174392719_1491204439457_log.csv");
		
		try {
			String linha1 = getPrimeiraLinhaValida(nomeArquivo);
			String linha2 = getUltimaLinha(nomeArquivo);
			
			Transacao trans1 = getTransacao(linha1);
			Transacao trans2 = getTransacao(linha2);
			
			System.out.println(trans1);
			System.out.println(trans2);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static String getPrimeiraLinhaValida(Path caminho) throws Exception {
        try (var reader = java.nio.file.Files.newBufferedReader(caminho)) {
			//Pular a primeira linha
        	reader.readLine();
            return reader.readLine();
        }
    }


    public static String getUltimaLinha(Path caminho) throws Exception {
        try (FileChannel channel = FileChannel.open(caminho, StandardOpenOption.READ)) {

            long tamanho = channel.size();
            if (tamanho == 0) return null;

            // Mapeia o arquivo inteiro na memória virtual
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, tamanho);

            // Busca reversa pelo '\n'
            int pos = (int)tamanho - 1;
            StringBuilder sb = new StringBuilder();

            byte b = buffer.get((int) pos);
            if (b == '\n') { // Achou o final da última linha
            	b = ' ';
            	while (b != '\n') { //Vai correr voltando até achar o final da penultima
                	pos--;
                    b = buffer.get((int) pos);
            	}
            	// Estou no \n da penultima linha
            	int inicio = pos + 1;
                for (int i = inicio; i < tamanho; i++) {
                    sb.append((char) buffer.get(i));
                }
            }
            return sb.toString();
        }
    }
	
	public static Transacao getTransacao(String linha) {
		String[] nomes = "step,type,amount,nameOrig,oldbalanceOrg,newbalanceOrig,nameDest,oldbalanceDest,newbalanceDest,isFraud,isFlaggedFraud".split(",");
		String[] campos = linha.split(",");
		
		for (int ind = 0; ind < campos.length; ind++) {
			if (campos[ind] == null) {
				throw new IllegalArgumentException("O Campo: "+nomes[ind]+" não pode estar sem informação");
			}
		}
		
		if (Long.valueOf(campos[0]) <= 0) {
			throw new IllegalArgumentException("step should be positive: "+ campos[0]);
		}
		int[] indCampos = {2, 4, 5, 7, 8};
		for (int i = 0; i < indCampos.length; i++) {
			if (new BigDecimal(campos[indCampos[i]]).compareTo(BigDecimal.ZERO) < 0) {
				throw new IllegalArgumentException(nomes[indCampos[i]]+" should be positive: " +campos[indCampos[i]]);
			}
		}

		if ((campos[3].trim().isEmpty()) || (campos[6].trim().isEmpty())) {
			throw new IllegalArgumentException("name should not be empty");
		}
		
		Cliente clienteOrigem  = new Cliente(campos[3], new BigDecimal(campos[4]), new BigDecimal(campos[5]));
		Cliente clienteDestino = new Cliente(campos[6], new BigDecimal(campos[7]), new BigDecimal(campos[8]));
		Transacao transacao = new Transacao(
				Long.valueOf(campos[0]), 
				TipoTransacao.valueOf(campos[1].toUpperCase()),
				new BigDecimal(campos[2]),
				clienteOrigem,
				clienteDestino,
				"1".equals(campos[9]),
				"1".equals(campos[10])
				);
		return transacao;
	}
	
	

}
