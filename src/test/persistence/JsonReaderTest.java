package persistence;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import model.RegistrationSystem;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
}
