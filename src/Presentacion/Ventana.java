/*
 * Ventana.java
 * 
 */
package Presentacion;

import Buscadores.BuscadorProfundidad;
import Buscadores.BuscadorCostoUniforme;
import Buscadores.BuscadorAvaro;
import Buscadores.BuscadorAmplitud;
import Buscadores.BuscadorAEstrella;
import Manejo_Archivos.AdmArchivo;
import Entidades_Auxiliares.Elementos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase que contiene todos los elementos de la interfaz gráfica de usuario
 *
 * @author Cristhian Fuertes 1123259 
 */
public class Ventana extends JFrame implements MouseListener, ActionListener, Elementos {

    private Container contenedor;
    private File archivoElegido;
    private Icon iconoActual, iconoE;
    private JPanel panelSuperior, panelInferior, panelCentral, panelCuadricula;
    private JButton botonIniciar,botonRestablecer ,botonOb1, botonOb2, botonL1, botonL2, botonPenalidad, botonRobot;
    private Object botonPresionado;
    private int dim, penalidad, pesoObj1, pesoObj2;
    private JLabel[][] grilla;
    private DialogoInicio di;
    private BuscadorAmplitud ba;
    private BuscadorProfundidad bp;
    private BuscadorCostoUniforme bcu;
    private BuscadorAvaro bv;
    private BuscadorAEstrella bae;
    private boolean archivoCargado = false, btnPenaPres=false, bandera=true;
    private AdmArchivo ad;
    private ArrayList<Point> ruta = new ArrayList<Point>();
    private int[][] matriz;
    private JComboBox listaAlg;
    private Reporte rep;
    private JMenuBar barraMenu;
    private JMenu menuArchivo;
    private JMenu menuAyuda;
    private JMenuItem itemNuevaBusqueda, itemSalir, itemAcercaDe, itemGuia;

