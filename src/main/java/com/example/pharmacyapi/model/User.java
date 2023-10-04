package com.example.pharmacyapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;

    @Column
    private String emailAddress;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // This means that users can only write to this property, not read it
    private String password;


    @OneToOne(cascade = CascadeType.ALL) // This reflects its one-to-one relationship with userProfile and means that if we delete the user, then to delete the userProfile as well
    @JoinColumn(name = "profile_id", referencedColumnName = "id") // This represents the foreign key/column that joins the User table to UserProfile table
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE) // This means when you fetch an instance of a user, fetch the associated medications
    private List<Medication> medicationList;

    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE) // This means when you fetch an instance of a user, fetch the associated reminders
    private List<Reminder> reminderList;


    public User() {
    }

    public User(Long id, String userName, String emailAddress, String password, UserProfile userProfile) {
        this.id = id;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.userProfile = userProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
