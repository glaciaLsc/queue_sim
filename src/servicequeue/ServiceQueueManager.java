package servicequeue;

/*
 * Hussain Alnasser
 */

import java.util.ArrayList;

public class ServiceQueueManager
{
    public static final int MAX_NUMBER_OF_QUEUES = 5;
    private int myNumberOfServiceLines;
    private ServiceQueue[] myServiceQueues;
    private Cashier[] myCashiers;
    private CustomerGenerator myCustomerGenerator;
    private long myTotalWaitTime, myTotalServiceTime, myTotalIdleTime, myAverageIdleTime, myPresentTime,
    	myStartTime;
    private double myAverageWaitTime;
    private double myAverageServiceTime;
    private ArrayList<Integer> myTotalsArrayList; 
    
 
    public ServiceQueueManager(int serviceLines, int maxNumberCustomers, int maxTimeBetweenCustomers, int maxTimeService, int checkTime)
    {
        myNumberOfServiceLines = serviceLines;
        myServiceQueues = new ServiceQueue[myNumberOfServiceLines];
        myCashiers = new Cashier[myNumberOfServiceLines];
        myCustomerGenerator = new CustomerGenerator(maxTimeBetweenCustomers, maxNumberCustomers, this);
        
        for(int i = 0; i < myNumberOfServiceLines; i++)
        {
        	myServiceQueues[i] = new ServiceQueue();
        	myServiceQueues[i].setQueueID(i);
        	myCashiers[i] = new Cashier(maxTimeService, checkTime, myServiceQueues[i]);
        }
    }
    
  
    public ServiceQueueManager()
    {
    	myNumberOfServiceLines = 3;
    	myServiceQueues = new ServiceQueue[myNumberOfServiceLines];
    	myCashiers = new Cashier[myNumberOfServiceLines];
    	myCustomerGenerator = new CustomerGenerator(100, 500, this);
    	
    	for(int i = 0; i < myNumberOfServiceLines; i++)
    	{
    		myServiceQueues[i] = new ServiceQueue();
    		myCashiers[i] = new Cashier(100, 100, myServiceQueues[i]);
    	}
    }
    

    public int totalServedSoFar()
    {
        int total = 0;
        for(ServiceQueue sq : myServiceQueues)
        {
            total += sq.getTotalServed();
        }
        
        return total;
    }
    

    public int totalWaitTime()
    {
        int total = 0;
        for(ServiceQueue sq : myServiceQueues)
        {
            total += sq.getTotalWait();
        }
        
        return total;
    }
 
    public int totalServiceTime()
    {
        int total = 0;
        for(ServiceQueue sq : myServiceQueues)
        {
            total += sq.getTotalService();
        }
        
        return total;
    }
    

    public ServiceQueue determineShortestQueue()
    {
    	ServiceQueue shortestQueue = myServiceQueues[0];
    	int totalNumberCustomersInQueue = myServiceQueues[0].getTotalCustomersInLine();
    	for(ServiceQueue sq : myServiceQueues)
    	{
    		if(sq.getTotalCustomersInLine() < totalNumberCustomersInQueue)
    		{
    			totalNumberCustomersInQueue = sq.getTotalCustomersInLine();
    			shortestQueue = sq;
    		}
    	}
        return shortestQueue;
    }

    public int averageWaitTime()
    {
        int average = 0;
        for(ServiceQueue sq : myServiceQueues)
        {
            average += sq.getTotalWait();
        }
        average /= myServiceQueues.length;
        return average;
    }
    

    public int averageServiceTime()
    {
        int average = 0;
        for(ServiceQueue sq : myServiceQueues)
        {
            average += sq.getTotalService();
        }
        average /= myServiceQueues.length;
        return average;
    }
  
    public int averageIdleTime()
    {
        int average = 0;
        for(ServiceQueue sq : myServiceQueues)
        {
            average += sq.getTotalIdle();
        }
        average /= myServiceQueues.length;
        return average;
    }
    

    public void startProgram()
    {
    	for(int i = 0; i < myNumberOfServiceLines; i++)
    	{
    		myCashiers[i].start();
    	}
    	
    	myCustomerGenerator.start();
    }
    
    public void stopProgram()
    {
    	for(int i = 0; i < myNumberOfServiceLines; i++)
    	{
    		myCashiers[i].stop();
    	}
    	
    	myCustomerGenerator.stop();
    }

    public ArrayList<Integer> getTotals()
    {
    	myTotalsArrayList = new ArrayList<Integer>(6);
    	
    	myTotalsArrayList.add(this.totalServedSoFar());
    	myTotalsArrayList.add(this.totalWaitTime());
    	myTotalsArrayList.add(this.totalServiceTime());
    	myTotalsArrayList.add(this.averageWaitTime());
    	myTotalsArrayList.add(this.averageServiceTime());
    	myTotalsArrayList.add(this.averageIdleTime());
    	
    	return myTotalsArrayList;
    }
    

    public ServiceQueue[] getAllServiceQueues()
    {
    	return myServiceQueues;
    }
    
    public int getNumberServiceLines()
    {
    	return myNumberOfServiceLines;
    }
    
    public CustomerGenerator getCustomerGenerator()
    {
    	return myCustomerGenerator;
    }
}
