package com.stavaray.uploadreadcsv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastName;
    private String description;
    private boolean banned;

    public User(String name, String lastName, String description, boolean banned) {
        this.name = name;
        this.lastName = lastName;
        this.description = description;
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", banned=" + banned +
                '}';
    }
}
