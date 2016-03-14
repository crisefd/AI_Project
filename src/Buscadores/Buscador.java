/*
 * Buscador.java
 * 
 */
package Buscadores;

import Entidades_Auxiliares.Actor;
import Manejo_Archivos.AdmArchivo;
import Entidades_Auxiliares.Elementos;
import Entidades_Auxiliares.Nodo;
import Presentacion.Reporte;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase que contiene los métodos y atributos fundamentales y que heredan los
 * demás clases que la heredan para desarrollar los algoritmos de busqueda
 *
 * @author Cristhian Fuertes 1123259
 *
 */
public abstract class Buscador implements Elementos {

    /**
     * Matriz númerica con las posiciones de los elementos del campo
     */
    protected int[][] cuadricula;
    /**
     * Matriz númerica que copia los valores iniciales de cuadricula
     */
    protected int[][] cuadriculaAux;
    /**
     * Valor booleano que cambia de valor dependiendo de si el agente esta o no
     * cargando el objeto 1
     */
    protected boolean cargandoObj1 = false,
            /**
             * Valor booleano que cambia de valor dependiendo de si el agente
             * esta o no cargando el objeto 2
             */
            cargandoObj2 = false;
    /**
     * Matriz que contiene todas las opciones de búsqueda para el algoritmo
     */
    protected final String[][] OPCIONES = { {"o1", "l1", "o2", "l2"},
                                            {"o2", "l2", "o1", "l1"},
                                            {"o1", "o2", "l1", "l2"},
                                            {"o1", "o2", "l2", "l1"},
                                            {"o2", "o1", "l2", "l1"},
                                            {"o2", "o1", "l1", "l2"} };
    /**
     * Indica la fila actual que esta siendo analizada en el algoritmo
     */
    protected int opcion = 0,
            /**
             * Indica la columna actual que esta siendo analizada en el
             * algoritmo
             */
            paso = 0,
            /**
             * El peso del objeto 1
             */
            pesoObj1,
            /**
             * El peso del objeto 2
             */
            pesoObj2,
            /**
             * El costo del robot de moverse al terminar la busqueda en la
             * primera columna de la matriz OPCIONES
             */
            costoPaso1 = 0,
            /**
             * El costo del robot de moverse al terminar la busqueda en la
             * segunda columna de la matriz OPCIONES
             */
            costoPaso2 = 0,
            /**
             * El costo del robot de moverse al terminar la busqueda en la
             * tercera columna de la matriz OPCIONES
             */
            costoPaso3 = 0,
            /**
             * El costo del robot de moverse al terminar la busqueda en la
             * cuarta columna de la matriz OPCIONES
             */
            costoPaso4,
            /**
             * La suma de los costo de los cuatro pasos en la primera opcione de
             * la matriz OPCIONES
             */
            costoOp1 = 0,
            /**
             * La suma de los costo de los cuatro pasos en la segunda opción de
             * la matriz OPCIONES
             */
            costoOp2 = 0,
            /**
             * La suma de los costo de los cuatro pasos en la tercera opción de
             * la matriz OPCIONES
             */
            costoOp3 = 0,
            /**
             * La suma de los costo de los cuatro pasos en la cuarta opción de
             * la matriz OPCIONES
             */
            costoOp4 = 0,
            /**
             * La suma de los costo de los cuatro pasos en la quinta opción de
             * la matriz OPCIONES
             */
            costoOp5 = 0,
            /**
             * La suma de los costo de los cuatro pasos en la sexta opción de la
             * matriz OPCIONES
             */
            costoOp6 = 0,
    
    
            costoDef=0;
    /**
     * Lista que contiene todos los nodos del arbol de búsqueda
     */
    protected ArrayList<Nodo> arbol = new ArrayList<Nodo>();
    /**
     * Lista que contiene todas las coordenadas visitadas por el agente para
     * completar el paso 1
     */
    protected ArrayList<Point> rutaPaso1 = new ArrayList<Point>(),
            /**
             * Lista que contiene todas las coordenadas visitadas por el agente
             * para completar el paso 2
             */
            rutaPaso2 = new ArrayList<Point>(),
            /**
             * Lista que contiene todas las coordenadas visitadas por el agente
             * para completar el paso 3
             */
            rutaPaso3 = new ArrayList<Point>(),
            /**
             * Lista que contiene todas las coordenadas visitadas por el agente
             * para completar el paso 4
             */
            rutaPaso4 = new ArrayList<Point>();
    /**
     * Lista de todas las coordenas de los nodos generados en el algoritmo de
     * búsqueda
     */
    protected ArrayList<Point> coorVisitadas = new ArrayList<Point>();
    /**
     * Lista de todas las coordenadas recorridas durante los 4 pasos para la
     * opción 1
     */
    protected ArrayList<Point> rutaOp1 = new ArrayList<Point>(),
            /**
             * Lista de todas las coordenadas recorridas durante los 4 pasos
             * para la opción 2
             */
            rutaOp2 = new ArrayList<Point>(),
            /**
             * Lista de todas las coordenadas recorridas durante los 4 pasos
             * para la opción 3
             */
            rutaOp3 = new ArrayList<Point>(),
            /**
             * Lista de todas las coordenadas recorridas durante los 4 pasos
             * para la opción 4
             */
            rutaOp4 = new ArrayList<Point>(),
            /**
             * Lista de todas las coordenadas recorridas durante los 4 pasos
             * para la opción 5
             */
            rutaOp5 = new ArrayList<Point>(),
            /**
             * Lista de todas las coordenadas recorridas durante los 4 pasos
             * para la opción 6
             */
            rutaOp6 = new ArrayList<Point>(),
            /**
             * La ruta que contiene las coordenadas recorridas durante la opción
             * que tenia el menor costo de movimiento
             */
            rutaDef = new ArrayList<Point>();
    /**
     * El valor de las casillas de penalidad
     */
    protected final int PENALIDAD;
    /**
     * El reporte del comportamiento del algoritmo que sera mostrada al usuario
     */
    protected Reporte rep;
    /**
     * Contiene la ubicación inicial del robot
     */
    protected Actor robot,
            /**
             * Contiene la ubicación inicial del objeto 1
             */
            objeto1,
            /**
             * Contiene la ubicación inicial del objeto 2
             */
            objeto2,
            /**
             * Contiene la ubicación del lugar 1
             */
            lugar1,
            /**
             * Contiene la ubicación inicial del lugar 2
             */
            lugar2;
    /**
     * La fila del nodo que actualmente esta siendo expandido
     */
    protected int fil,
            /**
             * La columna del nodo que actualmente esta siendo expandido
             */
            col,
            /**
             * La cantidad de nodos expandidos durante la busqueda en la opcion
             * 1
             */
            nodosExpandidosOp1,
            /**
             * La cantidad de nodos expandidos durante la busqueda en la opcion
             * 2
             */
            nodosExpandidosOp2,
            /**
             * La cantidad de nodos expandidos durante la busqueda en la opcion
             * 3
             */
            nodosExpandidosOp3,
            /**
             * La cantidad de nodos expandidos durante la busqueda en la opcion
             * 4
             */
            nodosExpandidosOp4,
            /**
             * La cantidad de nodos expandidos durante la busqueda en la opcion
             * 5
             */
            nodosExpandidosOp5,
            /**
             * La cantidad de nodos expandidos durante la busqueda en la opcion
             * 6
             */
            nodosExpandidosOp6,
            /**
             * La cantidad de nodos expandidos durante la busqueda el paso 1
             */
            nodosExpandidosP1 = 1,
            /**
             * La cantidad de nodos expandidos durante la busqueda el paso 2
             */
            nodosExpandidosP2 = 1,
            /**
             * La cantidad de nodos expandidos durante la busqueda el paso 3
             */
            nodosExpandidosP3 = 1,
            /**
             * La cantidad de nodos expandidos durante la busqueda el paso 4
             */
            nodosExpandidosP4 = 1,
            /**
             * La cantidad de nodos expandidos durante la busqueda en la opción
             * con el menor costo movimientos
             */
            nodosExpandidos;
    /**
     * La profundidad del arbol al la busqueda en el paso 1
     */
    protected int profundidadP1 = 1,
            /**
             * La profundidad del arbol al la busqueda en el paso 2
             */
            profundidadP2 = 1,
            /**
             * La profundidad del arbol al la busqueda en el paso 3
             */
            profundidadP3 = 1,
            /**
             * La profundidad del arbol al la busqueda en el paso 4
             */
            profundidadP4 = 1,
            /**
             * La profundidad del arbol al la busqueda en la opción 1
             */
            profundidadOp1,
            /**
             * La profundidad del arbol en la busqueda en la opción 2
             */
            profundidadOp2,
            /**
             * La profundidad del arbol en la busqueda en la opción 3
             */
            profundidadOp3,
            /**
             * La profundidad del arbol en la busqueda en la opción 4
             */
            profundidadOp4,
            /**
             * La profundidad del arbol en la busqueda en la opción 5
             */
            profundidadOp5,
            /**
             * La profundidad del arbol en la busqueda en la opción 6
             */
            profundidadOp6,
            /**
             * La profundidad del arbol en la busqueda de la opcion con el menor
             * costo de movimientos
             */
            profundidad;
    /**
     * El tiempo que tarda el algoritmo en terminar la busquedas
     */
    protected double tiempo;
    /**
     * Objeto AdmArchivo
     */
    protected AdmArchivo adm;

