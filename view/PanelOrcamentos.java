package view;

import controller.ClienteController;
import controller.OrcamentoController;
import controller.ServicoController;

import model.Cliente;
import model.Orcamento;
import model.Receita;
import model.Servico;
import view.util.ClienteTableModel;
import view.util.ServicoTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelOrcamentos extends JPanel {
    private int orcamentoAtualId;
    private final OrcamentoController orcamentoController;
    private final ServicoController servicoController;
    private final ClienteController clienteController;


    private final ServicoTableModel tabelaServicosModel;
    private final ClienteTableModel tabelaClientesModel;

    private JTable tableItensOrcamento;
    private JTable tableServicosDisponiveis;
    private JTable tabelaClientes;

    public PanelOrcamentos(ServicoController servicoController, OrcamentoController orcamentoController, ClienteController clienteController) {
        this.servicoController = servicoController;
        this.orcamentoController = orcamentoController;
        this.clienteController = clienteController;


        tabelaServicosModel = new ServicoTableModel(servicoController.listarTodosServicos());
        tabelaClientesModel = new ClienteTableModel(clienteController.listarTodosClientes());

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Tabela de serviços disponíveis
        tableServicosDisponiveis = new JTable(tabelaServicosModel);
        tabelaClientes = new JTable(tabelaClientesModel);

        JScrollPane scrollServicos = new JScrollPane(tableServicosDisponiveis);
        JScrollPane scrollClientes = new JScrollPane(tabelaClientes);

        scrollServicos.setBorder(BorderFactory.createTitledBorder("Serviços"));
        scrollClientes.setBorder(BorderFactory.createTitledBorder("Clientes"));

        // Tabela de itens que estara no orçamento
        tableItensOrcamento = new JTable(new DefaultTableModel(new Object[]{"Descrição", "Quantidade", "Valor Unitário", "Subtotal"}, 0));
        JScrollPane scrollItens = new JScrollPane(tableItensOrcamento);
        scrollItens.setBorder(BorderFactory.createTitledBorder("Itens para o Orçamento"));

        JSplitPane spTabelas = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollServicos, scrollClientes);

        JPanel panelBotoesAccoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnSelecionarCliente = new JButton("Vincular Cliente");
        btnSelecionarCliente.addActionListener(e -> selecionarClienteParaOrcamento());

        JButton btnAdicionar = new JButton("Adicionar Serviço ao Orçamento");
        btnAdicionar.addActionListener(e -> adicionarServicoAoOrcamento());

        JButton btnFinalizarOrcamento = new JButton("Finalizar Orçamento");

        btnFinalizarOrcamento.addActionListener(e -> finalizarOrcamento());

        JButton btnFinalizarEAprovar = new JButton("Finalizar e Aprovar");
        btnFinalizarEAprovar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aprovarOrcamento();
            }
        });

        JButton btnCarregarDados = new JButton("Carregar Dados");
        btnCarregarDados.addActionListener(e -> atualizarTabelaAuxiliares());

        panelBotoesAccoes.add(btnSelecionarCliente);
        panelBotoesAccoes.add(btnAdicionar);
        panelBotoesAccoes.add(btnFinalizarOrcamento);
        panelBotoesAccoes.add(btnFinalizarEAprovar);
        panelBotoesAccoes.add(btnCarregarDados);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spTabelas, scrollItens);
        splitPane.setResizeWeight(0.5);
        spTabelas.setResizeWeight(.5);

        add(splitPane, BorderLayout.CENTER);
        add(panelBotoesAccoes, BorderLayout.SOUTH);

        atualizarTabelaItens();
    }

    private void adicionarServicoAoOrcamento() {

        if (orcamentoAtualId == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um cliente primeiro",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

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

            orcamentoController.adicionarItemServico(orcamentoAtualId, servico.getId(), quantidade, observacoes);

            atualizarTabelaItens();
        }
    }

    private void atualizarTabelaItens() {
        DefaultTableModel model = (DefaultTableModel) tableItensOrcamento.getModel();
        model.setRowCount(0);

        Orcamento orcamento = orcamentoController.buscarOrcamento(orcamentoAtualId);
        if (orcamento != null && orcamento.getItensOrcamento() != null) {
            orcamento.getItensOrcamento().forEach(item -> {
                if (item != null && item.getServico() != null) {
                    model.addRow(new Object[]{
                            item.getServico().getDescricao(),
                            item.getQuantidade(),
                            String.format("%,.2f", item.getServico().getValorUnitario()),
                            String.format("%,.2f", item.getServico().getValorUnitario() * item.getQuantidade())
                    });
                }
            });
        }
    }

    private void selecionarClienteParaOrcamento() {
        int selectedRow = tabelaClientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um cliente na tabela",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = tabelaClientesModel.getClienteAt(selectedRow);
        this.orcamentoAtualId = orcamentoController.criarOrcamento(cliente).getId();
        JOptionPane.showMessageDialog(this,
                "Orçamento criado para " + cliente.getNome(),
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void finalizarOrcamento() {
        int selectedRow = tabelaClientes.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um cliente antes de finalizar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = tabelaClientesModel.getClienteAt(selectedRow);

        Orcamento orcamento = orcamentoController.buscarOrcamento(orcamentoAtualId);
        if (orcamento != null) {
            orcamento.setCliente(cliente);
            double total = orcamento.getValorTotal();
            if (total <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Coloque ItemOrcamento/Servicos antes de finalizar",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Orçamento finalizado para " + cliente.getNome() +
                                "\nTotal: Kz " + String.format("%,.2f", total),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Inicia uma operacao antes de finalizar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void atualizarTabelaAuxiliares() {
        tabelaServicosModel.atualizarDados(servicoController.listarTodosServicos());
        tabelaClientesModel.atualizarDados(clienteController.listarTodosClientes());
    }

    private void aprovarOrcamento() {
        finalizarOrcamento();
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JComboBox<Receita.FORMAPAGAMENTO> comboFORMAPAGAMENTO = new JComboBox<>(Receita.FORMAPAGAMENTO.values());
        panel.add(new JLabel("Forma de Pagamento:"));
        panel.add(comboFORMAPAGAMENTO);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Aprovar Orçamento e Definir Pagamento",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            Receita.FORMAPAGAMENTO formaPagamento =
                    (Receita.FORMAPAGAMENTO) comboFORMAPAGAMENTO.getSelectedItem();

            orcamentoController.aprovarOrcamento(
                    orcamentoAtualId,
                    formaPagamento
            );

                    JOptionPane.showMessageDialog(
                            this,
                            "Orçamento aprovado e receita criada!\n" +
                                    "Forma de Pagamento: " + formaPagamento.toString(),
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE
                    );

            atualizarTabelaItens();
        }
    }
}