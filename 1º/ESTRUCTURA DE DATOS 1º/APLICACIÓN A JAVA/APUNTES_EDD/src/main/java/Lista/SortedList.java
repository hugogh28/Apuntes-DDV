/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lista;

/**
 *
 * @author Hugo
 * @param <T>
 */
// Implementación de una lista ordenada con una lista enlazada doble
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class SortedList<T> {

    // VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
    private DoubleNode<T> head; // Referencia al primer nodo
    private int size; // Tamaño: número de nodos

    // CONSTRUCTOR de lista vacía
    public SortedList() {
        head = null;
        size = 0;
    }

    // MÉTODOS: públicos porque a través de estos operamos con las variables instancia

    // COMPARA LISTAS
    public boolean equals(SortedList<T> list) { // O(N)
        // Comprueba si son del mismo tamaño
        if (this.size() == list.size()) {
            for (int i = 0; i < this.size(); i++) { // O(N)
                // Devuelve falso si encuentra nodos diferentes
                if (!this.get(i).equals(list.get(i))) {
                    return false;
                }
            }
            // Devuelve verdadero si son del mismo tamaño y no ha encontrado nodos diferentes
            return true;
        }
        // Devuelve falso si no son del mismo tamaño o ha encontrado nodos diferentes
        return false;
    }

    // CALCULA TAMAÑO DE LISTA
    public int size() { // O(1)
        return size;
    }

    // ESTÁ VACÍA?
    public boolean isEmpty() { // O(1)
        // Si la cabeza es nula, la lista está vacía
        return this.head == null; // O(1)
    }

    // VACIAR LISTA
    public void clear() { // O(1)
        // Cabeza nula = lista vacía
        head = null; // O(1)
        size = 0;
        // Se pierden los punteros de los siguientes nodos y se eliminan de la memoria automáticamente
    }
    
    // NODO TIENE CONTENIDO?
public boolean contains(T data) { // O(N)
    // Se recorre desde la cabeza
    DoubleNode<T> current = this.head;

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

// OBTIENE CONTENIDO DE NODO EN POSICIÓN
public T get(int pos) throws IndexOutOfBoundsException { // O(N)
    // Comprueba si la posición es válida
    if (pos < 0 || pos >= this.size()) {
        throw new IndexOutOfBoundsException();
    } // Se quiere obtener el contenido de la cabeza
    else if (pos == 0) {
        return head.getData();
    } // Se quiere obtener el contenido de otro nodo
    else {
        return getNodePos(pos).getData(); // O(N)
    }
}

// OBTIENE NODO EN POSICIÓN
private DoubleNode<T> getNodePos(int pos) { // O(N)
    // La posición que le llegará será válida (hay un nodo)
    // pero no será la del primer ni último nodo

    // Se recorre la lista: se comienza desde el siguiente a la cabecera
    DoubleNode<T> current = head;

    // Avanza desde cabecera en [-1] hasta la posición deseada
    for (int i = -1; i < pos; i++) {
        current = current.getNext();
    }

    // Devuelve el nodo en esa posición
    return current;
}

// AÑADIR EN ORDEN
public boolean add(T data) {
    // Se crea nuevo nodo
    DoubleNode<T> newNode = new DoubleNode<>(data);

    // Nodo que recorre la lista desde la cabeza
    DoubleNode<T> current = head;

    // Si está vacía
    if (isEmpty()) {
        head = newNode;
        return true;
    }

    // Recorre toda la lista
    for (int i = 0; i < size; i++) {
        // Evitar repeticiones
        if (current.getData().equals(data)) {
            return false;
        } // La info del nodo actual es mayor que la del nuevo nodo: se pone antes
        else if (current.getData().compareTo(data) > 0) {
            // Se coge el nodo anterior
            DoubleNode<T> previousNode = current.getPrevious();
            // Se pone el nuevo antes del actual
            current.setPrevious(newNode);
            newNode.setNext(current);
            // Se pone el nuevo después del anterior
            previousNode.setNext(newNode);
            newNode.setPrevious(previousNode);

            return true;
        } // La info del nodo actual es menor que la del nuevo nodo: pasa al siguiente nodo
        else if (current.getData().compareTo(data) < 0) {
            current = current.getNext();
        }
    }

    // Ha recorrido toda la lista y no se ha insertado porque ningún nodo es mayor: se inserta al final
    current.setNext(newNode);
    newNode.setPrevious(current);
    return true;
}

// BORRA NODO DADO CONTENIDO
public int removeData(T data) { // O(N)
    // Obtiene la "posicion" del nodo que contiene ese dato
    int index = posOf(data); // O(N)

    // Si está
    if (index != -1) {
        // Se borra el nodo en el índice que tenga ese contenido
        removePos(index); // O(N)
    }

    return index;
}

// OBTIENE LA POSICIÓN DE UN NODO CON CONTENIDO
public int posOf(T data) { // O(N)
    // Contador de posición
    int pos = 0;

    // Se recorre la lista: se comienza desde la cabeza
    DoubleNode<T> current = head;

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

// BORRAR EN POSICIÓN
public void removePos(int pos) throws IndexOutOfBoundsException { // O(N)
    // Comprueba si la posición es válida
    if (pos < 0 || pos >= this.size()) {
        throw new IndexOutOfBoundsException();
    } // Se quiere borrar al principio
    else if (pos == 0) { // O(1)
        head = head.getNext();
    } // Se quiere borrar al final
    else if (pos == this.size() - 1) { // O(N)
        this.removeEnd();
    } // Se quiere borrar en otra posición
    else { // O(N)
        // Obtiene el nodo anterior al de la posición
        DoubleNode<T> previousNode = getNodePos(pos - 1); // O(N)

        // Nodo posterior al de la posición
        DoubleNode<T> nextNode = getNodePos(pos + 1); // O(N)

        // El siguiente al anterior será el posterior
        previousNode.setNext(nextNode); // O(1)

        // El que estaba en esa posición se elimina automáticamente ya que no es apuntado por nada
    }
}

// BORRAR FINAL
public void removeEnd() { // O(N)
    // Se recorre la lista desde la cabeza
    DoubleNode<T> lastKnown = head;
    // Nodo posterior al actual
    DoubleNode<T> nextLast = lastKnown.getNext();

    // Recorre de nodo en nodo mientras que su siguiente no sea nulo
    while (nextLast.getNext() != null) { // O(N)
        lastKnown = lastKnown.getNext();
        nextLast = nextLast.getNext();
    }
    // Ahora el penúltimo nodo (el siguiente de su siguiente es nulo) no apunta a nada
    lastKnown.setNext(null);

    // El nodo que era el último se borra automáticamente
}


}

