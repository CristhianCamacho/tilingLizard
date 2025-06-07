package com.cc.tilingLizard.utils;

import com.cc.tilingLizard.paneles.Elementos_UI;
import com.cc.tilingLizard.paneles.panel_disenio.Panel_patron_disenio;
import com.cc.tilingLizard.paneles.panel_resultado.Panel_de_dibujo_resultado;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

public class Constants {

    public static File DIR_ACTUAL;
    public static File DIR_DE_PROPIEDADES;

    public static File Last_DIR_used = null;

    public static void set_Directorios()
    {
        DIR_ACTUAL = new File(System.getProperty("user.dir"));

        DIR_DE_PROPIEDADES = new File(DIR_ACTUAL, "properties");
    }

    public static String COLOCAR_PUNTOS = "colocar_puntos";
    public static String MOVER_PUNTOS = "mover_puntos";
    public static String BORRAR_PUNTOS = "borrar_puntos";
    public static String BORRAR_TODO = "borrar_todo";

    public static final String ABRIR = "Abrir";
    public static final String GUARDAR = "Guardar";
    public static final String GUARDAR_COMO_PNG = "Guardar como PNG";
    public static final String SALIR = "Salir";

    public static final String LOS_ARCHIVOS_SE_PERDERAN_DESEA_SALIR_DE_TODAS_FORMAS =
            "Los Archivos que no se hayan guardado se perderan  \n desea Salir de todas formas?";

    public static int dimensionPrefPanel_de_controles_width = 80;
    public static int splitDividerLoation = 100;

    public static String AL_INICIO = "Al inicio";
    public static String AL_FINAL = "Al final";

    public static String ULTIMA_DISTANCIA = "Ult_dist";

    public static int TAM = 10;

    public static String RutinaActual = "";
    public static String RUTINA_1 = "RUTINA_1";

    ////
    public static BasicStroke dashedBasicStroke =
            new BasicStroke(
                    2,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL,
                    5,
                    new float[] { 5 },
                    5);

    public static final float InitialBasicStrokeWidth = 3;//1;
    public static final float InitialBasicStrokeMiterlimit = 3;
    public static final float[] InitialBasicStrokeDashArray = new float[] { 0, 1 };
    public static final float InitialBasicStrokeDash_phase = 0;
    public static BasicStroke initialBasicStroke =
            new BasicStroke(
                    InitialBasicStrokeWidth,
                    BasicStroke.CAP_ROUND, //BasicStroke.CAP_SQUARE,
                    BasicStroke.JOIN_ROUND, //BasicStroke.JOIN_MITER,
                    InitialBasicStrokeMiterlimit,
                    InitialBasicStrokeDashArray,
                    InitialBasicStrokeDash_phase);

    public static BasicStroke getInitialBasicStroke()
    {
        return initialBasicStroke;
    }

    public static Vector calcularEneagono1(
            int centroX,
            int centroY,
            int nroPuntas,
            int lado,
            int salto)
    {


        double saltoAngulo =2 * Math.PI / nroPuntas;

        // para que se vea vertical y no chueca
        double correccionAngulo = Math.PI/2 - saltoAngulo;

        Vector v_puntos = new Vector();
        Point2D[] puntos = new Point2D[nroPuntas];

        for(int i=0; i<nroPuntas; i++)
        {
            double _cos = Math.cos(correccionAngulo + saltoAngulo*i);
            double _sin = Math.sin(correccionAngulo + saltoAngulo*i);

            double x = centroX + lado*_cos;
            double y = centroY + lado*_sin;

            puntos[i]=new Point2D.Double( x , y );

        }


//		para hallar las intersecciones entre lineas que parten de puntos adyacentes
        Point2D[] puntos3 = new Point2D[nroPuntas+1];
        if (salto>1) {
            for (int i = 0; i < nroPuntas + 1; i++) {

                if (i+salto==7) {
                    int a = 0;
                }

                puntos3[i] = inseccionEntre(puntos[(i) % nroPuntas],
                        puntos[(i + salto) % nroPuntas],
                        puntos[(i + 1) % nroPuntas],
                        puntos[(nroPuntas + i + 1 - salto) % nroPuntas]
                );
            }
        }

        int i_real = 0;
        // agregamos los puntos
        for(int i=0; i<nroPuntas; i++)
        {
            v_puntos.add(puntos[(i)%nroPuntas].clone());
            System.out.println("P("+(i_real++)+")= ("+puntos[(i)%nroPuntas].getX()+", "
                    +puntos[(i)%nroPuntas].getY()+")");
            if (salto>1) {
                v_puntos.add(puntos3[(i) % nroPuntas].clone());
                System.out.println("P("+(i_real++)+")= ("+puntos3[(i)%nroPuntas].getX()+", "
                        +puntos3[(i)%nroPuntas].getY()+")");
            }
            //v_puntos.add(puntos3[(i)%nroPuntas].clone());
            //v_puntos.add(puntos[(i+1)%nroPuntas].clone());
        }
        v_puntos.add(puntos[0].clone());
        System.out.println("P("+0+")= P("+(2*nroPuntas)+") = ("+puntos[(0)%nroPuntas].getX()+", "
                +puntos[(0)%nroPuntas].getY()+")");

        return v_puntos;
    }

