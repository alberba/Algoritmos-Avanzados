import mesurament.Mesurament24;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Mesurament24.mesura();
        Random random = new Random();
        int [] nMuestras = {1000, 10000, 100000};

        for (int n : nMuestras) {
            int[] numeros = new int[n];


            for (int i = 0; i < n; i++) {
                numeros[i] = random.nextInt(101);
            }


            long iniTime = System.nanoTime();
            System.out.println("\nTiempos para n = " + n + ": ");
            algoritmoN(numeros);
            System.out.println("Tiempo del algoritmo de complejidad n: " + (System.nanoTime() - iniTime));
            iniTime = System.nanoTime();
            algoritmoNlogN(numeros);
            System.out.println("Tiempo del algoritmo de complejidad nlog(n): " + (System.nanoTime() - iniTime));
            iniTime = System.nanoTime();
            algoritmoN2(numeros);
            System.out.println("Tiempo del algoritmo de complejidad n^2: " + (System.nanoTime() - iniTime));
        }
    }

    public static void algoritmoN(int[] numeros) {
        TreeMap<Integer, Integer> lista = new TreeMap<>();
        for (int numero : numeros) {
            // Si el número ya está en el mapa, se le suma 1 a su valor
            if (lista.containsKey(numero)) {
                lista.put(numero, lista.get(numero) + 1);
            } else {
                // Se crea una nueva entrada en el mapa
                lista.put(numero, 1);
            }
        }

        Map.Entry<Integer, Integer> moda = lista.firstEntry();

        // Se recorren los valores de la lista
        for (Map.Entry<Integer, Integer> entry : lista.entrySet()) {
            if (entry.getValue() > moda.getValue()) {
                moda = entry;
            }
        }
        System.out.println("La moda es el valor " + moda.getKey() + " y aparece " + moda.getValue() + " veces.");
    }

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