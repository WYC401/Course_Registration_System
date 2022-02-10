package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CourseManagementTest {
    private CourseManagement CM;
    static Course cpsc213 = new Course("ComputerSystem", 213, "This is a syllabus", "Meghan");
    static Course cpsc210 = new Course("ComputerSystem", 210, "This is a syllabus", "Meghan");
    static Course cpsc313 = new Course("ComputerSystem", 313, "This is a syllabus", "Meghan");
    static Course cpsc110 = new Course("ComputerSystem", 110, "This is a syllabus", "Meghan");
    static Course cpsc221 = new Course("ComputerSystem", 221, "This is a syllabus", "Meghan");
    static Course cpsc406 = new Course("ComputerSystem", 406, "This is a syllabus", "Meghan");

    @BeforeEach
    public void setup() {
        CM = new CourseManagement();
        CM.addCourses(cpsc213);
        CM.addCourses(cpsc210);
        CM.addCourses(cpsc313);
        CM.addCourses(cpsc110);
        CM.addCourses(cpsc221);
        assertTrue(CM.setPrerequisites(cpsc210, cpsc110));
        assertTrue(CM.setPrerequisites(cpsc213, cpsc210));
        assertTrue(CM.setPrerequisites(cpsc313, cpsc213));
        assertTrue(CM.setPrerequisites(cpsc313, cpsc221));
        assertFalse(CM.setPrerequisites(cpsc406, cpsc313));


    }

    @Test
    public void TestAddCourse() {
        Set<Integer> tempCourseSet = new HashSet<Integer>();
        tempCourseSet.add(213);
        tempCourseSet.add(210);
        tempCourseSet.add(110);
        tempCourseSet.add(313);
        tempCourseSet.add(221);
        assertEquals(tempCourseSet, CM.getAllCoursesID());
        assertEquals(cpsc213, CM.getCourse(213));
        assertNull(CM.getCourse(20));
        assertEquals(213, CM.getCourseID(cpsc213));
        assertNull(CM.getCourseID(cpsc406));
    }

    @Test
    public void TestSetPrerequisites() {
        Set<Integer> tempIDset = new HashSet<>();
        tempIDset.add(213);
        tempIDset.add(221);
        assertEquals(tempIDset, CM.returnPrerequisitesID(cpsc313.getCourseID()));
    }

    @Test
    public void TestReturnAllCoursesNeeded() {
        Set<Integer> tempIDSet = new HashSet<Integer>();
        tempIDSet.add(210);
        tempIDSet.add(213);
        System.out.print(CM.displayCurrentCourseGraph());
        System.out.print(CM.displayCurrentCourseGraph());
        System.out.print(CM.displayCourseGraph(CM.returnAllNeededCoursesID(tempIDSet, 313)));
        assertNull(CM.returnAllNeededCoursesID(tempIDSet, 406));
    }

    @Test
    public void TestDeleteCourse() {
        assertFalse(CM.deleteCourse(cpsc406));
        assertTrue(CM.deleteCourse(cpsc313));
        assertTrue(CM.deleteCourse(cpsc213));
    }
}
