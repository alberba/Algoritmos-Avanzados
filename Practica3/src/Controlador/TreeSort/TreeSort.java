package Controlador.TreeSort;


import Modelo.Modelo;
import Modelo.Algoritmo;

import java.util.ArrayList;

public class TreeSort extends Thread {
    private final Modelo modelo;
    private TreeNode raiz;
    private final ArrayList<Double> arrayInicial;

    public TreeSort(ArrayList<Double> arrayInicial, Modelo modelo) {
        raiz = null;
        this.arrayInicial = arrayInicial;
        this.modelo = modelo;
    }

    public void run() {
        long t = System.nanoTime();
        sort();
        modelo.addTiempo(System.nanoTime() - t);
        modelo.añadirAlgoritmo(Algoritmo.TREESORT);
    }

    /**
     * Método para insertar un elemento dentro del array, simulando la ordenación TreeSort
     * @param nodo nodo padre
     * @param data Data a introducir
     * @return nodo padre actualizado
     */
    public TreeNode insert(TreeNode nodo, double data) {
        // Si el árbol está vacío, crea un nuevo nodo
        if (nodo == null) {
            nodo = new TreeNode(data);
            return nodo;
        }

        // Recorre el árbol para encontrar el lugar correcto para insertar el nuevo nodo
        if (data < nodo.getData()) {
            nodo.setLeft(insert(nodo.getLeft(), data));
        } else if (data > nodo.getData()) {
            nodo.setRight(insert(nodo.getRight(), data));
        }

        return nodo;
    }

    /**
     * Método para ordenar un array o conjunto de datos utilizando el mecanismo de TreeSort
     * @return Array ordenado
     */
    public ArrayList<Double> sort() {
        // Inserta todos los elementos del array
        for (double i: arrayInicial) {
            raiz = insert(raiz, i);
        }

        ArrayList<Double> aux = new ArrayList<>();
        return treeToArray(raiz);
    }

    /**
     * Método que convierte el árbol en un ArrayList ordenado
     * @param nodo Nodo raíz del árbol
     * @return Array ordenado
     */
    public ArrayList<Double> treeToArray(TreeNode nodo) {

        ArrayList<Double> aux = new ArrayList<>();
        if (nodo != null) {
            aux.addAll(treeToArray(nodo.getLeft()));
            aux.add(nodo.getData());
            aux.addAll(treeToArray(nodo.getRight()));
        }

        return aux;
    }

}
