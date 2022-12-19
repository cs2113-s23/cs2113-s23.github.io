---
layout: default
permalink: /lab/6
---

# Lab 6: I'm a dot thing living in a grid world

*View the video for this lab on [youtube](https://youtu.be/oVsOfsy1-mc).*

## Preliminaries

### Github Link

<!--
Accept this assignment here: [https://classroom.github.com/a/25EbQLBh](https://classroom.github.com/a/25EbQLBh) -->

[https://classroom.github.com/a/A1fyNHee](https://classroom.github.com/a/A1fyNHee) 

### Development Environment

**This lab should be developed locally** on your machine using any java installation; do not use the remote connection, because it will be unable to run the code to generate a new window and display results. Use VSCode to clone the git repo to your local computer, open a terminal through VSCode, and make sure the terminal is set to be 'git bash' (see the video at the top of this page for help).

### Running your program

Run your lab on the command line by executing the following pipeline

```
java DotChaser | java -jar Plotter.jar
```

The program `Plotter.jar` is provided for you. 
### Testing your lab

There is no test script for this lab. Your grade is based on a code review of your object-oriented design principles. 


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
Rewrite `DotChaser` as an object-oriented program. You will (presumably) be creating other `.java` files as well. The output of `DotChaser` should not change, but the design must follow all the good object-oriented design principles we have discussed. In particular, you must use encapsulation, information hiding, inheritance, and polymorphism. Maximize code reuse; keep implementation and interface as separate as possible. Your inheritance should follow the picture on the right.

You must do these things:

* **Remove static methods**: only `main()` should be static in all of your classes.
* **Polymorphism**: Your parent class `Thing` most likely will not need a single if statement. If you find you need one/more, make sure there isn't a way to utilize polymorphism instead. If you still find the need for if statements, ask your instructor if they are appropriate. Related to this, your child classes should have at least one method which demonstrates polymorphism. This should naturally come from your design, but we make note of it here as a requirement to give you that extra nudge.
* **Use a linked list**: Remove `Node` from `main()` and put it inside a proper `List` or `Queue` class. You'll have to write this, or adapt a queue from previous assignments. Your `main()` should not have `Node` variables anymore, but instead a `List` or `Queue` variable with nice calls such as `list.add(thing)`.
* **README.md**: In the  README file and write a paragraph (or two) that explains how your redesign makes use of encapsulation, information hiding, inheritance and polymorphism.

Be sure to add all your new Java files to the repo for submission
</div>

# Part 2: Add a new thing (15 pts)

The original `DotChaser` had two types of Things: `typeA`, which randomly choses left, right or straight at every round; and `typeB`, which randomly chooses left, right or straight every 10th round. Now that you have a nice object-oriented version, create a third type of Thing. What exactly it does is up to you, but it needs to use some diagonal motion (i.e. left-right-left-right-... sequences, or draws a circle, triangle, or other shape). The principal thing to keep in mind is how OOP makes this easier and cleaner. 


<div class="requirement">
Create a third `Thing` type that must have.

1. Ensure that the orignal `DotChaser` still works the same as before, even after you've added your new type of Thing. That is, the red and blue dot act normally.
2. The new type of Thing must move in a *new* way different than `typeA` or `typeB`. 
3. In your `README.md` indicate the name of the new type and the Java file used to program it. **Be sure to add that file to your repo for submission :)**
4. Add a paragraph to the `README.md` file that explains how your new OOP design makes adding new types of Things easy.
4. Add a final paragraph to the `README.md` that explains exactly where in your program there is a polymorphic function call, and how that plays an essential role in the program functioning properly.

*Be sure to add all your new Java files to the repo for submission.*


Here's an example of a solution with an extra yellow thing doing spirals

<center>
<img src="/images/DotChaser-spiral.png" alt="DotChaser Spiral Yellow" width="50%" height="50%" />
</center>
</div>

# Part 3: Create a UML diagram for your `Thing`s (10 pts)

Use Violet UML (or another tool) to generate a UML diagram for your many `Thing` types. 

> Create a UML diagram. Include it in repo named 'UML.png' and link it into your README. If you do the extra credit below, be sure to include that Thing type in your UML diagram. 

# Extra Credit (up to 10 points) (optional)

Create yet another type of movable thing, follow proper OOP design, and have it move in a more complicated way. Points will be given for creativity and complexity. As an example, imagine coding a thing that draws letters!

> You must:
> 1. Indicate your `README.md` file that you complete the extra credit, and what Java files to look at.
> 2. Add another paragraph in your `README.md` describing this extension to the object model.
> 3. Include a screenshot of your plotter output and create an image in the README so we know what to look for. 
>
> *Be sure to add all your new Java files to the repo for submission.*
> 
> Don't forget to include this thing type in your UML diagram.


