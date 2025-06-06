package view;

import javax.swing.*;

public class JanelaMain extends JFrame {
    public JanelaMain() {
        setTitle("Sistema de Gestão Phinanceira - Gráfica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Clientes", new PanelClientes());
        tabbedPane.addTab("Serviços", new PanelServicos());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JanelaMain janela = new JanelaMain();
            janela.setVisible(true);
        });
    }
}