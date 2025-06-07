package com.cc.tilingLizard.eventos;

import com.cc.tilingLizard.command.ListaDeAcciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class EventosRedoUndo implements ActionListener{


    public EventosRedoUndo(JMenuItem redo, JMenuItem undo)
    {
        if(redo == null)
        {
            ListaDeAcciones.getInstance().setUndoMenu(undo);
        }
        if(undo == null)
        {
            ListaDeAcciones.getInstance().setRedoMenu(redo);
        }

    }

    public void actionPerformed(ActionEvent arg0) {

        JMenuItem jmi =	(JMenuItem) arg0.getSource();
        String str = jmi.getText();

        if(str.equalsIgnoreCase("Redo"))
        {
            ListaDeAcciones.getInstance().redo();
        }

        if(str.equalsIgnoreCase("Undo"))
        {
            ListaDeAcciones.getInstance().undo();
        }
    }
}
