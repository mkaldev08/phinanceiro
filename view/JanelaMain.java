package view;

import controller.ClienteController;
import controller.OrcamentoController;
import controller.ServicoController;

import javax.swing.*;

public class JanelaMain extends JFrame {

    private final ServicoController servicoController;
    private final ClienteController clienteController;
    private final OrcamentoController orcamentoController;

    public JanelaMain() {
        // Inicializa os controllers
        this.servicoController = new ServicoController();
        this.clienteController = new ClienteController();
        this.orcamentoController = new OrcamentoController(servicoController);
        setTitle("Sistema de Gestão Phinanceira - Gráfica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Clientes", new PanelClientes(clienteController));
        tabbedPane.addTab("Serviços", new PanelServicos(servicoController));
        tabbedPane.addTab("Orçamentos", new PanelOrcamentos(servicoController, orcamentoController));

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JanelaMain janela = new JanelaMain();
            janela.setVisible(true);
        });
    }
}