    /**
     * Realiza la busqueda y encuentra la solución al  problema
     *
     * @return La ruta del robot
     */
    public abstract ArrayList<Point> buscar();

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
    public Buscador(int dimension, int pesoObjeto1, int pesoObjeto2, int[][] matriz, int penalidad) {
        PENALIDAD = penalidad;
        pesoObj1 = pesoObjeto1;
        pesoObj2 = pesoObjeto2;
        cuadricula = matriz;
        cuadriculaAux = new int[cuadricula.length][cuadricula.length];
        for (int i = 0; i < cuadricula.length; i++) {
            for (int j = 0; j < cuadricula.length; j++) {
                cuadriculaAux[i][j] = cuadricula[i][j];

            }
        }

        for (int i = 0; i < cuadricula.length; i++) {
            for (int j = 0; j < cuadricula.length; j++) {

                int n = cuadricula[i][j];

                switch (n) {

                    case ROBOT:
                        robot = new Actor(i, j, 0);
                        break;

                    case OBJ1:
                        objeto1 = new Actor(i, j, pesoObj1);
                        break;

                    case OBJ2:
                        objeto2 = new Actor(i, j, pesoObj2);
                        break;

                    case LUG1:
                        lugar1 = new Actor(i, j, 0);
                        break;

                    case LUG2:
                        lugar2 = new Actor(i, j, 0);
                        break;


                }
            }
        }


    }

