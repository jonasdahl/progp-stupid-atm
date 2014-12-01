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
	private int balance;
	
	/**
	 * Loads user if exists from the database into this object.
	 * @return true if user existed, false if cardnumber or pincode where wrong
	 */
	public boolean verifyAndLoad(int cardNumber, int pinCode) {
		// TODO Verify and check if user exists in file, then add info to instance variables.
		return true;
	}
	
	//TODO Getters. Retrieve data from db-file.
	
	/**
	 * Reads current balance from the account database.
	 * @return double value of account balance, in kronor
	 * NB: database stores value in Ören so balance must
	 * be converted to Kr before being presented to the client.
	 */
	public double getBalance(){
		double value = 133767;//TODO: parse db
		return (value/100); //Because value is stored in ören
	}
	
	// TODO Setters. If we want to update something, we also have to update the database file!
	
	/**
	 * Updates the balance in the account and the database
	 * @param addValue the deposited
	 */
	public void setDepositBalance(int addValue){
		this.balance = this.balance + addValue;
		//TODO: change balance in db
	}
	/**
	 * Considers a withdraw a negative deposit
	 * @param withdrawn amount
	 */
	public void setWithdrawBalance(int withdrawn){
		setDepositBalance(0-withdrawn);
	}
}
