---
layout: toc
permalink: /c/0
title: C Basics
---

*View all the videos from this unit a [single playlist on youtube](https://www.youtube.com/playlist?list=PLnVRBITSZMSOoHlGwzgsPAbuSj41qmz_c)*

# C Basics and I/O

## C Programming and Unix 

C is an incredibly important programming language, particularly for systems level programming that's because C is a low level language, much closer to the hardware and Operating System then say Java, which we'll discuss in more detail later in the semester. In fact, almost all modern operating systems are written in C including Unix, Linux, Mac OSX, and even Windows.

The C programming language, itself, was developed for the purpose of writing the original Unix operating system (the precursor to Linux and OSX). It shouldn't be that surprising then, that if you want to learn how to write programs that interact with the Unix system directly, then those programs must be written in C. And, in so doing, the act of learning to program in C will illuminate key parts of the Unix system.

In many ways, you can view C as the lingua franca of programming; it's the language that every competent programmer should be able to program, even a little bit in. The syntax and programming constructs of C are present in all modern programming languages, and the concepts behind managing memory, data structures, and the like, underpin modern programming. Humorously, while nearly all programmers know how to program in C, most try to avoid doing so because the same power that C provides as a "low level" language is what makes it finicky and difficult to deal with.

But it's really, really, really important for **you** to know C because the programming concepts are so fundamental to how you'll think about programming going forward, and if you desire to be a professional software developer or engineer ---- you've got to know C! More so, you'll need it in your later classes ;-)


<a id="org2ea46af"></a>

## C Programming Preliminaries

First: **YOU kinda ALREADY KNOW C!** That's because you've been programming in Java and the Java syntax and C syntax are super similar. That's because Java "stole" C style syntax. But Java is a more advanced program language (in more than just the object oriented sense), so there are more than a few things that Java does that C does not; however many are the same. For example:

-   Conditionals: Use `if` and `else` with the same syntax
-   Loops: Use `while` and `for` loops with the same syntax
-   Basic Types: Use `int`, `float`, `double`, `char`
-   Variable Declaration: You declare what each variable is
-   Functions/Methods: You describe a functions input/output
-   Logical operators: `==` `<=` `>=` `<` `>` `&&` `||` and `!` all work the same way

The big differences between C and Java:

-   No objects or advanced types: C does not have advanced types built in, this includes `string`. Instead, strings are null terminated arrays of `char`s. But you can create more advanced data types like structs, but they also have slightly different properties.

-   No function overloading: Even functions with different type declarations, that is, take different types of input and return different types, cannot share the same name. Only the last declaration will be used.

-   All functions are pass-by-value: In Java, items are also passed by value, that is the value of the reference to an object, but some interpret this as a pass-by-reference system. That means in Java, when you pass an object to a method, that method can directly manipulate that object. In C, instead, when you pass a variable to a function, it *always* passes by value. You can have a notion of pass-by-reference by using pointers, but it's subtly different than how it works in Java. 

-   Compiles to assembly: Compiled C code produces a binary, raw assembly code that the computer can run. In Java, you compile down to byte-code that the Java Virtual Machine (JVM) runs. The byte code runs on the JVM, which interprets into into raw assembly code, that then runs on the computer. 

-  No builtin boolean types: just use integer values of 0 for false, and everything is true. 

While clearly, the two programming languages, Java and C, are different, they are actually have a lot of similarities. But, as you'll see later, when we transition to Java, the object oriented model is a true advancement on C. I hope, though, you'll see the bones of C within the Java object model. 


<a id="org4bdc5e8"></a>

## Hello World

When learning any programming language, you always start with the "Hello World" program. The way in which your program "speaks" says a lot about the syntax and structure of programming in that language. Below is the "Hello World" program for Java and C, for comparison.

<div class="side-by-side">
<div class="side-by-side-a">

```java
/*HelloWorld.java*/
public class HelloWorld{
    public static void main(String args[]){
        System.out.println("Hello World");
    }
}
```

</div>
<div class="side-by-side-b">

```c
/*helloworld.c*/
#include <stdio.h>

// Hello World in C
int main(int argc, char * argv[]){
  printf("Hello World\n");
  return 0;
}
```
</div>
</div>

While in Java (since everything is an Object), the `main` function is wrapped within the `HelloWorld` class. But focusing on the `main` functions function declarations, they both take in a list of arguments. The C `main` function, though, returns an integer. This is the exit value of the program. On success, a C program returns 0. On error, it returns anything other than 0. 

