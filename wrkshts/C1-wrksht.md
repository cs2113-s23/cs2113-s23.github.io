---
layout: worksheet
permalink: /worksheet/c1
showsolution: true
---

# Worksheet: C1

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.

<!--
* Github Classroom Link: [https://classroom.github.com/a/nnkSgVZj](https://classroom.github.com/a/nnkSgVZj)-->


* Github Classroom Link: [https://classroom.github.com/a/xZ8Vg93M](https://classroom.github.com/a/xZ8Vg93M)

## Questions





### q
For each of the statements, indicate if it evaluates to True? (Assume all needed headers are included).

```c
a) sizeof(int) == sizeof(int *)
b) sizeof(char) == sizeof(char *)
c) sizeof(short) == sizeof(short *)
d) sizeof(long) == sizeof(long *)
e) sizeof(char *) == sizeof(short *)
```

#### s

```c
a) False // size of (int) is 4 bytes while size of (int *) is 8 bytes
b) False // size of (char) is 1 byte while size of (char *) is 8 bytes
c) False // size of (short) is 2 bytes while size of (short *) is 8 bytes
d) True // size of (long) is 8 bytes and size of (long *) is also 8 bytes
e) True // size of (char *) is 8 bytes and size of (short *) is also 8 bytes
```

### q
Examine the following program closely, and describe what happened when it is run. What output do you expect?
```c
unsigned int bigger_largest_uint = 4294967295 + 1; //largest_uint + 1
int bigger_largest_int = 2147483647 + 1; //largest_int + 1

//Before you run this, what do you expect the value to be
if (4294967295 < bigger_largest_uint){
    printf("A");
}
else{
    printf("B");
}

if (2147483647 < bigger_largest_int){
    printf("C");
}
else{
    printf("D");
}
```

#### s

* The expected output is `BD`.
* For unsigned int

  ```
     1111......111 (32 1s = 4294967295)
     +           1
     -------------------
     1000......000 (1 followed by 32 0s = 0)
```

  Thus, `bigger_largest_uint = 0` is less than `4294967295`.

* For int,

  ```
     0111......111 (0 followed by 31 0s = 2147483647)
     +           1
     -------------------
     1000......000 (1 followed by 31 0s = -2147483648)
```

Thus, `bigger_largest_int = -2147483648` is less than `2147483647`.

### q
Describe a situation in which the difference between x++ and ++x would make a significant difference in the operation of the program.

#### s

```c
int a = 5;
int b = 10;
int c = a * b++; // Results c=5*10=50 and b=11
int d = a * ++b; // Results d=5*11=55 and b=11
```

As shown above, the order of operation can have significant impact on the results of calculations.

### q
Why does the expression

```c
(3 & 4) && (1)
```
    
differ from
    
```c
(3 && 4) && (1)
```
Your answer should be specific to the output and the underlying reason why they differ.

#### s

* `(3 & 4) && (1)` gives the output of 0.
* `(3 && 4) && (1)` gives the output of 1.
* In the first expression, we had a bit-wise and between 3 and 4

```
  011 (3)
 +100 (4)
 -----
  000 (0)
 ```
Now `(3 & 4) && (1)` becomes `(0) && (1)` which equals to 0.
* In the second expression, we have an `and` operation between 3 and 4 first and then an `and` operation with 1. Since all values are non-zero, thus not False, the result of the expression is 1.


### q
What is the output for a, b, and c?

   ```c
   int a = 1 << 1;
   int b = a << 1;
   int c = b << 2;
   ```
   Can you find a closed-form formula for shifting bits, using the variables x, y, and z?

   ```c
   int z = x << y
   ```

#### s
* The output for a, b, and c are 2, 4 and 16.

```c
1 << 1 = 10 (2)
10 << 1 = 100 (4)
100 << 2 = 10000 (16)
```

Closed-form formula for shifting bits is:
```c
int z = 2^y * x
```

### q
As we discussed in class a pointer (`*`) is a variable that stores a memory address. Pointers are further distinguished by the data type that lies at the store memory address. For example `int * p;` declares a variable `p` that is a pointer to an integer data type. Another way to think about this is `p` stores the address of a place in memory, that contains integer data.

 Also remember that we can assign a pointer with the `&` operator, which gets the address of the variable it is applied to.
```c
int c = 45;
int * p = &c;
```

Based on your best reasoning, answer the questions below about pointers (which we will officially cover in future classes). Try your best reason about the questions below without looking them up. Remember, worksheets are about learning and getting experience with class material :)

1. What do you think the following declaration signifies? `(int *) * c;`
2. Do think that `(int *) * c;` is an equivalent declaration to `int ** c;`?

Finally, draw a memory diagram for each of the designated marks. You can upload the drawing or ASCII art to your repo.
```c
int main(){
    int a = 1;
    int b = 2;
    int c = 3;
    int * p = &a;
    int ** q = &p; //Mark 0
    *p = 15; //Mark 1
    p = &b;
    **q = 0; //Mark 2

}
```
Mark 0
   ```
.----------.-------.
| variable | value |
|----------+-------|
| a        | 1     | <-.
|----------+-------|   |        
| b        | 2     |   |       
|----------+-------|   |       
| c        | 3     |   |       
|----------+-------|   |         
| p        | .-----|---' <----.        
|----------+-------|          |   
| q        |   .---+----------'  
|------------------|             

```

#### s

1. It is a double pointer. In other words, it is a pointer to an integer pointer. This means that c contains a memory address. At the memory address stored in `c`, is another memory address. If you follow the address stored in `c`, and then follow the second memory address, we will find integer data in memory.
2. Yes they are equivalent. Frequently, double pointers are written without parenthesis (*programmers ~~are lazy~~ try to be efficient.*). Parenthesis can be used to help visually understand the code easier.


Mark 1
   ```
.----------.-------.
| variable | value |
|----------+-------|
| a        | 15    | <-.
|----------+-------|   |        
| b        | 2     |   |       
|----------+-------|   |       
| c        | 3     |   |       
|----------+-------|   |         
| p        | .-----|---' <----.        
|----------+-------|          |   
| q        |   .---+----------'  
|------------------|             

```
Mark 2

   ```
.----------.-------.
| variable | value |
|----------+-------|
| a        | 15    | 
|----------+-------|          
| b        | 0     | <-.       
|----------+-------|   |       
| c        | 3     |   |       
|----------+-------|   |         
| p        | .-----|---' <----.        
|----------+-------|          |   
| q        |   .---+----------'  
|------------------|             

```

### q
Consider the following snippet of `c` code

```c
int a[] = {1, 2, 3, 4};
short b[] = {1, 2, 3, 4};
char c[] = {1, 2, 3, 4};
int * p = a;   
```

a. What is the `sizeof(a[0])`?
b. What is the `sizeof(a)`?
c. What is the `sizeof(b)`?
d. What is the `sizeof(c)`? 
e. What is the `sizeof(p)`?
f. What is the `sizeof(*p)`?
g. Explain the differences in the `sizeof` for each of these output?

#### s

a. What is the `sizeof(a[0])`? 4

b. What is the `sizeof(a)`? 16

c. What is the `sizeof(b)`? 8

d. What is the `sizeof(c)`? 4

e. What is the `sizeof(p)`? 8

f. What is the `sizeof(*p)`? 4

g. Explain the differences in the `sizeof` for each of these output?
&nbsp;&nbsp;&nbsp;&nbsp; a. Size of `a[0]` is the same as size of an int, which is 4 bytes.
&nbsp;&nbsp;&nbsp;&nbsp; b. Size of `a` is the memory taken by the entire array of 4 ints, which is 4*4=16 bytes.
&nbsp;&nbsp;&nbsp;&nbsp; c. Size of `b` is the memory taken by the entire array of 4 shorts, which is 4*2=8 bytes.
&nbsp;&nbsp;&nbsp;&nbsp; d. Size of `c` is the memory taken by the entire array of 4 chars, which is 4*1=4 bytes.
&nbsp;&nbsp;&nbsp;&nbsp; e. `p` is a pointer of `a`, which we know pointers have size of 8 bytes.
&nbsp;&nbsp;&nbsp;&nbsp; f. Size of `*p` is dereferencing `p` which points it to the first element of array `a`, resulting in an int of size 4 bytes.


### q
Consider the following snippet of `c` code. Upload an image to the repo and/or include it in the Markdown file. (You can also do it in ASCII art, like in lecture notes).

```c
int a = 5;
int b = a;
int c[2];

int *p1 = &a;
int *p2 = c;
*p2 = b;
c[1] = a;
//MARK 1
   
*p1 = *p2;
b = 20;
//MARK 2
   
```

a. Draw the memory diagram at `MARK 1`
b. Draw the memory diagram at `MARK 2`
 
#### s

a. Memory diagram at `MARK 1`

   ```
.----------.-------.
| variable | value |
|----------+-------|
| a        | 5     |<---------.
|----------+-------|          | 
| b        | 5     |          |
|----------+-------|          |
| c[0]     | 5     | <--------|--. <-- c
|----------+-------|          |  |
| c[1]     | 5     |          |  |
|----------+-------|          |  |
| p1       |   .---+----------'  |
|----------+-------|             |
| p2       |   .---+-------------'
'----------'-------'
```

b. Memory diagram at `MARK 2`

```
.------------------.
| variable | value |
|----------+-------|
| a        | 5     |<---------.
|----------+-------|          | 
| b        | 20    |          |
|----------+-------|          |
| c[0]     | 5     | <--------|--. <-- c
|----------+-------|          |  |
| c[1]     | 5     |          |  |
|----------+-------|          |  |
| p1       |   .---+----------'  |
|----------+-------|             |
| p2       |   .---+-------------'
'----------+-------'
```

### q
 
For the following snippet of `c` code, draw a memory diagram for each step in the `for` loop at the `MARK`. (This requires drawing **4** memory diagrams) Upload an image to the repo and/or include it in the Markdown file. (You can also do it in ASCII art, like in lecture notes).

```c
#include <stdio.h>

int foo[4] {4, 7, 3, 9};
int * bar = NULL;
int * other = NULL;
    
int main() {
    for (int i = 0; i < 4; i++) {
        if (i % 2 == 0) {
            foo[i] = foo[i] % 2;
            bar = &(foo[i])
        }
        else {
            foo[i] = foo[i] * 2;
            other = &(foo[i])
        }
    //MARK
    }
}
```

#### s

```
    +----------+-------+
    | variable | value |
    +----------+-------+
    |          | 0     | <--<-.foo<---+
    +----------+-------+              |
    |          | 7     |              |
    +----------+-------+              |
    |          | 3     |              |
    +----------+-------+              |
    |          | 9     |              |
    +----------+-------+              |
    | bar      |   +------------------+
    +----------+-------+
    | other    | NULL  |
    +----------+-------+
    | i        | 0     |
    +----------+-------+
```

```
    +----------+-------+
    | variable | value |
    +----------+-------+
    |          | 0     | <--<-.foo<---+
    +----------+-------+              |
    |          | 14    | <------------|-+
    +----------+-------+              | |
    |          | 3     |              | |
    +----------+-------+              | |
    |          | 9     |              | |
    +----------+-------+              | |
    | bar      |   +------------------+ |
    +----------+-------+                |
    | other    |   +--------------------+
    +----------+-------+
    | i        | 1     |
    +----------+-------+
```

```
    +----------+-------+
    | variable | value |
    +----------+-------+
    |          | 0     | <--<-.foo
    +----------+-------+
    |          | 14    | <--------------+
    +----------+-------+                |
    |          | 1     | <------------+ |
    +----------+-------+              | |
    |          | 9     |              | |
    +----------+-------+              | |
    | bar      |   +------------------+ |
    +----------+-------+                |
    | other    |   +--------------------+
    +----------+-------+
    | i        | 2     |
    +----------+-------+
```

```
    +----------+-------+
    | variable | value |
    +----------+-------+
    |          | 0     | <--<-.foo
    +----------+-------+
    |          | 14    |
    +----------+-------+
    |          | 1     | <------------+
    +----------+-------+              |
    |          | 18    | <------------|-+
    +----------+-------+              | |
    | bar      |   +------------------+ |
    +----------+-------+                |
    | other    |   +--------------------+
    +----------+-------+
    | i        | 3     |
    +----------+-------+
```

### q

Fill in the `sum` function so that it adds the elements in array.

```c
#include <stdio.h>

int sum(<ARGUMENTS GO HERE!>) {
    //Code goes here!
}

int main() {
    int array[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int s = sum(array);
    printf("%d\n", s);
}
```

#### s

```c
#include <stdio.h>

int sum(int arr[], int len) {
    int return_value = 0;                   // Inserted line

    for(int i = 0; i < len; i++){           // Inserted line
        return_value += arr[i];             // Inserted line
    }                                       // Inserted line

    return return_value;                    // Inserted line
}

int main() {
    int array[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int len = 10;                           // Inserted line
    int s = sum(array, len);                // Updated line
    printf("%d\n", s);
}
```

### q
Using the following `struct` declaration, print out the `f` member of `FOO`. (Hint: It might be helpful to first figure out a value that you can then debug with!)
```c
#include <stdio.h>

int main() {

    struct bar {
        float f;
        double d;
    };

    struct foo {
        int i;
        int j;
        struct bar * BAR;
    };
    
    struct foo * FOO;

    // ... some code that sets up foo!
    
    printf("f = %g", <FILL-IN-HERE> ); //finish this line.
}
```

#### s

The preferred way to access `f` is `FOO->BAR->f`. 

### q
 Using the following struct definition:
```c
typedef struct {
    short head;
    int left_arm;
    int * right_arm;
    char left_leg;
    long * right_leg;
} frankenstein;

frankenstein monster;


//size of the monster's parts
int head_length = sizeof(monster.head);
int left_arm_length = sizeof(monster.left_arm);
int right_arm_length = sizeof(monster.right_arm);
int left_leg_length = sizeof(monster.left_leg);
int right_leg_length = sizeof(monster.right_leg);
int height = sizeof(monster);
```

First, without running the code, write down the expected sizes of each of the `int` length variables above, including `height`. 

Second, write a program to reveal the *actual* sizes. The `height` may differ than what you expect, right? Why might you think that's case. (We'll go over the true reason in class)

#### s

```
head_length = 2
left_arm_length = 4
right_arm_length = 8
left_leg_length = 1
right_leg_length = 8
height_expected = 23
height = 32
```

Note that 2 + 4 + 8 + 1 + 8 = 23 but that does not equal the height 32. The monster is *not* the sum of its part. The reason for this is because of padding. C wants to have nice round data elements that are roughly 4-bytes aligned. So it rounds up each of the members, padding them out to 4-bytes (or 8-bytes). After that padding, you get 32 bytes. 

