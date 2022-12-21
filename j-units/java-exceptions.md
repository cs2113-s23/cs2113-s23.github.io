---
layout: toc
permalink: j/exceptions
---

# Exceptions

##  Understanding the issues involved in error handling

It's very hard to write programs that handle unexpected, or even merely unusual, situations gracefully. Think about it: how often do we just say "assume the file exists" or "assume the user enters a positive integer like you asked for"? All the time! Real-world programs can't do that. They have to be built to respond reasonably to any and all weird inputs, or unusual phenomena that can crop up while computing. These issues are made worse by the fact that well-designed code may end up being reused in different applications and even by different people than the original authors ever imagined. That means lots of things might be thrown at their code that the authors never imagined. None the less, they have to prepare for it.

So let's consider an example of a very simple program. What could go wrong? 

```java
/**
 * Run this program like this: 'java Ex1 3,5,9,4'
 * and it will compute
 *               ___________________________
 * floor[ 100 * V (1/3 + 1/5 + 1/9 + 1/4)/4) ]
 *
 * i.e. the floor of 100 x the square root of the
 * average of the reciprocals of the given numbers.
 */
public class Ex1 {
  public static void main(String[] args) {
    String[] A = args[0].split(",");
    int[] B    = new int[A.length];

    for (int i = 0; i < A.length; i++) {
      B[i] = Integer.parseInt(A[i]);
    }
    double sum = 0;

    for (int i = 0; i < B.length; i++) {
      sum += 1.0 / B[i];
    }
    double ssum = Math.sqrt(sum / B.length);
    int    res  = (int)(100.0 * ssum);
    System.out.println(res);
  }
}
```

 Obviously, this is a very simple program. As far as what could go wrong ... lots of stuff. Try some of these. 
 
```
~/$ java Ex1 1,2,3
78
~/$ java Ex1 -1,-2,-3
0
~/$ java Ex1 1,0,3
2147483647
~/$ java Ex1 1,foo
Exception in thread "main" java.lang.NumberFormatException: For input string: "foo"
  at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
  at java.lang.Integer.parseInt(Integer.java:492)
  at java.lang.Integer.parseInt(Integer.java:527)
  at Ex1.main(Ex1.java:15)
~/$ java Ex1 ,
0
~/$ java Ex1
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 0
  at Ex1.main(Ex1.java:12)
```

The first call is OK. Everything else has an error ... some of which the program is telling you about, others not. Can you identify all the things that could go wrong here? 

In fact, to be a bit more representative of real programs, let's assume that the we have things broken up into functions and maybe even split into different classes. We might get a situation like this. 

```java
public class Ex2 {
  public static int getSF(String[] av) {
    String[] A = av[0].split(",");
    int[] B    = new int[A.length];

    for (int i = 0; i < A.length; i++) {
      B[i] = Integer.parseInt(A[i]);
    }
    return SpecialFunc.compute(B);
  }

  public static void main(String[] args) {
    System.out.println(getSF(args));
  }
}
```
```java
public class SpecialFunc {
  public static int compute(int[] B) {
    double sum = 0;

    for (int i = 0; i < B.length; i++) {
      sum += 1.0 / B[i];
    }
    double ssum = Math.sqrt(sum / B.length);
    int    res  = (int)(100.0 * ssum);
    return res;
  }
}
```

Of course all the same things could go wrong, but now they can go wrong in different places: some problems crop up in `main`, some in the `getSF` method, and some in the `compute` function that's in a totally separate class! So if we want to actually take care to account for these kind of things and act appropriately in every situation ... what can we do? Now we start to see some of the problems that handling errors (or "exceptions") present us with. Here are a few: 

1. **Where's the BEST PLACE to handle the error?** The place where the error occurs might be far away from the code that is able to determine what best to do about the error. For example, `SpecialFunc.compute()` might encounter an error (like a zero value it is supposed to divide by), but it is only really the `Ex2.main()` method that would know whether an error message should be printed and the program terminated, or whether (for example) the user should be requested to enter a new string.

