package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    // delete or rename this class!
    private Student studentTest;
    private static CourseOfferedBySemester cpsc213ThisSemester = new CourseOfferedBySemester("ComputerSystem", 213,
            "This is a syllabus", "Meghan", "2021W1", 2, -1);
    private static CourseOfferedBySemester cpsc313ThisSemester = new CourseOfferedBySemester("ComputerSystem", 313,
            "This is a syllabus", "Meghan", "2021W1", 1, -1);
    private static CourseOfferedBySemester cpsc110ThisSemester = new CourseOfferedBySemester("ComputerSystem", 110,
            "This is a syllabus", "Meghan", "2021W1", 1, -1);

    @BeforeEach
    public void setup() {
        studentTest = new Student("Test student", 1, "Mathematics");
    }

    @Test
    public void constructorTest() {
        assertEquals("Test student", studentTest.getName());
        assertEquals(1, studentTest.getId());
        assertEquals("Mathematics", studentTest.getMajor());
    }

    @Test
    public void addTakenCourseTest() {
        assertFalse(studentTest.isAlreadyTaken(cpsc110ThisSemester));
        assertTrue(studentTest.getTakenCourses().isEmpty());
        assertFalse(studentTest.isAlreadyRegistered(cpsc110ThisSemester));
        assertTrue(studentTest.addTakenCourse(cpsc110ThisSemester));
        assertTrue(studentTest.getTakenCourses().contains(cpsc110ThisSemester));
        assertTrue(studentTest.isAlreadyTaken(cpsc110ThisSemester));
        assertFalse(studentTest.addTakenCourse(cpsc110ThisSemester));
        Set<Integer> temp = new HashSet<>();
        temp.add(110);
        assertEquals(temp, studentTest.getAlreadyTakenCourseID());


    }

    @Test
    public void registerCourseTest() {
        assertFalse(studentTest.isAlreadyTaken(cpsc110ThisSemester));
        assertTrue(studentTest.addTakenCourse(cpsc110ThisSemester));
        assertFalse(studentTest.canBeRegistered(cpsc110ThisSemester));
        assertTrue(studentTest.canBeRegistered(cpsc213ThisSemester));
        studentTest.registerCourse(cpsc213ThisSemester);
        assertTrue(studentTest.isAlreadyRegistered(cpsc213ThisSemester));
        assertFalse(studentTest.canBeRegistered(cpsc213ThisSemester));
        Set<Integer> temp = new HashSet<>();
        temp.add(213);
        assertEquals(temp, studentTest.getAlreadyRegisteredID());
    }

    @Test
    public void dropCourseTest() {
        assertFalse(studentTest.dropCourse(cpsc313ThisSemester));
        studentTest.registerCourse(cpsc213ThisSemester);
        assertTrue(studentTest.dropCourse(cpsc213ThisSemester));
        assertFalse(studentTest.isAlreadyRegistered(cpsc313ThisSemester));
    }

}