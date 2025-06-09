package view;

import controller.OrcamentoController;
import controller.ServicoController;
import model.Orcamento;
import model.Servico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelOrcamentos extends JPanel {
    private int orcamentoAtualId;
    private final OrcamentoController orcamentoController;
    private final ServicoController servicoController;
    private ServicoTableModel tabelaServicosModel;
    private JTable tableItensOrcamento;
    private JTable tableServicosDisponiveis;

    public PanelOrcamentos(ServicoController servicoController, OrcamentoController orcamentoController) {
        this.servicoController = servicoController;
        this.orcamentoController = orcamentoController;
        this.orcamentoAtualId = this.orcamentoController.criarOrcamento().getId();

        tabelaServicosModel = new ServicoTableModel(servicoController.listarTodosServicos());

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Tabela de serviços disponíveis
        tableServicosDisponiveis = new JTable(tabelaServicosModel);
        JScrollPane scrollServicos = new JScrollPane(tableServicosDisponiveis);

        // Tabela de itens que estara no orçamento
        tableItensOrcamento = new JTable(new DefaultTableModel(new Object[]{"Descrição", "Quantidade", "Valor Unitário", "Subtotal"}, 0));
        JScrollPane scrollItens = new JScrollPane(tableItensOrcamento);

        JPanel panelBotoesAccoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnAdicionar = new JButton("Adicionar Serviço ao Orçamento");
        btnAdicionar.addActionListener(e -> adicionarServicoAoOrcamento());

        JButton btnFinalizarOrcamento = new JButton("Finalizar Orçamento");
        btnFinalizarOrcamento.addActionListener(e -> finalizarOrcamento());

        JButton btnCarregarDados = new JButton("Carregar Dados");
        btnCarregarDados.addActionListener(e -> atualizarTabelaServicosDisponiveis());

        panelBotoesAccoes.add(btnAdicionar);
        panelBotoesAccoes.add(btnFinalizarOrcamento);
        panelBotoesAccoes.add(btnCarregarDados);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollServicos, scrollItens);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);
        add(panelBotoesAccoes, BorderLayout.SOUTH);

        atualizarTabelaItens();
    }

    private void adicionarServicoAoOrcamento() {
        int selectedRow = tableServicosDisponiveis.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um serviço");
            return;
        }

        Servico servico = ((ServicoTableModel) tableServicosDisponiveis.getModel()).getServicoAt(selectedRow);


        JSpinner spinnerQuantidade = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JTextField txtObservacoes = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Quantidade:"));
        panel.add(spinnerQuantidade);
        panel.add(new JLabel("Observações:"));
        panel.add(txtObservacoes);

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Serviço", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int quantidade = (Integer) spinnerQuantidade.getValue();
            String observacoes = txtObservacoes.getText();


            orcamentoController.adicionarItem(orcamentoAtualId, servico.getId(), quantidade, observacoes);

            atualizarTabelaItens();
        }
    }

    private void atualizarTabelaItens() {
        DefaultTableModel model = (DefaultTableModel) tableItensOrcamento.getModel();
        model.setRowCount(0);

        Orcamento orcamento = orcamentoController.buscarOrcamento(orcamentoAtualId);
        if (orcamento != null && orcamento.getProdutos() != null) {
            orcamento.getProdutos().forEach(produto -> {
                if (produto != null && produto.getServico() != null) {
                    model.addRow(new Object[]{
                            produto.getServico().getDescricao(),
                            produto.getQuantidade(),
                            produto.getServico().getValorUnitario(),
                            produto.getServico().getValorUnitario() * produto.getQuantidade()
                    });
                }
            });
        }
    }

    private void finalizarOrcamento() {
        Orcamento orcamento = orcamentoController.buscarOrcamento(orcamentoAtualId);
        if (orcamento == null) return;

        double total = orcamento.getValorTotal();
        JOptionPane.showMessageDialog(this,
                "Orçamento finalizado!\nTotal: Kz " + String.format("%.2f", total),
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        this.orcamentoAtualId = orcamentoController.criarOrcamento().getId();
        atualizarTabelaItens();
    }

    private void atualizarTabelaServicosDisponiveis() {
        tabelaServicosModel.atualizarDados(servicoController.listarTodosServicos());
    }
}