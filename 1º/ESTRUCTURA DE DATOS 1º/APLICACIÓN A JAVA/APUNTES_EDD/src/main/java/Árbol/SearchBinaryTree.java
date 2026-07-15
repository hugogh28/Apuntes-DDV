/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Árbol;

/**
 *
 * @author Hugo
 */
import java.util.NoSuchElementException;
import java.util.Random;

public class SearchBinaryTree<T extends Comparable> {

    // VARIABLES INSTANCIA: privadas para q no se pueda acceder directamente a ellas
    private T data; // Contenido en nodo actual (raíz)
    private T min, max; // Valores mínimo y máximo del subárbol
    private int height; // Altura del árbol

    private SearchBinaryTree<T> left; // referencia a la raíz del subárbol (hijo) izquierdo
    private SearchBinaryTree<T> right; // referencia a la raíz del subárbol (hijo) derecho

    Random ranGenerator; // Clase generadora de números aleatorios

    // CONSTRUCTORES
    // Constructor de árbol con contenido pero sin "nodos"
    public SearchBinaryTree(T data) {
        this.data = this.min = this.max = data;
        this.height = 1;
        left = right = null;
        this.ranGenerator = new Random(System.currentTimeMillis());
    }

    // MÉTODOS (@Override son los heredados de la interfaz)
    // Públicos pq a traves de estos operamos con las variables instancia
    // ES ÁRBOL DE BÚSQUEDA?//////////////////////////////////////////////
    // Comprueba si la raíz es mayor que el máximo del subárbol derecho y menor que el mínimo del subárbol derecho
    public boolean isSearchTree() { // O(N) donde N es el nº de nodos
        // Si tiene hijo izquierdo
        if (left != null) {
            // Comprueba que la raíz sea mayor que el máximo del subárbol izquierdo
            if (data.compareTo(left.max) > 0) {
                return left.isSearchTree(); // O(N)
            } // Si no no es un árbol de búsqueda
            return false;
        }

        // Si tiene hijo derecho
        if (right != null) {
            // Comprueba que la raíz sea menor que el mínimo del subárbol izquierdo
            if (data.compareTo(right.min) < 0) {
                return right.isSearchTree(); // O(N)
            } // Si no no es un árbol de búsqueda
            return false;
        }

        // Si llega hasta aquí es de búsqueda
        return true;
    }
    
    // ES ÁRBOL BALANCEADO??//////////////////////////////////////////////
    // Sí si la altura entre el subárbol izquierdo y el derecho no difiere en más de un nivel
    public boolean isBalanced() { // O(N) donde N es el nº de nodos
        int heightLeft = 0;
        int heightRight = 0;

        if (left != null) {
            heightLeft = left.height;
        }
        if (right != null) {
            heightRight = right.height;
        }

        boolean balanced = Math.abs(heightLeft - heightRight) <= 1;

        boolean balancedLeft = true;
        boolean balancedRight = true;

        if (left != null) {
            balancedLeft = left.isBalanced(); // O(N)
        }
        if (right != null) {
            balancedRight = right.isBalanced(); // O(N)
        }

        return balanced && balancedLeft && balancedRight;
    }

    // ESTÁ VACÍO?///////////////////////////////////////////////////////////////
    public boolean isEmpty() { // O(1)
        // Si su raíz no tiene contenido
        return data == null;
    }

    // VACIAR////////////////////////////////////////////////////////////////////
    public void clearChildren() { // O(N)
        // Borra subárbol izquierdo de forma recursiva mientras haya
        if (left != null) {
            left.clearChildren(); // O(N)
            left = null;
        } // Borra subárbol derecho de forma recursiva mientras haya
        else if (right != null) {
            right.clearChildren(); // O(N)
            right = null;
        }

        this.data = this.min = this.max = null;
        this.height = 1;
        this.ranGenerator = new Random(System.currentTimeMillis());
    }

    // ES HOJA?////////////////////////////////////////////////////////////////////////
    public boolean isLeaf() { // O(1)
        // Si no hay ni izquierdo ni derecho
        return (getLeft() == null && getRight() == null);
    }

