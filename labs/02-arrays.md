---
layout: default
permalink: lab/2
---
<!--
- [Preliminaries](#orgd0c7238)
  - [Lab Learning Goals](#orgfc6aeaa)
  - [Lab Setup](#orge6967bf)
  - [Submission Folder](#orge8fe267)
  - [Submission Instructions](#orgef13726)
  - [Test Script](#org1b1e284)
  - [README file](#org44adbba)
  - [Compiling your code:](#orgd8b4087)
- [Lab Description and Requirements:](#org2438abc)
  - [Part 1: Reading Input from the User and Error Checking (20 points)](#orge86dafc)
    - [Error checking `scanf()`](#orgcf91ac5)
    - [Requirements](#orgfd822f9)
  - [Part 2: Printing the array in the right format (20 points)](#orgd842193)
    - [Passing Arrays as Function Arguments](#org8d56312)
    - [Requirements](#org61dc06e)
  - [Part 3: Reversing the array (25 points)](#org62c8fc0)
    - [Requirements](#orgd8230f6)
  - [Part 4: Randomizing the array (25 points)](#org04163cd)
    - [Random Integers](#org7146619)
    - [Seeding Random Numbers](#orgf9f9e55)
    - [Pseudocode for array randomization](#orga35975d)
    - [Requirements](#org9163908)
  - [Part 5: Sorting the array (10 points)](#org34e2f2b)
    - [Requirements](#org17b7989)

-->

*View the video for this lab on [youtube](https://youtu.be/Ad2MAxZ1SmM)*

# Lab 2: Working with Arrays and Functions

<a id="orgd0c7238"></a>

<!--
* Github Classroom Link: [https://classroom.github.com/a/lqWc1oDt](https://classroom.github.com/a/lqWc1oDt) -->
https://classroom.github.com/a/snBnL6sR

* Github Classroom Link: [https://classroom.github.com/a/snBnL6sR](https://classroom.github.com/a/snBnL6sR)

## Preliminary

### Development Environment

You should develop using VSCode+SSH. Remember to access the starter code via git within VSCode. You'll also be submitting your assignment via git within VSCode. You'll need to have a remote connection (ssh) in VSCode as well, in order to run this lab on the seas ubuntu server (you need this to be running the same version of C that we will use to grade it). All the other C labs this semester will have similar requirements.

### Test Script

To help you complete the lab, I have provide a test script that will run basic tests against your program. The script is not designed to be comprehensive, and you will be graded based on a larger array of tests. To execute the test script, run it from anywhere within the repository directory.

    ./test.sh

### README file

You are required to fill out the README.md file in your repository describing your programming process.


### Compiling your code:

To compile your code in the integrated terminal in VSCode, use `gcc` the gnu c compiler using the following command:

    gcc -Wall manipulator.c -o manipulator

The `-Wall` option will provide additional warnings that you mind find helpful for debugging your program.  


## Lab Description and Requirements:

In this lab you will be programming a single program called `manipulator` which will take in user input to construct an array, and then perform basic manipulations over that array as desired by the user. Here is a basic run of the program for reference:

    $> ./manipulator 
    Enter the length:
    4
    Enter 4 numbers (space seperated):
    1 2 3 4
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    1
    { 1 2 3 4 }
    
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    2
    { 4 3 2 1 }
    
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    3
    { 4 1 2 3 }
    
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    4
    { 1 2 3 4 }
    
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    0
    $> 


<a id="orge86dafc"></a>

## Part 1: Reading Input from the User and Error Checking (20 points)

The first part of the lab is to get user input, error check that input, and populate the array. See the `TODO PART 1` comments to direct you to the appropriate places to work. Below is some additional information to help you complete this task.


<a id="orgcf91ac5"></a>

### Error checking `scanf()`

Recall that in the prior lessons that in C we write output and read input using format strings. In this part of the lab you will write a simple program that takes input from the user and manipulates that input in some way depending on the user choice. You must also error check user input.

    RETURN VALUES
    
         These functions return the number of input items assigned.  This
         can be fewer than provided for, or even zero, in the event of a
         matching failure.  Zero indicates that, although there was input
         available, no conversions were assigned; typically this is due to
         an invalid input character, such as an alphabetic character for a
         `%d' conversion.  The value EOF is returned if an input failure
         occurs before any conversion such as an end-of-file occurs.  If
         an error or end-of-file occurs after conversion has begun, the
         number of conversions which were successfully completed is
         returned.

What this means is that if a user enters something that is not a number, then scanf() will return 0, indicating that it could not properly convert the input into a number given the `%d` format directive. Note that if you tell `scanf` to read in a number, it will stop as soon as it sees a whitespace. We can easily check for such a condition in our code:

```c
int a, res;
printf("Enter a number:\n");
res = scanf("%d", &a);
if(res == 0) {
  printf(stderr, "ERROR: Invalid input"); // note in this case, it's ok to use stdout instead of stderr
  //exit, return, or take some other action
}
```

<a id="orgfd822f9"></a>

### Requirements

<div class="requirement">

For this part of the lab you must complete the `TODO` labels that request user input for the user:

-   You must read in the desired length of the array to be manipulated. If the user input is not a number, you must return 1 and exit with the following print statement:
     
    ```c
    printf("ERROR: Invalid input\n");
    ```

-   You must read in the appropriate number of values into the array and check to make sure that they are valid integers. If the user input is not a number, you must return 1 and exit with the following print statement:
    
    ```c
    printf("ERROR: Invalid input\n");
    ```

-   You must set up the logic for dealing with user choice of operations. For example, this means if user chooses 0, then the loop should break, and if an invalid number is entered the loop should continue with the following error printed:
    
        printf("ERROR: Invalid number. Choose again.\n\n");

Here is some sample output with the error conditions:

    $> ./manipulator 
    Enter the length:
    a
    ERROR: Invalid input
    $> ./manipulator 
    Enter the length:
    5
    Enter 5 numbers (space separated):
    1 2 a d a m
    ERROR: Invalid input
    $> ./manipulator 
    Enter the length:
    5
    Enter 5 numbers (space separated):
    1 2 3 4 5
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    a
    ERROR: Invalid input. Choose again.
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    0
    $> 

</div>


<a id="orgd842193"></a>

## Part 2: Printing the array in the right format (20 points)

For this part of the lab you must complete the `print_array()` function and properly pass the arguments to the array.


<a id="org8d56312"></a>

### Passing Arrays as Function Arguments

When you pass arrays to functions you are not actually passing the array, but rather a reference to the array, i.e., a pointer. Recall that pointers and arrays are really the same thing. So if you have a function that takes an array as an argument, it is really taking a pointer. For example:

```c

void foo(int * a){
   //...
}
```

It remains ambiguous if `a` is a pointer to a array of integers or a single integer, but it is semantically and syntactically correct. If you want to disambiguate the fact that the function takes a reference to an array of integers, that is, a constant pointer, rather than an arbitrary pointer, you can specify it like such:

```c

void foo(int a[]){
  //...
}

```

Finally, when you pass an array, since you are only passing a reference, you are not providing any information about the length of the array. While you might be tempted to use `sizeof()`, that is incorrect. `sizeof()` returns the size in bytes required to store the data item, which for an array of integers is not the length of the array because each integer is 4 bytes.

To manage sizes of arrays, it is customary to pass both the reference to the array and the size of the array to the function to ensure that you do not access the array out of bounds.

```c
void foo(int a[], int len){
  //...
}
```


<a id="org61dc06e"></a>

### Requirements

<div class="requirement">

-   You must complete the `print_array()` function and properly call it in the `main()` function when the user provides option 1.

-   The format output of the array must match the following example:

    { 0 1 2 3 4 }

-   The entire array must be printed with the appropriate values in the right order
-   Numbers must be space separated
-   There must be a leading and trailing brace, `{` and `}` respectively, which are also space separated.
-   Following printing the array, an additional new line should be printed
-   The array and the length of the array must be passed as arguments to print<sub>array</sub>.

Sample output below:

    $> ./manipulator 
    Enter the length:
    5
    Enter 5 numbers (space separated):
    0 1 2 3 4 5
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    ERROR: Invalid number. Choose again.
    
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    1
    { 0 1 2 3 4 }
    
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    0

</div>


<a id="org62c8fc0"></a>

## Part 3: Reversing the array (25 points)

In this part of the lab you are going to complete the function to reverse the array.


<a id="orgd8230f6"></a>

### Requirements

<div class="requirement">

-   You must complete the `reverse_array()` function and properly call it in the main() function when the user provides the option 2.

-   You must print the array following the completion of the operation. (Hint: you've already written this operation, don't do something twice!)

Sample output:

    $> ./manipulator 
    Enter the length:
    5
    Enter 5 numbers (space seperated):
    0 1 2 3 4
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    1
    { 0 1 2 3 4 }
    
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    2
    { 4 3 2 1 0 }
    
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    0

</div>


<a id="org04163cd"></a>

## Part 4: Randomizing the array (25 points)


<a id="org7146619"></a>

### Random Integers

A random number generator is built into the C standard library: `random()` returns a random integer between 0 and 2<sup>64</sup>-1. For the purposes of this lab, you'll only need random numbers in the range of the length of the array. To properly bound the random numbers being generated, use the modulo operator:

```c
int r = random() % bound; //produce random number between 0 and bound-1
```


<a id="orgf9f9e55"></a>

### Seeding Random Numbers

Additionally, as you test your program, you will likely want your random numbers to be deterministic, that is, be random but predictably random so that each run you get the same random numbers. To make things easier for you, I have seeded the random number generator like so:

```c
srandom(1845); //seed random number generator with seed 1845
```


<a id="orga35975d"></a>

### Pseudocode for array randomization

Randomizing an array of values is equivalent for performing random swaps for items in the array. It is a bit overkill, but here is some pseudocode to use:

    foreach index i in array:
      choose a random index j
      swap array[i] with array[j]


<a id="org9163908"></a>

### Requirements

<div class="requirement">

-   You must complete the `randomize_array()` and properly call it when the user selects option 3.

-   Your randomization routine must execute as the pseudocode above.

-   You must use the proper seed for testing purposes, as described above.

Here is some sample output:

    $> ./manipulator 
    Enter the length:
    5
    Enter 5 numbers (space seperated):
    0 1 2 3 4
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    3
    { 3 1 2 4 0 }
    
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    0

</div>


<a id="org34e2f2b"></a>

## Part 5: Sorting the array (10 points)

The last part of the assignment is the trickiest. You must write a array sort routine. You may or may not have had experience writing sort functions before, so I encourage you to look at insertion sort on wikipedia, which is perhaps the easiest to implement.


<a id="org17b7989"></a>

### Requirements

<div class="requirement">

-   You must complete the `sort_array()` and properly call it when the user selects option 4.

-   Any sorting function you implement will receive credit.

Here is some sample output:

    $> ./manipulator 
    Enter the length:
    5   
    Enter 5 numbers (space seperated):
    0 1 2 3 4
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    3
    { 3 1 2 4 0 }
    
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    4
    { 0 1 2 3 4 }
    
    Choose an operation:
    (0) : exit
    (1) : print array
    (2) : reverse array
    (3) : randomize array
    (4) : sort array
    0

</div>
