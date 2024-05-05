package Vista.dialogos;

import javax.swing.*;
import java.awt.*;

public class XMLPanel extends JPanel {
    private JTextField xml;

    public XMLPanel(String pathActual) {
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Numero de Poblaciones: "));
        xml = new JTextField(15);
        xml.setText(pathActual);
        this.add(xml);
    }

    public String getPathXML() {
        // Se revisa que el texto ingresado acabe en .xml
        if (xml.getText().endsWith(".xml")) {
            if (xml.getText().startsWith("src/"))
                return xml.getText();
            else
                return "src/" + xml.getText();
        } else {
            if (xml.getText().startsWith("src/"))
                return xml.getText() + ".xml";
            else
                return "src/" + xml.getText() + ".xml";
        }
    }
}
