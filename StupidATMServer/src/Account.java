/**
 * A class for a bank account
 * @author Jonas Dahl & Nick Nyman
 * @version 1.0
 * @date 2014-11-29
 */
public class Account {
	private int id;
	private String personalID;
	private String name;
	private int cardNumber;
	private int pinCode;
	
	/**
	 * Loads user if exists from the database into this object.
	 * @return true if user existed, false if cardnumber or pincode where wrong
	 */
	public boolean verifyAndLoad(int cardNumber, int pinCode) {
		// TODO Verify and check if user exists in file, then add info to instance variables.
		return true;
	}
	
	// TODO Setters. If we want to update something, we also have to update the database file!
}