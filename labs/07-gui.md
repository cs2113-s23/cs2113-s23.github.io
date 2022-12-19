---
layout: default
permalink: lab/7
---

# Lab 7: GUI Enigma (Replacement)

*View the video for this lab on [YouTube](https://youtu.be/XLK8MnmXqmw)*

**This lab is not required. If you choose to complete it, you can earn up to 5 bonus points on the Java exam.**

## Preliminaries

### Github Link


Accept this assignment here: [https://classroom.github.com/a/-Mqb12F3](https://classroom.github.com/a/-Mqb12F3)

### Development Environment

This lab can be developed locally on your machine using any java installation.

### Running your program

Run your lab on the command line by executing the following pipeline

```
java EnigmaGUI
```

### Testing your lab

There is no test script for this lab. Your grade is based on running your GUI and testing different input/outputs as well as additional functionality. We will also read your code for object-oriented design principles. 


# Enigma GUI

In this lab, you are going to write a GUI wrapper around the Enigma program you wrote as part of [lab-5](/lab/5). 

## Reviewing how Engima worked

If you recall, that lab required you to complete three classes


* `Rotor` : representing a rotor of an Engima Machine
* `Enigma` : representation of an Enigma Machine
* `Comms` : the communication (and main method) of an enigma machine.

To decrypt and encrypt, you provided the settings as command line arguments:

```
                  ,-- inner rotor initially positioned so X is on top
                  |,-- middle rotor initially positioned so # is on top
                  ||  ,-- outer rotor initially positioned so Y is on top
                  || /
java Comms 4 2 3 "X#Y" encrypt
           | | |
           | | `-- outer rotor is rotor 3
           | `-- middle rotor is rotor 2
           `-- inner rotor is rotor 4
```

And then the input and output on the command line

```
~/$ java Comms 1 2 3 "###" encrypt
AAA
NDU
```

```
~/$ java Comms 3 1 2 "SAT" encrypt
DO#YOUR#BEST#AND#KEEP#ON#KEEPIN#ON
ACAAFAEOZFWKBQKPXZOGIKXTNPEBDXWQCZ
```

```
~/$ java Comms 5 2 4 "EST" decrypt
CSHIAWDFGDCOE#EZKJHRWAZDDCBCILON#PKUJEXEXSHINZ
THE#NATIONAL#ANIMAL#OF#SCOTLAND#IS#THE#UNICORN
```

## Building a GUI

The purpose of this lab is to wrap all that functionality into a GUI. For example, here is a screenshot of a GUI implementation that you should be able to achieve:

<img src="/images/Engima-GUI.png" 
alt="Enigma GUI" 
width="60%" 
style="display: block;
margin-left: auto;
margin-right: auto;"/>

You must use the following GUI elements in completing this lab
* `JComboBox` : for selecting the rotor numbers
* `JTextField` : for selecting the start of the rotors
* `JTextArea` : for providing input to and output from Enigma
* `JButton` : for selecting encrypt or decrypt
* `JLabel` : for including other text references, such as "Inner" or "Middle"

You can use any layout scheme you want for including these elements, but it should look something similar to the screenshot and be obvious in how to use it. For reference, I only used the `BorderLayout`, but you may find other layouts effective here.  (*Hint: you may also find it useful to create additional `JPanels` which organize different parts of your GUI, like the settings and the input/output areas.*)

## Requirements

<div class="requirement">

You must submit *at least* two classes
* `EnigmaGUI.java` : the main method for launching the GUI
* `EnigmaFrame.java` : the JFrame that contains the GUI

You may also create additional classes as you see fit to complete this assignment. 

You should use your completed Enigma code from [lab-5](/lab/5), but if you did not fully finish that assignment, you can use the following class files to complete this assignment. (Note that only compiled version of `Enigma` and `Rotor` are provided, but the full src of `Comms` is available.)
* [Enigma.class](/src/Enigma.class)
* [Rotor.class](/src/Rotor.class)
* [Comms.java](/src/Comms.java) 

These class files are included in the starter code for your repository. If you want to use your own version, copy over your `Enigma.java` and `Rotor.java` file and compiles those, which will replace the existing class files. 
</div>
