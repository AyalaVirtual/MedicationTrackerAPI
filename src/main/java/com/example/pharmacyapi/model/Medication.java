package com.example.pharmacyapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.*;
import java.util.List;


@Entity // This is a marker that defines that a class can be mapped to a table
@Table(name = "medications") // This makes it a table
public class Medication {

    @Id // Primary key
    @Column // This marks it as a column in the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This means to generate the value for the ID as the next available integer
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String dosage;

    @Column
    private boolean isCurrent;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    // This links the table representing the Medication model to the table representing the Reminder model
    @OneToMany(mappedBy = "medication", orphanRemoval = true) // This means it's a one-to-many relationship that is mappedBy the variable representing the link to the other table. orphanRemoval = true means that if we delete the medication, delete the reminder as well
    @LazyCollection(LazyCollectionOption.FALSE) // This means when you fetch an instance of a medication, fetch the associated reminders
    private List<Reminder> reminderList;


    public Medication() {
    }

    public Medication(Long id, String name, String description, String dosage, boolean isCurrent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dosage = dosage;
        this.isCurrent = isCurrent;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    /**
     * This method adds reminders to the medication's reminder list
     *
     * @param reminder represents the reminder the user is trying to add to the medication's reminder list
     */
    public void addToReminderList(Reminder reminder) {
        reminderList.add(reminder);
    }

    public List<Reminder> getReminderList() {
        return reminderList;
    }

    public void setReminderList(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }


    @java.lang.Override
    public java.lang.String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dosage='" + dosage + '\'' +
                ", isCurrent=" + isCurrent +
                '}';
    }
}
