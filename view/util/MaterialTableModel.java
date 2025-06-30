 package view.util;

import model.Material;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

 public class MaterialTableModel extends AbstractTableModel {
     private List<Material> materiais;
     private final String[] colunas = {"Descrição", "Valor Unitário","Unidade"};

     public MaterialTableModel(List<Material> materiais) {
         this.materiais = new ArrayList<>(materiais);
     }

     public int getRowCount() {
         return materiais == null ? 0 : materiais.size();
     }

     public int getColumnCount() {
         return colunas.length;
     }

     public Object getValueAt(int rowIndex, int columnIndex) {
         Material material = materiais.get(rowIndex);
         return switch (columnIndex) {
             case 0 -> material.getDescricao();
             case 1 -> String.format("%,.2f", material.getPrecoUnitario());
             case 2 -> material.getUnidadeMedida();
             default -> null;
         };
     }

     public String getColumnName(int column) {
         return colunas[column];
     }

     public Material getMaterialAt(int rowIndex) {
         return materiais.get(rowIndex);
     }

     public void atualizarDados(List<Material> novosMateriais) {
         this.materiais = new ArrayList<>(novosMateriais);
         fireTableDataChanged();
     }
 }