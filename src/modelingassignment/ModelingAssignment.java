/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelingassignment;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Kangwenn
 */
public class ModelingAssignment {

    public static final int CUST_SERVED = 100;
    public static final int BUSY = 1;
    public static final int IDLE = 0;
    
    static int num_cust_served;
    static int next_event_type, num_custs_delayed, num_delays_required, num_events, num_in_q;
    static int[] server_status;
    static int cumulative_arrival_time, cumulative_service_time; //extra variable created
    static double area_num_in_q, area_server_status, mean_interarrival, mean_service, sim_time, time_last_event, total_of_delays;
    static LinkedList<Integer> arrival_time, service_time; //for simulation table

    static double[] time_arrival, time_next_event;
    static int server;

    static Queue<Integer> interArrivalTime;
    static Queue<Integer> waitingTime;
    static Queue<Integer> serviceTime;

    public static void main(String[] args) {
        initializeRandomNumber();
        initializeSimulation();
        simulate();
        report();
    }

    public static void initializeSimulation() {
        /* Initialization function. */
        time_arrival = new double[CUST_SERVED + 1];
        time_next_event = new double[3];

        num_events = 2; //departure and arrival event
        num_delays_required = 100;

        // report heading
        System.out.println("Single-server queueing system\n");
        System.out.printf("Number of customers%14d", num_delays_required);

        /* Initialize the simulation clock. */
        sim_time = 0.0;
        /* Initialize the state variables. */
        server_status = new int[4];
        for(int i=0;i<3;i++)
            server_status[i]=IDLE;
        num_in_q = 0;
        time_last_event = 0.0;
        /* Initialize the statistical counters. */
        num_custs_delayed = 0;
        total_of_delays = 0.0;
        area_num_in_q = 0.0;
        area_server_status = 0.0;

        // for simulation table
        arrival_time = new LinkedList<>();
        service_time = new LinkedList<>();
        cumulative_arrival_time = 0;
        cumulative_service_time = 0;

        /* Initialize event list. Since no customers are present, the departure
(service completion) event is eliminated from consideration. */
        time_next_event[1] = sim_time + randomInterarrival();
        time_next_event[2] = 1.0e+30;
    }

    public static void simulate() {
        while(num_cust_served<CUST_SERVED){
            /* Determine the next event. */
            timing();
            /* Update time-average statistical accumulators. */
            update_time_avg_stats();
            /* Invoke the appropriate event function. */
            switch (next_event_type) {
                case 1:
                    arrive();
                    break;
                case 2:
                    depart();
                    break;
        }
        }
    }
    
    public static void timing() {
        /* Timing function. */
        int i;
        double min_time_next_event = 1.0e+29;
        next_event_type = 0;
        /* Determine the event type of the next event to occur. */
        for (i = 1; i <= num_events; ++i) {
            if (time_next_event[i] < min_time_next_event) {
                min_time_next_event = time_next_event[i];
                next_event_type = i;
            }
        }
        /* Check to see whether the event list is empty. */
        if (next_event_type == 0) {
            /* The event list is empty, so stop the simulation. */
            System.out.printf("\nEvent list empty at time %f", sim_time);
            System.exit(1);
        }
        /* The event list is not empty, so advance the simulation clock. */
        sim_time = min_time_next_event;
    }
    