    /**
     * Constructor
     *
     * @param ad Archivo que contiene la información del problema a resolver
     */
    public Buscador(AdmArchivo ad) {

        this.adm = ad;
        cuadricula = adm.obtenerCuadricula();
        cuadriculaAux = new int[cuadricula.length][cuadricula.length];
        for (int i = 0; i < cuadricula.length; i++) {
            for (int j = 0; j < cuadricula.length; j++) {
                cuadriculaAux[i][j] = cuadricula[i][j];

            }
        }
        pesoObj1 = adm.obtenerPesoObj1();
        pesoObj2 = adm.obtenerpesoObj2();
        PENALIDAD = adm.obtenerPenalidad();

        for (int i = 0; i < cuadricula.length; i++) {
            for (int j = 0; j < cuadricula.length; j++) {

                int n = cuadricula[i][j];

                switch (n) {

                    case ROBOT:
                        robot = new Actor(i, j, 0);
                        break;

                    case OBJ1:
                        objeto1 = new Actor(i, j, pesoObj1);
                        break;

                    case OBJ2:
                        objeto2 = new Actor(i, j, pesoObj2);
                        break;

                    case LUG1:
                        lugar1 = new Actor(i, j, 0);
                        break;

                    case LUG2:
                        lugar2 = new Actor(i, j, 0);
                        break;


                }



            }
        }



    }

    /**
     * Determina si el nodo que actualmente esta siendo generado tiene el mismo
     * estado que el padre de su padre
     *
     * @param n El nodo a analizar
     * @return Resultado indicando si es verdad o no la evaluación
     */
    protected boolean esSuPropioAbuelo(Nodo n) {
        boolean res = false;


        Nodo abuelo = (n.obtenerPadre().obtenerPadre());

        if (n.obtenerEstado().equals(abuelo.obtenerEstado())) {
            res = true;
        }


        return res;
    }

    /**
     * Restablece las condiciones iniciales del campo al finalizar una opción de
     * búsqueda
     */
    protected void restablecerCampo() {




        for (int i = 0; i < cuadricula.length; i++) {
            for (int j = 0; j < cuadricula.length; j++) {
                cuadricula[i][j] = cuadriculaAux[i][j];

            }
        }



        for (int i = 0; i < cuadricula.length; i++) {
            for (int j = 0; j < cuadricula.length; j++) {

                int n = cuadricula[i][j];

                switch (n) {

                    case ROBOT:
                        robot = new Actor(i, j, 0);
                        break;
                    case OBJ1:
                        objeto1 = new Actor(i, j, pesoObj1);
                        break;
                    case OBJ2:
                        objeto2 = new Actor(i, j, pesoObj2);
                        break;
                    case LUG1:
                        lugar1 = new Actor(i, j, 0);
                        break;
                    case LUG2:
                        lugar2 = new Actor(i, j, 0);
                        break;


                }

            }
        }

    }

