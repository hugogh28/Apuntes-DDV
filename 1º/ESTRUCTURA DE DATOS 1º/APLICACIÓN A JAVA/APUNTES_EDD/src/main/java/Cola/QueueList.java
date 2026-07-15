/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cola;

/**
 *
 * @author Hugo
 */

import Lista.*;
import java.util.NoSuchElementException;

// Implementación de una cola con una simple linkedlist. Implementa la interfaz Queue.
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class QueueList<T> implements Queue<T> {

    // VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
    private SimpleNode<T> first; // Primer nodo en salir
    private SimpleNode<T> last; // Último nodo en entrar
    private int numNodes; // Nº de nodos en la cola

    // CONSTRUCTOR de cola vacía
    public QueueList() {
        // Referencias nulas
        first = last = null;
        // Tiene 0 nodos
        numNodes = 0;
    }

    // MÉTODOS (@Override son los heredados de la interfaz)
    // Públicos porque a través de estos operamos con las variables instancia
    // TAMAÑO
    @Override
    public int size() {
        return this.numNodes;
    }

    // ESTÁ VACÍA?
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    // CONTENIDO AL PRINCIPIO
    @Override
    public T front() throws NoSuchElementException {
        // Si la cola está vacía: excepción
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            // Devuelve contenido del primero (al principio)
            return first.getData();
        }
    }

    // ENCOLAR (es como addEnd())
    @Override
    public void add(T data) {
        // Se crea nuevo nodo a partir de ese contenido
        SimpleNode<T> newNode = new SimpleNode<>(data);

        // Si cola vacía: nuevo nodo es el primero y el último (único)
        if (this.isEmpty()) {
            first = last = newNode;
        } else {
            // Si no, nuevo nodo siguiente a último (no sería el único)
            // El último en introducirse apunta al nuevo
            last.setNext(newNode);
            // Y el nuevo se convierte en el último
            last = newNode;
        }

        // Aumenta el nº de nodos
        numNodes++;
    }

    // ELIMINAR PRINCIPIO
    @Override
    public T remove() {
        // Si la cola está vacía: excepción
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            // El siguiente del primer nodo pasa a ser el nuevo primero
            T data = first.getData();
            first = first.getNext();
            // Reduce el nº de nodos
            numNodes--;
            return data;
        }
    }

    // VACIAR COLA
    @Override
    public void clear() {
        // Si la cola está vacía: excepción
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            // Referencias vuelven a ser nulas
            first = last = null;
            // No tiene nodos
            numNodes = 0;
        }
    }
}
