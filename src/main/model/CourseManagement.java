package model;
import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
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
    private Graph<Integer,DefaultEdge> courseGraph;
    private HashMap<Integer, Course> courseMap;
    public CourseManagement() {
        courseGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        courseMap = new HashMap<>();
    }

    // MODiFIES: this
    // EFFECT: add a course into courseManagement system.
    public void addCourses(Course course) {
        courseGraph.addVertex(course.getCourseID());
        courseMap.put(course.getCourseID(),course);
    }

    // REQUIRES: two List-like object to indicate those courses being of its prerequisites and taking courses
    //           as prerequisites
    // MODIFIES: this
    // EFFECT: add a course together with its prerequisites and the courses taking it as prerequisites.
    //          success in added will return ture

    public boolean setPrerequisites(Course course, Course prerequisite) {
        if(getAllCoursesID().contains(prerequisite.getCourseID()) && getAllCoursesID().contains(course.getCourseID())) {
            courseGraph.addEdge(course.getCourseID(), prerequisite.getCourseID());
            return true;
        }
        return false;
    }


    // REQUIRES: the course name(id) is valid and its prerequisites list is also valid
    // MODIFIES: this
    // EFFECT: change the prerequisite of certain course. return true if it works
    public boolean changePrerequisites() {
        return false;
    }

    // REQUIRES: the course name(id) is valid
    // MODIFIES: this
    // EFFECT: delete input course and its relationship, return true
    //         failure in deletion will return false
    public boolean deleteCourse(Course course) {
        if(containCourse(course.getCourseID())) {
            courseGraph.removeVertex(course.getCourseID());
            courseMap.remove(course.getCourseID(),course);
            return true;
        }
        return false;
    }

    // REQUIRES: the course name (id) is valid
    // EFFECT: return a list of prerequisites for input course
    public Set<Integer> returnPrerequisitesID(Integer courseID) {
        if(!containCourse(courseID)) {
            return null;
        }
        Set<DefaultEdge> outgoingEdges = courseGraph.outgoingEdgesOf(courseID);
        Set<Integer> prerequisitesSet = new HashSet<>();
        for(DefaultEdge e: outgoingEdges) {
            prerequisitesSet.add(courseGraph.getEdgeTarget(e));
        }
        return prerequisitesSet;
    }

    // REQUIRES: the course name is valid
    // EFFECT: return a list of all the courses that must be taken before registration of input course
    public Graph<Integer,DefaultEdge> returnAllNeededCoursesID(Set<Integer> courseAlreadyTaken, Integer courseID) {
        AllDirectedPaths<Integer,DefaultEdge> allPathToCourse = new AllDirectedPaths<>(courseGraph);
        Set<Integer> singleCourseSet = new HashSet<Integer>();
        singleCourseSet.add(courseID);
        List<GraphPath<Integer,DefaultEdge>> pathList = allPathToCourse.getAllPaths(courseAlreadyTaken, singleCourseSet,
                true,null);
        GraphPath<Integer, DefaultEdge> tempMinLength = pathList.get(0);
        for(GraphPath<Integer,DefaultEdge> gp: pathList) {
            if(gp.getLength() < tempMinLength.getLength()) {
                tempMinLength = gp;
            }
        }
        return tempMinLength.getGraph();
    }

    public Set<Integer> getAllCoursesID() {
        return courseGraph.vertexSet();
    }

    private boolean containCourse(Integer courseID) {
        return getAllCoursesID().contains(courseID);
    }

    public String displayCourseGraph(Graph<Integer,DefaultEdge> courseGraph) {
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
    public void displayCurrentCourseGraph() {
        System.out.println(displayCourseGraph(this.courseGraph));
    }

    public Course getCourse(Integer courseID) {
        return courseMap.get(courseID);
    }

    public Integer getCourseID(Course course) {
        Set<Integer> idSet = courseMap.keySet();
        for(Integer i : idSet) {
            if(courseMap.get(i) == course) {
                return i;
            }
        }
        return null;
    }

}
