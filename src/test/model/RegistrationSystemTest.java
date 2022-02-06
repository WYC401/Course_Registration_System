package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationSystemTest {
    private RegistrationSystem registrationSystem;
    private CourseManagement CM;
    private static CourseOfferedBySemester cpsc213ThisSemester = new CourseOfferedBySemester("ComputerSystem",213,
            "This is a syllabus","Meghan","2021W1",2,-1);
    private static CourseOfferedBySemester cpsc313ThisSemester = new CourseOfferedBySemester("ComputerSystem",313,
            "This is a syllabus","Meghan","2021W1",2,-1);
    private static CourseOfferedBySemester cpsc110ThisSemester = new CourseOfferedBySemester("ComputerSystem",110,
            "This is a syllabus","Meghan","2021W1",2,-1);
    private Student yicheng = new Student("Yicheng Wang",1,"Statistics");
    private Student chenyang = new Student("Chenyang Li", 2, "History");
    private Student richard = new Student("Richard Yang", 3, "Computer Science");
    private Student jintong = new Student("Jingtong Yan", 4, "Statistics");


    @BeforeEach
    public void  setup() {
        registrationSystem = new RegistrationSystem();
        initialCourseGraph();
        registrationSystem.loadCourseManagement(CM);
        registrationSystem.addCourseAvailable(cpsc213ThisSemester);
        registrationSystem.addCourseAvailable(cpsc313ThisSemester);

        registrationSystem.addStudent(yicheng);
        registrationSystem.addStudent(chenyang);
        registrationSystem.addStudent(richard);
        CourseOfferedBySemester cpsc213PreviousSemester = new CourseOfferedBySemester("ComputerSystem",213,
                "This is a syllabus","Meghan","2021W1",2,60);
        CourseOfferedBySemester cpsc210PreviousSemester = new CourseOfferedBySemester("ComputerSystem",210,
                "This is a syllabus","Meghan","2021W1",2,60);
        CourseOfferedBySemester cpsc110PreviousSemester = new CourseOfferedBySemester("ComputerSystem",110,
                "This is a syllabus","Meghan","2021W1",2,60);
        CourseOfferedBySemester cpsc221PreviousSemester = new CourseOfferedBySemester("ComputerSystem",221,
                "This is a syllabus","Meghan","2021W1",2,60);
        yicheng.addTakenCourse(cpsc210PreviousSemester);
        richard.addTakenCourse(cpsc110PreviousSemester);
        richard.addTakenCourse(cpsc213PreviousSemester);
        richard.addTakenCourse(cpsc221PreviousSemester);
    }
    private void initialCourseGraph() {
        CM = new CourseManagement();
        Course cpsc213 = new Course("ComputerSystem",213,"This is a syllabus","Meghan");
        Course cpsc210 = new Course("ComputerSystem",210,"This is a syllabus","Meghan");
        Course cpsc313 = new Course("ComputerSystem",313,"This is a syllabus","Meghan");
        Course cpsc110 = new Course("ComputerSystem",110,"This is a syllabus","Meghan");
        Course cpsc221 = new Course("ComputerSystem",221,"This is a syllabus","Meghan");
        CM.addCourses(cpsc213);
        CM.addCourses(cpsc210);
        CM.addCourses(cpsc313);
        CM.addCourses(cpsc110);
        CM.addCourses(cpsc221);
        CM.setPrerequisites(cpsc210, cpsc110);
        CM.setPrerequisites(cpsc213, cpsc210);
        CM.setPrerequisites(cpsc313, cpsc213);
        CM.setPrerequisites(cpsc313, cpsc221);
        //CM.displayCurrentCourseGraph();

    }

    @Test
    public void registerTest () {
        assertFalse(registrationSystem.register(cpsc213ThisSemester, chenyang));
        assertTrue(registrationSystem.register(cpsc213ThisSemester,yicheng));
        assertFalse(registrationSystem.register(cpsc213ThisSemester,richard));
        assertTrue(registrationSystem.register(cpsc313ThisSemester,richard));
        assertFalse(registrationSystem.register(cpsc313ThisSemester,richard));
        assertTrue(registrationSystem.register(cpsc110ThisSemester,chenyang));
        assertTrue(registrationSystem.register(cpsc110ThisSemester,yicheng));
        assertFalse(registrationSystem.register(cpsc110ThisSemester,jintong));
    }
    @Test
    public void dropTest() {
        assertTrue(registrationSystem.register(cpsc313ThisSemester,richard));
        assertTrue(registrationSystem.drop(cpsc313ThisSemester,richard));
        assertFalse(registrationSystem.drop(cpsc313ThisSemester,richard));
        assertFalse(registrationSystem.drop(cpsc213ThisSemester,chenyang));
    }


}
