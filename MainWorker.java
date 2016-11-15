import java.util.concurrent.BlockingQueue;

public class MainWorker implements Runnable{
		private BlockingQueue<Transaction> q;
		private Bank bank;
		private int numOfWorkers;
		
		public MainWorker(BlockingQueue<Transaction> q, Bank bank, int numOfWorkers){
			this.q = q;
			this.bank = bank;
			this.numOfWorkers = numOfWorkers;
		}
		@Override
		public void run() {
			try{
				bank.readInTransactions(q, numOfWorkers);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
		}
		
	}