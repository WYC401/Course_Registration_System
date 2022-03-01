package persistence;

import model.*;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkRawCourse(Course course, String courseName, Integer courseID, String syllabus,
                                  String instructor) {
        assertEquals(courseName, course.getCourseName());
        assertEquals(courseID, course.getCourseID());
        assertEquals(instructor, course.getInstructor());
        assertEquals(syllabus, course.getSyllabus());
    }

    protected void checkStudent(Student student, String name, String major, int id,
                                Set<Integer> courseSetToTake, Set<Integer> courseSetAlreadyTaken) {
        assertEquals(name, student.getName());
        assertEquals(id, student.getId());
        assertEquals(major, student.getMajor());
        checkTwoSetEqual(student.getAlreadyRegisteredID(), courseSetToTake);
        checkTwoSetEqual(student.getAlreadyTakenCourseID(), courseSetAlreadyTaken);

    }

    protected void checkCourseBySemester(CourseOfferedBySemester course, String courseName, int courseID,
                                         String syllabus, String instructor, int seatsRemaining,
                                         String semester, int seatsTotal, int grade, List<Student> studentList) {
        checkRawCourse(course, courseName, courseID, syllabus, instructor);
        assertEquals(grade, course.getGrade());
        assertEquals(seatsTotal, course.getSeatsTotal());
        assertEquals(seatsRemaining, course.getSeatsRemaining());
        assertEquals(semester, course.getSemester());
        assertEquals(studentList.size(), course.getSeatsTotal() - course.getSeatsRemaining());
    }

    protected void checkTwoSetEqual(Set<Integer> set1, Set<Integer> set2) {
        for (Integer i : set1) {
            assertTrue(set2.contains(i));
        }
        for (Integer i : set2) {
            assertTrue(set1.contains(i));
        }
    }
}
