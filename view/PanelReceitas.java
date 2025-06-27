package view;

import controller.ReceitaController;
import model.Receita;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelReceitas extends JPanel {
    private final ReceitaController controller;
    private final ReceitaTableModel tableModel;
    private final JTable tabelaReceitas;

    public PanelReceitas(ReceitaController controller) {
        this.controller = controller;
        this.tableModel = new ReceitaTableModel();
        this.tabelaReceitas = new JTable(tableModel);

        initComponents();
        carregarDados();
    }

    private void initComponents() {
        setLayout(new BorderLayout());


        JToolBar toolBar = new JToolBar();

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> carregarDados());

        JButton btnMarcarRecebido = new JButton("Marcar como Recebido");
        btnMarcarRecebido.addActionListener(e -> marcarComoRecebido());

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> mostrarDialogoFiltro());

        toolBar.add(btnAtualizar);
        toolBar.add(btnMarcarRecebido);
        toolBar.add(btnFiltrar);


        tabelaReceitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaReceitas.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(tabelaReceitas);

        add(toolBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void carregarDados() {
        tableModel.atualizarDados(controller.listarTodasReceitas());
    }

    private void marcarComoRecebido() {
        int selectedRow = tabelaReceitas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma receita para marcar como recebida",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = tabelaReceitas.convertRowIndexToModel(selectedRow);
        Receita receita = tableModel.getReceitaAt(modelRow);

        if (receita.isRecebido()) {
            JOptionPane.showMessageDialog(this,
                    "Esta receita já foi marcada como recebida",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Confirmar recebimento de " + receita.getValor() + "?\n" +
                        "Cliente: " + receita.getCliente().getNome(),
                "Confirmar Recebimento",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.registrarRecebimento(receita.getId());
            carregarDados();
        }
    }

    private void mostrarDialogoFiltro() {
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Filtrar Receitas", true);
        dialog.setLayout(new GridLayout(0, 2, 10, 10));

        // Componentes do filtro
        JCheckBox checkRecebidos = new JCheckBox("Mostrar apenas recebidos");
        JCheckBox checkPendentes = new JCheckBox("Mostrar apenas pendentes");

        JButton btnAplicar = new JButton("Aplicar Filtros");
        btnAplicar.addActionListener(e -> {
            boolean apenasRecebidos = checkRecebidos.isSelected();
            boolean apenasPendentes = checkPendentes.isSelected();

            List<Receita> receitasFiltradas;

            if (apenasRecebidos && !apenasPendentes) {
                receitasFiltradas = controller.listarReceitasRecebidas();
            } else if (apenasPendentes && !apenasRecebidos) {
                receitasFiltradas = controller.listarReceitasPendentes();
            } else {
                receitasFiltradas = controller.listarTodasReceitas();
            }

            tableModel.atualizarDados(receitasFiltradas);
            dialog.dispose();
        });

        dialog.add(new JLabel("Filtros:"));
        dialog.add(new JLabel(""));
        dialog.add(checkRecebidos);
        dialog.add(checkPendentes);
        dialog.add(new JLabel(""));
        dialog.add(btnAplicar);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

}