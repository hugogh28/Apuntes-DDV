/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cola;

/**
 *
 * @author Hugo
 */
//Implementación de una cola con un array dinámico (similar a ArrayList). Implementa la interfaz Queue.
import java.util.NoSuchElementException;

//T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float...)
public class QueueArray<T> implements Queue<T>{
    //VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
    
    private int size;//Elementos ya insertados
    final int CAPACIDAD_INICIAL = 10;//Capacidad que dice si .length
    
    //Array contenedor
    private Object queue[];//Object es una instancia de la clase que contiene T
    
    //CONSTRUCTOR de cola vacía
    public QueueArray(){
        size = 0;//Lista vacía
        queue = new Object[CAPACIDAD_INICIAL];//Inicialización del array
    }
    
    //MÉTODOS (@Override son los heredados de la interfaz)
    //Públicos porque a través de estos operamos con las variables instancia
    //TAMAÑO///////////////////////////////////////////////////////////////
    @Override
    public int size(){
        return size;
    }
    
    //¿ESTÁ VACÍA?////////////////////////////////////////////////////////
    @Override
    public boolean isEmpty(){
        //Lo está si el tamaño del array es 0
        return this.size() == 0;
    }
    
    //CONTENIDO AL PRINCIPIO/////////////////////////////////////////////
    @Override
    public T front(){
        //Si la cola está vacía: excepción
        if(this.isEmpty){
            throw new NoSuchElementException();
        }//Devuelve contenido al principio
        else{
            return(T) queue[0];
        }//O(1)
    }
    
    //ENCOLAR (es como addEnd())///////////////////////////////////////////
    @Override
    public void add(T data){
        final int LONGITUD_ACTUAL = this.size();//Elementos insertados
        final int CAPACIDAD_ACTUAL = queue.length();
        
        //Comprobamos si haría falta redimensionar 
        //Si el elemento cabe(longitud<capacidad)
        if(LONGITUD_ACTUAL < CAPACIDAD_ACTUAL){//O(1
            //Se mete el nuevo elemento en la últina posición
            queue[LONGITUD_ACTUAL] = data;
        }//Si el elemento no cabe (longitud>capacidad)--> REDIMENSIONAR
        else{//O(N)
            //Se crea nuevo array el doble de grande
            Object copia[] = new Object[LONGITUD_ACTUAL * 2];
            
            //Se copian todos los elementos en el nuevo array
            for(int i=0;i<LONGITUD_ACTUAL;i++){//O(N)
                copia[i]=queue[i];
            }
            
            //Se mete el nuevo elemento en la última posición
            copia[LONGITUD_ACTUAL] = data;
            
            queue = copia;
        }
        
        //Se incrementa la longitud 1
        size++;
    }
    
    //ELIMINAR PRINCIPIO////////////////////////////////////////////////////////
@Override
public T remove() {
    // Coge el contenido del principio
    T data = front();
    
    // Desde el principio hasta el tamaño, coge el valor de su siguiente
    for (int i = 0; i < this.size(); i++) { // O(N)
        queue[i] = queue[i + 1];
    }
    
    // Reducimos longitud
    size--;
    
    return data;
}

//VACIAR COLA///////////////////////////////////////////////////////////////
@Override
public void clear() {
    // Se vuelve a crear nuevo array y el anterior automáticamente se borra
    queue = new Object[CAPACIDAD_INICIAL];
    // Su tamaño vuelve a ser 0
    size = 0;
}

}
