package view;

import model.Servico;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ServicoTableModel extends AbstractTableModel {
    private final List<Servico> servicos;
    private final String[] colunas = {"ID", "Descrição", "Valor Unitário"};

    public ServicoTableModel(List<Servico> servicos) {
        this.servicos = servicos;
    }

    @Override
    public int getRowCount() {
        return servicos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Servico servico = servicos.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> servico.getId();
            case 1 -> servico.getDescricao();
            case 2 -> String.format("%,.2f", servico.getValorUnitario());
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public Servico getServicoAt(int rowIndex) {
        return servicos.get(rowIndex);
    }
}