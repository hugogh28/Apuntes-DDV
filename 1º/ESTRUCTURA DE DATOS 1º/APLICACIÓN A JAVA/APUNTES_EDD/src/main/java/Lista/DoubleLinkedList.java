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

public class DoubleLinkedList<T> implements List<T> {

    private DoubleNode<T> header;
    private DoubleNode<T> tail;
    private int size;

    DoubleLinkedList() {
header = new DoubleNode<>(null);
tail = header;
size = 0;

}
    

    @Override
    public boolean equals(List<T> list) {
if (this.size() == list.size()) {
Iterator listIterator = list.iterator();
for (T elem : this) {
if (!elem.equals(listIterator.next())) {
return false;
}
}
return true;
}
return false;
}

    @Override
    public int size() {
return this.size;
}

    @Override
    public boolean isEmpty() {
return header.getNext() == null;
}

    @Override
    public void clear() {
this.header = new DoubleNode<>(null);
this.tail = this.header;
this.size = 0;
}

    @Override
    public boolean contains(T data) {
for (T elem : this) {
if (elem.equals(data)) {
return true;
}
}
return false;
}

    //AÑADIR AL PRINCIPIO///////////////////////////////////////////////////////
    @Override
    public void addBeg(T data) //O(1)
    {
    //Nuevo nodo con ese contenido
    DoubleNode<T> newNode = new DoubleNode<>(data);

    //El anterior al siguiente a la cabezera es el nuevo
    header.getNext().setPrevious(newNode);
    //El anterior al nuevo es la cabezera
    newNode.setPrevious(header);
    //El siguiente al nuevo es el siguiente a la cabezera
    newNode.setNext(header.getNext());
    //El siguiente a la cabecera es el nuevo
    header.setNext(newNode);

    //Se incrementa el nº de nodos llenos (1 más)
    size++;
}

    //INSERTAR EN POSICIÓN//////////////////////////////////////////////////////
    @Override
    public void addPos(int pos, T data) throws IndexOutOfBoundsException //O(N)
    {
    //Comprueba si la posición es válida
    if (pos < 0 || pos > this.size()) {
        throw new IndexOutOfBoundsException();
    } //Se quiere insertar al principio
    else if (pos == 0) {
        addBeg(data); //O(1)
    } //Se quiere insertar al final
    else if (pos == this.size()) {
        addEnd(data); //O(1)
    } //Se quiere insertar en otra posición
    else {
        //Nuevo nodo con ese contenido
        DoubleNode<T> newNode = new DoubleNode<>(data);

        //Nodo en la posición (+1 pq la pos[0] la ocupa la cabecera, no el primero)
        DoubleNode<T> nodePos = this.getNodePos(pos + 1); //O(N)

        //Siguiente a nodo en la posición
        DoubleNode<T> nextNodePos = nodePos.getNext();

        //El siguiente al nodo en posición es el nuevo
        nodePos.setNext(newNode);
        newNode.setPrevious(nodePos);

        //Si el siguiente al nodo en posición es nulo significa q es la cola
        if (nextNodePos == null) {
            this.tail = newNode;
        } else {
            //El anterior al siguiente en la posición es el nuevo
            nextNodePos.setPrevious(newNode);
            //El siguiente al nuevo es el siguiente al q está en la posición
            newNode.setNext(nextNodePos);
        }
        //Se incrementa el nº de nodos llenos (1 más)
        size++;
    }
}

    //AÑADIR AL FINAL DADO CONTENIDO DE NODO////////////////////////////////////
    @Override
    public void addEnd(T data) //O(1)
    {
    //Si la lista está vacía: newNode = primer nodo
    if (this.isEmpty()) {
        addBeg(data);
    } //O(1)
    else {
        //Nuevo nodo con ese contenido
        DoubleNode<T> newNode = new DoubleNode<>(data);

        //El siguiente al último es el nuevo
        tail.setNext(newNode);
        //El anterior al nuevo es el último
        newNode.setPrevious(tail);
        //El último pasa a ser el nuevo
        tail = newNode;

        //Se incrementa el nº de nodos llenos (1 más)
        size++;
    }
}

