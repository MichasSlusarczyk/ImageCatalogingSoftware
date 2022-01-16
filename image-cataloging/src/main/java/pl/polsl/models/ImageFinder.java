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
 * class for searching folder and finding all images inside
 * @author Szymon Sieczko
 */
public class ImageFinder {

    private final String[] extensions = {"png", "jpg", "bmp"};
    private ArrayList<File> images;

    /**
     * Method for getting extension of file
     * @param fileName name of the file
     * @return extension of file
     */
    public String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        return i > 0 ? fileName.substring(i + 1) : "";
    }
    
    
    /**
     * Method for initializing array of images
     * @param startDir path to folder that will be searched
     * @return list of images contained in the folder and subfolders
     */
    public ArrayList<File> getImages(String startDir) {
        images = new ArrayList<>();
        return searchRecursivly(startDir);
    }

    /**
     * Recursive method for finding all image files in folder and it's subfolders
     * @param startDir path to folder that will be searched
     * @return list of images contained in the folder and subfolders
     */
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
