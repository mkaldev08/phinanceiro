package controller;

import model.Orcamento;
import model.Receita;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReceitaController {
    private List<Receita> receitas;

    public ReceitaController() {
        this.receitas = new ArrayList<>();
    }

    public Receita criarReceita(Orcamento orcamento, Receita.FORMAPAGAMENTO formaPagamento) {
        if (orcamento == null || orcamento.getCliente() == null) {
            throw new IllegalArgumentException("Orçamento inválido ou sem cliente");
        }

        Receita novaReceita = new Receita(
                orcamento.getCliente(),
                formaPagamento,
                orcamento
        );

        receitas.add(novaReceita);
        return novaReceita;
    }

    public void registrarRecebimento(int idReceita) {
        Receita receita = receitas.stream()
                .filter(r -> r.getId() == idReceita)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Receita não encontrada"));

        receita.registrarRecebimento();
    }

    public List<Receita> listarReceitasPorCliente(int idCliente) {
        return receitas.stream()
                .filter(r -> r.getCliente().getId() == idCliente)
                .collect(Collectors.toList());
    }

    public List<Receita> listarReceitasRecebidas() {
        return receitas.stream()
                .filter(Receita::isRecebido)
                .collect(Collectors.toList());
    }

    public List<Receita> listarReceitasPendentes() {
        return receitas.stream()
                .filter(r -> !r.isRecebido())
                .collect(Collectors.toList());
    }

    public List<Receita> listarTodasReceitas() {
        return new ArrayList<>(receitas);
    }
}