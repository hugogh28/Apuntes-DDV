/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Grafo;

/**
 *
 * @author Hugo
 */
import Cola.*;
import Lista.*;

// INTERFAZ DE UN GRAFO: VÁLIDA PARA GRAFOS DIRIGIDOS Y NO DIRIGIDOS, PONDERADOS
// Y NO PONDERADOS. No para multigrafos ni multidigrafos.
// Solo se definen los métodos que debe tener, ya que se desarrollan al implementarse
// T (CONTENIDO) es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
// P (PESO) es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public interface Graph<T, P> {

    // Nº de nodos
    public int numNodes();

    // Nº de aristas
    public int numEdges();

    // Comprueba si algún nodo contiene tal valor
    public boolean contains(T data);

    // Inserta (nuevo) nodo dado contenido
    public void insertNode(T data);

    // Modifica contenido de nodo
    public void setNode(T nodeA, T nodeB);

    // Borra nodo y sus aristas
    public void removeNode(T node);

    // Comprueba si hay alguna arista entre dos nodos
    public boolean haveEdge(T nodeA, T nodeB);

    // Inserta arista entre dos nodos existentes
    public void insertEdge(T nodeA, T nodeB, P weight);

    // Modifica el peso de una arista entre dos nodos
    public void setWeight(T nodeA, T nodeB, P weight);

    // Borra la arista entre dos nodos
    public void removeEdge(T nodeA, T nodeB);

    // Pasar grafo a String
    public String toString();

    // Pasar grafo a lista
    public List<T> toList();

    // Crear lista (ArrayList) con los nodos adyacentes a nodo
    public List<T> adjacentNodes(T node);

    // Recorrido en anchura desde un nodo: se mantiene en los nodos con la misma profundidad
    public Queue<T> travelWidth(T node);

    // Recorrido en profundidad (Preorden): el camino más largo posible
    public List<T> travelDepth(T node);
}

