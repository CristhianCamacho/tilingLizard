package com.cc.tilingLizard.paneles.panel_disenio;

import com.cc.tilingLizard.eventos.*;
import com.cc.tilingLizard.paneles.Elementos_UI;
import com.cc.tilingLizard.paneles.Panel_calculable;
import com.cc.tilingLizard.utils.Constants;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class Panel_patron_disenio extends JPanel implements Panel_calculable {

    JScrollPane scroll_panel_de_controles;
    JInternalFrame panel_de_controles;
    public Panel_de_dibujo panel_de_dibujo;

    public JTextArea jta_nombre_del_modelo;
    public JTextArea jta_estado;
    public JCheckBox check_pintarEjes;
    public JButton b_color_lineas;

    public JCheckBox check_previo;
    public JTextField jf_ultima_distancia;
    public JCheckBox check_siguiente;

    public JCheckBox mover_puntos;

    //public JTextArea jta_zoom;
    //public JComboBox jcb_zoom;
    public JSlider js_zoom;
    int js_VAL_MIN =0;
    public int totalZoom = 100;
    // al cambiar 'p_ini' aqui hacemos que el zoom maximo sea
    // 3 --> 200%
    // 4 --> 300%
    // 5 --> 400% = 100*(5-1) : punto_inicial_el_100%
    // porque la posicion inicial es el 100%
    int p_ini = 20;//6-->500
    public int js_POS_INI =totalZoom+js_VAL_MIN;
    public int js_VAL_MAX =totalZoom*(p_ini-1)+js_VAL_MIN;

    //public JLabel jl_zoom;
    public JTextField jl_zoom;

    // seccion de rotacion
    public JSlider js_rotar;
    public JTextField jl_rotar;
    public int js_POS_INI_ROTACION =0;
    public int js_VAL_MAX_ROTACION =2*360;
    int p_ini_rotacion = js_POS_INI_ROTACION;

    public static String PUNTOS = "puntos";
    public static String DISTANCIAS = "distancias";
    public static String ANGULOS_EJE_X = "angulos eje X";
    public static String ANGULOS_ENTRE_LINEAS = "angulos entre lineas";

    public Panel_patron_disenio()
    {
        panel_grafico();
    }

    public void panel_grafico()
    {
        createUI("Sin_nombre");
    }

    public void createUI(String nombre_del_modelo)
    {
        this.setLayout(new BorderLayout());

        panel_de_controles=new JInternalFrame("panel_de_controles");
        panel_de_controles.setLayout( new BoxLayout( panel_de_controles.getContentPane(), BoxLayout.Y_AXIS));
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

        JPanel panelNombreModelo = new JPanel();
        TitledBorder titleNombreModelo;
        titleNombreModelo = BorderFactory.createTitledBorder("Nombre del modelo");
        panelNombreModelo.setBorder(titleNombreModelo);
        panelNombreModelo.setLayout(new BorderLayout());
        //JLabel L_0=new JLabel("Nombre del modelo");
        //L_0.setBounds(10,10,200,10);
        jta_nombre_del_modelo=new JTextArea(nombre_del_modelo);
        //jta_nombre_del_modelo.setBounds(10,30,150,20);

        //panel_de_controles.add(L_0);
        panelNombreModelo.add(jta_nombre_del_modelo, BorderLayout.CENTER);
        _panel_de_controles.add(panelNombreModelo);

        JPanel panelEstado = new JPanel();
        TitledBorder titleEstado;
        titleEstado = BorderFactory.createTitledBorder("Estado");
        panelEstado.setBorder(titleEstado);
        panelEstado.setLayout(new BorderLayout());
        //JLabel L_1=new JLabel("Estado");
        //L_1.setBounds(10,10+20+20+10,200,10);
        jta_estado=new JTextArea("???");
        //jta_estado.setBounds(10,30+20*2+10,150,20);

        //panel_de_controles.add(L_1);
        //panel_de_controles.add(jta_estado);
        panelEstado.add(jta_estado, BorderLayout.CENTER);

        check_pintarEjes = new JCheckBox("Ejes");
        check_pintarEjes.setSelected(false);
        //check_pintarEjes.addMouseListener(new Eventos(this, elementos_UI));
        check_pintarEjes.addActionListener(new ActionListener()
                                           {
                                               public void actionPerformed(ActionEvent arg0) {
                                                   panel_de_dibujo.pintar_ejes = check_pintarEjes.isSelected();
                                                   panel_de_dibujo.repaint();
                                               }
                                           }
        );
        panelEstado.add(check_pintarEjes, BorderLayout.EAST);

        _panel_de_controles.add(panelEstado);

        b_color_lineas = new JButton("color lineas");
        b_color_lineas.addActionListener(new ActionListener()
                                         {
                                             public void actionPerformed(ActionEvent arg0) {
                                                 Color color1 = JColorChooser.showDialog( null, "Seleccione un color para la Lineas", panel_de_dibujo.getColorLineas() );
                                                 System.out.println(this.getClass().getName()+":color lineas = "+color1);
                                                 panel_de_dibujo.setColorLineas(color1);
                                                 panel_de_dibujo.repaint();

                                                 b_color_lineas.setBackground(color1);
                                                 Color colorOpuesto = new Color((255-color1.getRed()),
                                                         (255-color1.getGreen()),
                                                         (255-color1.getBlue()));
                                                 b_color_lineas.setForeground(colorOpuesto);
                                             }
                                         }
        );
        _panel_de_controles.add(b_color_lineas);

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

        JPanel panelBotones = new JPanel();
        TitledBorder titleBotones;
        titleBotones = BorderFactory.createTitledBorder("Estado de los Puntos");
        panelBotones.setBorder(titleBotones);
        panelBotones.setLayout(new GridLayout(3,1) );

        // 1
        JPanel panelColocarPuntos = new JPanel();
        TitledBorder titleColocarPuntos;
        titleColocarPuntos = BorderFactory.createTitledBorder("Colocar Puntos");
        panelColocarPuntos.setBorder(titleColocarPuntos);
        panelColocarPuntos.setLayout(new GridLayout(2,1) );

        JButton colocar_puntos=new JButton(Constants.COLOCAR_PUNTOS);
        colocar_puntos.addMouseListener(new Eventos(this));
        panelColocarPuntos.add(colocar_puntos);

        String elementosSentido[] =
                {
                        Constants.AL_INICIO,
                        Constants.AL_FINAL
                };
        JComboBox jcb_sentido = new JComboBox(elementosSentido);
        jcb_sentido.setSelectedIndex(1);
        jcb_sentido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Elementos_UI.getInstance().sentidoColocadoDePuntos = ((String)jcb_sentido.getItemAt(jcb_sentido.getSelectedIndex()));
            }
        });
        panelColocarPuntos.add(jcb_sentido);

        panelBotones.add(panelColocarPuntos);

        // 2
        JPanel panelMoverBorrarPuntos = new JPanel();
        TitledBorder titleMoverBorrarPuntos;
        titleMoverBorrarPuntos = BorderFactory.createTitledBorder("Puntos Borrar o Mover");
        panelMoverBorrarPuntos.setBorder(titleMoverBorrarPuntos);
        panelMoverBorrarPuntos.setLayout(new GridLayout(2,1) );

        /*
        JButton mover_puntos=new JButton("mover_puntos");
        mover_puntos.addMouseListener(new Eventos(this, elementos_UI));
        panelMoverBorrarPuntos.add(mover_puntos);
        */
        mover_puntos = new JCheckBox(Constants.MOVER_PUNTOS);
        mover_puntos.setSelected(false);
        mover_puntos.addMouseListener(new Eventos(this));
        mover_puntos.addActionListener(new ActionListener()
                                       {
                                           public void actionPerformed(ActionEvent arg0) {
                                               if(mover_puntos.isSelected() == false)
                                               {
                                                   panel_de_dibujo.mSelectedPoint = null;
                                                   panel_de_dibujo.i_mSelectedPoint = -1;

                                                   panel_de_dibujo.mListSelectedPoints = new LinkedList<>();
                                                   panel_de_dibujo.index_ListSelectedPoints = new LinkedList<>();
                                               }
                                           }
                                       }
        );

        panelMoverBorrarPuntos.add(mover_puntos);

        JButton borrar_puntos=new JButton(Constants.BORRAR_PUNTOS);
        borrar_puntos.addMouseListener(new Eventos(this));
        panelMoverBorrarPuntos.add(borrar_puntos);

        panelBotones.add(panelMoverBorrarPuntos);

        // 3
        JPanel panelBotonBorrarTodo = new JPanel();
        TitledBorder titleBotonBorrarTodo;
        titleBotonBorrarTodo = BorderFactory.createTitledBorder("Borrar Todo");
        panelBotonBorrarTodo.setBorder(titleBotonBorrarTodo);
        panelBotonBorrarTodo.setLayout(new GridLayout(2,1) );

        JButton borrar_todo=new JButton(Constants.BORRAR_TODO);
        borrar_todo.addMouseListener(new Eventos(this));
        panelBotonBorrarTodo.add(borrar_todo);

        JPanel panelUltimaDistancia = new JPanel();
        panelUltimaDistancia.setLayout(new GridLayout(1,2) );
        panelUltimaDistancia.add(new JLabel(Constants.ULTIMA_DISTANCIA));
        jf_ultima_distancia = new JTextField();
        panelUltimaDistancia.add(jf_ultima_distancia);
        jf_ultima_distancia.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    double newDistance = Double.parseDouble(jf_ultima_distancia.getText());
                    panel_de_dibujo.calcularNewUltimaDistancia(newDistance, check_siguiente.isSelected());
                    panel_de_dibujo.requestFocus();
                    panel_de_dibujo.repaint();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        JPanel panelChecksUltimaDistancia = new JPanel();
        panelChecksUltimaDistancia.setLayout(new GridLayout(1,2) );

        check_previo = new JCheckBox("");
        check_previo.setSelected(false);
        check_previo.addActionListener(new ActionListener()
                                       {
                                           public void actionPerformed(ActionEvent arg0) {
                                               check_siguiente.setSelected(!check_previo.isSelected());
                                               jf_ultima_distancia.setText( ""+panel_de_dibujo.getNewUltimaDistancia(check_siguiente.isSelected()) );
                                           }
                                       }
        );
        panelChecksUltimaDistancia.add(check_previo);

        check_siguiente = new JCheckBox("");
        check_siguiente.setSelected(true);
        check_siguiente.addActionListener(new ActionListener()
                                          {
                                              public void actionPerformed(ActionEvent arg0) {
                                                  check_previo.setSelected(!check_siguiente.isSelected());
                                                  jf_ultima_distancia.setText( ""+panel_de_dibujo.getNewUltimaDistancia(check_siguiente.isSelected()) );
                                              }
                                          }
        );
        panelChecksUltimaDistancia.add(check_siguiente);

        panelUltimaDistancia.add(panelChecksUltimaDistancia);

        panelBotonBorrarTodo.add(panelUltimaDistancia);

        panelBotones.add(panelBotonBorrarTodo);

        _panel_de_controles.add(panelBotones);

        //// panel para el zoom
        JPanel panelZoom = new JPanel();
        TitledBorder titleZoom;
        titleZoom = BorderFactory.createTitledBorder("Zoom %");
        panelZoom.setBorder(titleZoom);
        panelZoom.setLayout(new BorderLayout() );

	/* si fuera un combo
	String elementos[]= {"10","20","30","40","50","60","70","80","90",
						 "100","120","150","180","200","250","300",
						 "350","400","450","500","550","600","650"};
	jcb_zoom = new JComboBox(elementos);
	jcb_zoom.setSelectedIndex(9);
	jcb_zoom.addActionListener(new Eventos(this, elementos_UI));
	panelZoom.add(jcb_zoom, BorderLayout.CENTER);
	*/
        js_zoom = new JSlider( SwingConstants.HORIZONTAL, js_VAL_MIN,
                js_VAL_MAX,
                js_POS_INI );
        js_zoom.setPaintTicks( true );
        js_zoom.setMajorTickSpacing( (int)((js_VAL_MAX-js_VAL_MIN)/2) );
        js_zoom.setMinorTickSpacing( (int)((js_VAL_MAX-js_VAL_MIN)/4) );
        js_zoom.setPaintLabels(true);

        panelZoom.add(js_zoom, BorderLayout.CENTER);

        // registrar componente de escucha de eventos de JSlider
        js_zoom.addChangeListener(new Eventos(this));

        jl_zoom = new JTextField(""+/*js_POS_INI*/js_zoom.getValue()/*+"%"*/);
        //jl_zoom.setText(""+js_POS_INI);
        jl_zoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                js_zoom.setValue(Integer.parseInt(jl_zoom.getText()));
            }
        });
        panelZoom.add(jl_zoom, BorderLayout.SOUTH);

        _panel_de_controles.add(panelZoom);


        JPanel panelRotar = new JPanel();
        TitledBorder titleRotar;
        titleRotar = BorderFactory.createTitledBorder("Rotar Grados");
        panelRotar.setBorder(titleRotar);
        panelRotar.setLayout(new BorderLayout());

        js_rotar = new JSlider( SwingConstants.HORIZONTAL,
                js_POS_INI_ROTACION,//0,
                js_VAL_MAX_ROTACION,//360,
                p_ini_rotacion/*0*/ );
        js_rotar.setPaintTicks( true );
        js_rotar.setMajorTickSpacing( 90 );
        js_rotar.setMinorTickSpacing( 10 );
        js_rotar.setPaintLabels(true);

        panelRotar.add(js_rotar, BorderLayout.CENTER);

        js_rotar.addChangeListener(new Eventos(this));

        jl_rotar = new JTextField(""+js_rotar.getValue());
        jl_rotar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                js_rotar.setValue(Integer.parseInt(jl_rotar.getText()));
            }
        });
        panelRotar.add(jl_rotar, BorderLayout.SOUTH);

        _panel_de_controles.add(panelRotar);

        /////

        ///// panel para ocultar o mostrar los puntos
        JPanel panelPuntosDeControl = new JPanel();
        TitledBorder titlePuntosDeControl;
        titlePuntosDeControl = BorderFactory.createTitledBorder("Ocultar los puntos de control");
        panelPuntosDeControl.setBorder(titlePuntosDeControl);
        panelPuntosDeControl.setLayout( new GridLayout(4, 1));

        // para ver los puntos de control
        JCheckBox jcb_mostrar_puntos_de_control = new JCheckBox(PUNTOS);
        jcb_mostrar_puntos_de_control.setSelected(true);
        jcb_mostrar_puntos_de_control.addMouseListener(new Eventos(this));
        panelPuntosDeControl.add(jcb_mostrar_puntos_de_control);

        // para ver las distancias entre puntos
        JCheckBox jcb_mostrar_distancias_entre_puntos = new JCheckBox(DISTANCIAS);
        jcb_mostrar_distancias_entre_puntos.setSelected(true);
        jcb_mostrar_distancias_entre_puntos.addMouseListener(new Eventos(this));
        panelPuntosDeControl.add(jcb_mostrar_distancias_entre_puntos);

        // para para ver los angulos con el eje X
        JCheckBox jcb_mostrar_angulos_con_el_eje_x = new JCheckBox(ANGULOS_EJE_X);
        jcb_mostrar_angulos_con_el_eje_x.setSelected(true);
        jcb_mostrar_angulos_con_el_eje_x.addMouseListener(new Eventos(this));
        panelPuntosDeControl.add(jcb_mostrar_angulos_con_el_eje_x);

        // para ver los angulos entre lineas
        JCheckBox jcb_mostrar_angulos_entre_lineas = new JCheckBox(ANGULOS_ENTRE_LINEAS);
        jcb_mostrar_angulos_entre_lineas.setSelected(true);
        jcb_mostrar_angulos_entre_lineas.addMouseListener(new Eventos(this));
        panelPuntosDeControl.add(jcb_mostrar_angulos_entre_lineas);


        _panel_de_controles.add(panelPuntosDeControl);

        panel_de_dibujo=new Panel_de_dibujo("panel_de_dibujo", this);
        panel_de_dibujo.addMouseListener(new Eventos_Panel_de_dibujo(this));
        panel_de_dibujo.addMouseMotionListener(new Eventos_Panel_de_dibujo(this));

        panel_de_dibujo.addMouseListener(new Evento_SeleccionDePuntos_Panel_de_dibujo(this));
        panel_de_dibujo.addMouseMotionListener(new Evento_SeleccionDePuntos_Panel_de_dibujo(this));

        panel_de_dibujo.setFocusable(true);
        panel_de_dibujo.addKeyListener(new RedoUndoKeyListener());
        panel_de_dibujo.addKeyListener(new EventosKeyListenerMoverTodosLosPuntos(this));
        panel_de_dibujo.setVisible(true);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        //splitPane.setLeftComponent(scroll_panel_de_controles);
        splitPane.setLeftComponent(panel_de_controles);
        //splitPane.setRightComponent(jif_panel_de_dibujo);
        splitPane.setRightComponent(panel_de_dibujo);
        splitPane.setDividerLocation(Constants.splitDividerLoation);

        //add(panel_de_controles);
        //add(panel_de_dibujo);
        this.add(splitPane, BorderLayout.CENTER);
    }




    public void set_nombre_del_modelo( String s )
    {
        jta_nombre_del_modelo.setText(s);
    }
    public String get_nombre_del_modelo()
    {
        return jta_nombre_del_modelo.getText();
    }
    public void pintar_puntos()
    {
        panel_de_dibujo.repaint();
    }

    public void dibujar_punto(double x, double y)
    {
        panel_de_dibujo.dibujar_punto(x,y);
    }

    public void mover_punto(double x, double y)
    {
        panel_de_dibujo.mover_punto(x,y);
    }

    public void actualizarUltimaDistancia(Point2D point2D1, Point2D point2D2, Point2D point2D0) {
        if (check_siguiente.isSelected()) {
            jf_ultima_distancia.setText(""+
                    (int)(point2D1).distance(point2D2));
        }
        else
        {
            jf_ultima_distancia.setText(""+
                    (int)(point2D0).distance(point2D1));
        }

    }

    public void mover_todos_los_puntos(double x, double y)
    {
        panel_de_dibujo.mover_todos_los_puntos(x,y);
    }

    public void mover_todos_los_puntos(int keyCode)
    {
        panel_de_dibujo.moverTodosLosPuntos(keyCode );
    }

    public void set_punto_inicial_puntos(Point2D point2D)
    {
        panel_de_dibujo.set_punto_inicial_puntos(point2D);
    }

    public boolean esta_este_punto_en_la_lista(double x, double y)
    {
        return panel_de_dibujo.esta_este_punto_en_la_lista(x,y);
    }

    public void aplicarZoom(String zoom)
    {
        panel_de_dibujo.aplicarZoom(zoom);
        jl_zoom.setText(zoom/*+"%"*/);
    }

    public void aplicarRotacion(String rotacion)
    {
        panel_de_dibujo.aplicarRotacion(rotacion);
        jl_rotar.setText(rotacion);
    }

    public void mostrarPuntosDeControl(boolean isSelected)
    {
        panel_de_dibujo.mostrarPuntosDeControl(isSelected);
    }

    public void mostrarDistancias(boolean isSelected)
    {
        panel_de_dibujo.mostrarDistancias(isSelected);
    }

    public void mostrarAngulosEjeX(boolean isSelected)
    {
        panel_de_dibujo.mostrarAngulosEjeX(isSelected);
    }

    public void mostrarAngulosEntreLineas(boolean isSelected)
    {
        panel_de_dibujo.mostrarAngulosEntreLineas(isSelected);
    }

    public void setSliderPOS_INI(int posInicial)
    {
        js_zoom.setValueIsAdjusting(true);
        js_zoom.setValue(posInicial);
        js_zoom.setValueIsAdjusting(false);
    }

    public void setSliderROTACION_INI(int posInicial)
    {
        js_rotar.setValueIsAdjusting(true);
        js_rotar.setValue(posInicial);
        js_rotar.setValueIsAdjusting(false);
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

    }

    public void calcularEneagono1(int nroPuntas, int lado, int salto, int sentidoDeAgregado, boolean agregarAlFinal)
    {
        panel_de_dibujo.calcularEneagono1(nroPuntas, lado, salto, sentidoDeAgregado, agregarAlFinal);

    }

}