package view;

import controller.*;

import javax.swing.*;

public class JanelaMain extends JFrame {

    private final ServicoController servicoController;
    private final ClienteController clienteController;
    private final OrcamentoController orcamentoController;
    private final ReceitaController receitaController;
    private final MaterialController materialController;
    private final FornecedorController fornecedorController;
    private final DespesaController despesaController;

    public JanelaMain() {
        // Inicializa os controllers
        this.servicoController = new ServicoController();
        this.clienteController = new ClienteController();
        this.receitaController = new ReceitaController();
        this.materialController = new MaterialController();
        this.fornecedorController = new FornecedorController();
        this.despesaController = new DespesaController();
        this.orcamentoController = new OrcamentoController(servicoController, materialController, receitaController);
        
        setTitle("Sistema de Gestão Phinanceira - Gráfica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Fornecedores", new PanelFornecedores(fornecedorController));
        tabbedPane.addTab("Clientes", new PanelClientes(clienteController));
        tabbedPane.addTab("Materiais e Serviços", new PanelServicosEMateriais(servicoController, materialController));
        tabbedPane.addTab("Orçamentos", new PanelOrcamentos(servicoController, orcamentoController, clienteController, materialController));
        tabbedPane.addTab("Receitas", new PanelReceitas(receitaController));
        tabbedPane.addTab("Despesas", new PanelDespesas(despesaController, fornecedorController));
        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JanelaMain janela = new JanelaMain();
            janela.setVisible(true);
        });
    }
}