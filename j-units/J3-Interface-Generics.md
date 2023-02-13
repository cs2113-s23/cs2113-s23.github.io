---
layout: toc
permalink: /j/3
---

<style>
.uml-size {
  text-align: center;
  width: 350px;
}
</style>

*View all the videos from this unit a [single playlist on youtube](https://youtube.com/playlist?list=PLnVRBITSZMSMjoXDXUFkq6HX0i6mYNMGR)*

# Motivating Interfaces

## The Multiple Inheritance Problem

Let's return the example of `GWPerson`, `GWFaculty`, and `GWStudent` example from the prior lesson. As a refresher, we had the following class hierarchy:

<img src="/images/uml-person-student-faculty.png" alt="UML for GWPerson, GWFaculty, and GWStudent" width="250" 
style="display: block;
margin-left: auto;
margin-right: auto;"/>


Now let's suppose we want to add another class to this model, `GWStaff`. Simple enough, we can add the following to our model, and at this point, we should be able to imagine what features a `GWStaff` member may have. 



<img src="/images/uml-person-student-faculty-staff.png" 
alt="UML for GWPerson, GWFaculty, GWStudent and GWStaff"
width="270" 
style="display: block;
margin-left: auto;
margin-right: auto;"/>


Ok ... now, what if we want to add a class for a student that is *also* on staff, which is actually quite common due to work study programs and tuition remission programs. Perhaps this class could be a `GWStudentStaff` and we place it in the diagram like below.


<img src="/images/uml-person-student-faculty-staff-studentstaff.png" 
alt="UML for GWPerson, GWFaculty, GWStudent, GWStaff and GWStudentStaff"
width="290" 
style="display: block;
margin-left: auto;
margin-right: auto;"/>

There is nothing necessarily wrong with this model, where `GWStudentStaff` inherits from *both* `GWStudent` and `GWStaff` and in a number of OOP languages, like Python, this is totally normal. 

**In Java, though, you <span style="text-decoration:underline">cannot</span> inherit from multiple classes.** Java uses a single inheritance model. That means, you can only list one class following an `extends` statement.

So how do you represent the models like above?

## GWStaff Interface

Within our toy model, we can define the features needed for staff as having two primary functions:
* `getSalary()` and `setSalary()` : how much the staff member get's paid
* `getManager()` and `setManager()` : who the staff person reports to

In a world of multiple inheritance, we could imagine defining a class (or abstract class) where these features are defined and would be overwritten in sub-classes. But since there is no multiple inheritance in Java, we instead can define a `GWStaff` as an **interface**.

An **interface** in java is a specification of methods that must be defined when a class **implements** the interface. It's essentially, a way to guarantee certain functionality in the class that **realizes** the implementation. 

For example, we'd defined the interface like, below. 

```java
public interface GWStaff {
   public double getSalary();
   public void setSalary(double salary);
   public GWPerson getManager();
   public void setSalary(GWPerson manager);
}
```
Note this is the entirety of the code needed to define this interface. The implementation of each of these methods must occur in the class realizing this implementation. 

Moreover, thinking of this interface, as faculty are also paid and have a manager, namely the department chair, it would make sense for `GWFaculty` to also implement this interface. We then get the following class declarations in this model.

```java
public abstract class GWPerson{/*...*/}
public interface GWStaff{/*...*/}
public class GWStudent extends GWPerson{/*...*/}
public class GWFaculty extends GWPerson implements GWStaff{/*...*/}
public class GWStudentStaff extends GWStudent implements GWStaff{/*...*/}
```

Which we can visualize using the following UML diagram.

<img src="/images/uml-staff-interface.png"
alt="UML for GWStaff interface"
width="400" 
style="display: block;
margin-left: auto;
margin-right: auto;"/>

Here the solid line with solid arrowheads indicate classes that inerit from another, like `GWStudent` inherits from `GWPerson`. A dotted line with a solid arrowhead indicates a class that *realizes* an interface, like `GWFaculty` realizing `GWStaff`. Finally, an interface is annotaed with `<<>>` markers in its name. 

<font color="red"><b>PAUSE: Let's do exercises 1-3 on the J3 worksheet now for the next ten minutes.<br></b></font>

## Using interfaces

Like abstract classes, we can use the interface as the type name, and due to polymorphism, we can have different implementations of that realize that interface. For example, consider a payroll program that determines the total compensation for a collection of staff members. 


```java
//array of class that realize the GWStaff interface
GWStaff staff[] = {new GWStudentStaff(/*...*/),
                   new GWFaculty(/*...*/),
                   new GWFaculty(/*...*/),
                   new GWStudentStaff(/*...*/)};

double totalCompensation = 0.0;
for(int i = 0; i < staff.length; i++){
    totalCompensation += staff[i].getSalary(); //interface ensures that this
                                               //method is implemented
}
```

In many ways, just like inheritance, realizing an interface is an "is-a" relationship. So we can refer to all classes using the type annotation of the interface. In this case, that would be `GWStaff`. 

# Realizing multiple interfaces

Importantly, a class can implement multiple interfaces. Note that an interface, unlike a class inheritance, does not reuse any code defined in methods nor data members. An interface is simply a contract that a realizing class implements the following features. So it is natural that a class can realize different interfaces.

An example of this, which we take a bit more time to explore below, is in Java collections, which is Java's built in data structure library. For example, recall from your data structures class that we have a set interface for lists (just for int's in the example below):