    // CONTIENE ELEMENTO?////////////////////////////////////////////////////////
    public boolean contains(T data) { // O(N) donde N es el nº de nodos
        // No si no está en ningún subárbol (si es nulo)
        return this.findElement(data) == null; // O(N)
    }

    // OBTIENE SUBÁRBOL QUE NACE EN RAÍZ CON ESE CONTENIDO (PREORDEN)////////////
    public SearchBinaryTree<T> findElement(T data) { // O(N) donde N es el nº de nodos
        // Comprueba si está vacío
        if (this.isEmpty()) {
            return null;
        } // Comprueba si es igual q la raíz
        else if (data.compareTo(data) == 0) {
            return this;
        } // Comprueba si es menor (izquierda)
        else if (data.compareTo(data) <= 0) {
            return left.findElement(data); // O(N)
        } // Comprueba si es mayor (derecho)
        else if (data.compareTo(data) >= 0) {
            return right.findElement(data); // O(N)
        } // No está
        return null;
    }

    // OBTIENE CONTENIDO EN RAÍZ/////////////////////////////////////////////////
    public T getRoot() { // O(1)
        if (isEmpty()) {
            throw new NoSuchElementException("Error en getRoot(): árbol vacío");
        }
        // Si tiene contenido lo devuelve
        return data;
    }

    // OBTIENE SUBÁRBOL IZQUIERDO////////////////////////////////////////////////
    public SearchBinaryTree<T> getLeft() { // O(1)
        if (isEmpty()) {
            throw new NoSuchElementException("Error en getLeft(): árbol vacío");
        }
        // Devuelve subárbol derecho si no está vacío su padre
        return left;
    }

    // OBTIENE SUBÁRBOL DERECHO//////////////////////////////////////////////////
    public SearchBinaryTree<T> getRight() { // O(1)
        if (isEmpty()) {
            throw new NoSuchElementException("Error en getRight(): árbol vacío");
        }
        // Devuelve subárbol derecho si no está vacío su padre
        return right;
    }

    // MODIFICA RAÍZ/////////////////////////////////////////////////////////////
    public T setRoot(T newData) { // O(1)
        if (isEmpty()) {
            throw new NoSuchElementException("Error en setRoot(): árbol vacío");
        }

        // Si no está vacío, guarda su contenido actual
        T oldData = this.data;

        // Se actualiza el contenido de la raíz
        this.data = newData; // O(1)

        // Se devuelve el contenido anterior
        return oldData;
    }

    // INSERTAR EN ORDEN/////////////////////////////////////////////////////////////
    public void sortedInsert(T data) {
        // Comprueba si es menor que la raíz
        boolean smaller = data.compareTo(this.data) <= 0;

        // Si es menor que la raíz
        if (smaller) {
            if (left == null) {
                // Se crea nuevo subárbol izquierdo si no existe
                left = new SearchBinaryTree(data);
            } else {
                // Si existe, vuelve a llamar a la función
                left.sortedInsert(data);
            }
        } else {
            // Si es mayor que la raíz
            if (right == null) {
                // Se crea nuevo subárbol derecho si no existe
                right = new SearchBinaryTree(data);
            } else {
                // Si existe, vuelve a llamar a la función
                right.sortedInsert(data);
            }
        }

        // Coge la altura de cada subárbol, si está nulo es 0 y sino es su altura
        int leftHeight = (left == null) ? 0 : left.height;
        int rightHeight = (right == null) ? 0 : right.height;

        // Actualiza la altura del subárbol sumándole 1 a la mayor altura de sus hijos
        this.height = 1 + Math.max(leftHeight, rightHeight);

        // Si el mínimo sigue siendo menor que el valor introducido se mantiene, sino se actualiza
        this.min = this.min.compareTo(data) <= 0 ? min : data;
        this.max = this.max.compareTo(data) >= 0 ? max : data;
    }

