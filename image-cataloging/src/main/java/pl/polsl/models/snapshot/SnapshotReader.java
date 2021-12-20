package pl.polsl.models.snapshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SnapshotReader 
{
    public SnapshotReader(String path) throws IOException
    {
        Path rootPath = Paths.get(path, SnapshotWriter._rootFolderName);
        if(!Files.exists(rootPath))
        {
            throw new IOException();
        }

        _directory = Paths.get(path).toString();
    }

    public List<String> getFoldersList() throws IOException
    {
        try(Stream<String> lines = Files.lines(Paths.get(_directory, SnapshotWriter._rootFolderName, SnapshotWriter._foldersListFileName)))
        {
            return lines.collect(Collectors.toList());
        }
    }

    public SnapshotFolder getSnapshotFolder(String folderName) throws IOException
    {
        Path path = Paths.get(_directory, SnapshotWriter._rootFolderName, folderName);
        if(Files.exists(path))
        {
            return new SnapshotFolder(path.toString());
        }

        throw new IOException();
    }
    
    private String _directory = null;
}
