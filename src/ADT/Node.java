/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

/**
 *
 * @author Kangwenn
 */

public class Node<T> {

  private T data;       //data in the node
  private Node next;    //link to next node

  public Node(T data) {
    this.data = data;
    next = null;
  }

  public Node(T data, Node next) {
    this.data = data;
    this.next = next;
  }

  public void setData(T data) {
    this.data = data;
  }

  public void setNext(Node next) {
    this.next = next;
  }

  public T getData() {
    return data;
  }

  public Node getNext() {
    return next;
  }

}

