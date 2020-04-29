package com.example.guc_registration_system.Model;

public class CourseModel {

    public String courseID,courseName,semester,faculty,programme;
    private int creditValue;

    public CourseModel(){

    }

    public CourseModel(String courseID, String courseName, String semester, String faculty, String programme, int creditValue) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.semester = semester;
        this.faculty = faculty;
        this.programme = programme;
        this.creditValue = creditValue;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public int getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(int creditValue) {
        this.creditValue = creditValue;
    }
}
