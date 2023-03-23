---
layout: worksheet
permalink: /worksheet/j5
showsolution: false
---

# Worksheet: J5

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.

## Note

Attempt to answer these questions before running the code. This will improve your ability to analyize and reason about code without an IDE or compiler. This skill we be helpful on the exams.

## Questions

### q

Compile the two files below and run the main method in the `SimpleThreadExample`. 

```
$ javac *.java
$ java SimpleThreadExample
```

```java
/* SimpleThreadExample.java */
public class SimpleThreadExample {

    public static void main(String[] args) {
    
        
        Messages msg = new Messages();
        Thread morningThread = new GreetingsThread(msg, 0);
        Thread afternoonThread = new GreetingsThread(msg, 1);

        morningThread.start();
        afternoonThread.start();
        //MARK
        
    }
}
```

```java
/* Messages.java */
public class Messages {
    public String morning;
    public String afternoon;
    public String evening;
    public String night;

    public Messages() {
      morning = "Good morning!";
      afternoon = "Good afternnon!";
      evening = "Good evening!";
      night = "Good night!";
    }

    public String getMessage(int choice) {
      switch(choice) {
         case 0:
           return this.morning;
         case 1:
           return this.afternoon;
         case 3:
           return this.evening;
         case 4:
           return this.night;
         default:
           return "";        
      }
    }
 }
```

```java
/* GreetingsThread.java */
public class GreetingsThread extends Thread {

    private Messages msg;
    private int choice;
    public GreetingsThread(Messages msg, int choice) {
        this.msg = msg;
        this.choice = choice;
    }

    public void run() {
        System.out.println("Started the " + msg.getMessage(choice) + " thread...");
            
        //print 10 times
        for (int i = 0; i < 10; i++) {
            System.out.println(msg.getMessage(choice));
        }

        System.out.println("Exiting the " + msg.getMessage(choice) + " thread...");
    }
}
```

Then:
* Run the program a few times. Describe the output of this program. Is it consistent?
* Draw a memory diagram of the program at `MARK`

#### s
* The output of the program is not consistent.
* ```
  Stack
  
  .------------------------------------------------------------.
  |                                                            |
  |  main                   .------->morningThread -----.      '---->afternoonThread ---.
  | .-----------------.    |        .-------------.     |           .--------------.    |
  | | msg             |    |        | run()       |     |           | run()        |    |
  | | morningThread   |----'        | i           |     |           | i            |    |
  '-| afternoonThread |             | this        |     |           | this         |    |
    '-----------------'             '-------------'     |           '--------------'    |
                                                        |                               |
  ------------------------------------------------------+-------------------------------+--
  Heap                                                  |                               |
                                                        |                               |
     .--------------------------------------------------+--.                            |
     |  .-----------.             .-----------------.   |  |    .-----------------.     |
     '->| Messages  |<----.       | GreetingsThread |<--'  |    | GreetingsThread |<----'
        +-----------+     |       +-----------------+      |    +-----------------+
    .---| morning   |     '-------| msg             |      '----| msg             |
    | .-| afternoon |             | choice 0        |           | choice 1        |
    | | | evening   |-----------. '-----------------'           '-----------------'
    | | | night     |---------. |
    | | '-----------'         | |
    | |                       | |
    | |  .-----------------.  | |
    | |  | Good night!     |<-' |
    | |  '-----------------'    |
    | |                         |
    | |  .-----------------.    |
    | |  | Good evening!   |<---'
    | |  '-----------------'
    | | 
    | |  .-----------------.
    | '->| Good afternoon! |
    |    '-----------------'
    | 
    |    .-----------------.
    '--->| Good morning!   |
         '-----------------'
  ```

### q

Now let's modify `GreetingsThread` to add a `Thread.sleep()` in the `run()` method. Recompile the `GreatingThread` class as below.

Run the program multiple times.  Does the output change in any way? Does one thread always finish first, or does the order change?


