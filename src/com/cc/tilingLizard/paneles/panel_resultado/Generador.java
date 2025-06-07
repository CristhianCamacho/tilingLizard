package com.cc.tilingLizard.paneles.panel_resultado;

import java.awt.*;
import java.awt.geom.Line2D;

public class Generador {
    double x;
    double y;
    double angulo;    //radianes
    Color colorLine;
    Graphics g;
    public Generador(double x, double y, double angulo, Color color) {
        this.x=x;
        this.y=y;
        this.angulo=angulo*Math.PI/180;
        //this.colorLine=color;
    }
    public Generador() {
        this.x=0.0;
        this.y=0.0;
        this.angulo=0.0;
        //this.colorLine=Color.black;
    }

    public void inicia(Graphics g, Color colorLine1){
        x=0.0;
        y=0.0;
        angulo=0.0;
        this.colorLine = colorLine1;
        this.g=g;
    }

    public void colorea(Color color){
        //this.color=color;
    }

    public void gira(double angulo){
        this.angulo+=angulo*Math.PI/180;
    }

    public void giraRad(double angulo){
        this.angulo+=angulo;
    }

    public void salta(double x, double y){
        this.x=x;
        this.y=y;
    }

    public void salta(double distancia){
        double xx=x+distancia*Math.cos(angulo);
        double yy=y-distancia*Math.sin(angulo);
        salta(xx, yy);
    }

    public void traza(double distancia, boolean esPimeraOUltimaLineaRecursiva){
        double xx=x+distancia*Math.cos(angulo);
        double yy=y-distancia*Math.sin(angulo);

        if (!esPimeraOUltimaLineaRecursiva)
        {

            if (colorLine!=null){
                g.setColor(colorLine);
            }

            g.drawLine((int)xx, (int)yy, (int)x, (int)y);
            //((Graphics2D)g).setPaint(colorLine);
            //((Graphics2D)g).draw(new Line2D.Double(xx, yy, x, y));
        }
        salta(xx, yy);
    }
}
