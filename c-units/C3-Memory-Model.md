---
layout: toc
permalink: /c/3
title: C Memory Model
---

*View all the videos from this unit a [single playlist on youtube](https://youtube.com/playlist?list=PLnVRBITSZMSPIx22PQz_rBfbKMjK4ApuX)*

# Memory Model

So far in programming C, we haven't given a lot of thought to the variables we declare and what it actually means to declare a variable of a given type. Recall that in C the notion of a type and the amount of memory to store that type are strongly linked. For the basic types we've looked at so far, here are their memory requirements:

-   `int` : integer number : 4-bytes
-   `short` : integer number : 2-bytes
-   `long` : integer number : 8-bytes
-   `char` : character : 1-byte
-   `float` : floating point number : 4-bytes
-   `double` : floating point number : 8-bytes
-   `void *` : pointers : 8-bytes on (64 bit machines)

But, what does it mean for a type to require memory, and where does that memory come from and how is it managed? Understanding the memory model in C is vital to becoming a good programmer because there are situations where you have to use complex memory management to write effective programs. Simple mistakes can lead to programs with mysterious bugs that fail in inexplicable ways.


<a id="orgac8542c"></a>

# Local Memory Allocation on the Stack

When you declare a variable, you are actually stating to C that you need to create space for the data of that variable to exist. Consider a very simple example.

```c
int a = 10;
```

The declaration of the integer `a` will allocate memory for the storage for an integer (4-bytes). We refer to the data stored in memory via the variable `a`.

**Memory allocation** refers to the process by which the program makes "space" for the storage of data. When you declare a variable of a type, enough memory is allocation locally to store data of that type. The allocation is local, occurring within the scope of the function, and when that function returns the memory is **deallocated**. This should be intuitive based on your experience with programming so far, you can't reference a variable outside the scope of your function.

However with pointers in C, it's easy to make mistakes where your pointer references a memory address out of scope of the current function or even completely unallocated memory. As an example of a common mistake, consider the simple program below which has a function `plus()` which adds two numbers and returns a memory address for the result value.

```c
int * plus(int a, int b) {
  int c = a + b;
  return &c;    //return a reference to
                //locally declared c
}

int main(int argc, char * argv[]) {
  int * p = plus(1, 2);
  printf("%d\n", *p); //dereference return value
                      //to print an integer 
}
```

The function plus is declared to take two integers as arguments and return a pointer to an integer. Within the body of the function, the two integers are summed together, and the result in stored in `c`, a variable declared locally within the context of the function. The function then returns the memory address of `c`, which is assigned to the pointer value `p` in main.

What's the problem? The memory of `c` is deallocated once the function returns, and now `p` is referencing a memory address which is unallocated. The print statement, which deferences `p`, following the pointer to the memory address, may fail. The above code is **bad**, and you can also follow the reasoning with a stack diagram.

                plus(1,2)         return &c            printf("%d\n",*p)
                 
    (main)         |  (main)         |  (main)            |  (main)
    .---.----.     |  .---.----.     |  .---.----.        |  .---.----.
    | p |  .-+-X   |  | p |  .-+-X   |  | p |  .-+---.    |  | p |  .-+---.
    '---'----'     |  '---'----'     |  '---'----'   |    |  '---'----'   |
                   | -------------   | ------------  |    |               |
                   |  (plus)         |  (plus)       |    |               |
                   |  .---.---.      |  .---.---.    |    |               |
                   |  | a | 1 |      |  | a | 1 |    |    |               |
                   |  |---|---|      |  |---|---|    |    |               |
                   |  | b | 2 |      |  | b | 2 |    |    |               |
                   |  |---|---|      |  |---|---|    |    |               |
                   |  | c | 3 |      |  | c | 3 | <--'    |            X--'
                   |  '---'---'      |  '-------'
                    
                    c exists locally    returning a reference       When p is dereferenced
                    in plus()           to c assined to p           it points to unallocated memory

First, in `main()`, p waits for the result of the call to `plus()`, which set's `c`. Once `plus()` returns, the value of `p` references a variable declared in `plus()`, but all locally declared variables in `plus()` are deallocated once `plus()` returns. That means, by the time the `printf()` is called and `p` is dereferenced, the memory address references unallocated memory, and we cannot guarantee that the data at that memory address will be what we expect.


<a id="org0b3f7e7"></a>

## The Stack

Another term for local memory allocation is **stack allocation** because the way programs track execution across functions is based on a stack. A **stack** is a standard ordered data structure, like a list, that has the property that the *last item* inserted on the stack is the *first item* that is removed. This is often referred to as LIFO data structure, last-in-first-out. A stack has two primary functions:

-   **push** : push an item on to the top of the stack
-   **pop** : pop an item off the top of the stack

The stack's **top** always references the last item pushed onto the stack, and the items below the top are ordered base on when they were pushed on. The most recently pushed items come first. This means when you pop items off the top stack, the next item becomes the next top, which maintains the LIFO principle.

The stack model (last-in-first-out) matches closely the model of function calls and returns during program execution. The act of calling a function is the same as *pushing* the called function execution onto the top of the stack, and, once that function completes, the results are returned *popping* the function off the stack.

Each function is contained within a structure on the stack called a **stack frame**. A stack frame contains all the allocated memory from variable declarations as well as a pointer to the execution point of the calling function, the so called **return pointer**. A very common exploit in computer security is a buffer overflow attack where an attacker overwrite the return pointer such that when the function returns, code chosen by the attacker is executed.

To understand how function calls are modeled in the stack, we have nested function calls under `addonetwo()`, and which ever function is currently executing has the stack frame at the top of the stack and the calling function, where to the current function returns, is the stack frame next from top. When the current function returns, its stack frame is popped off the stack, and the calling function, now the top of the stack, continues executing from the point of function call.

<div class="side-by-side">
<div class="side-by-side-a">

```c

int gettwo() {
   return 2;
} 

int getone() {
  return 1;
}

int addonetwo() {
  int one = getone();
  int two = gettwo();
  return one + two;
}

int main() {
  int a = addonetwo();

}
```

</div>
<div class="side-by-side-b">

    
                program executed     .------ top of stack
                                     v
                    main()
    push main         |      |     main()     |
                      |      '----------------' 
    
                  addonetwo()
                      |
    push addonetwo    |      |   addonetwo()  |
                      |      |     main()     |
                      |      '----------------'
    
                   getone()
                      |
                      |      |     getone()   |
    push getone       |      |   addonetwo()  |
                      |      |     main()     |
                      |      '----------------'
    
                   return 1
    pop               |      |   addonetwo()  |
                      |      |     main()     |
                      |      '----------------'
    
                   gettwo()
                      |
                      |      |     gettwo()   |
    push gettwo       |      |   addonetwo()  |
                      |      |     main()     |
                      |      '----------------'
    
                   return 2
    pop               |      |   addonetwo()  |
                      |      |     main()     |
                      |      '----------------'
    
               return  1 + 2 
    pop               |      |     main()     |
                      |      '----------------'
    
                 program exits

</div></div>

The act of pushing and popping functions onto the stack also affects the memory allocation. By *pushing* a function onto the stack, the computer is actually allocating memory for the function's local variables, and once that function returns, the function and its allocated memory is *popped* off the stack, deallocating it. This is why local declared variables are also called **stacked variables**.

Following the example from before we can now better understand why it fails by adding in the pushes and pops of the stack.

                *PUSH*            *POP*
    
                plus(1,2)         return &c            printf("%d\n",*p)
                 
    (main)         |  (main)         |  (main)            |  (main)
    .---.----.     |  .---.----.     |  .---.----.        |  .---.----.
    | p |  .-+-X   |  | p |  .-+-X   |  | p |  .-+---.    |  | p |  .-+---.
    '---'----'     |  '---'----'     |  '---'----'   |    |  '---'----'   |
                   | -------------   | ------------  |    |               |
                   |  (plus)         |  (plus)       |    |               |
                   |  .---.---.      |  .---.---.    |    |               |
                   |  | a | 1 |      |  | a | 1 |    |    |               |
                   |  |---|---|      |  |---|---|    |    |               |
                   |  | b | 2 |      |  | b | 2 |    |    |               |
                   |  |---|---|      |  |---|---|    |    |               |
                   |  | c | 3 |      |  | c | 3 | <--'    |            X--'
                   |  '---'---'      |  '-------'
                    
                 Pusing plus()           The return of plus()      When p is dereferenced          
                 onto the stack          pops it off the stack     in the print, p now 
                 allocates memory        unallocated all stack     references unallocated
                 for c                   variables including c     memory


<a id="org57cfe3a"></a>

# Global Memory Allocation on the Heap

Just because the sample program with `plus()` from the previous section doesn't work properly when returning a memory reference, it does not mean you cannot write functions that return a memory reference. What is needed is a different allocation procedure for global memory which is not deallocated automatically when functions return and thus remains in scope for the entirety of the program execution.

In fact, you have already seen how to do this in Java when you using the `new` construct. 

If you think about it, the memory cannot have been allocated on the stack because it is memory being returned from a function, the `new` function. As we've seen previously, if a function returns a local reference of a variable declared on the stack, that memory is automatically deallocated when the function returns. Instead, this memory must have been allocated somewhere else, and it is. The `new` function performs a dynamic memory allocation in global memory that is not associated with scope of functions or the stack. It is instead allocating on the **heap**.


<a id="org307726c"></a>

## The heap, `malloc()` and `free()`

The global memory region for a program is called the **heap**, which is a fragmented data structure where new allocations try and fit within unallocated regions. Whenever a program needs to allocate memory globally or in a dynamic way, that memory is allocated on the heap, which is shared across the entire program irrespective of function calls.

In C the `new` function is called `malloc()`, or memory allocator. The `malloc()` function takes the number of bytes to be allocated as its argument, and it returns a pointer to a memory region on the heap of the requested byte-size. Here is a code snippet to allocate memory to store an integer on the heap:

```c
//                           .--- Allocate sizeof(int) number of bytes 
//                           v
 int * p = (int *) malloc(sizeof(int));
//            ^
//            '-- Cast to a integer pointer
```

First, to allocate an integer on the heap, we have to know how **big** an integer is, that is, what size is it, which we learn via `sizeof()` function. Since an `int` is 4 byes in size, `malloc()` will allocate 4 bytes of memory on the heap in which an integer can be stored. `malloc()` then returns the memory address of the newly allocated memory, which is assigned to `p`. Since `malloc()` is a general purpose allocation tool, just allocating bytes which can be used to store data generally, **we have to cast the resulting pointer value to the right type, `int *`. If we don't, the program won't fail, but you will get a compiler warning.**

We can now use `p` like a standard pointer as before; however, once we're done with `p` we have to explicitly deallocate it. **Unlike stack based memory allocations which are implicitly deallocated when functions return, there is no way for C to know when you are done using memory allocated on the heap.** C does not track references, like Java, so it can't perform garbage collection; instead, you, the programmer, must indicate when you're done with the memory by *freeing*. The deallocation function is `free()`, which takes a pointer value as input and "frees" the referenced memory on the heap.

```c
int * p = (int *) malloc(sizeof(int));

//do something with p

free(p); //<-- deallocate p
```

With all of that, we can now complete the `plus()` program to properly return a memory reference to the result.

```c
int * plus(int a, int b) { 
  int *p = (int *) malloc(sizeof(int)); //allocate enough space for 
  *p = a + b;                   //for an integer
  return p;  //return pointer

}

int main(int argc, char * argv[]) {
  int * p = plus(1, 2); //p now references memory on the heap
  printf("%d\n", *p); 
  free(p); //free allocated memory
}
```


<a id="org0601a96"></a>

## Program Layout: Stack vs. Heap

Now that you understand the two different memory allocation procedures, let's zoom out and take a larger look at how memory in programs is managed more generally. Where is the stack? Where is the heap? How do they grow or shrink?

To answer these questions, you first need to think of a program as a memory profile. All information about a program, including the actual binary code and variables all are within the memory layout of a program. When executing, the Operating System will manage that memory layout, and a snapshot of that memory and the current execution point basically defines a program. This allows the operating system to swap in and out programs as needed.

On 64-bit machines, the total available memory addresses are from 0 to 2<sup>64</sup>-1. For a program, the top and bottom of the address space are what is important. We can look at the program's memory layout in diagram form:

    
         2^64-1--->  .----------------------.
    High Addresses   |      Enviroment      |
                     |----------------------|
                     |                      |   Functions and variable are declared
                     |         STACK        |   on the stack.
    base pointer ->  | - - - - - - - - - - -|
                     |           |          |
                     |           v          |
                     :                      :
                     .                      .   The stack grows down into unused space
                     .         Empty        .   while the heap grows up. 
                     .                      .
                     .                      .   (other memory maps do occur here, such 
                     .                      .    as dynamic libraries, and different memory
                     :                      :    alloocat)
                     |           ^          |
                     |           |          |
     break point ->  | - - - - - - - - - - -|   Dynamic memory is declared on the heap
                     |          HEAP        |
                     |                      |
                     |----------------------|
                     |          BSS         |   The compiled binary code is down here as
                     |----------------------|   well as static and initialzed data
                     |          Data        |
                     |----------------------|
    Low Addresses    |          Text        |
          0 ----->   '----------------------'  

At the higher addresses is the stack and at the lower address is the heap. The two memory allocation regions grow into the middle of the address space, which is unused and unallocated. In this way, the two allocations will not interfere with each other. The stack **base pointer** is the current top of the stack, and as functions are called and returned, it will shift appropriately. The **break point** refers to the top of the programs data segment, which contains the heap. As the heap fills up, requirement more space, the break is set to higher addresses.

You should note that this memory layout is **virtual**. From the program's perspective it has access to the entire address range, but in reality, this might not be the case because the program is sharing **physical memory** with other programs, including the operating system. How that process works is a discussion for another class.



# Memory Leaks and Memory Violations

In C, the programmer is responsible for memory management, which includes both the allocation and deallocation of memory. As a result, there are many mistakes that can be made, which is natural considering that all programmers make mistakes. Perhaps the most common mistake is a memory leak, where heap allocated memory is not freed. Consider the following program.

```c
int main(int argc, char * argv[]) {
  int i, * p;

  for(i = 0; i > 100; i++){
    p = (int *) malloc(sizeof(int));
    *p = i;
  }

}
```

At the `malloc`, on line 5, the returned pointer to newly allocated memory is overwriting the previous value of `p`. There is no `free()` occuring, and once the previous pointer value is overwritten, there is no way to free that memory. It is considered *lost*, and the above program has a memory leak. Memory leaks are very bad, and over time, can cause your program to fail.

Another common mistake is dereferencing a **dangling pointer**. A dangling pointer is when a pointer value once referenced allocated memory, but that memory has seen been deallocated. We see an example of this already in the `plus()` program, but it can also occur for heap allocations.

```c
int main(int argc, char * argv[]) {
  int *p = (int *) malloc(sizeof(int));
  //... code
  free(p);
  //... code  
  *p = 10;
}
```

Once `p` has been freed, the memory referenced by the `p`'s value can be reclaimed by other allocations. At the point where `p` is dereferenced for the assignment, it might be the case that you are actually overwriting memory for some other value, and corrupting your program. Once memory is freed, it should never be dereferenced. These kinds of memory violations can lead to the dreaded `SEGFAULT`.

Another, common mistake with memory allocation is a **double free**. The heap allocation functions maintain special data structures so that it is easy to find unallocated memory and reallocate for future `malloc()` calls. If you call `free()` twice on a pointer, you will corrupt that process, result in a `core dump` or some other very scary error.

## Using Valgrind to Detect Memory Leaks and Violations

Memory leaks and violations are pernicious and extremely hard to debug. At times it may appear everything is running correctly, only for later in your code for you to realize that everything is bunk. So it's really important for you to identify and remove memory leaks and violations from your code. 

An important tool in the fight to eradicate memory leaks and violations is `valgrind`. The way `valgrind` works is that it traces your program, tracking each call to `malloc` and `free`, ensuring that there is an associated `free` for every allocated memory. It also tracks all pointer dereferences, checking to make sure that you follow pointers to usable/valid memory (thus ensuring that there isn't a memory violation). 

Let's look at a few examples of using `valgrind` to debug some obvious memory leaks. 

### Debugging a memleak with valgrind

Consider the incorrect code below:

```c
//memleak.c
#include <stdio.h>
#include <stdlib.h>

int main() {

    int * p = malloc(sizeof(int));
    *p = 10;

    printf("*p = %d\n", *p);

    p = malloc(sizeof(int)); //<-- memory leak
    *p = 20;

    printf("*p = %d\n", *p);

    free(p);
}
```

This example is a bit obvious, but it's almost always not this clear why you have a memory leak. So we can turn our attention to `valgrind`, but before, we should compile our program with debugging symbols using `-g`. 

```
gcc -g memleak.c -o memleak
```
This gives `valgrind` access to the exact line of code and variable names to give you more information about what is wrong. After compiling the program, you run it like so:

```
valgrind ./memleak
```

Getting the following output:

```
==3367== Memcheck, a memory error detector
==3367== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==3367== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
==3367== Command: ./memleak
==3367== 
*p=10
*p=20
==3367== 
==3367== HEAP SUMMARY:
==3367==     in use at exit: 4 bytes in 1 blocks
==3367==   total heap usage: 3 allocs, 2 frees, 1,032 bytes allocated
==3367== 
==3367== LEAK SUMMARY:
==3367==    definitely lost: 4 bytes in 1 blocks
==3367==    indirectly lost: 0 bytes in 0 blocks
==3367==      possibly lost: 0 bytes in 0 blocks
==3367==    still reachable: 0 bytes in 0 blocks
==3367==         suppressed: 0 bytes in 0 blocks
==3367== Rerun with --leak-check=full to see details of leaked memory
==3367== 
==3367== For lists of detected and suppressed errors, rerun with: -s
==3367== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)
```

Notice that it clearly says that 4 bytes were **definitely** lost. And it even helpfully tells you to rerun your program with `--leak-check=full` to see details of leaked memory. So let's do that, and we get the additional information:

```
==4310== 4 bytes in 1 blocks are definitely lost in loss record 1 of 1
==4310==    at 0x483B7F3: malloc (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
==4310==    by 0x10919E: main (memleak.c:6)
```

This says: the memory that was lost was allocated using `malloc` on line 6 of our program. Wow! Very specific. Looking at our program we see exactly where that occurred, and this allows us to fix it. Unfortunately, though, it can't tell us where to put the `free`, but it does tell us that we need to do so somewhere.


### Understanding a memory violation using valgrind

To see an example of a memory violation, let's look at another incorrect program.

```c
//memviolation.c
#include <stdio.h>
#include <stdlib.h>

int main() {

    int * p = malloc(sizeof(int));
    *p = 10;

    printf("*p = %d\n", *p);

    free(p);    
    
    *p = 20; //<-- memory violation (accessing invalid memory)

    printf("*p = %d\n", *p); //<-- memory violation (accessing invalid memory)
 
    free(p);//<-- memory violation (double free)
}
```

Notice that we derference and assign to `*p` after freeing `p`, then later we again dereference when printing, and then again, we double free p (it's already been freed!). This is a slew of memory violation, and`valgrind` will identify all of these. Let's look at the output:

```
==5489== Memcheck, a memory error detector
==5489== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==5489== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
==5489== Command: ./memviolation
==5489== 
*p=10
==5489== Invalid write of size 4
==5489==    at 0x1091D6: main (memviolation.c:13)
==5489==  Address 0x4a44040 is 0 bytes inside a block of size 4 free'd
==5489==    at 0x483CA3F: free (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
==5489==    by 0x1091D1: main (memviolation.c:11)
==5489==  Block was alloc'd at
==5489==    at 0x483B7F3: malloc (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
==5489==    by 0x10919E: main (memviolation.c:6)
==5489== 
==5489== Invalid read of size 4
==5489==    at 0x1091E0: main (memviolation.c:15)
==5489==  Address 0x4a44040 is 0 bytes inside a block of size 4 free'd
==5489==    at 0x483CA3F: free (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
==5489==    by 0x1091D1: main (memviolation.c:11)
==5489==  Block was alloc'd at
==5489==    at 0x483B7F3: malloc (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
==5489==    by 0x10919E: main (memviolation.c:6)
==5489== 
*p=20
==5489== Invalid free() / delete / delete[] / realloc()
==5489==    at 0x483CA3F: free (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
==5489==    by 0x109200: main (memviolation.c:17)
==5489==  Address 0x4a44040 is 0 bytes inside a block of size 4 free'd
==5489==    at 0x483CA3F: free (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
==5489==    by 0x1091D1: main (memviolation.c:11)
==5489==  Block was alloc'd at
==5489==    at 0x483B7F3: malloc (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
==5489==    by 0x10919E: main (memviolation.c:6)
==5489== 
==5489== 
==5489== HEAP SUMMARY:
==5489==     in use at exit: 0 bytes in 0 blocks
==5489==   total heap usage: 2 allocs, 3 frees, 1,028 bytes allocated
==5489== 
==5489== All heap blocks were freed -- no leaks are possible
==5489== 
==5489== For lists of detected and suppressed errors, rerun with: -s
==5489== ERROR SUMMARY: 3 errors from 3 contexts (suppressed: 0 from 0)
```

First, notice that at the end it lists 3 errors, which match up to the three violations in the code. Then above, the first of these errors is an invalid write. This occurs when we `*p=20` because `p` is already free. The second error is an invalid read when we use `*p` in our `printf()`, and finally, the third error, is a invalid `free()` when we free `p` for the second time. Again, `valgrind` notes the location in code these events occurred at, which gives us a start on fixing our code, but we still have to reason about *why* there is an error. That's the hard part.


# Dynamic Array Allocation

From the last class, we discuss the standard memory allocation situation where we need to allocate memory on the heap.

<div class="side-by-side">
<div class="side-by-side-a">

```c
int * p = (int *) malloc(sizeof(int));
*p = 10;
```

</div>
<div class="side-by-side-b">

     STACK          HEAP
    .---.----.     .----.
    | p |  .-+---->| 10 |
    '---'----'     '----'

</div>
</div>

On the left, we use `malloc()` to allocate enough memory to store an `int`, and we assign the address of that memory to the integer pointer, `p`. On the right, is the stack diagram of this allocation. The pointer `p` exists on the stack, but it now references the memory on the heap.

We now know, in excruciating detail, that arrays and pointers are the same. This idea extends to the dynamic allocation of arrays. If we have an integer pointer `p` it can point to a single integer, or it can point to the start of a sequence of integers. A sequence of contiguous integers *is* an array. All we need is to allocate enough space to store all those integers, and `malloc()` can do that too.

Consider what's needed to allocate an array of a given size. For example, how many bytes would be needed to allocate an integer array of size 5? There are 4-bytes for each integer, and the array holds 5 integers: 20 bytes. To allocate the array, we just ask `malloc()` to allocate 20 bytes, and cast the result to an `int *`, like below.

<div class="side-by-side">
<div class="side-by-side-a">

```c
int * p = (int *) malloc(5 * sizeof(int));
p[0] = 10;
p[1] = 20;
//...
p[4] = 50;
```

</div>
<div class="side-by-side-b">

     STACK          HEAP
    .---.----.     .----.
    | p |  .-+---->| 10 | p[0]
    '---'----'     |----|
                   | 20 | p[1]
                   |----|
                   :    :  
                   .    .
                   :    :
                   |----|
                   | 50 | p[4]
                   '----'

</div>
</div>

The result of the `malloc()` is 20 bytes of contiguous memory which is referenced by an integer pointer, which is the **same** as an array! We can even use the array indexing method, `[]`, to access and assign to the array.


<a id="org78608e2"></a>

## Array allocation with `calloc()`

Because allocating items as an array is so common, we have a special function for it.

```c
int *p = (int *) calloc(5, sizeof(int));
```

While allocating arrays with `malloc()` is simple and effective, it can be problematic. First off, `malloc()` makes no guarantee that memory allocated will be clean &#x2014; it can be anything. For example, consider this simple program:

```c
//allocate a 20 byte array
int * a = (int *) malloc(20 * sizeof(int));
int i;
for(i = 0; i < 20; i++){
  printf("%d\n", a[i]);
}
```

What is the output of this program? We don't know. It's undefined. The allocated memory from `malloc()` can have any value, usually whatever value the memory used to have if it was previously allocated. If you don't believe me, try running and executing the program a few times, and you'll see that you can get widely different results.

The second problem with using `malloc()` is that it is a multi-purpose allocation tool. It is generally designed to allocate memory of a given size that can be used for both arrays and other data types. This means that to allocate an array of the right size, you have to perform an arithmetic computation, like `20 * sizeof(int)`, which is non-intuitive and reduces the readability of code.

To address these issues, there is a special purpose allocator that is a lot more effective for array allocation. It's `calloc()` or the *counting allocator*. It's usage is as follows.

```c
//  The number of items 
//  to be allocated      --.
//                         |
//                         v
  int * a = (int *) calloc(20, sizeof(int));
//                                  ^
//                                  |      
//                            The size of each item
```

`calloc()` takes two arguments, the number of items needed and the size of each item. For an array, that is the length of the array, and the size is the `sizeof()` the array type, which is `int` in this case. **Not only does `calloc()` make the array allocation more straight forward, it will also zero-out or clean the memory that is allocated**. For example, this program will always print 0's.

```c
//allocate a 20 byte array
int * a = (int *) calloc(20, sizeof(int));
int i;
for(i = 0; i < 20; i++){
  printf("%d\n", a[i]); // 0 b/c calloc zeros out allocated memory
}
```


<a id="orgbcc8dd1"></a>

## Double Pointer Arrays for Advanced Data Types

So far in this lesson we've discussed allocating basic types, but we can also dynamically allocate memory for other types, such as strings and structures. For example, consider the `pair_t` structure below:

```c
typedef struct{
  int left;
  int right;
} pair_t;
```

Suppose we want an array of such structures, then we can use dynamic allocation using `calloc()` to allocate array of `pair_t`'s just like we did to generate an array of `int`'s.

```c
pair_t * pairs = (pair_t *) calloc(10, sizeof(pair_t));

pairs[0].left  =  2;  //do assignment
pairs[0].right = 10;
```

As you can see, once the array is generated, we can access each individual `pair_t` using array indexing, and the array was allocated with enough memory for 10 `pair_t` strucutes we use the `.` operator to access each item.

This allocation is fine for many circumstances, but it can pose some subtle problems in certain situations. Suppose we wanted to keep track of which pairs have been set and which have not? Could we just look at the array of pairs and know this? We can't because `calloc()` will zero out all the pairs and we can't tell difference between a pair just stores to zeros and one that was not set. Another problem could occur if we want to be memory efficient. What if we only want to allocate the full `pair_t` struct as needed?

Adding an extra layer of redirection makes such tasks much easier. Essentially, we wish to construct an array of pointers to the desired type, or a pointer to a pointer, a **double pointer**. Instead of having an array of `pair_t`'s, we have an array of pointers to `pair_t`'s. Then we can know if the `pair_t` is set because either the pointer will be `NULL` or it will reference a `pair_t`, and we can allocate new `pair_t`'s as needed. The allocation is as follows:

```c
//     .-- Double Pointer -.          array of pair_t pointers
//     |                   |                      |
//     v                   v                      v
pair_t ** pairs = (pair_t **) calloc(10, sizeof(pair_t *));

pairs[0] = malloc(sizeof(pair_t)); //allocate memory for a new pair_t

pairs[0]->left  =  2; //do assignment
pairs[0]->right = 10;
```

While at first this might seem like a funky and unnecessary way of allocating structure types, but it is quite common in practice. It is often the case that you will need to store and reference a number of structure types, the amount of them is unknown ahead of time. Managing all this data requires careful programming and memory management. Keeping track of what has been allocated, what has been freed, and what resources are newly available are the key to designing good programs.


<a id="org91b25d1"></a>

## Deallocating Double Pointers

A common mistake when dealing with double pointers is poor deallocation. Let's extend the above example by modularizing the process of creating the array of pair pointers and the addition of a new pair into functions to simplify the code. This might look like below.

```c

pair_t ** new_pairs() {
    pair_t ** pairs = (pair_t **) calloc(10, sizeof(pair_t *));
    return pairs;
}

pair_t * add_pair(pair_t ** pairs, int left , int right){
  int i;
  for(i = 0; i < 10; i++) {
    if(pairs[i] == NULL) {
      pairs[i] = malloc(sizeof(pair_t)); //allocate

      pairs[i]->left = left;//do asignment
      pairs[i]->right = right;

      return pairs[i]; //return the new pair
    }
  }  
  return NULL; //list full, return NULL for error
}

int main(int argc, char * argv[]) {
  pair_t ** pairs = new_pairs(); //create a new pair array

  add_pair(pairs, 2, 10); //assign a new pair
  add_pair(pairs, 0, 3); //assign a new pair
  //...
  // deallocate?
}
```

Now, the addition of a new pair is as simple as calling `add_pair()` which will find the first `NULL` pointer in the `pairs` array to do the insert. If the array is full, it returns `NULL` on error.

This is great! We've just generalized our double pointer array into a mini data structure that is memory efficient and easy to use. There's one problem though, how do we properly deallocate the double pointer to make sure we don't have a memory leak? The following will not work:

```c
void delete_pairs(pair_t ** pairs) {
   free(pairs); // memory leak!!!  
}
```

This will cause a memory leak because the index of pairs are pointers which may reference other allocated memory. You can't just free up the larger array without first deallocating any memory referenced from within the array. If you do, then the address of that memory will be lost, thus leaking the memory.

Instead, you have to be more careful and generate a code block to deallocate it properly.

```c
void delete_pairs(pair_t ** pairs) {
  int i;

  for(i = 0; i < 10; i++) {
    if (pairs[i] != NULL) { //don't want to free(NULL), cause coredump
       free(pairs[i]); //free allocated memory within array
    }
  }

  free(pairs); //free array
}
```


# Programming a Dynamic Data Structure in C

We now have everything we need to do some more advanced programming in C, notably, to program a data structure. Let's look at what it takes to implement stack in C using a linking data structure. Recall that a stack is a first-in-first-out (FIFO) data structure with the following interface. 

* `push(e)` : push an element onto the stack
* `pop(e)` : pop an emelemnt off of the stack
* `isEmpty()` : return true if the stack is empty
* `size()` : return the number of elements on the stack

For our example, we'll store a stack of strings, just to make it a bit more interesting. We'll call this stack a `sstack` for a string stack.

## Header File

To start with, we want to make our stack usable in multiple programs, so we should create our own header file for the stack. We can call this `sstack.h` for "string stack", and in it, we wan declare our structures and functions that will comprise it. 

#### Structures of a `sstack`

First, let's note that if we are using a linked-list like data structure for our stack, we need to declare nodes and links between those nodes. So we can start there:

```c

struct node {
   char * str; //string
   struct node * next; //pointer to the next node
}

typedef struct node node_t; //typedef so we can use node_t
```

Then we can define a `sstack` struct that keeps track of the number of nodes and the head node. 

```c
typedef struct sstack_t {
   int size; //track it's size
   node_t * head;
}

```

#### Functions over the `sstack`

Now that we know our methods, we have to declare the functions that operate over the stack. We'll define these later when we write the source file.

```c

//constructor/destructor
sstack_t * new_sstack(); //create a new sstack
void del_sstack(sstack_t * ss); //delete a stack


//interface
void push(sstack_t * ss, char * str);
char * pop(sstack_t * ss);
int is_empty(sstack_t * ss);
int size(sstack_t * ss);
```

First, we need two functions to both create a new `sstack` and also to deallocate our `sstack`. This operates much like the `new` operator in Java, but we have to do so explicitly. Moreover, we have to create a routine for deallocating the `sstack` as this does not occur automatically in C. 

Next, we have the interface functions. Each of these has a pointer to a `sstack` as an argument. This allows the functions to know the context of the `sstack` it is operating over. This is very similar in java to the dot-operator. That is, when you do `ss.push()` in Java, `ss` is passed as the context for which object to call `push` on. In C, however, we have to pass that context as a explicit argument. 


### Source File

Now that we've declared our types and functions, we look to define these in a source file, we can call `sstack.c`. At the top of that source file, we `#include` our header file as well as the C standard library and the string library.


```c
#include <stdlib.h>
#include <string.h>
#include "sstack.h"

```

#### Allocating

The first thing we need to do is actually allocate the storage requirements for `sstack` in `new_sstack()`. This requires at least one `malloc` call to create a new `sstack_t` structure and also initializing that structure.

```c
sstack_t * new_sstack() {
    //allocate a new sstack_t struct
    sstack_t * ss = malloc(sizeof(sstack_t));
    ss->size = 0; //initiali size is 0
    ss->head = NULL; //the head is null
}
```

#### Push

Given that the `sstack_t` has been initialized, we can then push new strings onto the stack. Importantly, consider that a string in C is also a pointer, and that pointer to memory can be used in other contexts -- it would be outside the control of our `sstack`. This bad programming can lead to memory violations. 

Consider the following **incorrect** implementation of `push()` below:

```c
void push(sstack_t * ss, char * str) {

    //allocate
    node_t * n = malloc(sizeof(node_t));
    
    n->str = str; //<-- this is bad!
    
    //set this node to the new head
    n->next = ss->head;
    ss->head = n;
    
    ss->size++; //increase size
}
```

Then the user of this code may do the following:

```c
int main() {
    sstack_t * ss = new_sstack();
    
    char word[100];
    
    printf("Enter a word:\n");
    scanf("%99s", word);
    push(ss, word);

    printf("Enter a second word:\n");
    scanf("%99s", word);
    push(ss, word);

    //...
}
```

If we diagram out our `sstack` we'd find that we *aren't storing two words* but rather a pointer to the same word. That's because we are only copying the pointer passed to push, and not the memory of the string itself.

For example, suppose the user entered "hello" and "world". After "hello" we'd have the following diagram

```
*stack*                    *heap*

main 
----
word: "hello" <-------------------------------------.
ss: ------------------->  [ size: 1 ]               |
                          [ head: --]---> [str --]--'
                                          [next -]--> NULL
```

After "world" was pushed ... We lost the fact that we pushed "hello". 

```
*stack*                    *heap*

main 
----
word: "world" <-------------------------------------.<----------.
ss: ------------------->  [ size: 1 ]               |           |
                          [ head: --]---> [str --]--'           |
                                          [next -]--> [str --]--'
                                                      [next -]-> NULL

``` 

Instead, we want to *duplicate* the string and store our own copy.

```c
void push(sstack_t * ss, char * str){

    //allocate
    node_t * n = malloc(sizeof(node_t));
    
    n->str = strdup(str); //<-- create a copy of the string
                          //    this is a malloc!
                          
    //set this node to the new head
    n->next = ss->head;
    ss->head = n;
    
    ss->size++; //increase size
}
```

**Using `strdup()` we create a malloc'ed duplication of the string**, and now we have the following diagram after "world" is entered.

```
*stack*                    *heap*

main 
----
word: "world" 
ss: ------------------->  [ size: 1 ]              .->"hello"
                          [ head: --]---> [str --]-'           .->"world"
                                          [next -]--> [str --]-'
                                                      [next -]-> NULL

``` 

Where both "hello" and "world" are strings allocated on the heap.


#### Pop

For `pop` this is also a deallocation routine, as we allocated a `node_t` for each element. We need to both return the next string in the list and deallocate the node that stored that string. It will be the responsbility of the user deallocate the returned string.

```c

char * pop(sstack_t * ss){
    //check if empty
    if(! ss->head ) return NULL;

    //retrieve node and str
    node_t * n = ss->head;
    char * str = n->str;
    
    //set head to the node's next    
    ss->head = n->next;
    
    //free the node
    free(n);

    //reduce the size
    ss->size--;

    //return the string
    return str;        
}
```

#### Is Empty and Size

The next two functions are pretty straight forward

```c

int is_empty(sstack_t * ss){
    return !ss->head; //check for NULL
}

int size(sstack_t * ss){
    return ss->size;
}

```

#### Deallocating

Finally, we need to deallocate our `sstack`. To do this we can take advantage of some of the other functions we already wrote, and remembering that the we used `strdup()` when storing the string.

```c
void del_sstack(sstack_t * ss){
    while(!is_empty(ss)){
        char * str = pop(ss); //free's the node
        free(str); //free's from strdup()    
    }    
    free(ss); //free the sstack itself
}

```

### Using the `sstack`

Finally, we can use our `sstack` in a program. Let's consider using the stack to reverse five words.

```c
#include <stdio.h>
#include "sstack.h"

int main(){

    char word[100];
    sstack_t * ss = new_sstack();
    
    printf("Enter five words\n");
    for(int i=0;i<5;i++){
        scanf("%99s",word);
        push(ss,word);
    }
    
    printf("Your words in reverse\n");
    
    while(!is_empty(ss)){
        char * str = pop(ss);
        printf("%s\n",str);
        free(str);
    }
    
    del_sstack(ss);

}
```
