package Practica2.Controlador;

import Practica2.Main.Main;
import Practica2.Modelo.Formas.*;
import Practica2.Modelo.Modelo;
import Practica2.NotiEnum;
import Practica2.Notificacion;
import Practica2.Vista.PanelGrafico;
import Practica2.Vista.dialogos.EnumPolygon;

public class Controlador extends Thread implements Notificacion {

    private final Main prog;
    private static final int LADO_INICIAL = 256;
    private boolean interrumpir = false;
    private double pasos = 0;
    private static int nPasosTotal = 0;
    public Controlador(Main p) {
        prog = p;
        prog.getModelo().reset();
    }

    public void run() {
        Modelo modelo = prog.getModelo();
        int n = modelo.getProfundidad();
        long tiempo;
        nPasosTotal = obtenerPasosPoligono(modelo.getTipo() == EnumPolygon.CUADRADO, n);
        // ESTIMACIONES // Complejidad = a^n
        // Si no es la primera ejecución, no hace falta calcular la predicción
        if (modelo.getC() > 0.000001) { // Ejecuciones repetidas
            if (modelo.getTipo() == EnumPolygon.CUADRADO) {
                long estimacion = (long) (modelo.getC() * Math.pow(4, n));
                System.out.println("Para " + n + " tardaré unos " + estimacion + " ns.");
                tiempo = System.nanoTime();
                generarCuadrado(new Punto(PanelGrafico.SIZE / 2, PanelGrafico.SIZE / 2), LADO_INICIAL, n);
            } else {
                Punto[] puntosIniciales = Triangulo.getPuntosIniciales();
                long estimacion = (long) (modelo.getC() * Math.pow(3, n));
                System.out.println("Para " + n + " tardaré unos " + estimacion + " ns.");
                tiempo = System.nanoTime();
                generarSierpinski(puntosIniciales[0], puntosIniciales[1], puntosIniciales[2], modelo.getProfundidad());
            }

            tiempo = System.nanoTime() - tiempo;
            System.out.println("He tardado " + tiempo + " ns.");
        } else { // Primera ejecución
            double c;
            tiempo = System.nanoTime();
            if (modelo.getTipo() == EnumPolygon.CUADRADO) {
                generarCuadrado(new Punto(PanelGrafico.SIZE / 2, PanelGrafico.SIZE / 2), LADO_INICIAL, n);
                tiempo = System.nanoTime() - tiempo;
                c = (1.0 * tiempo) / Math.pow(4, n);
            } else {
                Punto[] puntosIniciales = Triangulo.getPuntosIniciales();
                generarSierpinski(puntosIniciales[0], puntosIniciales[1], puntosIniciales[2], modelo.getProfundidad());
                tiempo = System.nanoTime() - tiempo;
                c = (1.0 * tiempo) / Math.pow(3, n);
            }
            System.out.println("He tardado unos " + tiempo + " ns.");
            // Se actualiza la c para futuras estimaciones
            modelo.setC(c);
        }
        if (!interrumpir) {
            prog.notificar(NotiEnum.PARAR, null);
            System.out.println("Fin de las ejecuciones");
        } else {
            // Se notifica mediante la consola de salida que se ha interrumpido el proceso
            System.out.println("Interrumpido");
        }
    }

