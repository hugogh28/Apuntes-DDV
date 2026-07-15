/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conjunto;

/**
 *
 * @author Hugo
 */
//Implementación de un conjunto con un array "dinámico". Implementa la interfaz Set.
//T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)

public class SetArray<T> implements Set<T> {

    // VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
    private int cardinal; // Elementos ya insertados
    final int CAPACIDAD_INICIAL = 10; // Capacidad que determina la longitud del array
    private Random rand; // Generador aleatorio

    // Array contenedor de los elementos
    private T elements[]; // T es una instancia de la clase que contiene T

    // CONSTRUCTOR
    SetArray() {
        cardinal = 0; // Lista vacía
        elements = (T[]) new Object[CAPACIDAD_INICIAL]; // Inicialización del array
        rand = new Random(); // Crea generador aleatorio
    }

    // MÉTODOS (@Override son los heredados de la interfaz)
    // Públicos porque a través de estos operamos con las variables instancia

    // TAMAÑO
    @Override
    public int size() { // O(1)
        return cardinal;
    }

    // ESTÁ VACÍO?
    @Override
    public boolean isEmpty() { // O(1)
        return this.size() == 0;
    }

    // CONTIENE ELEMENTO?
    @Override
    public int contains(T data) { // O(N)
        // Variable para posición donde esté ese contenido
        int pos = -1; // -1 es que no está

        // Recorre el conjunto comprobando si está
        for (int i = 0; i < cardinal; ++i) { // O(N)
            if (elements[i].equals(data)) {
                // Actualiza la posición si está
                pos = i;
                // Ya lo ha encontrado: sale del for
                break;
            }
        }
        // Devuelve su posición
        return pos;
    }

    // AÑADIR (al final)
    @Override
    public boolean add(T data) { // O(N)
        // Si el contenido todavía no está en el conjunto se añade
        if (this.contains(data) == -1) {
            // Si el conjunto está lleno: REDIMENSIONAR
            if (this.size() >= elements.length) {
                // Crear nuevo array más grande
                T newSet[] = (T[]) new Object[this.size() * 2];

                // Copiar array en el nuevo hasta última con contenido ([cardinal-1])
                for (int i = 0; i < cardinal; i++) { // O(N)
                    newSet[i] = elements[i];
                }

                // Actualizar conjunto
                elements = newSet;
            }

            // Añadir nuevo contenido
            elements[cardinal] = data; // O(1)

            // Incrementar nº de elementos en el conjunto
            cardinal++;

            return true;
        }
        return false;
    }
    
