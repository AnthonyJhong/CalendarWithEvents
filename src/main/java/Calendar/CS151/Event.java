package Calendar.CS151;

import java.time.LocalDate;

/**
 * Event class for Calendar
 * @author AnthonyJhong
 *@version 1.0 2/11/2019
 */


/**
 * Event class allows users to create Events that consist of a TimeInterval, a name, and a date
 *
 */
public class Event implements Comparable<Event> {
	
	private TimeInterval scheduledTime;
	private String name;
	private LocalDate eventStartDate;
	private LocalDate eventEndDate;
	private String recurringDays;
	private boolean isRecurring;
	
	/**
	 * Constructs an Event with values for TimeInterval, name, start date, end date, and recurring days
	 * @param t Time interval
	 * @param n name
	 * @param sDate start date
	 */
	public Event(TimeInterval t,String n, LocalDate sDate){
		scheduledTime = t;
		name = n;
		eventStartDate = sDate;
		eventEndDate = null;
		recurringDays = null;
		isRecurring = false;
	}
	/**
	 * Constructs an Event with values for TimeInterval, name, start date, end date, and recurring days
	 * @param t Time interval
	 * @param n name
	 * @param sDate start date
	 * @param eDate end date
	 * @param r recurring days
	 */
	public Event(TimeInterval t,String n, LocalDate sDate, LocalDate eDate, String r){
		scheduledTime = t;
		name = n;
		eventStartDate = sDate;
		eventEndDate = eDate;
		recurringDays = r;
		isRecurring = true;
	}
	/**
	 * Sets the value of the name for the Event
	 * @param n value for name
	 */
	public void setName(String n) {
		name = n;
	}
	/**
	 * Sets the TimeInterval for the Event
	 * @param t value of the TimeInterval
	 */
	public void setScheduled(TimeInterval t) {
		scheduledTime = t;
	}
	/**
	 * Sets the date of the event
	 * @param d value of the date of the event
	 */
	public void setEventStartDate(LocalDate d) {
		eventStartDate = d;
	}
	/**
	 * Sets the value of the end day of the event
	 * @param d end date of the event
	 */
	public void setEventEndDate(LocalDate d) {
		eventEndDate = d;
	}
	/**
	 * Set the days that the event recurs
	 * @param days days that event recurs
	 */
	public void setRecurringDays(String days) {
		recurringDays = days;
	}
	/**
	 * Returns whether or not the event is recurring
	 * @return if the event is recurring or not
	 */
	public boolean getIsRecurring() {
		return isRecurring;
	}
	/**
	 * Returns the name of the event
	 * @return name of the event
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns the TimeInterval of the Event
	 * @return TimeInterval of Event
	 */
	public TimeInterval getScheduled() {
		return scheduledTime;
	}
	/**
	 * Returns the date of the event
	 * @return date of event
	 */
	public LocalDate getEventStartDate() {
		return eventStartDate;
	}
	/**
	 * Returns the days that the event recurs
	 * @return Days that event recurs
	 */
	public String getRecurringDays() {
		return  recurringDays;
	}
	/**
	 * returns the end date of the event 
	 * @return End date of the event
	 */
	public LocalDate getEventEndDate() {
		return eventEndDate;
	}
	/**
	 * Returns a boolean true (both events are equal) false (both events are different)
	 * @param otherObject object that will be compared with the current event
	 * @return boolean
	 */
	@Override
	public boolean equals(Object otherObject) {
		
		if (otherObject == null) {return false;} 
		if (this.getClass() != otherObject.getClass()) {return false;}
		Event temp = (Event)otherObject;
		
		return (scheduledTime == temp.getScheduled())&& (name == temp.getName())&& 
				(eventStartDate == temp.getEventStartDate()) && (eventEndDate == temp.getEventEndDate()) &&
				(recurringDays == temp.getRecurringDays());
		  
	}
	/**
	 * Compares events to see if they are in the correct order
	 * @param e an event that will be compared with the current event
	 * @return an integer value that tells you if the events are in the correct order
	 */
	@Override
	public int compareTo(Event e) {
		if(eventStartDate.compareTo(e.getEventStartDate()) == 0) {
			return scheduledTime.compareTo(e.getScheduled());
		}
		else {
			return eventStartDate.compareTo(e.getEventStartDate());
		}
	}
}