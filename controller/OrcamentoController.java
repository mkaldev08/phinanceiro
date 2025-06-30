package controller;

import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrcamentoController {
    private List<Orcamento> orcamentos;
    private ServicoController servicoController;
    private MaterialController materialController;
    private final ReceitaController receitaController;
    private static int lastId = 0;

    public OrcamentoController(ServicoController servicoController, MaterialController materialController, ReceitaController receitaController) {
        this.orcamentos = new ArrayList<>();
        this.servicoController = servicoController;
        this.materialController = materialController;
        this.receitaController = receitaController;
    }

    public void aprovarOrcamento(int orcamentoId, Receita.FORMAPAGAMENTO formaPagamento) {
        Orcamento orcamento = buscarOrcamento(orcamentoId);
        if (orcamento != null) {
            orcamento.aprovar();

            // Cria a receita associada
            Receita receita = receitaController.criarReceita(orcamento, formaPagamento);
            System.out.println("TESTE: " + receita.getDescricao());
        }
    }

    public Orcamento criarOrcamento(Cliente cliente) {
        Orcamento novoOrcamento = new Orcamento(cliente);
        novoOrcamento.setId(++lastId);
        orcamentos.add(novoOrcamento);
        limparOrcamentoAtual(novoOrcamento.getId());
        return novoOrcamento;
    }

    public void adicionarItemServico(int orcamentoId, int servicoId, int quantidade, String observacoes) {
        Orcamento orcamento = buscarOrcamento(orcamentoId);
        Servico servico = servicoController.buscarServico(servicoId);

        if (orcamento == null || servico == null) {
            throw new IllegalArgumentException("Orçamento ou Serviço não encontrado");
        }

        ItemOrcamento item = new ItemOrcamento(servico, quantidade, observacoes);

        orcamento.adicionarItemOrcamento(item);
    }

    public void adicionarItemMaterial(int orcamentoId, int materialId, int quantidade, String observacoes) {
        Orcamento orcamento = buscarOrcamento(orcamentoId);

        Material material = materialController.buscarMaterial(materialId);

        if (orcamento == null || material == null) {
            throw new IllegalArgumentException("Orçamento ou Material não encontrado");
        }

        ItemOrcamento item = new ItemOrcamento(material, quantidade, observacoes);

        orcamento.adicionarItemOrcamento(item);
    }

    public Orcamento buscarOrcamento(int id) {
        return orcamentos.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void limparOrcamentoAtual(int orcamentoId) {
        Orcamento orc = buscarOrcamento(orcamentoId);
        orc = new Orcamento(orc.getCliente());
    }

    public List<ItemOrcamento> listarItensOrcamento(int orcamentoId) {
        Orcamento orcamento = buscarOrcamento(orcamentoId);
        return orcamento != null ? orcamento.getItensOrcamento() : Collections.emptyList();
    }
}