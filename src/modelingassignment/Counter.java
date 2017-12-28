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
    private int serviceEndTime;
    private static int counterNumber = 0;
    private int counterNo;
    private String counterName;

    public Counter() {
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
    public int getServiceEndTime() {
        return serviceEndTime;
    }

    /**
     * @param serviceEndTime the serviceTime to set
     */
    public void setServiceEndTime(int serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
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

    /**
     * @return the counterName
     */
    public String getCounterName() {
        return counterName;
    }

    /**
     * @param counterName the counterName to set
     */
    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }
}
