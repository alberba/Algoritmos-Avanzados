package AlgoritmosN.Controlador;

import AlgoritmosN.Main.Main;
import AlgoritmosN.Modelo.Modelo;
import AlgoritmosN.NotiEnum;

import java.util.Arrays;
import java.util.Random;

public class Controlador extends Thread {

    private final Main prog;
    private final int [] nMuestras = {100, 500, 1000, 2000, 3000};
    private boolean interrumpir = false;
    private int pasos = 0;
    public Controlador(Main p) {
        prog = p;
        p.getModelo().reset();
    }

    public void run() {
        Modelo modelo = prog.getModelo();
        // Recorrido a todas las muestras de n
        for (int n : nMuestras) {
            if (interrumpir) {
                break;
            }
            long tiempo;
            int[] numeros = generarNAleatorios100(n);

            long iniTime = System.nanoTime();
            System.out.println("\nTiempos para n = " + n + ": ");

            // Cálculo del algoritmo de complejidad n
            algoritmoN(numeros);
            if (interrumpir) {
                break;
            }
            tiempo = System.nanoTime() - iniTime;
            System.out.println("Tiempo del algoritmo de complejidad n: " + tiempo);
            // Pasamos el resultado al modelo
            modelo.addTiempoN(n, tiempo, 0);

            // Cálculo del algoritmo de complejidad nlog(n)
            iniTime = System.nanoTime();
            algoritmoNlogN(numeros);
            if (interrumpir) {
                break;
            }
            tiempo = System.nanoTime() - iniTime;
            System.out.println("Tiempo del algoritmo de complejidad nlog(n): " + tiempo);
            // Pasamos el resultado al modelo
            modelo.addTiempoN(n, tiempo, 1);

            // Cálculo del algoritmo de complejidad n^
            iniTime = System.nanoTime();
            algoritmoN2(numeros);
            if (interrumpir) {
                break;
            }
            tiempo = System.nanoTime() - iniTime;
            System.out.println("Tiempo del algoritmo de complejidad n^2: " + tiempo);
            // Pasamos el resultado al modelo
            modelo.addTiempoN(n, tiempo, 2);
        }
        // Solo envía la notificación de parar si ha acabado las ejecuciones
        if (!interrumpir) {
            prog.getVista().notificar(NotiEnum.PARAR);
        } else {
            // Se notifica mediante la consola de salida que se ha interrumpido el proceso
            System.out.println("Interrumpido");
        }
    }

    /**
     * Algoritmo de complejidad N para calcular la moda de un array de enteros
     * @param numeros Array de enteros
     */
    public void algoritmoN(int [] numeros) {
        int [] frecuenciaNumeros = new int[101];
        int moda = 0;
        int maxRepeticiones = 0;
        // Se recorren los valores y se cuentan las repeticiones de cada valor
        for (int numero : numeros) {
            frecuenciaNumeros[numero]++;
        }

        // Se busca el valor con más repeticiones
        for (int i = 0; i < frecuenciaNumeros.length; i++) {
            if (interrumpir) {
                return;
            }
            pasos++;
            if(pasos % 10000 == 0) {
                pasos = 0;
                prog.getVista().notificar(NotiEnum.PROGRESO);
            }
            if (frecuenciaNumeros[i] > maxRepeticiones) {
                moda = i;
                maxRepeticiones = frecuenciaNumeros[i];
            }
        }
        System.out.println("La moda es el valor " + moda + " y aparece " + maxRepeticiones + " veces.");
    }

    /**
     * Algoritmo de complejidad nlog(n) para calcular la moda de un array de enteros
     * @param numeros Array de enteros
     */
    public void algoritmoNlogN(int [] numeros) {
        // Se ordena el array (complejidad nlog(n))
        Arrays.sort(numeros);
        int moda = numeros[0];
        int maxRepeticiones = 1;
        int repeticiones = 1;

        // Los valores repetidos se encontrarán juntos, por lo que se recorre el array y se cuentan las repeticiones
        for (int i = 1; i < numeros.length; i++) {
            if (interrumpir) {
                return;
            }
            pasos++;
            if(pasos % 10000 == 0) {
                pasos = 0;
                prog.getVista().notificar(NotiEnum.PROGRESO);
            }
            // En el caso de coincidencia, se incrementa el contador de repeticiones
            if (numeros[i] == numeros[i - 1]) {
                repeticiones++;
            } else {
                // Se trata de un número nuevo, por lo que se comprueba si el número anterior es el
                // que mas repeticiones tiene
                if (repeticiones > maxRepeticiones) {
                    moda = numeros[i - 1];
                    maxRepeticiones = repeticiones;
                }
                // Se reinicia el contador
                repeticiones = 1;
            }
        }
        System.out.println("La moda es el valor " + moda + " y aparece " + maxRepeticiones + " veces.");
    }

    /**
     * Algoritmo de complejidad n^2 para calcular la moda de un array de enteros
     * @param numeros Array de enteros
     */
    public void algoritmoN2(int [] numeros) {
        int moda = numeros[0];
        int maxRepeticiones = 1;
        // Se recorren los valores
        for (int numero : numeros) {
            if (interrumpir) {
                return;
            }
            pasos++;
            pasos++;
            if(pasos % 10000 == 0) {
                pasos = 0;
                prog.getVista().notificar(NotiEnum.PROGRESO);
            }
            int repeticiones = 0;
            // Para cada valor, se cuentan las veces que aparece
            for (int aux : numeros) {

                if (numero == aux) {
                    repeticiones++;
                }
                // Si el número actual tiene más repeticiones que el anterior, se actualiza la moda
                if (repeticiones > maxRepeticiones) {
                    moda = numero;
                    maxRepeticiones = repeticiones;
                }
            }
        }
        System.out.println("La moda es el valor " + moda + " y aparece " + maxRepeticiones + " veces.");
    }

    /**
     * Método que genera n números aleatorios entre 0 y 100
     * @param n Tamño del array
     * @return Array de enteros
     */
    private int[] generarNAleatorios100(int n) {
        Random random = new Random();
        int[] numeros = new int[n];
        // Generación de n números aleatorios
        for (int i = 0; i < n; i++) {
            numeros[i] = random.nextInt(101);
        }
        return numeros;
    }

    public void notificar(NotiEnum s) {
        if (s == NotiEnum.PARAR) {
            interrumpir = true;
        }
    }
}
