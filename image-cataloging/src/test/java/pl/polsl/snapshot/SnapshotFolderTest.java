package pl.polsl.snapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.models.snapshot.SnapshotFolder;

/**
 *
 * @author Wiktoria Fica
 */
public class SnapshotFolderTest {
    
  //  SnapshotFolder snapshotFolder;
    
    @BeforeEach
    public void setUp() {
     //   this.snapshotFolder = new SnapshotFolder("src\\test\\resources\\snapshotTestFolder");
    }
    
    @ParameterizedTest
    @CsvSource({"src\\test\\resources\\snapshotTestDirectory\\.Snapshot\\Folder1, C:\\Users\\IO\\Desktop\\Folder\\b.png;C:\\Users\\IO\\Desktop\\Folder\\d.png", 
        "src\\test\\resources\\snapshotTestDirectory\\.Snapshot\\Folder2, C:\\Users\\IO\\Desktop\\Folder\\e.png"})
    public void getImagesListTest(String folderPath, String fileNames) throws IOException {
      //GIVEN
      ArrayList<String> expectedImages = new ArrayList();
      String [] fileNamesArray = fileNames.split(";");
      expectedImages.addAll(Arrays.asList(fileNamesArray));
      SnapshotFolder snapshotFolder = new SnapshotFolder(folderPath);
   //   snapshotFolder.setFolderPath(folderPath);
      //WHEN
       List<String> imagesList = snapshotFolder.getImagesList();
      //THEN
      assertEquals(expectedImages, imagesList);
    }
}
