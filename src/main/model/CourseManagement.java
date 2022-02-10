package model;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.DOTExporter;

/*
This is CourseManagement System that store all the courses and their relationships across all the faculties.
it should have the following functionalities:
(1) add a new course to the system
(2) change the prerequisite relation
(3) change the information of the courses---
(4) delete the courses from school's database
(5) search for all the prerequisites of a certain course
(6) find all the courses needed to be taken for specific course
 */

import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

public class CourseManagement {
    private Graph<Integer, DefaultEdge> courseGraph;
    private HashMap<Integer, Course> courseMap;

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

    // REQUIRES: two List-like object to indicate those courses being of its prerequisites and taking courses
    //           as prerequisites
    // MODIFIES: this
    // EFFECT: add a course together with its prerequisites and the courses taking it as prerequisites.
    //          success in added will return ture

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
    // EFFECT: return a list of prerequisites for input course
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

    // REQUIRES: the course name is valid
    // EFFECT: return a list of all the courses that must be taken before registration of input course

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

    public Set<Integer> getAllCoursesID() {
        return courseGraph.vertexSet();
    }

    private boolean containCourse(Integer courseID) {
        return getAllCoursesID().contains(courseID);
    }

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

    public String displayCurrentCourseGraph() {
        return displayCourseGraph(this.courseGraph);
    }


    public Course getCourse(Integer courseID) {
        return courseMap.get(courseID);
    }

    public Integer getCourseID(Course course) {
        Set<Integer> idSet = courseMap.keySet();
        for (Integer i : idSet) {
            if (courseMap.get(i) == course) {
                return i;
            }
        }
        return null;
    }

}
