/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grafo;

/**
 *
 * @author Hugo
 */
import Cola.*;
import Lista.*;
import java.util.NoSuchElementException;

// Implementación de un grafo PONDERADO mediante una lista de adyacencias. Implementa la interfaz Graph.
// T (CONTENIDO) es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
// P (PESO) es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class AdjacentListGraph<T, P> implements Graph<T, P> {

    // VARIABLES INSTANCIA: privadas para q no se pueda acceder directamente a ellas
    private List<T> nodes; // Lista de nodos
    private List<List<Integer>> edges; // Lista (Integer) en la que cada posición contiene una lista con los nodos adyacentes a ese nodo (aristas)
    private List<List<Double>> weights; // Lista (Double) en la que cada posición contiene una lista con los los pesos de las aristas de es nodo

    // CONSTRUCTOR grafo vacío
    public AdjacentListGraph() {
        // Se crean listas vacías
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        weights = new ArrayList<>();
    }

    // MÉTODOS (@Override son los heredados de la interfaz)
    // Públicos pq a traves de estos operamos con las variables instancia
    // NUM DE NODOS
    @Override
    public int numNodes() {
        return nodes.size(); // tamaño del array = nº de nodos
    }

    // NUM DE ARISTAS
    @Override
    public int numEdges() {
        int numEdges = 0;

        // Para cada nodo
        for (int i = 0; i < numNodes(); ++i) {
            // Sumar el nº de aristas que tiene
            numEdges += edges.get(i).size();
        }

        return numEdges;
    }

    // CONTIENE (PERTENECE) ELEMENTO?
    @Override
    public boolean contains(T node) {
        // Comprueba si está en la arraylist de nodos
        return nodes.contains(node);
    }

    // INSERTAR NUEVO NODO
    @Override
    public void insertNode(T data) {
        // Se añade nuevo nodo O(N) SI REQUIERE REDIMENSIONAMIENTO
        nodes.addEnd(data);

        // Se crean nuevas listas de aristas y pesos que corresponderían a ese nodo
        edges.addEnd(new SimpleLinkedList<Integer>());
        weights.addEnd(new SimpleLinkedList<Double>());
    }

    // MODIFICAR/REEMPLAZAR NODO
    @Override // NO SÉ SI ESTÁ BIEN
    public void setNode(T nodeA, T nodeB) {
        // Cambia el nodoA por el nuevo nodoB
        nodes.set((int) nodeA, nodeB);

        // ALGUNAS VECES TAMBIÉN HAY QUE CAMBIAR EN LA LISTA DE ARISTAS
        // edges.set((int) nodeB, edges.get((int) nodeA);
    }
    
    // ELIMINAR NODO
@Override // NO SÉ SI ESTÁ BIEN
public void removeNode(T node) {
    // Obtiene posición de nodo (comprobando si está) y lo elimina
    int posNode = nodes.removeData(node);

    // Elimina la lista de aristas en la posición de ese nodo
    edges.removePos(posNode);

    // Elimina la lista de pesos en la posición de ese nodo
    weights.removePos(posNode);
}

// TIENEN (NODOS) UNA ARISTA EN COMÚN?
@Override
public boolean haveEdge(T nodeA, T nodeB) throws IndexOutOfBoundsException {
    // Excepción si alguna posición no válida (menor de 0 mayor del tamaño del array)
    if ((int) nodeA < 0 || (int) nodeB < 0 || numNodes() <= (int) nodeA || numNodes() <= (int) nodeB) {
        throw new IndexOutOfBoundsException();
    }

    // Verdadero si la lista de aristas del nodoA contiene al nodoB
    return edges.get((int) nodeA).get((int) nodeB) != null;
}

