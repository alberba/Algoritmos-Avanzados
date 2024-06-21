package Controlador;

import Main.Main;
import Modelo.*;
import Notification.NotiEnum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.BitSet;
import java.io.BufferedWriter;
import java.io.FileWriter;



import java.math.BigInteger;
import java.util.Map;
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
        if (num.compareTo(TWO) == 0) { // num == 2
            return true;
        } else if (num.remainder(TWO).compareTo(ZERO) == 0) { // num par
            return false;
        } else if (num.compareTo(THREE) == 0) { // num == 3
            return true;
        } else { // num impar
            boolean fin = false;
            for (int i = 0; (i < tope) && !fin; i++) {
                //fin = primFermat(num);
                fin = primMillerRabin(num);
            }
            return fin;
        }

    }

    private boolean primFermat(BigInteger num) {
        BigInteger a = random(TWO, num.subtract(ONE));
        return a.modPow(num.subtract(ONE), num).compareTo(ONE) == 0;
    }

    private boolean primMillerRabin(BigInteger num) {
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
        res = a.modPow(dCandidato, num);
        // Si a^d mod n -> 1 o -1 (-1 cumple la segunda condición con r = 0)
        if ((res.compareTo(ONE) == 0) || (res.compareTo(num.subtract(ONE)) == 0)) { // mod == 1 o -1 (n-1)
            return true;
        }

        res = a.modPow(dCandidato, ONE); // x = a^d, uso modPow(d, 1) porque no existe a.pow(BigInteger)
        // Se recorren las posibles r (0 < r < s; r = 0 ya se ha comprobado) hasta encontrar una posibilidad
        // que cumpla la segunda condición (a^2r*d mod n = -1)
        for (BigInteger i = new BigInteger("1"); i.compareTo(s.subtract(ONE)) < 0; i = i.add(ONE)) {
            // Cada iteración se incrementa la r (potencia de 2), es decir, se eleva al cuadrado
            res = res.multiply(res); // res1 = a^d*a^d = (a^d)^2 = a^2d; res2 = a^2d*a^2d = (a^2d)^2 = a^4d...
            if (res.remainder(num).compareTo(num.subtract(ONE)) == 0) { // Se cumple la condición para un r
                return true;
            }
        }
        return false;
    }

    private BigInteger random(BigInteger a, BigInteger b) {
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

    /**
     * Función encargada de generar las claves pública y privada para el algoritmo RSA
     */
    public void RSA() {
        // Generar dos números primos grandes p y q
        BigInteger p = BigInteger.probablePrime(modelo.getBitLength() / 2, random);
        BigInteger q = BigInteger.probablePrime(modelo.getBitLength() / 2, random);

        // Calcular n = p * q y φ(n) = (p-1) * (q-1)
        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Seleccionar un exponente e coprimo con φ(n)
        BigInteger e = new BigInteger(modelo.getBitLength(), random);
        while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.ONE)) {
            e = new BigInteger(modelo.getBitLength(), random);
        }

        // Calcular d como el inverso modular de e mod φ(n)
        BigInteger d = e.modInverse(phi);
        ArrayList<BigInteger> claves = new ArrayList<>();
        claves.add(e);
        claves.add(d);
        claves.add(n);
        modelo.notificar(NotiEnum.SETCLAVES,claves);

        String clavesString = "Clave privada:\n" + d + "\nn:\n" + n + "\n\n\n" +
                "Clave pública:\n" + e;
        prog.getVista().notificar(NotiEnum.ADDOUTPUT, clavesString);

        // Almacenar claves en un fichero para poder ser copiadas
        try {
            BufferedWriter ficheroClaves = new BufferedWriter(new FileWriter("claves.txt"));
            ficheroClaves.write("Clave privada:\n\nd:\n" + d + "\nn:\n" + n + "\n\n\n");
            ficheroClaves.write("Clave pública:\n\ne:\n" + e + "\nn:\n" + n + "\n");
            ficheroClaves.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Función encargada de comprimir un archivo de texto mediante el algoritmo de Huffman
     * @param rutaArchivo Ruta del archivo a encriptar
     * @throws IOException Excepción en caso de error al leer el archivo
     */
    public void comprimirArchivo(String rutaArchivo) throws IOException {
        // Leer el contenido del archivo
        String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        Huffman huffman = new Huffman();

        // Obtener los códigos de Huffman para el contenido del archivo
        Map<Character, String> codigosHuffman = huffman.comprimirHuffman(contenido);

        // Serializar el árbol de Huffman
        String arbolSerializado = huffman.serializarArbol(codigosHuffman);

        // Escribir el árbol de Huffman en el archivo
        Files.write(Paths.get("archivo_comprimido.txt"), arbolSerializado.getBytes());

        // Escribir un delimitador especial para indicar donde termina el árbol de Huffman
        Files.write(Paths.get("archivo_comprimido.txt"), new byte[] {0}, StandardOpenOption.APPEND);

        // Reemplazar cada carácter en el contenido con su código de Huffman
        BitSet bits = new BitSet();
        int bitIndex = 0;
        for (char c : contenido.toCharArray()) {
            String codigoHuffman = codigosHuffman.get(c);
            for (char bit : codigoHuffman.toCharArray()) {
                if (bit == '1') {
                    bits.set(bitIndex);
                }
                bitIndex++;
            }
        }

        // Escribir el contenido comprimido en el archivo
        Files.write(Paths.get("archivo_comprimido.txt"), bits.toByteArray(), StandardOpenOption.APPEND);
    }

    /**
     * Función encargada de encriptar un archivo de texto mediante el algoritmo RSA
     * @param path Ruta del archivo a encriptar
     */
    public void encriptarArchivo(String path)  {
        int lastDotIndex = path.lastIndexOf(".");
        String base = path.substring(0, lastDotIndex);
        String extension = path.substring(lastDotIndex + 1);
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(path)));

            BufferedWriter ficheroEncriptado = new BufferedWriter(new FileWriter(base + "Encriptado." + extension));
            // Se encripta cada char del fichero y se almacena en el nuevo
            for (char m : contenido.toCharArray()) {
                BigInteger encriptado = encriptar(new BigInteger(String.valueOf((int) m)));
                ficheroEncriptado.write(encriptado.toString() + " "); // Guarda el valor encriptado como un String
            }
            ficheroEncriptado.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Función encargada de desencriptar un archivo de texto mediante el algoritmo RSA
     * @param path Ruta del archivo a desencriptar
     */
    public void desencriptarArchivo(String path) {
        String[] pathPartes = path.split("Encriptado");
        try {
            // Los números están divididos por espacios para poder ser leídos correctamente
            String[] caracteresEncriptados = (new String(Files.readAllBytes(Paths.get(path)))).split(" ");
            BufferedWriter ficheroDesencriptado = new BufferedWriter(new FileWriter(pathPartes[0] + "Desencriptado" + pathPartes[1]));
            // Se desencripta cada char del fichero y se almacena en el nuevo
            for (String caracterEncriptado: caracteresEncriptados) {
                ficheroDesencriptado.write(desencriptar(new BigInteger(caracterEncriptado)));
            }
            ficheroDesencriptado.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BigInteger encriptar(BigInteger m) {
        System.out.println("Valor original: " + m + " (" + (char) m.intValue() + ")");
        BigInteger[] clave = modelo.getClavePublica(); // [0] = e, [1] = n
        BigInteger num = m.modPow(clave[0], clave[1]);
        System.out.println("Valor encriptado: " + num);
        return num;
    }

    public char desencriptar(BigInteger c) {
        BigInteger[] clave = modelo.getClavePrivada(); // [0] = d, [1] = n
        BigInteger num = c.modPow(clave[0], clave[1]);
        // Mod 256 porque es el id máximo en ASCII
        //num = num.mod(new BigInteger("256"));
        System.out.println("Valor desencriptado: " + num + " (" + (char) num.intValue() + ")");
        return (char) num.intValue();
    }

}
