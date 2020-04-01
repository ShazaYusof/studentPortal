package com.example.guc_registration_system.Model;

public class LectureModel {

   public String lectID,lectName,lectProgramme,LectPassport,lectEmail,lectPhone,lectAddress,databaseId;

    public LectureModel() {

    }

    public LectureModel(String lectID, String lectName, String lectProgramme, String lectPassport, String lectEmail, String lectPhone, String lectAddress, String databaseId) {
        this.lectID = lectID;
        this.lectName = lectName;
        this.lectProgramme = lectProgramme;
        LectPassport = lectPassport;
        this.lectEmail = lectEmail;
        this.lectPhone = lectPhone;
        this.lectAddress = lectAddress;
        this.databaseId = databaseId;
    }

    public String getLectID() {
        return lectID;
    }

    public void setLectID(String lectID) {

        this.lectID = lectID;
    }

    public String getLectName() {

        return lectName;
    }

    public void setLectName(String lectName) {

        this.lectName = lectName;
    }

    public String getLectEmail() {

        return lectEmail;
    }

    public void setLectEmail(String lectEmail) {

        this.lectEmail = lectEmail;
    }

    public String getLectPhone() {

        return lectPhone;
    }

    public void setLectPhone(String lectPhone) {

        this.lectPhone = lectPhone;
    }

    public String getLectAddress() {

        return lectAddress;
    }

    public void setLectAddress(String lectAddress) {

        this.lectAddress = lectAddress;
    }

    public String getLectProgramme() {
        return lectProgramme;
    }

    public void setLectProgramme(String lectProgramme) {
        this.lectProgramme = lectProgramme;
    }

    public String getLectPassport() {
        return LectPassport;
    }

    public void setLectPassport(String lectPassport) {
        LectPassport = lectPassport;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }
}
