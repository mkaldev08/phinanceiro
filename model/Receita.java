package model;

import java.time.LocalDate;

public class Receita extends TransacaoFinanceira {
    private Cliente cliente;
    private Orcamento orcamentoOrigem;
    private FORMAPAGAMENTO formaPagamento;
    private boolean recebido;
    private int id;

    public Receita(Cliente c, FORMAPAGAMENTO formP, Orcamento orc) {
        super(orc.getValorTotal(), "Receita de " + c.getNome() + " " + orc.getId());
        this.cliente = c;
        this.formaPagamento = formP;
        this.orcamentoOrigem = orc;
    }

    public enum FORMAPAGAMENTO {
        DINHEIRO("Dinheiro"),
        EXPRESS("Express"),
        MULTICAIXA("Multicaixa"),
        TRANSFERENCIA("Transferência");

        private final String descricao;

        FORMAPAGAMENTO(String descricao) {
            this.descricao = descricao;
        }

        @Override
        public String toString() {
            return descricao;
        }
    }

    public void registrarRecebimento() {
        this.recebido = true;
        this.setData(LocalDate.now());
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isRecebido() {
        return recebido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Orcamento getOrcamentoOrigem() {
        return orcamentoOrigem;
    }


    public FORMAPAGAMENTO getFormapagamento() {
        return formaPagamento;
    }

    public String getDataFormatada() {
        return this.getData().toString();
    }
}
