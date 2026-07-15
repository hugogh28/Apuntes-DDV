/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Árbol;

/**
 *
 * @author Hugo
 */

import Cola.*;
import Pila.*;
import java.util.NoSuchElementException;

// Implementación de un árbol binario enlazado en memoria dinámica. Implementa la interfaz Binary tree.
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class LinkedBinaryTree<T> implements BinaryTree<T>{

    // VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
    private T data; // Contenido en nodo actual (raíz)
    private LinkedBinaryTree<T> parent; // Referencia a (la raíz del subárbol) padre
    private LinkedBinaryTree<T> left; // Referencia a la raíz del subárbol (hijo) izquierdo
    private LinkedBinaryTree<T> right; // Referencia a la raíz del subárbol (hijo) derecho

    // CONSTRUCTORES
    // Constructor de árbol con contenido pero sin "nodos"
    public LinkedBinaryTree(T data) {
        this.data = data;
        parent = left = right = null;
    }

    // Constructor de árbol dado contenido de raíz y padre
    public LinkedBinaryTree(T data, LinkedBinaryTree<T> parent) {
        this.data = data;
        this.parent = parent;
        left = right = null;
    }

    // Constructor de árbol dado subárbol izquierdo, el nodo padre y el subárbol izquierdo
    public LinkedBinaryTree(T data, LinkedBinaryTree<T> parent, LinkedBinaryTree<T> left, LinkedBinaryTree<T> right) {
        this.data = data;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    // MÉTODOS (@Override son los heredados de la interfaz)
    // Públicos porque a través de estos operamos con las variables instancia
    // ESTÁ VACÍO?
    @Override
    public boolean isEmpty() { // O(1)
        // Si su raíz no tiene contenido
        return data == null;
    }

    // VACIAR
    @Override
    public void clearChildren() { // O(N)
        // Borra subarbol izquierdo de forma recursiva mientras haya
        if (left != null) {
            left.clearChildren(); // O(N)
            left.parent = null;
            left = null;
        } // Borra subarbol derecho de forma recursiva mientras haya
        else if (right != null) {
            right.clearChildren(); // O(N)
            right.parent = null;
            right = null;
        }
    }

    // ES HOJA?
    @Override
    public boolean isLeaf() { // O(1)
        // Si no hay ni izquierdo ni derecho
        return (getLeft() == null && getRight() == null);
    }

    // CONTIENE ELEMENTO?
    @Override
    public boolean contains(T data) {
        // Lo busca en profundidad en preorden
        return containsDepthPreOrder(this, data);
    }

    // BÚSQUEDA EN PREORDEN
    private boolean containsDepthPreOrder(LinkedBinaryTree<T> root, T data) { // O(N)
        // No está contenido si nodo está vacío
        if (root.isEmpty()) {
            return false;
        }

        // Comprueba si el contenido está en la propia raíz
        if (root.data.equals(data)) {
            return true;
        }

        // Si no, comprueba si está en el subárbol izquierdo o derecho
        return (containsDepthPreOrder(root.left, data) || containsDepthPreOrder(root.right, data));
    }

    // BÚSQUEDA EN INORDEN
    private boolean containsDepthInOrder(LinkedBinaryTree<T> root, T data) { // O(N)
        // No está contenido si nodo está vacío
        if (root.isEmpty()) {
            return false;
        }

        // Comprueba si el contenido está en el subárbol izquierdo
        if (containsDepthPreOrder(root.left, data)) {
            return true;
        }

        // Si no, comprueba si está en la propia raíz o en el subárbol derecho
        return (root.data.equals(data) || containsDepthPreOrder(root.right, data));
    }

    // BÚSQUEDA EN POSTORDEN
    private boolean containsDepthPostOrder(LinkedBinaryTree<T> root, T data) { // O(N)
        // No está contenido si nodo está vacío
        if (root.isEmpty()) {
            return false;
        }

        // Comprueba si el contenido está en el subárbol izquierdo
        if (containsDepthPreOrder(root.left, data)) {
            return true;
        }

        // Si no, comprueba si está en la propia raíz o en el subárbol derecho
        return (containsDepthPreOrder(root.right, data) || root.data.equals(data));
    }

    // BÚSQUEDA EN PREORDEN CON PILA ENLAZADA
    private boolean containsDepthPreOrderStack(LinkedBinaryTree<T> root, T data) { // O(N)
        // Crea una pila enlazada que almacena objetos de tipo (raíz/nodo de) árbol
        Stack<LinkedBinaryTree<T>> stack = new StackList<>();

        // Añade el árbol al principio de la pila (apilar)
        stack.push(root);

        // Mientras que no se termine
        while (!stack.isEmpty()) { // O(N)
            // Extrae el último nodo en insertarse (desapilar)
            LinkedBinaryTree<T> newRoot = stack.pop();

            // 1. Busca en la raíz
            if (newRoot.data.equals(data)) {
                return true;
            } // 2. Busca en el subárbol izquierdo
            else if (newRoot.left != null) {
                // Añade el hijo izquierdo a la pila para que continúe la búsqueda (el loop)
                stack.push(newRoot.left);
            } // 3. Busca en el subárbol derecho
            else if (newRoot.right != null) {
                // Añade el hijo izquierdo a la pila para que continúe la búsqueda (el loop)
                stack.push(newRoot.right);
            }
        }
        // No está
        return false;
    }

    // BÚSQUEDA EN ANCHURA CON COLA ENLAZADA
    private boolean containsWidthQueue(LinkedBinaryTree<T> root, T data) { // O(N)
        // Crea una cola enlazada que almacena objetos de tipo (raíz/nodo de) árbol
        Queue<LinkedBinaryTree<T>> queue = new QueueList<>();

        // Añade el árbol al final da cola (encolar)
        queue.add(root);

        // Mientras que no se termine
        while (!queue.isEmpty()) { // O(N)
            // Extrae el último primer en insertarse (desapilar)
            LinkedBinaryTree<T> newRoot = queue.remove();

            // 1. Busca en la raíz
            if (newRoot.data.equals(data)) {
                return true;
            } // 2. Busca en el subárbol izquierdo si hay
            else if (newRoot.left != null) {
                // Añade el hijo izquierdo a la cola para que continúe la búsqueda (el loop)
                queue.add(newRoot.left);
            } // 3. Busca en el subárbol derecho si hay
            else if (newRoot.right != null) {
                // Añade el hijo izquierdo a la cola para que continúe la búsqueda (el loop)
                queue.add(newRoot.right);
            }
        }
        // No está
        return false;
    }

    // OBTIENE SUBARBOL QUE NACE EN RAÍZ CON ESE CONTENIDO (PREORDEN)
    @Override
    public BinaryTree<T> findElement(T data) { // O(N)
        // 1. Comprueba si está en la raíz
        if (this.data.equals(data)) {
            return this;
        // Si no, pasa a sus hijos
        } else {
            // Subárbol que cuya raíz tiene ese contenido
            BinaryTree<T> subTree;

            // 2. Comprueba si está en el izquierdo (si existe)
            if (left != null) {
                subTree = left.findElement(data); // O(N)
                if (subTree != null) {
                    return subTree;
                }
            } // 3. Comprueba si está en su hijo derecho (si existe)
            else if (right != null) {
                subTree = right.findElement(data); // O(N)
                if (subTree != null) {
                    return subTree;
                }
            }
        }
        // No está
        return null;
    }

    // OBTIENE CONTENIDO EN RAÍZ
    @Override
    public T getRoot() { // O(1)
        if (isEmpty()) {
            throw new NoSuchElementException("Error en getRoot(): árbol vacío");
        }
        // Si tiene contenido lo devuelve
        return data;
    }
        // OBTIENE SUBÁRBOL IZQUIERDO
    @Override
    public LinkedBinaryTree<T> getLeft() { // O(1)
    if (isEmpty()) {
        throw new NoSuchElementException("Error en getLeft(): árbol vacío");
    }
    // Devuelve subárbol derecho si no está vacío su padre
    return left;
}

    // OBTIENE SUBÁRBOL DERECHO
    @Override
    public LinkedBinaryTree<T> getRight() { // O(1)
    if (isEmpty()) {
        throw new NoSuchElementException("Error en getRight(): árbol vacío");
    }
    // Devuelve subárbol derecho si no está vacío su padre
    return right;
}

    // MODIFICA RAÍZ
    @Override
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

    // INSERTA IZQUIERDA
    @Override
    public void insertLeft(T data) throws UnsupportedOperationException { // O(1)
    if (isEmpty()) {
        throw new NoSuchElementException("Error en insertLeft(): árbol vacío");
    }

    // Si ya tiene hijo izquierdo no se puede crear uno nuevo
    if (this.left != null) {
        throw new UnsupportedOperationException();
    }

    // Si es nulo puede crear el nuevo hijo
    this.left = new LinkedBinaryTree<T>(data, this); // O(1)
}

    // INSERTA DERECHA
    @Override
    public void insertRight(T data) throws UnsupportedOperationException { // O(1)
    if (isEmpty()) {
        throw new NoSuchElementException("Error en insertRight(): árbol vacío");
    }

    // Si ya tiene hijo derecho no se puede crear uno nuevo
    if (this.right != null) {
        throw new UnsupportedOperationException();
    }

    // Si es nulo puede crear el nuevo hijo
    this.right = new LinkedBinaryTree<T>(data, this); // O(1)
}

    // ELIMINA SUBÁRBOL IZQUIERDO
    @Override
    public BinaryTree<T> removeLeft() throws NoSuchElementException { // O(1)
    if (isEmpty()) {
        throw new NoSuchElementException("Error en removeLeft(): árbol vacío");
    }

    // Si es nulo no se puede borrar
    if (left == null) {
        throw new NoSuchElementException();
    }

    // Se guarda el subárbol a eliminar
    LinkedBinaryTree<T> subTree = left;

    // Elimina su padre y su contenido
    left.parent = null; // O(1)
    left = null;

    // Devuelve el árbol eliminado
    return subTree;
}

    // ELIMINA SUBÁRBOL DERECHO
    @Override
    public BinaryTree<T> removeRight() throws NoSuchElementException { // O(1)
    if (isEmpty()) {
        throw new NoSuchElementException("Error en removeLeft(): árbol vacío");
    }

    // Si es nulo no se puede borrar
    if (right == null) {
        throw new NoSuchElementException();
    }

    // Se guarda el subárbol a eliminar
    LinkedBinaryTree<T> subTree = right;

    // Elimina su padre y su contenido
    right.parent = null; // O(1)
    right = null;

    // Devuelve el árbol eliminado
    return subTree;
}

    // IMPRIME EN PREORDEN
    @Override
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
    @Override
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
    @Override
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

    // NÚMERO DE NODOS
    // Método estático: no se puede hacer this.método() sino método(this)
    public static int numNodes(LinkedBinaryTree tree) { // O(N)
    int numNodes;

    // Si está vacío
    if (tree.isEmpty()) {
        // No tiene ningún nodo
        numNodes = 0;
    } // Añade la raíz actual y recorre sus hijos
    else {
        numNodes = 1 + numNodes(tree.getLeft()) + numNodes(tree.getRight()); // O(N)
    }

    return numNodes;
}

    // NÚMERO DE HOJAS
    // Método estático: no se puede hacer this.método() sino método(this)
    public static int numLeafs(LinkedBinaryTree tree) { // O(N)
    int numLeafs;

    // Si está vacío
    if (tree.isEmpty()) {
        // No tiene ningún nodo
        numLeafs = 0;
    } // Es hoja si no tiene hijos
    else if (tree.getLeft() == null && tree.getRight() == null) {
        numLeafs = 1;
    } // Recorre sus hijos
    else {
        numLeafs = numLeafs(tree.getLeft()) + numLeafs(tree.getRight()); // O(N)
    }

    return numLeafs;
}

    // CALCULAR NIVEL DE CONTENIDO
    // Calcula el nivel en el que aparece la primera vez el contenido desde TAL nivel
    public static int dataLevel(T data, LinkedBinaryTree tree, int nivel) {
    // No está si el árbol está vacío
    if (tree.isEmpty()) {
        nivel = 0;
    } // Está en la raíz
    else if (tree.getRoot().equals(data)) {
        nivel++;
    }

    // Elige el menor nivel en el que aparece el contenido entre el árbol izquierdo y derecho
    nivel = minNum(dataLevel(data, tree.getLeft(), nivel), dataLevel(data, tree.getRight(), nivel));

    return nivel;
}

    // CUÁL ES MENOR?
    private static int minNum(int a, int b) {
    // Si a es mayor o igual a b devuelve b
    if (a >= b) {
        return b;
    }// Si no, devuelve b
    return a;
}
    // CALCULAR PROFUNDIDAD
    // Método estático: no se puede hacer this.método() sino método(this)
    public static int depth(LinkedBinaryTree tree) // O(N)
{
    // nº de niveles
    int levels;

    // Si está vacío
    if (tree.isEmpty()) {
        // No tiene ningún nodo
        levels = 0;
    } // Añade la raíz actual y recorre sus hijos
    else {
        levels = 1 + maxNum(depth(tree.getLeft()), depth(tree.getRight())); // O(N)
    }

    return levels;
}

    // CUÁL ES MAYOR?
    private static int maxNum(int a, int b) {
    // Si a es mayor o igual a b devuelve a
    if (a >= b) {
        return a;
    }// Si no, devuelve b
    return b;
}

    // SON ÁBOLES IGUALES?
    // Deben ser exactamente iguales (recorrido en preorden)
    public static boolean equals(LinkedBinaryTree treeA, LinkedBinaryTree treeB) {
    boolean equal;

    // Iguales si están vacíos ambos
    if (treeA.isEmpty() && treeB.isEmpty()) {
        return true;
    } // Diferentes si solo uno está vacío
    else if (treeA.isEmpty() || treeB.isEmpty()) {
        return false;
    }// Recorre para comparar cada subárbol (En preOrden)
    else {
        // Compara la raíz
        equal = (treeA.getRoot().equals(treeB.getRoot()))
        // Compara sus respectivos subárboles izquierdos
        && equals(treeA.getLeft(), treeB.getLeft())
        // Compara sus respectivos subárboles derechos
        && equals(treeA.getRight(), treeB.getRight());
    }
    return equal;
}

    // ÁRBOL ESPEJO
    // Lo crea copiando en su subárbol izquierdo el derecho del original y en su subárbol derecho su izquierdo
    public LinkedBinaryTree<T> mirror() {
    LinkedBinaryTree<T> mirror;

    // Si está vacío no se puede hacer espejo
    if (isEmpty()) {
        return null;
    }
    // Se crea nuevo árbol (data, padre, subárbol izquierdo (será el derecho), subárbol derecho (será el izquierdo)
    mirror = new LinkedBinaryTree<>(this.getRoot(), this, this.getRight(), this.getLeft());

    return mirror;
}
}



