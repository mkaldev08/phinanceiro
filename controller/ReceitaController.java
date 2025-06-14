package controller;

public class ReceitaController {

    private final OrcamentoController orcamentoController;
    private final ClienteController clienteController;
    public ReceitaController(OrcamentoController oc, ClienteController cl) {
        this.orcamentoController = oc;
        this.clienteController = cl;
    }

    public void receberPagamento(){

    }
}
