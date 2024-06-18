package Controlador;

import java.util.*;

public class Hoffman {

    // Clase para almacenar nodos del árbol Huffman
    static class HuffmanNode implements Comparable<HuffmanNode> {
        int frequency;
        char data;
        HuffmanNode left, right;

        HuffmanNode(int freq, char data) {
            this.frequency = freq;
            this.data = data;
            left = right = null;
        }

        @Override
        public int compareTo(HuffmanNode o) {
            return this.frequency - o.frequency;
        }
    }

    // Función para construir el árbol Huffman y obtener los códigos Huffman
    public Map<Character, String> buildHuffmanTree(String input) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            pq.offer(new HuffmanNode(entry.getValue(), entry.getKey()));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode newNode = new HuffmanNode(left.frequency + right.frequency, '\0');
            newNode.left = left;
            newNode.right = right;
            pq.offer(newNode);
        }

        HuffmanNode root = pq.poll();
        Map<Character, String> huffmanCodes = new HashMap<>();
        buildCode(root, "", huffmanCodes);
        return huffmanCodes;
    }

    // Función recursiva para construir los códigos Huffman
    private void buildCode(HuffmanNode node, String code, Map<Character, String> huffmanCodes) {
        if (node != null) {
            if (node.left == null && node.right == null) {
                huffmanCodes.put(node.data, code);
            }
            buildCode(node.left, code + "0", huffmanCodes);
            buildCode(node.right, code + "1", huffmanCodes);
        }
    }


}