    public static void arrive() {
        /* Arrival event function. */
        double delay;
        boolean idle;
        idle=false;
        server = 0;
        /* Schedule next arrival. */
        time_next_event[1] = sim_time + randomInterarrival();
        /* Check to see whether server is busy. */
        while(idle==false && server<=3)
        {
            if(server_status[server]==BUSY)
                server++;
            else
                idle=true;
        }
        if(idle=true)
        {
            /* Server is idle, so arriving customer has a delay of zero. (The
            following two statements are for program clarity and do not affect
            the results of the simulation.) */
            delay = 0.0;
            total_of_delays += delay;
            /* Increment the number of customers delayed, and make server busy. */
            ++num_custs_delayed;
            server_status[server] = BUSY;
            /* Schedule a departure (service completion). */
            time_next_event[2] = sim_time + randomService();
        }else
        {
            /* Server is busy, so increment number of customers in queue. */
            ++num_in_q;
            /* Check to see whether an overflow condition exists. */
//            if (num_in_q > Q_LIMIT) {
//                /* The queue has overflowed, so stop the simulation. */
//                System.out.printf("\nOverflow of the array time_arrival at");
//                System.out.printf(" time %f", sim_time);
//                System.exit(2);
//            }
            /* There is still room in the queue, so store the time of arrival of the
            arriving customer at the (new) end of time_arrival. */
            time_arrival[num_in_q] = sim_time;
        }
    }
    
    public static void depart() {
        /* Departure event function. */
        int i;
        double delay;
        /* Check to see whether the queue is empty. */
        if (num_in_q == 0) {
            /* The queue is empty so make the server idle and eliminate the
departure (service completion) event from consideration. */
            
            
            time_next_event[2] = 1.0e+30;
        } else {
            /* The queue is nonempty, so decrement the number of customers in
queue. */
            server_status[server] = IDLE;
            --num_in_q;
            /* Compute the delay of the customer who is beginning service and update
the total delay accumulator. */
            delay = sim_time - time_arrival[1];
            total_of_delays += delay;
            /* Increment the number of customers delayed, and schedule departure. */
            ++num_custs_delayed;
            time_next_event[2] = sim_time + randomService();
            /* Move each customer in queue (if any) up one place. */
            for (i = 1; i <= num_in_q; ++i) {
                time_arrival[i] = time_arrival[i + 1];
            }
        }
    }
    
    public static void update_time_avg_stats() {
        /* Update area accumulators for time-average statistics. */
        double time_since_last_event;
        /* Compute time since last event, and update last-event-time marker. */
        time_since_last_event = sim_time - time_last_event;
        time_last_event = sim_time;
        /* Update area under number-in-queue function. */
        area_num_in_q += num_in_q * time_since_last_event;
        /* Update area under server-busy indicator function. */
        area_server_status += server_status[server] * time_since_last_event;
    }
    
