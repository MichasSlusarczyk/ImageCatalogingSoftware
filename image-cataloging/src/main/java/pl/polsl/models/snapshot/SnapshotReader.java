package pl.polsl.models.snapshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SnapshotReader is a class used to get data from .Snapshot directory
 */
public class SnapshotReader {

    private String directory;

    /**
     * Constructor. Associates object with specific .Snapshot directory.
     * @param path Path to .Snapshot
     * @throws IOException this exception is thrown when program can't find given .Snapshot
     */
    public SnapshotReader(String path) throws IOException
    {
        Path rootPath = Paths.get(path, SnapshotWriter.ROOT_FOLDER_NAME);
        if(!Files.exists(rootPath))
        {
            throw new IOException();
        }

        directory = Paths.get(path).toString();
    }

    /**
     * Returns the list of folders contained in .Snapshot
     * @return list of folder names
     * @throws IOException this exception is thrown when program can't open file with folders names
     */
    public List<String> getFoldersList() throws IOException
    {
        try(Stream<String> lines = Files.lines(Paths.get(directory, SnapshotWriter.ROOT_FOLDER_NAME, SnapshotWriter.FOLDERS_LIST_FILE_NAME)))
        {
            return lines.collect(Collectors.toList());
        }
    }

    /**
     * Returns SnapshotFolder object associated with desired folder
     * @param folderName name of the chosen folder
     * @return instance of SnapshotFolder
     * @throws IOException throws this exception when there isn't such folder in snapshot
     */
    public SnapshotFolder getSnapshotFolder(String folderName) throws IOException
    {
        Path path = Paths.get(directory, SnapshotWriter.ROOT_FOLDER_NAME, folderName);
        if(Files.exists(path))
        {
            return new SnapshotFolder(path.toString());
        }

        throw new IOException();
    }
}