package Vista;


import Main.Main;
import Notification.NotiEnum;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Vista extends JFrame implements ActionListener {

    private final Main prog;
    private final JButton detButton, ficheroButton;
    private final PanelGrafico panel;

    public Vista(String title, Main p) {
        super(title);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1200, 800));

        // INSERCIÓN DE BOTONES
        JPanel buttons = new JPanel();
        detButton = new JButton("Detectar Idioma");
        detButton.addActionListener(this);
        buttons.add(detButton);
        ficheroButton = new JButton("Abrir fichero");
        ficheroButton.addActionListener(this);
        buttons.add(ficheroButton);
        this.add(BorderLayout.NORTH, buttons);

        // INSERCIÓN DE PANEL
        panel = new PanelGrafico(p);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.add(scrollPane, BorderLayout.CENTER);

        panel.repaint();
    }

    public void mostrar() {
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(400, 0);
        this.setVisible(true);
    }

    public void resetPanel() {
        panel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == detButton) {
            prog.notificar(NotiEnum.DETIDIOMA, panel.getText());
        } else if (e.getSource() == ficheroButton) {
            JFileChooser fileChooser = new JFileChooser();
            // El directorio inicial por defecto será la carpeta del proyecto
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Se ha escogido un fichero
                panel.setText(leerFichero(fileChooser.getSelectedFile()));
            }
        }
    }

    /**
     * Lee un fichero y devuelve su contenido en forma de String
     * @param fichero Fichero a leer
     * @return Contenido del fichero
     */
    public String leerFichero(File fichero) {
        StringBuilder texto = new StringBuilder();
        try {
            Scanner myReader = new Scanner(fichero);
            while (myReader.hasNextLine()) {
                texto.append(myReader.nextLine()).append("\n");
            }
            myReader.close();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        return texto.toString();
    }
}
