package com.cc.tilingLizard.paneles.panel_resultado;

import com.cc.tilingLizard.eventos.Eventos;
import com.cc.tilingLizard.paneles.Elementos_UI;
import com.cc.tilingLizard.paneles.Panel_calculable;
import com.cc.tilingLizard.utils.Constants;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Vector;

public class Panel_resultado extends JPanel implements Panel_calculable {

    JScrollPane scroll_panel_de_controles;
    JInternalFrame panel_de_controles;
    public Panel_de_dibujo_resultado panel_de_dibujo;

    public JTextArea jta_nombre_del_modelo;
    public JTextArea jta_estado;

    public JComboBox jcb_nivel;

    public JButton color_lineas;
    public JButton stroke_lineas;
    public JButton color_fondo;

    public JProgressBar progresoFractal;
    public JLabel tiempoRestanteFractal;

    public JProgressBar progresoRutina;
    public JLabel tiempoRestanteRutina;

    public JProgressBar progresoTiling;
    public JLabel tiempoRestanteTiling;

    public JCheckBox check_liveDrawing;
    public JCheckBox check_dibujarPrimeraLineaComoShape;
    public JCheckBox check_no_dibujarPrimeraLineaPatronRecursivo;

    public JButton jb_tilig_cuadrado;
    public JButton jb_tilig_hexagonal;

    Elementos_UI elementos_UI;

    //Tabla tabla;
    boolean datos_por_defecto=true;
    //String signos[];

    public Vector v_puntos=new Vector();

    public static String DETENER = "Stop/Detener";
    public static String CALCULAR = "Calcular";
    public static String CONTINUAR = "continuar";
    public static String CLEAR = "Clear Screen";
    public static String COLOR_LINEAS = "Lineas Color";
    public static String STROKE_LINEAS = "Stroke";
    public static String COLOR_FONDO = "Fondo Color";

    public static String LIVE_DRAWING = "Live Drawing";
    public static String DIBUJAR_PRIMERA_LINEA_RECURSIVA_COMO_SHAPE = "Prim rec.linea Shape";
    public static String NO_DIBUJAR_PRIMERAS_LINEAS_PATRON_RECURSIVO = "No 1ra-ulti linea PR";

    public static final String TILING_CUADRADO = "Tiling Cuadrado";
    public static final String TILING_HEXAGONAL = "Tiling Hexagonal";

    public static final String SIN_TILING = "Sin Tiling";

    public Panel_resultado()
    {
        elementos_UI = Elementos_UI.getInstance();

        panel_grafico();
    }

    public void panel_grafico()
    {
        createUI("Sin_nombre");
    }

