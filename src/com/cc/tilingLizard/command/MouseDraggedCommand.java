package com.cc.tilingLizard.command;

import com.cc.tilingLizard.paneles.panel_disenio.Panel_patron_disenio;

import java.awt.*;

public class MouseDraggedCommand implements Command{

	Panel_patron_disenio panel_patron;
	
	double puntoX_valorOriginal; 
	double puntoY_valorOriginal;
	
	double puntoX_valorNuevo; 
	double puntoY_valorNuevo;
	
	boolean borrar_puntos_valorOriginal;
	boolean mas_puntos_valorOriginal;
	boolean mover_puntos_valorOriginal;
	boolean borrar_todo_valorOriginal;
	
	boolean esta_este_punto_en_la_lista = true;
	
	public MouseDraggedCommand(Panel_patron_disenio panel_patron,
                               double puntoX,
                               double puntoY)
	{
		puntoX_valorNuevo = puntoX;
		puntoY_valorNuevo = puntoY;
		
		this.panel_patron = panel_patron;
		
		borrar_puntos_valorOriginal = panel_patron.panel_de_dibujo.borrar_puntos;
		mas_puntos_valorOriginal = panel_patron.panel_de_dibujo.mas_puntos;
		mover_puntos_valorOriginal = panel_patron.panel_de_dibujo.mover_puntos;
		borrar_todo_valorOriginal = panel_patron.panel_de_dibujo.borrar_todo;
		
		esta_este_punto_en_la_lista = panel_patron.esta_este_punto_en_la_lista(puntoX_valorNuevo, puntoY_valorNuevo);
		
	}
	
	public void execute() {

//		System.out.println("mouseDragged");
		if(mover_puntos_valorOriginal)
		{
			if (esta_este_punto_en_la_lista && mover_puntos_valorOriginal) {
				panel_patron.mover_punto(puntoX_valorNuevo, puntoY_valorNuevo);
			}
		}
		else
		// esto es para hacer un drag de todos los puntos
		{
			panel_patron.mover_todos_los_puntos(puntoX_valorNuevo, puntoY_valorNuevo);
		}
		//System.out.println ( e.getSource() );
		panel_patron.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

	public void undo() {
		
		if(esta_este_punto_en_la_lista && mover_puntos_valorOriginal)
		{
			panel_patron.panel_de_dibujo.borrar_puntos = false;
			panel_patron.panel_de_dibujo.mas_puntos = false;
			panel_patron.panel_de_dibujo.mover_puntos = true;
			panel_patron.panel_de_dibujo.borrar_todo = false;
			
			panel_patron.dibujar_punto(puntoX_valorNuevo,puntoY_valorNuevo);
			panel_patron.mover_punto(puntoX_valorNuevo, puntoY_valorNuevo);
			
			panel_patron.panel_de_dibujo.borrar_puntos = borrar_puntos_valorOriginal;
			panel_patron.panel_de_dibujo.mas_puntos = mas_puntos_valorOriginal;
			panel_patron.panel_de_dibujo.mover_puntos = mover_puntos_valorOriginal;
			panel_patron.panel_de_dibujo.borrar_todo = borrar_todo_valorOriginal;
		}
		else
		// esto es para hacer un drag de todos los puntos
		{
			panel_patron.mover_todos_los_puntos(puntoX_valorNuevo, puntoY_valorNuevo);
		}
		
	}

	public void redo() {
		
		if(esta_este_punto_en_la_lista && mover_puntos_valorOriginal)
		{
			panel_patron.mover_punto(puntoX_valorNuevo, puntoY_valorNuevo);
		}
		else
		// esto es para hacer un drag de todos los puntos
		{
			panel_patron.mover_todos_los_puntos(puntoX_valorNuevo, puntoY_valorNuevo);
		}
		
	}

}