```java
public interface IntList {
    public int get(int id);
    public void set(int idx,int val);
    public void insert(int idx, int val);
    public void addToHead(int val);
    public void addToTail(int val);
    public int rmFromHead();
    public int rmFromTail();
    public boolean empty();
    public int size();

}
```
 
A realization of this interface would be a `IntLinkedList` or an `IntArrayList` that implements all these methods. But, these same data structures could also realize other APIs, such as `IntQueue` or `IntStack`. 

```java
public interface IntQueue {
    public void enqueue(int val);
    public int dequeue();
}

public interface IntStack {
    public void push(int val);
    public int pop();
}
```

So when we declare our `IntLinkedList` (or `IntArrayList`) we describe that it realizes *all* interfaces!

```java
public class IntLinkedList implements IntList, IntQueue, IntStack {
    // All the LinkedList implementation stuff
    //... plus all List methods realized 
 
    //IntQueue interface realized
    public void enqueue(int val) {
        this.addToHead(val);
    }
    public int dequeue(int val) {
        this.rmFromTail(val);
    }
   
    //IntStack interface relized
    public void push(int val) {
        this.addToHead(val);
    }
    public int pop(int val) {
        this.rmFromHead(val);
    }
   
}
```

## Declaring a type for certain interface

Continuing with the example above of `IntList`, `IntStack`, and `IntQueue`, which should you use in code? For example, what's the difference between each of the lines of code below?

```java
IntList l = new IntLinkedList();
IntStack s = new IntLinkedList();
IntQueue q = new IntLinkedList();
```

Technically, `l` and `s` and `q` are all realized by the same underlying class, namely a `IntLinkedList`, so why call them by different types? The answer is twofold.

First, we use the type annotations of being a list (or a stack, or a queue) to indicate to us (the programmer) the kinds of functionality we are drawing on for our program. Sure, we all know that any reasonable linked list implementation can be used like a stack, but it's much easier to read the code when we say so explicitly. 

Second, when we declare a variable to be a type of the interface, we are restricted the scope of the methods we can call using that type. For example, calling `s.addToHead()` would cause a compile error -- `IntStacks` do not have a method `addToHead()`. 

But we know that `s` is really a `IntLinkedList` and does have such a method. So in a way, using a specific interface provides a form of **encapsulation** of functionality (rather than data) whereby we restrict the methods associated with the realized class such that we get a pure representation of that interface. For example, if we want to use a stack, it doesn't make sense that we can use `s.insert()` as this violates the properties of a stack. 

## Yes, you can cast, but you shouldn't.

Casting in java is much like pointer casting in C --- it doesn't change the underlying data, but only how we interpret that data. This make sense because an object in Java is technically a reference type, like a pointer. 

So yes, continuing with the example above, we can cast `s` or `q` or `l` to a `IntLinkedList` if we want and have access to the full functionality. Like the code below:

```java
 ((IntLinkedList) s).insert(5, 10);
```

But this should feel awkward and wrong. If we wanted `s` to be a full feature linked list, than it is on the programmer to declare it as such. Casting is a shortcut around the elegance of OOP, and it can also cause unexpected runtime errors. 

For example, consider a method that expects `IntStack` as input, like below.

```java
public void reverseStack(IntStack s) {
  // which implementation of an IntStack is s?
  IntLinkedList l =  (IntLinkedList) s;  //this could cause a runtime error
  //...
}
```
The programmer is making a strong assumption about the nature of `s`, specifically it the `IntStack` is realized via an `IntLinkedList` and so it can be directly cast. This may not be the case. `IntStack` could be realized by an `IntArrayList` or some other class that can realize an `IntStack` that is yet to be written. 


# Java Generics and Interface

## Using `Object` to generalize

Let's consider the example of `IntList`, `IntStack` and `IntQueue` interfaces we defined above. One aspect of these interfaces that should appear somewhat abnormal is the `Int` aspect --- they are only defined for lists of ints, a stack of ints, and queue of ints. It would definitely be better if we could define some universe `List`, `Stack` and `Queue` because if we wanted to define an interface for a `String` (as opposed to an `int`), we'd need to totally rewrite the interface for every possible `Object`.

But... couldn't we take advantage of the fact that all classes extend `Object`? Yes, and we could define a generic interface for `List` that uses the `Object` type. Such as,


```java
public interface List {
    public Object get(int idx);
    public void set(int idx, Object val);
    public void insert(int idx, Object val);
    public void addToHead(Object val);
    public void addToTail(Object val);
    public Object rmFromHead();
    public Object rmFromTail();
    public boolean empty();
    public int size();
}
```

Now we can define a `LinkedList` and an `ArrayList` that realizes this interface at the `Object` type. (Note the same would be true for `Queue` and `Stack` interfaces.)

```java
public class LinkedList implements List{/*...*/};
public class ArrayList implements List{/*...*/};
```

But using these generic `Object` version limits us. While we can store any kind of object in our `LinkedList`, this is both a blessing and a curse. There is nothing stopping us from accidentally storing objects of different runtime type in this list;

```java

l.addToHead("inserting a string");
l.addToHead(new Integer(10)); //or an Integer object
l.addToHead(new Double(3.14)); //or an Double object
//... and many others
```

While there may be good reasons to mix class types within the list, there are also (often better) reasons to restrict the list to a single type, so you know what class to expect on removal. For example, using the list above, if you were to do a `rmFromTail()`, how would you properly determine which type to cast back to?! 

## Java Generics

The issue of implementing generic interfaces that can function for any class type is a known problem and is solved differently in different languages. In Java, note that the example above with generic `Object` interfaces is technically correct and if the programmer followed a single type everything would work correctly. In fact, prior to Java 8, this was the recommended way to write generic interfaces. The issue with this approach is that if you want to restrict the class type, there is no way to do so. We need to add an additional annotation to generic interfaces to indicate what class type to expect. Then the compiler can check for that type in your code, ensuring consistency and identifying errors. 

This is called java **generics**, and the class type annotation is noted using `<>`. For example, we can define our generic `List` interface with a generic type `T` that is stand in for `Object`, as if it is a variable for a more specific type, like `String` that will be defined later. In fact, java refers to this as the **type argument**. 

```java
public interface List<T> {
    public T get(int idx);
    public void set(int idx, T val);
    public void insert(int idx, T val);
    public void addToHead(T val);
    public void addToTail(T val);
    public T rmFromHead();
    public T rmFromTail();
    public boolean empty();
    public int size();
}
```

Then when we realize this interface, we continue annotating the generic variable, as if it was passed as an argument in a function call.

```java
public class LinkedList<T> implements List<T> {/*...*/}
```

Finally, when we instantiate a `List`, we pass a type argument within `<>` to indicate at which class type the list should be invoked.

```java 
List<String> l = new LinkedList<String>();
l.addToHead("l is expecting String types to be added to it");
```

And if we tried to insert a non `String` to `l`, we would get a compiler error:

