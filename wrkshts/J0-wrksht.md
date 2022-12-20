---
layout: worksheet
permalink: /worksheet/j0
showsolution: False
---

# Worksheet: J0

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.

Download the [UnitTestExample.java](worksheet/UnitTestExample)

## Setup

In order to compile your solutions, you'll need to have `gcc` installed. However, this semester we will all be using the same version of `gcc` on the same operating system, that is, your accounts through SEAS at `ubuntu-vlab01.seas.gwu.edu`. So, instead of having you install `gcc` on your local machine now, we'll lay some groundwork for what will be covered in lab this week in terms of setting up an environment to run your C code on this SEAS cluster.

For now, either open any programming text editor and create a blank file called `work0.c` (or whatever you wish to call it), or use the starter files you downloaded from the link above. There is where you will test your solutions to this worksheet. Once you're ready to compile and run some C code, you'll need to 1) copy that file over to the SEAS cluster and 2) log in to the cluster to access it and compile/run it. Here are the two relevant terminal commands:

`scp work0.c kinga@ubuntu-vlab01.seas.gwu.edu:/home/ead/kinga/work0.c`
will copy a file called `work0.c` from the current terminal directory (which you may have to `cd` to depending on where you saved your file) to the SEAS cluster. This step will require you to enter your password.

Once you copy your file with the above command (which stands for "secure copy") to the SEAS cluster, you can connect to the cluster using your terminal with the following command (which will also ask for your password):
`ssh kinga@ubuntu-vlab01.seas.gwu.edu`

Once there, you can compile-and-run your C file that you just copied over a minute ago. To exit the remote connection to the SEAS cluster, type the `logout` command.

All this `scp`ing and `ssh`-ing will get old, very fast. In lab this week we'll provide a much more efficient way to develop your code on the SEAS cluster; feel free to check out Lab 0 if you finish this worksheet early!


## Questions


### q1
Write a program called `colors.c` that prints out the following rainbow of colors:

   ```
Here is a rainbow:
.
.
.
red
orange
yellow
green
blue
purple
```

Add the program to your repo. [Note: in the file you downloaded from github classroom, the solution to this problem is given for free to get you started.]

#### s
```c
//colors.c

#include <stdio.h>

int main() {
    printf("Here is a rainbow:\n");
    printf(".\n");
    printf(".\n");
    printf(".\n");
    printf("red\n");
    printf("orange\n");
    printf("yellow\n");
    printf("green\n");
    printf("blue\n");
    printf("purple\n");
}
```

### q

Compile the program above with `gcc`. 
* What gcc command did you use?
* Did you have any errors? If so, note them and how you fixed them. 
* If you had no errors, introduce one (like removing a `;`) and note the error message. 

#### s

* `gcc colors.c -o colors`
* Possible errors could be that the `\` needed to be escaped `\\` to actually print `\`
* UNIQUE TO YOU

### q

The following program should not compile... fix it and explain why.

```c   
int main() {
  printf("Why oh why does this program fail to compile?!?!\n");
}
```

#### s
    Failed to include `#inlude <stdio.h>`, this is required for `printf()`

### q

We were expecting the following output

```
They're not equal :-| 
They're not equal :-| 
They're not equal :-| 
They're not equal :-| 
They're not equal :-| 
They're equal :-) 
They're not equal :-| 
They're not equal :-| 
They're not equal :-| 
They're not equal :-| 
```

But it's not working :( ... Can you fix the program below to get it to work?
  
```c
int main() {
  int a = 10;
  int b = 5;
  while(a > 0) {
    if(a = b) {
      printf("They're equal :-) \n");
    }else {
      printf("They're not equal :-| \n");
    }
    a -= 1;
  }
}
```

Describe what was wrong?

#### s
`a = b` is an assignment, not an equality check. Since `a = b` is assignment, the value of the assignment is returned, and since it's not 0, it's true, so the statement `They're equal :-)` always print. It should have been `a == b`.

### q

What value(s) is/are `true` in C? What value(s) is/are `false` in C? 

#### s

Values that are not 0 are true. There are many types of 0, all false. For example, integer 0, character `\0`, and floating point `NULL`. 


### q
Convert the following code snippet into a function declaration and definition with proper formatting.

```c
int minus(int a, int b){return a-b;}
```

#### s
```c
int minus(int, int);
//..some code, like main()

int minus(int a, int b) {
  return a - b;
}
    
```
    

### q
The following three programs will not compile when trying
  
```
gcc main.c one.c two.c
```

Fix the issues. The programs are below:
    
```c
//funcs.h
void printOne();
void printTwo();
```
```c
   //one.c
   void printOne() {
     printf("one\n");
   }
```
```c
   //two.c
   void printTwo() {
     printf("two\n");
   }
```
```c
   //main.c

   int main() {
    for(int i = 0; i < 10; i++){
      if(i % 2) {
        printOne();
      }else {
        printTwo();
      }
    }
   }
```

#### s
Missing the `#include` statements
```c
     //main.c
     #include "funcs.h"
```
Note that it needs to be in quotes `"funcs.h"`

### q

Below are Java print statements. Write the C equivalent print statement.

```java
int a = 5;
int b = 2;
float c = 4.4;
System.out.println("a = " + a + " b=" + b + " c=" + c);
```

#### s
```c
//main.c
int a = 5;
int b = 2;
float c = 4.4;
printf("a = %d b=%d c=%f\n", a, b, c);
```

### q
What is the differences between `fopen()` file mode `w` and `w+`?


#### s
* `w` opens in write mode, and will create the file if it doesn't exist 
* `w+` open a file for reading and writing mode, and will create the file if it doesn't exist

### q

The C program below doesn’t write anything to a file.

```c
int main() {
  FILE * stream = fopen("helloworld.txt", "r");
  fprintf(stream, "Hello World!\n");
  fclose(stream);
}
```
Fix the program and describe the error(s). 

#### s
The file needs to be open in write mode.
```c
FILE * stream = fopen("helloworld.txt", "w"); //<--
```

### q
    
What is the output of this C program?

```c
int main() {
   float f = 3.14;
   int n = 10;
   printf("%d %f\n", f, n);
}
```
Was it what you expected or not? Try and describe the process by which the output was achieved.

#### s
The output is `3 10.0` because the float `f` is converted to an integer, rounded down to `3` and the integer `n` is converted to a float `3.0` for printing. That's because we used `%d` and `%f`.

### q
Consider the format directive `%.3g`, use `man 3 printf` to describe what the output would be if the input was `3.141592`
    
#### s
The `%g` format formats a floating point number, like `%f`. The `.3` says to only include three significant digits (the . is ignored in `%g`), and so that means `3.15` prints (three sig digits).

### q
You're opening a file, and you get an error!?! Provide **two** preferred ways to report the error to `stderr`, as in ... 

```c
if(fopen( /* ... */) == NULL) {
  // WHAT GOES HERE?!
}
```

#### s
You could use `fprintf()`
```c
fprintf(stderr, "fopen: ERROR Cannot open file\n"); 
```
Or use `perror()`
```c
perror("fopen");
```

### q
Fix and describe the error in the code below.

```c
printf("Enter a number: ");

int num;
scanf("%d", num);
    
printf("You entered %d\n", num);
```

#### s
It's missing an `&` to indicate to store the scanned value at the *address of* `num`
```c
scanf("%d", &num);
```

### q
Fix and describe the error in the code below.
    
```c
printf("Enter a number: ");

int num;
scanf("%f", &num);
    
printf("You entered %d\n", num);
```

#### s
```c
scanf("%d", &num);
``` 



