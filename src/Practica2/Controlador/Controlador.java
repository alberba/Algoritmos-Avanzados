package Practica2.Controlador;

import Practica2.Main.Main;
import Practica2.Modelo.Formas.Cuadrado;
import Practica2.Modelo.Formas.Punto;
import Practica2.Modelo.Modelo;
import Practica2.NotiEnum;
import Practica2.Notificacion;
import Practica2.Vista.PanelGrafico;
import Practica2.Vista.dialogos.EnumPolygon;

import java.awt.*;

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
        if (modelo.getTipo() == EnumPolygon.CUADRADO) {
            generarCuadrado(new Punto(PanelGrafico.SIZE / 2, PanelGrafico.SIZE / 2), LADO_INICIAL, modelo.getProfundidad());
        } else {
            // TODO: Realizar la llamada a la función
            //drawSierpinski();
        }
        if (!interrumpir) {
            prog.notificar(NotiEnum.PARAR, null);
            System.out.println("Fin de las ejecuciones");
        } else {
            // Se notifica mediante la consola de salida que se ha interrumpido el proceso
            System.out.println("Interrumpido");
        }
    }

    public void generarCuadrado(Punto centro, int lado, int profundidad) {
        // Dibujar el contorno del cuadrado actual con el color correspondiente al nivel
        Punto topLeft = new Punto(centro.getX() - lado / 2, centro.getY() - lado / 2);
        prog.getModelo().notificar(NotiEnum.ADDCUADRADO, new Cuadrado(topLeft, lado));

        // Caso base: si el nivel es 0, detener la recursión
        if (profundidad == 0)
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

    // Función recursiva para dibujar el triángulo de Sierpinski

    private void drawSierpinski(Graphics g, Punto p1, Punto p2, Punto p3, int depth, Color[] colors) {
        // Caso base: si la profundidad es 0, dibujar el triángulo
        if (depth == 0) {
            g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            g.drawLine(p2.getX(), p2.getY(), p3.getX(), p3.getY());
            g.drawLine(p3.getX(), p3.getY(), p1.getX(), p1.getY());
        } else {
            // Calcular los puntos medios de los lados del triángulo
            Punto mid1 = new Punto((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
            Punto mid2 = new Punto((p2.getX() + p3.getX()) / 2, (p2.getY() + p3.getY()) / 2);
            Punto mid3 = new Punto((p3.getX() + p1.getX()) / 2, (p3.getY() + p1.getY()) / 2);

            // Dibujar los triángulos de Sierpinski recursivamente, con colores diferentes para cada nivel
            drawSierpinski(g, p1, mid1, mid3, depth - 1, colors);
            drawSierpinski(g, mid1, p2, mid2, depth - 1, colors);
            drawSierpinski(g, mid3, mid2, p3, depth - 1, colors);
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
        if (s == NotiEnum.INICIAR) {
            //generarCuadrado();
        }
    }
}
