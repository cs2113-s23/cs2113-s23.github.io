---
layout: toc
permalink: lab/0
---

*View the video explainer for this lab on [youtube](https://youtu.be/JGwK5nwuAps)*


# Lab 0: Git Familiarization

The goal of this lab is to get you more comfortable with using `git`. You'll need this lab in order to submit your assignments this semester, which will always be done via `git`.

## Part 0: Creating a github account and installing software

You are **required** to have a github account for this class. 

### If you do not have a github account

You need to sign up for one :-) Not just for this class, but git is an industry standard used by just about any team that is writing code. Decide if you'd like to keep this git account for your entire career (don't worry, you can make your repos private!) or use one just for this class.

1. Go to https://github.com/ and enter your info to “Sign up for GitHub”.
2. Select a username and password -- your username is your github id
   * For your github id, choose something tasteful. Your name with a hyphen, like `firstname-lastname` could be a good choice, but choose something you like and represents you. However, remember that your github username is publicly-visible.
   * Note that for job interviews, you may want to advertise your github id, so don't choose something like `youallsuxor` ...
3. Use an email address you have access to and check frequently. When there are issues/updates to your submissions, you'll get an email. You may want to use your GW email address for this. You can change your email in the future when you graduate.

### If you do have a github account

Please go to your account and check that ...

> You are using an email address that you check often because you will get important notifications.


### Install Git on your system

You may already have `git` command installed on your system. But if not, you need to do so now. Here's how you do it in various contexts:

#### Mac

On Mac, you will need to install the command line tools. To see if the command line tools are installed, Open a terminal a try to use `git` by simply type

```
git
```

If it is installed, you'll see a usage info appear. If it is not installed, you will be prompted to install the command line tools. Do so. It will take about 5-10 minutes, but then you'll have all sorts of good stuff installed, including `git`.


#### Windows

On windows, you can install git from this [link](https://git-scm.com/download/win), but perhaps a better option for you is to install the Windows Subsystem Linux (WSL), which provies a full ubuntu/linux subsystem, as if it is a virtual machine.

This is a [great guide from Microsoft](https://docs.microsoft.com/en-us/windows/wsl/install-win10). Once you do so, open a WSL terminal and follow the linux guide above. 

#### Linux/Ubuntu

If you are running a linux/ubuntu system, you can simply type

```
sudo apt install git
```

For other linux distros, refer to your package manager, but the command will be similar for `yum` or `rpm`.

At this point you should also install some other packages that you may want for the class

```
sudo apt-get install gcc make manpages default-jdk valgrind libreadline-dev
```


### Extras for git

Setup an ssh-key for your github account. This is a huge time saver and much more secure than using a password. Check out this [guide](https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/connecting-to-github-with-ssh).

You can also generate an access token, which you could (should!) use in place of a password when using git's https access. Check out this [guide](https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/creating-a-personal-access-token).



## Part 1: Creating your first `git` repo and sharing it with the course staff

In order to submit code this semester, you will need to create a repository for each assignment, place your code in there, and upload it. You'll also need to give access to the grading staff. These items will be a part of every project's grading rubric. Let's try it out together for this assignment

### Notify us with your github username

Send an email to your grader identifying your github username, so they can associate that account with this course. 

### Creating a new git repo

There are many ways to do this, but we'll compromise between the github website and the command line. First, in your browser, log in to github.com with your account, click on the icon in the top right, select `Your Repositories`, and then pres the green `New` button in the top right to create a new repo.

Under `Repository name`, type `gitusername-lab0` but replace `gitusername` with your actual git username. Make sure you type it exactly as it is listed here, i.e. `-lab0` must appear after your username. 

Next, select the toggle button below to be `Private`.

Next, press the green `Create repository` button.

The page will load the homepage of your repo. Under the `Settings` tab at the top, click on `Collaborators` on the left hand menu, click the green `Add people` button, and add the following collaborators (the grader):
`TBA`

This will send an invite (that experies in seven days) to your grader(s). They'll be monitoring their inboxes for these items. This also means you should email the graders first if you have grading questions; the professor is not automatically added to your submissions.

## Part 2: Checking out your repo

Once you have your new repo set up, click back on the `Code` tab, and copy the URL in the bar at looks similar to `https://github.com/kdobolyi/kdobolyi-lab0.git`

In your terminal, navigate to a folder of your choosing, and type (replacing it with your own URL):
`git clone https://github.com/kdobolyi/kdobolyi-lab0.git`

This will make a local copy of the (empty) repo in your current directory in a folder called `kdobolyi-lab0` (in my case -- yours will be different).

## Part 3: Adding code to your repo

Next, copy and paste the following Java code into a file in your repo's folder, and call it `HelloWorld.java`:

```
public class HelloWorld{
   public static void main(String[] args){
      System.out.println("Hello World!");
   }
}
```

### Tagging a file to be added to your repo

Even though `HelloWorld.java` is in your folder, it is not a part of your repo until you let the server know it exists, and push it to github. This is a three step process. First, we will formally flag this file to be added to the repo with:
`git add HelloWorld.java`

### Commiting your changes

Next, we need to tell git that we're ready to commit to these changes. You can ask it to commit all recent changes with the following command, which also requires a human-readable message:
`git commit -a -m "my first attempt"`

The `-a` flag is telling git to grab all the updated files, and the `-m` flag stands for the required message between quotes.

### Pushing your changes

All this is great, but it's only flagging this on your local copy of the files; none of this is on the github server yet. To get it there, you have to push your changes with the command:
`git push`

If everything goes through smoothly, you can refresh the website of your repo, and you'll see your `HelloWorld.java` file there. If not, find a TA to help you.

## Part 4: Making a change

Finally, we're going to update the file we just pushed to git. Change the message in `HelloWorld.java` to include your name. Then, go through the steps above to commit and push those changes (you don't need to add the file to the repo, because it is already there -- only new files need to be added, and only once).

Make sure that when you refresh the browser, you can see your changes there.

# Grading Rubric

|Item | Points |
|github username has been emailed to the grader | 10 |
|the name of the repo for this lab matches the pattern  `gitusername-lab0` | 10 |
|the grader has been added as a collaborator to the repo | 10 |
|the repo has been made private | 10 |
|there is an initial commit of `HelloWorld.java` | 20 |
|the initial commit has a useful message | 10 |
|there is a second commit where `HelloWorld.java` has been correctly modified to include the student's name/username | 20 |
|the second commit has a unique, useful message | 10 |
| TOTAL | 100 |