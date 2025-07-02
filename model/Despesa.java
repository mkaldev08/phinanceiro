package model;

public class Despesa extends TransacaoFinanceira {
    private Fornecedor fornecedor;
    private CATEGORIA categoria;
    private int id;


    public enum CATEGORIA {
        MATERIAL, ALUGUEL, SALARIO, MANUTENCAO, OUTROS
    }

    public Despesa(double valor, String descricao,
                   Fornecedor fornecedor, CATEGORIA categoria) {
        super(valor, descricao);
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public CATEGORIA getCategoria() {
        return categoria;
    }

    public void setCategoria(CATEGORIA categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
