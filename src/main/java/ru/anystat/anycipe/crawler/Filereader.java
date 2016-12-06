package ru.anystat.anycipe.crawler;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Lofv on 09.11.2016.
 */


public class Filereader {

    private ArrayList<String> list = new ArrayList<String>();

    public Filereader(String fileName) {
        setList(fileName);
    }

    private void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
    }

    public ArrayList<String> getList() {
        return list;
    }

    private void setList(String fileName) {
        read(fileName);
    }

    private void read(String fileName) {

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
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
