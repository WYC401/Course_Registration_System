package persistence;

import model.CourseOfferedBySemester;
import model.RegistrationSystem;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class JsonReader {
    private String sc;

    public JsonReader(String sc) {
        this.sc = sc;
    }

    public RegistrationSystem read() throws IOException {
        String jsonText = readFile(sc);
        JSONObject jsonObject = new JSONObject(jsonText);
        return parseRegistrationSystem(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private RegistrationSystem parseRegistrationSystem(JSONObject jsonObject) {
        JSONObject jsonCourseMapThisSemester = jsonObject.getJSONObject("courseMapThisSemester");
        JSONObject jsonStudentMapByUsername = jsonObject.getJSONObject("studentMapByUsername");
        JSONObject jsonCourseManagementSystem = jsonObject.getJSONObject("courseManagementSystem");
        return new RegistrationSystem(parseCourseMap(jsonCourseMapThisSemester), parseStudentMapByUsername(jsonStudentMapByUsername),
                parseCourseManagementSystem(jsonCourseManagementSystem));
    }

    private HashMap<Integer, CourseOfferedBySemester> parseCourseMap(JSONObject jsonCourseMapThisSemester) {

    }
}
