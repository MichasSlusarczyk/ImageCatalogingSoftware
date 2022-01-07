package pl.polsl.models.snapshot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SnapshotWriter 
{
    public void initializeSnapshot(String path) throws IOException
    {
        Path directoryPath = Paths.get(path, _rootFolderName);
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
        _directory = Paths.get(path).toString();
        _createFoldersList();
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

    public void createFolder(String folderName, List<String> imagesPathsList) throws IOException, FileAlreadyExistsException
    {
        _addFolderToFoldersList(folderName);
        Path virtualFolderPath = Paths.get(_directory, _rootFolderName, folderName);
        Files.createDirectory(virtualFolderPath);
        _createImagesList(folderName);
        _saveImagesToImagesList(folderName, imagesPathsList);
    }

    private void _saveImagesToImagesList(String folderName, List<String> imagesPathsList) throws IOException
    {
        try(FileWriter imagesListWriter = new FileWriter(Paths.get(_directory, _rootFolderName, folderName, _imagesListFileName).toString(), true))
        {
            for(String imagePath : imagesPathsList)
            {
                imagesListWriter.write(imagePath + "\n");
            }
        }
    }

    private void _createImagesList(String folderName) throws IOException, FileAlreadyExistsException
    {
        if(!(new File(Paths.get(_directory, _rootFolderName, folderName, _imagesListFileName).toString()).createNewFile()))
        {
            throw new FileAlreadyExistsException(Paths.get(_directory, _rootFolderName, folderName, _imagesListFileName).toString());
        }
    }

    private void _addFolderToFoldersList(String folderName) throws IOException
    {
        try(FileWriter foldersListWriter = new FileWriter(Paths.get(_directory, _rootFolderName, _foldersListFileName).toString(), true))
        {
            foldersListWriter.write(folderName + "\n");
        }
    }

    private void _createFoldersList() throws IOException, FileAlreadyExistsException
    {
        if(!(new File(Paths.get(_directory, _rootFolderName, _foldersListFileName).toString()).createNewFile()))
        {
            throw new FileAlreadyExistsException(Paths.get(_directory, _rootFolderName, _foldersListFileName).toString());
        }
    }

    private String _directory = null;
    public static final String _rootFolderName = ".Snapshot";
    public static final String _foldersListFileName = "FoldersList";
    public static final String _imagesListFileName = "ImagesList";
}
