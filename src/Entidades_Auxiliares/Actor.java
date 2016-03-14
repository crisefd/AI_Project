/*
 * Actor.java
 */
package Entidades_Auxiliares;

import java.awt.Point;

/**
 * Clase que almacena las posiciones iniciales de los elementos
 *
 * @author Cristhian Fuertes 1123259
 */
public class Actor extends Point {

    /**
     * El peso del actor
     */
    public int peso;

    /**
     * Constructor que inicializa la ubicacion inicial del actor
     *
     * @param x la coordenada vertical del actor
     * @param y la coordenada horizontal del actor
     * @param peso el peso del actor
     */
    public Actor(int x, int y, int peso) {

        super(x, y);
        this.peso = peso;

    }
}
