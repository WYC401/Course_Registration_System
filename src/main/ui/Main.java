package ui;

import model.RegistrationSystem;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.*;
import org.jgrapht.nio.json.JSONExporter;
import org.jgrapht.nio.json.JSONImporter;
import org.jgrapht.traverse.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import org.jgrapht.util.SupplierUtil;
import org.json.*;
import persistence.JsonReader;

/*
This is main function where registration app runs
 */
public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        new RegistrationApp();

    }


}



