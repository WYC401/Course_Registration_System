package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseTest {
    private Course course;

    @BeforeEach
    public void setup() {
        course = new Course("ddd", 0, "",
                "");
    }

    @Test
    public void testSetter() {
        course.setCourseID(210);
        course.setCourseName("Software Construction");
        course.setInstructor("Meghan");
        course.setSyllabus("Here is a syllabus");
        assertEquals("Software Construction", course.getCourseName());
        assertEquals(210, course.getCourseID());
        assertEquals("Here is a syllabus", course.getSyllabus());
        assertEquals("Meghan", course.getInstructor());
    }

}
