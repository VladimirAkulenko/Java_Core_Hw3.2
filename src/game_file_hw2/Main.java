package game_file_hw2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(100, 500, 23, 253.4);
        GameProgress progress2 = new GameProgress(80, 300, 13, 53.4);
        GameProgress progress3 = new GameProgress(95, 700, 54, 751.8);

        saveGame("C:\\Владимир\\Обучение\\Games\\savegames\\progress1.dat", progress1);
        saveGame("C:\\Владимир\\Обучение\\Games\\savegames\\progress2.dat", progress2);
        saveGame("C:\\Владимир\\Обучение\\Games\\savegames\\progress3.dat", progress3);

        List<String> stringList = new ArrayList<>();
        stringList.add("C:\\Владимир\\Обучение\\Games\\savegames\\progress1.dat");
        stringList.add("C:\\Владимир\\Обучение\\Games\\savegames\\progress2.dat");
        stringList.add("C:\\Владимир\\Обучение\\Games\\savegames\\progress3.dat");

        zipFiles("C:\\Владимир\\Обучение\\Games\\savegames\\zip.zip", stringList);

        List<File> fileList = Arrays.asList(
                new File("C:\\Владимир\\Обучение\\Games\\savegames\\progress1.dat"),
                new File("C:\\Владимир\\Обучение\\Games\\savegames\\progress2.dat"),
                new File("C:\\Владимир\\Обучение\\Games\\savegames\\progress3.dat"));

        fileList.stream().forEach(file -> {
            if (file.delete()) {
                System.out.println("Файл " + file.getName() + " удалён");
            } else {
                System.out.println("Файл " + file.getName() + " не удалён");
            }
        });
    }

    public static void saveGame(String path, GameProgress progress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(progress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> stringList) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(path))) {
            for (String list : stringList) {
                try (FileInputStream fileInputStream = new FileInputStream(list)) {

                    ZipEntry zipEntry = new ZipEntry(list);
                    zipOutputStream.putNextEntry(zipEntry);

                    byte[] buffer = new byte[fileInputStream.available()];
                    fileInputStream.read(buffer);
                    zipOutputStream.write(buffer);

                    zipOutputStream.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