    public static void report() {
        /* Report generator function. */
        /* Compute and write estimates of desired measures of performance. */
        /*
        System.out.printf("\n\nAverage delay in queue%11.3f minutes\n\n",
                total_of_delays / num_custs_delayed);
        System.out.printf("Average number in queue%10.3f\n\n",
                area_num_in_q / sim_time);
        System.out.printf("Server utilization%15.3f\n\n",
                area_server_status / sim_time);
        
        */
        
        System.out.println();
        //Q1: Simulation table
        System.out.println("=========================================================================="); // header
        System.out.println("---  No of   --- Arrival --- Time Service --- Service --- Time service ---"); // header
        System.out.println("--- Customer ---  Time   ---    Begin     ---   Time  ---      ends    ---"); // header
        System.out.println("=========================================================================="); // header
        
        //local variable to represent each elements
        LinkedList<Integer> time_service_begin = new LinkedList<Integer>();
        LinkedList<Integer> time_service_end = new LinkedList<Integer>();
        
        int at = arrival_time.get(0); //arrival time
        int tsb = at; time_service_begin.add(tsb);// time service begin
        int st = service_time.get(0); // service time
        int tse = tsb + st; time_service_end.add(tse);// time service end
        
        System.out.printf("--- %8d --- %7d --- %12d --- %7d --- %12d ---\n",1, at, tsb, st, tse); //first row, initialization
        //the rest of the row
        for (int i = 1; i < num_custs_delayed; i++){
            at = arrival_time.get(i);
            tsb = at > tse? at: tse; time_service_begin.add(tsb);// current `at' is larger than previous `tse'? if yes then return `at', else return `tse'
            st = service_time.get(i);
            tse = tsb + st; time_service_end.add(tse);
            System.out.printf("--- %8d --- %7d --- %12d --- %7d --- %12d ---\n",(i+1), at, tsb, st, tse);
        }
        System.out.println("=========================================================================="); // footer
                
        // For Q2
        System.out.println("a. Average waiting time for a customer: " + total_of_delays / (double)num_custs_delayed); //Q2(a)
        
        int customer_who_waits = 0;
        for (int i = 0; i < num_custs_delayed; i++) if (arrival_time.get(i) != time_service_begin.get(i)) customer_who_waits++; //assumption: no delay time when arrival time is the same with time service begin
        System.out.println("b. Probability that a customer has to wait in the queue: " + customer_who_waits/(double)num_custs_delayed); //Q2(b)
        
        int idle_time = 0; 
        for (int i = 1; i < num_custs_delayed; i++) idle_time += (time_service_begin.get(i) - time_service_end.get(i-1)); //assumption: sum the difference between previous time service end and current time service begin
        System.out.println("c. Probability of idle server: " + idle_time/(double)time_service_end.getLast()); //Q2(c), assume last value of time service end is the total simulation time
        
        System.out.println("d. Average service time: " + cumulative_service_time/(double)num_custs_delayed); // Q2(d)
        
        int sum_interarrival = arrival_time.get(num_custs_delayed)-arrival_time.get(0); // assumption: the i value is the sum of all interarrival time, assuming the value start from 0
        System.out.println("e. Average time between arrival: " + sum_interarrival/(double)(num_custs_delayed-1)); //Q2(e). Assumption: time between arrival is used, e.g. 9 values to generate 10 customer
        
        System.out.println("f. Average waiting time for the customer who waits: " + total_of_delays/(double)customer_who_waits); //Q2(f), info from (a) and (b)
        
        System.out.println("g. Average time customer spends in the system: " + (total_of_delays + cumulative_service_time)/(double)num_custs_delayed);
        
        System.out.printf("\nTime simulation ended%12.3f minutes (why? Please figure out when the original code actually `stops')\n", sim_time);
    }

    public static void initializeRandomNumber() {
        interArrivalTime = Acceptance.generateInterarrivalTime(CUST_SERVED);
        //System.out.println(interArrivalTime);
        //printFrequency(interArrivalTime);
        waitingTime = Acceptance.generateWaitingTime(CUST_SERVED);
        //System.out.println(waitingTime);
        //printFrequency(waitingTime);
        serviceTime = Acceptance.generateServiceTime(CUST_SERVED);
        //System.out.println(serviceTime);
        //printFrequency(serviceTime);
    }

    public static int randomInterarrival() {
        /* Random number generator for interarrival time */
        int val = interArrivalTime.remove();
        cumulative_arrival_time += val;
        arrival_time.add(cumulative_arrival_time); // use cumulative so that arrival time is calculated
        return val;
    }

    public static int randomService() {
        /* Random number generator for service time */
        int val = serviceTime.remove();
        cumulative_service_time += val;
        service_time.add(val); //use original val of service time
        return val;
    }

    public static void printFrequency(Queue<Integer> numbers) {
        int size = numbers.size();
        int[] frequency = {0, 0, 0, 0, 0, 0};
        for (int i = 0; i < size; i++) {
            int x = numbers.remove();
            switch (x) {
                case 0:
                    frequency[0]++;
                    break;
                case 1:
                    frequency[1]++;
                    break;
                case 2:
                    frequency[2]++;
                    break;
                case 3:
                    frequency[3]++;
                    break;
                case 4:
                    frequency[4]++;
                    break;
            }
        }
        printArray(frequency);
    }

    public static void printArray(int[] numbers) {
        System.out.print("[");
        for (int i = 0; i < (numbers.length - 1); i++) {
            System.out.print(numbers[i] + ",");
        }
        System.out.print(numbers[numbers.length - 1] + "]\n");
    }
}
