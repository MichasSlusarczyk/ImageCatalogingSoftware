package pl.polsl.models.snapshot;

public class SnapshotImage 
{
    /**
     * Constructor
     * @param folderPath 
     * @param imageRealPath
     */
    public SnapshotImage(String folderPath, String imageRealPath)
    {
        _folderPath = folderPath;
        _imageRealPath = imageRealPath;
    }

    private String _folderPath;
    private String _imageRealPath;
}
