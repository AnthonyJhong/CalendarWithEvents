package Calendar.CS151;

import java.util.Scanner;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *MyCalendarTester
 *@author AnthonyJhong
 *@version 1.0 2/11/2019
 * */
 
/**
 * myCalendarTester tests my Event, TimeInterval, and MyCalendar Classes
 */

public class MyCalendarTester {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		startingCalendar();
		MyCalendar cal = new MyCalendar();
		System.out.println();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		DateTimeFormatter dateFormatterforRead = DateTimeFormatter.ofPattern("M/d/yy");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
		DateTimeFormatter printFormatter = DateTimeFormatter.ofPattern("E, MMM d, yyyy");
		
		Scanner in = new Scanner(new File("C:\\Users\\antho\\IdeaProjects\\CalendarProject\\src\\main\\java\\Calendar\\CS151\\events.txt"), "UTF-8");
		
		while(in.hasNext()) {
			
			String name = in.nextLine();
			String dateTime = in.nextLine();
			String []dates = dateTime.split(" ");
			
			if(dates.length == 5) {
				String tempDays = dates[0];
				LocalTime tempStartTime = LocalTime.parse(dates[1], timeFormatter);
				LocalTime tempEndTime = LocalTime.parse(dates[2], timeFormatter);
				TimeInterval eventInterval = new TimeInterval(tempStartTime, tempEndTime);
				LocalDate tempStartDate = LocalDate.parse(dates[3], dateFormatterforRead);
				LocalDate tempEndDate = LocalDate.parse(dates[4], dateFormatterforRead);
				Event currentEvent = new Event(eventInterval, name, tempStartDate, tempEndDate, tempDays);
				cal.addEvent(currentEvent);
				
			}
			else if (dates.length == 3) {
				LocalDate tempDate = LocalDate.parse(dates[0], dateFormatterforRead);
				LocalTime tempStart = LocalTime.parse(dates[1], timeFormatter);
				LocalTime tempEnd = LocalTime.parse(dates[2], timeFormatter);
				TimeInterval eventInterval = new TimeInterval(tempStart, tempEnd);
				Event currentEvent = new Event(eventInterval, name, tempDate);
				cal.addEvent(currentEvent);
			}	
		}
		System.out.println("Loading is Done! \n");
		
		in.close();
		
		Scanner input = new Scanner(System.in);
		String userInput = "";
		
		while(true) {
			
			System.out.println("Select one of the following options:");
			System.out.println("[V]iew by  [C]reate [G]o to [E]vent list [D]elete  [Q]uit");
			
			userInput = input.nextLine();
			
			if(userInput.toUpperCase().equals("V")) {
				
				while(!userInput.toUpperCase().equals("D")|| userInput.toUpperCase().equals("M")) {
					System.out.println("[D]ay view or [M]onth view");
					userInput = input.nextLine();
					
					if(userInput.toUpperCase().equals("D")) {
						
						while(true) {
							LocalDate dayDate = cal.getCurrentViewDate();
							System.out.println(printFormatter.format(dayDate));
							cal.printEventsOnDayForView(dayDate);
							System.out.println();
							
							System.out.println("[P]revious or [N]ext or [G]o back to main menu");
							userInput = input.nextLine();
							
							if(userInput.toUpperCase().equals("P")) {
								
								cal.ascendByDay(-1);
								
							}
							else if(userInput.toUpperCase().equals("N")) {
								
								cal.ascendByDay(1);
								
							}
							else if(userInput.toUpperCase().equals("G")) {
								
								break;
								
							}
							else
								System.out.println("Please enter a valid character!");
						}
						cal.setDateToCurrent();
						break;
						
					}
					else if(userInput.toUpperCase().equals("M")) {
						
						while(true) {
							
							cal.printCalendarWithEvents(cal.getCurrentViewDate());
							System.out.println();
							
							System.out.println("[P]revious or [N]ext or [G]o back to main menu");
							userInput = input.nextLine();
							
							if(userInput.toUpperCase().equals("P")) {
								
								cal.ascendByMonth(-1);
								
							}
							else if(userInput.toUpperCase().equals("N")) {
								
								cal.ascendByMonth(1);
								
							}
							else if(userInput.toUpperCase().equals("G")) {
								
								break;
								
								
							}
							else
								System.out.println("Please enter a valid character!");
						}
						cal.setDateToCurrent();
						break;
					}
					else
						System.out.println("Please enter a valid character!");
				}
			}
			else if(userInput.toUpperCase().equals("C")) {
				
				System.out.println("You are now creating an event!");
				System.out.print("Please enter a name for the Event: ");
				String createdName = input.nextLine();
				
				System.out.print("Please enter the date of the event in 'mm/dd/YYYY' format: ");
				String createdDateString = input.nextLine();
				LocalDate createdDate = LocalDate.parse(createdDateString, formatter);
				System.out.print("Please enter a start time using a 24 Hour clock: ");
				LocalTime tempStartCreate = LocalTime.parse(input.nextLine(), timeFormatter);
				System.out.print("Please enter a end time using a 24 Hour Clock: ");
				LocalTime tempEndCreate = LocalTime.parse(input.nextLine(), timeFormatter);
				TimeInterval tempCreateInterval = new TimeInterval(tempStartCreate, tempEndCreate);
				
				if(cal.eventOverlap(createdDate, tempCreateInterval)) {
					System.out.println("The event that you tried to create overlapped with another event! \n");
				}
				else if(cal.nameExists(createdName)) {
					System.out.println("An event with the name " + createdName + " already exists! \n");
				}
				else {
					Event tempEventCreate = new Event(tempCreateInterval, createdName, createdDate);
					cal.addEvent(tempEventCreate);
					System.out.println("The event " + createdName + " has been added to your event list! \n");
				}
			}
			else if(userInput.toUpperCase().equals("G")) {
				
				System.out.print("Please enter the date of the day that you would like to view (mm/dd/yyyy): ");
				String dateOfGoToString = input.nextLine();
				LocalDate dateOfGoTo = LocalDate.parse(dateOfGoToString, formatter);
				System.out.println(printFormatter.format(dateOfGoTo));
				cal.printEventsOnDayForView(dateOfGoTo);
				System.out.println();
			}
			else if(userInput.toUpperCase().equals("E")) {
				
				cal.printAllEvents();
				System.out.println();
			}
			else if(userInput.toUpperCase().equals("D")) {
				while(true) {
					System.out.println("[S]elected  [A]ll  [DR]");
					userInput = input.nextLine();
					if(userInput.toUpperCase().equals("A")) {
						System.out.print("Please enter a date (mm/dd/yyyy): " );
						String deleteDateString = input.nextLine();
						LocalDate deleteDate = LocalDate.parse(deleteDateString, formatter);
						cal.deleteAllEventsOnDate(deleteDate);
						System.out.println("All of the events on " + formatter.format(deleteDate));
						System.out.println();
						break;
					}
					else if(userInput.toUpperCase().equals("S")) {
						System.out.print("Please enter a date (mm/dd/yyyy): " );
						String deleteDateString = input.nextLine();
						LocalDate deleteDate = LocalDate.parse(deleteDateString, formatter);
						cal.printEventsOnDayForDelete(deleteDate);
						System.out.print("\nPlease enter the name of the event you would like to delete (press enter if there are no events): ");
						String deleteName = input.nextLine();
						cal.deleteEvent(deleteDate, deleteName);
						System.out.println();
						break;
					}
					else if(userInput.toUpperCase().equals("DR")) {
						System.out.println("List of all Regular Events:");
						cal.printAllRecurringEvents();
						System.out.print("\nPlease enter the name of the recurring event you would like to delete (press enter of there are no events): ");
						String regularName = input.nextLine();
						cal.deleteRecurring(regularName);
						System.out.println();
						break;
					}
					else
						System.out.println("Please enter a valid character!");
				}
				
			}
			else if(userInput.toUpperCase().equals("Q")) {
				break;
			}
			else {System.out.println("Please enter a valid letter!");}
		}
		
