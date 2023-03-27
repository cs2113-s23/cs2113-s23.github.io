---
layout: toc
permalink: j/5
---

*View all the videos from this unit a [single playlist on youtube](https://youtube.com/playlist?list=PLnVRBITSZMSPPFvxKvBaWr9NVEqGh1yCd)*

# Java Threads

## What are "threads"?

In a **single-threaded program**, which is what you've been dealing with up until now (sort-of, except that whole GUI thing, remember?), an executing program consists of a function call stack that grows and shrinks as functions are called and functions return. At the bottom of that function call stack is the call record for `main()`, and when that call returns, the program is over. In a multi-threaded program, there are multiple function call stacks that execute simultaneously. The "main thread" has the `main()` function call record on the bottom of the stack - other executing function call stacks (an executing function-call stack is called a thread) may have different function calls on the bottom-most function call record. The program ends when every thread has exited, i.e. when the bottom-most function-call record of each call stack has returned.

However, there are many reasons to have **multi-threaded execution**. For example, your web browser might have a separate thread running for each individual tab. The web server (host) your browser connects to might also have a pool of threads in order to serve pages. Your text editor (like MSWord) also probably has several concurrent threads running at the same time; one of them might be constantly spell-checking your writing in real-time. These are just some examples; note that they all share some of the same data that the threads all act upon.

Ignoring for the moment the very important issues surrounding how the different threads (i.e. the different executing function call stacks) communicate with, and otherwise interact with one another, the fundamental problems that have to be addressed when designing a threading API are:

1.    How do you start a new thread? I.e. how do you call a function so that instead of the call resulting in a new function call record being pushed on top of the stack record of the caller in the caller's function call stack, a new stack is created and the function call becomes the bottom-most record on the new stack?
2.    How do you get data into the newly created function call stack when you start it?
3.    How do you get data out of the new function call stack once it's finished executing?


## Java threads: the very basic basics

You might be surprised to find that Java's answer to this involves ... classes, inheritance and polymorphism. Are you surprised? I hope not! The Java API has a class called `Thread` that includes two important methods: 

```java
public void run();
public void start();
```

Creating a new thread (i.e. a new function call stack) works like this: 


1.    an instance of `Thread` is created
2.    the `start()` method is called on the instance
3.    the `run()` method is called by `start()`, and that call becomes the bottom-most record on a new function call stack


So, if you have something specific you want a new thread to do, you derive a new class from `Thread` (extend `Thread`) and override the `run()` method to do whatever it is you want done. 
 

<div class="side-by-side">
<div class="side-by-side-a">
```java
import java.util.*;

public class Ex0 {
  public static void main(String[] args) {
    Thread t = new Foo();

    t.start();
    int x = 5 * 5;
    System.out.println(x);
  }
}
```
</div>
<div class="side-by-side-b">
```java
public class Foo extends Thread {
  public void run() {
    int x = 7 * 7;
    System.out.println(x);
  }
}
```
</div>
</div>

Hopefully the following picture gives you some idea of how this works. 

<img src="/images/thread-visual.png" alt="Memory diagram of threads"
style="display: block;
margin-left: auto;
margin-right: auto;"/>


 Some important points to take away are:

*    It's wrong to say "such and such object is in thread 1". Objects live in the heap, outside of any of the call-stacks!
*    Local varaiables, be they primitive types or references, do belong to a particular thread, since they live in function call records.
*    It's wrong to say "such and such function is in thread 1". A given function *call* belongs to a thread, but there could be many threads with active function calls for the same function.



## Getting data in and out: it's all in the Object

If you look at the above picture, what should strike you the `Thread` object - more literally the `Foo` object `f` in this example - is pointed to by references from both threads (from both call stacks). That means that it can be a repository of data that we want to share between threads. We can add fields to `Foo f` for data we want to communicate between the two threads. Below is an example of a fun little program that uses the `Thread` object to communicate data from the main thread to the new thread. I want you to pay attention to how both threads (both call stacks) make calls to the same methods: `Ex1.printSlow()`. 

It makes used of the thread's **`sleep()` method to put a thread to sleep for a specified amount of
milliseconds**. This method must be called inside a try-catch block for reasons we'll discuss in a bit.


<div class="side-by-side">
<div class="side-by-side-a">
```java
import java.util.*;

public class Ex1 {
  public static Random rand = new Random();

  public static void printSlow(String s, String t) {
    for (int i = 0; i < s.length(); i++) {
      try {
        Thread.sleep(rand.nextInt(1000));
      } catch (Exception e) {}
      System.out.println(t + s.charAt(i));
    }
  }

  public static void main(String[] args) {
    String s = "Mississippi";
    Thread t = new Foo(s, "   ");

    t.start();
    printSlow(s, " ");
  }
}
```
</div>
<div class="side-by-side-b">
```java
public class Foo extends Thread {
  private String msg, tab;

  public Foo(String s, String t) {
    this.msg = s;
    this.tab = t;
  }

  public void run() {
    Ex1.printSlow(this.msg, this.tab);
  }
}
```
</div>
</div>

The output will look something like this:
```
(base) > java Ex1
 M
   M
   i
   s
 i
   s
   i
   s
 s
 s
 i
   s
   i
 s
   p
   p
 s
 i
   i
 p
 p
 i
```

The outer layer of printing comes from `main`, while the inner layer of printing comes from the `run` method of the thread after its `start` was called.

<img src="/images/thread-print-slow.jpg" alt="Memory diagram of threads"
width="50%"
style="display: block;
margin-left: auto;
margin-right: auto;"/>

Not pictured here is the `Random` object. 

<br>
<font color="red"><b>PAUSE: Let's do problems 1-3 on the worksheet (15 minutes)</b></font>
<br>

# Concurrency

## The `join()` and `join(int)` methods

Java provides another method called `join` that forces the thread it is called upon to wait (to finish) before allowing the other thread to continue. <i>What?</i>

For example, if you have the following code inside a `main` method as above:
````
t.start();
...
try {
  t.join(5000);
} catch (InterruptedException e) {
  System.out.println("Interrupted while joining a thread...");
}
````

this code will force the `main` thread (where we started `t`) to wait for five seconds, potentially allowing the `run()` method of `t` to finish. You can also call `join()` without any argument to force the wait until `t` finishes completely.

Note that the `join()` must be called inside a try-catch block; this is known as a checked exception because the method was declared (in the library) as possibly raising an exception; the developer is forced to write code to handle this case (even if, as on the worksheet, the exception never occurs). If something interrupts the thread, catching the exception allows you to handle it elegantly. 

## The `isAlive()` method

The `isAlive()` method can be called on a thread to check if it is still running. This can be useful when you want to know if a thread is finished, but don't want to block the execution as above.

<br>
<font color="red"><b>PAUSE: Let's do problems 4 - 9 on the worksheet (20 minutes)</b></font>
<br>

## Race Conditions

In general, parallel programming is fraught with peril! If you're not careful, you can end up with bugs whose occurrence or precise behavior depends on the exact order in which the instructions of each thread are executed relative to one another. This kind of bug is called a **race condition**.

To see a nice example of how this might happen, let's turn back to our old friend the `Queue` class. The following program has a single `Queue`, `Q`, that is shared by two threads. Both threads call enqueue 1,000 times, and after the second thread dies, the main thread then counts that there were indeed 1,000 items enqueued by each thread. 

<div class="side-by-side">
<div class="side-by-side-a">
```java
public class Ex0 {
  public static class QThread extends Thread {
    Queue Q;
    public QThread(Queue Q) {
      this.Q = Q;
    }

    public void run() {
      for(int i = 0; i < 1000; i++)
        Q.enqueue("b" + i);
    }
  }

  public static void main(String[] args) {
    Queue  Q = new Queue();
    Thread t = new QThread(Q);

    t.start();

    while (Q.empty()) {}

    for(int i = 0; i < 1000; i++) {
      Q.enqueue("a" + i);
    }

    while(t.isAlive()) {}
    int a = 0, b = 0;

    while(!Q.empty()) {
      if(Q.dequeue().charAt(0) == 'a')
        a++;
      else
        b++;
    }

    System.out.println("a=" + a + " b=" + b);
  }
}
```
</div>
<div class="side-by-side-b">
```java
public class Queue {
  public void enqueue(String s) {
    if(head == null) {
      head = tail = new Node(s, null);
    } else {
      tail.next = new Node(s, null);
      tail = tail.next;
    }
  }

  public String dequeue() {
    Node t = head;

    head = head.next;

    if(head == null) {
      tail = null;
    }
    return t.data;
  }

  public boolean empty() {
    return head == null;
  }

  private class Node {
    public String data;
    public Node next;
    public Node(String d, Node n) {
      data = d;
      next = n;
    }
  }
  private Node head = null, tail = null;
}
```
</div>
</div>

## So what happens when I run this?

One of two things may occur ...

### You may see the program crash

When I run this ... it crashes. Why? Well there are a couple of possible race conditions here. The one that just happened to me went something like this: Things were OK for a while, each thread enqueuing values, but then we had a bad interleaving of execution of instructions. It probably happened like is shown in the following table: 


<div class="side-by-side">
<div class="side-by-side-a">
Thread 2 (run)
```java
tail.next = new Node(s,null);

tail = tail.next;
```
</div>
<div class="side-by-side-b">
Thread 1 (main)

```java
tail.next = new Node(s,null);

tail = tail.next;
//     ---------
//   this causes the problem
//   because Thread 2 has just
//   set tail to point to a
//   Node whose "next" is null!
```
</div>
</div>

### You mays see the program stuck in an infinite loop

When I run this it gets stuck in an infinite loop. Why? Well, the lines 

```java
Thread t = new QThread(Q);
t.start();
while(Q.empty()) {} 
```

start up a new thread and then wait until the new thread actually gets something enqueued in `Q` to continue. The problem is that the Java compiler / VM perform certain optimizations. In this case, one or the other of them may decide that recomputing `Q.empty()` is a pointless waste of time, since the call `Q.empty()` doesn't actually change any fields, and nothing else happens in the loop, and thus either `Q.empty()` gives true every time or false every time - why recompute it? And so it is transformed to 

```java
Thread t = new QThread(Q);
t.start();
boolean tmp = Q.empty();
while(tmp) {}
```

... and we get our infinite loop. The problem is that the compiler/JVM is not taking into account the possibility that there may be other threads changing `Q.head` and `Q.tail`, and thus the result of `Q.empty()` may indeed change. 


We can add the modifier `volatile` to `Q.head` and `Q.tail`

```java
private volatile Node head = null, tail = null;
```

**`volatile` tells the compiler/JVM that, as it does its optimizing, it cannot assume that other threads won't be modifying these fields**. This fixes the infinite loop. 


## Synchronization in general, and the `synchronized` keyword in particular

While for the most part we want threads to process independently, in parallel, what we just saw is that there are times when we need to coordinate, or synchronize the execution of separate threads in order to avoid race conditions. To the previous example, we need to ensure that the two threads don't simultaneously execute enqueue's - they need to take turns. Our problem was that two threads could interleave their executions inside of the `enqueue` method (on the same linked list object): we want a way to make sure that if one thread is inside the `enqueue` method, the other is now allowed to enter it (which would solve our race condition above).

Perhaps the simplest of the many mechanisms Java provides for synchronizing the execution of threads are synchronized methods. **Declaring one or more methods in a class with the `synchronized` modifier causes the Java virtual machine to ensure that no two threads execute overlapping calls of synchronized methods on the same object**. It does this by temporarily delaying threads as needed. To be clear, suppose we have a class Foo: 

```java
public class Foo {
  ...
  public sychronized void bar() {
    ...
  }
  ...
}
```

If `var1` and `var2 `are references to distinct objects of type `Foo` then `Thread x` could call `var1.bar()` and `Thread y` could call `var2.bar()`, and the two methods could execute simultaneously. However, if both threads called `var1.bar()` at the same time **(note: we calling `bar()` on the same object this time!)**, then one `Thread` would execute `bar()` while the other `Thread` was delayed, and only after the first thread had completed its call to `bar()` could the second thread start executing its call.

So, to fix our bug, all we need to do is declare `enqueue` (and `dequeue`) with the synchronized modifier. 

```java
public class Queue {
  public synchronized void enqueue(String s) {
    if(head == null) {
      head = tail = new Node(s, null);
    } else {
      tail.next = new Node(s, null);
      tail      = tail.next;
    }
  }

  public synchronized String dequeue() {
    Node t = head;

    head = head.next;

    if(head == null) {
      tail = null;
    }
    return t.data;
  }

  public boolean empty() {
    return head == null;
  }

  private class Node {
    public String data;
    public Node   next;
    public Node(String d, Node n) {
      data = d;
      next = n;
    }
  }
  private volatile Node head = null, tail = null;
}
```

<br>
<font color="red"><b>PAUSE: Let's do problems 10 - 13 on the worksheet (10 minutes)</b></font>
<br>


## A Race Condition Example: Counting

Here is another example, slightly contrived, but that illustrates further how `synchronized` works. Imagine you need to process a lot of data files, and you realize that you could parallelized the processing by assigning a new thread to each data file. Instead of one main thread looping over all the files, launch all the threads at the same time.

The program below takes file paths as arguments, and creates a `CountThread` for each one. The threads share a single `SafeCounter` object which simply counts how many lines are in the files. Each thread increments the counter. This is a classic race condition. There is a single value `int` to update as the counter that is shared across all threads. As the threads grab the value, in between reading its value and then sending the `++` updated value, another thread might read the value as well and miss the new update.

**Sharing memory between threads is dangerous, but often necessary.** This is why we have the `synchronized` keyword; although it is used with methods, it forces the method to be an 'atomic transaction' -- that is, you can't break it down into smaller statements. Either the whole method runs, or it doesn't (because it is waiting for another call to the same method to finish).

Try running this on a couple large files, and remove the `synchronized` keyword from the methods. You'll see different counts output from different runs. With the `synchronized` keyword, it will always be the same (correct) value. 


<div class="side-by-side">
<div class="side-by-side-a">
```java
public class SafeCounter {
    private int value = 0;
    public synchronized void increment() {
        value++;
    }
    public synchronized int getValue() {
        return value;
    }
}
```
```java
public class CountThread extends Thread {
  String filename;
  SafeCounter counter;

  public CountThread(String filename, SafeCounter counter) {
    this.filename = filename;
    this.counter = counter;
  }

  public void run() {
    try {
      Scanner scan = new Scanner(new File(filename));
      while(scan.hasNextLine()) {
        scan.nextLine();
        counter.increment();
      }
      scan.close();
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}
```
</div>
<div class="side-by-side-b">
```java
/**
 * Counts the lines in the given files. Sums all counts up.
 * One thread per given filename command-line argument.
 */
public class CountFiles {

  public static void main(String[] args) {
    SafeCounter counter = new SafeCounter();

    CountThread[] threads = new CountThread[args.length];
    for(int xx = 0; xx < args.length; xx++)
      threads[xx] = new CountThread(args[xx], counter);

    for(int xx = 0; xx < args.length; xx++)
      threads[xx].start();

    try {
      for(int xx = 0; xx < args.length; xx++)
        threads[xx].join();
    } catch(Exception ex) {
      ex.printStackTrace();
    }
    
    System.out.println("Final count = " + counter.getValue());
  }
}
```
</div>
</div>

<br>
<font color="red"><b>PAUSE: Let's do problem 14 on the worksheet (5 minutes)</b></font>
<br>

## Synchronization with the Event Dispatch Thread


Whenever we have two or more threads reading-from/writing-to the same object, we have to worry about the possibility of race conditions. But isn't that, in fact, what we've been doing with GUIs? The Event Dispatch Thread manipulates all of the `JFrame`, `JButtons`, `JLabels` and so on, but in several of our programs the main thread has manipulated these as well, and so have other threads we've spawned. So should we be worried? The short answer is ... yes. **These Swing classes are not "thread safe", meaning they do not employ synchronized methods or other mechanisms to ensure that race conditions don't arise when multiple threads are involved**. So, we have kind of been playing with fire.

The way one is supposed to update Swing objects is to use the "event queue". The event queue, like our `Queue` with synchronized methods, is thread safe. Multiple threads can safely add items to it. What kind of items get added to the event queue? `Runnable` items. These items will be dequeued and their `run()` methods called, but they will be executed in the Event Dispatch Thread. If you do everything related to a GUI component this way, the component is only ever modified in the Event Dispatch Thread, so there is no concurrency, and thus no race conditions. **When you have an action you'd like to take in order to update a GUI component, instead of executing the action directly in whatever thread you are in, you should enqueue a `Runnable` object with the `invokeLater` method.**

For example, suppose have a `JTextField tf` and you want to set its text to "0.0". Instead of executing the statement: 

```java
tf.setText("0.0");
```

you should create the class: 

```java
public class MyRunnable implements Runnable {
  private JTextField t;
  public void MyRunnable(JTextField t) { this.t = t; }
  public void run() { t.setText("0.0"); }
}
```

and then, where you would have had `tf.setText("0.0");` you would write: 

```
java.awt.EventQueue.invokeLater(new myRunnable(tf));
```

This way, when the Event Dispatch Thread has a spare moment, it will dequeue this `MyRunnable` object and call its `run()` method, which will cause it to set `tf`'s text to "0.0". 

This idiom causes a proliferation of classes, which is a bit ugly and awkward, so Java allows you to define and instantiate a class "inline", using what are called anonymous classes. Using this mechanism, we can set our text with the following: 

```java
final JTextField tfp = tf;
java.awt.EventQueue.invokeLater(
   new Runnable() {
     public void run() { tfp.setText("0.0"); }
   }
);
```

The `final JTextField tfp` is there because of a technicality, which is that any local variable you reference from within the anonymous class has to be `final`. 

Because it rapidly involves a lot of anonymous classes, which we're not covering in-depth, we'll mostly stick to "playing with fire" in our simple GUIs for this class, and access GUI elements from outside the Event Dispatch Thread. Let's just hope we don't get burned! 


---
Material in this unit adopted with permission from IC211 at USNA


