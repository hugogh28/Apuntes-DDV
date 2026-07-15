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

public class ArrayList<T> implements List<T> {

    private int size;
    final int CAPACIDAD_INICIAL = 10;
    private Object list[];

    public ArrayList() {
        size = 0;
        list = new Object[CAPACIDAD_INICIAL];
    }

    @Override
    public boolean equals(List<T> lista) {
        if (this.size() != lista.size()) {
            return false;
        }

        Iterator listaIterator = lista.iterator();

        for (T elem : this) {
            if (!elem.equals(listaIterator.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public void clear() {
        list = new Object[CAPACIDAD_INICIAL];
        size = 0;
    }

    @Override
    public boolean contains(T data) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(data)) {
                return true;
            }
        }
        return false;
    }
    
    // AÑADIR AL PRINCIPIO
    @Override
    public void addBeg(T data) { //O(N)
        final int LONGITUD_ACTUAL = this.size();//elementos insertados
        final int CAPACIDAD_ACTUAL = list.length;

        // Se comprueba si cabría el nuevo elemento en el array
        // si el elemento cabe (longitud < capacidad)
        if (LONGITUD_ACTUAL < CAPACIDAD_ACTUAL) //O(N)
        {
            // Se mueven todos los elementos a la derecha
            // Recorre desde el FINAL
            for (int i = size; i > 0; i--) {
                list[i] = list[i - 1];
            }

            list[0] = data;

            size++;
        } else { // REDIMENSIONAR
            // se crea nuevo array el doble de grande
            Object copia[] = new Object[LONGITUD_ACTUAL * 2];

            // Se inserta el nuevo elemento al principio del nuevo array
            copia[0] = data;

            // Se copia todos los elementos del array anterior en el nuevo desde la segunda pos
            for (int i = 1; i < LONGITUD_ACTUAL + 1; i++) {
                copia[i] = list[i - 1];
            }

            // Se reemplaza el array anterior por el nuevo
            list = copia;
        }

        // Se incrementa el nº de elementos
        size++;
    }

    // AÑADIR EN POSICIÓN
    @Override
    public void addPos(int pos, T data) throws IndexOutOfBoundsException { //O(N)
        final int LONGITUD_ACTUAL = this.size();//elementos insertados
        final int CAPACIDAD_ACTUAL = list.length;

        // comprobamos si la posición es inválida
        if (pos < 0 || pos > LONGITUD_ACTUAL) {
            throw new IndexOutOfBoundsException();
        }

        // comprobamos si haría falta redimensionar
        // si el elemento cabe (longitud < capacidad)
        if (LONGITUD_ACTUAL < CAPACIDAD_ACTUAL) //O(N)
        {
            // desde la siguiente posición al último elemento hasta la posición
            // baja de elemento copiando el anterior
            for (int i = LONGITUD_ACTUAL; i < pos; i--) { //O(N)
                list[i] = list[i - 1];
            }

            list[pos] = data;
        } else {
            // se crea nuevo array el doble de grande
            Object copia[] = new Object[LONGITUD_ACTUAL * 2];

            // se copian cada elemento hasta antes de la posicion
            for (int i = 0; i < pos; i++) { //O(N)
                copia[i] = list[i];
            }

            // se inserta nuevo elemento en esa posicion
            list[pos] = data;

            // se copia cada elemento desde el siguiente a la posición hasta el
            // tamaño de la lista original, de manera de cada elemento del array
            // copia tenga el mismo valor que el que tiene una posición más en el
            // array original (ya que el copia tiene ya un elemento demás)
            for (int i = pos + 1; i <= this.size(); i++) { //O(N)
                copia[i] = list[i - 1];
            }

            list = copia;
        }

        // se incrementa la longitud
        size++;
    }

    // AÑADIR AL FINAL
    @Override
    public void addEnd(T data) //añade al final //O(N) SI SE REDIMENSIONA
    {
        final int LONGITUD_ACTUAL = this.size();//elementos insertados
        final int CAPACIDAD_ACTUAL = list.length;

        //comprobamos si haría falta redimensionar
        //si el elemento cabe (longitud < capacidad)
        if (LONGITUD_ACTUAL < CAPACIDAD_ACTUAL) //O(1)
        {
            //se mete el nuevo elemento en la última posición
            list[LONGITUD_ACTUAL] = data;
        } else { // REDIMENSIONAR //O(N)
            //se crea nuevo array el doble de grande
            Object copia[] = new Object[LONGITUD_ACTUAL * 2];

            //se copian todos los elementos en el nuevo array
            for (int i = 0; i < LONGITUD_ACTUAL; i++) { //O(N)
                copia[i] = list[i];
            }

            //se mete el nuevo elemento en la última posición
            copia[LONGITUD_ACTUAL] = data;

            list = copia;
        }

        //se incrementa la longitud 1
        size++;
    }

    // ELIMINAR EL POSICIÓN
    @Override
    public void removePos(int pos) throws IndexOutOfBoundsException { //O(N)
        //comprobamos si la posición es inválida
        if (pos < 0 || pos > this.size()) {
            throw new IndexOutOfBoundsException();
        }

        //desde pos hasta el tamaño, se copia el siguiente a cada elemento en este
        for (int i = pos; i < this.size(); i++) { //O(N)
            list[i] = list[i + 1];
        }

        //reducimos longitud
        size--;
    }

    // ELIMINAR CON CONTENIDO
    @Override
    public int removeData(T data) { //O(2*N) = O(N)
    //obtiene la posicion del elemento
        int index = posOf(data); //O(N)

        //Borra en esa posición
        removePos(index); //O(N)

        return index;
    }

    // CLONAR ENTERA
    @Override
    public List<T> clone() { //O(N²) pq recorre dos listas de tamaño N
        //creamos nueva lista donde se va a clonar
        List<T> clon = new ArrayList<>();

        //for each elemento de this, añadeselo a clon
        for (T elem : this) { //O(N²)
            clon.addEnd(elem); //O(N)
        }

        return clon;
    }

    // OBTIENE CONTENIDO EN POSICIÓN
    @Override
    public T get(int pos) throws IndexOutOfBoundsException { //O(1)
        //comprobamos si la posición es inválida
        if (pos < 0 || pos > this.size()) {
            throw new IndexOutOfBoundsException();
        }

        //devuelve el elemento en esa posición
        return (T) list[pos];
    }

    // MODIFICA CONTENIDO EN POSICIÓN
    @Override
    public void set(int pos, T data) throws IndexOutOfBoundsException { //O(1)
        //comprobamos si la posición es inválida
        if (pos < 0 || pos > this.size()) {
            throw new IndexOutOfBoundsException();
        }

        //modifica el elemento en esa posición
        list[pos] = data;
    }

    // OBTIENE POSICIÓN CON CONTENIDO
    @Override
    public int posOf(T data) { //O(N)
        //recorre todo el array
        for (int i = 0; i < this.size(); i++) //compara cada elemento con el buscado //O(N)
        {
            if (list[i].equals(data)) //si lo encuentra, devuelve su posición
            {
                return i;
            }
        }

        //no se ha encontrado
        return -1;
    }

    // IMPRIMIR
    @Override
    public void print() { //O(N)
        //objeto de clase iterator para cada elemento de la lista dada
        Iterator<T> listaIterator = this.iterator();

        while (listaIterator.hasNext()) {
            System.out.println(" [" + listaIterator.next().toString() + "] ");
        }
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedM
    }

}