    /**
     * Constructor
     */
    public Ventana() {
        super("Proyecto IA");
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
        } catch (Exception ex) {
        }
        di = new DialogoInicio(this, true);

    }

    /**
     * Instancia todos los componentes visuales de la ventana principal del
     * aplicativo
     *
     * @param dim Dimension de la cuadricula la cual corresponde a la matriz
     * númerica del archivo leido
     * @param pesoObj1 El peso del objeto 1
     * @param pesoObj2 El peso del objeto 2
     * @param penalidad El valor de las casillas de penalidad
     */
    public void initComponents(int dim, int pesoObj1, int pesoObj2, int penalidad) {
        this.dim = dim;
        this.pesoObj1 = pesoObj1;
        this.pesoObj2 = pesoObj2;
        this.penalidad = penalidad;
        setSize(1300, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contenedor = getContentPane();
        contenedor.setLayout(new BorderLayout());
        barraMenu= new JMenuBar();
        menuArchivo= new JMenu("Archivo");
        menuAyuda= new JMenu("Ayuda");
        itemNuevaBusqueda= new JMenuItem("Nueva Busqueda");
        itemSalir= new JMenuItem("Salir");
        itemSalir.addActionListener(this);
        itemAcercaDe= new JMenuItem("Acerca De");
        itemAcercaDe.addActionListener(this);
        itemGuia= new JMenuItem("Guia");
        itemGuia.addActionListener(this);
        itemNuevaBusqueda.addActionListener(this);
        menuAyuda.add(itemAcercaDe);
        menuAyuda.add(itemGuia);
        menuArchivo.add(itemNuevaBusqueda);
        menuArchivo.add(itemSalir);
        barraMenu.add(menuArchivo);
        barraMenu.add(menuAyuda);
        panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(1, 5, 2, 2));
        panelSuperior.setBorder(new BevelBorder(BevelBorder.RAISED));
        botonOb1 = new JButton(new ImageIcon("src/Imagenes/book1_.png"));
        botonOb1.addActionListener(this);
        botonOb1.setBorderPainted(false);
        botonOb1.setContentAreaFilled(false);
        botonOb2 = new JButton(new ImageIcon("src/Imagenes/book2_.png"));
        botonOb2.setBorderPainted(false);
        botonOb2.setContentAreaFilled(false);
        botonOb2.addActionListener(this);
        botonL1 = new JButton(new ImageIcon("src/Imagenes/box1_.png"));
        botonL1.setBorderPainted(false);
        botonL1.setContentAreaFilled(false);
        botonL1.addActionListener(this);
        botonL2 = new JButton(new ImageIcon("src/Imagenes/box2_.png"));
        botonL2.setBorderPainted(false);
        botonL2.setContentAreaFilled(false);
        botonL2.addActionListener(this);
        botonPenalidad = new JButton(new ImageIcon("src/Imagenes/wrinkled.gif"));
        botonPenalidad.setBorderPainted(false);
        botonPenalidad.setContentAreaFilled(false);
        botonPenalidad.addActionListener(this);
        panelSuperior.add(botonOb1);
        panelSuperior.add(botonOb2);
        panelSuperior.add(botonL1);
        panelSuperior.add(botonL2);
        panelSuperior.add(botonPenalidad);
        botonRobot = new JButton(new ImageIcon("src/Imagenes/robot.png"));
        botonRobot.setBorderPainted(true);
        botonRobot.setContentAreaFilled(false);
        botonRobot.addActionListener(this);
        panelSuperior.add(botonRobot);
        panelCentral = new JPanel();
        panelCuadricula = new JPanel();
        panelCuadricula.setLayout(new GridLayout(dim, dim, 0, 0));

        panelCentral.setLayout(new BorderLayout());


        grilla = new JLabel[dim][dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                grilla[i][j] = new JLabel();

                grilla[i][j].setOpaque(true);
                grilla[i][j].setBorder(new BevelBorder(BevelBorder.RAISED));
                grilla[i][j].setHorizontalAlignment(Math.round(grilla[i][j].getWidth() / 2));
                grilla[i][j].addMouseListener(this);
                panelCuadricula.add(grilla[i][j]);


            }
        }
       

        panelCentral.add(new JLabel("                                                                           "), BorderLayout.EAST);
        panelCentral.add(panelCuadricula, BorderLayout.CENTER);
        panelCentral.add(new JLabel("                                                                           "), BorderLayout.WEST);
        panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout());
        panelInferior.setSize(100, 20);
        botonRestablecer= new JButton("Limpiar Grilla");
        botonIniciar = new    JButton("      Iniciar     ");
        botonIniciar.setIcon(new ImageIcon("src/Imagenes/Start.png"));
        botonRestablecer.setIcon(new ImageIcon("src/Imagenes/clear.png"));
        botonIniciar.addActionListener(this);
        botonRestablecer.addActionListener(this);
        botonIniciar.setEnabled(false);
        listaAlg = new JComboBox(new Object[]{"Preferente Por Amplitud", 
                                              "Preferente Por Profundidad (Sin Ciclos)", 
                                              "Costo Uniforme", 
                                              "Voraz(Sin Ciclos)", 
                                              "A*"});
       
         panelInferior.add(botonRestablecer);
         panelInferior.add(new JLabel("                       "));
         panelInferior.add(listaAlg);
         panelInferior.add(new JLabel("                       "));
         panelInferior.add(botonIniciar);
         listaAlg.setSize(30, panelInferior.getHeight());
         panelInferior.setBorder(panelSuperior.getBorder());
         
        contenedor.add(panelSuperior, BorderLayout.NORTH);
        contenedor.add(panelCentral, BorderLayout.CENTER);
        contenedor.add(panelInferior, BorderLayout.SOUTH);
         setJMenuBar(barraMenu);
        setIconImage(new ImageIcon("src/Imagenes/icon_.png").getImage());

        validate();

         iconoE= new ImageIcon("src/Imagenes/wrinkledB.gif");

        setVisible(true);
        hilo.start();


    }
   
    private Thread hilo = new Thread(){
        @Override
      public void run(){
          while(bandera){
              if(!botonRobot.isEnabled()&&!botonOb1.isEnabled()&&!botonOb2.isEnabled()
                 &&!botonL1.isEnabled()&&!botonL2.isEnabled()){
                  botonIniciar.setEnabled(true);
              }else{
                  botonIniciar.setEnabled(false);
              }
          }
      }  
    };
    private void pintarCampo(int[][] ma) {
        this.matriz = ma;
       
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int n = matriz[i][j];
                switch (n) {
                    case OBJ1:
                        grilla[i][j].setIcon(botonOb1.getIcon());
                        break;
                    case OBJ2:
                        grilla[i][j].setIcon(botonOb2.getIcon());
                        break;
                    case LUG1:
                        grilla[i][j].setIcon(botonL1.getIcon());
                        break;
                    case LUG2:
                        grilla[i][j].setIcon(botonL2.getIcon());
                        break;
                    case ROBOT:
                        grilla[i][j].setIcon(botonRobot.getIcon());
                        break;
                    case LIBRE:
                        grilla[i][j].setIcon(null);
                        break;
                    default:
                        grilla[i][j].setIcon(iconoE);
                        break;

                }

            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel celda = (JLabel) e.getSource();

        if (celda.getIcon() == null) {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (celda.equals(grilla[i][j])) {

                        grilla[i][j].setIcon(iconoActual);
                        if(!btnPenaPres){
                            iconoActual = null;
                        }
                       
                            if (botonPresionado.equals(botonRobot)) {
                                botonRobot.setEnabled(false);
                            } else {
                                if (botonPresionado.equals(botonOb1)) {
                                    botonOb1.setEnabled(false);
                                } else {
                                    if (botonPresionado.equals(botonOb2)) {
                                        botonOb2.setEnabled(false);
                                    } else {
                                        if (botonPresionado.equals(botonL1)) {
                                            botonL1.setEnabled(false);
                                        } else {
                                            if (botonPresionado.equals(botonL2)) {
                                                botonL2.setEnabled(false);
                                            } 
                                        }
                                    }
                                
                            }
                        }

                        return;
                    }

                }
            }
        }




    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        botonPresionado = e.getSource();

        if (botonPresionado.equals(botonRobot)) {

            iconoActual = botonRobot.getIcon();
            

        } else {
            if (botonPresionado.equals(botonOb1)) {
                btnPenaPres=false;
                iconoActual = botonOb1.getIcon();
               
            } else {
                if (botonPresionado.equals(botonOb2)) {
                     btnPenaPres=false;
                    iconoActual = botonOb2.getIcon();
                   
                } else {
                    if (botonPresionado.equals(botonL1)) {
                         btnPenaPres=false;
                        iconoActual = botonL1.getIcon();
                        
                    } else {
                        if (botonPresionado.equals(botonL2)) {
                             btnPenaPres=false;
                            iconoActual = botonL2.getIcon();
                           
                        } else {
                            if (botonPresionado.equals(botonPenalidad)) {
                                btnPenaPres=true;
                                iconoActual= iconoE;

                            } else {
                                 btnPenaPres=false;
                                if (botonPresionado.equals(botonIniciar) && !archivoCargado) {
                                    matriz = new int[this.dim][this.dim];
                                    for (int i = 0; i < dim; i++) {
                                        for (int j = 0; j < dim; j++) {
                                            Icon ic = grilla[i][j].getIcon();

                                            if (ic == null) {
                                                matriz[i][j] = LIBRE;

                                            } else {
                                                if (ic.equals(botonRobot.getIcon())) {
                                                    matriz[i][j] = ROBOT;
                                                } else {
                                                    if (ic.equals(botonOb1.getIcon())) {
                                                        matriz[i][j] = OBJ1;
                                                    } else {
                                                        if (ic.equals(botonOb2.getIcon())) {
                                                            matriz[i][j] = OBJ2;
                                                        } else {
                                                            if (ic.equals(botonL1.getIcon())) {
                                                                matriz[i][j] = LUG1;
                                                            } else {
                                                                if (ic.equals(botonL2.getIcon())) {
                                                                    matriz[i][j] = LUG2;

                                                                } else {
                                                                    if (ic.equals(iconoE)) {
                                                                        matriz[i][j] = penalidad;
                                                                       
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }



                                    MostradorRuta mr = new MostradorRuta();
                                     bandera=false;
                                    inicializarBusqueda();
                                    mr.start();

                                } else {
                                    if(botonPresionado.equals(botonIniciar)){
                                          MostradorRuta mr = new MostradorRuta(); 
                                          bandera=false;
                                           inicializarBusqueda();
                                           mr.start();
                                    }else{
                                        if(botonPresionado.equals(botonRestablecer)){
                                            btnPenaPres=false;
                                            botonRobot.setEnabled(true);
                                            botonL1.setEnabled(true);
                                            botonL2.setEnabled(true);
                                            botonPenalidad.setEnabled(true);
                                            botonOb1.setEnabled(true);
                                            botonOb2.setEnabled(true);
                                            iconoActual=null;
                                            for(int i=0;i<grilla.length;i++){
                                                for(int j=0;j<grilla.length;j++){
                                                    grilla[i][j].setIcon(null);
                                                }
                                            }
                                          
                                        }else{
                                            if(botonPresionado.equals(itemGuia)){
                                                try {
                                                    File path = new File("src/Manejo_Archivos/Archivos/proyecto_enunciado.pdf");
                                                    Desktop.getDesktop().open(path);
                                                   } catch (IOException ex) {
                                                   JOptionPane.showMessageDialog(null, ex.getMessage()+"\nError, al cargar el pdf", null, JOptionPane.ERROR_MESSAGE);
                                                  }
                                            }else{
                                                if(botonPresionado.equals(itemNuevaBusqueda)){
                                                    this.dispose();
                                                    new Ventana();
                                                }else{
                                                    if(botonPresionado.equals(itemSalir)){
                                                        System.exit(0);
                                                        
                                                    }else{
                                                        if(botonPresionado.equals(itemAcercaDe)){
                                                            new DialogoAcerca(Ventana.this,"Proyecto IA",true).setVisible(true);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }


    }

    private void inicializarBusqueda() {

        int eleccion = listaAlg.getSelectedIndex();

        if (archivoCargado) {

            switch (eleccion) {
                case 0:
                    ba = new BuscadorAmplitud(dim, pesoObj1, pesoObj2, matriz, penalidad);
                    ruta.addAll(ba.buscar());
                    rep = ba.obtenerReporte();
                    
                    break;
                case 1:
                    bp = new BuscadorProfundidad(dim, pesoObj1, pesoObj2, matriz, penalidad);
                    ruta.addAll(bp.buscar());
                    rep = bp.obtenerReporte();
                    break;
                case 2:
                    bcu = new BuscadorCostoUniforme(dim, pesoObj1, pesoObj2, matriz, penalidad);
                    ruta.addAll(bcu.buscar());
                    rep = bcu.obtenerReporte();
                    break;
                case 3:
                    bv = new BuscadorAvaro(dim, pesoObj1, pesoObj2, matriz, penalidad);
                    ruta.addAll(bv.buscar());
                    rep = bv.obtenerReporte();
                    break;
                case 4:
                    bae = new BuscadorAEstrella(dim, pesoObj1, pesoObj2, matriz, penalidad);
                    ruta.addAll(bae.buscar());
                    rep = bae.obtenerReporte();
                    break;

            }

        } else {

            switch (eleccion) {
                case 0:
                    ba = new BuscadorAmplitud(new AdmArchivo(dim, pesoObj1, pesoObj2, matriz, penalidad));
                    ruta.addAll(ba.buscar());
                    rep = ba.obtenerReporte();
                    ;
                    break;
                case 1:
                    bp = new BuscadorProfundidad(new AdmArchivo(dim, pesoObj1, pesoObj2, matriz, penalidad));
                    ruta.addAll(bp.buscar());
                    rep = bp.obtenerReporte();
                    break;
                case 2:
                    bcu = new BuscadorCostoUniforme(new AdmArchivo(dim, pesoObj1, pesoObj2, matriz, penalidad));
                    ruta.addAll(bcu.buscar());
                    rep = bcu.obtenerReporte();
                    break;
                case 3:
                    bv = new BuscadorAvaro(new AdmArchivo(dim, pesoObj1, pesoObj2, matriz, penalidad));
                    ruta.addAll(bv.buscar());
                    rep = bv.obtenerReporte();
                    break;
                case 4:
                    bae = new BuscadorAEstrella(new AdmArchivo(dim, pesoObj1, pesoObj2, matriz, penalidad));
                    ruta.addAll(bae.buscar());
                    rep = bae.obtenerReporte();
                    break;

            }
        }



    }

    private class MostradorRuta extends Thread {

        Icon icono = botonRobot.getIcon();
        int i;

        @Override
        public void run() {


            i = 0;


            while (i < ruta.size()) {
                Point p = ruta.get(i);


                if (matriz[p.x][p.y] == LUG1) {
                    grilla[p.x][p.y].setIcon(icono);
                    try {
                        sleep(250);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    grilla[p.x][p.y].setIcon(botonL1.getIcon());

                } else {
                    if (matriz[p.x][p.y] == LUG2) {
                        grilla[p.x][p.y].setIcon(icono);
                        try {
                            sleep(250);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        grilla[p.x][p.y].setIcon(botonL2.getIcon());
                    } else {
                       
                        if (matriz[p.x][p.y] == penalidad) {
                            grilla[p.x][p.y].setIcon(icono);
                           
                            try {
                                sleep(250);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            grilla[p.x][p.y].setIcon(iconoE);
                        } else {

                          
                            grilla[p.x][p.y].setIcon(icono);
                            try {
                                sleep(250);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            grilla[p.x][p.y].setIcon(null);

                        }
                    }
                }




                i++;
            }
            mostrarRuta();
        }

        private void mostrarRuta() {
            int j = 0;
            String msg = "Inicio";

            while (j < ruta.size()) {
                if (j == 0) {
                    msg += "(" + ruta.get(j).x + "," + ruta.get(j).y + ")";
                } else {
                    msg += " -> " + "(" + ruta.get(j).x + "," + ruta.get(j).y + ")";
                }
                j++;
            }

            msg += " Meta";

            new DialogoReporte(Ventana.this, true, msg, rep);


        }
    }

    private class DialogoReporte extends JDialog implements ActionListener {

        private JButton botonSalir, botonNuevo;

        public DialogoReporte(JFrame dueño, boolean modal, String msg, Reporte rep) {
            super(dueño, modal);
            setTitle("Reporte de búsqueda  usando el algoritmo: "+listaAlg.getSelectedItem());
            getContentPane().setLayout(new BorderLayout());
            JScrollPane panelSuperior = new JScrollPane();
            getContentPane().add(panelSuperior, BorderLayout.NORTH);
            JTextArea areaTxt = new JTextArea(msg, 1, 40);
            areaTxt.setEditable(false);
            panelSuperior.setViewportView(areaTxt);
            botonSalir = new JButton("Salir");
            botonNuevo = new JButton("Nueva Búsqueda");
            botonSalir.addActionListener(this);
            botonNuevo.addActionListener(this);
            JPanel panelInferior = new JPanel();
            panelInferior.setLayout(new GridLayout(1, 3));
            panelInferior.add(botonNuevo);
            panelInferior.add(new JLabel("                 "));
            panelInferior.add(botonSalir);
            JPanel panelReporte = new JPanel(new GridLayout(4, 1));
            JTextField campoTiempo = new JTextField("Tiempo de cómputo: " + rep.obtenerTiempo() + " milisegundos");
            JTextField campoProfundidad = new JTextField("Profundidad del árbol de búsqueda: " + rep.obtenerProfundidad());
            JTextField campoExpandidos = new JTextField("Nodos expandidos en el árbol de búsqueda: " + rep.obtenerNodosExpandidos());
            JTextField campoCosto= new JTextField("Costo de ruta: " +rep.obtenerCostoRuta() );
            campoTiempo.setEditable(false);
            campoProfundidad.setEditable(false);
            campoExpandidos.setEditable(false);
            campoCosto.setEditable(false);
            panelReporte.add(campoTiempo);
            panelReporte.add(campoProfundidad);
            panelReporte.add(campoExpandidos);
            panelReporte.add(campoCosto);
            getContentPane().add(panelReporte, BorderLayout.CENTER);
            getContentPane().add(panelInferior, BorderLayout.SOUTH);
            setLocation(new Point(400, 200));
            setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            pack();
            setResizable(false);
            setVisible(true);




        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();


            if (comando.equals("Salir")) {
                System.exit(0);
            } else {
                Ventana.this.dispose();
                new Ventana();
            }
        }
    }

    private class DialogoInicio extends JDialog implements ActionListener, KeyListener{

        private JPanel panDimension, panPeso1, panPeso2, panPenalidad, panBoton;
        private JLabel etiquetaDim, etiquetaPeso1, etiquetaPeso2, etiquetaPenalidad;
        private JComboBox listaDim;
        private JTextField campoPeso1, campoPeso2, campoPena;
        private JButton botonAceptar, botonCargar, botonCancelar;
        private JFrame dueño;
        private JFileChooser selectorArchivos = new JFileChooser();

        public DialogoInicio(JFrame dueño, boolean modal) {
            super(dueño, modal);
            this.dueño = dueño;
            selectorArchivos.setCurrentDirectory(new File("src/Manejo_Archivos/Archivos/ambiente.txt"));
            getContentPane().setLayout(new GridLayout(5, 1));
            getContentPane().setBackground(Color.black);
            panDimension = new JPanel(new FlowLayout());
            panDimension.setBackground(Color.black);
            panPeso1 = new JPanel(new FlowLayout());
            panPeso1.setBackground(Color.black);
            panPeso2 = new JPanel(new FlowLayout());
            panPeso2.setBackground(Color.black);
            panPenalidad = new JPanel(new FlowLayout());
            panPenalidad.setBackground(Color.black);
            etiquetaDim = new JLabel("Dimension del campo:                ");
            etiquetaDim.setForeground(Color.white);
            etiquetaPeso1 = new JLabel("Escriba el peso del objeto 1:     ");
            etiquetaPeso1.setForeground(Color.white);
            etiquetaPeso2 = new JLabel("Escriba el peso del objeto 2:     ");
            etiquetaPeso2.setForeground(Color.white);
            etiquetaPenalidad = new JLabel("Escriba el valor de la penalidad:");
            etiquetaPenalidad.setForeground(Color.white);
            listaDim = new JComboBox(new Object[]{5, 6, 7, 8, 9, 10});
            campoPeso1 = new JTextField("1",2);
            campoPeso2 = new JTextField("1",2);
            campoPena = new JTextField("1", 2);
            panDimension.add(etiquetaDim);
            panDimension.add(listaDim);
            panPeso1.add(etiquetaPeso1);
            panPeso1.add(campoPeso1);
            panPeso2.add(etiquetaPeso2);
            panPeso2.add(campoPeso2);
            panPenalidad.add(etiquetaPenalidad);
            panPenalidad.add(campoPena);
            botonAceptar = new JButton("Aceptar");
            botonAceptar.addActionListener(this);
            botonAceptar.setSelected(true);
            botonCargar = new JButton("Cargar Archivo");
            botonCargar.addActionListener(this);
            botonCancelar = new JButton("Cancelar");
            botonCancelar.addActionListener(this);
            panBoton = new JPanel(new GridLayout(1, 3));
            panBoton.setBackground(Color.black);
            panBoton.add(botonAceptar);
            panBoton.add(botonCargar);
            panBoton.add(botonCancelar);
            getContentPane().add(panDimension);
            getContentPane().add(panPeso1);
            getContentPane().add(panPeso2);
            getContentPane().add(panPenalidad);
            getContentPane().add(panBoton);
            validate();
            pack();
            setResizable(false);
            setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            setLocation(new Point(400, 200));
            setIconImage(new ImageIcon("src/Imagenes/icon_.png").getImage());
            setVisible(true);
            this.addKeyListener(this);


        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            if (comando.equals("Aceptar")) {


                try {
                    int dim = (Integer) listaDim.getSelectedItem();
                    int pesoObj1 = Integer.parseInt(campoPeso1.getText());
                    int pesoObj2 = Integer.parseInt(campoPeso2.getText());
                    int penalidad = Integer.parseInt(campoPena.getText());
                    
                    if(dim<1||pesoObj1<1||pesoObj2<1||penalidad<1){
                         JOptionPane.showMessageDialog(null,"\n Solo se aceptan datos númericos enteros mayores o iguales a 1","", JOptionPane.ERROR_MESSAGE);
                    }else{
                        this.setVisible(false);
                        ((Ventana) dueño).initComponents(dim, pesoObj1, pesoObj2, penalidad);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage()+"\n Solo se aceptan datos númericos enteros","", JOptionPane.ERROR_MESSAGE);
                   

                }

            } else {

                if (comando.equals("Cargar Archivo")) {
                    this.setVisible(false);
                    boolean repetir;
                    
                    do{
                        repetir=false;
                        try{
                                FileNameExtensionFilter filtro = new FileNameExtensionFilter("Solo txt", "txt");
                                selectorArchivos.setFileFilter(filtro);
                                int s = selectorArchivos.showOpenDialog(null);

                                if (s == JFileChooser.APPROVE_OPTION) {
                                    archivoElegido = selectorArchivos.getSelectedFile();
                                    ad = new AdmArchivo(archivoElegido);
                                    int[][] matriz = ad.obtenerCuadricula();

                                    int pesoObj1 = ad.obtenerPesoObj1();
                                    int pesoObj2 = ad.obtenerpesoObj2();
                                    int penalidad = ad.obtenerPenalidad();


                                    ((Ventana) dueño).initComponents(matriz.length, pesoObj1, pesoObj2, penalidad);
                                    botonOb1.setEnabled(false);
                                    botonOb2.setEnabled(false);
                                    botonL1.setEnabled(false);
                                    botonL2.setEnabled(false);
                                    botonPenalidad.setEnabled(false);
                                    botonRobot.setEnabled(false);
                                    pintarCampo(matriz);
                                    archivoCargado = true;
                                    Ventana.this.botonRestablecer.setEnabled(false);

                                }else{
                                    if(s == JFileChooser.CANCEL_OPTION){
                                        this.setVisible(true);
                                        break;
                                    }
                                }
                        }catch(Exception ex){
                            JOptionPane.showMessageDialog(null,"Error al cargar el archivo","", JOptionPane.ERROR_MESSAGE);
                            repetir=true;
                        }
                    }while(repetir);

                } else {
                    if (comando.equals("Cancelar")) {
                        System.exit(0);

                    }
                }

            }



        }

        @Override
        public void keyTyped(KeyEvent e) {
             if(e.getKeyCode() == KeyEvent.VK_ENTER){
                try {
                    int dim = (Integer) listaDim.getSelectedItem();
                    int pesoObj1 = Integer.parseInt(campoPeso1.getText());
                    int pesoObj2 = Integer.parseInt(campoPeso2.getText());
                    int penalidad = Integer.parseInt(campoPena.getText());
                    this.setVisible(false);
                    ((Ventana) dueño).initComponents(dim, pesoObj1, pesoObj2, penalidad);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage()+"\n Solo se aceptan datos númericos","", JOptionPane.ERROR_MESSAGE);
                    campoPeso1.setText("");
                    campoPeso2.setText("");
                    campoPena.setText("");

                }

           }
        }

        @Override
        public void keyPressed(KeyEvent e) {
           if(e.getKeyCode() == KeyEvent.VK_ENTER){
                try {
                    int dim = (Integer) listaDim.getSelectedItem();
                    int pesoObj1 = Integer.parseInt(campoPeso1.getText());
                    int pesoObj2 = Integer.parseInt(campoPeso2.getText());
                    int penalidad = Integer.parseInt(campoPena.getText());
                    this.setVisible(false);
                    ((Ventana) dueño).initComponents(dim, pesoObj1, pesoObj2, penalidad);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage()+"\n Solo se aceptan datos númericos","", JOptionPane.ERROR_MESSAGE);
                    campoPeso1.setText("");
                    campoPeso2.setText("");
                    campoPena.setText("");

                }

           }
        }

        @Override
        public void keyReleased(KeyEvent e) {
           
        }
    }

    private class DialogoAcerca extends JDialog {
    private Container contenedor;
    private BorderLayout esquemaBorder;
    private FlowLayout esquema;
    private JPanel panelEtiqueta;
    private JPanel panelIcono;
    private JPanel panelInferior;
    private JLabel etiquetaColores,etiquetaLugar, etiquetaVersion, etiquetaDesarrollado, etiquetaCurso, etiquetaFecha, etiquetaIcono,  etiquetaTitulo;
    private Icon pic;
    private JButton botonAceptar;
    
    /**
     * Constructor que inicializa los atributos del dialogo
     * @param padre La ventana padre del dialogo
     * @param tit El titulo del dialogo
     * @param modal Variable booleana que determina si el dialogo es modal o no
     */
   public DialogoAcerca(JFrame padre,String tit,boolean modal){
        super(padre, tit, modal); 
       // setSize(400,250);
        setResizable(false);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        contenedor= getContentPane();
        esquemaBorder= new BorderLayout();
        contenedor.setLayout(esquemaBorder);
        panelEtiqueta= new JPanel();
        panelEtiqueta.setLayout(new GridLayout(7,1));
        etiquetaTitulo= new JLabel("Acerca De");
        etiquetaColores= new JLabel("Sorting Robot");
        etiquetaVersion= new JLabel("Version 1.0");
        etiquetaLugar= new JLabel("Universidad del Valle - Escuela de Ingeniería de Sistemas y Computación");
        etiquetaDesarrollado= new JLabel("Desarrollado por Cristhian Eduardo Fuertes Daza");
        etiquetaCurso= new JLabel("Introducción a la Inteligencia Artificial 2013-B");
        etiquetaFecha= new JLabel("Octubre 7 de 2013");
        panelEtiqueta.add(etiquetaTitulo); 
        panelEtiqueta.add(etiquetaColores);
        panelEtiqueta.add(etiquetaVersion);
        panelEtiqueta.add(etiquetaLugar);
        panelEtiqueta.add(etiquetaDesarrollado);
        panelEtiqueta.add(etiquetaCurso);
        panelEtiqueta.add(etiquetaFecha);
        contenedor.add(panelEtiqueta, BorderLayout.EAST);
        panelIcono= new JPanel();
        panelIcono.setLayout(new BorderLayout());
        pic= new ImageIcon("src/Imagenes/robot.png");
        etiquetaIcono= new JLabel(pic);
        panelIcono.add(etiquetaIcono, BorderLayout.CENTER);
        panelIcono.add(new JLabel("        "),BorderLayout.WEST);
        panelIcono.add(new JLabel("        "),BorderLayout.EAST);
        contenedor.add(panelIcono, BorderLayout.WEST);
        panelInferior= new JPanel();
        esquema= new FlowLayout(FlowLayout.RIGHT);
        panelInferior.setLayout(esquema);
        BevelBorder linea= new BevelBorder(BevelBorder.LOWERED,Color.LIGHT_GRAY,Color.LIGHT_GRAY);
        panelInferior.setBorder(linea);
        botonAceptar= new JButton("Cerrar");
        botonAceptar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                 dispose();
            }
        });
        panelInferior.add(botonAceptar);
        contenedor.add(panelInferior, BorderLayout.SOUTH);
        pack();
        
        
    }
    
   
}
    
    /**
     * Método principal del aplicativo que instancia la IGU
     *
     * @param args argumentos que se le pasan al método principal
     */
    public static void main(String[] args) {


        new Ventana();


    }
}