    /**
     * Determina si las coordenadas que quiero visitar estan dentro de los
     * limites de la matriz
     *
     * @param fil La fila a la se desea mover
     * @param col La columna al que se desea mover
     * @return Valor verdadero o falso correspondiente al resultado de la
     * evalución
     */
    protected boolean esValida(int fil, int col) {

        try {
            int a = cuadricula[fil][col];



        } catch (ArrayIndexOutOfBoundsException ex) {

            return false;

        }

        return true;
    }

    /**
     * Inidica la posición dentro del árbol donde se encuentra el nodo con el
     * estado meta
     *
     * @return El indice del nodo
     */
    protected int indiceMeta() {
        int ind = 0;

        while (ind < arbol.size()) {
            if (arbol.get(ind).obtenerEstado().x == fil && arbol.get(ind).obtenerEstado().y == col) {
                break;
            }
            ind++;

        }


        return ind;
    }

    /**
     * Determina cual es el nodo que sera el siguiente para expandir
     */
    protected abstract void determinarNodosExpandir();

    /**
     * Determina el elemento que esta actualmente siendo buscado
     *
     * @return El elemento buscado
     */
    protected Actor determinar_Objetivo() {
        Actor ac = null;

        switch (opcion) {
            case 0:
                switch (paso) {

                    case 0:
                        ac = objeto1;
                        break;
                    case 1:
                        ac = lugar1;
                        break;
                    case 2:
                        ac = objeto2;
                        break;
                    case 3:
                        ac = lugar2;
                        break;

                }

                break;
            case 1:
                switch (paso) {
                    case 0:
                        ac = objeto2;
                        break;
                    case 1:
                        ac = lugar2;
                        break;
                    case 2:
                        ac = objeto1;
                        break;
                    case 3:
                        ac = lugar1;
                        break;

                }

                break;
            case 2:
                switch (paso) {

                    case 0:
                        ac = objeto1;
                        break;
                    case 1:
                        ac = objeto2;
                        break;
                    case 2:
                        ac = lugar1;
                        break;
                    case 3:
                        ac = lugar2;
                        break;


                }

                break;
            case 3:
                switch (paso) {

                    case 0:
                        ac = objeto1;
                        break;
                    case 1:
                        ac = objeto2;
                        break;
                    case 2:
                        ac = lugar2;
                        break;
                    case 3:
                        ac = lugar1;
                        break;

                }

                break;
            case 4:
                switch (paso) {

                    case 0:
                        ac = objeto2;
                        break;
                    case 1:
                        ac = objeto1;
                        break;
                    case 2:
                        ac = lugar2;
                        break;
                    case 3:
                        ac = lugar1;
                        break;

                }

                break;
            case 5:
                switch (paso) {

                    case 0:
                        ac = objeto2;
                        break;
                    case 1:
                        ac = objeto1;
                        break;
                    case 2:
                        ac = lugar1;
                        break;
                    case 3:
                        ac = lugar2;
                        break;

                }

                break;

        }

        return ac;
    }

    /**
     * Determina el elemento que esta actualmente siendo buscado
     *
     * @return La cadena que representa el elemento buscado
     */
    protected String determinarObjetivo() {
        String ac = null;

        switch (opcion) {
            case 0:
                switch (paso) {

                    case 0:
                        ac = "o1";
                        break;
                    case 1:
                        ac = "l1";
                        break;
                    case 2:
                        ac = "o2";
                        break;
                    case 3:
                        ac = "l2";
                        break;

                }

                break;
            case 1:
                switch (paso) {
                    case 0:
                        ac = "o2";
                        break;
                    case 1:
                        ac = "l2";
                        break;
                    case 2:
                        ac = "o1";
                        break;
                    case 3:
                        ac = "l1";
                        break;

                }

                break;
            case 2:
                switch (paso) {

                    case 0:
                        ac = "o1";
                        break;
                    case 1:
                        ac = "o2";
                        break;
                    case 2:
                        ac = "l1";
                        break;
                    case 3:
                        ac = "l2";
                        break;


                }

                break;
            case 3:
                switch (paso) {

                    case 0:
                        ac = "o1";
                        break;
                    case 1:
                        ac = "o2";
                        break;
                    case 2:
                        ac = "l2";
                        break;
                    case 3:
                        ac = "l1";
                        break;

                }

                break;
            case 4:
                switch (paso) {

                    case 0:
                        ac = "o2";
                        break;
                    case 1:
                        ac = "o1";
                        break;
                    case 2:
                        ac = "l2";
                        break;
                    case 3:
                        ac = "l1";
                        break;

                }

                break;
            case 5:
                switch (paso) {

                    case 0:
                        ac = "o2";
                        break;
                    case 1:
                        ac = "o1";
                        break;
                    case 2:
                        ac = "l1";
                        break;
                    case 3:
                        ac = "l2";
                        break;

                }

                break;

        }

        return ac;
    }

