---
layout: worksheet
permalink: /worksheet/c3
showsolution: true
---

# Worksheet: C3

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.

* Github Classroom Link: [https://classroom.github.com/a/QE1LnwS1](https://classroom.github.com/a/QE1LnwS1)

## Note

Attempt to answer these questions before running the code. This will improve your ability to analyize and reason about code without an IDE or compiler. This skill we be helpful on the exams.

## Questions

### q
Examine the program below and complete the code.

```c
#include <stdio.h>

#define NUM_ROWS 3
#define NUM_COLS 3

int main() {

    int matrix[NUM_ROWS][NUM_COLS] = { {2, 5, 1},
                                       {3, 9, 6},
                                       {8, 7, 4}};

    // WRITE CODE TO PRINT OUT THE MATRIX

}
```
**Finish the code above so that the output is the following:**

```
2 5 1 
3 9 6 
8 7 4 
```

#### s

```c
// WRITE CODE TO PRINT OUT THE MATRIX
for(int i = 0; i < NUM_ROWS; i++){
    for(int j = 0; j < NUM_COLS; j++){
        printf("%d ", matrix[i][j]);
    }
    printf("\n");
}
```


### q
Examine the program below and complete the code.

```c
#include <stdio.h>

#define NUM_ROWS 3
#define NUM_COLS 3

int main() {

    int matrix_a[NUM_ROWS][NUM_COLS] = { {2, 5, 1},
                                         {3, 9, 6},
                                         {8, 7, 4}};

    int matrix_b[NUM_ROWS][NUM_COLS] = { {4, 1, 6},
                                         {9, 7, 5},
                                         {3, 8, 2}};

    int matrix_c[NUM_ROWS][NUM_COLS];

    // https://en.wikipedia.org/wiki/Matrix_multiplication_algorithm#Iterative_algorithm
    /*
        For i from 1 to num_rows:
            For j from 1 to num_cols:
                Let sum = 0
                For k from 1 to num_cols:
                    Set sum = sum + (Aik * Bkj)
                Set Cij = sum
    */
    
    // USING THE ALGORITHM ABOVE
    // WRITE CODE HERE TO MULTIPLY THE TWO MATRICES
    // PUT THE RESULT IN MATRIX_C

    // PRINT RESULTING MATRIX_C
}
```
**Finish the code above so that the output is the following:**

```
56 45 39 
111 114 75 
107 89 91 
```

#### s

```c
// USING THE ALGORITHM ABOVE
// WRITE CODE HERE TO MULTIPLY THE TWO MATRICES
// PUT THE RESULT IN MATRIX_C

for(int i = 0; i < NUM_ROWS; i++){
    for(int j = 0; j < NUM_COLS; j++) {
        int sum = 0;
        for(int k = 0; k < NUM_COLS; k++) {
            sum = sum + (matrix_a[i][k] * matrix_b[k][j]);
        }
        matrix_c[i][j] = sum;
    }
}

// PRINT RESULTING MATRIX_C
for(int i = 0; i < NUM_ROWS; i++) {
    for(int j = 0; j < NUM_COLS; j++) {
        printf("%d ", matrix_c[i][j]);
    }
    printf("\n");
}
```

### q

The following function declaration will not compile. 

```c
void foobar( int a[][]);
```

Provide an explanation for what is wrong and how to fix it.

#### s

For array that has multiple dimensions, all but the first dimension needs to have bounds. Thus, at the very least, the declaration needs to be `void foobar( int a[][NUM_COLS]);` where `NUM_COLS` is an integer constant or variable.


### q
Examine the program below and answer the following questions.

```c
#include <stdio.h>

int main() {
    int array[3][3] = { {1, 2, 3}, {4, 5, 6}, {7, 8, 9} };

    printf("%d\n", *(*array + 4));
}
```
**Attempt to answer these questions without running the code!**

* What is the output of this program?
* Explain the memory layout of a two-dimensional array?

#### s

* The output of the program is `5`.
* In the memory, a two dimensional array will be stored sequentially where the 1st dimension comes before the 2nd, then the 3rd, etc. Thus, the memory diagram for `array` in the program above is:
  ```
    .--------------.
    |       |    1 |<-- array <-- array[0] 
    |-------+------|
    |       |    2 |
    |-------+------|
    |       |    3 |
    |-------+------|
    |       |    4 | <------- array[1]
    |-------+------|
    |       |    5 |
    |-------+------|
    |       |    6 |
    |-------+------|
    |       |    7 | <------- array[2]
    |-------+------|
    |       |    8 |
    |-------+------|
    |       |    9 |
    '-------+------'
  ```


### q

Examine the program below and answer the following questions.

```c
#include <stdio.h>
#include <string.h>
int main(){
    int a[2][3] = { {10,20,30}, {5,6,7} };
   
    int * p = (int *)a+4;

    p[1] = 42;
    //MARK
}
```

**Attempt to answer these questions without running the code**

* What are the values of `a` at `MARK`
* In plain English, provide a description of how the line `int * p = (int *)a+4` works when the array dimensions are `int[2][3]`.
* Draw the memory diagram at `MARK`

#### s

* ```
  a[0][0] = 10, a[0][1] = 20, a[0][2] = 30
  a[1][0] = 5,  a[1][1] = 6,  a[1][2] = 42
  ```
* Multidimensional arrays are stored sequentially rather than having a subarry for each `a[i]`, so when `a` is declared with dimensions `int[2][3]`, `(int *)a+4` will start from the first element and add 4 to its address, which is pointing to the value 6 in the array `a`.
* ```
  .---------+--------.
  | a[0][0] |   10   |<--<-.a<--a[0]
  |---------+--------|     |
  | a[0][1] |   20   |     |
  |---------+--------|     |
  | a[0][2] |   30   |     |
  |---------+--------|     |
  | a[1][0] |   5    |<----'----a[1]
  |---------+--------|
  | a[1][0] |   6    |<--.
  |---------+--------|   |
  | a[1][0] |   42   |   |
  |---------+--------|   |
  |   p     |    .-------'
  '---------+--------'
  ```

### q 

Examine the program below and answer the following questions.

```c
int main(){
  int a[][3] = { {10,20,30}, 
                {5,6,7},
                {-1,-2,-3},
                {17,19,21}};

  int ** p = &a[1];
  int * q =  &p[1];
  
  q[1] = 42;
  //MARK
}
```


**Attempt to answer these questions without running the code**

* What are the values of `a` after at `MARK`
* Offer a plain English explanation for what exactly `int * q = &p[1]` is referring to in `a`
* Draw the memory diagram at `MARK`

#### s

* ```
  a[0][0] = 10, a[0][1] = 20, a[0][2] = 30
  a[1][0] = 5,  a[1][1] = 6,  a[1][2] = 7
  ```
* `q` is referring to `7` in `a`. Since `p` is of type `int **` and pointing to `a[1][0]=5`, `p[1]` add size of `int *` (8 bytes = size of two ints) and points to `a[1][2]=7`.
* ```
  .---------+--------.
  | a[0][0] |   10   |<--<--a<--a[0]
  |---------+--------|
  | a[0][1] |   20   |
  |---------+--------|
  | a[0][2] |   30   |
  |---------+--------|
  | a[1][0] |   5    |<----.----a[1]
  |---------+--------|     |
  | a[1][1] |   6    |     |
  |---------+--------|     |
  | a[1][2] |   7    |<-.  |
  |---------+--------|  |  |
  | a[2][0] |   42   |<-+--+----a[2]
  |---------+--------|  |  |
  | a[2][1] |   -2   |  |  |
  |---------+--------|  |  |
  | a[2][2] |   -3   |  |  |
  |---------+--------|  |  |
  | a[3][0] |   17   |<-+--+----a[3]
  |---------+--------|  |  |
  | a[3][1] |   19   |  |  |
  |---------+--------|  |  |
  | a[3][2] |   21   |  |  |
  |---------+--------|  |  |
  |   p     |    .------+--'
  |---------+--------|  |   
  |   q     |    .------'
  '---------+--------'
  ```


### q

Below are the results of two Tic-Tac-Toe games, player `o` wins game 1 while player `x` wins game 2. Unfortunately one of the players (`x` or `o`) has inserted some evil code to change the boards.

Run the code below in the C visualizer and answer the questions below.
```c
#include <stdio.h>
#include <string.h>

int main() {
  char *game_1[3] = {
    "o|x| ",
    "o|o|x",
    "x|x|o"};

char *game_2[3] = {
      "x|x|o",
      "o|x| ",
      "o|x|o",
  };
  
  //Begin Evil Code
  for (int i = 0; i < 3; i++) {
    char **tmp = &game_1[i];
    game_1[i] = game_2[i];
    game_2[i] = *tmp;
  }
  //End Evil Code

  printf("The winner of game 1 is: ...");
  printf("The winner of game 2 is: ...");
}
```
* What do you notice about the pointers to the strings on the heap?
* What did the evil code do to the 2 boards and who inserted the evil code?

#### s

* Player `x` inserted the evil code so that they won.
* In the evil code, it seems like the two boards are swapped line by line with the help of the `tmp` variable. However, the reality is that `game_2` is copied to `game_1` because `tmp` is given the address, not value, of each string in `game_1`. This means after the line `game_1[i] = game_2[i]`, both `tmp` and `game_1[i]` are pointing to `game_2[i]`. Therefore, when the for loop is completed, everything in `game_2[i]` is copied to `game_1[i]` resulting in two identical boards where player `x` won both games.

### q
**Answer the following questions:**
* What does `malloc()` do? What are the advantages or disadvantages of using `malloc()`, if any?
* What is the difference between `malloc()` and `calloc()`?

#### s
1. `malloc()` allocates a certain size of memory space and return the address of the newly allocated space. We use `malloc()` for dynamic memory allocation, which means we can utilize memory spaces when needed and free it when not needed. Furthermore, space allocated using `malloc()` can be used throughout the entire program regardless of function calls.
2. `malloc()` takes 1 argument, which is the size of the memory space to be allocated while `calloc()` takes 2 arguments, the 1st being number of items and the 2nd being the size of each item. Furthermore, `malloc()` allocates the space and return the address but does not clear out exisiting data in the allocated space. `calloc()` not only allocates  the space needed, return the address, but also zero out all the spaces that is allocated.

### q

A computer science major who struggles with algebra is trying to modify their grades. The student was able to hack into the administrative code and insert some Evil Code to change their grade. 

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
  char *name;
  char grade;
} course_t;

