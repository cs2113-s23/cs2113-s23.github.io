---
layout: default
permalink: /guides/vscode-java
title: VScode with Java
---

# Running VSCode with Java

> Note that for using Java we DO NOT USE a docker environment. You will run all your programs locally on your machine. 


1. Determine which version of the Java Developers Kit (JDK) is currently installed on your machine. Open a terminal/powershell/command-prompt (not in VScode) and check your Java version

   ```
java -version
```

2. If your version of Java is less than 11. [Install the latest version of Java](https://www.oracle.com/java/technologies/downloads/). (The latest version is 17, but you only need at least version 11)
 
   > Oracal also provides a handy [installation guide](https://docs.oracle.com/en/java/javase/17/install/), if you run into issues.

3. Install the Extension Pack for Java plugin for VS Code
![Visual Studio Image](/images/vscode-java-plugin.png)

4. In VSCode create a new folder, or open an existing folder.

5. Create a new file called *Hello.java* and populate the file with the hello world code.
```java
/*Hello.java*/
public class Hello {
    public static void main(String args[]){
        System.out.println("hello, world");
    }
}
```

6. Open a new terminal in vscode.

7. In the terminal window use **javac** to compile Hello.java, then use **java** to run Hello.
![VS Code javac Image](/images/vscode-javac.png)

# Updating your Git environment

Since you are no longer using the `ubuntu-vlab01` server, your git environment will need to potentially be reinstalled. 
## Windows
If you are working on Windows, we recommend that you set-up and use WSL, which you can find resources to do so in [this article](https://docs.microsoft.com/en-us/learn/modules/get-started-with-windows-subsystem-for-linux/). If you set-up WSL, then you can follow the steps below for Ubuntu. Otherwise, please refer to [this section](https://github.com/git-guides/install-git#install-git-on-windows)
## Mac
Git should already be installed on Mac. If not, it can be installed on Mac through a package manager like `Homebrew` with `brew install git`. However, to do this, you will need to install `Homebrew`, and you can find instructions [here](https://brew.sh/). Alternatives can be found [here](https://github.com/git-guides/install-git).
## Linux
For Linux and other variations, you can install from the command line with the included package managers. Instructions for the various package managers can be found [here](https://github.com/git-guides/install-git#install-git-on-linux).

# Other things to remember
There are a few other things that you need to remember to do before your environment is comparable to what you had prior.
- Regenerate your SSH key and add it to your Github account so you don't have to reauthenticate. For Linux and Mac, you can use the `ssh-keygen` utility as you previously did at the beginning of the semester. Non-WSL Windows users can do a [similar process](https://phoenixnap.com/kb/generate-ssh-key-windows-10)
- Ensure that the `Java` extension is downloaded for VScode