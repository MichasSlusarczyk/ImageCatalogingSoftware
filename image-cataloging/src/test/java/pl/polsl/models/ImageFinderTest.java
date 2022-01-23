package pl.polsl.models;

import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


/**
 *
 * @author Wiktoria Fica
 */
public class ImageFinderTest {
    ImageFinder imageFinder;
    
    @BeforeEach
    public void setUp() {
        this.imageFinder = new ImageFinder();
    }
    
    @ParameterizedTest
    @CsvSource({"file.txt, txt", "file.mp4, mp4", "file..doc, doc"}) 
    public void getExtensionTest(String filename, String expectedExtension) {
        //WHEN
        String extension = imageFinder.getExtension(filename);
        //THEN
        assertEquals(expectedExtension, extension, "The method should have returned the file's extension which comes after the dot.");
    }
    
    @ParameterizedTest
    @CsvSource({"file.", "file", "file;txt"}) 
    public void getExtensionNoExtensionTest(String filename) {
        //WHEN
        String extension = imageFinder.getExtension(filename);
        //THEN
        assertTrue(extension.isEmpty(), "If the file's name passed as an argument does not have any extension then the "
                + "method should have returned an empty string.");
    }
    
    @ParameterizedTest
    @CsvSource({"src\\test\\resources\\testDirectoryWithThreeFiles, a.png;b.png;c.png", 
        "src\\test\\resources\\testDirectoryWithFourFiles, d.png;e.png;f.png;g.png"})
    public void getImagesTest(String startDirectoryPath, String fileNames) {
      //GIVEN
      ArrayList<File> expectedImages = new ArrayList();
      String [] fileNamesArray = fileNames.split(";");
      for(String fileName : fileNamesArray) {
          expectedImages.add(new File(startDirectoryPath + "\\" + fileName));
      }
      //WHEN
      ArrayList<File> foundImages = imageFinder.getImages(startDirectoryPath);
      //THEN
      assertEquals(expectedImages, foundImages, "The paths to the files contained directly in this directory "
              + "should have included the start directory path and the files' names.");
    }
    
    @ParameterizedTest
    @CsvSource({"src\\test\\resources\\emptyTestDirectory", 
        "src\\test\\resources\\testDirectoryWithNoImages"})
    public void getImagesDirectoryWithNoImagesTest(String startDirectoryPath) {
      //WHEN
      ArrayList<File> foundImages = imageFinder.getImages(startDirectoryPath);
      //THEN
      assertTrue(foundImages.isEmpty(), "If the start directory dos not contain any .png files then "
              + "the returned array should have been empty.");
    }
}
