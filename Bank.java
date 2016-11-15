import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Bank {
	private static final int NUMBER_OF_ACCOUNTS = 20;
	private Account[] accounts;
	static BlockingQueue<Transaction> queue;
	private static CountDownLatch count;

	public static void main(String args[]) throws FileNotFoundException, InterruptedException {
		Bank bank = new Bank();
		int numOfWorkers = Integer.parseInt("4");
		queue = new ArrayBlockingQueue<>(3);
		count = new CountDownLatch(1);

		MainWorker mainWorker = new MainWorker(queue, bank, numOfWorkers);

		bank.runParallel(11);
		Thread mainThread = new Thread(mainWorker);
		// Thread workThread1 = new Thread(bank.new Worker());

		// workThread1.start();
		mainThread.start();

		try {
			// workThread1.join();
		} catch (Exception e) {

		}
		/*
		 * try { workThread1.join(); mainThread.join(); } catch (Exception e) {
		 * 
		 * }
		 */
		count.await();
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

	public void readInTransactions(BlockingQueue<Transaction> q, int numOfWorkers) throws FileNotFoundException, InterruptedException {
		Scanner in = new Scanner(new File("small.txt"));
		while (in.hasNextLine()) {
			int id1 = Integer.parseInt(in.next());
			int id2 = Integer.parseInt(in.next());
			int amount = Integer.parseInt(in.next());

			Transaction trans = new Transaction(id1, id2, amount, accounts);
			q.put(trans);
		}
		
		for(int i = 0; i < numOfWorkers; i++){
			Transaction e = new Transaction(-1, 0, 0, accounts);
			q.put(e);
		}

		in.close();
	}

	public void runParallel(int workers) {
		List<Worker> theWorkers = new ArrayList<Worker>();
		for (int i = 0; i < workers; i++) {
			Worker worker = new Worker();
			theWorkers.add(worker);
			worker.start();
		}

	}

	class Worker extends Thread {
		public void run() {
			boolean finished = false;
			//while (!finished) {
				try {
					Transaction trans = queue.take();
					if (trans.getAcct1().getID() == (-1)) {
						finished = true;
						count.countDown();
					} else {
						trans.makeTransaction();
					}
					// count.countDown();

				} catch (Exception e) {

				//}
			}
		}
	}

}