    public void createUI(String nombre_del_modelo)
    {
        this.setLayout(new BorderLayout());


        //Dimension Size = elementos_UI.jf_principal.getSize();;

        //int ancho=Size.width;
        //int alto=Size.height;

        //setSize(ancho,alto);

        panel_de_controles=new JInternalFrame("panel_de_controles");
        //panel_de_controles.setLayout(new BorderLayout());
        panel_de_controles.setLayout( new BoxLayout( panel_de_controles.getContentPane(), BoxLayout.Y_AXIS));
        //panel_de_controles.setLayout(new FlowLayout(FlowLayout.LEFT));
        //panel_de_controles.setBounds(5,5,(int)(ancho/4-10),(int)(alto-100));
        panel_de_controles.setVisible(true);

        scroll_panel_de_controles = new JScrollPane();
        panel_de_controles.add(scroll_panel_de_controles);

        JPanel _panel_de_controles=new JPanel();
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        _panel_de_controles.setPreferredSize(
                                new Dimension(Constants.dimensionPrefPanel_de_controles_width,
                                        _panel_de_controles.getHeight()));
                    }
                }
        );

        _panel_de_controles.setLayout( new BoxLayout( _panel_de_controles, BoxLayout.Y_AXIS));
        scroll_panel_de_controles.setViewportView(_panel_de_controles);

        JPanel panelTopNombreEstadoNivel = new JPanel();
        //panelTopNombreEstadoNivel.setLayout( new BoxLayout( panelTopNombreEstadoNivel, BoxLayout.Y_AXIS));
        panelTopNombreEstadoNivel.setLayout( new GridLayout(3,1));

        JPanel panelNombreModelo = new JPanel();
        TitledBorder titleNombreModelo;
        titleNombreModelo = BorderFactory.createTitledBorder("Nombre del modelo");
        panelNombreModelo.setBorder(titleNombreModelo);
        panelNombreModelo.setLayout(new BorderLayout());
        //JLabel L_0=new JLabel("Nombre del modelo");
        jta_nombre_del_modelo=new JTextArea(nombre_del_modelo);

        //panelNombreModelo.add(L_0, BorderLayout.WEST);
        panelNombreModelo.add(jta_nombre_del_modelo, BorderLayout.CENTER);
        //panel_de_controles.add(panelNombreModelo);
        panelTopNombreEstadoNivel.add(panelNombreModelo);

        JPanel panelEstado = new JPanel();
        TitledBorder titleEstado;
        titleEstado = BorderFactory.createTitledBorder("Estado");
        panelEstado.setBorder(titleEstado);
        panelEstado.setLayout(new BorderLayout());
        //JLabel L_1=new JLabel("Estado");
        //L_1.setBounds(10,60,200,10);
        jta_estado=new JTextArea("***");
        //jta_estado.setBounds(10,80,150,20);

        //panelEstado.add(L_1, BorderLayout.WEST);
        panelEstado.add(jta_estado, BorderLayout.CENTER);
        //panel_de_controles.add(panelEstado);
        panelTopNombreEstadoNivel.add(panelEstado);

////////////////////////////////////////////////////////////////////////////////

        String elementos[]=new String[8];
        for(int i=0; i<elementos.length ; i++ )
            elementos[i]=""+i;

        JPanel panelNivel = new JPanel();
        TitledBorder titleNivel;
        titleNivel = BorderFactory.createTitledBorder("nivel de recursividad");
        panelNivel.setBorder(titleNivel);
        panelNivel.setLayout(new BorderLayout());
        //JLabel L_2=new JLabel("nivel de recursividad");
        //L_2.setBounds(20,120,200,30);
        jcb_nivel = new JComboBox(elementos);
        jcb_nivel.setSelectedIndex(1);
        jcb_nivel.addActionListener(new Eventos(null));
        //jcb_nivel.setBounds(20,160,100,20);

        //panelNivel.add(L_2, BorderLayout.WEST);
        panelNivel.add(jcb_nivel, BorderLayout.CENTER);
        //panel_de_controles.add(panelNivel);
        panelTopNombreEstadoNivel.add(panelNivel);


        _panel_de_controles.add(panelTopNombreEstadoNivel);
