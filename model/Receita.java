package model;

public class Receita extends TransacaoFinanceira {
    private Cliente cliente;
    private Orcamento orcamentoOrigem;

    public Receita() {
        super();
    }

    public enum FORMAPAGAMENTO {
        DINHEIRO, EXPRESS, MULTICAIXA, TRANSFERENCIA
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Orcamento getOrcamentoOrigem() {
        return orcamentoOrigem;
    }

    public void setOrcamentoOrigem(Orcamento orcamentoOrigem) {
        this.orcamentoOrigem = orcamentoOrigem;
    }
}