    // VACIAR
@Override
public void clear() { // O(1)
    // Array se sustituye por otro nuevo (vacío)
    elements = (T[]) new Object[CAPACIDAD_INICIAL];
    // Se eliminan todos los elementos
    cardinal = 0;
}

// EXTRAER Y ELIMINAR ELEMENTO RANDOM
@Override
public T removeRandom() throws NoSuchElementException { // O(N)
    // Si está vacío: excepción
    if (isEmpty()) {
        throw new NoSuchElementException();
    }

    // Genera posición aleatoria
    int randPos = Math.abs(rand.nextInt(cardinal));

    // Saca su contenido
    T data = elements[randPos];

    // Lo elimina
    remove(data); // O(N)

    // Devuelve el contenido que estaba en esa posición
    return data;
}

// ELIMINAR ELEMENTO
@Override
public void remove(T data) { // O(N)
    // Posición donde está ese contenido (-1 es que no está)
    int pos = this.contains(data); // O(N)

    // Si el contenido está lo elimina
    if (pos != -1) {
        elements[pos] = null;
    }
}

// COMPARAR CONJUNTOS
@Override
public boolean equals(Set<T> otherSet) { // O(N^2) porque ambos tienen tamaño N
    // Si no tienen el mismo tamaño no son iguales
    if (this.size() != otherSet.size()) {
        return false;
    }

    // Recorre el otro conjunto
    for (T otherData : otherSet) { // O(N)
        // Si algún elemento no está en este conjunto no son iguales
        if (this.contains(otherData) == -1) { // O(N)
            return false;
        }
    }

    // Son iguales porque tienen el mismo tamaño y contienen los mismos elementos
    return true;
}

// CONTIENE TODOS ELEMENTOS DE OTRO CONJUNTO?
// Diferente de conjunto.equals(otro) porque en este pueden tener diferente tamaño
@Override
public boolean containsAll(Set<T> otherSet) { // O(M*N) donde M es el tamaño del otro conjunto
    // Se crea variable iterador con cada elemento del otro conjunto
    Iterator<T> otherElement = otherSet.iterator();
    // Variable de elemento siguiente al iterador
    T otherData;

    // Recorre el otro conjunto
    while (otherElement.hasNext()) { // O(M)
        // Avanza al siguiente elemento
        otherData = otherElement.next();

        // Si ese elemento no está en este conjunto devuelve false
        if (this.contains(otherData) == -1) { // O(N)
            return false;
        }
    }
    return true;
}

// UNIÓN DE CONJUNTOS
@Override
public void join(Set<T> otherSet) { // O(M*N) donde M es el tamaño del otro conjunto
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
public void difference(Set<T> otherSet) { // O(M*N) donde M es el tamaño del otro conjunto
    // Se crea variable iterador con cada elemento del otro conjunto
    Iterator<T> otherElement = otherSet.iterator();
    // Variable de elemento siguiente al iterador
    T otherData;

    // Recorre el otro conjunto
    while (otherElement.hasNext()) { // O(M)
        // Avanza al siguiente elemento
        otherData = otherElement.next();

        // Si ese elemento está en este conjunto lo borra
        if (this.contains(otherData) != -1) { // O(N)
            remove(otherData);
        }
    }
}

// INTERSECCIÓN DE CONJUNTOS (eliminar no compartidos)
@Override
public void intersection(Set<T> otherSet) { // O(M*N) donde M es el tamaño del otro conjunto
    // Se crea variable iterador con cada elemento del otro conjunto
    Iterator<T> otherElement = otherSet.iterator();
    // Variable de elemento siguiente al iterador
    T otherData;

    // Recorre el otro conjunto
    while (otherElement.hasNext()) { // O(M)
        // Avanza al siguiente elemento
        otherData = otherElement.next();

        // Si ese elemento NO está en este conjunto lo borra
        if (this.contains(otherData) == -1) { // O(N)
            remove(otherData);
        }
    }
}

/////////////////////////ITERADOR DE CONJUNTO EN ARRAY//////////////////////////
public class SetArrayIterator implements Iterator<T> {

    // VARIABLES INSTANCIA: privadas para q no se pueda acceder directamente a ellas
    int pos; // posición del elemento actual durante la iteración
    private final SetArray<T> iteratingSet; // puntero al conjunto iterándose

    // CONSTRUCTOR dado conjunto sobre el que iterar
    public SetArrayIterator(SetArray<T> set) {
        pos = 0; // posición del primer elemento

        this.iteratingSet = set; // el conjunto dado será el que se va a iterar
    }

    // MÉTODOS (@Override son los heredados de la interfaz)
    // Públicos pq a traves de estos operamos con las variables instancia
    // COMPRUEBA SI TIENE SIGUIENTE//////////////////////////////////////////
    @Override
    public boolean hasNext() {
        // comprueba si la posición es menor que el tamaño del conjunto
        // (si todavía está dentro de este)
        return pos < iteratingSet.size();
    }

    // OBTIENE EL SIGUIENTE//////////////////////////////////////////////////
    @Override
    public T next() {
        // Contenido en la posición
        T data = iteratingSet.elements[pos];

        // Pasa a siguiente posición
        pos++;

        // Devuelve el contenido
        return data;
    }
}

}
