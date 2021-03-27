import generators.FileGenerator;
import generators.StringGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            FileGenerator fileGenerator = new FileGenerator(new StringGenerator());

            System.out.print("Number of lines: ");
            int numberOfLines = Integer.parseInt(reader.readLine());
            System.out.print("Max line length: ");
            int maxLineLength = Integer.parseInt(reader.readLine());
            System.out.println("Sorting...");

            Path textFile = fileGenerator.generateFile(numberOfLines, maxLineLength);
            MergeSort.sortFile(textFile);
            System.out.println("The sorted file is located in the temp folder");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
