package view;

import controller.FornecedorController;
import model.Fornecedor;
import view.util.FornecedorTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class PanelFornecedores extends JPanel {
    private final FornecedorController controller;
    private final FornecedorTableModel tableModel;
    private final JTable tabelaFornecedores;

    public PanelFornecedores(FornecedorController controller) {
        this.controller = controller;
        tableModel = new FornecedorTableModel(controller.listarTodosFornecedores());

        this.tabelaFornecedores = new JTable(tableModel);

        initComponents();
        atualizarTabela();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelBotoesAccoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnAdicionar = new JButton("Adicionar Fornecedor");
        btnAdicionar.addActionListener(this::adicionarFornecedor);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(this::editarFornecedor);

        JButton btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this::removerFornecedor);

        panelBotoesAccoes.add(btnAdicionar);
        panelBotoesAccoes.add(btnEditar);
        panelBotoesAccoes.add(btnRemover);

        // Tabela para listar fornecedors
        tabelaFornecedores.setFillsViewportHeight(true);
        tabelaFornecedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaFornecedores.setBounds(30, 40, 200, 300);

        add(panelBotoesAccoes, BorderLayout.NORTH);
        add(new JScrollPane(tabelaFornecedores), BorderLayout.CENTER);
    }

    private void adicionarFornecedor(ActionEvent eventClick) {

        JPanel panel = new JPanel(new GridLayout(7, 2));

        JTextField txtNome = new JTextField();
        JTextField txtSobreNome = new JTextField();
        JTextField txtEndereco = new JTextField();
        JTextField txtIdentidade = new JTextField();
        JTextField txtTelefone = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtProduto = new JTextField();

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
        panel.add(new JLabel("Produto Fornecido:"));
        panel.add(txtProduto);

        int result = JOptionPane.showConfirmDialog(null, panel, "Novo Fornecedor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Fornecedor novoFornecedor = new Fornecedor(
                        txtProduto.getText(), txtNome.getText(),
                        txtSobreNome.getText(),
                        txtTelefone.getText(), txtEmail.getText(),
                        txtEndereco.getText(), txtIdentidade.getText());

                controller.cadastrarFornecedor(novoFornecedor);
                atualizarTabela();
            } catch (IllegalArgumentException erro) {
                JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void atualizarTabela() {
        tableModel.atualizarDados(controller.listarTodosFornecedores());
    }

    private void removerFornecedor(ActionEvent eventClick) {

        int selectedRow = tabelaFornecedores.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null,
                    "Selecione um fornecedor para remover",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Fornecedor fornecedor = controller.listarTodosFornecedores().get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(
                null, "Tem certeza que deseja remover este fornecedor?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.removerFornecedor(fornecedor.getId());
            atualizarTabela();
        }
    }

    private void editarFornecedor(ActionEvent evt) {
        int selectedRow = tabelaFornecedores.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null,
                    "Selecione um fornecedor para editar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Fornecedor fornecedor = controller.listarTodosFornecedores().get(selectedRow);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        JTextField txtNome = new JTextField(fornecedor.getNome());
        JTextField txtSobreNome = new JTextField(fornecedor.getSobreNome());
        JTextField txtEndereco = new JTextField(fornecedor.getEndereco());
        JTextField txtIdentidade = new JTextField(fornecedor.getBilheteIdentidade());
        JTextField txtTelefone = new JTextField(fornecedor.getTelefone());
        JTextField txtEmail = new JTextField(fornecedor.getEmail());
        JTextField txtProduto = new JTextField(fornecedor.getProdutoFornecido());

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
        panel.add(new JLabel("Produto Fornecido:"));
        panel.add(txtProduto);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Editar Fornecedor",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
              fornecedor.setSobreNome(txtSobreNome.getText());
                fornecedor.setTelefone(txtTelefone.getText());
                fornecedor.setEmail(txtEmail.getText());
                fornecedor.setEndereco(txtEndereco.getText());
                fornecedor.setProdutoFornecido(txtProduto.getText());
                fornecedor.setBilheteIdentidade(txtIdentidade.getText());

                controller.atualizarFornecedor(fornecedor);
                atualizarTabela();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}