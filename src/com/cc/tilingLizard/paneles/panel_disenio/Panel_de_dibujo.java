package com.cc.tilingLizard.paneles.panel_disenio;

import com.cc.tilingLizard.paneles.Elementos_UI;
import com.cc.tilingLizard.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Vector;

public class Panel_de_dibujo extends JPanel {

    public Vector v_puntos=new Vector();

    public boolean mas_puntos=false;
    public boolean mover_puntos=false;
    public boolean borrar_puntos=false;
    public boolean borrar_todo=false;

    public boolean pintar_ejes=false;

    Point2D mSelectedPoint;
    int i_mSelectedPoint;

    LinkedList<Point2D> mListSelectedPoints = new LinkedList<>();
    LinkedList<Integer> index_ListSelectedPoints = new LinkedList<>();

    Panel_patron_disenio panel_patron_inicial;

    Point2D oldPuntoInicial;

    boolean mostrarPuntosDeControl = true;
    boolean mostrarDistancias = true;
    boolean mostrarAngulosEjeX = true;
    boolean mostrarAngulosEntreLineas = true;
    //boolean mostrarPuntosNumerados = true;

    Double oldZoom;
    Double oldRotacion;

    public Image backgroundImage;

    Color color_lineas = Color.RED;

    public JChecksLado jc_lado_1;
    public JChecksLado jc_lado_2;
    public JChecksLado jc_lado_3;
    public JChecksLado jc_lado_4;
    public JChecksLado jc_lado_5;
    public JChecksLado jc_lado_6;

    public class JChecksLado
    {
        public JCheckBox jchb_lado;
        public JCheckBox dir_lado;

        public Point2D[] lado;

        public JChecksLado(JCheckBox jchb_lado1, JCheckBox dir_lado_1)
        {
            jchb_lado = jchb_lado1;
            dir_lado = dir_lado_1;
        }
    }

