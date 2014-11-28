import java.io.IOException;
import java.net.ServerSocket;

/**
   @author Jonas Dahl
   @version 1.0
   @date 2014-11-28
*/
public class ATMServer {
    private static int portNumber = 8989;
    
    public static void main(String[] args) throws IOException {
    	ServerSocket serverSocket = null;
        try {
        	serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            log("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        
        while (true) {
        	new ATMServerThread(serverSocket.accept()).start();
        	log("New client connected.");
        }
    }
    
    public static void log(String logStr) {
    	System.out.println(logStr);
    }
}
