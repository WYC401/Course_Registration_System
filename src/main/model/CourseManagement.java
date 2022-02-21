package model;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.json.JSONExporter;
import org.json.JSONObject;
import persistence.Writable;
import sun.misc.IOUtils;
import sun.misc.IOUtils.*;
import sun.nio.ch.IOUtil;

/*
This is CourseManagement System that stores all the courses and their relationships across all the faculties.
it should have the following functionalities:
(1) add a new course to the system
(2) change the prerequisite relation
(3) change the information of the courses---
(4) delete the courses from school's database
(5) search for all the prerequisites of a certain course
(6) find all the courses needed to be taken for specific course
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class CourseManagement implements Writable {
    private Graph<Integer, DefaultEdge> courseGraph;
    private HashMap<Integer, Course> courseMap;

    //EFFECT: courseMap used to link the ID with course are created and the courses are stored in courseGraph for their
    //      relationship of prerequisites
    public CourseManagement() {
        courseGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        courseMap = new HashMap<>();
    }

    // MODiFIES: this
    // EFFECT: add a course into courseManagement system.
    public void addCourses(Course course) {
        courseGraph.addVertex(course.getCourseID());
        courseMap.put(course.getCourseID(), course);
    }

    // REQUIRES: two course objects to indicate those courses being of its prerequisites and taking courses
    //           as prerequisites
    // MODIFIES: this
    // EFFECT: add a course together with its prerequisites and the courses taking it as prerequisites.
    //          success in adding will return ture
    public boolean setPrerequisites(Course course, Course prerequisite) {
        if (getAllCoursesID().contains(prerequisite.getCourseID())
                && getAllCoursesID().contains(course.getCourseID())) {
            courseGraph.addEdge(course.getCourseID(), prerequisite.getCourseID());
            return true;
        }
        return false;
    }


    // REQUIRES: the course name(id) is valid
    // MODIFIES: this
    // EFFECT: delete input course and its relationship, return true
    //         failure in deletion will return false
    public boolean deleteCourse(Course course) {
        if (containCourse(course.getCourseID())) {
            courseGraph.removeVertex(course.getCourseID());
            courseMap.remove(course.getCourseID(), course);
            return true;
        }
        return false;
    }

    // REQUIRES: the course name (id) is valid
    // EFFECT: return a set of prerequisites for input course
    public Set<Integer> returnPrerequisitesID(Integer courseID) {
        if (!containCourse(courseID)) {
            return null;
        }
        Set<DefaultEdge> outgoingEdges = courseGraph.outgoingEdgesOf(courseID);
        Set<Integer> prerequisitesSet = new HashSet<>();
        for (DefaultEdge e : outgoingEdges) {
            prerequisitesSet.add(courseGraph.getEdgeTarget(e));
        }
        return prerequisitesSet;
    }

    // REQUIRES: the course ID is valid
    // EFFECT: return a graph of all the courses that must be taken before registration of input course

    @SuppressWarnings("methodlength")
    public Graph<Integer, DefaultEdge> returnAllNeededCoursesID(Set<Integer> courseAlreadyTaken, Integer courseID) {
        Stack<Integer> s = new Stack<>();
        HashMap<Integer, Integer> tempVisitedMap = new HashMap<>();
        for (Integer i : getAllCoursesID()) {
            tempVisitedMap.put(i, 0);
        }
        for (Integer i : courseAlreadyTaken) {
            tempVisitedMap.put(i, 1);
        }

        if (!containCourse(courseID)) {
            return null;
        }
        s.push(courseID);
        while (!s.empty()) {
            int temp = s.pop();
            if (tempVisitedMap.get(temp) == 0) {
                tempVisitedMap.put(temp, 1);
                Set<DefaultEdge> outgoingEdges = courseGraph.outgoingEdgesOf(temp);
                if (!Objects.isNull(outgoingEdges)) {
                    for (DefaultEdge e : outgoingEdges) {
                        s.push(courseGraph.getEdgeTarget(e));
                    }
                }
            }
        }
        Set<Integer> tempVisitedSet = new HashSet<>();
        for (Integer i : getAllCoursesID()) {
            if (tempVisitedMap.get(i) == 1) {
                tempVisitedSet.add(i);
            }
        }
        return new AsSubgraph<Integer, DefaultEdge>(courseGraph, tempVisitedSet);


    }

    //EFFECT: return all the coursesID in the management system
    public Set<Integer> getAllCoursesID() {
        return courseGraph.vertexSet();
    }

    //EFFECT: return true if a course corresponding to courseID is in course set of management system.
    private boolean containCourse(Integer courseID) {
        return getAllCoursesID().contains(courseID);
    }

    //EFFECT: return a string that stores course given
    public String displayCourseGraph(Graph<Integer, DefaultEdge> courseGraph) {
        DOTExporter<Integer, DefaultEdge> exporter =
                new DOTExporter<>(v -> String.valueOf(v));
        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(String.valueOf(v)));
            return map;
        });
        Writer writer = new StringWriter();
        exporter.exportGraph(courseGraph, writer);
        return writer.toString();
    }

    //EFFECT: return a string that stores all the course relationship in the course management system
    public String displayCurrentCourseGraph() {
        return displayCourseGraph(this.courseGraph);
    }

    //EFFECT: return the corresponding course object if a courseID is given
    public Course getCourse(Integer courseID) {
        return courseMap.get(courseID);
    }

    //EFFECT: return the courseID if a course is given
    public Integer getCourseID(Course course) {
        Set<Integer> idSet = courseMap.keySet();
        for (Integer i : idSet) {
            if (courseMap.get(i) == course) {
                return i;
            }
        }
        return null;
    }

    @Override
    public JSONObject toJson() throws IOException {
        JSONObject json = new JSONObject();
        json.put("courseMap", courseMapToJson());
        json.put("courseGraph", courseGraphToJson());
        return json;
    }

    public JSONObject courseMapToJson() {
        JSONObject json = new JSONObject();
        for(Integer i: courseMap.keySet()) {
            json.put(String.valueOf(i), courseMap.get(i).toJson());
        }
        return json;
    }

    public JSONObject courseGraphToJson() throws IOException {
        JSONObject json = new JSONObject();
        JSONExporter<Integer, DefaultEdge> exporter = new JSONExporter<>(v -> String.valueOf(v));
        File dataDirectory = new File("./data");
        dataDirectory.mkdir();
        File tempFile = new File("./data/temp.json");
        PrintWriter writer = new PrintWriter(tempFile);
        exporter.exportGraph(courseGraph, writer);

        if(tempFile.exists()) {
            StringBuilder contentBuilder = new StringBuilder();
            try(Stream<String> stream = Files.lines(Paths.get("./data/temp.json"), StandardCharsets.UTF_8)) {
                stream.forEach(s -> contentBuilder.append(s));
                return new JSONObject(contentBuilder.toString());
            }
        } else {
            throw new FileNotFoundException();
        }

    }

}
