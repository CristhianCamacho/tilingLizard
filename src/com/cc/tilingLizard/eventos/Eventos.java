package com.cc.tilingLizard.eventos;

import com.cc.tilingLizard.IO.Abrir_lizard_tiling;
import com.cc.tilingLizard.IO.Filtro_PNG_JPG;
import com.cc.tilingLizard.IO.Guardar_lizard_tiling;
import com.cc.tilingLizard.IO.Guardar_resultado_como_PNG;
import com.cc.tilingLizard.command.ListaDeAcciones;
import com.cc.tilingLizard.command.StatePointsButtonCommand;
import com.cc.tilingLizard.dialogosEneagonos.VentaDeCrearEneagono;
import com.cc.tilingLizard.dialogosEneagonos.VentaDeCrearEstrella;
import com.cc.tilingLizard.paneles.Elementos_UI;
import com.cc.tilingLizard.paneles.panel_disenio.Panel_patron_disenio;
import com.cc.tilingLizard.paneles.panel_resultado.Panel_resultado;
import com.cc.tilingLizard.paneles.panel_resultado.stroke.JOptionFactory;
import com.cc.tilingLizard.rutinas.VentanaDeCrearRutina1;
import com.cc.tilingLizard.utils.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

public class Eventos implements ActionListener, MouseListener, MouseMotionListener, ChangeListener//KeyListener
{
    Elementos_UI elementos_ui;
    Panel_patron_disenio panel_patron;

    public Eventos(Panel_patron_disenio panel_patron_inicial)
    {
        elementos_ui = Elementos_UI.getInstance();
        this.panel_patron = panel_patron_inicial;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (panel_patron != null)
        {
            panel_patron.panel_de_dibujo.repaint();
        }

        Object o=e.getSource();

        if(o instanceof JMenuItem)
        {
            JMenuItem jmi=(JMenuItem)o;

            switch( jmi.getText() ) {

                case Constants.ABRIR:
                    System.out.println ("Abrir");
                    elementos_ui.abrir_lizard_tiling = new Abrir_lizard_tiling();
                    break;

                case Constants.GUARDAR:
                        System.out.println ("Guardar");
                    elementos_ui.guardar_lizard_tiling=new Guardar_lizard_tiling();
                    break;

                case Constants.GUARDAR_COMO_PNG:
                    System.out.println ("Guardar como PNG");
                    elementos_ui.guardar_resultado_como_PNG = new Guardar_resultado_como_PNG();
                    break;

                case Constants.SALIR:
                    int i=JOptionPane.showConfirmDialog(
                            null,
                            Constants.LOS_ARCHIVOS_SE_PERDERAN_DESEA_SALIR_DE_TODAS_FORMAS
                            );
                    if(i==0)
                    {
                        System.exit(0);
                    }
                    break;

                default:
                    break;
            }

            if(jmi.getText().equals("Mover pts Patron 12 --> Patron 34"))
            {
                Vector v_clone = (Vector)elementos_ui.panel_patron_12.panel_de_dibujo.v_puntos.clone();
                elementos_ui.panel_patron_34.panel_de_dibujo.v_puntos=v_clone;
                elementos_ui.panel_patron_34.panel_de_dibujo.repaint();
            }

            else if( jmi.getText().equals("Imagen de Fondo Panel Patron 12") ||
                    jmi.getText().equals("Imagen de Fondo Panel Patron 34") ||
                    jmi.getText().equals("Imagen de Fondo Panel Patron 56") )
            {
                Filtro_PNG_JPG filtro = new Filtro_PNG_JPG();

                JFileChooser filechooser;
                String userDir = System.getProperty("user.home");
                filechooser = new JFileChooser(userDir +"/Desktop");
                filechooser.setFileFilter(filtro);
                File aux=new File(".");

                String aus=aux.getAbsolutePath();
                System.out.println("aus "+aus);

                int valor=filechooser.showOpenDialog(Elementos_UI.getInstance().jf_principal);
                if(valor==JFileChooser.APPROVE_OPTION)//aceptar
                {
                    File aux_img=new File(filechooser.getCurrentDirectory().getAbsolutePath()+File.separatorChar+filechooser.getSelectedFile().getName());

                    try
                    {
                        Image picture = ImageIO.read(aux_img);

                        if (jmi.getText().equals("Imagen de Fondo Panel Patron 12")) {
                            elementos_ui.panel_patron_12.panel_de_dibujo.backgroundImage = picture;
                            elementos_ui.panel_patron_12.panel_de_dibujo.repaint();
                        } else
                        if (jmi.getText().equals("Imagen de Fondo Panel Patron 34")) {
                            elementos_ui.panel_patron_34.panel_de_dibujo.backgroundImage = picture;
                            elementos_ui.panel_patron_34.panel_de_dibujo.repaint();
                        } else
                        if (jmi.getText().equals("Imagen de Fondo Panel Patron 56")) {
                            elementos_ui.panel_patron_56.panel_de_dibujo.backgroundImage = picture;
                            elementos_ui.panel_patron_56.panel_de_dibujo.repaint();
                        }

                    }
                    catch (Exception ee)
                    {
                        String workingDir = System.getProperty("user.dir");
                        System.out.println("Current working directory : " + workingDir);
                        ee.printStackTrace();
                    }
                }
            }

            else if(jmi.getText().equals("estrellas"))
            {
                VentaDeCrearEstrella.getInstance().mostrar();
            }
            else if(jmi.getText().equals("eNeagonos"))
            {
                VentaDeCrearEneagono.getInstance().mostrar();
            }
            else if(jmi.getText().equals("rutina 1"))
            {
                VentanaDeCrearRutina1.getInstance().mostrar();
            }
        }
    }

