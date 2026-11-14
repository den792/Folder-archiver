package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Main {
    public static void main(String[] args){
//        String pathToFile="C:\\Users\\user\\Desktop\\Новая папка (4)\\experiment";
//        String newZip="C:\\Users\\user\\Desktop\\Новая папка (4)\\ZipResult";
//        ZipFolder zip=new ZipFolder(pathToFile,newZip);
//        zip.CreateOutputsStream();
        String zipFile = "C:\\Users\\user\\Desktop\\Новая папка (4)\\ZipResult";
        String destFolder = "C:\\Users\\user\\Desktop\\Новая папка (4)\\unpacked";
        UnZip unZip=new UnZip(zipFile,destFolder);
        unZip.extract();
    }
}
