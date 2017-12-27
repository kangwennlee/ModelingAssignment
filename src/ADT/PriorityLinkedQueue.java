/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;
/**
 *
 * @author Kangwenn
 * @param <T>
 */
public class PriorityLinkedQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T> {
    protected Node<T> front; //pointing to first node in the queue
    protected Node<T> rear;  //pointing to last node in the queue
    protected int numElements = 0; //total number of elements in the queue
    
    public PriorityLinkedQueue()
    {
        front = null;
        rear = null;
    }

    @Override
    public void enqueue(T element){
        front = enqueue(element, front);
        numElements++;
    }
    
    //enqueue with priority
    private Node enqueue(T newEntry, Node currNode) {
        //base case(when reached end of list)
        if (currNode == null){
            currNode = new Node(newEntry);
            rear = currNode;
        }
        //if not end of list and newEntry higher priority than currNode
        else if (newEntry.compareTo((T) currNode.getData()) < 0){
            currNode = new Node(newEntry, currNode);
            
        }
        /*if not end of list and newEntry lower priority than currNode,
        then continue to iterate*/
        else {
            Node nodeAfter = enqueue(newEntry, currNode.getNext());
            currNode.setNext(nodeAfter);
        }
        return currNode;
    }

    /*remove the entry from the queue by using first-in, first-out(FIFO)principle
    according to their priority, entry with higher priority will be removed first, 
    however, entries with same priorty will be removed according to FIFO principle.
    Since the entries had already been sorted according to their priority,
    thus with FIFO principle, the first entry will always be removed first */
    @Override
    public T dequeue() throws QueueUnderflowException {
        //make sure no removing from empty queue
        if(isEmpty())
            throw new QueueUnderflowException("Dequeue attempted on empty queue.");
        else
        {
            T element;
            element = front.getData();
            front = front.getNext();
            /*if there is no more next node, the queue is now empty, 
            thus front and rear node shall be assigned with null
            */
            if(front == null)
                rear = null;
            numElements--;
            return element;
        }
    }

    //retrive the data stored in the first node from the queue
    @Override
    public T getFront() {
        return front.getData();
    }
    
    //check if the queue is empty, by checking the total number of elements in the queue
    @Override
    public boolean isEmpty()
    // Returns true if this queue is empty; otherwise, returns false
    {
        return (numElements==0);
    }
    
    //get the total number of elements in the queue
    @Override
    public int size() {
        return numElements;
    }

    //removing all elements from the queue
    @Override
    public void clear() {
        front = null;
        rear = null;
        numElements=0;
    }

    //remove an entry from any position from the queue
    @Override
    public void remove(T entry) {
        //make sure queue not empty
        if(isEmpty())
            throw new QueueUnderflowException("Dequeue attempted on empty queue.");
        //make sure queue contain this entry
        else if(!contains(entry)){
            throw new QueueUnderflowException("This queue does not contains element " + entry);
        }
        /*retrieve the entry node and remove it from queue
        if desired node is first node in queue, then it is same as dequeuing the entry,
        thus dequeue operation is operated
        else if the desired node is from the middle or end of queue,
        then traversal of the queue is done until the desired node is found 
        */
        else
        {
            if (front.getData() == entry){
                dequeue();
            }
            else{
                front = remove(entry, front);
                numElements--;
            }
            
        }
    }
    /*traverse through queue until desired node is found, 
    then replace desired node with the node after it, 
    and assign the node after it to the node before it,
    the recursion will stop once it reach the first node
    and the first node will bbe returned as reference to the new queue
    */
    private Node remove(T entry, Node currNode) {
        //if reached desired Node, retrieve the Node after it
        if(currNode.getData() == entry){
            currNode = currNode.getNext();
        }
        /*continue to traverse through the queue
        if (nodeAfter == null), the node after the desired node is null
        that means the desired node is the last node in the queue
        thus the rear node will be assigned to the node before the desired node*/
        else{
            Node nodeAfter = remove(entry, currNode.getNext());
            if (nodeAfter == null){
                rear = currNode;
            }
            currNode.setNext(nodeAfter);
        }
        return currNode;
    }

    //check if the queue contains this entry
    @Override
    public boolean contains(T entry) {
        boolean contain = false;
        Node tempNode = front;
        //while not end of list, check if queue contain element
        while(tempNode != null){
            if (tempNode.getData() == entry){
                contain = true;
            }
            tempNode = tempNode.getNext();
        }
        return contain;
    }

    //retrive the element in the queue with its position in the queue
    @Override
    public T getEntry(int position) {
        T result = null;

        //make sure the position is between valided range in queue(from 1 to numElements)
        /*then traverse the queue with "position" times of iteration,
        retrieve the data from the current pointing node and return the data retrieved*/
        if ((position >= 1) && (position <= numElements)) {
            Node currentNode = front;
            for (int i = 0; i < position - 1; ++i) {
                currentNode = currentNode.getNext();		
            }
            result = (T) currentNode.getData();	
        }

        return result;
    }
    
    
}
