package persistence;

import model.RegistrationSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonWriter {
    private static final int TAPNUM = 4;
    private String path;
    private PrintWriter writer;

    public JsonWriter(String path) {
        this.path = path;
    }

    public void  open() throws FileNotFoundException {
        writer = new PrintWriter(new File(path));
    }

    public void write(RegistrationSystem registrationSystem) throws IOException {
        writer.print(registrationSystem.toJson().toString(TAPNUM));
    }

    public void close() {
        writer.close();
    }
}
