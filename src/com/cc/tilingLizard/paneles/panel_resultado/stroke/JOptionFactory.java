package com.cc.tilingLizard.paneles.panel_resultado.stroke;

import com.cc.tilingLizard.paneles.Elementos_UI;
import com.cc.tilingLizard.paneles.panel_resultado.stroke.strokes.*;
import com.cc.tilingLizard.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Vector;

public class JOptionFactory {

    public static final String NORMAL = "Normal";
    public static final String VACIO = "vacio";
    public static final String LINEA_CADENA = "linea cadena";
    public static final String CIRCULOS = "Circulos";
    public static final String CIRCULOS_Y_ESTRELLAS = "Circulos y Estrellas";
    public static final String CIRCULOS_Y_ESTRELLAS_COMPUESTO = "Circulos y Estrellas compuesto";
    public static final String SELLO_DE_SALOMON_ESTRELLA_DE_DAVID = "Sello de Salomon, Estrella de David";
    public static final String ESTRELLA_NRO_PUNTAS = "Estrella nro de Puntas";
    public static final String ESTRELLA_NRO_PUNTAS_COMPUESTO = "Estrella nro de Puntas compuesto";
    public static final String WOBBLE = "Wobble";
    public static final String TEXT = "Text";

    public static final String ZIGZAG_STROKE = "Zig Zag";
    public static final String ANIMATED_STROKE = "Animated Stroke";

    public static String stroke_actual = "";
    static float width=-1;
    static int salto = -1;
    static int nroPuntas = -1;
    static float ancho_linea;
    static int operation;

    static float dash_phase;

    static JComboBox jcb_salto;

