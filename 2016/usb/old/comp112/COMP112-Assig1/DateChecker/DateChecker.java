// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 Assignment 1
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;

/**
 * Checks dates, prints out in long format and draws calendars.
 * The processDates method
 * Reads a date from the user as three integers, and then
 * (a) checks that the date is valid (ie, represents a real date,
 *     taking into account leap years), reporting if it is not valid.
 *     (It may assume the standard modern calendar, and isn't required
 *      to give correct answers for dates before the 1600's when the
 *      calendar was different.
 * (b) If the date is valid, it prints out the date in a long form: eg
 *     Monday 3rd March, 2014. This requires working out which day
 *     of the week the date is.
 *     (Core only needs dates this year)
 * (c) It draws a one week calendar, highlighting the date:
 *     It shows the seven days of the week as a row of rectangles, highlighting
 *      the day corresponding to the date. It doesn't need to show the dates
 *      for each day.
 * (d) (Completion) It draws a monthly calendar for the month containing the date:
 *     It should draw a title containing the month and the year, and a
 *     grid of rectangles for each day of the month, giving the day of the month
 *     in each rectangle. This will be between 4 rows and 6 rows of 7 rectangles.
 *     The ISO standard for calendars specifies that the first day of each week
 *     should be a Monday.
 *     Ideally, the calendar should include the last few days of the previous
 *     month when the month doesn't start on a Monday and the first few days
 *     of the next month when the month doesn't end on a Sunday.
 *
 * Reasonable design would have a number of methods, for example:
 *  isValidDate  which would return a boolean (true or false)
 *  isLeapYear   which would return a boolean (true or false)
 *  findDay      which would return the day of the week as an int (0 to 6)
 *  drawWeek     which would draw the weekly "calendar"
 *  drawMonth    which would draw the monthly calendar
 * You might choose to design it differently, but doing it all in one huge method
 *  would not be good design.
 */

public class DateChecker {

    // constants:
    /*# YOUR CODE HERE */

    /**
     * Loop to repeatedly process a date.
     * Asks the user if they want to enter another date each time.
     */
    public void processDates(){
        do  {
            UI.clearText();
            UI.clearGraphics();
            processADate();
        }
        while (UI.askBoolean("Enter another date?")); 
    }

    /**
     * Asks user for a date, then produces the required output
     */
    public void processADate(){
        /*# YOUR CODE HERE */
    }

    // Main
    /** Create a new DateChecker object and call processDates */
    public static void main(String[] arguments){
        DateChecker dc = new DateChecker();
        dc.processDates();
    }        

}
