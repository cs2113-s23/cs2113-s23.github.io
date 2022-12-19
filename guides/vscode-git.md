---
layout: default
permalink: /guides/vscode-git
title: Setting up VS Code
---

# Using VSCode with Git and SSH to Complete/Submit a programming Assignment

> Before starting, you should have completed the [VScode Setup Guide with SSH](guides/vscode-ssh). This guide assume you have VScode and SSH capabilities installed, as well as the VSCode extension Remote Development Extension. 


1. Accept the github classroom assignment and follow the link to the repository. For example, if you are working on [Lab 1](/lab/1), after accepting the assignment, you'll have a new repository named `lab-1-username` where `username` is your github username. My github username is `adamaviv`, so you will see that in the screenshots below.

2. Copy the repository link by clicking the `Code` label

   ![Github Repo Link](/images/vscode-git-repo.png)

   If you have setup an ssh key, use ssh, otherwise use HTTPS.
   
   To setup an ssh key, see this [guide](https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/connecting-to-github-with-ssh)

3. Open up a new window in VScode via `File->New Window` or &#8984;&#8679;N / Ctrl&#8679;. Then click the explorer and then `Clone Repository`

   ![VScode New Window, Explore, Clone Repository](/images/vscode-explorer-clone-repo.png)

   > If you don't see an option to `Clone Repository` then you may not have `git` installed. Follow this [link](https://git-scm.com/download) to install it for your computer. 
   
4. Enter the repo link in the command pallet and confirm the file location you want to cloen the repository to. 

   ![Enter repo link](/images/vscode-clone-repo-link.png)
   ![Confirm Location](/images/confirm_file_location.PNG)
   


5. You will be prompted where to save the repo. Save it wherever you want, but I like to create a folder called `git` or `vscode` to save all my projects. 

6. Go ahead and open the repo in this window by selecting `Open`

   ![Open in this window](/images/vscode-open.png)




7. Once complete, you'll have your workspace ready to go and integrated terminal open.

   ![Init Complete](/images/vscode-init-complete.png)

8.  Do your work. And once done, you need to commit your work. Start by clicking on the repo symbol

    ![Repo symbol](/images/vscode-repo-symbol.png)

9.  Add the modified files (or new files) to be staged for commit by pressing the `+`

    ![add files to the commit](/images/vscode-git-add.png)

10. Click the &#10003; to perform the commit 

11. Finally, push your changes to github by clicking the `...` at the top and selecting push

    ![Push changes](/images/vscode-git-push.png)

12. Finally, finally, you should go back to github as a final check to make sure you pushed all your changes.

## Using the git command line in the integrated terminal

You can also use the command line to do the git commands. 


```
git add file.txt        #add a file to the commit
git commit -m "message" #perform the commit with the commit message
git push                #push the commit to github 
```

If you want to add all the files that have been modified and commit all at once, use the following

```
git commit -a -m "message" 
```

## Having an issue with ssh?

If you find that your ssh key is not available in the container, you may need to add your private key to the `ssh-agent`.

VScode has a [good guide](https://code.visualstudio.com/docs/remote/containers#_sharing-git-credentials-with-your-container) explaining how to help with that

## Asking you to set up your git profile?

If you're getting a error saying you need to setup your name and email, then open the integrated terminal and type these two commands


   ```
   git config --global user.name "John Doe"
   git config --global user.email johndoe@example.com
   ```
   
Where `John Doe` is replaced with your name and `johndoe@example.com` is replaced with the email address you used to sign up to github
