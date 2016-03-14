/*
 * BuscadorAvaro.java
 */
package Buscadores;

import Buscadores.Buscador;
import Entidades_Auxiliares.Actor;
import Manejo_Archivos.AdmArchivo;
import Entidades_Auxiliares.Lista;
import Entidades_Auxiliares.Nodo;
import Presentacion.Reporte;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Clase que hereda de Buscador e implementa la estrategia de busqueda avara
 *
 * @author Cristhian Fuertes 1123259
 */
public class BuscadorAvaro extends Buscador {

    private Lista listaExpander = new Lista(2);

    /**
     * Constructor
     *
     * @param ad Archivo que contiene la información del problema a resolver
     */
    public BuscadorAvaro(AdmArchivo ad) {
        super(ad);

    }

    /**
     * Constructor
     *
     * @param dimension Tamaño de la cuadricula la cual corresponde al de la
     * matriz númerica leida del archivo
     * @param pesoObjeto1 El peso del objeto 1
     * @param pesoObjeto2 El peso del objeto 2
     * @param matriz La matriz númerica
     * @param penalidad El valor de las casillas de penalidad
     */
    public BuscadorAvaro(int dimension, int pesoObjeto1, int pesoObjeto2, int[][] matriz, int penalidad) {

        super(dimension, pesoObjeto1, pesoObjeto2, matriz, penalidad);

    }

    /**
     * Método redefinido de la clase Buscador.java
     *
     * @return La ruta tomadada por el sorting robot para resolver el problema
     */
    @Override
    public ArrayList<Point> buscar() {

        double tiempoInicial = System.currentTimeMillis();
        fil = robot.x;
        col = robot.y;

        do {
            int estimado = estimarCosto(determinar_Objetivo(), new Point(fil, col));
            arbol.add(new Nodo(new Point(fil, col), null, calcularCosto(fil, col), estimado));
            coorVisitadas.add(new Point(fil, col));
            expandirNodos();
            construirRutaPaso();
            arbol.clear();
            coorVisitadas.clear();
            paso++;
            if (paso == 4) {
                construirRutaOpcion();
                rutaPaso1.clear();
                rutaPaso2.clear();
                rutaPaso3.clear();
                rutaPaso4.clear();
                opcion++;
                restablecerCampo();
                fil = robot.x;
                col = robot.y;
                paso = 0;

            }


        } while (opcion < 6);

        construirRutaDefinitiva();
        double tiempoFinal = System.currentTimeMillis();
        tiempo = Math.abs(tiempoInicial - tiempoFinal);
        rep = new Reporte(profundidad, nodosExpandidos, tiempo, costoDef);

        return rutaDef;
    }

    /**
     * Método redefinido de la clase Buscador.java
     */
    @Override
    protected void expandirNodos() {

        do {
            boolean arriba = false, abajo = false, derecha = false, izquierda = false;



            Nodo pa;
            int ind = 0;
            if (arbol.size() == 1) {
                pa = arbol.get(0);
                // System.out.println("tamaño raiz" + arbol.size());
            } else {
                determinarNodosExpandir();
                pa = listaExpander.get(0);
                ind = arbol.indexOf(pa);
                arbol.get(ind).expandir(true);
                pa.expandir(true);
                //  System.out.println("Se instancion un padre "+ pa);
            }

            if (objeto1 != null) {
                if (fil == objeto1.x && col == objeto1.y) {
                    cargandoObj1 = true;
                }

            }

            if (objeto2 != null) {

                if (fil == objeto2.x && col == objeto2.y) {
                    cargandoObj2 = true;
                }

            }

            fil = pa.obtenerEstado().x;
            col = pa.obtenerEstado().y;


            if (esMeta(fil,col)) {
               // eliminarObjeto(determinar_Objetivo());
                break;
            }


            if (esValida(fil + 1, col) && !visitada(fil + 1, col) && !estorbo(fil + 1, col)) {//abajo

                int estimado = estimarCosto(determinar_Objetivo(), new Point(fil + 1, col));

                arbol.add(new Nodo(new Point(fil + 1, col), pa, calcularCosto(fil + 1, col), estimado));
                coorVisitadas.add(new Point(fil + 1, col));
                abajo = true;
              
            }
            if (esValida(fil, col + 1) && !visitada(fil, col + 1) && !estorbo(fil, col + 1)) {//derecha
               
                int estimado = estimarCosto(determinar_Objetivo(), new Point(fil, col + 1));
                arbol.add(new Nodo(new Point(fil, col + 1), pa, calcularCosto(fil, col + 1), estimado));
                coorVisitadas.add(new Point(fil, col + 1));
                derecha = true;
               
            }
            if (esValida(fil - 1, col) && !visitada(fil - 1, col) && !estorbo(fil - 1, col)) {//arriba
             
                int estimado = estimarCosto(determinar_Objetivo(), new Point(fil - 1, col));
                arbol.add(new Nodo(new Point(fil - 1, col), pa, calcularCosto(fil - 1, col), estimado));
                coorVisitadas.add(new Point(fil - 1, col));
                arriba = true;
               
            }
            if (esValida(fil, col - 1) && !visitada(fil, col - 1) && !estorbo(fil, col - 1)) {//izquierda
             
                int estimado = estimarCosto(determinar_Objetivo(), new Point(fil, col - 1));
                arbol.add(new Nodo(new Point(fil, col - 1), pa, calcularCosto(fil, col - 1), estimado));
                coorVisitadas.add(new Point(fil, col - 1));
                izquierda = true;
              
            }

            if (!arriba && !abajo && !izquierda && !derecha) {
                arbol.get(ind).expandir(false);
                arbol.get(ind).desigarComoHoja(true);

            }

        } while (true);

    }

    @Override
    protected void determinarNodosExpandir() {
        int i = 0;

        listaExpander.clear();
        while (i < arbol.size()) {
            Nodo e = arbol.get(i);
            if (!(e.estaExpandido()) && !(e.esHoja())) {
                listaExpander.add(e);
            }
            i++;
        }

        if (paso == 0) {
            nodosExpandidosP1++;

        } else {
            if (paso == 1) {
                nodosExpandidosP2++;
            } else {
                if (paso == 2) {
                    nodosExpandidosP3++;

                } else {
                    if (paso == 3) {
                        nodosExpandidosP4++;

                    }
                }
            }
        }

    }

    @Override
    protected void expandirRaiz() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
