package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseManagementTest {
    private CourseManagement CM;
    static Course cpsc213 = new Course("ComputerSystem",213,"This is a syllabus","Meghan");
    static Course cpsc210 = new Course("ComputerSystem",210,"This is a syllabus","Meghan");
    static Course cpsc313 = new Course("ComputerSystem",313,"This is a syllabus","Meghan");
    static Course cpsc110 = new Course("ComputerSystem",110,"This is a syllabus","Meghan");
    static Course cpsc221 = new Course("ComputerSystem",221,"This is a syllabus","Meghan");

    @BeforeEach
    public void setup() {
        CM = new CourseManagement();
        CM.addCourses(cpsc213);
        CM.addCourses(cpsc210);
        CM.addCourses(cpsc313);
        CM.addCourses(cpsc110);
        CM.addCourses(cpsc221);
        CM.setPrerequisites(cpsc210, cpsc110);
        CM.setPrerequisites(cpsc213, cpsc210);
        CM.setPrerequisites(cpsc313, cpsc213);
        CM.setPrerequisites(cpsc313, cpsc221);
        CM.displayCurrentCourseGraph();
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
    }

    @Test
    public void TestSetPrerequisites() {
        Set<Integer> tempIDset = new HashSet<>();
        tempIDset.add(213);
        tempIDset.add(221);
        assertEquals(tempIDset,CM.returnPrerequisitesID(cpsc313.getCourseID()));
    }
}