    // INSERTAR LO MÁS A LA IZQUIERDA POSIBLE
    public void insertLeft(T data) throws UnsupportedOperationException {
        // Si el subárbol izquierdo es nulo (no existe)
        if (left == null) {
            // Crea uno
            left = new SearchBinaryTree(data);

            // Coge la altura de cada subárbol, si está nulo es 0 y sino es su altura
            int leftHeight = (left == null) ? 0 : left.height;
            int rightHeight = (right == null) ? 0 : right.height;

            // Actualiza la altura del subárbol sumándole 1 a la mayor altura de sus hijos
            this.height = 1 + Math.max(leftHeight, rightHeight);

            // Si el mínimo sigue siendo menor que el valor introducido se mantiene, sino se actualiza
            this.min = this.min.compareTo(data) <= 0 ? min : data;
            this.max = this.max.compareTo(data) >= 0 ? max : data;
        } // Sino, inserta en orden por la izquierda
        else {
            left.sortedInsert(data);
        }
    }

    // INSERTAR LO MÁS A LA DERECHA POSIBLE
    public void insertRight(T data) throws UnsupportedOperationException {
        // Si el subárbol derecho es nulo (no existe)
        if (right == null) {
            // Crea uno
            right = new SearchBinaryTree(data);

            // Coge la altura de cada subárbol, si está nulo es 0 y sino es su altura
            int leftHeight = (left == null) ? 0 : left.height;
            int rightHeight = (right == null) ? 0 : right.height;

            // Actualiza la altura del subárbol sumándole 1 a la mayor altura de sus hijos
            this.height = 1 + Math.max(leftHeight, rightHeight);

            // Si el mínimo sigue siendo menor que el valor introducido se mantiene, sino se actualiza
            this.min = this.min.compareTo(data) <= 0 ? min : data;
            this.max = this.max.compareTo(data) >= 0 ? max : data;
        } // Sino, inserta en orden por la derecha
        else {
            right.sortedInsert(data);
        }
    }

    // BORRAR MÍNIMO
    public BinaryTree<T> removeLeft() throws NoSuchElementException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // BORRAR MÁXIMO
    public BinaryTree<T> removeRight() throws NoSuchElementException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // IMPRIME EN PREORDEN
    public String printPreOrder() { // O(N)
        if (isEmpty()) {
            throw new NoSuchElementException("Error en printPreOrder(): árbol vacío");
        }

        String text = "";

        // Pasa el contenido a String
        text += data.toString(); // O(1)

        // Si tiene hijo izquierdo
        if (left != null) {
            // Lo imprime y continúa con su subárbol
            text += " " + left.printPreOrder(); // O(N)
        }

        // Si tiene hijo derecho
        if (right != null) {
            // Lo imprime y continúa con su subárbol
            text += " " + right.printPreOrder(); // O(N)
        }

        // Devuelve el árbol en String, listo para hacer sout
        return text;
    }

    // IMPRIME EN INORDEN
    public String printInOrder() { // O(N)
        if (isEmpty()) {
            throw new NoSuchElementException("Error en printInOrder(): árbol vacío");
        }

        String text = "";

        // Si tiene hijo izquierdo
        if (left != null) {
            // Lo imprime y continúa con su subárbol
            text += " " + left.printInOrder(); // O(N)
        }

        // Pasa el contenido a String
        text += data.toString(); // O(1)

        // Si tiene hijo derecho
        if (right != null) {
            // Lo imprime y continúa con su subárbol
            text += " " + right.printInOrder(); // O(N)
        }

        // Devuelve el árbol en String, listo para hacer sout
        return text;
    }

    // IMPRIME EN POSTORDEN
    public String printPostOrder() { // O(N)
        if (isEmpty()) {
            throw new NoSuchElementException("Error en printPostOrder(): árbol vacío");
        }

        String text = "";

        // Si tiene hijo izquierdo
        if (left != null) {
            // Lo imprime y continúa con su subárbol
            text += " " + left.printPostOrder(); // O(N)
        }

        // Si tiene hijo derecho
        if (right != null) {
            // Lo imprime y continúa con su subárbol
            text += " " + right.printPostOrder(); // O(N)
        }

        // Pasa el contenido a String
        text += data.toString(); // O(1)

        // Devuelve el árbol en String, listo para hacer sout
        return text;
    }

}