2. **What KIND of error are we dealing with?** There can be many kinds of errors, and in some situations a portion of the program may need to know precisely which errors occurred while in other situations it may be more desirable to treat all errors or large groups of errors the same. For example, maybe we simply want `main` to terminate with a usage message no matter what the error. On the other hand, maybe we want it to give detailed feedback about exactly what the problem is. Maybe we don't want to treat a zero in the input as an error (since we divide by it) but simply report the result as "infinity". 

3. **Ensure the feedback is USEFUL!** Different kinds of errors might require vastly different kinds and amounts of information pertaining to the circumstances that led to the error in order to properly remediate it. If the error is that a given filename turned out not to exist on the filesystem ... well, the name of the file that the system tried to find is useful information. If the error is that an attempt was made to access an array out of bounds ... well, where in the code this happened and what index was tried is useful information. 

The moral of the story is that error handling is difficult. The article ["Exception Handling in C++"](http://www.stroustrup.com/except89.pdf) by Bjarne Strouptrup (the author of the C++ language) is a really great read. It talks about a lot of the difficulties inherent in error handling, and it explains the design of C++'s exception handling mechanism, on which Java's is strongly based. He identifies four standard approaches to exception handling prior to the design of the C++ mechanism: 


1.    Terminate the program.
2.    Return a value representing 'error'
3.    Return a legal value and leave the program in an illegal state.
4.    Call a function supplied to be called in case of 'error'.

He also details why each of these is insufficient. You ought to be able to come up with some good objections on your own.

## Overview of the Java's Exception-handing mechanism

Java's exception handling mechanism basically provides an alternate way to "return" from a function (or, indeed, from an arbitrary block of code, as we'll see) which, instead of returning an object of the return-type specified in the function prototype, returns an object of type `Throwable`, which is a class in the Java API. This special alternative way to exit a function is acheived with a `throw` statement. Like this:

We refer to this as "raising an exception" (or "error" in some cases).

In the code that called the function or evaluated the expression that caused the exception to be raised, we need some way to capture that `throwable object`. This is accomplished with a `try-catch` block. 

```java
try {
  //... regular old code
} catch(Throwable e) {
  //... code to exectue if an exception was raised in executing the "regular old code"
}
```

The semantics (meaning) of this is that the regular old code in the try block is executed as usual. If no exceptions are raised while executing this code, the `catch` block is simply ignored. If, however, an exception is raised at some point, the "regular old code" following that point is not executed. Instead, control jumps to the `catch` block, and the code in it is executed. This is where you deal with whatever the problem was that caused an exception to be raised. 

## Catching an Exception

There are already several things we might do that cause exceptions to be thrown. You can see two by playing with the above program: indexing an array out of bounds, and calling `Integer.parseInt()` with a string that cannot be interpreted as an integer. So let's see if we can catch these exceptions. Where we catch them, and what we do as a result depends on what the goals of our program are. Here are two possibilities: 

```java
public class Ex2 {
  public static int getSF(String[] av) {
    String[] A = av[0].split(",");
    int[] B    = new int[A.length];

    for (int i = 0; i < A.length; i++) {
      B[i] = Integer.parseInt(A[i]);
    }
    return SpecialFunc.compute(B);
  }

  public static void main(String[] args) {
    try {
      System.out.println(getSF(args));
    } catch (Throwable e) {
      System.out.println("Bad stuff happened!");
    }
  }
}
```
```java
public class Ex3 {
  public static int getSF(String[] av) {
    try {
      String[] A = av[0].split(",");
      int[] B    = new int[A.length];

      for (int i = 0; i < A.length; i++) {
        B[i] = Integer.parseInt(A[i]);
      }
      return SpecialFunc.compute(B);
    } catch (Throwable e) { // Shh! Act like nothing happened
      return 0;
    }
  }

  public static void main(String[] args) {
    System.out.println(getSF(args));
  }
}
```

## Raising an exception

Whether we want to provide the simple error message "Bad stuff happened!" or whether we want to put our heads in the sand and return zero in the face of any error, the solutions above are only half effective. Why? Because errors like dividing by zero or taking the square root of a negative number are not causing errors to be raised, and therefore, our calling functions are blissfully unaware that they've even happened. To make our program complete (admittedly with a very simple and silly kind of response to errors) `SpecialFunc.compute()` needs to raise exceptions in those cases. That's as simple as adding some checks with:

