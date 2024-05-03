package Modelo;

import java.util.ArrayList;

public class Poblacion implements Comparable<Poblacion>{
    private String comunidad;
    private String provincia;
    private String poblacion;
    private double lat;
    private double lon;
    private final ArrayList<Carretera> carreterasPoblacion;

    public Poblacion(String comunidad, String provincia, String poblacion, double lat, double lon) {
        this.comunidad = comunidad;
        this.provincia = provincia;
        this.poblacion = poblacion;
        this.lat = lat;
        this.lon = lon;
        carreterasPoblacion = new ArrayList<Carretera>();
    }

    public String getNom() {
        return poblacion;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getNumCarreteras() {
        return carreterasPoblacion.size();
    }

    public ArrayList<Carretera> getCarreteras() {
        return carreterasPoblacion;
    }



    public void addCarretera(Carretera carretera) {
        this.carreterasPoblacion.add(carretera);
    }

    @Override
    public int compareTo(Poblacion o) {
        return this.poblacion.compareTo(o.poblacion);
    }

}
