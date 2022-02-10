package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseOfferedBySemesterTest {
    private CourseOfferedBySemester course;
    private Student yicheng = new Student("Yicheng Wang", 1, "Statistics");
    private Student chenyang = new Student("Chenyang Li", 2, "History");
    private Student richard = new Student("Richard Yang", 3, "Computer Science");
    private Student jintong = new Student("Jingtong Yan", 4, "Statistics");

    @BeforeEach
    public void setup() {
        course = new CourseOfferedBySemester("Computer System", 213, "", "TBD",
                "2021W2", 2, -1);
    }

    @Test
    public void constructorTest() {
        assertEquals("2021W2", course.getSemester());
        assertEquals(2, course.getSeatsTotal());
        assertEquals(2, course.getSeatsRemaining());
        assertEquals(-1, course.getGrade());

    }

    @Test
    public void setSeatsTotalTest() {
        course.setSeatsRemaining(0);
        assertFalse(course.setSeatsTotal(1));
        assertFalse(course.setSeatsTotal(0));
        course.setSeatsRemaining(0);
        assertTrue(course.setSeatsTotal(10));
        assertEquals(8, course.getSeatsRemaining());
        assertEquals(10, course.getSeatsTotal());
        course.setSeatsTotal(2);
        course.setSeatsRemaining(0);
        assertTrue(course.setSeatsTotal(2));
        assertEquals(0, course.getSeatsRemaining());
        assertEquals(2, course.getSeatsTotal());
    }

    @Test
    public void addOneStudentTest() {
        assertFalse(course.isFull());
        course.addOneStudent(yicheng);
        assertEquals(1, course.getStudentRegistered().get(0).getId());
        assertFalse(course.isFull());
        assertEquals(1, course.getSeatsRemaining());
        course.addOneStudent(chenyang);
        assertTrue(course.isFull());
        assertEquals(2, course.getStudentRegistered().get(1).getId());
        assertEquals(0, course.getSeatsRemaining());
        course.addOneStudent(richard);
        assertEquals(2, course.getStudentRegistered().size());
    }

    @Test
    public void removeStudentTest() {
        course.addOneStudent(yicheng);
        course.addOneStudent(chenyang);
        course.removeStudent(richard);
        assertEquals(2, course.getStudentRegistered().get(1).getId());
        assertEquals(1, course.getStudentRegistered().get(0).getId());
        course.removeStudent(yicheng);
        assertEquals(1, course.getStudentRegistered().size());
        assertEquals(2, course.getStudentRegistered().get(0).getId());
        course.removeStudent(chenyang);
        assertEquals(0, course.getStudentRegistered().size());
    }

    @Test
    public void setGradeTest() {
        course.setGrade(100);
        assertEquals(100, course.getGrade());
    }
}
