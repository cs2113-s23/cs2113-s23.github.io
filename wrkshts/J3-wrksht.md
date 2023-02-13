---
layout: worksheet
permalink: /worksheet/j3
showsolution: false
---

# Worksheet: J3

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.

Submit a file called `worksheet-J3.md` in your repo for this assignment.

## Note

Attempt to answer these questions before running the code. This will improve your ability to analyize and reason about code without an IDE or compiler. This skill we be helpful on the exams.

## Questions

### q

Consider the class declaration below:

```java
// Is this possible?
public class Lion extends Mammal, Carnivore {
    // ...
}
```

* Explain why the following class declaration is not possible in Java. 
* What are the limitations of the  `extends` key word?
* How can you accomplish this inheritance structure task in Java?

#### s

Java classes can only extend from one class! So we can have `Lion extends Mammal` or `Lion extends Carnivore`, but not both. To accomplish functionality similar to this, we would have:

```java
public class Lion extends Mammal implements Carnivore {
    // ...
}
```
As it makes sense for an animal to inherit from the specific class it is (Mammalia), and for it to have an interface that represents how it interacts (or interfaces) with food.

### q

What are some of the functional differences between an `abstract class` and an `interface`? Use the example below to answer this question.

```java
public abstract class Employee {
    // ...
}

// vs.

public interface Employee {
    //...
}
```



#### s
Abstract classes and interfaces can:

* Provide _just_ method declarations that a class must realize

Abstract classes do (and interfaces do not):

* Provide methods and constructors a child class can choose to use, or override
* Provide inherited class variables
* Have access modifiers for inherited methods
* Require the child class to inherit only itself, and no other class


### q

Consider the interfaces for a `Stack` and `Queue` of `int`s. 
```java
public interface Stack {
   public void push(int v);
   public int pop();
   public int peek();
}

public interface Queue {
   public void enqueue(int v);
   public int dequeue();
   public int peek();
}
```


Now suppose you had a `LinkedList` implementation to store `int`s with the following methods defined. 

```java
public class LinkedList implements Stack, Queue {
  public LinkedList() {/*...*/}
  public void addToFront(int v) {/*...*/}
  public int rmFromFront() {/*...*/}
  public void addToBack(int v) {/*...*/}
  public void rmFromBack() {/*...*/}
  
  //FINISH HERE
  
}
```

Using those methods in `LinkedList` complete the realization of a `Stack` and `Queue`:

#### s


```java

public void push(int v) { 
    addToFront(v);
}
public int pop() { 
    rmFromFront(v);
}
public void enqueue(int v) {
    addToBack(v);
}
public int dequeue() {
    rmFromFront(v);
}
public int peek() {
    int v = rmFromFront(v); 
    addToFront(v); 
    return v;
}
```


### q

Rewrite the `Stack` and `Queue` interfaces from above to be generic, as well as the `LinkedList`. Explain how this is now generic to manage collections of any class. 

#### s

```java
public interface Stack<T> {
   public void push(T v);
   public T pop();
   public T peek();
}

public interface Queue<T> {
   public void enqueue(T v);
   public T dequeue();
   public T peek();
}

public class LinkedList<T> implements Stack<T>, Queue<T> {

  public LinkedList() {/*...*/}
  public void addToFront(T v) {/*...*/}
  public T rmFromFront() {/*...*/}
  public void addToBack(T v) {/*...*/}
  public void rmFromBack() {/*...*/}

  public void push(T v) { 
    addToFront(v);
  }
  public T pop() { 
    rmFromFront(v);
  }
  public void enqueue(T v) {
    addToBack(v);
  }
  public T dequeue() {
    rmFromFront(v);
  }
  public T peek() {
    T v = rmFromFront(v); 
    addToFront(v); 
    return v;
  }
}
```

### q

Suppose you have interfaces `Adder` and `Multiplier`:

```java
public interface Adder<T> {
    T add(T a, T b);
}
```

```java
public interface Multiplier<T> {
    T multiply(T a, T b);
}
```

Finish the implementation of the `IntegerCalculator` and `FloatCalculator` classes below. 

```java
public class IntegerCalculator implements Adder<Integer>, Multiplier<Integer> {
    private String calculatorName;

    public IntegerCalculator(String calculatorName) {
        this.calculatorName = calculatorName;
    }

    public String getCalculatorName() {
        return calculatorName;
    }

    // TODO: add the methods needed to implement the adder and multiplier interfaces.
}
```

