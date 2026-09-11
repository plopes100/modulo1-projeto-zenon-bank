package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transacao(
		Long step, 
		TipoTransacao tipoTransacao, 
		BigDecimal valor, 
		Cliente clienteOrigem, 
		Cliente clienteDestino,
		Boolean houveFraude,
		Boolean fraudeFoiIdentificada
		) {

}
