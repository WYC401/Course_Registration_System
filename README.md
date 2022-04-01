# Course Registration System

## About
This is course registration system which can allow students to perform following things:
- register courses
- drop courses already registered
- search courses 
- view all the courses registered

## Who will Use it?
Students with valid username and password can register into this system.

## What is interesting? 
The system is developed by *graph data structure* and I am thinking about adding more functionalities like 
showing students learning roadmap。

## User story
- As a user, I want to be able to register a new course which I have not taken.
- As a user, I want to be able to search courses I want to register.
- As a user, I want to be able to drop the courses I do not like.
- As a user, I want to be able to view all the courses I have registered.
- As a user, I want to be able to save the current state of registration system.
- As a user, I want to be able to load the previous state of registration system.

## Phase 4: Task 2
Sample Logs are:
- Thu Mar 31 17:22:59 PDT 2022 student with ID 1 registered course with ID 110
- Thu Mar 31 17:22:59 PDT 2022 student with ID 1 dropped course with ID 110
- Thu Mar 31 17:22:59 PDT 2022 Yicheng Wang logined in 
- Thu Mar 31 17:22:59 PDT 2022 Loaded from previous state
- Thu Mar 31 17:22:59 PDT 2022 Saved this state

## Phase 4: Task 3
- From the UML, there are some coupling in the class Search Pane, RegisterPane, ViewRegisteredCoursePane and MenuUI. The
users' information can be accessed by passing his/her ID Number so there is no need to include the user as a filed for 
these classes. 
- Also, the class SearchPane and DropPane have the same look except for the drop button, so maybe an 
abstract class can be extracted from them. 
- Last, it is worth of effort to change the name of some methods to make it understandable. 