```java
throw new Throwable();
```

... for cases when the checks fail, with one little catch. Just as the compiler needs to know what the name, return type and parameters are a for a function, Java needs to know what exceptions a method might raise; and it expects that fact to be made specific. How? By adding a `throws` clause to the end of the prototype. 

```java
public class SpecialFunc {
  public static int compute(int[] B) throws Throwable {
    double sum = 0;

    for (int i = 0; i < B.length; i++) {
      if (B[i] == 0) {
        throw new Throwable();
      }
      sum += 1.0 / B[i];
    }

    if (sum < 0) {
      throw new Throwable();
    }
    double ssum = Math.sqrt(sum / B.length);
    int    res  = (int)(100.0 * ssum);
    return res;
  }
}
```

With this, our `Ex3` version (which printed out zero whenever there was an error) works great. Our `Ex2` version is not quite there, but for an interesting reason: the method `getSF()` calls `SpecialFunc.compute()`, which might end up raising an exception, without making any provision for the possibility. That, as it turns out, is a no-no! With a caveat that we will address shortly, any Java method that includes code that might result in an exception being raised must either catch the exception, or pass it along to the next function down the call-stack — essentially itself throwing the exception. This means that such an "intermediate method", `gertSF()` in our case, must also be annotated with the "throws clause". **These types of exceptions are called checked exceptions** because they are declared as possibly being thrown/raised, and therefore, the programmer must place the code inside a try-catch block.


```java
public class Ex2 {
  public static int getSF(String[] av) throws Throwable {
    String[] A = av[0].split(",");
    int[] B    = new int[A.length];

    for (int i = 0; i < A.length; i++) {
      B[i] = Integer.parseInt(A[i]);
    }
    return SpecialFunc.compute(B);
  }

  public static void main(String[] args) {
    try {
      System.out.println(getSF(args));
    } catch (Throwable e) {
      System.out.println("Bad stuff happened!");
    }
  }
}
```

## The Exception hierarchy — OOP in action
 So that's the basic mechanism for error-handling in Java: `throws` and `try-catch`. Here, however, is where the object-oriented design case study comes in. We identified three problems we needed to address in an error handling mechanism, and so far we've only addressed point 1: we can deal with the fact that errors occur in one method, but need to be remediated in some other, possibly distant, method. But what about point 2, being able to distinguish between different kinds of errors when needed (and only when needed)? What about point 3, being able to deliver different kinds of data about the error to the point in the code that would actually remediate?

The answer to this is simple: derive different classes from `Throwable` that correspond to different kinds of errors. In fact, the API already has a whole class hierarchy rooted from `Throwable` and, of course, as we create our own exception classes we expand that hierarchy. To understand exception handling in Java, we really need to understand at least a part of that hierarchy. 

```
                       Throwable
                       /      \
                      /        \
                   Error     Exception
                              /    \
                             /      \
                            /        \
               ** RuntimeException     IOException
```

What's important here is that the compiler does not require methods to handle (i.e. either catch or re-throw) Error's or RuntimeException's. Java calls these **unchecked exceptions**. You can raise an unchecked exception wherever you want, and no other code is required to catch it. If it is not caught, it pops the entire stack until the program crashes. Every other exception is a **checked exception**. It must be handled! This means that if you call a method that raises a checked exception, you have to either (1) catch that exception at your call site, or (2) declare your own method also `throws` it.

How does a hierarchy like this solve our other two problems? Based on the type of exception we catch we can determine categories of errors that occurred. Using polymorphism, different `Throwable` sub-types, can present us with information specific to their sub-type, but through the uniform interface of methods available in type `Throwable`. 

## Catching different kinds of Exceptions

A given try block can actually have multiple catch-s, each differing in the type they are trying to catch. Of course, all types are ultimately `Throwable`s, so what happens is the catches are tried top-to-bottom, and the first type that matches has its `catch` block (and only its `catch` block) executed. This allows us to, for example, offer error messages that are actually informative! 
 
