---
layout: default
permalink: /guides/vscode-ssh
title: VScode with SSH client
---

# Running VSCode with SSH via `ubuntu-vlab01.seas.gwu.edu`

> You can use this guide to instead develop using `ssh` connection to `ubuntu-vlab01.seas.gwu.edu` (the SEAS linux server). 
>

1. Install Visual Studio Code (VS Code)
[https://code.visualstudio.com/download](https://code.visualstudio.com/download)

2. Install the [Remote - SSH Extension](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-ssh)

3. Install SSH on your local computer
   * Ubuntu/Debian : Comes pre-installed but if it doesn't
     ```
     sudo apt-get install openssh-client
     ```
   * MacOS : Comes pre-installed
   * Windows : Install [Git for Windows](https://git-scm.com/download/win) comes with an ssh client
   
4. Connect to the GW VPN
   -- See this [guide](https://it.gwu.edu/what-virtual-private-network) from GW IT for more details
   > You must be connected to the GW VPN to ssh into `vlab01`
   
5. Open a new VSCode Window
   -- Open the command pallet with **F1** key
   -- Type `Remote-SSH: Connect to host` and choose to connect to a new host
   -- Then enter `ssh gwusername@ubuntu-vlab01.seas.gwu.edu` where `gwusername` is your NetID for GW. Like in your email `gwusername@gwu.edu`
   -- If prompted for the operating system, **select Linux (not the OS of your machine)**, and hit select that you want to continue when it mentions a fingerprint. This is because Linux is the OS of the remote host.
   -- Hit enter, and you may be prompted for a password, enter your GW account password
   -- Once your connected you should see a green bar at the bottom left saying something like `SSH ubuntu-vlab01.seas.gwu.edu.`

6. Continue with this [guide](/guides/vscode-git) for using vscode and git to complete your assignment
   > If you're asked to open a docker container, say no. It should work as is. 
    
### Setting up your git profile on `ubuntu-vlab01`

> You only need to do this once, but you need to do it in order for us to grade your work
   
1. Once your connected to `ubuntu-vlab01` via instructions above open an integrated terminal
   -- Open the command pallet with **F1** key
   -- Type/select `Terminal: Create New Terminal` 

2. In the terminal run the following commands

   ```
   git config --global user.name "John Doe"
   git config --global user.email johndoe@example.com
   ```
   Where `John Doe` is replaced with your name and `johndoe@example.com` is replaced with the email address you used to sign up to github. 

## Extra stuff

### Setting up an SSH key on `ubuntu-vlab01` with github

> Note you only need to do this once

1. Once your connected to `ubuntu-vlab01` via instructions above open an integrated terminal
   -- Open the command pallet with **F1** key
   -- Type/select `Terminal: Create New Terminal` 

2. Generate a ssh-key use `ssh-keygen`
   -- In the terminal type
      ```
      ssh-keygen
      ```
   -- Hit enter to each of the questions
   -- No, you do not need a passphrase

3. Copy your public key
   -- In the terminal type
      ```
      cat .ssh/id_rsa.pub
      ```
  -- This will print out your public key, select and copy it from thee terminal
  -- Make sure you copy the whole thing beginning with `ssh-rsa` ending with something like `username@ubuntu-vlab01`
  
4. Go to [github.com](github.com) and add your SSH key
   -- make sure you're logged in
   -- select your icon in the upper right
   -- select settings
   -- select `SSH and GPG Keys` from the options
   -- click the green button `New SSH key`
   -- Paste your ssh-key you copied from above in there and hit enter
   
5. Once you do that, you shouldn't need a password to push/pull your repos

   
### Retrieving files from ubuntu-vlab01 to your local machine

> Note that when you work on `ubuntu-vlab01` all the files are stored remotely. This is no big deal since you can always push your changes for this class to github, but you may want to also store a local copy. 

1. Once your connected to `ubuntu-vlab01` via instructions above open an integrated terminal
   -- Open the command pallet with **F1** key
   -- Type/select `Terminal: Create New Integrated Terminal` 


2. Select the explorer tab on VSCode on the left
   -- Open folder
   -- Select the folder you wish to load into VSCode (this can be your home folder)

3. Navigate to the file you want in the explorer within VSCode.

4. Select the file and DRAG it to your local machine desktop or another folder from your file explorer.






