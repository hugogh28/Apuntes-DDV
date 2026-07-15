/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pila;

/**
 *
 * @author Hugo
 */
import java.util.EmptyStackException;

// Implementación de una pila con un array "dinámico". Implementa la interfaz Stack.
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class StackArray<T> implements Stack<T> {

    // VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
    private T stack[]; // Array de Objects (de cualquier tipo)
    private int top; // Cima: posición del último elemento
    int initCapacity = 10; // Capacidad inicial

    // CONSTRUCTOR
    StackArray() {
        // Crea un array de Objects con capacidad inicial
        stack = (T[]) new Object[initCapacity]; // Casting (T[]) para que no haya error
        // No tiene elementos todavía (está vacío)
        top = -1;
    }

    // MÉTODOS (@Override son los heredados de la interfaz)

    // TAMAÑO
    @Override
    public int size() // O(1)
    {
        // Obtiene el tamaño (nº de elementos insertados)
        return this.top + 1; // +1 para que sea 1 cuando top sea 0 (hay un nodo)
    }

    // ESTÁ VACÍA?
    @Override
    public boolean isEmpty() // O(1)
    {
        // Está vacía si no tiene ningún elemento insertado
        return this.size() == 0;
    }

    // CONTENIDO DEL ÚLTIMO ELEMENTO
    @Override
    public T topData() throws EmptyStackException // O(1)
    {
        // Si está vacía
        if (isEmpty()) {
            throw new EmptyStackException();
        } // Si no, devuelve el contenido del último elemento introducido
        else {
            return stack[top];
        }
    }

    // DESAPILAR
    @Override
    public T pop() throws EmptyStackException // O(1)
    {
        // Si está vacía
        if (isEmpty()) {
            throw new EmptyStackException();
        } // Si no,
        else {
            // Saca el contenido de la cima actual
            T topData = stack[top];

            stack[top] = null; // Integer puede ser null, pero int no

            // Reduce cima (ya no se podrá acceder a la anterior posición y se borra su contenido)
            top--;

            return topData;
        }
    }
    
    // APILAR
    @Override
    public void push(T data) // O(N)
    {
        // Si la pila está llena: REDIMENSIONAR
        if (this.size() >= stack.length) {
            // Crear nuevo array más grande
            T newStack[] = (T[]) new Object[this.size() * 2];

            // Copiar array en el nuevo hasta última con contenido [top] pq empieza desde -1
            for (int i = 0; i <= top; i++) // O(N)
            {
                newStack[i] = stack[i];
            }

            // Actualizar pila
            stack = newStack;
        }

        // Nuevo contenido se insertará en siguiente posición
        top++;

        // Apilar nuevo contenido
        stack[top] = data; // O(1)
    }

    // VACIAR
    @Override
    public void clear() throws EmptyStackException // O(N)
    {
        // Si está vacía
        if (isEmpty()) {
            throw new EmptyStackException();
        } // Si no,
        else {
            // Recorre todo el array borrando cada elemento
            for (int i = 0; i <= this.top; i++) {
                stack[i] = null;
            }

            // Su cima vuelve a pasar a -1 (pila vacía)
            top = -1;
        }
    }

}