    //MouseListener
    public synchronized void mouseClicked(MouseEvent e)
    {
        //System.out.println ( "mouseClicked en Eventos \n   "+e.getSource() );


        if( e.getSource() instanceof JButton || e.getSource() instanceof JCheckBox )
        {
            String aux = "";

            if (e.getSource() instanceof JButton) {
                JButton temp=(JButton)e.getSource();
                aux=temp.getText();
            }
            if (e.getSource() instanceof JCheckBox) {
                JCheckBox temp=(JCheckBox)e.getSource();
                aux=temp.getText();
            }

            if(aux.equals(Constants.COLOCAR_PUNTOS))
            {
                StatePointsButtonCommand insertpoint = new StatePointsButtonCommand(panel_patron,
                        false,
                        true,
                        false,
                        false);
                insertpoint.execute();
                ListaDeAcciones.getInstance().undoStackPush(insertpoint);
            }
            else
            if(aux.equals(Constants.MOVER_PUNTOS))
            {
                StatePointsButtonCommand movepoint = new StatePointsButtonCommand(panel_patron,
                        false,
                        false,
                        ((JCheckBox)e.getSource()).isSelected(),
                        false);
                movepoint.execute();
                ListaDeAcciones.getInstance().undoStackPush(movepoint);
            }
            else
            if(aux.equals(Constants.BORRAR_PUNTOS))
            {
                StatePointsButtonCommand deletepoint = new StatePointsButtonCommand(panel_patron,
                        true,
                        false,
                        false,
                        false);
                deletepoint.execute();
                ListaDeAcciones.getInstance().undoStackPush(deletepoint);
            }
            else
            if(aux.equals(Constants.BORRAR_TODO))
            {
                StatePointsButtonCommand deleteallpoints = new StatePointsButtonCommand(panel_patron,
                        false,
                        false,
                        false,
                        true);
                deleteallpoints.execute();
                ListaDeAcciones.getInstance().undoStackPush(deleteallpoints);
            }
            else
            if(aux.equals(Panel_resultado.CALCULAR))
            {
                //if(elementos_ui.tabbedpane.getSelectedIndex()==elementos_ui.PANEL_DE_DIBUJO)
                if ( !elementos_ui.getCalculandoLizard() )
                {
                    //set_calcular_fractales(true);
                    elementos_ui.calcular_lizard(false);
                }
            }
            else
            if(aux.equals(Panel_resultado.DETENER))
            {
                System.out.println(this.getClass().getName()+":elementos_ui.detenerHilo();");
                Constants.DETENER_TODO();
            }
            else
            if(aux.equals(Panel_resultado.CLEAR))
            {
                Constants.DETENER_TODO();

                try {
                    Thread.sleep(40);
                } catch (Exception e12) {
                    System.err.println(e12);
                }

                elementos_ui.clear();
            }
            else
            if(aux.equals(Panel_resultado.COLOR_LINEAS))
            {
                Color color1 = elementos_ui.panel_resultado.color_lineas.getBackground() ;
                color1 = JColorChooser.showDialog( null, "Seleccione un color para la Lineas", color1 );
                System.out.println(this.getClass().getName()+":color1="+color1);
                elementos_ui.setColorLineas_Panel_resultado(color1);
            }
            else
            if(aux.equals(Panel_resultado.STROKE_LINEAS))
            {
                JOptionFactory.showStrokeDialog();
            }
            else
            if(aux.equals(Panel_resultado.COLOR_FONDO))
            {
                Color color1 = elementos_ui.panel_resultado.panel_de_dibujo.getBackground() ;
                color1 = JColorChooser.showDialog( null, "Seleccione un color para el Fondo", color1 );
                System.out.println(this.getClass().getName()+":color1="+color1);
                elementos_ui.setColorFondo_Panel_resultado(color1);
                //set_calcular_fractales(true);
            }
            else
            if(aux.equals(Panel_resultado.TILING_CUADRADO) ||
                    aux.equals(Panel_resultado.TILING_HEXAGONAL) )
            {
                // paar evitar cosas con un doble click al boton
                if (!elementos_ui.getCalculandoLizard())
                {
                    elementos_ui.tiling(aux);
                }
            }

        }

        if( e.getSource() instanceof JCheckBox )
        {
            JCheckBox temp=(JCheckBox)e.getSource();
            boolean isSelected = temp.isSelected();

            if( panel_patron == elementos_ui.panel_patron_12 ||
                panel_patron == elementos_ui.panel_patron_34 ||
                panel_patron == elementos_ui.panel_patron_56  )
            {

                if (temp.getText().equalsIgnoreCase(Panel_patron_disenio.PUNTOS)) {
                    panel_patron.mostrarPuntosDeControl(isSelected);
                }

                if (temp.getText().equalsIgnoreCase(Panel_patron_disenio.DISTANCIAS)) {
                    panel_patron.mostrarDistancias(isSelected);
                }

                if (temp.getText().equalsIgnoreCase(Panel_patron_disenio.ANGULOS_EJE_X)) {
                    panel_patron.mostrarAngulosEjeX(isSelected);
                }

                if (temp.getText().equalsIgnoreCase(Panel_patron_disenio.ANGULOS_ENTRE_LINEAS)) {
                    panel_patron.mostrarAngulosEntreLineas(isSelected);
                }
            }
        }
    }

