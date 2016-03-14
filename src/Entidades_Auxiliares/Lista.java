/*
 * Lista.java
 */
package Entidades_Auxiliares;

import java.util.Vector;

/**
 * Cola de prioridad que almacena en orden ascendetes los nodos en base a los
 * valores de las funciones de costo, heuristica y utilidad
 *
 * @author Cristhian Fuertes 1123259
 *
 */
public class Lista extends Vector<Nodo> {

    private int tipo; //1 para costo, 2 para heuristica y 3 para utilidad

    /**
     * El tipo de función con la cual la lista encolara los nodos
     *
     * @param t El tipo, 0 para costo, 1 para heuristica y 2 para utilidad
     */
    public Lista(int t) {
        tipo = t;

    }

    @Override
    public boolean add(Nodo n) {
        boolean res = false;

        switch (tipo) {
            case 1:
                if (n != null) {
                    if (size() == 0) {
                        super.add(n);
                        res = true;
                    } else {
                        int i = 0;
                        boolean b = false;

                        while (i < size()) {
                            if ((n.obtenerCosto()) <= (get(i).obtenerCosto())) {
                                insertElementAt(n, i);
                                res = true;
                                b = true;
                                break;
                            }

                            i++;
                        }
                        if (!b) {
                            super.add(n);

                        }
                    }
                }



                break;
            case 2:
                if (n != null) {
                    if (size() == 0) {
                        super.add(n);
                        res = true;
                    } else {
                        int i = 0;
                        boolean b = false;

                        while (i < size()) {
                            if ((n.obtenerEstimado()) <= (get(i).obtenerEstimado())) {
                                insertElementAt(n, i);
                                res = true;
                                b = true;
                                break;
                            }

                            i++;
                        }
                        if (!b) {
                            super.add(n);

                        }
                    }

                }



                break;
            case 3:
                if (n != null) {
                    if (size() == 0) {
                        super.add(n);
                        res = true;
                    } else {
                        int i = 0;
                        boolean b = false;

                        while (i < size()) {
                            if ((n.obtenerUtilidad()) <= (get(i).obtenerUtilidad())) {
                                insertElementAt(n, i);
                                res = true;
                                b = true;
                                break;
                            }

                            i++;
                        }
                        if (!b) {
                            super.add(n);

                        }
                    }

                }




                break;

        }


        return res;
    }
}