    //BORRAR NODO CON CONTENIDO/////////////////////////////////////////////////
    @Override
    public int removeData(T data) //O(N)
    {
    int posNode = posOf(data);

    //Si existe
    if (posNode != -1) {
        //Se borra el nodo en el índice que tenga ese contenido
        removePos(posNode); //O(N)
    }

    //Devuelve la posicion
    return posNode;
}

    //BORRAR PRINCIPIO//////////////////////////////////////////////////////////
    public void removeBeg() //O(1)
    {
    //El siguiente a la cabeza pasa a ser la nueva cabeza
    header = header.getNext();

    size--;
}

    //BORRAR EN POSICIÓN////////////////////////////////////////////////////////
    @Override
    public void removePos(int pos) throws IndexOutOfBoundsException //O(N)
    {
    //Comprueba si la posición es válida
    if (pos < 0 || pos > this.size()) {
        throw new IndexOutOfBoundsException();
    } //Se quiere borrar al principio
    else if (pos == 0) {
        removeBeg(); //O(1)
    } //Se quiere borrar al final
    else if (pos == this.size()) {
        removeEnd(); //O(1)
    } //Se quiere borrar en otra posición
    else {
        //Nodo en la posición (+1 pq la pos[0] la ocupa la cabecera, no el primero)
        DoubleNode<T> node = getNodePos(pos + 1); //O(N)

        //Anterior al nodo en posición
        DoubleNode<T> previousNode = node.getPrevious();

        //Siguiente al nodo en posición
        DoubleNode<T> nextNode = node.getNext();

        //Si el siguiente es nulo, el nodo es la cola
        if (nextNode == null) {
            //El anterior no apunta a nada
            previousNode.setNext(null);
            //La cola pasa a ser el anterior
            this.tail = previousNode;
        } else {
            //El anterior al siguiente es el anterior al nodo
            nextNode.setPrevious(previousNode);
            //El siguiente al anterior es el siguiente al nodo
            previousNode.setNext(nextNode);

            //Se borran las referencias del nodo (opcional pq lo haría el recolector de basura de java)
            node.setPrevious(null);
            node.setNext(null);
        }
        size--;
    }
}

    //BORRAR FINAL//////////////////////////////////////////////////////////////
    public void removeEnd() //O(1)
    {
    //La cola pasa a ser su anterior
    tail = tail.getPrevious();

    //La cola no apunta a nada
    tail.setNext(null);

    size--;
}

    //OBTIENE NODO EN POSICIÓN//////////////////////////////////////////////////
    private DoubleNode<T> getNodePos(int pos) //O(N)
    {
    //La posición q le llegará será válida (hay un nodo)
    //pero no será la del primer ni último nodo

    //Se recorre la lista: se comienza desde el siguiente a la cabecera
    DoubleNode<T> current = header.getNext();

    //Avanza desde cabecera en [-1] hasta la posición deseada
    for (int i = -1; i <= pos; i++){
        current = current.getNext();
    }

    //Devuelve el nodo en esa posicición
    return current;
}

