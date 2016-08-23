package com.example.trannh08.ad005database_sqlite.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by trannh08 on 8/23/2016.
 */
public class Contact implements Serializable {
    private int Id;
    private String Name;
    private String Phone;
    private String Street;
    private String Email;
    private String City;

    public Contact() {
    }

    public Contact(int id, String name, String phone, String street, String email, String city) {
        Id = id;
        Name = name;
        Phone = phone;
        Street = street;
        Email = email;
        City = city;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
