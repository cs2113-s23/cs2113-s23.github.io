---
layout: worksheet
permalink: /worksheet/j4
showsolution: true
---

# Worksheet: J4

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.

* Github Classroom Link: [https://classroom.github.com/a/_Q-r6-OP](https://classroom.github.com/a/_Q-r6-OP)

## Note

Attempt to answer these questions before running the code. This will improve your ability to analyize and reason about code without an IDE or compiler. This skill we be helpful on the exams.

## Questions

### q
How does object-oriented programming pair so closely with GUIs?
#### s
GUIs have a lot of components that have similar interfaces and jobs, but may have slightly different functionality. For example we might have an exit button, and another such as a submit button, which you can only click when certain things are done. Both of these are buttons, are displayed the same, but have different functionality. We see then that if both of these buttons inherit from a parent Button class, then we won't have much extra code to write beyond what they do when they're clicked. So we see that any part of a GUI is going to be an object, and that we will end up using a lot of inheritance, which is why OOP is so closely tied to GUIs.

### q

What is the relationship between `WindowListener` and `WindowAdapter`?

#### s
`WindowListener` is an interface with methods that correspond to any event that might happen to a window, such as opening, closing, or minimizing it. `WindowAdapter` on the other hand is an abstract class that implements `WindowListener, WindowStateListener, WindowFocusListener`. The main difference we see then is that `WindowAdapter` is essentially a combination of all possible ways to interact(interface :) )  with a window, and so we know that if we have something that is a window, we can inherit from this parent class, indicating it's a window, instead of simply implementing all the interfaces separately. High level the reasoning behind this is as follows: we have many ways to interface with a window, but these overlap with other non-window objects(such as menus), so not everything that implements a subset of these interfaces is a window, but if you implement them ALL the object is a window. So we put all these interfaces in an abstract class so we can say something IS a window, by inheriting it, but we don't only put them in an abstract class as some non-window objects may have similar interfaces.

A side philosophy side tangent: a lot of times we can learn more about something based on how it interacts with things, then its actual definition. This is a common thing in most sciences, usually we don't care about what something is, only how it interacts with other things, to the point a lot of definitions are actually based on how things interact with other things, not just what they are. We see here this is a great example, we're defining what a window is by how it can be interacted with, not what it "is".


### q

Go to the Java docs for [`WindowAdapter`](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/event/WindowAdapter.html)

What other kinds of listening does it do other than just those in `WindowListener`?


#### s
`WindowStateListener`: called whenever a window changes state, such as from minimized to active, or active to closed.

`WindowFocusListener`: Called when a window is focused, or not focused.


### q
Assume we define a class as below:
```java
class ParentJFrame extends JFrame {
    private JFrame[] frames = new JFrame[5];

    public ParentJFrame() {
        for (int i = 0; i < 5; i++) {
            JFrame f = new JFrame();
            f.setTitle("Worksheet Child" + i);
            f.setSize(300, 300);
            // Make windows appear on a diagonal line
            f.setLocation(100 * i, 100 * i);
            f.addWindowListener(new ChildAdapter());
            f.setVisible(true);
            frames[i] = f;
        }
        this.addWindowListener(new ParentAdapter());
    }

    private static class ChildAdapter extends WindowAdapter {
        // Called when window closes
        @Override
        public void windowClosing(WindowEvent e) {
            System.out.println("Closed!");
        }

    }
    
    private static class ParentAdapter extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            System.out.println("Parent Closed!");
        }

    }

}
```
What would be the output of the code below if we close the `ParentJFrame` immediately after it appears?
```java
public static void main(final String args[]) {
        ParentJFrame frame = new ParentJFrame();
        frame.setTitle("Quiz Parent");
        frame.setSize(100, 100);
        frame.setLocation(100, 100);
        frame.setVisible(true);
    }
```


#### s
We see the output is "Parent Closed!", but the children stay open

### q
If we close all children windows instead of the parent, how does that change the output?

#### s
We will see "Closed!" printed 5 times, and the parent stays open.

### q
Assume we add this line to the end of `main` to the above program and then close the parents. How does this change the behaviour of the program?
```java
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // makes it so that closing window exits program
```
#### s
We see "Parent Closed!" and all frames close and the program stops.

### q
Now consider the case in which we close the children windows, and then the parent (from question 4). What would the output be in this case?
#### s
"Closed!" 5 times, then parent closed, and the program will stop.

