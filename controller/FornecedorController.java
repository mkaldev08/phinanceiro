
package controller;

import model.Fornecedor;

import java.util.ArrayList;
import java.util.List;

public class FornecedorController {
    private List<Fornecedor> fornecedores;
    private static int lastId = 0;

    public FornecedorController() {
        this.fornecedores = new ArrayList<>();
    }

    public void cadastrarFornecedor(Fornecedor novoFornecedor) {
        validarFornecedor(novoFornecedor);
        lastId += 1;
        novoFornecedor.setId(lastId);
        this.fornecedores.add(novoFornecedor);
        lastId = novoFornecedor.getId();
    }

    public List<Fornecedor> listarTodosFornecedores() {
        return new ArrayList<>(fornecedores);
    }

    public void removerFornecedor(int id) {
        Fornecedor fornecedor = buscarFornecedor(id);
        if (fornecedor != null) {
            fornecedores.remove(fornecedor);
            System.out.println("Fornecedor removido com sucesso!");
        }
    }

    public Fornecedor buscarFornecedor(int id) {
        return fornecedores.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void atualizarFornecedor(Fornecedor fornecedorAtualizado) {
        validarFornecedor(fornecedorAtualizado);
        System.out.println("Print: " + fornecedorAtualizado.getId());
        Fornecedor fornecedorExistente = buscarFornecedor(fornecedorAtualizado.getId());
        if (fornecedorExistente == null) {
            throw new IllegalArgumentException("Fornecedor não encontrado para atualização");
        }

        fornecedorExistente.setNome(fornecedorAtualizado.getNome());
        fornecedorExistente.setTelefone(fornecedorAtualizado.getTelefone());
        fornecedorExistente.setEmail(fornecedorAtualizado.getEmail());
        fornecedorExistente.setEndereco(fornecedorAtualizado.getEndereco());
        fornecedorExistente.setProdutoFornecido(fornecedorAtualizado.getProdutoFornecido());
    }

    private void validarFornecedor(Fornecedor fornecedor) {
        if (fornecedor == null) {
            throw new IllegalArgumentException("Fornecedor não pode ser nulo");
        }

        if (fornecedor.getNome() == null || fornecedor.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do fornecedor é obrigatório");
        }

        if (fornecedor.getTelefone() == null || fornecedor.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do fornecedor é obrigatório");
        }
        if (fornecedor.getProdutoFornecido().trim().isEmpty() || fornecedor.getProdutoFornecido() == null) {
            throw new IllegalArgumentException("O Produto fornecido obrigatório");
        }
    }
}