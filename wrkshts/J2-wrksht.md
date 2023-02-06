---
layout: worksheet
permalink: /worksheet/j2
showsolution: false
---

# Worksheet: J2

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.

Submit a file called `worksheet-J2.md` in your repo for this assignment.

## Note

Attempt to answer these questions before running the code. This will improve your ability to analyze and reason about code without an IDE or compiler. This skill we be helpful on the exams.

## Questions


### q
Define **polymorphism** in the context of Java and provide one example where it is valuable? 

#### s

**Polymorphism** in Java allows multiple implementation of the same interface. One example will be the `Point` and `LabPoint` classes we implementated in class where we can have a `toString()` method in both `Point` and `LabPoint` that prints out different information.


### q

Consider the following program from the class notes

```java
public class Ex3 {
  public static void main(String[] args) {
    Random   rand = new Random(System.currentTimeMillis());
    Point    v    = new Point(3, 4);
    LabPoint w    = new LabPoint(5, 2, "A");
    String   x    = "I'm a string";
    Scanner  y    = new Scanner(System.in);

    Object u;
    int i = rand.nextInt(4);

    if( i == 0 )
      u = v;
    else if( i == 1 )
      u = w;
    else if( i == 2 )
      u = x;
    else
      u = y;
    System.out.println(u); //<--
  }
}
```

Explain how polymorphism makes this program possible. 

#### s
Since every class that does not have the `extends` keyword implicitly extends the `Object` class, the `Object u` declaration makes `u` capable of "morphing" into any of the 4 variables `v, w, x, y`.

### q
What is the output of this program? You should be able to do this without running the program!

```java
class A {
    public String toString(){
        return "A";
    }
}

class B extends A{
    public String toString() {
        return "B";
    }
}

class C extends A {
    public String toString() {
        return super.toString();
    }
}

class D extends C {
    public String toString() {
        return super.toString();
    }
}

public class tmp {
    public static void main(final String args[]) {
        D d = new D();
        System.out.println(d.toString());
    }
}
```

#### s
The output is `A` because `class D` extends `class C` and `class C` extends `class A` where both classes `C, D` have their `toString()` function return `super.toString()` and the `toString()` method of `class A` returns a single character `A`.

### q
What is the output of this program? You should be able to do this without running the program!

```java
class A {
    public String toString() {
        return "A";
    }
    
    public String fancyToString() {
        return "~~A~~";
    }
}

class B extends A {
    public String toString() {
        A letterA = this;
        return letterA.fancyToString();
    }
    public String fancyToString() {
        return "~~B~~";
    }
}

public class LetterPrinter {
    public static void main(final String args[]) {
        B letterB = new B();
        System.out.print(letterB.toString() + " ");
        
        A letterA = letterB;
        System.out.println(letterA.toString());
    }
}
```

#### s
* The output of the program is `~~B~~ ~~B~~`.
* With polymorphism, `letterB.toString()` will make `letterA` of `A letterA = this;` in `class B` to `class B`, which results in `letterA.fancyToString();` return `~~B~~`. Similarly, in the main function, when `A letterA = letterB`, it is morphed into `class B` rather than `class A`, resulting in `letterA.toString()` give the same output as the first print statement `~~B~~`.

### q
What is the output of this program? You should be able to do this without running the program!

```java
class A {
    public String toString() {
        return "A";
    }
    
    public String fancyToString() {
        return "~~A~~";
    }
}

class B extends A {
    public String fancyToString(){
        return "~~B~~";
    }
}

public class LetterPrinter {
    public static void main(final String args[]) {
        B letterB = new B();
        System.out.print(letterB.toString() + " ");
        
        A letterA = letterB;
        System.out.println(letterA.toString());
    }
}
```

#### s
* The output of this program is `A A`.
* Note that `class B` does not have a `toString()` method, which means calling `letterB.toString()` will result in the `toString()` method of `class A` since `class B extends A`. Similarly, although `A letterA = letterB` morphed `letterA` to `class B`, it still uses the `toString()` method of `class A`.