    /**
     * Elimina los objetos del campo cuando son encontrados por el agente
     *
     * @param ob El objeto encontrado
     */
    protected void eliminarObjeto(Actor ob) {

        if (ob.equals(objeto1)) {
            objeto1 = null;

            for (int i = 0; i < cuadricula.length; i++) {
                for (int j = 0; j < cuadricula.length; j++) {
                    if (cuadricula[i][j] == OBJ1) {
                        cuadricula[i][j] = LIBRE;

                    }
                }
            }
        } else {
            if (ob.equals(objeto2)) {
                objeto2 = null;
                for (int i = 0; i < cuadricula.length; i++) {
                    for (int j = 0; j < cuadricula.length; j++) {
                        if (cuadricula[i][j] == OBJ2) {
                            cuadricula[i][j] = LIBRE;

                        }
                    }
                }
            }

        }

    }

    /**
     * Sirve de condición de parada del algoritmo
     *
     * @param i La fila del nodo que esta siendo expandido
     * @param j La columna del nodo que esta siendo expandido
     * @return El resultado de la evalución
     */
    protected boolean esMeta(int i, int j) {
        boolean res = false;
        switch (opcion) {
            case 0:
                switch (paso) {

                    case 0:

                        if (i == objeto1.x && j == objeto1.y) {
                            eliminarObjeto(objeto1);

                            res = true;

                        }
                        ;
                        break;
                    case 1:
                        if (i == lugar1.x && j == lugar1.y) {
                            cargandoObj1 = false;
                            res = true;
                        }
                        ;
                        break;
                    case 2:
                        if (i == objeto2.x && j == objeto2.y) {
                            eliminarObjeto(objeto2);
                            res = true;
                        }
                        ;
                        break;
                    case 3:
                        if (i == lugar2.x && j == lugar2.y) {
                            cargandoObj2 = false;
                            res = true;
                        }
                        break;

                }

                break;
            case 1:
                switch (paso) {
                    case 0:
                        if (i == objeto2.x && j == objeto2.y) {
                            eliminarObjeto(objeto2);
                            res = true;
                        }
                        ;
                        break;
                    case 1:
                        if (i == lugar2.x && j == lugar2.y) {
                            cargandoObj2 = false;
                            res = true;
                        }
                        ;
                        break;
                    case 2:
                        if (i == objeto1.x && j == objeto1.y) {
                            eliminarObjeto(objeto1);

                            res = true;
                        }
                        ;
                        break;
                    case 3:
                        if (i == lugar1.x && j == lugar1.y) {
                            cargandoObj1 = false;
                            res = true;
                        }
                        ;
                        break;

                }

                break;
            case 2:
                switch (paso) {

                    case 0:
                        if (i == objeto1.x && j == objeto1.y) {
                            eliminarObjeto(objeto1);
                            res = true;
                        }
                        ;
                        break;
                    case 1:
                        if (i == objeto2.x && j == objeto2.y) {
                            eliminarObjeto(objeto2);
                            res = true;
                        }
                        ;
                        break;
                    case 2:
                        if (i == lugar1.x && j == lugar1.y) {
                            cargandoObj1 = false;
                            res = true;
                        }
                        ;
                        break;
                    case 3:
                        if (i == lugar2.x && j == lugar2.y) {
                            cargandoObj2 = false;
                            res = true;
                        }
                        ;
                        break;


                }

                break;
            case 3:
                switch (paso) {

                    case 0:
                        if (i == objeto1.x && j == objeto1.y) {
                            eliminarObjeto(objeto1);
                            res = true;
                        }
                        ;
                        break;
                    case 1:
                        if (i == objeto2.x && j == objeto2.y) {
                            eliminarObjeto(objeto2);
                            res = true;
                        }
                        break;
                    case 2:
                        if (i == lugar2.x && j == lugar2.y) {
                            cargandoObj2 = false;
                            res = true;
                        }
                        ;
                        break;
                    case 3:
                        if (i == lugar1.x && j == lugar1.y) {
                            cargandoObj1 = false;
                            res = true;
                        }
                        ;
                        break;

                }

                break;
            case 4:
                switch (paso) {

                    case 0:
                        if (i == objeto2.x && j == objeto2.y) {
                            eliminarObjeto(objeto2);
                            res = true;
                        }
                        ;
                        break;
                    case 1:
                        if (i == objeto1.x && j == objeto1.y) {
                            eliminarObjeto(objeto1);
                            res = true;
                        }
                        ;
                        break;
                    case 2:
                        if (i == lugar2.x && j == lugar2.y) {
                            cargandoObj2 = false;
                            res = true;
                        }
                        ;
                        break;
                    case 3:
                        if (i == lugar1.x && j == lugar1.y) {
                            cargandoObj1 = false;
                            res = true;
                        }
                        ;
                        break;

                }

                break;
            case 5:
                switch (paso) {

                    case 0:
                        if (i == objeto2.x && j == objeto2.y) {
                            eliminarObjeto(objeto2);
                            res = true;
                        }
                        ;
                        break;
                    case 1:
                        if (i == objeto1.x && j == objeto1.y) {
                            eliminarObjeto(objeto1);
                            res = true;
                        }
                        ;
                        break;
                    case 2:
                        if (i == lugar1.x && j == lugar1.y) {
                            cargandoObj1 = false;
                            res = true;
                        }
                        ;
                        break;
                    case 3:
                        if (i == lugar2.x && j == lugar2.y) {
                            cargandoObj2 = false;
                            res = true;
                        }
                        ;
                        break;

                }

                break;

        }


        return res;
    }

