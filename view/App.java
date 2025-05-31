package view;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame janela = new JFrame("Phinanceiro");
                janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                janela.setSize(1000, 600);

                janela.setLocationRelativeTo(null);
                janela.setVisible(true);

            }
        });
    }
}