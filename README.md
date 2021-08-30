## Scrum Board

# Project Description

An interactive system that is capable of preforming a variety of scrum operations, such as creating products, scrumboards and sprints, assigning tasks to users, notifying users when build presentations have been scheduled, allowing users to complete tasks, among many others.

# Team Members

* Sidd Mohanty - Project Lead
* Ellis Delgado
* Jasmine McCall
* Joshua Muckey
* Brian Tran

# Technologies Used

* Spring (Spring Boot, Spring WebFlux)
* Intuit Karate
* Postman
* AWS KeySpaces
* AWS S3
* SonarCloud
* GitHub
* Trello

# Features

* Dynamic user types that change based on whether or not a particular user is simultaneously a product owner and scrummaster in the system
* Functioning notification system that can be cleared by the user
* Auto completion of Sprints that have reached their end date and time
* Users can apply for tasks in order to get priority when those tasks are assigned
* Users can change the status of a task that they have been assigned from BACKLOG to TODO, IN_PROGRESS, TESTING and COMPLETED, simulating the developmental progress of that user story.
* Full AOP integration used for user authentication that works with our dynamic user typing; a user who is not registered in the system as a product owner cannot perform product level actions such as creating scrum boards, or creating entirely new user stories.
