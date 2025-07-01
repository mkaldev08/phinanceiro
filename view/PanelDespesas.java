package view;

import controller.DespesaController;
import controller.FornecedorController;
import model.Despesa;
import model.Fornecedor;
import view.util.DespesaTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class PanelDespesas extends JPanel {
    private final DespesaController despesaController;
    private final FornecedorController fornecedorController;
    private final DespesaTableModel tableModel;
    private final JTable tabelaDespesas;

    public PanelDespesas(DespesaController despesaController,
                         FornecedorController fornecedorController) {
        this.despesaController = despesaController;
        this.fornecedorController = fornecedorController;
        this.tableModel = new DespesaTableModel(despesaController.listarTodasDespesas());
        this.tabelaDespesas = new JTable(tableModel);

        initComponents();
        atualizarTabela();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelBotoesAccoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnAdicionar = new JButton("Adicionar Despesa");
        btnAdicionar.addActionListener(this::adicionarDespesa);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(this::editarDespesa);

        JButton btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this::removerDespesa);

        panelBotoesAccoes.add(btnAdicionar);
        panelBotoesAccoes.add(btnEditar);
        panelBotoesAccoes.add(btnRemover);

        tabelaDespesas.setFillsViewportHeight(true);
        tabelaDespesas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(panelBotoesAccoes, BorderLayout.NORTH);
        add(new JScrollPane(tabelaDespesas), BorderLayout.CENTER);
    }

    private void adicionarDespesa(ActionEvent eventClick) {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JTextField txtDescricao = new JTextField();
        JTextField txtValor = new JFormattedTextField();
        JComboBox<Fornecedor> comboFornecedor = new JComboBox<>(
                fornecedorController.listarTodosFornecedores().toArray(new Fornecedor[0]));
        JComboBox<Despesa.CATEGORIA> comboCategoria = new JComboBox<>(
                Despesa.CATEGORIA.values());


        comboFornecedor.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Fornecedor) {
                    Fornecedor f = (Fornecedor) value;
                    setText(f.getNome() + " - " + f.getProdutoFornecido());
                }
                return this;
            }
        });

        panel.add(new JLabel("Descrição:"));
        panel.add(txtDescricao);
        panel.add(new JLabel("Valor:"));
        panel.add(txtValor);
        panel.add(new JLabel("Fornecedor:"));
        panel.add(comboFornecedor);
        panel.add(new JLabel("Categoria:"));
        panel.add(comboCategoria);

        int result = JOptionPane.showConfirmDialog(null, panel, "Nova Despesa",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double valor = Double.parseDouble(txtValor.getText());
                Fornecedor fornecedor = (Fornecedor) comboFornecedor.getSelectedItem();
                Despesa.CATEGORIA categoria =
                        (Despesa.CATEGORIA) comboCategoria.getSelectedItem();

                despesaController.registrarDespesa(valor, txtDescricao.getText(), fornecedor, categoria);
                atualizarTabela();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor inválido", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void atualizarTabela() {
        tableModel.atualizarDados(despesaController.listarTodasDespesas());
    }

    private void removerDespesa(ActionEvent eventClick) {
        int selectedRow = tabelaDespesas.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null,
                    "Selecione uma despesa para remover",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Despesa despesa = despesaController.listarTodasDespesas().get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(
                null, "Tem certeza que deseja remover esta despesa?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            despesaController.removerDespesa(despesa.getId());
            atualizarTabela();
        }
    }

    private void editarDespesa(ActionEvent evt) {
        int selectedRow = tabelaDespesas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null,
                    "Selecione uma despesa para editar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Despesa despesa = despesaController.listarTodasDespesas().get(selectedRow);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        JTextField txtDescricao = new JTextField(despesa.getDescricao());
        JTextField txtValor = new JTextField(String.valueOf(despesa.getValor()));
        JComboBox<Fornecedor> comboFornecedor = new JComboBox<>(
                fornecedorController.listarTodosFornecedores().toArray(new Fornecedor[0]));
        JComboBox<Despesa.CATEGORIA> comboCategoria = new JComboBox<>(
                Despesa.CATEGORIA.values());


        // Converter LocalDate para Date
        Date data = java.sql.Date.valueOf(despesa.getData());

// Criar o Spinner com modelo de data
        JSpinner spinnerData = new JSpinner(new SpinnerDateModel(data, null, null, Calendar.DAY_OF_MONTH));

// Configurar o formato de exibição
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy");
        spinnerData.setEditor(dateEditor);

        // Configurar seleção atual
        comboFornecedor.setSelectedItem(despesa.getFornecedor());
        comboCategoria.setSelectedItem(despesa.getCategoria());

        // Configurações adicionais (igual no adicionar)
        comboFornecedor.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Fornecedor) {
                    Fornecedor f = (Fornecedor) value;
                    setText(f.getNome() + " - " + f.getProdutoFornecido());
                }
                return this;
            }
        });


        panel.add(new JLabel("Descrição:"));
        panel.add(txtDescricao);
        panel.add(new JLabel("Valor:"));
        panel.add(txtValor);
        panel.add(new JLabel("Fornecedor:"));
        panel.add(comboFornecedor);
        panel.add(new JLabel("Categoria:"));
        panel.add(comboCategoria);
        panel.add(new JLabel("Data:"));
        panel.add(spinnerData);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Editar Despesa",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double valor = Double.parseDouble(txtValor.getText());
                Fornecedor fornecedor = (Fornecedor) comboFornecedor.getSelectedItem();
                Despesa.CATEGORIA categoria =
                        (Despesa.CATEGORIA) comboCategoria.getSelectedItem();

                despesa.setDescricao(txtDescricao.getText());
                despesa.setValor(valor);
                despesa.setFornecedor(fornecedor);
                despesa.setCategoria(categoria);

                LocalDate novaData = LocalDate.parse(data.toString());
                despesa.setData(novaData);

                despesaController.atualizarDespesa(despesa);
                atualizarTabela();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}