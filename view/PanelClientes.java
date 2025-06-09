package view;

import controller.ClienteController;
import model.Cliente;
import model.Servico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;


public class PanelClientes extends JPanel {
    private final ClienteController controller;
    private final DefaultTableModel tableModel;
    private final JTable tabelaClientes;

    public PanelClientes(ClienteController controller) {
        this.controller = controller;
        this.tableModel = new DefaultTableModel(new String[]{"Nome", "Sobrenome", "Identidade", "Telefone", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável diretamente
            }
        };

        this.tabelaClientes = new JTable(tableModel);
        initComponents();
        atualizarTabela();
    }


    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelBotoesAccoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnAdicionar = new JButton("Adicionar Cliente");
        btnAdicionar.addActionListener(this::adicionarCliente);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(this::editarCliente);

        JButton btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this::removerCliente);

        panelBotoesAccoes.add(btnAdicionar);
        panelBotoesAccoes.add(btnEditar);
        panelBotoesAccoes.add(btnRemover);

        // Tabela para listar clientes
        tabelaClientes.setFillsViewportHeight(true);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaClientes.setBounds(30, 40, 200, 300);

        add(panelBotoesAccoes, BorderLayout.NORTH);
        add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);
    }

    private void adicionarCliente(ActionEvent eventClick) {

        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField txtNome = new JTextField();
        JTextField txtSobreNome = new JTextField();
        JTextField txtEndereco = new JTextField();
        JTextField txtIdentidade = new JTextField();
        JTextField txtTelefone = new JTextField();
        JTextField txtEmail = new JTextField();

        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Sobrenome:"));
        panel.add(txtSobreNome);
        panel.add(new JLabel("Identidade:"));
        panel.add(txtIdentidade);
        panel.add(new JLabel("Telefone:"));
        panel.add(txtTelefone);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Endereço:"));
        panel.add(txtEndereco);

        int result = JOptionPane.showConfirmDialog(null, panel, "Novo Cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Cliente novoCliente = new Cliente(txtNome.getText(), txtSobreNome.getText(), txtTelefone.getText(), txtEmail.getText(), txtEndereco.getText(), txtIdentidade.getText());

                controller.cadastrarCliente(novoCliente);
                atualizarTabela();
            } catch (IllegalArgumentException erro) {
                JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa a tabela antes de reconstrui-la
        controller.listarTodosClientes().forEach(cliente -> {
            tableModel.addRow(new Object[]{
                    cliente.getNome(),
                    cliente.getSobreNome(),
                    cliente.getBilheteIdentidade(),
                    cliente.getTelefone(),
                    cliente.getEmail()
            });
        });
    }

    private void removerCliente(ActionEvent eventClick) {

        int selectedRow = tabelaClientes.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null,
                    "Selecione um cliente para remover",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = controller.listarTodosClientes().get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(
                null, "Tem certeza que deseja remover este cliente?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.removerCliente(cliente.getId());
            atualizarTabela();
        }
    }

    private void editarCliente(ActionEvent evt) {
        int selectedRow = tabelaClientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null,
                    "Selecione um cliente para editar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = controller.listarTodosClientes().get(selectedRow);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField txtNome = new JTextField(cliente.getNome());
        JTextField txtSobreNome = new JTextField(cliente.getSobreNome());
        JTextField txtEndereco = new JTextField(cliente.getEndereco());
        JTextField txtIdentidade = new JTextField(cliente.getBilheteIdentidade());
        JTextField txtTelefone = new JTextField(cliente.getTelefone());
        JTextField txtEmail = new JTextField(cliente.getEmail());

        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Sobrenome:"));
        panel.add(txtSobreNome);
        panel.add(new JLabel("Identidade:"));
        panel.add(txtIdentidade);
        panel.add(new JLabel("Telefone:"));
        panel.add(txtTelefone);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Endereço:"));
        panel.add(txtEndereco);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Editar Cliente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Cliente clienteAtualizado = new Cliente(txtNome.getText(),
                        txtSobreNome.getText(),
                        txtTelefone.getText(), txtEmail.getText(),
                        txtEndereco.getText(), txtIdentidade.getText());

                controller.atualizarCliente(clienteAtualizado);
                atualizarTabela();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}