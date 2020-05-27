package servicequeue;
/*
 * Garrett Justice
 * Hussain Alnasser
 */

import java.util.Random;

public class CustomerGenerator extends UniformCustomerGenerator implements Runnable
{
	private int myMaxTimeBetweenCustomers, myRandomTimeBetweenCustomers, myMaxNumberCustomers, myGeneratedCustomers;
	private ServiceQueue myShortestServiceQueue;
	private Thread myThread;
	private boolean myIsRunning;

	public CustomerGenerator(int maxTimeBetweenCustomers, int maxNumberCustomers, ServiceQueueManager serviceQueueManager)
	{
		super(maxTimeBetweenCustomers, serviceQueueManager);
		myMaxTimeBetweenCustomers = maxTimeBetweenCustomers;
		myMaxNumberCustomers = maxNumberCustomers; 
		myIsRunning = true;
		myThread = new Thread(this);
	}


	public long generateTimeBetweenCustomers()
	{
		return super.generateTimeBetweenCustomers();
	}


	public Customer generateCustomer()
	{
		Customer newCustomer = new Customer();
		return newCustomer;
	}

	public void run()
	{
		while (myGeneratedCustomers < myMaxNumberCustomers && myIsRunning)
		{
			myShortestServiceQueue = super.getSQM().determineShortestQueue();
			myShortestServiceQueue.insertCustomer(this.generateCustomer());
			myGeneratedCustomers++;
			try
	        {
	            Thread.sleep(this.generateTimeBetweenCustomers());
	        } catch (InterruptedException e)
	        {
	            System.out.println("Thread derp");
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

	public int getMyMaxTimeBetweenCustomers()
	{
		return myMaxTimeBetweenCustomers;
	}


	
	public void setMaxNumberCustomers(int maxNumberCustomers)
	{
		myMaxNumberCustomers = maxNumberCustomers;
	}
	
	public int getMaxNumberCustomers()
	{
		return myMaxNumberCustomers;
	}
	
	public int getGeneratedCustomers()
	{
		return myGeneratedCustomers;
	}
}