    public static void showStrokeDialog() {
        JOptionPane jop = new JOptionPane();
        JDialog dialog = jop.createDialog("Stroke Type");
        //dialog.setLayout(new BorderLayout());

        JPanel contenedor = new JPanel();
        contenedor.setLayout( new BoxLayout(contenedor, BoxLayout.Y_AXIS) );

        JPanel jp_tipo_stroke = new JPanel();jp_tipo_stroke.setLayout(new BoxLayout(jp_tipo_stroke, BoxLayout.X_AXIS));
        JPanel jp_width = new JPanel();jp_width.setLayout(new BoxLayout(jp_width, BoxLayout.X_AXIS));
        JPanel jp_miterlimit = new JPanel();jp_miterlimit.setLayout(new BoxLayout(jp_miterlimit, BoxLayout.X_AXIS));
        JPanel jp_floatMin = new JPanel();jp_floatMin.setLayout(new BoxLayout(jp_floatMin, BoxLayout.X_AXIS));
        JPanel jp_floatMax = new JPanel();jp_floatMax.setLayout(new BoxLayout(jp_floatMax, BoxLayout.X_AXIS));
        JPanel jp_dash_phase = new JPanel();jp_dash_phase.setLayout(new BoxLayout(jp_dash_phase, BoxLayout.X_AXIS));

        JPanel jp_front_type = new JPanel();jp_front_type.setLayout(new BoxLayout(jp_front_type, BoxLayout.X_AXIS));
        JPanel jp_texto = new JPanel();jp_texto.setLayout(new BoxLayout(jp_texto, BoxLayout.Y_AXIS));
        JPanel jp_texto_1 = new JPanel();jp_texto_1.setLayout(new BoxLayout(jp_texto_1, BoxLayout.X_AXIS));
        JPanel jp_texto_ejemplo = new JPanel();jp_texto_ejemplo.setLayout(new BoxLayout(jp_texto_ejemplo, BoxLayout.X_AXIS));

        JPanel jp_nro_puntas = new JPanel();jp_nro_puntas.setLayout(new BoxLayout(jp_nro_puntas, BoxLayout.X_AXIS));
        JPanel jp_salto = new JPanel();jp_salto.setLayout(new BoxLayout(jp_salto, BoxLayout.X_AXIS));
        JPanel jp_ancho_linea_compuesto = new JPanel();jp_ancho_linea_compuesto.setLayout(new BoxLayout(jp_ancho_linea_compuesto, BoxLayout.X_AXIS));

        String[] array = {
                NORMAL,
                VACIO,
                LINEA_CADENA,
                CIRCULOS,
                CIRCULOS_Y_ESTRELLAS,
                CIRCULOS_Y_ESTRELLAS_COMPUESTO,
                SELLO_DE_SALOMON_ESTRELLA_DE_DAVID,
                ESTRELLA_NRO_PUNTAS,
                ESTRELLA_NRO_PUNTAS_COMPUESTO,
                WOBBLE,
                TEXT,
                ANIMATED_STROKE,
                ZIGZAG_STROKE
        };
        JComboBox jcb_tipoStroke = new JComboBox(array);
        jcb_tipoStroke.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                dialog.pack();
            }
        });
        jp_tipo_stroke.add(new JLabel("Tipo de Stroke "));
        jp_tipo_stroke.add(jcb_tipoStroke);
        contenedor.add(jp_tipo_stroke);

        //JLabel im = new JLabel("Java Technology Dive Log", new ImageIcon("images/gwhite.gif"), JLabel.CENTER);
        jp_width.add(new JLabel("width = "));
        JTextField jtf_width = new JTextField(""+ Constants.initialBasicStroke.getLineWidth());//3
        jtf_width.setHorizontalAlignment(SwingConstants.RIGHT);
        jp_width.add(jtf_width);
        contenedor.add(jp_width);

        jp_miterlimit.add(new JLabel("miterlimit = "));
        JTextField jtf_miterlimit = new JTextField(""+Constants.initialBasicStroke.getMiterLimit());
        jtf_miterlimit.setHorizontalAlignment(SwingConstants.RIGHT);
        jp_miterlimit.add(jtf_miterlimit);
        contenedor.add(jp_miterlimit);

        jp_floatMin.add(new JLabel("floatMin = "));
        JTextField jtf_floatMin = new JTextField(""+Constants.initialBasicStroke.getDashArray()[0]);
        jtf_floatMin.setHorizontalAlignment(SwingConstants.RIGHT);
        jp_floatMin.add(jtf_floatMin);
        contenedor.add(jp_floatMin);

        jp_floatMax.add(new JLabel("floatMax = "));
        JTextField jtf_floatMax = new JTextField(""+Constants.initialBasicStroke.getDashArray()[1]);//5
        jtf_floatMax.setHorizontalAlignment(SwingConstants.RIGHT);
        jp_floatMax.add(jtf_floatMax);
        contenedor.add(jp_floatMax);

        jp_dash_phase.add(new JLabel("dash_phase = "));
        JTextField jtf_dash_phase = new JTextField(""+Constants.initialBasicStroke.getDashPhase());//20
        jtf_dash_phase.setHorizontalAlignment(SwingConstants.RIGHT);
        jp_dash_phase.add(jtf_dash_phase);
        contenedor.add(jp_dash_phase);

        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] allFonts = ge.getAllFonts();
        JLabel jl_texto_ejemplo = new JLabel();
        final Choice choice_font = new Choice();
        //JComboBox choice_font = new JComboBox();

        for (int i = 0; i < allFonts.length; i++)
            choice_font.addItem(allFonts[i].getName());

        choice_font.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent ie) {

                try
                {
                    String removeDotCero = jtf_width.getText().contains(".")?
                            jtf_width.getText().substring(0,jtf_width.getText().lastIndexOf('.')):
                            jtf_width.getText()
                            ;
                    Font font1 = new Font((String)choice_font.getSelectedItem(), Font.PLAIN,
                            Integer.parseInt(
                                    removeDotCero
                            ));
                    jl_texto_ejemplo.setFont(font1);
                    dialog.pack();
                }
                catch (Exception e)
                {
                    System.err.println(e);
                }

            }
        });

        jp_front_type.add(new JLabel("Font = "));
        jp_front_type.add(choice_font);
        jp_front_type.setVisible(false);
        contenedor.add(jp_front_type);

        jp_texto_1.add(new JLabel("Texto = "));
        JTextField jtf_texto = new JTextField(" Sierpinski ");
        jtf_texto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                jl_texto_ejemplo.setText(jtf_texto.getText());
                dialog.pack();
            }
        });
        jtf_texto.setHorizontalAlignment(SwingConstants.RIGHT);
        jp_texto_1.add(jtf_texto);
        jp_texto.add(jp_texto_1);
        jl_texto_ejemplo.setHorizontalAlignment(SwingConstants.RIGHT);
        jl_texto_ejemplo.setText(jtf_texto.getText());
        jp_texto_ejemplo.add(jl_texto_ejemplo);
        jp_texto.add(jp_texto_ejemplo);
        jp_texto.setVisible(false);
        contenedor.add(jp_texto);

        int max = 200;
        String elementos[]=new String[max];
        for(int i=0; i<elementos.length ; i++ )
            elementos[i]=""+(i+1);
        //JTextField jtf_nro_puntas = new JTextField("Nro de puntas de la estrella ");
        JComboBox jcb_NroDePuntas;
        //jtf_nro_puntas.setHorizontalAlignment(SwingConstants.RIGHT);
        jcb_NroDePuntas = new JComboBox(elementos);
        if (nroPuntas==-1) {
            jcb_NroDePuntas.setSelectedIndex(6-1);
        } else {
            jcb_NroDePuntas.setSelectedIndex(nroPuntas-1);
        }

        int _max = Constants.getMaxSalto(Integer.parseInt((String)jcb_NroDePuntas.getSelectedItem()));
        String _elementos[]=new String[_max];
        for(int i=0; i<_elementos.length ; i++ )
            _elementos[i]=""+(i+1);

        jcb_salto = new JComboBox(_elementos);

        jcb_NroDePuntas.addActionListener(new ActionListener()
                                          {
                                              public void actionPerformed(ActionEvent arg0) {
                                                  String sItem = (String) jcb_NroDePuntas.getSelectedItem();

                                                  int max = Constants.getMaxSalto(Integer.parseInt((String)jcb_NroDePuntas.getSelectedItem()));
                                                  String elementos[]=new String[max];
                                                  for(int i=0; i<elementos.length ; i++ )
                                                  {
                                                      elementos[i]=""+(i+1);
                                                      //System.out.println(this.getClass()+" elementos["+i+"]"+elementos[i]);
                                                  }

                                                  JPanel c = (JPanel) jcb_salto.getParent();
                                                  c.remove(jcb_salto);
                                                  jcb_salto = new JComboBox(elementos);
                                                  if (salto == -1) {
                                                      jcb_salto.setSelectedIndex(max-1);
                                                  } else {
                                                      jcb_salto.setSelectedIndex(salto-1);
                                                  }

                                                  c.add(jcb_salto);

                                                  c.updateUI();
                                              }
                                          }
        );
        jp_nro_puntas.add(new JLabel("Nro de Puntas = "));
        jp_nro_puntas.add(jcb_NroDePuntas);
        //jp_nro_puntas.add(jtf_nro_puntas);
        jp_nro_puntas.setVisible(false);
        contenedor.add(jp_nro_puntas);

        jcb_salto.setSelectedIndex(_max-1);
        jcb_salto.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent arg0) {
                                            String sItem = (String) jcb_salto.getSelectedItem();
                                        }
                                    }
        );
        jp_salto.add(new JLabel("Salto entre Puntas = "));
        jp_salto.add(jcb_salto);
        //jp_nro_puntas.add(jtf_nro_puntas);
        jp_salto.setVisible(false);
        contenedor.add(jp_salto);

        String _elemOp[] = {
                "ADD",
                "SUBTRACT",
                "INTERSECT",
                "DIFFERENCE"
        };

        jp_ancho_linea_compuesto.add(new JLabel("Operacion = "));
        JComboBox jcb_operacion = new JComboBox(_elemOp);
        jp_ancho_linea_compuesto.add(jcb_operacion);
        jp_ancho_linea_compuesto.add(new JLabel("Ancho linea = "));
        contenedor.add(jp_ancho_linea_compuesto);
        JTextField jtf_ancho_linea = new JTextField("7");
        jtf_ancho_linea.setHorizontalAlignment(SwingConstants.RIGHT);
        jp_ancho_linea_compuesto.add(jtf_ancho_linea);
        jp_ancho_linea_compuesto.setVisible(false);
        contenedor.add(jp_ancho_linea_compuesto);

        /****************************/
        // add evento al combo de tipo de stroke
        jcb_tipoStroke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                stroke_actual = (String)jcb_tipoStroke.getSelectedItem();

                switch (stroke_actual) {

                    case NORMAL:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(true);
                        jp_floatMin.setVisible(true);
                        jp_floatMax.setVisible(true);
                        jp_dash_phase.setVisible(true);

                        jp_front_type.setVisible(false);
                        jp_texto.setVisible(false);
                        jp_nro_puntas.setVisible(false);
                        jp_salto.setVisible(false);
                        jp_ancho_linea_compuesto.setVisible(false);
                        break;

                    case VACIO:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(false);
                        jp_floatMin.setVisible(false);
                        jp_floatMax.setVisible(false);
                        jp_dash_phase.setVisible(false);

                        jp_front_type.setVisible(false);
                        jp_texto.setVisible(false);
                        jp_nro_puntas.setVisible(false);
                        jp_salto.setVisible(false);
                        jp_ancho_linea_compuesto.setVisible(false);
                        break;

                    case LINEA_CADENA:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(false);
                        jp_floatMin.setVisible(false);
                        jp_floatMax.setVisible(false);
                        jp_dash_phase.setVisible(true);

                        jp_front_type.setVisible(false);
                        jp_texto.setVisible(false);
                        jp_nro_puntas.setVisible(false);
                        jp_salto.setVisible(false);
                        jp_ancho_linea_compuesto.setVisible(false);
                        break;

                    case CIRCULOS:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(false);
                        jp_floatMin.setVisible(false);
                        jp_floatMax.setVisible(false);
                        jp_dash_phase.setVisible(true);
                        //if (width!=-1) {
                        jtf_dash_phase.setText(String.valueOf( Float.parseFloat(jtf_width.getText())/2) );
                        //}

                        jp_front_type.setVisible(false);
                        jp_texto.setVisible(false);
                        jp_nro_puntas.setVisible(false);
                        jp_salto.setVisible(false);
                        jp_ancho_linea_compuesto.setVisible(false);
                        break;

                    case CIRCULOS_Y_ESTRELLAS:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(false);
                        jp_floatMin.setVisible(false);
                        jp_floatMax.setVisible(false);
                        jp_dash_phase.setVisible(false);

                        jp_front_type.setVisible(false);
                        jp_texto.setVisible(false);
                        jp_nro_puntas.setVisible(false);
                        jp_salto.setVisible(false);
                        jp_ancho_linea_compuesto.setVisible(false);
                        break;

                    case CIRCULOS_Y_ESTRELLAS_COMPUESTO:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(false);
                        jp_floatMin.setVisible(false);
                        jp_floatMax.setVisible(false);
                        jp_dash_phase.setVisible(false);

                        jp_front_type.setVisible(false);
                        jp_texto.setVisible(false);
                        jp_nro_puntas.setVisible(false);
                        jp_salto.setVisible(false);
                        jp_ancho_linea_compuesto.setVisible(true);
                        break;

                    case SELLO_DE_SALOMON_ESTRELLA_DE_DAVID:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(false);
                        jp_floatMin.setVisible(false);
                        jp_floatMax.setVisible(false);
                        jp_dash_phase.setVisible(false);

                        jp_front_type.setVisible(false);
                        jp_texto.setVisible(false);
                        jp_nro_puntas.setVisible(false);
                        jp_salto.setVisible(false);
                        jp_ancho_linea_compuesto.setVisible(false);
                        break;

                    case ESTRELLA_NRO_PUNTAS:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(false);
                        jp_floatMin.setVisible(false);
                        jp_floatMax.setVisible(false);
                        jp_dash_phase.setVisible(false);

                        jp_front_type.setVisible(false);
                        jp_texto.setVisible(false);
                        jp_nro_puntas.setVisible(true);
                        jp_salto.setVisible(true);
                        jp_ancho_linea_compuesto.setVisible(false);
                        break;

                    case ESTRELLA_NRO_PUNTAS_COMPUESTO:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(false);
                        jp_floatMin.setVisible(false);
                        jp_floatMax.setVisible(false);
                        jp_dash_phase.setVisible(false);

                        jp_front_type.setVisible(false);
                        jp_texto.setVisible(false);
                        jp_nro_puntas.setVisible(true);
                        jp_salto.setVisible(true);
                        jp_ancho_linea_compuesto.setVisible(true);
                        break;

                    case WOBBLE:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(false);
                        jp_floatMin.setVisible(false);
                        jp_floatMax.setVisible(false);
                        jp_dash_phase.setVisible(false);

                        jp_front_type.setVisible(false);
                        jp_texto.setVisible(false);
                        jp_nro_puntas.setVisible(false);
                        jp_salto.setVisible(false);
                        jp_ancho_linea_compuesto.setVisible(false);
                        break;

                    case TEXT:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(false);
                        jp_floatMin.setVisible(false);
                        jp_floatMax.setVisible(false);
                        jp_dash_phase.setVisible(false);

                        jp_front_type.setVisible(true);
                        jp_texto.setVisible(true);
                        jp_nro_puntas.setVisible(false);
                        jp_salto.setVisible(false);
                        jp_ancho_linea_compuesto.setVisible(false);
                        break;

                    case ANIMATED_STROKE:
                        jtf_width.setVisible(true);
                        jp_miterlimit.setVisible(false);
                        jp_floatMin.setVisible(false);
                        jp_floatMax.setVisible(false);
                        jp_dash_phase.setVisible(false);

                        jp_front_type.setVisible(false);
                        jp_texto.setVisible(false);
                        jp_nro_puntas.setVisible(false);
                        jp_salto.setVisible(false);
                        jp_ancho_linea_compuesto.setVisible(false);
                        break;

                    case ZIGZAG_STROKE:

                        /*
                        ZigzagStroke z_stroke = new ZigzagStroke(t_stroke, 15, 15);
                        */

                        break;

                    default:
                        throw new IllegalArgumentException("Invalid Stroke ");
                }
            }

        });

        /***********************************************/
        // cargar valores por defecto
        if(Elementos_UI.getInstance().panel_resultado.panel_de_dibujo.getStroke() != null) {

            try {
                Stroke s_tmp = Elementos_UI.getInstance().panel_resultado.panel_de_dibujo.getStroke();

                if (s_tmp instanceof BasicStroke) {
                    jcb_tipoStroke.setSelectedItem(NORMAL);

                    BasicStroke s = (BasicStroke)s_tmp;
                    jtf_width.setText(""+((s.getLineWidth())));
                    jtf_miterlimit.setText(""+((s.getMiterLimit())));
                    jtf_floatMin.setText(""+((s.getDashArray()[0])));
                    jtf_floatMax.setText(""+((s.getDashArray()[1])));
                    jtf_dash_phase.setText(""+((s.getDashPhase())));
                } else
                if (s_tmp instanceof CompositeStroke) {

                    if (stroke_actual == VACIO)
                    {
                        jcb_tipoStroke.setSelectedItem(VACIO);

                        CompositeStroke s = (CompositeStroke) s_tmp;
                        jtf_width.setText(String.valueOf(width));
                    }

                } else
                if (s_tmp instanceof ShapeStroke)
                {
                    /*
                    WOBBLE

                    ZIGZAG_STROKE
*/
                    if (stroke_actual == LINEA_CADENA)
                    {
                        jcb_tipoStroke.setSelectedItem(LINEA_CADENA);
                        jtf_width.setText(String.valueOf(width));
                        jtf_dash_phase.setText(String.valueOf(dash_phase));
                    }
                    else
                    if (stroke_actual == CIRCULOS)
                    {
                        jcb_tipoStroke.setSelectedItem(CIRCULOS);
                        jtf_width.setText(String.valueOf(width));
                        jtf_dash_phase.setText(String.valueOf(dash_phase));
                    }
                    else
                    if (stroke_actual == CIRCULOS_Y_ESTRELLAS)
                    {
                        jcb_tipoStroke.setSelectedItem(CIRCULOS_Y_ESTRELLAS);
                        jtf_width.setText(String.valueOf(width));
                    }
                    else
                    if (stroke_actual == SELLO_DE_SALOMON_ESTRELLA_DE_DAVID) {
                        jcb_tipoStroke.setSelectedItem(SELLO_DE_SALOMON_ESTRELLA_DE_DAVID);
                        jtf_width.setText(String.valueOf(width));
                    }
                    else
                    if (stroke_actual == ESTRELLA_NRO_PUNTAS)
                    {
                        jcb_tipoStroke.setSelectedItem(ESTRELLA_NRO_PUNTAS);
                        jtf_width.setText(String.valueOf(width));
                        jcb_NroDePuntas.setSelectedIndex(nroPuntas-1);
                        jcb_salto.setSelectedIndex(salto-1);
                        jcb_operacion.setSelectedIndex(operation);
                    }

                }
                else
                if (s_tmp instanceof CompositeStroke.CompoundStroke)
                {
                    if (stroke_actual == CIRCULOS_Y_ESTRELLAS_COMPUESTO)
                    {
                        jcb_tipoStroke.setSelectedItem(CIRCULOS_Y_ESTRELLAS_COMPUESTO);
                        jtf_width.setText(String.valueOf(width));
                    }
                    else
                    if (stroke_actual == ESTRELLA_NRO_PUNTAS_COMPUESTO)
                    {
                        jcb_tipoStroke.setSelectedItem(ESTRELLA_NRO_PUNTAS_COMPUESTO);
                        jtf_width.setText(String.valueOf(width));
                        jcb_salto.setSelectedIndex(salto-1);
                        jcb_NroDePuntas.setSelectedIndex(nroPuntas-1);
                        jtf_ancho_linea.setText(String.valueOf(ancho_linea));
                        jcb_operacion.setSelectedIndex(operation);
                    }

                }
                else
                if (s_tmp instanceof WobbleStroke)
                {
                    jcb_tipoStroke.setSelectedItem(WOBBLE);
                    jtf_width.setText(String.valueOf(((WobbleStroke) s_tmp).getWidth()));

                } else
                if (s_tmp instanceof TextStroke) {
                    jcb_tipoStroke.setSelectedItem(TEXT);

                    TextStroke s = (TextStroke)s_tmp;
//System.out.println("JOptionFactory:"+ String.valueOf(s.getFontSize()));
                    jtf_width.setText(String.valueOf(s.getFontSize()));
                    jtf_texto.setText(s.getText());
                    choice_font.select(s.getFontName());

                    Font font1 = new Font(s.getFontName(), Font.PLAIN, s.getFontSize());
                    jl_texto_ejemplo.setFont(font1);
                }

            } catch (Exception err) {
                System.err.println(err);
            }

        }

        JPanel jp_botones = new JPanel();jp_botones.setLayout(new BoxLayout(jp_botones, BoxLayout.X_AXIS));

        JButton jb_aceptar = new JButton("Aceptar");
        jb_aceptar.addActionListener(new ActionListener() {
                                         @Override
                                         public void actionPerformed(ActionEvent e) {

                                             Stroke result_stroke;

                                             switch ((String)jcb_tipoStroke.getSelectedItem()) {
                                                 case NORMAL:

                                                     result_stroke = new BasicStroke(
                                                             Float.parseFloat(jtf_width.getText()),
                                                             BasicStroke.CAP_ROUND,//CAP_BUTT, CAP_SQUARE, CAP_ROUND
                                                             BasicStroke.JOIN_ROUND,//JOIN_BEVEL, JOIN_MITER, JOIN_ROUND
                                                             Float.parseFloat(jtf_miterlimit.getText()),
                                                             new float[] { Float.parseFloat(jtf_floatMin.getText()),
                                                                     Float.parseFloat(jtf_floatMax.getText()) },
                                                             Float.parseFloat(jtf_dash_phase.getText()));

                                                     break;
                                                 case VACIO:

                                                     result_stroke = getStrokeVacio(
                                                             Float.parseFloat(jtf_width.getText()),
                                                             0.5f);

                                                     break;
                                                 case LINEA_CADENA:

                                                     result_stroke = getStrokeLinea(
                                                             Float.parseFloat(jtf_width.getText()),
                                                             Float.parseFloat(jtf_dash_phase.getText()));

                                                     break;
                                                 case CIRCULOS:

                                                     result_stroke = getStrokeCirculos(
                                                             Float.parseFloat(jtf_width.getText()),
                                                             Float.parseFloat(jtf_dash_phase.getText()));

                                                     break;
                                                 case CIRCULOS_Y_ESTRELLAS:

                                                     result_stroke = getStrokeCirculoYEstrellas(Float.parseFloat(jtf_width.getText()));

                                                     break;
                                                 case CIRCULOS_Y_ESTRELLAS_COMPUESTO:

                                                     result_stroke = getStrokeCirculoYEstrellasCompuesto(Float.parseFloat(jtf_width.getText()));

                                                     break;
                                                 case SELLO_DE_SALOMON_ESTRELLA_DE_DAVID:

                                                     result_stroke = getStrokeSelloDeSalomonEstrellaDeDavid(Float.parseFloat(jtf_width.getText()));

                                                     break;
                                                 case ESTRELLA_NRO_PUNTAS:

                                                     result_stroke = getStrokeEstrellaNroPuntas(
                                                             //Integer.parseInt(jtf_nro_puntas.getText()),
                                                             Integer.parseInt((String)jcb_NroDePuntas.getSelectedItem()),
                                                             Integer.parseInt((String)jcb_salto.getSelectedItem()),
                                                             Float.parseFloat(jtf_width.getText()));

                                                     break;
                                                 case ESTRELLA_NRO_PUNTAS_COMPUESTO:

                                                     result_stroke = getStrokeEstrellaNroPuntasCompuesto(
                                                             //Integer.parseInt(jtf_nro_puntas.getText()),
                                                             Integer.parseInt((String)jcb_NroDePuntas.getSelectedItem()),
                                                             Integer.parseInt((String)jcb_salto.getSelectedItem()),
                                                             Float.parseFloat(jtf_width.getText()),
                                                             Float.parseFloat(jtf_ancho_linea.getText()),
                                                             jcb_operacion.getSelectedIndex());

                                                     break;
                                                 case WOBBLE:

                                                     result_stroke = new WobbleStroke(Float.parseFloat(jtf_width.getText()),0.2f, 1f);

                                                     break;
                                                 case TEXT:
                                                     String removeDotCero = jtf_width.getText().contains(".")?
                                                             jtf_width.getText().substring(0,jtf_width.getText().lastIndexOf('.')):
                                                             jtf_width.getText()
                                                             ;

                                                     Font font1 = new Font(choice_font.getSelectedItem(), Font.PLAIN, Integer.parseInt(removeDotCero));
                                                     result_stroke = new TextStroke(jtf_texto.getText(), font1 , false, true);

                                                     break;
                                                 case ANIMATED_STROKE:
                                                     result_stroke = new AnimatedStroke(
                                                             Integer.parseInt(jtf_width.getText()),
                                                             Integer.parseInt(jtf_width.getText())
                                                     );

                                                     break;

                                                 default:
                                                     throw new IllegalArgumentException("Invalid Stroke ");
                                             }

                                             Elementos_UI.getInstance().panel_resultado.panel_de_dibujo.setStroke(result_stroke);

                                             dialog.setVisible(false);
                                         }
                                     }

        );
        jp_botones.add(jb_aceptar);

        JButton jb_cancel = new JButton("Cancel");
        jb_cancel.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            dialog.setVisible(false);
                                        }
                                    }
        );
        jp_botones.add(jb_cancel);

        contenedor.add(jp_botones);

        dialog.setContentPane(contenedor);
        dialog.pack();
        dialog.setVisible(true);


        // seleccionar el stroke
                /*
                Stroke stroke = new BasicStroke(3, BasicStroke.CAP_SQUARE,
                    BasicStroke.JOIN_MITER, 10,
                    new float[] { 0, 5 }, 20);
                */
    }

    protected static Stroke getStrokeVacio(float grosorForma, float grosorContorno)
    {
        width = grosorForma;

        return new CompositeStroke( new BasicStroke( grosorForma ), new BasicStroke( grosorContorno ) );
    }

    protected static Stroke getStrokeCirculos(float grosor, float dash_pha)
    {
        width = grosor;
        dash_phase = dash_pha;

        Stroke _shapeStroke = new ShapeStroke(
                new Shape[] {
                        new Ellipse2D.Float(0, 0, grosor/2, grosor/2)//4
                },
                //15.0f
                dash_phase
        );

        return _shapeStroke;
    }

    protected static Stroke getStrokeLinea(float grosor, float dash_pha)
    {
        width = grosor;
        dash_phase = dash_pha;

        LinkedList<Point2D> lista_Point2D = new LinkedList<Point2D>();

        // estrella de 5 puntas
        float escala = 0.1f * grosor/5f;
        //float escala = 0.1f;
        //

/*
        lista_Point2D.add(new Point2D.Double(escala*72,escala*211));
        lista_Point2D.add(new Point2D.Double(escala*21,escala*123));
        lista_Point2D.add(new Point2D.Double(escala*122,escala*124));
        lista_Point2D.add(new Point2D.Double(escala*172,escala*37));
        lista_Point2D.add(new Point2D.Double(escala*272,escala*37));
        lista_Point2D.add(new Point2D.Double(escala*322,escala*124));
        //lista_Point2D.add(new Point2D.Double(escala*2.02,escala*2.96));
        lista_Point2D.add(new Point2D.Double(escala*221,escala*124));
        lista_Point2D.add(new Point2D.Double(escala*172,escala*211));
        lista_Point2D.add(new Point2D.Double(escala*72,escala*211));
*/
/*
        lista_Point2D.add(new Point2D.Double(escala*108,escala*183));
        lista_Point2D.add(new Point2D.Double(escala*107,escala*233));
        lista_Point2D.add(new Point2D.Double(escala*20,escala*182));
        lista_Point2D.add(new Point2D.Double(escala*20,escala*132));
        lista_Point2D.add(new Point2D.Double(escala*220,escala*132));
        lista_Point2D.add(new Point2D.Double(escala*220,escala*82));
        lista_Point2D.add(new Point2D.Double(escala*420,escala*82));
        lista_Point2D.add(new Point2D.Double(escala*420,escala*32));
        lista_Point2D.add(new Point2D.Double(escala*508,escala*83));
        lista_Point2D.add(new Point2D.Double(escala*508,escala*133));
        lista_Point2D.add(new Point2D.Double(escala*308,escala*133));
        lista_Point2D.add(new Point2D.Double(escala*308,escala*183));
*/
        /**Cubo Sombra*/
        /*
        lista_Point2D.add(new Point2D.Double(escala*104,escala*139));
        lista_Point2D.add(new Point2D.Double(escala*22,escala*276));
        lista_Point2D.add(new Point2D.Double(escala*22,escala*176));
        lista_Point2D.add(new Point2D.Double(escala*104,escala*39));
        lista_Point2D.add(new Point2D.Double(escala*204,escala*39));
        lista_Point2D.add(new Point2D.Double(escala*204,escala*139));
        */
        /**Cubo cuadrado Sombra*/
        lista_Point2D.add(new Point2D.Double(escala*382,escala*142));
        lista_Point2D.add(new Point2D.Double(escala*209,escala*242));
        lista_Point2D.add(new Point2D.Double(escala*209,escala*442));
        lista_Point2D.add(new Point2D.Double(escala*36,escala*342));
        lista_Point2D.add(new Point2D.Double(escala*36,escala*142));
        lista_Point2D.add(new Point2D.Double(escala*209,escala*42));

        Stroke _shapeStroke = new ShapeStroke(
                new Shape[] {
                        Constants.crear_POLIGONO_CERRADO_LinkedList(lista_Point2D)
                },
                //15.0f
                width/2 + dash_pha
        );

        return _shapeStroke;
    }

    protected static Stroke getStrokeCirculoYEstrellas(float grosor)
    {
        width = grosor;

        LinkedList<Point2D> lista_Point2D = new LinkedList<Point2D>();

        // estrella de 5 puntas
        float escala = grosor/9f;

        lista_Point2D.add(new Point2D.Double(escala*2.83,escala*3.32));
        lista_Point2D.add(new Point2D.Double(escala*1.77,escala*0.04));
        lista_Point2D.add(new Point2D.Double(escala*4.56,escala*2.07));
        lista_Point2D.add(new Point2D.Double(escala*7.35,escala*0.04));
        lista_Point2D.add(new Point2D.Double(escala*6.29,escala*3.32));
        lista_Point2D.add(new Point2D.Double(escala*9.08,escala*5.35));
        lista_Point2D.add(new Point2D.Double(escala*5.63,escala*5.35));
        lista_Point2D.add(new Point2D.Double(escala*4.56,escala*8.63));
        lista_Point2D.add(new Point2D.Double(escala*3.49,escala*5.35));
        lista_Point2D.add(new Point2D.Double(escala*0.04,escala*5.35));

        Stroke _shapeStroke = new ShapeStroke(
                new Shape[] {
                        Constants.crear_POLIGONO_CERRADO_LinkedList(lista_Point2D),
                        new Ellipse2D.Float(0, 0, grosor/2, grosor/2)//4
                },
                //15.0f
                grosor
        );

        return _shapeStroke;
    }

    protected static CompositeStroke.CompoundStroke getStrokeCirculoYEstrellasCompuesto(float grosor)
    {
        return new CompositeStroke.CompoundStroke(
                //getStrokeVacio(grosor,0.5f),
                new BasicStroke(
                        (int)(grosor),
                        BasicStroke.CAP_BUTT,//CAP_BUTT, CAP_SQUARE, CAP_ROUND
                        BasicStroke.JOIN_MITER,//JOIN_BEVEL, JOIN_MITER, JOIN_ROUND
                        1.5f,
                        new float[] {1,
                                0 },0),
                getStrokeCirculoYEstrellas(grosor),
                CompositeStroke.CompoundStroke.DIFFERENCE);

    }

    protected static Stroke getStrokeSelloDeSalomonEstrellaDeDavid(float grosor)
    {
        LinkedList<Point2D> lista_Point2D = new LinkedList<Point2D>();

        // estrella de 5 puntas
        float escala = grosor/9f;

        lista_Point2D.add(new Point2D.Double(escala*2.59,escala*2.22));
        lista_Point2D.add(new Point2D.Double(escala*0.02,escala*2.22));
        lista_Point2D.add(new Point2D.Double(escala*1.31,escala*4.45));
        lista_Point2D.add(new Point2D.Double(escala*0.02,escala*6.68));
        lista_Point2D.add(new Point2D.Double(escala*2.59,escala*6.68));
        lista_Point2D.add(new Point2D.Double(escala*3.88,escala*8.91));

        lista_Point2D.add(new Point2D.Double(escala*5.17,escala*6.68));

        lista_Point2D.add(new Point2D.Double(escala*7.74,escala*6.68));
        lista_Point2D.add(new Point2D.Double(escala*6.45,escala*4.45));
        lista_Point2D.add(new Point2D.Double(escala*7.74,escala*2.22));

        lista_Point2D.add(new Point2D.Double(escala*7.74,escala*2.22));
        lista_Point2D.add(new Point2D.Double(escala*5.17,escala*2.22));
        lista_Point2D.add(new Point2D.Double(escala*3.88,escala*0.01));

        Stroke _shapeStroke = new ShapeStroke(
                new Shape[] {
                        Constants.crear_POLIGONO_CERRADO_LinkedList(lista_Point2D),
                },
                //15.0f
                grosor
        );

        return _shapeStroke;
    }

    protected static Stroke getStrokeEstrellaNroPuntas(int nroPts, int salt, float grosor)
    {
        width = grosor;
        salto = salt;
        nroPuntas = nroPts;

        Vector vRes = Constants.calcularEneagono1((int)grosor, (int)grosor, nroPuntas, (int)grosor, salto);
        LinkedList<Point2D> lista_Point2D = new LinkedList<Point2D>();
        for (Object obj : vRes) {
            if (obj instanceof Point2D) {
                lista_Point2D.add((Point2D)obj);
            }
        }

        Stroke _shapeStroke = new ShapeStroke(
                new Shape[] {
                        Constants.crear_POLIGONO_CERRADO_LinkedList(lista_Point2D),
                },
                //15.0f
                2*grosor
        );

        return _shapeStroke;
    }

    protected static Stroke getStrokeEstrellaNroPuntasCompuesto(int nroPts, int salt, float grosor, float grosorLinea, int op)
    {
        ancho_linea = grosorLinea;
        operation = op;

        return new CompositeStroke.CompoundStroke(
                new BasicStroke(
                        (int)(ancho_linea),
                        BasicStroke.CAP_BUTT,//CAP_BUTT, CAP_SQUARE, CAP_ROUND
                        BasicStroke.JOIN_MITER,//JOIN_BEVEL, JOIN_MITER, JOIN_ROUND
                        1.5f,
                        new float[] {1,
                                0 },0),
                getStrokeEstrellaNroPuntas(nroPts, salt, grosor),// grosor/2
                op);// CompositeStroke.CompoundStroke.DIFFERENCE
    }


}

