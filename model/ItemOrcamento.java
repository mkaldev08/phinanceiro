package model;

public class ItemOrcamento {
    private Servico servico;
    private Material material;
    private int quantidade;
    private String observacoes;
    private double valorUnitario;
    private double desconto;
    private int id;

    public ItemOrcamento(Servico servico, int quantidade, String observacoes) {
        this.servico = servico;
        this.quantidade = quantidade;
        this.observacoes = observacoes;
        this.valorUnitario = servico.getValorUnitario();
    }

    public ItemOrcamento(Material material, int quantidade, String observacoes) {
        this.material = material;
        this.quantidade = quantidade;
        this.observacoes = observacoes;
        this.valorUnitario = material.getPrecoUnitario();
    }

    public double getSubtotal() {
        return valorUnitario * quantidade * (1 - desconto);
    }

    public Servico getServico() {
        return servico;
    }

    public Material getMaterial() {
        return material;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto >= 0 && desconto <= 1 ? desconto : 0; // percentagem normalizada (0% a 100% como 0 a 1)
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}