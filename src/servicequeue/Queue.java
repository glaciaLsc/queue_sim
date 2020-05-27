package servicequeue;

/*
 * Garrett Justice
 * Hussain Alnasser
 */

import java.util.LinkedList;

public class Queue<T>
{
    LinkedList<T> myQueue;
    
    public Queue()
    {
        myQueue = new LinkedList<T>();
    }

    public void enqueue(T enqueueMe)
    {
        myQueue.addFirst(enqueueMe);
    }
  
    public T dequeue()
    {
        return myQueue.pollLast();
    }
    
 
    public T peek()
    {
        return myQueue.peekLast();
    }

    public boolean hasNext()
    {
        return !myQueue.isEmpty();
    }
}
