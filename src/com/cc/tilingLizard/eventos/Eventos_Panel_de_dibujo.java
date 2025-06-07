package com.cc.tilingLizard.eventos;

import com.cc.tilingLizard.command.ListaDeAcciones;
import com.cc.tilingLizard.command.MouseClickedCommand;
import com.cc.tilingLizard.command.MouseDraggedCommand;
import com.cc.tilingLizard.paneles.panel_disenio.Panel_patron_disenio;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class Eventos_Panel_de_dibujo implements InternalFrameListener,MouseListener,MouseMotionListener,KeyListener,ListSelectionListener
{
    Panel_patron_disenio panel_patron;

    public Eventos_Panel_de_dibujo(Panel_patron_disenio panel_patron_)
    {
        this.panel_patron = panel_patron_;
    }
    public void actionPerformed(ActionEvent e)
    {
    }

    public void internalFrameDeactivated(InternalFrameEvent ife)
    {
        //System.out.println (ife);
    }

    public void internalFrameActivated(InternalFrameEvent ife)
    {
        //elementos_ui.pintar_puntos();
        //System.out.println (ife);
    }

    public void internalFrameDeiconified(InternalFrameEvent ife)
    {
        //System.out.println (ife);
    }

    public void internalFrameIconified(InternalFrameEvent ife)
    {
        //System.out.println (ife);
    }

    public void internalFrameClosed(InternalFrameEvent ife)
    {
        //System.out.println (ife);
    }

    public void internalFrameOpened(InternalFrameEvent ife)
    {
        panel_patron.pintar_puntos();
        //System.out.println (ife);
    }

    public void internalFrameOpening(InternalFrameEvent ife)
    {
        //System.out.println (ife);
    }

    public void internalFrameClosing(InternalFrameEvent ife)
    {
        //System.out.println (ife);
    }

    //MouseListener
    public void mouseClicked(MouseEvent e)
    {
        MouseClickedCommand mouseClickedCommand = new MouseClickedCommand(panel_patron,
                e.getPoint().getX(),
                e.getPoint().getY() );
        mouseClickedCommand.execute();
        ListaDeAcciones.getInstance().undoStackPush(mouseClickedCommand);
    }

    public void mouseEntered(MouseEvent e)
    {
        //System.out.println ( "mouse entro" );
    }
    public void mouseExited(MouseEvent e)
    {
        //System.out.println ( "mouse salio" );
    }
    public void mousePressed(MouseEvent e)
    {

        panel_patron.set_punto_inicial_puntos(null);
    }
    public void mouseReleased(MouseEvent e)
    {
        //System.out.println ( e.getSource() );
    }

    //MouseMotionListener
    public void mouseMoved(MouseEvent e)
    {
        panel_patron.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseDragged(MouseEvent e)
    {
        MouseDraggedCommand mouseDraggedCommand = new MouseDraggedCommand(panel_patron,
                e.getPoint().getX(),
                e.getPoint().getY() );
        mouseDraggedCommand.execute();
        ListaDeAcciones.getInstance().undoStackPush(mouseDraggedCommand);
    }

    public void keyPressed(KeyEvent e)
    {
        System.out.println ( e.getSource() );
    }

    public void keyReleased(KeyEvent e){
        //	System.out.println ( e );
    }
    public void keyTyped(KeyEvent e){
        System.out.println ( e );
    }

    public void valueChanged(ListSelectionEvent e)
    {
        //System.out.println ( e.getSource() );
    }

}