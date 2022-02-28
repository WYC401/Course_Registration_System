package persistence;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{
    @Test
    public void testNoSuchFile() {
        try{
            JsonReader reader = new JsonReader("./data/NosuchFile.json");
            RegistrationSystem rs = reader.read();
            fail("IOException should have been thrown!");
        } catch (IOException e) {

        }
    }

    @Test
    public void testEmptyRegistrationSystem() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRegistrationSystem.json");
        try{
            RegistrationSystem rs = reader.read();
            assertEquals(0, rs.numberOfCourseInDatabase());
            assertEquals(0, rs.numberOfStudent());
            assertEquals(0, rs.numberOfCourseThisSemester());
        } catch(IOException e) {
            fail("Can not read from file");
        }
    }

    @Test
    public void testNormalRegistrationSystem() {
        JsonReader reader = new JsonReader("./data/testReaderNormalRegistrationSystem.json");
        try{
            RegistrationSystem rs = reader.read();
            checkStudentsInSystem(rs);
            checkCourseInSystem(rs);
            checkCourseManagement(rs);
        } catch(IOException e) {
            fail("Should not catch it");
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
}
