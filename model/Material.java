package model;

public class Material {
    private int id;
    private String descricao;
    private String unidadeMedida;
    private double precoUnitario;
    private int estoque;

    public Material(String descricao, String unidadeMedida, double precoUnitario) {
        this.descricao = descricao;
        this.unidadeMedida = unidadeMedida;
        this.precoUnitario = precoUnitario;
    }


    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty())
            this.descricao = descricao;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        if (estoque > 0) {
            this.estoque = estoque;
        } else if (estoque == 0) {
            this.estoque = estoque;
        } else {
            this.estoque = 1;
        }
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
