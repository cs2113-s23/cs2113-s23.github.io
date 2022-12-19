---
layout: worksheet
permalink: /worksheet/j6
showsolution: false
---

> This worksheet is out of date and will need to be updated

# Worksheet: J6

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.

* Github Classroom Link: TBA

## Questions


### q
Why is Exception and Error handling important in relation to GUIs and writing an application that has an abstraction of user input to other threads?

#### s

### q
To help you in the next few questions, briefly describe how a `mouseReleased` event differs from a `mousePressed` event. Are both of these events happening equivalent to 2 mouse clicks?

[Hint: The documentation can help](https://docs.oracle.com/javase/7/docs/api/java/awt/event/MouseListener.html)
#### s
`mouseReleased` is invoked when a mouse button is released while `mousePressed` is invoked when a mouse button is pressed. Therefore, if `mouseReleased` happened before `mousePressed` then the mouse has been pressed for a 2nd time (released indicate 1st press). If `mousePressed` happened before `mouseReleased` then it is equivalent to 1 mouse click.

### q
Write a program that creates a window, and draws a rectangle that is your favorite color. Extend this program so that the rectangle will rotate 90 degrees clockwise when it is left-clicked.

Hint 1: A Graphics2d object has built in functions for rotation. [This](https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html) might help you find the function.

Hint 2: The rotate function requires the rotation metric to be specified in a particular measurement (found in the documentation). `Math.toRadians()` might be of help

#### s
```java
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Q3 extends JFrame {

    public Q3() {
        super();

        JComponent c = new Q3_Component();

        this.add(c);
        this.pack();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class Q3_Component extends JComponent {

        int x = 300, y = 300; // initial position
        final int dw = 800, dh = 800; // width/height of component
        final int sidelen = 100; // width/height of square

        int dir = 0;
        int left_bound = x;
        int upper_bound = y;

        Color myColor = Color.BLUE;

        public Q3_Component() {
            super();
            setPreferredSize(new Dimension(dw, dh));

            // Mouse Listener only listens for mouse events
            // which happens once everytime an input(event) happens
            this.addMouseListener(new MouseInputAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();

                    rotate(x, y);
                }
            });
        }

        // Change the direction of the square and repaint component
        private void rotate(int x, int y) {
            if (withinObj(x, y)) {
                this.dir = (this.dir + 1) % 4;

                switch (this.dir) {
                    case 0:
                        this.left_bound = this.x;
                        this.upper_bound = this.y;
                        break;
                    case 1:
                        this.left_bound = this.x - sidelen;
                        this.upper_bound = this.y;
                        break;
                    case 2:
                        this.left_bound = this.x - sidelen;
                        this.upper_bound = this.y - sidelen;
                        break;
                    case 3:
                        this.left_bound = this.x;
                        this.upper_bound = this.y - sidelen;
                        break;
                }

                this.repaint();
            }
        }

        // Judge whether input coordinates x and y are within our object (square)
        private boolean withinObj(int x, int y) {
            if (x < this.left_bound + sidelen && x > this.left_bound && y < this.upper_bound + sidelen
                    && y > this.upper_bound) {
                        return true;
                    } else {
                        return false;
                    }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.rotate(dir * Math.PI / 2, x, y);

            // Drawing rectangles, defining colors, overlap
            g2.setColor(myColor);
            g2.fillRect(x, y, sidelen, sidelen);
        }

    }

    public static void main(String[] args) {
        Q3 f = new Q3();
        f.setVisible(true);
    }
}
```

### q
Extend your program from the previous question by adding the following functionalities:

    * When your mouse goes  within the bounds of the object, it should change to a different color than it originally is.
    * When your mouse exits the bounds of the object it should revert to the original color

#### s
```java
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Q4 extends JFrame {

    public Q4() {
        super();

        JComponent c = new Q4_Component();

        this.add(c);
        this.pack();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class Q4_Component extends JComponent {

        int x = 300, y = 300; // initial position
        final int dw = 800, dh = 800; // width/height of component
        final int sidelen = 100; // width/height of square

        int dir = 0;
        int left_bound = x;
        int upper_bound = y;

        Color myColor = Color.BLUE;

        public Q4_Component() {
            super();
            setPreferredSize(new Dimension(dw, dh));

            // Mouse Listener only listens for mouse events
            // which happens once everytime an input(event) happens
            this.addMouseListener(new MouseInputAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();

                    rotate(x, y);
                }
            });

            // Mouse Motino Listener listens for movements of the mouse constantly
            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();

                    mouseEnter(x, y);
                }
            });
        }

        // Change the direction of the square and repaint component
        private void rotate(int x, int y) {
            if (withinObj(x, y)) {
                this.dir = (this.dir + 1) % 4;

                switch (this.dir) {
                    case 0:
                        this.left_bound = this.x;
                        this.upper_bound = this.y;
                        break;
                    case 1:
                        this.left_bound = this.x - sidelen;
                        this.upper_bound = this.y;
                        break;
                    case 2:
                        this.left_bound = this.x - sidelen;
                        this.upper_bound = this.y - sidelen;
                        break;
                    case 3:
                        this.left_bound = this.x;
                        this.upper_bound = this.y - sidelen;
                        break;
                }

                this.repaint();
            }
        }

        // Mouse enter method
        private void mouseEnter(int x, int y) {
            if (withinObj(x, y)) {
                myColor = Color.RED;
                this.repaint();
            } else {
                myColor = Color.BLUE;
                this.repaint();
            }
        }

        // Judge whether input coordinates x and y are within our object (square)
        private boolean withinObj(int x, int y) {
            if (x < this.left_bound + sidelen && x > this.left_bound && y < this.upper_bound + sidelen
                    && y > this.upper_bound) {
                        return true;
                    } else {
                        return false;
                    }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.rotate(dir * Math.PI / 2, x, y);

            // Drawing rectangles, defining colors, overlap
            g2.setColor(myColor);
            g2.fillRect(x, y, sidelen, sidelen);
        }

    }

    public static void main(String[] args) {
        Q4 f = new Q4();
        f.setVisible(true);
    }
}
```

### q 
Animate your rectangle's rotation so that the rotation is visible. This can be done with multiple calls to rotation with a small rotation angle.
#### s
```java
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Q5 extends JFrame {

    public Q5() {
        super();

        JComponent c = new Q5_Component();

        this.add(c);
        this.pack();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class Q5_Component extends JComponent {

        int x = 300, y = 300; // initial position
        final int dw = 800, dh = 800; // width/height of component
        final int sidelen = 100; // width/height of square

        double dir = 0;
        int left_bound = x;
        int upper_bound = y;

        Color myColor = Color.BLUE;

        Q5_Thread myThread;

        public Q5_Component() {
            super();
            setPreferredSize(new Dimension(dw, dh));

            // Mouse Listener only listens for mouse events
            // which happens once everytime an input(event) happens
            this.addMouseListener(new MouseInputAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();

                    rotateAnimation(x, y);
                }
            });

            // Mouse Motino Listener listens for movements of the mouse constantly
            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();

                    mouseEnter(x, y);
                }
            });
        }

        // Change the direction of the square and repaint component
        private void rotate(int num_rotation) {
            dir = Math.round(dir * num_rotation + 1) / (double)num_rotation % 4;
            this.repaint();
        }

        // Mouse enter method
        private void mouseEnter(int x, int y) {
            if (withinObj(x, y)) {
                myColor = Color.RED;
                this.repaint();
            } else {
                myColor = Color.BLUE;
                this.repaint();
            }
        }

        // Judge whether input coordinates x and y are within our object (square)
        private boolean withinObj(int x, int y) {
            if (x < this.left_bound + sidelen && x > this.left_bound && y < this.upper_bound + sidelen
                    && y > this.upper_bound) {
                return true;
            } else {
                return false;
            }
        }

        // Create and start a thread for the rotation animation
        private void rotateAnimation(int x, int y) {
            if (withinObj(x, y)) {
                // Here dir is incremented by 1 because left_bound
                // and upper_bound are for the next position
                switch ((int)((this.dir+1) % 4)) {
                    case 0:
                        this.left_bound = this.x;
                        this.upper_bound = this.y;
                        break;
                    case 1:
                        this.left_bound = this.x - sidelen;
                        this.upper_bound = this.y;
                        break;
                    case 2:
                        this.left_bound = this.x - sidelen;
                        this.upper_bound = this.y - sidelen;
                        break;
                    case 3:
                        this.left_bound = this.x;
                        this.upper_bound = this.y - sidelen;
                        break;
                }

                myThread = new Q5_Thread(this);
                myThread.start();
            }            
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.rotate(dir * Math.PI / 2, x, y);

            // Drawing rectangles, defining colors, overlap
            g2.setColor(myColor);
            g2.fillRect(x, y, sidelen, sidelen);
        }

    }

    private class Q5_Thread extends Thread {
        Q5_Component d;
        int num_rotation = 1000; // 1000 frames for the entire rotation

        public Q5_Thread(Q5_Component d) {
            this.d = d;
        }

        public void run() {
            // Realizing animation by performing a certain
            // amount of rotation on object.
            for (int i = 0; i < num_rotation; i++) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }
                d.rotate(num_rotation);
                d.repaint();
            }
        }
    }

    public static void main(String[] args) {
        Q5 f = new Q5();
        f.setVisible(true);
    }
}
```

### q
Did you experience any challenges with your object actually updating to the changes? If so, what were they and how did you solve them? If not, what mechanism in your code was responsible for ensuring it updated?

#### s
There may be other approaches to solving the above problems. I used a thread to realize the animation and a MouseMotionListener to constantly listen for the position of the mouse.

### q
As a reminder, the `InputStream` class is useful for byte-by-byte reading, or reading a group of bytes. The `Reader` class works on characters and groups of characters, and the Scanner class allows for token-based reading. 

For each class, briefly describe a program in which you would use that class, and what functionality would be enabled by choosing that class specifically. Be as specific as possible.

#### s
When taking input from the output of another machine, `InputStream` is usually better since the output is often uniformly in bytes. However, when taking input from the user, `Reader` is often better since groups of characters are usually the input and makes more sence from a user oriented perspective.


### q
Imagine that you wanted to reverse-engineer some piece of malware that you know was compiled with `c`. You know the file you are being asked to reverse-engineer contains a sequence of bytes that is present in all versions of such malware. The signature can be initialized with 
```java
byte[] signature = "while(file_exists){delete_file();}".getBytes()
```

Sketch out the beginning of a Java program that would take an executable file `evil_malware`, and be able to read it in to check for the signature.

Note: Here we are assuming that we used the `-XX:+UseCompressedStrings` flag for our JMV so that our `char`s can be in the same format as `c` strings.
#### s
```java
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Q8 {
    public static void main(String args[]) {
        byte[] signature = "while(file_exists){delete_file();}".getBytes();

        FileInputStream myInputStream;

        try {
            myInputStream = new FileInputStream("evil_malware.exe");

            byte[] data = new byte[signature.length];

            // The read function returns the number of successful bytes read
            int numBytes = myInputStream.read(data, 0, signature.length);

            // if numBytes is -1 means no bytes were read
            while (numBytes != -1) {
                if(Arrays.equals(data, signature)) {
                    System.out.println("Malware Found");
                    break;
                }

                numBytes = myInputStream.read(data, 0, signature.length);
            }

            myInputStream.close();
        } catch (IOException e) {
            System.out.println("File not found");
            return;
        }
    }
}
```

### q
If you were writing a web scraper that needed to download a rather large file, which of the input methods that we have discussed would you extend to fulfill your needs? Assume you are working on a machine in which the network speed allowed you to download at a faster rate than you can write to disk, and your working memory avialable to your program is not enough to store the whole file in memory before writing. Describe your implementation at a high level, ensuring that you describe specific functionality your program would have to solve this problem.

#### s
I would use a `InputStream` that reads byte by byte from the file and at the same time write to my disk. Since the network speed is faster than writing speed, there will be a buffer that stores a certain amount of bytes from the input and write to the disk whenever it is ready.
