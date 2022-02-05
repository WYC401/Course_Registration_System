package model;

import org.junit.jupiter.api.BeforeEach;

public class RegistrationSystemTest {
    private RegistrationSystem registrationSystem;

    @BeforeEach
    public void  setup() {
        registrationSystem = new RegistrationSystem();
        CourseOfferedBySemester cpsc213 = new CourseOfferedBySemester();
        registrationSystem.addCourseAvailable();

    }
}
