package com.example.guc_registration_system.Model;

public class LecturerCourseModel {

    private String courseID;
    private String semester;
    private  String courseName;

    public LecturerCourseModel(){

    }

    public LecturerCourseModel(String courseID, String semester, String courseName) {
        this.courseID = courseID;
        this.semester = semester;
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