////////////////////////////////////////////////////////////////////////////////

        JPanel panelBotones = new JPanel();
        TitledBorder titleBotones;
        titleBotones = BorderFactory.createTitledBorder("Acciones");
        panelBotones.setBorder(titleBotones);
        //panelBotones.setBackground(Color.RED);
        //panelBotones.setLayout(new BorderLayout());
        //panelBotones.setLayout( new BoxLayout( panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setLayout( new GridLayout(7,1));
        //panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        JButton calcular=new JButton(CALCULAR);
        //calcular.setBounds(10,220,150,40);
        calcular.addMouseListener(new Eventos(null));
        panelBotones.add(calcular);

        JButton detener=new JButton(DETENER);
        //detener.setBounds(10,270,150,40);
        detener.addMouseListener(new Eventos(null));
        panelBotones.add(detener);

        //JButton continuar=new JButton(CONTINUAR);
        //detener.setBounds(10,270,150,40);
        //continuar.addMouseListener(new Eventos(null, elementos_UI));
        //panelBotones.add(continuar, BorderLayout.SOUTH);

        JButton clear=new JButton(CLEAR);
        //detener.setBounds(10,270,150,40);
        clear.addMouseListener(new Eventos(null));
        panelBotones.add(clear);

        //JPanel p_colores_lineas = new JPanel();
        //p_colores_lineas.setLayout( new GridLayout(3,1));
        //p_colores.setLayout(new BoxLayout(p_colores, BoxLayout.Y_AXIS));

        color_lineas=new JButton(COLOR_LINEAS);
        color_lineas.addMouseListener(new Eventos(null));
        panelBotones.add(color_lineas);
        stroke_lineas=new JButton(STROKE_LINEAS);
        stroke_lineas.addMouseListener(new Eventos(null));
        panelBotones.add(stroke_lineas);

        color_fondo=new JButton(COLOR_FONDO);
        color_fondo.addMouseListener(new Eventos(null));
        panelBotones.add(color_fondo);

        //panelBotones.add(p_colores_lineas);

        //panel_de_controles.add(panelBotones);
        _panel_de_controles.add(panelBotones);
        _panel_de_controles.setBackground(Color.yellow);

        JPanel panelProgreso = new JPanel();
        TitledBorder titleProgreso;
        titleProgreso = BorderFactory.createTitledBorder("progreso fractal");
        panelProgreso.setBorder(titleProgreso);
        panelProgreso.setLayout( new GridLayout(2,1));

        progresoFractal = new JProgressBar();
        progresoFractal.setMinimum(0);
        progresoFractal.setMaximum(100);
        progresoFractal.setStringPainted(true);
        panelProgreso.add(progresoFractal);

        tiempoRestanteFractal = new JLabel("");
        panelProgreso.add(tiempoRestanteFractal);

        //panel_de_controles.add(panelProgreso);
        _panel_de_controles.add(panelProgreso);

        JPanel panelProgresoRutina = new JPanel();
        TitledBorder titleProgresoRutina;
        titleProgresoRutina = BorderFactory.createTitledBorder("progreso rutina");
        panelProgresoRutina.setBorder(titleProgresoRutina);
        panelProgresoRutina.setLayout( new GridLayout(2,1));

        progresoRutina = new JProgressBar();
        progresoRutina.setMinimum(0);
        progresoRutina.setMaximum(100);
        progresoRutina.setStringPainted(true);
        panelProgresoRutina.add(progresoRutina);

        tiempoRestanteRutina = new JLabel("");
        panelProgresoRutina.add(tiempoRestanteRutina);

        //panel_de_controles.add(panelProgresoRutina);
        _panel_de_controles.add(panelProgresoRutina);

        /****/
        JPanel panelProgresoTiling = new JPanel();
        TitledBorder titleProgresoTiling;
        titleProgresoTiling = BorderFactory.createTitledBorder("progreso tiling");
        panelProgresoTiling.setBorder(titleProgresoTiling);
        panelProgresoTiling.setLayout( new GridLayout(2,1));

        progresoTiling = new JProgressBar();
        progresoTiling.setMinimum(0);
        progresoTiling.setMaximum(100);
        progresoTiling.setStringPainted(true);
        panelProgresoTiling.add(progresoTiling);

        tiempoRestanteTiling = new JLabel("");
        panelProgresoTiling.add(tiempoRestanteTiling);

        //panel_de_controles.add(panelProgresoRutina);
        _panel_de_controles.add(panelProgresoTiling);
        /****/

        JPanel panelLiveDrawingYOtros = new JPanel();
        panelLiveDrawingYOtros.setLayout( new GridLayout(5,1));
        TitledBorder titleLiveDrawing;
        titleLiveDrawing = BorderFactory.createTitledBorder(LIVE_DRAWING);
        panelLiveDrawingYOtros.setBorder(titleLiveDrawing);
        check_liveDrawing = new JCheckBox(LIVE_DRAWING);
        check_liveDrawing.setSelected(false);
        panelLiveDrawingYOtros.add(check_liveDrawing);

        check_dibujarPrimeraLineaComoShape = new JCheckBox(DIBUJAR_PRIMERA_LINEA_RECURSIVA_COMO_SHAPE);
        check_dibujarPrimeraLineaComoShape.setSelected(false);
        panelLiveDrawingYOtros.add(check_dibujarPrimeraLineaComoShape);
        check_no_dibujarPrimeraLineaPatronRecursivo = new JCheckBox(NO_DIBUJAR_PRIMERAS_LINEAS_PATRON_RECURSIVO);
        check_no_dibujarPrimeraLineaPatronRecursivo.setSelected(false);
        panelLiveDrawingYOtros.add(check_no_dibujarPrimeraLineaPatronRecursivo);

        jb_tilig_cuadrado = new JButton(TILING_CUADRADO);
        jb_tilig_cuadrado.addMouseListener(new Eventos(null));
        panelLiveDrawingYOtros.add(jb_tilig_cuadrado);
        jb_tilig_hexagonal = new JButton(TILING_HEXAGONAL);
        jb_tilig_hexagonal.addMouseListener(new Eventos(null));
        panelLiveDrawingYOtros.add(jb_tilig_hexagonal);

        _panel_de_controles.add(panelLiveDrawingYOtros);

        //panel_de_dibujo=new JInternalFrame("panel_de_dibujo");
        panel_de_dibujo=new Panel_de_dibujo_resultado();
        SwingUtilities.invokeLater(() ->
                panel_de_dibujo.v_puntos = Constants.calcularEneagono1(
                        panel_de_dibujo.getWidth()/2,
                        panel_de_dibujo.getHeight()/2,
                        6,
                        100,
                        1
                )
            );

        //panel_de_dibujo.setLayout(null);
        //panel_de_dibujo.setBounds(5+(int)(ancho/4),5,(int)(ancho*3/4-40),(int)(alto-100) );
        //panel_de_dibujo.addMouseListener(new Eventos_I(elementos_UI));
        //panel_de_dibujo.addMouseMotionListener(new Eventos_I(elementos_UI));
        //panel_de_dibujo.setBackground(Color.red);

        panel_de_dibujo.setStroke(Constants.getInitialBasicStroke());

        panel_de_dibujo.setVisible(true);

        ////
        //tabla=new Tabla(  this , 0 );//numero_de_filas
        ////

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(panel_de_controles);
        splitPane.setRightComponent(panel_de_dibujo);
        splitPane.setDividerLocation(Constants.splitDividerLoation);
        //	.setDividerLocation(0.2);

        //add(panel_de_controles);
        //add(panel_de_dibujo);
        this.add(splitPane, BorderLayout.CENTER);
    }

    //public boolean mas_puntos=false;
    //public boolean mover_puntos=false;
    //public boolean borrar_puntos=false;
    //public boolean borrar_todo=false;

    //Point2D mSelectedPoint;
    //int i_mSelectedPoint;


    public void set_nombre_del_modelo( String s )
    {
        jta_nombre_del_modelo.setText(s);
    }
    public String get_nombre_del_modelo()
    {
        return jta_nombre_del_modelo.getText();
    }

    @Override
    public void calcularEstrella(int nroPuntas, int lado, int salto, int sentidoDeAgregado)
    {
        panel_de_dibujo.calcularEstrella(nroPuntas, lado, salto, sentidoDeAgregado);
    }

    @Override
    public void calcularEstrella1(int nroPuntas, int lado, int salto, int sentidoDeAgregado)
    {
        panel_de_dibujo.calcularEstrella1(nroPuntas, lado, salto, sentidoDeAgregado);
    }

    public void calcularEneagono(int nroPuntas, int lado, int salto, int sentidoDeAgregado, boolean agregarAlFinal)
    {
        panel_de_dibujo.calcularEneagono(nroPuntas, lado, salto, sentidoDeAgregado, agregarAlFinal);
        panel_de_dibujo.repaint();
    }

    public void calcularEneagono1(int nroPuntas, int lado, int salto, int sentidoDeAgregado, boolean agregarAlFinal)
    {
        panel_de_dibujo.calcularEneagono1(nroPuntas, lado, salto, sentidoDeAgregado, agregarAlFinal);
        panel_de_dibujo.repaint();
    }

/*
	public String[] get_signos()
{
		return tabla.get_signos();
}
*/

}


