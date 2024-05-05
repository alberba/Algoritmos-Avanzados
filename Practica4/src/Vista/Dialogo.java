package Vista;

import Modelo.Grafo;
import Modelo.Algoritmo;
import Vista.dialogos.NPoblacionesPanel;
import Main.Main;
import Vista.dialogos.OrigenDestinoPanel;
import Vista.dialogos.TypePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialogo extends JDialog implements ActionListener {

    private final JButton okButton = new JButton("OK");
    private final Main prog;
    private final JPanel interior;

    public Dialogo(Main prog, int nPueblosActual, Grafo grafo) {
        this.interior = new JPanel();
        this.interior.setLayout(new BoxLayout(interior, BoxLayout.Y_AXIS));
        this.interior.add(new NPoblacionesPanel(nPueblosActual));
        this.interior.add(new OrigenDestinoPanel(grafo));
        this.prog = prog;
        this.setLayout(new BorderLayout());
        this.add(interior, BorderLayout.CENTER);
        okButton.addActionListener(this);
        this.add(okButton, BorderLayout.SOUTH);
        mostrar();
    }
    
    public Dialogo(Main prog, Algoritmo algoritmo) {
        this.interior = new TypePanel(algoritmo == Algoritmo.DIJKSTRA);
        this.interior.setLayout(new BoxLayout(interior, BoxLayout.Y_AXIS));
        this.prog = prog;
        okButton.addActionListener(this);
        this.interior.add(okButton);
        this.add(interior);
        mostrar();
    }

    public void mostrar() {
        this.pack();
        this.setVisible(true);
        this.setLocation(400, 400);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            notificar();
            this.dispose();
        }
    }

    public void notificar() {
        try {
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos");
        }
    }
}
