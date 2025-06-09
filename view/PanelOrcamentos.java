package view;

import controller.OrcamentoController;
import controller.ServicoController;
import model.Servico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelOrcamentos extends JPanel {
    private int orcamentoAtualId;
    private final OrcamentoController orcamentoController;
    private final ServicoController servicoController;
    private JTable tableServicosDisponiveis;
    private JTable tableItensOrcamento;

    public PanelOrcamentos(ServicoController servicoController, OrcamentoController orcamentoController) {
        this.servicoController = servicoController;
        this.orcamentoController = orcamentoController;
        this.orcamentoAtualId = this.orcamentoController.criarOrcamento().getId();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Tabela de serviços disponíveis
        tableServicosDisponiveis = new JTable(new ServicoTableModel(servicoController.listarTodosServicos()));
        JScrollPane scrollServicos = new JScrollPane(tableServicosDisponiveis);

        // Tabela de itens que estarao no orçamento
        tableItensOrcamento = new JTable(new DefaultTableModel(
                new Object[]{"Descrição", "Quantidade", "Valor Unitário", "Subtotal"}, 0));
        JScrollPane scrollItens = new JScrollPane(tableItensOrcamento);


        JButton btnAdicionar = new JButton("Adicionar ao Orçamento");
        btnAdicionar.addActionListener(e -> adicionarServicoAoOrcamento());
        JButton btnFinalizarOrcamento = new JButton("Finalizar Orçamento");
        btnFinalizarOrcamento.addActionListener(e -> finalizarOrcamento());


        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollServicos, scrollItens);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);
        add(btnAdicionar, BorderLayout.SOUTH);
        add(btnFinalizarOrcamento, BorderLayout.WEST);

        atualizarTabelaItens();
    }

    private void adicionarServicoAoOrcamento() {
        int selectedRow = tableServicosDisponiveis.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um serviço");
            return;
        }

        Servico servico = ((ServicoTableModel) tableServicosDisponiveis.getModel())
                .getServicoAt(selectedRow);


        JSpinner spinnerQuantidade = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JTextField txtObservacoes = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Quantidade:"));
        panel.add(spinnerQuantidade);
        panel.add(new JLabel("Observações:"));
        panel.add(txtObservacoes);

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Serviço",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int quantidade = (Integer) spinnerQuantidade.getValue();
            String observacoes = txtObservacoes.getText();


            orcamentoController.adicionarItem(orcamentoAtualId,
                    servico.getId(), quantidade, observacoes);

            atualizarTabelaItens();
        }
    }

    private void atualizarTabelaItens() {

        DefaultTableModel model = (DefaultTableModel) tableItensOrcamento.getModel();
        model.setRowCount(0);

        orcamentoController.buscarOrcamento(orcamentoAtualId).getProdutos()
                .forEach(produto -> {
                    Servico servico = produto.getServico();
                    model.addRow(new Object[]{
                            servico.getDescricao(),
                            produto.getQuantidade(),
                            servico.getValorUnitario(),
                            servico.getValorUnitario() * produto.getQuantidade()
                    });
                });
    }

    private void finalizarOrcamento() {
        double total = orcamentoController.criarOrcamento().getValorTotal();
        JOptionPane.showMessageDialog(this,
                "Orçamento finalizado!\nTotal: Kz " + String.format("%.2f", total),
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
//        orcamentoController.limparOrcamentoAtual(orcamentoController.orc);
        DefaultTableModel model = (DefaultTableModel) tableItensOrcamento.getModel();
        model.setRowCount(0);
    }
}