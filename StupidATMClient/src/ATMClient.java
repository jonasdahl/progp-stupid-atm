import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
   @author Jonas Dahl & Nick Nyman.
   @version 1.0
   @date 2014-11-28
*/
public class ATMClient {
	/** Specifies port number. */
    private static int portNumber = 8989;
    /** Specifies host name. */
    private static String hostName = "127.0.0.1";
    /** Stores info about language. */
    private static Language t;
    /** Socket to server. */
    private static Socket socket;
    /** Out-stream to server. */
    private static PrintWriter out;
    /** In-stream from server. */
    private static BufferedReader in;
    /** In-stream from standard in. */
    private static BufferedReader stdIn;
    
    /**
     * Initializes ATMClient, sets socket, out, in and so on. Exits program if error occurs when
     * initializing those variables and prints some part of 
     */
    public ATMClient() {
    	try {
			t = new Language("enUS"); // Default language is English.
		} catch (IOException e) {
			System.out.println("An error occurred when reading language file.");
			System.exit(1);
		}	
        socket = null;
        out = null;
        in = null;
        stdIn = null;
        
        try {
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (UnknownHostException e) {
            System.err.println(t.t("unknown_host") + " " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(t.t("no_io") + " " + hostName);
            System.exit(1);
        }
    }
    
    /**
     * Reads one line (until \n) from standard input. Blocks until there is a line to read.
     * @return the string that has been read
     * @exits if the read gives an IOException
     */
    private String readLine() {
    	String line = "";
    	try {
			line = stdIn.readLine();
		} catch (IOException e) {
			System.out.println();
			System.exit(1);
		}
    	return line;
    }

    /**
     * Turns text string to double. For example " 443,2" ==> 443.2 or "  44.22  " ==> 44.22
     * @param text the string to be parsed
     * @return the double parsed from the string or -1 if the string couldn't be parsed
     */
    private double grabDoubleFromText(String text) {
    	double value = 0;
    	try {
    		value = Double.parseDouble(text.trim().replace(',', '.'));
    	} catch (NumberFormatException e) {
    		value = -1;
    	}
    	return value;
    }

    /**
     * Turns text string to int. For example " 443" ==> 443 or "  44  " ==> 44
     * @param text the string to be parsed
     * @return the double parsed from the string or -1 if the string couldn't be parsed
     */
    private int grabIntFromText(String text) {
    	int value = 0;
    	try {
    		value = Integer.parseInt(text.trim());
    	} catch (NumberFormatException e) {
    		value = -1;
    	}
    	return value;
    }
    
    /**
     * Runs the client process.
     * @throws IOException 
     * @prints menu when menu is supposed to be printed.
     */
    public void start() throws IOException {
    	String line;
        boolean cont = true;
        while (cont) {
            clearScreen();
        	System.out.println(t.t("menu"));
        	line = readLine();
        	if (line == null) 
        		break;
        	
        	int choice = grabIntFromText(line);
        	String response;
        	switch (choice) {	// TODO Everything, constants for cases, nicer error handling osv.. =D
        	case 1: //LOGIN
        		clearScreen();
        		System.out.println(t.t("login"));
        		System.out.print(t.t("card_number") + ": ");	// Ask for card number
        		String cardNumber = readLine();
        		System.out.print(t.t("pincode") + ": ");	// Ask for pin code
        		String pinCode = readLine();
        		out.println("L");	// Send an "L" for login
        		out.println(cardNumber);  // Send card number
        		out.println(pinCode);	  // Send pin code
        		response = in.readLine();
        		if (response.startsWith("E")) {
        			System.out.println(t.t("error"));
        		} else {
        			System.out.println(t.t("logged_in"));
        		}
        		System.out.print(t.t("enter"));
        		stdIn.readLine();	// Throw away enter
        		break;
        	case 2: // Withdraw
        		clearScreen();
        		System.out.println(t.t("withdraw"));
        		System.out.print(t.t("two_digit_code") + ": ");	// Ask for code
        		String code = stdIn.readLine();
        		System.out.print(t.t("amount") + ": ");	// Ask for amount
        		String amount = stdIn.readLine();
        		out.println("W");	// Send a "W" for withdrawal
        		out.println(code);  // Send code
        		out.println(amount);	  // Send amount
        		response = in.readLine();
        		if (response.startsWith("E")) {
        			System.out.println(t.t("error"));
        		} else {
        			System.out.println(t.t("you_have") + " " + amount + " " + t.t("in_cash"));
        			System.out.println(t.t("you_have") + " " + ((double)Integer.parseInt(response)/100) + " " + t.t("on_account"));
        		}
        		System.out.print(t.t("enter"));
        		stdIn.readLine();	// Throw away enter
        		break;
        	case 3: // Balance
        		clearScreen();
        		System.out.println(t.t("balance"));
        		out.println("B");	// Send a "B" for balance
        		response = in.readLine();
        		if (response.startsWith("E")) {
        			System.out.println(t.t("error"));
        		} else {
        			System.out.println(t.t("you_have") + " " + ((float)Integer.parseInt(response)/100) + " " + t.t("on_account"));
        		}
        		System.out.print(t.t("enter"));
        		stdIn.readLine();	// Throw away enter
        		break;
        	case 4: // Quit
        		clearScreen();
        		out.println("Q");	// Send an "Q" for byebye
        		cont = false;
        		break;
        	case 5: // Change language
        		clearScreen();
        		System.out.println(t.t("language_question"));	// Ask for language
        		String answer = stdIn.readLine();
        		setLanguage(answer);
        		break;
        	}
        }
    }
    
    public static void main(String[] args) throws IOException {
    	ATMClient client = new ATMClient();
    	client.start();
        
    }
    
    public static void setLanguage(String lang) throws IOException {
    	t = new Language(lang);
    }
    
    public static void clearScreen() {
    	final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }
}   
