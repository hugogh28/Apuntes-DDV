/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pila;

/**
 *
 * @author Hugo
 */

import Lista.*;
import java.util.EmptyStackException;

// Implementación de una pila con una simple linkedlist. Implementa la interfaz Stack.
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class StackList<T> implements Stack<T> {

    // VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
    private SimpleNode<T> top; // Cima: último nodo en insertarse
    private int numNodes; // Nº de nodos en la pila

    // CONSTRUCTOR de pila vacía
    public StackList() {
        // Cima nula
        top = null;
        // Tiene 0 nodos
        numNodes = 0;
    }

    // MÉTODOS (@Override son los heredados de la interfaz)
    // Públicos porque a través de estos operamos con las variables instancia
    // TAMAÑO
    @Override
    public int size() {
        // Es el nº de nodos
        return numNodes;
    }

    // ESTÁ VACÍA?
    @Override
    public boolean isEmpty() {
        // Está vacía si el tamaño es 0
        return (size() == 0);
    }

    // CONTENIDO DEL ÚLTIMO ELEMENTO
    @Override
    public T topData() throws EmptyStackException {
        // Si está vacía
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            // Si no, devuelve el contenido de la cima
            return top.getData();
        }
    }

    // APILAR (desde cabecera: es como addBeg())
    @Override
    public void push(T data) {
        // Se crea nuevo nodo con ese contenido
        SimpleNode<T> newNode = new SimpleNode<>(data);

        // Si está vacía el nuevo nodo es la cima
        if (isEmpty()) {
            top = newNode;
        } else {
            // Si no (cima no es nula), el siguiente al nuevo es la cima
            newNode.setNext(top);
            // Ahora la cima es el nuevo
            top = newNode;
        }
        // Se aumenta el nº de nodos
        numNodes++;
    }

    // DESAPILAR
    @Override
    public T pop() throws EmptyStackException {
        // Si está vacía
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            // Si no, obtiene el contenido de la cima
            T data = top.getData();
            // Se actualiza la cima al siguiente nodo
            top = top.getNext();
            // Se reduce el nº de nodos
            numNodes--;
            // Devuelve el contenido que había en la cima
            return data;
        }
    }

    // VACIAR
    @Override
    public void clear() throws EmptyStackException {
        // Si está vacía
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            // Si no, la cima no contiene nada
            top = null;
            // La pila no tiene nodos
            numNodes = 0;
        }
    }
}
