package pl.polsl.models.snapshot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SnapshotFolder is a class representing a single folder in .Snapshot directory.
 * It provides the capability of reading list of images contained in folder and 
 * getting SnapshotImage objects associated with them.
 */
public class SnapshotFolder 
{
    /**
     * Constructor
     * @param folderName path to folder associated with this object
     */
    public SnapshotFolder(String folderName)
    {
        _folderName = folderName;
    }

    /**
     * Returns the list of images contained in folder
     * @return list of images
     * @throws IOException this exception is thrown when program can't read images list from file
     */
    public List<String> getImagesList() throws IOException
    {
        try(Stream<String> lines = Files.lines(Paths.get(_folderName, SnapshotWriter._imagesListFileName)))
        {
            return lines.collect(Collectors.toList());
        }
    }

    /**
     * Returns SnapshotImage object associated with desired image
     * @param imageRealPath real path to image on disk, can be aquired from getImagesList.
     * @return instance of SnapshotImage
     * @throws IOException this exception is thrown when program can't find provided image
     */
    public SnapshotImage getSnapshotImage(String imageRealPath) throws IOException
    {
        File f = new File(imageRealPath);
        if(!f.isFile())
        {
            throw new IOException();
        }
        
        return new SnapshotImage(_folderName, imageRealPath);
    }

    private String _folderName;
}
