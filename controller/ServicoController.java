package controller;

import model.Servico;

import java.util.ArrayList;
import java.util.List;

public class ServicoController {
    private List<Servico> servicos;
    private static int lastId = 0;

    public ServicoController() {
        this.servicos = new ArrayList<>();
    }

    public void cadastrarServico(Servico novoServico) {
        validarServico(novoServico);
        lastId += 1;
        novoServico.setId(lastId);
        this.servicos.add(novoServico);
        lastId = novoServico.getId();
    }


    public List<Servico> listarTodosServicos() {
        return new ArrayList<>(servicos);
    }

    public void validarServico(Servico servico) {
        if (servico == null)
            throw new IllegalArgumentException("Servico não pode ser nulo");
        else if (servico.getDescricao() == null || servico.getValorUnitario() < 0)
            throw new IllegalArgumentException("Verifique os dados");
    }

    public Servico buscarServico(int id) {
        return servicos.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void atualizarServico(Servico servicoAtualizado) {
        validarServico(servicoAtualizado);
        Servico servicoExistente = buscarServico(servicoAtualizado.getId());
        if (servicoExistente == null) {
            throw new IllegalArgumentException("Servico não encontrado para atualização");
        }

        servicoExistente.setDescricao(servicoAtualizado.getDescricao());
        servicoExistente.setValorUnitario(servicoAtualizado.getValorUnitario());

    }

    public void removerServico(int id) {
        Servico servico = buscarServico(id);
        if (servico != null) {
            servicos.remove(servico);
            System.out.println("Serviço removido com sucesso!");
            System.out.printf("Id eliminado %d\n - ID: %d ", id, servico.getId());
        }

    }
}
