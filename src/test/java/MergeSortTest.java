import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MergeSortTest {

    @Test
    public void sortFile() throws IOException {
        Path actualSortedFile = MergeSort.sortFile(Paths.get("src/test/resources/TestFile.txt"));
        Path expectedSortedFile = Paths.get("src/test/resources/ExpectedSortedFile.txt");

        List<String> actual = Files.readAllLines(actualSortedFile);
        List<String> expected = Files.readAllLines(expectedSortedFile);

        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }
}