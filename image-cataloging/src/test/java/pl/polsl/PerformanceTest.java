package pl.polsl;

import org.junit.jupiter.api.Test;
import pl.polsl.models.hashcode.Cataloger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Karolina Kołek
 */
public class PerformanceTest {

    @Test
    void catalog() throws IOException {
        // GIVEN
        Cataloger cataloger = new Cataloger();
        String realFolderPath = "src\\test\\resources\\folderWith500Images";
        ArrayList<String> imagePaths = createImagePathsList(realFolderPath);

        for(int i = 0; i < 100; i++){

            // WHEN
            long millisActualTime = System.currentTimeMillis();
            cataloger.catalog(realFolderPath, imagePaths);
            long executionTime = System.currentTimeMillis() - millisActualTime;

            // THEN
            assertTrue(executionTime < 120000);
            clean(realFolderPath + "\\.Snapshot");
        }
    }

    private ArrayList<String> createImagePathsList(String realFolderPath){
        ArrayList<String> imagePaths = new ArrayList<>();
        File folder = new File(realFolderPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                imagePaths.add(listOfFiles[i].getName());
            }
        }
        return imagePaths;
    }

    private void clean(String snapshotPath) throws IOException {
        Path path = Paths.get(snapshotPath);
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}