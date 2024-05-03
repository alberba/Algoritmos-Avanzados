package Vista;

public class Nodo {
    private static final double rangoLat = 16.0437;
    private static final double minLat = 27.7256;
    private static final double RangoLon = 22.313903;
    private static final double minLon = -18.0243;

    private final int x;
    private final int y;
    private final String nombre;

    public Nodo(int x, int y, String nombre) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;
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

    // Constantes para la conversión de coordenadas
    public static double getRangoLat() {
        return rangoLat;
    }

    public static double getMinLat() {
        return minLat;
    }

    public static double getRangoLon() {
        return RangoLon;
    }

    public static double getMinLon() {
        return minLon;
    }
}
