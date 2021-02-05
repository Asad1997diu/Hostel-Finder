package com.example.hostelfinder.Model;

public class Notification {

    private String userid;
    private String name;
    private String occupation;
    private String institute;
    private String district;
    private String phone;
    private String room;
    private String bed;
    private String duration;
    private String postid;
    private boolean ispost;

    public Notification() {
    }

    public Notification(String userid, String name, String occupation, String institute, String district, String phone, String room, String bed, String duration, String postid, boolean ispost) {
        this.userid = userid;
        this.name = name;
        this.occupation = occupation;
        this.institute = institute;
        this.district = district;
        this.phone = phone;
        this.room = room;
        this.bed = bed;
        this.duration = duration;
        this.postid = postid;
        this.ispost = ispost;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public boolean isIspost() {
        return ispost;
    }

    public void setIspost(boolean ispost) {
        this.ispost = ispost;
    }
}
