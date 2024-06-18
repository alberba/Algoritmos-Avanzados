package Controlador;

import Main.Main;
import Modelo.*;
import Notification.NotiEnum;
import java.util.ArrayList;



import java.math.BigInteger;
import java.util.Random;

public class Controlador {

    private final Modelo modelo;
    private final Random random = new Random();

    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");

    public Controlador(Main p) {
        modelo = p.getModelo();
        Factorizador factorizador = new Factorizador(this);
    }

    public void run() {
    }

    public boolean esPrimo(BigInteger num) {
        return primMillerRabin(num.toString());
    }

    private boolean primFermat(String n) {
        BigInteger num = new BigInteger(n);

        if (num.compareTo(TWO) == 0) { // num == 2
            return true;
        } else if (num.remainder(TWO).compareTo(ZERO) == 0) { // num par
            return false;
        } else { // num impar
            BigInteger a = random(TWO, num.subtract(ONE));
            if (a.modPow(num.subtract(ONE), num).compareTo(ONE) == 0) {
                return true;
            }
        }
        return true;
    }

    private boolean primMillerRabin(String n) {
        BigInteger num = new BigInteger(n);

        if (num.compareTo(TWO) == 0) { // num == 2
            return true;
        } else if (num.remainder(TWO).compareTo(ZERO) == 0) { // num par
            return false;
        } else { // num impar
            BigInteger a = random(TWO, num.subtract(ONE));
            BigInteger s, dCandidato, x;
            s = new BigInteger("0");
            dCandidato = num.subtract(ONE);
            // Se recorren las posibles d y s hasta encontrar una posibilidad (d impar),
            // por cada iteración se incrementa s y se ajusta d al nuevo valor (se divide entre 2)
            for (; dCandidato.remainder(TWO).compareTo(ZERO) == 0; s = s.add(ONE)) {
                dCandidato = dCandidato.divide(TWO);
            }
            x = a.modPow(dCandidato, num);
            // Si a^d mod n -> 1 o -1 (-1 cumple la segunda condicion con r = 0)
            if (x.compareTo(ONE) == 0 || x.compareTo(num.subtract(ONE)) == 0) { // mod == 1 o -1 (n-1)
                return true;
            }
            BigInteger res = a.modPow(dCandidato, ONE); // x = a^d, uso modPow(d, 1) porque no existe a.pow(BigInteger)
            BigInteger a2 = a.pow(2);
            // Se recorren las posibles r (0 < r < s; r = 0 ya se ha comprobado) hasta encontrar una posibilidad
            // que cumpla la segunda condición (a^2r*d mod n = -1)
            for (BigInteger i = new BigInteger("1"); i.compareTo(s.subtract(ONE)) < 0; i = i.add(ONE)) {
                // Cada iteración se incrementa la r (potencia de 2), es decir, se eleva al cuadrado
                res = res.multiply(a2); // res = a^((2^r)*d)*a^2 = a^(2^(r+1)*d)
                if (res.remainder(num).compareTo(num.subtract(ONE)) == 0) { // Se cumple la condición para un r
                    return true;
                }
            }
        }
        return false;
    }

    private BigInteger random(BigInteger a, BigInteger b) {
        // Random entre 2 y n-2. En este caso entre a y b
        BigInteger res = null;
        String aux = "";
        int n = b.toString().length();
        for (int i = 0; i < n; i++) {
            int k = 48 + random.nextInt(10);
            aux = aux + (char) (k);
        }
        res = new BigInteger(aux);
        if (res.compareTo(a) < 0) {  // menor que la base
            res = random(a, b);
        } else if (res.compareTo(b) > 0) {  // mayor que el tope
            res = random(a, b); // llamada recursiva poco probable
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
        prog.getModelo().notificar(NotiEnum.SETCLAVES,claves);

    }

}
