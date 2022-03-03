package persistence;

import model.RegistrationSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

// this class serves to write the registration system into json file

public class JsonWriter {
    private static final int TAPNUM = 4;
    private String path;
    private PrintWriter writer;

    //EFFECT: specify target writing path
    public JsonWriter(String path) {
        this.path = path;
    }

    //EFFECT: instantiate a new writer. if the target path is not valid, throw FileNotFoundException
    public void  open() throws FileNotFoundException {
        writer = new PrintWriter(new File(path));
    }

    //EFFECT: write RegistrationSystem to the target json file. If the temporary file involved in converting course
    // graph is not found, throw IOException.
    public void write(RegistrationSystem registrationSystem) throws IOException {
        writer.print(registrationSystem.toJson().toString(TAPNUM));
    }

    //EFFECT: close the writer.
    public void close() {
        writer.close();
    }
}
