package com.example.guc_registration_system.Model;

public class CourseModel {

    public String courseID,courseName;

    public CourseModel(){

    }

    public CourseModel(String courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;

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


}
