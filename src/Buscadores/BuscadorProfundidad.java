/*
 *BuscadorProfundidad.java
 */
package Buscadores;

import Buscadores.Buscador;
import Manejo_Archivos.AdmArchivo;
import Entidades_Auxiliares.Nodo;
import Presentacion.Reporte;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Clase que hereda de Buscador e implementa la estrategia de busqueda
 * preferente por profundidad sin ciclos
 *
 * @author Cristhian Fuertes 1123259
 *
 */
public class BuscadorProfundidad extends Buscador {

    private int ind;

    /**
     * Constructor
     *
     * @param ad Archivo que contiene la información del problema a resolver
     */
    public BuscadorProfundidad(AdmArchivo ad) {
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
    public BuscadorProfundidad(int dimension, int pesoObjeto1, int pesoObjeto2, int[][] matriz, int penalidad) {

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

           
            Nodo pa = siguientePadre();
            arbol.get(ind).expandir(true);
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


            if (!pa.obtenerGenerada(3)) {//izquierda
                pa.asignarGeneradas(3, true);
                arbol.get(ind).asignarGeneradas(3, true);
                if (esValida(fil, col - 1) && !estorbo(fil, col - 1) && !visitada(fil, col - 1)) {

                    arbol.add(new Nodo(new Point(fil, col - 1), pa, calcularCosto(fil, col - 1)));
                    coorVisitadas.add(new Point(fil, col - 1));


                }

               

            } else {

                if (!pa.obtenerGenerada(2)) {//arriba
                    pa.asignarGeneradas(2, true);
                    arbol.get(ind).asignarGeneradas(2, true);
                    if (esValida(fil - 1, col) && !estorbo(fil - 1, col) && !visitada(fil - 1, col)) {

                        arbol.add(new Nodo(new Point(fil - 1, col), pa, calcularCosto(fil - 1, col)));
                        coorVisitadas.add(new Point(fil - 1, col));

                    }
                   

                } else {
                    if (!pa.obtenerGenerada(1)) {//derecha
                        pa.asignarGeneradas(1, true);
                        arbol.get(ind).asignarGeneradas(1, true);
                        if (esValida(fil, col + 1) && !estorbo(fil, col + 1) && !visitada(fil, col + 1)) {

                            arbol.add(new Nodo(new Point(fil, col + 1), pa, calcularCosto(fil, col + 1)));
                            coorVisitadas.add(new Point(fil, col + 1));

                        }
                       

                    } else {
                        if (!pa.obtenerGenerada(0)) {//abajo
                            pa.asignarGeneradas(0, true);
                            arbol.get(ind).asignarGeneradas(0, true);
                            if (esValida(fil + 1, col) && !estorbo(fil + 1, col) && !visitada(fil + 1, col)) {

                                arbol.add(new Nodo(new Point(fil + 1, col), pa, calcularCosto(fil + 1, col)));
                                coorVisitadas.add(new Point(fil + 1, col));


                            }
                           
                        }
                    }
                }

            }


           



        } while (true);

    }

    @Override
    protected void expandirRaiz() {



        Nodo pa = arbol.get(0);


        arbol.get(0).asignarGeneradas(0, true);
        pa.asignarGeneradas(0, true);
        if (esValida(fil + 1, col) && !estorbo(fil + 1, col)) {//abajo

            arbol.add(new Nodo(new Point(fil + 1, col), pa, calcularCosto(fil + 1, col)));
            coorVisitadas.add(new Point(fil + 1, col));
           
        }

        arbol.get(0).asignarGeneradas(1, true);
        pa.asignarGeneradas(1, true);
        if (esValida(fil, col + 1) && !estorbo(fil, col + 1)) {//derecha
           
            arbol.add(new Nodo(new Point(fil, col + 1), pa, calcularCosto(fil, col + 1)));
            coorVisitadas.add(new Point(fil, col + 1));
           

        }

        arbol.get(0).asignarGeneradas(2, true);
        pa.asignarGeneradas(2, true);
        if (esValida(fil - 1, col) && !estorbo(fil - 1, col)) {//arriba
           
            arbol.add(new Nodo(new Point(fil - 1, col), pa, calcularCosto(fil - 1, col)));
            coorVisitadas.add(new Point(fil - 1, col));
           

        }
        arbol.get(0).asignarGeneradas(3, true);
        pa.asignarGeneradas(3, true);
        if (esValida(fil, col - 1) && !estorbo(fil, col - 1)) {//izquierda
          
            arbol.add(new Nodo(new Point(fil, col - 1), pa, calcularCosto(fil, col - 1)));
            coorVisitadas.add(new Point(fil, col - 1));
           

        }
      
    }

    private Nodo siguientePadre() {

        Nodo n = null;
        ind = arbol.size() - 1;

        while (ind >= 0) {
            if (!arbol.get(ind).obtenerGenerada(0) || !arbol.get(ind).obtenerGenerada(1) || !arbol.get(ind).obtenerGenerada(2) || !arbol.get(ind).obtenerGenerada(3)) {
                n = arbol.get(ind);

                break;

            }

            ind--;
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

        return n;

    }

    @Override
    protected void determinarNodosExpandir() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
