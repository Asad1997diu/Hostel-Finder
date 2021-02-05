package com.example.hostelfinder.Model;

public class Post {
    private String postid;
    private String postimage;
    private String publisher;
    private String name;
    private String address;
    private String number;
    private String gender;
    private String seatno;
    private String price;
    private String description;

    public Post() {
    }

    public Post(String postid, String postimage, String publisher, String name, String address, String number, String gender, String seatno, String price, String description) {
        this.postid = postid;
        this.postimage = postimage;
        this.publisher = publisher;
        this.name = name;
        this.address = address;
        this.number = number;
        this.gender = gender;
        this.seatno = seatno;
        this.price = price;
        this.description = description;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSeatno() {
        return seatno;
    }

    public void setSeatno(String seatno) {
        this.seatno = seatno;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
