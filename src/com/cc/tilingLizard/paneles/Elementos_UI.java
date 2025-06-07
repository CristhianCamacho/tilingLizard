package com.cc.tilingLizard.paneles;

import com.cc.tilingLizard.eventos.Eventos;
import com.cc.tilingLizard.eventos.EventosRedoUndo;
import com.cc.tilingLizard.IO.Abrir_lizard_tiling;
import com.cc.tilingLizard.IO.Archivos_recientes;
import com.cc.tilingLizard.IO.Guardar_lizard_tiling;
import com.cc.tilingLizard.IO.Guardar_resultado_como_PNG;
import com.cc.tilingLizard.paneles.panel_disenio.Panel_de_dibujo;
import com.cc.tilingLizard.paneles.panel_disenio.Panel_patron_disenio;
import com.cc.tilingLizard.paneles.panel_resultado.Crear_Algoritmo;
import com.cc.tilingLizard.paneles.panel_resultado.Panel_resultado;
import com.cc.tilingLizard.paneles.panel_resultado.stroke.JOptionFactory;
import com.cc.tilingLizard.paneles.panel_resultado.stroke.strokes.WobbleStroke;
import com.cc.tilingLizard.utils.Constants;

import javax.media.j3d.Switch;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.math.BigInteger;
import java.util.Vector;

public class Elementos_UI implements Runnable {

    private static Elementos_UI instance;
    public static JFrame jf_principal;

    public JMenuBar jm_barra_de_menu;

    public Panel_patron_disenio panel_patron_12;
    public Panel_patron_disenio panel_patron_34;
    public Panel_patron_disenio panel_patron_56;



    public Panel_resultado panel_resultado;

    public JTabbedPane tabbedpane;

    public JPanel panelUnoSolo;
    public JPanel panelPatrones;
    public JSplitPane splitPanePatrones;
    public JSplitPane splitPane12_34;
    public JSplitPane splitPane34_56;

    public final int PERSPECTIVA_TRES_TABBEDS = 1;
    public final int PERSPECTIVA_UN_SOLO_PANEL = 2;
    public int PERSPECTIVA_actual = PERSPECTIVA_UN_SOLO_PANEL;

    public String sentidoColocadoDePuntos = Constants.AL_FINAL;

    public boolean calculandoLizard = false;

    public String nombre_del_modelo="sin_nombre";

    public Abrir_lizard_tiling abrir_lizard_tiling;
    public Guardar_lizard_tiling guardar_lizard_tiling;
    public Guardar_resultado_como_PNG guardar_resultado_como_PNG;

    public boolean detener_tiling = false;
    public Thread lastThread;

    public void detener_tiling()
    {
        detener_tiling = true;
        detenerRutina1 = true;
    }

