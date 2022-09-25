package Calendar.CS151;

import java.time.LocalTime;

/**
 * TimeInterval Class for Calendar
 * @author AnthonyJhong
 * @version 1.0 2/11/19
 */

/**
 * The TimeInterval class has both a starting and an ending time that can be altered by the user
 */

public class TimeInterval implements Comparable<TimeInterval>{
	private LocalTime startTime;
	private LocalTime endTime;
	
	/**
	 * Constructs a TimeInterval setting the startTime and the endTime
	 * @param setStart value for the start time for the interval
	 * @param setEnd value for the endTime for the time interval
	 */
	public TimeInterval(LocalTime setStart, LocalTime setEnd) {
		startTime = setStart;
		endTime = setEnd;
	}
	/**
	 * set the start time of the TimeInterval
	 * @param t value of the startTime
	 */
	public void setStartTime(LocalTime t) {
		t = startTime;	
	}
	/**
	 * set the end time of the TimeInterval
	 * @param t value of the endTime
	 */
	public void setEndTime(LocalTime t) {
		t = endTime;
	}
	/**
	 * returns the start time of the TimeInterval
	 * @return current start time
	 */
	public LocalTime getStartTime() {
		return startTime;
	}
	/**
	 * returns the end time of the TimeInterval
	 * @return current end time
	 */
	public LocalTime getEndTime() {
		return endTime;
	}
	/**
	 * Returns a boolean that returns true when TimeIntervals are overlapping and 
	 * false when they are not
	 * @param t TimeInterval that you want to compare with the current TimeInterval
	 * @return boolean whether or not the TimeInterval overlaps with the current one
	 */
	public boolean isOverlapping(TimeInterval t) {
		return !startTime.isAfter(t.getEndTime()) && !t.getStartTime().isAfter(endTime);
	}
	/**
	 * Returns a string interpretation of the TimeInterval (hh:mm - hh:mm)
	 * @return String interpretation of TimeInteval
	 */
	public String intervalToString() {
		
		return startTime.toString() + " - " + endTime.toString();
	}
	/**
	 * Compares the starting time of the current instance of a TimeInterval 
	 * and the starting time of another TimeInterval
	 * @return an integer value based on if the values are equal or different
	 */
	@Override
	public int compareTo(TimeInterval o) {
		return startTime.compareTo(o.getStartTime());
	}	
}