```java
l.addToHead(Integer(10)); //<-- this will cause a compiler error
```

<font color="red"><b>PAUSE: Let's do exercise 4 on the J3 worksheet now.<br></b></font>

## Generics as an Erasure to Object

Generics in Java are not runtime mechanisms. They are **compile time** type checks; that is, they are annotations on the class type that can be validated via the type checker to ensure consistency. When the type argument mismatches, you get a compile error, not a runtime error.

Under the hood, after compilation, all the generic type annotation is **erased** and replaced with `Object` with appropriate casting. For example,

```java
List<String> l = new LinkedList<String>;
//...
String s = l.rmFromHead();
```

Becomes the following after erasure


```java
List l = new LinkedList;//no generic type argument
                        //using Object instead
//...
String s = (String) l.rmFromHead(); //adds cast for type argument
```

That's right. You are essentially just using the same generic `Object` implementations as before when your code actually executes, but you gain an important consistency check to ensure that you don't mix types. It also makes your code more readable, as you can track which types are used where, and also define multiple generic types. For example, for a generic `Map<K,V>` that maps some class `K` to some class `V`.

## Restrictions on Generics

Because of erasure --- that is all generic type arguments are erased to `Object` --- there are a number of restrictions when using generics.

### Cannot use primitive types

Erasure requires all generic types to be objects, not primitive types. So you cannot use `int` as a type argument, instead you need to use the `Object` representation of basic types. Such as `Integer`, `Boolean`, `Double`, and etc.

```java
List<Integer> l = new LinkedList<Integer>();
```

### Cannot create instances of generic types

Consider a generically defined method with a type parameter `T` 

```java
public class FooBarBaz<T> {
   public  T getT(){
       T t = new T(); //<-- compile error
       return t; 
   }
}
```

Erasure says that `T` is actually replaced with `Object`, and if allowed to run it would simply produce a instance of `Object`, and not of the type T. Worse, you'll get an invalid cast like below (causing the compile error)

```java
Object t = (T) new Object();
```

Since `T` will most likely be some more specific type (that is a class that extends `Object`), this cast is invalid as a generic `Object` will not have the functionality of `T`. 

There is a way to still do this using Java reflections --- but that's a discussion for another day.

### Cannot create arrays of generic types

For the same reason as above, you can't directly create arrays of generic types. Consider the following code:

```java
public class FooBarBaz<T> {
   T array[];
   public FooBarBaz{
      array = new T[];
   }
}
```

After erasure, we again have an odd cast that can cause a type error:


```java
public class FooBarBaz {
   Object array[];
   public FooBarBaz {
      array = (T[]) new Object[];
   }
}
```

Unfortunately, there are not good solutions to this that do not leverage more advanced tools, like Java reflections --- still a lesson for another day. 

While you can do some fancy casting with arrays of `Objects`, best practice is to use Java collections when you want generic array-like functionality. This is discussed latter in this unit. 

<!-- The standard way to do this is to manage the class type directly. For example, we leave the array as a generic `Object` array. But, since *all* classes extend `Object` we can treat this like an array of pointers that can reference any class. -->

<!-- ```java -->
<!-- public class FooBarBaz<T>{ -->
<!--    Object array[]; -->
<!--    public FooBarBaz{ -->
<!--       array = new Object[]; -->
<!--    } -->
<!-- } -->
<!-- ``` -->

<!-- When we want to get a value out of the array, we cast ourselves. -->

<!-- ```java -->
<!-- public T getIdx(int idx){ -->
<!--     //will generate a warning, which we can supress -->
<!--     @SuppressWarnings("unchecked") -->
<!--     return (T) array[i]; //explicitely cast for type checking -->
<!-- } -->
<!-- ``` -->

<!-- But, this will cause a warning at compile time due to a unchecked weak typing. That is, it is impossible to know at run time if the values in `array` are truly of class type `T`. However, if you know you're doing this right, you can suppress this warning with `@SuppressWarnings("unchecked")`. -->

<!-- ## Using a Upper Bounding Type in Generics -->

