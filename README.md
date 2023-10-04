# Patient / Medication Tracker & Reminder API 

A RestAPI designed to allow patients / pharmacy customers to track current and past medications and set reminders for them. Users are able to search for, add, edit, and delete different medications and their reminders from their account / user profile. 

## Technologies Used

* Java 17
* Maven 
* Spring Boot
* Spring Data (JPA)
* Spring Security 
* JWT Tokens 
* H2 Database
* Postman 
* Apache Tomcat 
* MockMVC 
* Cucumber with Rest Assured 
* IntelliJ 



## General Approach

I started off by creating my user stories and acceptance criteria. This helped me visualize my end goals and outline what I wanted to achieve with this project. I also created a spreadsheet of all my HTTP methods and endpoints to keep track of which methods were supposed to hit which endpoint.

Finally, I created an ERD (entity relationship diagram) to plan my different models and their relevant attributes. This helped me to visualize their relationships to one another and plan out how I was going to link the corresponding tables in the database.



## Entity Relationship Diagram 

<img src="./images/PatientMedReminderERD.png" alt="ERD">



## HTTP Endpoints 

| Request Type | URL                             | Functionality                  | Access | 
|--------------|---------------------------------|--------------------------------|--------|
| POST         | /api/medications/               | Create a medication            | Public |
| GET          | /api/medications/               | Get all medications            | Public |
| GET          | /api/medications/1/             | Get a medication               | Public |
| PUT          | /api/medications/1/             | Update a medication            | Public |
| DELETE       | /api/medications/1/             | Delete a medication            | Public |
| POST         | /api/medications/1/reminders/   | Creating a medication reminder | Public |
| GET          | /api/medications/reminders/     | Get all medication reminders   | Public |
| GET          | /api/medications/1/reminders/1/ | Get a medication reminder      | Public |
| PUT          | /api/medications/1/reminders/1/ | Update a medication reminder   | Public |
| DELETE       | /api/medications/1/reminders/1/ | Delete a medication reminder   | Public |



## User Stories 

<b>User Story 1:</b>
<br>
As a user I want to register/sign up for an account using my email address and password so that I can track my medications.

<b>Acceptance Criteria:</b>
<br>
* User should be able to create a new account using their email address and create a unique password.


<b>User Story 2:</b>
<br>
As a user I want to log in to my account with my email and password to access my medication tracking features.

<b>Acceptance Criteria:</b>
<br>
* User should be able to login to their account using their email and password.
* The application should validate the user's credentials against the stored user data in the database.



<b>User Story 3:</b>
<br>
As a user I want to create a user profile with my personal information (name, contact information) for a more personalized experience.

<b>Acceptance Criteria:</b>
<br>
* User should be able to access their profile and saved medications.


<b>User Story 4:</b>
<br>
As a user I want to view a list of all my current and past medications to track when and how often I've taken each medication for easy reference. (GET all meds)

<b>Acceptance Criteria:</b>
<br>
* User can search for all medications, past and present.
* Search results should display a list of all medications.


<b>User Story 5:</b>
<br>
As a user I want to view a specific current or past medication to track when and how often I've taken it. (GET med by id)

<b>Acceptance Criteria:</b>
<br>
* User can filter search results by medications.
* Search results should return an exact match to the user's search criteria.


<b>User Story 6:</b>
<br>
As a user I want to add a new medication to my profile, specifying the medication name, description, dosage, and whether I am currently taking it. (POST med)

<b>Acceptance Criteria:</b>
<br>
* User can add new medications to their list of prescribed meds.
* User can save the details of a medication to their profile.


<b>User Story 7:</b>
<br>
As a user I want to update/edit the details of a medication in my profile, including the name, description, dosage, and current status. (PUT med)

<b>Acceptance Criteria:</b>
<br>

* User can edit/update medications.
* User can save the updated information for specific medications to their profile.


<b>User Story 8:</b>
<br>
As a user I want to delete a medication from my profile when I no longer need to track it. (DELETE med)

<b>Acceptance Criteria:</b>
<br>
* User can delete medications.


<b>User Story 9:</b>
<br>
As a user I want to view a list of all my medication reminders to track upcoming reminders for easy reference. (GET all reminders)

<b>Acceptance Criteria:</b>
<br>
* User can search for all reminders.
* Search results should display a list of all reminders.


<b>User Story 10:</b>
<br>
As a user I want to view a specific reminder to easily refer back to it. (GET reminder by id)

<b>Acceptance Criteria:</b>
<br>
* User can filter search results for reminders by medication.
* Search results should return an exact match to the user's search criteria.


<b>User Story 11:</b>
<br>
As a user I want to add a new medication reminder to my profile. (POST reminder)

<b>Acceptance Criteria:</b>
<br>
* User can add new reminders to their list of reminders, specifying the medication name and instructions.
* User can save the details of a reminder to their profile.


<b>User Story 12:</b>
<br>
As a user I want to update/edit the details of a reminder in my profile, including the name and instructions. (PUT reminder)

<b>Acceptance Criteria:</b>
<br>
* User can edit/update reminders.
* User can save the updated information for specific reminders to their profile.


<b>User Story 13:</b>
<br>
As a user I want to delete a reminder from my profile when I no longer need it. (DELETE reminder)

<b>Acceptance Criteria:</b>
<br>
* User can delete reminders.



## Major Hurdles

When writing the tests for my Controller and Service classes, I was able to get all tests for the medication model to pass in both MockMVC and using Cucumber-Rest Assured before adding the security and authorization. However, after adding the security and authorization, the tests would no longer pass. All the endpoints work, so I know it's just a matter of me doing more research into how to refactor the test code for models that implement security and authorization, which I plan on doing in the near future.  



### Links
* User Stories - https://docs.google.com/document/d/1KTKWRJAiN2i-BFSEM_pHePvvJ_ciq0ii6ej0DfFLOsU/edit?usp=sharing

* HTTP requests/endpoints spreadsheet - https://docs.google.com/spreadsheets/d/1RojJb3knWkSgYE3XLzehLXKo2tuQJLuZdd0BcsWADXY/edit?usp=sharing

* ERD (entity relationship diagram) - https://lucid.app/lucidchart/3d409703-f2a7-49e0-b24f-2aff4ef96b20/edit?viewport_loc=-547%2C-21%2C3750%2C1557%2C0_0&invitationId=inv_b1b165af-6680-4a82-9f86-846e1a23d271



### Special Thanks

* Suresh Sigera - my instructor who taught me all the concepts used in this project. 
* [GitHub](https://github.com/sureshmelvinsigera) 



### Author

:woman_technologist: Erica Ayala

* [LinkedIn](https://www.linkedin.com/in/ayalavirtual)

* [GitHub](https://www.github.com/AyalaVirtual) 



