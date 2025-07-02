package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Orcamento {
    private Cliente cliente;
    private LocalDate data;
    private ArrayList<ItemOrcamento> itens;
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
        this.itens = new ArrayList<>();
        this.status = STATUS.PENDENTE;
        this.data = LocalDate.now();
        this.cliente = cliente;
    }

    public void adicionarItemOrcamento(ItemOrcamento item) {
        itens.add(item);
    }

    public List<ItemOrcamento> getItensOrcamento() {
        return new ArrayList<>(itens);
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

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setItemOrcamentos(ArrayList<ItemOrcamento> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return itens.stream()
                .mapToDouble(ItemOrcamento::getSubtotal)
                .sum();
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}