// INSERTAR NUEVA ARISTA ENTRE NODOS
@Override
public void insertEdge(T nodeA, T nodeB, P weight) throws IndexOutOfBoundsException {
    // Excepción si alguna posición no válida (menor de 0 mayor del tamaño del array)
    if ((int) nodeA < 0 || (int) nodeB < 0 || numNodes() <= (int) nodeA || numNodes() <= (int) nodeB) {
        throw new IndexOutOfBoundsException();
    }

    // Si todavía no hay arista (entre A y B)
    if (edges.get((int) nodeA).posOf((int) nodeB) == -1) {
        // Hacerla añadiendo B a la lista de aristas de A
        edges.get((int) nodeA).addEnd((int) nodeB);
        // Asignándole ese peso
        weights.get((int) nodeA).addEnd((double) weight);
    } else { // Si ya hay arista modifica el peso con el nuevo valor
        setWeight(nodeA, nodeB, weight);
    }

    // Si todavía no hay arista (entre A y B)
    if (edges.get((int) nodeB).posOf((int) nodeA) == -1) {
        // Hacerla añadiendo B a la lista de aristas de A
        edges.get((int) nodeB).addEnd((int) nodeA);
        // Asignándole ese peso
        weights.get((int) nodeB).addEnd((double) weight);
    } else { // Si ya hay arista modifica el peso con el nuevo valor
        setWeight(nodeB, nodeA, weight);
    }
}

// MODIFICAR/REEMPLAZAR EL PESO DE UNA ARISTA
@Override
public void setWeight(T nodeA, T nodeB, P weight) {
    // Posición de arista AB: dentro de aristas se va a la posición de A para entrar a su lista de nodos adyacentes y obtiene la posición del nodo B
    int edgeAB = edges.get((int) nodeA).posOf((int) nodeB);
    // Dentro de pesos va a la posición de A para entrar a su lista de aristas y en la posición de la AB cambia su peso
    weights.get((int) nodeA).set(edgeAB, (double) weight);
}

// ELIMINAR ARISTA
@Override // NO SÉ SI ESTÁ BIEN
public void removeEdge(T nodeA, T nodeB) { // O(N)
    // Obtiene posiciones de ambos nodos
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

    // Elimina el peso de cada arista (lo hace nulo pero no elimina su hueco)
    weights.get(posA).set(posB, null);
    weights.get(posB).set(posA, null);
}

// PASAR/CALCULAR LISTA DEL GRAFO
@Override
public List<T> toList() { // O(1)
    return nodes;
}

// PASAR/CALCULAR LISTA DE NODOS ADYACENTES
@Override
public List<T> adjacentNodes(T node) throws IndexOutOfBoundsException { // O(1)
    // Obtiene posición del nodo
    int nodePos = nodes.posOf(node);

    // Excepción si no existe ese nodo
    if (nodePos == -1) {
        throw new IndexOutOfBoundsException();
    }

    // Dentro de aristas se va a la posición del nodo para obtener su lista de nodos adyacentes
    return (List<T>) edges.get(nodePos);
}

// RECORRER (IMPRIMIR) EN ANCHURA DESDE UN NODO
@Override
public Queue<T> travelWidth(T node) {
    // Obtener posición del nodo
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
    while (!nodesQueue.isEmpty()) {
        // Obtener el elemento del principio actual
        T current = nodesQueue.front();

        // Se marca ese como recorrido
        if (!inserted[(int) current]) {
            inserted[(int) current] = true;

            // Lista de nodos adyacentes al actual
            List<T> adjacents = this.adjacentNodes(current);

            // Pasando por cada nodo adyacente
            for (T adjacentNode : adjacents) {
                // Si todavía no ha sido visitado
                if (!inserted[(int) adjacentNode]) {
                    // Se inserta en la cola
                    nodesQueue.add(adjacentNode);
                }
            }
        }
    }
    // Devuelve la cola con los nodos en orden de recorrido en anchura
    return nodesQueue;
}

// RECORRER (IMPRIMIR) EN PROFUNDIDAD (PREORDEN)
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

