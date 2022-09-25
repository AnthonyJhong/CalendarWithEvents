package Calendar.CS151;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;

/**
 * MyCalendar for Calendar project
 * @author AnthonyJhong
 *@version 1.0 2/11/2019
 */

/**
 * The MyCalendar class consists of an ArrayList of Events and the current date that 
 * the user is viewing
 */

public class MyCalendar {
	
	private LocalDate currentViewingDate;
	private ArrayList<Event> allEvents;
	private DayOfWeek dayValArr[] = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
			DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
	private String abriviatedDays[] = {"M", "T", "W", "R", "F", "A", "S"};
	private String []months = {"January", "Febuary", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December"};
	
	/**
	 * Constructs a MyCalendar object that instantiates the date and the ArrayList of events
	 */
	public MyCalendar() {
		currentViewingDate = LocalDate.now();
		allEvents = new ArrayList<Event>();
	}
	/**
	 * Sets the current date the user is viewing to the current date
	 */
	public void setDateToCurrent() {
		currentViewingDate = LocalDate.now();
	}
	/**
	 * Returns the current viewing date of the user
	 * @return current viewing date
	 */
	public LocalDate getCurrentViewDate() {
		return currentViewingDate;
	}
	/**
	 * Returns the ArrayList of Events
	 * @return ArrayList of Events
	 */
	public ArrayList<Event> getAllEvents(){
		return allEvents;
	}
	/**
	 * This will change the month of the current viewing date of the user
	 * @param c number of months to jump
	 */
	public void ascendByMonth(int c) {
		currentViewingDate =  currentViewingDate.plusMonths(c);
	}
	/**
	 * This will change the current viewing date by days
	 * @param i how many days to change the viewing date by
	 */
	public void ascendByDay(int i) {
		currentViewingDate = currentViewingDate.plusDays(i);
	}
	/**
	 * Adds an event to ArrayList of Events
	 * @param e Event that user wants to add
	 */
	public void addEvent(Event e) {
		allEvents.add(e);
		Collections.sort(allEvents);
	}
	/**
	 * Deletes an event if it exists using the day of the event and the name of the event
	 * @param day the day of the event
	 * @param eventName the name of the event
	 */
	public void deleteEvent(LocalDate day, String eventName) {
		
		if(allEvents.size() == 0) {
			return;
		}
		
		for(int i = 0; i < allEvents.size(); i++) {
			
			if(allEvents.get(i).getName().toLowerCase().equals(eventName.toLowerCase()) && 
					allEvents.get(i).getEventStartDate().equals(day)) {
			
				System.out.println("Event " + allEvents.get(i).getName() + 
						" has been deleted from your event list! Here is a list of your current events on: ");
				allEvents.remove(allEvents.get(i));
				System.out.println();
				this.printEventsOnDayForDelete(day);
				return;
			}
		}
		System.out.println("The one time event you tried to delete does not exist!");
	}
	/**
	 * Deletes an event with the specified name if and only if the event is recurring
	 * @param name name of the event
	 */
	public void deleteRecurring(String name) {
		
		for(int i = 0; i < allEvents.size(); i++) {
			if(allEvents.get(i).getIsRecurring() == true) {
				if(allEvents.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
					System.out.println("Event " + allEvents.get(i).getName() + " has been deleted from your event list!");
					allEvents.remove(allEvents.get(i));
					return;
				}
			}
		}
		System.out.println("The Regular Event you tried to delete does not exist!");
	}
	/**
	 * Deletes all events on a specified data
	 * @param day date specified
	 */
	public void deleteAllEventsOnDate(LocalDate day){
		if(allEvents.size()==0)
			return;
		ArrayList<Event> eventsToDelete = new ArrayList<>();
		for(Event e: allEvents) {
			if(e.getIsRecurring() == false) {
				if(e.getEventStartDate().equals(day))
					eventsToDelete.add(e);
			}
		}
		
		if(eventsToDelete.size() == 0)
			return;
		for(Event e: eventsToDelete) {
			allEvents.remove(e);
		}
		
	}
	/**
	 * Prints all of the events found on a particular day
	 * @param day Day of the events
	 */
	public void printEventsOnDayForDelete(LocalDate day) {
		
		DateTimeFormatter printDateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		System.out.println(printDateFormatter.format(day));
		
		if(allEvents.size() == 0) {
			System.out.print("There are no events on today!");
			return;
		}
		Event tempEvent = allEvents.get(0);
		LocalDate tempStartDate = tempEvent.getEventStartDate();
		TimeInterval tempTime = tempEvent.getScheduled();
		String tempName = tempEvent.getName();
		int counter = 0;
				
		for(int i = 0; i < allEvents.size(); i++) {
			
			tempEvent = allEvents.get(i);
			tempStartDate = tempEvent.getEventStartDate();
			if(day.equals(tempStartDate)) {
				tempTime = tempEvent.getScheduled();
				tempName = tempEvent.getName();
				System.out.print("    "+tempTime.intervalToString() + " ");
				System.out.println(tempName);
				counter ++;
			}	
		}
		if(counter == 0) 
			System.out.print("There are no events on today!\n");
	}
	/**
	 * Prints all of the events on a given day in a different format
	 * @param day Day of the events
	 */
	public void printEventsOnDayForView(LocalDate day) {
		if(allEvents.size() == 0) {
			return;
		}
		
		ArrayList<Event> orderByTime = new ArrayList<>();
		ArrayList<TimeInterval> timeOrder = new ArrayList<>();
		Event tempEvent = allEvents.get(0);
		LocalDate tempStartDate = tempEvent.getEventStartDate();
		LocalDate tempEndDate = null;
		TimeInterval tempTime = tempEvent.getScheduled();
		String tempName = tempEvent.getName();
		String tempDays = null;
				
		for(int i = 0; i < allEvents.size(); i++) {
			
			tempEvent = allEvents.get(i);
			
			if(tempEvent.getIsRecurring() == false) {
				tempStartDate = tempEvent.getEventStartDate();
				if(day.equals(tempStartDate)) {
					orderByTime.add(tempEvent);
					timeOrder.add(tempEvent.getScheduled());
				}	
			}
			else {
				ArrayList<DayOfWeek> recurringEventDays = new ArrayList<>();
				tempStartDate = tempEvent.getEventStartDate();
				tempEndDate = tempEvent.getEventEndDate();
				if((day.isAfter(tempStartDate) && day.isBefore(tempEndDate))||day.equals(tempStartDate)||day.equals(tempEndDate)) {
					tempDays = tempEvent.getRecurringDays();
					for(int j = 0; j < tempDays.length(); j++) {
						for(int k = 0; k < abriviatedDays.length; k++) {
							if(tempDays.substring(j, j+1).toUpperCase().equals(abriviatedDays[k])) {
								recurringEventDays.add(dayValArr[k]);
							}
							
						}
					}
					for(int j = 0; j < recurringEventDays.size(); j++) {
						if(day.getDayOfWeek().equals(recurringEventDays.get(j))) {
							orderByTime.add(tempEvent);
							timeOrder.add(tempEvent.getScheduled());
						}
					}
				}
			}
		}
		if(orderByTime.size()==0) {
			return;
		}
		
		Collections.sort(timeOrder);
		for(int i = 0; i < timeOrder.size(); i++) {
			for(int j = 0; j < orderByTime.size(); j ++) {
				if(timeOrder.get(i).equals(orderByTime.get(j).getScheduled())) {
					tempTime = orderByTime.get(j).getScheduled();
					tempName = orderByTime.get(j).getName();
					System.out.print(tempName + ": ");
					System.out.println(tempTime.intervalToString());
				}
			}
		}
		
	}
	/**
	 * Method will let the user know if there is a recurring event on a certain day
	 * @param day day that the user wants to check for
	 * @return boolean
	 */
	public boolean isEventOnDate(LocalDate day) {
		if(allEvents.isEmpty()) {return false;}
		
		Event tempEvent = allEvents.get(0);
		LocalDate tempStartDate = tempEvent.getEventStartDate();
		LocalDate tempEndDate = null;
		String tempDays = null;
		
		for(int i = 0; i < allEvents.size(); i++) {
			
			tempEvent = allEvents.get(i);
			
			if(tempEvent.getIsRecurring() == true) {
				tempStartDate = tempEvent.getEventStartDate();
				tempEndDate = tempEvent.getEventEndDate();
				if((day.isAfter(tempStartDate) && day.isBefore(tempEndDate))||day.equals(tempStartDate)||day.equals(tempEndDate)) {
					tempDays = tempEvent.getRecurringDays();
					for(int j = 0; j < tempDays.length(); j++) {
						for(int k = 0; k < abriviatedDays.length; k++) {
							if(tempDays.substring(j, j+1).toUpperCase().equals(abriviatedDays[k])) {
								if(dayValArr[k] == day.getDayOfWeek())
									return true;
							}
							
						}
					}
				}
			}
			else {
				if(tempEvent.getEventStartDate().equals(day)) {
					return true;
				}
			}
		}	
		return false;
	}
	/**
	 * Method will let the you know if a certain time and date will overlap any event in the event list
	 * @param day day that the user wants to check for
	 * @param time the time that needs to be cross checked for overlap
	 * @return boolean boolean true (the to be created event overlaps) 
	 * 		   false (The to be created event does not overlap)
	 */
	public boolean eventOverlap(LocalDate day, TimeInterval time) {
		if(allEvents.isEmpty()) {return false;}
		
		Event tempEvent = allEvents.get(0);
		LocalDate tempStartDate = tempEvent.getEventStartDate();
		LocalDate tempEndDate = null;
		String tempDays = null;
		
		for(int i = 0; i < allEvents.size(); i++) {
			
			tempEvent = allEvents.get(i);
			
			if(tempEvent.getIsRecurring() == true) {
				tempStartDate = tempEvent.getEventStartDate();
				tempEndDate = tempEvent.getEventEndDate();
				if((day.isAfter(tempStartDate) && day.isBefore(tempEndDate))||day.equals(tempStartDate)||day.equals(tempEndDate)) {
					tempDays = tempEvent.getRecurringDays();
					for(int j = 0; j < tempDays.length(); j++) {
						for(int k = 0; k < abriviatedDays.length; k++) {
							if(tempDays.substring(j, j+1).toUpperCase().equals(abriviatedDays[k])) {
								if(dayValArr[k] == day.getDayOfWeek()) {
									if(time.isOverlapping(tempEvent.getScheduled()))
									return true;
								}
							}
							
						}
					}
				}
			}
			if( tempEvent.getScheduled().isOverlapping(time) &&
					allEvents.get(i).getEventStartDate().equals(day)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Prints all of the events in the ArrayList of Events
	 */
	public void printAllEvents() {
		
		if(allEvents.size() == 0) {
			System.out.println("There are no events to show!");
			return;
		}
		Event tempEvent = allEvents.get(0);
		LocalDate tempStartDate = tempEvent.getEventStartDate();
		LocalDate tempEndDate = null;
		TimeInterval tempTime = tempEvent.getScheduled();
		String tempName = tempEvent.getName();
		String recuringDates = "";
		DateTimeFormatter printDateFormatter = DateTimeFormatter.ofPattern("E, MMM d, yyyy");
		
		
		for(int i = 0; i < allEvents.size(); i ++) {
			
			tempEvent = allEvents.get(i);
			
			if(tempEvent.getIsRecurring() == false) {
				tempStartDate = tempEvent.getEventStartDate();
				tempTime = tempEvent.getScheduled();
				tempName = tempEvent.getName();
				
				System.out.print(printDateFormatter.format(tempStartDate) + " ");
				System.out.print(tempTime.intervalToString() + " ");
				System.out.println(tempName);
			}
			else {
				tempStartDate = tempEvent.getEventStartDate();
				tempEndDate = tempEvent.getEventEndDate();
				recuringDates = tempEvent.getRecurringDays();
				tempTime = tempEvent.getScheduled();
				tempName = tempEvent.getName();
				
				System.out.print(recuringDates + " ");
				System.out.print(tempStartDate.getMonthValue() + "/" +
						 tempStartDate.getDayOfMonth()+ "/"+tempStartDate.getYear() + " ");
				System.out.print(tempEndDate.getMonthValue()+ "/" + tempEndDate.getDayOfMonth() + "/" +
						 tempEndDate.getYear()+ " ");
				System.out.print(tempTime.intervalToString() + " ");
				System.out.println(tempName);
			}
		}
	}
	/**
	 * Prints the current month, finds the days that have events and embodies them with curly braces
	 * @param date The date that will be used to print the month for the calendar
	 */
	public void printCalendarWithEvents(LocalDate date) {
		
		LocalDate firstDay = LocalDate.of(date.getYear(), date.getMonth(), 1);
		int days = date.lengthOfMonth();

		System.out.println("  " + months[date.getMonthValue() -1] +" "+ date.getYear());
		System.out.println("Su Mo Tu We Th Fr Sa");
		
		DayOfWeek numOfDate = firstDay.getDayOfWeek();
		int dayOfWeekVal = numOfDate.getValue();
		
		int counter = 0;
		if(dayOfWeekVal != 7) {
			for(int i = 0; i < dayOfWeekVal; i++) {
				System.out.print("   ");
				counter ++;
			}
		}
		if(allEvents.size() != 0) {
			boolean didNotPass = true;
			for(int i = 1; i <= days; i ++) {
				LocalDate temp = LocalDate.of(date.getYear(), date.getMonth(), i);
				didNotPass = true;
				for(int j = 0; j < allEvents.size(); j++) {
					if(this.isEventOnDate(temp)) {
						System.out.printf("{"+ "%2d", i);
						System.out.print("}");
						didNotPass = false;
						break;
					}
				}
				if(didNotPass)
					System.out.printf("%2d",i);
	
				System.out.print(" ");
				
				counter ++;
				if(counter%7 == 0 || days == i)
					System.out.println();
					
			}
			System.out.println();
		}
		else {
			for(int i = 1; i <= days; i ++) {
				System.out.printf("%2d",i);
				System.out.print(" ");
				counter ++;
				if(counter%7 == 0 || days == i)
					System.out.println();
			}
			System.out.println();
		}
	}
	/**
	 * Method will print all recurring/regular events
	 */
	public void printAllRecurringEvents() {
		
		if(allEvents.size() == 0) {
			System.out.println("There are no events to show!");
			return;
		}
		Event tempEvent = allEvents.get(0);
		LocalDate tempStartDate = tempEvent.getEventStartDate();
		LocalDate tempEndDate = null;
		TimeInterval tempTime = tempEvent.getScheduled();
		String tempName = tempEvent.getName();
		String recuringDates = "";
		int counter = 0;
		
		for(int i = 0; i < allEvents.size(); i ++) {
			
			tempEvent = allEvents.get(i);
			if(tempEvent.getIsRecurring()){
				tempStartDate = tempEvent.getEventStartDate();
				tempEndDate = tempEvent.getEventEndDate();
				recuringDates = tempEvent.getRecurringDays();
				tempTime = tempEvent.getScheduled();
				tempName = tempEvent.getName();
				
				System.out.print(recuringDates + " ");
				System.out.print(tempStartDate.getMonthValue() + "/" +
						 tempStartDate.getDayOfMonth()+ "/"+tempStartDate.getYear() + " ");
				System.out.print(tempEndDate.getMonthValue()+ "/" + tempEndDate.getDayOfMonth() + "/" +
						 tempEndDate.getYear()+ " ");
				System.out.print(tempTime.intervalToString() + " ");
				System.out.println(tempName);
				counter ++;
			}
		}		
		if(counter ==0)
			System.out.println("There are no events to show!");
	}
	/**
	 * Method will let you know your event list contains an event with the name specified
	 * @param eventName name of the event you are looking for
	 * @return boolean (True event list contains event, False the event list does not)
	 */
	public boolean nameExists(String eventName) {
		if(allEvents.size() == 0)
			return false;
		for(int i = 0; i < allEvents.size(); i++) {
			if(allEvents.get(i).getName().toLowerCase().equals(eventName.toLowerCase()))
				return true;
		}
		return false;
	}
}