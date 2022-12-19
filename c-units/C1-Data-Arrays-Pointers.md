---
layout: toc
permalink: /c/1
title: C Data Types, Arrays, Pointers, Strings
---

*View all the videos from this unit a [single playlist on youtube](https://youtube.com/playlist?list=PLnVRBITSZMSP2jh5c-MOe6-v4QpK59Ibw)*

# C Data Types

## Basic Data Types
In C there are two primary data types, integer and floating point types. 

The integer types are as following, and depending on the system will store numbers in the following number of bytes.

-   `char` : character : 1-byte
-   `short` : integer number : 2-bytes
-   `int` : integer number : 4-bytes (at most, but always greater than or equal to 2 bytes)
-   `long` : integer number : 8-bytes (at most, but always greater than or equal to 4-bytes)
-   `long long` : integer number : 8-bytes (at least)

And the floating point types
-   `float` : floating point number : 4-bytes
-   `double` : floating point number : 8-bytes


Finally, there is a new type, a pointer type which is also an integer type that refers to a place in memory.
-   `(type) *` : pointers : 8-bytes on (64 bit machines)

Note the `(type)` can be replaced with any other data type. So a `int *` or a `char *` are both pointer types, where the former "points to" an `int` and the later "points to" a `char`.

These types and the operations over them are sufficient for most programming; however, we will need more to accomplish the needed tasks. In particular, there are three aspects of these types that require further exploration:

1.  Advanced Structured Types: Create new types and formatted by combining basic types.

2.  Pointers: Working with references to data

3.  Arrays: Organizing data into linear structures.

(*Aside: recall that C doesn't have boolean types. They are just an `int`.*)

<a id="org805dcea"></a>

## Numeric values for integer types

Once you declare an integer type, like a `int` or `short` or `long`, you can assign it a value. The most obvious way to do this, is to include a number, like `10`

```c
int a = 10;
short s = 8;
```

You can define numbers in more ways. For example, C allows you to declare in different bases.

```
int a = 0xa; //a is 10 in hexadecimal!
short s = 010 //10 is 8 in octal!
```

A leading 0x indicates the values to follow are in hexadecimal (base 16). With a leading 0 indicates the value following is in base 8.

### ASCII and char

But recall that `char` is also an integer type, but we don't often think of it that way. We think of it representing a single character. Recall, though, the ASCII table.

![ASCII Table](https://upload.wikimedia.org/wikipedia/commons/1/1b/ASCII-Table-wide.svg)

We can define a `char` with a straight number:

```c
char c = 42;
printf("%c\n", c); //prints '*'
```

But we don't need to know the ascii table to assign `*` to `c` using single quotes.

```c
char c = '*';
printf("%c\n", c); //prints '*'
printf("%d\n", c); //prints 42 
```

## The `sizeof()` function/macro and data storage requirements of data types

C has a built in function `sizeof()` where you can provide a type name or a variable and it will return the number of bytes need to represent that variable---or put another way, how many bytes are needed to *store* that kind of data. (This is very useful for dynamic memory, which we will address later.)

As an example, consider the program below:

```c
#include <stdio.h>

int main() {

  char c = 'G';
  short s = 5;
  int i = 10;
  long l = 300;
  long long ll = 500000;

  int * p1 = &i;
  char * p2 = &c;
  long long  * p3 = &ll;
  
  float f = 3.14;
  double d = 2.789;

  //note the format %lu is for unsigned long long as sizeof() returns up to an 8-byte number
  //if you used %d, that format is for integer values, and will produce a warning 
  printf("sizeof(char): %lu, sizeof(c): %lu \n", sizeof(char), sizeof(c));
  printf("sizeof(short): %lu, sizeof(s): %lu \n", sizeof(short), sizeof(s));
  printf("sizeof(int): %lu, sizeof(i): %lu \n", sizeof(int), sizeof(i));
  printf("sizeof(long): %lu, sizeof(l): %lu \n", sizeof(long), sizeof(l));
  printf("sizeof(long long): %lu, sizeof(ll): %lu \n", sizeof(long long), sizeof(ll));
  printf("------------------\n");
  printf("sizeof(int *): %lu, sizeof(p1): %lu \n", sizeof(int*), sizeof(p1));
  printf("sizeof(char *): %lu, sizeof(p2): %lu \n", sizeof(char*), sizeof(p2));
  printf("sizeof(long long *): %lu, sizeof(p3): %lu \n", sizeof(long long *), sizeof(p3));
  printf("------------------\n");
  printf("sizeof(float): %lu, sizeof(f): %lu \n", sizeof(float), sizeof(f));
  printf("sizeof(double): %lu, sizeof(d): %lu \n", sizeof(double), sizeof(d));
  
}
```

When I run this (on my Mac OSX 64-bit machine) I get the following output: (*it may be different on your computer!*)
```
$ ./sizeof 
sizeof(char): 1, sizeof(c): 1 
sizeof(short): 2, sizeof(s): 2 
sizeof(int): 4, sizeof(i): 4 
sizeof(long): 8, sizeof(l): 8 
sizeof(long long): 8, sizeof(ll): 8 
------------------
sizeof(int *): 8, sizeof(p1): 8 
sizeof(char *): 8, sizeof(p2): 8 
sizeof(long long *): 8, sizeof(p3): 8 
------------------
sizeof(float): 4, sizeof(f): 4 
sizeof(double): 8, sizeof(d): 8 
```

Note that `long` and `long long` are both 8-bytes. Also, notice that all the various pointer types are always 8-bytes. That's because pointers store memory addresses. It doesn't matter what is at that memory address (that is what it points to), the amount of storage needed to store a memory address is always the same. 


## Signed vs. Unsigned

All data numeric types have a signed and unsigned interpretation. By default, a numeric type is considered signed, unless the `unsigned` declaration is used. 


Recall that numbers are stored in bytes, which are made up of bits. Each bit will be either a zero or one in memory, so the bits `1100` in memory (in base 2) would be `12` in base 10. But what if we defined the first bit in `1100` as telling us whether the number is positive or negative? In that case, the base 10 number would be 4, and the `1` at the start might indiciate the number should be interpreted as negative, and therefore its value could be `-4`.

When interpreting a signed value, the leading, most significant, bit of the number (or **parity** bit) determines the sign. If the bit is 1, then the value is negative, and if it's 0, then the value is positive. This leads `n-1` bits for representing a number numerically, where `n` is the number of bits in the data type. For example, an 4-byte signed `int` uses 31 bits for the numeric representation and 1 bit for the sign. An `unsigned int` uses 32 bits for the numeric representation. Both signed `int` and `unsigned int` can represent the same number of numbers, just half of the signed `int` numbers are negative. 

We can write a small programs to show this:
```c
//signess.c
#include <stdio.h>
#include <stdlib.h>

int main() {

  unsigned int smallest_uint = 0;
  unsigned int largest_uint = 4294967295; //2^32-1

  printf("Unsigned Integers\n");
  printf("--(unsigned format)--\n");
  printf("   smallest_uint= %u\n", smallest_uint);
  printf("    largest_uinn= %u\n", largest_uint);
  printf("\n");
  
  printf("--(signed format)--\n");
  printf("   smallest_uint= %d\n",smallest_uint);
  printf("     largest_uit= %d\n",largest_uint);

  printf("\n\n");
  
  int smallest_int = 2147483648; // 2^31 
  int largest_int = 2147483647; // 2^31-1

  printf("Signed Integers\n");
  printf("--(unsigned format)--\n");
  printf("   smallest_int= %u\n", smallest_int);
  printf("    largest_int= %u\n", largest_int);
  printf("\n");
  
  printf("--(signed format)--\n");
  printf("    smallest_int= %d\n",smallest_int);
  printf("     largest_int= %d\n",largest_int);

}
```
If we look at the output, we see something surprising:

```
$ ./signess 
Unsigned Integers
--(unsigned format)--
   smallest_uint= 0
    largest_uinn= 4294967295

--(signed format)--
   smallest_uint= 0
     largest_uit= -1


Signed Integers
--(unsigned format)--
   smallest_int= 2147483648
    largest_int= 2147483647

--(signed format)--
    smallest_int= -2147483648
     largest_int= 2147483647
```

The largest unsigned int is the largest negative (signed) integer, -1. That's because of 2's complement interpretation (up next) for negative values, but it also belies another important concept in programming---the underlying bits do not change, but how we interpret those bits matter. 

### 2's compliment 

The 2's compliment interpretation of negative values is somewhat counter-intuitive at first, but actually simplifies operations with negative values. 

To see an example, let's consider a signed 4-bit number. A 4-bit number has three bits for the number and 1 bit for the parity, so we could count 8 positive items (0-7) positive numbers.

```
0000 +0
0001 +1
0010 +2
0011 +3
0100 +4
0101 +5
0110 +6
0111 +7
```

On the negative side, we can also have three bits to count 8 items, -8 to -1, but we don't count quite the same way. We count backwards, starting with -8.
```
1000 -8  ---> 0111 + 1 = -1000 = -8 
1001 -7  ---> 0110 + 1 = -0111 = -7 
1010 -6  ---> 0101 + 1 = -0110 = -6
1011 -5  ---> 0100 + 1 = -0101 = -5
1100 -4  ---> 0011 + 1 = -0100 = -4
1101 -3  ---> 0010 + 1 = -0011 = -3
1110 -2  ---> 0001 + 1 = -0010 = -2
1111 -1  ---> 0000 + 1 = -0000 = -1
```
The rule for converting the bits to negative value is to compliment the bits (ones become zero, and zeros becomes ones), and add 1. 

So why do this?!? Well consider adding (or subtracting) two numbers, like 5 + (-5) -- doing the bit-wise math ...

```
     111   <--carry
    0101 (5)
   +1011 (-5)
  -----
(1)0000
 ^
 '--dropped, only a 4-bit number
```

We get `10000` but it's a 4-bit number, so we lose the leading 1, and get `0000` or 0. And that's the right answer! Also, let's look at another example, 
4-5 = -1

```
  0100 (4)
 +1011 (-5)
 -----
  1111 (-1)
```

While it may seem funky, it makes the math work. You'll learn more about this in your architecture course. 

<!-- good place for a quiz -->

# Operators

C has all the standard operators as Java. There are two classes of operators, numeric and bitwise operators.

## Numeric Operators

The numeric operators are as follows

* `x + y` : addition
* `x - y` : subtraction
* `x * y` : multiplication
* `x / y` : divisions
* `x % y` : modulo

Assignment operators
* `x += y` : add `x` to `y` and store result in `x` 
* `x -= y` : subtract `y` from `x` and store result in `x`
* `x *= y` : multiply `x` by `y` and store result in `x`
* `x /= y` : divided `x` by `y` and store result in `x`

Then there are unary addition and subtraction for adding/subtracting 1:

* `x++` : add one to `x` and return `x`
* `++x` : add one to `x` and return `x+1`
* `x--` : subtract one from `x` and return `x`
* `--x` : subtract one from `x` and return `x-1`

These subtle different is prefix/postfix incrementor/decrimentors are best shown through a program example.

<div class="side-by-side">
<div class="side-by-side-a">
```c
#include <stdio.h>
#include <stdlib.h>

int main() {
  int x = 10;

  printf("x = %d\n", x);
  printf("x++ = %d\n", x++); //prints 10, but x=11
  printf("x = %d\n", x);
  printf("++x = %d\n", ++x); //prints 12
  printf("x = %d\n", x);
  printf("\n");

  x=10;
  printf("x = %d\n", x);
  printf("x-- =%d\n", x--); //prints 10, but x=9
  printf("x = %d\n", x);
  printf("--x = %d\n", --x); //prints 8
  printf("x = %d\n", x);
  printf("\n");

}
```
</div>
<div class="side-by-side-b">
```
x = 10
x++ = 10
x = 11
++x = 12
x = 12

x = 10
x-- = 10
x = 9
--x = 8
x = 8
```
</div>
</div>

Let's run this code in the [C Visualizer](https://pythontutor.com/render.html#code=%0A%0A%23include%20%3Cstdio.h%3E%0A%23include%20%3Cstdlib.h%3E%0A%0Aint%20main%28%29%20%7B%0A%20%20int%20x%20%3D%2010%3B%0A%0A%20%20printf%28%22x%20%3D%20%25d%5Cn%22,%20x%29%3B%0A%20%20printf%28%22x%2B%2B%20%3D%20%25d%5Cn%22,%20x%2B%2B%29%3B%20//prints%2010,%20but%20x%3D11%0A%20%20printf%28%22x%20%3D%20%25d%5Cn%22,%20x%29%3B%0A%20%20printf%28%22%2B%2Bx%20%3D%20%25d%5Cn%22,%20%2B%2Bx%29%3B%20//prints%2012%0A%20%20printf%28%22x%20%3D%20%25d%5Cn%22,%20x%29%3B%0A%20%20printf%28%22%5Cn%22%29%3B%0A%0A%20%20x%3D10%3B%0A%20%20printf%28%22x%20%3D%20%25d%5Cn%22,%20x%29%3B%0A%20%20printf%28%22x--%20%3D%25d%5Cn%22,%20x--%29%3B%20//prints%2010,%20but%20x%3D9%0A%20%20printf%28%22x%20%3D%20%25d%5Cn%22,%20x%29%3B%0A%20%20printf%28%22--x%20%3D%20%25d%5Cn%22,%20--x%29%3B%20//prints%208%0A%20%20printf%28%22x%20%3D%20%25d%5Cn%22,%20x%29%3B%0A%20%20printf%28%22%5Cn%22%29%3B%0A%0A%7D&cumulative=false&curInstr=4&heapPrimitives=nevernest&mode=display&origin=opt-frontend.js&py=c_gcc9.3.0&rawInputLstJSON=%5B%5D&textReferences=false).

## Bit-wise operators

Bitwise operators manipulate the underlying the bit representations of the numbers, rather than the numeric representations. The three core operators are 

* `x | y` : bitwise or each bit of `x` and `y`
* `x &`y : bitwise and each of `x` and `y`
* `x ^ y` : bitwise xor of `x` and `y`
* `~x` : compliment the bits of `x` (flipping 0->1 and 1->0)

So for example, consider `x = 3` and `y = 1`, then `x | y = 3` 

```
   0000...0011 (x)
 | 0000...0001 (y)  
 -----------------
   0000...0011 (x | y)
```

And if we do `&` (bit-wise and), `x & y = 1`.

```
   0000...0011 (x)
 & 0000...0001 (y)  
 -----------------
   0000...0001 (x & y)
```

And if we do bit-wise xor, `x ^ y = 2`

```
   0000...0011 (x)
 ^ 0000...0001 (y)  
 -----------------
   0000...0010 (x ^ y)
```

The second class of bit-wise operators are shift operators.
* `x << y` : left shift `x` by `y` bits
* `x >> y` : right shift `x` by `y` bits

These operators move bits either left or right in the number. For example, `2 << 1` shifts the bits of the value `2` to the left by 1.

```
 0000...0010 << 1 = 0000...0100 (4)
```
As you shift you add 0's to the number, so if we right shift
```
 0000...0010 >> 1 = 0000...0001 (1)
 0000...0010 >> 2 = 0000...0000 (0)
```

Another aspect of shifting is that it's equivalent to multiplying by 2 (left shifting) or dividing by 2 (right shifting) for every shift amount. So for example:
```c
int i = 12;
printf("i << 3 = %d\n", i << 3); //prints 12*(2*2*2) = 96. 
printf("i >> 2 = %d\n", i >> 2); //prints 12/(2*2) = 3. 
```


# Advanced Types

## `struct`s

An incredibly useful tool in programming is to be able to create advanced types built upon basic types. Consider managing a pair of integers. In practice, you could declare two integer variables and manage each separately, like so:

```c
int left;
int right;
left = 1;
right = 2;
```

But that is cumbersome and you always have to remember that the variable `left` is paired with the variable `right`, and what happens when you need to have two pairs or three. It just is not manageable.

Instead, what we can do is declare a new type that is a structure containing two integers.

```c
struct pair{    //declaring a new pair type 
  int left;     //that containing two integers
  int right;
};

struct pair p1;       //declare two variables of that type
struct pair p2;

p1.left = 10;    //assign values to the pair types
p1.right = 20;

p2.left = 0;
p2.right = 5;

```

The first part is to declare the new structure type by using the keyword `struct` and specify the basic types that are members of the structure. Next, we can declare variables of that type using the type name, `struct pair`. With those variables, we can then refer to the member values, `left` and `right`, using the `.` operator. (This operates a lot like Java objects --- but, importantly(!), it is **not** an object as it doesn't encapsulate associate both data and the functions that operate on that data.)

One question to consider: *How is the data for the structure laid out in memory?* 

Another way to ask is: *How many bytes does it take to store the structure?*

In this example, the structure contains two integers, so it is 8 bytes in size. In memory, it would be represented by two integers that are adjacent in memory space.

    
    struct pair
    .--------------------.
    |.--------..--------.|
    ||<- 4B ->||<- 4B ->||
    ||  left  ||  right ||
    |'________''________'|
    '--------------------'
     <----- 8 bytes ----->

Using the `.` and the correct name either refers to the first or second four bytes, or the `left` or `right` integer within the pair. When we print its size, that is exactly what we get.

```c
printf("%lu\n", sizeof(struct pair));
```

While the pair struct is a simple example, we will see many advanced structure types that combine more varied data.

<a id="org6329683"></a>

## Defining new types with `typedef`

While structure data is ever present in the system, it is often hidden by declaring new type names. The way to introduce a new type name or type definition is using `typedef`. Here is an example for the pair structure type we declared above.

```c
typedef struct{    //declaring a new structure
  int left;        //that containing two integers
  int right;
} pair_t;          //the type name for the structure is pair_t

pair_t p1;       //declare two variables of that type
pair_t p2;

p1.left = 10;    //assign values to the pair types
p1.right = 20;

p2.left = 0;
p2.right = 5;

```

This time we declare the same type, a pair of two integers, but we gave that structure type a distinct name, a `pair_t`. When declaring something of this type, we do not need to specify that it is a structure, instead, we call it what it is, a `pair_t`. The compiler is going to recognize the new type and ensure that it has the properties of the structure.

The suffix `_t` is typically used to specify that this type is not a basic type and defined using `typedef`. This is a convention of C, not a rule, but it can help guide you through the morass of types you will see in this class and beyond when programming in C.



<a id="org1482f83"></a>

# Pointers and Arrays


<a id="org077db10"></a>

## Pointers

In C, pointers play an outsized role, and is in fact the primary reason one may want to program something in C. A pointer is extremely powerful and allows for a lot of programming control.

Some terminology first:
* A **pointer** is a data type whose value is a **memory address**. 
* A pointer **references** or **points to** a value of a given data type.
* **Dereferencing** a pointer returns the value it points to.
* ... or put another way, **following the pointer** leads to the value. 


Like all types, you must declare a pointer as a variable, and note what type of data it references. For example, `int *` are pointers to `int`s and `char *` are pointers to `char`s. Here are some basic operations associated with pointers.

-   `int * p` : pointer declaration
-   `*p` : pointer dereference, follow the pointer to the **value**
-   `&a` : Address of the variable `a`
-   `p = &a` : pointer assignment, p now references a
-   `*p = 20` : assignment via a dereference, follow the pointer and assign a the value.

Individually, each of these operations can be difficult to understand. Following a **stack diagram** (or **memory diagram**), where variables and values are modeled. For the purposes of this class, we will draw stack diagrams like this:

    +----------+-------+
    | variable | value |
    +----------+-------+

If we have a pointer variable, then we'll do this:

    +----------+-------+
    | pointer  |  .------->
    +----------+-------+

This will indicate that the value of the pointer is a memory address that references some other memory.

To codify this concept further, let's follow a running example of the following program:

```c
int a = 10, b;
int *p =  &a;
a = 20; 
b = *p; 
*p = 30;
p = &b;
```

Let us walk through it step by step [using the C Visualizer](https://pythontutor.com/c.html#code=int%20main%28%29%20%7B%0A%20%20%0A%20%20int%20a%20%3D%2010,%20b%3B%0A%20%20int%20*p%20%3D%20%20%26a%3B%0A%20%20a%20%3D%2020%3B%20%0A%20%20b%20%3D%20*p%3B%20%0A%20%20*p%20%3D%2030%3B%0A%20%20p%20%3D%20%26b%3B%0A%0A%7D%0A%0A&mode=edit&origin=opt-frontend.js&py=c&rawInputLstJSON=%5B%5D).


<a id="orgd548539"></a>

## Pointers to structures

Just like for other types, we can create pointers to structured memory. Consider for example:

```c
typedef struct{
  int left;
  int right;
} pair_t;

pair_t pair;
pair.left = 1;
pair.right = 2;

pair_t * p = &pair;

```

This should be familiar to you as we can treat `pair_t` just like other data types, except we know that it is actually composed of two integers. However, now that `p` references a `pair_t` how do we deference it such that we get the member data? Here is one way.

```c

printf("pair: (%d,%d)\n", (*p).left, (*p).right);

```

Looking closely, you see we first use the `*` operator to deference the pointer, and then the `.` operator to refer to a member of the structure. That is a lot of work because we will frequently need to access members of structures via a pointer reference. So there is a shortcut! The arrow or `->`, which dereferences and then does member reference for pointers to structures. Here is how that looks:

```c
printf("pair: (%d,%d)\n", p->left, p->right); //prints (1,2) 

p->left  = 1845; //also use for left-hand of assignment
p->right = 2017;

printf("pair: (%d,%d)\n", p->left, p->right); //prints (2017,1845)
```

See how this code behaves using the [C Visualizer](https://pythontutor.com/c.html#code=typedef%20struct%20pair%20%7B%0A%20%20int%20left%3B%0A%20%20int%20right%3B%0A%7D%20pair_t%3B%0A%0Aint%20main%28%29%20%7B%0A%20%20%0A%20%20pair_t%20pair%3B%0A%20%20pair.left%20%3D%201%3B%0A%20%20pair.right%20%3D%202%3B%0A%0A%20%20pair_t%20*%20p%20%3D%20%26pair%3B%0A%20%20%0A%20%20printf%28%22pair%3A%20%28%25d,%25d%29%5Cn%22,%20%28*p%29.left,%20%28*p%29.right%29%3B%0A%20%20printf%28%22pair%3A%20%28%25d,%25d%29%5Cn%22,%20p-%3Eleft,%20p-%3Eright%29%3B%20//prints%20%281,2%29%20%0A%0A%20%20p-%3Eleft%20%20%3D%201845%3B%20//also%20use%20for%20left-hand%20of%20assignment%0A%20%20p-%3Eright%20%3D%202017%3B%0A%0A%20%20printf%28%22pair%3A%20%28%25d,%25d%29%5Cn%22,%20p-%3Eleft,%20p-%3Eright%29%3B%20//prints%20%282017,1845%29%0A%7D%0A%0A&mode=edit&origin=opt-frontend.js&py=c_gcc9.3.0&rawInputLstJSON=%5B%5D).

<a id="org535a9c8"></a>

## Array Types

The last type are array types which provides a way for the program to declare an arbitrary amount of the same type in continuous memory. Here is a simple example with an array of integers:

```c

int array[10]; //declare an array of 10 integers

//assign to the array
for(int i = 0; i < 10; i++){
  array[i] = 2 * i; //index times 2
 }

//reference the array
for(int i = 0; i < 10; i++){
  printf("%d:%d\n", i, array[i]); 
 }

```

running this program we get:

```
    $ ./array-example
    0:0
    1:2
    2:4
    3:6
    4:8
    5:10
    6:12
    7:14
    8:16
    9:18
```

We declare an array using the `[ ]` following the variable name. Unlike in Java, we don't need to use a `new` operator to actually create the array. Instead the array is allocated automatically when declared within a function. 
 
We use the term **index** to refer to an element of an array. Above, the array `array` is of size 10, which means that we can use indexes 0 through 9 (computer scientist start counting at 0). To index the array, for both retrieval and assignment, we use the `[ ]` operators as well.


### Managing array length and out of bounds

Unlike in Java, C doesn't have a mechanism for combining the length of an array with the array itself. That's because arrays are not objects, but simply a type. This means you could index outside the bands of the array. 

```c
#include <stdio.h>

int main() {
  int b = 30;
  
  //statically declare array values with { }
  int array[10] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

  for(int i = 0; i < 15; i++) {
    printf("array[%d] = %d\n", i, array[i]);
  }

  printf("-----\n");

  for(int i = 0; i > -15; i--) {
    printf("array[%d] = %d\n", i, array[i]);
  }
    
}
```

If we run this program, it prints things out without error, even negative indexes! But what are these values?

```
$ ./arrayoutofbounds 
array[0] = 0
array[1] = 1
array[2] = 2
array[3] = 3
array[4] = 4
array[5] = 5
array[6] = 6
array[7] = 7
array[8] = 8
array[9] = 9
array[10] = 1196032059
array[11] = 1723250372
array[12] = -345625960
array[13] = 32766
array[14] = 1846066377
-----
array[0] = 0
array[-1] = 0
array[-2] = 30
array[-3] = 15
array[-4] = -4
array[-5] = 32766
array[-6] = -345625968
array[-7] = 32766
array[-8] = -345625944
array[-9] = 1
array[-10] = 73010927
array[-11] = 32766
array[-12] = -345625984
array[-13] = 1723250372
array[-14] = 1196032059
```

This is arbitrary memory values in your program that exist out of the bounds of the array. It's very easy to accidentally go out of the bounds of an array and cause an error in your program, even if the program doesn't report any errors. This is called a memory violation, as you are accessing memory you shouldn't.

In general, then, it's up to you track the size of the array, and pass that size to other functions that need that array so that you know how big it is.

Note that the [C Visualizer](https://pythontutor.com/c.html#code=%23include%20%3Cstdio.h%3E%0A%0Aint%20main%28%29%20%7B%0A%20%20int%20b%20%3D%2030%3B%0A%20%20%0A%20%20//statically%20declare%20array%20values%20with%20%7B%20%7D%0A%20%20int%20array%5B10%5D%20%3D%20%7B0,%201,%202,%203,%204,%205,%206,%207,%208,%209%7D%3B%0A%0A%20%20for%28int%20i%20%3D%200%3B%20i%20%3C%2015%3B%20i%2B%2B%29%20%7B%0A%20%20%20%20printf%28%22array%5B%25d%5D%20%3D%20%25d%5Cn%22,%20i,%20array%5Bi%5D%29%3B%0A%20%20%7D%0A%0A%20%20printf%28%22-----%5Cn%22%29%3B%0A%0A%20%20for%28int%20i%20%3D%200%3B%20i%20%3E%20-15%3B%20i--%29%20%7B%0A%20%20%20%20printf%28%22array%5B%25d%5D%20%3D%20%25d%5Cn%22,%20i,%20array%5Bi%5D%29%3B%0A%20%20%7D%0A%20%20%20%20%0A%7D%0A&mode=edit&origin=opt-frontend.js&py=c_gcc9.3.0&rawInputLstJSON=%5B%5D) will complain if you try to do this sort of thing.

### `sizeof` on arrays

You may be tempted to use the `sizeof` function to solve the issue of array sizing, but it doesn't quite do what you expect. Recall that the sizeof function will return: *how many bytes does it take to store that array?*

In the examples above `array` is array of 10 integers, each of 4-bytes in size, so it takes 40 bytes to store the `array`. But it only has 10 indexable integers. 

The `sizeof` is useful for understanding the memory storage needs of arrays, but it is not a good choice for managing interaction of the array. There are many situations where it will cause nuanced errors in your program, and it is also considered poor programming practice.  



## Pointers to Arrays

Now, it is time to blow your mind. It turns out that in C arrays and pointers can function in the same way. Seriously. They are not really the same, but you can think of them as the same, and should for many contexts.


Let me demonstrate. First consider, what is the value of `array`? It's a variable, so it should have a value, right? Let's try and print it out. 

```c
#include <stdio.h>

int main() {
  int array[10] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

  printf("array: %lu (as unsigned number)\n", (unsigned long) array);
  printf("array: %p  (as hexadecimal number)\n", array);
  
}
```
Running this, we get
```
$ ./arrayvalue 
array: 140732826151536 (as unsigned number)
array: 0x7ffeea1c6a70  (as hexadecimal number)
```

Those are *none* of the integers are assigned into the array. In fact, it's a pointer to those integers. And if it's a pointer, then I can store it as a value in a pointer type, like in the below program.

```c
/*pointer-array.c*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

  int array[10];
  int i;
  int * p = array; //p points to array

  //assign to the array  
  for(i = 0; i < 10; i++){
    array[i] = 2 * i; //index times 2
  }

  //derefernce p and assign 2017
  *p = 2017;

  //print the array
  for(i = 0; i < 10; i++){
    printf("%d:%d\n", i, array[i]); 
  }

}
```

    $ ./pointer-array 
    0:2017
    1:2
    2:4
    3:6
    4:8
    5:10
    6:12
    7:14
    8:16
    9:18

Notice that at index 0 the value is now 2017. Also notice that when you assigned the pointer value, we did not take the address of the array. That means p is really referencing the address of the first item in the array and for that matter, so is `array`!

It gets crazier because we can also use the `[ ]` operators with pointers. Consider this small change to the program:

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

  int array[10];
  int i;
  int * p = array; //p points to array

  //assign to the array  
  for(i = 0; i < 10; i++){
    array[i] = 2 * i; //index times 2
  }

  //index p at 5  and assign 2017
  p[5] = 2017;   //<---------------!!

  //print the array
  for(i = 0; i < 10; i++){
    printf("%d:%d\n", i, array[i]); 
  }

}
```

    $ ./pointer-array-index 
    0:0
    1:2
    2:4
    3:6
    4:8
    5:2017 //<---------!!!
    6:12
    7:14
    8:16
    9:18

In this case we indexed the pointer at 5 and assigned to it the value 2017, which resulted in that value appearing in the output. 

*What is the implication of this?*

We know that `p` is a pointer and we know to assign to the value referenced by a pointer it requires a dereference, so the `[ ]` must be a dereference operation. And it is. In fact we can translate the `[ ]` operation like so:

```
p[5] = *(p+5)
```

What the `[ ]` operation does is increments the pointer by the index and then dereference. As a stack diagram, we can visualize this like so:

```
    .--------------.
    |       |    0 |<--<-. array
    |-------+------|     |
    |       |    2 |     |
    |-------+------|     |
    |       |    4 |     |
    |-------+------|     |
    |       |    6 |     |
    |-------+------|     |
    |       |    8 |     |
    |-------+------|     |
    |       | 2017 |<----+----- p+5, array+5
    |-------+------|     |
    |       |   12 |     |
    |-------+------|     |
    |       |   14 |     |
    |-------+------|     |
    |       |   16 |     |
    |-------+------|     |
    |       |   18 |     |
    |-------+------|     |
    | p     |   .--+-----'
    '-------+------'
```

This is called **pointer arithmetic**, which is a bit complicated, but we'll return to it later when discussing strings. The important take away is that there is a close relationship between pointers and arrays. And now you also know why arrays are indexed starting at 0 --- it is because of pointer arithmetic. The first item in the array is the same as just dereferencing the pointer to the array, thus occurring at index 0.

Before I described that relationship as the same, but they are not exactly the same. Where they differ is that **pointers can be reassigned like any variable, but arrays cannot**. They are constants references to the start of the array. For example, this is not allowed:

```c

int a[10];
int b[10];
int *p;

p = a; // ok

b = p; // not ok!

```

Array pointers are constant, we cannot reassign to them. Later, it will become obvious why; if we could reassign the array pointer, then how would reclaim that memory? The answer is you could not. It would be lost...but we haven't talked about reclaiming memory yet.


## Arrays of Structures

Just like with other types, you can have arrays of structures, which are simply memory aligned structures. For example, we can make a stack/memory diagram of the following program

```c
struct floatint {
  float f;
  int i;
};

int main() {
  struct floatint array[3];

  //use . operator as 
  array[0].f = 3.14;
  array[0].i = 10;
  
  array[1].f = 6.8;
  array[1].i = 5;

  struct floatint * p;
  p = &array[2]; //p references last floatint in array
  p->f = 2.7; //use -> operator to derefence
  p->i = 11;
  
}
```
When accessing the structures in the array directly, we can use the `.` operator as the `[]` dereferences (implicitly) that struct. However with `p`, since it points to a struct in the array, we can use the `->`. We can view the memory diagram of this program 

Let's check this code out in the [C Visualizer](https://pythontutor.com/c.html#code=struct%20floatint%20%7B%0A%20%20float%20f%3B%0A%20%20int%20i%3B%0A%7D%3B%0A%0Aint%20main%28%29%20%7B%0A%20%20struct%20floatint%20array%5B3%5D%3B%0A%0A%20%20//use%20.%20operator%20as%20%0A%20%20array%5B0%5D.f%20%3D%203.14%3B%0A%20%20array%5B0%5D.i%20%3D%2010%3B%0A%20%20%0A%20%20array%5B1%5D.f%20%3D%206.8%3B%0A%20%20array%5B1%5D.i%20%3D%205%3B%0A%0A%20%20struct%20floatint%20*%20p%3B%0A%20%20p%20%3D%20%26array%5B2%5D%3B%20//p%20references%20last%20floatint%20in%20array%0A%20%20p-%3Ef%20%3D%202.7%3B%20//use%20-%3E%20operator%20to%20derefence%0A%20%20p-%3Ei%20%3D%2011%3B%0A%20%20%0A%7D%0A&mode=edit&origin=opt-frontend.js&py=c_gcc9.3.0&rawInputLstJSON=%5B%5D).


## Passing Arrays to Functions

Let's consider a simple example of a function that sums the number in an array list returning the result. The main function could look like the following:

```c

int main() {
 int nums[10];
 
 for(int i = 0; i < 10; i++){
    nums[i] = (i + 7) * 2 / 3; //just some math
 }

 int s = sum(nums);
}

```

But the function call to `sum(nums)` could be problematic. What is actually being passed as the argument to the function `sum`? `nums` is a pointer to the start of the array, not the array itself, so `sum` doesn't know anything about the length of the array. So we need to ALSO pass the length of the array to `sum`.


```c
int main() {
 int nums[10];
 
 for(int i = 0; i < 10; i++){
    nums[i] = (i + 7) * 2 / 3; //just some math
 }

 int s = sum(nums, 10); //<--- now passing the length 
}

```

This gives us the following function

```c
int sum(int * array, int len) {
  int s = 0;
  for(int i = 0 ; i < len; i++){
     s += array[i];
  }
  return s;
}
```

Check out how it runs using the [C Visualizer](https://pythontutor.com/c.html#code=int%20sum%28int%20*%20array,%20int%20len%29%20%7B%0A%20%20int%20s%20%3D%200%3B%0A%20%20for%28int%20i%20%3D%200%20%3B%20i%20%3C%20len%3B%20i%2B%2B%29%7B%0A%20%20%20%20%20s%20%2B%3D%20array%5Bi%5D%3B%0A%20%20%7D%0A%20%20return%20s%3B%0A%7D%0A%0Aint%20main%28%29%20%7B%0A%20%20int%20nums%5B10%5D%3B%0A%20%0A%20%20for%28int%20i%20%3D%200%3B%20i%20%3C%2010%3B%20i%2B%2B%29%7B%0A%20%20%20%20nums%5Bi%5D%20%3D%20%28i%20%2B%207%29%20*%202%20/%203%3B%20//just%20some%20math%0A%20%20%7D%0A%0A%20%20int%20s%20%3D%20sum%28nums,%2010%29%3B%20//%3C---%20now%20passing%20the%20length%20%0A%7D%0A%0A%0A&mode=edit&origin=opt-frontend.js&py=c_gcc9.3.0&rawInputLstJSON=%5B%5D).

Note that we call `array` in `sum` an `int *`, rather than an `int[]`. That's because `array` is a pointer to the start of the array `nums`, and as we saw before, we can use that pointer as if it is an array. We can see this in the following memory diagram

```
             .-------------------------.
             |                         |
 .-------.   |           .----------.  |
 |   | 0 | <-'- nums     | array| .-+--'
 |---+---|               |------+---|
 |   | 5 |               |   len| 10|
 |---+---|               |------+---|
 :   :   :               |    s |   |
 |---+---|               '----------'
 | s |   |               sum memory diagram
 '-------'
 main memory diagram
```


But this can sometimes cause issues because `array`'s value can be changed. It's just a pointer type. It's not a constant

```
array = &s; //totally fine
```

To avoid this, you can instead declare it as an array, like below


```c
int sum(int array[], int len);
```

When you do that, `array` is now an array type and thus it can not be assigned to. Another way to accomplish the same is to use `const` declaration

```c
int sum(const int * array, int len);
```

When you read man pages and documentation, and you see `const`, this may mean that it's expecting an array to be passed.
