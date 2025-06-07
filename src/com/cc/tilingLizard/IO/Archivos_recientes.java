package com.cc.tilingLizard.IO;

import com.cc.tilingLizard.paneles.Elementos_UI;
import com.cc.tilingLizard.utils.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

public class Archivos_recientes
        implements ActionListener
{

    public static final int MAX = 20;
    public static File LISTA_DE_FILES_RECIENTES = new File(Constants.DIR_DE_PROPIEDADES, "recientes.tilingLizard");;
    public static Archivos_recientes handle;
    public static Vector lista_de_archivos_recientes;
    public static JMenu file;

    public Archivos_recientes()
    {
    }

    public static void cargar_Archivos_recientes(JMenu f)
    {
        lista_de_archivos_recientes = new Vector();
        file = f;
        handle = new Archivos_recientes();
        if(LISTA_DE_FILES_RECIENTES.exists())
        {
            file.addSeparator();
            try
            {
                BufferedReader bufferedreader = new BufferedReader(new FileReader(LISTA_DE_FILES_RECIENTES));
                String s;
                while((s = bufferedreader.readLine()) != null)
                {
                    JMenuItem jmenuitem;
                    file.add(jmenuitem = new JMenuItem(s));
                    jmenuitem.addActionListener(handle);
                    lista_de_archivos_recientes.addElement(s);
                }
                bufferedreader.close();
            }
            catch(Exception exception)
            {
                JOptionPane.showMessageDialog(null, "No se puede leer de la lista de archivos recientes.");//"Unable to read the recent file list."
            }
        }
    }

    public static void agregar_Archivo_reciente(File file1)
    {
        String s = file1.getAbsolutePath();
        JMenuItem jmenuitem = new JMenuItem(s);
        jmenuitem.addActionListener(handle);
        int l = lista_de_archivos_recientes.size();
        int i1 = l;
        int i = 0;
        if(l > 0)
        {
            for(; i < l; i++)
                if(s.equals((String)lista_de_archivos_recientes.elementAt(i)))
                    break;

            if(i < l)
            {
                if(i > 0)
                {
                    Object obj = lista_de_archivos_recientes.elementAt(i);
                    lista_de_archivos_recientes.removeElementAt(i);
                    lista_de_archivos_recientes.insertElementAt(obj, 0);
                }
            } else
            if(l == MAX)
            {
                lista_de_archivos_recientes.removeElementAt(5);
                lista_de_archivos_recientes.insertElementAt(s, 0);
            } else
            {
                lista_de_archivos_recientes.insertElementAt(s, 0);
                i1++;
            }
        } else
        {
            lista_de_archivos_recientes.addElement(s);
            i1++;
        }
        file.removeAll();
        try
        {
            BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(LISTA_DE_FILES_RECIENTES));
            for(int k = 0; k < i1; k++)
            {
                String s1 = (String)lista_de_archivos_recientes.elementAt(k);
                bufferedwriter.write(s1, 0, s1.length());
                bufferedwriter.newLine();
                JMenuItem jmenuitem1;
                file.add(jmenuitem1 = new JMenuItem(s1));
                jmenuitem1.addActionListener(handle);
            }

            bufferedwriter.close();
        }
        catch(Exception exception)
        {
            System.err.println(exception.getMessage());
            JOptionPane.showMessageDialog(null,"No se puede agregar a la lista de archivos recientes.");//"Unable to write recent file list."
        }
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();

        File aux=new File(s);

        String aux_s=s.substring(s.lastIndexOf("\\"));
        aux_s=aux_s.substring( 0,aux_s.length()- Filtro_lizard_tiling.extension.length() );

        Vector v = Flujo.leer(aux);

        Elementos_UI.getInstance().set_nombre_del_modelo( aux_s );
        Elementos_UI.getInstance().datos_para_abrir( v );

        Elementos_UI.getInstance().panel_patron_12.panel_de_dibujo.repaint();
        Elementos_UI.getInstance().panel_patron_34.panel_de_dibujo.repaint();
        Elementos_UI.getInstance().panel_patron_56.panel_de_dibujo.repaint();
    }
}