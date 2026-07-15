/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lista;

/**
 *
 * @author Hugo
 */
import java.util.Iterator;

// Implementación de una lista con una lista enlazada simple. Implementa la interfaz List.
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class SimpleLinkedList<T> implements List<T> {

    // VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
    private SimpleNode<T> head; // Única referencia al primer nodo

    // CONSTRUCTOR de lista vacía
    public SimpleLinkedList() {
        head = null;
    }

    // MÉTODOS (@Override son los heredados de la interfaz)
    // Públicos porque a través de estos operamos con las variables instancia
    // COMPARA LISTAS////////////////////////////////////////////////////////////
    @Override
    public boolean equals(List<T> list) { // O(N)
        // comprueba si son del mismo tamaño
        if (this.size() == list.size()) {
            for (int i = 0; i < this.size(); i++) { // O(N)
                // devuelve falso si encuentra nodos diferentes
                if (this.get(i) != list.get(i)) {
                    return false;
                }
            }
            // devuelve verdadero si son del mismo tamaño y no ha encontrado nodos diferentes
            return true;
        }
        // devuelve falso si no son del mismo tamaño o ha encontrado nodos diferentes
        return false;
    }

    // CALCULA TAMAÑO DE LISTA///////////////////////////////////////////////////
    @Override
    public int size() { // O(N)
        // Se recorre la lista: se comienza desde la cabeza
        SimpleNode<T> current = head;

        // Contador de nodos
        int counter = 0;

        // Avanza y suma al contador hasta que pasa a un nodo vacío
        while (current != null) { // O(N)
            current = current.getNext();
            counter++;
        }

        // Devuelve el valor del contador (nº de nodos)
        return counter;
    }

    // ESTÁ VACÍA?///////////////////////////////////////////////////////////////
    @Override
    public boolean isEmpty() { // O(1)
        // Si la cabeza es nula, la lista está vacía
        return this.head == null; // O(1)
    }

    // VACIAR LISTA//////////////////////////////////////////////////////////////
    @Override
    public void clear() { // O(1)
        // Cabeza nula = lista vacía
        head = null; // O(1)

        // Se pierden los punteros de los siguientes nodos y se eliminan de la memoria automáticamente
    }

    // NODO TIENE CONTENIDO?/////////////////////////////////////////////////////
    @Override
    public boolean contains(T data) { // O(N)
        // Se recorre desde la cabeza
        SimpleNode<T> current = this.head;

        while (current != null) { // O(N)
            // Devuelve true si tiene ese contenido
            if (current.getData().equals(data)) {
                return true;
            }

            // Avanza al siguiente
            current = current.getNext();
        }
        // Devuelve falso si ha recorrido toda la lista y no hay nodo con ese contenido
        return false;
    }
    
    // AÑADIR AL PRINCIPIO///////////////////////////////////////////////////////
@Override
public void addBeg(T data) { // O(1)
    // Se crea nuevo nodo
    SimpleNode<T> newNode = new SimpleNode(data);

    // El nuevo nodo apunta a la cabeza de la lista
    newNode.setNext(head); // O(1)

    // La cabeza es el nuevo nodo
    this.head = newNode; // O(1)
}

// INSERTAR EN POSICIÓN//////////////////////////////////////////////////////
@Override
public void addPos(int pos, T data) throws IndexOutOfBoundsException { // O(N)
    // Comprueba si la posición es válida
    if (pos < 0 || pos > this.size()) { // O(N) por this.size()
        throw new IndexOutOfBoundsException();
    } // Se quiere insertar al principio
    else if (pos == 0) {
        addBeg(data); // O(1)
    } // Se quiere insertar al final
    else if (pos == this.size()) {
        addEnd(data); // O(N)
    } // Se quiere insertar en otra posición
    else {
        // Obtiene el nodo anterior al de la posición
        SimpleNode<T> previous = getNodePos(pos - 1); // O(N)

        // Obtiene el nodo en la posición
        SimpleNode<T> node = previous.getNext(); // O(1)

        // Crea nuevo nodo a insertar
        SimpleNode<T> newNode = new SimpleNode(data);

        // El siguiente al anterior será el nuevo
        previous.setNext(newNode);
        // El siguiente al nuevo será el nodo que estaba en esa posición
        newNode.setNext(node);
    }
}

// AÑADIR AL FINAL DADO CONTENIDO DE NODO////////////////////////////////////
@Override
public void addEnd(T data) { // O(N)
    // Se crea nuevo nodo con el contenido
    SimpleNode<T> newNode = new SimpleNode(data);

    // Si la lista está vacía: newNode = head
    if (this.isEmpty()) {
        head = newNode;
    } // O(1)
    else {
        // Se recorre la lista desde la cabeza
        SimpleNode<T> lastKnown = head;

        // Avanza de nodo en nodo mientras que su siguiente no sea nulo
        while (lastKnown.getNext() != null) { // O(N)
            lastKnown = lastKnown.getNext();
        }

        // Ahora lastKnown es el último nodo, cuya referencia será el nuevo nodo
        lastKnown.setNext(newNode);
    }
}

// BORRAR NODO CON CONTENIDO/////////////////////////////////////////////////
@Override
public int removeData(T data) { // O(N)
    // Obtiene la "posición" del nodo que contiene ese dato
    int index = posOf(data); // O(N)

    // Si está
    if (index != -1) {
        // Se borra el nodo en el índice que tenga ese contenido
        removePos(index); // O(N)
    }

    return index;
}
// BORRAR PRINCIPIO//////////////////////////////////////////////////////////
public void removeBeg() { // O(1)
    // El siguiente a la cabeza pasa a ser la nueva cabeza
    head = head.getNext(); // O(1)
    // El nodo que era la antigua cabeza se elimina automáticamente
}

// BORRAR EN POSICIÓN////////////////////////////////////////////////////////
@Override
public void removePos(int pos) throws IndexOutOfBoundsException { // O(N)
    // Comprueba si la posición es válida
    if (pos < 0 || pos >= this.size()) {
        throw new IndexOutOfBoundsException();
    } // Se quiere borrar al principio
    else if (pos == 0) { // O(1)
        this.removeBeg();
    } // Se quiere borrar al final
    else if (pos == this.size() - 1) { // O(N)
        this.removeEnd();
    } // Se quiere insertar en otra posición
    else {// O(N)
        // Obtiene el nodo anterior al de la posición
        SimpleNode<T> previousNode = getNodePos(pos - 1); // O(N)

        // Nodo posterior al de la posición O(1)
        SimpleNode<T> nextNode = previousNode.getNext().getNext(); // O(1)

        // El siguiente al anterior será el posterior
        previousNode.setNext(nextNode); // O(1)

        // El que estaba en esa posición se elimina automáticamente que no es apuntado por nada
    }
}

// BORRAR FINAL//////////////////////////////////////////////////////////////
public void removeEnd() { // O(N)
    // Se recorre la lista desde la cabeza
    SimpleNode<T> lastKnown = head;

    // Nodo posterior al actual
    SimpleNode<T> nextLast = lastKnown.getNext();

    // Recorre de nodo en nodo mientras que su siguiente no sea nulo
    while (nextLast.getNext() != null) { // O(N)
        lastKnown = lastKnown.getNext();
        nextLast = nextLast.getNext();
    }
    // Ahora el penúltimo nodo (el siguiente de su siguiente es nulo) no apunta a nada
    lastKnown.setNext(null);

    // El nodo que era el último se borra automáticamente
}

// OBTENER NODO EN POSICIÓN//////////////////////////////////////////////////
private SimpleNode<T> getNodePos(int pos) { // O(N)
    // La posición que le llegará será válida (hay un nodo)
    // pero no será la del primer ni último nodo

    // Se recorre la lista: se comienza desde la cabeza
    SimpleNode<T> current = head;

    // Avanza de nodo en nodo mientras que su siguiente no sea nulo y no
    // haya llegado a la posición deseada
    while (current.getNext() != null && pos > 0) { // O(N)
        current = current.getNext();
        pos--;
    }
    // Devuelve el nodo en esa posición
    return current;
}

// OBTENER CONTENIDO DE NODO EN POSICIÓN/////////////////////////////////////
@Override
public T get(int pos) throws IndexOutOfBoundsException { // O(N)
    // Comprueba si la posición es válida
    if (pos < 0 || pos >= this.size()) {
        throw new IndexOutOfBoundsException();
    } else {
        // Devuelve el contenido del nodo en esa posición
        return getNodePos(pos).getData(); // O(N)
    }
}

// OBTENER LA POSICIÓN DE UN NODO CON CONTENIDO//////////////////////////////
@Override
public int posOf(T data) { // O(N)
    // Contador de posición
    int pos = 0;

    // Se recorre la lista: se comienza desde la cabeza
    SimpleNode<T> current = head;

    // Avanza mientras el nodo no sea nulo
    while (current != null) { // O(N)
        // Devuelve la posición si tiene ese contenido
        if (current.getData().equals(data)) {
            return pos;
        }

        // Avanza al siguiente y suma a la posición
        current = current.getNext();
        pos++;
    }
    // No está
    return -1;
}

// OBTENER LA POSICIÓN DE UN NODO////////////////////////////////////////////
public int posOfNode(SimpleNode<T> node) { // O(N)
    // Contador de posición
    int pos = 0;

    // Se recorre la lista: se comienza desde la cabeza
    SimpleNode<T> current = head;

    // Avanza mientras el nodo no sea nulo
    while (current != null) { // O(N)
        // Devuelve la posición si tiene ese contenido
        if (current == node) {
            return pos;
        }

        // Avanza al siguiente y suma a la posición
        current = current.getNext();
        pos++;
    }
    // No está
    return -1;
}

// MODIFICAR CONTENIDO DE NODO EN POSICIÓN////////////////////////////////////
@Override
public void set(int pos, T data) throws IndexOutOfBoundsException { // O(N)
    // Comprueba si la posición es válida
    if (pos < 0 || pos >= this.size()) {
        throw new IndexOutOfBoundsException();
    } else {
        getNodePos(pos).setData(data); // O(N)
    }
}

// CLONAR LISTA//////////////////////////////////////////////////////////////
@Override
public List<T> clone() { // O(N²) pq recorre dos listas de tamaño N
    // Se crea nueva lista
    List<T> clone = new SimpleLinkedList<>();

    // Para cada nodo de esta lista se copia su contenido en el final de la lista clon
    for (T elem : this) { // O(N)
        clone.addEnd(elem); // O(N)
    }
    // Devuelve la lista clon
    return clone;
}

// COPIAR LISTA DESDE UNA POSICIÓN///////////////////////////////////////////
public List<T> copyPos(int pos) { // O(N²) pq recorre dos listas de tamaño N (peor caso: copiar desde principio)
    // Comprueba si la posición es válida
    if (pos < 0 || pos > this.size()) {
        throw new IndexOutOfBoundsException();
    } else {
        // Se crea lista copia
        List<T> copy = new SimpleLinkedList<>();

        // Se recorrerá desde el nodo en esa posición
        SimpleNode<T> current = getNodePos(pos);

        // El peor caso es q quiera copiar la lista desde el comienzo, por lo que
        // la clonaría, creando otra del mismo tamaño
        while (current != null) { // O(N²) = O(N) de addEnd * O(N) del while
            // Mientras q no sea nulo, añade nodo con valor en copia y avanza
            copy.addEnd(current.getData()); // O(N)
            current = current.getNext();
        }

        // Devuelve la nueva lista
        return copy;
    }
}

// COPIAR LISTA DESDE UN NODO////////////////////////////////////////////////
private List<T> copyNode(SimpleNode<T> nodoRef) { // O(N²)
    // Posición de ese nodo
    int posNodoRef = posOfNode(nodoRef); // O(N)

    // Se crea lista copia a partir de copiar desde la posición del nodo
    List<T> copy = copyPos(posNodoRef); // O(N²)

    // Se devuelve la nueva lista
    return copy;
}

// IMPRIME LISTA/////////////////////////////////////////////////////////////
@Override
public void print() { // O(N)
    // Nodo actual y último conocido, comienza en la cabeza
    SimpleNode<T> current = this.head;

    // Tamaño
    int size = this.size();

    // Tamaño 0: lista vacía
    if (size == 0) {
        System.out.println("Lista vacía.");
    } // Lista no vacía
    else {
        // Mientras el nodo actual no sea nulo, reconoce su posición e imprime su valor
        while (current != null) {
            // Cabeza (único nodo)
            if (current == this.head && this.head.getNext() == null) {
                System.out.print("[Head: " + current.getData() + "]");
                // Pasa al siguiente nodo
                current = current.getNext();
            } // Cabeza y más nodos
            else if (current == this.head && this.head.getNext() != null) {
                System.out.print("[Head: " + current.getData() + ",");
                // Pasa al siguiente nodo
                current = current.getNext();
            } // Cola: no apunta a nada (siguiente nulo)
            else if (current != this.head && current.getNext() == null) {
                System.out.print(" Tail: " + current.getData() + "]");
                // Pasa al siguiente nodo
                current = current.getNext();
            } // Nodo interno: no es cabeza ni cola
            else if (current != this.head && current.getNext() != null) {
                System.out.print(" " + current.getData() + ",");
                // Pasa al siguiente nodo
                current = current.getNext();
            }
        }
        System.out.println(" Tamaño: " + size);
    }
}

@Override
public Iterator<T> iterator() {
    throw new UnsupportedOperationException("Not supported yet.");
}

}

