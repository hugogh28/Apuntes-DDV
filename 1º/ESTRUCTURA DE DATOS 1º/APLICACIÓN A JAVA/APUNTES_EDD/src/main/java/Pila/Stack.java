/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Pila;

/**
 *
 * @author Hugo
 */
// INTERFAZ DE UNA PILA
// Solo se definen los métodos que debe tener, ya que se desarrollan al implementarse
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public interface Stack<T> {

    // obtiene el tamaño (nº de elementos)
    public int size();

    // comprueba si la pila está vacía
    public boolean isEmpty();

    // obtiene el contenido del último elemento
    public T topData();

    // saca un elemento (último en insertarse) y devuelve su contenido
    public T pop();

    // inserta nuevo elemento dado su contenido
    public void push(T data);

    // borra todos los elementos
    public void clear();
}

