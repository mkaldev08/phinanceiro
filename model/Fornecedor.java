package model;

public class Fornecedor extends Pessoa {
    private String produtoFornecido;
    int id;

    public Fornecedor(String produtoFornecido, String nome, String sobreNome, String telefone, String email, String endereco, String bilheteIdentidade) {
        super(nome, sobreNome, telefone, email, endereco, bilheteIdentidade);
        this.produtoFornecido = produtoFornecido;
    }

    public String getProdutoFornecido() {
        return produtoFornecido;
    }

    public void setProdutoFornecido(String produtoFornecido) {
        this.produtoFornecido = produtoFornecido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
