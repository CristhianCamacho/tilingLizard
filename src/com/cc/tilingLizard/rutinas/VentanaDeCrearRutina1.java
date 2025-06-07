package com.cc.tilingLizard.rutinas;

import com.cc.tilingLizard.paneles.Elementos_UI;
import com.cc.tilingLizard.paneles.panel_resultado.Panel_resultado;
import com.cc.tilingLizard.paneles.panel_resultado.stroke.JOptionFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class VentanaDeCrearRutina1 extends JFrame{

    public static VentanaDeCrearRutina1 instance;

    Elementos_UI elementosUI;

    public String ALEATORIO = "Aleatorio";
    public String GRADIENTE = "Gradiente";
    public String FIJO = "Fijo";
    public String GRADIENTE_ALEATORIO = "Gradiente Aleatorio";

    public JComboBox jcb_tipoDeDibujoDeColores;

    JButton[] jb_botonesColoresGradientes = new JButton[2];
    public Color[] coloresGradientes = {Color.WHITE, Color.BLACK};
    JPanel p_botonesColoresGradiente;

    Color[] coloresAleatorios = {Color.WHITE,
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.ORANGE,
            Color.PINK,
            Color.RED,
            Color.YELLOW,
            Color.BLACK};

    public JComboBox jcb_numeroDeGradientes;
    public JComboBox jcb_numeroDeLineas;

    public JComboBox jcb_nivelDeRecursividad;
    public JComboBox jcb_porcentajeZoomSalto;
    public JComboBox jcb_porcentajeFinZoom;

    public JComboBox jcb_tipoStroke;

    JRadioButton jrb_sinTiling;
    //JRadioButton jrb_tilingCuadrado;
    JRadioButton jrb_tilingHexagonal;

    private VentanaDeCrearRutina1()
    {
        super("Ventana para Crear Rutina");
        //initialize();
    }

    public static VentanaDeCrearRutina1 getInstance()
    {
        if(instance == null)
        {
            instance = new VentanaDeCrearRutina1();
        }

        return instance;
    }

    private void initialize()
    {
        setLayout( new BoxLayout( this.getContentPane(), BoxLayout.Y_AXIS));

        JPanel panelColores = crearPanelDeColores();
        add(panelColores);

        JPanel panelNivelDeRecursividad = crearPanelNivelDeRecursividad();
        add(panelNivelDeRecursividad);

        JPanel panelPorcentajeZoom = crearPanelPorcentajeZoom();
        add(panelPorcentajeZoom);

        JPanel panelTipoStroke = crearPanelTipodeStroke();
        add(panelTipoStroke);

        JPanel panelCalcular = crearPanelCalcular();
        add(panelCalcular);

        darTamanio();

        this.setVisible(true);
    }

    private void darTamanio()
    {
        this.pack();

        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();

        Dimension ventanaDim = this.getSize();
        setLocation((int)( (dim.getWidth()-ventanaDim.getWidth())/2 ),
                (int)( (dim.getHeight()-ventanaDim.getHeight())/2 ));
    }

    private JPanel crearPanelDeColores()
    {
        JPanel result = new JPanel();
        result.setLayout(new BorderLayout());
        TitledBorder titleNombreModelo;
        titleNombreModelo = BorderFactory.createTitledBorder("Seleccione los colores");
        result.setBorder(titleNombreModelo);

        JPanel contenedor = new JPanel();
        contenedor.setLayout( new BoxLayout( contenedor, BoxLayout.Y_AXIS));

        String elementos[]={ALEATORIO, GRADIENTE, GRADIENTE_ALEATORIO, FIJO};
        jcb_tipoDeDibujoDeColores = new JComboBox(elementos);
        jcb_tipoDeDibujoDeColores.setSelectedIndex(1);
        jcb_tipoDeDibujoDeColores.addActionListener(new ActionListener()
                                                    {
                                                        public void actionPerformed(ActionEvent arg0) {

                                                            String sItem = (String) jcb_tipoDeDibujoDeColores.getSelectedItem();
                                                            if(sItem.equalsIgnoreCase(ALEATORIO))
                                                            {
                                                                p_botonesColoresGradiente.setVisible(false);
                                                                jcb_numeroDeGradientes.setVisible(false);
                                                                jcb_numeroDeLineas.setVisible(false);
                                                            }
                                                            else
                                                            if(sItem.equalsIgnoreCase(GRADIENTE))
                                                            {
                                                                p_botonesColoresGradiente.setVisible(true);
                                                                jcb_numeroDeGradientes.setVisible(true);
                                                                jcb_numeroDeLineas.setVisible(false);
                                                            }
                                                            else
                                                            if(sItem.equalsIgnoreCase(GRADIENTE_ALEATORIO))
                                                            {
                                                                p_botonesColoresGradiente.setVisible(true);
                                                                jcb_numeroDeGradientes.setVisible(true);
                                                                jcb_numeroDeLineas.setVisible(false);

                                                                updatePanelBotonesColorGradienteAleatorio();
                                                            }
                                                            else
                                                            if(sItem.equalsIgnoreCase(FIJO))
                                                            {
                                                                p_botonesColoresGradiente.setVisible(true);
                                                                jcb_numeroDeGradientes.setVisible(true);
                                                                jcb_numeroDeLineas.setVisible(true);

                                                                instance.pack();
                                                            }
                                                        }
                                                    }
        );
        contenedor.add(jcb_tipoDeDibujoDeColores);

        p_botonesColoresGradiente = new JPanel();
        p_botonesColoresGradiente.setLayout( new GridLayout(1, 2));


        jb_botonesColoresGradientes[0] = new JButton(""+0);
        jb_botonesColoresGradientes[0].setBackground(coloresGradientes[0]);
        jb_botonesColoresGradientes[0].setForeground(getColorOpuesto(coloresGradientes[0]));
        jb_botonesColoresGradientes[0].addActionListener(new ActionListener()
                                                         {
                                                             public void actionPerformed(ActionEvent arg0) {
                                                                 coloresGradientes[0] = JColorChooser.showDialog( null, "Seleccione un color", coloresGradientes[0] );
                                                                 jb_botonesColoresGradientes[0].setBackground(coloresGradientes[0]);
                                                                 jb_botonesColoresGradientes[0].setForeground(getColorOpuesto(coloresGradientes[0]));
                                                             }
                                                         }
        );
        p_botonesColoresGradiente.add(jb_botonesColoresGradientes[0]);

        jb_botonesColoresGradientes[1] = new JButton(""+1);
        jb_botonesColoresGradientes[1].setBackground(coloresGradientes[1]);
        jb_botonesColoresGradientes[1].setForeground(getColorOpuesto(coloresGradientes[1]));
        jb_botonesColoresGradientes[1].addActionListener(new ActionListener()
                                                         {
                                                             public void actionPerformed(ActionEvent arg0) {
                                                                 coloresGradientes[1] = JColorChooser.showDialog( null, "Seleccione un color", coloresGradientes[1] );
                                                                 jb_botonesColoresGradientes[1].setBackground(coloresGradientes[1]);
                                                                 jb_botonesColoresGradientes[1].setForeground(getColorOpuesto(coloresGradientes[1]));
                                                             }
                                                         }
        );
        p_botonesColoresGradiente.add(jb_botonesColoresGradientes[1]);


        contenedor.add(p_botonesColoresGradiente);

        int n = 50;//(int)(a/b);
        elementos=new String[n];////String sItem = ;
        for(int i=0; i<elementos.length ; i++ )
            elementos[i]=""+(i+1);

        JPanel panelNroGradientes = new JPanel();
        panelNroGradientes.setLayout( new BoxLayout( panelNroGradientes, BoxLayout.X_AXIS));
        JLabel labelGradientes = new JLabel("Numero de gradientes");
        panelNroGradientes.add(labelGradientes);
        jcb_numeroDeGradientes = new JComboBox(elementos);
        jcb_numeroDeGradientes.setSelectedItem(elementos[0]);
        jcb_numeroDeGradientes.addActionListener(new ActionListener()
                                                 {
                                                     public void actionPerformed(ActionEvent arg0) {
                                                         String sItem = (String) jcb_tipoDeDibujoDeColores.getSelectedItem();
                                                         if(sItem.equalsIgnoreCase(GRADIENTE))
                                                         {
                                                             updatePanelBotonesColorGradiente(new Color[]{Color.WHITE, Color.BLACK});
                                                         }
                                                         else
                                                         if( sItem.equalsIgnoreCase(GRADIENTE_ALEATORIO) )
                                                         {
                                                             String s_value = (String)jcb_numeroDeGradientes.getSelectedItem();
                                                             int nroBotones = Integer.parseInt(s_value) + 1;

                                                             //p_botonesColoresGradiente.removeAll();
                                                             //p_botonesColoresGradiente.setLayout(new GridLayout(1,nroBotones));

                                                             jb_botonesColoresGradientes = new JButton[nroBotones];
                                                             coloresGradientes = new Color[nroBotones];

                                                             for(int i=0;i<nroBotones;i++)
                                                             {
                                                                 jb_botonesColoresGradientes[i] = new JButton(""+i);
                                                             }

                                                             updatePanelBotonesColorGradienteAleatorio();
                                                         }
                                                         else
                                                         if( sItem.equalsIgnoreCase(FIJO) )
                                                         {
                                                             String s_value = (String)jcb_numeroDeGradientes.getSelectedItem();
                                                             int nroBotones = Integer.parseInt(s_value) + 1;

                                                             //p_botonesColoresGradiente.removeAll();
                                                             //p_botonesColoresGradiente.setLayout(new GridLayout(1,nroBotones));

                                                             //JButton[] tmpBotones = jb_botonesColoresGradientes;

                                                             jb_botonesColoresGradientes = new JButton[nroBotones];
                                                             //coloresGradientes = new Color[nroBotones];

                                                             for(int i=0;i<nroBotones;i++)
                                                             {
                                                                 jb_botonesColoresGradientes[i] = new JButton(""+i);
                                                             }

                                                             updatePanelBotonesColorGradiente(new Color[]{Color.WHITE, Color.BLACK});
                                                         }
                                                     }
                                                 }
        );
        panelNroGradientes.add(jcb_numeroDeGradientes);
        contenedor.add(panelNroGradientes);

        n = 500;//(int)(a/b);
        elementos=new String[n];////String sItem = ;
        for(int i=0; i<elementos.length ; i++ )
            elementos[i]=""+(i+1);

        JPanel panelNroLineas = new JPanel();
        panelNroLineas.setLayout( new BoxLayout( panelNroLineas, BoxLayout.X_AXIS));
        JLabel labelLineas = new JLabel("Numero de Lineas");
        panelNroLineas.add(labelLineas);
        jcb_numeroDeLineas = new JComboBox(elementos);
        jcb_numeroDeLineas.setVisible(false);
        jcb_numeroDeLineas.setSelectedItem(elementos[0]);
        jcb_numeroDeLineas.addActionListener(new ActionListener()
                                             {
                                                 public void actionPerformed(ActionEvent arg0) {
													 	/*
														 String sItem = (String) jcb_tipoDeDibujoDeColores.getSelectedItem();
														 if(sItem.equalsIgnoreCase(GRADIENTE))
														 {
															 updatePanelBotonesColorGradiente();
														 }
														 else
														 if(sItem.equalsIgnoreCase(GRADIENTE_ALEATORIO))
														 {
															 String s_value = (String)jcb_numeroDeGradientes.getSelectedItem();
															 int nroBotones = Integer.parseInt(s_value) + 1;

															 //p_botonesColoresGradiente.removeAll();
															 //p_botonesColoresGradiente.setLayout(new GridLayout(1,nroBotones));

															 jb_botonesColoresGradientes = new JButton[nroBotones];
															 coloresGradientes = new Color[nroBotones];

															 for(int i=0;i<nroBotones;i++)
															 {
																 jb_botonesColoresGradientes[i] = new JButton(""+i);
															 }

															 updatePanelBotonesColorGradienteAleatorio();

														 }*/
                                                 }
                                             }
        );
        panelNroLineas.add(jcb_numeroDeLineas);
        contenedor.add(panelNroLineas);

        result.add(contenedor);

        return result;
    }

    private Color getColorOpuesto( Color color1 )
    {
        Color colorOpuesto = new Color((int)(255-color1.getRed()),
                (int)(255-color1.getGreen()),
                (int)(255-color1.getBlue()));

        return colorOpuesto;
    }
    /*
    private JPanel crearPanelNroDeIteraciones()
    {
        JPanel result = new JPanel();
        result.setLayout(new BorderLayout());
        TitledBorder titleNombreModelo;
        titleNombreModelo = BorderFactory.createTitledBorder("Numero de Iteraciones de zoom");
        result.setBorder(titleNombreModelo);
        result.setLayout(new BorderLayout());

        String elementos[]=new String[100];
        for(int i=0; i<elementos.length ; i++ )
            elementos[i]=""+(i+1);

        jcb_nroDeIteraciones = new JComboBox(elementos);
        jcb_nroDeIteraciones.setSelectedIndex(49);
        jcb_nroDeIteraciones.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent arg0) {
                    String sItem = (String) jcb_nroDeIteraciones.getSelectedItem();
                }
            }
        );
        result.add(jcb_nroDeIteraciones);

        return result;
    }
    */
    private JPanel crearPanelNivelDeRecursividad()
    {
        JPanel result = new JPanel();
        result.setLayout(new BorderLayout());
        TitledBorder titleNombreModelo;
        titleNombreModelo = BorderFactory.createTitledBorder("Nivel de Recursividad ");
        result.setBorder(titleNombreModelo);

        int nivel_index = Elementos_UI.getInstance().panel_resultado.jcb_nivel.getSelectedIndex();
        int nivelDeRMAX = Elementos_UI.getInstance().panel_resultado.jcb_nivel.getMaximumRowCount();
        String elementos[]=new String[nivelDeRMAX];
        for(int i=0; i<elementos.length ; i++ )
            elementos[i]=""+(i);

        jcb_nivelDeRecursividad = new JComboBox(elementos);
        jcb_nivelDeRecursividad.setSelectedIndex(nivel_index);
        jcb_nivelDeRecursividad.addActionListener(new ActionListener()
                                                  {
                                                      public void actionPerformed(ActionEvent arg0) {
                                                          String sItem = (String) jcb_nivelDeRecursividad.getSelectedItem();
                                                      }
                                                  }
        );
        result.add(jcb_nivelDeRecursividad);

        return result;
    }

    private JPanel crearPanelPorcentajeZoom()
    {
        JPanel result = new JPanel();
        result.setLayout(new BoxLayout( result, BoxLayout.X_AXIS));
        TitledBorder titleNombreModelo;
        titleNombreModelo = BorderFactory.createTitledBorder("porcentaje final de zoom y salto");
        result.setBorder(titleNombreModelo);

        /***/
        int nivelDeZ1 = 255;//110;
        String elementos1[]=new String[nivelDeZ1];//js_POS_INI js_VAL_MAX
        for(int i=0; i<elementos1.length ; i++ )
            elementos1[i]=""+(i+1);

        jcb_porcentajeFinZoom = new JComboBox(elementos1);
        try {
            //jcb_porcentajeFinZoom.setSelectedItem(elementos1[elementosUI.panel_patron_inicial.js_zoom.getValue()-1]);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        jcb_porcentajeFinZoom.addActionListener(new ActionListener()
                                                {
                                                    public void actionPerformed(ActionEvent arg0) {
                                                        String sItem = (String) jcb_porcentajeFinZoom.getSelectedItem();
                                                    }
                                                }
        );
        jcb_porcentajeFinZoom.setSelectedIndex(99);
        result.add(jcb_porcentajeFinZoom);

        /***/
        int nivelDeSalto = 100;//elementosUI.panel_resultado.panel_de_dibujo.getStroke();
        String elementos[]=new String[nivelDeSalto];//js_POS_INI js_VAL_MAX
        for(int i=0; i<elementos.length ; i++ )
            elementos[i]=""+(i+1);

        jcb_porcentajeZoomSalto = new JComboBox(elementos);
        try{
            int posIni = 1;//elementosUI.panel_patron_inicial.js_zoom.getMinimum();//elementosUI.panel_patron_inicial.js_zoom.getValue()-1;
            jcb_porcentajeZoomSalto.setSelectedItem(elementos[posIni>0? posIni: 0]);//elementosUI.panel_patron_inicial.js_zoom.getValue();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        jcb_porcentajeZoomSalto.addActionListener(new ActionListener()
                                                {
                                                    public void actionPerformed(ActionEvent arg0) {
                                                        String sItem = (String) jcb_porcentajeZoomSalto.getSelectedItem();
                                                    }
                                                }
        );
        jcb_porcentajeZoomSalto.setSelectedIndex(2);
        result.add(jcb_porcentajeZoomSalto);

        return result;
    }

    private JPanel crearPanelTipodeStroke() {
        JPanel result = new JPanel();
        result.setLayout(new BoxLayout( result, BoxLayout.X_AXIS));
        TitledBorder titleIniRotacion;
        titleIniRotacion = BorderFactory.createTitledBorder("Tipo de Stroke");
        result.setBorder(titleIniRotacion);

        String[] elementos1 = {JOptionFactory.NORMAL, JOptionFactory.WOBBLE, ""};

        jcb_tipoStroke = new JComboBox(elementos1);
        result.add(jcb_tipoStroke);

        return result;
    }
/*
    crearPanelPorcentajeRotacion()
    {


        int nivelDeZ1 = elementosUI.panel_patron_inicial.js_VAL_MAX_ROTACION+1;
        String elementos1[]=new String[nivelDeZ1];
        for(int i=0; i<elementos1.length ; i++ )
            elementos1[i]=""+(i);

        jcb_porcentajeIniRotacion = new JComboBox(elementos1);
        try{
            int posIni = elementosUI.panel_patron_inicial.js_rotar.getValue()-1;
            jcb_porcentajeIniRotacion.setSelectedItem(elementos1[posIni>0? posIni: 0]);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        jcb_porcentajeIniRotacion.addActionListener(new ActionListener()
                                                    {
                                                        public void actionPerformed(ActionEvent arg0) {
                                                            String sItem = (String) jcb_porcentajeIniRotacion.getSelectedItem();
                                                        }
                                                    }
        );
        result.add(jcb_porcentajeIniRotacion);

        int nivelDeZ = elementosUI.panel_patron_inicial.js_VAL_MAX_ROTACION+1;//elementosUI.panel_de_dibujo.jcb_nivel.getMaximumRowCount();
        String elementos[]=new String[nivelDeZ];//js_POS_INI js_VAL_MAX
        for(int i=0; i<elementos.length ; i++ )
            elementos[i]=""+(i);

        jcb_porcentajeFinRotacion = new JComboBox(elementos);
        //jcb_porcentajeFinZoom.setSelectedItem(elementos[2*elementosUI.panel_patron_inicial.js_zoom.getValue()-1]);//elementosUI.panel_patron_inicial.js_zoom.getValue();
        jcb_porcentajeFinRotacion.setSelectedItem(elementos[elementosUI.panel_patron_inicial.js_rotar.getValue()]);
        jcb_porcentajeFinRotacion.addActionListener(new ActionListener()
                                                    {
                                                        public void actionPerformed(ActionEvent arg0) {
                                                            String sItem = (String) jcb_porcentajeFinRotacion.getSelectedItem();
                                                        }
                                                    }
        );
        result.add(jcb_porcentajeFinRotacion);

        jcb_porcentajeIniRotacion.setSelectedIndex(elementosUI.panel_patron_inicial.js_rotar.getValue());
        jcb_porcentajeFinRotacion.setSelectedIndex(elementosUI.panel_patron_inicial.js_rotar.getValue());

    }
*/
    private JPanel crearPanelCalcular()
    {
        JPanel result = new JPanel();

        result.setLayout(new BorderLayout());
        //result.setLayout(new BoxLayout( result, BoxLayout.Y_AXIS));
        TitledBorder titleNombreModelo;
        titleNombreModelo = BorderFactory.createTitledBorder(Panel_resultado.CALCULAR);
        result.setBorder(titleNombreModelo);

        JPanel p_radioTiling = new JPanel();
        p_radioTiling.setLayout(new BoxLayout( p_radioTiling, BoxLayout.Y_AXIS));
        jrb_sinTiling = new JRadioButton(Panel_resultado.SIN_TILING);
        jrb_sinTiling.setVisible(true);
        p_radioTiling.add(jrb_sinTiling);
        /*
        jrb_tilingCuadrado = new JRadioButton(Panel_resultado.TILING_CUADRADO);
        p_radioTiling.add(jrb_tilingCuadrado);
        */
        jrb_tilingHexagonal = new JRadioButton(Panel_resultado.TILING_HEXAGONAL);
        p_radioTiling.add(jrb_tilingHexagonal);
        ButtonGroup bg_tiling = new ButtonGroup();
        bg_tiling.add(jrb_sinTiling);
        //bg_tiling.add(jrb_tilingCuadrado);
        bg_tiling.add(jrb_tilingHexagonal);
        jrb_sinTiling.setSelected(true);

        result.add(p_radioTiling, BorderLayout.NORTH);

        JButton jb_botonCalcular =new JButton(Panel_resultado.CALCULAR);
        jb_botonCalcular.addActionListener(new ActionListener()
                                           {
                                               public void actionPerformed(ActionEvent arg0) {
                                                   instance.setVisible(false);

                                                   elementosUI = Elementos_UI.getInstance();
                                                   elementosUI.setCalculandoLizard(false);

                                                   /*if ()*/ {
                                                       elementosUI.ejecutarRutina1( ((String)jcb_tipoDeDibujoDeColores.getSelectedItem()) // GRADIENTE, FIJO, GRADIENTE_ALEATORIO
                                                               ,
                                                               coloresGradientes,
                                                               Integer.parseInt((String)jcb_numeroDeLineas.getSelectedItem()),
                                                               coloresGradientes.length,//Integer.parseInt((String)jcb_numeroDeGradientes.getSelectedItem()+1),//int nroDeGradientes,
                                                               Integer.parseInt((String)jcb_nivelDeRecursividad.getSelectedItem()),//int nivelDeRecursividad,
                                                               Integer.parseInt((String)jcb_porcentajeZoomSalto.getSelectedItem()),//int porcentajeZoomMin);
                                                               Integer.parseInt((String)jcb_porcentajeFinZoom.getSelectedItem()),
                                                               jrb_tilingHexagonal.isSelected(),
                                                               (String)jcb_tipoStroke.getSelectedItem()
                                                       );
                                                   }/*
                                                   else
                                                   {
                                                       /*
                                                       elementosUI.tiling(
                                                               jrb_tilingHexagonal.isSelected()?
                                                                       Panel_resultado.TILING_CUADRADO:
                                                                       jrb_tilingHexagonal.isSelected()?
                                                                               Panel_resultado.TILING_HEXAGONAL:
                                                                               Panel_resultado.SIN_TILING
                                                               );
                                                       */
                                                   //}

                                               }
                                           }
        );
        result.add(jb_botonCalcular, BorderLayout.CENTER);

        return result;
    }

    public void updatePanelBotonesColorGradiente(Color[] colores)
    {
        String s_value = (String)jcb_numeroDeGradientes.getSelectedItem();
        int nroBotones = Integer.parseInt(s_value) + 1;

        p_botonesColoresGradiente.removeAll();
        p_botonesColoresGradiente.setLayout(new GridLayout(1,nroBotones));

        //// para ajustar el tamanio del arreglo de botones
        if(coloresGradientes.length<nroBotones)
        {
            Color[] c_tmp = new Color[nroBotones];
            int i;
            for(i=0;i<coloresGradientes.length;i++)
            {
                c_tmp[i]=coloresGradientes[i];
            }

            int contColores = 0;
            for(int j=i; j<nroBotones; j++)
            {
                c_tmp[j] = colores[contColores];//Color.WHITE;

                if ((j-1)%colores.length==0) {
                    contColores = 0;
                } else {
                    contColores++;
                }
            }

            coloresGradientes = c_tmp;
        }
        else
        {
            Color[] c_tmp = new Color[nroBotones];
            int i;
            for(i=0;i<nroBotones;i++)
            {
                c_tmp[i]=coloresGradientes[i];
            }

            coloresGradientes = c_tmp;
        }
        ////

        //// para ajustar el tamanio de arreglo de botones colores gradientes
        p_botonesColoresGradiente.removeAll();
        jb_botonesColoresGradientes = new JButton[nroBotones];
        ////

        for(int i=0;i<nroBotones;i++)
        {
            jb_botonesColoresGradientes[i] = new JButton(""+i);
            jb_botonesColoresGradientes[i].setBackground(coloresGradientes[i]);
            jb_botonesColoresGradientes[i].setForeground(getColorOpuesto(coloresGradientes[i]));

            final int i_ = i;
            jb_botonesColoresGradientes[i].addActionListener(new ActionListener()
                                                             {
                                                                 public void actionPerformed(ActionEvent arg0) {
                                                                     coloresGradientes[i_] = JColorChooser.showDialog( null, "Seleccione un color", coloresGradientes[i_] );
                                                                     jb_botonesColoresGradientes[i_].setBackground(coloresGradientes[i_]);
                                                                     jb_botonesColoresGradientes[i_].setForeground(getColorOpuesto(coloresGradientes[i_]));
                                                                 }
                                                             }
            );

            p_botonesColoresGradiente.add(jb_botonesColoresGradientes[i]);
            p_botonesColoresGradiente.updateUI();
        }
    }

    public void updatePanelBotonesColorGradienteAleatorio()
    {
        String s_value = (String)jcb_numeroDeGradientes.getSelectedItem();
        int nroBotones = Integer.parseInt(s_value) + 1;

        p_botonesColoresGradiente.removeAll();
        p_botonesColoresGradiente.setLayout(new GridLayout(1,nroBotones));

        int nro = nroBotones;//jb_botonesColoresGradientes.length;

        for(int i=0; i<nro; i++)
        {
            coloresGradientes[i] = coloresAleatorios[(int)(Math.random()*coloresAleatorios.length)];

            // para no tener colores iguales adyacentes
            if(i>0)
            {
                if(coloresGradientes[i].getRGB() == coloresGradientes[(i-1)%nro].getRGB())
                {
                    coloresGradientes[i] = coloresAleatorios[(int)(Math.random()*coloresAleatorios.length)];
                }
            }

            jb_botonesColoresGradientes[i] = new JButton(""+i);
            jb_botonesColoresGradientes[i].setBackground(coloresGradientes[i]);
            jb_botonesColoresGradientes[i].setForeground(getColorOpuesto(coloresGradientes[i]));

            final int i_ = i;
            jb_botonesColoresGradientes[i].addActionListener(new ActionListener()
                                                             {
                                                                 public void actionPerformed(ActionEvent arg0) {
                                                                     coloresGradientes[i_] = JColorChooser.showDialog( null, "Seleccione un color", coloresGradientes[i_] );
                                                                     jb_botonesColoresGradientes[i_].setBackground(coloresGradientes[i_]);
                                                                     jb_botonesColoresGradientes[i_].setForeground(getColorOpuesto(coloresGradientes[i_]));
                                                                 }
                                                             }
            );

            p_botonesColoresGradiente.add(jb_botonesColoresGradientes[i]);
            p_botonesColoresGradiente.updateUI();
        }
    }

    public void mostrar()
    {
        if(this.jcb_tipoDeDibujoDeColores==null)
        {
            initialize();
        }
        updateValues();
        this.setVisible(true);
    }

    private void updateValues()
    {
        int nivel_index = Elementos_UI.getInstance().panel_resultado.jcb_nivel.getSelectedIndex();
        jcb_nivelDeRecursividad.setSelectedIndex(nivel_index);
    }

    public void setElementosUI(Elementos_UI elementosUI )
    {
        this.elementosUI = elementosUI;
    }

    public static void main (String Arg[])
    {
        (new VentanaDeCrearRutina1()).setVisible(true);
    }
}