```java
/* GreetingsThread.java */
public class GreetingsThread extends Thread {
    private Messages msg;
    private int choice;

    public GreetingsThread(Messages msg, int choice) {
        this.msg = msg;
        this.choice = choice;
    }

    public void run() {
        System.out.println("Started the " + msg.getMessage(choice) + " thread...");

        for (int i = 0; i < 10; i++) {
            System.out.println(msg.getMessage(choice));

            try {
                // Sleep for 1 second (1000 milliseconds)
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                System.out.println("Interrupted while sleeping...");
            }
        }

        System.out.println("Exiting the " + msg.getMessage(choice) + " thread...");
    }
}
```

#### s
Because each thread will sleep for 1 second after each print statement, the output changes in a way that two outputs are not clustered (e.g. more interleaving between the two messages). The order of two threads finish is still random.

### q

Now let's add a `System.out.println()` at the end of the main method. Recompile the program with this addition, continuing from above. Explain how it is possible that the main method is complete but the program is still producing output.


```java
/* SimpleThreadExample.java */
public class SimpleThreadExample {
    public static void main(String[] args) {
            
        Messages msg = new Messages();
        Thread morningThread = new GreetingsThread(msg, 0);
        Thread afternoonThread = new GreetingsThread(msg, 1);

        morningThread.start();
        afternoonThread.start();

        System.out.println("Main method exiting...");
    }
}
```

#### s
The main method acts as a 3rd thread where we have 3 different threads in total. Thus, the main thread completing has no effect on the `morningThread` and `afternoonThread`, which will both continue running.

### q

Finally let's add a `thread.join()` to join the `morningThread` for 5 seconds before starting the `afternoonThread`. Recompile and rerun. Then describe the output of this program. Explain how attempting to join the first thread for 5 seconds affects the output of this program.


```java
/* SimpleThreadExample.java */
public class SimpleThreadExample {
    public static void main(String[] args) {
        Messages msg = new Messages();
        Thread morningThread = new GreetingsThread(msg, 0);
        Thread afternoonThread = new GreetingsThread(msg, 1);

        morningThread.start();

        try {
            System.out.println("Joining the morning thread for 5 seconds...");
            morningThread.join(5000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted while joining a thread...");
        }

        afternoonThread.start();

        System.out.println("Main method exiting...");
    }
}
```

#### s
The output becomes more consistent than previous implementations. The join for 5 seconds forces the `morningThread` to run for the first 5 seconds, resulting in 5 "Good morning" being printed out consecutively (thread sleeps for 1 second after each print). After that, the `afternoonThread` starts and the main thread exits which leads to interleaving messages between `morningThread` and `afternoonThread`. Finally, because `morningThread` started at least 5 seconds before `afternoonThread`, it finishes first leaving the `afternoonThread` printing out consecutive "Good afternoon!" messages.

### q

What does `join()` do compared to `join(10)`?

#### s
`join()` will make the current thread wait until the referenced thread terminates; it may wait 0 seconds (if the referenced thread already terminated), or infinitly long (if the referenced thread never terminates). `join(10)` will make the current thread wait for a maximum of 10 milliseconds.

### q

What is the difference between `isAlive()` and `join()`? (note, no arguments to `join`.)

#### s
When `isAlive()` is called, the status of the referenced thread is returned as a boolean without the need to wait for the referenced thread to terminate before the current thread continues. However, when `join()` is called, current thread will wait for the referenced thread to terminate.

### q

Compile the two files below and run the main method in the `AnimalFootRace`. Then describe the output of this program.  Explain why the threads finish in the order that they do.

```
$ javac *.java
$ java AnimalFootRace
```

```java
/* AnimalFootRace.java */
public class AnimalFootRace {
    public static void main(String[] args) {
        Thread tortoiseThread = new AnimalRacerThread("Tortoise", 5);
        Thread hareThread = new AnimalRacerThread("Hare", 20);
        Thread cheetahThread = new AnimalRacerThread("Cheetah", 50);

        System.out.println("On your marks, get set, go!");
        tortoiseThread.start();
        hareThread.start();
        cheetahThread.start();
    }
}
```

