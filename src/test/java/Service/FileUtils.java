package Service;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static void clearFile(String filename) {
        try (FileWriter fw = new FileWriter(filename, false)) {
            // false = do not append, overwrite the file
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<Entitati>\n" +
                    "</Entitati>"); // Write nothing to effectively clear the file
        } catch (IOException e) {
            System.out.println("Eroare la stergerea continutului fisierului: " + e.getMessage());
        }
    }
}
