package modelingassignment;

import java.io.*;
import java.util.*;

/*
Online code adopted and modified from https://vdocuments.site/documents/csm-dump-truck-problem-simulation.html
Author: Bhalchandra Shirole
*/

// Declare event notices
class event {

    String type;
    int time;
    int truck;

    public event(String type, int time, int truck) {
        this.type = type;
        this.time = time;
        this.truck = truck;
    }

    public void display() {
        System.out.println("(" + type + ", " + time + ", truck" + truck + ")"); //event notices
    }
}

// future event list
class fel {

    event e[] = new event[1000];
    int size;

    public fel() {
        size = 0;
    }

    public void add(event a) {
        if (size < 1000) {
            e[size] = a;
            size++;
        } else {
            System.out.println("FEL full");
        }
    }

    public event remove() {
        int min = 0;
        int pos = 0;
        for (int i = 0; i < size; i++) {
            if (e[i] != null) {
                min = e[i].time;
                break;
            }
        }
        for (int i = 0; i < size; i++) {
            if (e[i] != null && e[i].time <= min) {
                min = e[i].time;
                pos = i;
            }
        }
        event ret = new event(e[pos].type, e[pos].time, e[pos].truck);
        e[pos] = null;
        return ret;
    }

    public void display() {
        for (int i = 0; i < 1000; i++) {
            if (e[i] != null) {
                e[i].display();
            }
        }
    }
}

// loader queue and weighing queue
class queue {

    int q[];
    int f, r;

    public queue() {
        q = new int[100];
        f = 0;
    }

    public void add(int truck) {
        q[f] = truck;
        f++;
    }

    public int remove() {
        for (int i = 0; i < 100; i++) {
            if (q[i] != 0) {
                int temp = q[i];
                q[i] = 0;
                return temp;
            }
        }
        return 0;
    }

    public void display() {
        for (int i = 0; i < 100; i++) {
            if (q[i] != 0) {
                System.out.print(" DT" + q[i]);
            }
        }
    }
}

class state {

    static int lq, l, wq, w, clock;
}

class Practical3 {

    static Random rand;
    
    public static void main(String args[]) {
        System.out.println("Main started");
        fel list = new fel();
        queue lq = new queue();
        queue wq = new queue();
        initialise(list, lq, wq);
        System.out.println("main ended");
    }

    public static void initialise(fel list, queue lq, queue wq) {
        rand = new Random(10); // create random value with specified seed
        event endevent = new event("end", 100, 0);
        list.add(endevent);
        state.lq = 3;
        state.wq = 0;
        state.l = 2;
        state.w = 1;
        state.clock = 0;
        lq.add(4);
        lq.add(5);
        lq.add(6);
        list.add(new event("endload", 5, 3));
        list.add(new event("endload", 10, 2));
        list.add(new event("endwait", 12, 1));
        start(list, lq, wq);
    }

    public static int loadtime() {
        double temp = rand.nextDouble();
        if (temp <= 0.3) {
            return 5;
        }
        if (temp <= 0.8) {
            return 10;
        }
        return 15;
    }

    public static int weightime() {
        double temp = rand.nextDouble();
        if (temp <= 0.7) {
            return 12;
        }
        return 16;
    }

    public static int traveltime() {
        double temp = rand.nextDouble();
        if (temp <= 0.4) {
            return 40;
        }
        if (temp <= 0.7) {
            return 60;
        }
        if (temp <= 0.9) {
            return 80;
        }
        return 100;
    }

    public static void start(fel list, queue lq, queue wq) {
        System.out.println("\n ------------------------------------------------- \n Clock " + state.clock);
        System.out.println("System states");
        System.out.println("LQ(t) " + state.lq + " WQ(t) " + state.wq + " L(t) " + state.l + " W(t) " + state.w);
        System.out.print("loader queue ");
        lq.display();
        System.out.print("\n weigh queue ");
        wq.display();
        System.out.print("\n Fel \n");
        list.display();
        //System.out.println(list.size);
        event emminent = list.remove();
        state.clock = emminent.time;
        if (emminent.type.equals("endload")) {
            endload(list, emminent, lq, wq);
        }
        if (emminent.type.equals("endwait")) {
            endwait(list, emminent, lq, wq);
        }
        if (emminent.type.equals("arrivalload")) {
            arrload(list, emminent, lq, wq);
        }
        if (emminent.type.equals("end")) {
            return;
        }
        start(list, lq, wq);
    }

    public static void endload(fel list, event emminent, queue lq, queue wq) {
        int next;
        if (state.w < 1) {
            state.w++;
            state.l--;
            next = state.clock + weightime();
            list.add(new event("endwait", next, emminent.truck));
        }
//add
        if (state.w == 1) {
            state.wq++;
            state.l--;
            wq.add(emminent.truck);
        }
        //add
        if (state.lq != 0) {
            state.l++;
            state.lq--;
            next = state.clock + loadtime();
            list.add(new event("endload", next, lq.remove()));
        }
    }

    public static void endwait(fel list, event emminent, queue lq, queue wq) {
        int next;
        state.w--;
        next = state.clock + traveltime();
        list.add(new event("arrivalload", next, emminent.truck));
        if (state.wq != 0) {
            state.w++;
            state.wq--;
            next = state.clock + weightime();
            list.add(new event("endwait", next, wq.remove()));
        }
    }

    public static void arrload(fel list, event emminent, queue lq, queue wq) {
        int next;
        if (state.l < 2) {
            state.l++;
            next = state.clock + loadtime();
            list.add(new event("endload", next, emminent.truck));
        }
        //add
        if (state.l == 2) {
            state.lq++;
            lq.add(emminent.truck);
        }
        //add
    }
}
