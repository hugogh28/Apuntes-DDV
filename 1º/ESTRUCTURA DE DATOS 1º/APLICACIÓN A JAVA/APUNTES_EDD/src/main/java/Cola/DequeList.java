/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cola;

/**
 *
 * @author Hugo
 */
// Implementación de una BICOLA con una linkedlist doble. Implementa la interfaz Deque.
import Lista.*;
import java.util.NoSuchElementException;


// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class DequeList<T> implements Deque<T> {
    // VARIABLES INSTANCIA: privadas para q no se pueda acceder directamente a ellas

    private DoubleNode<T> header; // Cabecera
    private DoubleNode<T> last; // Último nodo en entrar
    private int numNodes; // Nº de nodos en la cola

    // CONSTRUCTOR de bicola vacía
    public DequeList() {
        // Referencias nulas
        header = new DoubleNode<>(null);
        header = last;
        // Tiene 0 nodos
        numNodes = 0;
    }

    @Override
    public int size() { // O(1)
        return this.numNodes;
    }

    @Override
    public boolean isEmpty() { // O(1)
        return this.size() == 0;
        // return header.getNext() == null;
    }

    @Override
    public T front() { // O(1)
        // Si la bicola está vacía: excepción
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            // Primero con contenido es el siguiente a la cabecera
            DoubleNode<T> first = header.getNext();
            // Devuelve su contenido
            return first.getData();
        }
    }

    @Override
    public T back() { // O(1)
        // Si la bicola está vacía: excepción
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            // Devuelve contenido del último (al final)
            return last.getData();
        }
    }
    
    @Override
public void addFirst(T data) { // O(1)
    // Primer nodo que tendrá contenido (siguiente a cabecera)
    DoubleNode<T> first = header.getNext();

    // Si el primero es nulo
    if (first == null) {
        // Se añade por el final
        addLast(data); // O(1)
    } else {
        // Se crea nuevo nodo a partir de ese contenido
        DoubleNode<T> newNode = new DoubleNode<>(data);

        // El siguiente al nuevo nodo es el primero con contenido
        newNode.setNext(first);
        // El anterior al primero con contenido es el nuevo
        first.setPrevious(newNode);
        // El siguiente a la cabecera pasa a ser el nuevo (se convierte en el primero con contenido)
        header.setNext(newNode);
        // El anterior al nuevo es la cabecera
        newNode.setPrevious(header);

        // Incrementamos el nº de nodos
        numNodes++;
    }
}

@Override
public void addLast(T data) { // O(1)
    // Se crea nuevo nodo a partir de ese contenido
    DoubleNode<T> newNode = new DoubleNode(data);

    // Si cola vacía: nuevo nodo es el primero y el último (único)
    if (this.isEmpty()) {
        // El siguiente a la cabecera es el nuevo
        header.setNext(newNode);
        // El previo al nuevo es la cabecera
        newNode.setPrevious(header);
        // El nuevo se convierte en el último
        last = newNode;
    } else { // Si no, nuevo nodo siguiente a último (no sería el único)
        // El último en introducirse apunta al nuevo
        last.setNext(newNode); // O(1)
        // El anterior al nuevo es el último
        newNode.setPrevious(last);
        // Y el nuevo se convierte en el último
        last = newNode;
    }

    // Aumenta el nº de nodos
    numNodes++;
}

@Override
public T removeFirst() { // O(1)
    // Si la bicola está vacía: excepción
    if (this.isEmpty()) {
        throw new NoSuchElementException();
    } else {
        // Primero con contenido es el siguiente a la cabecera
        DoubleNode<T> first = header.getNext();

        // Extrae el contenido del principio
        T data = first.getData();

        // Cabecera apunta al siguiente del primero
        header.setNext(first.getNext());
        // El siguiente del primero apunta a cabecera
        first.getNext().setPrevious(header);

        // Reduce el nº de nodos
        numNodes--;

        // Devuelve el contenido que había
        return data;
    }
}

@Override
public T removeLast() { // O(1)
    // Si la bicola está vacía: excepción
    if (this.isEmpty()) {
        throw new NoSuchElementException();
    } else {
        // Extrae el contenido del final
        T data = last.getData();

        // Penúltimo nodo
        DoubleNode<T> penultimate = last.getPrevious();

        // El siguiente del anterior al último se hace nulo
        penultimate.setNext(null);
        // El anterior al último se hace nulo
        last.setPrevious(null);
        // El último pasa a ser el anterior
        last = penultimate;

        // Reduce el nº de nodos
        numNodes--;

        // Devuelve el contenido que había al final
        return data;
    }
}

@Override
public void clear() { // O(1)
    // Referencias vuelven a ser nulas
    header = new DoubleNode<>(null);
    header = last;
    // Tiene 0 nodos
    numNodes = 0;
}

}
