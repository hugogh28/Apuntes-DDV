/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MasPilas;

/**
 *
 * @author Hugo
 */
public class Pila {
    /**
     * Clase que define las operaciones básicas que debe tener una pila.
     */
    //Puntero que indica el inicio de la pila o tambien conocida como el tope de la pila
    private Nodo inicio;
    //Variable para registrar el tamaño de la pila
    private int tamaño;
    /**
     * Constructor por defecto.
     */
    public void Pila(){
        inicio = null;
        tamaño = 0;
    }
    /**
     * Consulta si la piña esta vacia
     * @return true si el primer nodo (inicio), no apunta a otro nodo
     */
    public boolean esVacia(){
        return inicio == null;
    }
    /**
     * Consulta cuantos elementos (nodos) tiene la pila 
     * @return numero entero entre [0,n] donde n es el numero de elementos que contenga la lista
     */
    public int getTamaño(){
        return tamaño;
    }
    /**
     * Agrega un nuevo nodo a la pila.
     * @param valor a agregar
     */
    public void apilar(int valor){
        //Define un nuevo nodo
        Nodo nuevo = new Nodo();
        //Agrega al valor al nodo
        nuevo.setValor(valor);
        //Consulta si la pila esta vacia
        if(esVacia()){
            //Inicializa la pila con el nuevo valor
            inicio = nuevo;
        }
        //Caso contrario agrega el nuevo nodo al inicio de la pila
        else{
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
        }
        //Incrementa el contador del tamaño
        tamaño++;
    }
    /**
     * Elimina el elemento que se encuentra en el tope de la pila
     */
    public void retirar(){
        if(!esVacia()){
            //Asigna como primer nodo al siguiente de la pila
            inicio = inicio.getSiguiente();
            //Decrementa el contador del tamaño de la pila
            tamaño--;
        }
    }
    /**
     * Consulta el valor del nodo que se encuentra en la cima de la pila
     * @return valor del nodo
     * @throws Exception
     */
    public int cima() throws Exception{
        if(!esVacia()){
            return inicio.getValor();
        }else{
            throw new Exception("La pila se encuentra vacia");
        }
    }
    /**
     * Busca un elemento en la pila
     * @param referencia valor del nodo a buscar
     * @return true si el valor de referencia existe en la pila
     */
    public boolean buscar(int referencia){
        //Crea una copia de la pila
        Nodo aux = inicio;
        //Bandera para verificar si existe el elemento a buscar
        boolean existe = false;
        //Recorre la pila hasta llegar a encontrar el nodo o llegar al final de la pila
        while(existe!=true&&aux!=null){
            //Compara si el valor del nodo es igual al de referencia
            if(referencia == aux.getValor()){
                //Cambia el valor de la bandera
                existe = true;
            }
            else{
                //Avanza al siguiente nodo
                aux = aux.getSiguiente();
            }
        }
        //Retorna el valor de la bandera
        return existe;
    }
    /**
     * Elimina un nodo de la pila ubicado por su valor
     * @param referencia valor de referencia para ubicar el nodo
     */
    public void remover(int referencia){
        //Consulta si el valor existe en la pila
        if(buscar(referencia)){
            //Crea una pila auxiliar para guardar los valores que se vayan desapilando de la pila original
            Nodo pilaAux = null;
            //Recorre la pila hasta llegar al nodo que tenga el valor igual que el de referencia
            while(referencia != inicio.getValor()){
                //Crea un nodo temporal para agregarlos a la pila auxiliar
                Nodo temp = new Nodo();
                //Ingresa el valor al nodo temporal
                temp.setValor(inicio.getValor());
                //consulta si la pila auxiliar no ha sido inicializada
                if(pilaAux==null){
                    //Inicializa la pila auxiliar
                    pilaAux = temp;
                }
                //Caso contrario si la pila auxiliar ya contiene elementos
                //los agrega al inicio
            }
        }
    }
    
}
