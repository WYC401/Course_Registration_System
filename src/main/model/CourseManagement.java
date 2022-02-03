package model;
import org.jgrapht.*;
import org.jgrapht.graph.*;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseManagement {
    private Graph<Course,DefaultEdge> courseGraph;

    public CourseManagement() {
        courseGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    // MODiFIES: this
    // EFFECT: add a course into courseManagement system.
    public void addCourses(Course course) {
        courseGraph.addVertex(course);
    }

    // REQUIRES: two List-like object to indicate those courses being of its prerequisites and taking courses
    //           as prerequisites
    // MODIFIES: this
    // EFFECT: add a course together with its prerequisites and the courses taking it as prerequisites.
    //          success in added will return ture

    public boolean setPrerequisites(Course course, Course prerequisite) {
        if(getAllCourses().contains(prerequisite)) {
            courseGraph.addEdge(course, prerequisite);
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
        if(containCourse(course)) {
            courseGraph.removeVertex(course);
            return true;
        }
        return false;
    }

    // REQUIRES: the course name (id) is valid
    // EFFECT: return a list of prerequisites for input course
    public Set<Course> returnPrerequisites(Course course) {
        Set<DefaultEdge> outgoingEdges = courseGraph.outgoingEdgesOf(course);
        Set<Course> prerequisitesSet = new HashSet<Course>();
        for(DefaultEdge e: outgoingEdges) {
            prerequisitesSet.add(courseGraph.getEdgeTarget(e));
        }
        return prerequisitesSet;
    }

    // REQUIRES: the course name is valid
    // EFFECT: return a list of all the courses that must be taken before registration of input course
    public ArrayList<Course> returnAllNeededCourses() {
        return null;
    }

    public Set<Course> getAllCourses() {
        return courseGraph.vertexSet();
    }

    private boolean containCourse(Course course) {
        return getAllCourses().contains(course);
    }
}
