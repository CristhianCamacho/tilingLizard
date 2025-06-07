package com.cc.tilingLizard;

import com.cc.tilingLizard.paneles.Elementos_UI;
import com.cc.tilingLizard.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    JFrame jf_principal;
    Elementos_UI elementos_UI;

    public static void main(String[] args) {
        new Main();
    }

    public Main()
    {
        this.crearJFrame("Tiling Lizard");
    }

    public void crearJFrame(String s)
    {
        jf_principal=new JFrame(s);
        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
        jf_principal.setSize(new Dimension(dim.width-100,dim.height-100));
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Constants.set_Directorios();
                createUI();
            }
        });
    }

    public void createUI()
    {

        elementos_UI = new Elementos_UI(jf_principal);
        elementos_UI.center();

        jf_principal.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //dispose();
                System.exit(0);
            }
        });


        jf_principal.setVisible(true);

    }
}
