import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MergeSort {

    public static Path sortFile(Path file) throws IOException {
        List<Path> sortedFiles = splitToSortedFiles(file);
        return mergeFiles(sortedFiles);
    }

    private static List<Path> splitToSortedFiles(Path file) throws IOException {
        List<Path> files = new ArrayList<>();
        int maxFileSize = 1024*1024;

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            while (true) {
                List<String> strings = new ArrayList<>();
                String line = reader.readLine();
                for (int i = 0; i < maxFileSize; i++) {
                    if (line == null) {
                        files.add(getSortedFile(strings));
                        return files;
                    }
                    strings.add(line);
                    line = reader.readLine();
                }
                files.add(getSortedFile(strings));
            }
        }
    }

    private static Path getSortedFile(List<String> strings) throws IOException {
        Collections.sort(strings);

        Files.createDirectories(Paths.get(System.getProperty("user.dir") + "\\temp"));
        Path tempFile = Files.createTempFile(Paths.get(System.getProperty("user.dir") + "\\temp"), "temp", ".txt");

        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            for (String s : strings) {
                writer.write(s);
                writer.newLine();
            }
        }
        return tempFile;
    }

    private static Path mergeFiles(List<Path> files) throws IOException {
        Queue<Path> filesQueue = new PriorityQueue<>();
        filesQueue.addAll(files);

        Path currentFile = filesQueue.poll();
        Path nextFile = filesQueue.poll();

        while (currentFile != null || nextFile != null) {
            Path sortedFile = Paths.get(System.getProperty("user.dir") + "\\temp\\" + "SortedFile.txt");
            Files.deleteIfExists(sortedFile);
            if (currentFile != null && nextFile == null) {
                return Files.move(currentFile, currentFile.resolveSibling(sortedFile));
            } else if (nextFile != null && currentFile == null) {
                return Files.move(nextFile, nextFile.resolveSibling(sortedFile));
            }

            Path mergedSortedTemp = Files.createTempFile(Paths.get(System.getProperty("user.dir") + "\\temp"), "mergedSortedTemp", ".txt");
            try (BufferedWriter writer = Files.newBufferedWriter(mergedSortedTemp)) {
                try (BufferedReader reader = Files.newBufferedReader(currentFile);
                     BufferedReader secondReader = Files.newBufferedReader(nextFile)) {

                    String firstLine = reader.readLine();
                    String secondLine = secondReader.readLine();

                    while (firstLine != null && secondLine != null) {
                        int compare = firstLine.compareTo(secondLine);
                        if (compare < 0) {
                            writer.write(firstLine);
                            writer.newLine();
                            firstLine = reader.readLine();
                        } else {
                            writer.write(secondLine);
                            writer.newLine();
                            secondLine = secondReader.readLine();
                        }
                    }
                    if (firstLine == null) {
                        while (secondLine != null) {
                            writer.write(secondLine);
                            writer.newLine();
                            secondLine = secondReader.readLine();
                        }
                    } else {
                        while (firstLine != null) {
                            writer.write(firstLine);
                            writer.newLine();
                            firstLine = reader.readLine();
                        }
                    }
                }
                filesQueue.add(mergedSortedTemp);
            }
            Files.deleteIfExists(currentFile);
            Files.deleteIfExists(nextFile);

            currentFile = filesQueue.poll();
            nextFile = filesQueue.poll();
        }
        return null;
    }
}
