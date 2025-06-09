package view;

import model.Servico;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ServicoTableModel extends AbstractTableModel {
    private List<Servico> servicos;
    private final String[] colunas = {"Descrição", "Valor Unitário"};

    public ServicoTableModel(List<Servico> servicos) {
        this.servicos = new ArrayList<>(servicos);
    }


    public int getRowCount() {
        return servicos == null ? 0 : servicos.size();
    }


    public int getColumnCount() {
        return colunas.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Servico servico = servicos.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> servico.getDescricao();
            case 1 -> String.format("%,.2f", servico.getValorUnitario());
            default -> null;
        };
    }

    public String getColumnName(int column) {
        return colunas[column];
    }

    public Servico getServicoAt(int rowIndex) {
        return servicos.get(rowIndex);
    }

    public void atualizarDados(List<Servico> novosServicos) {
        this.servicos = new ArrayList<>(novosServicos);
        fireTableDataChanged();
    }
}