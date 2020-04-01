package com.example.guc_registration_system.Model;

public class StudentModel {

    public String studID,studName,studEmail,studPassportNo,studPhone,studAddress,faculty,programme,studSemester,databaseID;

    public StudentModel(){

    }

    public StudentModel(String studID, String studName, String studEmail, String studPassportNo, String studPhone, String studAddress, String faculty, String programme, String studSemester, String databaseID) {
        this.studID = studID;
        this.studName = studName;
        this.studEmail = studEmail;
        this.studPassportNo = studPassportNo;
        this.studPhone = studPhone;
        this.studAddress = studAddress;
        this.faculty = faculty;
        this.programme = programme;
        this.studSemester = studSemester;
        this.databaseID = databaseID;
    }

    public String getStudID() {
        return studID;
    }

    public void setStudID(String studID) {
        this.studID = studID;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public String getStudEmail() {
        return studEmail;
    }

    public void setStudEmail(String studEmail) {
        this.studEmail = studEmail;
    }

    public String getStudPassportNo() {
        return studPassportNo;
    }

    public void setStudPassportNo(String studPassportNo) {
        this.studPassportNo = studPassportNo;
    }

    public String getStudPhone() {
        return studPhone;
    }

    public void setStudPhone(String studPhone) {
        this.studPhone = studPhone;
    }

    public String getStudAddress() {
        return studAddress;
    }

    public void setStudAddress(String studAddress) {
        this.studAddress = studAddress;
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

    public String getStudSemester() {
        return studSemester;
    }

    public void setStudSemester(String studSemester) {
        this.studSemester = studSemester;
    }

    public String getDatabaseID() {
        return databaseID;
    }

    public void setDatabaseID(String databaseID) {
        this.databaseID = databaseID;
    }
}
