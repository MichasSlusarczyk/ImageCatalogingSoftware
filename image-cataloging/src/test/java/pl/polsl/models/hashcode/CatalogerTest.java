package pl.polsl.models.hashcode;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.polsl.models.snapshot.SnapshotReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Karolina Kołek
 */
class CatalogerTest {

    private Cataloger cataloger;

    @BeforeEach
    void setUp() {
        cataloger = new Cataloger();
    }

    @Test
    void catalog() throws IOException {

        // GIVEN
        String realFolderPath = "src\\test\\resources\\integratingTestDirectory";
        ArrayList<String> imagePaths = new ArrayList<>(Arrays.asList("src\\test\\resources\\integratingTestDirectory\\a.png",
                "src\\test\\resources\\integratingTestDirectory\\b.png",
                "src\\test\\resources\\integratingTestDirectory\\c.png",
                "src\\test\\resources\\integratingTestDirectory\\d.png",
                "src\\test\\resources\\integratingTestDirectory\\e.png",
                "src\\test\\resources\\integratingTestDirectory\\f.png"));
        List<String> expectedFoldersList = new ArrayList<>(Arrays.asList("Folder1", "Folder2", "Folder3"));

        // WHEN
        cataloger.catalog(realFolderPath, imagePaths);
        SnapshotReader snapshotReader = new SnapshotReader("src\\test\\resources\\integratingTestDirectory");
        List<String> foldersList = snapshotReader.getFoldersList();

        // THEN
        assertEquals(expectedFoldersList, foldersList);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path path = Paths.get("src\\test\\resources\\integratingTestDirectory\\.Snapshot");
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}