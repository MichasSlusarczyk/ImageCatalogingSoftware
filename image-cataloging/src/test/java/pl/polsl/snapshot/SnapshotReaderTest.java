package pl.polsl.snapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.models.snapshot.SnapshotReader;

/**
 *
 * @author Wiktoria Fica
 */
public class SnapshotReaderTest {  
    
    @ParameterizedTest
    @CsvSource({"src\\test\\resources\\snapshotTestDirectory, Folder1;Folder2;Folder3",
    "src\\test\\resources\\snapshotTestDirectory2, Folder1;Folder2;Folder3;Folder4;Folder5"})
    public void getFoldersListTest(String folderPath, String fileNames) throws IOException {
      //GIVEN
      ArrayList<String> expectedImages = new ArrayList();
      String [] fileNamesArray = fileNames.split(";");
      expectedImages.addAll(Arrays.asList(fileNamesArray));
      SnapshotReader snapshotReader = new SnapshotReader(folderPath);
      //WHEN
       List<String> imagesList = snapshotReader.getFoldersList();
      //THEN
      assertEquals(expectedImages, imagesList);
    }
}
