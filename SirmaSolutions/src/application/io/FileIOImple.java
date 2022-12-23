package application.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import application.io.FileIO;

public class FileIOImple implements FileIO {

    @Override
    public List<String> read(String filePath) {
        List<String> content = new ArrayList<>();

        File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    content.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            //print log here
        }

        return content;
    }

    @Override
    public void write(String fileContent, String file)  {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(fileContent);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //print log here
        }
    }
}