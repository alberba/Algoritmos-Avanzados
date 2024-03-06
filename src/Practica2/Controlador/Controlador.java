package Practica2.Controlador;

import Practica2.Main.Main;
import Practica2.Modelo.Formas.Cuadrado;
import Practica2.Modelo.Formas.Punto;
import Practica2.Modelo.Modelo;
import Practica2.NotiEnum;
import Practica2.Notificacion;

import java.awt.*;
import java.util.Random;

public class Controlador extends Thread implements Notificacion {

    private final Main prog;
    private boolean interrumpir = false;
    public Controlador(Main p) {
        prog = p;
    }

    public void run() {
        Modelo modelo = prog.getModelo();
        // Recorrido a todas las muestras de n




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
        Punto[] centrosHijos = crearCentrosHijos(centro, ladoHijos);

        // Dibujar los contornos de los cuadrados más pequeños recursivamente
        for (Punto centroHijo : centrosHijos) {
            generarCuadrado(centroHijo, ladoHijos, profundidad - 1);
        }
    }

    private Punto[] crearCentrosHijos(Punto centro, int ladoHijos) {
        return new Punto[]{
                new Punto(centro.getX() - ladoHijos, centro.getY() - ladoHijos),
                new Punto(centro.getX() + ladoHijos, centro.getY() - ladoHijos),
                new Punto(centro.getX() + ladoHijos, centro.getY() + ladoHijos),
                new Punto(centro.getX() - ladoHijos, centro.getY() + ladoHijos)};
    }

    @Override
    public void notificar(NotiEnum s, Object o) {
        if (s == NotiEnum.INICIAR) {
            interrumpir = true;
        }
    }
}
