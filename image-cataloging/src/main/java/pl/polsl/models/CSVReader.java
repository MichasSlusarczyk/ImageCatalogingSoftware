/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

/**
 * class for reading data form csv file
 * @author Szymon Sieczko
 */
public class CSVReader {

    /**
     * Method that reads lines from csv file
     * @param path path to csv file
     * @param delimiter chars that separete info in csv file
     * @return list of ararys of objects that contain information about previously analyzed folders
     */
    public List<Object[]> read(String path, String delimiter) {
        ArrayList<Object[]> records = new ArrayList<>();
        File saveFile = new File(path);
        if (saveFile.exists()) {
            try ( Scanner scanner = new Scanner(saveFile);) {
                while (scanner.hasNextLine()) {
                    records.add(getRecordFromLine(scanner.nextLine(), delimiter));
                }
            } catch (FileNotFoundException e) {
            }
        }

        return records;
    }

    /**
     * Method that parse lines from csv file
     * @param line line from csv file
     * @param delimiter chars that separete info in csv file
     * @return array of objects that contain information about previously analyzed folders
     */
    private Object[] getRecordFromLine(String line, String delimiter) {
        ArrayList<Object> rowValues = new ArrayList<>();
        try ( Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(delimiter);
            rowValues.add(rowScanner.next());
            rowValues.add(rowScanner.nextInt());
            rowValues.add(rowScanner.next());

        }
        return rowValues.toArray();
    }
}
