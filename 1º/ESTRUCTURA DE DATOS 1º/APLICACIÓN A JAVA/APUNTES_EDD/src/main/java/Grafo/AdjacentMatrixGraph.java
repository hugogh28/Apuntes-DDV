/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grafo;

/**
 *
 * @author Hugo
 */
import Lista.*;
import java.util.NoSuchElementException;
import Cola.*;
import java.util.Iterator;

//Implementación de un grafo mediante una matriz de adyacencias. Implementa la interfaz Graph.
//T(CONTENIDO) es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float...)
//P(PESO) es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float...)
public class AdjacentMatrixGraph<T, P>implements Graph<T, P> {
    //VARIABLES INSTANCIA: privadas para que no se pueda acceder directamente a ellas
    private List<T> nodes;//Lista de nodos
    private List<List<Double>> edges;//Lista (Double) en la que cada posición contiene una lista con los pesos de cada arista de ese nodo
    
    //CONSTRUCTOR grafo vacío
    public AdjacentMatrixGraph(){
        //Se crean listas vacías
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }
    
    
    //MÉTODOS (@Override son los herederos de la interfaz)
    //Públicos porque a través de estos operamos con las variables instancia
    //NÚMERO DE NODOS//////////////////////////////////////////////////////////////////////////////
    @Override
    public int numNodes(){//0(1)
        return nodes.size();//Tamaño del array = n* de nodos
    }
    
