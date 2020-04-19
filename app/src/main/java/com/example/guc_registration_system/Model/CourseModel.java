package com.example.guc_registration_system.Model;

public class CourseModel {

    public String courseID,courseName,semester,faculty,programme;

    public CourseModel(){

    }

    public CourseModel(String courseID, String courseName, String semester, String faculty, String programme) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.semester = semester;
        this.faculty = faculty;
        this.programme = programme;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSemester() {
        return semester;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getProgramme() {
        return programme;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }
}
