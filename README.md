# Scrum Board

## Project Description

An interactive system that is capable of preforming a variety of scrum operations, such as creating products, scrumboards and sprints, assigning tasks to users, notifying users when build presentations have been scheduled, allowing users to complete tasks, among many others.

## Team Members

* Sidd Mohanty - Project Lead
* Ellis Delgado
* Jasmine McCall
* Joshua Muckey
* Brian Tran

## Technologies Used

* Spring (Spring Boot, Spring WebFlux)
* Intuit Karate
* Postman
* AWS KeySpaces
* AWS S3
* SonarCloud
* GitHub
* Trello

## Features

* Dynamic user types that change based on whether or not a particular user is simultaneously a product owner and scrummaster in the system
* Functioning notification system that can be cleared by the user
* Auto completion of Sprints that have reached their end date and time
* Users can apply for tasks in order to get priority when those tasks are assigned
* Users can change the status of a task that they have been assigned from BACKLOG to TODO, IN_PROGRESS, TESTING and COMPLETED, simulating the developmental progress of that user story.
* Full AOP integration used for user authentication that works with our dynamic user typing; a user who is not registered in the system as a product owner cannot perform product level actions such as creating scrum boards, or creating entirely new user stories.

## Getting Started

* Clone the repository in your terminal with the `git clone <repository url>` command
  * You need to establish a Keyspace connection on the AWS website
      - Once you get your *AWS Username* and *AWS Password* from the IAM, you need to set those as environmental variables
      - You need to set up the truststore password in the terminal
      - Please do not push these to the repo! You will have to pay AWS alot of money if you do!

## Usage
  * To start the application, go to the driver.java file and run the application
  * Once the connection is established, use *Postman* to test any routes from the User Controller!
  * To test the application through *Karate*, go to any of the feature folders within the test folders and start one of files. Once the test is running, a link will be provided in the console displaying the results.

## License
