

//References:
//https://www.javatpoint.com/multithreading-in-java
//https://docs.oracle.com/cd/E19455-01/806-5257/
//https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
//Skeleton Code posted in Canvas. 

import java.io.*;
import java.net.*;
import java.util.*;

//public class server
public class server {
    private static ServerSocket serverSocket;
    public static int port=8080;  //setting the default port to 8080

    public static void main(String args[]) throws Exception {
        if(args.length > 0){
			try{
                port = Integer.parseInt(args[0]);
                System.out.println(" ");
                System.out.println("Using the entered port: "+port);
                System.out.println(" ");
			} catch(Exception ef){
				System.out.println("Error in the port number. [[[ "+ef.getMessage()+" ]]]");
			}
		} else {
			System.out.println("Using default port :8080");
		}
        
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started....");
            System.out.println("Server is ready for the responses....");

        } catch (Exception e) {
            System.out.println("Error in the server [[ " + e.getMessage() + " ]]");
        }
        // Process HTTP service requests in an infinite loop.
        while (true) {
            // Listen for a TCP connection request.
            Socket socket = serverSocket.accept();
            // Construct an object to process the HTTP request message.

            HttpRequest request = new HttpRequest(socket);
            // Create a new thread to process the request.
            Thread thread = new Thread(request);
            // Start the thread.
            thread.start();

        }
    }

}

//thread for the implementation of the thread. 

final class HttpRequest implements Runnable {

    final static String CRLF = "\r\n";
    Socket socket;

    // Constructor
    public HttpRequest(Socket socket) {
        this.socket = socket;
    }

    // Implement the run() method of the Runnable interface.
    public void run() {

        try {
            processRequest();
        } catch (Exception e) {
        }

    }
    private void processRequest() throws Exception {

        // Get a reference to the socket's input and output streams.
        OutputStream os = socket.getOutputStream();
        DataOutputStream outputStream = new DataOutputStream(os);


        // Get a reference to the socket's input and output streams.
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Get the request line of the HTTP request message.
        String requestLine = inputStream.readLine();

        // Display the request line.
        System.out.println();
        System.out.println("The request line sent by the client.\n");
        System.out.println(requestLine);

        // Get and display the header lines.
        String headerLine = null;
        while ((headerLine = inputStream.readLine()).length() != 0) {
            System.out.println(headerLine);
        }

        // Extract the filename from the request line.
        StringTokenizer tokens = new StringTokenizer(requestLine);
        tokens.nextToken(); // skip over the method, which should be "GET"
        String fileName = tokens.nextToken();

        // Prepend a "." so that file request is within the current directory.
        fileName = "." + fileName;
        // Opening the requested file.
        FileInputStream fileinputstream = null;
        boolean fileExists = true;
        try {
            fileinputstream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            fileExists = false;
        }

        // Construct the response message.
        String statusLine = null;
        String contentTypeLine = null;
        String entityBody = null;
        if (fileExists) {
            statusLine = "HTTP/1.1 200 OK";
            contentTypeLine = "Content‐type: " + contentType(fileName) + CRLF;
        } 
        else {
            statusLine = "HTTP/1.1 404 Not Found";
        
        contentTypeLine = "The Content is Empty.\n";
        entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" + "<BODY>Not Found</BODY></HTML>";
        }
        
        //writting to the output stream to send to client
        outputStream.writeBytes(CRLF+statusLine);
        outputStream.writeBytes(CRLF+contentTypeLine);
        outputStream.writeBytes(CRLF);

        if (fileExists) {
            sendBytes(fileinputstream, os);
            fileinputstream.close();
        } else {
            outputStream.writeBytes(CRLF+entityBody);
            outputStream.writeBytes(CRLF+statusLine);
            outputStream.writeBytes(CRLF+contentTypeLine);
        }
        inputStream.close();
        outputStream.close();
        socket.close();

    }


    //method to send the bytes data to the response to the client. 

    private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
        // Construct a 1K buffer to hold bytes on their way to the socket.
        byte[] buffer = new byte[1024];
        int bytes = 0;
        // Copy requested file into the socket's output stream.
        while ((bytes = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }
    }

    //reading the content type of the file. 
    private static String contentType(String fileName) {
        if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
            return "text/html";
        }
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (fileName.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet‐stream";
    }
}
