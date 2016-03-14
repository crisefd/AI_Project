/*
 *BuscadorAmplitud.java
 */
package Buscadores;

import Buscadores.Buscador;
import Manejo_Archivos.AdmArchivo;
import Manejo_Archivos.AdmArchivo;
import Entidades_Auxiliares.Nodo;
import Entidades_Auxiliares.Nodo;
import Presentacion.Reporte;
import Presentacion.Reporte;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Clase que hereda de Buscador e implementa la estrategia de busqueda
 * preferente por amplitud
 *
 * @author Cristhian Fuertes 1123259
 */
public class BuscadorAmplitud extends Buscador {

    private int indice;

    /**
     * Constructor
     *
     * @param ad Archivo que contiene la información del problema a resolver
     */
    public BuscadorAmplitud(AdmArchivo ad) {
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
    public BuscadorAmplitud(int dimension, int pesoObjeto1, int pesoObjeto2, int[][] matriz, int penalidad) {

        super(dimension, pesoObjeto1, pesoObjeto2, matriz, penalidad);

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

    /**
     * Método redefinido de Buscador.java
     *
     * @return
     */
    @Override
    public ArrayList<Point> buscar() {
        double tiempoInicial = (double) System.currentTimeMillis();
        fil = robot.x;
        col = robot.y;


        do {
            indice = 1;
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
        double tiempoFinal = (double) System.currentTimeMillis();

        tiempo = Math.abs(tiempoInicial - tiempoFinal);

        rep = new Reporte(profundidad, nodosExpandidos, tiempo, costoDef);
        return rutaDef;


    }

    /**
     * Método redefinido de Buscador.java
     */
    @Override
    protected void expandirNodos() {



        expandirRaiz();
        do {
            Nodo pa;
            pa = siguientePadre();
            fil = pa.obtenerEstado().x;
            col = pa.obtenerEstado().y;
            pa.expandir(true);

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
            arbol.get(indice).expandir(true);




        } while (true);

    }

    /**
     * Determina el siguiente nodo ha ser expandido
     *
     * @return el siguiente nodo a ser expandido
     */
    protected Nodo siguientePadre() {

        Nodo p = null;
       
        while (indice < arbol.size()) {
            if (!(arbol.get(indice).estaExpandido())) {

                p = arbol.get(indice);
               
                break;
            }
            indice++;
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


        return p;
    }
    /**
     * Método sobrescrito de Buscador.java
     */
    @Override
    protected void determinarNodosExpandir() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
