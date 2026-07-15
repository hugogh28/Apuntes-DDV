/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Árbol;

/**
 *
 * @author Hugo
 */
import java.util.*;

//INTERFAZ DE UN ÁRBOL
//Solo se definen los métodos que debe tener, ya que se desarrollan al implementarse
//T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public interface BinaryTree<T> {

    //Comprueba si está vacío
    public boolean isEmpty();

    //Elimina todos los nodos (hijos) de forma recursiva
    public void clearChildren();

    //Comprueba si es una hoja (sin hijos)
    public boolean isLeaf();

    //Comprueba si ese contenido está en el árbol
    public boolean contains(T data);

    //Busca ese contenido y devuelve el subárbol cuya raíz lo contiene
    public BinaryTree<T> findElement(T data);

    //Devuelve el contenido de la raíz
    public T getRoot();

    //Devuelve el subárbol izquierdo
    public BinaryTree<T> getLeft();

    //Devuelve el subárbol derecho
    public BinaryTree<T> getRight();

    //Modifica el contenido de la raíz (devolviendo el que hubiese antes)
    public T setRoot(T data);

    //Inserta hijo izquierdo (creando nuevo subárbol izquierdo)
    public void insertLeft(T data) throws UnsupportedOperationException;

    //Inserta hijo derecho (creando nuevo subárbol derecho)
    public void insertRight(T data) throws UnsupportedOperationException;

    //Elimina el subárbol izquierdo
    public BinaryTree<T> removeLeft() throws NoSuchElementException;

    //Elimina el subárbol derecho
    public BinaryTree<T> removeRight() throws NoSuchElementException;

    //Imprime en Preorden
    public String printPreOrder();

    //Imprime en Inorden
    public String printInOrder();

    //Imprime en Postorden
    public String printPostOrder();
}

