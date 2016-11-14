
public class Transaction {
	private Account acct1;
	private Account acct2;
	private int amount;
	
	public Transaction(int acct1, int acct2, int amount, Account[] accounts){
		this.acct1 = accounts[acct1];
		this.acct2 = accounts[acct2];
		this.amount = amount;
	}
	
	
	
	public synchronized void makeTransaction(){
		acct1.setBalance(-amount);
		acct2.setBalance(amount);
		acct1.incrementTransactions();
		acct2.incrementTransactions();
	}
	
}
