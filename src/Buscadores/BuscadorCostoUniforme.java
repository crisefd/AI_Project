/*
 *BuscadorCostoUniforme.java
 */
package Buscadores;

import Buscadores.Buscador;
import Manejo_Archivos.AdmArchivo;
import Entidades_Auxiliares.Lista;
import Entidades_Auxiliares.Nodo;
import Presentacion.Reporte;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Clase que hereda de Buscador e implementa la estrategia de busqueda de costo
 * uniforme
 *
 * @author Cristhian Fuertes 
 */
public class BuscadorCostoUniforme extends Buscador {

    private Lista listaExpander = new Lista(1);

    /**
     * Constructor
     *
     * @param ad Archivo que contiene la información del problema a resolver
     */
    public BuscadorCostoUniforme(AdmArchivo ad) {
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
    public BuscadorCostoUniforme(int dimension, int pesoObjeto1, int pesoObjeto2, int[][] matriz, int penalidad) {

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
            arbol.add(new Nodo(new Point(fil, col), null, calcularCosto(fil, col)));
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
        tiempo = Math.abs(tiempoFinal - tiempoInicial);
        rep = new Reporte(profundidad, nodosExpandidos, tiempo, costoDef);

        return rutaDef;

    }

    /**
     * Método sobrescrito de Buscador.java
     */
    @Override
    protected void expandirNodos() {



        expandirRaiz();

        do {
            determinarNodosExpandir();
            Nodo pa;
            int ind;
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

                break;

            }


            if (esValida(fil + 1, col) && !estorbo(fil + 1, col)) {//abajo

                Nodo n = new Nodo(new Point(fil + 1, col), pa, calcularCosto(fil + 1, col));
                if (!esSuPropioAbuelo(n)) {
                    arbol.add(n);
                    coorVisitadas.add(new Point(fil + 1, col));
                  
                }
            }
            if (esValida(fil, col + 1) && !estorbo(fil, col + 1)) {//derecha
               
                Nodo n = new Nodo(new Point(fil, col + 1), pa, calcularCosto(fil, col + 1));

                if (!esSuPropioAbuelo(n)) {
                    arbol.add(n);
                    coorVisitadas.add(new Point(fil, col + 1));
                 
                }

            }
            if (esValida(fil - 1, col) && !estorbo(fil - 1, col)) {//arriba
             
                Nodo n = new Nodo(new Point(fil - 1, col), pa, calcularCosto(fil - 1, col));
                if (!esSuPropioAbuelo(n)) {
                    arbol.add(n);
                    coorVisitadas.add(new Point(fil - 1, col));
                   
                }
            }
            if (esValida(fil, col - 1) && !estorbo(fil, col - 1)) {//izquierda
                
                Nodo n = new Nodo(new Point(fil, col - 1), pa, calcularCosto(fil, col - 1));
                if (!esSuPropioAbuelo(n)) {
                    arbol.add(n);
                    coorVisitadas.add(new Point(fil, col - 1));
                  
                }
            }



        } while (true);


    }

    @Override
    protected void determinarNodosExpandir() {
        int i = 0;

        listaExpander.clear();
        while (i < arbol.size()) {
            if (!(arbol.get(i).estaExpandido())) {
                listaExpander.add(arbol.get(i));
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

            arbol.add(new Nodo(new Point(fil + 1, col), pa, calcularCosto(fil + 1, col)));
            coorVisitadas.add(new Point(fil + 1, col));

        }
        if (esValida(fil, col + 1) && !estorbo(fil, col + 1)) {//derecha

            arbol.add(new Nodo(new Point(fil, col + 1), pa, calcularCosto(fil, col + 1)));
            coorVisitadas.add(new Point(fil, col + 1));


        }
        if (esValida(fil - 1, col) && !estorbo(fil - 1, col)) {//arriba

            arbol.add(new Nodo(new Point(fil - 1, col), pa, calcularCosto(fil - 1, col)));
            coorVisitadas.add(new Point(fil - 1, col));

        }
        if (esValida(fil, col - 1) && !estorbo(fil, col - 1)) {//izquierda

            arbol.add(new Nodo(new Point(fil, col - 1), pa, calcularCosto(fil, col - 1)));
            coorVisitadas.add(new Point(fil, col - 1));

        }

    }
}
