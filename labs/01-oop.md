---
layout: default
permalink: /lab/1
---

# Lab 1: Enigma 

*View the videos for this lab on [youtube](https://youtu.be/6IrmHfD9aCA)*

## Setup

Download the [Comms.java](./Comms.java) file.

Download the [Enigma.java](./Enigma.java) file.

Download the [Rotor.java](./Rotor.java) file.

Download the [Lab1_Tester.java](./Lab1_Tester.java) file.

### Github setup

Use git, as discussed in Lab 0, to create a repo called `gitusername-lab1`, add these four files to it, and commit and push the changes to github. The timestamp of your invitation of the grader as a collaborator must be from this lab session.

## Enigma Machines (simplified model)

The Enigma machine was used by the Germans in WWII to send encoded messages. At the time, it was a breakthrough in cryptography, and was essentially an extremely advanced substitution cipher. The Enigma machine is also famous for not just being a very advanced cipher, but also because it was broken by none other than Alan Turning, whom many consider the founder of computer science.

In this lab, we will model a simplified version of the Enigma machine. If you're interested in learning more, [Prof. Gavin Taylor](https://www.usna.edu/Users/cs/taylor/) (USNA) has a comprehensive [write up on the topic](https://www.usna.edu/Users/cs/nchamber/courses/ic211/s19/lab/l04/realenigma.html). (More details and an image are found at the end of this lab.)

### What you need to know for this lab

What you need to know for this lab: Enigma machines used interchangeable rotors that could be placed in different orientations to obtain different substitution patterns. More significantly, the rotors rotated after each character was encoded, changing the substitution pattern and making the code very difficult to break. The behavior of the rotating rotors can be modeled, in a simplified form, by a device consisting of labeled, concentric rings. For example, the picture here has three rings labeled with the letters of the alphabet and '#' (representing a space).

To encrypt a character using this model, find the character on the inner rotor (i.e., the inside ring) and note the character aligned with it on the outer rotor (i.e., the outside ring), then find that character on the middle rotor (i.e., the middle ring) and output the one aligned with it on the outer rotor. After a character is encrypted, turn the inner rotor clockwise one step. Whenever the inner rotor returns to its original orientation, the middle rotor turns once in lock-step, just like the odometer in a car.

<div style="text-align:center">
![](/images/enigma.gif)
</div>

For example, in this configuration the character 'A' would be encrypted as 'N', since 'A' on the inner rotor is aligned with 'H' on the outer rotor, and 'H' on the middle rotor is aligned with 'N' on the outer rotor. After performing this encryption, the inner rotor is rotated clockwise, so the letter 'A' would next be encrypted as 'D'.


Note that decrypting a message requires following the same steps, only in reverse: Find the character on the outer rotor, note the character aligned with it on the middle rotor, find that character on the outer rotor, then output the character aligned with it on the inner rotor. Don't forget to rotate the rotors after each letter is decrypted.

## Task Requirements

### The Task

You will define a class `Rotor` to simulate the workings of a single rotor, and the class `Enigma` to simulate the workings of an Enigma machine using `Rotor` instances. You will be provided a class `Comms` (with a `run` method) as part of the initial material. You should read that file to see how `Enigma` instances are suppose to be used.

**You may not alter `Comms.java` in any way.** 

Your task is to write both `Enigma` and `Rotor` using proper OOP design with class constructors, information hiding, and encapsulation.

### The `Rotor` Class

The `Rotor` class represents a rotor, including the values of the characters in the rotor and its current orientation (which character is currently on top of the rotor). A good strategy for representing this would be to store the characters in a `String` of length 27 (`#` indicating space) -- index 0 is the top most character. Note that `Rotors` rotate! And after a full rotation, the next outer rotor rotates (like a odometer in a car). That means you'll need to remember where the rotor started. All of this can lead to some good OOP! :)

For example, your rotor should be able to do the following

1.    Be constructed, requiring a `String` that defines the rotor and a single character defining the symbol that should be initially at the top of the rotor. Note: in the constructor, you can call other methods, like the method to rotate!
2.    Rotate one click clockwise. This should involve changing the `String`.
3.    Return the index in the `String` at which a given character appears.
4.    Return the character at a given index. 

An example of a rotor `String` is `#GNUAHOVBIPWCJQXDKRYELSZFMT` ... which you are to interpret circularly, so that the last character loops around to the first. If you imagine that the first positition indicates the top spot on the rotor, then:

`#GNUAHOVBIPWCJQXDKRYELSZFMT` rotated one click clockwise is `T#GNUAHOVBIPWCJQXDKRYELSZFM`

### The `Enigma` Class

This we leave partly up to you. We expect your Engima to have 5 possible rotors, and when your Enigma class is created, it chooses which 3 to use along with their rotor starting symbols. You must hardcode the 5 possible rotors in your class as the following `String`s:

1. `#GNUAHOVBIPWCJQXDKRYELSZFMT`
2. `#EJOTYCHMRWAFKPUZDINSXBGLQV`
3. `#BDFHJLNPRTVXZACEGIKMOQSUWY`
4. `#NWDKHGXZVRIFJBLMAOPSCYUTQE`
5. `#TGOWHLIFMCSZYRVXQABUPEJKND`

You must also have encrypt and decrypt methods for encrypting and decrypting strings. These must be compatible with the Comms.java file that we give you. The behavior in these methods must follow the enigma procedure described above in "Our Simple Model of the Enigma". You may also choose to write additional helper methods as necessary.

### The `Comms` Class

**Note you should not edit the `Comms` class**, but you do need to know how it works. 

This is the program's driver class, and it is provided for you. The program takes as input (from the `run` method in `Comms`) the three rotors and their starting characters, and a message. The diagram below describes the meaning of each argument, and then the Java code shows how you can run this program:

```
                  ,-- inner rotor initially positioned so X is on top
                  |,-- middle rotor initially positioned so # is on top
                  ||  ,-- outer rotor initially positioned so Y is on top
                  || /           ,---- the message to be encrypted/decrypted
                  |||           /
           4 2 3 "X#Y" encrypt AAA
           | | |
           | | `-- outer rotor is rotor 3
           | `-- middle rotor is rotor 2
           `-- inner rotor is rotor 4

String[] args = {"4", "2", "3", "X#Y", "encrypt", "AAA"};
Comms.run(args);
```

The `Comms.run()` method returns a string corresponding to the command (in this example, `encrypt`) applied to the incoming message (`AAA` in this example.)

A couple example runs are here. Note that you have to type in the string you want to encrypt/decrypt. You must also make your own tests and fully test your code:

```
String[] args = {"1", "2", "3", "###", "encrypt", "AAA"};
String result = Comms.run(args);
// the result would be NDU
```

```
String[] args = {"3", "1", "2", "SAT", "encrypt", "DO#YOUR#BEST#AND#KEEP#ON#KEEPIN#ON"};
String result = Comms.run(args);
// the result would be ACAAFAEOZFWKBQKPXZOGIKXTNPEBDXWQCZ
```

```
String[] args = {"5", "2", "4", "EST", "decrypt", "CSHIAWDFGDCOE#EZKJHRWAZDDCBCILON#PKUJEXEXSHINZ"};
String result = Comms.run(args);
// the result would be THE#NATIONAL#ANIMAL#OF#SCOTLAND#IS#THE#UNICORN
```

## Junit testing

To run your unit tests -- remember, you need to have the junit jar file in the same directory -- compile and run your code with the following commands on the terminal (depending on your OS):

### Windows
`javac -classpath ".;junit-platform-console-standalone-1.7.0-M1.jar" Lab1_Tester.java`
`java -classpath ".;junit-platform-console-standalone-1.7.0-M1.jar" org.junit.runner.JUnitCore Lab1_Tester`

### Mac/Linux
`javac -classpath ".:junit-platform-console-standalone-1.7.0-M1.jar" Lab1_Tester.java`
`java -classpath ".:junit-platform-console-standalone-1.7.0-M1.jar" org.junit.runner.JUnitCore Lab1_Tester`


# Grading rubric and submission

Use git, as discussed in lab zero, to submit your work in a repo called `gitusername-lab1`. You will be graded on the following:

|Item | Points |
|the name of the repo for this lab matches the pattern  `gitusername-lab1` | 4 |
|the grader has been added as a collaborator to the repo with an invite timestamp during the lab| 4 |
|the repo has been made private | 4 |
|the test cases pass (11 points each) | 88 |
|TOTAL | 100 |

---
This lab is adopted from IC211 (spring 2019) at USNA. 


