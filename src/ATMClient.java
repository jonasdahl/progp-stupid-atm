import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
   @author Jonas Dahl
   @version 1.0
   @date 2014-11-28
*/
public class ATMClient {
    private static int portNumber = 8989;
    private static String hostName = "127.0.0.1";
    private static String lang = "svSE";
    
    public static void main(String[] args) throws IOException {
    	Language t = new Language(lang);
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdIn = null;
        try {
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (UnknownHostException e) {
            System.err.println(t.word("Don't know about host") + " " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(t.word("Couldn't get I/O for the connection to") + " " + hostName);
            System.exit(1);
        }
        
        while (true) {
        	printMenu(in, out);
        }
    }
    
    public static void printMenu(BufferedReader in, PrintWriter out) {
    	System.out.println();
    }
}   
