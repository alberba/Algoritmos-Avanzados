package Vista;

public class Nodo {

    private final int x;
    private final int y;
    private final String nombre;
    private final int indice;

    public Nodo(int x, int y, String nombre, int indice) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;
        this.indice = indice;
    }

    public String getNombre() {
        return nombre;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndice() {
        return indice;
    }
}
