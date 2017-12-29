/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelingassignment;

import ADT.*;
import java.util.LinkedList;

/**
 *
 * @author Kangwenn
 */
public class CinemaTicketingPriority {

    //random generated time
    static LinkedList<Integer> interArrivalTime;
    static LinkedList<Integer> serviceTime;
    static LinkedList<Double> arrive_late;

    //constants
    //stopping condition
    final static int NUM_CUST_GENERATED = 400;
    final static int NUM_OF_COUNTER = 3;

    //lists and queue
    static PriorityQueueInterface<Event> eventList = new PriorityLinkedQueue<>();
    static LinkedList<User> allUser = new LinkedList<>();
    static LinkedList<Event> allEvent = new LinkedList<>();
    static LinkedList<User> serviceQueue = new LinkedList<>();
    static Counter[] counter = new Counter[NUM_OF_COUNTER];
    static Counter priorityCounter = new Counter();

    static int simulationTime = 0;

    public static void initializeRandomNumber() {
        //
        //Use this for simulation of 3 normal counter and one priority counter
        //scenario1Random1(2.23);
        //scenario1Random2(2.23);
        scenario1Random3(2.23);
        //scenario1Random4(2.23);
        //scenario1Random5(2.23);
        //
    }

    public static void main(String[] args) {
        initializeRandomNumber();
        initialize();
        scheduleNextUser();
        simulate();
        //printEventList();
        //printUserList();
        calculateStatistics();
        
    }

    public static void printEventList() {
        for (int i = 0; i < allEvent.size(); i++) {
            System.out.println(allEvent.get(i));
        }
    }
    
    public static void printUserList() {
        System.out.println("=========================================================================="); // header
        System.out.println("---  No of   --- Arrival --- Time Service --- Service --- Time service ---"); // header
        System.out.println("--- Customer ---  Time   ---    Begin     ---   Time  ---      ends    ---"); // header
        System.out.println("=========================================================================="); // header
        
        for (int i = 0; i < allUser.size(); i++) {
            System.out.printf("--- %8d --- %7d --- %12d --- %7d --- %12d ---\n",allUser.get(i).getUserNo(), allUser.get(i).getArrivalTime(), allUser.get(i).getServiceBeginTime(), allUser.get(i).getServiceTime(), allUser.get(i).getServiceEndTime());
        }
        System.out.println("=========================================================================="); // footer
    }

    public static void calculateStatistics() {
        double totalWaitingTime = 0.0;
        double totalServiceTime = 0.0;
        double totalInterArrivalTime = 0.0;
        double averageWaitingTime;
        double averageServiceTime;
        double averageInterArrivalTime;
        for (int i = 0; i < allUser.size(); i++) {
            totalWaitingTime += allUser.get(i).getWaitingTime();
            totalServiceTime += allUser.get(i).getServiceTime();
            totalInterArrivalTime += allUser.get(i).getInterArrivalTime();
        }
        averageWaitingTime = (totalWaitingTime / allUser.size());
        averageServiceTime = (totalServiceTime / allUser.size());
        averageInterArrivalTime = (totalInterArrivalTime / allUser.size());
        System.out.print("Average Waiting Time: " + averageWaitingTime + "\nAverage Service Time: " + averageServiceTime + "\nAverage InterArrival Time: " + averageInterArrivalTime + "\n");
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
        user.setArrive_late(randomArriveLate());
        Event event = new Event("Arrival", user.getArrivalTime(), user);
        allEvent.add(event);
        eventList.enqueue(event);
        System.out.println("Schedule arrival event for user " + user.getUserNo());
        System.out.println(event);
        System.out.println("");
    }

