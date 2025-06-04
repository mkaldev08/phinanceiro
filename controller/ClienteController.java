
package controller;

import model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteController {
    private List<Cliente> clientes;
    private static int lastId = 0;

    public ClienteController() {
        this.clientes = new ArrayList<>();
    }

    public void cadastrarCliente(Cliente novoCliente) {
        validarCliente(novoCliente);
        novoCliente.setId(lastId++);
        this.clientes.add(novoCliente);
        lastId = novoCliente.getId();
    }

    public List<Cliente> listarTodosClientes() {
        return new ArrayList<>(clientes);
    }

    public void removerCliente(int id) {
        Cliente cliente = buscarCliente(id);
        if (cliente != null) {
            clientes.remove(cliente);
            System.out.println("Cliente removido com sucesso!");
        }
    }

    public Cliente buscarCliente(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void atualizarCliente(Cliente clienteAtualizado) {
        validarCliente(clienteAtualizado);
        Cliente clienteExistente = buscarCliente(clienteAtualizado.getId());
        if (clienteExistente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para atualização");
        }

        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setEndereco(clienteAtualizado.getEndereco());
    }

    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do cliente é obrigatório");
        }
    }
}