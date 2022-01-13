/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.controllers;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import pl.polsl.models.snapshot.SnapshotFolder;
import pl.polsl.models.snapshot.SnapshotReader;
import pl.polsl.views.FolderView;
import pl.polsl.views.ImagePanel;
import pl.polsl.views.MainWindow;

/**
 *
 * @author Szymon Sieczko
 */
public class FolderViewController {

    FolderView folderView;
    MainWindow mainView;
    SnapshotReader snapshotReader;
    JTree foldersTree;
    ImagePanel imagePanel;

    public FolderViewController(FolderView v, MainWindow mainWindow) {
        folderView = v;
        mainView = mainWindow;
        initController();
    }

    private void initController() {
        foldersTree = folderView.getFolderTree();
        imagePanel = folderView.getImagePanel();
        foldersTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) foldersTree.getLastSelectedPathComponent();
                    if (node == null) {
                        return;
                    }
                    if (node.isLeaf()) {
                        TreeImageNode nodeCast = (TreeImageNode) node;
                        imagePanel.setImage(nodeCast.getFilePath());
                        imagePanel.repaint();
                    }
                }
            }
        });
    }

    void fillFolderTree(String path) {
        DefaultTreeModel treeModel = (DefaultTreeModel) foldersTree.getModel();
        DefaultMutableTreeNode treeRoot = (DefaultMutableTreeNode) treeModel.getRoot();
        treeRoot.removeAllChildren();
        treeModel.reload();
        imagePanel.clear();
        imagePanel.revalidate();
        try {
            ArrayList<FolderToDisplay> imagesStructure = getImagesStructure(path);
            for (int i = 0; i < imagesStructure.size(); i++) {
                DefaultMutableTreeNode folder = new DefaultMutableTreeNode(imagesStructure.get(i).folderName);
                List<String> images = imagesStructure.get(i).imagesList;
                for (int j = 0; j < images.size(); j++) {
                    Path filePath = Paths.get(images.get(j));
                    TreeImageNode treeImage = new TreeImageNode(filePath.getFileName().toString(), filePath.toString());
                    folder.add(treeImage);
                }
                treeRoot.add(folder);
            }
        foldersTree.setRootVisible(true);
        mainView.getPreviousButton().setEnabled(true);
        mainView.getNextButton().setEnabled(false);
        CardLayout cardLayout = (CardLayout) (mainView.getCardsContainer().getLayout());
        cardLayout.next(mainView.getCardsContainer());
        } catch (IOException e) {
            foldersTree.setRootVisible(false);
            JOptionPane.showMessageDialog(foldersTree, "Couldn't read snapshot", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private ArrayList<FolderToDisplay> getImagesStructure(String path) throws IOException {
        snapshotReader = new SnapshotReader(path);
        List<String> Folders = snapshotReader.getFoldersList();
        ArrayList<FolderToDisplay> treeFolders = new ArrayList<>();
        for (int i = 0; i < Folders.size(); i++) {
            SnapshotFolder snapshotFolder = snapshotReader.getSnapshotFolder(Folders.get(i));
            List<String> images = snapshotFolder.getImagesList();
            treeFolders.add(new FolderToDisplay(Folders.get(i), images));
        }

        return treeFolders;
    }
}

class FolderToDisplay {

    String folderName;
    List<String> imagesList;

    public FolderToDisplay(String name, List<String> images) {
        folderName = name;
        imagesList = images;
    }
}

class TreeImageNode extends DefaultMutableTreeNode {
    String path;
    String name; 
    
    public TreeImageNode(String fileName, String filePath) {
        name = fileName;
        path = filePath;
    }
    
    @Override
    public String toString() { 
    return this.name;
} 
    public String getFilePath(){
        return this.path;
    }
}