    public static Shape crear_POLIGONO_CERRADO_LinkedList(LinkedList<Point2D> lista_Point2D)
    {
        int n=lista_Point2D.size();
        float[][] mPoints=new float[2][n];

        Iterator<Point2D> it = lista_Point2D.iterator();
        Point2D aux;
        int cont=0;
        while(it.hasNext())
        {
            aux = it.next();
            if(aux instanceof Point2D)
            {
                mPoints[0][cont]=(float)aux.getX();
                mPoints[1][cont]=(float)aux.getY();
            }
            cont++;
        }

        GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO,
                mPoints.length);

        path.moveTo(mPoints[0][0], mPoints[1][0]);

        for (int i = 0; i < n ; i += 1)
        {
            path.lineTo(mPoints[0][i], mPoints[1][i]);
        }
        path.closePath();

        return path;
    }

    public static Shape get_punto_de_control(Point2D p, int tam)
    {
        int lado=tam;
        return new Rectangle2D.Double( p.getX()-lado/2 , p.getY()-lado/2 , lado , lado );
    }

    public static void verificarLiveDrawing()
    {
        //
        if( Elementos_UI.getInstance().panel_resultado.check_liveDrawing.isSelected() /*&& !Elementos_UI.getInstance().getCalculandoLizard()*/ ) {

            if (Math.random()>0.75)
            {
                Elementos_UI.getInstance().clear();
            }
            else
            {
                Elementos_UI.getInstance().calcular_lizard(true);
            }
        }
    }

    public static boolean contieneElIndice(LinkedList<Integer> index_ListSelectedPoints, int i)
    {
        boolean result = false;
        for (Integer inte: index_ListSelectedPoints) {
            if (inte.intValue() == i) {
                result = true;
                break;
            }
        }

        return result;
    }

    public static void setRenderingHConstants(Graphics2D g2d, boolean all) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        if (all)
        {
            //g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
            //g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));

            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            //g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);

            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE );
        }
    }

    public static void setRenderingHConstantsANTIALIAS_OFF(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF));
    }

    public static Point2D inseccionEntre(Point2D punto1,
                                         Point2D punto2,
                                         Point2D punto3,
                                         Point2D punto4)
    {
        Point2D result = null;

        double m1;
        if( (punto1.getX() - punto2.getX())==0 )
        {
            m1 = 0;
        }
        else
        {
            m1 = ( punto1.getY() - punto2.getY() ) /
                    ( punto1.getX() - punto2.getX() );

        }

        double m2;
        if( (punto3.getX() - punto4.getX())==0 )
        {
            m2 = 0;
        }
        else
        {
            m2 = ( punto3.getY() - punto4.getY() ) /
                    ( punto3.getX() - punto4.getX() );

        }



        double x = ( m1 * punto2.getX() - punto2.getY() -
                m2 * punto4.getX() + punto4.getY()   ) /
                ( m1 - m2 );

        double y = m1 * (x - punto2.getX()) + punto2.getY();


        if ( m1 > 4.97582161916076E10 ) { // inifinite?
//x=punto3.getX();
            y = m2 * (punto2.getX() - punto3.getX()) + punto3.getY();
        }

        if ( m1==0 && m2>0.0) {
            x = punto2.getX();
            y = m2 * (x - punto3.getX()) + punto3.getY();
        }

        if ( m1<-0.0 && m2==0) {
            x = punto3.getX();
            y = m1 * (x - punto2.getX()) + punto2.getY();
        }

        if ( m1==-0.0 && Double.isInfinite(m2)) {
            x = punto3.getX();
            y = punto2.getY();
        }

        if ( m1==0.0 && m2==0) {
            x = punto2.getX();
            y = punto3.getY();
        }

        System.out.println("m1="+m1+", m2="+m2 + " --> ("+x+", "+y+")");


        result = new Point2D.Double(x, y);

        return result;
    }

    public static Shape crear_POLIGONO_CERRADO(Vector v_puntos, boolean cerrado)
    {
        int n=v_puntos.size();
        float[][] mPoints=new float[2][n];

        Iterator<Point2D> it = v_puntos.iterator();
        Point2D aux;
        int cont=0;
        while(it.hasNext())
        {
            aux = it.next();
            if(aux instanceof Point2D)
            {
                mPoints[0][cont]=(float)aux.getX();
                mPoints[1][cont]=(float)aux.getY();
            }
            cont++;
        }

        GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO,
                mPoints.length);

        path.moveTo(mPoints[0][0], mPoints[1][0]);

        for (int i = 0; i < n ; i += 1)
        {
            path.lineTo(mPoints[0][i], mPoints[1][i]);
        }
        if (cerrado)
        {
            path.closePath();
        }

        return path;
    }

    public static int getMaxSalto(int n)
    {
        int iReturn = 1;

        //int n = Integer.parseInt((String)jcb_NroDePuntas.getSelectedItem());

        if(n%2==1)
        {
            iReturn = (int)(n/2);
        }
        else
        {
            iReturn = (int)(n/2) - 1;
        }

        return iReturn;
    }

    public static void DETENER_TODO()
    {
        Elementos_UI.getInstance().setCalculandoLizard(false);
        //Elementos_UI.getInstance().detenerHilo();

        Elementos_UI.getInstance().detener_tiling();
    }

    public static String concatenar_hasta(String f, String tope)
    {
        String frase=f;
        String salida="";
        int cont=1;

        while( !(frase.substring(cont-1,cont).equalsIgnoreCase(tope)) )
        {
            salida=salida+frase.substring(cont-1,cont);
            cont++;
        }

        System.out.println("Elementos_UI:concatenar_hasta:salida"+salida);

        return salida;
    }

    public static void updatePanel_de_dibujoBackground()
    {
        Panel_de_dibujo_resultado panel_de_dibujo_resultado = Elementos_UI.getInstance().panel_resultado.panel_de_dibujo;

        try {

            Rectangle rectangle = new Rectangle(panel_de_dibujo_resultado.getLocationOnScreen().x,
                    panel_de_dibujo_resultado.getLocationOnScreen().y,
                    panel_de_dibujo_resultado.getSize().width,
                    panel_de_dibujo_resultado.getSize().height);
            Robot robot;
            try {
                robot = new Robot();
                BufferedImage bufferedImage = robot.createScreenCapture(rectangle);

                Image image = Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
                panel_de_dibujo_resultado.setBackgroundBufferedImage(bufferedImage);
                panel_de_dibujo_resultado.setBackgroundImage(image);

            } catch (Exception e) {

                System.out.println(e);
            }

            Elementos_UI.getInstance().setCalculandoLizard(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector aplicarRotacion(Vector v_puntos, int rotarGrados, int TAM)
    {
        Point2D punto_centro_de_gravedad = getCentroDeGravedad(v_puntos, TAM);

        Vector v_result = new Vector();
        for(int i=0; i<v_puntos.size(); i++)
        {
            Point2D punto_i = (Point2D) v_puntos.get(i);
            Double alpha_radianes = Math.toRadians( rotarGrados );

//System.out.println("Constants:aplicarRotacion alpha_radianes="+alpha_radianes);

            Double x = punto_centro_de_gravedad.getX() +
                    (punto_i.getX() - punto_centro_de_gravedad.getX() )*Math.cos( alpha_radianes )
                    - (punto_i.getY() - punto_centro_de_gravedad.getY() )*Math.sin( alpha_radianes );
            Double y = punto_centro_de_gravedad.getY() +
                    (punto_i.getX() - punto_centro_de_gravedad.getX() )*Math.sin( alpha_radianes )
                    + (punto_i.getY() - punto_centro_de_gravedad.getY() )*Math.cos( alpha_radianes );

            Point2D punto_fin = new Point2D.Double( x,y);
            v_result.add(punto_fin);
            //v_puntos.setElementAt(punto_fin, i);
        }

        return v_result;
    }

    public static Vector aplicarTraslacion(Vector v_puntos, Point2D moverVector, int TAM)
    {
        Vector v_result = new Vector();
        for(int i=0; i<v_puntos.size(); i++)
        {
            Point2D punto_i = (Point2D) v_puntos.get(i);
            Double x = punto_i.getX() + moverVector.getX();
            Double y = punto_i.getY() + moverVector.getY();

            Point2D punto_fin = new Point2D.Double( x,y);
            v_result.add(punto_fin);
        }

        return v_result;
    }

    public static Point2D getCentroDeGravedad(Vector v_puntos, int TAM)
    {
        Point2D result = null;
        double x_centro_de_gravedad = 0;
        double y_centro_de_gravedad = 0;

        int max_i=v_puntos.size();
        if(!v_puntos.isEmpty())
            if(get_punto_de_control((Point2D)v_puntos.get(0), TAM)
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

    public static Point2D[] subArrayPoint2D(Point2D[] sourceArr, int start, int end)
    {
        Point2D[] subArr = new Point2D[ 1 + end - start];

        for (int i = 0; i <= end - start; i++){
            subArr[i] = sourceArr[i + start];
        }

        return subArr;
    }

    public static Point2D[] invertiArregloPoint2D(Point2D[] sourceArr)
    {
        Point2D[] res = new Point2D[sourceArr.length];

        for (int i = 0; i < sourceArr.length; i++) {
            res[i] = sourceArr[sourceArr.length-i-1];
        }

        return res;
    }

    public static Vector invertiVector(Vector v_a_invertir)
    {
        Vector v_result = new Vector();
        for(int i = v_a_invertir.size()-1 ; i >= 0  ; i--)
        {
            v_result.add(v_a_invertir.get(i));
        }

        return v_result;
    }

    public static void leerLadosYDirecciones(Panel_patron_disenio panel_patron_XX, Vector v, int tam_p, int contador)
    {

        /** leer los lados **/
        String lado_1 = (String)v.get(tam_p+1 +contador);
        for(int j=1;j<lado_1.length();j++)
        {
            int lado1Lenght = "lado1=".length()-1;
            if ( lado_1.substring(j-1,j+lado1Lenght).equalsIgnoreCase("lado1=") )
            {
                String isTrue = Constants.concatenar_hasta( lado_1.substring(j+lado1Lenght,lado_1.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_1.jchb_lado, isTrue);
                break;
            }
        }
        String lado_2 = (String)v.get(tam_p+2 +contador);
        for(int j=1;j<lado_2.length();j++)
        {
            int lado2Lenght = "lado2=".length()-1;
            if ( lado_2.substring(j-1,j+lado2Lenght).equalsIgnoreCase("lado2=") )
            {
                String isTrue = Constants.concatenar_hasta( lado_2.substring(j+lado2Lenght,lado_2.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_2.jchb_lado, isTrue);
                break;
            }
        }
        String lado_3 = (String)v.get(tam_p+3 +contador);
        for(int j=1;j<lado_3.length();j++)
        {
            int lado3Lenght = "lado3=".length()-1;
            if ( lado_3.substring(j-1,j+lado3Lenght).equalsIgnoreCase("lado3=") )
            {
                String isTrue = Constants.concatenar_hasta( lado_3.substring(j+lado3Lenght,lado_3.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_3.jchb_lado, isTrue);
                break;
            }
        }
        String lado_4 = (String)v.get(tam_p+4 +contador);
        for(int j=1;j<lado_4.length();j++)
        {
            int lado4Lenght = "lado4=".length()-1;
            if ( lado_4.substring(j-1,j+lado4Lenght).equalsIgnoreCase("lado4=") )
            {
                String isTrue = Constants.concatenar_hasta( lado_4.substring(j+lado4Lenght,lado_4.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_4.jchb_lado, isTrue);
                break;
            }
        }
        String lado_5 = (String)v.get(tam_p+5 +contador);
        for(int j=1;j<lado_5.length();j++)
        {
            int lado5Lenght = "lado5=".length()-1;
            if ( lado_5.substring(j-1,j+lado5Lenght).equalsIgnoreCase("lado5=") )
            {
                String isTrue = Constants.concatenar_hasta( lado_5.substring(j+lado5Lenght,lado_5.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_5.jchb_lado, isTrue);
                break;
            }
        }
        String lado_6 = (String)v.get(tam_p+6 +contador);
        for(int j=1;j<lado_6.length();j++)
        {
            int lado6Lenght = "lado6=".length()-1;
            if ( lado_6.substring(j-1,j+lado6Lenght).equalsIgnoreCase("lado6=") )
            {
                String isTrue = Constants.concatenar_hasta( lado_6.substring(j+lado6Lenght,lado_6.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_6.jchb_lado, isTrue);
                break;
            }
        }
        /** END **/
        /** leer las direcciones **/
        String direccion_1 = (String)v.get(tam_p+7 +contador);
        for(int j=1;j<direccion_1.length();j++)
        {
            int dirlado1Lenght = "dirlado1=".length()-1;
            if ( direccion_1.substring(j-1,j+dirlado1Lenght).equalsIgnoreCase("dirlado1=") )
            {
                String isTrue = Constants.concatenar_hasta( direccion_1.substring(j+dirlado1Lenght,direccion_1.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_1.dir_lado, isTrue);
                break;
            }
        }
        String direccion_2 = (String)v.get(tam_p+8 +contador);
        for(int j=1;j<direccion_2.length();j++)
        {
            int dirlado2Length = "dirlado2=".length()-1;
            if ( direccion_2.substring(j-1,j+dirlado2Length).equalsIgnoreCase("dirlado2=") )
            {
                String isTrue = Constants.concatenar_hasta( direccion_2.substring(j+dirlado2Length,direccion_2.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_2.dir_lado, isTrue);
                break;
            }
        }
        String direccion_3 = (String)v.get(tam_p+9 +contador);
        for(int j=1;j<direccion_3.length();j++)
        {
            int dirlado3Lenght = "dirlado3=".length()-1;
            if ( direccion_3.substring(j-1,j+dirlado3Lenght).equalsIgnoreCase("dirlado3=") )
            {
                String isTrue = Constants.concatenar_hasta( direccion_3.substring(j+dirlado3Lenght,direccion_3.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_3.dir_lado, isTrue);
                break;
            }
        }
        String direccion_4 = (String)v.get(tam_p+10 +contador);
        for(int j=1;j<direccion_4.length();j++)
        {
            int dirlado4Lenght = "dirlado4=".length()-1;
            if ( direccion_4.substring(j-1,j+dirlado4Lenght).equalsIgnoreCase("dirlado4=") )
            {
                String isTrue = Constants.concatenar_hasta( direccion_4.substring(j+dirlado4Lenght,direccion_4.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_4.dir_lado, isTrue);
                break;
            }
        }
        String direccion_5 = (String)v.get(tam_p+11 +contador);
        for(int j=1;j<direccion_5.length();j++)
        {
            int dirlado5Lenght = "dirlado5=".length()-1;
            if ( direccion_5.substring(j-1,j+dirlado5Lenght).equalsIgnoreCase("dirlado5=") )
            {
                String isTrue = Constants.concatenar_hasta( direccion_5.substring(j+dirlado5Lenght,direccion_5.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_5.dir_lado, isTrue);
                break;
            }
        }
        String direccion_6 = (String)v.get(tam_p+12 +contador);
        for(int j=1;j<direccion_6.length();j++)
        {
            int dirlado6Lenght = "dirlado6=".length()-1;
            if ( direccion_6.substring(j-1,j+dirlado6Lenght).equalsIgnoreCase("dirlado6=") )
            {
                String isTrue = Constants.concatenar_hasta( direccion_6.substring(j+dirlado6Lenght,direccion_6.length()) , "_" );
                isTrueString(panel_patron_XX.panel_de_dibujo.jc_lado_6.dir_lado, isTrue);
                break;
            }
        }
        /** END **/
    }

    public static void isTrueString(JCheckBox jcheck, String isTrue)
    {
        switch (isTrue)
        {
            case "true":
                jcheck.setSelected(true);
                break;
            case "false":
                jcheck.setSelected(false);
                break;
            default:
                break;
        }
    }

}
