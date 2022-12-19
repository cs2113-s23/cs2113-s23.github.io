---
layout: worksheet
permalink: /worksheet/c2
showsolution: true
---

# Worksheet: C2

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.

<!--
* Github Classroom Link: [https://classroom.github.com/a/lpad6pMF](https://classroom.github.com/a/lpad6pMF) -->

* Github Classroom Link: [https://classroom.github.com/a/Po7P0CVu](https://classroom.github.com/a/Po7P0CVu)

## Note

Attempt to answer these questions before running the code. This will improve your ability to analyze and reason about code without an IDE or compiler. This skill we be helpful on the exams.

## Questions

### q
Examine the program below and answer the following questions.

```c
#include <stdio.h>

int main() {
    char a[3] = "Go Buff!";

    printf("%s\n", a);
}
```
    
* Explain why the full string `"Go Buff!"` will not print.
* Provide **two** ways to fix the program so the full string would print.

#### s

* Because `a` is declared to have length 3, it only stores the first 3 characters of `"Go Buff!"` which are `"Go "`.
* Method 1: Change the declaration of `a` to `char a[9] = "Go Buff!";` because there are 8 characters in `"Go Buff!"`.
* Method 2: Change the declaration of `a` to `char a[] = "Go Buff!";` so we do not explicitly declare the length to the string `a`.
* Method 3: Change the declaration of `a` to `char *a = "Go Buff!";` so we have a pointer to a string.

### q
Examine the program below and answer the following questions.

```c
#include <stdio.h>
#include <string.h>

int main() {
    char str[] = "string";

    if (sizeof(str) == strlen(str)) {
        printf("equal\n");
    } else if (sizeof(str) > strlen(str)) {
        printf("greater than\n");
    }
}
```

* What is the output of this program?
* How is the sizeof function different from the strlen function?

#### s

* The output of this program is `greater than`.
* The sizeof function gives how much memory is used (in terms of bytes) to store `str`, which is 7 because there is a null terminator at the end of `"string"`.
* The strlen function simply gives the length of the string `str`, which is 6 because there are 6 characters in `"string"`.

### q

Consider the following code snippet

```c
#include <stdio.h>
#include <string.h>

int main() {
  char str[] = "A string";

  printf("sizeof(str) =  %lu\n", sizeof(str));
  printf("strlen(str) =  %d\n", strlen(str));
  
}
```

* What is the output of this program?
* Why does the sizes differ?

#### s

* The output of this program is
  ```
  sizeof(str) =  9
  strlen(str) =  8
  ```

* The sizes are different because the `sizeof` function includs the terminating symbol ('\0') of `str` while the `strlen` function returns the number of characters in the string ignoring the terminating symbol. Consequently, `sizeof(str)` returns 9 while `strlen(str)` returns 8 since there are 8 symbols in string `"A string"`.


### q

Consider the following code snippet

```c
#include <stdio.h>
#include <string.h>

int main() {
  char * str = "A string";

  printf("sizeof(str) =  %lu\n", sizeof(str));
  printf("strlen(str) =  %d\n", strlen(str));
  
}
```


* What is the output of this program?
* Why are the sizes same or different?

#### s

* The output of this program is
  ```
  sizeof(str) =  8
  strlen(str) =  8
  ```
* The sizes happens to be the same. `sizeof(str)` is 8 due to the actual size of pointers and `strlen(str)` is 8 due to there are 8 characters in `str`.

### q

Examine the program below and answer the following questions.

```c
#include <stdio.h>
#include <string.h>

int main() {
    char a[] = "aaa";
    char b[] = "bbb";

    if (strcmp(a, b) == -1) {
        printf("aaa is less than bbb\n");
    }

    if (strcmp(b, a) == 1) {
        printf("bbb is greater than aaa\n");
    }
}
```

* What is the output of this program?
* Explain how the function `strcmp` from the `string.h` library works.

#### s

* The output of this program is
  ```
  aaa is less than bbb
  bbb is greater than aaa
  ```
* The function strcmp takes 2 string arguments, s1 and s2 e.g.`strcmp(s1, s2)`. The return value is 1 when s1 is greater alphabetically, is 0 when the two strings are identical and is -1 when s1 is smaller alphabetically.


### q
Examine the program below and answer the following questions.

```c
#include <stdio.h>
#include <string.h>

int main() {
    char str1[] = "10 9 8 7 6 5 4 3 2 1";
    char * str2 = "10 9 8 7 6 5 4 3 2 1";
        
    if (strcmp(str1, str2) == 0) { //tests if they are the same string
        printf("Same\n");    
    }
    else {
        printf("Different\n");
    }
}
```

* What is the output of this program?
* Describe how a char * and an char array are similar.

#### s

* The output of this program is `Same`.
* Both char * and char array can be used to represent a string.


### q
Examine the program below and answer the following questions.

```c
#include <stdio.h>
#include <string.h>

int main() {
    char str1[10] = "10 9 8 7 6 5 4 3 2 1";
    char * str2 = "10 9 8 7 6 5 4 3 2 1";
        
    if (strcmp(str1, str2) == 0) { //tests if they are the same string
        printf("Same\n");    
    }
    else{
        printf("Different\n");
    }
}
```

* What is the output of this program?
* Why is it different then the output from the previous question.

#### s

* The output of this program is `Different`.
* The output is different from the previous question because `str1` was declared to have length 10 which means it only contains the string `10 9 8 7 6`. This is different from `str2`, which equal to `10 9 8 7 6 5 4 3 2 1`.


### q

The following program is WRONG!

```c
#include <stdio.h>
#include <string.h>

int main() {
    char str[100] = "C is my favorite language";

    str[strlen(str) + 0] = '!';
    str[strlen(str) + 1] = '!';
    str[strlen(str) + 3] = '\0';
    
    printf("%s And python is my second!\n", str);
}
```

We want the output to be
```
C is my favorite language!! And python is my second!
```

But the output is this instead (note the single `!`)

```
C is my favorite language! And python is my second!
```

Answer the following questions:
* Why did the program not function as expected?
* Provide a corrected version of the program

#### s

* `strlen(str)` is incrementing as we add characters to `str`. The first time we do `str[strlen(str)+0]`, character `!` is added to the end of the string. Consequently, `strlen(str)` also incremented by 1, from 25 to 26. Then, we want to add another `!` by doing `str[strlen(str)+1]` which did not work because at this point, `strlen(str)+1` is 26 and adding to the 27th character will result in having a character after the terminating character.
* Fixes for the program can be
  ```c
  #include <stdio.h>
  #include <string.h>
  
  int main() {
      char str[100] = "C is my favorite language";
  
      int len = strlen(str);                           // New line fixing the code
  
      str[len+0] = '!';                                // Changing strlen(str) to the new variable len
      str[len+1] = '!';                                // Changing strlen(str) to the new variable len
      str[len+3] = '\0';                               // Changing strlen(str) to the new variable len
      
      printf("%s And python is my second!\n", str);
  }
  ```
  or
  ```c
  #include <stdio.h>
  #include <string.h>
  
  int main() {
      char str[100] = "C is my favorite language";
      
      str[strlen(str)] = '!';                                // Changing strlen(str)+0 to strlen(str)
      str[strlen(str)] = '!';                                // Changing strlen(str)+1 to strlen(str)
      str[strlen(str)] = '\0';                               // Changing strlen(str)+2 to strlen(str)
      
      printf("%s And python is my second!\n", str);
  }
  ```

### q
Examine the program below and answer the following questions.

```c
#include <stdio.h>
#include <string.h>

int main() {
    char * strings[3] = {"Hello", " world", " !!!"};
    char o = 'o';
    char * o_str = "o";
    char * world = "world";

    printf("P1: ");
    if (strings[0][4] == o) {
        printf("true\n");
    } else {
        printf("false\n");
    }

    printf("P2: ");
    if (*strings[0] + 4 == *o_str) {
        printf("true\n");
    } else {
        printf("false\n");
    }

    printf("P3: ");
    if (strcmp((strings[1] + 1), world) == 0) {
        printf("true\n");
    } else {
        printf("false\n");
    }

    printf("P4: ");
    if (strings[2] == " !!!") {
        printf("true\n");
    } else {
        printf("false\n");
    }
}
```

* What is the output for P1, P2, P3, and P4?
* Please explain what exactly each if statement is checking, as in what is being compared and why they are equal or not. 

#### s
* The output of this program is
  ```
  P1: true
  P2: false
  P3: true
  P4: true
  ```
* `P1: true` It is checking if the 5th character of the 1st string (array indexes starts from zero) in `strings` is the character "o". The result is `true` because the character is indeed "o" and `==` can be used for character comparison as it compares the ASCII code of the characters on each side.
* `P2: false` Firstly, `*strings[0]` dereferences the first character in the first string of `strings` ("Hello"), which is the character "H", adding 4 to it will result in 'L' (4th character after 'H' in the ASCII Table). The right hand side is dereferencing a pointer to character "o", which is the character "o". Thus, the left and right hand sides are not equal.
* `P3: true` We first observe that `strings[1]` is the string " world", with a space as the first character and an implicit "\0" terminating the string. `strings[1]+1` returns the string "world" because it starts at the 2nd character of `strings[1]` and terminates where `strings[1]` terminates. Thus, we are comparing "world" with "world" using the `strcmp` method and the result is the two sides are equal, thus `true`.
* `P4: true` This is tricky! When you declare a string using `char *`, you are creating a constant array in the `data` segment. So if there are two strings in the program declared in this way, then C optimizes and has each case reference the **same** instance of the string in the same memory location. In this case `strings[2]` references the string `" !!!"` which is compared to a constant string `" !!!"` in the if statement. C looks at this and notes that these two strings can be the same in memory, and does so. Thus when we compare the pointers, they are the same pointer value because they point to the same string. The result is true.



### q
Examine the program below and answer the following questions.

```c
#include <stdio.h>

int main() {
    char str[100] = "the importance of being earnest";
    str[0] -= 32;
    str[4] -= 32;
    str[18] -= 32;
    str[24] -= 32;
    printf("%s\n", str);
}
```

* What is the output of this program?
* How did this happen?

#### s

* The output of this program is `The Importance of Being Earnest`.
* In the ASCII table, code for lower case letters are larger than their corresponding upper case by 32. e.g. a's ASCII code is 97 whereas A's is 65. Thus, in the program, we took the four letters from 4 different positions (0, 4, 18 and 24) and turning them into the same letter but in upper case.


### q
Examine the program below and answer the following questions.

```c
#include <stdio.h>

void super_cool_function(char *p) {
    *p = *p - 4;
}

int main() {
    char a[6] = {'L','I','P','P','S', 4};
    
    for (int i = 0; i < 6; i++) {
        char * b = &a[i];
        super_cool_function(b);
    }

    printf("%s\n", a);
}
```

* What is the output of this program?
* What does super_cool_function do?

#### s

* The output of this program is `HELLO`.
* The super_cool_function takes a pointer to charater as a single parameter `char *p`. It dereference the pointer `*p`, decrease the value by 4 `*p - 4` and put the value into the slot where `*p` is pointing to. Note that decreasing a character's value by 4 means going back 4 characters in alphbetical order (ASCII Table).


### q
Examine the program below and answer the following questions.

```c
#include <stdio.h>
#include <string.h>

int main() {
    char string_a[24] = "Raise High";
    char * string_b = " the Roof Beam, Carpenters";

    printf("%s\n", strncat(string_a, string_b, 14));
}
```

* What is the output of this program?
* What is the function definition of the C string.h function strncat. (Hint: use the man pages!)

#### s

* The output of this program is `Raise High the Roof Beam`.
* `strncat` appends the second string (source string - first parameter) to the first string (destination string - second parameter) with a maximum of n (third parameter) bytes from the source string. In this case, 14 bytes from `string_b` is appended into the destination string `string_a`. Note that `string_a` has 24 characters/bytes of space, which perfectly fits the resulting string of `Raise High the Roof Beam` from the strncat function. If the destination string is not large enough for the result, an error will occur.


### q

Consider the following code snippet

```c
#include <stdio.h>
#include <string.h>

int main(){
    char str[] = "this is a string";
    
    for(char * p = str; *p; p++){//<-- A
        if(*p > 'l'){
            *p = *p + 1; //<-- B
        }
    }
}
```

* For the code line marked as `A`, explain why the for loop will properly iterate over each character in the string `str`.
* For the code line marked as `B`, explain how this will modify the string
* What is the expected output? 

#### s

* The for loop is turned into the following while loop
  ```c
  char * p = str;
  while(*p) {
    // code
    p++;
  }
  ```
  Where character pointer `p` is initialized with the first address of str. Until `p` becomes the terminating character at the end of `str`, the while loop (for loop) will keep running and within each loop, `p` is incremented by 1, which makes it point to the next address (stores the next character of `str`).
* Line marked as `B` will make the character in the string the next character alphabetically (e.g. a->b, y->z). It first dereference `p`, add 1 to the value and return it to the slot `p` is pointing to.
* `uhit it a tusiog`

### q

Consider the following code snippet

```c
int mystery(char * s1, char * s2){
  while(*s1++ == *s2++ && *s1 && *s2); //<-- A
  return(*s1 == '\0' && *s2 == '\0'); //<-- B
}
```


* What does the `mystery` function do?
* Offer an explanation for the functionality of line `A`
* Offer an explanation for the functionality of line `B`

#### s

* The `mystery` function compares two strings `s1` and `s2`, returns 1 if they are identical and 0 if not.
* Line `A` loops through the two strings, until at least 1 of the following 3 things happen:
  1. The character `s1` is pointing to does not equal to the character `s2` is pointing to.
  2. The character `s1` is pointing to is the terminating character "\0".
  3. The character `s2` is pointing to is the terminating character "\0".
* Line `B` returns 1 (true) only when both `s1` and `s2` are pointing to the terminating character "\0", otherwise it returns 0 (false).
* **Note**: The function above will not work if `s1` and `s2` varies only by the last character. The code below will work as intended by comparing the last character in the ruturn statement (line B).
  ```c
  int mystery(char * s1, char * s2){
      while( *s1++ == *s2++ && *s1 && *s2);//<-- A
      return( *(s1-1)==*(s2-1) && *s1 == '\0' && *s2 == '\0' );//<-- B
  }
  ```

### q
Examine the program below and complete the code.

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void print_error_and_exit(void) {
    fprintf(stderr, "USAGE ERROR - try again like this:\n");
    fprintf(stderr, "count -to <number> -by <number>\n");
    exit(1); //exit the program
}

int main(int argc, char * argv[]) {
    int to_num;
    int by_num;

    // Validate argument count
    if (argc < 5 || argc > 5) {
        print_error_and_exit();
    }

    // Validate -to parameter
    if (strcmp(argv[1], "-to") != 0) {
        print_error_and_exit();
    }

    // Validate -to parameter number
    if (sscanf(argv[2], "%d", &to_num) == 0) {
        print_error_and_exit();
    }

    // COMPLETE THE PARAMETER VALIDATION CODE


    for (int count = by_num; count <= to_num; count += by_num) {
        printf("%d\n", count);
    }
}
```
**Finish the code above so that the output is the following:**

```
$ ./count bad parameters 
USAGE ERROR - try again like this:
count -to <number> -by <number>
```

```
$ ./count -to 10 -by 2
2
4
6
8
10
```

#### s

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void print_error_and_exit(void) {
    fprintf(stderr, "USAGE ERROR - try again like this:\n");
    fprintf(stderr, "count -to <number> -by <number>\n");
    exit(1); //exit the program
}

int main(int argc, char * argv[]) {
    int to_num;
    int by_num;

    // Validate argument count
    if (argc < 5 || argc > 5) {
        print_error_and_exit();
    }

    // Validate -to parameter
    if (strcmp(argv[1], "-to") != 0) {
        print_error_and_exit();
    }

    // Validate -to parameter number
    if (sscanf(argv[2], "%d", &to_num) == 0) {
        print_error_and_exit();
    }

    // Start of Solution Code

    // Validate -by parameter
    if (strcmp(argv[3], "-by") != 0) {
        print_error_and_exit();
    }

    // Validate -by parameter number
    if (sscanf(argv[4], "%d", &by_num) == 0) {
        print_error_and_exit();
    }

    // End of Solution Code

    for (int count = by_num; count <= to_num; count += by_num) {
        printf("%d\n", count);
    }
}
```

