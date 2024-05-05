package Vista;

import Modelo.Grafo;
import Modelo.Algoritmo;
import Notification.NotiEnum;
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
    private TypePanel typePanel;
    private OrigenDestinoPanel origenDestinoPanel;

    public Dialogo(Main prog, int nPueblosActual) {
        this.interior = new NPoblacionesPanel(nPueblosActual);
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
        this.interior.add(new TypePanel(esDijkstra, origenDestinoPanel));
        this.interior.add(origenDestinoPanel);
        if(!esDijkstra) {
            origenDestinoPanel.setVisible(false);
        }
        this.prog = prog;
        this.setLayout(new BorderLayout());
        okButton.addActionListener(this);
        this.add(okButton, BorderLayout.SOUTH);
        this.add(interior, BorderLayout.CENTER);
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
            if(!(interior instanceof NPoblacionesPanel) && origenDestinoPanel.getOrigenSeleccionado() == origenDestinoPanel.getDestinoSeleccionado()) {
                JOptionPane.showMessageDialog(this, "El origen y el destino no pueden ser iguales", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            notificar();
            this.dispose();
        }
    }

    public void notificar() {
        try {
            if (interior instanceof NPoblacionesPanel) {
                prog.getModelo().notificar(NotiEnum.SETNPOBLACIONES, ((NPoblacionesPanel) interior).getNPoblaciones());
            } else {
                Algoritmo algoritmo = typePanel.getTipo().equals("Dijkstra") ? Algoritmo.DIJKSTRA : Algoritmo.PRIM;
                if (algoritmo == Algoritmo.DIJKSTRA) {
                    prog.getModelo().notificar(NotiEnum.PARAMPUEBLO, new String[]{origenDestinoPanel.getOrigenSeleccionado(), origenDestinoPanel.getDestinoSeleccionado()});
                }
                prog.getModelo().notificar(NotiEnum.SETALGORITMO, algoritmo);
            }
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos");
        }
    }

    public TypePanel getTypePanel() {
        return typePanel;
    }

    public void setTypePanel(TypePanel typePanel) {
        this.typePanel = typePanel;
    }

    public OrigenDestinoPanel getOrigenDestinoPanel() {
        return origenDestinoPanel;
    }

    public void setOrigenDestinoPanel(OrigenDestinoPanel origenDestinoPanel) {
        this.origenDestinoPanel = origenDestinoPanel;
    }
}
