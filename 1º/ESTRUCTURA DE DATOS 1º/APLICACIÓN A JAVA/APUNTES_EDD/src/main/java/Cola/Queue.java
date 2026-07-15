/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Cola;

/**
 *
 * @author Hugo
 */
// INTERFAZ DE UNA COLA
// Solo se definen los métodos que debe tener, ya que se desarrollan al implementarse
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public interface Queue<T> {

    // obtiene el tamaño (nº de elementos)
    public int size();

    // comprueba si la cola está vacía
    public boolean isEmpty();

    // obtiene el contenido del elemento que está al principio
    public T front();

    // inserta al final nuevo elemento dado su contenido
    public void add(T data);

    // extrae por el principio
    public T remove();

    // borra todos los elementos
    public void clear();
}

