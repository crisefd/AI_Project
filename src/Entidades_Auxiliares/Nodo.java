/*
 *Nodo.java
 */
package Entidades_Auxiliares;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Clase que encapsula la información de un nodo del árbol de busqueda
 *
 * @author Cristhian Fuertes 1123259
 */
public class Nodo {

    private Point estado;
    private Nodo padre;
    private int profundidad, costo, estimado, utilidad;
    private boolean expandido, hoja = false, izqGenerada = false, derGenerada = false, arrGenerada = false, abaGenerada = false;

    /**
     * Constructor
     *
     * @param estado El estado del nodo
     * @param padre El padre de este nodo
     * @param costo El costo del robot para pasar a ese estado
     * @param estimado El valor de la función heuristica
     */
    public Nodo(Point estado, Nodo padre, int costo, int estimado) {

        this.estado = estado;
        this.padre = padre;
        this.estimado = estimado;

        if (padre == null) {
            this.profundidad = 0;
            expandido = true;
            this.costo = costo;

           
        } else {
            this.profundidad = this.padre.obtenerProfundidad() + 1;
            expandido = false;
            this.costo = costo + (this.padre.obtenerCosto());
           
        }

        utilidad = this.costo + this.estimado;





    }

    /**
     * Constructor
     *
     * @param estado El estado del nodo
     * @param padre El padre de este nodo
     * @param costo El costo del robot para moverse a este estado
     */
    public Nodo(Point estado, Nodo padre, int costo) {
        this.estado = estado;
        this.padre = padre;


        if (padre == null) {
            this.profundidad = 0;
            expandido = true;
            this.costo = costo;
        } else {
            this.profundidad = this.padre.obtenerProfundidad() + 1;
            expandido = false;
            this.costo = costo + (this.padre.obtenerCosto());

        }



    }

    /**
     * Cambia el valor de la bandera indicando los nodos que han sido generados
     * por este nodo
     *
     * @param dir La dirección en la que fue generado el nodo
     * @param b El nuevo valor de la bandera
     */
    public void asignarGeneradas(int dir, boolean b) {// 0 abajo, 1 derecha, 2 arriba y 3 izquierda

        switch (dir) {
            case 0:
                abaGenerada = b;
                break;
            case 1:
                derGenerada = b;
                break;
            case 2:
                arrGenerada = b;
                break;
            case 3:
                izqGenerada = b;
                break;

        }


    }

    /**
     * Getter a las banderas de los nodos generados por este nodo
     *
     * @param dir La direccion en la que el nodo que se quiere consultar fue
     * generado
     * @return El resultado de la evalución
     */
    public boolean obtenerGenerada(int dir) {// 0 abajo, 1 derecha, 2 arriba y 3 izquierda
        boolean res;

        switch (dir) {
            case 0:
                res = abaGenerada;
                break;
            case 1:
                res = derGenerada;
                break;
            case 2:
                res = arrGenerada;
                break;
            default:
                res = izqGenerada;
                break;

        }

        return res;


    }

    /**
     * Getter al valor al atributo profundida
     *
     * @return El valor de la profundidad en el árbol de este nodo
     */
    public int obtenerProfundidad() {
        return profundidad;
    }

    /**
     * Getter al atributo costo
     *
     * @return El costo almacenada en el nodo
     */
    public int obtenerCosto() {
        return costo;
    }

    /**
     * Designa como hoja un nodo
     *
     * @param h El nuevo valor de la bandera hoja
     */
    public void desigarComoHoja(boolean h) {
        hoja = h;

    }

    /**
     * Regresa verdadero si el nodo es hoja, falso si no
     *
     * @return El valor de la bandera hoja
     */
    public boolean esHoja() {
        return hoja;
    }

    /**
     * Getter al valor de la función heuristica almacenada en el nodo
     *
     * @return El valor de la funcion heuristica
     */
    public int obtenerEstimado() {
        return estimado;
    }

    /**
     * Retorna verdadero si el nodo ha sido expandido
     *
     * @return El valor del atributo expandido
     */
    public boolean estaExpandido() {

        return expandido;
    }

    /**
     * Getter al valor de la función utilidad almacenada en el nodo
     *
     * @return El valor de la utilidad
     */
    public int obtenerUtilidad() {
        return utilidad;
    }

    /**
     * Setter a la bandera expandido
     *
     * @param b El nuevo valor de la bandera
     */
    public void expandir(boolean b) {

        expandido = b;

    }

    /**
     * Getter al padre de este nodo
     *
     * @return El padre del nodo
     */
    public Nodo obtenerPadre() {
        return padre;
    }

    /**
     * Getter al estado almacenada en el nodo
     *
     * @return El estado del nodo
     */
    public Point obtenerEstado() {
        return estado;
    }
}
