package servicequeue;

/*
* Hussain Alnasser
 */
public class ServiceQueue
{
	private int queueid;
    private int myNumberCustomersServedSoFar;
    private int myNumberCustomersInLine;
    private int myTotalWaitTime;
    private int myTotalServiceTime;
    private int myTotalIdleTime;
    private Queue<Customer> myQueue;

    public void setQueueID(int queueid){
    	this.queueid = queueid;
    }
    
    public int getQueueId(){
    	return this.queueid;
    }
    public ServiceQueue()
    {
        myQueue = new Queue<Customer>();
        myNumberCustomersServedSoFar = 0;
        myNumberCustomersInLine = 0;
        myTotalWaitTime = 0;
        myTotalServiceTime = 0;
        myTotalIdleTime = 0;
    }

    public void addToElapsedTime(int time)
    {
        
    }

    public void addToIdleTime(int time)
    {
        myTotalIdleTime += time;
    }

    public void addToWaitTime(int time)
    {
        myTotalWaitTime += time;
    }

    public void addToServiceTime(int time)
    {
        myTotalServiceTime += time;
    }

    public void insertCustomer(Customer newCustomer)
    {
        myQueue.enqueue(newCustomer);
        myNumberCustomersInLine++;
    }

    public Customer serveCustomer()
    {
        if (myQueue.hasNext())
        {
        	myNumberCustomersInLine--;
        	myNumberCustomersServedSoFar++;
            return myQueue.dequeue();
        }
        return null;
    }

    public int averageWaitTime()
    {
        return myTotalWaitTime / myNumberCustomersServedSoFar;
    }

    public long averageServiceTime()
    {
        return myTotalServiceTime / myNumberCustomersServedSoFar;
    }

    public long averageIdleTime()
    {
        return myTotalIdleTime / myNumberCustomersServedSoFar;
    }
    
    
    public int getTotalServed()
    {
        return myNumberCustomersServedSoFar;
    }
    
    public int getTotalWait()
    {
        return myTotalWaitTime;
    }
    
    public int getTotalService()
    {
        return myTotalServiceTime;
    }
    
    public int getTotalIdle()
    {
        return myTotalIdleTime;
    }
    
    public int getTotalCustomersInLine()
    {
    	return myNumberCustomersInLine;
    }
    
    public int getNumberCustomersServedSoFar()
    {
    	return this.myNumberCustomersServedSoFar;
    }
}
