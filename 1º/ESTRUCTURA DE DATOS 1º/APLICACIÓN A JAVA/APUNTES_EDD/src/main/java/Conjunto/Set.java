/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Conjunto;

/**
 *
 * @author Hugo
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

// INTERFAZ DE UN CONJUNTO
// Solo se definen los métodos que debe tener, ya que se desarrollan al implementarse
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
// Iterable es para poder hacer (for:each)'s
public interface Set<T> extends Iterable<T> {

    // tamaño
    public int size();

    // comprobar si está vacío
    public boolean isEmpty();

    // comprobar si está un elemento y devuelve dónde
    public int contains(T data);

    // añadir nuevo elemento (sin repetir)
    public boolean add(T elem);

    // vaciar
    public void clear();

    // eliminar y devolver un elemento aleatorio
    public T removeRandom() throws NoSuchElementException;

    // eliminar elemento
    public void remove(T data);

    // comprobar si contiene exactamente los mismos elementos q otro conjunto
    public boolean equals(Set<T> otherSet);

    // comprobar si todos los elementos de otro conjunto están en este (es un subconjunto de este)
    public boolean containsAll(Set<T> otherSet);

    // añadir elementos de otro conjunto q no estén ya en este (unión)
    public void join(Set<T> otherSet);

    // eliminar todos los elementos que comparta con otro conjunto (diferencia)
    public void difference(Set<T> otherSet);

    // eliminar todos los elementos que no comparta con otro conjunto (intersección)
    public void intersection(Set<T> otherSet);

    // iterador
    @Override
    public Iterator iterator();
}

