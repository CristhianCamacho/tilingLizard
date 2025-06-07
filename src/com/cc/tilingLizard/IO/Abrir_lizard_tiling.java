package com.cc.tilingLizard.IO;

import com.cc.tilingLizard.paneles.Elementos_UI;

import javax.swing.*;
import java.io.File;
import java.util.Vector;

public class Abrir_lizard_tiling
{

    JFileChooser filechooser;
    private Filtro_lizard_tiling filtro = new Filtro_lizard_tiling();

    Elementos_UI elementos_UI;

    int valor;

    public Abrir_lizard_tiling()
    {
        elementos_UI=Elementos_UI.getInstance();
        //jf_principal

        filechooser = new JFileChooser();
        filechooser.setFileFilter(filtro);

        File aux=new File(".");

        String aus=aux.getAbsolutePath();
        System.out.println("aus "+aus);

////////////////////////////////////////////////////////////////////////////////
        //Administrador_de_E_S aes=new Administrador_de_E_S();

        //String dir_base=aes.obtener_directorio_base( aus );//"Prototipo_progLineal_0.9"
        //System.out.println("dir_base "+dir_base);
////////////////////////////////////////////////////////////////////////////////


        //for(int i=0;i<aus.length()-dir_base.length();i++)
        //	{
        //		if( (aus.substring(i,i+dir_base.length())).equalsIgnoreCase(dir_base) )
        //		aus=aus.substring(0,i+dir_base.length());
        //	}

        //aus=aus.substring(0,aus.length()-2);
        //aux=null;
        //aux=new File(aus + "/src/com/cristhian/camacho/Modelos_fractales/");

        System.out.println( "System.getProperty(\"user.dir\")"+System.getProperty("user.dir") );
        String aux_=System.getProperty("user.dir");

        if( aux_.contains("out\\production") )
        {
            aux_=aux_.substring( 0 , aux_.indexOf("out\\production") );
            aux_+="Modelos_lizard";
        }
        else
        {
            aux_+="/Modelos_lizard";
        }

        aux=new File(aux_);

        filechooser.setCurrentDirectory(aux);

        valor=filechooser.showOpenDialog(elementos_UI.jf_principal);

        verificar_valor();

        elementos_UI.repaintPaneles_patrones();
    }

    public void Abrir(String path)
    {
        File aux=new File(path);

        if(!aux.exists())
        {

            JOptionPane.showMessageDialog(elementos_UI.jf_principal,"el archivo no existe");
        }
        else
        {

            int i=JOptionPane.showConfirmDialog(elementos_UI.jf_principal,"Los Archivos que no se hayan guardado se perderan  \n desea Abrir de todas formas?");

            if(i==0)
            {

                File file = filechooser.getSelectedFile();
                String aux_s=file.getName();
                aux_s=aux_s.substring( 0,aux_s.length()-filtro.extension.length() );



                elementos_UI.set_nombre_del_modelo( aux_s );

                Vector v =Flujo.leer(aux);

                elementos_UI.datos_para_abrir( v );

                Archivos_recientes.agregar_Archivo_reciente(file);
            }

        }

    }
    public void verificar_valor()
    {


        if(valor==JFileChooser.APPROVE_OPTION)//aceptar

        {

            Abrir(filechooser.getCurrentDirectory().getAbsolutePath()+File.separatorChar+filechooser.getSelectedFile().getName());
        }


    }

    public static void main(String arg[])
    {

    }



}