```java
/* AnimalRacerThread.java */
public class AnimalRacerThread extends Thread {
    public static final int NUM_LAPS = 8;
    private String animalName;
    private int animalSpeed;

    public AnimalRacerThread(String animalName, int animalSpeed) {
        this.animalName = animalName;
        this.animalSpeed = animalSpeed;
    }

    @Override
    public void run() {
        for (int i = 1; i <= NUM_LAPS; i++) {
            System.out.println(animalName + " lap " + i);
            try {
                Thread.sleep(1000 / animalSpeed);
            } catch (InterruptedException e) {
                System.out.println("Interrupted while sleeping...");
            }
        }
        System.out.println(animalName + "Finished!");
    }
}
```

#### s

The output of the program is:
```
On your marks, get set, go!
Tortoise lap 1
Cheetah lap 1
Hare lap 1
Cheetah lap 2
Hare lap 2
Cheetah lap 3
Cheetah lap 4
Hare lap 3
Cheetah lap 5
Cheetah lap 6
Hare lap 4
Cheetah lap 7
Tortoise lap 2
Cheetah lap 8
Hare lap 5
CheetahFinished!
Hare lap 6
Hare lap 7
Tortoise lap 3
Hare lap 8
HareFinished!
Tortoise lap 4
Tortoise lap 5
Tortoise lap 6
Tortoise lap 7
Tortoise lap 8
TortoiseFinished!
```
Each thread sleeps for `1000 / animalSpeed` milliseconds. Because the `cheetahThread`, `hareThread` and `tortoiseThread` has `animalSpeed` of 50, 20 and 5 respectively, the threads sleeps for 20, 50 and 200 milliseconds respectively. Therefore, the `cheetahThread` finishes first, `hareThread` second and `tortoiseThread` last.

### q

Modify the `AnimialFootRace` main method. Add additional code to give the hare a head start, without modifying the animal speeds. Then compile the two files below and run the main method in the `AnimalFootRace`. Provide the modified code below.  Then describe the output of this program.  Explain why your modification worked or did not work.

```
$ javac *.java
$ java AnimalFootRace
```

```java
/* AnimalFootRace.java */
public class AnimalFootRace {
    public static void main(String[] args) {
        Thread tortoiseThread = new AnimalRacerThread("Tortoise", 5);
        Thread hareThread = new AnimalRacerThread("Hare", 20);
        Thread cheetahThread = new AnimalRacerThread("Cheetah", 50);

        System.out.println("On your marks, get set, go!");
        tortoiseThread.start();
        hareThread.start();
        cheetahThread.start();
    }
}
```

```java
/* AnimalRacerThread.java */
public class AnimalRacerThread extends Thread {
    public static final int NUM_LAPS = 8;
    private String animalName;
    private int animalSpeed;

    public AnimalRacerThread(String animalName, int animalSpeed) {
        this.animalName = animalName;
        this.animalSpeed = animalSpeed;
    }

    @Override
    public void run() {
        for (int i = 1; i <= NUM_LAPS; i++) {
            System.out.println(animalName + " lap " + i);
            try {
                Thread.sleep(1000 / animalSpeed);
            } catch (InterruptedException e) {
                System.out.println("Interrupted while sleeping...");
            }
        }
        System.out.println(animalName + "Finished!");
    }
}
```

#### s
* Modified main function
  ```java
  /* AnimalFootRace.java */
  public class AnimalFootRace {
      public static void main(String[] args) {
          Thread tortoiseThread = new AnimalRacerThread("Tortoise", 5);
          Thread hareThread = new AnimalRacerThread("Hare", 20);
          Thread cheetahThread = new AnimalRacerThread("Cheetah", 50);
  
          System.out.println("On your marks, get set, go!");
          hareThread.start();
          try {
              System.out.println("Joining the morning thread for 5 seconds...");
              hareThread.join(500);
          } catch (InterruptedException e) {
              System.out.println("Interrupted while joining a thread...");
          }
  
          tortoiseThread.start();
          cheetahThread.start();
      }
  }
  ```
