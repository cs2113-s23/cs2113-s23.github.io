---
layout: worksheet
permalink: /worksheet/j1
showsolution: false
---

# Worksheet: J1

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.

Create a new repo using all the steps in Lab 0 called `yourgitusername-worksheet-J1`. Then, add the following file to it:

You may submit a single file in any format; Java (with comments), text, and/or markdown (an `.md` file). It can be called anything (reasonable).

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
your solution here

### q

Write a Java class `Q2` that asks the user to enter the size of an array, then fills it with
multiples of two. Finally, it generates a string representation of the array, i.e. `{2, 4, 6, 8, ...}`

#### s
your solution here

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
your solution here

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
your solution here

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
your solution here

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
your solution here

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
your solution here

### q

What principle of OOP does the `private` declaration for variable and functions achieve? Explain.

#### s
your solution here

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
your solution here
### q

For the below questions, when the class `Point` is referenced, we are talking about the below class, which you can assume is fully implemented and working as described:

```java
public class Point {
   private double x,y; //the x,y fields
   public Point(double x,double y); //construct a point from an x,y
   public Point(Point other); //construct a point from another point
   public double getX(); //return the x component
   public double getY(); //return the y component
   public double setXY(double x, double y); //sets the x and y fields
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
your solution here

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
your solution here

### q

What if we switch line 1 and 2 in the previous question?

#### s
your solution here

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
your solution here

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
your solution here

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
your solution here

### q

Consider the UML diagram [exercise](https://cs2113-s22.github.io/j/1#using-uml-diagrams-to-plan-your-program) from the notes. Expand this to include an **intern**. An **intern** is like an employee, has a manager, unit, but has an expiration on their employment. How does this fit into the UML diagram?

Additionally, come up with one additional type for this company, describe it and add it to the UML diagram.

Include your UML diagram and explanation with this worksheet (in the file you're submitting, or as a screenshot/photo/etc).

#### s

your solution here