    public Elementos_UI(JFrame f)
    {
        instance = this;
        jf_principal = f;

        // create Menus
        jm_barra_de_menu=new JMenuBar();

        /** ARCHIVO **/
        JMenu archivo=new JMenu("Archivo");
        archivo.setMnemonic(KeyEvent.VK_A);
        archivo.setToolTipText("opciones de archivo");

        jm_barra_de_menu.add(archivo);

        JMenuItem m;

        m=new JMenuItem(Constants.ABRIR, KeyEvent.VK_B);
        //m.setIcon(new ImageIcon("Imagenes"+File.separatorChar+"icono_nuevo.gif"));
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_B, ActionEvent.ALT_MASK));
        m.setToolTipText("abre un archivo");
        m.addActionListener(new Eventos(null));
        archivo.add(m);
        archivo.addSeparator();

        JMenu mr=new JMenu("Abrir Recientes");
        mr.setToolTipText("abrir archivos recientes");
        archivo.add(mr);
        archivo.addSeparator();
        Archivos_recientes.cargar_Archivos_recientes(mr);

        m=new JMenuItem("Guardar",KeyEvent.VK_G);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_G,ActionEvent.ALT_MASK));
        m.setToolTipText("guarda un archivo");
        m.addActionListener(new Eventos(null));
        archivo.add(m);
        archivo.addSeparator();

        m=new JMenuItem("Guardar como PNG",KeyEvent.VK_P);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_P,ActionEvent.ALT_MASK));
        m.setToolTipText("guarda un archivo como PNG");
        m.addActionListener(new Eventos(null));
        archivo.add(m);
        archivo.addSeparator();

        m=new JMenuItem("Salir",KeyEvent.VK_S);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_S,ActionEvent.ALT_MASK));
        m.setToolTipText("salir del programa");
        m.addActionListener(new Eventos(null));
        archivo.add(m);
        archivo.addSeparator();


        /** EDIT **/
        /////
        // para los eventos de undo redo
        /////
        archivo=new JMenu("Edit");
        archivo.setMnemonic(KeyEvent.VK_E);
        //archivo.setToolTipText("para cambiar la vista");

        m=new JMenuItem("Undo",KeyEvent.VK_U);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_U,ActionEvent.ALT_MASK));
        m.setToolTipText("para deshacer la accion");
        m.addActionListener(new EventosRedoUndo(null, m));
        archivo.add(m);
        archivo.addSeparator();

        m=new JMenuItem("Redo",KeyEvent.VK_R);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_R,ActionEvent.ALT_MASK));
        m.setToolTipText("para rehacer la accion");
        m.addActionListener(new EventosRedoUndo(m, null));
        archivo.add(m);
        archivo.addSeparator();

        m=new JMenuItem("Mover pts Patron Inicial --> Patron Recursivo",KeyEvent.VK_P);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_P,ActionEvent.ALT_MASK));
        m.setToolTipText("para mover los puntos de Patron Inicial al Patron Recursivo");
        m.addActionListener(new Eventos(null));
        archivo.add(m);

        archivo.addSeparator();

        m=new JMenuItem("Imagen de Fondo Panel Patron 12",KeyEvent.VK_1);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_1,ActionEvent.ALT_MASK));
        m.setToolTipText("para selecciona una imagen y ponerla al Patron 12");
        m.addActionListener(new Eventos(null));
        archivo.add(m);

        jm_barra_de_menu.add(archivo);

        m=new JMenuItem("Imagen de Fondo Panel Patron 34",KeyEvent.VK_3);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_3,ActionEvent.ALT_MASK));
        m.setToolTipText("para selecciona una imagen y ponerla al Patron 34");
        m.addActionListener(new Eventos(null));
        archivo.add(m);

        jm_barra_de_menu.add(archivo);

        m=new JMenuItem("Imagen de Fondo Panel Patron 56",KeyEvent.VK_5);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_5,ActionEvent.ALT_MASK));
        m.setToolTipText("para selecciona una imagen y ponerla al Patron 56");
        m.addActionListener(new Eventos(null));
        archivo.add(m);

        jm_barra_de_menu.add(archivo);

        /** ARCHIVO **/
        archivo=new JMenu("Figuras");
        archivo.setMnemonic(KeyEvent.VK_F);
        archivo.setToolTipText("Menu para dibujar figuras predefinidas");

        m=new JMenuItem("estrellas",KeyEvent.VK_P);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_P,ActionEvent.ALT_MASK));
        m.setToolTipText("dibujar una estrellas");
        m.addActionListener(new Eventos(null));
        archivo.add(m);
        archivo.addSeparator();

        m=new JMenuItem("eNeagonos",KeyEvent.VK_E);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_E,ActionEvent.ALT_MASK));
        m.setToolTipText("dibujar un 'n' agono");
        m.addActionListener(new Eventos(null));
        archivo.add(m);
        archivo.addSeparator();

        jm_barra_de_menu.add(archivo);

        /** RUTINA **/
        archivo=new JMenu("rutinas");
        archivo.setMnemonic(KeyEvent.VK_R);
        archivo.setToolTipText("para ejecutar alguna rutina de dibujo");

        m=new JMenuItem("rutina 1",KeyEvent.VK_1);
        m.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_1,ActionEvent.ALT_MASK));
        m.setToolTipText("para ejecutar al rutina 1");
        m.addActionListener(new Eventos(null));
        archivo.add(m);
        archivo.addSeparator();

        jm_barra_de_menu.add(archivo);



        jf_principal.add(jm_barra_de_menu,BorderLayout.NORTH);

        // creamos los paneles
        panel_patron_12=new Panel_patron_disenio();
        panel_patron_34=new Panel_patron_disenio();
        panel_patron_56=new Panel_patron_disenio();

        panel_resultado=new Panel_resultado();

        tabbedpane = new JTabbedPane();
        tabbedpane.addChangeListener(new Eventos(null));

        switchPERSPECTIVA(PERSPECTIVA_UN_SOLO_PANEL);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // setear valores por defecto
                panel_patron_12.panel_de_dibujo.jc_lado_1.jchb_lado.setSelected(true);
                panel_patron_12.panel_de_dibujo.jc_lado_2.jchb_lado.setSelected(true);
                panel_patron_12.panel_de_dibujo.jc_lado_2.dir_lado.setSelected(true);

                panel_patron_34.panel_de_dibujo.jc_lado_3.jchb_lado.setSelected(true);
                panel_patron_34.panel_de_dibujo.jc_lado_4.jchb_lado.setSelected(true);
                panel_patron_34.panel_de_dibujo.jc_lado_4.dir_lado.setSelected(true);

                panel_patron_56.panel_de_dibujo.jc_lado_5.jchb_lado.setSelected(true);
                panel_patron_56.panel_de_dibujo.jc_lado_6.jchb_lado.setSelected(true);
                panel_patron_56.panel_de_dibujo.jc_lado_6.dir_lado.setSelected(true);
            }
        });

    }

    public void switchPERSPECTIVA(int perspectiva)
    {
        PERSPECTIVA_actual = perspectiva;

        switch(PERSPECTIVA_actual){

            case PERSPECTIVA_TRES_TABBEDS:
            {
                /*
                Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
                jf_principal.setSize(new Dimension(dim.width-20,720+10));

                if(panelUnoSolo!=null)
                {
                    jf_principal.remove(panelUnoSolo);
                    splitPanePatrones.remove(panel_patron_recursivo);
                    splitPane.remove(splitPanePatrones);
                    splitPane.remove(panel_de_dibujo);
                }

                if(tabbedpane==null)
                {
                    tabbedpane = new JTabbedPane();
                    tabbedpane.addChangeListener(new Eventos(null,this));
                }

                tabbedpane.addTab("patron inicial",null,panel_patron_inicial,"para definir el patron inicial");

                tabbedpane.addTab("patron recursivo",null,panel_patron_recursivo,"para definir el patron recursivo");

                tabbedpane.addTab("Dibujar",null,panel_de_dibujo,"para dibujar el fractal");

                jf_principal.add(tabbedpane);
                llenar_CONSTANTES();//para saber en que panel estamos

                tabbedpane.updateUI();
                */


                center();
                break;
            }
            case PERSPECTIVA_UN_SOLO_PANEL:
            {
                if(tabbedpane!=null)
                {
                    jf_principal.remove(tabbedpane);
                    tabbedpane.remove(panel_patron_12);
                    tabbedpane.remove(panel_patron_34);
                    tabbedpane.remove(panel_patron_56);
                    tabbedpane.remove(panel_resultado);
                }

                if(panelUnoSolo==null)
                {
                    panelUnoSolo = new JPanel();
                    panelUnoSolo.setLayout(new BoxLayout(panelUnoSolo,BoxLayout.X_AXIS));
                    splitPanePatrones = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

                    panelPatrones = new JPanel();
                    panelPatrones.setLayout(new BoxLayout(panelPatrones,BoxLayout.Y_AXIS));
                    splitPane12_34 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
                    splitPane34_56 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
                }

                splitPanePatrones.setLeftComponent(panelPatrones);
                splitPanePatrones.setRightComponent(panel_resultado);
                splitPanePatrones.setDividerLocation((int)(jf_principal.getWidth()*1/3)-splitPanePatrones.getDividerSize());

                splitPane12_34.setTopComponent(panel_patron_12);
                splitPane12_34.setBottomComponent(panel_patron_34);
                splitPane12_34.setDividerLocation((int)(jf_principal.getHeight()*1/3)-2*splitPanePatrones.getDividerSize());
                splitPane34_56.setTopComponent(splitPane12_34);
                splitPane34_56.setBottomComponent(panel_patron_56);
                splitPane34_56.setDividerLocation((int)(jf_principal.getHeight()*2/3)-2*splitPanePatrones.getDividerSize());

                panelPatrones.add(splitPane12_34);
                panelPatrones.add(splitPane34_56);

                panelUnoSolo.add(splitPanePatrones);

                jf_principal.add(panelUnoSolo);
                panel_patron_12.updateUI();
                panel_patron_34.updateUI();
                panel_patron_56.updateUI();
                panel_resultado.updateUI();

                center();
                break;
            }
        }
    }

    public static Elementos_UI getInstance()
    {
        if (instance == null)
        {
            instance = new Elementos_UI(jf_principal);
        }

        return instance;
    }

    public void center()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = jf_principal.getSize();
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        jf_principal.setLocation(x, y);
    }

    public synchronized void clear()
    {
        panel_resultado.panel_de_dibujo.setBackgroundImage(null);
        panel_resultado.panel_de_dibujo.setBackgroundImage(null);

        panel_resultado.panel_de_dibujo.repaint();
        panel_resultado.panel_de_dibujo.repaint();

        // la barra de progreso al 0%
        panel_resultado.progresoFractal.setValue(0);
        panel_resultado.progresoRutina.setValue(0);

        // para el tiempo que falta
        panel_resultado.tiempoRestanteFractal.setText("");
        panel_resultado.tiempoRestanteRutina.setText("");
    }

    // nunca synchronized se tira feo
    public void setCalculandoLizard(boolean calculandoL) {
        calculandoLizard = calculandoL;
    }

    // nunca synchronized se tira feo
    public boolean getCalculandoLizard() {
        return calculandoLizard;
    }

    public synchronized void calcular_lizard(boolean isLiveDrawing) {
        setCalculandoLizard(true);
        Point2D[] pb=panel_resultado.panel_de_dibujo.get_puntos();

        Point2D[] p_12=panel_patron_12.panel_de_dibujo.get_puntos();
        Point2D[] p_34=panel_patron_34.panel_de_dibujo.get_puntos();
        Point2D[] p_56=panel_patron_56.panel_de_dibujo.get_puntos();

        Graphics g = panel_resultado.panel_de_dibujo.getGraphics();
        g.setColor(panel_resultado.panel_de_dibujo.getColor_lineas());
        ((Graphics2D)g).setStroke(panel_resultado.panel_de_dibujo.getStroke());

        boolean dibujarPrimeraLineaComoShape = panel_resultado.check_dibujarPrimeraLineaComoShape.isSelected();
        boolean no_dibujarPrimeraLineaPatronRecursivo = panel_resultado.check_no_dibujarPrimeraLineaPatronRecursivo.isSelected();

        int orden=panel_resultado.jcb_nivel.getSelectedIndex();
        if (orden == 0)
            return;

        if (orden == 1 && dibujarPrimeraLineaComoShape)
        {
            dibujarNivel_1(g);
            return;
        }
        else
        {
            Crear_Algoritmo ca_12_1;
            Crear_Algoritmo ca_12_2;
            Crear_Algoritmo ca_34_1;
            Crear_Algoritmo ca_34_2;
            Crear_Algoritmo ca_56_1;
            Crear_Algoritmo ca_56_2;

            Thread hilo = null;

            Panel_de_dibujo.JChecksLado lado_resultado_1 = panel_patron_12.panel_de_dibujo.jc_lado_1;
            Panel_de_dibujo.JChecksLado lado_resultado_2 = panel_patron_12.panel_de_dibujo.jc_lado_2;
            Panel_de_dibujo.JChecksLado lado_resultado_3 = panel_patron_12.panel_de_dibujo.jc_lado_3;
            Panel_de_dibujo.JChecksLado lado_resultado_4 = panel_patron_12.panel_de_dibujo.jc_lado_4;
            Panel_de_dibujo.JChecksLado lado_resultado_5 = panel_patron_12.panel_de_dibujo.jc_lado_5;
            Panel_de_dibujo.JChecksLado lado_resultado_6 = panel_patron_12.panel_de_dibujo.jc_lado_6;

            Panel_de_dibujo.JChecksLado[] lado_1 = getSelectedLado(pb, lado_resultado_1, lado_resultado_2, lado_resultado_3, lado_resultado_4, lado_resultado_5, lado_resultado_6);
            /** 12 **/
            for(Panel_de_dibujo.JChecksLado cl : lado_1)
            {
                if (cl == null)
                    continue;

                Point2D[]  newArray = cl.lado;
                Crear_Algoritmo ca = new Crear_Algoritmo(
                        cl.dir_lado.isSelected()? Constants.invertiArregloPoint2D(newArray): newArray,
                        p_12,
                        g,
                        orden,
                        this,
                        no_dibujarPrimeraLineaPatronRecursivo,
                        panel_resultado.panel_de_dibujo.getColor_lineas() != null?
                        panel_resultado.panel_de_dibujo.getColor_lineas():
                        panel_patron_12.panel_de_dibujo.getColorLineas());
                /*Thread */hilo = new Thread(ca);
                hilo.start();
            }
            /*
            Point2D[]  newArray_12_1 = lado_1[0].lado;
            ca_12_1 = new Crear_Algoritmo(
                    lado_1[0].dir_lado.isSelected()? Constants.invertiArregloPoint2D(newArray_12_1): newArray_12_1,
                    p_12,
                    g,
                    orden,
                    this,
                    no_dibujarPrimeraLineaPatronRecursivo,
                    panel_resultado.panel_de_dibujo.getColor_lineas() != null?
                    panel_resultado.panel_de_dibujo.getColor_lineas():
                    panel_patron_12.panel_de_dibujo.getColorLineas());
            Thread hilo_12_1 = new Thread(ca_12_1);
            hilo_12_1.start();

            Point2D[] newArray_12_2 = lado_1[1].lado;//Constants.subArrayPoint2D(pb, 1, 2);
            ca_12_2 = new Crear_Algoritmo(
                    lado_1[1].dir_lado.isSelected()? Constants.invertiArregloPoint2D(newArray_12_2): newArray_12_2,
                    p_12,
                    g,
                    orden,
                    this,
                    no_dibujarPrimeraLineaPatronRecursivo,
                    panel_resultado.panel_de_dibujo.getColor_lineas() != null?
                    panel_resultado.panel_de_dibujo.getColor_lineas():
                    panel_patron_12.panel_de_dibujo.getColorLineas());
            Thread hilo_12_2 = new Thread(ca_12_2);
            hilo_12_2.start();
            */

if(!isLiveDrawing)
try {
    if ( hilo != null )
    hilo.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}

            lado_resultado_1 = panel_patron_34.panel_de_dibujo.jc_lado_1;
            lado_resultado_2 = panel_patron_34.panel_de_dibujo.jc_lado_2;
            lado_resultado_3 = panel_patron_34.panel_de_dibujo.jc_lado_3;
            lado_resultado_4 = panel_patron_34.panel_de_dibujo.jc_lado_4;
            lado_resultado_5 = panel_patron_34.panel_de_dibujo.jc_lado_5;
            lado_resultado_6 = panel_patron_34.panel_de_dibujo.jc_lado_6;

            Panel_de_dibujo.JChecksLado[] lado_2 = getSelectedLado(pb, lado_resultado_1, lado_resultado_2, lado_resultado_3, lado_resultado_4, lado_resultado_5, lado_resultado_6);

            for(Panel_de_dibujo.JChecksLado cl : lado_2)
            {
                if (cl == null)
                    continue;

                Point2D[]  newArray = cl.lado;
                Crear_Algoritmo ca = new Crear_Algoritmo(
                        cl.dir_lado.isSelected()? Constants.invertiArregloPoint2D(newArray): newArray,
                        p_34,
                        g,
                        orden,
                        this,
                        no_dibujarPrimeraLineaPatronRecursivo,
                        panel_resultado.panel_de_dibujo.getColor_lineas() != null?
                        panel_resultado.panel_de_dibujo.getColor_lineas():
                        panel_patron_12.panel_de_dibujo.getColorLineas());
                hilo = new Thread(ca);
                hilo.start();
            }

            /** 34 **/
            /*
            Point2D[] newArray_34_1 = lado_2[0].lado;//Constants.subArrayPoint2D(pb, 2, 3);
            ca_34_1 = new Crear_Algoritmo(
                    lado_2[0].dir_lado.isSelected()? Constants.invertiArregloPoint2D(newArray_34_1): newArray_34_1,
                    p_34,
                    g,
                    orden,
                    this,
                    no_dibujarPrimeraLineaPatronRecursivo,
                    panel_resultado.panel_de_dibujo.getColor_lineas() != null?
                    panel_resultado.panel_de_dibujo.getColor_lineas():
                    panel_patron_34.panel_de_dibujo.getColorLineas());
            Thread hilo_34_1 = new Thread(ca_34_1);
            hilo_34_1.start();

            Point2D[] newArray_34_2 = lado_2[1].lado;//Constants.subArrayPoint2D(pb, 3, 4);
            ca_34_2 = new Crear_Algoritmo(
                    lado_2[1].dir_lado.isSelected()? Constants.invertiArregloPoint2D(newArray_34_2): newArray_34_2,
                    p_34,
                    g,
                    orden,
                    this,
                    no_dibujarPrimeraLineaPatronRecursivo,
                    panel_resultado.panel_de_dibujo.getColor_lineas() != null?
                    panel_resultado.panel_de_dibujo.getColor_lineas():
                    panel_patron_34.panel_de_dibujo.getColorLineas());
            Thread hilo_34_2 = new Thread(ca_34_2);
            hilo_34_2.start();
*/

if(!isLiveDrawing)
try {
    if ( hilo != null )
        hilo.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}

            lado_resultado_1 = panel_patron_56.panel_de_dibujo.jc_lado_1;
            lado_resultado_2 = panel_patron_56.panel_de_dibujo.jc_lado_2;
            lado_resultado_3 = panel_patron_56.panel_de_dibujo.jc_lado_3;
            lado_resultado_4 = panel_patron_56.panel_de_dibujo.jc_lado_4;
            lado_resultado_5 = panel_patron_56.panel_de_dibujo.jc_lado_5;
            lado_resultado_6 = panel_patron_56.panel_de_dibujo.jc_lado_6;

            Panel_de_dibujo.JChecksLado[] lado_3 = getSelectedLado(pb, lado_resultado_1, lado_resultado_2, lado_resultado_3, lado_resultado_4, lado_resultado_5, lado_resultado_6);

            for(Panel_de_dibujo.JChecksLado cl : lado_3)
            {
                if (cl == null)
                    continue;

                Point2D[]  newArray = cl.lado;
                Crear_Algoritmo ca = new Crear_Algoritmo(
                        cl.dir_lado.isSelected()? Constants.invertiArregloPoint2D(newArray): newArray,
                        p_56,
                        g,
                        orden,
                        this,
                        no_dibujarPrimeraLineaPatronRecursivo,
                        panel_resultado.panel_de_dibujo.getColor_lineas() != null?
                                panel_resultado.panel_de_dibujo.getColor_lineas():
                                panel_patron_12.panel_de_dibujo.getColorLineas());
                hilo = new Thread(ca);
                hilo.start();
            }

            /** 56 **/
/*
            Point2D[] newArray_56_1 = lado_3[0].lado;//Constants.subArrayPoint2D(pb, 4, 5);
            ca_56_1 = new Crear_Algoritmo(
                    lado_3[0].dir_lado.isSelected()? Constants.invertiArregloPoint2D(newArray_56_1): newArray_56_1,
                    p_56,
                    g,
                    orden,
                    this,
                    no_dibujarPrimeraLineaPatronRecursivo,
                    panel_resultado.panel_de_dibujo.getColor_lineas() != null?
                    panel_resultado.panel_de_dibujo.getColor_lineas():
                    panel_patron_56.panel_de_dibujo.getColorLineas());
            Thread hilo_56_1 = new Thread(ca_56_1);
            hilo_56_1.start();

            Point2D[] newArray_56_2 = lado_3[1].lado;//Constants.subArrayPoint2D(pb, 5, 6);
            ca_56_2 = new Crear_Algoritmo(
                    lado_3[1].dir_lado.isSelected()? Constants.invertiArregloPoint2D(newArray_56_2): newArray_56_2,
                    p_56,
                    g,
                    orden,
                    this,
                    no_dibujarPrimeraLineaPatronRecursivo,
                    panel_resultado.panel_de_dibujo.getColor_lineas() != null?
                    panel_resultado.panel_de_dibujo.getColor_lineas():
                    panel_patron_56.panel_de_dibujo.getColorLineas());
            Thread hilo_56_2 = new Thread(ca_56_2);
            hilo_56_2.start();

            lastThread = hilo_56_2;
*/

        }
    }

    public void dibujarNivel_1(Graphics g)
    {  /*
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    */
        ((Graphics2D)g)
                .draw(Constants
                        .crear_POLIGONO_CERRADO(
                                panel_resultado
                                        .panel_de_dibujo.v_puntos,
                                false));
        setCalculandoLizard(false);

        Constants.updatePanel_de_dibujoBackground();

                    /*
                }
            });
            hilo.start();
            */
    }

    Panel_de_dibujo.JChecksLado[] getSelectedLado(Point2D[] pb,
                              Panel_de_dibujo.JChecksLado isLado1,
                              Panel_de_dibujo.JChecksLado isLado2,
                              Panel_de_dibujo.JChecksLado isLado3,
                              Panel_de_dibujo.JChecksLado isLado4,
                              Panel_de_dibujo.JChecksLado isLado5,
                              Panel_de_dibujo.JChecksLado isLado6)
    {
        Panel_de_dibujo.JChecksLado[] result = new Panel_de_dibujo.JChecksLado[6];
        int cont = 0;

        if(isLado1.jchb_lado.isSelected())
        {
            //result = Constants.subArrayPoint2D(pb, 0,1);
            isLado1.lado = Constants.subArrayPoint2D(pb, 0,1);
            result[cont] = isLado1;
            cont++;
        }

        if(isLado2.jchb_lado.isSelected())
        {
            //result = Constants.subArrayPoint2D(pb, 1,2);
            isLado2.lado = Constants.subArrayPoint2D(pb, 1,2);
            result[cont] = isLado2;
            cont++;
        }

        if(isLado3.jchb_lado.isSelected())
        {
            //result = Constants.subArrayPoint2D(pb, 2,3);
            isLado3.lado = Constants.subArrayPoint2D(pb, 2,3);
            result[cont] = isLado3;
            cont++;
        }

        if(isLado4.jchb_lado.isSelected())
        {
            //result = Constants.subArrayPoint2D(pb, 3,4);
            isLado4.lado = Constants.subArrayPoint2D(pb, 3,4);
            result[cont] = isLado4;
            cont++;
        }

        if(isLado5.jchb_lado.isSelected())
        {
            //result = Constants.subArrayPoint2D(pb, 4,5);
            isLado5.lado = Constants.subArrayPoint2D(pb, 4,5);
            result[cont] = isLado5;
            cont++;
        }

        if(isLado6.jchb_lado.isSelected())
        {
            //result = Constants.subArrayPoint2D(pb, 5,6);
            isLado6.lado = Constants.subArrayPoint2D(pb, 5,6);
            result[cont] = isLado6;
            cont++;
        }

        return result;
    }

    public String get_nombre_del_modelo()
    {
        return nombre_del_modelo;
    }

    public void set_nombre_del_modelo(String s)
    {
        nombre_del_modelo=s;
        panel_patron_12.jta_nombre_del_modelo.setText(s);
        panel_patron_34.jta_nombre_del_modelo.setText(s);
        panel_patron_56.jta_nombre_del_modelo.setText(s);
        panel_resultado.jta_nombre_del_modelo.setText(s);
    }

    public String datos_para_guardar()
    {
        String s_salida="";

        Vector v_panel_patron_12 = panel_patron_12.panel_de_dibujo.v_puntos;
        Vector v_panel_patron_34 = panel_patron_34.panel_de_dibujo.v_puntos;
        Vector v_panel_patron_56 = panel_patron_56.panel_de_dibujo.v_puntos;

        s_salida=s_salida+v_panel_patron_12.size()+"_PATRON_12\n";
        for(int i=0;i<v_panel_patron_12.size();i++)
        {
            Point2D p=(Point2D)v_panel_patron_12.get(i);

            s_salida=s_salida+"("+p.getX()+","+p.getY()+")\n";

        }
        s_salida=s_salida+"lado1="+panel_patron_12.panel_de_dibujo.jc_lado_1.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado2="+panel_patron_12.panel_de_dibujo.jc_lado_2.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado3="+panel_patron_12.panel_de_dibujo.jc_lado_3.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado4="+panel_patron_12.panel_de_dibujo.jc_lado_4.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado5="+panel_patron_12.panel_de_dibujo.jc_lado_5.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado6="+panel_patron_12.panel_de_dibujo.jc_lado_6.jchb_lado.isSelected()+"_\n";

        s_salida=s_salida+"dirlado1="+panel_patron_12.panel_de_dibujo.jc_lado_1.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado2="+panel_patron_12.panel_de_dibujo.jc_lado_2.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado3="+panel_patron_12.panel_de_dibujo.jc_lado_3.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado4="+panel_patron_12.panel_de_dibujo.jc_lado_4.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado5="+panel_patron_12.panel_de_dibujo.jc_lado_5.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado6="+panel_patron_12.panel_de_dibujo.jc_lado_6.dir_lado.isSelected()+"_\n";


        s_salida=s_salida+v_panel_patron_34.size()+"_PATRON_34\n";
        for(int i=0;i<v_panel_patron_34.size();i++)
        {
            Point2D p=(Point2D)v_panel_patron_34.get(i);

            s_salida=s_salida+"("+p.getX()+","+p.getY()+")\n";

        }
        s_salida=s_salida+"lado1="+panel_patron_34.panel_de_dibujo.jc_lado_1.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado2="+panel_patron_34.panel_de_dibujo.jc_lado_2.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado3="+panel_patron_34.panel_de_dibujo.jc_lado_3.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado4="+panel_patron_34.panel_de_dibujo.jc_lado_4.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado5="+panel_patron_34.panel_de_dibujo.jc_lado_5.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado6="+panel_patron_34.panel_de_dibujo.jc_lado_6.jchb_lado.isSelected()+"_\n";

        s_salida=s_salida+"dirlado1="+panel_patron_34.panel_de_dibujo.jc_lado_1.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado2="+panel_patron_34.panel_de_dibujo.jc_lado_2.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado3="+panel_patron_34.panel_de_dibujo.jc_lado_3.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado4="+panel_patron_34.panel_de_dibujo.jc_lado_4.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado5="+panel_patron_34.panel_de_dibujo.jc_lado_5.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado6="+panel_patron_34.panel_de_dibujo.jc_lado_6.dir_lado.isSelected()+"_\n";


        s_salida=s_salida+v_panel_patron_56.size()+"_PATRON_56\n";
        for(int i=0;i<v_panel_patron_56.size();i++)
        {
            Point2D p=(Point2D)v_panel_patron_56.get(i);

            s_salida=s_salida+"("+p.getX()+","+p.getY()+")\n";

        }
        s_salida=s_salida+"lado1="+panel_patron_56.panel_de_dibujo.jc_lado_1.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado2="+panel_patron_56.panel_de_dibujo.jc_lado_2.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado3="+panel_patron_56.panel_de_dibujo.jc_lado_3.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado4="+panel_patron_56.panel_de_dibujo.jc_lado_4.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado5="+panel_patron_56.panel_de_dibujo.jc_lado_5.jchb_lado.isSelected()+"_\n";
        s_salida=s_salida+"lado6="+panel_patron_56.panel_de_dibujo.jc_lado_6.jchb_lado.isSelected()+"_\n";

        s_salida=s_salida+"dirlado1="+panel_patron_56.panel_de_dibujo.jc_lado_1.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado2="+panel_patron_56.panel_de_dibujo.jc_lado_2.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado3="+panel_patron_56.panel_de_dibujo.jc_lado_3.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado4="+panel_patron_56.panel_de_dibujo.jc_lado_4.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado5="+panel_patron_56.panel_de_dibujo.jc_lado_5.dir_lado.isSelected()+"_\n";
        s_salida=s_salida+"dirlado6="+panel_patron_56.panel_de_dibujo.jc_lado_6.dir_lado.isSelected()+"_\n";

        return s_salida;
    }

    public void datos_para_abrir(Vector v)
    {
        Vector v_patron_12 = new Vector() ;
        Vector v_patron_34 = new Vector() ;
        Vector v_patron_56 = new Vector() ;

        String inicio=(String)v.get(0);

        String etiqueta="_PATRON_12";

        int tam_p_12=0;
        int tam_p_34=0;
        int tam_p_56=0;

        if( inicio.endsWith(etiqueta) ) {
            String segundo = inicio.substring(0, inicio.length() - etiqueta.length());

            tam_p_12 = Integer.parseInt(segundo);
            System.out.println("Elementos_UI:datos_para_abrir:tam_p_12 " + tam_p_12);

            for (int i = 0; i < tam_p_12; i++) {
                String tercero = (String) v.get(i + 1);

                String s_x = "";
                String s_y = "";
                Point2D punto;

                for (int j = 1; j < tercero.length() - 1; j++) {
                    {
                        if (tercero.substring(j - 1, j).equalsIgnoreCase("(")) {
                            s_x = Constants.concatenar_hasta(tercero.substring(j, tercero.length()), ",");
                        } else if (tercero.substring(j - 1, j).equalsIgnoreCase(",")) {
                            s_y = Constants.concatenar_hasta(tercero.substring(j, tercero.length()), ")");
                        }
                    }
                }

                System.out.println("Elementos_UI:datos_para_abrir: (" + s_x + ", " + s_y + ")");

                punto = new Point2D.Double(Double.parseDouble(s_x), Double.parseDouble(s_y));
                v_patron_12.add(punto);
            }
            Constants.leerLadosYDirecciones(panel_patron_12, v, tam_p_12, 0);
        }

        int contador = 6 + 6;
        inicio = (String) v.get(tam_p_12 + 1 + contador);
        etiqueta = "_PATRON_34";

        if (inicio.endsWith(etiqueta)) {
                String segundo = inicio.substring(0, inicio.length() - etiqueta.length());

                tam_p_34 = Integer.parseInt(segundo);
                System.out.println("Elementos_UI:datos_para_abrir:tam_p_34 " + tam_p_34);

                for (int i = 0; i < tam_p_34; i++) {
                    String tercero = (String) v.get(i + tam_p_12 + 2 + contador);

                    String s_x = "";
                    String s_y = "";
                    Point2D punto;

                    for (int j = 1; j < tercero.length(); j++) {
                        if (tercero.substring(j - 1, j).equalsIgnoreCase("(")) {
                            s_x = Constants.concatenar_hasta(tercero.substring(j, tercero.length()), ",");
                        }
                        if (tercero.substring(j - 1, j).equalsIgnoreCase(",")) {
                            s_y = Constants.concatenar_hasta(tercero.substring(j, tercero.length()), ")");
                        }
                    }

                    System.out.println("Elementos_UI:datos_para_abrir: (" + s_x + ", " + s_y + ")");

                    punto = new Point2D.Double(Double.parseDouble(s_x), Double.parseDouble(s_y));
                    v_patron_34.add(punto);
                }
                Constants.leerLadosYDirecciones(panel_patron_34, v, tam_p_12 + tam_p_34 + tam_p_56, contador+1);
            }

        contador = contador+6+6;
        inicio = (String) v.get(tam_p_12 + tam_p_34 + 2 + contador);
        etiqueta = "_PATRON_56";

        if (inicio.endsWith(etiqueta)) {
                String segundo = inicio.substring(0, inicio.length() - etiqueta.length());

                tam_p_56 = Integer.parseInt(segundo);
                System.out.println("Elementos_UI:datos_para_abrir:tam_p_56 " + tam_p_56);

                for (int i = 0; i < tam_p_56; i++) {
                    String tercero = (String) v.get(i + tam_p_12 + tam_p_34 + 3 + contador);

                    String s_x = "";
                    String s_y = "";
                    Point2D punto;

                    for (int j = 1; j < tercero.length(); j++) {


                        if (tercero.substring(j - 1, j).equalsIgnoreCase("("))
                            s_x = Constants.concatenar_hasta(tercero.substring(j, tercero.length()), ",");
                        if (tercero.substring(j - 1, j).equalsIgnoreCase(","))
                            s_y = Constants.concatenar_hasta(tercero.substring(j, tercero.length()), ")");
                    }

                    System.out.println("Elementos_UI:datos_para_abrir: (" + s_x + ", " + s_y + ")");

                    punto = new Point2D.Double(Double.parseDouble(s_x), Double.parseDouble(s_y));
                    v_patron_56.add(punto);
                }
                Constants.leerLadosYDirecciones(panel_patron_56, v, tam_p_12 + tam_p_34 + tam_p_56, contador+2);
            }

        panel_patron_12.panel_de_dibujo.v_puntos=v_patron_12;
        setAllFalse(panel_patron_12.panel_de_dibujo);
        panel_patron_34.panel_de_dibujo.v_puntos=v_patron_34;
        setAllFalse(panel_patron_34.panel_de_dibujo);
        panel_patron_56.panel_de_dibujo.v_puntos=v_patron_56;
        setAllFalse(panel_patron_56.panel_de_dibujo);

    }

    public void setColorLineas_Panel_resultado(Color color1)
    {
        panel_resultado.color_lineas.setBackground(color1);
        panel_resultado.panel_de_dibujo.setColorLineas(color1);

        Color colorOpuesto;
        if (color1 != null)
        {
            colorOpuesto = new Color((int)(255-color1.getRed()),
                    (int)(255-color1.getGreen()),
                    (int)(255-color1.getBlue()));

        }
        else
        {
            colorOpuesto = Color.BLACK;
        }

        panel_resultado.color_lineas.setForeground(colorOpuesto);
    }

    public void setColorFondo_Panel_resultado(Color color1)
    {
        panel_resultado.color_fondo.setBackground(color1);
        panel_resultado.panel_de_dibujo.setColor_fondo(color1);
    }

    public void tiling(String tipoDeTiling)
    {
        try {


            setCalculandoLizard(true);

            Vector oldPoints =  (Vector)panel_resultado.panel_de_dibujo.v_puntos.clone();

            // mover teseando
            Vector v_puntos = (Vector)panel_resultado.panel_de_dibujo.v_puntos.clone();


            /** v_puntosRotadosYtrasladados 1 **/
            // 1
            //ejecutarLizard();

            Vector v_puntosRotados = Constants.aplicarRotacion(v_puntos, -120, Constants.TAM);

            double xlado2med = ( ((Point2D)v_puntosRotados.get(2)).getX() - ((Point2D)v_puntosRotados.get(1)).getX() )/2;
            double ylado2med = ( ((Point2D)v_puntosRotados.get(2)).getY() - ((Point2D)v_puntosRotados.get(1)).getY() )/2;

            double lado_hexagono = ((Point2D)v_puntosRotados.get(0)).distance((Point2D)v_puntosRotados.get(1));
            double lado_h = (Math.sqrt(3)/2) * lado_hexagono;

            // 2
            v_puntosRotados = Constants.aplicarRotacion(v_puntos, -120, Constants.TAM);
            Vector v_puntosRotadosYtrasladados = Constants.aplicarTraslacion(
                    v_puntosRotados,
                    new Point2D.Double(2*(xlado2med + lado_h) , 0),
                    Constants.TAM);
            panel_resultado.panel_de_dibujo.v_puntos = v_puntosRotadosYtrasladados;
            //ejecutarLizard();

            // 3
            v_puntosRotados = Constants.aplicarRotacion(v_puntos, 120, Constants.TAM);
            v_puntosRotadosYtrasladados = Constants.aplicarTraslacion(
                    v_puntosRotados,
                    new Point2D.Double(-2*xlado2med - 2*lado_h , 0),
                    Constants.TAM);
            panel_resultado.panel_de_dibujo.v_puntos = v_puntosRotadosYtrasladados;
            //ejecutarLizard();

            for (int index_row = -1; index_row < 3; index_row++ ) {
                double salto_row = 3 * lado_hexagono;
                for (int index_col = -1; index_col < 3; index_col++) {
                    double salto_col = 3 * 2 * lado_h;

                    // 4
                    v_puntosRotados = Constants.aplicarRotacion(v_puntos, -120, Constants.TAM);
                    v_puntosRotadosYtrasladados = Constants.aplicarTraslacion(
                            v_puntosRotados,
                            new Point2D.Double(-xlado2med - lado_h + index_col * salto_col, -ylado2med - lado_hexagono + index_row * salto_row),
                            Constants.TAM);
                    panel_resultado.panel_de_dibujo.v_puntos = v_puntosRotadosYtrasladados;
                    ejecutarLizard();
                    // 5
                    v_puntosRotadosYtrasladados = Constants.aplicarTraslacion(
                            v_puntos,
                            new Point2D.Double(-3 * (xlado2med + lado_h) + index_col * salto_col, -ylado2med - lado_hexagono + index_row * salto_row),
                            Constants.TAM);
                    panel_resultado.panel_de_dibujo.v_puntos = v_puntosRotadosYtrasladados;
                    ejecutarLizard();
                    // 6
                    v_puntosRotados = Constants.aplicarRotacion(v_puntos, 120, Constants.TAM);
                    v_puntosRotadosYtrasladados = Constants.aplicarTraslacion(
                            v_puntosRotados,
                            new Point2D.Double(1 * (xlado2med + lado_h) + index_col * salto_col, (ylado2med - 2 * lado_hexagono) + index_row * salto_row),
                            Constants.TAM);
                    panel_resultado.panel_de_dibujo.v_puntos = v_puntosRotadosYtrasladados;
                    ejecutarLizard();
                }
            }

            for (int index_row = -2; index_row < 2; index_row++ ) {
                double salto_row = 3 * lado_hexagono;
                for (int index_col = -1; index_col < 2; index_col++) {
                    double salto_col = 3 * 2 * lado_h;
                    // 7
                    v_puntosRotados = Constants.aplicarRotacion(v_puntos, -120, Constants.TAM);
                    v_puntosRotadosYtrasladados = Constants.aplicarTraslacion(
                            v_puntosRotados,
                            new Point2D.Double(-xlado2med - lado_h + 3*(xlado2med + lado_h) + index_col * salto_col, 2*(ylado2med + lado_hexagono) + index_row * salto_row),
                            Constants.TAM);
                    panel_resultado.panel_de_dibujo.v_puntos = v_puntosRotadosYtrasladados;
                    ejecutarLizard();
                    // 8
                    v_puntosRotados = Constants.aplicarRotacion(v_puntos, 120, Constants.TAM);
                    v_puntosRotadosYtrasladados = Constants.aplicarTraslacion(
                            v_puntosRotados,
                            new Point2D.Double((xlado2med + lado_h) + 3*(xlado2med + lado_h) + index_col * salto_col, 2*(ylado2med + lado_hexagono) + index_row * salto_row),
                            Constants.TAM);
                    panel_resultado.panel_de_dibujo.v_puntos = v_puntosRotadosYtrasladados;
                    ejecutarLizard();
                    // 9
                    v_puntosRotadosYtrasladados = Constants.aplicarTraslacion(
                            v_puntos,
                            new Point2D.Double(3 * (xlado2med + lado_h) + 3*(xlado2med + lado_h) + index_col * salto_col, 2*(ylado2med + lado_hexagono) + index_row * salto_row),
                            Constants.TAM);
                    panel_resultado.panel_de_dibujo.v_puntos = v_puntosRotadosYtrasladados;
                    ejecutarLizard();
                }
            }

            //lastThread.join();
            panel_resultado.panel_de_dibujo.v_puntos = oldPoints;


            /** **/


/*
            // mover los puntos
            double xMin = ((Point2D)v_puntos.get(0)).getX();
            double yMin = ((Point2D)v_puntos.get(0)).getY();

            double xMax = ((Point2D)v_puntos.get(0)).getX();
            double yMax = ((Point2D)v_puntos.get(0)).getY();

            //double diferenciaAnteriorYmaxActualYmax = 0;
            double anteriorYMax = ((Point2D)v_puntos.get(0)).getY();

            for(int i=0; i<v_puntos.size(); i++) {

                Point2D valPoint2D = ((Point2D)v_puntos.get(i));
                if ( valPoint2D.getX() <= xMin )
                {
                    xMin = valPoint2D.getX();
                }
                if ( valPoint2D.getY() <= yMin )
                {
                    yMin = valPoint2D.getY();
                }

                if ( valPoint2D.getX() >= xMax )
                {
                    xMax = valPoint2D.getX();
                }
                if ( valPoint2D.getY() >= yMax )
                {
                    anteriorYMax = yMax;
                    yMax = valPoint2D.getY();
                }
            }

            final double diferenciaAnteriorYmaxActualYmax = yMax - anteriorYMax;

            double anchoFigura = xMax - xMin;
            double altoFigura = yMax - yMin;

            int MAX_X_SCREEN = (int)(panel_resultado.panel_de_dibujo.getWidth()/anchoFigura);
            int MAX_Y_SCREEN = (int)(panel_resultado.panel_de_dibujo.getHeight()/altoFigura);

            // por si pasa un error feo
            if (MAX_X_SCREEN > 1000 ||
                    MAX_Y_SCREEN > 1000) {
                return;
            }

            final double _xMin = xMin;
            final double _yMin = yMin;
            Thread hiloRutina1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    int MIN_ROW = -1;
                    int MIN_COL = -1;

                    int MAX_ROW = MAX_Y_SCREEN + 1 + 1;
                    int MAX_COL = MAX_X_SCREEN + 1 + 1;

                    settearBarraDeProgresoTilingValoresIniciales( (MAX_ROW - (MIN_ROW) +1)*(MAX_COL - (MIN_COL) +1) );

                    for(int columna=MIN_ROW; columna<=MAX_ROW; columna++)
                    {
                        for(int fila=MIN_COL; fila<=MAX_COL; fila++)
                        {

                            // para que no hagan mas calculos si se presiono el boton de Stop
                            //if (detener_tiling || !getCalculandoLizard() ) {
                            //    return;
                            //}

                            Vector nuevosPuntos = new Vector();
                            for(int i=0; i<v_puntos.size(); i++)
                            {
                                Point2D valPoint2D = ((Point2D)v_puntos.get(i));

                                double correccionX = 0;
                                double correccionY = 0;


                                switch (tipoDeTiling) {

                                    case Panel_resultado.TILING_CUADRADO:

                                        break;
                                    case Panel_resultado.TILING_HEXAGONAL:

                                        // triagulos
                                        if (v_puntos.size() == 4)
                                        {
                                            correccionY = altoFigura;
                                        }
                                        // no triangulos
                                        else
                                        {
                                            correccionY = columna*diferenciaAnteriorYmaxActualYmax;
                                        }

                                        if (columna == -1)
                                        {
                                            correccionX = anchoFigura / 2;
                                        }
                                        else
                                        if(columna%2==1)
                                        {
                                            correccionX = -anchoFigura/2;
                                        } else
                                        {
                                            correccionX = -anchoFigura;
                                        }

                                        break;

                                    default:
                                        throw new IllegalArgumentException("Invalid tipoDeTiling ");

                                }

                                Point2D newPoint2D = new Point2D.Double(
                                        valPoint2D.getX() - _xMin + fila*anchoFigura - correccionX,
                                        valPoint2D.getY() - _yMin + columna*altoFigura - correccionY
                                );
                                nuevosPuntos.add(newPoint2D);
                            }
                            panel_resultado.panel_de_dibujo.v_puntos = nuevosPuntos;

                            try {
                                Thread.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //panel_patron_inicial.panel_de_dibujo.v_puntos = oldPoints;

                            // tiling de no triangulos
                            if (v_puntos.size() != 4)
                            {
                                ejecutarLizard();
                            }
                            else // tiling de triangulos
                                if (v_puntos.size() == 4)
                                {
                                    ejecutarLizard();

                                    Vector nuevosPuntosTriang = new Vector();
                                    Vector v_puntosTriang = Constants.aplicarRotacion(v_puntos, 60, Panel_de_dibujo.TAM);

                                    for(int i=0; i<v_puntosTriang.size(); i++)
                                    {
                                        Point2D valPoint2D = ((Point2D) v_puntosTriang.get(i));

                                        double correccionX;// = -anchoFigura/2;
                                        double correccionY = -altoFigura*0.3333;

                                        switch (tipoDeTiling) {

                                            case Panel_resultado.TILING_CUADRADO:
                                                correccionX = -anchoFigura/2;
                                                //correccionY = -altoFigura*0.3333;

                                                break;
                                            case Panel_resultado.TILING_HEXAGONAL:
                                                //correccionY = -altoFigura*0.3333;
                                                if(columna%2==1)
                                                {
                                                    correccionX = -anchoFigura/2;
                                                } else
                                                {
                                                    correccionX = -anchoFigura;
                                                }

                                                break;

                                            default:
                                                throw new IllegalArgumentException("Invalid tipoDeTiling ");

                                        }

                                        Point2D newPoint2D = new Point2D.Double(
                                                valPoint2D.getX() - _xMin + fila * anchoFigura - correccionX,
                                                valPoint2D.getY() - _yMin + columna * altoFigura - correccionY
                                        );
                                        nuevosPuntosTriang.add(newPoint2D);
                                    }

                                    panel_resultado.panel_de_dibujo.v_puntos = nuevosPuntosTriang;

                                    if (panel_resultado.panel_de_dibujo.v_puntos.size() == 0)
                                    {
                                        System.out.println("3 tiling: v_puntos.size() = " + v_puntos.size());
                                    }
                                    try {
                                        Thread.sleep(2);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    ejecutarLizard();
                                }

                            incrementarBarraDeProgresoTilingEnUno();
                        }


                    }
                }
            });
            hiloRutina1.start();

            (new Thread(new Runnable() {
                @Override
                public void run() {

                    while ( hiloRutina1.isAlive() || getCalculandoLizard() ) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        hiloRutina1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }



                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            //panel_patron_inicial.jl_zoom.setText(s_oldZoom);
                            //panel_patron_inicial.js_zoom.setValue(int_oldZoom);
                            panel_resultado.panel_de_dibujo.v_puntos = oldPoints;

                            panel_resultado.panel_de_dibujo.repaint();
                        }
                    });

                }})).start();
        */
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    long inicioParaCalcTimeTiling = -1;
    long finParaCalcTimeTiling = -1;
    long diferenciaParaCalcTimeTiling = -1;
    public void settearBarraDeProgresoTilingValoresIniciales(int maximo)
    {
        //// esto es para settear la barra de progreso VALORES INICIALES
        panel_resultado.progresoTiling.setMinimum(0);
        panel_resultado.progresoTiling.setMaximum(maximo);
        panel_resultado.progresoTiling.setValue(0);
        ////

        inicioParaCalcTimeTiling = -1;
        finParaCalcTimeTiling = -1;
        diferenciaParaCalcTimeTiling = -1;
    }

    public void incrementarBarraDeProgresoTilingEnUno()
    {
        int valIni = panel_resultado.progresoTiling.getValue();
        panel_resultado.progresoTiling.setValue(valIni+1);

        // para setear el tiempo que falta
        if(inicioParaCalcTimeTiling == -1)
        {
            inicioParaCalcTimeTiling = System.nanoTime();
            return;
        }
        if(finParaCalcTimeTiling == -1)
        {
            finParaCalcTimeTiling = System.nanoTime();
            return;
        }
        if(diferenciaParaCalcTimeTiling == -1)
        {
            diferenciaParaCalcTimeTiling = finParaCalcTimeTiling - inicioParaCalcTimeTiling;
        }
        long actual = panel_resultado.progresoTiling.getValue();
        long maximo = panel_resultado.progresoTiling.getMaximum();
        BigInteger tiempoRestante = BigInteger.valueOf(((maximo-actual)*diferenciaParaCalcTimeTiling));

        BigInteger tiempoRestanteEnSeg = BigInteger.valueOf((long) (tiempoRestante.longValue()/Math.pow(10,6)) );

        long tiempoRestanteMin = (long)(tiempoRestanteEnSeg.longValue()/60);
        long tiempoRestanteSeg = (long)(tiempoRestanteEnSeg.longValue()%60);

        //panel_de_dibujo.tiempoRestanteRutina.setText("faltan "+tiempoRestanteMin+":"+tiempoRestanteSeg);
        panel_resultado.tiempoRestanteTiling.setText("faltan "+tiempoRestanteEnSeg.longValue());
    }

    public synchronized void ejecutarLizard()
    {
        calcular_lizard(panel_resultado.check_liveDrawing.isSelected());
    }



    private void setAllFalse(Panel_de_dibujo panel_de_dibujo)
    {
        panel_de_dibujo.mas_puntos=false;
        panel_de_dibujo.mover_puntos=false;
        panel_de_dibujo.borrar_puntos=false;
        panel_de_dibujo.borrar_todo=false;
    }
/*
    public String datos_para_guardar()
    {
        String s_salida="";

        Vector v_patron_12 = panel_patron_12.panel_de_dibujo.v_puntos;
        Vector v_patron_34 = panel_patron_34.panel_de_dibujo.v_puntos;
        Vector v_patron_56 = panel_patron_56.panel_de_dibujo.v_puntos;

        s_salida=s_salida+v_patron_12.size()+"_PATRON_12\n";

        for(int i=0;i<v_patron_12.size();i++)
        {
            Point2D p=(Point2D)v_patron_12.get(i);

            s_salida=s_salida+"X="+p.getX()+"_"+"Y="+p.getY()+"_\n";

        }

        s_salida=s_salida+v_patron_34.size()+"_PATRON_34\n";

        for(int i=0;i<v_patron_34.size();i++)
        {
            Point2D p=(Point2D)v_patron_34.get(i);

            s_salida=s_salida+"X="+p.getX()+"_"+"Y="+p.getY()+"_\n";

        }

        s_salida=s_salida+v_patron_56.size()+"_PATRON_34\n";

        for(int i=0;i<v_patron_56.size();i++)
        {
            Point2D p=(Point2D)v_patron_56.get(i);

            s_salida=s_salida+"X="+p.getX()+"_"+"Y="+p.getY()+"_\n";

        }

        return s_salida;
    }
*/
    public void repaintPaneles_patrones()
    {
        panel_patron_12.setSliderPOS_INI(panel_patron_12.js_POS_INI);
        panel_patron_34.setSliderPOS_INI(panel_patron_34.js_POS_INI);
        panel_patron_56.setSliderPOS_INI(panel_patron_56.js_POS_INI);

        panel_patron_12.panel_de_dibujo.repaint();
        panel_patron_34.panel_de_dibujo.repaint();
        panel_patron_56.panel_de_dibujo.repaint();
    }

    public boolean detenerRutina1 = false;
    ////
    //boolean isGradiente = true;
    String tipo;
    Color[] _coloresGradientes = {Color.WHITE, Color.BLACK};
    //Color colorIni = Color.WHITE;
    //Color colorFin = Color.BLACK;
    //int nroIteraciones = 50;
    int nroDeGradientes = 3;//(int)(panel_patron_inicial.js_VAL_MAX/nroIteraciones);
    int nroLineas = -1;
    int nivelDeRecursividad = 2;
    int porcentajeZoomSalto = 3;
    int porcentajeZoomMax = 100;
    boolean isTilingHexagonal = false;
    String tipoStroke = JOptionFactory.NORMAL;

    //// rutina 1
    // tipo = GRADIENTE, GRADIENTE_ALEATORIO, FIJO,
    public synchronized void ejecutarRutina1(String tipo,
                                             Color[] coloresGradiente,
                                             int nroLineas,
            /*int nroIteraciones,*/
                                             int nroDeGradientes,
                                             int nivelDeRecursividad,
                                             int porcentajeZoomSalto,
                                             int porcentajeZoomMax,
                                             boolean isTilingHexagonal,
                                             String tipoStroke
    )
    {
        Constants.RutinaActual = Constants.RUTINA_1;

        this.tipo = tipo;
        _coloresGradientes = coloresGradiente;
        this.nroLineas = nroLineas;
        this.nroDeGradientes = nroDeGradientes;
        this.nivelDeRecursividad = nivelDeRecursividad;
        this.porcentajeZoomSalto = porcentajeZoomSalto;
        this.porcentajeZoomMax = porcentajeZoomMax;
        this.isTilingHexagonal = isTilingHexagonal;
        this.tipoStroke = tipoStroke;

        detenerRutina1 = false;

        Thread hiloRutina1 = new Thread(this);
        hiloRutina1.start();
    }

    @Override
    public void run()
    {
        if(Constants.RutinaActual == Constants.RUTINA_1) {
            setCalculandoLizard(false);
            ejecutarRutina1_run();
        }
    }

    public synchronized void ejecutarRutina1_run()
    {

        int MAX_LINE_WIDTH = this.porcentajeZoomMax;
        int ZoomSalto = this.porcentajeZoomSalto;

        panel_resultado.jcb_nivel.setSelectedIndex(nivelDeRecursividad);

        if ( !getCalculandoLizard() )
        {
            calcular_lizard(false);
        }

        double nroIteraciones = MAX_LINE_WIDTH/(ZoomSalto*nroDeGradientes);
        double nroIteraciones_MAS_1 = nroIteraciones + 1;

        settearBarraDeProgresoValoresIniciales( (int)(nroIteraciones*nroDeGradientes) );

        for(int j=0;j<nroDeGradientes;j++)
        {
            //int MAX_LINE_WIDTH_tmp = MAX_LINE_WIDTH;

            double saltoR = -1;
            double saltoG = -1;
            double saltoB = -1;

            // transparente viene null del dialogo de colores
            if (_coloresGradientes[j] != null && _coloresGradientes[(j+1)%nroDeGradientes] != null) {
                saltoR = (-_coloresGradientes[j].getRed() + _coloresGradientes[(j + 1) % nroDeGradientes].getRed()) / nroIteraciones;
                saltoG = (-_coloresGradientes[j].getGreen() + _coloresGradientes[(j + 1) % nroDeGradientes].getGreen()) / nroIteraciones;
                saltoB = (-_coloresGradientes[j].getBlue() + _coloresGradientes[(j + 1) % nroDeGradientes].getBlue()) / nroIteraciones;
            }


            int i_cont_lineas = 0;
            int i_cont_colores_lineas = 0;

            double _AcumuladoColorR = 0;
            double _AcumuladoColorG = 0;
            double _AcumuladoColorB = 0;

            for(int i=0;i<=nroIteraciones_MAS_1;i++) {
                if (detenerRutina1) {
                    return;
                }

                Color colorTemp = null;

                if ("Gradiente Aleatorio".equalsIgnoreCase(tipo) || "Gradiente".equalsIgnoreCase(tipo)) {

                    int colorR;
                    int colorG;
                    int colorB;

                    // transparente viene null del dialogo de colores
                    if (_coloresGradientes[j] != null) {
                        //colorR = (int) Math.nextUp(coloresGradientes[j].getRed() + (double) saltoR * i);
                        //colorG = (int) Math.nextUp(coloresGradientes[j].getGreen() + (double) saltoG * i);
                        //colorB = (int) Math.nextUp(coloresGradientes[j].getBlue() + (double) saltoB * i);

                        //colorTemp = new Color(colorR, colorG, colorB);

                        _AcumuladoColorR = _AcumuladoColorR + saltoR;
                        _AcumuladoColorG = _AcumuladoColorG + saltoG;
                        _AcumuladoColorB = _AcumuladoColorB + saltoB;

//System.out.println(String.format("(%1s , %2s , %3s), salto=%4s",_AcumuladoColorR,_AcumuladoColorG,_AcumuladoColorB, saltoR));

                        colorR = (int) Math.floor(_coloresGradientes[j].getRed() + _AcumuladoColorR);
                        colorG = (int) Math.floor(_coloresGradientes[j].getGreen() + _AcumuladoColorG);
                        colorB = (int) Math.floor(_coloresGradientes[j].getBlue() + _AcumuladoColorB);

//System.out.println(String.format("(%1s , %2s , %3s) ",colorR,colorG,colorB));

                        colorR = colorR>255?255:colorR;
                        colorR = colorR<0?0:colorR;
                        colorG = colorG>255?255:colorG;
                        colorG = colorG<0?0:colorG;
                        colorB = colorB>255?255:colorB;
                        colorB = colorB<0?0:colorB;

                      colorTemp = new Color(colorR, colorG, colorB);
                    }

                } else
                    if ("Aleatorio".equalsIgnoreCase(tipo)) {
                        // random
                        colorTemp = new Color((int) (Math.random() * 255),
                                (int) (Math.random() * 255),
                                (int) (Math.random() * 255));

                    } else
                        if ("Fijo".equalsIgnoreCase(tipo)) {


                            // colores fijos deacuerdo al numerso de lineas
                            if (i_cont_lineas < nroLineas) {

                                i_cont_lineas++;
                            } else {
                                i_cont_lineas = 0;
                                i_cont_colores_lineas++;
                            }

                            // transparente viene null del dialogo de colores
                            if (_coloresGradientes[(i_cont_colores_lineas) % nroDeGradientes] != null) {
                                colorTemp = _coloresGradientes[(i_cont_colores_lineas) % nroDeGradientes];
                            }

                        } else {
                            colorTemp = null;
                        }


                MAX_LINE_WIDTH = MAX_LINE_WIDTH - ZoomSalto;
                float lineWidth = MAX_LINE_WIDTH - ZoomSalto;
                if (lineWidth<0) {
                    break;
                }
                Stroke result_stroke = null;
                switch (tipoStroke)
                {
                    case JOptionFactory.NORMAL:
                        result_stroke = new BasicStroke(
                                lineWidth,
                                BasicStroke.CAP_ROUND, //BasicStroke.CAP_SQUARE,
                                BasicStroke.JOIN_ROUND, //BasicStroke.JOIN_MITER,
                                Constants.InitialBasicStrokeMiterlimit,
                                Constants.InitialBasicStrokeDashArray,
                                Constants.InitialBasicStrokeDash_phase);
                        break;
                    case JOptionFactory.WOBBLE:
                        result_stroke = new WobbleStroke(lineWidth,0.2f, 1f);
                        break;
                    default:
                        break;
                }

                panel_resultado.panel_de_dibujo.setStroke(result_stroke);

                if (/*coloresGradientes[j] == null ||*/ colorTemp != null)
                {
                    // evento de seleccion de color de lineas
                    setColorLineas_Panel_resultado(colorTemp);

                    // to draw one fractal until the end before to begin to draw another one, when doing Rutina1
                    while (getCalculandoLizard()) {
                        try {
                            Thread.sleep(2);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    if (!isTilingHexagonal)
                    {
                        // evento del boton de calcular
                        calcular_lizard(false);
                    }
                    else
                    {

                        tiling(Panel_resultado.TILING_HEXAGONAL);
                    }
                }

                // incrementamos la barra de progreso de rutina en uno
                incrementarBarraDeProgresoEnUno();
            }
        }
    }

    public void settearBarraDeProgresoValoresIniciales(int maximo)
    {
        //// esto es para settear la barra de progreso VALORES INICIALES
        panel_resultado.progresoRutina.setMinimum(0);
        panel_resultado.progresoRutina.setMaximum(maximo);
        panel_resultado.progresoRutina.setValue(0);
        //System.out.println(this.getClass().getName()+":curvaKoch() maximo="+maximo);
        //System.out.println(this.getClass().getName()+":curvaKoch() elementos_UI.panel_de_dibujo.progresoDibujo.getMaximum()="+elementos_UI.panel_de_dibujo.progresoFractal.getMaximum());
        ////

        inicioParaCalcTime = -1;
        finParaCalcTime = -1;
        diferenciaParaCalcTime = -1;
    }

    long inicioParaCalcTime = -1;
    long finParaCalcTime = -1;
    long diferenciaParaCalcTime = -1;
    public void incrementarBarraDeProgresoEnUno()
    {
        //// para settear la barra de progreso
        int valIni = panel_resultado.progresoRutina.getValue();
        panel_resultado.progresoRutina.setValue(valIni+1);
        //System.out.println(this.getClass().getName()+":curvaKoch() elementos_UI.panel_de_dibujo.progresoDibujo.getValue()="+elementos_UI.panel_de_dibujo.progresoFractal.getValue());
        //System.out.println(this.getClass().getName()+":curvaKoch() valIni="+valIni);
        ////

        // para setear el tiempo que falta
        if(inicioParaCalcTime == -1)
        {
            inicioParaCalcTime = System.nanoTime();
            return;
        }
        if(finParaCalcTime == -1)
        {
            finParaCalcTime = System.nanoTime();
            return;
        }
        if(diferenciaParaCalcTime == -1)
        {
            diferenciaParaCalcTime = finParaCalcTime - inicioParaCalcTime;
        }
        long actual = panel_resultado.progresoRutina.getValue();
        long maximo = panel_resultado.progresoRutina.getMaximum();
        BigInteger tiempoRestante = BigInteger.valueOf(((maximo-actual)*diferenciaParaCalcTime));

        BigInteger tiempoRestanteEnSeg = BigInteger.valueOf((long) (tiempoRestante.longValue()/Math.pow(10,6)) );

        long tiempoRestanteMin = (tiempoRestanteEnSeg.longValue() / 60);
        long tiempoRestanteSeg = (tiempoRestanteEnSeg.longValue() % 60);

        //panel_de_dibujo.tiempoRestanteRutina.setText("faltan "+tiempoRestanteMin+":"+tiempoRestanteSeg);
        panel_resultado.tiempoRestanteRutina.setText("faltan "+tiempoRestanteEnSeg.longValue());
    }
}
