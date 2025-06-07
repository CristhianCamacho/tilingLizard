package com.cc.tilingLizard.IO;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.Vector;
import java.io.IOException;

public class Flujo
{
    public static PrintWriter entrada = null;
    public static BufferedReader salida = null;

    public Flujo()
    {
    }

    public static void escribir(String dato,String dir)
    {
        try
        {
            entrada = new PrintWriter(new FileWriter(dir));
            entrada.write(dato);
            entrada.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    public static void escribir(String dato, File dir)
    {
        try
        {
            entrada = new PrintWriter(new FileWriter(dir));
            entrada.write(dato);
            entrada.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    public static Vector leer(String dir)
    {
        Vector vector=new Vector();

        try
        {
            salida = new BufferedReader(new FileReader(dir));
            String aux="";
            while((aux=salida.readLine())!=null)
            {
                vector.addElement(aux);
            }
        }
        catch(IOException e)
        {
        }

        return vector;
    }

    public static Vector leer(File dir)
    {
        Vector vector=new Vector();

        try
        {
            salida = new BufferedReader(new FileReader(dir));
            String aux="";
            while((aux=salida.readLine())!=null)
            {
                vector.addElement(aux);
            }
        }
        catch(IOException e)
        {
        }

        return vector;
    }
}