    /**
     * Expande los nodos del árbol hasta encontrar alguno de los elementos
     */
    protected abstract void expandirNodos();

    /**
     * Construye una lista de coordenadas que corresponde a las visitadas por el
     * robot para encontrar algún elemento
     */
    protected void construirRutaPaso() {
        Nodo n;
        switch (paso) {
            case 0:


                n = arbol.get(indiceMeta());
                profundidadP1 = arbol.get(arbol.size() - 1).obtenerProfundidad();
                rutaPaso1.add(n.obtenerEstado());
                costoPaso1 = n.obtenerCosto();
                do {
                    if (n.obtenerPadre() == null) {
                        break;
                    }
                    rutaPaso1.add(n.obtenerPadre().obtenerEstado());

                    n = n.obtenerPadre();

                } while (n != null);

                Collections.reverse(rutaPaso1);



                break;
            case 1:


                n = arbol.get(indiceMeta());
                profundidadP2 = arbol.get(arbol.size() - 1).obtenerProfundidad();
                rutaPaso2.add(n.obtenerEstado());
                costoPaso2 = n.obtenerCosto();
                do {
                    if (n.obtenerPadre() == null) {
                        break;
                    }
                    rutaPaso2.add(n.obtenerPadre().obtenerEstado());

                    n = n.obtenerPadre();

                } while (n != null);

                Collections.reverse(rutaPaso2);
                rutaPaso2.remove(0);


                break;
            case 2:
                n = arbol.get(indiceMeta());
                profundidadP3 = arbol.get(arbol.size() - 1).obtenerProfundidad();
                rutaPaso3.add(n.obtenerEstado());
                costoPaso3 = n.obtenerCosto();
                do {
                    if (n.obtenerPadre() == null) {
                        break;
                    }

                    rutaPaso3.add(n.obtenerPadre().obtenerEstado());
                    n = n.obtenerPadre();

                } while (n != null);

                Collections.reverse(rutaPaso3);
                rutaPaso3.remove(0);
                break;
            case 3:
                n = arbol.get(indiceMeta());
                profundidadP4 = arbol.get(arbol.size() - 1).obtenerProfundidad();
                rutaPaso4.add(n.obtenerEstado());
                costoPaso4 = n.obtenerCosto();
                do {
                    if (n.obtenerPadre() == null) {
                        break;
                    }

                    rutaPaso4.add(n.obtenerPadre().obtenerEstado());
                    n = n.obtenerPadre();

                } while (n != null);

                Collections.reverse(rutaPaso4);
                rutaPaso4.remove(0);

                break;

        }

    }

