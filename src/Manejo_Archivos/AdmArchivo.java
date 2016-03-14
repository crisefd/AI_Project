/*
 * AdmArchivo.java
 *
 */
package Manejo_Archivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Clase que escribe y lee archivos de texto plano, y permite extraer la
 * información para el desarrollo de los algoritmos de búsqueda
 *
 * @author Cristhian Fuertes 1123259
 */
public class AdmArchivo {

    private File arch = null;
    private FileReader fr = null;
    private BufferedReader br = null;
    private FileWriter fw = null;
    private PrintWriter pw = null;
    private int dimension, pesoObj1, pesoObj2, penalidad = 1;
    private int[][] cuadricula;

    /**
     *
     * @param dimension tamaño de la matriz
     * @param pesoObj1 entero que representa el peso del objeto1
     * @param pesoObj2 entero que representa el peso del objeto 2
     * @param cuadricula matriz númerica que representa la ubicación de los
     * elementos del campo
     * @param penalidad el valor de las casillas de penalidad
     */
    public AdmArchivo(int dimension, int pesoObj1, int pesoObj2, int[][] cuadricula, int penalidad) {
        this.dimension = dimension;
        this.pesoObj1 = pesoObj1;
        this.pesoObj2 = pesoObj2;
        this.cuadricula = cuadricula;
        this.penalidad = penalidad;
        escribir_archivo(null);


    }

    /**
     *
     * @param f El archivo que sera leido
     */
    public AdmArchivo(File f) {
        leer_de_archivo(f);

    }

    /**
     *
     * @return El atributo, leido del archivo, representando el peso del objeto
     * 1
     */
    public int obtenerPesoObj1() {
        return pesoObj1;
    }

    /**
     *
     * @return El atributo, leido del archivo, representando el peso del objeto
     * 2
     */
    public int obtenerpesoObj2() {
        return pesoObj2;
    }

    /**
     *
     * @return La matriz númerica, leida del archivo, que representa las
     * ubicaciones de los objetos en el campo
     */
    public int[][] obtenerCuadricula() {
        return cuadricula;
    }

    /**
     *
     * @return El valor de las casillas de penalidad, leida del archivo, del
     * campo
     */
    public int obtenerPenalidad() {
        boolean encontrada = false;
        for (int i = 0; i < cuadricula.length; i++) {
            for (int j = 0; j < cuadricula.length; j++) {
                if (cuadricula[i][j] >= 1) {
                    penalidad = cuadricula[i][j];
                    encontrada = true;
                    break;
                }

            }
            if (encontrada) {
                break;
            }
        }
        return penalidad;
    }

    private void escribir_archivo(File f) {
        String cadena;
        try {
            if (f == null) {
                Date d= new Date();    
                String fecha= new SimpleDateFormat("dd-MM-yyyy").format(d);
                String hora= new SimpleDateFormat("hh-mm-ss").format(d);
             
                File archivo= new File("src/Manejo_Archivos/Archivos/ambiente"+fecha+"-"+hora+".txt");
                archivo.createNewFile();
                fw = new FileWriter(archivo);
            } else {
                fw = new FileWriter(f);
            }
            pw = new PrintWriter(fw);

            cadena = dimension + " " + pesoObj1 + " " + pesoObj2 + "\n";



            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    cadena += cuadricula[i][j] + " ";
                }
                cadena += "\n";
            }

            pw.print(cadena);

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            try {
                fw.close();
            } catch (IOException ioe) {

                System.err.println(ioe.getMessage());

            }
        }


    }

    private void leer_de_archivo(File f) {

        StringTokenizer stk;
        String linea;

        try {
            arch = f;
            fr = new FileReader(arch);
            br = new BufferedReader(fr);

            linea = br.readLine();

            stk = new StringTokenizer(linea, " ");

            dimension = Integer.parseInt(stk.nextToken());
            pesoObj1 = Integer.parseInt(stk.nextToken());
            pesoObj2 = Integer.parseInt(stk.nextToken());

            cuadricula = new int[dimension][dimension];

            int e = 0;
            while ((linea = br.readLine()) != null) {
                stk = new StringTokenizer(linea, " ");
                for (int i = 0; i < dimension; i++) {
                    cuadricula[e][i] = Integer.parseInt(stk.nextToken());

                }
                e++;
            }
         
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            


        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }finally {

            try {
                fr.close();
            } catch (Exception exc) {
                System.err.println(exc.getMessage());

            }

        }

    }
}
