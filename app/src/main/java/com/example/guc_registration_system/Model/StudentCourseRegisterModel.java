package com.example.guc_registration_system.Model;

public class StudentCourseRegisterModel {

    public String courseName,courseID,courseYear,courseProgramme;

    public StudentCourseRegisterModel(){

    }

    public StudentCourseRegisterModel(String courseName, String courseID, String courseYear, String courseProgramme) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.courseYear = courseYear;
        this.courseProgramme = courseProgramme;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseProgramme() {
        return courseProgramme;
    }

    public void setCourseProgramme(String courseProgramme) {
        this.courseProgramme = courseProgramme;
    }
}