<!-- One way to avoid some of the restrictions above is to introduce a **bounding type**. For example, suppose we want know that the generic type argument is a subclass of some other class `Foo`, then we can declare the class as so with a `?` as a wildcard for that other class.  -->

<!-- ```java -->
<!-- public class Bar<? extends Foo>{ -->
<!--     Foo array[] -->
<!--     public Bar(){ -->
<!--         array = new Foo[]; -->
<!--     } -->
<!-- } -->
<!-- ``` -->

<!-- Taking advantage of inheritance and polymorphous allows us to use `Foo` as a standin as the super type for whichever class is passed as the type argument.  -->

<!-- Unfortunately, this may not work in all situations, for example, if you want to write a list that can be generic to any type, not just subtypes of one type.  -->

<font color="red"><b>PAUSE: Let's do exercises 5-11 on the J3 worksheet now.<br></b></font>

# Exploring Common Java Interfaces and their Realizations

Now that you have some experience with interface and generics, let's take a moment to explore some common interfaces that you will likely experience.

## Comparable Interface

Perhaps the most common interface is the `Comparable` interface (see the [API](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html)). The `Comparable` interface declares a single method that must be realized:

```java
public interace Comparable<T> {
    int compareTo(T o);
}
```

And like `strcmp()` in C, this function returns a value less than 0 if `this` is less than the other object `o`, greater than 0 if `this` is greater than the other object `o`, and 0 if they are equal. 

As an example, let's consider a simple class `PiggyBank` that tracks how many pennies (1c), nickels (5c), dimes (10c) and quarters (25c) it contains. And we want each `PiggyBank` to be comparable to other `PiggyBank`s not on the total value, but the TOTAL number of coins! 

```java
public class PiggyBank implements Comparable<PiggyBank> {

    private int pennies, nickels, dimes, quarters;
    public PiggBank() { 
        pennies = nickels = dimes = quarters = 0;
    }
    public void addPenny() {
        pennies++;
    }    
    public void addNickel() {
        nickels++;
    }    
    public void addDime() {
        dimes++;
    }    
    public void addQuater() {
        quarters++;
    }    
    public double getDollars() {
        return 0.01 * pennies + 0.05 * nickels + 0.10 * dimes + 0.25 * quarters;
    }
    public int totalCoins() {
        return pennies + nickes + dimes + quaters;
    }
    public int compareTo(PiggyBank other) {
         tt = this.totalCoins();
         ot = other.totalCoins();
         if(tt < ot) 
            return -1;
         else if (tt > ot) 
            return 1;
         else 
            return 0;        
    }    
}
```

Now that the generic type argument for the generic is `PiggyBank`, we don't declare a type argument to use within the class. That's because `PiggyBank`s are comparable to *other* `PiggyBank`s not to any generic type. 

