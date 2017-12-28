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
public class CinemaTicketing {

    //random generated time
    static LinkedList<Integer> interArrivalTime;
    static LinkedList<Integer> serviceTime;

    //constants
    //stopping condition
    final static int NUM_CUST_SERVED = 400;
    final static int NUM_OF_COUNTER = 4;

    //lists and queue
    static PriorityQueueInterface<Event> eventList = new PriorityLinkedQueue<>();
    static LinkedList<User> allUser = new LinkedList<>();
    static LinkedList<Event> allEvent = new LinkedList<>();
    static LinkedList<User> serviceQueue = new LinkedList<>();
    static Counter[] counter = new Counter[NUM_OF_COUNTER];

    static int simulationTime = 0;

    public static void initializeRandomNumber() {
        //initialize(z0,a,c,m,numberOfRandomNumber,lamda)
        //
        //4 Counter Scenario Initialization for Inter Arrival Time
        //Acceptance.initialize(7, 5, 3, 64, NUM_CUST_SERVED, 0.91);
        //Acceptance.initialize(37, 333, 83, 88, NUM_CUST_SERVED, 0.91);
        //Acceptance.initialize(61, 149, 37, 128, NUM_CUST_SERVED, 0.91);
        //Acceptance.initialize(23, 245, 61, 64, NUM_CUST_SERVED, 0.91);
        Acceptance.initialize(17, 37, 9, 64, NUM_CUST_SERVED, 0.91);
        //
        interArrivalTime = Acceptance.generateTime();
        //
        //4 Counter Scenario Initialization for Service Time
        //Acceptance.initialize(37, 21, 15, 64, NUM_CUST_SERVED, 2.23);
        //Acceptance.initialize(5, 137, 1, 256, NUM_CUST_SERVED, 2.23);
        //Acceptance.initialize(37, 29, 7, 128, NUM_CUST_SERVED, 2.23);
        //Acceptance.initialize(49, 117, 29, 128, NUM_CUST_SERVED, 2.23);
        //Acceptance.initialize(17, 85, 21, 256, NUM_CUST_SERVED, 2.23);
        //
        //
        //3 Counter + 10% faster Service Time Scenario Initialization for Service Time
        //Acceptance.initialize(37, 21, 15, 64, NUM_CUST_SERVED, 2.007);
        //Acceptance.initialize(5, 137, 1, 256, NUM_CUST_SERVED, 2.007);
        //Acceptance.initialize(37, 29, 7, 128, NUM_CUST_SERVED, 2.007);
        //Acceptance.initialize(49, 117, 29, 128, NUM_CUST_SERVED, 2.007);
        Acceptance.initialize(17, 85, 21, 256, NUM_CUST_SERVED, 2.007);
        //
        serviceTime = Acceptance.generateTime();
    }

    public static void main(String[] args) {
        initializeRandomNumber();
        initialize();
        scheduleNextUser();
        simulate();
        //printEventList();
        calculateStatistics(); //Calculate average waiting time, average service time and average inter arrival time
        //PointEstimate.calculatePointEstimate();
    }

    public static void printEventList() {
        for (int i = 0; i < allEvent.size(); i++) {
            System.out.println(allEvent.get(i));
        }
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
        averageWaitingTime = (totalWaitingTime / NUM_CUST_SERVED);
        averageServiceTime = (totalServiceTime / NUM_CUST_SERVED);
        averageInterArrivalTime = (totalInterArrivalTime / NUM_CUST_SERVED);
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
        user.setInterArrivalTime(randomInterarrival()); //assign a random interarrival time to the user
        user.setArrivalTime(simulationTime + user.getInterArrivalTime()); //set the arrival time
        user.setServiceTime(randomService()); //assign a random service time to the user
        Event event = new Event("Arrival", user.getArrivalTime(), user); //create an arrival event for the user
        allEvent.add(event);
        eventList.enqueue(event); //add the event into the event list
        System.out.println("Schedule arrival event for user " + user.getUserNo());
        System.out.println(event);
        System.out.println("");
    }

    public static void simulate() {
        //run the simulation until the simulation time reach 360 minutes, which is 6 hours.
        while (simulationTime < 360) {
            Event nextEvent = eventList.dequeue(); //dequeue the event out from the eventList
            simulationTime = nextEvent.getEventTime(); //update simulationTime
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
        serviceQueue.add(user); //add user into the service queue
        System.out.println("Add user " + user.getUserNo() + " into queue");
        printServiceQueue();
        System.out.println("");
        scheduleEnterServiceEvent(user); //after the user arrive, create a service event for the user
    }

    public static void scheduleEnterServiceEvent(User user) {
        user.setWaitingTime(getMinServiceTime(user) - user.getArrivalTime());
        if (user.getWaitingTime() < 0) {
            //If the user arrive later after all the counter end their service,
            //waiting time will be negative, arrival time > last user's service end time
            //thus reset user's waiting time to 0
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
        //call getMinServiceTime to retrive next earliest possible service start time for the user.
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
        //user is assigned to this counter
        minCounter.setServiceEndTime(minNextServiceTimeStart + user.getServiceTime());
        user.setServicingCounter(minCounter);
        System.out.println("assign user " + user.getUserNo() + " to Counter " + user.getServicingCounter().getCounterNo());
        return minNextServiceTimeStart;
    }

    public static void startService() {
        User user = serviceQueue.poll();
        System.out.println("user " + user.getUserNo() + " at counter " + user.getServicingCounter().getCounterNo() + " on time " + user.getServiceBeginTime());
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

}
