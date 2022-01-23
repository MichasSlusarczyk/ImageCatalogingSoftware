package pl.polsl.models.snapshot;


/**
 * SnapshotImage is a class representing a single image in .Snapshot directory
 */
public class SnapshotImage {
    private String folderPath;
    private String imageRealPath;

    /**
     * Constructor
     * @param folderPath 
     * @param imageRealPath
     */
    public SnapshotImage(String folderPath, String imageRealPath)
    {
        this.folderPath = folderPath;
        this.imageRealPath = imageRealPath;
    }
}