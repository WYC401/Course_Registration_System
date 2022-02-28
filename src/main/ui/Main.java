package ui;
import jdk.nashorn.internal.parser.JSONParser;
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
        //new RegistrationApp();


        Graph<URI, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

        URI google = new URI("http://www.google.com");
        URI wikipedia = new URI("http://www.wikipedia.org");
        URI jgrapht = new URI("http://www.jgrapht.org");

        // add the vertices
        g.addVertex(google);
        g.addVertex(wikipedia);
        g.addVertex(jgrapht);

        // add edges to create linking structure
        g.addEdge(jgrapht, wikipedia);
        g.addEdge(google, jgrapht);
        g.addEdge(google, wikipedia);
        g.addEdge(wikipedia, google);


        /*
        Graph<Integer,DefaultEdge> g2 = new DefaultDirectedGraph(SupplierUtil.createIntegerSupplier(1),SupplierUtil.DEFAULT_EDGE_SUPPLIER,false);
        FileReader fr = new FileReader(new File("./data/trialexample.json"));
        JSONImporter<Integer, DefaultEdge> importer = new JSONImporter<>();
        importer.importGraph(g2, fr);

        PrintWriter writer;
        writer = new PrintWriter(new File("./data/trialexample2.json"));
        JSONExporter<Integer, DefaultEdge> exporter =
                new JSONExporter<>(v -> String.valueOf(v));
        exporter.exportGraph(g2, writer);
        writer.close();
        */

        JsonReader reader = new JsonReader("./data/registrationSystemCore.json");
        RegistrationSystem rs = reader.read();
        PrintWriter writer = new PrintWriter(new File("./data/testReaderNormalRegistrationSystem.json"));
        writer.print(rs.toJson());
        writer.close();



    }
}



