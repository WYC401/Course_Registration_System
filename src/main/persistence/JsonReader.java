package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

// The class can read registration system from a json file.

public class JsonReader {
    private String sc;

    // EFFECTS: construct reader to read the json file
    public JsonReader(String sc) {
        this.sc = sc;
    }

    // EFFECTS: read source file. If succeeding in reading, return registration System. If not, throw IOException.
    public RegistrationSystem read() throws IOException {
        String jsonText = readFile(sc);
        JSONObject jsonObject = new JSONObject(jsonText);
        return parseRegistrationSystem(jsonObject);
    }

    // EFFECTS: read source file. If success, return a string. If not, throw IOException.
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECT: parse the given jsonObject and turn it into a Registration System
    private RegistrationSystem parseRegistrationSystem(JSONObject jsonObject) {
        JSONObject jsonCourseMapThisSemester = jsonObject.getJSONObject("courseMapThisSemester");
        JSONObject jsonStudentMapByUsername = jsonObject.getJSONObject("studentMapByUsername");
        JSONObject jsonCourseManagementSystem = jsonObject.getJSONObject("courseManagementSystem");
        return new RegistrationSystem(parseCourseMap(jsonCourseMapThisSemester),
                parseStudentMapByUsername(jsonStudentMapByUsername),
                parseCourseManagementSystem(jsonCourseManagementSystem));
    }

    // EFFECT: parse given json Object and return a student map
    private HashMap<List<String>, Student> parseStudentMapByUsername(JSONObject jsonStudentMapByUsername) {
        HashMap<List<String>, Student> sm = new HashMap<>();
        for (String k : jsonStudentMapByUsername.keySet()) {
            JSONObject jsonObject = jsonStudentMapByUsername.getJSONObject(k);
            String[] usernameAndPassword = k.split(" ");
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(usernameAndPassword[0]);
            temp.add(usernameAndPassword[1]);
            sm.put(temp, parseStudent(jsonObject));
        }
        return sm;
    }

    // EFFECTS: parse given json object to student
    private Student parseStudent(JSONObject jsonObject) {
        String major = jsonObject.getString("major");
        String name = jsonObject.getString("name");
        Integer id = jsonObject.getInt("id");
        Student student = new Student(name, id, major);
        String tempCourseMapAlreadyTaken = jsonObject.getString("courseMapAlreadyTaken");
        String[] temp1 = tempCourseMapAlreadyTaken.replace("[", "").replace("]", "").split(", ");
        for (String i : temp1) {
            if (i.length() == 0) {
                break;
            }
            student.addTakenCourse(Integer.valueOf(i));
        }
        String tempCourseMapToTake = jsonObject.getString("courseMapToTake");
        String[] temp2 = tempCourseMapToTake.replace("[", "").replace("]", "").split(", ");
        for (String i : temp2) {
            if (i.length() == 0) {
                break;
            }
            student.registerCourse(Integer.valueOf(i));
        }
        return student;
    }

    // EFFECT: parse the given json object to course map
    private HashMap<Integer, CourseOfferedBySemester> parseCourseMap(JSONObject jsonCourseMapThisSemester) {
        HashMap<Integer, CourseOfferedBySemester> hm = new HashMap<>();
        for (String k : jsonCourseMapThisSemester.keySet()) {
            JSONObject jsonObject = jsonCourseMapThisSemester.getJSONObject(k);
            hm.put(Integer.valueOf(k), parseCourseThisSemester(jsonObject));
        }
        return hm;
    }

    //EFFECT: parse the given json object to course
    private CourseOfferedBySemester parseCourseThisSemester(JSONObject jsonObject) {
        String syllabus = jsonObject.getString("syllabus");
        String courseName = jsonObject.getString("courseName");
        String instructor = jsonObject.getString("instructor");
        String semester = jsonObject.getString("semester");
        Integer seatsRemaining = jsonObject.getInt("seatsRemaining");
        Integer seatsTotal = jsonObject.getInt("seatsTotal");
        Integer courseID = jsonObject.getInt("courseID");
        Integer grade = jsonObject.getInt("grade");
        CourseOfferedBySemester course = new CourseOfferedBySemester(courseName, courseID,
                syllabus, instructor, semester, seatsTotal, grade);

        JSONArray jsonArray = jsonObject.getJSONArray("studentsRegistered");
        for (int i = 0; i < jsonArray.length(); i++) {
            course.setSeatsRemaining(seatsTotal);
            course.addOneStudent(parseStudent(jsonArray.getJSONObject(i)));
        }
        course.setSeatsRemaining(seatsRemaining);
        return course;
    }

    // EFFECTS: parse the given json object to course management system
    private CourseManagement parseCourseManagementSystem(JSONObject jsonObject) {
        JSONObject jsonCourseGraph = jsonObject.getJSONObject("courseGraph");
        JSONObject jsonCourseMap = jsonObject.getJSONObject("courseMap");
        CourseManagement courseManagement = new CourseManagement();
        parseRawCourseMap(courseManagement, jsonCourseMap);
        parseCourseGraph(courseManagement, jsonCourseGraph);
        return courseManagement;

    }

    // EFFECT: parse the json object according the courseMangement Object input and
    // make change to courseMangement object
    private void parseCourseGraph(CourseManagement courseManagement, JSONObject jsonCourseMap) {
        JSONArray nodesArray = jsonCourseMap.getJSONArray("nodes");
        for (int i = 0; i < nodesArray.length(); i++) {
            courseManagement.addCourses(courseManagement
                    .getCourse(Integer.parseInt(nodesArray.getJSONObject(i).getString("id"))));
        }
        JSONArray edgeArray = jsonCourseMap.getJSONArray("edges");
        for (int i = 0; i < edgeArray.length(); i++) {
            int source = Integer.parseInt(edgeArray.getJSONObject(i).getString("source"));
            int target = Integer.parseInt(edgeArray.getJSONObject(i).getString("target"));
            courseManagement.setPrerequisites(courseManagement.getCourse(source), courseManagement.getCourse(target));
        }
    }

    // EFFECT: parse the jsonObject according to courseManagement Object input and construct course map inside the
    // course management system
    private void parseRawCourseMap(CourseManagement courseManagement, JSONObject jsonCourseMap) {
        for (String k : jsonCourseMap.keySet()) {
            courseManagement.addCourses(parseCourse(jsonCourseMap.getJSONObject(k)));
        }
    }

    //EFFECT: parse json object into a course
    private Course parseCourse(JSONObject jsonObject) {
        String syllabus = jsonObject.getString("syllabus");
        String courseName = jsonObject.getString("courseName");
        String instructor = jsonObject.getString("instructor");
        int courseID = jsonObject.getInt("courseID");
        return new Course(courseName, courseID, syllabus, instructor);
    }
}
