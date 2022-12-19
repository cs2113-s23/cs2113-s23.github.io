---
layout: toc
permalink: /c/2
title: C Pointer Arithmetic, Double Arrays, and Command Line Args
---

*View all the videos from this unit a [single playlist on youtube](https://youtube.com/playlist?list=PLnVRBITSZMSOrEoPLyNYwZWjWvvSBaJRy)*

# C Strings

A string in C is simply an array of `char` objects that is `NULL` terminated. Here's a typical C string declaration:

```c
char str[] = "Hello!";
```

A couple things to note about the declaration:

-   First that we declare `str` like an array, but we do not provide it a size.

-   Second, we assign to `str` a quoted string.

-   Finally, while we know that strings are `NULL` terminated, there is no explicit `NULL` termination.

We will tackle each of these in turn below.


<a id="org5e55833"></a>

## Advanced Array Declarations

While the declaration looks familiar  without the array size, this actually means that the size will be determined automatically by the assignment. All arrays can be declared in this static way; here is an example for an integer array:

```c
int array[] = {1, 2, 3};
```

In that example, the array values are denoted using the `{ }` and comma separated within. The length of the array is clearly 3, and the compiler can determine that by inspecting the static declaration, so it is often omitted. However, that does not mean you cannot provide a size, for example

```c
int array[10] = {1, 2, 3};
```

is also perfectly fine but has a different semantic meaning. The first declaration (without a size) says allocate *only* enough memory to store the statically declared array. The second declaration (with the size) says to allocate enough memory to store *size* items of the data type and initialize as many possible values as provided to this array (the values of the remaining indexes are undefined, but typically 0'ed out).

You can see this actually happening in this simple program:

```c
/*array_deceleration.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char * argv[]) {

  int a[] = {1, 2, 3};
  int b[10] = {1, 2, 3};
  int i;

  printf("sizeof(a):%d sizeof(b):%d\n", 
         (int) sizeof(a),
         (int) sizeof(b)
         );

  printf("\n");
  for(i = 0; i < 3; i++){
    printf("a[%d]: %d\n", i, a[i]);
  }    

  printf("\n");
  for(i = 0; i < 10; i++){
    printf("b[%d]: %d\n", i, b[i]);
  }    
}
```

    $ ./array_decleration
    sizeof(a):12 sizeof(b):40
    a[0]: 1
    a[1]: 2
    a[2]: 3
    
    b[0]: 1
    b[1]: 2
    b[2]: 3
    b[3]: 0
    b[4]: 0
    b[5]: 0
    b[6]: 0
    b[7]: 0
    b[8]: 0
    b[9]: 0

As you can see, both declerations work, but the allocation sizes are different. Array `b` is allocated to store 10 integers with a size of 40 bytes, while array `a` only allocated enough to store the static declaration. Also note that the allocation implicitly filled in 0 for non statically declared array elements in `b`, which is behavior you'd expect.


<a id="orga4289bc"></a>

## The quoted string declaration

Now that you have a broader sense of how arrays are declared, let's adapt this to strings. The first thing we can try and declare is a string, that is an array of `char`'s, using the declaration like we had above.

```c
char a[]   = {'G','o',' ','B','u','f','f','!'};
char b[10] = {'G','o',' ','B','l','u','e','!'};
```

Just as before we are declaring an array of the given type which is `char`. We also use the static declaration for arrays. At this point we should feel pretty good &#x2014; we have a string, but not really. Let's look at an example using this declaration:

```c
/*string_declerations.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char * argv[]) {

  char a[]   = {'G','o',' ','B','u','f','f','!'};
  char b[10] = {'G','o',' ','B','l','u','e','!'};
  int i;

  printf("sizeof(a):%d sizeof(b):%d\n", 
         (int) sizeof(a),
         (int) sizeof(b)
         );


  printf("\n");
  for(i = 0; i < 8; i++){
    //print char and ASCII value
    printf("a[%d]: %c (%d)\n", i, a[i], a[i]);
  }    


  printf("\n");
  for(i = 0; i < 10; i++){
    //print char and ASCII value
    printf("b[%d]: %c (%d) \n", i, b[i], b[i]);
  }    

  printf("\n");
  printf("a: %s\n", a); //format print the string
  printf("b: %s\n", b); //format print the string

}
```

```
sizeof(a):8 sizeof(b):10

a[0]: G (71)
a[1]: o (111)
a[2]:   (32)
a[3]: B (66)
a[4]: u (117)
a[5]: f (102)
a[6]: f (102)
a[7]: ! (33)

b[0]: G (71) 
b[1]: o (111) 
b[2]:   (32) 
b[3]: B (66) 
b[4]: l (108) 
b[5]: u (117) 
b[6]: e (101) 
b[7]: ! (33) 
b[8]:  (0) 
b[9]:  (0) 

a: Go Buff!O //<-- whats that?!?!
b: Go Blue!
```

First observations is `sizeof` the arrays match our expectations. A `char` is 1 byte in size and the arrays are allocated to match either the implicit size (8) or the explicit size (10). We can also print the arrays iteratively, and the ASCII values are inset to provide a reference. However, when we try and format print the string using the `%s` format, something strange happens for `a` that does not happen for `b`.

The problem is that `a` is not `NULL` terminated, that is, the last `char` numeric value in the string is not 0. **`NULL` termination is very important for determining the length of the string**. Without this special marker, the `printf()` function is unable to determine when the string ends, so it prints extra characters that are not really part of the string.

We can change the declaration of `a` to explicitly `NULL` terminate like so:

```c
char a[]   = {'G','o',' ','B','u','f','f','!', '\0'};
```

The escape sequence `'\0'` is equivalent to `NULL`, and now we have a legal string. But, I think we can all agree this is a really annoying way to do string declarations using array formats because all strings should be `NULL` terminated anyway. Thus, the double quoted string shorthand is used.

```c
char a[]   = "Go Buff!";
```

The quoted string is the same as statically declaring an array with an implicit `NULL` termination, and it is ever so much more convenient to use. You can also more explicitly declare the size, as in the below example, which declares the array of the size, but also will NULL terminate.

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char * argv[]) {

  char a[]   = "Go Buff!";
  char b[10] = "Go Blue!";
  int i;

  printf("sizeof(a):%d sizeof(b):%d\n", 
         (int) sizeof(a),
         (int) sizeof(b)
         );


  printf("\n");
  for(i = 0; i < 9; i++){
    //print char and ASCII value
    printf("a[%d]: %c (%d)\n", i, a[i], a[i]);
  }    


  printf("\n");
  for(i = 0; i < 10; i++){
    //print char and ASCII value
    printf("b[%d]: %c (%d) \n", i, b[i], b[i]);
  }    

  printf("\n");
  printf("a: %s\n", a); //format print the string
  printf("b: %s\n", b); //format print the string

}
```
```
$ ./string_declerations 
sizeof(a):9 sizeof(b):10

a[0]: G (71)
a[1]: o (111)
a[2]:   (32)
a[3]: B (66)
a[4]: u (117)
a[5]: f (102)
a[6]: f (102)
a[7]: ! (33)

b[0]: G (71) 
b[1]: o (111) 
b[2]:   (32) 
b[3]: B (66) 
b[4]: l (108) 
b[5]: u (117) 
b[6]: e (101) 
b[7]: ! (33) 
b[8]:  (0) 
b[9]:  (0) 

a: Go Buff!
b: Go Blue!
```
You may now be wondering what happens if you do something silly like this,

```c
char a[3] = "Go Buff!";
```

where you declare the string to be of size 3 but assign a string requiring much more memory? Well &#x2026; why don't you try writing a small program to finding out what happen, which you will do in homework.


<a id="org6cf0f57"></a>

## String format input, output, overflows, and `NULL` deference's:

While strings are not basic types, like numbers, they do have a special place in a lot of operations because we use them so commonly. One such place is in formats.

You already saw above that `%s` is the format character to process a string, and it is also the format character used to scan a string. We can see how this all works using this simple example:

```c
/*format_string*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

  char name[20];

  printf("What is your name?\n");
  scanf("%s", name);

  printf("\n");
  printf("Hello %s!\n", name); 

}
```

There are two formats. The first will ask the user for their name, and read the response using a `scanf()`. Looking more closely, when you provide `name` as the second argument to `scanf()`, you are saying: "Read in a string and write it to the memory referenced by `name`." Later, we can then print `name` using a `%s` in a `printf()`. Here is a sample execution:

    $ ./format_string 
    What is your name?
    Adam
    
    Hello Adam!

That works great. Let's try some other input:

    $ ./format_string 
    What is your name?
    Adam Aviv
    
    Hello Adam!

Hmm. That didn't work like expected. Instead of reading in the whole input "Adam Aviv" it only read a single word, "Adam". This has to do with the functionality of `scanf()` that **`%s` does not refer to an entire line but just an individual whitespace separated string**.

The other thing to notice is that the string `name` is of a fixed size, 20 bytes. What happens if I provide input that is longer &#x2026; much longer.

    aviv@saddleback: demo $ ./format_string 
    What is your name?
    AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAdam
    
    Hello AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAdam!
     *** stack smashing detected ***: ./format_string terminated
    Aborted (core dumped)

That was interesting. The execution identified that you *overflowed* the string, that is tried to write more than 20 bytes. This caused a check to go off, and the program to crash. Generally, **a segmentation fault occurs when you try to read or write invalid memory**, i.e., outside the allowable memory segments.

We can go even further with this example and come up with a name sooooooo long that the program crashes in a different way:

    What is your name?
    AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    
    Hello AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!
    Segmentation fault (core dumped)

In this case, we got a segmentation fault. The `scanf()` wrote so far out of bounds of the length of the array that it wrote memory it was not allowed to do so. This caused the segmentation fault.

Another way you can get a segmentation fault is by dereferencing `NULL`, that is, you have a pointer value that equals `NULL` and you try to follow the pointer to memory that does not exist.

```c
/*null_print.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc,char*argv[]) {

  printf("This is a bad idea ...\n");
  printf("%s\n", (char *) NULL);
}
```

    $ ./null_print
    This is a bad idea ...
    Segmentation fault (core dumped)

This example is relatively silly as I purposely dereference `NULL` by trying to treat it as a string. While you might not do it so blatantly, you will do something like this at some point. It is a mistake we all make as programmers, and it is a particularly annoying mistake that is inevitable when you program with pointers and strings. It can be frustrating, but we will also go over many ways to debug such errors throughout the semester.


<a id="org6006897"></a>

# String Library Functions

Certain programming paradigms that would seem obvious to do in C do not do as you would expect them to do. Here's an example:

```c
/*string_badcmp.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc,char *argv[]) {

  char str[20];

  printf("Enter 'Buff' for a secret message:\n");

  scanf("%s", str);

  if(str == "Buff")
    printf("Go Buff! Go Blue!\n");
  else
    printf("No secret for you.\n");
  
}
```

And if we run this program and enter in the appropriate string, we do not get the result we expect.

    $ ./string_badcmp 
    Enter 'Buff' for a secret message:
    Buff
    No secret for you.

What happened? If we look at the if statement expression:

```c
if(str == "Buff")
```

Our intuition is that this will compare the string `str` and "Buff" based on the values in the string, that is, is `str` "Navy" ? But that is not what this is doing because remember a string is an array of characters and an array is a pointer to memory and so the equality is check to see if the `str` and "Buff" are stored in the same place in memory and has nothing to do with the actual strings.

To see that this is case, consider this small program which also does not do what is expected:

```c
/*string_badequals.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
  char s1[] = "Buff";
  char s2[] = "Buff";
  if(s1 == s2)
    printf("Go Buff!\n");
  else
    printf("Go Blue!\n");
  
  printf("\n");
  printf("s1: %p \n", s1);
  printf("s2: %p \n", s2);
}
```

    $ ./string_badequals 
    Go Blue!
    
    s1: 0x7fffe43994f0 
    s2: 0x7fffe4399500 

Looking closely, although both `s1` and `s2` reference the same string values they are not the *same* string in memory and have two different addresses. (The `%p` formats a memory address in hexadecimal.)

The right way to compare to strings is to compare each character, but that is a lot of extra code and something we don't want to write every time. Fortunately, its been implemented for us along with a number of other useful functions in the string library.


<a id="org8cfaba7"></a>

## The string library `string.h`

To see all the goodness in the string library, start by typing `man string` in your linux terminal. Up will come the manual page for all the functions in the string library:

    STRING(3)                              Linux Programmer's Manual                             STRING(3)
    
    NAME
           stpcpy,  strcasecmp,  strcat, strchr, strcmp, strcoll, strcpy, strcspn, strdup, strfry, strlen,
           strncat, strncmp, strncpy, strncasecmp,  strpbrk,  strrchr,  strsep,  strspn,  strstr,  strtok,
           strxfrm, index, rindex - string operations
    
    SYNOPSIS
           #include <strings.h>
    
           int strcasecmp(const char *s1, const char *s2);
    
           int strncasecmp(const char *s1, const char *s2, size_t n);
    
           char *index(const char *s, int c);
    
           char *rindex(const char *s, int c);
    
           #include <string.h>
    
           char *stpcpy(char *dest, const char *src);
    
           char *strcat(char *dest, const char *src);
    
           char *strchr(const char *s, int c);
    
           int strcmp(const char *s1, const char *s2);
    
           int strcoll(const char *s1, const char *s2);
    
           char *strcpy(char *dest, const char *src);
    
           size_t strcspn(const char *s, const char *reject);
    
           char *strdup(const char *s);
    
           char *strfry(char *string);
    
           size_t strlen(const char *s);
    
           ...

To use the string library, the only thing you need to do is include `string.h` in the header declarations. You can further explore different functions string library within their own manual pages. The two most relevant to our discussion will be `strcmp()` and `strlen()`. However, I encourage you to explore some of the others, for example `strfry()` will randomize the string to create an anagram &#x2013; how useful!


<a id="orgb010dd6"></a>

## String Comparison

To solve our string comparison delimina, we will use the `strcmp()` function from the string library. Here is the revelant man page:

    STRCMP(3)                              Linux Programmer's Manual                             STRCMP(3)
    
    NAME
           strcmp, strncmp - compare two strings
    
    SYNOPSIS
           #include <string.h>
    
           int strcmp(const char *s1, const char *s2);
    
           int strncmp(const char *s1, const char *s2, size_t n);
    
    DESCRIPTION
           The  strcmp()  function  compares  the two strings s1 and s2.  It returns an integer less than,
           equal to, or greater than zero if s1 is found, respectively, to be less than, to match,  or  be
           greater than s2.
    
           The  strncmp()  function  is similar, except it compares the only first (at most) n bytes of s1
           and s2.
    
    RETURN VALUE
           The strcmp() and strncmp() functions return an integer less than, equal  to,  or  greater  than
           zero if s1 (or the first n bytes thereof) is found, respectively, to be less than, to match, or
           be greater than s2.

It comes in two varieties. One with a maximum length specified and one that relies on null termination. Both return the same values. If the two strings are equal, then the value is 0, if the first string string is greater (larger alphabetically) than it returns 1, and if the first string is less than (smaller alphabetically) then it returns -1.

Plugging in `strcmp()` into our secret message program, we get the desired results.

```c
/*string_strncp.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc,char *argv[]) {

  char str[20];

  printf("Enter 'Buff' for a secret message:\n");

  scanf("%s", str);

  if(strcmp(str, "Buff") == 0) 
    printf("Go Buff! Go Blue!\n");
  else
    printf("No secret for you.\n");

}
```

    aviv@saddleback: demo $ ./string_strcmp 
    Enter 'Buff' for a secret message:
    Buff
    Go Buff! Go Blue!


<a id="orgf218195"></a>

## String Length vs String Size

Another really important string library function is `strlen()` which returns the length of the string. It is important to differentiate the length of the string from the size of the string.

-   *string length*: how many characters, not including the null character, are in the string

-   *sizeof* : how many bytes required to store the string.

One of the most common mistakes when working with C strings is to consider the `sizeof` the string and not the length of the string, which are clearly two different values. Here is a small program that can demonstrate how this can go wrong quickly:

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char *argv[]) {

  char str[] = "Hello!";
  char * s = str;

  printf("strlen(str):%d sizeof(str):%d sizeof(s):%d\n",
         (int) strlen(str),  //the length of the str
         (int) sizeof(str),  //the memory size of the str
         (int) sizeof(s)    //the memory size of a pointer
         );
}
```

    $ ./string_length 
    strlen(str):6 sizeof(str):7 sizeof(s):8

Note that when using `strlen()` we get the length of the string "Hello!" which has 6 letters. The size of the string `str` is how much memory is used to store it, which is 7, if you include the null terminated. However, things get bad when you have a pointer to that string `s`. Calling `sizeof()` on `s` returns how much memory needed to store `s` which is a pointer and thus is 8-bytes in size. That has nothing to do with the length of the string or the size of the string. This is why when working with strings always make sure to use the right length not the size.



# Pointer Arithmetic and Strings

As noted many times now, strings are arrays, and as such, you can work with them as arrays using indexing with `[ ]`; however, often when programmers work with strings, they use pointer arithmetic. For example, here is a routine to print a string to `stdout`:

```c
void my_puts(char * str){

  while(*str){
    putchar(*str);
    str++;
  }

}
```

This function `my_puts()` takes a string and will write the string, char-by-char to stdout using the `putchar()` function. What might seem a little odd here is the use of the while loop, so lets unpack that:

```c
while(*str) 
```

What does this mean? First notice that `str` is declared as a `char *` which is a pointer to a character. We also know that pointers and arrays are the same, so we can say that `str` is a string that references the first character in the string's array. Next the `*str` operation is a dereference, which says to follow the pointer and retrieve the value that it references. In this case that would be a character value. Finally, the fact that this operation occurs in the expression part means that we are testing the value that the pointer references for not be false, which is the same as asking if it is not zero or not `NULL`.

So, the `while(*str)` says to continue looping as long the pointer `str` does not reference `NULL`. The pointer value of `str` does change in the loop and is incremented, `str++`, for each interaction after the call to `putchar()`.

Now putting it all together, you can see that this routine will iterate through a string using a pointer until the `NULL` terminator is reached. Phew. While this might seem like a backwards way of doing this, it is actually a rather common and straightforword programming practice with strings and pointers in general.


<a id="orgf358d1b"></a>

## Pointer Arithmetic and Types

Something that you might have noticed is that we have been using pointer arithmetic for different types in the same way. That is, consider the two arrays below, one an array of integers and one a string:

```c
int a[]  = {0, 1, 2, 3, 4, 5, 6};
char str[] = "Hello!";
```

Both arrays are the same length, 7, but they are different sizes. Integers are 4-bytes, so to store 7 integers requires 4\*7=28 bytes. But characters are 1 byte in size, so to store 7 characters requires just 7 bytes. In memory the two arrays may look something like this:

           <------------------------ 28 bytes ---------------------------->
           .---------------.----------------.--- - - - ---.----------------.
    a   -> |             0 |              1 |      ..     |              6 |
           '---------------'----------------'--- - - - ---'----------------'
    
           .---.---.---.---.---.---.---.
    str -> | H | e | l | l | o | ! | \0|
           '---'---'---'---'---'---'---.
           <-------  7 bytes ----------> 

Now consider what happens when we use pointer arithmetic on these arrays to dereference the third index:

```c
/*pointer_math.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

  int a[] = {0, 1, 2, 3, 4, 5, 6};
  char str[] = "Hello!";

  printf("a[3]:%d str[3]:%c\n", *(a + 3), *(str + 3));

}
```

    aviv@saddleback: demo $ ./pointer_math 
    a[3]:3 str[3]:l

Knowing what you know, the output is not consistent. When you add 3 to the array of integers `a`, you adjust the pointer by 12 bytes so that you now reference the value 3. However, when you add 3 to the string pointer, you adjust the pointer by 3 bytes to reference the value 'l' (the second 'l' in the word, by the way).

The reason for this has to do with pointer arithmetic consideration of typing. When you declare a pointer to reference a particular type, C is aware that adding to the pointer value should consider the type of data being referenced. So when you add 1 to an integer pointer, you are moving the reference forward 4 bytes since that is the size of the integer. 

<a id="org79a12a0"></a>

## Character Arrays as Arbitrary Data Buffers

Now you may be wondering, how do I access the individual bytes of larger data types? The answer to this is the final peculiarity of character arrays in C.

Consider that a `char` data type is 1 byte in size, which is the smallest data element we work with as programmers. Now consider that an array of `char`'s matches exactly that many bytes. So when we write something like:

```c
char s[4];
```

What we are really saying is: "allocate 4 bytes of data." We like to think about storing a string of length 3 in that character array with one byte for the null terminator, but we do not have to. In fact, any kind of data can be stored there as long as it is only 4-bytes in size. An integer is four bytes in size. Let's store an integer in `s`.

```c
    #include <stdio.h>
    #include <stdlib.h>
    
    int main(int argc, char *argv[]) {
    
      char s[4];
    
      s[0] = 255;
      s[1] = 255;
      s[2] = 255;
      s[3] = 255;
    
      int * i = (int *) s;
      printf("*i = %d\n", *i);
    
    }
```

What this program does is set all the bytes in the character array to 255, which is the largest value 1-byte can store. The result is that we have 4-bytes of data that are all 1's, since 255 in binary is 11111111. Four bytes of data that is all 1's. Next, consider what happens with this cast:

```c
int * i = (int *) s;
```

Now the pointer `i` references the same memory as `s`, which is 4-bytes of 1's. What's different is that `i` is an integer pointer not a character pointer. That means the 4-bytes of 1's is an integer not characters from the perspective of `i`. And when we dereference `i` to print those bytes as a number, we get:

    
    *i = -1

Which is the signed value for all 1's (remember two's compliment?). What we've just done is use characters as a generic container for data and then used pointer casting to determine how to interpret that data. This may seem crazy &#x2014; it is &#x2014; but it is what makes C so low level and useful.

We often refer to character arrays as **buffers** because of this property of being arbitrary containers. A buffer of data is just a bunch of bytes, and a character array is the most direct way to access that data.

<a name="lec4"></a>


<a id="org9f1b8c8"></a>

# Multidimensional Arrays

We continue our discussion of data types in C by looking at double arrays, which is an array of arrays. This will lead directly to command line arguments as these are processed as an array of strings, which are arrays themselves, thus double arrays.


<a id="org224229e"></a>

## Declaring Double Arrays

Like single arrays, we can declare double arrays using the `[ ]`, but with two. We can also do static declarations of values with `{ }`. Here's an example:

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

  int darray[][4] = { {0, 0, 0, 0},
                      {1, 1, 1, 1},
                      {2, 2, 2, 2},
                      {3, 3, 3, 3} };
  int i, j;

  for(i = 0; i < 4; i++){
    printf("darray[%d] = { ", i);
    for(j = 0; j < 4; j++){
      printf("%d ", darray[i][j]); //<---
    }
    printf("}\n");
  }


}
```
Which would print:

    darray[0] = { 0 0 0 0 }
    darray[1] = { 1 1 1 1 }
    darray[2] = { 2 2 2 2 }
    darray[3] = { 3 3 3 3 }

Each index in the array references another array. Like before we allow C to determine the size of the outer array when declaring statically. However, you must define the size of the inner arrays. This is because of the way the memory is allocated. While the array example above is square in size, the double array can be asymmetric.


<a id="orgbee8c24"></a>

## The type of a double array

Let's think a bit more about what a double array really is given our understanding of the relationship between pointers and arrays. For a single array, the array variable is a pointer that references the first item in the array. For a double array, the array variable references a reference that references the first item in the first array. Here's a visual of how we can think of the typing 

```
    int**              int*    .---.---.---.---.
                .---.  _.----> | 0 | 0 | 0 | 0 |  <-- darray[0]
    darray ---> | .-+-'        '---'---'---'---'
                |---|          .---.---.---.---.
                | .-+--------> | 1 | 1 | 1 | 1 |  <-- darray[1]
                |---|          '---'---'---'---'
                | .-+-._       .---.---.---.---.
                |---|   '----> | 2 | 2 | 2 | 2 |  <-- darray[2]
                | .-+-._       '---'---'---'---'
                '---'   '._    .---.---.---.---.
                           '-> | 3 | 3 | 3 | 3 |  <-- darray[3]
                               '---'---'---'---'               
```

If we follow the arrays, we see that the type of `darray` is actually a `int **`. That means, it is a pointer that references a memory address that stores another pointer that references a memory address of an integer. So when we say double array, we are also referring to double pointers.

To demonstrate this further, we can even show the dereferences directly.

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

  int darray[][4] = { {0, 0, 0, 0},
                      {1, 1, 1, 1},
                      {2, 2, 2017, 2},
                      {3, 3, 3, 3}};

  printf("*(*(darray+2)+2) = %d\n", *(*(darray + 2) + 2));
  printf("     daray[2][2] = %d\n", darray[2][2]);
}
```

which prints

    *(*(darray+2)+2) = 2017
         daray[2][2] = 2017

As you can see it takes two dereferences to get to the integer value.


## Memory Layout of Multidimensional Arrays

However, how we interpret the typing of double arrays (and multi-dimensional arrays) in C and how C actually lays out that data in memory are two different animals! 

As you now know, arrays are memory aligned regions of the same data type. The value of the array variable is the pointer to the first element. A multi-dimensional array, like `int array[2][3]`,  operates in the same way (mostly) where `array` is of type `int **` (a pointer to a pointer) and so `array[0]` returns a `int *`, a pointer to the start of the array  `array[0]`. 

Things are not quite as they seem... consider the following program and its output.

<!-- <div class="side-by-side">
<div class="side-by-side-a"> -->
```c
#include <stdio.h>
#include <stdlib.h>

int main() {

  int array[2][3];
  int n = 0;
  for(int i = 0; i < 2; i++){
    for(int j = 0; j < 3; j++){
      array[i][j] = n++;
    }					   
  }

  // print out memory addresses of inner arrays
  printf("array = %p\n", array);
  for(int i = 0; i < 2; i++){
    printf("  array[%d] = %p\n", i, array[i]);
      for(int j = 0; j < 3; j++){
    	printf("    array[%d][%d] = %d\n", i, j, array[i][j]);
      }
  }

  printf("\n");
  int *p = (int *) array; // tell C to treat array as a 1D, not 2D array
  for(int i = 0; i < 6; i++){
    printf("p[%d] = %d\n", i, p[i]);
  }

  printf("\n");
  for(int i = 0; i < 2; i++){
    for(int j = 0; j < 3; j++){
      printf("array[%d][%d] = *(p + (%d * 2  + %d)) = %d\n",
	     i, j, i, j, *(p + (i * 2 + j)));
    }
  }
}

```
<!-- </div>
<div class="side-by-side-b"> -->
```
array = 0x7ffdcfc269a0
  array[0] = 0x7ffdcfc269a0
    array[0][0] = 0
    array[0][1] = 1
    array[0][2] = 2
  array[1] = 0x7ffdcfc269ac
    array[1][0] = 3
    array[1][1] = 4
    array[1][2] = 5

p[0] = 0
p[1] = 1
p[2] = 2
p[3] = 3
p[4] = 4
p[5] = 5

array[0][0] = *(p + (0 * 3  + 0)) = 0
array[0][1] = *(p + (0 * 3  + 1)) = 1
array[0][2] = *(p + (0 * 3  + 2)) = 2
array[1][0] = *(p + (1 * 3  + 0)) = 3
array[1][1] = *(p + (1 * 3  + 1)) = 4
array[1][2] = *(p + (1 * 3  + 2)) = 5
```
<!-- </div>
</div> -->

For starters, the pointer value `array` and `array[0]` are the same, and also, we can use an `int * (p)` to traverse the entirety of the double array. That's because C doesn't really allocated subarrays for each `array[i]` but rather uses elaborate pointer math to give the illusion of multiple sub-arrays. Instead, it allocates a larger array of size `2 * 3=6` integers. Like in the memory diagram below

```
    .--------------.
    |       |    0 |<--<-. array <-- array[0] 
    |-------+------|     |
    |       |    1 |     |
    |-------+------|     |
    |       |    2 |     |
    |-------+------|     |
    |       |    3 | <---+--- array[1]
    |-------+------|     |
    |       |    4 |     |
    |-------+------|     |
    |       |    5 |<----+----- p+5, array+5 , array + 1 * 3 + 2
    |-------+------|     |
    | p     |   .--+-----'
    '-------+------'
```


<a id="org45921ee"></a>

## Array of Strings as Double Arrays

Now let us consider another kind of double array, an array of strings. Recall that a C string is just an array of characters, so an array of strings is a double array of characters. One of the tricky parts of string arrays is the typing declaration.

Before we declared arrays using the following notation:

```c
char str1[] = "This is a string";
```

But now we know that the type of arrays and pointers are really the same, so we can also declare a string this way:

```c
char * str2 = "This is also a string";
```

Note that there is a difference between these two declarations in how and where C actually stores the string in memory. Consider the output of this program:

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

  char str1[] = "This is a string";
  char * str2 = "This is also a string";

  printf("str1: %s \t\t &str1:%p\n", str1, str1);
  printf("str2: %s \t &str2:%p\n", str2, str2);

}
```


    str1: This is a string 		 &str1:0x7fff4344d090
    str2: This is also a string 	 &str2:0x4006b4

While both strings print fine as strings, the memory address of the two strings are very different. One is located in the stack memory region (`str1`) and other is in the data segment (`str2`, and it happens this memory is not writeable after declaration, making `str2` a constant). In later lessons we will explore this further, but for the moment, the important takeaway is that we can now refer to strings as `char *` types.

Given we know that `char *` is the type of a string, then an array of `char *`'s is an array of strings.

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

  char * strings[] = {"monocyte",
                     "basophil",
                     "leukocyte",
                     "neutrophil"};
  int i;

  printf("strings: %p\n", strings);
  for(i = 0; i < 4; i++){
    printf("strings[%d]: '%s' %p\n", i, strings[i], strings[i]);
  }
}
```

    strings: 0x7fff7af68080
    strings[0]: 'monocyte' 0x400634
    strings[1]: 'basophil' 0x40063d
    strings[2]: 'leukocyte' 0x400648
    strings[3]: 'neutrophil' 0x400658

Like before we can see that `strings` is a pointer that references a pointer to a `char`, but that's just an array of strings or a double array. Another thing you may notice is that the length and size of each of the strings is different. This is because the way the array is declared with `char *` as the type of the string rather than `char []` which changes how the array is stored.

However, there is some wierdness in this example. Take a look at how some additional lines execute in the <a target="_blank" href="https://pythontutor.com/visualize.html#code=%23include%20%3Cstdio.h%3E%0A%23include%20%3Cstdlib.h%3E%0A%0Aint%20main%28int%20argc,%20char%20*argv%5B%5D%29%20%7B%0A%0A%20%20char%20*%20strings%5B%5D%20%3D%20%7B%22monocyte%22,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%22basophil%22,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%22leukocyte%22,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%22neutrophil%22%7D%3B%0A%20%20int%20i%3B%0A%0A%20%20printf%28%22strings%3A%20%25p%5Cn%22,%20strings%29%3B%0A%20%20for%28i%20%3D%200%3B%20i%20%3C%204%3B%20i%2B%2B%29%7B%0A%20%20%20%20printf%28%22strings%5B%25d%5D%3A%20'%25s'%20%25p%5Cn%22,%20i,%20strings%5Bi%5D,%20strings%5Bi%5D%29%3B%0A%20%20%7D%0A%20%20%0A%20%20strings%5B1%5D%20%3D%20%22cat%22%3B%0A%20%20//%20strings%5B2%5D%5B0%5D%20%3D%20'X'%3B%20%20%20%20%20//core%20dump%0A%20%20//%20*%28strings%5B2%5D%29%20%3D%20%22bird%22%3B%20%20//core%20dump%0A%7D&cumulative=false&heapPrimitives=nevernest&mode=edit&origin=opt-frontend.js&py=c_gcc9.3.0&rawInputLstJSON=%5B%5D&textReferences=false">C visualizer</a>. `strings` itself is on the stack in memory, but each of the different blood cells in the array is on the heap; they are strings, and we cannot change their values (just like in Java where strings are constant).

<a id="org3482183"></a>

# Command Line Arguments

Now that you have seen an array of strings, where else does that type appear? In the arguments to the `main()` function. This is part of the command line arguments and is a very important part of systems programming.

While we will also use standard input, this class will require reading in more input from the user in the form of command line arguments. These will be used as basic settings for the program and are much more efficient than always reading in these settings from standard input.

<a id="org008d53a"></a>

## Understanding `main()` arguments

You may have noticed that I have been writing main functions slightly differently then you have seen them before.

```c
//
//argument ____.             ._____ argument
// count       |             |       variables 
//             v             v
 int main(int argc, char * argv[]);
```

The arguments to main correspond to the command line input. The first is the number of such arguments, and the second is a string array of the argument values. Here's an example that illuminates the arguments:

```c
/*print_args.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
  int i;

  for(i = 0; i < argc; i++){
    printf("argv[%d] = %s\n", i, argv[i]);
  }
}
```

    aviv@saddleback: demo $ ./print_args arg1 arg2 arg3 x y z adam aviv
    argv[0] = ./print_args
    argv[1] = arg1
    argv[2] = arg2
    argv[3] = arg3
    argv[4] = x
    argv[5] = y
    argv[6] = z
    argv[7] = adam
    argv[8] = aviv

Looking at the program and its output, you can see that there is correspondence to the arguments provided and the index in the array. It's important to note that the name of the program being run is `arg[0]`, which means that **all** programs have at least one argument, the name of the program. For example:

    aviv@saddleback: demo $ ./print_args 
    argv[0] = ./print_args

The name of the program is not compiled into the executable. It is instead passed as a true command line argument by the shell, which forks and executes the program. The mechanics of this will become clear later in the semester when we implement our own simplified version of the shell. To demonstrate this now, consider how the `arg[0]` changes when I change the name of the executable:

    aviv@saddleback: demo $ cp print_args newnameofprintargs
    aviv@saddleback: demo $ ./newnameofprintargs 
    argv[0] = ./newnameofprintargs
    aviv@saddleback: demo $ ./newnameofprintargs a b c d e f
    argv[0] = ./newnameofprintargs
    argv[1] = a
    argv[2] = b
    argv[3] = c
    argv[4] = d
    argv[5] = e
    argv[6] = f


<a id="org1a114e5"></a>

## `NULL` Termination in args arrays

Another interesting construction of the `argv` array is that the array is `NULL` terminated much like a string is null terminated. The reason for this is so the OS can determine how many arguments are present. Without null termination there is no way to know the end of the array.

You can use this fact when parsing the array by using pointer arithmetic and checking for a NULL reference:

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

  char ** curarg;
  int i;

  for(curarg = argv, i=0; //initialize curarg to argv array and i to 0
       *curarg != NULL;    //stop when curarg references NULL
       curarg++, i++){     //increment curarg and i

     printf("argv[%d] = %s\n", i, *curarg);
  }

}
```

    viv@saddleback: demo $ ./print_args_pointer a b c d e
    argv[0] = ./print_args_pointer
    argv[1] = a
    argv[2] = b
    argv[3] = c
    argv[4] = d
    argv[5] = e

Notice that the pointer incrementing over the `argv` arrays is of type `char **`. Its a pointer to a string, which is itself an array of chars, so its a pointer to a pointer. (POINTERS ARE MADNESS!)


<a id="org2082fd6"></a>

## Basic Parsing of Command Line Arguments: `atoi()` and `sscanf()`

Something that you will often have to do when writing programs is parse command line arguments. The required error checking procedure can be time consuming, but it is also incredibly important for the overall user experience of your program. For real-world testing, there are ways to pipe stored arguments to your program, but here we'll rely on manual execution of test cases.

Lets consider a simple program that will print a string a user specified number of times. We would like to execute it this way:

    run_n_times 5 string

Where `string` is printed n times. Now, what we know about command line arguments is that they are processed as strings, so both `string` and `5` are strings. We need to convert "5" into an integer 5.

There are two ways to do this. The first is `atoi()` which converts a string to a number, but looking at the manual page for `atoi()` we find that `atoi()` does not detect errors. For example, this command line arguments will not be detected:

    run_n_times notanumber string

Executing, `atoi("notanumber")` will return 0, so a simple routine like:

```c
int main(int argc, char * argv[]) {
  int i;
  int n = atoi(argv[1]); //does not detect errors

  for(i = 0; i < n; i++){
    printf("%s\n", argv[2]);
  }
}
```

will just print nothing and not return the error. While this might be reasonable in some settings, but we might want to detect this error and let the user know.

Instead, we can convert the `argv[1]` to an integer using `scanf()`, but we have another problem. We have only seen `scanf()` in the concept of reading from standard input, but we can also have it read from an arbitrary string. That version of `scanf()` is called `sscanf()` and works like such:

```c
int main(int argc, char * argv[]) { 
  int i;
  int n;

  if(sscanf(argv[1],"%d", &n) == 0){
    fprintf(stderr, "ERROR: require a number\n");
    exit(1); //exit the program
  }

  for(i = 0; i < n; i++){
    printf("%s\n", argv[2]);
  }
}
```

Recall that `scanf()` returns the number of items that successfully match the format string. So if no items match, then the user did not provide a number to match the `%d` format. So this program successfully error checks the first argument. But, what about the second argument? What happens when we run with these arguments?

    ./run_n_times 5

There is no `argv[2]` provided and worse because the `argv` array is NULL terminated, `argv[2]` references `NULL`. When the `printf()` dereferences `argv[2]` it wlll cause a segmentation fault. How do we fix this? We also have to error check the number of arguments.

```c
int main(int argc, char * argv[]) {
  int i;
  int n;

  if(argc < 2){
  fprintf(stderr, "ERROR: invalid number of arguments\n");
    exit(1); //exit the program
  }

  if(sscanf(argv[1],"%d", &n) == 0) ){
    fprintf(stderr, "ERROR: require a number\n");
    exit(1); //exit the program
  }

  for(i = 0; i < n; i++){
    printf("%s\n", argv[2]);
  }
}
```

And now we have properly error checked user arguments to this simple program. As you can see, error checking is tedious but incredibly important.

