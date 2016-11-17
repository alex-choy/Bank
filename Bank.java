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
	private BlockingQueue<Transaction> queue;
	private CountDownLatch count;
	private Semaphore sem;

	//*************************************************************************************************************
	
	//*************************************************************************************************************
	
	
	public Bank(int numOfWorkers) {
		accounts = new Account[NUMBER_OF_ACCOUNTS];
		for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
			accounts[i] = new Account(i);
		}

		queue = new ArrayBlockingQueue<>(12);
		count = new CountDownLatch(numOfWorkers);
		sem = new Semaphore(6);
	}

	public void printValues() throws InterruptedException {	
		count.await();
		for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
			System.out.println(this.accounts[i].toString());
		}
	}

	public void readInTransactions(String fileToRead, int numOfWorkers)
			throws FileNotFoundException, InterruptedException {
		Scanner in = new Scanner(new File(fileToRead));
		while (in.hasNextLine()) {
			int id1 = Integer.parseInt(in.next());
			int id2 = Integer.parseInt(in.next());
			int amount = Integer.parseInt(in.next());

			Transaction trans = new Transaction(id1, id2, amount, accounts);
			queue.put(trans);
		}

		for (int i = 0; i < numOfWorkers; i++) {
			Transaction e = new Transaction(-1, 0, 0, accounts);
			queue.put(e);
		}

		in.close();
	}

	public void runParallel(int workers) throws InterruptedException {
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
			while (!finished) 
				try {
					sem.acquire();
					Transaction trans = queue.take();
					if (trans.getAcct1().getID() < 0) {
						finished = true;
						count.countDown();
					} else {
						trans.makeTransaction();
					}
					sem.release();
				} catch (Exception e) {

				
			}
		}
	}

}
