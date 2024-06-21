package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Modal extends JDialog {

    public Modal(Vista p, String event) {
        JPanel interior = new PanelGraficoDibujo(p.getProg());
        constructA();
        this.add(interior, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void constructA() {
        this.setModal(true);
        this.pack();
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
    }
}
