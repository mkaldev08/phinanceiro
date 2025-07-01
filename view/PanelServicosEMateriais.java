package view;

import controller.MaterialController;
import controller.ServicoController;
import model.Material;
import model.Servico;
import view.util.MaterialTableModel;
import view.util.ServicoTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelServicosEMateriais extends JPanel {
    private final ServicoController controller;
    private final MaterialController materialController;
    private final ServicoTableModel tableModel;
    private final MaterialTableModel materialTableModel;
    private final JTable tabelaServicos;
    private final JTable tabelaMateriais;

    public PanelServicosEMateriais(ServicoController controller, MaterialController materialController) {
        this.controller = controller;
        this.materialController = materialController;
        this.tableModel = new ServicoTableModel(controller.listarTodosServicos());
        this.materialTableModel = new MaterialTableModel(materialController.listarTodosMateriais());
        this.tabelaServicos = new JTable(tableModel);
        this.tabelaMateriais = new JTable(materialTableModel);

        initComponents();
        atualizarTabela();
        atualizarMateriais();
    }

    public void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelServicos = new JPanel(new BorderLayout());

        JPanel panelBotoesServicos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdicionarServico = new JButton("Adicionar Serviço");
        JButton btnEditarServico = new JButton("Editar");
        JButton btnRemoverServico = new JButton("Remover");

        panelBotoesServicos.add(btnAdicionarServico);
        panelBotoesServicos.add(btnEditarServico);
        panelBotoesServicos.add(btnRemoverServico);

        panelServicos.add(panelBotoesServicos, BorderLayout.NORTH);
        panelServicos.add(new JScrollPane(tabelaServicos), BorderLayout.CENTER);
        panelServicos.setBorder(BorderFactory.createTitledBorder("Serviços"));

        JPanel panelMateriais = new JPanel(new BorderLayout());

        JPanel panelBotoesMateriais = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdicionarMaterial = new JButton("Adicionar Material");
        JButton btnEditarMaterial = new JButton("Editar");
        JButton btnRemoverMaterial = new JButton("Remover");

        panelBotoesMateriais.add(btnAdicionarMaterial);
        panelBotoesMateriais.add(btnEditarMaterial);
        panelBotoesMateriais.add(btnRemoverMaterial);

        panelMateriais.add(panelBotoesMateriais, BorderLayout.NORTH);
        panelMateriais.add(new JScrollPane(tabelaMateriais), BorderLayout.CENTER);
        panelMateriais.setBorder(BorderFactory.createTitledBorder("Materiais"));

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                panelServicos,
                panelMateriais);

        splitPane.setDividerLocation(0.5);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        add(splitPane, BorderLayout.CENTER);

        btnAdicionarServico.addActionListener(this::adicionarServico);
        btnEditarServico.addActionListener(this::editarServico);
        btnRemoverServico.addActionListener(this::removerServico);

        btnAdicionarMaterial.addActionListener(this::adicionarMaterial);
        btnEditarMaterial.addActionListener(this::editarMaterial);
        btnRemoverMaterial.addActionListener(this::removerMaterial);
    }

    private void atualizarTabela() {
        tableModel.atualizarDados(controller.listarTodosServicos());
    }

    public void atualizarMateriais() {
        materialTableModel.atualizarDados(materialController.listarTodosMateriais());
    }

    private void adicionarServico(ActionEvent eventClick) {

        JPanel panel = new JPanel(new GridLayout(2, 2));

        JTextField txtDescricao = new JTextField();
        JTextField txtValorUnitario = new JTextField();

        panel.add(new JLabel("Descrição:"));
        panel.add(txtDescricao);
        panel.add(new JLabel("Valor (kz):"));
        panel.add(txtValorUnitario);

        int result = JOptionPane.showConfirmDialog(null, panel, "Novo Serviço", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Servico novoServico = new Servico(txtDescricao.getText(),
                        Double.parseDouble(txtValorUnitario.getText()));

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
            JOptionPane.showMessageDialog(null, "Selecione um cliente para editar", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
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

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                servico.setDescricao(txtDescricao.getText());

                servico.setValorUnitario(Double.parseDouble(txtValorUnitario.getText()));

                controller.atualizarServico(servico);
                atualizarTabela();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerServico(ActionEvent eventClick) {
        int selectedRow = tabelaServicos.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um servço para remover", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Servico servico = controller.listarTodosServicos().get(selectedRow);

        int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este serviço?", "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.removerServico(servico.getId());
            atualizarTabela();
        }
    }

    private void adicionarMaterial(ActionEvent eventClick) {

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField txtDescricao = new JTextField();
        JTextField txtValorUnitario = new JTextField();
        JTextField txtUnidadeMedida = new JTextField();

        panel.add(new JLabel("Descrição:"));
        panel.add(txtDescricao);
        panel.add(new JLabel("Valor (kz):"));
        panel.add(txtValorUnitario);
        panel.add(new JLabel("Unidade de Medida:"));
        panel.add(txtUnidadeMedida);

        int result = JOptionPane.showConfirmDialog(null, panel, "Novo Material", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Material novoMaterial = new Material(txtDescricao.getText(),
                        txtUnidadeMedida.getText(),
                        Double.parseDouble(txtValorUnitario.getText()));

                materialController.cadastrarMaterial(novoMaterial);
                atualizarMateriais();
            } catch (IllegalArgumentException erro) {
                JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarMaterial(ActionEvent evt) {
        int selectedRow = tabelaMateriais.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um Material para editar", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Material material = materialController.listarTodosMateriais().get(selectedRow);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField txtDescricao = new JTextField(material.getDescricao());
        JTextField txtValorUnitario = new JTextField(Double.toString(material.getPrecoUnitario()));
        JTextField txtUnidadeMedida = new JTextField(material.getUnidadeMedida());

        panel.add(new JLabel("Descrição:"));
        panel.add(txtDescricao);
        panel.add(new JLabel("Valor (kz):"));
        panel.add(txtValorUnitario);
        panel.add(new JLabel("Unidade Medida:"));
        panel.add(txtUnidadeMedida);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Material", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                material.setDescricao(txtDescricao.getText());
                material.setUnidadeMedida(txtUnidadeMedida.getText());
                material.setPrecoUnitario(Double.parseDouble(txtValorUnitario.getText()));

                materialController.atualizarMaterial(material);
                atualizarMateriais();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerMaterial(ActionEvent eventClick) {
        int selectedRow = tabelaMateriais.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um Material para remover", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Material material = materialController.listarTodosMateriais().get(selectedRow);

        int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este Material?", "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            materialController.removerMaterial(material.getId());
            atualizarMateriais();
        }
    }
}
