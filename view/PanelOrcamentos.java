package view;

import controller.ClienteController;
import controller.OrcamentoController;
import controller.ServicoController;
import controller.MaterialController;

import model.*;
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
    private final MaterialController materialController;


    private final ClienteTableModel tabelaClientesModel;
    private final DefaultTableModel modelItensOrcamento = new DefaultTableModel(new Object[]{"Descrição", "Quantidade", "Valor Unitário", "Subtotal"}, 0);

    private JTable tableItensOrcamento;
    private JTable tableItensDisponiveis;
    private JTable tabelaClientes;

    public PanelOrcamentos(ServicoController servicoController, OrcamentoController orcamentoController, ClienteController clienteController, MaterialController materialController) {
        this.servicoController = servicoController;
        this.orcamentoController = orcamentoController;
        this.clienteController = clienteController;
        this.materialController = materialController;

        tabelaClientesModel = new ClienteTableModel(clienteController.listarTodosClientes());

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Tabela de itens disponíveis

        DefaultTableModel modelItensDisponiveis = new DefaultTableModel(
                new Object[]{"Tipo", "Descrição", "Valor Unitário"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 2 ? Double.class : String.class;
            }
        };

        for (Servico s : servicoController.listarTodosServicos()) {
            modelItensDisponiveis.addRow(new Object[]{"Serviço", s.getDescricao(), s.getValorUnitario()});
        }
        for (Material m : materialController.listarTodosMateriais()) {
            modelItensDisponiveis.addRow(new Object[]{"Material", m.getDescricao(), m.getPrecoUnitario()});
        }

        tableItensDisponiveis = new JTable(modelItensDisponiveis);
        tabelaClientes = new JTable(tabelaClientesModel);

        JScrollPane scrollServicos = new JScrollPane(tableItensDisponiveis);
        JScrollPane scrollClientes = new JScrollPane(tabelaClientes);

        scrollServicos.setBorder(BorderFactory.createTitledBorder("Serviços"));
        scrollClientes.setBorder(BorderFactory.createTitledBorder("Clientes"));

        // Tabela de itens que estara no orçamento

        tableItensOrcamento = new JTable(modelItensOrcamento);
        JScrollPane scrollItens = new JScrollPane(tableItensOrcamento);
        scrollItens.setBorder(BorderFactory.createTitledBorder("Itens para o Orçamento"));

        JSplitPane spTabelas = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollServicos, scrollClientes);

        JPanel panelBotoesAccoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnSelecionarCliente = new JButton("Vincular Cliente");
        btnSelecionarCliente.addActionListener(e -> selecionarClienteParaOrcamento());

        JButton btnAdicionar = new JButton("Adicionar Item ao Orçamento");
        btnAdicionar.addActionListener(e -> adicionarItemAoOrcamento());

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

    private void adicionarItemAoOrcamento() {
        if (orcamentoAtualId == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente primeiro", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedRow = tableItensDisponiveis.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um item");
            return;
        }

        String tipo = (String) tableItensDisponiveis.getValueAt(selectedRow, 0);
        String descricao = (String) tableItensDisponiveis.getValueAt(selectedRow, 1);
        double valor = (Double) tableItensDisponiveis.getValueAt(selectedRow, 2);

        JSpinner spinnerQuantidade = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JTextField txtObservacoes = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Tipo:"));
        panel.add(new JLabel(tipo));
        panel.add(new JLabel("Quantidade:"));
        panel.add(spinnerQuantidade);
        panel.add(new JLabel("Observações:"));
        panel.add(txtObservacoes);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Adicionar Item",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int quantidade = (Integer) spinnerQuantidade.getValue();
            String observacoes = txtObservacoes.getText();

            if (tipo.equals("Serviço")) {
                Servico servico = servicoController.listarTodosServicos().stream()
                        .filter(s -> s.getDescricao().equals(descricao) && s.getValorUnitario() == valor)
                        .findFirst()
                        .orElse(null);

                if (servico != null) {
                    orcamentoController.adicionarItemServico(orcamentoAtualId, servico.getId(), quantidade, observacoes);
                } else {
                    JOptionPane.showMessageDialog(this, "Serviço não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } else if (tipo.equals("Material")) {
                Material material = materialController.listarTodosMateriais().stream()
                        .filter(m -> m.getDescricao().equals(descricao) && m.getPrecoUnitario() == valor)
                        .findFirst()
                        .orElse(null);

                if (material != null) {
                    orcamentoController.adicionarItemMaterial(orcamentoAtualId, material.getId(), quantidade, observacoes);
                } else {
                    JOptionPane.showMessageDialog(this, "Material não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

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

                if (item != null && item.getMaterial() != null) {
                    model.addRow(new Object[]{
                            item.getMaterial().getDescricao(),
                            item.getQuantidade(),
                            String.format("%,.2f", item.getMaterial().getPrecoUnitario()),
                            String.format("%,.2f", item.getMaterial().getPrecoUnitario() * item.getQuantidade())
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
                modelItensOrcamento.setRowCount(0);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Inicia uma operacao antes de finalizar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void atualizarTabelaAuxiliares() {
        DefaultTableModel model = (DefaultTableModel) tableItensDisponiveis.getModel();
        model.setRowCount(0);

        carregarServicos(model);
        carregarMateriais(model);
        tabelaClientesModel.atualizarDados(clienteController.listarTodosClientes());

    }

    private void carregarServicos(DefaultTableModel model) {
        servicoController.listarTodosServicos().forEach(servico ->
                model.addRow(new Object[]{
                        "Serviço",
                        servico.getDescricao(),
                        servico.getValorUnitario()
                })
        );
    }

    private void carregarMateriais(DefaultTableModel model) {
        materialController.listarTodosMateriais().forEach(material ->
                model.addRow(new Object[]{
                        "Material",
                        material.getDescricao(),
                        material.getPrecoUnitario()
                })
        );
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