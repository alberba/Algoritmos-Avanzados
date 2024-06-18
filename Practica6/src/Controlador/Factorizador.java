package Controlador;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Factorizador {
    Controlador controlador;

    public Factorizador(Controlador controlador) {
        this.controlador = controlador;
    }

    /**
     * Factoriza un número
     * @param n Número a factorizar
     */
    public void factorizar(String n) {
        long temps = System.nanoTime();
        BigInteger num = new BigInteger(n);
        BigInteger zero = new BigInteger("0");
        BigInteger dos = new BigInteger("2");
        BigInteger test = new BigInteger("3");
        TreeMap<BigInteger, Integer> factores = new TreeMap<>();
        System.out.println("Voy a factorizar " + num);

        // Mientras sea par, se irá dividiendo el número por 2
        while (num.remainder(dos).compareTo(zero) == 0) {
            num = num.divide(dos);
            factores.put(dos, factores.getOrDefault(dos, 0) + 1);
        }

        // Mientras el test sea menor o igual que el resto por factorizar
        while (test.compareTo(num) <= 0) {
            // Comprueba si el propio número es primo
            if (controlador.esPrimo(num)) {
                factores.put(num, factores.getOrDefault(num, 0) + 1);
                break;
            } else if ((controlador.esPrimo(test)) && (num.remainder(test).compareTo(zero) == 0)) {
                // Irá dividiendo el número por el factor test hasta que este deje de serlo
                while (num.remainder(test).compareTo(zero) == 0) {
                    num = num.divide(test);
                    factores.put(test, factores.getOrDefault(test, 0) + 1);
                }
            }
            // Solo probamos con los impares
            test = test.add(dos);
        }

        for(BigInteger factor : factores.keySet()) {
            System.out.println("   factor -------> " + factor + "  (x" + factores.get(factor) + ")");
        }
        System.out.println("He tardado " + (System.nanoTime() - temps) + "nanosec\n");
    }

}
