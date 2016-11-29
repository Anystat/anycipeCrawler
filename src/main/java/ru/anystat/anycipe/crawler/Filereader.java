package ru.anystat.anycipe.crawler;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Lofv on 09.11.2016.
 */

//нужен для чтения списка ингредиентов или списка сайтов из текстового файла
public class Filereader {

    static ArrayList<String> list = new ArrayList<String>();

    public static ArrayList<String> read(String fileName) {

        try {
            exists(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "windows-1251"));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                list.add(inputLine);
                System.out.println(list);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
    }
}