```java
public class Ex2 {
  public static int getSF(String[] av) throws Throwable {
    String[] A = av[0].split(",");
    int[] B    = new int[A.length];

    for (int i = 0; i < A.length; i++) {
      B[i] = Integer.parseInt(A[i]);
    }
    return SpecialFunc.compute(B);
  }

  public static void main(String[] args) {
    try {
      System.out.println(getSF(args));
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Error! An argument is required.");
    } catch (NumberFormatException e) {
      System.out.println("Error! Argument included a non-integer value.");
    } catch (Throwable e) {
      System.out.println("Bad stuff happened!");
    }
  }
}
```

If we run this, we get output like the following: 
 

```
~/$ java Ex2v
Error! An argument is required.
~/$ java Ex2 1,df
Error! Argument included a non-integer value.
~/$ java Ex2 1,0
Bad stuff happened!
~/$ java Ex2 ,
0
~/$ java Ex2 -1
Bad stuff happened!
```
So we've got some errors covered with nice explanations. Others, not so much. Next class we'll use polymorphism and we'll define our own exceptions, all in the name of handling our errors nicely! 

**Note:** can you tell me what would've happened if we'd put the `catch(Throwable e)` first instead of last?

# Java I/O 

A common place where Java programmers first really experience exception handling in action is with I/O (input/output), so let's take a look at it now. Not only is it something you should know as a Java programmer, but it gives us a nice case study in object oriented design. So we're going to first try to understand the problem: what requirements do we really have of an I/O system? Then we'll see how the Java IO package's object oriented design meets those goals. Finally, in the lab assignment, we'll see how exception handling dovetails with I/O.

## Reading ... What's the problem?

I/O (input and output) is kind of what programs are all about. If we couldn't instruct the program as to our intentions or we couldn't somehow perceive the results produced by the program, what would be the point in running it? In this lesson we look at byte and character I/O — I/O concerning files, buffers in memory, network connections, things like that. GUI I/O will be covered later.

Designing a system for handling I/O is a daunting problem for language/library/API developers. These operations are so ubiquitous in programs that getting it wrong means making pretty much every program anyone ever writes more difficult, and getting it right means making pretty much every program anyone ever writes easier. So, let's consider some desirable properties, some design goals, regarding reading, i.e. the "input" half of I/O. In particular, we'll look at stream-oriented input and output.

* **Bytes or chars or tokens?** Fundamentally, all data in the modern computing world is byte-oriented, and oftentimes we want to read data that way. On the other hand, often we want to read textual data - i.e. data that is character-oriented. In C, where char's and bytes are synonymous, we don't need to distinguish between the two. In Java, however, where characters are unicode and not generally single-byte values, we very much have to distinguish between the two. Sometimes the programmer will want to read bytes, sometimes characters, and sometimes tokens - like the textual representations of doubles or booleans.

* **Treat multiple sources of input in a uniform way Input can come from many different sources:** files, network connections, standard in, strings, arrays of bytes, arrays of characters, pipes. etc. We saw in writing C that writing functions that took an `FILE *` stream argument, which could equally take a `inputfilestream` or `stdin`, was a powerful idea. This is just an example of the power of treating multiple sources of input in a uniform way, i.e. with one programming construct that applies to many different actual sources of input.

* **Flexibility to add new operations, improve efficiency, or modify input streams on the fly:** With something as universally used as stream-oriented I/O, there's no way to design a system that will meet everyone's needs all of the time. Therefore, the system needs to provide programmers the flexibility to change how things work without abandoning or breaking the whole system.

## Bytes, Chars or Tokens?

The Java I/O design takes into account issue 1 from the above in a typically OOP way, by having three separate classes of input objects. 