    /**
     * Construye una lista de coordenadas que corresponde a las visitadas por el
     * robot para encontrar los cuatro elementos
     */
    protected void construirRutaOpcion() {

        switch (opcion) {
            case 0:
                rutaPaso1.addAll(rutaPaso2);
                rutaPaso3.addAll(rutaPaso4);
                rutaPaso1.addAll(rutaPaso3);
                rutaOp1.addAll(rutaPaso1);
                costoOp1 = costoPaso1 + costoPaso2 + costoPaso3 + costoPaso4;
                profundidadOp1 = profundidadP1 + profundidadP2 + profundidadP3 + profundidadP4;
                nodosExpandidosOp1 = nodosExpandidosP1 + nodosExpandidosP2 + nodosExpandidosP3 + nodosExpandidosP4;

                break;
            case 1:
                rutaPaso1.addAll(rutaPaso2);
                rutaPaso3.addAll(rutaPaso4);
                rutaPaso1.addAll(rutaPaso3);
                rutaOp2.addAll(rutaPaso1);
                costoOp2 = costoPaso1 + costoPaso2 + costoPaso3 + costoPaso4;
                profundidadOp2 = profundidadP1 + profundidadP2 + profundidadP3 + profundidadP4;
                nodosExpandidosOp2 = nodosExpandidosP1 + nodosExpandidosP2 + nodosExpandidosP3 + nodosExpandidosP4;

                break;
            case 2:
                rutaPaso1.addAll(rutaPaso2);
                rutaPaso3.addAll(rutaPaso4);
                rutaPaso1.addAll(rutaPaso3);
                rutaOp3.addAll(rutaPaso1);
                costoOp3 = costoPaso1 + costoPaso2 + costoPaso3 + costoPaso4;
                profundidadOp3 = profundidadP1 + profundidadP2 + profundidadP3 + profundidadP4;
                nodosExpandidosOp3 = nodosExpandidosP1 + nodosExpandidosP2 + nodosExpandidosP3 + nodosExpandidosP4;
                break;
            case 3:
                rutaPaso1.addAll(rutaPaso2);
                rutaPaso3.addAll(rutaPaso4);
                rutaPaso1.addAll(rutaPaso3);
                rutaOp4.addAll(rutaPaso1);
                costoOp4 = costoPaso1 + costoPaso2 + costoPaso3 + costoPaso4;
                profundidadOp4 = profundidadP1 + profundidadP2 + profundidadP3 + profundidadP4;
                nodosExpandidosOp4 = nodosExpandidosP1 + nodosExpandidosP2 + nodosExpandidosP3 + nodosExpandidosP4;
                break;
            case 4:
                rutaPaso1.addAll(rutaPaso2);
                rutaPaso3.addAll(rutaPaso4);
                rutaPaso1.addAll(rutaPaso3);
                rutaOp5.addAll(rutaPaso1);
                costoOp5 = costoPaso1 + costoPaso2 + costoPaso3 + costoPaso4;
                profundidadOp5 = profundidadP1 + profundidadP2 + profundidadP3 + profundidadP4;
                nodosExpandidosOp5 = nodosExpandidosP1 + nodosExpandidosP2 + nodosExpandidosP3 + nodosExpandidosP4;

                break;
            case 5:

                rutaPaso1.addAll(rutaPaso2);
                rutaPaso3.addAll(rutaPaso4);
                rutaPaso1.addAll(rutaPaso3);
                rutaOp6.addAll(rutaPaso1);
                costoOp6 = costoPaso1 + costoPaso2 + costoPaso3 + costoPaso4;
                profundidadOp6 = profundidadP1 + profundidadP2 + profundidadP3 + profundidadP4;
                nodosExpandidosOp6 = nodosExpandidosP1 + nodosExpandidosP2 + nodosExpandidosP3 + nodosExpandidosP4;

                break;

        }

    }