    //OBTIENE CONTENIDO DE NODO EN POSICIÓN/////////////////////////////////////
    @Override
    public T get(int pos) throws IndexOutOfBoundsException //O(N){
        //Comprueba si la posición es válida
        if (pos < 0 || pos > this.size()) {
            throw new IndexOutOfBoundsException();
        } //Se quiere obtener el contenido del siguiente a la cabecera
        else if (pos == 0) {
            DoubleNode<T> first = header.getNext();

            return first.getData();
        } //Se quiere obtener contenido de la cola
        else if (pos == this.size()) {
            return tail.getData();
        }//O(1)
        //Se quiere obtener el contenido de otro nodo
        else {
            return getNodePos(pos).getData(); //O(N)
        }
    }

    //OBTIENE LA POSICIÓN DE UN NODO CON CONTENIDO//////////////////////////////
    @Override
    public int posOf(T data) //O(N)
    {
    //Contador de posición
    int pos = 0;

    //Avanza mientras el nodo no sea nulo
    for (T elem : this) {
        if (elem.equals(data)) {
            return pos;
        }
        pos++;
    }

    //No está
    return -1;
}

    //MODIFICA CONTENIDO DE NODO EN POSICIÓN////////////////////////////////////
    @Override
    public void set(int pos, T data) throws IndexOutOfBoundsException //O(N)
    {
    //Comprueba si la posición es válida
    if (pos < 0 || pos > this.size()) {
        throw new IndexOutOfBoundsException();
    } //Se quiere cambiar el contenido del siguiente a la cabecera
    else if (pos == 0) {
        DoubleNode<T> first = header.getNext();

        first.setData(data);
    } //Se quiere cambiar contenido de la cola
    else if (pos == this.size()) {
        tail.setData(data);
    }//O(1)
    //Se quiere cambiar el contenido de otro nodo
    else {
        getNodePos(pos).setData(data); //O(N)
    }
}

    //CLONAR LISTA//////////////////////////////////////////////////////////////
    @Override
    public List<T> clone() //O(N)
    {
    //Se crea nueva lista
    List<T> clone = new DoubleLinkedList<>();

    //Para cada nodo de esta lista se copia su contenido en el final de la lista clon
    for (T elem : this) {
        clone.addEnd(elem);
    }

    //Devuelve la lista clon
    return clone;
}

    //INSERTAR LISTA EN ESTA A PARTIR DE POSICIÓN///////////////////////////////////////////////////////////////////////////
    //EJ 1.A) DEL PARCIAL MARZO 2023
    public DoubleLinkedList<T> insertAllQuadratic(DoubleLinkedList<T> lista, int pos) { //O(N*M) donde N es el tamaño de esta lista y M el de la q se va a insertar
    //Por cada elemento de la lista a insertar
    for (T elem : lista) { //O(M)
        //Se añade ese mismo elemento desde la posición
        this.addPos(pos, elem); //O(N)
        //Pasa a la siguiente posición
        ++pos;
    }

    //Devuelve esta lista con todos los nodos de la anterior insertados
    return this;
}

    //INSERTAR LISTA EN ESTA A PARTIR DE POSICIÓN///////////////////////////////////////////////////////////////////////////
    //EJ 1.B) DEL PARCIAL MARZO 2023
    public DoubleLinkedList<T> insertAllLinear(DoubleLinkedList<T> lista, int pos) { //O(N) donde N es el tamaño de esta lista
    //Lista a insertar vacía: nada
    if (lista.isEmpty()) {
        return this;
    }

    //Nodo anterior al que está en la posición desde la que insertará la otra lista
    DoubleNode<T> anterior = this.getNodePos(pos);//O(N)
    //Nodo posterior al que está en la posición desde la que insertará la otra lista
    DoubleNode<T> posterior = anterior.getNext(); //O(1)

    //Primer nodo (posterior a cabecera) de la lista a insertar
    DoubleNode<T> primero = lista.header.getNext(); //O(1)

    //El siguiente al anterior en la posición es el primero de la lista a insertar
    anterior.setNext(primero); //O(1)
    //El anterior al primero de la lista a insertar es el anterior a la posición desde la que insertar
    primero.setPrevious(anterior); // O(1)Se inserta en la posición correspondiente

    //Si el posterior al que estaba en la posición no es nulo (no era la cola)
    if (posterior != null) {
        //El anterior al posterior pasa a ser la cola de la lista
        posterior.setPrevious(lista.tail); //O(1)
        //El posterior al de la cola pasa a ser el posterior al que estaba en la posición
        lista.tail.setNext(posterior); //O(1)
    } else {
        //El que estaba en esa posición era nulo, por lo que debe actualizarse cola para ser la cola de la lista a insertar
        tail = lista.tail; //O(1)
    }
    //Se actualiza el tamaño de la lista sumando el de la lista recién insertada
    size += lista.size(); //O(1)

    //Se vacía la lista insertada para que esta lista deje de apuntar a sus nodos
    lista.clear();

    //Devuelve esta lista con todos los nodos de la anterior insertados
    return this;
}

}
