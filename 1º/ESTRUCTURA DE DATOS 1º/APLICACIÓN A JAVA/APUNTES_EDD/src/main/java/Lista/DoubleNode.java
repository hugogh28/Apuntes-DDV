/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lista;

/**
 *
 * @author Hugo
 */
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class DoubleNode<T> {

    // VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
    private T data; // Contenido
    private DoubleNode<T> next = null; // Referencia al siguiente nodo
    private DoubleNode<T> previous = null; // Referencia al nodo anterior

    // CONSTRUCTOR de nodo dado su contenido
    public DoubleNode(T data) {
        this.data = data;
    }

    // MÉTODOS: públicos porque a través de estos operamos con las variables instancia
    // Getter del contenido
    public T getData() {
        return data;
    }

    // Getter del siguiente nodo
    public DoubleNode<T> getNext() {
        return next;
    }

    // Getter del nodo anterior
    public DoubleNode<T> getPrevious() {
        return previous;
    }

    // Setter del contenido
    public void setData(T data) {
        this.data = data;
    }

    // Setter del siguiente nodo
    public void setNext(DoubleNode<T> next) {
        this.next = next;
    }

    // Setter del nodo anterior
    public void setPrevious(DoubleNode<T> previous) {
        this.previous = previous;
    }
}

