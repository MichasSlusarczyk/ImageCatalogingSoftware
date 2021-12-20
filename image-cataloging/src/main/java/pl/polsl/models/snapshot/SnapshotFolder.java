package pl.polsl.models.snapshot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SnapshotFolder 
{
    public SnapshotFolder(String folderPath)
    {
        _folderPath = folderPath;
    }

    public List<String> getImagesList() throws IOException
    {
        try(Stream<String> lines = Files.lines(Paths.get(_folderPath, SnapshotWriter._imagesListFileName)))
        {
            return lines.collect(Collectors.toList());
        }
    }

    public SnapshotImage getSnapshotImage(String imageRealPath) throws IOException
    {
        try(FileReader fr = new FileReader(Paths.get(_folderPath, SnapshotWriter._imagesListFileName).toString()))
        {
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while((line = br.readLine()) != null)
            {
                if(line.equals(imageRealPath))
                {
                    return new SnapshotImage(_folderPath, imageRealPath);
                }
            }
        }

        throw new IOException();
    }

    private String _folderPath;
}
