---
layout: default
permalink: /guides/docker-wsl-vscode-troubleshoot
title: Troubleshooting Docker, WSL, VSCode
---

# **Docker/VSCode/WSL Issues**
>NOTE: This is not a required or mandatory step for this class. WSL is a utility for Windows that allows a Linux-based workflow. If you want more information on WSL, please read [this article](https://docs.microsoft.com/en-us/learn/modules/get-started-with-windows-subsystem-for-linux/).

> Before starting, you should have attempted the [VScode Setup Guide with Docker](guide/vscode). This page will provide common troubleshooting steps when attempting to get Docker working within VSCode and WSL. WSL can be used to emulate Linux if you are more familiar with that workflow. *It is not required that you use WSL!* This page assumes that you have Docker's desktop client already installed.

> If you are unable to get WSL working in a timely fashion, please just refer to the guide above, and priortize having a functional setup for class. Further assistance can be found within Hunter's office hours.

>This guide also assumes that you have downloaded your relevant repository within your WSL filesystem, as well as set up the necessary utilities within WSL (Git, mainly). It is important to note that there is a *big* difference in pulling a Git repository in your Windows filesystem, and within the WSL filespace. If you need more guidance 
<hr>


### **Step 0: I don't have WSL!**
>Scenario: *I don't have WSL, and I'm not sure where to find it.*

### **Solution**
You can find a rather verbose installation guide for WSL on [Microsoft's website](https://docs.microsoft.com/en-us/windows/wsl/install-win10#manual-installation-steps). It will guide you through installation, and provide the commands you can just copy and paste.

If you are stuck and don't know what distribution of Linux to use, Ubuntu 20.04 is an easy one to work with, and has plenty of support and troubleshooting forums on sites such as StackOverflow.
<hr>

### **Step 0.5: WSL doesn't work with VSCode!**
>Scenario: *I've installed VSCode, but it doesn't just work.*

### **Solution**
You can find another guide for this problem on [VSCode's website](https://code.visualstudio.com/docs/remote/wsl)

<hr>

### **Step 1: There's a pop-up, but I get an error!**
>Scenario: *I've installed VSCode and WSL. If I open VSCode with the assignment, Docker won't open.*
   
If the error message appears as below, it means that WSL2 has to be set up, and you are on an older version of WSL. You will need admin priviliges on your computer in order to make further changes and solve this problem (i.e. you need to be using your own computer).

![Error Message](/images/wsl_docker_vscode_container_error.PNG)


### **Solution**
A) Open a Windows Powershell window (can be done on the Windows searchbar) and enter 
```
wsl -l -v
```
You may see less instances on your own machine, but you should see a WSL instance that does not have Docker in the name (Ubuntu in the image below, yours may vary).

![Powershell Distributions Listing](/images/powershell_distributions.PNG)

Make a note of the distribution. The asterisk to the left of the distribution indicates the currently active distribution, it isn't necessarily indicative of the one you are wanting. 

B) You will now need to change the version of this WSL instance with 
```
wsl --set-version [Distribution Name] 2
```
You should substitute the relevant distribution name in place of the brackets (but do not include the brackets in your final command). For example, my command would look like
```
wsl --set-version Ubuntu 2
```

After this, the underlying filesystem will be converted, and it will take a few minutes. 

C) Next, open up Docker Desktop and go to the settings (the cog in the top right of the window). You should select the option that enables WSL backend support. Ensure that you apply the changes on this screen!

![Docker Desktop Enable WSL](/images/docker_wsl_suppport.PNG)

You should then click Resources on the left list of options, and the click "WSL Integration" to enable your selected distribution. Also ensure that you have checked the  that enables integration.

![Docker WSL Integration](/images/docker_wsl_integration.PNG)

Make sure you  hit Apply and Restart! 

After this, it should work. If not, you may need to enable CPU Virtualization in your BIOS settings. More details can be seen in Problem 1.
<hr>

### **Step 2: There's no Docker Pop-Up!**
>Scenario: I open VSCode, and I do not get a pop-up that lets me open the Docker container in VSCode.

Normally this will open up in the bottom right of the screen and *should* appear like the box below. ![Github Repo Link](/images/vscode_docker_popup.PNG) 

### **Solution**
A.) You should first ensure that you have opened up a WSL window within VSCode by pressing F1, searching for "WSL", and then select the "Remote-WSL: New WSL Window" option. This gives you a VSCode window that is connected to your WSL filesystem. ![New WSL Window](/images/new_wsl_window.PNG)

B.) Reopen the window that contains the Docker container by going to File -> Open Folder... and selecting the relevant folder. This assumes that you have cloned the relevant git repository (your assignment repository) into your WSL system. This will require that you install Git within WSL (sudo apt install git-all), and have completed set-up of Git on your own within WSL.

![Open Folder](/images/open_folder_vscode.PNG)

C.) The pop-up should reappear, and you should be able to attempt to open the Docker container.
<hr>


# Troubleshooting



### **Problem 1: I've installed WSL, but it won't launch!**
>Scenario: *I've completed the installation of WSL with Microsoft's guide, and double checked that I followed all the steps. WSL still won't launch.*

### **Solution**
This can sometimes occur when CPU virtualization is turned off. Course staff is still working on further diagnosing this issue as well. One fix is changing this setting in your computer's BIOS settings.

You can access your computers BIOS settings by shutting it down, pressing the power button, and pressing the key that is indicated on the bootup screen. This text might be formatted in a manner similar to
```
Press F12 to enter BIOS settings
```
The specific key that you have to press can vary based on architectures or laptop manufacturer. 

Once you enter the BIOS settings, you should find a way to enter into Advanced Settings. From there you are looking for a setting that relates to CPU Virtualization. Sometimes this is called SVM mode as well.

This step can take some trial and error, and requires time and patience. If you need further help with this, please either post on Ed or come to office hours so we can help you further.

<hr>

### **Problem 18,446,744,073,709,551,615 : My problem isn't listed here!**
>Scenario: *I don't see a problem that I am encountering listed on this webpage.*

### **Solution**
Please post a question on the class Ed page, and we will help you as soon as possible. Be sure to be detailed in your post about what you have tried, what you are hoping to do, and what the exact problem is. Screenshots help us help you much faster!
<hr>


















