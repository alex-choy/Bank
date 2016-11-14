import java.util.concurrent.BlockingQueue;

public class MainWorker implements Runnable{
		private BlockingQueue<Transaction> q;
		private Bank bank;
		
		public MainWorker(BlockingQueue<Transaction> q, Bank bank){
			this.q = q;
			this.bank = bank;
		}
		@Override
		public void run() {
			try{
				bank.readInTransactions(q);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
		}
		
	}