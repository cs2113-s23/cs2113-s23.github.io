---
layout: default
permalink: lab/1
---

*View the videos for this lab on this [youtube playlist](https://youtube.com/playlist?list=PLnVRBITSZMSPA0Y8NlhpRIP-2Em68oXQ8)*

# Basic Programming in C

<!--
* Github Classroom Link: [https://classroom.github.com/a/b8SmbGuo](https://classroom.github.com/a/b8SmbGuo) -->

* Github Classroom Link: [https://classroom.github.com/a/b8SmbGuo](https://classroom.github.com/a/b8SmbGuo)

Remember to [clone that repo from git to your local setup in VSCode](/guides/vscode-git) so that you can edit your file on your laptop and then submit it from VSCode like we did in lab-0.


## Part 1: From Java to C

In the `part1` directory, you'll find three Java programs, `prob1.java`, `prob2.java`, and `prob3.java`. Rewrite each of these programs into C programs that have the same output (either to `stdout` or `stderr`) for any given input. 

> Part 1 Requirements:
> Your submission in the `part1` directory must include
> * `prob1.c` : C version of `prob1.java`
> * `prob2.c` : C version of `prob2.java`
> * `prob3.c` : C version of `prob3.java`


## Part 2: Fix the errors in the following C programs

In the `part2` directory, you'; find three C programs `whoops.c`, `mybad.c` and `thatsgottahurt.c`. Each of these programs fail to compile and/or have logic errors. Fix the programming errors such that the compile and run correctly based on the intention of the programmer! *Note you shouldn't change the primary functionality of the programs, just the compilation/programming errors*


> Part 2 Requirements:
> Your submission in the `part2` directory should include corrected version of the following files
> * `whoops.c` 
> * `mybad.c` 
> * `thatsgottahurt.c` 
>
> Additionally, add a file to the `part2` directory called `fixes.md` where you describe how each of the programs compilation error was corrected.

## Part 3: Hour Glass

In `part3` directory. complete the program that prints hourglass patterns of `*`'s of different widths. Here's some sample output, and, yes, you should print the row numbers. They will help you debug :)

```
$ ./hourglass 
Enter hourglass width:
6
****** 6
 ****  4
  **   2
  **   2
 ****  4
****** 6
```

```
$ ./hourglass 
Enter hourglass width:
7
******* 7
 *****  5
  ***   3
   *    1
  ***   3
 *****  5
******* 7
```

```
$ ./hourglass 
Enter hourglass width:
30
****************************** 30
 ****************************  28
  **************************   26
   ************************    24
    **********************     22
     ********************      20
      ******************       18
       ****************        16
        **************         14
         ************          12
          **********           10
           ********            8
            ******             6
             ****              4
              **               2
              **               2
             ****              4
            ******             6
           ********            8
          **********           10
         ************          12
        **************         14
       ****************        16
      ******************       18
     ********************      20
    **********************     22
   ************************    24
  **************************   26
 ****************************  28
****************************** 30
```



