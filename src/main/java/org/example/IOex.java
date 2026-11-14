package org.example;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class ZipFolder{
    String pathToFile; //шлях до папки яку треба архівувати
    String newZip;
    ZipFolder(String pathToFile, String newZip){
        this.pathToFile=pathToFile;
        this.newZip=newZip;
    }

    void CreateOutputsStream()  {
        try(FileOutputStream fos=new FileOutputStream(newZip);//ярхів який буде створено
        ZipOutputStream zos=new ZipOutputStream(fos) ) {//zipOutputStream буде використовувати цей шлях, автоматично при записі стискуючи байти
            File file=new File(pathToFile);//файл за цим шляхом

            makingZip(zos," ",file);


        }catch (IOException e){
            throw new RuntimeException(e);

        }

    }
    void makingZip(ZipOutputStream zos,String path, File file){
        String currentPath=createAStructure(file,path,zos); // поточний шлях до папки

        File[] files=file.listFiles();// створити массив з файлами які є у поточній папці

        for(File f: files){
            if (f.isDirectory()){
                makingZip(zos, currentPath, f);
            }else {
                zipAFile(f, currentPath, zos);
            }
        }



    }
    String createAStructure(File file, String path,ZipOutputStream zos){
        String tmpPath=path+file.getName()+"/";
        // створюємо Entry
        if(!path.isEmpty()){
            ZipEntry folderStructure=new ZipEntry(tmpPath);
            try {
                zos.putNextEntry(folderStructure);
                zos.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return tmpPath;

    }
    void zipAFile(File file, String path,ZipOutputStream zos){
        String fullFileName=path+file.getName();
        ZipEntry entryFile=new ZipEntry(fullFileName);

        try ( FileInputStream  fileBody=new FileInputStream(file);){
            zos.putNextEntry(entryFile);

            byte[] buffer=new byte[4000];
            int bytesRead;
            while ((bytesRead=fileBody.read(buffer))>0){
                zos.write(buffer,0,bytesRead);
            }
            zos.closeEntry();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}