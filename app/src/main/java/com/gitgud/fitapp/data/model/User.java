package com.gitgud.fitapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gitgud.fitapp.entities.user.LoginUserQuery;

import java.util.ArrayList;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String lastName;
    private String email;
    private String birthdate;
    private String db_id;



    public User(String name) {
        this.name = name;
    }

    public User(LoginUserQuery.LoginUser user) {
        this.name = user.name();
        this.lastName = user.lastName();
        this.email = user.email();
        this.birthdate = user.birthDate();
        this.db_id = user.id();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    public String getDb_id() {
        return db_id;
    }
}
