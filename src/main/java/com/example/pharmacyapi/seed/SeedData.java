package com.example.pharmacyapi.seed;

import com.example.pharmacyapi.model.Medication;
import com.example.pharmacyapi.model.Reminder;
import com.example.pharmacyapi.model.User;
import com.example.pharmacyapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.pharmacyapi.repository.MedicationRepository;
import com.example.pharmacyapi.repository.ReminderRepository;


@Component
public class SeedData implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MedicationRepository medicationRepository;
    private final ReminderRepository reminderRepository;


    @Autowired
    public SeedData(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository, MedicationRepository medicationRepository, ReminderRepository reminderRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.medicationRepository = medicationRepository;
        this.reminderRepository = reminderRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        User user = new User();
        user.setUserName("pombagira");
        user.setEmailAddress("cigana@gmail.com");
        user.setPassword(passwordEncoder.encode("quimbanda7"));
        userRepository.save(user);

        // Medication 1
        Medication medication1 = new Medication();
        medication1.setName("Oxycontin");
        medication1.setDescription("Oxycodone belongs to a class of drugs known as opioid analgesics and is used to help relieve severe ongoing pain.");
        medication1.setDosage("1 tablet every 12 hours");
        medication1.setIsCurrent(false);
        medication1.setUser(user);
        medicationRepository.save(medication1);

        Reminder reminder1A = new Reminder();
        reminder1A.setName("Morning");
        reminder1A.setInstructions("Take only one a tablet at a time if your dose is for more than one tablet. Avoid breaking, crushing, chewing, or dissolving tablets to avoid accidental overdose.");
        reminder1A.setMedication(medication1);
        reminder1A.setUser(user);
        reminderRepository.save(reminder1A);

        Reminder reminder1B = new Reminder();
        reminder1B.setName("Evening");
        reminder1B.setInstructions("Take only one a tablet at a time if your dose is for more than one tablet. Avoid breaking, crushing, chewing, or dissolving tablets to avoid accidental overdose.");
        reminder1B.setMedication(medication1);
        reminder1B.setUser(user);
        reminderRepository.save(reminder1B);


        // Medication 2
        Medication medication2 = new Medication();
        medication2.setName("Methadone");
        medication2.setDescription("This medication belongs to a class of drugs known as opioid analgesics. It is used to treat opioid use disorder by preventing withdrawal symptoms.");
        medication2.setDosage("1 bottle daily");
        medication2.setIsCurrent(true);
        medication2.setUser(user);
        medicationRepository.save(medication2);

        Reminder reminder2A = new Reminder();
        reminder2A.setName("Morning");
        reminder2A.setInstructions("Take by mouth with or without food on a regular schedule as directed by your doctor.");
        reminder2A.setMedication(medication2);
        reminder2A.setUser(user);
        reminderRepository.save(reminder2A);


        // Medication 3
        Medication medication3 = new Medication();
        medication3.setName("Clonazepam");
        medication3.setDescription("This medication belongs to a class of drugs called benzodiazepines and is used to treat panic attacks, as well as preventing and controlling seizures.");
        medication3.setDosage("2 or 3 times daily");
        medication3.setIsCurrent(false);
        medication3.setUser(user);
        medicationRepository.save(medication3);

        Reminder reminder3A = new Reminder();
        reminder3A.setName("Morning");
        reminder3A.setInstructions("Take this medication by mouth as directed by your doctor.");
        reminder3A.setMedication(medication3);
        reminder3A.setUser(user);
        reminderRepository.save(reminder3A);

        Reminder reminder3B = new Reminder();
        reminder3B.setName("Afternoon");
        reminder3B.setInstructions("Take this medication by mouth as directed by your doctor.");
        reminder3B.setMedication(medication3);
        reminder3B.setUser(user);
        reminderRepository.save(reminder3B);

        Reminder reminder3C = new Reminder();
        reminder3C.setName("Evening");
        reminder3C.setInstructions("Take this medication by mouth as directed by your doctor.");
        reminder3C.setMedication(medication3);
        reminder3C.setUser(user);
        reminderRepository.save(reminder3C);
    }

}