int main() {

  course_t *courses[3];

  courses[0] = malloc(sizeof(course_t));
  courses[0]->name = "Basket Weaving";
  courses[0]->grade = 'A';

  courses[1] = malloc(sizeof(course_t));
  courses[1]->name = "Complex Tic Tac Toe Analysis";
  courses[1]->grade = 'A';

  courses[2] = malloc(sizeof(course_t));
  courses[2]->name = "Algebra for CS Majors";
  courses[2]->grade = 'D';

  //... some admin code

  // Begin Evil Code inserted by student
  courses[2] = malloc(sizeof(course_t));
  courses[2]->name = "Algebra for CS Majors";
  courses[2]->grade = 'B';

  // Rest of program
  // ...

  printf("Your grades for the semester are ...\n");
  // prints grades
  free(courses[0]);
  free(courses[1]);
  free(courses[2]);
}
```

* Did the student introduce a memory leak? 
* If so, how many bytes were leaked?
* Explain the memory leak or why there can't be a memory leak.

#### s

* The student did introduce a memory leak.
* 16 bytes were leaked.
* Before the admin code (`//... some admin code`), `courses[2]` points to a block of memory with size of `course_t`. In the evil code, `courses[2]` points to a new block with size of `course_t` which means although `courses[2]` was freed at the end of the program, only the block allocated in the evil code is freed while the block of memory created in the original code was never freed.


