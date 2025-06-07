package com.cc.tilingLizard.eventos;

import com.cc.tilingLizard.paneles.panel_disenio.Panel_de_dibujo;
import com.cc.tilingLizard.paneles.panel_disenio.Panel_patron_disenio;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EventosKeyListenerMoverTodosLosPuntos implements KeyListener {

    Panel_patron_disenio panel_patron_inicial;

    public EventosKeyListenerMoverTodosLosPuntos(Panel_patron_disenio panel_patron_dis)
    {
        panel_patron_inicial = panel_patron_dis;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(" : keyTyped()="+ this.getClass() + e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(" : keyPressed()="+ this.getClass() + e.getKeyChar());

        if  (e.getKeyCode() == KeyEvent.VK_UP ||
                e.getKeyCode() == KeyEvent.VK_DOWN ||
                e.getKeyCode() == KeyEvent.VK_RIGHT ||
                e.getKeyCode() == KeyEvent.VK_LEFT
        ) {

            panel_patron_inicial.mover_todos_los_puntos(e.getKeyCode());
            e.getSource();
            /*
            if (e.getSource() instanceof Panel_de_dibujo)
            {
                ((Panel_de_dibujo)e.getSource()).mover_todos_los_puntos(e.getKeyCode());
            }
            */
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println(" : keyReleased()="+ this.getClass() + e.getKeyChar());
    }
}