		input.close();
		
		PrintWriter out = new PrintWriter(new File("output.txt"), "UTF-8");
		ArrayList<Event> allEventsForPrint = cal.getAllEvents();
		if(allEventsForPrint.size() != 0) {
			for(int i = 0; i < allEventsForPrint.size(); i++) {
				if(allEventsForPrint.get(i).getIsRecurring() == false) {
					out.println(allEventsForPrint.get(i).getName());
					out.print(formatter.format(allEventsForPrint.get(i).getEventStartDate()) + " ");
					out.print(allEventsForPrint.get(i).getScheduled().getStartTime().toString() + " ");
					out.println(allEventsForPrint.get(i).getScheduled().getEndTime().toString() + " ");

				}
				else {
					out.println(allEventsForPrint.get(i).getName());
					out.print(allEventsForPrint.get(i).getRecurringDays() + " ");
					out.print(allEventsForPrint.get(i).getScheduled().getStartTime().toString() + " ");
					out.print(allEventsForPrint.get(i).getScheduled().getEndTime().toString() + " ");
					out.print(formatter.format(allEventsForPrint.get(i).getEventStartDate()) + " ");
					out.println(formatter.format(allEventsForPrint.get(i).getEventEndDate()) + " ");	
				}	
			}
		}
		out.close();
		System.out.println("Good Bye, your event list has been saved to output.txt!");
	}
	/**
	 * Prints a calendar view of the current month, putting the current day in square brackets
	 */
	public static void startingCalendar() {
		
		LocalDate today = LocalDate.now();
		String []months = {"January", "Febuary", "March", "April", "May", "June", "July", "August",
				"September", "October", "November", "December"};
		LocalDate firstDay = LocalDate.of(today.getYear(), today.getMonth(), 1);
		int days = today.lengthOfMonth();
		
		System.out.println("  " + months[today.getMonthValue() -1] +" "+ today.getYear());
		System.out.println("Su Mo Tu We Th Fr Sa");
		
		DayOfWeek numOfDate = firstDay.getDayOfWeek();
		int dayOfWeekVal = numOfDate.getValue();
		int counter = 0;
		
		if(dayOfWeekVal != 6) {
			for(int i = 0; i < dayOfWeekVal; i++) {
				System.out.print("   ");
				counter ++;
			}
		}
		for(int i = 1; i <= days; i ++) {
			if(counter%7 == 0  && dayOfWeekVal != 6)
				System.out.println("");
			if(today.getDayOfMonth() == i) {
				System.out.printf("["+ "%2d", i);
				System.out.print("]");
			}
			else
				System.out.printf("%2d",i);

			System.out.print(" ");
			counter ++;
		}
		System.out.println();
	}
}