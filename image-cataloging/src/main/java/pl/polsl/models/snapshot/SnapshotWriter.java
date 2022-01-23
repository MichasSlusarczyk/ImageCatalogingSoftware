package pl.polsl.models.snapshot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * SnapshotWriter class is used to create new .Snapshot directory with its folders and
 * cataloged images
 */
public class SnapshotWriter {

    private String directory = null;
    public static final String ROOT_FOLDER_NAME = ".Snapshot";
    public static final String FOLDERS_LIST_FILE_NAME = "FoldersList";
    public static final String IMAGES_LIST_FILE_NAME = "ImagesList";

    /**
     * Creates the .Snapshot folder and folders list file in it
     * @param path directory where it should create the folder
     * @throws IOException thrown when such folder already exists and can't be deleted
     */
    public void initializeSnapshot(String path) throws IOException
    {
        Path directoryPath = Paths.get(path, ROOT_FOLDER_NAME);
        if(Files.exists(directoryPath))
        {
            if(!deleteDirectory(directoryPath.toFile()))
            {
                throw new IOException();
            }
        }
        Files.createDirectory(directoryPath);
        if(System.getProperty("os.name").startsWith("Windows"))
        {
            Files.setAttribute(directoryPath, "dos:hidden", true);
        }
        directory = Paths.get(path).toString();
        createFoldersList();
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    /**
     * Creates a folder in .Snapshot with its images list
     * @param folderName name of the folder to be created
     * @param imagesPathsList list of images contained in this folder
     * @throws IOException thrown when program can't create a file
     * @throws FileAlreadyExistsException thrown when such folder already exists
     */
    public void createFolder(String folderName, List<String> imagesPathsList) throws IOException, FileAlreadyExistsException
    {
        addFolderToFoldersList(folderName);
        Path virtualFolderPath = Paths.get(directory, ROOT_FOLDER_NAME, folderName);
        Files.createDirectory(virtualFolderPath);
        createImagesList(folderName);
        saveImagesToImagesList(folderName, imagesPathsList);
    }

    private void saveImagesToImagesList(String folderName, List<String> imagesPathsList) throws IOException
    {
        try(FileWriter imagesListWriter = new FileWriter(Paths.get(directory, ROOT_FOLDER_NAME, folderName, IMAGES_LIST_FILE_NAME).toString(), true))
        {
            for(String imagePath : imagesPathsList)
            {
                imagesListWriter.write(imagePath + "\n");
            }
        }
    }

    private void createImagesList(String folderName) throws IOException, FileAlreadyExistsException
    {
        if(!(new File(Paths.get(directory, ROOT_FOLDER_NAME, folderName, IMAGES_LIST_FILE_NAME).toString()).createNewFile()))
        {
            throw new FileAlreadyExistsException(Paths.get(directory, ROOT_FOLDER_NAME, folderName, IMAGES_LIST_FILE_NAME).toString());
        }
    }

    private void addFolderToFoldersList(String folderName) throws IOException
    {
        try(FileWriter foldersListWriter = new FileWriter(Paths.get(directory, ROOT_FOLDER_NAME, FOLDERS_LIST_FILE_NAME).toString(), true))
        {
            foldersListWriter.write(folderName + "\n");
        }
    }

    private void createFoldersList() throws IOException
    {
        if(!(new File(Paths.get(directory, ROOT_FOLDER_NAME, FOLDERS_LIST_FILE_NAME).toString()).createNewFile()))
        {
            throw new FileAlreadyExistsException(Paths.get(directory, ROOT_FOLDER_NAME, FOLDERS_LIST_FILE_NAME).toString());
        }
    }
}