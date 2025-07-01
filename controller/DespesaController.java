package controller;

import model.Despesa;
import model.Fornecedor;
import model.Despesa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DespesaController {
    private List<Despesa> despesas;
    private int lastId = 1;

    public DespesaController() {
        this.despesas = new ArrayList<>();
    }

    public void registrarDespesa(double valor, String descricao,
                                 Fornecedor fornecedor,
                                 Despesa.CATEGORIA categoria) {
        Despesa despesa = new Despesa(valor, descricao, fornecedor, categoria);
        despesa.setId(lastId++);
        despesas.add(despesa);
    }

    public List<Despesa> listarDespesasPorFornecedor(int idFornecedor) {
        return despesas.stream()
                .filter(d -> d.getFornecedor().getId() == idFornecedor)
                .collect(Collectors.toList());
    }

    public List<Despesa> listarTodasDespesas() {
        return new ArrayList<>(despesas);
    }

    public double calcularTotalDespesas() {
        return despesas.stream()
                .mapToDouble(Despesa::getValor)
                .sum();
    }


    public void removerDespesa(int id) {
        Despesa despesaParaRemover = buscarDespesaPorId(id);
        despesas.remove(despesaParaRemover);
    }

    public void atualizarDespesa(Despesa despesaAtualizada) {

        if (despesaAtualizada == null) {
            throw new IllegalArgumentException("Despesa não pode ser nula");
        }

        Despesa despesaExistente = despesas.stream()
                .filter(d -> d.getId() == despesaAtualizada.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Despesa não encontrada"));


        despesaExistente.setDescricao(despesaAtualizada.getDescricao());
        despesaExistente.setValor(despesaAtualizada.getValor());
        despesaExistente.setFornecedor(despesaAtualizada.getFornecedor());
        despesaExistente.setCategoria(despesaAtualizada.getCategoria());
        despesaExistente.setData(despesaAtualizada.getData());
    }


    public Despesa buscarDespesaPorId(int id) {
        return despesas.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);
    }
}