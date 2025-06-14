package view;

import model.Cliente;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClienteTableModel extends AbstractTableModel {
    private List<Cliente> clientes;
    private final String[] colunas = {"Nome", "Sobrenome", "Identidade", "Telefone", "Email"};

    public ClienteTableModel(List<Cliente> clientes) {
        this.clientes = new ArrayList<>(clientes);
    }

    @Override
    public int getRowCount() {
        return clientes == null ? 0 : clientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = clientes.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> cliente.getNome();
            case 1 -> cliente.getSobreNome();
            case 2 -> cliente.getBilheteIdentidade();
            case 3 -> cliente.getTelefone();
            case 4 -> cliente.getEmail();
            default -> null;
        };
    }

    public String getColumnName(int column) {
        return colunas[column];
    }

    public Cliente getClienteAt(int rowIndex) {
        return clientes.get(rowIndex);
    }

    public void atualizarDados(List<Cliente> novoCliente) {
        this.clientes = new ArrayList<>(novoCliente);
        fireTableDataChanged();
    }
}
