package Vista.dialogos;

import javax.swing.*;

public class TypePanel extends JPanel {

    private JRadioButton r1;
    private JRadioButton r2;
    private ButtonGroup group;


    public TypePanel(boolean esDijkstra, OrigenDestinoPanel origenDestinoPanel) {
        group = new ButtonGroup();
        r1 = new JRadioButton("Dijkstra", esDijkstra);
        r2 = new JRadioButton("Prim", !esDijkstra);
        r1.addActionListener(e -> origenDestinoPanel.setVisible(true));
        r2.addActionListener(e -> origenDestinoPanel.setVisible(false));
        group.add(r1);
        group.add(r2);
        this.add(r1);
        this.add(r2);
    }

    public String getTipo() {
        return r1.isSelected() ? "Dijkstra" : "Prim";
    }
}
