package com.cc.tilingLizard.paneles.panel_resultado;

import com.cc.tilingLizard.paneles.Elementos_UI;
import com.cc.tilingLizard.utils.Constants;

import java.awt.*;
import java.awt.geom.Point2D;

public class Crear_Algoritmo implements Runnable{

    Point2D puntos_basico[];
    double distancias_basico[];
    double dist_inicio_fin;
    double angulos_basico[];
    double angulos_en_grados_basico[];


    Point2D puntos_recursivo[];
    double distancias_recursivo[];
    double angulos_recursivo[];
    double angulos_en_grados_recursivo[];


    int orden=0;
    Generador generador;

    Graphics grafico;
    Color lineColor;

    Elementos_UI elementos_UI;

    boolean no_dibujarPrimeraLineaPatronRecursivo;

    public Crear_Algoritmo(Point2D[] pb,Point2D[] pr, Graphics g, int ni, Elementos_UI elementos_UI,
                           boolean no_dibujarPrimeraLineaPatronR,
                            Color lineColor1)
    {
        generador=new Generador();

        puntos_basico=pb;
        distancias_basico=calcular_distancias(puntos_basico);
        angulos_basico=calcular_angulos(puntos_basico,"basico");
        angulos_en_grados_basico=calcular_angulos_en_grados(angulos_basico);

        puntos_recursivo=pr;
        distancias_recursivo=calcular_distancias(puntos_recursivo);
        angulos_recursivo=calcular_angulos(puntos_recursivo,"recursivo");
        angulos_en_grados_recursivo=calcular_angulos_en_grados(angulos_recursivo);

        dist_inicio_fin=calcular_distancia_inicio_fin(puntos_recursivo);


        orden=ni;

        grafico=g;
        lineColor = lineColor1;
        this.elementos_UI=elementos_UI;

        no_dibujarPrimeraLineaPatronRecursivo = no_dibujarPrimeraLineaPatronR;

        Graphics2D g2=(Graphics2D)grafico;
        Constants.setRenderingHConstants(g2, true);
		/*
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
		*/

        generador.inicia(g, lineColor);

        //hilo.start();

    }

    public double calcular_distancia_inicio_fin(Point2D[] puntos)
    {
        if ( puntos.length == 0 )
        {
            return 0.0;
        }

        double distancia_i_f=Math.pow(
                ( Math.pow(	(puntos[0].getX()-puntos[puntos.length-1].getX()),2)+
                        Math.pow(	(puntos[0].getY()-puntos[puntos.length-1].getY()),2)
                ) , 0.5 );

        return distancia_i_f;
    }

    public double[] calcular_distancias(Point2D[] puntos)
    {
        if ( puntos.length == 0 )
        {
            return new double[]{0};
        }

        double[] distancias=new double[puntos.length-1];

        for(int i=0;i<distancias.length;i++)
        {
            distancias[i]=Math.pow(
                    ( Math.pow(	(puntos[i].getX()-puntos[i+1].getX()),2)+
                            Math.pow(	(puntos[i].getY()-puntos[i+1].getY()),2)
                    ) , 0.5 );

        }

        return distancias;
    }

