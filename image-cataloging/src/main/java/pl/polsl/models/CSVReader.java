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
 *
 * @author Szymon Sieczko
 */
public class CSVReader {

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
