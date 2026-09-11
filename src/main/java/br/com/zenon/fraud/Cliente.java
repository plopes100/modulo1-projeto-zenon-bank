package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Cliente(String nome, BigDecimal saldoAnterior, BigDecimal saldoAtual ) {

}
