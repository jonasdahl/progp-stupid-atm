import java.io.IOException;
import java.net.ServerSocket;
import java.text.DateFormat;
import java.util.Date;

/**
 * The server class of the ATM system. TODO Complete description
 * @author Jonas Dahl & Nick Nyman
 * @version 1.0
 * @date 2014-11-28
*/
public class ATMServer {
	/**
	 * The port number we use for this server.
	 */
    private static int portNumber = 8989; // TODO On launch. Fix so we take it as argument, but this is good for testing.
    
    /**
     * The main function. Starts listening for new clients and starts one thread 
     * for each client.
     * @param args ingored
     * @prints errors to stdout if something happens when creating socket and logs things happening
     * 		   during run time
     * @throws IOException if server socket error occurs when accepting
     */
    public static void main(String[] args) throws IOException {
    	ServerSocket serverSocket = null;
        try {
        	serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
        	// We had an error when listening on port. Log and exit.
            log("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            log(e.getMessage());
            System.exit(1);
        }
        
        // One turn in the loop for each client, that starts a new thread.
        while (true) {
        	new ATMServerThread(serverSocket.accept()).start();
        	log("New client connected.");
        }
    }
    
    /**
     * Prints the parameter to stdout.
     * @prints the parameter to stdout
     * @param logStr the string to be logged to stdout, with timestamp
     */
    public static void log(String logStr) {
    	Date now = new Date();
    	DateFormat df = DateFormat.getTimeInstance();
    	System.out.println(df.format(now) + ": " +logStr);
    }
}
