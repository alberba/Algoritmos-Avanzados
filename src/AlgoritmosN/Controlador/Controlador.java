package AlgoritmosN.Controlador;

import AlgoritmosN.Main.Main;
import AlgoritmosN.Modelo.Modelo;
import AlgoritmosN.NotiEnum;

import java.util.Arrays;
import java.util.Random;

public class Controlador extends Thread {

    private final Main prog;
    public Controlador(Main p) {
        prog = p;
        p.getModelo().reset();
    }

    public void run() {
        Random random = new Random();
        Modelo modelo = prog.getModelo();
        int [] nMuestras = {100, 500, 1000, 2000, 3000};

        // Recorrido a todas las muestras de n
        for (int n : nMuestras) {
            long[] tiempos = new long[3];
            int[] numeros = new int[n];

            // Generación de n números aleatorios
            for (int i = 0; i < n; i++) {
                numeros[i] = random.nextInt(101);
            }

            long iniTime = System.nanoTime();
            System.out.println("\nTiempos para n = " + n + ": ");

            // Cálculo del algoritmo de complejidad n
            algoritmoN(numeros);
            tiempos[0] = System.nanoTime() - iniTime;
            System.out.println("Tiempo del algoritmo de complejidad n: " + tiempos[0]);

            // Cálculo del algoritmo de complejidad nlog(n)
            iniTime = System.nanoTime();
            algoritmoNlogN(numeros);
            tiempos[1] = System.nanoTime() - iniTime;
            System.out.println("Tiempo del algoritmo de complejidad nlog(n): " + tiempos[1]);

            // Cálculo del algoritmo de complejidad n^2
            iniTime = System.nanoTime();
            algoritmoN2(numeros);
            tiempos[2] = System.nanoTime() - iniTime;
            System.out.println("Tiempo del algoritmo de complejidad n^2: " + tiempos[2]);

            // Pasamos los resultados al modelo
            modelo.addTiempoN(n, tiempos);
            // Notificamos a la vista para que se actualice
            prog.notificar(NotiEnum.DIBUJAR);
        }
    }

    /**
     * Algoritmo de complejidad N para calcular la moda de un array de enteros
     * @param numeros Array de enteros
     */
    public static void algoritmoN(int [] numeros) {
        int [] frecuenciaNumeros = new int[101];
        int moda = 0;
        int maxRepeticiones = 0;
        // Se recorren los valores y se cuentan las repeticiones de cada valor
        for (int numero : numeros) {
            frecuenciaNumeros[numero]++;
        }

        // Se busca el valor con más repeticiones
        for (int i = 0; i < frecuenciaNumeros.length; i++) {
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
    public static void algoritmoNlogN(int [] numeros) {
        // Se ordena el array (complejidad nlog(n))
        Arrays.sort(numeros);
        int moda = numeros[0];
        int maxRepeticiones = 1;
        int repeticiones = 1;

        // Los valores repetidos se encontrarán juntos, por lo que se recorre el array y se cuentan las repeticiones
        for (int i = 1; i < numeros.length; i++) {
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
    public static void algoritmoN2(int [] numeros) {
        int moda = numeros[0];
        int maxRepeticiones = 1;
        // Se recorren los valores
        for (int numero : numeros) {
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
}
