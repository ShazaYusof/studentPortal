package com.example.guc_registration_system.Model;

public class AssignmentModel {

    public String fileName,url,courseID;

    public AssignmentModel(){

    }

    public AssignmentModel(String fileName, String url, String courseID) {
        this.fileName = fileName;
        this.url = url;
        this.courseID = courseID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
}
