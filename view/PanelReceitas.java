package view;

import controller.ReceitaController;

import javax.swing.*;
import java.awt.*;

public class PanelReceitas extends JPanel {
    private final ReceitaController receitaController;

    public PanelReceitas(ReceitaController controller) {
        this.receitaController = controller;
    }

    public void initComponents(){
        setLayout(new BorderLayout());
    }
}