    public Panel_de_dibujo(String string, Panel_patron_disenio panel_patron_inicial)
    {
        //super(string);
        this.panel_patron_inicial = panel_patron_inicial;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initializeCheckRadio();
            }
        });
    }

    public void initializeCheckRadio()
    {
        JPanel panelArribaDer_All = new JPanel();
        JPanel panelArribaDer_up = new JPanel();
        JPanel panelArribaDer_down = new JPanel();

        panelArribaDer_All.setLayout(new BoxLayout(panelArribaDer_All, BoxLayout.Y_AXIS));
        panelArribaDer_up.setLayout(new BoxLayout(panelArribaDer_up, BoxLayout.X_AXIS));
        panelArribaDer_down.setLayout(new BoxLayout(panelArribaDer_down, BoxLayout.X_AXIS));

        jc_lado_1 = new JChecksLado( new JCheckBox("1"), new JCheckBox("1") );
        jc_lado_2 = new JChecksLado( new JCheckBox("2"), new JCheckBox("2") );
        jc_lado_3 = new JChecksLado( new JCheckBox("3"), new JCheckBox("3") );
        jc_lado_4 = new JChecksLado( new JCheckBox("4"), new JCheckBox("4") );
        jc_lado_5 = new JChecksLado( new JCheckBox("5"), new JCheckBox("5") );
        jc_lado_6 = new JChecksLado( new JCheckBox("6"), new JCheckBox("6") );

        panelArribaDer_up.add(jc_lado_1.jchb_lado);
        panelArribaDer_down.add(jc_lado_1.dir_lado);
        panelArribaDer_up.add(jc_lado_2.jchb_lado);
        panelArribaDer_down.add(jc_lado_2.dir_lado);
        panelArribaDer_up.add(jc_lado_3.jchb_lado);
        panelArribaDer_down.add(jc_lado_3.dir_lado);
        panelArribaDer_up.add(jc_lado_4.jchb_lado);
        panelArribaDer_down.add(jc_lado_4.dir_lado);
        panelArribaDer_up.add(jc_lado_5.jchb_lado);
        panelArribaDer_down.add(jc_lado_5.dir_lado);
        panelArribaDer_up.add(jc_lado_6.jchb_lado);
        panelArribaDer_down.add(jc_lado_6.dir_lado);

        panelArribaDer_All.add(panelArribaDer_up);
        panelArribaDer_All.add(panelArribaDer_down);

        add(panelArribaDer_All);
    }

    public void dibujar_punto(double x ,double y)
    {
        Point2D punto;
        punto= new Point2D.Double( x , y );

        if(mas_puntos)
        {
            if (Elementos_UI.getInstance().sentidoColocadoDePuntos.equalsIgnoreCase(Constants.AL_FINAL)) {
                v_puntos.add(punto);
            }
            else if (Elementos_UI.getInstance().sentidoColocadoDePuntos.equalsIgnoreCase(Constants.AL_INICIO))
            {
                v_puntos.add(0, punto);
            }

            panel_patron_inicial.jta_estado.setText("mas_puntos");
        }
        else
        if(mover_puntos)
        {
            panel_patron_inicial.jta_estado.setText("mover_puntos");

            for (int i = 0; i < v_puntos.size(); i++)
            {

                Shape s = Constants.get_punto_de_control((Point2D)v_puntos.get(i), Constants.TAM);
                if (s.contains(punto) &&
                        mSelectedPoint != v_puntos.get(i))
                {
                    mSelectedPoint = (Point2D)v_puntos.get(i);
                    i_mSelectedPoint=i;

                    // ultima distancia seleccionada por el punto
                    panel_patron_inicial.actualizarUltimaDistancia(
                            (Point2D) v_puntos.get(i),
                            ((Point2D) v_puntos.get((i+1)%v_puntos.size())),
                            i>0?
                                    ((Point2D) v_puntos.get((i-1)%v_puntos.size())) :
                                    ((Point2D) v_puntos.get((i+1)%v_puntos.size()))
                    );

                    break;
                }
            }
        }
        else
        if(borrar_puntos)
        {
            panel_patron_inicial.jta_estado.setText("borrar_puntos");

            for (int i = 0; i < v_puntos.size(); i++)
            {

                Shape s = Constants.get_punto_de_control((Point2D)v_puntos.get(i), Constants.TAM);
                if (s.contains(punto))
                {
                    mSelectedPoint = (Point2D)v_puntos.get(i);
                    i_mSelectedPoint=i;

                    // ultima distancia seleccionada por el punto
                    panel_patron_inicial.actualizarUltimaDistancia(
                            (Point2D) v_puntos.get(i),
                            ((Point2D) v_puntos.get((i+1)%v_puntos.size())),
                            i>0?
                                    ((Point2D) v_puntos.get((i-1)%v_puntos.size())) :
                                    ((Point2D) v_puntos.get((i+1)%v_puntos.size()))
                    );

                    borrar_punto();

                    break;
                }


            }
        }
        else
        if(borrar_todo)
        {
            panel_patron_inicial.jta_estado.setText("borrar_todo");
            v_puntos=new Vector();

            mSelectedPoint = null;
            i_mSelectedPoint = -1;

            mListSelectedPoints = new LinkedList<>();
            index_ListSelectedPoints = new LinkedList<>();
        }

        this.repaint();
    }

    public void mover_punto(double x ,double y)
    {
        Point2D punto;
        punto= new Point2D.Double( x , y );

        if (mSelectedPoint != null)
        {
            mSelectedPoint.setLocation(punto);

            v_puntos.setElementAt(mSelectedPoint,i_mSelectedPoint);
            this.repaint();

            // ultima distancia seleccionada por el punto
            panel_patron_inicial.actualizarUltimaDistancia(
                    (Point2D) v_puntos.get(i_mSelectedPoint),
                    ((Point2D) v_puntos.get((i_mSelectedPoint+1)%v_puntos.size())),
                    i_mSelectedPoint>0?
                            ((Point2D) v_puntos.get((i_mSelectedPoint-1)%v_puntos.size())) :
                            ((Point2D) v_puntos.get((i_mSelectedPoint+1)%v_puntos.size()))
            );
        }

        Constants.verificarLiveDrawing();
    }

    public void mover_todos_los_puntos(double x ,double y)
    {
        if(oldPuntoInicial==null)
        {
            oldPuntoInicial = new Point2D.Double( x , y );
            return;
        }


        Point2D punto = new Point2D.Double( x , y );

        for(int i=0; i<v_puntos.size(); i++)
        {
            Point2D punto_i = (Point2D) v_puntos.get(i);

            double deltaX = punto.getX() - oldPuntoInicial.getX();
            double deltaY = punto.getY() - oldPuntoInicial.getY();
            Point2D punto_fin = new Point2D.Double( deltaX + punto_i.getX(),
                    deltaY + punto_i.getY());

            //v_puntos.remove(i);
            v_puntos.setElementAt(punto_fin, i);
        }
		/*
		if(!this.mover_puntos)
		{
			mSelectedPoint = null;
		}

		if(mSelectedPoint!=null)
		{
			double deltaX = punto.getX() - oldPuntoInicial.getX();
			double deltaY = punto.getY() - oldPuntoInicial.getY();
			mSelectedPoint = new Point2D.Double( deltaX + mSelectedPoint.getX(),
													deltaY + mSelectedPoint.getY());
		}
		*/
        oldPuntoInicial = punto;
        //pintar_puntos();
        this.repaint();
    }

    public void moverTodosLosPuntos(int keyCode) {

        double deltaX = 0;
        double deltaY = 0;

        switch( keyCode ) {
            case KeyEvent.VK_UP:
                // handle up
                deltaX = 0;
                deltaY = -1;
                break;
            case KeyEvent.VK_DOWN:
                // handle down
                deltaX = 0;
                deltaY = 1;
                break;
            case KeyEvent.VK_LEFT:
                // handle left
                deltaX = -1;
                deltaY = 0;
                break;
            case KeyEvent.VK_RIGHT :
                // handle right
                deltaX = +1;
                deltaY = 0;
                break;
        }

        if (mSelectedPoint != null)
        {
            Point2D punto_fin = new Point2D.Double( deltaX + mSelectedPoint.getX(),
                    deltaY + mSelectedPoint.getY());
            mSelectedPoint = punto_fin;

            v_puntos.setElementAt(mSelectedPoint, i_mSelectedPoint);
            this.repaint();

            Constants.verificarLiveDrawing();

            return;
        }
        else
        if(!mListSelectedPoints.isEmpty())
        {
            for(int i=0; i<mListSelectedPoints.size(); i++)
            {
                Point2D punto_fin = new Point2D.Double(
                        deltaX + mListSelectedPoints.get(i).getX(),
                        deltaY + mListSelectedPoints.get(i).getY());

                mListSelectedPoints.set(i, punto_fin);
                v_puntos.setElementAt(mListSelectedPoints.get(i), index_ListSelectedPoints.get(i));
            }

            this.repaint();

            Constants.verificarLiveDrawing();

            return;
        }

        // movemos todos los puntos
        for(int i=0; i<v_puntos.size(); i++)
        {
            Point2D punto_i = (Point2D) v_puntos.get(i);


            Point2D punto_fin = new Point2D.Double( deltaX + punto_i.getX(),
                    deltaY + punto_i.getY());

            //v_puntos.remove(i);
            v_puntos.setElementAt(punto_fin, i);
        }

        this.repaint();
    }

    public void set_punto_inicial_puntos(Point2D point2D)
    {
        oldPuntoInicial = point2D;//new Point2D.Double( x , y );
    }

    public void borrar_punto()
    {
        Vector v=new Vector();

        if (mSelectedPoint != null)
        {
            for(int i=0;i<i_mSelectedPoint;i++)
            {
                v.add(v_puntos.get(i));
            }

            for(int i=i_mSelectedPoint+1;i<v_puntos.size();i++)
            {
                v.add(v_puntos.get(i));
            }

            v_puntos=v;

            //pintar_puntos();
            this.repaint();
        }
    }

    public void borrar_puntos_seleccionados()
    {
        if (!mListSelectedPoints.isEmpty())
        {
            v_puntos.removeAll(mListSelectedPoints);

            mListSelectedPoints = new LinkedList<>();
            index_ListSelectedPoints = new LinkedList<>();

            this.repaint();
        }
    }

    public void calcularNewUltimaDistancia(double newDistance, boolean next) {
        Point2D p1 = (Point2D) v_puntos.get(i_mSelectedPoint);

        Point2D p2 = next?
                ((Point2D) v_puntos.get((i_mSelectedPoint+1)%v_puntos.size())) :
                i_mSelectedPoint>0?
                        ((Point2D) v_puntos.get((i_mSelectedPoint-1)%v_puntos.size())) :
                        ((Point2D) v_puntos.get((i_mSelectedPoint)%v_puntos.size()))
                ;

        double distance = p1.distance(p2);

        double cost = distance > 0? (p2.getX() - p1.getX())/distance: 1;
        double sent = distance > 0? (p2.getY() - p1.getY())/distance: 0;

        Point2D pnew = new Point2D.Double(p1.getX() + newDistance*cost, p1.getY() + newDistance*sent);

        if (next) {
            v_puntos.set((i_mSelectedPoint+1)%v_puntos.size(), pnew);
        }
        else
        {
            v_puntos.set((i_mSelectedPoint)%v_puntos.size(), pnew);
        }

    }

    public int getNewUltimaDistancia(boolean next) {

        Point2D p1 = (Point2D) v_puntos.get(i_mSelectedPoint);

        Point2D p2 = next?
                ((Point2D) v_puntos.get((i_mSelectedPoint+1)%v_puntos.size())) :
                i_mSelectedPoint>0?
                        ((Point2D) v_puntos.get((i_mSelectedPoint-1)%v_puntos.size())) :
                        ((Point2D) v_puntos.get((i_mSelectedPoint+1)%v_puntos.size()))
                ;

        return (int)p1.distance(p2);
    }

    public void seleccionarVariosPuntos()
    {
        mListSelectedPoints = new LinkedList<>();
        index_ListSelectedPoints = new LinkedList<>();

        Rectangle2D areaSeleccionada = getRectangle2DDouble(
                x_selection_1, y_selection_1, x_selection_2, y_selection_2
        );

        for(int i=0; i<v_puntos.size(); i++)
        {
            Point2D punto_i = (Point2D) v_puntos.get(i);
            if(areaSeleccionada.contains(punto_i))
            {
                mListSelectedPoints.add(punto_i);
                index_ListSelectedPoints.add(i);
            }
        }
    }

    public boolean esta_este_punto_en_la_lista(double x ,double y)
    {
        boolean resultado = false;
        Shape s = Constants.get_punto_de_control( new Point2D.Double(x, y), 4*Constants.TAM );

        for(int i=0; i<v_puntos.size(); i++)
        {
            Point2D punto_i = (Point2D) v_puntos.get(i);
            if(s.contains(punto_i))
            {
                resultado = true;

                break;
            }
        }

        return resultado;
    }

    public int pos_de_este_punto_en_la_lista(double x ,double y)
    {
        int resultado = -1;
        Shape s = Constants.get_punto_de_control( new Point2D.Double(x, y), Constants.TAM );

        for(int i=0; i<v_puntos.size(); i++)
        {
            Point2D punto_i = (Point2D) v_puntos.get(i);
            if(s.contains(punto_i))
            {
                resultado = i;

                break;
            }
        }

        return resultado;
    }

    public void aplicarZoom(String zoom)
    {
        double d_zoom1 = Double.parseDouble(zoom);//100;

        System.out.println(this.getClass().getName()+":aplicarZoom d_zoom1="+d_zoom1);

        // para la primera vez
        if(oldZoom == null)
        {
            oldZoom = Double.valueOf(d_zoom1);
            return;
        }
        //
////	//////para corregir el zoom
        //System.out.println("                 zoom1="+d_zoom1);
        //System.out.println("oldZoom.doubleValue()1="+oldZoom.doubleValue());
        //double d_zoom = 1.0 + d_zoom1-oldZoom.doubleValue();
        double d_zoom = d_zoom1/oldZoom.doubleValue();
        oldZoom = Double.valueOf(d_zoom1);

        System.out.println(this.getClass().getName()+":aplicarZoom d_zoom="+d_zoom);
        //System.out.println(this.getClass().getName()+":aplicarZoom oldZoom="+oldZoom.doubleValue());
        //double d_zoom = d_zoom1;
        //System.out.println("                 zoom2="+d_zoom);
        //System.out.println("oldZoom.doubleValue()2="+oldZoom.doubleValue());
        //////

        Point2D punto_centro_de_gravedad = getCentroDeGravedad(d_zoom);

        for(int i=0; i<v_puntos.size(); i++)
        {
            Point2D punto_i = (Point2D) v_puntos.get(i);

            double deltaX = punto_i.getX() * d_zoom;
            double deltaY = punto_i.getY() * d_zoom;
            Point2D punto_fin = new Point2D.Double( deltaX + punto_centro_de_gravedad.getX(),
                    deltaY + punto_centro_de_gravedad.getY());

            //v_puntos.remove(i);
            v_puntos.setElementAt(punto_fin, i);
        }

        this.repaint();
    }

    public void aplicarRotacion(String rotar)
    {
        if(oldRotacion == null)
        {
            oldRotacion = Double.valueOf(rotar);
            //return;
        }

        Point2D punto_centro_de_gravedad = getCentroDeGravedad();
        Double _rotarGrados = Double.valueOf(rotar) - oldRotacion;

        for(int i=0; i<v_puntos.size(); i++)
        {
            Point2D punto_i = (Point2D) v_puntos.get(i);
            Double alpha_radianes = Math.toRadians( _rotarGrados );

            System.out.println("alpha_radianes="+alpha_radianes);

            Double x = punto_centro_de_gravedad.getX() +
                    (punto_i.getX() - punto_centro_de_gravedad.getX() )*Math.cos( alpha_radianes )
                    - (punto_i.getY() - punto_centro_de_gravedad.getY() )*Math.sin( alpha_radianes );
            Double y = punto_centro_de_gravedad.getY() +
                    (punto_i.getX() - punto_centro_de_gravedad.getX() )*Math.sin( alpha_radianes )
                    + (punto_i.getY() - punto_centro_de_gravedad.getY() )*Math.cos( alpha_radianes );

            Point2D punto_fin = new Point2D.Double( x,y);

            v_puntos.setElementAt(punto_fin, i);
        }

        oldRotacion = Double.valueOf(rotar);

        this.repaint();

    }

    public Point2D getCentroDeGravedad(double d_zoom)
    {
        Point2D result = null;
        double x_centro_de_gravedad = 0;
        double y_centro_de_gravedad = 0;

        int max_i=v_puntos.size();
        if(!v_puntos.isEmpty())
            if(Constants.get_punto_de_control((Point2D)v_puntos.get(0), Constants.TAM)
                    .contains((Point2D)v_puntos.get(v_puntos.size()-1)))
            {
                // para que el ultimo punto no influya en el centro de gravedad
                max_i=v_puntos.size()-1;
            }

        for(int i=0; i<max_i; i++)
        {
            Point2D punto_i = (Point2D) v_puntos.get(i);

            x_centro_de_gravedad += punto_i.getX();
            y_centro_de_gravedad += punto_i.getY();
        }
        x_centro_de_gravedad = (1-d_zoom)*x_centro_de_gravedad/max_i;
        y_centro_de_gravedad = (1-d_zoom)*y_centro_de_gravedad/max_i;

		/*
		x_centro_de_gravedad = (1-d_zoom)*this.getWidth()/2;//x_centro_de_gravedad/v_puntos.size();
		y_centro_de_gravedad = (1-d_zoom)*this.getHeight()/2;//y_centro_de_gravedad/v_puntos.size();
		*/
        result = new Point2D.Double(x_centro_de_gravedad ,
                y_centro_de_gravedad );

        return result;
    }

    public Point2D getCentroDeGravedad()
    {
        Point2D result = null;
        double x_centro_de_gravedad = 0;
        double y_centro_de_gravedad = 0;

        int max_i=v_puntos.size();
        if(!v_puntos.isEmpty())
            if(Constants.get_punto_de_control((Point2D)v_puntos.get(0), Constants.TAM)
                    .contains((Point2D)v_puntos.get(v_puntos.size()-1)))
            {
                max_i=v_puntos.size()-1;
            }

        for(int i=0; i<max_i; i++)
        {
            Point2D punto_i = (Point2D) v_puntos.get(i);

            x_centro_de_gravedad += punto_i.getX();
            y_centro_de_gravedad += punto_i.getY();
        }
        x_centro_de_gravedad = x_centro_de_gravedad/max_i;
        y_centro_de_gravedad = y_centro_de_gravedad/max_i;

        result = new Point2D.Double(x_centro_de_gravedad ,
                y_centro_de_gravedad );

        return result;
    }

    public void setOldZoom(double oldZoom1)
    {
        oldZoom = Double.valueOf(oldZoom1);
    }

    public void mostrarPuntosDeControl(boolean isSelected)
    {
        mostrarPuntosDeControl = isSelected;

        this.repaint();
    }

    public void mostrarDistancias(boolean isSelected)
    {
        mostrarDistancias = isSelected;

        this.repaint();
    }

    public void mostrarAngulosEjeX(boolean isSelected)
    {
        mostrarAngulosEjeX = isSelected;

        this.repaint();
    }

    public void mostrarAngulosEntreLineas(boolean isSelected)
    {
        mostrarAngulosEntreLineas = isSelected;

        this.repaint();
    }



    public void pintar_puntos(Graphics g)
    {
        //Graphics2D g2=(Graphics2D)getGraphics();
        Graphics2D g2=(Graphics2D)g;

        //g2.clearRect(4,30,size().width-8,size().height-35);

        for(int i=0;i<v_puntos.size();i++)
        {


            Point2D punto_temp_1 =(Point2D)v_puntos.get(i);

            ////
            if(mostrarPuntosDeControl)
            {
                if(punto_temp_1==mSelectedPoint || Constants.contieneElIndice(index_ListSelectedPoints, i))
                    g2.setPaint(Color.RED);
                else
                    g2.setPaint(Color.BLUE);

                g2.fill( Constants.get_punto_de_control(punto_temp_1, Constants.TAM) );

            }
            ////

            //g2.setPaint(Color.RED);
            g2.setPaint(color_lineas);
            if ( i < (v_puntos.size()-1) )
            {
                Point2D punto_temp_2=(Point2D)v_puntos.get(i+1);
                g2.drawLine((int)punto_temp_1.getX(),(int)punto_temp_1.getY(),
                        (int)punto_temp_2.getX(),(int)punto_temp_2.getY());
            }


        }

        //if(this.mostrarPuntosDeControl)
        {
            if(v_puntos.size()>0)
            {
                //g2.setPaint(Color.BLACK);
                Constants.setRenderingHConstants(g2, false);
                /*
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                */
                g2.setFont(new Font("Calibri", Font.BOLD, 10));


                Point2D punto_0 =(Point2D)v_puntos.get(0);

                if (mostrarPuntosDeControl)
                {
                    Paint p_tmp = g2.getPaint();

                    if (i_mSelectedPoint == 0){
                        g2.setPaint(new Color(153,0,0));
                    } else {
                        g2.setPaint(new Color(0,0,153));
                    }

                    g2.drawString("P(" + 0 + ")=(" + Math.round(punto_0.getX()) + ", " + Math.round(punto_0.getY()) + ")",
                            (float)( punto_0.getX() + ((Math.random()>0.5)? 5 : (-1)*(5) )),
                            (float)( punto_0.getY() + ((Math.random()>0.5)? 35 : (-1)*(35) )) );

                    g2.setPaint(p_tmp);
                }

                // para calcular el angulo con el eje X
                int largoLineaEjeX = 10;

                for(int i=1;i<v_puntos.size();i++)
                {
                    Point2D punto_1 =(Point2D)v_puntos.get(i);

                    if (mostrarPuntosDeControl)
                    {
                        Paint p_tmp = g2.getPaint();

                        if (i_mSelectedPoint == i || Constants.contieneElIndice(index_ListSelectedPoints, i)){
                            g2.setPaint(new Color(153,0,0));
                        } else {
                            g2.setPaint(new Color(0,0,153));
                        }

                        g2.drawString("P("+i+")=("+Math.round(punto_0.getX())+", "+Math.round(punto_0.getY())+")",
                                (float)( punto_1.getX() + ((Math.random()>0.5)? 5 : -5 )),
                                (float)( punto_1.getY() + ((Math.random()>0.5)? 25 : -25 ) ) );
                        g2.setPaint(p_tmp);
                    }

                    if(mostrarAngulosEjeX)
                    {
                        double angulo_i_rad = calcular_angulos(punto_0, punto_1) ;
                        double angulo_i_degrees = Math.toDegrees ( angulo_i_rad );
                        // verde oscuro
                        // para dibujar el arco
                        g2.setPaint(new Color(0,153,0));
                        g2.drawLine((int)punto_0.getX(),
                                (int)punto_0.getY(),
                                (int)punto_0.getX() + largoLineaEjeX,
                                (int)punto_0.getY() );
                        g.drawArc((int) punto_0.getX() - largoLineaEjeX,
                                (int) punto_0.getY() - largoLineaEjeX,
                                2*largoLineaEjeX,
                                2*largoLineaEjeX,
                                0,
                                (int)angulo_i_degrees);

                        // para dibujar valor del angulo con el eje X
                        // verde oscuro
                        int random1 = (int)Math.pow(-1,Math.random()>0.5?1:0);

                        g2.setPaint(new Color(0,153,0));
                        g2.drawString(""+(int)angulo_i_degrees,
                                (float)( punto_0.getX() + random1*largoLineaEjeX ),
                                (float)( punto_0.getY() + random1*largoLineaEjeX ) );
                    }

                    if(mostrarAngulosEntreLineas)
                    {
                        if(i>1)
                        {
                            // para calcular el angulo entre lineas
                            double ang_i_rad = calcular_angulos_lineas((Point2D)v_puntos.get(i-2), punto_0,punto_1);
                            double ang_i_degrees = Math.toDegrees ( ang_i_rad );

                            double ang_i_rad_inicial = calcular_angulos(punto_0, (Point2D)v_puntos.get(i-2));
                            g2.setPaint(new Color(0,0,153));
                            g.drawArc((int) punto_0.getX() - (2 * largoLineaEjeX),
                                    (int) punto_0.getY() - (2 * largoLineaEjeX),
                                    4*largoLineaEjeX,
                                    4*largoLineaEjeX,
                                    (int) Math.toDegrees ( ang_i_rad_inicial ),
                                    (int)ang_i_degrees);

                            // para dibujar valor del angulo con el eje X
                            // verde oscuro
                            //int StringWidth = g2.getFontMetrics().stringWidth(""+(int)Math.abs(ang_i_degrees));
                            //java.awt.geom.Rectangle2D StringRectangle2D = g2.getFontMetrics().getStringBounds(""+(int)Math.abs(ang_i_degrees), (Graphics) g2);
                            //Rectangle r = new Rectangle(
                            //        (int)( punto_0.getX() + 2*largoLineaEjeX*Math.cos(ang_i_rad_inicial + ang_i_rad/2) ),
                            //        (int)( punto_0.getY() - 2*largoLineaEjeX*Math.sin(ang_i_rad_inicial + ang_i_rad/2) ),
                            //        StringWidth,
                            //        (int)StringRectangle2D.getHeight());

                            int random1 = (int)Math.pow(-1,Math.random()>0.5?1:0);

                            g2.setPaint(new Color(0,0,153));
                            g2.drawString(""+(int)Math.abs(ang_i_degrees),
                                    (float)( punto_0.getX() + random1*2*largoLineaEjeX*Math.cos(ang_i_rad_inicial + ang_i_rad/2) ),
                                    (float)( punto_0.getY() - random1*2*largoLineaEjeX*Math.sin(ang_i_rad_inicial + ang_i_rad/2) ) );
                        }
                    }


                    if(mostrarDistancias)
                    {
                        double distancia=punto_0.distance(punto_1);
                        Point2D u=getVectorUnitario(punto_0, punto_1);

                        // para dibujar la longitud entre lineas
                        g2.setPaint(new Color(153,0,0));
                        g2.drawString(""+(int)distancia,(float)(punto_1.getX()+u.getX()*distancia/2),
                                (float)(punto_1.getY()+u.getY()*distancia/2));
                    }

                    punto_0=punto_1;
                }

                Constants.setRenderingHConstantsANTIALIAS_OFF(g2);
            }
        }

        if (mover_puntos) {
            dibujarRectanguloDeSeleccion(g);
        }

        pintarPanelSizeEnLaEsquinaInferiorDerecha(g);
    }

    public void dibujarFlechaSiLosPuntosNoSonVisibles(Graphics g)
    {
        if (v_puntos==null || v_puntos.isEmpty()) {
            return;
        }

        boolean allPointsAreVisible = false;

        // Get the bounds of the device.
        GraphicsDevice graphicsDevice = this.getGraphicsConfiguration().getDevice();
        Rectangle graphicsConfigurationBounds = new Rectangle();
        graphicsConfigurationBounds.setRect(
                graphicsDevice.getDefaultConfiguration().getBounds().x,
                graphicsDevice.getDefaultConfiguration().getBounds().y,
                Panel_de_dibujo.this.getWidth()-1,
                Panel_de_dibujo.this.getHeight()-1 );

        // Is the location in this bounds?
        graphicsConfigurationBounds.setRect(graphicsConfigurationBounds.x, graphicsConfigurationBounds.y,
                graphicsConfigurationBounds.width, graphicsConfigurationBounds.height);

        for(int i=0; i<v_puntos.size(); i++)
        {
            Point2D p_tmp = (Point2D) v_puntos.get(i);
            if ( graphicsConfigurationBounds.contains(p_tmp.getX(), p_tmp.getY()) )
            {
                allPointsAreVisible = true;
                break;
            }
        }

        if (!allPointsAreVisible)
        {
            Point2D centro_del_panel = new Point2D.Double(this.getWidth()/2, this.getHeight()/2);
            Point2D centro_de_gravedad = getCentroDeGravedad();

            ((Graphics2D)g).setPaint(Color.red);
            ((Graphics2D)g).draw(graphicsConfigurationBounds);

            ((Graphics2D)g).setPaint(Color.GRAY);
            ((Graphics2D)g).draw(Constants.get_punto_de_control(centro_del_panel, Constants.TAM));
            ((Graphics2D)g).draw(new Line2D.Double(centro_de_gravedad ,centro_del_panel));
            g.drawString(""+(int)centro_del_panel.distance(centro_de_gravedad),
                    (int)centro_del_panel.getX() + 20,
                    (int)centro_del_panel.getY() + 20 );
        }
    }

    public void pintarPanelSizeEnLaEsquinaInferiorDerecha(Graphics g)
    {
        FontMetrics fm=g.getFontMetrics();
        String panelSize = "("+(int)getSize().getWidth()
                +","
                +(int)getSize().getHeight()+")";

        int textWidth = fm.stringWidth(panelSize);
        ///// to fill a rectagle background
        Graphics2D g2=(Graphics2D)g;
        g2.setPaint(Color.GRAY);
        g2.fillRect((int)(getSize().getWidth()-textWidth),
                (int)(getSize().getHeight()-5-fm.getHeight()),
                textWidth,
                fm.getHeight());
        /////
        g2.setPaint(Color.WHITE);
        g2.drawString(panelSize,
                (float)getSize().getWidth()-textWidth,
                (float)getSize().getHeight()-10);
    }

    public Point2D getVectorUnitario(Point2D punto_0, Point2D punto_1)
    {
        double distancia = punto_0.distance(punto_1);

        double x= punto_0.getX()-punto_1.getX();
        double y= punto_0.getY()-punto_1.getY();

        return new Point2D.Double(x/distancia, y/distancia);
    }

    public double calcular_angulos(Point2D p0, Point2D p1)
    {
        double angulo;

        Point2D a,b;
        double _x,_y;

        _x = 1;
        _y=0;
        a=new Point2D.Double( _x , _y );
        _x=p1.getX()-p0.getX();
        _y=p1.getY()-p0.getY();
        b=new Point2D.Double( _x , _y );

        double aOb=a.getX()*b.getX()+a.getY()*b.getY();
        double modulo_a=Math.pow(
                ( Math.pow(	(a.getX()),2)+
                        Math.pow(	(a.getY()),2)
                ) , 0.5 );

        double modulo_b=Math.pow(
                ( Math.pow(	(b.getX()),2)+
                        Math.pow(	(b.getY()),2)
                ) , 0.5 );
        int _s=calcular_signo(a,b);

        angulo = _s*Math.acos( aOb/(modulo_a*modulo_b) );

        return angulo;
    }

    public double calcular_angulos_lineas(Point2D p0, Point2D p1, Point2D p2)
    {
        double angulo;

        Point2D a,b;
        double _x,_y;

        _x=p0.getX()-p1.getX();
        _y=p0.getY()-p1.getY();
        a=new Point2D.Double( _x , _y );
        _x=p2.getX()-p1.getX();
        _y=p2.getY()-p1.getY();
        b=new Point2D.Double( _x , _y );

        double aOb=a.getX()*b.getX()+a.getY()*b.getY();
        double modulo_a=Math.pow(
                ( Math.pow(	(a.getX()),2)+
                        Math.pow(	(a.getY()),2)
                ) , 0.5 );

        double modulo_b=Math.pow(
                ( Math.pow(	(b.getX()),2)+
                        Math.pow(	(b.getY()),2)
                ) , 0.5 );
        int _s=calcular_signo(a,b);

        angulo = _s*Math.acos( aOb/(modulo_a*modulo_b) );

        return angulo;
    }

    public int calcular_signo(Point2D A, Point2D B)
    {
        int salida=0;


        double DET=A.getX()*B.getY()-A.getY()*B.getX();

        if(DET>=0)
            salida=-1;
        else
            salida=1;


        return salida;
    }

    //calcular_angulo_entre_lineas

    public Point2D[] get_puntos()
    {
        if (v_puntos.size() == 0)
        {
            System.out.println(this.getClass().getName()+":v_puntos.size() = " + v_puntos.size());
        }

        Point2D[] aux=new Point2D[v_puntos.size()];

        for (int i=0;i<aux.length;i++)
            aux[i]=(Point2D)v_puntos.get(i);

        return aux;
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.clearRect(0,0,getWidth(), getHeight());

        if(backgroundImage!=null)
        {
            System.out.println("backgroundImage"+backgroundImage);
            g2d.drawImage(backgroundImage, 0, 0, null);
        }

        if (pintar_ejes) {
            Constants.setRenderingHConstants(g2d, false);

            pintar_ejes(g);
        }

        pintar_puntos(g);

        dibujarFlechaSiLosPuntosNoSonVisibles(g);
    }

    public void pintar_ejes(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        int ancho = this.getWidth();
        int alto = this.getHeight();
        double escala = 50;

        int n_f=(int)((ancho/2)/escala);

        Color oldColor = g2d.getColor();
        g2d.setColor(Color.LIGHT_GRAY);
        //g2d.setFont(new Font("Helvetica",Font.PLAIN,6));

        for(int i=0;i<=n_f;i++)
        {
            g2d.drawLine((int) (ancho / 2 - i * escala), 0, (int) (ancho / 2 - i * escala), (alto));
        }
        for(int i=0;i<=n_f;i++)
        {
            g2d.drawLine((int) (ancho / 2 + i * escala), 0, (int) (ancho / 2 + i * escala), (alto));
        }

        int n_c=(int)((alto/2)/escala);

        for(int i=0;i<=n_c;i++)
        {
            g2d.drawLine(0, (int) (alto / 2 - i * escala), (ancho), (int) (alto / 2 - i * escala));
        }
        for(int i=0;i<=n_c;i++)
        {
            g2d.drawLine(0, (int) (alto / 2 + i * escala), (ancho), (int) (alto / 2 + i * escala));
        }

        {
            Stroke stroke_actual=g2d.getStroke();

            Stroke stroke = new BasicStroke(1, BasicStroke.CAP_SQUARE,
                    BasicStroke.JOIN_MITER, 2,
                    new float[] { 2, 5 }, 5);
            g2d.setStroke(stroke);

            for(int i=0;i<=2*n_f;i++)
            {
                g2d.drawLine((int) (ancho / 2 - i * escala / 2), 0, (int) (ancho / 2 - i * escala / 2), (alto));
            }
            for(int i=0;i<=2*n_f;i++)
            {
                g2d.drawLine((int) (ancho / 2 + i * escala / 2), 0, (int) (ancho / 2 + i * escala / 2), (alto));
            }
            for(int i=0;i<=2*n_c;i++)
            {
                g2d.drawLine((0), (int) (alto / 2 - i * escala / 2), (ancho), (int) (alto / 2 - i * escala / 2));
            }
            for(int i=0;i<=2*n_c;i++)
            {
                g2d.drawLine((0), (int) (alto / 2 + i * escala / 2), (ancho), (int) (alto / 2 + i * escala / 2));
            }

            for(int i=0;i<=2*n_c+4;i++)
            {
                if(i%2==1)
                    g2d.draw(new java.awt.geom.Ellipse2D.Double(ancho/2-i*escala/2,alto/2-i*escala/2,i*escala,i*escala) );
            }

            g2d.setStroke(stroke_actual);

            for(int i=0;i<=n_c+2;i++)
            {
                g2d.draw(new java.awt.geom.Ellipse2D.Double(ancho/2-i*escala,alto/2-i*escala,2*i*escala,2*i*escala) );
            }


        }

        g2d.setColor(Color.BLACK);
        //eje y
        g2d.drawLine((ancho / 2), (2), (ancho / 2), (alto - 2));
        //eje x
        g2d.drawLine((2), (alto / 2), (ancho - 2), (alto / 2));

        g2d.setColor(oldColor);
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    //// para calcular una estrella de n puntas
    public void calcularEstrella(int nroPuntas, int lado, int salto, int sentidoDeAgregado)
    {
        //int lado = 100;

        int centroX = this.getWidth()/2;
        int centroY = this.getHeight()/2;

        double saltoAngulo = 2 * Math.PI / nroPuntas;

        // para que se vea vertical y no chueca
        double correccionAngulo = Math.PI/2 - saltoAngulo;

        v_puntos = new Vector();
        Point2D[] puntos = new Point2D[nroPuntas];

        for(int i=0; i<nroPuntas; i++)
        {
            double x = centroX + lado*Math.cos(correccionAngulo + saltoAngulo*i);
            double y = centroY + lado*Math.sin(correccionAngulo + saltoAngulo*i);

            puntos[i]=new Point2D.Double( x , y );
        }

        int auxPuntoActual = 0;
        for(int i=0; i<=nroPuntas; i++)
        {
            auxPuntoActual+=salto;//(int)(nroPuntas/2);
            auxPuntoActual=auxPuntoActual%nroPuntas;
            v_puntos.add(puntos[auxPuntoActual]);
            //System.out.println(this.getClass()+" agregando "+puntos[auxPuntoActual]);
        }

        if (sentidoDeAgregado == 0) {
            // como esta
        } else {
            // invertir los valores del vector
            v_puntos = Constants.invertiVector(v_puntos);
        }

        this.repaint();
    }

    ////para calcular una estrella de n puntas
    public void calcularEstrella1(int nroPuntas, int lado, int salto, int sentidoDeAgregado)
    {
        int centroX = this.getWidth()/2;
        int centroY = this.getHeight()/2;

        double saltoAngulo = 2 * Math.PI / nroPuntas;

        // para que se vea vertical y no chueca
        double correccionAngulo = Math.PI/2 - saltoAngulo;

        v_puntos = new Vector();
        Point2D[] puntos = new Point2D[nroPuntas];

        for(int i=0; i<nroPuntas; i++)
        {
            double x = centroX + lado*Math.cos(correccionAngulo + saltoAngulo*i);
            double y = centroY + lado*Math.sin(correccionAngulo + saltoAngulo*i);

            puntos[i]=new Point2D.Double( x , y );
// poligono de n lados
//v_puntos.add(puntos[i]);
        }
		/*
		// para tener los puntos ordenados
		Point2D[] puntos1 = new Point2D[nroPuntas+1];
		int auxPuntoActual = 0;
		for(int i=0; i<nroPuntas; i++)
		{
			auxPuntoActual+=salto;//(int)(nroPuntas/2);
			auxPuntoActual=auxPuntoActual%nroPuntas;
			puntos1[i] = puntos[auxPuntoActual];

// para dibujar la estrella con intersecciones
//v_puntos.add(puntos1[i]);
		}
		*/
        // para hallar las intersecciones entre lineas que parten de puntos adyacentes
        Point2D[] puntos3 = new Point2D[nroPuntas+1];
        if (salto>1) {
            for(int i=0; i<nroPuntas+1; i++)
            {
                puntos3[i] =  Constants.inseccionEntre(puntos[(i)%nroPuntas],
                        puntos[(i+salto)%nroPuntas],
                        puntos[(i+1)%nroPuntas],
                        puntos[(i+nroPuntas-salto+1)%nroPuntas]
                );
            }
        }


        // agregamos los puntos
        for(int i=0; i<nroPuntas; i++)
        {
            if (salto>1) {
                v_puntos.add(puntos3[i].clone());
            }
            v_puntos.add(puntos[(i+1)%nroPuntas].clone());
        }
        if (salto>1) {
            v_puntos.add(puntos3[0].clone());
        }


        if (sentidoDeAgregado == 0) {
            // como esta
        } else {
            // invertir los valores del vector
            v_puntos = Constants.invertiVector(v_puntos);
        }

        this.repaint();
    }



    //// para calcular una estrella de n puntas
    public void calcularEneagono(int nroPuntas, int lado, int salto, int sentidoDeAgregado, boolean agregarAlFinal)
    {
        Vector oldPoints = (Vector)v_puntos.clone();

        int centroX = this.getWidth()/2;
        int centroY = this.getHeight()/2;

        double saltoAngulo = 2 * Math.PI / nroPuntas;

        // para que se vea vertical y no chueca
        double correccionAngulo = Math.PI/2 - saltoAngulo;

        v_puntos = new Vector();
        Point2D[] puntos = new Point2D[nroPuntas];

        for(int i=0; i<nroPuntas; i++)
        {
            double x = centroX + lado*Math.cos(correccionAngulo + saltoAngulo*i);
            double y = centroY + lado*Math.sin(correccionAngulo + saltoAngulo*i);

            puntos[i]=new Point2D.Double( x , y );
        }

        //// addding n+1 points to the vector
        //v_puntos.add(new Point2D.Double( puntos[0].getX()+1 , puntos[0].getY()+1 ));
        int cont = 0;
        while(cont < nroPuntas)
        {
            v_puntos.add(puntos[(cont*salto)%nroPuntas]);
            cont++;
        }
        v_puntos.add(puntos[0]);

        if (sentidoDeAgregado == 0) {
            // como esta
        } else {
            // invertir los valores del vector
            v_puntos = Constants.invertiVector(v_puntos);
        }

        if (agregarAlFinal) {
            oldPoints.addAll(v_puntos);
            v_puntos = oldPoints;
        }

        this.repaint();
    }

    ////para calcular una estrella de n puntas
    public void calcularEneagono1(int nroPuntas, int lado, int salto, int sentidoDeAgregado, boolean agregarAlFinal)
    {
        Vector oldPoints = (Vector)v_puntos.clone();

        v_puntos = Constants.calcularEneagono1(this.getWidth()/2, this.getHeight()/2, nroPuntas, lado, salto);

        if (sentidoDeAgregado == 0) {
            // como esta
        } else {
            // invertir los valores del vector
            v_puntos = Constants.invertiVector(v_puntos);
        }

        if (agregarAlFinal) {
            oldPoints.addAll(v_puntos);
            v_puntos = oldPoints;
        }

        this.repaint();
    }

    public Point2D.Double getPuntoAleatorioDentro()
    {
        Shape poligono = Constants.crear_POLIGONO_CERRADO(v_puntos, true);

        Double wRnd = ( Math.random()*this.getWidth() );
        Double hRnd = ( Math.random()*this.getHeight() );

        while ( !poligono.contains(wRnd, hRnd) )
        {
            wRnd = ( Math.random()*this.getWidth() );
            hRnd = ( Math.random()*this.getHeight() );
        }

        return new Point2D.Double(wRnd, hRnd);
    }

    public Point2D.Double getPuntoAleatorioFuera()
    {
        Shape poligono = Constants.crear_POLIGONO_CERRADO(v_puntos, true);

        Double wRnd = ( Math.random()*this.getWidth() );
        Double hRnd = ( Math.random()*this.getHeight() );

        while ( poligono.contains(wRnd, hRnd) )
        {
            wRnd = ( Math.random()*this.getWidth() );
            hRnd = ( Math.random()*this.getHeight() );
        }

        return new Point2D.Double(wRnd, hRnd);
    }

    public void drawPerfectRect(Graphics g, int x, int y, int x2, int y2) {
        int px = Math.min(x,x2);
        int py = Math.min(y,y2);
        int pw=Math.abs(x-x2);
        int ph=Math.abs(y-y2);
        g.drawRect(px, py, pw, ph);
    }

    public int x_selection_1, y_selection_1, x_selection_2, y_selection_2;

    public void dibujarRectanguloDeSeleccion(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        Stroke oldStroke = ((Graphics2D)g).getStroke();
        g2d.setStroke(Constants.dashedBasicStroke);
        g.setColor(Color.cyan.darker());
//System.out.println(String.format("(%1s, %2s) --> (%3s, %4s)",
//                x_selection_1, y_selection_1, x_selection_2, y_selection_2));
        drawPerfectRect(g, x_selection_1, y_selection_1, x_selection_2, y_selection_2);
        g2d.setStroke(oldStroke);

        g2d.dispose();
    }

    public Rectangle2D getRectangle2DDouble(
            int x,
            int y,
            int x2,
            int y2
    )
    {
        int px = Math.min(x,x2);
        int py = Math.min(y,y2);
        int pw=Math.abs(x-x2);
        int ph=Math.abs(y-y2);

        return new Rectangle2D.Double(px, py, pw, ph);
    }

    public void setColorLineas(Color color)
    {
        color_lineas = color;
    }

    public Color getColorLineas()
    {
        return color_lineas;
    }

}