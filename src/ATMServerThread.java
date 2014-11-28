import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

/**
 * The server thread. One thread for one client connection. Started by the server when new
 * connection is created. TODO Complete
 * @author Jonas Dahl & Nick Nyman
 * @version 1.0
 * @date 2014-11-28
 */
public class ATMServerThread extends Thread {
	/**
	 * The name of the thread.
	 */
	private static String threadName = "ATMServerThread";
	
	/**
	 * Some constants.
	 */
	private final static String LOGIN = "L";
	private final static String BALANCE = "B";
	private final static String WITHDRAW = "W";
	private final static String DEPOSIT = "D";
	private final static String EXIT = "Q";

	private final static String ERROR = "E ";
	private final static String AUTH_ERROR = ERROR + 1;
	private final static String INACTIVE_ERROR = ERROR + 2;
	private final static int TIMEOUT_TIME = 30;	// Minutes
	private final static int STATUS_OK = 1;
	
	
	/**
	 * Holds the socket so we always have the connection to client.
	 */
    private Socket socket = null;
    
    /**
     * The reader, where we get things the client sends us.
     */
    private BufferedReader in;
    
    /**
     * The writer, where we can send something and it'll fly away to the client.
     */
    PrintWriter out;

	/**
	 * Saves the date for the last activity.
	 */
	private Date lastActivity;
	
    /**
     * Constructor. Calls super constructor with a name of the thread and saves the
     * parameter.
     * @param socket the socket to the client.
     */
    public ATMServerThread(Socket socket) {
        super(threadName);
        this.socket = socket;
    }

    /**
     * Reads one line from the in-reader and returns it as a string. Blocks until string is received.
     * @return the string on input.
     * @prints logs the string to with log function on success.
     * @throws IOException if error occurs on read (se BufferedReader docs).
     */
    private String readLine() throws IOException {
        String str = in.readLine();
        ATMServer.log(""  + socket + " : " + str);
        return str;
    }

    /**
     * The run method is invoked when thread starts, ie when the client connects.
     * @prints TODO :)
     */
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader
                (new InputStreamReader(socket.getInputStream()));
            String inputLine, outputLine, sessionId;
            boolean listening = true;
            while (listening) {
                // TODO Send menu to client?
                inputLine = readLine();	// Reads input from client
                switch (inputLine) {
                case LOGIN:
                	String cardNumber = readLine();
                	String pinCode = readLine();
                	int response = verify(cardNumber, pinCode);
                	if (response > 0) {
                		out.println(response);
                    	this.lastActivity = new Date();
                	} else {
                		out.println(AUTH_ERROR);
                	}
                    break;
                case BALANCE:
                	if (active()) {
                		out.println(getBalance());
                	} else {
                		out.println(INACTIVE_ERROR);
                	}
                    break;
                case WITHDRAW:
                	String code = readLine();
                	String amount = readLine();
                	if (active()) {
                		out.println(withdraw(amount));
                	} else {
                		out.println(INACTIVE_ERROR);
                	}
                    break;
                case DEPOSIT:
                    break;
                case EXIT:
                	listening = false;
                	break;
                }
            }
            out.close();
            in.close();
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    
    }
    
    /**
     * TODO HELA FUNKTIONEN
     * @param cardNumber
     * @param pinCode
     * @return 1 on succes, 0 on fail
     */
    private int verify(String cardNumber, String pinCode) {
    	return 1;
    }
    
    /**
     * Checks if session is active.
     * @return true if session is active, false else
     */
    private boolean active() {
    	if (lastActivity == null)
    		return false;
    	Date now = new Date();
    	Calendar cal = Calendar.getInstance(); // Creates calendar
        cal.setTime(now); // Sets calendar to now
        cal.add(Calendar.MINUTE, -TIMEOUT_TIME); // Adds timeout
        Date nowMinusTimeout = cal.getTime(); // returns new date object, one hour in the future
        Date lastActivity = this.lastActivity;
    	
        this.lastActivity = now;	// Update last activity, this is an activity
    	if (nowMinusTimeout.compareTo(lastActivity) < 0)
    		return true;
    	return false;
    }
    
    /**
     * TODO HELA FUNKTIONEN
     * @param sessionId
     * @return the amount of money on the customer's account * 100 (159,59 kr is 15959)
     */
    private int getBalance() {
    	return 4711;
    }
    
    // TODO Parse int, do the withdraw, return status code
    private int withdraw(String amount) {
    	return STATUS_OK;
    }
}