#### q
Consider if we define the constructor of `ParentJFrame`  instead as:
```java
    public ParentJFrame() {
        for (int i = 0; i < 5; i++) {
            JFrame f = new JFrame();
            f.setTitle("Quiz " + i);
            f.setSize(100, 100);
            f.setLocation(100 * i, 100 * i);
            f.addWindowListener(new ChildAdapter());
            f.setVisible(true);
            // Line that was added
            // *******
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // makes it so that closing window exits program
            // *******
            frames[i] = f;

        }
        this.addWindowListener(new ParentAdapter());
    }
```
If we only close the child window, what is the output?
#### s
"Closed!" and the program will stop and all windows will close

### q
What does the program below produce for a GUI? (You can sketch and upload an image or describe it -- do this without running the program to make sure you understand what each line below is doing).
```java
 // For reference
    /*        N
    *         |
    *      W--|--E
    *         |
    *         S
    */
    public static void main(final String args[]) {
        JFrame frame = new JFrame();

        JButton bOne = new JButton("1");
        JButton bTwo = new JButton("2");
        JButton bThree = new JButton("3");
        JButton bFour = new JButton("4");
        JButton bFive = new JButton("5");
        JButton bSix = new JButton("6");

        JPanel primes = new JPanel();
        JPanel composites = new JPanel();

        primes.setLayout(new BorderLayout());
        composites.setLayout(new BorderLayout());

        primes.add(bTwo, BorderLayout.EAST);
        primes.add(bThree,BorderLayout.WEST);
        primes.add(bFive,BorderLayout.NORTH);

        composites.add(bFour, BorderLayout.NORTH);
        composites.add(bSix, BorderLayout.CENTER);
 
        frame.add(primes, BorderLayout.WEST);
        frame.add(composites, BorderLayout.EAST);
        frame.add(bOne, BorderLayout.CENTER);
        frame.pack();
        frame.setTitle("Quiz J4-2");
        frame.setSize(400, 400);
        frame.setLocation(100, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
```
#### s
![](/images/J4-9.png)

### q
What is the output of the below program if we click the `Multiply` window's `Calculate` button?
```java
class SimpleMultiplier extends JFrame {
    public SimpleMultiplier(JButton button) {
        this.add(button, BorderLayout.CENTER);
        this.setTitle("Multiply");
        this.setSize(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static int calculation(int a, int b) {
        return a * b;
    }
}

class SimpleAdder extends JFrame {
    public SimpleAdder(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(calculation(4, 2));
            }
        });
        this.add(button, BorderLayout.CENTER);
        this.setTitle("Add");
        this.setSize(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static int calculation(int a, int b){
        return a + b;
    }
}

```
```java
class Main {
    public static int calculation(int a, int b) {
        return a / b;
    }
    public static void main(final String args[]) {
        JButton multiplyButton = new JButton("Calculate!");
        multiplyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(calculation(4, 2));
            }
        });
        JButton addButton = new JButton("Calculate!");
        SimpleMultiplier multiplier = new SimpleMultiplier(multiplyButton);
        SimpleAdder adder = new SimpleAdder(addButton);
        multiplier.setVisible(true);
        adder.setVisible(true);
    }
}
```
#### s
Since we make the button's action listener for `SimpleMultiplier` in `Main`, that means it will use the `calculation` function from `Main`, not `SimpleMultiplier`. The output then will be 2.

### q
From the previous question, what would happen if we instead had clicked the `Add` window's `Calculate` button?

#### s
We get 6, following similar logic from the last question, except now the action listener was made in `SimpleAdder`.

### q

Consider the following Java swing GUI

```java
public class RedPillBluePill extends JFrame {
    JLabel label;

    public RedPillBluePill() {
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());        
        JButton red = new JButton("red");
        JButton blue = new JButton("blue");
        panel.add(red, BorderLayout.EAST);
        panel.add(blue, BorderLayout.WEST);
        label = new JLabel("click a button");
        this.add(label, BorderLayout.NORTH);
        this.add(panel, BorderLayout.SOUTH);

        red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                label.setText("RED");        
            }
        });

        blue.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                label.setText("BLUE");
            }

        });
    }
}
```

Convert the `ActionListener`s to Lambda Functions.

#### s
```java
red.addActionListener((e) -> {label.setText("RED");});
blue.addActionListener((e) -> {label.setText("BLUE");});
```


### q

Explain why for `ActionListener` you can use a Lambda function but for `WindowListener` you cannot?


