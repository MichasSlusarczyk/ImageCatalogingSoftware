/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Szymon Sieczko
 */
public class ImageFinder {

    private final String[] extensions = {"png", "jpg", "bmp"};
    private ArrayList<File> images;

    public String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        return i > 0 ? fileName.substring(i + 1) : "";
    }

    public ArrayList<File> getImages(String startDir) {
        images = new ArrayList<>();
        return searchRecursivly(startDir);
    }

    private ArrayList<File> searchRecursivly(String startDir) {
        File directory = new File(startDir);

        File[] files = directory.listFiles();

        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchRecursivly(file.getAbsolutePath());
                } else {
                    String fileExtension = getExtension(file.getName());
                    if (Arrays.asList(extensions).contains(fileExtension)) {
                        images.add(file);
                    }
                }
            }
        }
        return images;
    }

}
