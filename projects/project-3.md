---
layout: toc
permalink: project/3
---

# Project 3: Teamwork!

In this project, you be working in teams of 3 students to desing, implement, test, and demo a useful
tool of your creation and choosing. The objectives of this project are:

* To gain more experience with networking and/or multithreading
* To gain more experience with GUI programming
* To gain more experience with the software engineering lifecycle
* To learn how to work effectively as a team to meet project milestones
* To work on a larger scale project of sufficent depth and scope

## Group requirements

All group members must:
* Divide the work equally
* Always submit their contributions regularly through git (this is how we will grade)
* Treat eachother respectfully, fairly, and professionally at all times ([see the CS Dept JEDI website](https://www.cs.seas.gwu.edu/justice-equity-diversity-and-inclusion-resources) for more details)
* Select a team manager who is responsible for organizing the project as a part of their duties

You may choose your own group (or subset of a group); we will then pair remaining students randomly with any group that needs more members. We reserve the right to change group assignments and/or separate group members (which may involve students working alone) at any point during the semester. It is possible that there may be a group with either 2 or 4 members (based on the number of students enrolled); we will adjust the requirements for such a group. However, students will be expected to work in groups of 3 otherwise (and may NOT select to be in a smaller or larger group on their own).

Please use the post on Ed to let the professor know who you would like to be in your group (if anyone).

## Getting Started

### Deliverables

Please note the due dates in the schedule of the following items:
* team assignments handed out, inital repo created (work on in lab week 0)
* proposal brainstorm (work on in lab week 0, lecture week 1, due following day)
* project requirements specification and UML design (work on in lab week 1, due day after next lecture)
* coding, testing, and debugging project prototype (work on in lab week 2 and lecture week 3, due day after that lecture)
* prototype presentation to TAs (due/present in lab week 3)
* updated prototype presentation based on TA feedback to class (due/present in lecture week 4)
* final project code, presentation, and groupwork evals due (last Monday day of lecture)

Up to 30 points extra credit can be earned for choosing challenging projects (see grading rubric below)

## Team setup (week 0)

The first lab session for this project will have you:
* receive your team assignments at the start of lab
* ask you to select a team manager for your project
* create a github repo for your project, and add your team members, the grader, and the professor
* move on to your project proposal (below) when you finish these items

### Choosing a Team Manager

It is often the case that it's better to have a single manager, than no manager and/or multiple managers. Your team should nominate someone to be the manager for the entire project. This individual will be responsible for:
* setting up the initial github repo
* coordinating group meetings inside and outside of class. These meetings should be held in public spaces (like the library) as opposed to private spaces (like someone's dorm)
* coordinating the completion of deliverables, and their timely submission

Note that the manager must work with all the group members on these deliverables; they do not get to just 'dictate.' All group members must agree to all the timelines, division of labor, and due dates made collectively with the manager. The manager is also responsible for designing/coding/testing/etc the project with the rest of the group (though their workload can be adjusted due to the additional demands of coordination in their role).

### Setting up bi-weekly meetings

The manager should work with the group to identify two times each week (until the end of the semester) to meet outside of class for at least 1-2 hours (more might be needed individually) to work on the project together. A an excel file should be created an submitted to list all these meeting days and times, and to note attendance.

### Creating a github repo

Use git, as discussed in Lab 0, the team manager creates a repo called `gitusername-project3`, add these two files to it, and commit and push the changes to github. The timestamp of your invitation of the grader and professor as a collaborator must be from the lab first session for this project.

### Grading rubric for Team Setup

|Item | Points |
|the name of the repo for this lab matches the pattern `gitusername-project3` with the manager's github username| 3 |
|the grader and professor have both been added as a collaborator to the repo with an invite timestamp during the lab| 2 |
|all group members have been added to the repo| 2 |
|the repo has been made private | 3 |
|an excel file called `meeting_attendance.xlsx` is in the repo, and has at least **seven outside-of-class meetings** scheduled with space to record attendance | 5 |
|TOTAL | 15 |

## Project proposal (week 0 and week 1)

Your team should select an interesting project that ideally would be something that you would want to put on your resume via a github link (employers often ask for this sort of thing). You'll spend the next 2-3 weeks desiging and implementing a working prototype of your project, so it is important to pick something appropriately challenging.

### Examples of potential project ideas

* A board game implementation, such as chess, checkers, settlers of Catan, etc.
* A cybersecurity threat detection GUI
* A GUI to assist data scientists with labelling images and/or video for machine learning
* A chatbot that learns from its interactions with users (just keep it clean/healthy!)

### Project requirements

Your project must:
* Use some kind of meaningful networking and/or multithreading
* Be meaningfully different from the GWack project
* Focus on the development of a GUI
* Be complex enough (as approved by the professor)
* Have a testing regime planned and then implemented
* Have a demo that shows how your work met the grading criteria

### Proposal requirements

Once you've agreed upon a rough idea of what you want to create, you will write a project proposal that will help the professor identify if your project meets the requirements. It should have the following sections:
* Motivation for why this is an interesting project
* Description of what task/problem you're trying to solve
* Description of the GUI along with with a sketch
* Description of how your project incorporates networking and/or multithreading
* Description of how and why the project is sufficiently challenging
* Description of a testing plan and its deliverables
* A description of how the manager's workload will be adjusted to account for the extra work they are doing

When you are ready, submit your proposal as a PDF file before the due date on github.

### Grading rubric for Project Proposal

|Item | Points |
|a pdf file called `proposal.pdf` is in the repo with the project proposal | 1 |
|all group members' names are on the first page of the proposal | 1 |
|all seven (7) bullet points above are clearly written and have sufficient and convincing arguments and/or explanations| 14 |
|all seven bullet points above are found in a different heading in your document | 2 |
|TOTAL | 18 |

## Requirements Specification and UML Design (week 1)

You will want to create a list of functional and non-function requirements that your project well deliver upon. Think about the different
use cases your software will have, and use these to make a list of actions your software must be able to provide (functional requirements) 
as well as at least one constraint (non-functional requirement), such as "it must take less than ten seconds to connect."

Create a table for your requirements with the following columns (shows an example using GWack below, which you can delete):
| `number` | `description` | `functional or non-functional` | `testing input` | `testing expected output` | `PASS` |
| 1 | Allows the user to connect to a server using an IP address | functional | Tester will enter a hostname, IP address, and port. Then, the tester will press the Connect button | The server messages the client that they are connected | no |
| 2 | The client is able to connect to the server within ten seconds | non-functional | see input for (1) | The connection message is displayed within ten seconds | no |

You will use the `PASS` column in later parts of this project; for now, leave it empty.

Once you have an outline of what features and functionality your software will have, you will need to plan out what classes you will  need, as well as their relationships, fields, and methods. Prepare a UML diagram that meets the grading rubric below.

Your design must incorporate the feedback on the proposal from the professor.

### Grading rubric for Requirements Specification

|Item | Points |
|a pdf file called `requirements.pdf` is in the repo with a table of requirements in the format described above | 1 |
|all group members' names are on the first page of the requirements file | 1 |
|at least seven functional requirements are clearly and correctly described using the format above | 14 |
|at least two non-functional requirements are clearly and correctly described using the format above | 4 |
|the requirements capture all the functionality agreed upon (with professor feedback) from the proposal | 7 |
|an image called `uml_diagram.png` is in the repo with your UML diagram | 1 |
|the UML diagram contains at least three classes | 3 |
|the UML diagram contains at least one inheritance relationship, correctly documented | 2 |
|the UML diagram uses good OOP for field and member visibility | 3 |
|the UML diagram uses good OOP for inheritance from parent classes | 3 |
|the UML diagram has elements for all features of the GUI | 3 |
|the UML diagram shows the implementation of the networking and/or multithreading component | 3 |
|TOTAL | 45 |

## Project Implementation and Testing (week 2 and week 3)

Once you have completed your UML diagram, you can move on to coding up and testing your software. You will have a chance to  receive feedback from the TAs, your classmates, and your professor before you submit the final version of the code.

Once your prototype is complete, you need to test it using the testing plan in your requirements, and report correctly on which requirements were met, versus which failed. Resubmit your requirements (with any updates based on graded feedback) indicating whether or not your test cases passed or failed.

### Grading Rubric for Project Code

The coding portion of this project will be graded after the last day of lecture, after your final presentations. Partial credit will be given below.

|Item | Points |
|your code implements all of the functionality you outlined in your proposal and/or requirements | 100 |
|all test cases have been run, with `pass` or `fail` recorded accurately | 20 |
|all TA feedback has been addressed | 20 |
|TOTAL | 140 |

## Project Prototype Presentations and Group Evals (week 3 and week 4)

You will need to present your project prototype demo to both a TA during lab, as well as the class on the last lecture of the semester. Any feedback given by the TAs must be incorporated by your second presentation for full credit. The presentation should be 8-10 minutes.

You will also be required to fill out a [groupwork evaluation form](./groupwork_eval.csv) for each of your team members. We reserve the right to retroactively deduct points from any and all team members that are found to not be participating sufficiently/equally in any part of the project. These forms will be automatically processed, so please follow the instructions carefully -- forms that are malformed will not be graded.

### Grading Rubric for Prototype Presentations and Group Evals

| Item | Points |
| the students have uploaded a bulleted set of changes requested in a file called `ta_feedback.txt` | 10 |
| the presentation outlines the proposal motivation | 3 |
| the presentation was at least 8 minutes long | 10 |
| the presentation includes a working demo that incorporates all TA feedback | 10 |
| the team member participated equally in the presentation | up to minus 40 points for not presenting|
| the project was sufficiently hard and challenging for the group size | 30 |
| group evaluation submitted for each team member, following the instructions correctly | 15 | 
| extra credit for challenging projects | up to 50 | 
| TOTAL | 78 |

