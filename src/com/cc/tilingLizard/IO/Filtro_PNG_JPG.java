package com.cc.tilingLizard.IO;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class Filtro_PNG_JPG extends FileFilter {

    public String extension="PNG,JPG";

    public String getDescription(){
        return "."+extension;
    }

    public boolean accept( File archivo ){
        boolean res = false;

        String nombre = archivo.getName();

        if( nombre.endsWith("."+extension) || archivo.isDirectory() || archivo.isFile() )
            res = true;

        return res;
    }

}