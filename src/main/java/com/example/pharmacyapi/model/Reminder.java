package com.example.pharmacyapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Optional;


@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String instructions;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // This links the table representing the Reminder model to the table representing the Medication model
    @JsonIgnore // This prevents a stack overflow/API crashing from medications and reminders calling each other back and forth
    @ManyToOne
    @JoinColumn(name = "medication_id") // This represents the foreign key in SQL joining the columns to connect the 2 tables
    private Medication medication;


    public Reminder() {
    }

    public Reminder(Long id, String name, String instructions, Medication medication) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.medication = medication;
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }


    @java.lang.Override
    public java.lang.String toString() {
        return "Reminder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", medication=" + medication +
                '}';
    }
}
