package com.cc.tilingLizard.IO;

import java.io.*;
import javax.swing.filechooser.FileFilter;

public class Filtro_PNG extends FileFilter{

    public String extension="PNG";

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