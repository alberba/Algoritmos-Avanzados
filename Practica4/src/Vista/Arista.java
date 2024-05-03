package Vista;

import java.awt.*;

public class Arista {
    private final Nodo origen;
    private final Nodo destino;
    private Color color;

    public Arista(Nodo origen, Nodo destino) {
        this.origen = origen;
        this.destino = destino;
        this.color = Color.BLACK;
    }

    public Nodo getOrigen() {
        return origen;
    }

    public Nodo getDestino() {
        return destino;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
