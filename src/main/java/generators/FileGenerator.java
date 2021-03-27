package generators;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileGenerator {
    private StringGenerator stringGenerator;

    public FileGenerator(StringGenerator stringGenerator) {
        this.stringGenerator = stringGenerator;
    }

    public Path generateFile(int numberOfLines, int maxLineLength) throws IOException {
        Path path = Paths.get(System.getProperty("user.dir") + "\\temp" + "\\TextFile.txt");
        Files.createDirectories(path);
        Files.deleteIfExists(path);
        Path newFile = Files.createFile(path);

        try (BufferedWriter writer = Files.newBufferedWriter(newFile)) {
            for (int i = numberOfLines; i > 0; i--) {
                writer.write(stringGenerator.generateString(maxLineLength));
                writer.newLine();
            }
        }
        return newFile;
    }


}
