package model;

import java.time.LocalDate;

abstract class TransacaoFinanceira {
    private double valor;
    private String descricao;
    private LocalDate data;

    public TransacaoFinanceira(double valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
        this.setData(LocalDate.now());
    }

    public TransacaoFinanceira(){}

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
