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

    public static enum STATUS {
        PENDENTE, APROVADO, RECUSADO, CANCELADO
    }

    public Orcamento() {

    }

    public Cliente getCliente() {
        return cliente;
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

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}