Additionally, in C you have a compiler directive `#include` which functions much like the `import` statement in Java. In the Java program, we get access to `System` and its member stream `out` implicitly (although, we could `import java.util.System` explicitly). In this case the `#include <stdio.h>` tells the compiler to include the Standard Input and Output library, which provides the programmer access to the `printf` function for printing "Hello World". The `.h` in `stdio.h` refers to a **h**eader file, which functions like a `package` in Java to group function and data declarations together for ease of use. 




<a id="org1dc7924"></a>

## Compiling a C program

The compilation process for C is very similar to that of Java as both are compiled programs. The gold-standard C compiler is `gcc`, the <a href="https://www.gnu.org/">gnu</a> C compiler. For example, to compile `helloworld.c`, we do the following in our shell.


    #> gcc helloworld.c

Which will produce an executable, binary file called `a.out` (by tradition), which we can run by including a `./` in front.

    #> ./a.out
    Hello World

<font color="gray">Aside ... The reason you need to include `./` is to tell the shell not to look up the program named `a.out` on the path, but rather run the one here in this directory. The path is where the shell looks for programs to run, like `ls` or `cat`---which are also written in c---and are typically stored in `/bin` directory. You can type `which cat` or `which ls` to see where they exist on the path, if your interested. You can also `echo $PATH` in your terminal to see where binary files are looked up.</font><br>

Having a program named `a.out` is not very specific, so instead, you can specify an output file using the `-o` option.

    #> gcc helloworld.c -o helloworld
    #> ./helloworld
    Hello World

There are more advanced compilation techniques that we will cover later, such as including multiple files, compiling to object files, and using pre-compiler directives. 

<font color="gray">Note that on repl.it and other engines, the `clang` compiler is used. This operates just like `gcc` and on some systems is even symlinked to `clang` (that means they are the same thing!). While I'll use `gcc` in the demos, when you run your code on repl.it, it will actually run `clang`.</font><br><br> 

## No JVM

Note that unlike Java, we can run the compiled program directly. That is, you don't have to do something like

    #> javac HelloWorld.java
    #> java HelloWorld

That's because there is no JVM. The output of the compiler is a binary, executable program that runs directly on the CPU without an intermediary. This means that C runs much, much faster than Java. 

This also means that for every new computer with a different architecture, you have to recompile your code. In Java, you can compile your file to a class, in bytecode, and ship that out. Any JVM can run it. <font color="red">This is why your C code must compile and run on the class cluster to receive credit.</font><br>

But the JVM itself, is written in C, so it needs to be compiled for each computer---only once! And that's the power of JVM. Interpreted languages like Python also use this model. 

<a id="org39e1a47"></a>

## Includes

The process of including libraries in your program has a lot of similarities to that of Java imports. Note that all C libraries end in `.h`, unlike C++. Here are some common libraries you will may  want to include in your C program:

-   `stdlib.h` : The C standard library, contains many useful utilities, and is generally included in all programs.
-   `stdio.h` : The standard I/O library, contains utilities for reading and writing from files and file streams, and is generally included in all programs.
-   `unistd.h` : The Unix standard library, contains utilities for interacting with the unix system, such as system calls
-   `sys/types.h` : System types library, contains the definitions for the base types and structures of the unix system.
-   `string.h` : String library, contains utilities for handling C strings.
-   `ctype.h` : Character library, contains utilities for handing char conversions
-   `math.h` : Math library, contains basic math utility functions.
-   `stdbool.h` : provides boolean constants of `true` and `false`

When you put a `#include <header.h>` in your program, the compiler will search for that header in its header search path. The most common location is in `/usr/include`. However, if you place your header file in quotes:

    #include "header.h"

The compiler will look in the local directory for the file and not the search path. This will become important when you  develop larger, multi-file programs.


## Where is the C documentation? The C "standard" and the `man` pages!

