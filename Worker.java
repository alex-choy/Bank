import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable {
	private BlockingQueue<Transaction> q;

	public Worker(BlockingQueue<Transaction> q) {
		this.q = q;
	}

	@Override
	public void run() {
		try {
				Transaction trans = q.take();
				trans.makeTransaction();

		} catch (Exception e) {

		}

	}
}