    /**
     * Función para dibujar los cuadrados de forma recursiva
     * @param centro Coordenadas del centro del cuadrado actual
     * @param lado Tamaño del cuadrado actual
     * @param profundidad Nivel de profundidad actual
     */
    public void generarCuadrado(Punto centro, int lado, int profundidad) {
        // Dibujar el contorno del cuadrado actual con el color correspondiente al nivel
        Punto topLeft = new Punto(centro.getX() - lado / 2, centro.getY() - lado / 2);
        prog.getModelo().notificar(NotiEnum.ADDPOLIGONO, new Cuadrado(topLeft, lado));
        actualizarProgreso();

        // Tiempo de espera para visualizar el progreso
        try {
            sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Caso base: si el nivel es 0, detener la recursión
        if (profundidad == 0 || interrumpir)
            return;

        // Calcular el tamaño de los cuadrados más pequeños
        int ladoHijos = lado / 2;

        // Calcular las coordenadas de los centros de los cuadrados más pequeños
        Punto[] centrosHijos = crearCentrosHijosCuadrados(centro, ladoHijos);

        // Dibujar los contornos de los cuadrados más pequeños recursivamente
        for (Punto centroHijo : centrosHijos) {
            generarCuadrado(centroHijo, ladoHijos, profundidad - 1);
        }

    }

    /**
     * Función para dibujar los triángulos de Sierpinski de forma recursiva
     * @param p1 Primer vértice del triángulo
     * @param p2 Segundo vértice del triángulo
     * @param p3 Tercer vértice del triángulo
     * @param profundidad Nivel de profundidad actual
     */
    private void generarSierpinski(Punto p1, Punto p2, Punto p3, int profundidad) {
        actualizarProgreso();
        // Tiempo de espera para visualizar el progreso
        try {
            sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Manejo de finalización
        if (interrumpir)
            return;

        // Caso base: si la profundidad es 0, dibujar el triángulo
        if (profundidad == 0) {
            prog.getModelo().notificar(NotiEnum.ADDPOLIGONO, new Triangulo(p1, p2, p3));
        } else {

            // Calcular los puntos medios de los lados del triángulo
            Punto mid1 = new Punto((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
            Punto mid2 = new Punto((p2.getX() + p3.getX()) / 2, (p2.getY() + p3.getY()) / 2);
            Punto mid3 = new Punto((p3.getX() + p1.getX()) / 2, (p3.getY() + p1.getY()) / 2);

            // Dibujar los triángulos de Sierpinski recursivamente
            generarSierpinski(p1, mid1, mid3, profundidad - 1);
            generarSierpinski(mid1, p2, mid2, profundidad - 1);
            generarSierpinski(mid3, mid2, p3, profundidad - 1);
        }

    }

    /**
     * Se cuenta el número de pasos que se deben realizar (número de polígonos que se han de dibujar)
     * @param isCuadrado Indica si se trata de un cuadrado o un triángulo
     * @param profundidad Nivel de profundidad
     * @return Número de pasos a realizar
     */
    public int obtenerPasosPoligono(boolean isCuadrado, int profundidad) {
        int base = 3;
        if (isCuadrado)
            base = 4;
        int nPasos = 0;
        for (int i = profundidad; i >= 0; i--) {
            nPasos += (int) Math.pow(base, i);
        }
        return nPasos;
    }

    /**
     * Método encargado de actualizar el progreso en función de los pasos realizados hasta el momento
     * y el número de pasos totales a realizar
     */
    public void actualizarProgreso() {
        pasos++;
        while (pasos >=  (nPasosTotal * 1.0) / 100) {
            // Aumentamos el porcentaje tantas veces como sea necesario
            // Esto puede suceder en caso de que los polígonos totales a dibujar
            // sea menor a 100
            pasos -= (nPasosTotal * 1.0) / 100;
            prog.getVista().notificar(NotiEnum.PROGRESO, null);
        }
    }

    private Punto[] crearCentrosHijosCuadrados(Punto centro, int ladoHijos) {
        return new Punto[]{
                new Punto(centro.getX() - ladoHijos, centro.getY() - ladoHijos),
                new Punto(centro.getX() + ladoHijos, centro.getY() - ladoHijos),
                new Punto(centro.getX() + ladoHijos, centro.getY() + ladoHijos),
                new Punto(centro.getX() - ladoHijos, centro.getY() + ladoHijos)};
    }

    @Override
    public void notificar(NotiEnum s, Object o) {
        if (s == NotiEnum.PARAR) {
            interrumpir = true;
        }
    }
}
