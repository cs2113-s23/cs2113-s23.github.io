---
layout: toc
permalink: project/3
---



# Project 3: GWack (GW Slack Simulator)

*Watch the videos for this project on this [YouTube Playlist](https://youtube.com/playlist?list=PLnVRBITSZMSM94iAdy9Mig5cRMLBFnzKf)*

In this project, you will be write a client and server that implements a chat room, similar to Slack (or GWack!). Your client software should be GUI based, and your server software will be command line based. 

## Preliminaries

Github Classroom Link: [https://classroom.github.com/a/L1iVEE5G](https://classroom.github.com/a/L1iVEE5G)

### Testing/Grading

There is no test scripts. You will be grading based on functionality and OOP design.

### What to submit

You should submit at least the two files:
* `GWackChannel.java`: the server code to host a channel/chatroom
* `GWackClientGUI.java`: the client code and GUI for interacting with the Server.

### Compiling and Running your Code

While you may submit other code, we should be able to compile and run your programs using the following commands. This is how the grader can test the different components of the project independently.

For the server:

```
javac GWackChannel.java
java GwackChannel port
```

Where `port` is replaced by the port for the server to bind to.  

And for the client GUI, it is run as such. 

```
javac GWackClientGUI.java
java GwackCliengGUI
```

>  While there is no starter code, and you may design as you see fit, you MAY NOT USE any packages other than what ships with Java. We will not install any third party packages to compile/run your code. 


---

## GWack Slack Protocol

GWack, the GW Slack simulation, is a chat room where multiple clients can message each other. **Any message posted to the chatroom will be sent to the server and then relayed to all connected clients.** As part of this project, you will be implement both a client GUI that connect to a server and the server that hosts the chat room, or GWack channel. 

### Public channels for testing

To facilitate testing, we are hosting three channels you can connect to and interact with:

```
host: ssh-cs2113.adamaviv.com port: 8886
host: ssh-cs2113.adamaviv.com port: 8887
host: ssh-cs2113.adamaviv.com port: 8888
```

> IMPORTANT: These channels are shared for the entire class. We will monitor the connections and what is posted to these channels. Any behavior considered hateful (broadly defined) may result in disciplinary and academic actions.


### Client/Server Protocol

So that everyone's GWack client to can interact with each other and the public channels, we must define a standard protocol. 

#### Initial Handshake

When a client first connect, it sends the following information to the server. 

```
SECRET
3c3c4ac618656ae32b7f3431e75f7b26b1a14a87
NAME
username
```

> Note that there are newlines `\n` here. It is important for parsing you send each as a complete line. So your server can read a line, then expect the next line, and so on.

> Note that `username` should be replace with whatever this client chooses as a username. Like if they were named `george` it would be `george` instead of `username`

If successful, the server responds with the member list (see below), otherwise, the server will simply close the connection. The server must check that the SECRET sent by the user matches that expected value on the server side.


#### Member List Updates

One feature of the GWack channel is that you can view all the members of the channel. This is done by the server regularly distributing a list of members in the channel. These messages are always of the following form:

```
START_CLIENT_LIST
username1
username2
...
END_CLIENT_LIST
```

> Note that `username1` ... will be the names chosen by the users.

> `...` is indicate that all the clients should be listed here, and is not part of the protocol.



#### Sending/Receiving Messages


All other communications are considered as channel messages. A client simply sends one line a message at a time to the server. For example, supposer `username` send the following message to the server

```
GWack is so much better than slack
```

When the server receives that message, it then distributes it to all the clients, including the client that just sent the message inset in `[]`

```
[username] GWack is so much better than slack
```

When a client receives a message from the server, it will display that it in the display panel. 



## GWack Client (70 points)

You will implemented a threaded, GUI GWack client. Here's a visual of my implementation of the GWack Client. Yours can have different design and features, as long as the major features are there.

It's a visual of a connected client mid-message:

<img src="/images/project-3/gui-client.png" alt="GUI Display"
style="display: block;
margin-left: auto;
margin-right: auto;"/>

### Connecting and Disconnecting

You should have a mechanism for clients to set a name, a host/ip address and a port to connect to. There should also be a button for connecting and disconnecting. There also needs to be visuals for when the user is connected vs. not connected.

For example, here are the menu options for connected clients

<img src="/images/project-3/gui-client-connected.png" alt="GUI Display Connected"
style="display: block;
margin-left: auto;
margin-right: auto;"/>

and disconnected clients

<img src="/images/project-3/gui-client-not-connected.png" alt="GUI Display Disconnected"
style="display: block;
margin-left: auto;
margin-right: auto;"/>

You should also consider making the text-areas non-editable once connected as well.

### ERROR reporting

You should have some mechanism for reporting errors with connections. I choose to use [message dialogs](https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html), like the following. You can use something else, but you have to have something. 

<img src="/images/project-3/gui-client-connected-error.png" alt="GUI Display Error" width="50%"
style="display: block;
margin-left: auto;
margin-right: auto;"/>

Some errors to consider:
* Cannot connect to server
* Invalid host
* Invalid port
* Server disconnected

### Message Sending and Display

You have should have two text display areas. 

The first is to display all the messages that have been received. This text area should not be editable. 


<img src="/images/project-3/gui-client-display.png" alt="GUI Display messages" width="75%"
style="display: block;
margin-left: auto;
margin-right: auto;"/>


The second display should be used to draft messages, this should be editable. There should be some sort of button to initiate a send. 

<img src="/images/project-3/gui-client-send.png" alt="GUI send messages" width="75%"
style="display: block;
margin-left: auto;
margin-right: auto;"/>


Additionally, you should have a key listener so that messages send when the user hits ENTER:

```msgTextArea.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '\n'){
                    sendMessage();
                }                
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}

        }); 
```

> Important: the user can either send a message by hitting "ENTER" or clicking the button. 

### Members in the channel

There should be a display for the current members in the channel. This should update as the members come and go, but it should not be editable by the user. 

<img src="/images/project-3/gui-client-members.png" alt="GUI members display" width="15%"
style="display: block;
margin-left: auto;
margin-right: auto;"/>


## GWack Server (20 points)

Your GWack server (`GWackChannel`) should host a single chatroom or channel at a specified port, provided as a command line argument. The remaining requirements of the server are as follows:

* The server should be able to handle multiple connected clients
* All incoming messages should be distributed to all connected clients
* Clients should be able to connect and disconnect gracefully
* The current client list should be distributed and updated when clients leave
* The protocol for client interaction (as described above) should be observed


## Extra Features (up to 20 points)

Pick any (or all) the following features to add your GWack client and server. The max points you can achieve is 20 points --- that is you can earn a grade as high as 120/100 -- but you can choose to do any subset of the following additional features to receive credit.


> IMPORTANT: be sure to clearly describe which of the features you implemented in your README and how we can test them effectively.

> We've noted some features that are server dependent with (SERVER) tag

Possible feature upgrades:

* [5pts] (SERVER) Add a feature so that the username changes without disconnecting and reconnecting. Note this will require edits to your client and server.

* [5pts] Color coding messages from different users

* [10pts] Modify your GUI client so that it can connect to multiple GWack channels in the same GUI. Your GUI design should be somewhat comprehensive. 

* [5pts] Add in status updates, like away, busy, available, to members. (This could be server dependent, or you can come up with a *smart* way to do it for only clients.)

* [10pts] (SERVER) Allow for direct messaging between members. These messages can still traverse the server, but may not be broadcast to other channel members. You should also considered how to display DMs different from other messages. 

* [5pts] Channel theme/topic: Allow a user to set the current topic/theme of the channel. 

* [10pt] Add in typography to messages, such as bold, underline, and italic. (Hint, consider sending HTML instead of plain text?)

* [5-10pts] Propose your own feature upgrade. Post a message in Ed with the proposal for the feature. A teaching staff member will let approve and assign a point value. Then this description will update with that feature so that others can also attempt it. 

* [5pt] Pretty up your Client GUI -- spend some time making your Client GUI look really really really sharp. If you attempt this, it should be substantially better than what is in the screen shots above. That is, we want something that looks like a real product.


