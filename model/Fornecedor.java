package model;

public class Fornecedor extends Pessoa {
    private String produtoFornecido;
    private Despesa despesa;

    public Fornecedor(String produtoFornecido, Despesa despesa) {
        super();
        this.produtoFornecido = produtoFornecido;
        this.despesa = despesa;
    }

    public Fornecedor() {
    }

    public String getProdutoFornecido() {
        return produtoFornecido;
    }

    public void setProdutoFornecido(String produtoFornecido) {
        this.produtoFornecido = produtoFornecido;
    }
}