```java
public class FloatCalculator implements  Adder<Float>, Multiplier<Float> {
    private String calculatorName;

    public FloatCalculator(String calculatorName) {
        this.calculatorName = calculatorName;
    }

    public String getCalculatorName() {
        return calculatorName;
    }

    // TODO: add the methods needed to implement the adder and multiplier interfaces.
}
```

#### s

```java
public class IntegerCalculator implements Adder<Integer>, Multiplier<Integer> {
    // ...
	public Integer add(Integer a, Integer b) {
		return a + b;
	}

	public Integer multiply(Integer a, Integer b) {
		return a * b;
	}
}
```

```java
public class FloatCalculator implements  Adder<Float>, Multiplier<Float> {
    // ...
	public Float add(Float a, Float b) {
		return a + b;
	}

	public Float multiply(Float a, Float b) {
		return a * b;
	}
}
```

### q

Review the following Java util data structures:

* [`ArrayList`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/ArrayList.html) 
* [`Hashtable`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Hashtable.html)
* [`TreeMap`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/TreeMap.html)

For each, 
* Provide a two-to-three sentence description of each class
* For each interface realized by these classes, also provide a two-to-three sentence description. 
* Finally draw a UML diagram that connects all of these classes back to object

#### s

`ArrayList`

* `ArrayList` is an array of some type, that implements the `List` interface, meaning it has methods so the user of `ArrayList` has precise control over where in the list each element is inserted. The user can access elements by their integer index (position in the list), and search for elements in the list. When the array reaches its total capacity, it is resized.
* `AbstractList`
* See first answer for an explanation of the `List` interface. `Cloneable`, means that there is some `clone` method that will return a complete copy in memory of the implementing class, and `Serializable`, provides a method that guarantees the class can be "serialized", or turned into some ascii representation.


`HashTable`
* `Hashtable` maps objects of one type to another, by calling `hashCode` on the key object, in order to find the value. This means that any type used for the key must implement that method. Similar to the hashmap in the boggle project, the Hashtable class has an array of buckets, and when getting a value, the key hash maps to one of these buckets, which is then searched.
* `Map`, `Cloneable`, `Serializable`
* No other class extends `Dictionary`, but many classes implement map, such as `TreeMap` and `AbstractMap`.
* `Map` Provides methods so a user can pass some sort of key, and a value is returned. It also guarantees there is some list of keys, values, and key-value mappings available. Finally, it provides users the ability to check if the mapping is empty, if it contains a key or value, and a way to delete or update key-value pairs. See previous for `Cloneable` and `Serializable`

`TreeMap`

* Another map, except that instead of implementing it using a hash table, it is implemented using a Red-Black tree (see [here](https://en.wikipedia.org/wiki/Red%E2%80%93black_tree)) for a refresher). What happens, is we search for a node that represents our key-value pair.
* `AbstractMap`, which is an abstract class implementing `Map` but with only some basic methods implemented
* `NavigableMap`, `Cloneable`, `Serializable`
* `NavigableMap` is similar to the `Map` interface, except it also requires that all keys and values can be sorted. The additional methods required are ways for the user to get either the max/min keys and values, or the key/value that is immediately greater/lesser than a given key or value. We see that since a Red-Black tree is a sorted binary tree, that means the data stored in it must be sorted, and so we implement `NavigableMap`.


### q

Take a look at the documentation for `LinkedHashSet`: [https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/LinkedHashSet.html](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/LinkedHashSet.html)
and `HashSet`:
[https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/HashSet.html](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/HashSet.html)

When would it be preferable to use a `LinkedHashSet` instead of a `HashSet`?


#### s
We would use the `LinkedHashSet` when we have a set of items that we want to keep in a specific unchanging order. For example, if we want a set of colors, we'd use `HashSet`, since there can only be at most one instance of a color (it wouldn't make sense to store two identical reds), but there isn't an order to colors (the rainbow is only one way to order them, but it's not THE way to). If we wanted a set of integers, we'd want to use `LinkedHashSet`, since integers are inherently comparable and sortable, and we don't want them to be in a random order.

### q

The code below does not use Java generics. Update it to do so. Notably, there should not need casting, but no, the solution isn't just removing the `(String)` casting before the `.get` method. 

```java
import java.util.HashMap;

public class TestHashMap {

    public static void main (String[] argv) {
        // Create a new hashmap.
        Hashtable fabFour = new HashMap();

        // Insert four key and value pairs.
        fabFour.put("John", "John Lennon");
        fabFour.put("Paul", "Paul McCartney");
        fabFour.put("George", "George Harrison");
        fabFour.put("Ringo", "Ringo Star");

        // Use a key to retrieve a value.
        String fullName = (String) fabFour.get("Ringo");

        // Prints "Ringo Star"
        System.out.println(fullName);
    }
}
```

