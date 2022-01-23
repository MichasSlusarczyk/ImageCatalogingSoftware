package pl.polsl.models;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Wiktoria Fica
 */

public class CSVReaderTest {

    CSVReader CSVReader;

    @BeforeEach
    public void setUp() {
        CSVReader = new CSVReader();
    }

    @ParameterizedTest
    @CsvSource({"src\\test\\resources\\emptyFile.txt,;",
        "src\\test\\resources\\emptyFileThatDoesNotExist.txt,%"})
    public void CSVReaderEmptyFileTest(String path, String delimiter) {
        //WHEN
        ArrayList<Object[]> readCSVFile = (ArrayList<Object[]>) CSVReader.read(path, delimiter);
        //THEN
        assertTrue(readCSVFile.isEmpty(), "If the file is empty then the method should have returned "
                + "an empty array.");
    }

    @ParameterizedTest
    @CsvSource({"src\\test\\resources\\fileLimitedBySemicolon.txt,;",
        "src\\test\\resources\\fileLimitedByModulo.txt,%", "src\\test\\resources\\fileLimitedByLIM.txt,LIM"})
    public void CSVReaderFileDelimitedTest(String path, String delimiter) {
        //GIVEN
        List<Object[]> expectedContentOfFile = new ArrayList();
        expectedContentOfFile.add(new Object[]{"Dog", 2, "07.01.2022"});
        expectedContentOfFile.add(new Object[]{"Cat", 15, "07.01.2022"});
        expectedContentOfFile.add(new Object[]{"Horse", 11, "07.01.2022"});
        expectedContentOfFile.add(new Object[]{"Bear", 7, "07.01.2022"});
        //WHEN
        ArrayList<Object[]> readCSVFile = (ArrayList<Object[]>) CSVReader.read(path, delimiter);
        //THEN
        if (expectedContentOfFile.size() == readCSVFile.size()) {
            for (int i = 0; i < readCSVFile.size(); i++) {
                assertArrayEquals(expectedContentOfFile.get(i), readCSVFile.get(i), "The rows at index "
                        + i + " are not the same.");
            }
        }
    }
}