* Having the hare start 0.5 seconds in advance allowed it to finish all 8 laps before the tortoise or the cheetah begin their first lap. Thus, the output becomes the following:
  ```
  On your marks, get set, go!
  Joining the morning thread for 5 seconds...
  Hare lap 1
  Hare lap 2
  Hare lap 3
  Hare lap 4
  Hare lap 5
  Hare lap 6
  Hare lap 7
  Hare lap 8
  Tortoise lap 1
  Cheetah lap 1
  HareFinished!
  Cheetah lap 2
  Cheetah lap 3
  Cheetah lap 4
  Cheetah lap 5
  Cheetah lap 6
  Cheetah lap 7
  Tortoise lap 2
  Cheetah lap 8
  CheetahFinished!
  Tortoise lap 3
  Tortoise lap 4
  Tortoise lap 5
  Tortoise lap 6
  Tortoise lap 7
  Tortoise lap 8
  TortoiseFinished!
  ```
* The modification may not compile if the try/catch block is not included for the `join()` method. When the join time expires (e.g. 5 seconds on `join(5000)` or called thread finishes on `join()`), the interrupted status of the current thread (in this problem the `main` thread) is cleared when `InterruptedException` is raised. Thus, the `main` thread needs to catch the `InterruptedException`.

### q

Modify the `AnimialFootRace` main method. Add a new animal thread to the foot race. Then compile the two files below and run the main method in the `AnimalFootRace`. Provide the modified code below. Then describe the output of this program. 

```
$ javac *.java
$ java AnimalFootRace
```

```java
/* AnimalFootRace.java */
public class AnimalFootRace {
    public static void main(String[] args) {
        Thread tortoiseThread = new AnimalRacerThread("Tortoise", 5);
        Thread hareThread = new AnimalRacerThread("Hare", 20);
        Thread cheetahThread = new AnimalRacerThread("Cheetah", 50);

        System.out.println("On your marks, get set, go!");
        tortoiseThread.start();
        hareThread.start();
        cheetahThread.start();
    }
}
```

```java
/* AnimalRacerThread.java */
public class AnimalRacerThread extends Thread {
    public static final int NUM_LAPS = 8;
    private String animalName;
    private int animalSpeed;

    public AnimalRacerThread(String animalName, int animalSpeed) {
        this.animalName = animalName;
        this.animalSpeed = animalSpeed;
    }

    @Override
    public void run() {
        for (int i = 1; i <= NUM_LAPS; i++) {
            System.out.println(animalName + " lap " + i);
            try {
                Thread.sleep(1000 / animalSpeed);
            } catch (InterruptedException e) {
                System.out.println("Interrupted while sleeping...");
            }
        }
        System.out.println(animalName + "Finished!");
    }
}
```

#### s
* Modified main function
  ```java
  /* AnimalFootRace.java */
  public class AnimalFootRace {
      public static void main(String[] args) {
          Thread tortoiseThread = new AnimalRacerThread("Tortoise", 5);
          Thread hareThread = new AnimalRacerThread("Hare", 20);
          Thread cheetahThread = new AnimalRacerThread("Cheetah", 50);
          Thread hippoThread = new AnimalRacerThread("Flying Hippo", 500);
  
          System.out.println("On your marks, get set, go!");
          tortoiseThread.start();
          hareThread.start();
          cheetahThread.start();
          hippoThread.start();
      }
  }
  ```
* Output
  ```
  On your marks, get set, go!
  Flying Hippo lap 1
  Cheetah lap 1
  Hare lap 1
  Tortoise lap 1
  Flying Hippo lap 2
  Flying Hippo lap 3
  Flying Hippo lap 4
  Flying Hippo lap 5
  Flying Hippo lap 6
  Flying Hippo lap 7
  Flying Hippo lap 8
  Cheetah lap 2
  Flying HippoFinished!
  Hare lap 2
  Cheetah lap 3
  Cheetah lap 4
  Cheetah lap 5
  Hare lap 3
  Cheetah lap 6
  Hare lap 4
  Cheetah lap 7
  Tortoise lap 2
  Cheetah lap 8
  Hare lap 5
  CheetahFinished!
  Hare lap 6
  Hare lap 7
  Tortoise lap 3
  Hare lap 8
  HareFinished!
  Tortoise lap 4
  Tortoise lap 5
  Tortoise lap 6
  Tortoise lap 7
  Tortoise lap 8
  TortoiseFinished!
  ```
* Adding the flying hippo with speed 500 (10 times the speed of cheetah) resulting in expected output of flying hippo finishing the race before anyone else start their 2nd lap.


