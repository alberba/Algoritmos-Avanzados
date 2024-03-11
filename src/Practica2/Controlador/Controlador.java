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
    public Controlador(Main p) {
        prog = p;
        prog.getModelo().reset();
    }

    public void run() {
        Modelo modelo = prog.getModelo();
        int n = modelo.getProfundidad();
        if (modelo.getTipo() == EnumPolygon.CUADRADO) {
            double log2N = Math.log(n) / Math.log(2);
            // Si no es la primera ejecución, no hace falta calcular la predicción
            // TODO: Solucionar los tiempos

            if (modelo.getC() > 0.000001) { // Ejecuciones repetidas
                long estimacion = (long) (modelo.getC() * (n * log2N));
                System.out.println("Para " + n + " tardaré unos " + estimacion + " ns.");
                long tiempo = System.nanoTime();
                generarCuadrado(new Punto(PanelGrafico.SIZE / 2, PanelGrafico.SIZE / 2), LADO_INICIAL, n);
                tiempo = System.nanoTime() - tiempo;
                System.out.println("He tardado " + tiempo + " ns.");
                double c = (1.0 * tiempo) / (n * log2N);
                modelo.setC(c);
            } else { // Primera ejecución
                long tiempo = System.nanoTime();
                generarCuadrado(new Punto(PanelGrafico.SIZE / 2, PanelGrafico.SIZE / 2), LADO_INICIAL, n);
                tiempo = System.nanoTime() - tiempo;
                double c = (1.0 * tiempo) / (n * log2N);
                long estimacion = (long) (c * (n * log2N));
                System.out.println("He tardado unos " + tiempo + " ns.");
                // Se actualiza la c para futuras estimaciones
                modelo.setC(c);
            }
        } else {
            Punto[] puntosIniciales = Triangulo.getPuntosIniciales();
            generarSierpinski(puntosIniciales[0], puntosIniciales[1], puntosIniciales[2], modelo.getProfundidad());
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
        prog.getModelo().notificar(NotiEnum.ADDCUADRADO, new Cuadrado(topLeft, lado));

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
            prog.getModelo().notificar(NotiEnum.ADDTRIANGULO, new Triangulo(p1, p2, p3));
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
