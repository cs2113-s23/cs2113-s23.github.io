---
layout: worksheet
permalink: /worksheet/j6
showsolution: true
---

# Worksheet: J6

Worksheets are self-guided activities that reinforce lectures. They are not graded for accuracy, only for completion. Worksheets are due by Sunday night before the next lecture.


## Questions


### q
What is inside a packet?

#### s

All packets have a header, which stores the address or destination of a packet, and a payload which stores the data or message of the packet. 

### q
What is the purpose of an internet layer in the TCP/IP protocol? What do you, as a client, need to specify in this protocol?

#### s
The purpose of the internet layer is to inter-connect networks; for examplee GW has a network, and if you want to send data to Google, your packets must traverse GW network and potentially many other federated networks before finally reaching a server at Google.

You need to specify the IP address of a machine at this layer in order to interact with it.

### q
What is the purpose of the transport layer in the TCP/IP protocol? What do you, as a client, need to specify in this protocol?

#### s
The transport layer provides an abstraction for those two process to apear as if they are communicating directly with each other. 

Each process will typically communicate via a specific port at an IP address.

### q
What is the difference between SMTP and HTTP?

#### s
SMTP is a protocol used for sending/recieving emails; HTTP is used for webpages/websites.

### q 
What is the difference between an IP address and a domain name?

#### s
They are aliases; a domain name gets mapped to one or more IP addresses. The former is a human-readable web address, while the numeric IP address is the more "precise" location of the server or client.

### q
How does the operating system use ports?

#### s
The port address is a way for the Operating System to divide up the data arriving from the network based on the destination process. Additionally, ports tend to be tightly coupled with applications.

Ports allow the same computer to route incoming data to different applications running at the same time.

### q
Write code that creates a socket connection to ip address `123.45.678.900` at port `4040`. Then, print a color to that socket's output.

#### s
```java
try{
    Socket socket = new Socket("123.45.678.900", 4040);

    PrintWriter pw = new PrintWriter(sock.getOutputStream());
    pw.println("red");
    pw.close(); 
} catch (Exception e) {}
```

### q
What is the difference between a socket's input stream and its output stream?

#### s
A socket can write information (to a server, for example) using its output stream. A socket can receive information (from a server, for example) using its input stream.

### q
What is the difference between a `Socket` and a `SocketServer`?

#### s
The server is able to accept connections from sockets; it can then communicate with them with an input and output stream specific to that socket. A server may accept connections from multiple sockets simultaneously.

### q
How are threads useful with servers? How does a server manage to always be listening for sockets trying to connect?

#### s
A threaded server starts a new thread for each socket so that the main thread can continue to listen to incoming socket connections. Otherwise, only one client could be served at a time, which may be undesireable. 

As shown in the notes, we can use an infinite loop to be listening for new connections from client sockets; each such connection is then passed on to its own thread for processing, freeing up the server to continue to listen for more connections (while still processing all those client requests via the threads).