### q

The `Singleton` class below implements the `Singleton` pattern.  The singleton pattern is a software engineering design pattern that restricts the instantiating of a class to a single instance. Review the `Singleton` class code below.  Is it possible to create more than one instance of the `Singleton` class if two threads attempt to call the `getInstance()` method at the same time?


```java
// Java program to create a Singleton class.
public class Singleton {
    // This is a private member variable so that the singletonInstance
    // can only be accessed through the getInstance() method.
    private static Singleton singletonInstance;

    // Private constructor forces the class to be instantiated 
    // via the getInstance method.
    private Singleton() {
        // private constructor.
    }

    // Method to get an instance of this class.
    public static Singleton getInstance() {
        // If this singleton instance is null, 
        // then construct a new instance.
        // Otherwise return the existing instance.
        if (singletonInstance == null) {
            singletonInstance = new Singleton();
        }

        return singletonInstance;
    }
}
```

#### s
Theoretically, it is possible to create more than 1 instance of the `Singleton` class if 2 threads attempts to call `getInstance()` method at the same time in the above code. The reason it is only "theoretical" is two threads need to call `getInstance()` at almost exact same time so that one `getInstance()` is not complete before the other thread executes `getInstance()`. See the example below on how this issue is resolved.

### q

Review the modified thread safe Singleton class code below.  Is it possible to create more than one instance of the `Singleton` class if two threads attempt to call the `getInstance()` method at the same time? How does the synchronized keyword affect the attempts to call the `getInstance` method from multiple threads at the same time?


```java
// Java program to create thread safe Singleton class.
public class Singleton {
    // This is a private member variable so that the singletonInstance
    // can only be accessed through the getInstance() method.
    private static Singleton singletonInstance;

    // Private constructor forces the class to be instantiated 
    // via the getInstance method.
    private Singleton() {
        // private constructor.
    }

    // Synchronized method to control simultaneous access 
    // to the getInstance method.
    synchronized public static Singleton getInstance() {
        // If this singleton instance is null, 
        // then construct a new instance.
        // Otherwise return the existing instance.
        if (singletonInstance == null) {
            singletonInstance = new Singleton();
        }

        return singletonInstance;
    }
}
```

#### s
It is not possible for multiple threads to create more than one instance of the `Singleton` class if more than one threads attempt to call the `getInstance()` method at the same time with the synchronized keyword added to the `getInstance()` method.


### q

Review the `ThreadRunner` class code below.  Compile and run the `ThreadRunner` main method. Describe the output. Does the execution halt? Does `SimpleThreadTwo` finish running? If not, why does `SimpleThreadTwo` get stuck in the while loop (hint: recall the compiler optimization example)?

```
$ javac ThreadRunner.java
$ java ThreadRunner
```

```java
/* ThreadRunner.java */
public class ThreadRunner {

    private static boolean statusFlag = false;

    private static class SimpleThreadOne extends Thread {
        public void run() {
            for (int i = 1; i <= 2000; i++){
                System.out.println("Simple thread one counter - " + i);
            }
            // Change the status flag.
            statusFlag = true;
            System.out.println("Status flag changed to true in simple thread one.");
        }
    }

    private static class SimpleThreadTwo extends Thread {
        public void run() {
            int waitCounter = 1;
            while (!statusFlag) {
                waitCounter++;
            }
            System.out.println("Start simple thread two processing " + waitCounter);
        }
    }

    public static void main(String[] args) {
        SimpleThreadOne simpleThreadOne = new SimpleThreadOne();
        simpleThreadOne.start();
        SimpleThreadTwo simpleThreadTwo = new SimpleThreadTwo();
        simpleThreadTwo.start();
    }
}

```

#### s
Just like the example given in class, the `while (!statusFlag)` is optimized to run as the following two lines of code
```java
boolean tmp = !statusFlag;
while (tmp)
```
Thus, `SimpleThreadTwo` is not getting the updated `statusFlag` at the end of `SimpleThreadOne` which means the execution halts and `SimpleThreadTwo` never finishes running (stuck in the while loop).

### q