* **abstract class [InputStream](https://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html)** for byte-based input.
  ```java
int read(); //returns an int from 0 to 255
            //or -1 if at the end of the stream
int read(byte[] b, int off, int len);
void close();
...
```
* **abstract class [Reader](https://docs.oracle.com/javase/8/docs/api/java/io/Reader.html)** for character-based input
  ```java
int read(); //returns an int from 0 to 65535
            //or -1 if at the end of the stream
int read(char[] cbuf, int off, int len);
void close();
...
```
* **class [Scanner](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html)** for token-based input
   ```java
String next();
int nextInt();
double nextDouble();
...
```

As a programmer, you have to figure out how you want to read data in. Would you like to read a byte or chunk of bytes at a time? Then you want an InputStream object. Would you like to read a character or chunk of characters at a time? Then you want a Reader object. Would you like to read a token at a time, e.g. a double at a time or String at a time or boolean at a time? Then you want a Scanner object. So far, we've focused on reading tokens (an int or word or float) at a time.

## Treat multiple sources of input in a uniform way

Here's how the Java IO system uses OOP to allow multiple sources of input to be treated in a uniform way. First of all, the InputStream and Reader classes are abstract. They are the roots of class hierarchies. Specific sources of bytes of data give rise to classes that extend InputStream. For example, in the API we have: 

```
           InputStream
               ^  ^
              /    \
             /      \
FileInputStream   ByteArrayInputStream
```

So if, for example, you want to write code to search for the bytes 0x7F 0x45 0x4C 0x46, which indicates the beginning of a Executable and Linkable Format (ELF) file (i.e. an executable file in Unix, or what `gcc` compiles a program into &#128540;), you would write your method to take an InputStream argument. That way the same method works for both files and byte arrays.

Similarly, specific sources of characters give rise to classes that extend Reader. For example, in the API we have: 

```
              Reader <-._
             ^   ^       \____
            /     \           \
           /       \           \
StringReader  CharArrayReader  InputStreamReader
```


So, if you wanted to write code to count the number of non-alphabetical characters in text, you would write that method to take a Reader argument. That way the same method works for Strings, arrays of chars and (and this is interesting!) any InputStream — because we can make an `InputStream` the source of characters for a Reader via the InputStreamReader class! If you look at the API documentation for [InputStreamReader](https://docs.oracle.com/javase/8/docs/api/java/io/InputStreamReader.html), the InputStreamReader constructors take an InputStream as a parameter. And yes, if you're wondering `System.in` is an InputStream, so you can create an `InputStreamReader` from `System.in`.

Finally, we have our good friend the Scanner.

Class Scanner has constructors that take `InputStreams` or `Readers` as arguments. So if you wanted to write code to do something like add all the integers in some text, you would write that method to take a Scanner as an argument. That way it would work with files, byte arrays, char arrays or Strings. Putting this together, if you have a file whose name is "data.txt" and you want to read in tokens from it (e.g. ints and double and booleans and strings), you would create a scanner for it like this: 

```java
Scanner sc = new Scanner(new InputStreamReader(new FileInputStream("data.txt")));
/*                                              \_____________________________/
                                                 an InputStream whose bytes
                                                 come from file data.txt
                         \____________________________________________________/
                          a Reader whose chars come from the bytes in data.txt
             \_________________________________________________________________/
    a Scanner whose tokens are made up of chars whose bytes come from data.txt
*/
```

There are some shortcuts to all of this. So-called "convenience methods" to make, for example, a Reader directly from a file name. 

> Two important points here: 
> 1. Technically there is no `Scanner` constructor that takes a `Reader` as a parameter. Instead, it takes an object that implements the `Readable` interface. However, `Reader` implements `Readable`, so this constructor works with `Readers`, but is in fact a bit more general than that. 
> 2. The `Scanner` constructor that takes an InputStream as an argument is actually just a convenience thing. You only really need the constructor that takes a `Reader` as an argument. Why is that enough?



## Flexibility to add new operations, improve efficiency, or modify input streams on the fly

Finally we get to the third and last of our design goals: the flexibility to add new operations, improve efficiency, or modify input streams on the fly. When we want to modify or extend functionality in OOP, what do we always do? We use inheritance. I'll give you two examples of where this is done in the Java API, one to modify behavior and one to add functionality.

The first is the class [BufferedReader](https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html). The issue `BufferedReader` addresses is this: when a call to `read()` is made for a `Reader` that has, for example, a file as its ultimate source for data, that call results at some lower level in a system call to fetch that byte. At this low level, however, fetching a byte-at-a-time is tremendously inefficient. It typically takes as much time to fetch something like 1024 or 2048 bytes as it does a single byte. Therefore, it would be nice to have a variant of Reader that would fetch, say, 1024 bytes into a buffer the first time `read()` is called, then dole those out one-at-a-time for each `read()` call until the buffer is emptied. Only then would it go back to fetch more bytes from the lower-level — another chunk of 1024. That's what the class `BufferedReader` does. What's kind of funny is that it does it as a wrapper around another `Reader`. In other words, `BufferedReader` is a `Reader` that takes a `Reader` and wraps it in this buffering scheme. So for example, if you had a file `data.txt` to read tokens (e.g. integers) from, and you were worried about performance, you might create your Scanner like this: 

```java
Scanner sc1 = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream("data.txt"))));
```

The `BufferedReader` will make calls like `read(buff,0,1024)` to its underlying `InputStreamReader`, which will make a call like `read(buff,0,1024)` to its underlying `FileInputStream`, which will result in a lower-level system call to fetch the next 2024 bytes from the file. The object oriented design of Java's I/O package makes this possible. By deriving `BufferedReader` from `Reader`, the Java authors provide modified functionality that can be used anywhere a regular Reader can be used. 

The second example to look at is the class [LineNumberReader](https://docs.oracle.com/javase/8/docs/api/java/io/LineNumberReader.html), which is much easier to explain. Sometimes you want to be able to ask what line you're on as you read input. The `BufferedReader` is a great class that buffers the input for you, making the reading process more efficient by making less calls to the input stream. However, it doesn't track things like what number you are on. It's an extra piece of functionality you might wish a `BufferedReader` had. The class `LineNumberReader` extends `BufferedReader` to provide just that, and it takes any `Reader` as input. We can create one like this: 

```java
LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream("data.txt")));
```

... and whenever you want to know what line number you're on you can call `r.getLineNumber()`. Once again, the object oriented design of Java's I/O package makes this possible. By deriving `LineNumberReader` from `BufferedReader`, the Java authors provide new functionality that can use any type of input stream, yet still track the lines that have been processed. What if you still want a Scanner? You can create one for each line that your new reader returns: 

```java
LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream("data.txt")));
String line = reader.readLine();
while( line != null ) {
  Scanner sc = new Scanner(line);
      
  // read your tokens for whatever you need!
  // ...
      
  line = reader.readLine();
  System.out.println("You're on line " + reader.getLineNumber());      
}
```

We could dive deeper into the weeds here and talk about what would happen if you wrap a `Scanner` around a `LineNumberReader` (`new Scanner(new LineNumberReader(...))`). While this is allowed (it's a Reader!), you kind of lose the line number functionality because the `Scanner` is using the buffered characteristics of the reader here, and it reads ahead while you're calling its `next()` methods. If you ask for the line number, it will often be too far. Still, it could be useful in getting the general line number if not the precisely correct one. 

> **And then ...**
> 
> And then we should probably look at the Errors and Exceptions that all these > methods from all these classes throw ... but we won't. You can do that on your own! 

## A short note on output

Output is fundamentally a bit easier than input. Why? Because with output your code knows what it wants to write, so it controls the outgoing bytes. With input, your code doesn't know what's coming. It must react and adapt to the incoming bytes. So we're not going to describe output in as much detail. 

Similar to the input case, we have two separate hierarchies for output: the hierarchy rooted at `OutputStream`, which is for byte-oriented output, and the hierarchy rooted at `Writer`, which is for character-oriented output. The distinction is a bit blurrier than for the input case, because the class `PrintStream`, which is derived from `OutputStream`, provided methods for writing int's, double's, String's, etc., as does `PrintWriter`, which is derived from `Writer`. The distinction has to do with how characters are encoded as bytes: `PrintStream` using the JVM's default encoding and `PrintWriter` allowing the programmer to independently specify that encoding. These are distinctions we won't go into here. Note, however, that `System.out` and `System.err` are both `PrintStream` objects. 


Just to round out this example, let's look at what it would take to write "Hello World" to a file. 

```java

PrintWriter pw = new PrintWriter(new File("output.txt")); 
//Note that we are not specifiying a character set. Java defaults to unicode

pw.println("Hello World!");
pw.close(); //close the file
```

As you can see, printing/writing is much more straightforward than reading. 

---
Material in this lesson adopted with permission from USNA ic211
