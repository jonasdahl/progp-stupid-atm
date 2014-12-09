/**
 * A class that contains many constants used by both server and client.
 * @author Jonas Dahl & Nick Nyman
 * @version 1.0
 * @date 2014-12-07
 */
public class C {
	/** Some constants for sending on socket. */
	public final static String SOCKET_LOGIN 	= "L";
	public final static String SOCKET_BALANCE 	= "B";
	public final static String SOCKET_WITHDRAW	= "W";
	public final static String SOCKET_DEPOSIT 	= "D";
	public final static String SOCKET_UPDATE 	= "U";
	public final static String SOCKET_EXIT 		= "Q";
	
	/** Error constants. */
	public final static String ERROR = "E ";
	public final static String ERROR_AUTHENTICATION = ERROR + 1;
	public final static String ERROR_INACTIVE 		= ERROR + 2;
	public final static String ERROR_LANGUAGE 		= ERROR + 3;
	public final static String ERROR_CODE 			= ERROR + 4;
	public final static String ERROR_NEGATIVE 		= ERROR + 5;
	public final static String ERROR_BROKE 			= ERROR + 6;

	/** Status constants. */
	public final static String STATUS_OK 			= "0";
	public final static String STATUS_UPDATE 		= "-1";
	public final static String STATUS_LANGUAGE 		= "-2";
	public final static String STATUS_FILE 			= "-3";
	public final static String STATUS_END 			= "-4";
	public final static String STATUS_EOF 			= "-5";
	
	/** Other constants. */
	public final static String LANGUAGE_FILE_SEPARATOR = "==>";
	public final static String ACCOUNTS_FILE = "accounts.txt";
	public final static int    TIMEOUT_TIME = 30;
}