#### s
`WindowListener` has multiple methods that must be implemented, `ActionListener` only one, and lambda functions can only represent one.

### q

Write a program that allows you to enter a 6-digit PIN, like you would on your smartphone to unlock it. It should have the following layout:

```

 [ DISPLAY PIN AS TYPED ]

   [ 1 ]  [ 2 ] [ 3 ] 
   
   [ 4 ]  [ 5 ] [ 6 ]    
   
   [ 7 ]  [ 8 ] [ 9 ]       

   [ < ]  [ 0 ] 

````

Where `[ < ]` is a "backspace" button. The display should show the PIN as it is typed, and when the user enters the PIN 202113, the display changes to "YOU MAY ENTER!"

#### s

``` java
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

class Pin extends JFrame {
    public Pin() {
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("");
        JPanel panelA = new JPanel(new BorderLayout());
        JPanel panelB = new JPanel(new BorderLayout());
        JPanel panelC = new JPanel(new BorderLayout());
        JPanel panelD = new JPanel(new BorderLayout());
        JButton one = new JButton("[1]");
        one.addActionListener((e) -> {
            label.setText(label.getText() + "1");
        });
        panelA.add(one, BorderLayout.WEST);
        JButton two = new JButton("[2]");
        two.addActionListener((e) -> {
            label.setText(label.getText() + "2");
        });
        panelA.add(two, BorderLayout.CENTER);
        JButton three = new JButton("[3]");
        three.addActionListener((e) -> {
            label.setText(label.getText() + "3");
        });
        panelA.add(three, BorderLayout.EAST);
        JButton four = new JButton("[4]");
        four.addActionListener((e) -> {
            label.setText(label.getText() + "4");
        });
        panelB.add(four, BorderLayout.WEST);
        JButton five = new JButton("[5]");
        five.addActionListener((e) -> {
            label.setText(label.getText() + "5");
        });
        panelB.add(five, BorderLayout.CENTER);
        JButton six = new JButton("[6]");
        six.addActionListener((e) -> {
            label.setText(label.getText() + "6");
        });
        panelB.add(six, BorderLayout.EAST);
        JButton seven = new JButton("[7]");
        seven.addActionListener((e) -> {
            label.setText(label.getText() + "7");
        });
        panelC.add(seven, BorderLayout.WEST);
        JButton eight = new JButton("[8]");
        eight.addActionListener((e) -> {
            label.setText(label.getText() + "8");
        });
        panelC.add(eight, BorderLayout.CENTER);
        JButton nine = new JButton("[9]");
        nine.addActionListener((e) -> {
            label.setText(label.getText() + "9");
        });
        panelC.add(nine, BorderLayout.EAST);
        JButton zero = new JButton("[0]");
        zero.addActionListener((e) -> {
            label.setText(label.getText() + "0");
        });
        panelD.add(zero, BorderLayout.WEST);
        JButton back = new JButton("[<]");
        back.addActionListener((e) -> {
            label.setText(label.getText().substring(0,
                    label.getText().length() - 1 > 0 ? label.getText().length() - 2 : 0));
        });
        panelD.add(back, BorderLayout.CENTER);
        JPanel middle = new JPanel(new BorderLayout());
        JPanel bottom = new JPanel(new BorderLayout());
        middle.add(label, BorderLayout.NORTH);
        middle.add(panelA, BorderLayout.CENTER);
        middle.add(panelB, BorderLayout.SOUTH);
        bottom.add(panelC, BorderLayout.NORTH);
        bottom.add(panelD, BorderLayout.CENTER);
        this.add(middle, BorderLayout.NORTH);
        this.add(bottom, BorderLayout.CENTER);
        label.addPropertyChangeListener("text", (e) -> {
            if (label.getText().equals("202113")) {
                label.setText("YOU MAY ENTER");
            }
        });
    }

    public static void main(String[] args) {
        Pin p = new Pin();
        p.setVisible(true);
    }
}
```

### q

For the above program you wrote above, add a new feature. This could be to allow variable length PINs, match different PINs, allow users to select a PIN and then confirm it later, etc.

Describe your extension. 

#### s
Answers may vary!


### q

Add a feature were you listen for key strokes, like '1' or '2' for entering the PIN, and allow the user to either type or use the mouse

#### s

For this you can use a [KeyListener](https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html)


### q

Add a secret PIN that get's checked. If the user guesses the PIN, have your GUI change in some interesting way, such as change colors, or something else visual

#### s

Answers may vary


