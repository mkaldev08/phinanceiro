package model;

public class Despesa extends TransacaoFinanceira {
    private Fornecedor fornedor;
    private CATEGORIA categoria;


    public enum CATEGORIA {
        MATERIAL, ALUGUEL, SALARIO, MANUTENCAO, OUTROS
    }

    public Despesa() {
    }

    public Fornecedor getFornedor() {
        return fornedor;
    }

    public void setFornedor(Fornecedor fornedor) {
        this.fornedor = fornedor;
    }

    public CATEGORIA getCategoria() {
        return categoria;
    }

    public void setCategoria(CATEGORIA categoria) {
        this.categoria = categoria;
    }
}
