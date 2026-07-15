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

//Implementación de una lista con una lista enlazada doble. Implementa la interfaz List.
//T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class DoubleCircularList<T> implements List<T> {

    //VARIABLES INSTANCIA: privadas para q no se pueda acceder directamente a ellas
    //CABECERA (vacía) EN POS -1, POR LO Q EL PRIMER NODO ESTÁ EN POS 0 (como array)
    private DoubleNode<T> header; //Cabecera: nodo vacío q apuntará al primer nodo lleno
    private DoubleNode<T> tail; //Referencia al último nodo lleno

    private int size;//nodos llenos

    //CONSTRUCTOR de lista vacía
    DoubleCircularList() {
        //Se crea nodo cabecera (nulo)
        header = new DoubleNode<>(null);

        //Al ser una lista vacía el último nodo es el mismo que la cabezera
        tail = header;

        //Se asegura circularidad: el siguiente a la cola es la cabecera y el anterior a la cabecera es la cola
        header.setPrevious(tail);
        tail.setNext(header);

        //No tiene nodos llenos
        size = 0;
    }

    //MÉTODOS (@Override son los heredados de la interfaz)
    //Públicos pq a traves de estos operamos con las variables instancia
    //COMPARA LISTAS////////////////////////////////////////////////////////////
    @Override
    public boolean equals(List<T> list) {
        //comprueba si son del mismo tamaño
        if (this.size() == list.size()) {
            Iterator listIterator = list.iterator();
            for (T elem : this) //O(N)
            {
                if (!elem.equals(listIterator.next())) //devuelve falso si encuentra nodos diferentes
                {
                    return false;
                }
            }

            //devuelve verdadero si son del mismo tamaño y no ha enontrado nodos diferentes
            return true;
        }
        //devuelve falso si no son del mismo tamaño o ha encontrado nodos diferentes
        return false;
    }

    //CALCULA TAMAÑO DE LISTA///////////////////////////////////////////////////
    @Override
    public int size() {
        return this.size;
    }

    //ESTÁ VACÍA?///////////////////////////////////////////////////////////////
    @Override
    public boolean isEmpty() //O(1)
    {
        //Lista vacía si la cabezera apunta a sí misma
        return header.getNext() == header;
    }

    //VACIAR LISTA//////////////////////////////////////////////////////////////
    @Override
    public void clear() //O(1)
    {
        //Se vuelve a crear nodo cabecera (nulo)
        this.header = new DoubleNode<>(null);

        //Al ser una lista vacía el último nodo es el mismo que la cabezera
        this.tail = this.header;

        //No tiene nodos llenos
        this.size = 0;

        //Se asegura circularidad: el siguiente a la cola es la cabecera y el anterior a la cabecera es la cola
        header.setPrevious(tail);
        tail.setNext(header);
    }

    //NODO TIENE CONTENIDO?/////////////////////////////////////////////////////
    @Override
    public boolean contains(T data) //O(N)
    {
        //para cada nodo con contenido tipo T de esta lista
        for (T elem : this) //O(N)
        //comprueba si es el mismo q el buscado y devuelve true
        {
            if (elem.equals(data)) {
                return true;
            }
        }
        //sino encuentra un nodo con ese contenido
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

            //Nodo en la posición
            DoubleNode<T> nodePos = this.getNodePos(pos); //O(N)

            //Anterior a nodo en la posición
            DoubleNode<T> previousNodePos = nodePos.getPrevious();

            //El siguiente al anterior es el nuevo
            previousNodePos.setNext(newNode);
            //EL anterior al nuevo es el anterior
            newNode.setPrevious(previousNodePos);

            //El anterior al nodo en la posición es el nuevo
            nodePos.setPrevious(newNode);
            //El siguiente al nuevo es el q está en la posicioón
            newNode.setNext(nodePos);

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
        } //O(1
        else {
            //Nuevo nodo con ese contenido
            DoubleNode<T> newNode = new DoubleNode<>(data);

            //El siguiente al último es el nuevo
            tail.setNext(newNode);
            //El anterior al nuevo es el último
            newNode.setPrevious(tail);
            //El último pasa a ser el nuevo
            tail = newNode;

            //Se asegura circularidad: el siguiente a la cola es la cabecera y el anterior a la cabecera es la cola
            header.setPrevious(tail);
            tail.setNext(header);

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

        //Devuelve posición
        return posNode;
    }

    //BORRAR PRINCIPIO//////////////////////////////////////////////////////////
    public void removeBeg() //O(1)
    {
        //El siguiente a la cabeza pasa a ser la nueva cabeza
        header = header.getNext();

        //Se asegura circularidad: el siguiente a la cola es la cabecera y el anterior a la cabecera es la cola
        header.setPrevious(tail);
        tail.setNext(header);

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
            //Nodo en la posición
            DoubleNode<T> node = getNodePos(pos); //O(N)

            //Anterior al nodo en posición
            DoubleNode<T> previousNode = node.getPrevious();

            //Siguiente al nodo en posición
            DoubleNode<T> nextNode = node.getNext();

            //Si el siguiente es la cabecera, el nodo es la cola
            if (nextNode == header) {
                //La cola pasa a ser el anterior
                this.tail = previousNode;

                //Se asegura circularidad: el siguiente a la cola es la cabecera y el anterior a la cabecera es la cola
                header.setPrevious(tail);
                tail.setNext(header);
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

        //Se asegura circularidad: el siguiente a la cola es la cabecera y el anterior a la cabecera es la cola
        header.setPrevious(tail);
        tail.setNext(header);

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
        for (int i = -1; i <= pos; i++) {
            current = current.getNext();
        }

        //Devuelve el nodo en esa posicición
        return current;
    }

    //OBTIENE CONTENIDO DE NODO EN POSICIÓN/////////////////////////////////////
    @Override
    public T get(int pos) throws IndexOutOfBoundsException //O(N)
    {
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

    @Override
    public void print() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedM
    }

    @Override
    public Iterator<T> iterator() {
        return new DoubleCircularListIterator(header);
    }

    //CLASE ITERATOR DE LISTA CIRCULAR DOBLE
    private class DoubleCircularListIterator implements Iterator<T> {

        private int pos;
        private DoubleNode<T> current;

        public DoubleCircularListIterator(DoubleNode<T> header) {
            this.pos = 0;
            this.current = header;
        }

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            pos++;
            current = current.getNext();
            return current.getData();
        }
    }

}

