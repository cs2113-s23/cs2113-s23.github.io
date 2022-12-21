---
layout: toc
permalink: /j/0
---

*View all the videos from this unit a [single playlist on youtube](https://youtube.com/playlist?list=PLnVRBITSZMSOCWpDPjmYceVnpsVOlZUvL)*

# Object Oriented Programming 

There is one overarching idea in programming — from small programs, to gigantic systems: **Separation of Interface from Implementation** i.e. separating what you need to know in order to use a tool from how the tool actually works on the inside. This is behind good coding, it's behind good system design, it is behind the design of protocols like TCP, HTTP and DNS, it's behind almost anything that involves computing! This separation allows us to manage the complexity of building big programs. It allows us to reuse in a new program code that was written in some other time and place for some other purpose (and to avoid duplicating code within a program, which is wasteful and leads to errors!). It allows us to make changes to some of our code without having to worry about other parts of the program breaking. It makes collaboration easier and less error-prone. It brings many, many other benefits. In short: separating interface from implementation is a really good thing. 


# Java Objects 

Java objects are a way to group related data together into a single structure. A Java object also allows you to associate methods (both public and private) with that object to operate over the data.

To review this functionality, let's build up a working example of defining shapes and lines in the Cartesian plane. Recall that this the `x`, `y` coordinate system. For example, perhaps we have three points in the plane.

```
    y
    ^   p0
    |    * (2.3, 4.8)
    |p1
    | * (1.1, 3.0)
    |          
    |        p2 * (5.8, 0.3)
<---+---------------> x
    |
    v
```

If we wanted to define these points and some operations over them, say the distances between them, we could write the following program:

```java
public class PointDistances {

    public static void main(String args[]) {
        
        //                  p0   p1   p2
        double Xpoints[] = {2.3, 1.1, 5.8};
        double Ypoints[] = {4.8, 3.0, 0.3};

        System.out.println("dist(p0, p1) = " +
                           Math.sqrt(Math.pow(Xpoints[0] - Xpoints[1], 2) +
                                     Math.pow(Ypoints[0] - Ypoints[1], 2))
                           );
        System.out.println("dist(p0, p2) = " +
                           Math.sqrt(Math.pow(Xpoints[0] - Xpoints[2], 2) +
                                     Math.pow(Ypoints[0] - Ypoints[2], 2))
                           );
        System.out.println("dist(p0, p1) = " +
                           Math.sqrt(Math.pow(Xpoints[1] - Xpoints[2], 2) +
                                     Math.pow(Ypoints[1] - Ypoints[2], 2))
                           );
    }
}
```

While this provides some structure, it doesn't quite feel right. Adding another point would require modifying the array and writing a new method. Let's do better.

### Point Object

Let's start by defining a new class for storing a point, we'll place it in a new file called `Point.java`. A *class* is a recipe for creating objects. It defines what is stored in the object and what methods operate over. 

```java
//Point.java
public class Point {

    double x;
    double y;

    //constructor
    public Point(double X, double Y) {
        this.x = X;
        this.y = Y;
    }

    public double dist(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) +
                         Math.pow(this.y - other.y, 2));
    }
}
```

This class defines two data members, `x` and `y` to store the coordinates. It also defines a constructor for the `Point` object, and a method `dist`.


#### Constructor

A *constructor* for an object is a special method that defines how to "build" an object of the current class. In our case this is rather simple. We only need to the know the x and y coordinate, and we wish to store them.

#### this

The `this` variable is special variable that refers to the current object. Recall that the class is a definition for how to create objects, but within methods, like the constructor, we need a way to differentiate between things that are associated with the class and the current object being operated on. 

Within the constructor, the `this` keyword refers to the new object being constructed. So assigning to `this.x` or `this.y` says, store the input values `X` and `Y` within the newly created object.

(Aside: You don't explicly need to use `this`, its implied due to the scoping. Java will look for the most locally bounded `x` to use, and in this case that would be the `x` within the object being constructed. Where `this` would be strictly necessary is when you have variable shadowing. For example, if the constructor were declared as `public Point(double x, double y)` --- lowercase `x` and `y` ---then the most locally bound `x` and `y` are within the function, not the object. I find `this` helps disambiguate scoping, so sometimes I use it, but not always.)

#### `dist` method

The dist method is an object that operates over the object, called via the `.` operator, and another `Point` object. Via the `.` operate we get access to the `Point`  object whose method was called, in this case `this`, and also the `Point` object passed as an argument. Using both of those we can compute the distance between `this` point and the `other` point, returning the result.

#### `main` method

Now we can put it all together, writing a new main method.

```java
public class PointDistances2 {

    public static void main(String args[]) {

        Point p0 = new Point(2.3, 4.8);
        Point p1 = new Point(1.1, 3.0);
        Point p2 = new Point(5.8, 0.3);

        double d01 = p0.dist(p1);
        double d02 = p0.dist(p2);
        double d12 = p1.dist(p2);
        
        System.out.println("dist(p0, p1) = " + d01);
        System.out.println("dist(p0, p2) = " + d02);
        System.out.println("dist(p1, p2) = " + d12);
        
    }
}
```
```
$ java PointDistances2.java
dist(p0, p1) = 2.1633307652783933
dist(p0, p2) = 5.70087712549569
dist(p1, p2) = 5.420332093147061
```

Note that for each `Point` we have to create a `new` instance of it. That act of calling `new` invokes the constructor with the arguments, returning a new object, whose reference is stored in the variable. Later, we can use those objects to calculate distances. 


## Memory Diagrams in Java

Just like in C, we should also consider memory diagramming in Java. Let's look at the last program above `PointDistance2` and draw the memory diagram.


```
    STACK                            HEAP
     
    .-----------------.             .---------.
    | p0    |   .-----+-----------> | x | 2.2 | (Point Objects)
    |-------+---------.             |---+-----|
    | p1    |   .-----+---.         | y | 4.8 | 
    |-------+---------.    \        '---------'
    | p2    |   .-----+--.  '-----> .---------.
    |-------+---------.   \         | x | 1.1 |
    | d01   | 2.16... |    \        |---+-----|
    |-------+---------.     \       | y | 3.0 |
    | d02   | 5.70... |      \      '---------'
    |-------+---------.       '---> .---------.
    | d12   | 5.42... |             | x | 5.8 |
    '-----------------'             |---+-----|
                                    | y | 0.3 |
                                    '---------'
```

The basic types, in this case `double`, is stored on the stack, but the objects are allocated on the heap, via `new`. That means the variables in `main` *reference* those objects and thus pointers. 

Note to access members of the objects, we always use the `.` operator, and never the `->` operator in Java. That's because in Java you can never have a stack instance of an object: all objects are allocated on the heap and all variables reference a heap stored object. There is no need for two operators, and thus, as programmers are lazy, we opt for the simpler `.` operator. 

You can also view this through the [Java Visualizer for this example](https://cscircles.cemc.uwaterloo.ca/java_visualize/#code=public+class+MyPoint+%7B%0A+++%0A++++double+x%3B%0A++++double+y%3B%0A%0A++++//constructor%0A++++public+MyPoint(double+X,+double+Y)+%7B%0A++++++++this.x+%3D+X%3B%0A++++++++this.y+%3D+Y%3B%0A++++%7D%0A%0A++++public+double+dist(MyPoint+other)+%7B%0A++++++++return+Math.sqrt(Math.pow(this.x+-+other.x,+2)+%2B%0A+++++++++++++++++++++++++Math.pow(this.y+-+other.y,+2))%3B%0A++++%7D%0A%0A++++public+static+void+main(String+args%5B%5D)+%7B%0A%0A++++++++MyPoint+p0+%3D+new+MyPoint(2.3,+4.8)%3B%0A++++++++MyPoint+p1+%3D+new+MyPoint(1.1,+3.0)%3B%0A++++++++MyPoint+p2+%3D+new+MyPoint(5.8,+0.3)%3B%0A%0A++++++++double+d01+%3D+p0.dist(p1)%3B%0A++++++++double+d02+%3D+p0.dist(p2)%3B%0A++++++++double+d12+%3D+p1.dist(p2)%3B%0A++++++++%0A++++++++System.out.println(%22dist(p0,+p1)+%3D+%22+%2B+d01)%3B%0A++++++++System.out.println(%22dist(p0,+p2)+%3D+%22+%2B+d02)%3B%0A++++++++System.out.println(%22dist(p1,+p2)+%3D+%22+%2B+d12)%3B%0A++++++++%0A++++%7D%0A%7D&mode=display&curInstr=50).

## Encapsulation and Data Hiding


Java has a strong notion of *encapsulation*, which means that data and methods within objects should be limited such that they are accessed within scope. For example, the data members `x` and `y` are unprotected (default public) -- this is typically undesireable.

This means, as in the below example, we can use the `.` operator to both read the values of `p.x` and `p.y` as well as modify them. 

```java
public class PointDistances3 {

    public static void main(String args[]) {

        Point p = new Point(2.3, 4.8);

        System.out.println("p = (" + p.x + "," + p.y + ")");

        p.x = 5;
        p.y = 10;

        System.out.println("p = (" + p.x + "," + p.y + ")");
        
    }
}
```

However, this can be dangerous and violates the principal of encapsulation where only access data where most appropriate. 

### Public vs. Private Data Members

Java uses a notion of `public` and `private` declarations on both object data and methods to limit how programmers can use the object. (There is also `protected` but we'll get to that later.)

Perhaps we want to better control access to the data members, we can declare `x` and `y` as `private` class members. 

```java
public class Point {

   private double x;
   private double y;

   //..
```

And this will cause a compiler error when accessing `x` and `y` directly using the `.` operator. 

```
PointAccess.java:9: error: x has private access in Point
        p.x = 5;
         ^
PointAccess.java:10: error: y has private access in Point
        p.y = 10;
```

### Getter and Setters

So if we have private members, how do we access these members? We use getter and setter methods.

``` java
public class Point {

    private double x;
    private double y;

    public Point(double X, double Y){
        this.x = X;
        this.y = Y;
    }

    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }
    
    public double dist(Point other){
        return Math.sqrt( Math.pow(this.x - other.x, 2) +
                          Math.pow(this.y - other.y, 2));
    }
}
```

And then we call those public methods, rather than accessing the data members directly.

```java
public class PointAccess2 {

    public static void main(String args[]) {

        Point p = new Point(2.3, 4.8);

        System.out.println("p = (" + p.getX() + "," + p.getY() + ")");

        p.setX(5);
        p.setY(10);

        System.out.println("p = (" + p.getX() + "," + p.getY() + ")");
        
    }
}
```

Perhaps, we also want a method in `Point` to set both X and Y and the same time. So we can add that as well
```java
  public void setXY(double x, double y) {
        this.x = X;
        this.y = Y;
    }
```

And wait, that's also very much like the constructor, so why not call that there?!? 

```java
 public Point(double x, double y) {
        setXY(x,y);
    }
```

And now we're really programming.

### `toString()`

As we start to develop our `Point` class, we should still be somewhat unsatisfied with the printing routine. 

```
   System.out.println("p = (" + p.getX() + "," + p.getY() + ")");
```

This also seems like a violation of encapsulation because the representation of the `Point` as "(x,y)" string shouldn't change regardless of the instance of the `Point`. Instead, it should be part of the class definition of the object. It would make a lot more sense to have a method that returned the string representation. Even better, it would be great if we could just do the following, like we do with basic types?

```java
System.out.println("p=" + p);
```

If you were to try that, what you'd find is that something does print, but not what you expect

```
p = Point@5acf9800
p = Point@5acf9800
```

That string is the Java reference representation. Essentially say that it's the Point stored at reference `5acf9800`. The `+` operator with a string and a non-string object will automatically convert the non string object to a string by calling a special method `toString()` on that object. If one isn't defined, then the default `toString()` method is called inheritted from the `Object` class.

But, we can overwrite the default `toString()` to write our own specific for `Point`. 

```java
public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
```

Now, we've further simplified our main method

```java
public class PointAccess3 {

    public static void main(String args[]) {

        Point p = new Point(2.3, 4.8);

        System.out.println("p = " + p);

        p.setXY(5, 10);

        System.out.println("p = " + p);
        
    }
}
```

## More advanced object programming

### Line object

Suppose now we want to extend on our object model and create a new `Line` class that builds on our notion of `Point`. A `Line` can simply be defined as a combination of two points, a start and and end. This is relatively straightforward to write the `length()` method and `toString()` method given our work on `Point`. 

```java
public class Line {

    private Point start;
    private Point end;

    public Line(double x1, double y1, double x2, double y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);
    }

    public double length() {
        return start.dist(end);
    }

    public String toString() {
        return "[" + start + ":" + end + "]";
    }

}
```

Here's an example `main` method

```java
public class LineExample1 {

    public static void main(String args[]) {

        Line l = new Line(4.0, 6.0, 5.0, 7.0);

        System.out.println("l = " + l);
        System.out.println("l.length = " + l.length());
    }
}
```

### Method and Constructor Overloading

Looking at the constructor for `Line`, you might wonder --- it's just taking two `Points`, why not pass two `Points`? Yes. Totally true, but it would also be nice to keep the other constructor, based on 4 doubles. Good news everyone! You can do both by *overloading* the constructor. 

In our `Line` class, let's define a second constructor. It has the same name of the other constructor, but takes different arguments. Here are the two constructors side by side.

```java
    public Line(Point p1, Point p2) {
        start = p1;
        end = p2;
    }
    public Line(double x1, double y1, double x2, double y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);
    }

```

How does Java choose between these two constructors? It does type inspection on the arguments and matches to the constructor with the right matching types. So you cannot have two constructors (or methods within scope of an object/class) with the same name and the same arguments (unless their ordered types differ -- the compiler has to be able to differentiate them), otherwise Java will not know which method to call. 

### Maintaining Encapsulation with Objects References

The new `Line` constructor that takes two `Point`s bring up an interesting new problem regarding encapsulation. Consider the getter method for `Line` that retrieves one of the points:

```java

    public Point getStart() {
        return start;
    }
```

If this method were called by the user on a `Line` instance, than altering the `Point` `start` would allow the user to alter the data stored within that `Line` instance, even though it is set to `private`. Also, consider the new constructor

```java
    public Line(Point p1, Point p2) {
        start = p1;
        end = p2;
    }
```

The user passing the point `p1` could still alter it, even after using it in the constructor. To see this, let's look at a memory diagram, where we can see that we only have one object referenced by `start`, `p1` and `s` at point `**A**`. Modification by using `.` from any of these references modifies the same object. After modifying, it prints `[(20,10):(2.1,3.4)]` despite no calls to `l` to modify the underlying data it stores. 

```java
public class LineExample2 {

    public static void main(String args[]) {

        Point p1 = new Point(4.0, 8.5);
        Point p2 = new Point(2.1, 3.4);
        Line l = new Line(p1, p2); 

        Point s = l.getStart();//**A**

        s.setXY(20.0, 10.0);  //**B**
        System.out.println("l = " + l);// [(20,10):(2.1,3.4)]

    }
}
```

```
   **A**                                                
   
   STACK                    HEAP                       
                            
                             .--------.       
                   .-------> | x | 4.0| <-----.
  .----------.    /          |--------|       |
  | p1  |  .-+---'    .----> | y | 8.5|       |
  |-----+----|       /       '--------'       |
  | p2  |  .-+----. /                         |
  |-----+----|     X         .--------.       |
  | l   |  .-+--. / \        | x | 4.0| <-----+--.
  |-----+----|   X   '-----> |--------|       |  |
  | s   |  .-+-./ \          | y | 8.5|       |  |
  '----------'     \         '--------'       |  |
                    \                         |  |
                     \       .----------.     |  |
                      '----> |start | .-+-----'  |
                             |------+---|        |
                             |  end | .-+--------'
                             '----------'           
```

Now --- before solving this problem, you might ask is this really a problem? It really depends on the program. In many cases this may be the desired functionality. Here, though, we want to ensure that only via calls to modifiers within `Line` can we alter the underlying data storage. 

### Copy Constructor

In this case, let's solve this problem by creating a new overloaded constructor for `Point` that takes another `Point` instance as it's argument, constructing a new `Point` object based on it's `x` and `y` value. Like in the example below

```java
   public Point(Point other) { 
      this.x = other.getX();
      this.y = other.getY();
   }
```

And in our `Line` constructor, that takes two `Point`s, we modify that to call this `Point` constructor to perform a copy. 

```java
   public Line(Point p1, Point p2) {
     this.start = new Point(p1);
     this.end = new Point(p2);
   }

```

Similarly, when we call `getStart()` or `getEnd()`, we also use this constructor

```java
    public Point getStart() {
        return new Point(this.start);
    }
    public Point getEnd() {
        return new Point(this.end);
    }
```

Looking at the memory diagram for the main method above, with these modication, we see there are no longer shared references (at `**B**`). 

```
   **B** 
   
   STACK                      HEAP
                            
                             .--------.          .--------.
                   .-------> | x | 4.0|   .----> | x | 4.0|
  .----------.    /          |--------|   |      |--------|
  | p1  |  .-+---'           | y | 8.5|   |      | y | 8.5|
  |-----+----|               '--------'   |      '--------'
  | p2  |  .-+----.                       |              
  |-----+----|     \         .--------.   |      .--------.
  | l   |  .-+--.   \        | x | 4.0|   | .--> | x | 4.0|
  |-----+----|   \   '-----> |--------|   | |    |--------|
  | s   |  .-+-.  \          | y | 8.5|   | |    | y | 8.5|
  '----------'  \  \         '--------'   | |    '--------'
                 \  \                     | |
                  \  \       .----------. | |    .--------.
                   \  '----> |start | .-+-' | .->| x |20.0|
                    \        |------+---|   | |  |--------|
                     \       |  end | .-+---' |  | y |10.0|
                      \      '----------'     |  '--------'
                       '----------------------'      
                             
```                             

### Deep vs. Shallow copy

The new `Point` constructor is called **deep copy** constructor because it copies the object to a new object, without any references between them. That is relatively straightforward to do here because `Point`'s data members are both basic types. In contrast, a **shallow copy** which creates a new object that shares references. 

As an example, consider the following method for create a copy of the current line. (Note, this wouldn't compile due to `start` and `end` as private members.)

```java
  public Line(Line other) {
    this.start = other.start;
    this.end = other.end;
  }
```

Doing so would mean the new `Line` object (`this`) and the other `Line` object (`other`) would actually share the underlying data storage `Point`s. This is a *shallow* copy. Alternatively, we can do a *deep* copy of the line using the facilities already in place.

```java
  public Line(Line other) {
    this.start = other.getStart(); //returns a copy of Point start
    this.end = other.getEnd(); //returns a copy of Point end
  }
```

With that copy constructor in place, we can write a `copy()` method really easily

```java
  public Line copy() {
    return new Line(this); //construct new object based on this object
  }
```



## Private Methods

Just like with data, we can also declare a method private if they shouldn't be called outside the scope of the object. To see an example of this, let's consider adding a new data member to the `Line` class, `slope`.

The slope line is calculated as run over rise. For the example two points below (forming a line), the run is the distance between their `x` components (-4), and the rise is the distance between their `y` components (8).

```
    y
    ^   
    |    . (2,9)
    |    :\
    |  8 : \
    |    :  \    
    |    :.... (6,1)
    |     -4
<---+---------------> x
    |
    v
```

The slope of a line is run divided by rise, or 8/4 = -2. Importantly, to calculate the slope, we need to order the points by their `x` component. 

### Adding Slope

Let's start by modifying the `List` class to add a private member `slope`, and we can set that slope at construction once `start` and `end` is established. 

```java
public class Line {

    private Point start;
    private Point end;
    private double slope;
    
    public Line(double x1, double y1, double x2, double y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);
        slope = calcSlope();
    }

    public Line(Point p1, Point p2) {
        start = new Point(p1);
        end = new Point(p2);
        slope = calcSlope();
    }
    
    \\ ...
    
    public getSlope() {
        return slope;
    }
    
    private double calcSlope() {
        double run, rise;
        
        if(end.getX() > start.getX()) {
            run = end.getX() - start.getX();
            rise = end.getY() - start.getY();
        }else {
            run = start.getX() - end.getX();
            rise = start.getY() - end.getY();
        }
        
        return rise / run;
    }
```

Looking at the method `calcSlope()`, it will never need to be called anywhere but in the constructor for the `Line`. It only needs to be called once, and if the user wishes to learn the slope, they can call `getSlope()`. As a result, this method doesn't need to be `public`, and so it is declared `private`. 

Additionally, we could simplify our `calcSlope()` with an additional private method that reorders the points during construction such that the start point always has the lower x-value compared to the end point. 

```java
   private void orderPoints() {
        if(end.getX() < start.getX()) {
            Point tmp = start;
            start = end;
            end = start;
        }
    }
```

Again we want this to be a private method; the user shouldn't call it directly. Instead, it's called at construction to get the points in order, simplifying the `calcSlope()` method in the process.

```java
public Line(double x1, double y1, double x2, double y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);
        orderPoints();
        slope = calcSlope();
    }

    public Line(Point p1, Point p2) {
        start = new Point(p1);
        end = new Point(p2);
        orderPoints();
        slope = calcSlope();
    }

    
    private double calcSlope() {
        double run = end.getX() - start.getX();
        double rise = end.getY() - start.getY();
        return rise / run;
    }

    private void orderPoints() {
        if(end.getX() < start.getX()) {
            Point tmp = start;
            start = end;
            end = start;
        }
    }
```


## Static Methods

The `static` declarations for classes allow functions and data to be accessible within the class without needing to instantiate an object instance of that class. 

The classic example of `static` is the `main` method. It's declared static, that's because when you execute the `main` method of a class, you're not instantiating an instance of that class as an object, and then calling `main`. Instead you're calling `main` directly, or statically, without an instance. You can think of this as calling `main` **on** the class -- infact, this is how static methods are typically called (besides `main`, which is special).

Within the object oriented model, we use `static` to provide utility functions or constants that don't require object instantiating. You are probably familiar with [`Integer.parseInt()`](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/lang/Integer.html#parseInt(java.lang.String)). The `Integer` class is an object version of the basic type, and it is common to need to create an `int` by parsing a String, like:

```java

  int a = Integer.parseInt("5"); //a gets then integer 5
```

You'll notice that we don't create a new `Integer` object to call the member function `parseInt()`. We call `parseInt()` directly and statically (on the `Integer` class with the dot operator, rather than on an `Integer` object). 

### Static Slope Calculation

Calculating the slope of a line is a fairly useful utility that we can attach to our `Line` class as a static method. Doing so means a user doesn't have to create a `Line` in order to calculate the slope of the line that connects two points. 

We can write that `static` method like so, and note that it's also `public` because it is called outside the context of the class. 

```java
   public static double calcSlope(Point p1, Point p2) {
        if(p1.getX() > p2.getX())
            return (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
        else
            return (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
    }
```

Additionally, we can't rely on the points to be properly ordered, since we're not instantiating a `Line`. So we have to use if/else. Now we can write a `main` method taking advantage of the new static method. 

```java

public class staticSlope {

    public static void main(String args[]) {
        Point p1 = new Point(2, 9);
        Point p2 = new Point(6, 1);

        //Also, java has printf :)
        System.out.printf("p1: %s p2: %s dist(p1,p2): %.2f slope: %.2f\n",
                          p1, p2,
                          p1.dist(p2),
                          Line.calcSlope(p1, p2));
        //%.2f -- says print two decimal points 
        
    }

}

```

And just for fun  --- Java also have `printf()` :) --- which, in some cases is easier to use than `println()` or `print()`.


## Arrays

Java has arrays that store aligned data that is indexable with the `[]` operator. Arrays are actually objects; an `Array` object created with a `new` call. Like other objects, they are dynamically allocated.

### Arrays of Basic Types

To create an array of a basic type, we must first declare the variable to be an array object. This is done by adding `[]` to the variable name at declaration. 

```java
  int intArray1[];
  int[] intArray2;
```

Then we need to *allocate* the array object of that type. This works much like `calloc()` call in C. Java determines the size of each element based on the type (in this case `int`) and the number of items based on the allocation (in this case 10). As this is a dynamic allocation, we need the `new` operator.

```java
  intArray = new int[10];
```

The new array is zero'ed out. The memory diagram looks like the following.

```
  STACK                    HEAP
  
  .--------------.        .---.
  | intArray | .-+----->  | 0 |  intArray[0]
  '--------------'        |---|
                          | 0 |  intArray[1]
                          |---|
                          :   :
                          :   :
                          |---|
                          | 0 |  intArray[9]
                          '---'

```

For all basic types, the initial value of the array is always the zero value. For `bool`, this would be `false`.

### Arrays of Objects

Arrays of Objects are declared in the same way. For example, an array of `Point`s.

```java
    Point pArray[] =  new Points[10];
```

And just like with basic types, the new array is zero'ed out. But recall that the value of a object variable is a memory reference. So the zero'ed out value is `null`. *You still have to instantiate each of the `Point` objects of the array*.

```java
   for(int i = 0; i < 10; i++)
      pArray[i] = new Point(i, i);
```

As a memory diagram, we would have the following

```
  STACK                    HEAP
                                 .-------.
  .------------.        .---. .->| x | 0 |
  | pArray | .-+----->  | .-+-'  |-------| pArray[0]
  '------------'        |---|    | y | 0 |
                        | .-+-.  '-------' 
                        |---| |  .-------.
                        :   : '->| x | 1 |
                        :   :    |-------| pArray[1]
                        |---|    | y | 1 |
                        | .-+-.  '-------'
                        '---' |     :
                Array Object  |     :
                              |  .-------.
                              '->| x | 9 |
                                 |-------| pArray[9]
                                 | y | 9 |
                                 '-------'
                                 
                                 Point Objects
```

### Using `{}` to allocate an array

You can also allocate arrays without a `new` call by specifying directly using `{}` and the initial values. This works for both basic array types and object array types. 

```java
  int intArray[] = {0, 1, 2, 3, 4};
  Point pArray[] = {new Point(0, 0), new Point(1, 1), new Point(2, 2)};
```

Even when using initial value allocation, it's still an allocation. Under the covers, Java calls `new`, allocates the array, and assigns the initial values for you. 

### Iterating over an Array

Arrays, as objects, carry with them data and methods to support different operation. Most immediate is that an array object stores its length, which you can use to assure you do not access beyond the range of the array. If you do, this throws an exception, and if not caught, your program crashes. 

Using the length member, we can simply iterate over the array using indexes. Here's a routine that prints the distance between consecutive points in the array. 

```java
    Point lastPoint = null;
    for(int i = 0; i < pArray.length; i++) {
       if (lastPoint != null) {
          System.out.printf("dist(%s, %s) = %.2f\n",
                             lastPoint,
                             pArray[i],
                             lastPoint.dist(pArray[i]));
       }
       lastPoint = pArray[i];
   }
```

However, you may notice that this is a bit wasteful because `i` is only used for indexing. Instead, it would be nice to use a for-each semantic, where we say "for each point in the array" without the index. Java provides a way to simplify iteration when the "for-each" semantic is desired. 

```java
    Point lastPoint = null;
    for(Point p : pArray) {
       if (lastPoint != null) {
          System.out.printf("dist(%s, %s) = %.2f\n",
                             lastPoint,
                             p,
                             lastPoint.dist(p));
       }
       lastPoint = p;
   }
```

At each step the iteration, `p` gets assigned the next `Point` in the array, without having to use the index feature. Any object that implements `Iterable` can be used in this syntax. 

---
Material on this page adopted  with permission from [USNA courses ic211](https://www.usna.edu/Users/cs/nchamber/courses/ic211/s19/index.html), taught by Nate Chambers, Gavin Taylor, Chris Brown, and many others. Thank you. 
