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
public class Counter {
    private String serverStatus;
    private double areaServerStatus;
    private int serviceTime;
    private static int counterNumber = 0;
    private int counterNo;
    
    public Counter(){
        this.counterNumber++;
        this.counterNo = counterNumber;
        this.serverStatus = "idle";
        this.areaServerStatus = 0.0;
    }

    /**
     * @return the serverStatus
     */
    public String getServerStatus() {
        return serverStatus;
    }

    /**
     * @param serverStatus the serverStatus to set
     */
    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    /**
     * @return the areaServerStatus
     */
    public double getAreaServerStatus() {
        return areaServerStatus;
    }

    /**
     * @param areaServerStatus the areaServerStatus to set
     */
    public void setAreaServerStatus(double areaServerStatus) {
        this.areaServerStatus = areaServerStatus;
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
     * @return the counterNo
     */
    public int getCounterNo() {
        return counterNo;
    }

    /**
     * @param counterNo the counterNo to set
     */
    public void setCounterNo(int counterNo) {
        this.counterNo = counterNo;
    }
}