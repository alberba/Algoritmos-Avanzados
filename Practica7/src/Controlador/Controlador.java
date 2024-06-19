package Controlador;

import Main.Main;
import Modelo.*;
import Notification.NotiEnum;
import java.util.ArrayList;



import java.math.BigInteger;
import java.util.Random;

public class Controlador {

    private final Main prog;
    private final Modelo modelo;
    private final Random random = new Random();
    private final int tope = 100;

    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");

    public Controlador(Main p) {
        this.prog = p;
        this.modelo = p.getModelo();
    }

    public boolean esPrimo(BigInteger num) {
        return Miller_Rabin(num);
    }

    private boolean primFermat(String n) {
        BigInteger num = new BigInteger(n);

        if (num.compareTo(TWO) == 0) { // num == 2
            return true;
        } else if (num.remainder(TWO).compareTo(ZERO) == 0) { // num par
            return false;
        } else if (num.compareTo(THREE) == 0) { // num == 3
            return true;
        } else { // num impar
            for (int i = 0; i < tope; i++) {
                BigInteger a = random(TWO, num.subtract(ONE));
                if (a.modPow(num.subtract(ONE), num).compareTo(ONE) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean primMillerRabin(String n) {
        BigInteger num = new BigInteger(n);

        if (num.compareTo(TWO) == 0) { // num == 2
            return true;
        } else if (num.remainder(TWO).compareTo(ZERO) == 0) { // num par
            return false;
        } else if (num.compareTo(THREE) == 0) { // num == 3
            return true;
        } else { // num impar
            for (int j = 0; j < tope; j++) {
                BigInteger a = random(TWO, num.subtract(TWO));
                BigInteger s, dCandidato, res;
                s = new BigInteger("0");
                dCandidato = num.subtract(ONE);
                // Se recorren las posibles d y s hasta encontrar una posibilidad (d impar),
                // por cada iteración se incrementa s y se ajusta d al nuevo valor (se divide entre 2)
                do {
                    s = s.add(ONE);
                    dCandidato = dCandidato.divide(TWO);
                } while ((dCandidato.remainder(TWO).compareTo(ONE) != 0));
                /*
                for (; dCandidato.remainder(TWO).compareTo(ZERO) == 0; s = s.add(ONE)) {
                    dCandidato = dCandidato.divide(TWO);
                }
                 */
                res = a.modPow(dCandidato, num);
                // Si a^d mod n -> 1 o -1 (-1 cumple la segunda condición con r = 0)
                if ((res.compareTo(ONE) == 0) || (res.compareTo(num.subtract(ONE)) == 0)) { // mod == 1 o -1 (n-1)
                    return true;
                }
                /*
                res = a.modPow(dCandidato, ONE); // x = a^d, uso modPow(d, 1) porque no existe a.pow(BigInteger)
                //BigInteger a2 = a.pow(2);
                // Se recorren las posibles r (0 < r < s; r = 0 ya se ha comprobado) hasta encontrar una posibilidad
                // que cumpla la segunda condición (a^2r*d mod n = -1)
                for (BigInteger i = new BigInteger("1"); i.compareTo(s.subtract(ONE)) < 0; i = i.add(ONE)) {
                    // Cada iteración se incrementa la r (potencia de 2), es decir, se eleva al cuadrado
                    res = res.multiply(res); // res1 = a^d*a^d = (a^d)^2 = a^2d; res2 = a^2d*a^2d = (a^2d)^2 = a^4d...
                    if (res.remainder(num).compareTo(num.subtract(ONE)) == 0) { // Se cumple la condición para un r
                        return true;
                    }
                }
                */

                BigInteger i;
                for (i = new BigInteger("0"); (i.compareTo(s.subtract(ONE)) == -1); i = i.add(ONE)) {
                    res = res.multiply(res);
                    res = res.remainder(num);
                    if (res.compareTo(num.subtract(ONE)) == 0) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    private BigInteger random(BigInteger a, BigInteger b) {
        // random entre 2 y n-2. En este caso entre a y b
        BigInteger res = null;
        String aux = "";
        int n = b.toString().length();
        for (int i = 0; i < n; i++) {
            int k = 48 + random.nextInt(10);
            aux = aux + (char) (k);
        }
        res = new BigInteger(aux);
        if (res.compareTo(a) == -1) {  // menor que la base
            res = random(a, b);
        } else if (res.compareTo(b) == 1) {  // mayor que el tope
            res = random(a, b); // llamada recursiva poco probable
        }
        return res;
    }

    private boolean Miller_Rabin(BigInteger n) {
        // n > 4 e impar
        BigInteger a;
        BigInteger dos = new BigInteger("2");
        // Entero al azar entre 2 y n-1
        a = random(dos, n.subtract(ONE));
        return B(a, n);
    }

    private boolean B(BigInteger a, BigInteger n) {
        boolean res = true;
        BigInteger s, t, x, i;
        BigInteger uno = new BigInteger("1");
        BigInteger dos = new BigInteger("2");
        boolean stop;
        s = new BigInteger("0");  // s = 0
        t = n.subtract(uno);   // t = n - 1
        do {
            s = s.add(uno);     // s++
            t = t.divide(dos);          // t = t / 2
        } while ((t.remainder(dos).compareTo(uno)) != 0);   // no puede ser módulo 1
        x = a.modPow(t, n);   // a elevado a t módulo n
        if ((x.compareTo(uno) == 0) || (x.compareTo(n.subtract(uno)) == 0)) {    // si es 1 ó n-1
            res = true;
        } else {
            stop = false;
            for (i = new BigInteger("0"); (i.compareTo(s.subtract(uno)) == -1) && (!stop); i = i.add(uno)) {
                x = x.multiply(x);
                x = x.remainder(n);
                if (x.compareTo(n.subtract(uno)) == 0) {
                    stop = true;
                }
            }
            res = stop;
        }
        return res;
    }

    public void RSA() {
        // Generar dos números primos grandes p y q
        BigInteger p = BigInteger.probablePrime(modelo.getBitLength()/ 2, random);
        BigInteger q = BigInteger.probablePrime(modelo.getBitLength()/ 2 / 2, random);

        // Calcular n = p * q y φ(n) = (p-1) * (q-1)
        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Seleccionar un exponente e coprimo con φ(n)
        BigInteger e = new BigInteger(modelo.getBitLength()/ 2 , random);
        while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.ONE)) {
            e = new BigInteger(modelo.getBitLength()/ 2 , random);
        }

        // Calcular d como el inverso modular de e mod φ(n)
        BigInteger d = e.modInverse(phi);
        ArrayList<BigInteger> claves = new ArrayList<>();
        claves.add(e);
        claves.add(d);
        claves.add(n);
        modelo.notificar(NotiEnum.SETCLAVES,claves);

    }

}
