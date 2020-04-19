package com.example.guc_registration_system.Model;

public class StudentCourseModel {

    private String studentCourseID;
    private String studentDatabase;
    private String courseID;
    private String semester;
    private  String studID;
    private  String studentName;

    public StudentCourseModel(){

    }

    public StudentCourseModel(String studentCourseID, String studentDatabase, String courseID, String semester, String studID, String studentName) {
        this.studentCourseID = studentCourseID;
        this.studentDatabase = studentDatabase;
        this.courseID = courseID;
        this.semester = semester;
        this.studID = studID;
        this.studentName = studentName;
    }

    public String getStudentCourseID() {
        return studentCourseID;
    }

    public void setStudentCourseID(String studentCourseID) {
        this.studentCourseID = studentCourseID;
    }

    public String getStudentID() {
        return studentDatabase;
    }

    public void setStudentID(String studentID) {
        this.studentDatabase = studentID;
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

    public String getStudID() {
        return studID;
    }

    public void setStudID(String studID) {
        this.studID = studID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }
}
