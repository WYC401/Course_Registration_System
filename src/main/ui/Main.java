package ui;

import org.jgrapht.*;
import org.jgrapht.graph.*;



import java.io.*;
import java.net.*;
import java.util.*;



import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.*;
import org.jgrapht.traverse.*;







public class Main {
    public static void main(String[] args) throws URISyntaxException {
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
        URI start = g
                .vertexSet().stream().filter(uri -> uri.getHost().equals("www.jgrapht.org")).findAny()
                .get();

        DOTExporter<URI, DefaultEdge> exporter =
                new DOTExporter<>(v -> v.getHost().replace('.', '_'));
        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.toString()));
            return map;
        });
        Writer writer = new StringWriter();
        exporter.exportGraph(g, writer);
        System.out.println(writer.toString());

    }
}