    public double[] calcular_angulos(Point2D[] puntos,String tipo)
    {
        if ( puntos.length == 0 )
        {
            return new double[]{0};
        }

        double[] angulos=new double[puntos.length-1];

        Point2D a,b;
        double _x,_y;

        _x=1-0;
        _y=0;
        a=new Point2D.Double( _x , _y );
        _x=puntos[1].getX()-puntos[0].getX();
        _y=puntos[1].getY()-puntos[0].getY();
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

        angulos[0]=_s*Math.acos( aOb/(modulo_a*modulo_b) );

        //angulos[0]=-Math.atan(
        //	(puntos[1].getY()-puntos[0].getY())/(puntos[1].getX()-puntos[0].getX())  );

        ///////////////////////////////////
        //Point2D[] p_temp=null;

        //if(tipo.equals("basico"))		p_temp=puntos_basico;
        //if(tipo.equals("recursivo"))	p_temp=puntos_recursivo;
        ///////////////////////////////////

        for(int i=1;i<angulos.length;i++)
        {

            Point2D[] A=new Point2D[puntos.length];
            Point2D[] B=new Point2D[puntos.length];

            double x,y;
            x=puntos[i].getX()-puntos[i-1].getX();
            y=puntos[i].getY()-puntos[i-1].getY();
            A[i-1]=new Point2D.Double( x , y );
            x=puntos[i+1].getX()-puntos[i].getX();
            y=puntos[i+1].getY()-puntos[i].getY();
            B[i-1]=new Point2D.Double( x , y );

            double AoB=A[i-1].getX()*B[i-1].getX()+A[i-1].getY()*B[i-1].getY();

            double modulo_A=Math.pow(
                    ( Math.pow(	(A[i-1].getX()),2)+
                            Math.pow(	(A[i-1].getY()),2)
                    ) , 0.5 );

            double modulo_B=Math.pow(
                    ( Math.pow(	(B[i-1].getX()),2)+
                            Math.pow(	(B[i-1].getY()),2)
                    ) , 0.5 );


            int s=calcular_signo(A[i-1],B[i-1]);

            angulos[i]=s*Math.acos( AoB/(modulo_A*modulo_B) );

        }

        return angulos;
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

    public double[] calcular_angulos_en_grados(double[] angulos)
    {
        double[] angulos_en_grados=new double[angulos.length];

        for(int i=0;i<angulos_en_grados.length;i++)
        {
            angulos_en_grados[i]=(180/Math.PI)*angulos[i];
        }

        return angulos_en_grados;
    }
/*
    public void set_detener(boolean b)
    {
        detener=b;
    }

    public void set_continuar(boolean b)
    {
        continuar=b;
    }
*/
    //public static boolean detener=false;
    //public static boolean continuar=false;
    //Thread hilo=new Thread();

    public double createCorreccion()
    {
        if ( puntos_recursivo.length == 0 )
        {
            return 0.0;
        }
//    	if(tipo.equalsIgnoreCase("recursivo"))
        {
            Point2D a,b;
            double _x,_y;

            _x=1-0;
            _y=0;
            a=new Point2D.Double( _x , _y );
            _x=puntos_recursivo[puntos_recursivo.length-1].getX()-puntos_recursivo[0].getX();
            _y=puntos_recursivo[puntos_recursivo.length-1].getY()-puntos_recursivo[0].getY();
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



            double result = (-1)*_s*Math.acos( aOb/(modulo_a*modulo_b) );
            //return Math.toDegrees(result);
            return result;
        }

    }

    void generaKoch(int nivel, double distancia, double correccion, boolean esPimeraOUltimaLineaRecursiva)
    {
        //if (!detener)
        {
            //System.out.println("detener = "+detener);

            if(nivel==0){
                generador.traza(distancia, esPimeraOUltimaLineaRecursiva && no_dibujarPrimeraLineaPatronRecursivo);
            }else{

                // NO FUNCIONA
                // correccion para que no se dispare
                // y para el punto final de uno sea el inicial del siguiente
                // sin que tengan que estar en la misma linea horizontal
                //double correccion = createCorreccion();
                //generador.giraRad(correccion);
                /////

                for(int i=0;i<angulos_recursivo.length;i++)
                {


//        	if (i>0)
//        	{
//        		correccion = 0;
//        	}

                    //generador.gira(correccion+angulos_en_grados_recursivo[i]);
                    //generador.giraRad(correccion+angulos_recursivo[i]);
                    generador.giraRad(correccion);

                    generador.giraRad(angulos_recursivo[i]);

                    boolean esPimeraOUltimaLineaR = false;
                    if (i==0 || i==angulos_recursivo.length-1) {
                        esPimeraOUltimaLineaR = true;
                    }
                    generaKoch(nivel-1, distancias_recursivo[i]*(distancia/dist_inicio_fin), correccion, esPimeraOUltimaLineaR);

                    generador.giraRad(-correccion);

                    //if(i!=puntos_recursivo.length-2)
                    if(i<angulos_recursivo.length-1)
                    {
                        incrementarBarraDeProgresoEnUno();
                    }

                }

                for(int i=0;i<angulos_recursivo.length;i++)
                {
                    generador.giraRad(-angulos_recursivo[i]);
                }
        /*
        if(nivel==1)
        for(int i=0;i<angulos_recursivo.length;i++)
        {
        	generador.giraRad(-nivel*angulos_recursivo[i]);
        }
        */
        /*
        if(nivel==2)
        for(int i=0;i<angulos_recursivo.length;i++)
        {
          	//generador.giraRad(-2*angulos_recursivo[i]);
        	generador.giraRad(-2*correccion);
        }
        */
                //generador.giraRad(-angulos_recursivo[i1]);
                //correccion = createCorreccion();
                //generador.giraRad(-(puntos_recursivo.length-1)*correccion);
            }

        }
    }


    public void curvaKoch(){


        settearBarraDeProgresoValoresIniciales();

        //int wAlto=Toolkit.getDefaultToolkit().getScreenSize().height;
        //int wAncho=Toolkit.getDefaultToolkit().getScreenSize().width;

        double x=puntos_basico[0].getX();
        double y=puntos_basico[0].getY();

        int nivel=orden;
        generador.salta(x, y);

        double correccion = createCorreccion();

        for(int i=0;i<puntos_basico.length-1;i++)
        {
            //generador.gira(angulos_en_grados_basico[i]);
            generador.giraRad(angulos_basico[i]);
            generaKoch(nivel-1, distancias_basico[i], correccion, false);

            incrementarBarraDeProgresoEnUno();
        }

    }

    public void settearBarraDeProgresoValoresIniciales()
    {
        //// esto es para settear la barra de progreso VALORES INICIALES
        int maximo = (int)( (puntos_basico.length-1)*Math.pow(puntos_recursivo.length-1, orden-1) );
        elementos_UI.panel_resultado.progresoFractal.setMinimum(0);
        elementos_UI.panel_resultado.progresoFractal.setMaximum(maximo);
        elementos_UI.panel_resultado.progresoFractal.setValue(0);
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
        int valIni = elementos_UI.panel_resultado.progresoFractal.getValue();
        elementos_UI.panel_resultado.progresoFractal.setValue(valIni+1);

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
        int actual = elementos_UI.panel_resultado.progresoFractal.getValue();
        int maximo = elementos_UI.panel_resultado.progresoFractal.getMaximum();
        double tiempoRestante = (maximo-actual)*diferenciaParaCalcTime;

        long tiempoRestanteEnSeg = (long)(tiempoRestante/Math.pow(10,9));

        long tiempoRestanteMin = (int)(tiempoRestanteEnSeg/60);
        long tiempoRestanteSeg = (int)(tiempoRestanteEnSeg%60);

        elementos_UI.panel_resultado.tiempoRestanteFractal.setText("faltan "+tiempoRestanteMin+":"+tiempoRestanteSeg);
        //System.out.println(this.getClass().getName()+":curvaKoch() elementos_UI.panel_de_dibujo.progresoDibujo.getValue()="+elementos_UI.panel_de_dibujo.progresoFractal.getValue());
        //System.out.println(this.getClass().getName()+":curvaKoch() valIni="+valIni);
        ////
    }

    public void run()
    {
        curvaKoch();

        Constants.updatePanel_de_dibujoBackground();
    }



}
