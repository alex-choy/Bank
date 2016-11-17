import java.io.FileNotFoundException;

public class BankRunner {
	public static void main(String args[]) throws FileNotFoundException, InterruptedException {
		String fileToRead = args[0];
		int numOfWorkers = Integer.parseInt(args[1]);
		Bank bank = new Bank(numOfWorkers);

		MainWorker mainWorker = new MainWorker(bank, numOfWorkers, fileToRead);

		bank.runParallel(numOfWorkers);
		Thread mainThread = new Thread(mainWorker);
		
		mainThread.start();

		bank.printValues();
	}
}
