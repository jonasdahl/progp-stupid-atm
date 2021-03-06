import java.io.IOException;
import java.net.ServerSocket;
import java.text.DateFormat;
import java.util.Date;

/**
 * The server class of the ATM system.
 * @author Jonas Dahl & Nick Nyman
 * @version 1.0
 * @date 2014-11-28
*/
public class ATMServer {
	/** The port number we use for this server. */
    private static int portNumber;
    
    /**
     * The main function. Starts listening for new clients and starts one thread 
     * for each client.
     * @param args ingored
     * @prints errors to stdout if something happens when creating socket and logs things happening
     * 		   during run time
     * @throws IOException if server socket error occurs when accepting
     */
    public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Usage: ATMServer [port]");
			System.out.println("The port is to the clients.");
			System.exit(1);
		}
		try {
			portNumber = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("Invalid port number.");
			System.exit(1);
		}
		
    	ServerSocket serverSocket = null;
        try {
        	serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
        	// We had an error when listening on port. Log and exit.
            log("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            log(e.getMessage());
            System.exit(1);
        }
        
        // One turn in the loop for each client, which starts a new thread.
        boolean loop = true;
        while (loop) {
        	new ATMServerThread(serverSocket.accept()).start();
        	log("New client connected.");
        }
        // We know we won't reach this, but for the sake of Eclipse, we have it!
        serverSocket.close();
    }
    
    /**
     * Keeps a log of activities and events on standard output.
     * @prints the parameter to stdout, together with the time when received
     * @param logStr the string to be logged to stdout, with timestamp
     */
    public static void log(String logStr) {
    	Date now = new Date();
    	DateFormat df = DateFormat.getTimeInstance();
    	System.out.println(df.format(now) + ": " +logStr);
    }
}
