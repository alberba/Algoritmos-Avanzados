package Controlador;

import java.util.*;

public class Huffman {



    // Función para construir el árbol Huffman y obtener los códigos Huffman
    public Map<Character, String> comprimirHuffman(String contenido) {
        Map<Character, Integer> frecuencias = new HashMap<>();
        // Mira cuántas veces aparece cada carácter en la cadena de entrada
        for (char c : contenido.toCharArray()) {
            frecuencias.put(c, frecuencias.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<NodoHuffman> colaPrioridad = new PriorityQueue<>();
        // Crear un nodo hoja para cada carácter y añadirlo a la cola de prioridad
        for (Map.Entry<Character, Integer> entry : frecuencias.entrySet()) {
            colaPrioridad.offer(new NodoHuffman(entry.getValue(), entry.getKey()));
        }

        while (colaPrioridad.size() > 1) {
            NodoHuffman left = colaPrioridad.poll();
            NodoHuffman right = colaPrioridad.poll();
            // Se crea un nodo padre con la suma de las frecuencias de los dos nodos y se añade a la cola de prioridad
            NodoHuffman newNode = new NodoHuffman(left.getFrecuencia() + right.getFrecuencia(), '\0');
            newNode.setLeft(left);
            newNode.setRight(right);
            colaPrioridad.offer(newNode);
        }

        NodoHuffman root = colaPrioridad.poll();
        Map<Character, String> codigos = new HashMap<>();
        escribirCodigo(root, "", codigos);
        return codigos;
    }

    // Función recursiva para construir los códigos Huffman
    private void escribirCodigo(NodoHuffman node, String code, Map<Character, String> huffmanCodes) {
        if (node != null) {
            if (node.getLeft() == null && node.getRight() == null) {
                huffmanCodes.put(node.getDato(), code);
            }
            escribirCodigo(node.getLeft(), code + "0", huffmanCodes);
            escribirCodigo(node.getRight(), code + "1", huffmanCodes);
        }
    }

    public String serializarArbol(Map<Character, String> codigos) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Character, String> entry : codigos.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" ");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

}