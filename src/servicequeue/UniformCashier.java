package servicequeue;

/*
 * Garrett Justice
 */
import java.util.Random;

public class UniformCashier
{
    private ServiceQueue myServiceQueue;
    private int myRandomServiceTime;
    private int DelayTime;
    private Random myRand;
    
    public UniformCashier(int maxTime, ServiceQueue serviceQueue)
    {
        myServiceQueue = serviceQueue;
        DelayTime = maxTime;
        myRand = new Random();
    }
    
    public int generateServiceTime()
    {
        myRandomServiceTime = myRand.nextInt( DelayTime);
        return myRandomServiceTime;
    }
    
    public ServiceQueue getServiceQueue()
    {
        return myServiceQueue;
    }
    
    public int getRandomServiceTime()
    {
        return myRandomServiceTime;
    }
}
