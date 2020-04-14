package com.example.blood;

public class user_information {
    private String latti,longi;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    public String getLatti() {
        return latti;
    }

    public void setLatti(String latti) {
        this.latti = latti;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public user_information() {
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public user_information(String imagepath,String name, String url, String age, String blood, String phone, String email, String userid, String gender, String adress, String latti, String longi) {
        this.imagepath=imagepath;
        this.name = name;
        this.url = url;
        this.age = age;
        this.blood = blood;
        this.phone = phone;
        this.email = email;
        this.userid = userid;
        this.gender = gender;
        this.adress = adress;
        this.latti=latti;
        this.longi=longi;

    }

    private String name,url,age,blood,phone,email,userid,gender,adress,imagepath;

}
