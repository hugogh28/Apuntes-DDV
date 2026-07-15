/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Lista;

/**
 *
 * @author Hugo
 */
import java.util.Iterator;

// INTERFAZ DE UNA LISTA
// Solo se definen los métodos que debe tener, ya que se desarrollan al implementarse
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
// Iterable es para poder hacer (for:each)'s
public interface List<T> extends Iterable<T> {

    // Compara la lista con otra
    public boolean equals(List<T> list);

    // Obtiene el tamaño de la lista
    public int size();

    // Comprueba si la lista está vacía
    public boolean isEmpty();

    // Borra todos los elementos
    public void clear();

    // Comprueba si está en la lista
    public boolean contains(T data);

    // Añade al principio
    public void addBeg(T data);

    // Inserta en posición
    public void addPos(int pos, T data) throws IndexOutOfBoundsException;

    // Añade al final
    public void addEnd(T data);

    // Borra elemento dado
    public int removeData(T data);

    // Borra en posición
    public void removePos(int pos) throws IndexOutOfBoundsException;

    // Obtiene el elemento en posición
    public T get(int pos) throws IndexOutOfBoundsException;

    // Obtiene la posición del elemento dado
    public int posOf(T data);

    // Reemplaza en posición por otro elemento
    public void set(int pos, T data) throws IndexOutOfBoundsException;

    // Clona una lista creando otra igual
    public List<T> clone();

    // Imprimir la lista
    public void print();

    // Iterador de la lista
    @Override
    public Iterator iterator();
}

