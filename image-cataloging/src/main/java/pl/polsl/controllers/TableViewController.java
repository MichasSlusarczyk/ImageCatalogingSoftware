/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import pl.polsl.models.CSVReader;
import pl.polsl.models.CSVWriter;
import pl.polsl.models.ImageFinder;
import pl.polsl.models.hashcode.Cataloger;
import pl.polsl.views.TableView;

/**
 *
 * @author Szymon Sieczko
 */
public class TableViewController {

    TableView view;
    CSVReader CSVReader;
    CSVWriter CSVWriter;
    FolderViewController folderViewController;
    JTable table;
    ImageFinder imageFinder;
    JButton analyzeButton;

    public TableViewController(TableView v, FolderViewController folderController) {
        view = v;
        CSVReader = new CSVReader();
        CSVWriter = new CSVWriter();
        imageFinder = new ImageFinder();
        folderViewController = folderController;
        initController();
    }

    private void initController() {
        table = view.getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    analyzePreviousFolderDialog(table.getValueAt(table.getSelectedRow(), 0).toString());
                    table.clearSelection();
                }
            }
        });
        analyzeButton = view.getAnalyzeButton();
        analyzeButton.addActionListener(e -> analyzeDirectory(getFolderPath()));
    }
     /**
     * Method that fills table with data about previously analyzed folders
     */
    public void fillTable() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
        List<Object[]> tableData = CSVReader.read("src\\main\\resources\\previousFolders.csv", ";");
        for (Object[] row : tableData) {
            tableModel.addRow(row);
        }
    }
    
       /**
     * Dialog that appears after clicking table row
     */
    private void analyzePreviousFolderDialog(String path) {
        Object[] options1 = {"Cancel", "Analyze again",
            "Open"};
        int input = JOptionPane.showOptionDialog(table,
                "You have selected "
                + path,
                "Previously analyzed folder",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options1, options1[2]);
        if (input == 2) {
            folderViewController.fillFolderTree(path);
        }
        if (input == 1) {
            analyzeDirectory(path);
        }
    }
    
    /**
     * Dialog that helps to choose folder to analyze
     */
    public String getFolderPath(){
        JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("."));
            fileChooser.setDialogTitle("Select directory");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            if (fileChooser.showOpenDialog(analyzeButton) == JFileChooser.APPROVE_OPTION) {
               return fileChooser.getSelectedFile().getAbsolutePath();
            } else {
                return "";
            }
    }
    
    /**
     * Method that analyzes given folder
     * @param path path to real folder
     */
    public void analyzeDirectory(String path) {
        if(path.equals(""))
            return;
        
        ArrayList<File> images = imageFinder.getImages(path);
        ArrayList<String> pathsList = new ArrayList<>();
        if (!images.isEmpty()) {
            images.forEach(image -> {
                pathsList.add(image.getAbsolutePath());
            });
            try {
                Cataloger cataloger = new Cataloger();
                cataloger.catalog(path, pathsList);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(analyzeButton, "Couldn't sort images", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            addToPreviouslyAnalyzed(path, images.size());
        } else {
            JOptionPane.showMessageDialog(analyzeButton, "There are no images in this folder", "Results",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Method that add data to csv file that contains info about previously analyzed folders
     * @param path path to analyzed folder
     * @param imageCount number of images inside of folder and subfolders
     */
    private void addToPreviouslyAnalyzed(String path, Integer imageCount) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            CSVWriter.write("src\\main\\resources\\previousFolders.csv", ";", path, imageCount.toString(),
                    formatter.format(date));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(analyzeButton, "Couldn't add folder to previously analyzed list", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        fillTable();
    }
}