From working with Java, you are probably familiar with the Oracle Java SE documentation (that's the "Standard Edition"). For example, the latest version of the Java docs can be found here. 

https://docs.oracle.com/en/java/javase/15/docs/api/index.html

While there are many places where the functionality of C is written down, unlike Java, it's not "owned" by a single corporation so there are no unified documentation as it may be system or compiler dependent. There are instead standards for how a C program should be behave, so called defined behavior. 

The C standards are defined by the year in which they were defined. For example, the latest standard is C18 (codified in 2018). The biggest advance in C is the C99 standard (codified in 1999) and is the primary version we will use in this class. (Prior to C99 the standard was called ANSI C, which you may of heard of.)

The document for C and its standard libraries are encoded into unix systems through the manual pages (or "man pages"). In particular, they are in section 3 of the man pages. On any unix system, when the man pages are installed, you can type `man` followed by the function in question, like

```bash
man 3 fopen
```

And it will open a manual for you to read  (type `q` to quit). All the man page s are also available online for free (of course!).

<a id="org1f38971"></a>

## Control Flow
  
The same control flow you find in Java is present in C. This includes if/else statements.

```c
if( condition1 ){
  //do something if condition1 is true
}else if (condition2){
  //do something if condition1 is false and condition2 is true
}else{
  //do this if both condition1 and condition2 are false
}
```

While loops:

```c
while( condition ){
  //run this until the condition is not true
}
```

And, for loops:

```c
//run init at the start
for ( init; condition; iteration ){
  //run until condition is false preforming iteration on each loop.
}
```

As a final note, C also has case-switch statements, like Java, which is handy when dealing with large if/else blocks. Note that it only works for data types where equality works, that is the `==` operator. 

```c
switch(i){
    case 0 :
        //do something if i==0
        break; //<-- must have a break otherwise next case runs!
    case 1 :
        //do something if i==1
        break; //<-- must have a break otherwise next case runs!
    case 2 :
        //do something if i==2
        break; //<-- must have a break otherwise next case runs!
    //...
    default: //optional
        //do something if nothing else applies
        //don't need a break here because you fall out of the case statement
}
```

<a id="org66553c9"></a>

## What is true and false in C

*Everything but 0 is true!*

C does not have a boolean type, that is, a basic type that explicitly defines true and false. Instead, true and false are defined for each type where 0 or `NULL` is always false and everything else is true. All basic types can be used as a condition on its own. For example, this is a common form of writing an infinite loop:

```c
while(1){
  //loop forever!
}
```

That means that the output of boolean operations returns an integer value, and you can write code like the below. 

```
int a = (1 > 0);
if (a == 1) {
    print("The comparison was true!\n");
}else {
    printf("The comparison was false!\n");
}
```

The evaluation of the expression `(1 > 0)` returns a 1 as it is true, and thus, we compare can save that output to a variable, `a`. 

### Quick aside about `NULL` it's just 0 of a different type 

`NULL` is 0 and 0 is `NULL` and `NULL` is logical false. You can use all of them interchangeable.

```c
if (0 == NULL) {
  printf("They are the same?!?\n")
}
```

However, they are sort of, not quite exactly the same. The constant value 0 is of type `int` (or sometimes `long` or `long long` depending on context), while the constant value of NULL is of type `void *`. That is, `NULL` is used when zero-ing out a pointer value while 0 is used for zeroing out a number value. They are both actually zero, though. 


### Adding bools to C use 

While many C programmers (in which I include myself) prefer to use integer values for true/and false, there are others prefer to use the reserve words of `true` and `false` to refer to logical true and false. If you're one of those programmers, there is an easy way to add boolean constants to the language using compiler directives.

Consider the following program:

```c
#include <stdio.h>

#define true 1
#define false 0

int main() {

  if(true) {
    printf("Look it's true!\n");
  }

  if(!false) {
    printf("Look it's *not* false!\n");
  }
}
```

The `#define` tells the compiler to replace the word `true` with 1 and `false` with 0 anywhere it appears in the code. That means when your program, you get to write `true` and `false`, but when it compiles, it produces the code.


```c
#include <stdio.h>

int main(){

  if(1){
    printf("Look it's true!\n");
  }

  if(!0){
    printf("Look it's *not* false!\n");
  }
}
```

Rather than have to add the compiler directives to programs throughout, there's a library header `stdbool.h` that you can include to give you a the functionality of bool types.

```c
#include <stdio.h>
#include <stdbool.h>

int main(){

  bool b1 = 1 > 2; //stores 0
  bool b2 = 1 < 2; //stores 1

  if(b1 || b2) {
    printf("b1 OR b2 is true.\n");
  }

  if(b1 && b2) {
    printf("b1 and b2 is true\n");
  }

  if(b1 == false) {
    printf("b1 is false\n");
  }

  if(b2 == true) {
    printf("b2 is true\n");
  }
}
```

But the secret is that `bool` is really just an integer value of 1 or 0. Feel free to use `stdbool.h` if you want, but I will likely not use it in these notes. 

(*Quick aside: I wasn't entirely truthful above ... in fact `bool` is just a short hand for data type of `_Bool` which is a special data type in the c99 standard that can _only_ be 0 or 1 and is stored in a 1-byte wide unsigned integer.*) 


<a id="org32eb72e"></a>

# Functions and Header files

## Function Declaration and Definition
Functions in C follow the same basic construction as in Java. They have input parameters of a specified type and return a value of a specified type. For example, we can easily define a function that adds two integers together, returning the result, and then call that function in `main()`.

```c
//func1.c

//defining and declaring add()
int add(int a, int b) {
    return a + b;
}

int main() {
    int c = add(10, 20); //calling add()
}
```

The above program, say called `func1.c` will compile and run without any errors. However, if we swap the order of `main()` and `add()` in the program `func2.c`, we get a compilation error:

```c
//func2.c

int main() {
    int c = add(10, 20); //calling add()
}

//defining and declaring add() **after** main!
int add(int a, int b){
    return a + b;
}

```
```
func2.c:5:11: error: implicit declaration of function 'add' is invalid in C99 [-Werror,-Wimplicit-function-declaration]
  int c = add(10, 20);
          ^
1 error generated.
```

This error is saying, that the function `add` is invalid because it is not part of the C99 standard. Or to put another way, it has not been defined nor declared yet. The compiler doesn't know what to do.  BUT --- it's right there in the program?! Yes, it is, but it's been defined/declared after when it is called. This causes an error. 

Instead, we can first **declare** the function by stating its name, input types, and output type before `main()`. This doesn't include its **definition**, the actually code to be run when the function is called. The declaration is a promise that at some point I will define the function, but for now, you can trust me that it will exist at some point in the future. 

We can now write a version of this program where we first declare our functions and later define them, this time adding a few more functions. 

```c
//func3.c

//declaring add(), sub(), mult()
int add(int a, int b); 
int sub(int a, int b);
int mult(int a, int b);

int main() {
    int c = mult(3, add(10, sub(20, 5))); 
}

//definitions
int add(int a, int b) {
    return a + b;
}

int sub(int a, int b) {
    return add(a, -b);
}

int mult(int a, int b) {
    int c = 0;
    for(int i = 0; i < b; i++) {
        c = add(a, c)
    }
    return c;
}
```

## Headers and Source Files

Looking at the example from above, you may have a desire to separate the declarations, definitions and call-sites of the functions in even more. We only have three functions, but what if we had a full library worth of operations? It would be great to be able to explicitly include these functions for calling.

The way to do this in C is via header files. These are C files that contain basic declarations of functions and data types and are indicated with the suffix a `.h`. For example, we can create a header file `arithmetic.h` from the `add`, `sub`, and `mult` functions from above:

```c

//this says only include the below if __ARITHMETIC_H not previously defined
#ifndef __ARITHMETIC_H

//define now 
#define __ARITHMETIC_H

//arithmetic.h
int add(int a, int b); 
int sub(int a, int b);
int mult(int a, int b);

#endif
```

And then we can create a source file with those functions definitions `arithmetic.c`

```c
//arithmetic.c

//then #include says add the header information here
#include "arithmetic.h"

int add(int a, int b) {
    return a + b;
}

int sub(int a, int b) {
    return add(a, -b);
}

int mult(int a, int b) {
    int c = 0;
    for(int i = 0; i < b; i++) {
        c = add(a, c)
    }
    return c;
}
```

And finally, we have a main source file `main.c` with the `main()` function:

```c
//main.c

#include "arithmetic.h"

int main() {
    int c = mult(3, add(10, sub(20, 5))); 
}
```

A couple of important new compiler-directive syntax to note
* `#include "arithemtic.h"` : As before the `#include` tells the compiler to take everything from another file and add it to this one. Previously, we used the `#include <file.h>` syntax with the `<>`. The `<>` indicate to look for the file on the `INCLUDEPATH`, which is an environment variable that lists a bunch of directories where to find header files. The quotes `""` indicate to look in the local directory.
* `#ifndef __ARITHMETIC_H` ... `#endif` : The `#ifndef` is a compiler directive that works as an if statement, testing if `__ARITHMETIC_H` has previously NOT been defined, and if so, then include everything up to the `#endif`. We need this because we cannot declare the same function twice, which is an error in the same way you cannot declare a variable twice. Note that we have two `#include "arithmetic.h"` -- one in `main.c` and one in `arithmetic.c` -- so the including of the function declarations will only happen once because once we we enter the `#ifndef` block, the first thing we do is `#define __ARITHMETIC_H`. 

Now, we're ready to compile our program, which we can do by compiling everything all at once, like so:

```
gcc arithmetic.c main.c -o main
```

Note we do not have to include `arithmetic.h` because it will be `#include`d during compilation.

## Multistep compilations

This is all great, but it seems a bit silly that we have to always have to *recompile* the library `arithmetic.c` for every program we want. It would be much more convenient to compile `arithmetic.c` separately, to an intermediate state, and then use that to finish compiling other programs. And of course, there is a way to do this, but first let's take a closer look at compilation.

Compilation, as we've been describing it, is actually a two step process. First we **compile** the C code into assembly code, and then second, we **assemble** the assembly code into a binary executable that can run natively on the machine. This process can be done for any file, really. 

For example, let's compile `helloworld.c` in multiple steps. First compilation:

```
gcc -c helloworld.c -o helloworld.o
```

The `-c` says "compile" and the output is an **object file** `helloworld.o` that contains the compiled assembly from the C code. You can think of this file very much like a `.class` file in Java. 

Next, we can assemble the `helloworld.o` into an executable

```
gcc helloworld.o -o helloworld
```

This is done in the same way. In fact, when you call `gcc`, it does both steps, but just doesn't tell you it's doing it. 

Now to put it all together, we can create an object file from `arithmetic.c` that we can then use to assemble a bunch of other files.

```
gcc -c arithmetic.c -o arithmetic.o
gcc main.c arithmetic.o -o main
gcc foo.c arithmetic.o -o foo
gcc bar.c arithmetic.o -o bar
```

And so on. 



# Format Input and Output


<a id="org6d8f473"></a>

## `printf()` and `scanf()`

The way output is performed in Java in C also has parallels. In Java you can use a scanner to read from an input stream, and in C you can also do the same.

One big difference is that C has a notion of formatted output. You may have noticed that in Java you can just do something like `System.out.println("the value of i is " + i)` and it properly formats `i` into the output. You have to do a bit more work in C to get that output. More precisely, you have to format print/scan (i.e., `printf`, `scanf`) each variable into the output/input. It's more like scanning in Java, where you have to specify the type of data your expecting. 

Consider the two programs below.

<div class="side-by-side">
<div class="side-by-side-a">

```java
/*EnterNumber.java*/
import java.util.Scanner;

public class EnterNumber{

    public static void main(String args[]){
        int num;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter a number:");
        num = sc.nextInt();
        System.out.println("You entered " + num);
    }
}
```

</div>
<div class="side-by-side-b">

```c
/*enternumber.c*/
#include <stdio.h>

int main(int argc, char * argv[]) {
  int num;

  printf("Enter a number\n");
  scanf("%d", &num); //use &num to store 
                     //at the address of num

  printf("You entered %d\n", num);
}
```

</div>
</div>

The two programs above both ask the user to provide a number, and then print out that number. In Java, we can scan from input from terminal via `System.in` and write our output to the terminal via `System.out`. In the C parlance, we use the terms `stdin` (or *standard in*) and `stdout` (or *standard out*) to refer to the terminal output stream and input stream, respectively.  

To access `stdin` and `stdout` for reading/writing In C, we use **format printing** and **format scanning** to do the same basic I/O. The format tells C what kind of input or output to expect. In the above program `enternumber.c`, the `scanf` asks for a `%d` from `stdin`, which is the special format for a number, and similar, the `printf` has a `%d` format to indicate that `num` should be printed as a number to `stdout`.

There are other format options. For example, you can use `%f` to request a `float` or `double`.

```c
/* getpi.c */

#include <stdio.h>
int main(int argc, char * argv[]) {

  float pi;
  printf("Enter pi:\n");
  scanf("%f", &pi);

  printf("Mmmm, pi: %f\n", pi);
}
```

And you can use modifiers on the format to change the number of decimals to print. `%0.2f` says print a float with only 2 trailing decimals. You can also include multiple formats, and the order of the formats match the additional arguments

```c
int a = 10, b = 12;
float f = 3.14;
printf("An int:%d a float:%f and another int:%d", a, f, b);
//              |          |                  |   |  |  |
//              |          |                  `---|--|--'
//              |          `----------------------|--'    
//              `---------------------------------'
```

There are a number of different formats available, and you can read the manual pages for `printf` and `scanf` to get more detail.

    man 3 printf
    man 3 scanf

You have to use the 3 in the manual command because there exists other forms of these functions, namely for Bash programming, and you need to look in section 3 of the manual for C standard library manuals. You can also find these man pages on die.net --- [printf](https://linux.die.net/man/3/printf) and [scanf](https://linux.die.net/man/3/scanf).

For this class, we will mostly use the following format characters frequently:

-   `%d` : format integer
-   `%u` : format an unsigned integer
-   `%f` : format float/double
-   `%x` : format hexadecimal
-   `%p` : format a pointer
-   `%s` : format string
-   `%c` : format a char
-   `%ld` : format a long
-   `%lu`: format an unsigned long
-   `%%` : print a % symbol


<a id="orgad7d99e"></a>

## The `FILE *` and opening files

The last part of the standard input output library we haven't explored is reading/writing from files. Although, you've done this already in the form of the standard files, e.g., standard input and output, we have demonstrated how to open, read, write, and close other files that may exist on the file system.

All the file stream functions and types are defined in the header file `stdio.h`, so you have to include that. The man page for is [here](https://linux.die.net/man/3/stdio).

Open files in the standard C library are referred to as file streams, and have the type:

```c
FILE * stream;
```

and we open a file using `fopen()` which has the following prototype:

```c
FILE * fopen(const char * path, const char * mode);
```

The first argument `path` is a string storing the file system path of the file to open, and `mode` describes the settings of the file. For example:

```c
FILE * stream = fopen("funny_quotes.txt", "w");
```

will open a file in the current directory called "funny_quotes.txt" with write mode. We'll discuss the modes more shortly.

File streams, as pointers (that's what the `*` stands for, more on this later), are actually dynamically allocated, and they must be deallocated or closed. The function that closes a file stream is `fclose()`

```c
fclose(stream);
```


<a id="org4a33fcd"></a>

## File Modes

The mode of the file describes how to open and use the file. For example, you can open a file for reading, writing, append mode. You can start reading/writing from the start or end of the file. You can truncate the file when opening removing all the previous data. From the man page, here are the options:

    The argument mode points to a string beginning with one of the following sequences  (possibly  followed
    by additional characters, as described below):
    
    r      Open text file for reading.  The stream is positioned at the beginning of the file.
    
    r+     Open for reading and writing.  The stream is positioned at the beginning of the file.
    
    w      Truncate  file  to zero length or create text file for writing.  The stream is positioned at the
           beginning of the file.
    
    w+     Open for reading and writing.  The file is created if it does not exist, otherwise it  is  trun‐
           cated.  The stream is positioned at the beginning of the file.
    
    a      Open  for  appending  (writing  at end of file).  The file is created if it does not exist.  The
           stream is positioned at the end of the file.
    
    a+     Open for reading and appending (writing at end of file).  The file is created  if  it  does  not
           exist.   The  initial  file  position for reading is at the beginning of the file, but output is
           always appended to the end of the file.

One key thing to notice from the modes is that any mode string with a "+" is for both reading and writing, but using "r" vs. "w" has different consequences for if the file already exists. With "r" a file will never be truncated if it exists, which means its contents will be deleted, but "w" will always truncate if it exits. However, "r" mode will not create the file if it doesn't exist while "w" will. Finally, append mode with "a" is a special case of "w" that doesn't truncate with all writes occurring at the end of the file.

As you can also see in the man page description, the file stream is described as having a "position" which refers to where within the file we read/write from. When you read a byte, you move the position forward in the file. In later lessons, we will look into how to manipulate the stream more directly. For now


## Format Output with `fprintf()`

Let's start where we always start with understanding a new input/output system, hello world!

```c
/*hello_fopen.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char * argv[]){

  FILE * stream = fopen("helloworld.txt", "w");

  fprintf(stream, "Hello World!\n");

  fclose(stream);
}
```

    aviv@saddleback: demo $ ./hello_fopen 
    aviv@saddleback: demo $ cat helloworld.txt
    Hello World!
    aviv@saddleback: demo $ ./hello_fopen 
    aviv@saddleback: demo $ cat helloworld.txt
    Hello World!

The program opens a new stream with the write mode at the path "helloworld.txt" and prints to the stream, "Hello World!\n". When we execute the program, the file helloworld.txt is created if it doesn't exist, and if it does it is truncated. After printing to it, we can read it with `cat`, and we see that in fact "Hello World!" is in the file. If we run the program again, we still have "Hello World!" in the file, just one, and that's because the second time we run the program, the file exists, so it is truncated. The previous "Hello World!" is removed and we write "Hello World!".

However if we wanted to open the file in a different mode, say append, we get a different result:

```c
/*hello_append.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char * argv[]){

  FILE * stream = fopen("helloworld.txt", "a");    //<--

  fprintf(stream, "Hello World!\n");

  fclose(stream);
}
```

    aviv@saddleback: demo $ ./hello_append 
    aviv@saddleback: demo $ ./hello_append 
    aviv@saddleback: demo $ ./hello_append 
    aviv@saddleback: demo $ cat helloworld.txt 
    Hello World!
    Hello World!
    Hello World!
    Hello World!

The original "Hello World!" remains, and the additional "Hello World!"'s are append to the end of the file. Printing "Hello World!" does not require a format, but `fprintf()` can format just like `printf()`, but to a file stream. 

<!-- For example, consider this simple program that prints information about the command line arguments. -->

<!-- ```c -->
<!-- #include <stdio.h> -->
<!-- #include <stdlib.h> -->

<!-- int main(int argc, char * argv[]){ -->

<!--   int i; -->

<!--   FILE * stream = fopen("cmd.txt", "w"); -->

<!--   for(i=0;i<argc;i++){ -->
<!--     fprintf(stream, "argv[%d] = %s\n", i, argv[i]); -->
<!--   } -->

<!--   fclose(stream); -->
<!--   return 0; -->
<!-- } -->
<!-- ``` -->

<!--     aviv@saddleback: demo $ ./command_info  -->
<!--     aviv@saddleback: demo $ cat cmd.txt -->
<!--     argv[0] = ./command_info -->
<!--     aviv@saddleback: demo $ ./command_info a b c d e f -->
<!--     aviv@saddleback: demo $ cat cmd.txt -->
<!--     argv[0] = ./command_info -->
<!--     argv[1] = a -->
<!--     argv[2] = b -->
<!--     argv[3] = c -->
<!--     argv[4] = d -->
<!--     argv[5] = e -->
<!--     argv[6] = f -->


<a id="org6a2c5ee"></a>

## Format Input with `fscanf()`

Just as we can format print to files, we can format read, or scan, from a file. `fscanf()` is just like `scanf()`, except it takes a file stream as the first argument. For example, consider a data file with following entries:

    aviv@saddleback: demo $ cat file.dat
    Aviv Adam 10 20 50 3.141592 yes
    Pepin Joni 15 21 53 2.781 no

We can write a format to read this data in with `fscanf()`:

```c
int main(int argc, char * argv){

  FILE * stream = fopen("file.dat", "r");

  char fname[1024], lname[1024], yesno[4];
  int a, b, c;
  float f;

  while ( fscanf(stream,
                 "%s %s %d %d %d %f %s",
                 fname, lname, &a, &b, &c, &f, yesno) != EOF){

    printf("First Name: %s\n", fname);
    printf( "Last Name: %s\n", lname);
    printf("         a: %d\n", a);
    printf("         b: %d\n", b);
    printf("         b: %d\n", c);
    printf("         f: %f\n", f);
    printf("     yesno: %s\n", yesno);
    printf("\n");
  }

  fclose(stream);
  return 0;
}

```

Note that we are sepecifying the size of each of the three character arrays that will store strings.

And when we run it, we see that we scan each line at a time:

    aviv@saddleback: demo $ ./scan_file 
    First Name: Aviv
    Last Name: Adam
             a: 10
             b: 20
             b: 50
             f: 3.141592
         yesno: yes
    
    First Name: Pepin
    Last Name: Joni
             a: 15
             b: 21
             b: 53
             f: 2.781000
         yesno: no

One thing you should notice from the scanning loop is that we compare to `EOF`, which is special value for "End of File." The end of the file is encoded in such a way that you can compare against it. When scanning and you reach end of the file, EOF is returned, which can be detected and used to break the loop.

Another item to note is that scanning with `fscanf()` is the same as that with `scanf()`, and is white space driven to separate different values to scan. Also, "%s" reads a word, as separated by white space, and does not read the whole line.


<a id="orgdf8a742"></a>
## The standard file streams

C uses the abstraction of file pointers to define the standard file streams, which are defined variables for you to use in your code. These include `stdin`, `stdout`, and `stderr`. We can do format input and output with these file streams just like any file stream we opened using `fopen()`.

By default, `printf()` prints to `stdout`, but you can alternative write to any file stream. To do so, you use the `fprintf()` function, which acts just like `printf()`, except you explicitly state which file stream you wish to print. Similarly, there is a `fscanf()` function for format reading from files other than `stdin`.

```c
printf("Hello World\n"); //prints implicitly to standard out
fprintf(stdout, "Hello World\n"); //print explicitly to standard out
fprintf(stderr, "ERROR: World coming to an endline!\n"); //print to standard error
```

## Error Checking in C: `errno` and `strerror()`

Nearly all functions in the standard library return a value, typically an integer value of some type that is either true of false. This is how you check to see if an error occurred.

For example, check out the [man page](https://linux.die.net/man/3/fopen) for the `fopen`, and refer to the return value section:

    RETURN VALUE
           Upon successful completion fopen(), fdopen() and freopen() return a FILE pointer.  Otherwise,  NULL  is
           returned and errno is set to indicate the error.

If the file was not opened successfully, we could can check for that, like below.

```c

if ((stream = fopen("DOESNOTEXIST.txt", "r")) == NULL) {
   fprintf(stderr, "ERROR ... \n");
}
```

An error could occur, like above, most likely for the file not existing, but it could also be because of something else --- how can you tell?

In Java, the program would throw an exception, a special object that encodes the error. That exception could be caught, printed, and perhaps handled gracefully. For example, that could be notifying the user that you opened a file that does not exist --- you bozo! 

In C, there is no such thing as an exception (at least not in the same way). As a way to communicate about errors that occur nested within a stack of function calls, there is a special global integer that gets set `errno` (error number) that encapsulates the kind of error. 

While you could just print out that error number, that's not entirely human readable. There is a set of defined constants for each error number you could compare against (check out the [man page](https://linux.die.net/man/3/errno) for `errno` for them all!), that would be quite a lot of work. Instead, of course, there are library calls that do this for you and provide a helpful error message.

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

int main() {
  FILE * stream;
  if((stream = fopen("DOESNOTEXIST.txt","r")) == NULL) {
    fprintf(stderr,"fopen: %s\n", strerror(errno));
    //                      ^     ^
    //                      |     '- returns a string for the given error
    //                      '--- %s says format print a string

    exit(1); //exit/return from this program with an error
  }
}
```

Running this, you'd get the following output if the file truly does not exist
```
$ ./a.out
fopen: No such file or directory
```

And if you just didn't have permission to read the file, but it did exist, you'd get a different error message.

```
$ ./doesnotexist 
fopen: Permission denied
```

(Aside: Note that this program exits with status 1 to indicate something went wrong. A program exits with status 0 when it succeeds.)

## `stderr` and `perror`

The above example exits when it encountered the error, but we don't always need to nor want to exit the program when an error occurs. Sometimes it makes sense just to let the user know something bad happened and move on.

But then we have a problem. *How does the user differentiate between the programs output and reports of errors?* 

This is particularly problematic in the unix environment where pipelines are common for passing the output of one program (written to `stdout`) as the input to another program (read from `stdin`). 

To differentiate from error reporting and output, we use the third standard file stream: `stderr` or standard error. It works just like the other two standard file streams and can be used in the same way with `fprintf`. 

Now the error checking on our `fopen` is as follows:

```c
    fprintf(stderr, "fopen: %s\n", strerror(errno));
```

At this point, you are probably like...that's way, way too much typing for error checking, especially having to write `fprintf` and `stderr` over and over again. And you're right! So there's a library function `perror` that does just the above in one call.

```c
   perror("fopen");
```

You can still use `fprintf(stderr,...)` to add additional details to the error, but the *reason* for the error will be well formatted and printed for you in a straightforward call. 

## Final note on error checking!

**While I do not always show error checking below, it is important that your code does error checking.**

Do as I say, not as I do ;)





