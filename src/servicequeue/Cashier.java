package servicequeue;

/*
 * Garrett Justice
 * Hussain Alnasser
 */

public class Cashier extends UniformCashier implements Runnable
{
	private int myMaxTimeOfService, myDelay;
	private ServiceQueue myServiceQueue;
	private Thread myThread;
	private Customer myCustomer;
	private boolean myIsRunning;

	public Cashier(int maxTime, int checkTime, ServiceQueue serviceQueue)
	{
		super(maxTime, serviceQueue);
		myMaxTimeOfService = maxTime;
		myDelay = checkTime;
		myServiceQueue = serviceQueue;
		myIsRunning = true;
		myThread = new Thread(this);
	}

	public int serveCustomer()
	{
		if (myServiceQueue.getTotalCustomersInLine() == 0)
		{
			return 0;
		} else
		{
			myCustomer = myServiceQueue.serveCustomer();
			myServiceQueue.addToWaitTime((int) (System.currentTimeMillis() - myCustomer.getMyEntryTime()));
			myServiceQueue.addToServiceTime(super.getRandomServiceTime());
			return generateServiceTime();
		}
	}

	
	public int generateServiceTime()
	{
		return super.generateServiceTime();
	}

	@Override
	public void run()
	{
		while (myIsRunning)
		{
			this.serveCustomer();
			myServiceQueue.addToServiceTime(this.serveCustomer());
			try
			{
				Thread.sleep(this.generateServiceTime());
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void start()
	{
		myIsRunning = true;
		try
		{
			myThread.start();
		} catch (IllegalThreadStateException e)
		{
			System.out.println("Thread already started");
		}
	}


	public void stop()
	{
		myIsRunning = false;
	}
	
	public void setMaxTime(int maxTime)
	{
		myMaxTimeOfService = maxTime;
	}

	public void setDelay(int delay)
	{
		myDelay = delay;
	}

	public void setServiceQueue(ServiceQueue serviceQueue)
	{
		myServiceQueue = serviceQueue;
	}

	public int getMaxTime()
	{
		return myMaxTimeOfService;
	}

	public int getDelay()
	{
		return myDelay;
	}

	public ServiceQueue getServiceQueue()
	{
		return myServiceQueue;
	}
}
