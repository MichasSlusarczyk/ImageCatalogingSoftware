/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class for writing data to csv file
 * @author Szymon Sieczko
 */
public class CSVWriter {

    /**
     * Method that writes data to csv file
     * @param path path to csv file
     * @param delimiter chars that separete info in csv file
     * @param data data that will be written to csv file
     */
    public void write(String path, String delimiter, String... data) throws IOException {
        String newRow = String.join(delimiter, data);
        ArrayList<String> rows = new ArrayList<>();
        rows.add(newRow);
        File saveFile = new File(path);
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
            }
        }
        Scanner scanner = new Scanner(new File(path));
        for (int i = 0; i < 9; i++) {
            if (scanner.hasNextLine()) {
                rows.add(scanner.nextLine());
            }
        }
        FileWriter writer = new FileWriter(path);
        for (String row : rows) {
            writer.append(row).write("\n");
        }
        writer.flush();
        writer.close();
    }
}
