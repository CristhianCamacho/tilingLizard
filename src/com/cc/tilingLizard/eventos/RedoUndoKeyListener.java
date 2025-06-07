package com.cc.tilingLizard.eventos;

import com.cc.tilingLizard.command.ListaDeAcciones;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RedoUndoKeyListener implements KeyListener{

    int vk_anterior = 0;

    public void keyReleased(KeyEvent e)
    {}

    public void keyPressed(KeyEvent e)
    {
//		System.out.println("e.getKeyChar()="+e.getKeyChar());

        if(e.getKeyCode() == KeyEvent.VK_Z && vk_anterior == KeyEvent.VK_CONTROL)
            ListaDeAcciones.getInstance().undo();

        if(e.getKeyCode() == KeyEvent.VK_Y && vk_anterior == KeyEvent.VK_CONTROL)
            ListaDeAcciones.getInstance().redo();

        if(vk_anterior != KeyEvent.VK_CONTROL)

            vk_anterior = e.getKeyCode();

    }

    public void keyTyped(KeyEvent e)
    {}
}

