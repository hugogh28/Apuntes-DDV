/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Cola;

/**
 *
 * @author Hugo
 */
//INTERFAZ DE UNA BICOLA
//Solo se definen los métodos que debe tener, ya que se desarrollan al implementarse
//T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)

public interface Deque<T> {

    // Obtiene el tamaño (nº de elementos)
    public int size();

    // Comprueba si la cola está vacía
    public boolean isEmpty();

    // Obtiene el contenido del elemento que está al principio
    public T front();

    // Obtiene el contenido del elemento que está al final
    public T back();

    // Inserta al principio nuevo elemento dado su contenido
    public void addFirst(T data);

    // Inserta al final nuevo elemento dado su contenido
    public void addLast(T data);

    // Extrae por el principio
    public T removeFirst();

    // Extrae por el principio
    public T removeLast();

    // Borra todos los elementos
    public void clear();
}

