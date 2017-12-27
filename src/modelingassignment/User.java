/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelingassignment;

/**
 *
 * @author Kangwenn
 */
public class User implements Comparable<User>{
    private static int numberOfUser = 0;
    private int userNo;
    private int interArrivalTime;
    private int arrivalTime;
    private int serviceBeginTime;
    private int serviceEndTime;
    private int serviceTime;
    private int waitingTime;
    private Counter counterServiced;

public User(){
    numberOfUser++;
    this.userNo = numberOfUser;
}

    @Override
    public String toString() {
        return "User{" + "userNo=" + userNo + ", interarrivalTime=" + interArrivalTime + 
                ", arrivalTime=" + arrivalTime + ", serviceBeginTime=" + serviceBeginTime + 
                ", serviceEndTime=" + serviceEndTime + 
                ", serviceTime=" + serviceTime + ", waitingTime=" + waitingTime + '}';
    } 

    @Override
    public int compareTo(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the numberOfUser
     */
    public static int getNumberOfUser() {
        return numberOfUser;
    }

    /**
     * @param aNumberOfUser the numberOfUser to set
     */
    public static void setNumberOfUser(int aNumberOfUser) {
        numberOfUser = aNumberOfUser;
    }

    /**
     * @return the userNo
     */
    public int getUserNo() {
        return userNo;
    }

    /**
     * @param userNo the userNo to set
     */
    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    /**
     * @return the interArrivalTime
     */
    public int getInterArrivalTime() {
        return interArrivalTime;
    }

    /**
     * @param interArrivalTime the interArrivalTime to set
     */
    public void setInterArrivalTime(int interArrivalTime) {
        this.interArrivalTime = interArrivalTime;
    }

    /**
     * @return the arrivalTime
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return the serviceBeginTime
     */
    public int getServiceBeginTime() {
        return serviceBeginTime;
    }

    /**
     * @param serviceBeginTime the serviceBeginTime to set
     */
    public void setServiceBeginTime(int serviceBeginTime) {
        this.serviceBeginTime = serviceBeginTime;
    }

    /**
     * @return the serviceEndTime
     */
    public int getServiceEndTime() {
        return serviceEndTime;
    }

    /**
     * @param serviceEndTime the serviceEndTime to set
     */
    public void setServiceEndTime(int serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    /**
     * @return the serviceTime
     */
    public int getServiceTime() {
        return serviceTime;
    }

    /**
     * @param serviceTime the serviceTime to set
     */
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    /**
     * @return the waitingTime
     */
    public int getWaitingTime() {
        return waitingTime;
    }

    /**
     * @param waitingTime the waitingTime to set
     */
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    /**
     * @return the counterServiced
     */
    public Counter getCounterServiced() {
        return counterServiced;
    }

    /**
     * @param counterServiced the counterServiced to set
     */
    public void setCounterServiced(Counter counterServiced) {
        this.counterServiced = counterServiced;
    }
    
}
