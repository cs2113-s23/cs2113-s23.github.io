---
layout: default
permalink: /lab/2
---

# Lab 2: I'm a dot thing living in a grid world

*View the video for this lab on [youtube](https://youtu.be/oVsOfsy1-mc).*

## Setup

Download the [DotChaser.java](./DotChaser.java) file.

Download the [Plotter.jar](./Plotter.jar) file.


### Github setup

Use git, as discussed in Lab 0, to create a repo called `gitusername-lab2`, add these two files to it, and commit and push the changes to github. The timestamp of your invitation of the grader as a collaborator must be from this lab session.

### Running your program

Run your lab on the command line by executing the following pipeline

```
java DotChaser | java -jar Plotter.jar
```

The program `Plotter.jar` is provided for you. 

### Testing your lab

There is no test script for this lab. Your grade is based on a code review of your object-oriented design principles; see the grading rubric.


# Part 1: Rewriting the code using OOP (75 pts)

Take a look at the `DotChaser` program. You can compile and run it as is:

```
javac DotChaser.java
java DotChaser
```

You'll see some interesting (but boring) output.

```
55 50 b
45 50 r
done
56 50 b
45 51 r
done
57 50 b
46 51 r
done
58 50 b
.
.
.
```

Each line represents the location and color of a "Thing" dot in each round. The end of a round is indicated by "done"

```
55 50 b <- round i, blue dot at row 55, column 50
45 50 r <- round i, red dot at row 45, column 50
done    <- end round
56 50 b <- round i+1, blue dot at row 56, column 50
45 51 r <- round i+1, red dot at row 45, column 51
done    <- end round
```

As noted, a list of coordinates is pretty boring, so we also provided you a Plotter program that reads coordinates from `stdin` and plots them for a nice little visual of moving dots. Try it out yourself: 

```
java DotChaser | java -jar Plotter.jar 
```

The output of DotChaser is piped into Plotter, which plots each of the dots at the row,col coordinates and color given. The "done" at the end of a group of lines tells us that we are done with the "round", so now the updated display should be shown, and what follows will be values for the next "round". What you get is a really pretty animation like below. 

<center>
<img src="/images/DotChaser.png" alt="DotChaser" width="50%" height="50%" />
</center>


So what do you have to do? Well, the program `DotChaser` is written entirely as a *Procedural Program*. You will re-write it as an *Object-Oriented Program*, and once done, you will extend its functionality a bit as well. 

Note that the plotter can plot more than just red and blue. Here are your color choices as you make your things: r (red), b (blue), g (green), y (yellow), o (orange), p (pink), m (magenta), k (black).

<div class="requirement">

# Part 1: Rewrite `DotChaser` functionality into several classes using good OOP (65 pts)

Rewrite `DotChaser` as an object-oriented program. You will (presumably) be creating other `.java` files as well. The **output of `DotChaser` should not change**, but the design must follow all the good object-oriented design principles we have discussed. In particular, you must use encapsulation, information hiding, inheritance, and polymorphism. Maximize code reuse; keep implementation and interface as separate as possible. Your inheritance should follow the picture on the right.

You must do these things:

* **Remove static methods**: only `main()` should be static in all of your classes.
* **Polymorphism**: You should have `TypeA` and `TypeB` classes; observe how they differ. Your parent class `Thing` most likely will not need a single if statement. If you find you need one/more, make sure there isn't a way to utilize polymorphism instead. If you still find the need for if statements, ask your instructor if they are appropriate. Related to this, your child classes should have at least one method which demonstrates polymorphism. This should naturally come from your design, but we make note of it here as a requirement to give you that extra nudge.
* **Use a linked list**: Remove `Node` from `main()` and put it inside a proper `List` or `Queue` class called `ThingList`. You'll have to write this. Your `main()` should not have `Node` variables anymore, but instead a `ThingList` variable with nice calls such as `list.add(thing)`.
* **README.md**: In the  README file and write a paragraph (or two) that explains how your redesign makes use of encapsulation, information hiding, inheritance and polymorphism.

Be sure to add all your new Java files to the repo for submission
</div>

# Part 2: Add a new thing (15 pts)

The original `DotChaser` had two types of Things: `typeA`, which randomly choses left, right or straight at every round; and `typeB`, which randomly chooses left, right or straight every 10th round. Now that you have a nice object-oriented version, create a third type of `Thing` called `TypeC`. What exactly it does is up to you, but it needs to use some diagonal motion (i.e. left-right-left-right-... sequences, or draws a circle, triangle, or other shape). The principal thing to keep in mind is how OOP makes this easier and cleaner. 


<div class="requirement">
Create a third `ThingC` type that must have.

1. Ensure that the orignal `DotChaser` still works the same as before, even after you've added your new type of `ThingC`. That is, the red and blue dots act normally.
2. The new type of `ThingC` must move in a *new* way different than `typeA` or `typeB`. 
3. In your `README.md` indicate the name of the new type and the Java file used to program it. **Be sure to add that file to your repo for submission :)**
4. Add a paragraph to the `README.md` file that explains how your new OOP design makes adding new types of `ThingCS`s easy.
5. Add a final paragraph to the `README.md` that explains exactly where in your program there is a polymorphic function call, and how that plays an essential role in the program functioning properly.

*Be sure to add all your new Java files to the repo for submission.*


Here's an example of a solution with an extra yellow thing doing spirals:

<center>
<img src="/images/DotChaser-spiral.png" alt="DotChaser Spiral Yellow" width="50%" height="50%" />
</center>
</div>

# Part 3: Create a UML diagram for your `Thing`s (10 pts)

Use Violet UML (or another tool) to generate a UML diagram for your many `Thing` types. 

> Create a UML diagram. Include it in repo named 'UML.png' and link it into your README. If you do the extra credit below, be sure to include that `Thing` type in your UML diagram. 

# Grading rubric and submission

Use git, as discussed in lab zero, to submit your work in a repo called `gitusername-lab2`. You will be graded on the following:

|Item | Points |
|the name of the repo for this lab matches the pattern  `gitusername-lab2` | 3 |
|the grader has been added as a collaborator to the repo with an invite timestamp during the lab| 4 |
|the repo has been made private | 3 |
|`TypeA` and `TypeB` are child classes of `Thing` | 5 |
|`Thing` does not use if-statements nor the `instanceof` operator | 5 |
|no static methods besides `main` in any of your classes | 5 |
|the `Thing` class is responsible for movement, utilizing good OOP | 5 |
|the `Thing` class contains the appropriate fields, utilizing good OOP | 5 |
|the `Thing` class uses abstraction appropriately in all places, utilizing good OOP | 5 |
|the `TypeA` and `TypeB` classes turn appropriately, utilizing good OOP | 5 | 
|the `TypeA` and `TypeB` contain the minimum number of fields necessary, utilizing good OOP | 5 | 
|the `TypeA` and `TypeB` contain the minimum number of methods necessary, utilizing good OOP | 5 | 
|the `ThingList` class contains the `Node` class as an inner class, utilizing good OOP | 5 |
|the `ThingList` class has methods to `addAll`, `moveAll`, and `printAll` `Thing`s inside the list/queue | 5 |
|the `TypeC` class meets the specifications above | 15 |
|a README file as specified above is included | 5 |
|a UML diagram matching the code implementation as been included, that lists all classes and their methods and fields | 10 |
|the new implementation preserves the original `DotChaser` functionality for `TypeA` and `TypeB` | 5 |
|TOTAL | 100 |
