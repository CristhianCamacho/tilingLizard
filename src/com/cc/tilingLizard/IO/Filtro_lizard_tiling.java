package com.cc.tilingLizard.IO;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class Filtro_lizard_tiling extends FileFilter {

    public static String extension="LIZARD_TILING";

    public String getDescription(){
        return "."+extension;
    }

    public boolean accept( File archivo ){
        boolean res = false;

        String nombre = archivo.getName();

        if( nombre.endsWith("."+extension) || archivo.isDirectory() /*|| archivo.isFile()*/ )
        {
            res = true;

            System.out.println(nombre);
        }

        return res;
    }

}