### q
Consider the first two class declarations. What is the output of compiling the program below? 
```java
abstract class Letter {
    protected boolean uppercase;

    abstract String get_name();

    abstract int get_alphabet_position();
}
```
```java
class A extends Letter {
    public String toString() {
        return "A";
    }

    protected int get_alphabet_position() {
        return 1;
    }

    private String get_name() {
        return "A";
    }
}
```



#### s
The program does not compile because the `get_name()` in `class A` attempts to override the abstract method `get_name()` in abstract class `Letter` with reduced visibility `private`.

### q
If we change the implementation of `A` to the following, what does the code below output?
```java
abstract class Letter {
    protected boolean uppercase;

    abstract String get_name();

    abstract int get_alphabet_position();
}
```
```java
class A extends Letter {
    public String toString() {
        return "A";
    }

    public int get_alphabet_position() {
        return 1;
    }

    protected String get_name() {
        return "A";
    }
}
```

```java
public class Main {
    public static void main(final String args[]) {
        A a = new A();
        System.out.println("A: " + a.get_alphabet_position());
    }
}

```

#### s
The output of the program is `A: 1`.

### q
What is the output of this program? You should do this without running the program.

```java
class A {
    public String toString() {
        return "A";
    }
}

class B extends A {
    public String toString() {
        return "B";
    }
}

public class PolymorphicOverload {
    public void foo(B letterB1, B letterB2) {
        // 2
        System.out.println("foo2: " + letterB1 + " " + letterB2);
    }

    public void foo(A letterA1, A letterA2) {
        // 1
        System.out.println("foo1: " + letterA1 + " " + letterA2);
    }
    public static void main(String args[]) {
        PolymorphicOverload f = new PolymorphicOverload();
        B letterB = new B();
        A letterA = (A) new B();
        f.foo(letterB, letterA);
    }
}
```

#### s
* The output of the program is `foo1: B B`.
* While `letterB` and `letterA` are both `class B`, the declaration makes `f.foo(letterB, letterA)` look for the best method to fit `f.foo(class B, class A)` due to the static declaration of `letterA` being `class A`. This means the method `public void foo(A letterA1, A letterA2)` is called in the main function because it is the best fitting method where both parameters passed in can be `class A` (remember `B` extends `A`). Therefore, the output is `foo1: B B`.


### q
Assume that `class A` is implemented in such a way so that the program will compile and run. What is the output? You should do this problem without running the code.

```java
public class Temp {
    public static void foo(A a) {
        System.out.println("foo1: " + a.get_name());
    }
    public static void foo(Letter a) {
        System.out.println("foo2: " + a.get_name());
    }
    public static void main(final String args[]) {
        Letter a = (Letter) new A();
        foo(a);
    }
}
```

#### s
The output of this program is `foo2: A`. The variable `a` is declared as `Letter class` which will make `foo(a)` run the second method with declaration `public static void foo(Letter a)`.


### q
Suppose you had the following class structures


```java
public class Species {
    String genus;
    String species;
    public Species(String g, String s) {
        genus = g;
        species = s;
    }
    
    public Species(Species s) {
        genus = s.genus;
        species = s.species;
    }
    
    public String toString() {
        return genus + " " + species;
    }
}

public class Breed extends Species {
    protected String breed;

    public Breed(String b, String g, String s) {
        super(g, s);
        breed = b;
    }

    public Breed(String b, Species s) {
        super(s);
        breed = b;
    }

    public String toString() {
        return super.toString() + "(" + breed + ")";
    }
}

public class Pet {
    String name;
    Species species;

    public Pet(String n, Species s) {
       name = n;
       species = s;
    }

    public String toString() {
        String ret = "Name: " + name + "\n";
        ret += "Species: " + species;
        retunr ret;
    }       
}
```


