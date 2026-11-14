package org.example;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class UnZip {
    private String destPath;      // Папка, куди розпаковувати
    private String zipPath;       // Шлях до ZIP архіву

    UnZip(String zipPath, String destPath) {
        this.zipPath = zipPath;
        this.destPath = destPath;
    }

    void extract() {
        File destDir = new File(destPath);

        // Створюємо папку призначення(куди будуть розпаковуватись файли), якщо її немає на диску
        if (!destDir.exists()) {
            boolean created = destDir.mkdirs();
            System.out.println("Створено папку призначення: " + destPath + " - " + created);
        }

        makingUnZip(destDir);
    }

    void makingUnZip(File destDir) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                File outputFile = new File(destDir, entryName);//майбутній шлях нової папки

                System.out.println("Обробка: " + entryName);

                // якщо абстрактний шлях це папка
                if (entry.isDirectory()) {
                    // Це папка - створюємо її
                    if (!outputFile.exists()) {
                        boolean created = outputFile.mkdirs();
                        System.out.println("  Створено папку: " + entryName + " - " + created);
                    }
                } else {
                    // Це файл - спочатку створюємо батьківські папки
                    File parentDir = outputFile.getParentFile();//якщо повний шлях це "C:/unpacked/docs/file.txt", то parentDir буде вказувати на "C:/unpacked/docs" (папка, в якій лежить файл).
                    if (parentDir != null && !parentDir.exists()) {
                        parentDir.mkdirs();
                        System.out.println("  Створено батьківські папки: " + parentDir.getPath());
                    }

                    // Записуємо файл
                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[8192];  // 8KB буфер
                        int bytesRead;
                        while ((bytesRead = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, bytesRead);
                        }
                        System.out.println("  Розпаковано файл: " + entryName +
                                " (" + entry.getSize() + " байт)");
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException("Не вдалося створити файл: " + outputFile.getPath(), e);
                    }
                }

                zis.closeEntry();
            }

            System.out.println("Розархівація завершена успішно!");

        } catch (IOException e) {
            throw new RuntimeException("Помилка розархівації: " + e.getMessage(), e);
        }
    }
}