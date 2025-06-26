package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Orcamento {
    private Cliente cliente;
    private LocalDate data;
    private ArrayList<Produto> produtos;
    private double valorTotal;
    private STATUS status;
    private int id;

    public enum STATUS {
        PENDENTE("Pendente"), APROVADO("Aprovado"), RECUSADO("Recusado"), CANCELADO
                ("Cancelado");

        private final String descricao;

        STATUS(String descricao) {
            this.descricao = descricao;
        }

        @Override
        public String toString() {
            return this.descricao;
        }
    }

    public void aprovar() {
        this.status = STATUS.APROVADO;
    }

    public Orcamento(Cliente cliente) {
        this.produtos = new ArrayList<>();
        this.status = STATUS.PENDENTE;
        this.data = LocalDate.now();
        this.cliente = cliente;
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private double calcularValorTotal() {
        double tempTotal = 0;

        if (this.produtos == null || this.produtos.isEmpty()) {
            return tempTotal;
        }

        for (Produto produto : this.produtos) {
            if (produto.getServico() == null || produto.getQuantidade() < 1 || produto.getServico().getValorUnitario() < 1) {
                continue;
            }
            tempTotal += produto.getQuantidade() * produto.getServico().getValorUnitario();
        }
        return tempTotal;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public double getValorTotal() {
        valorTotal = calcularValorTotal();
        return valorTotal;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}
