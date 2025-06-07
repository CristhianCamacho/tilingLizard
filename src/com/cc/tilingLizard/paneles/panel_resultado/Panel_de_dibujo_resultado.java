package com.cc.tilingLizard.paneles.panel_resultado;

import com.cc.tilingLizard.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class Panel_de_dibujo_resultado extends JPanel{

    Image backgroundImage;
    BufferedImage backgroundBufferedImage;

    //color de las lineas
    Color color_lineas = Color.BLACK;
    Color color_fondo = Color.WHITE;
    Stroke stroke;

    //Hexagono
    public Vector v_puntos=new Vector();

    Point2D mSelectedPoint;
    int i_mSelectedPoint;

    boolean mostrarPuntosDeControl = true;
    boolean pintarPuntos = false;

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        //g2d.setBackground(java.awt.Color.WHITE);
        g2d.clearRect(0,0,getWidth(), getHeight());

        g.setColor(color_fondo);
        g.fillRect(0, 0, getWidth(), getHeight());

        if(backgroundImage!=null)
        {
            System.out.println("backgroundImage"+backgroundImage);
            g2d.drawImage(backgroundImage, 0, 0, null);
        }

        if (pintarPuntos)
        {
            pintar_puntos(g);
        }

        pintarPanelSizeEnLaEsquinaInferiorDerecha(g);
    }

    public void dibujar_punto(double x ,double y)
    {
        Point2D punto;
        punto= new Point2D.Double( x , y );

        for (int i = 0; i < v_puntos.size(); i++)
        {

            Shape s = Constants.get_punto_de_control((Point2D)v_puntos.get(i), Constants.TAM);
            if (s.contains(punto) &&
                    mSelectedPoint != v_puntos.get(i))
            {
                mSelectedPoint = (Point2D)v_puntos.get(i);
                i_mSelectedPoint=i;
            }
        }

        this.repaint();
    }
/*
    public void mover_punto(double x ,double y)
    {
        Point2D punto;
        punto= new Point2D.Double( x , y );

        if (mSelectedPoint != null)
        {
            mSelectedPoint.setLocation(punto);

            v_puntos.setElementAt(mSelectedPoint,i_mSelectedPoint);
            this.repaint();
        }

        Constants.verificarLiveDrawing();
    }
*/
    public void pintar_puntos(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g;

        for(int i=0;i<v_puntos.size();i++)
        {
            Point2D punto_temp_1 =(Point2D)v_puntos.get(i);

            ////
            if(mostrarPuntosDeControl)
            {
                if(punto_temp_1==mSelectedPoint)
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

        {
            if(v_puntos.size()>0)
            {
                Constants.setRenderingHConstants(g2, false);
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

                for(int i=1;i<v_puntos.size();i++)
                {
                    Point2D punto_1 =(Point2D)v_puntos.get(i);

                    if (mostrarPuntosDeControl)
                    {
                        Paint p_tmp = g2.getPaint();

                        if (i_mSelectedPoint == i){
                            g2.setPaint(new Color(153,0,0));
                        } else {
                            g2.setPaint(new Color(0,0,153));
                        }

                        g2.drawString("P("+i+")=("+Math.round(punto_0.getX())+", "+Math.round(punto_0.getY())+")",
                                (float)( punto_1.getX() + ((Math.random()>0.5)? 5 : -5 )),
                                (float)( punto_1.getY() + ((Math.random()>0.5)? 25 : -25 ) ) );
                        g2.setPaint(p_tmp);
                    }

                    punto_0=punto_1;
                }

                Constants.setRenderingHConstantsANTIALIAS_OFF(g2);
            }
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

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public BufferedImage getBackgroundBufferedImage() {
        return backgroundBufferedImage;
    }

    public void setBackgroundBufferedImage(BufferedImage backgroundBufferedImage) {
        this.backgroundBufferedImage = backgroundBufferedImage;
    }

    public void setColorLineas(Color color1)
    {
        color_lineas = color1;
    }
    public Color getColor_lineas()
    {
        return color_lineas;
        //getGraphics().setColor(color1);
    }
    public void setColor_fondo(Color color1)
    {
        color_fondo = color1;

        this.repaint();
    }
    public Color getColor_fondo()
    {
        return color_fondo;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke s)
    {
        stroke = s;
    }

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
}

