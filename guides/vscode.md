---
layout: default
permalink: /guides/vscode
title: VScode with Docker
---

# Running VSCode with a Docker Container for C

> Did something go wrong? Check out the [troubleshoot guide](/docker-wsl-vscode-troubleshoot)

1. Install Docker Desktop [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)
  > Note that if you are using windows you will also need to install WSL2. Microsoft has a great [guide](https://docs.microsoft.com/en-us/windows/wsl/install-win10) for that. Do this first before install Docker Desktop. 
  
2. Run Docker Desktop.  Docker desktop must be running to open a VS Code folder in a Docker container.
![Docker Icon Image](/images/docker-icon.png)

2. Install Visual Studio Code (VS Code)
[https://code.visualstudio.com/download](https://code.visualstudio.com/download)
	
3. Install the Remote Development extension pack plugin for VS Code 
![Visual Studio Image](/images/vscode-remotedev-plugin.png)


4. Open a folder in a docker container (Reference: [https://code.visualstudio.com/docs/remote/containers](https://code.visualstudio.com/docs/remote/containers))
	-- Open the command pallet with the **F1** key
	-- Type "Remote" to get a list of remote containers commands
	-- Select "Remote-Containers: Open Folder in Container"
![VS Code Open Folder In Container Image](/images/vscode-open-folder-in-container.png)


5. Create a new folder, or open an existing folder.


6. When prompted to "Add Development Container Configuration Files", select "Develop C++ applications on Linux." and "ubuntu-20.04"
![VS Code Add Development Container Configuration Image](/images/vscode-container-config.png)
![VS Code Add Development Container Configuration Linux Image](/images/vscode-container-config-linux.png)


7.  It may take and few minutes for the container to be created and initialized.

8.  Create a new file called *helloworld.c* and populate the file with the hello world code from [C0-C-basics](/c/0) 
![VS Code Hello World Image](/images/vscode-helloworld.png)

9. Open a new terminal.
![VS Code New Terminal Image](/images/vscode-new-terminal.png)

10. In the terminal window use **gcc** to compile and run helloworld.c.
![VS Code GCC Image](/images/vscode-gcc.png)

