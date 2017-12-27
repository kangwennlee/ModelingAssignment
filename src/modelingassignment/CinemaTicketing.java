/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelingassignment;

import ADT.*;
import java.util.Queue;
import java.util.LinkedList;

/**
 *
 * @author Kangwenn
 */
public class CinemaTicketing {
    //random generated time
    static Queue<Integer> interArrivalTime;
    static Queue<Integer> waitingTime;
    static Queue<Integer> serviceTime;
    
    //constants
    
    //stopping condition
    static int NUM_CUST_SERVED = 150;
    static int NUM_OF_COUNTER = 3;
    
    //lists and queue
    PriorityQueueInterface<Event> eventList = new PriorityLinkedQueue<>();
    LinkedList<User> allUser = new LinkedList<>();
    LinkedList<Event> allEvent = new LinkedList<>();
    Queue<User> serviceQueue = new LinkedList<>();
    Counter[] counter = new Counter[NUM_OF_COUNTER];
    
    
    int simulationTime;

    public static void main(String[] args) {
        initializeRandomNumber();
        
    }

    public void scheduleNextUser() {
        User user = new User();
        user.setInterArrivalTime(randomInterarrival());
        user.setArrivalTime(simulationTime+user.getInterArrivalTime());
        user.setServiceTime(randomService());
        Event event = new Event("Arrival",user.getArrivalTime(),user);
        allUser.add(user);
        allEvent.add(event);
        eventList.enqueue(event);
        System.out.println("Scheduled arrival event for next user " + user.toString());
        System.out.println(event);
    }
    
    
    public void simulate(){
        while(allUser.size()<=NUM_CUST_SERVED && eventList.size()!=0){
            Event nextEvent = eventList.dequeue();
            simulationTime = nextEvent.getEventTime();
            if(nextEvent.getType()=="Arrival"){
                arrive(nextEvent.getUser());
            }else if(nextEvent.getType()=="Service"){
                startService(nextEvent.getUser());
            }else{
                depart(nextEvent.getUser());
            }
        }
    }
    
    public void arrive(User user){
        serviceQueue.add(user);
        System.out.println("user" + user.getUserNo() + "enter queue");
        user.setWaitingTime(getMinimumWaitingTime(user));
        user.setServiceBeginTime(user.getArrivalTime()+user.getWaitingTime());
        user.setServiceEndTime(user.getServiceBeginTime()+user.getServiceTime());
        //System.out.println("setting user details" + user.toString());
        System.out.println(user.getCounterServiced());
        //user.getCounterServiced().setServerStatus("busy");
        scheduleEnterServiceEvent(user);
    }
    
    public void scheduleEnterServiceEvent(User user){
        Event event = new Event("Service",user.getServiceBeginTime(),user);
        allEvent.add(event);
        eventList.enqueue(event);
        System.out.println("scheduled service event for user " + user.getUserNo());
        System.out.println(event);
    }
    
    public int getMinimumWaitingTime(User user){
        int minServiceTime = 9999;
        int serviceTime;
        Counter minCounter = null;
        for(int i=0;i<NUM_OF_COUNTER;i++){
            serviceTime = counter[i].getServiceTime();
            if(serviceTime < minServiceTime){
                minServiceTime = serviceTime;
                minCounter = counter[i];
            }
        }
        //user is waiting for this counter
        user.setCounterServiced(minCounter);
        System.out.println("assign user " + user.toString() + "to Counter " +user.getCounterServiced());
        return minServiceTime;
    }
    
    public void startService(User user){
        serviceQueue.poll();
        System.out.println("user " + user.toString() + " at counter " + user.getCounterServiced());
    }

    public static void initializeRandomNumber() {
        interArrivalTime = Acceptance.generateInterarrivalTime(100);
        waitingTime = Acceptance.generateWaitingTime(100);
        serviceTime = Acceptance.generateServiceTime(100);
    }
    
    public static int randomInterarrival() {
        /* Random number generator for interarrival time */
        return interArrivalTime.poll();
    }
    
    public static int randomService() {
        /* Random number generator for service time */
        return serviceTime.poll();
    }

}
