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
    static Queue<Integer> serviceTime;

    //constants
    //stopping condition
    final static int NUM_CUST_SERVED = 150;
    final static int NUM_OF_COUNTER = 2;

    //lists and queue
    static PriorityQueueInterface<Event> eventList = new PriorityLinkedQueue<>();
    static LinkedList<User> allUser = new LinkedList<>();
    static LinkedList<Event> allEvent = new LinkedList<>();
    static LinkedList<User> serviceQueue = new LinkedList<>();
    static Counter[] counter = new Counter[NUM_OF_COUNTER];
    
    static int simulationTime = 0;
    
    public static void main(String[] args) {
        initializeRandomNumber();
        initialize();
        scheduleNextUser();
        simulate();
        //printEventList();
    }
    
    public static void printEventList() {
        for (int i = 0; i < allEvent.size(); i++) {
            System.out.println(allEvent.get(i));
        }
    }
    
    public static void printServiceQueue() {
        for (int i = 0; i < serviceQueue.size(); i++) {
            System.out.print(serviceQueue.get(i).getUserNo() + ", ");
        }
        System.out.println("");
    }
    
    public static void scheduleNextUser() {
        User user = new User();
        user.setInterArrivalTime(randomInterarrival());
        user.setArrivalTime(simulationTime + user.getInterArrivalTime());
        user.setServiceTime(randomService());
        Event event = new Event("Arrival", user.getArrivalTime(), user);
        allUser.add(user);
        allEvent.add(event);
        eventList.enqueue(event);
        System.out.println("Schedule arrival event for user " + user.getUserNo());
        System.out.println(event);
        System.out.println("");
    }
    
    public static void simulate() {
        while (allUser.size() <= NUM_CUST_SERVED && eventList.size() != 0) {
            Event nextEvent = eventList.dequeue();
            simulationTime = nextEvent.getEventTime();
            if (nextEvent.getType() == "Arrival") {
                arrive(nextEvent.getUser());
            } else if (nextEvent.getType() == "Service") {
                startService();
            } else {
                depart(nextEvent.getUser());
            }
        }
    }
    
    public static void initialize() {
        for (int i = 0; i < NUM_OF_COUNTER; i++) {
            counter[i] = new Counter();
            counter[i].setServiceEndTime(1);
        }
    }
    
    public static void arrive(User user) {
        scheduleNextUser();
        serviceQueue.add(user);
        System.out.println("Add user " + user.getUserNo() + " into queue");
        printServiceQueue();
        System.out.println("");
        scheduleEnterServiceEvent(user);
    }
    
    public static void scheduleEnterServiceEvent(User user) {
        user.setWaitingTime(getMinServiceTime(user) - user.getArrivalTime());
        if(user.getWaitingTime()<0)
            user.setWaitingTime(0);
        user.setServiceBeginTime(user.getArrivalTime() + user.getWaitingTime());
        user.setServiceEndTime(user.getServiceBeginTime() + user.getServiceTime());
        //System.out.println("setting user details" + user.toString());
        //System.out.println(user.getCounterServiced());
        //user.getCounterServiced().setServerStatus("busy");
        Event event = new Event("Service", user.getServiceBeginTime(), user);
        allEvent.add(event);
        eventList.enqueue(event);
        //System.out.println("scheduled service event for user " + user.getUserNo());
        System.out.println(event);
        System.out.println("");
    }
    
    public static int getMinServiceTime(User user) {
        int minNextServiceTimeStart = 9999;
        int nextServiceTimeStart = 0;
        Counter minCounter = null;
        int i;
        for (i = 0; i < NUM_OF_COUNTER; i++) {
            nextServiceTimeStart = counter[i].getServiceEndTime();
            if (nextServiceTimeStart < minNextServiceTimeStart) {
                minNextServiceTimeStart = nextServiceTimeStart;
                minCounter = counter[i];
            }
        }
        //user is waiting for this counter
        minCounter.setServiceEndTime(minNextServiceTimeStart + user.getServiceTime());
        user.setServicingCounter(minCounter);
        System.out.println("assign user " + user.getUserNo() + " to Counter " + user.getServicingCounter().getCounterNo());
        return minNextServiceTimeStart;
    }
    
    public static void startService() {
        User user = serviceQueue.poll();
        System.out.println("user " + user.getUserNo() + " at counter " + user.getServicingCounter().getCounterNo() + " on " + user.getServiceBeginTime());
        scheduleDepartureEvent(user);
    }
    
    public static void scheduleDepartureEvent(User user) {
        Event event = new Event("Departure", user.getServiceEndTime(), user);
        allEvent.add(event);
        eventList.enqueue(event);
        System.out.println(event);
        System.out.println("");
    }
    
    public static void depart(User user) {
        user.getServicingCounter().setServerStatus("idle");
    }
    
    public static void initializeRandomNumber() {
        interArrivalTime = Acceptance.generateInterarrivalTime(NUM_CUST_SERVED * 2);
        //System.out.println("Inter Arrival Time: "+interArrivalTime);
        //printFrequency(interArrivalTime);
        serviceTime = Acceptance.generateServiceTime(NUM_CUST_SERVED * 2);
        //System.out.println("Service Time: "+serviceTime);
        //printFrequency(serviceTime);
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
