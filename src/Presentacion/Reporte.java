/*
 * Reporte.java
 * 
 */
package Presentacion;

/**
 * Clase que contiene información sobre el comportamiento de los algoritmos que
 * luego sera mostrada al usuario del aplicativo.
 *
 * @author Cristhian Fuertes 1123259 
 */
public class Reporte {

    private int profundidadArbol, nodosExpandidos, costoRuta;
    private double tiempoComputo;

    /**
     *
     * @param p Entero indicando la profundidad de un árbol de búsqueda
     * @param ne La cantidad de nodos expandidos de un árbol de búsqueda
     * @param t Real indicando el tiempo, en milisegundos, que demoro un
     * algoritmo de busqueda
     */
    public Reporte(int p, int ne, double t, int costo) {
        profundidadArbol = p;
        tiempoComputo = t;
        nodosExpandidos = ne;
        costoRuta=costo;

    }

    /**
     *
     * @return La profundidad de un arbol de búsqueda
     */
    public int obtenerProfundidad() {
        return profundidadArbol;
    }

    /**
     *
     * @return La cantidad de nodos expandidos de un arbol de búsqueda
     */
    public int obtenerNodosExpandidos() {
        return nodosExpandidos;
    }

    /**
     *
     * @return El tiempo que tardo un algoritmo de busqueda
     */
    public double obtenerTiempo() {
        return tiempoComputo;
    }
    
    /**
     *
     * @return El costo de la ruta
     */
    public int obtenerCostoRuta(){
        return costoRuta;
    }
}