    /**
     * Determina la ruta con menor costo de movimiento de todas las opciones
     */
    protected void construirRutaDefinitiva() {
        int min = Math.min(Math.min(Math.min(costoOp1, costoOp2), Math.min(costoOp3, costoOp4)), Math.min(costoOp5, costoOp6));
        costoDef=min;

        if (min == costoOp1) {
            rutaDef.addAll(rutaOp1);
            profundidad = profundidadOp1;
            nodosExpandidos = nodosExpandidosOp1;

        } else {
            if (min == costoOp2) {
                rutaDef.addAll(rutaOp2);
                profundidad = profundidadOp2;
                nodosExpandidos = nodosExpandidosOp2;
            } else {
                if (min == costoOp3) {
                    rutaDef.addAll(rutaOp3);
                    profundidad = profundidadOp3;
                    nodosExpandidos = nodosExpandidosOp3;

                } else {
                    if (min == costoOp4) {
                        rutaDef.addAll(rutaOp4);
                        profundidad = profundidadOp4;
                        nodosExpandidos = nodosExpandidosOp4;

                    } else {
                        if (min == costoOp5) {
                            rutaDef.addAll(rutaOp5);
                            profundidad = profundidadOp5;
                            nodosExpandidos = nodosExpandidosOp5;

                        } else {
                            if (min == costoOp6) {
                                rutaDef.addAll(rutaOp6);
                                profundidad = profundidadOp6;
                                nodosExpandidos = nodosExpandidosOp6;

                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * Calcula el costo de moverse hasta la posicion i,j
     *
     * @param i La fila a la que se desea mover
     * @param j La columna a la que se desea mover
     * @return El costo de moverse 
     */
    protected int calcularCosto(int i, int j) {
        int costo;

        switch (cuadricula[i][j]) {

            case ROBOT:
                costo = 0;
                break;
            case OBJ1:
                if (cargandoObj2) {
                    costo = pesoObj1 + pesoObj2 + 1;
                    cargandoObj1 = true;
                } else {
                    costo = pesoObj1 + 1;
                    cargandoObj1 = true;
                }
                ;
                break;

            case OBJ2:
                if (cargandoObj1) {

                    costo = pesoObj1 + pesoObj2 + 1;
                    cargandoObj2 = true;
                } else {

                    costo = pesoObj2 + 1;
                    cargandoObj2 = true;
                }
                ;
                break;


            case LUG1:
                if (cargandoObj1 && cargandoObj2) {
                    costo = pesoObj2 + 1;
                    cargandoObj1 = false;
                } else {
                    if (cargandoObj1) {
                        costo = 1;
                        cargandoObj1 = false;
                    } else {
                        if (cargandoObj2) {
                            costo = pesoObj2 + 1;

                        } else {
                            costo = 1;
                        }
                    }
                }
                ;
                break;
            case LUG2:
                if (cargandoObj1 && cargandoObj2) {
                    costo = pesoObj1 + 1;
                    cargandoObj2 = false;
                } else {
                    if (cargandoObj2) {
                        costo = 1;
                        cargandoObj2 = false;
                    } else {
                        if (cargandoObj1) {
                            costo = pesoObj1 + 1;
                        } else {
                            costo = 1;
                        }
                    }
                }
                ;
                break;
            case LIBRE:
                if (cargandoObj1 && cargandoObj2) {
                    costo = pesoObj1 + pesoObj2 + 1;

                } else {
                    if (cargandoObj1) {
                        costo = pesoObj1 + 1;
                    } else {
                        if (cargandoObj2) {
                            costo = pesoObj2 + 1;
                        } else {
                            costo = 1;
                        }
                    }
                }

                ;
                break;
            default:
                if (cargandoObj1 && cargandoObj2) {
                    costo = pesoObj1 + pesoObj2 + PENALIDAD + 1;
                } else {
                    if (cargandoObj1) {
                        costo = pesoObj1 + PENALIDAD + 1;

                    } else {
                        if (cargandoObj2) {
                            costo = pesoObj2 + PENALIDAD + 1;

                        } else {
                            costo = PENALIDAD + 1;
                        }
                    }
                }

                ;
                break;

        }

        return costo;


    }

    /**
     * Getter al reporte
     *
     * @return
     */
    public Reporte obtenerReporte() {
        return rep;
    }

    /**
     *
     * @param i
     * @param j
     * @return
     */
    protected String obtenerElemento(int i, int j) {
        String ac;
        switch (cuadricula[i][j]) {

            case OBJ1:
                ac = "o1";
                break;
            case OBJ2:
                ac = "o2";
                break;
            case LUG1:
                ac = "l1";
                break;
            case LUG2:
                ac = "l2";
                break;
            default:
                ac = null;
                break;
        }
        return ac;
    }

    /**
     * Determina si el elemento en la posicion i,j es el elemento que
     * actualmente esta siendo buscado
     *
     * @param i la fila a la que se desea mover
     * @param j la columna a la que se desea mover
     * @return el resultado de la evaluación
     */
    protected boolean estorbo(int i, int j) {
        String ob1 = determinarObjetivo();
        String ob2 = obtenerElemento(i, j);

        if (ob2 == null) {
            return false;
        } else {
            if (ob1.equals(ob2)) {
                return false;
            } else {
                return true;
            }
        }


    }

    /**
     * Determina si ya existe un nodo en el árbol que contenga el estado con las
     * coordenas i,j
     *
     * @param i La fila a la que se desea mover
     * @param j La columna a la que se desea mover
     * @return el resultado del a evalución
     */
    protected boolean visitada(int i, int j) {

        return coorVisitadas.contains(new Point(i, j));

    }

    /**
     * Función heurística
     * @param obj El objetivo que buscado
     * @param p La posición actual a la que se desea mover el robot
     * @return El costo estimado de moverse hacia el objetivo
     */
    protected int estimarCosto(Actor obj, Point p) {

        int miPosX = p.x;
        int miPosY = p.y;
        int objPosX = obj.x;
        int objPosY = obj.y;
        int distanciaManhattan = Math.abs(miPosX - objPosX) + Math.abs(miPosY - objPosY);

        if (cargandoObj1 && cargandoObj2) {
            return distanciaManhattan * (pesoObj1 + pesoObj2);
        } else {
            if (cargandoObj1) {
                return distanciaManhattan * pesoObj1;
            } else {
                if (cargandoObj2) {
                    return distanciaManhattan * pesoObj2;
                } else {
                    return distanciaManhattan;
                }
            }
        }



    }

    /**
     * Método que expande la raiz del árbol de búsqueda
     */
    protected abstract void expandirRaiz();
}