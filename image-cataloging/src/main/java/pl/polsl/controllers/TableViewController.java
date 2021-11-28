/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
import pl.polsl.views.TableView;

/**
 *
 * @author Szymon Sieczko
 */
public class TableViewController {

    TableView view;
    CSVReader CSVReader;
    CSVWriter CSVWriter;
    JTable table;
    ImageFinder imageFinder;
    JButton analyzeButton;

    public TableViewController(TableView v) {
        view = v;
        CSVReader = new CSVReader();
        CSVWriter = new CSVWriter();
        imageFinder = new ImageFinder();
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
        analyzeButton.addActionListener(e -> analyzeDirectory());
    }

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

    private void analyzePreviousFolderDialog(String path) {
        Object[] options1 = {"Cancel", "Analyze again",
            "Open"};
        JOptionPane.showOptionDialog(table,
                "You have selected "
                + path,
                "Previously analyzed folder",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options1, options1[2]);
    }

    public void analyzeDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle("Select directory");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showOpenDialog(analyzeButton) == JFileChooser.APPROVE_OPTION) {
            ArrayList<File> images = imageFinder.getImages(fileChooser.getSelectedFile().getAbsolutePath());
            ArrayList<String> pathsList = new ArrayList<>();
            if (!images.isEmpty()) {
                for (File image : images) {
                    pathsList.add(image.getAbsolutePath());
                    System.out.println(image.getAbsolutePath());
                }
                // klasaMichała.przyjmijListeSciezek(pathsList)
                addToPreviouslyAnalyzed(fileChooser.getSelectedFile().getAbsolutePath(), images.size());
            } else {
                JOptionPane.showMessageDialog(analyzeButton, "There are no images in this folder", "Results",
                        JOptionPane.WARNING_MESSAGE);
            }

        } else {
            System.out.println("No Selection ");
        }
    }

    private void addToPreviouslyAnalyzed(String path, Integer imageCount) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        CSVWriter.write("src\\main\\resources\\previousFolders.csv", ";", path, imageCount.toString(),
                formatter.format(date));
        fillTable();
    }
}
