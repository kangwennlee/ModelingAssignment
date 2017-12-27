/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

/**
 *
 * @author kangw
 * @param <T>
 */
public class SortedLinkList<T extends Comparable<? super T>> implements SortedListInterface<T> {

    private Node firstNode;
    private int length;

    public SortedLinkList() {
        firstNode = null;
        length = 0;
    }

    @Override
    public boolean add(T newEntry) {
        firstNode = add(newEntry, firstNode);
        length++;
        return true;
    }

    private Node add(T newEntry, Node currNode) {
        if ((currNode == null) || newEntry.compareTo((T) currNode.getData()) < 0) {
            currNode = new Node(newEntry, currNode);
        } else {
            Node nodeAfter = add(newEntry, currNode.getNext());
            currNode.setNext(nodeAfter);
        }
        return currNode;
    }

    @Override
    public boolean remove(T anEntry) {
        return anEntry == remove(getPosition(anEntry));
    }

    @Override
    public int getPosition(T anEntry) {
        Node currNode = firstNode;
        int position = -1;
        for (int i = 1; i <= length; i++) {
            if (currNode.getData().equals(anEntry)) {
                position = i;
                break;
            }
            if(i==length && !currNode.getData().equals(anEntry))
                break;
            currNode = currNode.getNext();
        }
        return position;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= length)) {
            Node currentNode = firstNode;
            for (int i = 0; i < givenPosition - 1; ++i) {
                currentNode = currentNode.getNext();		// advance currentNode to next node
            }
            result = (T) currentNode.getData();	// currentNode is pointing to the node at givenPosition
        }

        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        Node tempNode = firstNode;
        int pos = 1;

        while (!found && (tempNode != null)) {
            if (anEntry.compareTo((T) tempNode.getData()) <= 0) {
                found = true;
            } else {
                tempNode = tempNode.getNext();
                pos++;
            }
        }
        return tempNode != null && tempNode.getData().equals(anEntry);
    }

    @Override
    public T remove(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= length)) {
            if (givenPosition == 1) {      	// CASE 1: remove first entry
                result = (T) firstNode.getData();     	// save entry to be removed
                firstNode = firstNode.getNext();		// update firstNode to point to the next node
            } else {                         	// CASE 2: remove interior entry or last entry
                Node nodeBefore = firstNode;
                for (int i = 1; i < givenPosition - 1; ++i) {
                    nodeBefore = nodeBefore.getNext();		// advance nodeBefore to its next node
                }
                result = (T) nodeBefore.getNext().getData();  	// save entry to be removed	
                nodeBefore.setNext(nodeBefore.getNext().getNext());	// make node before point to node after the 
            } 															// one to be deleted (to disconnect node from chain)

            length--;
        }

        return result;
    }

    @Override
    public final void clear() {
        firstNode = null;
        length = 0;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return (length == 0);
    }

    @Override
    public String toString() {
        String outputStr = "";
        Node currentNode = firstNode;
        while (currentNode != null) {
            outputStr += currentNode.getData() + "\n";;
            currentNode = currentNode.getNext();
        }
        return outputStr;
    }

}
