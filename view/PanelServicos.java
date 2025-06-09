package view;

import controller.ServicoController;
import model.Cliente;
import model.Servico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelServicos extends JPanel {
    private final ServicoController controller;
    private final DefaultTableModel tableModel;
    private final JTable tabelaServicos;

    public PanelServicos(ServicoController controller) {
        this.controller = controller;
        this.tableModel = new DefaultTableModel(new String[]{"ID", "Descrição", "Valor Unitário (Kz)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável diretamente
            }
        };

        this.tabelaServicos = new JTable(tableModel);
        initComponents();
        atualizarTabela();
    }

    public void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelBotoesAccoes = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnAdicionar = new JButton("Adicionar Serviço");
        btnAdicionar.addActionListener(this::adicionarServico);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(this::editarServico);

        JButton btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this::removerServico);

        panelBotoesAccoes.add(btnAdicionar);
        panelBotoesAccoes.add(btnEditar);
        panelBotoesAccoes.add(btnRemover);

        tabelaServicos.setFillsViewportHeight(true);
        tabelaServicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaServicos.setBounds(30, 40, 200, 300);

        add(panelBotoesAccoes, BorderLayout.NORTH);
        add(new JScrollPane(tabelaServicos), BorderLayout.CENTER);
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa a tabela antes de reconstrui-la
        controller.listarTodosServicos().forEach(servico -> {
            tableModel.addRow(new Object[]{
                    servico.getId(),
                    servico.getDescricao(),
                    servico.getValorUnitario()
            });
        });
    }


    private void adicionarServico(ActionEvent eventClick) {

        JPanel panel = new JPanel(new GridLayout(2, 2));

        JTextField txtDescricao = new JTextField();
        JTextField txtValorUnitario = new JTextField();

        panel.add(new JLabel("Descrição:"));
        panel.add(txtDescricao);
        panel.add(new JLabel("Valor (kz):"));
        panel.add(txtValorUnitario);


        int result = JOptionPane.showConfirmDialog(null, panel, "Novo Serviço", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Servico novoServico = new Servico(txtDescricao.getText(), Double.parseDouble(txtValorUnitario.getText()));

                controller.cadastrarServico(novoServico);
                atualizarTabela();
            } catch (IllegalArgumentException erro) {
                JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarServico(ActionEvent evt) {
        int selectedRow = tabelaServicos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null,
                    "Selecione um cliente para editar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Servico servico = controller.listarTodosServicos().get(selectedRow);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        JTextField txtDescricao = new JTextField(servico.getDescricao());
        JTextField txtValorUnitario = new JTextField(Double.toString(servico.getValorUnitario()));

        panel.add(new JLabel("Descrição:"));
        panel.add(txtDescricao);
        panel.add(new JLabel("Valor (kz):"));
        panel.add(txtValorUnitario);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Editar Cliente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Servico servAtualizado = new Servico(txtDescricao.getText(),
                        Double.parseDouble(txtValorUnitario.getText()));

                controller.atualizarServico(servAtualizado);
                atualizarTabela();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerServico(ActionEvent eventClick) {
        int selectedRow = tabelaServicos.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null,
                    "Selecione um servço para remover",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null, "Tem certeza que deseja remover este serviço?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.removerServico(selectedRow);
            atualizarTabela();
        }
    }
}