### q

What is the difference between a **memory violation** and a **memory leak**? Provide a small code example of each.

#### s
* While memory violation means memory is accessed when shouldn't be or prior to it being initialized, memory leak means memory being allocated without freed.
* Memory violation example:
  ```c
  #include <stdio.h>
  #include <stdlib.h>

  int main(int argc, char * argv[]){

    int i, *a;
  
    a = calloc(3, sizeof(int));

    a[3] = 5;
    printf("%d", a[3]);
    
    free(a);
  }
  ```
* Memory leak example:
  ```c
  #include <stdio.h>
  #include <stdlib.h>
   
  int main(int argc, char * argv[]){
    int * a = malloc(sizeof(int *));
    *a = 10;  

    printf("%d\n", *a);
  }
  ```

### q

Consider the following code

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
  char * name;
  short x;
  short y;
} point_t;

int main() {
  point_t * a = malloc(sizeof(point_t));

  char * tmp_name = "Point of Interest";
  a->name = malloc(sizeof(char)*strlen(tmp_name)+1);
  strcpy(a->name, tmp_name); //Copies the string on the right to the left
  a->x = 4;
  a->y = 1;

  point_t b;
  b = *a;
  free(a);

  printf("%s",b.name); //MARK
  free(b.name);
}
```

**Attempt to answer these questions without running the code**

* Is printing at the indicated MARK a memory violation?
* If so, how many bytes of a memory violation occur? If not, explain why there is no violation.
* Does this program leak any memory? 
* What is the output?

#### s

* Printing at the indicated MARK is not a memory violation.
* There is no memory violation because `b` is on the stack and `b.name` is the only thing in `b` pointing to the heap. `b.name` is also not freed when `a` is freed because `a->name` is pointing to a `char *` on the heap that has nothing to do with `a` itself.
* The program does not leak any memory.
* The output is `Point of Interest`.

### q

For the next few questions, consider that your implementing a Linked List in C using the following struct

```c
typedef struct node{
    int val;
    struct node * next;

} node_t;
typedef struct llist{
   node_t * head;
   int len;    
} llist_t;
```

Write the following functions:
* `llist_t * new_list()` : allocates a new empty linked list
* `void del_list(llist_t * list)` : dealocates a linked list  `list`
* `void add_to_head(llist_t *list,int new_value)` : add the `new_value` to the head of the list. 

#### s

```c
  llist_t * new_list(){
      // Allocate the space for the linked list.
      llist_t *my_llist = malloc(sizeof(llist_t));
  
      // Make the head NULL and length 0
      my_llist->head = NULL;
      my_list->len = 0;
      
      return my_llist;
  }