What is the output of the following snippet of code? If there is an ERROR, describe the error. **You should not need to run the code to determine the output**.

```java
    
   Species dog = new Species("Canis","Familaris");
   Breed shorthair = new Breed("shorthair", new Species("Felis","Catus"));
   Pet fluffy = new Pet("fluffy", new Breed("pomeranian", dog));
   Pet george = new Pet("george", dog);
   Pet brutus = new Pet("brutus", (Species) shorthair);
   
   System.out.println(fluffy);
   System.out.println(george);
   System.out.println(brutus);
```


#### s
```
Name: fluffy
Species: Canis Familaris(pomeranian)
Name: george
Species: Canis Familaris
Name: brutus
Species: Felis Catus(shorthair)
```


### q

Consider the following classes

```java
public class A {
    public int foo() {
        return 42;
    }

    public int bar() {
        return foo() + 8;
    }
}

public class B extends C {
    public int foo() {
        return 41;
    }

    public char baz() {
        return "y";
    }
}

public class C extends A {
    public char baz() {
        return "x";
    }
}

public class D extends A {
    public int bar() {
        return 7;
    }
}

public class E extends C {
    public int bar() {
        return foo() + 20;
    }
}

```

Draw the class hierarchy for the above classes, that is the UML diagram that simply shows who inherits from whom.

#### s

TODO

### q

Continuing with the classes from the previous question, consider a mystery function that returns a object of the given class.  **You do not know the definition of the mystery function, other than it compiles properly and returns an object of the class.** For each of the following method calls marked below, indicate the value of the output, if the output cannot be determined, or if there is an error.

```java

A a = mysteryA(); //<-- mystery function, this line compiles (the below may not!)
System.out.println(a.foo()); //<-- Mark A.1
System.out.println(a.bar()); //<-- Mark A.2
System.out.println(a.baz()); //<-- Mark A.3


B b = mysteryB(); //<-- mystery function, this line compiles (the below may not!)
System.out.println(b.foo()); //<-- Mark B.1
System.out.println(b.bar()); //<-- Mark B.2
System.out.println(b.baz()); //<-- Mark B.3

D d = mysteryD(); //<-- mystery function, this line compiles (the below may not!)
System.out.println(d.foo()); //<-- Mark D.1
System.out.println(d.bar()); //<-- Mark D.2
System.out.println(d.baz()); //<-- Mark D.3
```

#### s

```
A.1 : Compiles, Output not deterministic
A.2 : Compiles, Output not deterministic
A.3 : Doesn't Compile, No output

B.1 : Compiles, Output is 41
B.2 : Compiles, Output is 49
B.3 : Compiles, Output is y

D.1 : Compiles, Output is 42
D.2 : Compiles, Output is 7
D.3 : Doesn't Compile, No output
```

### q

What is the difference between a `class` and an `abstract class`?

#### s

An `abstract class` can only be inherited by another class and can not be directly instantiated. A `class` can be instantiated in most contexts, and still inherited by other classes as well. `abstract class`es must also be implemented before code will compile.


### q

If you were to create an abstract class for a `Car` -- what features could be defined in the implemented class vs. what could be defined in the abstract class? Provide justifications for your design.

#### s

Features such as car brand, model, year, and other attributes that would be highly variable would be defined in the class implmentation. Other functions that would be mostly fixed and more deterministic across different class definitions, such as `toString` and functions that get or set values would be defined in the `abstact` class.

# Grading rubric and submission

Use git, as discussed in Lab 0, to submit your work in a repo called `gitusername-worksheetJ2`. You will be graded on the following:

|Item | Points |
|the name of the repo for this lab matches the pattern  `gitusername-worksheetJ2` | 10 |
|the grader has been added as a collaborator to the repo with an invite timestamp during the lecture| 10 |
|the repo has been made private | 10 |
|the name of the answers file in your repo is  `worksheet-J2.md` | 10 |
|worksheet questions have been completed | 60 |
|TOTAL | 100 |