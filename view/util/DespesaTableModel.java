package view.util;

import model.Despesa;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DespesaTableModel extends AbstractTableModel {
    private List<Despesa> despesas;
    private final String[] colunas = {"Descrição", "Valor", "Fornecedor", "Categoria", "Data"};

    public DespesaTableModel(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public void atualizarDados(List<Despesa> despesas) {
        this.despesas = new ArrayList<>(despesas);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return despesas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Despesa despesa = despesas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return despesa.getDescricao();
            case 1:
                return String.format("%,.2f", despesa.getValor());
            case 2:
                return despesa.getFornecedor().getNome();
            case 3:
                return despesa.getCategoria().toString();
            case 4:
                return despesa.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}