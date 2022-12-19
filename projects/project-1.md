---
layout: default
permalink: /project/1
---

*View the videos for this project in this [YouTube playlist](https://youtube.com/playlist?list=PLnVRBITSZMSMdXKuNahum68VgkVT7ZRaf)*

# Project 1: Boggle Solver

## Preliminaries

In this project, the goal is to implement a data structure in C (notably a hashmap). 

* Github Classroom Link: [https://classroom.github.com/a/3zBYoC7G](https://classroom.github.com/a/3zBYoC7G)

### Development Environment

You should develop using VSCode+SSH.

### Test Script

To help you complete this project, each part is provided with a test script. The script is not designed to be comprehensive, and you will graded based on a larger array of tests. To execute the test script, run it from anywhere within the lab directory.

    ./test.sh

Note that when executing the test script, due to using valgrind, it may take many minutes to complete! So you are better served developing some tests on your own rather than relying on the test script.

### Compiling your code:

#### Part A
For part A (C implementation), we have provided you with a `Makefile`. You can compile the spellchecker or boggle solver by typing:

```
make spellcheck
make onePlayerBoggle
```

If you want to compile everything at once, simply type `make`. This will produce a number of additional `.o` files (or object files), which are compiled C files that are not yet assembled. **Do not add these to your repository as they get overridden on every compilation**. 

To clean up your repository, you can use `make clean` command.

```
make clean
```

Cleaning the directory is helpful to remove unwanted files and/or force a recompilation with `make`.

### VScode Building and Debugging

As part of the repo for this assignment, we have provided a diretory `.vscode` that contains a `task.json` and `launch.json` for you to have automatic building and launching of the `gdb` debugger.

To run the debugger, open the debugging panel on vscode and press the `Run and Debug` button. This may prompt you to install an extension in the current container: do so. Then you should see three possible debug launches: (1) `gdb spellcheck` (2) `gdb oneplayer 100` and (3)`gdb oneplayer 200`. Each of these correspond to one of the expected outputs described below. 

Also note, you can use `Ctrl-Shift-b` or `Cmd-Shift-b` to automatically build your code by calling `make` for you. 

---

## Data Structure Implementations

You will be implementing two basic data structures as part of this project: a Linked List and a Hash Table (or Hashmap). 

### Linked List

The Linked List you will complete only requires forward pointers on its nodes, and only the `push()` operation, that is, put a new node on the front of the list. Each node in the list stores a string value. There is no need for the list to be generic. 

### Hashmap

The Hashmap data structure is simply a membership Hash Table --- unlike a truly generic Hashmap that stores key, value pairs, this table returns `true` if an items is stored in the data structure and `false` otherwise. Put another way, it's a Hashmap that maps a value to true. The Hashmap you will implement only needs to store strings. It has the following member functions:

* `add(string) -> void` : add a string to the hashmap
* `check(string) -> bool` : check to see if a string is in the hashamp, return true if present, else false. 

The Hashmap should be implemented as a hash table with *separate chaining*. You may recall from your data structures class, this means that when two elements collide at an index, you add the item on to that spot, using say Linked List. 

Following that model, your Hashmap should have an array (or buckets) of Linked Lists. We have included a hash function for you in the start code. After achieving the hash value for a given string (modulo the range of buckets), you *push* that string onto the Linked List at that index associated with the hash value. Critically, the performance of the Hashmap depends on the length of the lists of each bucket  --- if the lists get too long, then the look up operation could become O(n)! That would be quite slow ...

The *load* on a hash table is defined as the number of items stored in the table divided by the number of buckets. High loads means longer lists at each bucket and worse performance. To keep performance steady, once the load reaches 0.75, you have to resize the hash table by doubling the number of buckets and reinserting all the items into their new hash locations. **YOU MUST IMPLEMENT A RESIZE ROUTINE -- YOU CANNOT SIMPLY SET YOUR NUMBER OF BUCKETS TO A LARGE VALUE!!** 

> A quick aside: Since the hashmap only maps to true/false we could also describe this as an implementation of a set. That is, we are only concerned with set membership. Is the item in the set, or not?

### Data Structure Implementation

The crucial part of this project is the data structure implementations. In C, you typically divide your data structures between a header file (a `.h` file) and a source file (a `.c` file). The header file contains the structure and function definitions, while the source file contains their implementations. You will primarily work with the source files (`.c`).

#### Linked List

As you can see the `llist.h`, the Linked List defines two strucures:

```c
//node type stored in lists
typedef struct ll_node {
  struct ll_node * next; //next node in list
  char * val; //string value stored in list
} ll_node_t;



//list_t struct to store a list
typedef struct {
  ll_node_t * head; //pointer to the node at the head of the list
  int size; //the number of nodes in the list
} llist_t;
```

The `ll_node_t` is a node within the linked list, storing the value (a `char *` string) and a pointer to the next node. The `llist_t` is a structure representation of the list, storing a pointer to the head of the list and it's current size (number of nodes).

There are three functions that operate over lists, described below. In `llist.c` you implement these methods. 

```c
// Return a newly initialized, empty linked list
llist_t * ll_init();

//delete/deallocate a linked list
void ll_delete(llist_t * ll);

//insert the string v (duplicated vis strdup!) onto the front of the list 
void ll_push(llist_t * ll, char * s);
```

### Hash Map

The hashmap data strcucture is defined in `hashmap.h` and you will implemented in `hashmap.c`. The header file containing the structure and functions can be found below (with comments).

```c
#define HM_INIT_NUM_BUCKETS 16
#define HM_MAX_LOAD 0.75

typedef struct {
  llist_t ** buckets; //array of `buckets` each pointing to a list_t (see list.h)
  int num_buckets; //how many buckets, or lenght of the bucket array (should always be a power of 2)
  int size; //how many items stored
} hashmap_t;


//initliaze a hashmap with INITIAL_BUCKETS number of buckets
hashmap_t * hm_init();

//delete/deallocate the hashmap
void hm_delete(hashmap_t * hm);

//add a string value to the hashmap
void hm_add(hashmap_t * hm, char * v);

//see if a string value is in the hashmap
bool hm_check(hashmap_t * hm, char * v);
```

In the C file you will implement a non-public (as in not in the header file) function `_resize()`
```c
void _resize(hashmap_t * hm)
```
which is called when the load is greater than 0.75.


### Spellchecker (40 points)

To help test your Hashmap and Linked List implementation, we've provided a simple interactive spellchecker program that allows the user to type phrases (without punctuation) and it will spellcheck it. Here's some sample inputs and outputs, along with the compilation. 

```
$ make
gcc -Wall -Wno-unused-variable -g -c -o hashmap.o hashmap.c
gcc -Wall -Wno-unused-variable -g -c -o llist.o llist.c 
gcc -Wall -Wno-unused-variable -g -o spellcheck spellcheck.c hashmap.o llist.o -lreadline -lm
gcc -Wall -Wno-unused-variable -g -c -o boggle.o boggle.c
gcc -Wall -Wno-unused-variable -g -o onePlayerBoggle onePlayerBoggle.c boggle.o hashmap.o llist.o -lreadline -lm
$ ./spellcheck 
ERROR: require dictionary file
$ ./spellcheck dictionary.txt 
spellcheck > spellcheck all these words at once
SPELLCHECK -> not a word
ALL -> WORD
THESE -> WORD
WORDS -> WORD
AT -> WORD
ONCE -> WORD
spellcheck > or
OR -> WORD
spellcheck > one
ONE -> WORD
spellcheck > at
AT -> WORD
spellcheck > a
A -> WORD
spellcheck > time
TIME -> WORD
spellcheck > this adfasdfasdf is not a word
THIS -> WORD
ADFASDFASDF -> not a word
IS -> WORD
NOT -> WORD
A -> WORD
WORD -> WORD
spellcheck > nor !!! 
NOR -> WORD
!!! -> not a word
spellcheck > 
$ # type ^D to insert EOF to exit (or ^C)
```

### Boggle Solver (60 points)

Now that you're Hash Map and Linked List are working, let's use them to do something a bit more interesting --- finding all the words on a boggle board! 

The boggle game structure and functions are defined in `boggle.h` and you will do most of your work in `boggle.c`. A boggle instance is defined as a 5x5 grid of dice, where each dice displays a different character. 

```C
#define BOGGLE_DIMENSION 5

typedef struct {
  char board[BOGGLE_DIMENSION][BOGGLE_DIMENSION]; //the boggle board
  hashmap_t * dict; //dictionary mapping
} boggle_t;

```

When printed the board looks like
```
.-----------.
| S N T A Y |
| W N T E I |
| N QuI H I |
| N F O S U |
| E E H N L |
'-----------'
```

The goal is to find as many words (**at least three letters long**) by traversing from one dice to another in all directions (left, right, up, down, and diagonal) without using a dice more than once. So for example `QUIT` is a word found on the board, and so is  `QUITE`. (You get a free 'u' for your 'Q'.)

A number of functions are implemented and provided for you in `boggle.c`, your main work will be completing the `bg_all_words()` function, which will search the boggle board for all words 3 letters to 8 letters in length. 

This is a recursive method that will explore outwards from a letter tile using **depth first search**. The idea is that you start a tile, like `Qu` and then try all neighbors (via a recursive call), outward, adding letters as you go and checking to see if you found a word. At somepoint you either search off the board or descended too far (checking a 9 letter word), and the recursion returns to explore another path. An algorithmic description is provided in a comment within `boggle.c` --- see there for more details.

Once you complete, you can run the `onePlayerBoggle` program at a given random seed, like the two examples below (warning: you may end up with some inappropriate words if they are in your dictionary and you can see these below, apologies):

```
vscode ➜ /workspaces/project-1-inst $ ./onePlayerBoggle dictionary.txt 100
.-----------.
| R E E M G |
| N I E D T |
| O T O W A |
| K T S H I |
| C S I I A |
'-----------'
SHOW
WEER
RIOTED
AHA
SHOT
WHIST
NIT
DEW
TEE
NOTE
WHISK
DEER
WEED
TINE
MEETS
SHOE
SHOD
DEEM
RITE
ION
HOED
TIER
TOWED
SOTS
TEEN
TEEM
SODA
TIED
MEOWS
STEW
TEED
AWE
WETS
WADE
STEM
WHAT
MEW
SIT
SIS
WHIT
MET
WAD
SHOED
STONIER
NOT
STEER
HOSTS
HOST
TWOS
TONIER
STEED
TOTEM
WHO
HAWED
OHS
ONTO
SHOTS
TOTED
REIN
RENTED
TOWS
SWAT
NEED
HAWS
NOTED
ADO
ITS
OWED
MEOW
HAW
WOT
SOW
HAT
NITS
TIN
SOT
WEIR
RIOTS
TAD
MEWS
HIT
TONE
HIS
TIE
WOE
HAD
STOWS
TIRE
SOD
STOWED
TWEE
SOIRE
INTO
OWE
RIOT
SHOWED
TOED
SOWED
AWED
SITS
EERIE
DOTS
STIR
SHAT
MEET
HOSTED
WET
ONE
STONE
TWEED
SHIT
HITS
TOW
IRE
DOTE
TOTS
RENTS
WOST
TOT
RENT
SHAD
WEE
WED
TON
HOW
HOT
TOTE
TWO
HOS
TOE
DOT
STEIN
DOS
HOWS
SHITTIER
HISS
TONER
SHADOW
HOE
HOD
TOST
TOSS
DOE
WHITS
ITEM
SHITS
REED
ODE
SHADE
STOW

Total Points: 205
```

```
vscode ➜ /workspaces/project-1-inst $ ./onePlayerBoggle dictionary.txt 200
.-----------.
| A D O N R |
| G G R I N |
| T E R S E |
| C R A T Qu|
| E E E O N |
'-----------'
INSET
EAST
TEN
CREATE
QUOTAS
TREAT
NOISE
TENS
TEE
NOTE
TEA
RARER
TSAR
RECREATE
EASE
RATS
EGO
OATEN
AGO
GROIN
ION
GORIEST
EGG
EATER
EON
NINES
EATEN
RATE
TEARIEST
AGE
EARS
CREASE
TENNIS
GREATER
ROGER
CREATES
OATS
DREARIES
INS
TOQUES
IRATER
INN
RISE
IRON
ADORN
SAT
ORE
QUEST
OAT
OAR
ADORE
SIR
SIN
NOT
NINE
NOR
STEER
CEASE
ERRS
GAG
INNS
GAD
ERECT
IRATE
RETREAT
ROD
GRATE
RISEN
REAR
GEARS
NOD
CRATE
RARE
NOTES
ERAS
GRIST
TREE
TREATS
ARGON
ESTER
OARS
TARGET
GEAR
ASTER
SINE
RETREATS
ADO
GRATES
GRATER
DAGGER
GROINS
ARISE
RINSE
ARTERIES
ERASE
GREATS
TAR
NET
RASTER
ROISTER
TRIES
QUOTES
NETS
RATES
ETA
ATE
ARTS
TEATS
STARTER
TART
TARS
TERN
GREAT
DRIES
TEAT
TEAS
TEAR
TARE
EGOIST
ROGERS
GOD
TARTER
QUOTE
GREASE
NEST
QUOTA
ERGO
STAR
GRIN
GADGET
AERIE
AERIES
TOQUE
ERR
ART
TEASE
START
SET
SATE
IRE
GORSE
ERG
ERE
GAGE
EAT
EAR
GET
ERA
ARE
STARE
TON
ARC
TENSION
TEARS
DORIES
SENIOR
REARS
CERISE
AERIEST
TOE
TARRIES
STEIN
RETRIES
TRIO
DON
ATES
RISQU
DOG
GORE
EATS
TERSE
NOTARIES
AGONIES
CRATES
OGRE
DRIEST
DAGGERS
SIRE
RAT
SARI
ARISEN
GRINS
AEON

Total Points: 393
```


> Note that the words are not alphabetical because hash tables are not ordered data structures.


## Bonus: Ordered Output (15 points)

> Create a new branch in your repository called `ordered` and work within that branch --- do not make these changes on your `main` branch otherwise it may affect your grading of part B. Once complete push this branch to the github and also open a issue with the title "BONUS Submission Ordered"

Update your data structure (perhaps using a BST?) such that the output of the words from your boggle solver is in alphabetical order.



