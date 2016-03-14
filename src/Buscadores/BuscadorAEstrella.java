/*
 * BuscadorAEstrella.java
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
 * Clase que hereda de Buscador e implementa la estrategia de busqueda A*
 *
 *
 * @author Cristhian Fuertes 1123259
 */
public class BuscadorAEstrella extends Buscador {

    private Lista listaExpander = new Lista(3);

    /**
     * Constructor
     *
     * @param ad Archivo de texto que contiene la información del problema a
     * resolver
     */
    public BuscadorAEstrella(AdmArchivo ad) {
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
    public BuscadorAEstrella(int dimension, int pesoObjeto1, int pesoObjeto2, int[][] matriz, int penalidad) {

        super(dimension, pesoObjeto1, pesoObjeto2, matriz, penalidad);

    }

    /**
     * Método sobrescrito de la clase Buscador.java
     *
     * @return La ruta que toma el sorting robot para resolver el problema
     */
    @Override
    public ArrayList<Point> buscar() {
        fil = robot.x;
        col = robot.y;
        double tiempoInicial = System.currentTimeMillis();
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
     * Metodo sobrescrito de la clase Buscador.java
     */
    @Override
    protected void expandirNodos() {

        expandirRaiz();


        do {




            Nodo pa;
            int ind;

            determinarNodosExpandir();
            pa = listaExpander.get(0);
            ind = arbol.indexOf(pa);
            arbol.get(ind).expandir(true);
            pa.expandir(true);


            fil = pa.obtenerEstado().x;
            col = pa.obtenerEstado().y;

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

            if (esMeta(fil, col)) {
                //eliminarObjeto(determinar_Objetivo());
                break;

            }





            if (esValida(fil + 1, col) && !estorbo(fil + 1, col)) {//abajo


                int estimado = estimarCosto(determinar_Objetivo(), new Point(fil + 1, col));
                Nodo n = new Nodo(new Point(fil + 1, col), pa, calcularCosto(fil + 1, col), estimado);

                if (!esSuPropioAbuelo(n)) {
                    arbol.add(n);
                    coorVisitadas.add(new Point(fil + 1, col));

                }
            }
            if (esValida(fil, col + 1) && !estorbo(fil, col + 1)) {//derecha


                int estimado = estimarCosto(determinar_Objetivo(), new Point(fil, col + 1));
                Nodo n = new Nodo(new Point(fil, col + 1), pa, calcularCosto(fil, col + 1), estimado);
                if (!esSuPropioAbuelo(n)) {
                    arbol.add(n);
                    coorVisitadas.add(new Point(fil, col + 1));

                }
            }
            if (esValida(fil - 1, col) && !estorbo(fil - 1, col)) {//arriba


                int estimado = estimarCosto(determinar_Objetivo(), new Point(fil - 1, col));
                Nodo n = new Nodo(new Point(fil - 1, col), pa, calcularCosto(fil - 1, col), estimado);
                if (!esSuPropioAbuelo(n)) {
                    arbol.add(n);
                    coorVisitadas.add(new Point(fil - 1, col));


                }
            }
            if (esValida(fil, col - 1) && !estorbo(fil, col - 1)) {//izquierda


                int estimado = estimarCosto(determinar_Objetivo(), new Point(fil, col - 1));
                Nodo n = new Nodo(new Point(fil, col - 1), pa, calcularCosto(fil, col - 1), estimado);
                if (!esSuPropioAbuelo(n)) {
                    arbol.add(n);
                    coorVisitadas.add(new Point(fil, col - 1));

                }
            }



        } while (true);

    }

    /**
     * Método sobrescrito de la clase Buscador.java
     */
    @Override
    protected void determinarNodosExpandir() {
        int i = 0;

        listaExpander.clear();
        while (i < arbol.size()) {
            Nodo e = arbol.get(i);
            if (!(e.estaExpandido())) {
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



        Nodo pa = arbol.get(0);

        if (esValida(fil + 1, col) && !estorbo(fil + 1, col)) {//abajo



            int estimado = estimarCosto(determinar_Objetivo(), new Point(fil + 1, col));
            arbol.add(new Nodo(new Point(fil + 1, col), pa, calcularCosto(fil + 1, col), estimado));
            coorVisitadas.add(new Point(fil + 1, col));

        }
        if (esValida(fil, col + 1) && !estorbo(fil, col + 1)) {//derecha


            int estimado = estimarCosto(determinar_Objetivo(), new Point(fil, col + 1));
            arbol.add(new Nodo(new Point(fil, col + 1), pa, calcularCosto(fil, col + 1), estimado));
            coorVisitadas.add(new Point(fil, col + 1));


        }
        if (esValida(fil - 1, col) && !estorbo(fil - 1, col)) {//arriba


            int estimado = estimarCosto(determinar_Objetivo(), new Point(fil - 1, col));
            arbol.add(new Nodo(new Point(fil - 1, col), pa, calcularCosto(fil - 1, col), estimado));
            coorVisitadas.add(new Point(fil - 1, col));


        }
        if (esValida(fil, col - 1) && !estorbo(fil, col - 1)) {//izquierda


            int estimado = estimarCosto(determinar_Objetivo(), new Point(fil, col + 1));
            arbol.add(new Nodo(new Point(fil, col - 1), pa, calcularCosto(fil, col - 1), estimado));
            coorVisitadas.add(new Point(fil, col - 1));

        }

    }
}
