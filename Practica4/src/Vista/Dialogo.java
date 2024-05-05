package Vista;

import Modelo.Grafo;
import Modelo.Algoritmo;
import Notification.NotiEnum;
import Main.Main;
import Vista.dialogos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Dialogo extends JDialog implements ActionListener {

    private final JButton okButton = new JButton("OK");
    private final Main prog;
    private final JPanel interior;
    private TypePanel typePanel;
    private OrigenDestinoPanel origenDestinoPanel;

    public Dialogo(Main prog, int nPueblosActual, int nCarreterasActual) {
        this.interior = new NPoblacionesPanel(nPueblosActual, nCarreterasActual);
        this.prog = prog;
        this.setLayout(new BorderLayout());
        okButton.addActionListener(this);
        this.add(interior, BorderLayout.CENTER);
        this.add(okButton, BorderLayout.SOUTH);
        mostrar();
    }
    
    public Dialogo(Main prog, Algoritmo algoritmo, Grafo grafo) {
        boolean esDijkstra = algoritmo == Algoritmo.DIJKSTRA;
        origenDestinoPanel = new OrigenDestinoPanel(grafo);
        typePanel = new TypePanel(esDijkstra, origenDestinoPanel);

        this.interior = new JPanel();
        this.interior.setLayout(new BoxLayout(interior, BoxLayout.Y_AXIS));
        this.interior.add(typePanel);
        this.interior.add(origenDestinoPanel);
        this.prog = prog;
        this.setLayout(new BorderLayout());
        this.add(okButton, BorderLayout.SOUTH);
        this.add(interior, BorderLayout.CENTER);
        mostrar();
        if(!esDijkstra) {
            origenDestinoPanel.setVisible(false);
        }
        okButton.addActionListener(this);
    }

    public Dialogo(Main prog, String path) {
        this.interior = new XMLPanel(path);
        this.prog = prog;
        this.setLayout(new BorderLayout());
        okButton.addActionListener(this);
        this.add(interior, BorderLayout.CENTER);
        this.add(okButton, BorderLayout.SOUTH);
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
            if(!(interior instanceof NPoblacionesPanel) && !(interior instanceof XMLPanel)) {
                if (Objects.equals(typePanel.getTipo(), "Dijkstra") && Objects.equals(origenDestinoPanel.getOrigenSeleccionado(), origenDestinoPanel.getDestinoSeleccionado())) {
                    JOptionPane.showMessageDialog(this, "El origen y el destino no pueden ser iguales", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (interior instanceof NPoblacionesPanel) {
                if (((NPoblacionesPanel) interior).getNCarreteras() >= ((NPoblacionesPanel) interior).getNPoblaciones()) {
                    JOptionPane.showMessageDialog(this, "El número de carreteras debe ser menor que el número de poblaciones", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            notificar();
            this.dispose();
        }
    }

    public void notificar() {
        try {
            if (interior instanceof NPoblacionesPanel) {
                prog.getModelo().notificar(NotiEnum.SETDATOSGRAFO, new int[]{((NPoblacionesPanel) interior).getNPoblaciones(), ((NPoblacionesPanel) interior).getNCarreteras()});
            } else if (interior instanceof XMLPanel) {
                prog.getModelo().notificar(NotiEnum.SETXML, ((XMLPanel) interior).getPathXML());
            } else {
                System.out.println(typePanel.getTipo());
                Algoritmo algoritmo = Objects.equals(typePanel.getTipo(), "Dijkstra") ? Algoritmo.DIJKSTRA : Algoritmo.PRIM;
                if (algoritmo == Algoritmo.DIJKSTRA) {
                    prog.getModelo().notificar(NotiEnum.PARAMPUEBLO, new String[]{origenDestinoPanel.getOrigenSeleccionado(), origenDestinoPanel.getDestinoSeleccionado()});
                }
                prog.getModelo().notificar(NotiEnum.SETALGORITMO, algoritmo);
            }
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos");
        }
    }
}
