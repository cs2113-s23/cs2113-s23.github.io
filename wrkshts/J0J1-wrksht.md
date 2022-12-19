---
layout: worksheet
permalink: /worksheet/j0j1
showsolution: true
---

# Worksheet: J0/J1

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.

* Github Classroom Link: [https://classroom.github.com/a/e1tWK6V2](https://classroom.github.com/a/e1tWK6V2)

## Note

Attempt to answer these questions before running the code. This will improve your ability to analyize and reason about code without an IDE or compiler. This skill we be helpful on the exams.

## Questions

### q

Which of the following is an object and which is a basic type?

```java

int a;
double b;
int c[] = {1, 2, 3};
String s = "Hello World";
```

#### s
* In the first two lines, `int` and `double` are basic types.
* In the last two lines, array and `String` are objects.

### q

Write a Java program `Q2.java` that is roughly the equivalent of this C code

```c
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main() {
    
    int c;
    printf("Enter an array length greater than 0 and less than or equal to 15:\n");
    scanf("%d", &c);

    if(c < 0 || c > 15) {
        fprintf(stderr,"ERROR: Invalid length %d\n", c);
        exit(1);
    }
    
    
    double * a = calloc(c, sizeof(double));    
    for(int i = 0; i < c; i++) {
        if(i > 0) {
            a[i] = (a[i - 1] + 2) *3.15;
        }else {
            a[i] = 10.8;
        }
    }
 
   char str[1024];
   strcpy(str, "{ ");//copy start of string
   for(int i = 0; i < c; i++) {
   
      //format print into cur_str
      char cur_str[32];
      snprintf(cur_str,32,"%d\n", i, (int) a[i]);
      
      //concatenate cur_str onto str
      strcat(str, cur_str);
   }
   strcat(str," }");
   
   //results prints a as { x y z ... w }
   printf("%s\n", str);
}
```

#### s
```java
import java.util.Scanner;

public class Q2 {
    public static void main(String args[]) {
        int c;
        System.out.println("Enter an array length greater than 0 and less than or equal to 15:");
        
        Scanner sc = new Scanner(System.in);
        c = sc.nextInt();
        sc.close();

        if(c < 0 || c > 15) {
            System.err.printf("ERROR: Invalid length %d\n",c);
            System.exit(1);
        }

        double a[] = new double[c];

        for(int i = 0; i < c; i++) {
            if(i > 0) {
                a[i] = (a[i - 1] + 2) *3.15;
            }else {
                a[i] = 10.8;
            }
        }

        String str = "{ ";

        for(int i = 0; i < c; i++){
            str += "" + ((int)a[i]) + " ";
        }
        str += "}";

        System.out.println(str);
    }
}
```

### q

Draw a memory diagram of your resulting `Q2.java` program you wrote for above? Be sure to label the `stack` and the `heap`

#### s
```
 stack                          heap
.-----------.                  .--------------.
|  c  |     |          .------>|  a[0]  |     |
|-----+-----|          |       |--------+-----|
|  a  |  .-------------'       |  a[1]  |     |
|-----+-----|                  |--------+-----|
| str |     |                  |  a[2]  |     |
'-----------'                  |--------+-----|
                               |  ...   |     |
                               |--------+-----|
                               |  a[n]  |     |
                               '--------------'
```

### q

Two part question:

(A) What is a static method in Java?

(B) Why does the main method need to be a static method?

```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("hello, world");
    }
}
```

#### s
* Static methods are called directly, or statically, without the need of an object instance.
* `main` methods need to be static because it needs to be called without instantiating an object of the class.

### q

What is the output of the following main method?

```java
public static void main(String args[]) {
    String str = "Java is my favorite language";
    str += '!';
    System.out.println(str + " and python is my second");
}
```

#### s
The output is `Java is my favorite language! and python is my second`.

### q

What is the output of the following programs?

```java
/* Program 1 */
public static void main(final String args[]) {
    String choice = new String("A");
    if (choice == "A") {
        System.out.println("Correct");
    }
    else {
        System.out.println("Wrong");
    }
}
```

```java
/* Program 2 */
public static void main(final String args[]) {
    String choice = new String("A");
    if (choice.equals("A")) {
        System.out.println("Correct");
    }
    else {
        System.out.println("Wrong");
    }
}
```

#### s
* Program 1's output is `Wrong` and Program 2's output is `Correct`.
* Similar to the case in C, `String` is an object type where `String choice` will make `choice` have the address of the string on the heap. Thus, in Program 1, the if statement is comparing a memory address to a string; while in Program 2, the `.equals()` method will compare the content `choice` is pointing at to the string `"A"`.

### q

Does the below program change the season? Why, or why not?

```java
static void change_season(String str) {
    str = "Spring";
}

public static void main(final String args[]) {
    String season = "Winter";
    change_season(season);
    System.out.println("The current season is: " + season);
}
```

#### s
The program does not change the season, because the original season variable, which is a pointer to
`Winter` is reassigned to a new string, `Spring`, inside the method. That breaks the link with the original season in `main`.

### q

What is the output of the main method below? Please explain.

```java
public class Point {
    double x = 0;
    double y = 0;

    public Point(double x, double y) {
        x = x;
        y = y;
    }
}
```

```java
public static void main(final String args[]) {
    Point point = new Point(1, 2);
    System.out.println("X: " + point.x + " Y: " + point.y);
}
```

#### s
The output of the main method is `X: 0.0 Y: 0.0`. The reason is in the `Point` class, the constructor does not pass the parameters `x` and `y` onto `x` and `y` of the `Point` class.

### q

What principle of OOP does the `private` declaration for variable and functions achieve? Explain.

#### s
The priciple of Encapsulation is achieved by `private` declaration of variable and functions. The reason is `private` variable and functions cannot be accessed by other objects.

### q

In the Point class below, how does Java choose between the two constructors.

```java
public class Point {

   private double x, y; 
   
   public Point(double x, double y) {
        this.x = x;
        this.y = y;
   }

   public Point(Point other) {
       this.x = other.getX();
       this.y = other.getY();
   }

}
```

#### s
Java chooses between the two constructors by checking the type of the argument passed in. When a new `Point` is called with two `double`s, it will use the constructor `public Point(double x, double y)`; in contrast, when a new `Point` is called with a `Point` passed in, it will use the constructor `public Point(Point other)`.

### q

For the below questions, when the class `Point` is referenced, we are talking about the below class, which you can assume is fully implemented and working as described:

```java
public class Point {
   private double x,y; //the x,y fields
   public Point(double x,double y); //construct a point from an x,y
   public Point(Point other); //construct a point from another point
   public double getX(); //return the x component
   public double getY(); //return the y component
   public double setXY(double x, double y); //return the x component
   public String toString(); //return the string representation
   private double sum_x_y(); // Returns the sum of X and Y
}

```


Say we want to make a class that extends `Point` with a method that can reflect a point across the X and Y axis:

```java
public class CustomPoint extends Point {
    public void reflect(); // Reflects point
}
```

Which of the following implementations achieves this?

```java
    // Option 1
    public void reflect() {
        x = -x;
        y = -y;
    }

    // Option 2
    public void reflect() {
        this.x = -this.x;
        this.y = -this.y;
    }

    // Option 3
    public void reflect() {
        this = Point(-x,-y);
    }
    
    // Option 4
    public void reflect() {
        double x = -this.getX();
        double y =-this.getY();
        this.setXY(x,y);
    }
    
    // Option 5
    public void reflect() {
        x = -this.getX();
        y = -this.getY();
    }
```

Explain why. 

#### s
Option 4 achieves the goal of reflecting the point. None of the other options compiles because `x` and `y` in `CustomPoint` is inheritted from `Point` and cannot be read/written to directly in `CustomPoint` since they are declared private in `Point`.

### q

If we add this constructor to `CustomPoint`:

```java
    public CustomPoint() {
        setXY(10, 10); // Line 1
        super(0, 0); // Line 2
    }
```

...and then run this program,  what is the output?

```java
    public static void main(final String args[]) {
        CustomPoint p = new CustomPoint();
        System.out.println(p.toString());
    }
```

#### s
The program does not compile because the constructor call on `Line 2` must be called before anything else inside the constructor for `CustomPoint`.

### q

What if we switch line 1 and 2 in the previous question?

#### s
Now the program compiles and the output is `(10.0, 10.0)`.

### q

If we want to re-implement `sum_x_y` in our custom point, but first reflect the point before returning the sum, which of the following implementations are valid? (Note: assume that `reflect` has a valid implementation)

```java
    //Option 1
    public double sum_x_y() {
        this.reflect()
        return super.sum_x_y();
    }

    //Option 2
    public double sum_x_y() {
        this.reflect();
        return this.getX() + this.getY();
    }

    //Option 3
    public double custom_sum_x_y() {
        this.reflect()
        return super.sum_x_y();
    }

    //Option 4
    public double custom_sum_x_y() {
        this.reflect();
        return this.getX() + this.getY();
    }

```

Explain your answer?

#### s
Option 2 is valid. Option 1 and 3 does not compile because `sum_x_y()` is a private method that cannot be accessed by `CustomPoint` class. Option 4 has the correct implementation of the method but does not override `sum_x_y` as it has a different function name.

### q

Consider the following class

```java

public class Racecar {

    private int number; 
    private Driver driver; //assume implemented properly
    protected String sponsor = null;
    public Racecar(int n, Driver d) {
        number = n;
        driver = d;
    }

    public String toString() {
        return "Car #" + number + " Driver: " + driver;
    }
    
    protected addSponsor(String sp) {
        sponsor = sp;
    }
}
```

Suppose we want to extend this to a `FormulaOne` class which has a make, e.g., Mercedes, complete the constructor and `toString()` method that would make this functional?

```java

public class FormulaOne extends Racecar {
    private String make;

    //TODO
}
```

#### s
```java

public class FormulaOne extends Racecar {

    private String make;
    public FormulaOne( int n, Driver name, String car_make) {
        super(n, name);
        make = car_make;

    }

    @Override
    public String toString() {
        return super.toString() +" Make: " + this.make;
    }
    
}

```

### q 

Using the `Racecar` and `FormulaOne` classes above, if we had a main method

```java

public static void main(String args[]) {


   Racecar r = new Racecar(/* ... some args .. */);
   r.addSponsor("Home Depot"); //<--A

   FormulaOne f1 = new FormulaOne(/* ... some args .. */);
   f1.addSponsor("Home Depot"); //<--B
     
}
```

Does the code work at mark `A` or mark `B` or neither? Explain.

#### s
The code works at both marks. Each mark is adding a sponsor to two different objects, so there is not a conflict of overwriting the other. Since `F1` inherits from `Racecar`, it also has access to the `addSponsor` function even though it was not explicitly defined in the F1 class.

### q

Consider the UML diagram [exercise](https://cs2113-s22.github.io/j/1#using-uml-diagrams-to-plan-your-program) from the notes. Expand this to include an **intern**. An **intern** is like an employee, has a manager, unit, but has an expiration on their employment. How does this fit into the UML diagram?

Additionally, come up with one additional type for this company, describe it and add it to the UML diagram.

Include your UML diagram and explanation with this worksheet. 

#### s
