package Modelo;

public class Carretera {

    private double distancia;
    private Poblacion pob1;
    private Poblacion pob2;

    public Carretera (double distancia, Poblacion pob1, Poblacion pob2) {
        this.distancia = distancia;
        this.pob1 = pob1;
        this.pob2 = pob2;
    }


    /**
     * Calcula la distancia entre dos poblaciones
     * @param pob1
     * @param pob2
     * @return
     */
    public static double calcularDistancia(Poblacion pob1, Poblacion pob2) {
        double x, y;
        x = pob1.getLon() - pob2.getLon();
        y = pob1.getLat() - pob2.getLat();

        return Math.hypot(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        Carretera carretera = (Carretera) obj;
        return (this.pob1.equals(carretera.pob2) && this.pob2.equals(carretera.pob1)
                || this.pob1.equals(carretera.pob1) && this.pob2.equals(carretera.pob2));
    }

    public double getDistancia() {
        return distancia;
    }

    public Poblacion getPob1() {
        return pob1;
    }

    public Poblacion getPob2() {
        return pob2;
    }

    public Poblacion getContrario(Poblacion poblacion) {
        return poblacion.equals(pob1) ? pob2 : pob1;
    }

}
