package Controlador;

import Main.Main;
import Notification.NotiEnum;
import Vista.Vista;
import Modelo.Modelo;

import java.math.BigInteger;
import java.util.TreeMap;

public class Factorizador extends Thread {
    private final Controlador controlador;
    private final Vista vista;
    private final Modelo modelo;
    private String n;

    public Factorizador(Main p, Controlador controlador, String n) {
        this.vista = p.getVista();
        this.controlador = controlador;
        this.modelo = p.getModelo();
        this.n = n;
    }

    public void run() {
        factorizar();
        if (modelo.getCifras().size() > 1) {
            System.out.println("Entró en el if");
            vista.notificar(NotiEnum.PINTARGRAFICO, null);
        }
    }

    /**
     * Factoriza un número
     */
    public void factorizar() {
        BigInteger num = new BigInteger(n);
        BigInteger zero = new BigInteger("0");
        BigInteger dos = new BigInteger("2");
        BigInteger tres = new BigInteger("3");
        BigInteger test = new BigInteger("5");
        TreeMap<BigInteger, Integer> factores = new TreeMap<>();
        vista.notificar(NotiEnum.ADDOUTPUT, "Voy a factorizar " + num + "\n");
        vista.notificar(NotiEnum.ADDOUTPUT, "Estimación de tiempo: " + polinomioNewton(n.length()) + " segundos\n");

        long temps = System.nanoTime();
        // Mientras sea par, se irá dividiendo el número por 2
        while (num.remainder(dos).compareTo(zero) == 0) {
            num = num.divide(dos);
            factores.put(dos, factores.getOrDefault(dos, 0) + 1);
        }
        // De igual forma mientras sea múltiplo de 3
        while (factor3(num)) {
            num = num.divide(tres);
            factores.put(tres, factores.getOrDefault(tres, 0) + 1);
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
        temps = System.nanoTime() - temps;

        for(BigInteger factor : factores.keySet()) {
            vista.notificar(NotiEnum.ADDOUTPUT, "   factor -------> " + factor + "  (x" + factores.get(factor) + ")\n");
        }
        vista.notificar(NotiEnum.ADDOUTPUT, "He tardado " + temps + " nanosec\n");
        // Se almacenan los resultados
        modelo.notificar(NotiEnum.ADDTIEMPO, new long[]{(long) n.length(), temps});
    }

    /**
     * Devuelve true si el valor es divisible por 3,
     * lo es cuando la suma de sus dígitos da un múltiplo de 3
     * @param num
     * @return
     */
    private boolean factor3(BigInteger num) {
        // Se recorren los dígitos de num y se suman
        int suma = 0;
        for (char c : num.toString().toCharArray()) {
            suma += c - '0';
        }
        return suma % 3 == 0;
    }

    private double polinomioNewton(int n) {   // Estimación de un polinomio aproximado en segundos
        double res = 0.0;
        res = res + ((109787.0 / 9216000.0) * n * n * n * n * n * n);
        res = res - ((970007.0 / 1536000.0) * n * n * n * n * n);
        res = res + ((31353317.0 / 2304000.0) * n * n * n * n);
        res = res - ((58362589.0 / 384000.0) * n * n * n);
        res = res + ((33275477.0 / 36000.0) * n * n);
        res = res - ((23120021.0 / 8000.0) * n);
        res = res + (3612189.0 / 1000.0);
        return res;
    }

}
