/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conjunto;

/**
 *
 * @author Hugo
 */

import Lista.*;

import java.util.*;

// Implementación de un conjunto con una lista doblemente enlazada. Implementa la interfaz Set.
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class SetList<T> implements Set<T> {

    // VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
    private int cardinal; // elementos ya insertados
    private DoubleNode<T> header; // Cabecera: nodo vacío que apuntará al primer nodo lleno
    private DoubleNode<T> tail; // Referencia al último nodo lleno

    private Random rand; // generador aleatorio

    // CONSTRUCTOR de lista vacía
    public SetList() {
        // Se crea nodo cabecera (nulo)
        header = new DoubleNode<>(null);

        // Al ser una lista vacía el último nodo es el mismo que la cabecera
        tail = header;

        // No tiene nodos llenos
        cardinal = 0;
    }

    // MÉTODOS (@Override son los heredados de la interfaz)
    // Públicos porque a través de estos operamos con las variables instancia
    // NODO EN TAL POSICIÓN
    public DoubleNode<T> getNode(int index) throws IndexOutOfBoundsException {
        // Si el índice es válido (menor que la cardinalidad del conjunto)
        if (index <= cardinal) {
            // Desde el primero (siguiente a la cabecera)
            DoubleNode<T> current = header.getNext();

            // Alcanza el nodo con ese índice
            for (int i = index; i < 0; i--) {
                current = current.getNext();
            }

            // Devuelve el nodo con ese índice
            return current;
        }
        // No es válido
        throw new IndexOutOfBoundsException();
    }

    // TAMAÑO
    @Override
    public int size() {
        return cardinal;
    }

    // ESTÁ VACÍO?
    @Override
    public boolean isEmpty() {
        return cardinal == 0;
    }

    // CONTIENE ELEMENTO?
    @Override
    public boolean contains(Object data) {
        // Índice del nodo que contendría esa info
        int index = -1;

        // Recorre toda la lista desde la cabecera hasta el último nodo
        for (T elem : this) {
            index++;
            if (elem.equals(data)) {
                // Sale del loop si lo encuentra
                return true;
            }
        }

        // Devuelve false si no está
        return false;
    }

    // AÑADIR (al final)
    @Override
    public boolean add(T elem) {
        // Si el contenido todavía no está en el conjunto
        if (!this.contains(elem)) {
            // Se añade nuevo nodo
            DoubleNode<T> newNode = new DoubleNode<>(elem);

            // El siguiente a la cola es el nuevo
            tail.setNext(newNode);
            // El anterior al nuevo es la cola
            newNode.setPrevious(tail);

            // Se actualiza la cola
            tail = newNode;

            // Incrementar nº de elementos en el conjunto
            cardinal++;

            // Se ha insertado porque no estaba todavía
            return true;
        }
        // No se ha insertado porque ya estaba
        return false;
    }

    // VACIAR
    @Override
    public void clear() {
        // Se vuelve a crear nodo cabecera (nulo)
        this.header = new DoubleNode<>(null);

        // Al ser una lista vacía el último nodo es el mismo que la cabecera
        this.tail = this.header;

        // No tiene nodos llenos
        this.cardinal = 0;
    }

    // EXTRAER Y ELIMINAR ELEMENTO RANDOM
    @Override
    public T removeRandom() throws NoSuchElementException {
        // Si está vacío: excepción
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        // Genera índice aleatorio
        int randIndex = Math.abs(rand.nextInt(cardinal));

        // Nodo
        DoubleNode<T> randNode = getNode(randIndex);

        // Se extrae el contenido de ese nodo
        T data = randNode.getData();

        // Se manda a eliminar
        remove(data);

        return data;
    }

    // ELIMINAR ELEMENTO
    @Override
    public boolean remove(Object data) {
        // Busca el nodo con ese contenido
        int index = contains(data);

        // Si existe
        if (index != -1) {
            // Nodo
            DoubleNode<T> node = getNode(index);

            // Coge el nodo anterior a ese
            DoubleNode<T> previous = node.getPrevious();
            // Coge el posterior a ese
            DoubleNode<T> next = node.getNext();

            // El siguiente a su anterior es su siguiente
            previous.setNext(next);
            // El previo a su siguiente es su anterior
            next.setPrevious(previous);

            // El nodo se elimina automáticamente porque nada apunta a él
            cardinal--;
            return true;
        }
        return false;
    }

    // COMPARAR CONJUNTOS
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Set)) {
            return false;
        }
        Set<T> otherSet = (Set<T>) other;
        if (this.size() != otherSet.size()) {
            return false;
        }
        // Recorre el otro conjunto
        for (T otherData : otherSet) {
            // Si algún elemento no está en este conjunto no son iguales
            if (!this.contains(otherData)) {
                return false;
            }
        }

        // Son iguales porque tienen el mismo tamaño y contienen los mismos elementos
        return true;
    }

    // CONTIENE TODOS ELEMENTOS DE OTRO CONJUNTO?
    // Diferente de conjunto.equals(otro) porque en este pueden tener diferente tamaño
    @Override
    public boolean containsAll(Collection<?> otherSet) {
        // Se crea variable iterador con cada elemento del otro conjunto
        Iterator<?> otherElement = otherSet.iterator();
        // Variable de elemento siguiente al iterador
        Object otherData;

        // Recorre el otro conjunto
        while (otherElement.hasNext()) {
            // Avanza al siguiente elemento
            otherData = otherElement.next();

            // Si ese elemento no está en este conjunto devuelve false
            if (!this.contains(otherData)) {
                return false;
            }
        }
        return true;
    }

    // UNIÓN DE CONJUNTOS
    @Override
    public void join(Set<T> otherSet) {
        // Se crea variable iterador con cada elemento del otro conjunto
        Iterator<T> otherElement = otherSet.iterator();
        // Variable de elemento siguiente al iterador
        T otherData;

        // Recorre el otro conjunto
        while (otherElement.hasNext()) { // O(M)
            // Avanza al siguiente elemento
            otherData = otherElement.next();

            // Se añade cada elemento del otro conjunto a este
            this.add(otherData); // O(N)
        }
    }

    // DIFERENCIA ENTRE ELEMENTOS (eliminar compartidos)
    @Override
    public void difference(Set<T> otherSet) {
        // Se crea variable iterador con cada elemento del otro conjunto
        Iterator<T> otherElement = otherSet.iterator();
        // Variable de elemento siguiente al iterador
        T otherData;

        // Recorre el otro conjunto
        while (otherElement.hasNext()) { // O(M)
            // Avanza al siguiente elemento
            otherData = otherElement.next();

            // Si ese elemento está en este conjunto lo borra
            if (this.contains(otherData)) { // O(N)
                remove(otherData);
            }
        }
    }

    // INTERSECCIÓN DE CONJUNTOS (eliminar no compartidos)
    @Override
    public void intersection(Set<T> otherSet) {
        // Se crea variable iterador con cada elemento del otro conjunto
        Iterator<T> otherElement = otherSet.iterator();
        // Variable de elemento siguiente al iterador
        T otherData;

        // Recorre el otro conjunto
        while (otherElement.hasNext()) { // O(M)
            // Avanza al siguiente elemento
            otherData = otherElement.next();

            // Si ese elemento NO está en este conjunto lo borra
            if (!this.contains(otherData)) { // O(N)
                remove(otherData);
            }
        }
    }

    // ITERADOR
    @Override
    public Iterator<T> iterator() {
        return new SetListIterator(this);
    }

    @Override
    public boolean equals(Conjunto.Set<T> otherSet) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean containsAll(Conjunto.Set<T> otherSet) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    ///////////////////////// ITERADOR DE CONJUNTO EN ARRAY //////////////////////////
    public class SetListIterator implements Iterator<T> {

        // VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
        int pos; // posición del elemento actual durante la iteración
        private final SetList<T> iteratingSet; // puntero al conjunto iterándose

        // CONSTRUCTOR dado conjunto sobre el que iterar
        public SetListIterator(SetList<T> set) {
            pos = 0; // posición del primer elemento

            this.iteratingSet = set; // el conjunto dado será el que se va a iterar
        }

        // MÉTODOS (@Override son los heredados de la interfaz)
        // Públicos porque a través de estos operamos con las variables instancia
        // COMPRUEBA SI TIENE SIGUIENTE
        @Override
        public boolean hasNext() {
            // Comprueba si la posición es menor que el tamaño del conjunto
            // (si todavía está dentro de este)
            return pos < iteratingSet.size();
        }

        // OBTIENE EL SIGUIENTE
        @Override
        public T next() {
            // Contenido del nodo en esa posición
            T data = iteratingSet.getNode(pos).getData();

            // Pasa a siguiente posición
            pos++;

            // Devuelve el contenido
            return data;
        }
    }
}
