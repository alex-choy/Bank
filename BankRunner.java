import java.io.FileNotFoundException;

public class BankRunner {
	public static void main(String args[]) throws FileNotFoundException, InterruptedException {
		if (args.length != 2 || Integer.parseInt(args[1]) <= 0) {
			System.out.println("Invalid Input, please enter a positive, non-zero numbers");
		} else {
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
}
