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
public class Event implements Comparable<Event>{
    private String type;
    private Integer eventTime;
    private User user;
    
    public Event(String type,int eventTime, User user){
        this.type = type;
        this.eventTime = eventTime;
        this.user = user;
    }

    @Override
    public int compareTo(Event t) {
        return this.getEventTime().compareTo(t.getEventTime());
    }

    
    @Override
    public String toString() {
        return "Event{" + "type=" + getType() + ", eventTime=" + getEventTime() + ", user=" + getUser() + '}';
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the eventTime
     */
    public Integer getEventTime() {
        return eventTime;
    }

    /**
     * @param eventTime the eventTime to set
     */
    public void setEventTime(Integer eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    
}
