package model;

public class Receita extends TransacaoFinanceira {
    private Cliente cliente;
    private Orcamento orcamentoOrigem;
    private FORMAPAGAMENTO formapagamento;

    public Receita(Cliente c, FORMAPAGAMENTO formP, Orcamento orc) {
        super();
        this.cliente = c;
        this.formapagamento = formP;
        this.orcamentoOrigem = orc;
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

    public FORMAPAGAMENTO getFormapagamento() {
        return formapagamento;
    }

    public void setFormapagamento(FORMAPAGAMENTO formapagamento) {
        this.formapagamento = formapagamento;
    }
}