void del_list(llist_t * list){
      // cur_node start with the head of the linked list.
      // next_node is used for temporary storage of next
      // node in the linked list. Initialized to NULL.
      node_t *cur_node = list->head;
      node_t *next_node = NULL;

      // Go through the linked list.
      while (cur_node != NULL)
      {
          // Make next_node point to the next element in
          // the linked list before the current node is 
          // freed and next node not accessible.
          next_node = cur_node->next;

          // Free the current node.
          free(cur_node);

          // Iterate through the linked list by making
          // the cur_node its next in-line.
          cur_node = next_node;
      }

      // Finally, free the linked list allcated
      // during creation.
      free(list);
  }

void add_to_head(llist_t * list, int new_value){
      node_t * new_head = malloc(sizeof(node_t));
      new_head->val = new_value;
      new_head->next = list->head;
      list->head = new_head;
      list->len++;
  }
```

### q

Referring to the linked list structures above, complete the following functions over these lists without recursive calls (a function calling itself).

```c
int sum(llist_t * list){
    // return a single integer, 
    // which is the sum of all nodes' val
    
    // FINISH ME
}

int max(llist_t * list){
    // return a single integer,
    // the maximum val among all the nodes

    // FINISH ME
}

void add_to_tail(llist_t * list, int new_value){
   // Add the new_value to the end of the link list
   
   // FINISH ME
}
```

#### s
```c
int sum(llist_t * list){
    // return a single integer, 
    // which is the sum of all nodes' val
    
    node_t * node = list->head;
    int sum = 0;
    
    while(node != NULL) {
        sum += node->val;
        node = node->next;
    }
}
```
```c
int max(llist_t * list){
    // return a single integer,
    // the maximum val among all the nodes

    node_t * node = list->head;
    int max = node->val;

    while(node != NULL) {
        node = node->next;
        if(node->val > max) {
            max = node->val;
        }
    }

    return max;
}
```
```c
void add_to_tail(llist_t * list, int new_value){
   // Add the new_value to the end of the linked list
   
    node_t * new_node = malloc(sizeof(node_t));
    new_node->val = new_value;
    new_node->next = NULL;
    
    if(list->head == NULL){
       list->head = new_node;
    }else{
    
       node_t * cur = list->head;
       while(cur->next != NULL){
          cur = cur->next;
       }
       cur->next = new_node;
    }

    list->len += 1;
}
```

### q

Referring to the linked list structures above, complete the following recursive functions over these lists


```c

int sum(llist_t * list){
    return _sum(list->head);
}

int _sum(node_t * node){
  //FINISH ME!
}


int min(llist_t * list){
    return _min(list->head);
}

int _min(node_t * node){
  //FINISH ME!
}

void add_to_tail(llist_t * list, int new_value){
   list->head = _add_to_tail(list->head, new_value);
}

node_t * _add_to_tail(node_t * node, int new_value){
  //FINISH ME! --- this one is tough!
}

```

#### s
```c
int _sum(node_t * node){
    //reach the end of the list, return 0
    if(node == NULL) return 0; 
    
    //otherwise sum of the list is the value of this node plus the sum of the remainder of the list
    return node->val + _sum(node->next); 
  }
```

```c
  int _min(node_t *node){
    //if this node is the last in the list, return it's value because it's the min in the list
    if(node->next == NULL) return node->val;
    
    //otherwise the min is either this value, or the min value in the reaminder of the list
    int m = _min(node);
    if (m < node->next)
      return m;
    else
      return node->next;
    
    //return m < node->next ? m : n;
  }
```
```c
  node_t * _add_to_tail(node_t *node, int new_value){
    //if we reached the null, put the new node there
    if(node == NULL){
        node_t * new_node = malloc(sizeof(node_t));
        new_node->next=NULL;
        new_node->val=new_value;
        return new_node; //return it to be assigned to the next value of the prior node
    }
    
    //recurse down, reassigning the next pointer
    node->next = _append(node->next,new_value);
    
    //return this node so it can be assigned the next pointer of the previous value
    return node;
  }
```
