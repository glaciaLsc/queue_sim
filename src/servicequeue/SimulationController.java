package servicequeue;
/*
 * Garrett Justice
 * Hussain Alnasser
 */
/**
 * Sample Queue MVC with threading, with this controller handling the threading
 * to pause while running. Notice that this is the conduit between the model
 * (ServiceQueueManager) and the view (SimulationView). You'll need to clean
 * these up and implement them appropriately.
 * 
 * @author Daniel Plante
 *
 */
public class SimulationController implements Runnable {
	// Data Members
	private ServiceQueueManager myModel ;
	private SimulationView myView;
	private boolean mySuspended;
	private Thread myThread;

	/**
	 * Basic Constructor
	 */
	public SimulationController() {
		myModel = new ServiceQueueManager();
		myView = new SimulationView(this);
		myThread = new Thread(this);
		mySuspended = false;
	}
	
	public void setModel(int p,int q,int r, int s,int t){
		suspend();
		myModel = new ServiceQueueManager(p, q, r, s, t);
		}

	int sercus=0;
	private void displayCustomers(int queue) {
		
		for (ServiceQueue sq : myModel.getAllServiceQueues()) {
			int numInQueue = sq.getTotalCustomersInLine();
			sercus=sq.getNumberCustomersServedSoFar();
			int served=sq.getTotalServed();
			if(served==myModel.getCustomerGenerator().getMaxNumberCustomers()){
				suspend();
			}
			
			myView.setCustomersInLine(queue, numInQueue);
		}
	}


	public void run() {
		try {
			synchronized (this) {
				this.updateView();
			}
		} catch (InterruptedException e) {
			System.out.println("Thread suspended.");
		}
	}

	/**
	 * Updates the view.
	 * 
	 * @throws InterruptedException
	 */
	private void updateView() throws InterruptedException {
		while (true) {
			this.waitWhileSuspended();

			try {
				Thread.sleep(100);
				myView.avg1.setText("Total Customers:"+(int)myView.numCust.getSelectedItem()+"\nTotal Served:"+myModel.totalServedSoFar()+"\nAverage Wait:"+(int)myModel.averageWaitTime()+"\nAverage Service:"+myModel.averageServiceTime());
				myView.stats.setText("Idle Wait:"+myModel.averageWaitTime());	
				
				for (ServiceQueue sq : myModel.getAllServiceQueues()) {
				//	System.out.println(sq.getNumberCustomersServedSoFar());
					myView.myTotalServed[sq.getQueueId()].setText(sq.getNumberCustomersServedSoFar()+"");
				}
				
				for (int x = 0; x < myView.numOfCashiers; x++) {
					this.displayCustomers(x);
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void waitWhileSuspended() throws InterruptedException {
		while (mySuspended) {
			this.wait();
		}
	}

	public void suspend() {
		mySuspended = true;
	}

	public void start() {
		try {
		
			myModel.startProgram();
			myThread.start();
		} catch (IllegalThreadStateException e) {
			System.out.println("Thread already started");
		}
	}

	public synchronized void resume() {
		mySuspended = false;
		this.notify();
	}

	public void startPause() {
		myView.changeStartPause();
		if (mySuspended) {
			this.resume();
		} else {
			this.suspend();
		}
	}

	public static void main(String[] args) {
		new SimulationController();
	}
}