#### s

```java
public class TestHashMap {

    public static void main(String[] argv) {
        // Create a new hashmap.
        HashMap<String,String> fabFour = new HashMap<String,String>();

        // Insert four key and value pairs.
        fabFour.put("John", "John Lennon");
        fabFour.put("Paul", "Paul McCartney");
        fabFour.put("George", "George Harrison");
        fabFour.put("Ringo", "Ringo Star");

        // Use a key to retrieve a value.
        String fullName = fabFour.get("Ringo");

        // Prints "Ringo Star"
        System.out.println(fullName);
    }
}
```

### q

Provide a 1-to-2 paragraph explanation for why we need Generics. You can use the sample code (above and below) to over this explanation.

#### s

A lot of what we do as computer scientists, is writing and using abstractions of
different things. Methods and functions provide ways for us to perform some
operation on some data of a specific type, such as integers, over and over
again, such as adding two numbers. Data structures such as classes provide ways
for us to give data some shape and functionality, if it can't just be
represented by a number (as most things can't). What if we want to operate on
some data, but we don't care about the specific type of the data, such as
putting it in a list. From here we see the point of generics, a way to operate
on any "generic" data, or data with any type. Another way to think about
generics in a more practical sense is that they are a way to store and work with data types,
instead of actual data. An example may be comparing different types of movies, instead of different movies.


### q

What is "Erasure" with java generics? 

For the code below, what does the code "erase" to? 

```java
 public static void main(final String args[]) {
        Shelf<String> favorite_words = shelfBuilder();
        favorite_words.addItem("Zoetrope");
        favorite_words.addItem("Succinct");
        //...        
        String s = favorite_words.getItem(1);
        System.out.println(s);
    }
```

#### s
The Java runtime actually doesn't know anything about generics, when you compile
a Java program with generics in them, at compilation time all "generic" types
are replaced with what they should be. Essentially, when we use a generic, at
compilation time the compiler essentially changes the `Shelf` class to only use the `Object` type, so wherever we see a `T` it's replaced with `Object`. From there, every time we do something that should return as a specific type `T`, we replace with a cast to that specific type. 

We see this program then erases to:

```java
 public static void main(final String args[]) {
        Shelf favorite_words = shelfBuilder();
        favorite_words.addItem((Object)"Zoetrope");
        favorite_words.addItem((Object)"Succinct");
        //...        
        String s = (String)favorite_words.getItem(1);
        System.out.println(s);
    }
```

We see this code won't work as the array doesn't have a size.
### q

Finish the `main` method in the `TestShelf` class above.

Expected output:
```
Shakespeare Characters: Hamlet Othello Cordelia Juliet
Famous Integers: 13 23 42 1729
```

```java
import java.util.ArrayList;
import java.util.List;

public class Shelf<T> {
    private List<T> shelfItems;

    private String shelfName;

    public Shelf(String shelfName) {
        this.shelfName = shelfName;
        shelfItems = new ArrayList<T>();
    }

    public int addItem(T item) {
        shelfItems.add(item);
        return shelfItems.size();
    }

    public void printShelf() {
        System.out.print(shelfName + ": ");
        for(T item: shelfItems) {
            System.out.print(item.toString() + " ");
        }
        System.out.println();
    }
}
```

```java
public class TestShelf {
    public static void main(final String args[]) {

        // TODO: Create a shelf to store Shakespeare character names:
        //       Hamlet, Othello, Cordelia, and Juliet
        // TODO: Then print the shelf.


        // TODO: Create a shelf to store famous integers:
        //       13, 23, 42, 1729,
        // TODO: Then print the shelf.


    }
}
```


#### s
```java
    public static void main(String[] args) {
        Shelf<String> shakespeare = new Shelf<>("Shakespeare Characters");
        shakespeare.addItem("Hamlet");
        shakespeare.addItem("Othello");
        shakespeare.addItem("Cordelia");
        shakespeare.addItem("Juliet");
        Shelf<Integer> integers = new Shelf<>("Famous Integers");
        integers.addItem(13);
        integers.addItem(23);
        integers.addItem(42);
        integers.addItem(1729);
        shakespeare.printShelf();
        integers.printShelf();
    }
```

### q 

Consider the following code snippets for a `LinkedList` you may implement and a main method:

```java
public class LinkedList {
   private class Node {
      int data;
      Node next;
   }
   Node head;

   void add(int data);
   int get(int idx);
   //...   
```

```java
public class TestingLinkedList {
  public class static main(String args[]) {
     LinkedList ll = new LinkedList();
     
     for(int i = 0; i < 100000; i++){
         ll.add(i * 3);
     }
     
     for(int i = 0; i < 100000; i++){
         System.out.println("" + ll.get(i)); //<-- MARK
     }
  }
}

```

Explain why the line with `MARK` is extremely inefficient? Use Big-O to explain.

#### s
A linked list has a search time of `O(n)`, since you must iterate through the
whole list to know if something is in it or not. Therefore, the for loop that
contains the marked line will have a cost of `O(n)` every single call, and it
will be called once for each item. This means the total runtime cost will be
`O(n^2)`, which is not good :(.

### q

Continuing with the example above, explain why expanding `LinkedList` to implement `Iterable` solves the inefficiency problem you described above. 

#### s

`Iterable` will keep track of where we are in the linked list, essentially holding a pointer to the last item we were at. This means everytime we get the next item, it'll just return `next` of the current location, and move the pointer forward to the next item. This results in a loop that is only `O(n)`, as we only see every item at most once.

### q

Finish the `main` method below to use the fibonacci iterator to print out the sequence: `1 2 3 5 8 13 21 ...`

```java
import java.util.Iterator;

class Fibonacci implements Iterable<Integer> {
    public static void main(final String args[]) {
        Fibonacci fibonacci = new Fibonacci(21);

        // TODO:  Use the fibonacci iterator to print out the sequence: 1 2 3 5 8 13 21
    }

    private int max;

    public Fibonacci(int max) {
        this.max = max;
    }

    public Iterator<Integer> iterator() {
        return new FibonacciIterator();
    }

    private class FibonacciIterator implements Iterator<Integer> {
        int current = 1;
        int previous = 0;

        @Override
        public boolean hasNext() {
            return current + previous <= max;
        }

        @Override
        public Integer next() {
            int tmp = current;
            current += previous;
            previous = tmp;
            return current;
        }
    }
}
```


#### s
Iterators are so cool!

```java
  public static void main(final String args[]) {
      Fibonacci fibonacci = new Fibonacci(21);
      for(int i : fibonacci){
          System.out.print(i+" ");
      }
  }
```

### q

Explain why the `Comparable` interface is an interface rather than class?

#### s

The only thing we want for something to be comparable is a function that tells us if one of it is greater than the other. That means we just want the class to define one function and thats it. We could do this using an abstract class, but we wouldn't use any of the other functionality an abstract class provides, and then the class couldn't extend any other class either, so we'd be using something with more power than we need, and limiting our options in the future. So instead we just use an interface.

### q

Add the `compareTo` method in the `Car` class above. So that the main method will print out:

```
Name: Lamborghini Top Speed: 225
Name: Porsche Top Speed: 202
Name: Mustang Top Speed: 144
Name: Jeep Top Speed: 110
```


```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Car implements Comparable<Car> {
    public static void main(String[] args) {
        List<Car> carsList = new ArrayList<>();
        carsList.add(new Car("Porsche", 202));
        carsList.add(new Car("Jeep", 110));
        carsList.add(new Car("Mustang", 144));
        carsList.add(new Car("Lamborghini", 225));

        Collections.sort(carsList);
        for(Car car : carsList) {
            System.out.println("Name: " + car.getName() + " Top Speed: " + car.getTopSpeed());
        }
    }
    private String name;
    private Integer topSpeed;

    public Car(String name, Integer topSpeed) {
        this.name = name;
        this.topSpeed = topSpeed;
    }

    public String getName() {
        return name;
    }

    public Integer getTopSpeed() {
        return topSpeed;
    }

    // TODO: Complete the Car class by adding the compareTo method
    //       needed to correctly implement Comparable<Car>.

}

```


#### s
```java
	public int compareTo(Car other) {
		return this.topSpeed - other.topSpeed;
	}
```

# Grading rubric and submission

Use git, as discussed in Lab 0, to submit your work in a repo called `gitusername-worksheetJ3`. You will be graded on the following:

|Item | Points |
|the name of the repo for this lab matches the pattern  `gitusername-worksheetJ3` | 10 |
|the grader has been added as a collaborator to the repo with an invite timestamp during the lecture| 10 |
|the repo has been made private | 10 |
|the name of the answers file in your repo is  `worksheet-J3.md` | 10 |
|worksheet questions have been completed | 60 |
|TOTAL | 100 |