    public void mouseEntered(MouseEvent e)
    {
        //System.out.println ( "mouseEntered \n   "+ e.getSource() );
	/*	Object o=e.getSource();
		if(o==elementos_gui.)
		{
		}

	   else

	*/
    }
    public void mouseExited(MouseEvent e)
    {
        //System.out.println ( "mouseExited \n   "+ e.getSource() );
    }
    public void mousePressed(MouseEvent e)
    {
        //	System.out.println ( "mousePressed \n   "+ e.getSource() );
    }

    public void mouseReleased(MouseEvent e)
    {
        //	System.out.println ( "mouseReleased \n   "+ e.getSource() );
    }

    //ChangeListener
    int vez=0;

    public void stateChanged( ChangeEvent e )
    {
        if( e.getSource() instanceof JSlider )
        {
//Panel_patron_disenio ppi = Elementos_UI.instance.panel_patron_inicial;
//Panel_patron_disenio ppr = Elementos_UI.instance.panel_patron_recursivo;

            // esto es para el zoom
            if (
                    (e.getSource()) == panel_patron.js_rotar
                    /*
                    ((JSlider)e.getSource()) == ppi.js_rotar ||
                    ((JSlider)e.getSource()) == ppr.js_rotar
                    */
                )
            {
                JSlider temp=(JSlider) e.getSource();
                String value = ""+temp.getValue();

                System.out.println("valueJSlider="+value);

                panel_patron.aplicarRotacion(""+value);
                /*
                if(panel_patron == elementos_ui.panel_patron_inicial)
                {
                    System.out.println(this.getClass().getName()+":stateChanged panel_patron_inicial temp.getValue()="+temp.getValue());
                    elementos_ui.panel_patron_inicial.aplicarRotacion(""+value);
                }
                else
                if(panel_patron == elementos_ui.panel_patron_recursivo)
                {
                    System.out.println(this.getClass().getName()+":stateChanged panel_patron_recursivo temp.getValue()="+temp.getValue());
                    elementos_ui.panel_patron_recursivo.aplicarRotacion(""+value);
                }
                */
            }
            else
            if (
                    (e.getSource()) == panel_patron.js_zoom
                    /*
                    ((JSlider)e.getSource()) == ppi.js_zoom ||
                    ((JSlider)e.getSource()) == ppr.js_zoom
                    */
                )
            {
                JSlider temp=(JSlider) e.getSource();
                String value = ""+temp.getValue();
                if(temp.getValue()==0)
                {
                    value=""+1;
                }

                panel_patron.aplicarZoom(""+value);
                /*
                if(panel_patron == elementos_ui.panel_patron_inicial)
                {
                    //System.out.println(this.getClass().getName()+":stateChanged temp.getValue()="+temp.getValue());
                    elementos_ui.panel_patron_inicial.aplicarZoom(""+value);
                }
                else
                if(panel_patron == elementos_ui.panel_patron_recursivo)
                {
                    //System.out.println(this.getClass().getName()+":stateChanged temp.getValue()="+temp.getValue());
                    elementos_ui.panel_patron_recursivo.aplicarZoom(""+value);
                }
                */
            }

        }
    }

    public void mouseMoved( MouseEvent e )
    {
        vez++;
        pintar_puntos();
    }
    public void mouseDragged( MouseEvent e )
    {
    }
    public void keyReleased( KeyEvent e )
    {
    }


    public void pintar_puntos()
    {
        if (panel_patron!=null)
        {
            panel_patron.panel_de_dibujo.repaint();
        }

/*
        {
            if(elementos_ui.tabbedpane.getSelectedIndex()==elementos_ui.PANEL_PATRON_INICIAL)
            {
                if(vez>2)
                    elementos_ui.panel_patron_inicial.panel_de_dibujo.repaint();//pintar_puntos();
            }
            else
            if(elementos_ui.tabbedpane.getSelectedIndex()==elementos_ui.PANEL_PATRON_RECURSIVO)
            {
                if(vez>2)//la primera vez da nullPointerException porque algun objeto no se a ejemplarizado (instanciado)
                    elementos_ui.panel_patron_recursivo.panel_de_dibujo.repaint();//.pintar_puntos();
            }
        }
*/

    }



}