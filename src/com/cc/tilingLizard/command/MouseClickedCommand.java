package com.cc.tilingLizard.command;

import com.cc.tilingLizard.paneles.panel_disenio.Panel_patron_disenio;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Vector;

public class MouseClickedCommand implements Command{

	Panel_patron_disenio panel_patron;
	
	double puntoX_valorOriginal; 
	double puntoY_valorOriginal;
	
	double puntoX_valorNuevo; 
	double puntoY_valorNuevo;
	
	boolean borrar_puntos_valorOriginal;
	boolean mas_puntos_valorOriginal;
	boolean mover_puntos_valorOriginal;
	boolean borrar_todo_valorOriginal;
	
	Point2D pointAnterior; 
	int i_pointAnterior;
		
	public MouseClickedCommand(Panel_patron_disenio panel_patron,
							   double puntoX,
							   double puntoY
							   )
	{
		puntoX_valorNuevo = puntoX;
		puntoY_valorNuevo = puntoY;
		
		this.panel_patron = panel_patron;
		panel_patron.panel_de_dibujo.requestFocus();
		
		borrar_puntos_valorOriginal = panel_patron.panel_de_dibujo.borrar_puntos;
		mas_puntos_valorOriginal = panel_patron.panel_de_dibujo.mas_puntos;
		mover_puntos_valorOriginal = panel_patron.panel_de_dibujo.mover_puntos;
		borrar_todo_valorOriginal = panel_patron.panel_de_dibujo.borrar_todo;
	}
	
	public void execute() {
		
		if(borrar_puntos_valorOriginal)
		{
			pointAnterior = new Point2D.Double(puntoX_valorNuevo,puntoY_valorNuevo);
			i_pointAnterior = panel_patron.panel_de_dibujo.pos_de_este_punto_en_la_lista(puntoX_valorNuevo,puntoY_valorNuevo);
		}
		
		//panel_patron.dibujar_punto(e.getPoint().getX(),e.getPoint().getY());//si agregamos puntos
		panel_patron.dibujar_punto(puntoX_valorNuevo,puntoY_valorNuevo);
		panel_patron.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	public void undo() {
		//puntoX_valorOriginal = puntoX_valorNuevo;
		//puntoY_valorNuevo = puntoY_valorNuevo;
		if(mas_puntos_valorOriginal)
		{
			panel_patron.panel_de_dibujo.borrar_puntos = true;
			panel_patron.panel_de_dibujo.mas_puntos = false;
			panel_patron.panel_de_dibujo.mover_puntos = false;
			panel_patron.panel_de_dibujo.borrar_todo = false;
			
			panel_patron.dibujar_punto(puntoX_valorNuevo,puntoY_valorNuevo);
			
			panel_patron.panel_de_dibujo.borrar_puntos = false;
			panel_patron.panel_de_dibujo.mas_puntos = true;
			panel_patron.panel_de_dibujo.mover_puntos = false;
			panel_patron.panel_de_dibujo.borrar_todo = false;
		}
		else
		if(borrar_puntos_valorOriginal)
		{
			panel_patron.panel_de_dibujo.v_puntos.insertElementAt(pointAnterior, i_pointAnterior);
			panel_patron.panel_de_dibujo.repaint();
		}/*
		else
		if(borrar_todo_valorOriginal)
		{
			panel_patron.panel_de_dibujo.borrar_puntos = false;
			panel_patron.panel_de_dibujo.mas_puntos = false;
			panel_patron.panel_de_dibujo.mover_puntos = false;
			panel_patron.panel_de_dibujo.borrar_todo = true;
			
			panel_patron.dibujar_punto(puntoX_valorNuevo,puntoY_valorNuevo);
			panel_patron.panel_de_dibujo.repaint();
		}*/
		
		
		panel_patron.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	public void redo() {
		
		System.out.println(this.getClass()+" borrar_todo_valorOriginal="+borrar_todo_valorOriginal);
		
		if(borrar_todo_valorOriginal)
		{
//			panel_patron.panel_de_dibujo.borrar_puntos = false;
//			panel_patron.panel_de_dibujo.mas_puntos = false;
//			panel_patron.panel_de_dibujo.mover_puntos = false;
//			panel_patron.panel_de_dibujo.borrar_todo = true;
//			
			panel_patron.panel_de_dibujo.v_puntos = new Vector();
			//panel_patron.dibujar_punto(0,0);
			//panel_patron.dibujar_punto(puntoX_valorNuevo,puntoY_valorNuevo);
			/*
			panel_patron.panel_de_dibujo.borrar_puntos = false;
			panel_patron.panel_de_dibujo.mas_puntos = false;
			panel_patron.panel_de_dibujo.mover_puntos = false;
			panel_patron.panel_de_dibujo.borrar_todo = false;
			*/
		}
		else
		{
			panel_patron.dibujar_punto(puntoX_valorNuevo,puntoY_valorNuevo);
		}
		
	}

}
