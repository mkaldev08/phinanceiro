package view;

import model.Receita;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ReceitaTableModel extends AbstractTableModel {
    private List<Receita> receitas;
    private final String[] colunas = {"Cliente", "Valor", "Forma Pagamento", "Recebido", "Data", "Orçamento"};

    public void atualizarDados(List<Receita> receitas) {
        this.receitas = receitas;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return receitas != null ? receitas.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Receita receita = receitas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return receita.getCliente().getNome();
            case 1:
                return String.format("%,.2f", receita.getValor());
            case 2:
                return receita.getFormapagamento();
            case 3:
                return receita.isRecebido() ? "Sim" : "Não";
            case 4:
                return receita.getDataFormatada();
            case 5:
                return "Orç. #" + receita.getOrcamentoOrigem().getId();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public Receita getReceitaAt(int rowIndex) {
        return receitas.get(rowIndex);
    }
}