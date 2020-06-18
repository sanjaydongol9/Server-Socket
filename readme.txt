

Project 01: A Simple Web Client and a Multithreaded Web Server.

Programming Language Used: Java
IDE used: Code written in Visual Studio and run in MacOs terminal.

Steps to Compile:

Step 1: javac *.java (This will create a class file for compilation).

Step 2: To run the server simply run 
	java server    //running this line will use default port 8080
	java server portNumber   //portNumber can be replaced by any port number user want to use. 

Step 3: To run the client
	java client localhost   //localhost is required and default port 8080 and default file index.htm will be accessed. 
	java client localhost portNumber fileName     //here portNumber is the port number of server and fileName is file that you requests. 


*************************************************
*	References to write the source code.	*
*************************************************

https://www.javatpoint.com/multithreading-in-java

https://docs.oracle.com/cd/E19455-01/806-5257/

https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html

Skeleton Code posted in Canvas. 

https://www.geeksforgeeks.org/java-io-printstream-class-java-set-1/


***********************
Files Included
***********************
1) server.java
2)client.java
3) index.htm 
4) readme.txt 
