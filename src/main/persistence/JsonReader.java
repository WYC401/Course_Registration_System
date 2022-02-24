package persistence;

import model.CourseOfferedBySemester;
import model.RegistrationSystem;
import model.Student;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
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
    private HashMap<List<String>, Student> parseStudentMapByUsername(JSONObject jsonStudentMapByUsername) {
        HashMap<List<String>, Student> sm = new HashMap<>();
        for(String k: jsonStudentMapByUsername.keySet()) {
            JSONObject jsonObject = jsonStudentMapByUsername.getJSONObject(k);
            String[] usernameAndPassword = k.split(" ");
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(usernameAndPassword[0]);
            temp.add(usernameAndPassword[1]);
            sm.put(temp, parseStudent(jsonObject));
        }
    }
    private Student parseStudent(JSONObject jsonObject) {
        String major = jsonObject.getString("major");
        String name = jsonObject.getString("name");
        Integer id = jsonObject.getInt("id");
        Student student = new Student(name, id, major);
        String tempCourseMapAlreadyTaken = jsonObject.getString("courseMapAlreadyTaken");
        String[] temp = tempCourseMapAlreadyTaken.replace("[", "").replace("]","").split(", ");
        for(String i : temp) {
            student.addTakenCourse(Integer.valueOf(i))
        }




    }
    private HashMap<Integer, CourseOfferedBySemester> parseCourseMap(JSONObject jsonCourseMapThisSemester) {
        HashMap<Integer,CourseOfferedBySemester> hm = new HashMap<>();
        for(String k: jsonCourseMapThisSemester.keySet()) {
            JSONObject jsonObject = jsonCourseMapThisSemester.getJSONObject(k);
            hm.put(Integer.valueOf(k), parseCourseThisSemester(jsonObject));
        }
        return hm;
    }


}
