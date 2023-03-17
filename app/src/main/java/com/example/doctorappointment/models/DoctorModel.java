package com.example.doctorappointment.models;

import java.io.Serializable;

public class DoctorModel implements Serializable {
    private String id, name, speciality, hospital, phone, email, about, Image;

    public DoctorModel() {

    }

    public DoctorModel(String id, String name, String speciality, String hospital, String phone, String email, String about, String image) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.hospital = hospital;
        this.phone = phone;
        this.email = email;
        this.about = about;
        Image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
