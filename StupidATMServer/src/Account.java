import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class for a bank account
 * 
 * @author Jonas Dahl & Nick Nyman
 * @version 1.0
 * @date 2014-11-29
 */
public class Account {
	/** The id in the database file. */
	private int id;
	/** The personal identification number. Personnummer. */
	private String personalID;
	/** The name of the account holder. */
	private String name;
	/** The card number associated with this person. */
	private int cardNumber;
	/** The pin code associated with the card. */
	private int pinCode;
	/** The possible correct codes for the user. */
	private static ArrayList<String> authcodes;

	/**
	 * Loads user, if it exists, from the database into this object.
	 * 
	 * @return user_id > 0 if user existed and the card number and pin code were
	 *         right, 0 if card number or pin code were wrong
	 */
	public int verifyAndLoad(String cardNo, String pinC) throws IOException {
		try {
			FileReader fl = new FileReader("accounts.txt");
			BufferedReader br = new BufferedReader(fl);
			ATMServer.log("Database opened.");

			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length != 6) {
					ATMServer.log("Error in database file: "
							+ Arrays.toString(parts));
					continue;
				}
				if (parts[3].trim().equals(cardNo)
						&& parts[4].trim().equals(pinC)) {
					id = Integer.parseInt(parts[0].trim());
					personalID = parts[1].trim();
					name = parts[2].trim();
					cardNumber = Integer.parseInt(parts[3].trim());
					pinCode = Integer.parseInt(parts[4].trim());
					//balance = Integer.parseInt(parts[5].trim());
					authcodes = new ArrayList<String>(Arrays.asList("01", "03",
							"05", "07", "09", "11", "13", "15", "17", "19",
							"21", "23", "25", "27", "29", "31", "33", "35",
							"37", "39", "41", "43", "45", "47", "49", "51",
							"53", "55", "57", "59", "61", "63", "65", "67",
							"69", "71", "73", "75", "77", "79", "81", "83",
							"85", "87", "89", "91", "93", "95", "97", "99"));
					br.close();
					return id;
				}
			}
			br.close();
		} catch (IOException e) {
			// We want to log and continue the exception
			ATMServer.log("Database read failed:");
			ATMServer.log(e.getMessage());
			throw new IOException();
		}
		ATMServer.log("Failed to find user.");
		return 0;
	}

	/**
	 * Checks whether the entered two-digit authorization code is among the
	 * correct ones.
	 * 
	 * @param enteredCode
	 *            the code to be verified
	 * @return boolean value, true if code is correct false if not
	 */
	public boolean authorize(String enteredCode) {
		if (authcodes.contains(enteredCode)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the current balance from the account.
	 * 
	 * @return int value of account balance, in cents (smallest possible unit
	 *         of currency).
	 */
	public int getBalance() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(C.ACCOUNTS_FILE));
			String line = br.readLine();
			while (line != null) {
				String[] parts = line.split("\\|");
				if (parts.length == 6) {
					if (parts[0].trim().equals("" + id)) {
						br.close();
						return Integer.parseInt(parts[5].trim());
					}
				}
				ATMServer.log("Read line from accounts file: " + line);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			ATMServer.log("Database read failed:");
			ATMServer.log(e.getMessage());
			return -1;
		}
		return -1;
	}

	/**
	 * Updates the balance in the account and the database.
	 * 
	 * @param addValue
	 *            the balance, an error (NEGATIVE_ERROR) if input parameter is <
	 *            0
	 */
	public String deposit(int addValue) {
		if (addValue < 0)
			return C.ERROR_NEGATIVE;
		editBalance(addValue);
		return "" + getBalance();
	}

	/**
	 * Changes balance in database file. Loops through every line, and saves it
	 * in a StringBuilder. Edits the line that needs to be edited, and then
	 * writes back the whole file.
	 * 
	 * @param diff the value of which the balance should change (-100 means remove 1 SEK from account)
	 * @return true if it succeeds, false if it fails.
	 */
	private boolean editBalance(int diff) {
		String fileText = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(C.ACCOUNTS_FILE));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				String[] parts = line.split("\\|");
				if (parts.length == 6) {
					if (parts[0].trim().equals("" + id)) {
						line = id + " | " + personalID + " | " + name + " | "
								+ cardNumber + " | " + pinCode + " | "
								+ (Integer.parseInt(parts[5].trim()) + diff);
					}
				}
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			fileText = sb.toString();
			br.close();
			PrintWriter writer = new PrintWriter(C.ACCOUNTS_FILE, "UTF-8");
			writer.print(fileText);
			writer.close();
		} catch (IOException e) {
			ATMServer.log("Database read failed:");
			ATMServer.log(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Considers a withdraw a negative deposit
	 * 
	 * @param withdrawn
	 *            the amount to be withdrawn
	 * @return the balance, or an error (NEGATIVE_ERROR) if input parameter is <
	 *         0, or an error (BROKE_ERROR) if input parameter is > balance
	 */
	public String withdraw(int withdrawn) {
		if (withdrawn < 0)
			return C.ERROR_NEGATIVE;
		if (getBalance() < withdrawn)
			return C.ERROR_BROKE;
		editBalance(-withdrawn);
		return "" + getBalance();
	}

	/**
	 * Determines whether this account is verified and filled.
	 * 
	 * @return true if there is a user from the database file in this object,
	 *         false else.
	 */
	public boolean isSet() {
		if (id > 0)
			return true;
		return false;
	}
}
