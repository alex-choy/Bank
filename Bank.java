import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Bank {
	private static final int NUMBER_OF_ACCOUNTS = 20;
	private Account[] accounts;

	public static void main(String args[]) throws FileNotFoundException, InterruptedException {
		Bank bank = new Bank();
		BlockingQueue<Transaction> queue = new ArrayBlockingQueue<>(10);

		MainWorker mainWorker = new MainWorker(queue, bank);
		Worker worker = new Worker(queue);

		Thread mainThread = new Thread(mainWorker);
		Thread workThread1 = new Thread(worker);
		Thread workThread2 = new Thread(worker);
		Thread workThread3 = new Thread(worker);
		Thread workThread4 = new Thread(worker);

		mainThread.start();
		workThread1.start();
		workThread2.start();
		workThread3.start();
		workThread4.start();

		try {
			mainThread.join();
			workThread1.join();
			workThread2.join();
			workThread3.join();
			workThread4.join();
		} catch (Exception e) {

		}

		bank.printValues();
	}

	public Bank() {
		accounts = new Account[NUMBER_OF_ACCOUNTS];
		for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
			accounts[i] = new Account(i);
		}
	}

	// TODO: Methods for:
	// Starting up Worker Threads
	// Reading transactions from the file (Start up worker threads before
	// reading transactions)
	// Printing out all the account values when everything is done

	public void printValues() {
		for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
			System.out.println(this.accounts[i].toString());
		}
	}

	public void readInTransactions(BlockingQueue<Transaction> q) throws FileNotFoundException, InterruptedException {
		Scanner in = new Scanner(new File("small.txt"));
		while (in.hasNextLine()) {
			int id1 = Integer.parseInt(in.next());
			int id2 = Integer.parseInt(in.next());
			int amount = Integer.parseInt(in.next());

			Transaction trans = new Transaction(id1, id2, amount, accounts);
			q.put(trans);
		}

		in.close();
	}

}