However, it may be the case that we want to compare to any subclass of `PiggyBank`, like a `CatBank` (... I have no idea what that is, but let's just say it exists.) Then since `CatBank` is-a `PiggyBank` it would make sense we could still compare the two, but the generic type annotation says *only* PiggyBanks.

Fortunately, generics allows us to describe an upper bound of the type generically. That is, say that it is comparable to all sub-types of the given type.

```java
public class PiggyBank implements Comparable<? extends PiggyBank>{/*...*/}
```

The `?` is a wildcard for which ever type extends `PiggBank`. The rest of the code remains the same, and we can now do the following comparison without having to cast.

```java
PiggyBank p = new PiggyBank();
CatBank c = new CatBank();
//...

p.compareTo(c);
```

One final note, when we instantiate a `PiggyBank` (or a `CatBank`) we do not pass a type argument. Again, this is because we define a specific type in the class declaration in the generic -- it is a `PiggyBank`, there's nothing more we can annotate here. 

## Iterable and Iterator Interface

Another common interface in Java is the `Iterable` and `Iterator` interface. These two interfaces work hand-in-hand to enable for-each iteration in Java. That is, being able to iterate over a collection of objects using the following syntax:

```java
LinkedList<String> l; 
//...
for(String s : l){ /*...*/ }
```

You can find the reference documentation here
* [Iterator](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Iterator.html)
* [Iterable](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Iterable.html) 

Consider if we were implementing a linked list that can store any generic type, and we wanted the linked list to also be iterable. We'd have the following class definition where `LinkedList` implements `Iterable`. Note that we are using the generic type annotation, as this linked list annotation can store any type `T`.

```java
public class LinkedList<T> implements Iterable<T> {
    private class Node {
        Node next;
        T value;
    }

    private Node head;

    public LinkedList() {
        head = null;
    }
    //... addToHead(), rmFromHead(), etc ... 
}

```

The `Iterable` interface requires one method to implemented, `iterator()` which returns an `Iterator`, which is itself an interface. So to realize the iterator, we have to create a new nested private class that realizes `Iterator` for this LinkedList. 

There are two required methods that must be realized for an `Iterator`

1. `boolean hasNext()` : returns `false` when we've reached the end of iterator, otherwise true
2. `T next()` : returns the value of the next item in the iteration, and increments the counter. 

And we can implement them in a straightforward way by creating a `curNode` to track our progress in the list

```java
private class LinkedListIterator implements Iterator<T> {
    private Node curNode;
    public LinkedListIterator() {
        curNode = head //start iteration at head
    }; 
    public boolean hasNext() {
        return curNode != null; //exit when reach null
    }
    public T next() {
        T data = curNode.data; //get the data
        curNode = curNode.next; //increment the curNode
        return data; //return the data
    }
}
```

The last step is to realize the `Iterable` interface in our `LinkedList` by having it return a `LinkedListIterator`

```java
public Iterator<T> iterator() {
    return new LinkedListIterator();
}
```

With that in place, the following code with a for-each loop is realized 

```java
LinkedList<String> l; 
//...
for(String s : l){ /*...*/ }
```

But under the hood, this gets translated into the following while loop using the interface.

```java

Iterator<String> iter = l.iterator(); //Iterable interface
while(iter.hasNext()) { //Iterator interface
   String s = iter.next(); //Iterator interface
   /*...*/
}
```

<font color="red"><b>PAUSE: Let's do exercises 12-16 on the J3 worksheet now.<br></b></font>

## Java Collections

The last interface we'll discuss here is the Java `Collection` interface, which is actually a family of interfaces that describe any data structure that stores a collection of objects. And of course, all `Collection`s implement `Iterable` :) 

The whole scope of `Collection` can be viewed in the UML diagram below. 

<img src="/images/uml-collections.png" 
alt="UML of Collections Interface"
width="800" 
style="display: block;
margin-left: auto;
margin-right: auto;"/>

A `Collection` takes a single generic type argument, `Collection<E>`, where `E` is the class of the *elements* in the collection. To highlight a few important implementations and which interfaces they realize.

* `LinkedList<E>` : implements a `List`, `Queue` and also a `Deque` (which has the same features as a `Stack`
* `ArrayList<E>` : implements a `List` and used for random access lists, and has subtypes `Vector<E>` and `Stack<T>` (although, you should probably use a `Deque`).
* `HashSet<E>` : implements a `Set`
* `TreeSet<E>` : also implements a `Set` but is navigable in sorted ordered (`SortedSet` and `NavigableSet` interfaces)
* `PriorityQueue<E>`: A queue where items are removed with some priority (usually implemented uesing a Heap)

Finally, while not technically a `Collection`, classes implementing the `Map` interface are also included in "java collections". A `Map` is generic on two type arguments, `Map<K,V>` as this a key-value store with `K` the type of the keys and `V` the type of the value. The full family is shown below in the UML diagram:

<img src="/images/uml-maps.png" 
alt="UML of Map Interface"
width="500" 
style="display: block;
margin-left: auto;
margin-right: auto;"/>

The key classes that you will encounter that realize a `Map` are:

* `HashMap<K,V>` : A hash table implementation of a `Map`
* `LinkedHashMap<K,V>` : A hash table that useses a linked list to enable iteration of the hash table. 
* `TreeMap<K,V>` : A (balanced) tree based implementation of `Map` that has the benefit of also being a `NavigableMap`, so it can be iterated in some order. 

<font color="red"><b>PAUSE: Let's do exercise 17 on the J3 worksheet now.<br></b></font>



