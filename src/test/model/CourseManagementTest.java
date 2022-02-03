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
    }

    @Test
    public void TestAddCourse() {
        Set<Course> tempCourseSet = new HashSet<Course>();

        tempCourseSet.add(cpsc213);
        tempCourseSet.add(cpsc210);
        tempCourseSet.add(cpsc313);
        tempCourseSet.add(cpsc110);
        tempCourseSet.add(cpsc221);
        assertEquals(tempCourseSet, CM.getAllCourses());
    }

    @Test
    public void TestSetPrerequisites() {
        Set<Course> tempCourseSet = new HashSet<Course>();
        tempCourseSet.add(cpsc221);
        tempCourseSet.add(cpsc213);
        assertEquals(tempCourseSet,CM.returnPrerequisites(cpsc313));
    }
}
