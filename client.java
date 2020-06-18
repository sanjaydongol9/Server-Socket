
//References:
//https://www.javatpoint.com/multithreading-in-java
//https://docs.oracle.com/cd/E19455-01/806-5257/
//https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
//Skeleton Code posted in Canvas. 
//https://www.geeksforgeeks.org/java-io-printstream-class-java-set-1/


import java.io.*;
import java.net.*;

public class client { 
	private static Socket socket;       //socket class for the socket connection
    public static String CRLF = "\r\n"; //CRLF for the new line
    public static String SP=" ";        //String variable for the soace
    private static int Port=8080;       //Default port 8080
    public static String hostName;  //default hostname localhost
    public static String fileName="index.htm";  //default fileName index.htm

	public static void main(String args[]) throws Exception{

        //using StringBuilder class to print the required data in the client. 
			StringBuilder newString = new StringBuilder();

        if(args.length == 1)   //if the argument entered in command line is only 1. 
		{
            if(args[0].equals("localhost")||args[0].equals("127.0.0.1")){
            hostName = args[0];
            System.out.println("\nDefault port: 8080 and file name index.htm used. ");
        }
        else{
            System.out.println("\nServer needs the  valid IP address.\n");
            System.exit(0);
        }
		}
		else if (args.length == 2){ //if there are two arguments in command line
            hostName = args[0];   //first command line is the localhost name
            
			try {
				Port = Integer.parseInt(args[1]);  //if the second argument is port use that. 
            }
            
            //if the second element is not port number then it is the file name requested. 
			catch (Exception ef)   
			{
                System.out.println("\nDefault Port: 8080 used...");
				fileName = args[1];
			}
		}
		else if (args.length == 3){  //if 3 arguments are entered then first is localhost
			hostName = args[0];
			Port = Integer.parseInt(args[1]); 
            fileName=args[2];
		}
		else  //if the length is greater than 3 or 0 then below line is printed and the program exits.
		{
        System.out.println("\nNot Enough Information is provided. Please provide valid information...\n");
        System.out.println("The format should be <server_IPaddress/name> [<port_number>] [<requested_file_name>] \n");
        System.exit(0);    
    }

        try{   
      String readFile;
	
        //get inet address of the localhost/IP Address
        InetAddress serverInet = InetAddress.getByName(hostName);

        //using socket to establish connection with the server already running
        socket = new Socket(serverInet, Port);
		System.out.println("connection established with the server");
        
        //the format of the request sent to the server for the desired file. 
        String clientRequest="GET" +SP+"/"+ fileName + SP+ "HTTP/1.1";

        //using the print stream class to write the data to the output.
        //We can use the class DataOutputStream like in server but it is easier 
        //to use printStream . Can finish the operation in couple of lines. 
        PrintStream printstream = new PrintStream(socket.getOutputStream());

        //writting the requested line to the server socket. 
        printstream.println(clientRequest);
        printstream.println(CRLF);

        //printing all these data in client window. 
        System.out.println("\n Waiting for the response from the Server. ");
        System.out.println(" ");
        System.out.println("Connection established to Port: "+socket.getPort()+"\n");
        System.out.println(" ");
        System.out.println("*************The response from the server***********");
        System.out.println("*****************************************************");
        System.out.println(" ");


        //input reader stream to read the necessary data coming from the server. 
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((readFile = inputStream.readLine()) != null) {
			    newString.append(readFile + "\n");
			}
            System.out.println(newString.toString());
            
            //closing the inputStream calss. 
          inputStream.close();
           
          //if any error catches in the code, the error is printed. 
		} catch (Exception err) {
			System.out.println(err.getMessage());
        }   
	}
}
