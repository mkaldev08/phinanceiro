package controller;

import model.Orcamento;
import model.Produto;
import model.Servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrcamentoController {
    private List<Orcamento> orcamentos;
    private ServicoController servicoController;
    private static int lastId = 0;

    public OrcamentoController(ServicoController servicoController) {
        this.orcamentos = new ArrayList<>();
        this.servicoController = servicoController;
    }

    public Orcamento criarOrcamento() {
        Orcamento novoOrcamento = new Orcamento();
        novoOrcamento.setId(++lastId);
        orcamentos.add(novoOrcamento);
        limparOrcamentoAtual(novoOrcamento.getId());
        return novoOrcamento;
    }

    public void adicionarItem(int orcamentoId, int servicoId, int quantidade, String observacoes) {
        Orcamento orcamento = buscarOrcamento(orcamentoId);
        Servico servico = servicoController.buscarServico(servicoId);

        if (orcamento == null || servico == null) {
            throw new IllegalArgumentException("Orçamento ou Serviço não encontrado");
        }

        Produto produto = new Produto();
        produto.setServico(servico);
        produto.setQuantidade(quantidade);
        produto.setObservacao(observacoes);
        produto.setDescricao(servico.getDescricao());

        orcamento.adicionarProduto(produto);
    }

    public Orcamento buscarOrcamento(int id) {
        return orcamentos.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public double calcularTotalOrcamento(int orcamentoId) {
        Orcamento orcamento = buscarOrcamento(orcamentoId);
        if (orcamento == null) return 0;

        return orcamento.getValorTotal();
    }

    public List<Servico> listarServicosDisponiveis() {
        return servicoController.listarTodosServicos();
    }

    public void limparOrcamentoAtual(int orcamentoId) {
        Orcamento orc = buscarOrcamento(orcamentoId);
        orc = new Orcamento();
    }

    public List<Produto> listarItensOrcamento(int orcamentoId) {
        Orcamento orcamento = buscarOrcamento(orcamentoId);
        return orcamento != null ? orcamento.getProdutos() : Collections.emptyList();
    }
}