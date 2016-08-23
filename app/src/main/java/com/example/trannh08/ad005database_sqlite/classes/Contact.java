package com.example.trannh08.ad005database_sqlite.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by trannh08 on 8/23/2016.
 */
public class Contact implements Serializable {
    public int Id;
    public String Name;
    public String Phone;
    public String Street;
    public String Email;
    public String City;

    public Contact() {
    }

    public Contact(int id, String name, String phone, String street, String email, String city){
        this.Id = id;
        this.Name = name;
        this.Phone = phone;
        this.Street = street;
        this.Email = email;
        this.City = city;
    }
}