    //NÚMERO DE ARISTAS///////////////////////////////////////////////////////////////////////////
    @Override
    public int numEdges(){//O(N²) donde N es el número de nodos
        int numEdges=0;
        
        //Para cada nodo
        for(int i=0; i<numNodes();i++){//O(N)
            //Sumar el número de aristas que tiene 
            numEdges += edges.get(i).size();//O(1)
        }
        
        /*
        //i para posición en la lista de aristas(nodo)
        for(int i=0; i<numNodes();i++){//O(N²)
            //j para posición de cada nodo en la lista de cada nodo (posición de lista de aristas)
            for( int j=0; j<numNodes();j++){//O(N)
                if(edges.get(i).get(j) != null;{
                    numEdges+=1;//No es nula: suma arista si la posición en la lista de nodos de cada nodo en la lista de aristas
                }
            }
        }
        */
        
        return numEdges;
    }
    //CONTIENE (PERTENECE) ELEMENTO? ///////////////////////////////////////////////////////////////////////////////////////////
@Override
public boolean contains(T data) { //O(N)
    // Comprueba si el dato está en la lista de nodos
    return nodes.contains(data); //O(N)
}

//INSERTAR NUEVO NODO////////////////////////////////////////////////////////////////////////////////////////////////////////////
@Override
public void insertNode(T data) { //O(N) donde N es el nº de nodos
    // Se añade un nuevo nodo O(N) SI REQUIERE REDIMENSIONAMIENTO
    nodes.addEnd(data);
    
    // En la lista de aristas, se añade un nuevo hueco (de momento vacío) a cada nodo que correspondería al nuevo nodo
    for (int i = 0; i < edges.size(); ++i) { //O(N) pq nodes y edges tienen el mismo tamaño
        edges.get(i).addEnd(null);
    }
    
    // Se añade un nuevo hueco en la lista de aristas que correspondería a la lista de adyacentes del nuevo nodo
    edges.addEnd(new ArrayList<Double>());
    
    // En la lista de aristas, se añaden huecos por cada nodo a la lista del nuevo nodo
    for (int i = 0; i < edges.size(); ++i) { //O(N) pq nodes y edges tienen el mismo tamaño
        edges.get(edges.size() - 1).addEnd(null);
    }
}
//MODIFICAR/REEMPLAZAR NODO/////////////////////////////////////////////////////////////////////////////////////////////////
@Override
public void setNode(T nodeA, T nodeB) { //O(1)
    // Cambia el nodoA por el nuevo nodoB
    nodes.set((int) nodeA, nodeB);

    // Cambiar en la lista de aristas
    // Suponiendo que nodeA y nodeB son índices válidos de los nodos
    edges.set((int) nodeB, edges.get((int) nodeA));
}

//ELIMINAR NODO//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
@Override
public void removeNode(T node) { //O(N²)
    // Obtiene la posición del nodo (comprobando si está) y lo elimina
    int posNode = nodes.removeData(node); //O(N)

    // Elimina la lista de aristas en la posición de ese nodo
    edges.removePos(posNode);

    // Elimina el nodo de las listas de aristas de cada nodo
    for (int i = 0; i < numNodes(); ++i) { //O(N)
        for (int j = 0; j < numNodes(); ++j) { //O(N)
            // Si ese nodo tiene una arista con el nodo a eliminar
            if (edges.get(i).get(j) == node) {
                // Lo elimina en esa posición
                edges.get(i).removePos(j);
            }
        }
    }
}

//TIENEN (NODOS) UNA ARISTA EN COMÚN?/////////////////////////////////////////////////////////////////////////////////////
@Override
public boolean haveEdge(T nodeA, T nodeB) { //O(1)
    // Excepción si alguna posición no válida (menor de 0 mayor del tamaño del array)
    if ((int) nodeA < 0 || (int) nodeB < 0 || numNodes() <= (int) nodeA || numNodes() <= (int) nodeB) {
        throw new IndexOutOfBoundsException();
    }

    // Hay arista si la posición del nodo B en la lista de aristas de A no es nula
    return edges.get((int) nodeA).get((int) nodeB) != null; //O(1)
}

//INSERTAR NUEVA ARISTA ENTRE NODOS///////////////////////////////////////////////////////////////////////////////////////
@Override
public void insertEdge(T nodeA, T nodeB, P weight) { //O(1)
    // Excepción si alguna posición no válida (menor de 0 mayor del tamaño del array)
    if ((int) nodeA < 0 || (int) nodeB < 0 || numNodes() <= (int) nodeA || numNodes() <= (int) nodeB) {
        throw new IndexOutOfBoundsException();
    }
    // En la lista de aristas inserta el peso que corresponde en la posición que corresponde a B dentro de la lista de A
    edges.get((int) nodeA).set((int) nodeB, (double) weight);
    // En la lista de aristas inserta el peso que corresponde en la posición que corresponde a B dentro de la lista de A
    edges.get((int) nodeA).set((int) nodeB, (double) weight);
}

//MODIFICAR/REEMPLAZAR EL PESO DE UNA ARISTA/////////////////////////////////////////////////////////////////////////
@Override
public void setWeight(T nodeA, T nodeB, P weight) {
    // Obtén posiciones de ambos nodos
    int posA = nodes.posOf(nodeA);
    int posB = nodes.posOf(nodeB);

    // No tienen arista en común: excepción
    if (!haveEdge(nodeA, nodeB)) {
        throw new NoSuchElementException();
    }
    // Modifica el peso (en la posición del otro nodo) en la lista de aristas de cada nodo
    edges.get(posA).set(posB, (double) weight);
    edges.get(posB).set(posA, (double) weight);
}

//ELIMINAR ARISTA//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
@Override //NO SÉ SI ESTÁ BIEN
public void removeEdge(T nodeA, T nodeB) throws NoSuchElementException {
    // Obtén posiciones de ambos nodos
    int posA = nodes.posOf(nodeA);
    int posB = nodes.posOf(nodeB);

    // Si no está alguno de los dos: excepción
    if (posA == -1 || posB == -1) {
        throw new NoSuchElementException();
    } // Si no tienen arista entre sí: no se puede borrar y excepción
    else if (!haveEdge(nodeA, nodeB)) {
        throw new NoSuchElementException();
    }

    // Elimina cada nodo de la lista de aristas del otro (lo hace nulo pero no elimina su hueco)
    edges.get(posA).set(posB, null);
    edges.get(posB).set(posA, null);
}

//PASAR/CALCULAR LISTA DEL GRAFO///////////////////////////////////////////////////////////////////////////////////////////
@Override
public List<T> toList() { //O(1)
    return nodes; //O(1)
}

//PASAR/CALCULAR LISTA DE NODOS ADYACENTES//////////////////////////////////////////////////////////////////////////
@Override
public List<T> adjacentNodes(T node) { //O(N)
    // Obtiene posición del nodo
    int nodePos = nodes.posOf(node);

    // Excepción si no existe ese nodo
    if (nodePos == -1) {
        throw new IndexOutOfBoundsException();
    }

    // Lista (enlazada simple) que contendrá los nodos adyacentes del nodo dado
    List<T> adjacents = new SimpleLinkedList<>();

    // i para posición en la lista de nodos adyacentes a ese nodo en la lista de aristas
    for (int i = 0; i < numNodes(); ++i) { //O(N)
        if (edges.get(nodePos).get(i) != null) {
            adjacents.addEnd(nodes.get(i)); //No es nula: añade ese nodo pq es adyacente al dado
        }
    }

    return adjacents;
}

//RECORRER (IMPRIMIR) EN ANCHURA DESDE UN NODO/////////////////////////////////////////////////////////////////////
@Override
public Queue<T> travelWidth(T node) {
    // Obtiene posición del nodo
    int nodePos = nodes.posOf(node);

    // Crear cola
    Queue<T> nodesQueue = new QueueList<>();

    // Crear array de vértices procesados del tamaño del nº de nodos del grafo
    Boolean[] inserted = new Boolean[numNodes()];
    for (int i = 0; i < numNodes(); i++) {
        // No se ha recorrido todavía
        inserted[i] = false;
    }

    // Insertar en la cola el vértice de partida, marcándolo como visitado
    nodesQueue.add(node);
    inserted[nodePos] = true;

    // Mientras la cola no esté vacía
    while (!nodesQueue.isEmpty()) //O(N)
    {
        // Obtiene el elemento del principio actual
        T current = nodesQueue.front();

        // Lista de nodos adyacentes al actual
        List<T> adjacents = this.adjacentNodes(current);

        // Iterador de esos adyacentes
        // Mientras que haya adyacentes
        for (T currentAdjacent : adjacents) {
            // Coge el valor del adyacente
            // Si todavía no ha sido visitado
            if (!inserted[(int) currentAdjacent]) {
                // Inserta el adyacente en la pila (luego se recorrerá el grafo desde este)
                nodesQueue.add(currentAdjacent);
                // Marca el adyacente como visitado
                inserted[(int) currentAdjacent] = true;
            }
        }
    }
    // Devuelve la cola con los nodos en orden de recorrido en anchura (leído al revés pq es una cola y el del principio fue el primero en entrar)
    return nodesQueue;
}

//RECORRER (IMPRIMIR) EN PROFUNDIDAD (PREORDEN)/////////////////////////////////////////////////////////////////////
@Override
public List<T> travelDepth(T nodeOrigin) throws NoSuchElementException {
    // Comprobar que ese nodo origen pertenece a este grafo
    if (!contains(nodeOrigin)) {
        throw new NoSuchElementException();
    }

    // Crear nueva lista vacía para guardar nodos visitados
    List<T> visited = new SimpleLinkedList<>();

    // Llamada a método recursivo
    visited = travelDepthRec(nodeOrigin, visited);

    // Devuelve la lista de nodos visitados
    return visited;
}

// Método recursivo
public List<T> travelDepthRec(T nodeOrigin, List<T> visited) {
    // Se visita el origen
    visited.addEnd(nodeOrigin);

    // Nueva lista que contiene los adyacentes del origen
    List<T> adjacents = this.adjacentNodes(nodeOrigin);

    // Nuevo elemento visitado
    T newVisited;

    while (!adjacents.isEmpty()) {
        // Coge el contenido del primer nodo adyacente (sería el de la izquierda si fuese un árbol)
        newVisited = adjacents.get(0);
        // Lo elimina
        adjacents.removeData(newVisited);

        // Si no ha sido visitado
        if (!visited.contains(newVisited)) {
            // Vuelve a llamar al método
            visited = travelDepthRec(newVisited, visited);
        }
    }

    // Devuelve la lista de nodos visitados
    return visited;
}

    
}
