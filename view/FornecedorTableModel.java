package view;

import model.Fornecedor;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class FornecedorTableModel extends AbstractTableModel {
    private List<Fornecedor> fornecedores;
    private final String[] colunas = {"Nome", "Sobrenome", "Identidade", "Telefone", "Email", "Produto Fornecido"};

    public FornecedorTableModel(List<Fornecedor> fornecedores) {
        this.fornecedores = new ArrayList<>(fornecedores);
    }

    @Override
    public int getRowCount() {
        return fornecedores == null ? 0 : fornecedores.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Fornecedor fornecedor = fornecedores.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> fornecedor.getNome();
            case 1 -> fornecedor.getSobreNome();
            case 2 -> fornecedor.getBilheteIdentidade();
            case 3 -> fornecedor.getTelefone();
            case 4 -> fornecedor.getEmail();
            case 5 -> fornecedor.getProdutoFornecido();
            default -> null;
        };
    }

    public String getColumnName(int column) {
        return colunas[column];
    }

    public Fornecedor getFornecedorAt(int rowIndex) {
        return fornecedores.get(rowIndex);
    }

    public void atualizarDados(List<Fornecedor> novoFornecedor) {
        this.fornecedores = new ArrayList<>(novoFornecedor);
        fireTableDataChanged();
    }
}