Review the modified `ThreadRunner` class code below.  Compile and run the `ThreadRunner` main method. Describe the output. Does the execution halt? Does `SimpleThreadTwo` finish running? If it does finish running, why does adding the volatile keyword before the boolean `statusFlag` change the behavior of the code compared to the previous question?

```
$ javac ThreadRunner.java
$ java ThreadRunner
```

```java
/* ThreadRunner.java */
public class ThreadRunner {

    private static volatile boolean statusFlag = false;

    private static class SimpleThreadOne extends Thread {
        public void run() {
            for (int i = 1; i <= 2000; i++){
                System.out.println("Simple thread one counter - " + i);
            }
            // Change the status flag.
            statusFlag = true;
            System.out.println("Status flag changed to true in simple thread one.");
        }
    }

    private static class SimpleThreadTwo extends Thread {
        public void run() {
            int waitCounter = 1;
            while (!statusFlag) {
                waitCounter++;
            }
            System.out.println("Start simple thread two processing " + waitCounter);
        }
    }

    public static void main(String[] args) {
        SimpleThreadOne simpleThreadOne = new SimpleThreadOne();
        simpleThreadOne.start();
        SimpleThreadTwo simpleThreadTwo = new SimpleThreadTwo();
        simpleThreadTwo.start();
    }
}

```

#### s
The execution no longer halts and `SimpleThreadTwo` does finish running. Adding the `volatile` keyword to `statusFlag` tells the compiler/JVM such variable may be changed by things running on another thread and it cannot assume `statusFlag` will not be modified when running.

### q

A second price auction is an auction where the highest bidder only has to pay whatever the second highest bid was. For example if person A bids $1 and person B bids $2, then person B wins the auction, but only pays $1. Below are two classes that reflect this, `SecondPriceAuction` and `Bidder`.  

Review the code below.  What might happen if two threads call the `makeBid` method at the same time? How would you modify the code to protect the auction outcome?


```java
public class SecondPriceAuction {
    private int currentHighestBid = 0;
    private int secondHighestBid = 0;

    public int getSecondHighestBid() {
        return secondHighestBid;
    }

    public void makeBid(int amount) {
        if (amount > currentHighestBid) {
            secondHighestBid = currentHighestBid;
            currentHighestBid = amount;
        }
        else if(amount > secondHighestBid) {
            secondHighestBid = amount;
        }
    }
}
```

```java
import java.util.Random;

public class Bidder extends Thread {
    private SecondPriceAuction secondPriceAuction;
    private int maxBid;

    public Bidder(SecondPriceAuction secondPriceAuction, int maxBet) {
        this.secondPriceAuction = secondPriceAuction;
        this.maxBid = maxBet;
    }

    public void run() {
        try {
            Thread.sleep((new Random()).nextInt(100));
        } catch (InterruptedException e) {
            System.out.println("Interrupted while sleeping...");
        }

        secondPriceAuction.makeBid(maxBid);
    }
}
```

```java
public class AuctionRunner {
    public static void main(final String args[]) {
        SecondPriceAuction secondPriceAuction = new SecondPriceAuction();
        Bidder[] bidders = new Bidder[3];

        // Create new bidders.
        for (int i = 0; i < bidders.length; i++) {
            bidders[i] = new Bidder(secondPriceAuction, i + 1);
        }

        // Start bidding.
        for (int i = 0; i < bidders.length; i++) {
            bidders[i].start();
        }

        // Ensures all threads have finished before we print out the price
        for (Bidder bidder : bidders) {
            try {
                bidder.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted while joining a thread...");
            }
        }

        System.out.println("Final Price: $" + secondPriceAuction.getSecondHighestBid());
    }
}
```

#### s
Two thread call `makeBid()` at the same time will cause a race condition on variables `currentHighestBid` and `secondHighestBid`. To solve the issue, we should add the `synchronized` keyword to the `makeBid()` method as shown below:
```java
public synchronized void makeBid(int amount) {
    if (amount > currentHighestBid) {
        secondHighestBid = currentHighestBid;
        currentHighestBid = amount;
    }
    else if(amount > secondHighestBid) {
        secondHighestBid = amount;
    }
}
```
