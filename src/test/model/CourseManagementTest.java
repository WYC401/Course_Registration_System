package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class CourseManagementTest {
    private CourseManagement courseManagement;
    static Course cpsc213 = new Course("ComputerSystem", 213, "This is a syllabus", "Meghan");
    static Course cpsc210 = new Course("ComputerSystem", 210, "This is a syllabus", "Meghan");
    static Course cpsc313 = new Course("ComputerSystem", 313, "This is a syllabus", "Meghan");
    static Course cpsc110 = new Course("ComputerSystem", 110, "This is a syllabus", "Meghan");
    static Course cpsc221 = new Course("ComputerSystem", 221, "This is a syllabus", "Meghan");
    static Course cpsc406 = new Course("ComputerSystem", 406, "This is a syllabus", "Meghan");

    @BeforeEach
    public void setup() throws IOException {
        courseManagement = new CourseManagement();
        courseManagement.addCourses(cpsc213);
        courseManagement.addCourses(cpsc210);
        courseManagement.addCourses(cpsc313);
        courseManagement.addCourses(cpsc110);
        courseManagement.addCourses(cpsc221);
        assertTrue(courseManagement.setPrerequisites(cpsc210, cpsc110));
        assertTrue(courseManagement.setPrerequisites(cpsc213, cpsc210));
        assertTrue(courseManagement.setPrerequisites(cpsc313, cpsc213));
        assertTrue(courseManagement.setPrerequisites(cpsc313, cpsc221));
        assertFalse(courseManagement.setPrerequisites(cpsc406, cpsc313));
        assertFalse(courseManagement.setPrerequisites(cpsc313, cpsc406));
        PrintWriter writer = new PrintWriter(new File("./data/sample.json"));
        writer.print(courseManagement.toJson().toString(4));
        writer.close();
        courseManagement.displayCurrentCourseGraph();

    }

    @Test
    public void testAddCourse() {
        Set<Integer> tempCourseSet = new HashSet<Integer>();
        tempCourseSet.add(213);
        tempCourseSet.add(210);
        tempCourseSet.add(110);
        tempCourseSet.add(313);
        tempCourseSet.add(221);
        assertEquals(tempCourseSet, courseManagement.getAllCoursesID());
        assertEquals(cpsc213, courseManagement.getCourse(213));
        assertNull(courseManagement.getCourse(20));
        assertEquals(213, courseManagement.getCourseID(cpsc213));
        assertNull(courseManagement.getCourseID(cpsc406));
    }

    @Test
    public void testSReturnPrerequisites() {
        Set<Integer> tempIDset = new HashSet<>();
        tempIDset.add(213);
        tempIDset.add(221);
        assertEquals(tempIDset, courseManagement.returnPrerequisitesID(cpsc313.getCourseID()));
    }

    @Test
    public void testReturnAllCoursesNeeded() {
        Set<Integer> tempIDSet = new HashSet<Integer>();
        tempIDSet.add(210);
        tempIDSet.add(213);
        System.out.print(courseManagement.displayCurrentCourseGraph());
        System.out.print(courseManagement.displayCurrentCourseGraph());
        System.out.print(courseManagement.displayCourseGraph(courseManagement
                .returnAllNeededCoursesID(tempIDSet, 313)));
        assertNull(courseManagement.returnAllNeededCoursesID(tempIDSet, 406));
    }

    @Test
    public void testReturnAllPrerequisites() {
        Set<Integer> tempIDSet = new HashSet<>();
        tempIDSet.add(213);
        tempIDSet.add(221);
        assertEquals(tempIDSet, courseManagement.returnPrerequisitesID(313));
        assertNull(courseManagement.returnPrerequisitesID(406));
    }

    @Test
    public void testDeleteCourse() {
        assertFalse(courseManagement.deleteCourse(cpsc406));
        assertTrue(courseManagement.deleteCourse(cpsc313));
        assertTrue(courseManagement.deleteCourse(cpsc213));
    }
}
