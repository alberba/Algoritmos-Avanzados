package Vista.dialogos;

import javax.swing.*;
import java.awt.*;

public class NBucketsPanel extends JPanel {

    private JTextField nBuckets;

    public NBucketsPanel(int profActual) {
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Nº de buckets: "));
        nBuckets = new JTextField(4);
        nBuckets.setText(Integer.toString(profActual));
        this.add(nBuckets);
    }

    public int getNBuckets() {
        try {
            return Integer.parseInt(nBuckets.getText());
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos");
            return 0;
        }
    }
}
