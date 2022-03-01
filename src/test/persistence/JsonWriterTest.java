package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonWriterTest  extends JsonTest{
    @Test
    public void testWriteInvalidFile() {
        JsonWriter writer = new JsonWriter("./data/illegal\0^&&&&fileName.json");
        try{
            writer.open();
            fail("File is not there or the name of file is invalid");//TODO:how to distinguish between the two case
        }catch(FileNotFoundException e) {

        }
    }

    @Test
    public void testWriterEmptyRegistrationSystem() {
        RegistrationSystem rs = new RegistrationSystem();
        JsonWriter writer = new JsonWriter("./data/testWriterEmptyRegistrationSystem.json");
        try{
            writer.open();
            writer.write(rs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRegistrationSystem.json");
            RegistrationSystem rs2 = reader.read();
            assertEquals(0, rs2.numberOfCourseInDatabase());
            assertEquals(0, rs2.numberOfStudent());
            assertEquals(0, rs2.numberOfCourseThisSemester());
        } catch(IOException e) {
            fail("should not catch!");
        }
    }

    @Test
    public void TestWriterNormalRegistrationSystem() {
        JsonWriter writer = new JsonWriter("./data/testWriterNormalRegistrationSystem.json");
        try {
            RegistrationSystem rs = new RegistrationSystem();
            initi(rs);
            writer.open();
            writer.write(rs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalRegistrationSystem.json");
            RegistrationSystem rs2 = reader.read();
            checkStudentsInSystem(rs2);
            checkCourseInSystem(rs2);
            checkCourseManagement(rs2);
        } catch(IOException e) {
            fail("should not catch!");
        }
    }

    private void checkStudentsInSystem(RegistrationSystem rs) {
        Set<Integer> toTake = new HashSet<>();
        Set<Integer> alreadyTaken = new HashSet<>();
        checkStudent(rs.searchStudent(Arrays.asList("chenyang2018", "lcylcy")), "Chenyang Li","History", 2, toTake, alreadyTaken);
        alreadyTaken.add(210);
        toTake.add(213);
        checkStudent(rs.searchStudent(Arrays.asList("yicheng2021", "wycwyc")), "Yicheng Wang","Statistics", 1, toTake, alreadyTaken);
        alreadyTaken.clear();
        alreadyTaken.add(213);
        alreadyTaken.add(221);
        alreadyTaken.add(110);
        toTake.clear();
        checkStudent(rs.searchStudent(Arrays.asList("richard2019","yzhyzh")), "Richard Yang", "Computer Science", 3, toTake, alreadyTaken);
        assertEquals(3, rs.numberOfStudent());
    }

    private void checkCourseInSystem(RegistrationSystem rs) {
        assertEquals(4, rs.numberOfCourseThisSemester());
        List<Student> ls = new ArrayList<Student>();
        checkCourseBySemester(rs.getCourseFromID(110), "ComputerSystem", 110, "This is a syllabus", "Meghan", 2, "2021W1", 2, -1, ls);
        checkCourseBySemester(rs.getCourseFromID(210), "ComputerSystem", 210, "This is a syllabus", "Meghan", 2, "2021W1", 2, -1, ls);
        checkCourseBySemester(rs.getCourseFromID(313), "ComputerSystem", 313, "This is a syllabus", "Meghan", 2, "2021W1", 2, -1, ls);
        ls.add(rs.searchStudent(Arrays.asList("yicheng2021", "wycwyc")));
        checkCourseBySemester(rs.getCourseFromID(213), "ComputerSystem", 213, "This is a syllabus", "Meghan", 1, "2021W1", 2, -1, ls);
    }

    public void checkCourseManagement(RegistrationSystem rs) {
        assertEquals(5, rs.numberOfCourseInDatabase());
        Set<Integer> s = new HashSet<>();
        s.add(110);
        s.add(210);
        s.add(221);
        s.add(213);
        s.add(313);
        checkTwoSetEqual(s, rs.getCourseSetInDatabase());
        Set<List<Integer>> relation = new HashSet<>();
        List<Integer> temp = new ArrayList<>();
        temp.add(210);
        temp.add(110);
        relation.add(temp);
        temp = new ArrayList<>();
        temp.add(213);
        temp.add(210);
        relation.add(temp);
        temp = new ArrayList<>();
        temp.add(313);
        temp.add(213);
        relation.add(temp);
        temp = new ArrayList<>();
        temp.add(313);
        temp.add(221);
        relation.add(temp);
        Set<List<Integer>> tempForCheck = rs.getAllPrerequisitesRelation();
        assertTrue(relation.containsAll(rs.getAllPrerequisitesRelation()));
        assertTrue(rs.getAllPrerequisitesRelation().containsAll(relation));
    }

    private void initi(RegistrationSystem registrationSystemCore) {
        CourseManagement courseManagement = new CourseManagement();
        Course cpsc213 = new Course("Computer System", 213, "This is a syllabus", "Meghan");
        Course cpsc210 = new Course("Software Construction", 210, "This is a syllabus", "Meghan");
        Course cpsc313 = new Course("Operating System", 313, "This is a syllabus", "Meghan");
        Course cpsc110 = new Course("Programming", 110, "This is a syllabus", "Meghan");
        Course cpsc221 = new Course("Basic Data Structure And Algorithm", 221, "This is a syllabus", "Meghan");
        Course cpsc310 = new Course("Introduction to Software Engineer", 310, "This is a syllabus", "Meghan");
        courseManagement.addCourses(cpsc213);
        courseManagement.addCourses(cpsc210);
        courseManagement.addCourses(cpsc313);
        courseManagement.addCourses(cpsc110);
        courseManagement.addCourses(cpsc221);
        courseManagement.setPrerequisites(cpsc210, cpsc110);
        courseManagement.setPrerequisites(cpsc213, cpsc210);
        courseManagement.setPrerequisites(cpsc313, cpsc213);
        courseManagement.setPrerequisites(cpsc313, cpsc221);
        courseManagement.setPrerequisites(cpsc310, cpsc221);
        courseManagement.setPrerequisites(cpsc310, cpsc213);
        registrationSystemCore.loadCourseManagement(courseManagement);
        addStudent(registrationSystemCore);
        addCourseThisSemester(registrationSystemCore);

    }

    //MODIFIERS: this
    //EFFECT: add students into registration system
    private void addStudent(RegistrationSystem registrationSystemCore) {
        Student yicheng = new Student("Yicheng Wang", 1, "Statistics");
        Student chenyang = new Student("Chenyang Li", 2, "History");
        Student richard = new Student("Richard Yang", 3, "Computer Science");
        registrationSystemCore.addStudent(Arrays.asList("yicheng2021", "wycwyc"), yicheng);
        registrationSystemCore.addStudent(Arrays.asList("chenyang2018", "lcylcy"), chenyang);
        registrationSystemCore.addStudent(Arrays.asList("richard2019", "yzhyzh"), richard);
        CourseOfferedBySemester cpsc213PreviousSemester = new CourseOfferedBySemester("Computer System", 213,
                "This is a syllabus", "Meghan", "2021W1", 2, 60);
        CourseOfferedBySemester cpsc210PreviousSemester = new CourseOfferedBySemester("Software Construction", 210,
                "This is a syllabus", "Meghan", "2021W1", 2, 70);
        CourseOfferedBySemester cpsc110PreviousSemester = new CourseOfferedBySemester("Programming", 110,
                "This is a syllabus", "Meghan", "2021W1", 2, 60);
        CourseOfferedBySemester cpsc221PreviousSemester = new CourseOfferedBySemester(
                "Basic Data Structure And Algorithm", 221,
                "This is a syllabus", "Meghan", "2021W1", 2, 60);
        yicheng.addTakenCourse(cpsc210PreviousSemester.getCourseID());
        richard.addTakenCourse(cpsc110PreviousSemester.getCourseID());
        richard.addTakenCourse(cpsc213PreviousSemester.getCourseID());
        richard.addTakenCourse(cpsc221PreviousSemester.getCourseID());
    }

    //MODIFIERS: this
    //EFFECT: add courses into the system
    private void addCourseThisSemester(RegistrationSystem registrationSystemCore) {
        CourseOfferedBySemester cpsc213ThisSemester = new CourseOfferedBySemester("ComputerSystem", 213,
                "This is a syllabus", "Meghan", "2021W1", 2, -1);
        CourseOfferedBySemester cpsc313ThisSemester = new CourseOfferedBySemester("ComputerSystem", 313,
                "This is a syllabus", "Meghan", "2021W1", 2, -1);
        CourseOfferedBySemester cpsc110ThisSemester = new CourseOfferedBySemester("ComputerSystem", 110,
                "This is a syllabus", "Meghan", "2021W1", 2, -1);
        CourseOfferedBySemester cpsc210ThisSemester = new CourseOfferedBySemester("ComputerSystem", 210,
                "This is a syllabus", "Meghan", "2021W1", 2, -1);
        registrationSystemCore.addCourseAvailable(cpsc213ThisSemester);
        registrationSystemCore.addCourseAvailable(cpsc313ThisSemester);
        registrationSystemCore.addCourseAvailable(cpsc110ThisSemester);
        registrationSystemCore.addCourseAvailable(cpsc210ThisSemester);
        registrationSystemCore.register(cpsc213ThisSemester, registrationSystemCore.searchStudent((Arrays.asList("yicheng2021", "wycwyc"))));

    }
}