    public static void simulate() {
        while (simulationTime < 360) {
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
            counter[i].setCounterName("Normal Counter");
        }
        priorityCounter.setCounterName("Priority Counter");
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
        if (user.getWaitingTime() < 0) {
            user.setWaitingTime(0);
        }
        user.setServiceBeginTime(user.getArrivalTime() + user.getWaitingTime());
        user.setServiceEndTime(user.getServiceBeginTime() + user.getServiceTime());
        Event event = new Event("Service", user.getServiceBeginTime(), user);
        allEvent.add(event);
        allUser.add(user);
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
        if (user.getArrive_late() >= 0.8) {
            minNextServiceTimeStart = priorityCounter.getServiceEndTime();
            minCounter = priorityCounter;
        } else {
            for (i = 0; i < NUM_OF_COUNTER; i++) {
                nextServiceTimeStart = counter[i].getServiceEndTime();
                if (nextServiceTimeStart < minNextServiceTimeStart) {
                    minNextServiceTimeStart = nextServiceTimeStart;
                    minCounter = counter[i];
                }
            }
        }
        //user is waiting for this counter
        minCounter.setServiceEndTime(minNextServiceTimeStart + user.getServiceTime());
        user.setServicingCounter(minCounter);
        System.out.println("assign user " + user.getUserNo() + " to Counter " + user.getServicingCounter().getCounterNo() + " " + user.getServicingCounter().getCounterName());
        return minNextServiceTimeStart;
    }

    public static void startService() {
        User user = serviceQueue.poll();
        System.out.println("user " + user.getUserNo() + " at counter " + user.getServicingCounter().getCounterNo() + " " + user.getServicingCounter().getCounterName() + " on time " + user.getServiceBeginTime());
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

    public static int randomInterarrival() {
        /* Random number generator for interarrival time */
        return interArrivalTime.poll();
    }

    public static int randomService() {
        /* Random number generator for service time */
        return serviceTime.poll();
    }

    public static double randomArriveLate() {
        return arrive_late.poll();
    }
    
    public static void scenario1Random1(double lamda){
        //initialize(z0,a,c,m,numberOfRandomNumber,lamda)
        Acceptance.initialize(7, 5, 3, 64, NUM_CUST_GENERATED, 0.91);
        interArrivalTime = Acceptance.generateTime();
        Acceptance.initialize(37, 21, 15, 64, NUM_CUST_GENERATED, lamda);
        serviceTime = Acceptance.generateTime();
        Acceptance.initialize(7, 145, 33, 64, NUM_CUST_GENERATED, 1);
        arrive_late = Acceptance.generateRandomNumber();
    }
    public static void scenario1Random2(double lamda){
        Acceptance.initialize(37, 333, 83, 88, NUM_CUST_GENERATED, 0.91);
        interArrivalTime = Acceptance.generateTime();
        Acceptance.initialize(5, 137, 1, 256, NUM_CUST_GENERATED, lamda);
        serviceTime = Acceptance.generateTime();
        Acceptance.initialize(7, 133, 37, 72, NUM_CUST_GENERATED, 1);
        arrive_late = Acceptance.generateRandomNumber();
    }
    public static void scenario1Random3(double lamda){
        Acceptance.initialize(61, 149, 37, 128, NUM_CUST_GENERATED, 0.91);
        interArrivalTime = Acceptance.generateTime();
        Acceptance.initialize(37, 29, 7, 128, NUM_CUST_GENERATED, lamda);
        serviceTime = Acceptance.generateTime();
        Acceptance.initialize(17, 101, 23, 100, NUM_CUST_GENERATED, 1);
        arrive_late = Acceptance.generateRandomNumber();
    }
    public static void scenario1Random4(double lamda){
        Acceptance.initialize(23, 245, 61, 64, NUM_CUST_GENERATED, 0.91);
        interArrivalTime = Acceptance.generateTime();
        Acceptance.initialize(49, 117, 29, 128, NUM_CUST_GENERATED, lamda);
        serviceTime = Acceptance.generateTime();
        Acceptance.initialize(37, 137, 61, 112, NUM_CUST_GENERATED, 1);
        arrive_late = Acceptance.generateRandomNumber();
    }
    public static void scenario1Random5(double lamda){
        Acceptance.initialize(17, 37, 9, 64, NUM_CUST_GENERATED, 0.91);
        interArrivalTime = Acceptance.generateTime();
        Acceptance.initialize(17, 85, 21, 256, NUM_CUST_GENERATED, lamda);
        serviceTime = Acceptance.generateTime();
        Acceptance.initialize(5, 25, 53, 172, NUM_CUST_GENERATED, 1);
        arrive_late = Acceptance.generateRandomNumber();
    }

}
