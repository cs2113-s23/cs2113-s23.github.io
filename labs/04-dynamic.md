---
layout: default
permalink: lab/4
---


<a id="org54ded3e"></a>

*View the videos for this lab on [youtube](https://youtube.com/playlist?list=PLnVRBITSZMSOuHoOPUfxkMrhjFhKVr8qo)*

# Lab 4: Dynamic Memory Management

<!-- * Github Classroom Link: [https://classroom.github.com/a/D9EPvBex](https://classroom.github.com/a/D9EPvBex) -->
https://classroom.github.com/a/OXUBaieq
* Github Classroom Link: [https://classroom.github.com/a/OXUBaieq](https://classroom.github.com/a/OXUBaieq) 

## Preliminaries

### Development Environment

You should develop using VSCode+SSH.

### Test Script

To help you complete the lab, I have provide a test script that will run basic tests against your program. The script is not designed to be comprehensive, and you will graded based on a larger array of tests. To execute the test script, run it from anywhere within the lab directory.

    ./test.sh

### README file

You are required to fill out the README.md file in your repository describing your programming process as well as answer any related lab questions.

# Part 1: Debugging Memory Errors with Valgrind (30 points)

In this lab, you will be required to dynamically allocate memory in multiple contexts, and you are also required to ensure that your program does not have memory leaks or memory violations. Fortunately, there exists a wonderful debugging program which can capture and help you debug both, Valgrind.


<a id="org472c4a2"></a>

## Memory Leaks

A memory leak occurs when you have dynamically allocated memory, using `malloc()` or `calloc()` that you do not free properly. As a result, this memory is lost and can *never* be freed, and thus a **memory leak** occurs. It is vital that memory leaks are plugged because they can cause system wide performance issues as one program begins to hog all the memory, affecting access to the resources for other programs.

To understand a memory leak, let's look at perhaps the most offensive memory leaking program ever written -- **DON'T RUN THIS PROGRAM WITHOUT CAREFUL SUPERVISION**:

```c
#include <stdio.h>
#include <stdlib.h>

#include <unistd.h>
#include <sys/types.h>

int main() {

  while(1) { 
    malloc(1024); //memory leak!
    sleep(0.5); //slow down the leak
  }

}
```

At the `malloc()`, new memory is allocated, but it is never assigned to a pointer. Thus there is no way to keep track of the memory and no way to deallocate it, thus we have a memory leak. This program is even more terrible in that it **loops forever** leaking memory. If run, it will eventually slow down and cripple your computer. **DON'T RUN THIS PROGRAM WITHOUT CAREFUL SUPERVISION**.

Normally, memory leaks are less offensive. Let's look at a more common memory leak.

```c
/*memleak_example.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char * argv[]) {

  int * a = malloc(sizeof(int));

  *a = 10;  

  printf("%d\n", *a);

  a = calloc(3, sizeof(int));
  a[0] = 10;
  a[1] = 20;
  a[2] = 30;

  printf("%d %d %d\n", a[0], a[1], a[2]);
}

```

This is a simple program that uses an integer pointer `a` in two allocations. First, it allocates a single integer and assigns the value `10` to the allocated memory. Next, it uses `a` to reference an array of integers of length 3. It prints out the values for both cases. Here is some program output and compilation. (The `-g` is to compile with debugging information, which will become important later.)

    #> gcc memleak_example.c -g -o memleak_example
    #> ./memleak_example 
    10
    10 20 30

On it's face, there doesn't seem to be anything wrong with this program in terms of its intended output. It compiles without errors, and it runs as intended. Yet, this program is wrong, and there is a memory leak in it.

Upon the second allocation and assignment to `a`, the previous allocation is not freed. The assignment of the second allocation from `calloc()` will overwrite the previous pointer value, which is used to reference the initial allocation, the one by `malloc()`. As a result, the previous pointer value and the memory it referenced is lost and cannot be freed; a classic memory leak.

Ok, so we know what a memory leak is and how to recognize one by reading code, but that's hard. Why can't the compiler or something figure this out for us? Turns out that this is **not** something that a compiler can easily check for. The only foolproof way to determine if a program has a memory leak is to run it and see what happens.

The valgrind debugger is exactly the tool designed to that. It will run your program and track the memory allocations and checks at the end if all allocated memory has been freed. If not, some memory was lost, then it will generate a warning. Let's look at the valgrind output of running the above program.

    #> valgrind ./memleak_example
    ==30134== Memcheck, a memory error detector
    ==30134== Copyright (C) 2002-2011, and GNU GPL'd, by Julian Seward et al.
    ==30134== Using Valgrind-3.7.0 and LibVEX; rerun with -h for copyright info
    ==30134== Command: ./memleak_example
    ==30134== 
    10
    10 20 30
    ==30134== 
    ==30134== HEAP SUMMARY:
    ==30134==     in use at exit: 16 bytes in 2 blocks
    ==30134==   total heap usage: 2 allocs, 0 frees, 16 bytes allocated
    ==30134== 
    ==30134== LEAK SUMMARY:
    ==30134==    definitely lost: 16 bytes in 2 blocks
    ==30134==    indirectly lost: 0 bytes in 0 blocks
    ==30134==      possibly lost: 0 bytes in 0 blocks
    ==30134==    still reachable: 0 bytes in 0 blocks
    ==30134==         suppressed: 0 bytes in 0 blocks
    ==30134== Rerun with --leak-check=full to see details of leaked memory
    ==30134== 
    ==30134== For counts of detected and suppressed errors, rerun with: -v
    ==30134== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 2 from 2)

Check out the `LEAK SUMMARY` section, and you find that 16 bytes were "definitely" lost. Let's rerun the valgrind with the `--leak-check` option set to "full" to see more details, which additonally prints the `HEAP SUMMARY`

    #> valgrind --leak-check=full ./memleak_example
    (...)
    
    ==30148== 4 bytes in 1 blocks are definitely lost in loss record 1 of 2
    ==30148==    at 0x4C2B6CD: malloc (in /usr/lib/valgrind/vgpreload_memcheck-amd64-linux.so)
    ==30148==    by 0x4005F7: main (memleak_example.c:6)
    ==30148== 
    ==30148== 12 bytes in 1 blocks are definitely lost in loss record 2 of 2
    ==30148==    at 0x4C29DB4: calloc (in /usr/lib/valgrind/vgpreload_memcheck-amd64-linux.so)
    ==30148==    by 0x400636: main (memleak_example.c:12)

It lists the two allocatoins. The first call to `malloc()` allocated 4 bytes, the size of an integer. The second allocation, allocated 3 integers, or 12-bytes, with `calloc()`. With this information, the programmer can track down the memory leak and fix it, which is exactly what you'll do for this task.


<a id="org34d4d3b"></a>

### Task 1 (15 points)

<div class="requirement">

Change into the `valgrind` directory in your lab folder. Compile and execute `memleak.c`. Verify the output and try and understand the program.

Answer the following questions in your worksheet:

1.  Run valgrind on the `memleak` program, how many bytes does it say have been definitely lost?

2.  What line does valgrind indicate the memory leak has occurred?

3.  Describe the memory leak.

4.  Try and fix the memory leak and verify your fix with valgrind. Describe how you fixed the memory leak.

You will submit your fixed `memleak.c` program in your submission, and we will verify that you fixed the memory leak.

</div>


<a id="orgef78496"></a>

## Memory Violations

Memory leaks are not just the only kind of memory errors that valgrind can detect, it can also detect memory violations. A **memory violation** is when you access memory that you shouldn't or access memory prior to it being initialized.

Let's look at really simple example of this, printing an uninitialized value.

```c
int a;
printf("%d\n", a);
```

The problem with this program is clear; we're printing out the value of `a` without having previously assigned to it. This error can be detected by the compiler with the `-Wall` option:

    #> gcc -Wall memviolation_simple.c
    memviolation_simple.c:7:18: warning: variable 'a' is uninitialized when used here [-Wuninitialized]
      printf("%d\n", a);

But other memory violations are harder to recognize particular those involving arrays. Let's look at the program below. You should be able to spot the error (hint: look at the for loop conditional).

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char * argv[]) {

  int i, *a;

  a = calloc(10, sizeof(int));

  for(i = 0; i <= 10; i++){
    a[i] = i;
  }
  for(i = 0; i <= 10; i++){
    printf("%d\n", a[i]);
  }

}
```

However, if we were to compile and just run this program, you may not recognize that anything is wrong:

    #> ./memviolation_array 
    0
    1
    2
    3
    4
    5
    6
    7
    8
    9
    10

No errors are reported and the numbers up to 10 are printed, but we know that we are actually writing *out-of-bounds* in our array, and we shouldn't do that! Valgrind, fortunately, can detect such errors:

    #> valgrind ./memviolation_array 
    ==30588== Memcheck, a memory error detector
    ==30588== Copyright (C) 2002-2011, and GNU GPL'd, by Julian Seward et al.
    ==30588== Using Valgrind-3.7.0 and LibVEX; rerun with -h for copyright info
    ==30588== Command: ./memviolation_array
    ==30588== 
    ==30588== Invalid write of size 4
    ==30588==    at 0x4005D8: main (in /home/scs/aviv/git/ic221/current/lab/04/stu/examples/memviolation_array)
    ==30588==  Address 0x51f2068 is 0 bytes after a block of size 40 alloc'd
    ==30588==    at 0x4C29DB4: calloc (in /usr/lib/valgrind/vgpreload_memcheck-amd64-linux.so)
    ==30588==    by 0x4005B4: main (in /home/scs/aviv/git/ic221/current/lab/04/stu/examples/memviolation_array)
    ==30588== 
    0
    1
    2
    3
    4
    5
    6
    7
    8
    9
    ==30588== Invalid read of size 4
    ==30588==    at 0x40060F: main (in /home/scs/aviv/git/ic221/current/lab/04/stu/examples/memviolation_array)
    ==30588==  Address 0x51f2068 is 0 bytes after a block of size 40 alloc'd
    ==30588==    at 0x4C29DB4: calloc (in /usr/lib/valgrind/vgpreload_memcheck-amd64-linux.so)
    ==30588==    by 0x4005B4: main (in /home/scs/aviv/git/ic221/current/lab/04/stu/examples/memviolation_array)
    ==30588== 
    10
    ==30588== 
    ==30588== HEAP SUMMARY:
    ==30588==     in use at exit: 40 bytes in 1 blocks
    ==30588==   total heap usage: 1 allocs, 0 frees, 40 bytes allocated
    ==30588== 
    ==30588== LEAK SUMMARY:
    ==30588==    definitely lost: 40 bytes in 1 blocks
    ==30588==    indirectly lost: 0 bytes in 0 blocks
    ==30588==      possibly lost: 0 bytes in 0 blocks
    ==30588==    still reachable: 0 bytes in 0 blocks
    ==30588==         suppressed: 0 bytes in 0 blocks
    ==30588== Rerun with --leak-check=full to see details of leaked memory
    ==30588== 
    ==30588== For counts of detected and suppressed errors, rerun with: -v
    ==30588== ERROR SUMMARY: 2 errors from 2 contexts (suppressed: 2 from 2)

If you notice in the execution output, there is an "Invalid read of size 4" occurring when `array[10]` is indexed and printed to the screen. This is a rather simple example, but invalid reads and writes and other kinds of memory violations can cause all sorts of problems in your program, and they should be investigated and fixed when possible.


<a id="org5c8af4f"></a>

### Task 2 (15 points)

<div class="requirement">

Change into the `valgrind` directory in your lab folder. Compile and execute the `memviolation.c` program. Complete the following tasks and answer the questions in your worksheet.

1.  Describe the output and exeuction of the program. Does it seem to be consistent?

2.  Run the program under valgrind, identify the line of code that is causing the memory violation and its input.

3.  Debug the memory violation and describe the programming bug.

4.  Fix the memory violation and verify your fix with valgrind.

Your submission will include the fixed `memviolation.c` program.

</div>


<a id="org74d92c9"></a>

# Part 2: Implementing `simplefs` (70 points)

In this part of the lab you will program a simple filesystem structure based on linked lists. To help you debug and test your file system, we have provided you with a shell interface for the file with three commands:

1.  `touch` : create a file if it doesn't exist or update it's timestamp if it does
2.  `rm` : remove a file by name
3.  `ls` : list all the current files in the file system

The files will be managed using a linked list, so you must also be able to iterate through a link list, remove items from the list, and add items to the end of the list. Below we outline some additional tools you'll need to complete the lab


<a id="org6a9f310"></a>

## The Filesystem Structures

The structures of the file system are described in the header file, `filesystem.h`:

```c
/**
 * Structure for a file
 **/
struct file {

  char * fname; //name of file
  time_t last; //last modified

  struct file * next_file;

};

//typedef to make it easier
typedef struct file file_t;


/**
 * Structure for a diretory
 **/
typedef struct {

  file_t * flist; //pointer to first file_t in the linked list or the
                  //head of the list

  int nfiles;    //number of files currently stored

} dir_t;  
```

The `file_t` structure represents a file and has three fields. The first is `char *` which references the string for the name of the file. The second field stores a timestamp for the last time a file was `touch`ed, and finally, the last field is a pointer to the next file in the file list. Recall that files will be stored using a linked list, just as you've seen before in prior classes.

The directory structure, `dir_t`, stores two values. The first is a pointer to the start of the file list, and the second is an integer tracking the number of files in the file system.


<a id="org9445dd3"></a>

## Linked List Review

A linked list a data structure by which nodes manage links to other nodes. For example, in the file system structure, the linked list will look like so

    .----------.    .-----------.    .-----------.
    | dir_t    | .->| file_t    | .->| file_t    | .-> NULL
    |          | |  | next_file-+-'  | next_file-+-'
    | flist----+-'  '-----------'    '-----------'
    | nfiles=2 |   
    '----------'    

The `dir_t` stores the head of the list, which references the first file. There are two files in the list above, and the file references `NULL` to indicate the end of the list. If a new item is appended to the list, then the last file will reference it, and the newly appended file will reference `NULL`.

One edge condition that is important to consider is when the file list is empty, the directory will reference `NULL` and `nfiles` should be 0.

    .----------.    
    | dir_t    | .-> NULL
    |          | | 
    | flist----+-' 
    | nfiles=0 |   
    '----------'    

If you forget about this edge condition, you will SEGFAULT &#x2014; 100% guaranteed.


<a id="org02af5bb"></a>

## Allocate a file and its filename with touch

While the starter code allocates the initial directory structure you will need to allocate the files using `malloc()`. The function that will be your `touch` implementation, which has the prototype:

```c
void  simplefs_touch(dir_t * dir, char *fname);
```

It will pass in a pointer to the directory, which stores the head of the list of files. At this point, one of two things could be true: the file with the name `fname` exists, in which case you will update its timestamp (see next section), or the file with the name `fname` does not exist, in which case you will need to create a new file with that name.

To allocate a new file, use `malloc()` or `calloc()` like so:

```c
file_t * new_file = malloc(sizeof(file_t));
```

which will allocate memory on the heap large enough to store a `file_t`. Next we need to set its fields. First we need to set the name of the file. You might be tempted to do this:

```c
new_file->fname = fname;
```

Where `fname` is the one passed to `simplefs_touch`, but there's a problem here. That `fname`, the one passed as an argument, may not persist. That is, it might not be allocated on the heap (in fact, it will not be). You need to allocate space for the string and copy it over. To help, I recommend you look up the fuction `strdup()` from the string library.


<a id="org3548bb8"></a>

## Timestamps and Time formats

One the new things in this lab is that you will be using timestamps. A timestamp in Unix is just a number, a `long`, that counts the number of seconds since the epoch, Jan 1st 1970. The files in your file system must store the last modification time, the time since touch was last called.

To retrieve the current time, use the `time()` command:

```c
file->last = time(NULL); //get the current time
```

Every time a file is `touch`ed, you should update the timestamp for that file. To print the timestamp, which is a number, in a human readable format, there is a nice library function, `ctime()`, which takes a pointer to a timestamp and returns a string representation.

```c
time_t ts = time(NULL);     //get the current time
printf("%s\n", ctime(&ts)); //convert the current time
//                   ^
//                   '--- Don't forget to pass the address of the timestamp!
```


<a id="orgb6b8d6c"></a>

## Removing a file from the list

One of the more challenging tasks in this lab will be implementing `rm` which requires you to iterate through the list of files, identify the file to be remove based on its name, and then remove that file while maintaing the consistency of the list. This will require careful pointer manipulation. For example consider this scenario below:

    
         .-----------.       .-----------.       .-----------.
     .-> | fname     |   .-> | fname     |   .-> | fname     |   .->
    -'   | next_file +---'   | next_file +---'   | next_file +---'
         '-----------'       '-----------'       '-----------'
                          (deleting this file)

The file to be deleted is between two other files. In this case we must realign the pointer of the previous file to the one being deleted to reference the file after the deleted file, like below:

                         .-----------------.
         .-----------.   |   xXXXXXXXXXXXx |     .-----------.
     .-> | fname     |   |   x *****     x '---> | fname     |   .->
    -'   | next_file +---'   x ********  x       | next_file +---'
         '-----------'       xXXXXXXXXXXXx       '-----------'
             (need to have
                   next_file point to the deleted files's next file)


<a id="orgc2832ba"></a>

## Deallocation and Memory Leaks

As you work through this lab, be sure to keep track of all the components that you allocated on the heap. You must deallocate them properly. To simplify this process, the code is organized such that there is a single function for freeing all the memory:

```c
void simplefs_rmfile(file_t *file) { 
  //TODO: Complete file dealocation 
  //      (note this is called by simplefs_rmdir() to deallocate individual files)

}
```

This function is called by `simplefs_rmdir()`, and should be one of the first things you implement. Use valgrind to check your memory violations.


## Compiling your programs with `gcc` and `make`

We have provided you with a Makefile to ease the compilation burden for this lab. To compile a given executable, simply type `make`.

    make

Before submitting, you should clean your `src` directory by typing:

    make clean

which will remove any lingering executables and other undesirable files. In a later lab, we will review make files for compilation.


<a id="org3c679bd"></a>

## Requirements:

<div class="requirement">

-   You must complete all the functions left remaining in the `filesystem.c` source code, which includes:
    -   `simplefs_touch()`
    -   `simplefs_rm()`
    -   `simplfs_rmfile()`

-   Your program must integrate with the shell program for testing purposes. The shell program has the following commands:
    
    -   `ls` : list files
    -   `rm name` : remove file of the name
    -   `touch name` : create a file called name if it doesn't exist or update the timestamp if it does
    
    **Do not edit the shell program**, it is provided for you, but you will interact with your file system implementation through it.

-   Your program must not have any memory leaks or violations.

-   Use the `make` command to do your compilation because this is somewhat complicated program.

-   **(EXTRA CREDIT: 5 points)** Complete two additional functions in `filesystem.c` to list the files in different sorted order:
    -   `simplefs_ls_sorttime()` : list file sorted by oldest to newest
    -   `simplefs_ls_sortname()` : sort files alphabetically by name

-   Sample Output:
    ```
    aviv@saddleback: simplefs $ make 
    gcc -Wall -g -c -o filesystem.o filesystem.c
    gcc -Wall -g -c -o shell.o shell.c
    gcc -o shell shell.o filesystem.o -lreadline -lncurses 
    aviv@saddleback: simplefs $ ./shell 
    simplefs > ls
    simplefs > touch a b c 
    simplefs > ls
      a	Mon Feb  2 18:09:13 2015
      b	Mon Feb  2 18:09:13 2015
      c	Mon Feb  2 18:09:13 2015
    simplefs > touch b
    simplefs > ls
      a	Mon Feb  2 18:09:13 2015
      b	Mon Feb  2 18:09:20 2015
      c	Mon Feb  2 18:09:13 2015
    simplefs > touch c
    simplefs > ls
      a	Mon Feb  2 18:09:13 2015
      b	Mon Feb  2 18:09:20 2015
      c	Mon Feb  2 18:09:22 2015
    simplefs > touch d
    simplefs > ls
      a	Mon Feb  2 18:09:13 2015
      b	Mon Feb  2 18:09:20 2015
      c	Mon Feb  2 18:09:22 2015
      d	Mon Feb  2 18:09:26 2015
    simplefs > rm c
    simplefs > ls
      a	Mon Feb  2 18:09:13 2015
      b	Mon Feb  2 18:09:20 2015
      d	Mon Feb  2 18:09:26 2015
    simplefs > touch go navy
    simplefs > ls
      a	Mon Feb  2 18:09:13 2015
      b	Mon Feb  2 18:09:20 2015
      d	Mon Feb  2 18:09:26 2015
      go	Mon Feb  2 18:09:32 2015
      navy	Mon Feb  2 18:09:32 2015
    simplefs > rm a b d
    simplefs > ls
      go	Mon Feb  2 18:09:32 2015
      navy	Mon Feb  2 18:09:32 2015
    simplefs > touch a b d
    simplefs > ls
      go	Mon Feb  2 18:09:32 2015
      navy	Mon Feb  2 18:09:32 2015
      a	Mon Feb  2 18:09:43 2015
      b	Mon Feb  2 18:09:43 2015
      d	Mon Feb  2 18:09:43 2015
    simplefs > 
    ```
</div>
