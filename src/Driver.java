import java.io.*;
import java.util.LinkedList;
import java.util.Locale;
import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.Collections;
//
// Driver - holds the main function as well as utilities directly used by the main function including a function to get menu options 
//			and the start menu itself
// N/A; N/A
//
public class Driver 
{
	// Fields
	private static Month currentMonth;
	private static Month[] monthNames;
	private static LinkedList<Expense>[] allMonths;
	private static Scanner input;
	private static LinkedList<Goal> goalStub;
	private static LinkedList<Debt> debtStub;
	private static MonthlyBreakdown currentBreakdown;
	
	// methods
	
	// 
	// sets up the program
	//
	//
	public static void setup()
	{
		// get current month
		currentMonth = LocalDate.now().getMonth();
		monthNames = Month.values();
		
		// get linked list data from that month on start 
		// below is an array of expense linked lists, month# = index-1
		allMonths = new LinkedList[]
		{
		SaveAsTXT.loadFromFile("JanuaryExpenses.txt"),
		SaveAsTXT.loadFromFile("FebrurayExpenses.txt"),
		SaveAsTXT.loadFromFile("MarchExpenses.txt"),
		SaveAsTXT.loadFromFile("AprilExpenses.txt"),
		SaveAsTXT.loadFromFile("MayExpenses.txt"),
		SaveAsTXT.loadFromFile("JuneExpenses.txt"),
		SaveAsTXT.loadFromFile("JulyExpenses.txt"),
		SaveAsTXT.loadFromFile("AugustExpenses.txt"),
		SaveAsTXT.loadFromFile("SeptemeberExpenses.txt"),
		SaveAsTXT.loadFromFile("OctoberExpenses.txt"),
		SaveAsTXT.loadFromFile("NovemberExpenses.txt"),
		SaveAsTXT.loadFromFile("DecemberExpenses.txt")
		};
		
		int currentMonthIndex = LocalDate.now().getMonthValue() - 1;
        int prevMonthIndex = (currentMonthIndex == 0) ? 11 : currentMonthIndex - 1;
        
        for (Expense e : allMonths[prevMonthIndex]) {
            if (e.isRecurring()) {

                // figure out what day to use in the current month
                // if the original day is beyond the length of current month, use last day
                // example: day 31 in a month that only has 30 days becomes day 30
                int originalDay = e.getDate().getDayOfMonth();
                int currentYear = LocalDate.now().getYear();
                int currentMonthNumber = LocalDate.now().getMonthValue();

                // lengthOfMonth() returns how many days are in that month
                // it accounts for leap years automatically
                int maxDay = LocalDate.of(currentYear, currentMonthNumber, 1)
                                      .lengthOfMonth();
                int targetDay = Math.min(originalDay, maxDay);

                // build the new date in the current month
                LocalDate newDate = LocalDate.of(currentYear, currentMonthNumber, targetDay);

                // check if this recurring entry already exists in the current month
                // to avoid duplicating it every time the program opens
                boolean alreadyExists = false;
                for (Expense existing : allMonths[currentMonthIndex]) {
                    if (existing.getName().equals(e.getName()) &&
                        existing.getDate().equals(newDate)) {
                        alreadyExists = true;
                        break;
                    }
                }

                if (!alreadyExists) {
                    // create a copy with the updated date
                    Expense recurringCopy = new Expense(
                        e.getName(), e.getAmount(),
                        currentYear, currentMonthNumber, targetDay,
                        e.isPaid(), e.isIncome(), true // keep isRecurring = true
                    );
                    allMonths[currentMonthIndex].add(recurringCopy);
                }
            }
        }

        // This will be removed to implement the Strategy design pattern
        // sort the current month after adding recurring entries
//        Collections.sort(allMonths[currentMonthIndex],
//            new Comparator<Expense>() {
//                public int compare(Expense a, Expense b) {
//                    return a.getDate().compareTo(b.getDate());
//                }
//            }
//        );
        
        // Added Strategy design pattern
        new SortByDateStrategy().sort(allMonths[currentMonthIndex]);
        
		input = new Scanner(System.in);
		goalStub = new LinkedList<Goal>();
		debtStub = new LinkedList<Debt>();
		currentBreakdown = 
				new MonthlyBreakdown(
						currentMonth.getDisplayName(TextStyle.FULL, Locale.US), 
						LocalDate.now().getYear(), 
						allMonths[currentMonth.getValue() - 1],
						goalStub,								//<<TO DO>> fix once goal class is created
						debtStub								//<<TO DO>> fix once debt class is created
					);
	}
	
	//
	// getMenuOption - Allows programmer to enter an end value. Will prompt user to enter number from 1 to end or to press 0 to exit the program. 
	// 				   Returns the value from 0 to end entered by user.
	// integer END (largest number user can enter), Scanner INFILE (the user input handle originally created in main); integer USERCHOICE 
	//																												   (the menu item the user chose)
	//
	public static int getMenuOption(int end, Scanner inFile)
	{
		System.out.printf("%s%d%s%d%s", "Please enter a selection (", 1, "-", end, ") or '0' to exit: " );

		int choice;
		
		// verify next input is an int so it doesn't throw an exception and exit
		if (inFile.hasNextInt() == false)
		{
			inFile.next();
			choice = -1;
		}
		else
		{
			choice = inFile.nextInt();
		}
		
				
		// loop to make user enter number between start and end
		while((choice < 0) || (choice > end))
		{					
			System.out.printf("\n%s\n","=".repeat(50));
			System.out.printf("\n%s\n\n", "[!] ERROR: Invalid Selection [!]");
			System.out.printf("%s%d%s%d%s", "Please enter a selection (", 1, "-", end, ") or '0' to exit: " );
			
			
			// Verify next input is an int so it doesn't throw an exception and exit
			if (inFile.hasNextInt() == false)
			{
				inFile.next();
				choice = -1;
			}
			else
			{
				choice = inFile.nextInt();
			}
		}
		
		return choice;
	}
	
	
	public static void displayOverallExpense(MonthlyBreakdown currentMonth, int startIndex)
    {

        // ===== TOTAL SPENDING =====
        // retrieves total spending for the entire month from MonthlyBreakdown
        float totalSpending = currentMonth.getTotalExpenses();

        // prints total formatted to 2 decimal places
        System.out.printf("Total Spending: $%.2f%n%n", totalSpending);

        // ===== EXPENSE LIST HEADER =====
        // prints column labels for expense table      
        System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║ ------    Expenses    ------ ║");
        System.out.println("\t╚══════════════════════════════╝\n");

        // formatted columns: index, name, amount
        System.out.printf("%-5s %-25s %-10s%n", "#", "Name", "Amount");
        System.out.printf("%s\n","-".repeat(50));

        // ===== PAGINATION SETUP =====
        // tracks number of items printed (limit = MAX_ITEMS)
        int itemsShown = 0;

        // convert from 1-based index (user view) to 0-based index (list access)
        int expenseIndex = startIndex;

        // ===== DISPLAY EXPENSES =====
        // loops through list and prints up to MAX_ITEMS (10) expenses
        while ((expenseIndex < currentMonth.getExpenses().size()) && (itemsShown < 10))
        {
            // retrieve current expense object
            Expense currentExpense = currentMonth.getExpenses().get(expenseIndex);

            // print formatted row with index, name, and amount
            System.out.printf("%-5d %-25s $%-10.2f%n",
                    expenseIndex + 1,              // display index (1-based)
                    currentExpense.getName(),      // expense name
                    currentExpense.getAmount());   // expense amount

            // move to next expense in list
            expenseIndex++;

            // increment count of displayed items
            itemsShown++;
        }

        // ===== EMPTY STATE =====
        // if no expenses were displayed, notify the user
        if (itemsShown == 0)
        {
            System.out.println("\n\tNo expenses found for this month.\n");
        }

        // ===== FOOTER =====
        // separator line for clean UI
        System.out.printf("\n%s\n","=".repeat(50));

    }
	
	//
	// startMenu - displays the start menu
	// Month CURRENTMONTH (the current month), Scanner INFILE (the user input handle originally created in main); integer USERCHOICE (the menu item the user chose)
	//
	public static int startMenu(Month currentMonth, MonthlyBreakdown currentBreakdown, Scanner inFile)
	{
		
		// display startup UI - format:
				// --------------------------------------
				//            BUDGET TRACKER
				//		    Current Month: April
				//---------------------------------------
				//
				// Start Menu Options:
				//	1. Overall Expenses
				//	2. Monthly Overview
				//	3. Add Expense
				//	4. Goals
				//	5. Debt
				//
				// Please enter a selection (1-5) or '0' to exit: <user space>
				System.out.printf("\n\n\n\n%s\n","*".repeat(50));
				System.out.printf("%s\n","*".repeat(50));
				System.out.printf("%32s\n","BUDGET TRACKER");
				
				// keep centered with different month names
				String label = "Current Month: " + currentMonth.getDisplayName(TextStyle.FULL, Locale.US);
				int padding = (50 - label.length())/2;
				
				for (int i=0; i < padding; i++)
				{
					System.out.print(" ");
				}
				System.out.printf("%s\n", label);
				
				System.out.printf("%s\n","*".repeat(50));
				System.out.printf("%s\n\n","*".repeat(50));
				
				displayOverallExpense(currentBreakdown, 0);
				
				System.out.printf("\n\t%s\n\n", "Start Menu Options:");

				System.out.printf("\t%s\n", "  1. Monthly Overview");
				System.out.printf("\t%s\n", "  2. Add/Modify/Delete Expense");
				System.out.printf("\t%s\n", "  3. Manage Goals");
				System.out.printf("\t%s\n", "  4. Manage Debt\n");
							
				// get user input for menu choice
				int choice = getMenuOption(4, inFile);
				
				
				return choice;
			}
	
	//
	// main - the driver which start the program and sets up the program via getting the linked list of expenses and the current month
	// N/A; N/A
	//
	public static void main(String[] args) 
	{
		setup();
		int userChoice;

		// call start menu
		do
		{
		userChoice = startMenu(currentMonth,currentBreakdown, input);
		
		// menu choices
		switch(userChoice) 
		{
		case 1:
			// View the monthly expenses breakdown, if there are none display alert w/ $0 amount.
			System.out.printf("\n%s\n","=".repeat(50));	
			
			// if 0 standard return, if non-0 change to month number
			int status;
			currentBreakdown = 
					new MonthlyBreakdown(
							currentMonth.getDisplayName(TextStyle.FULL, Locale.US), 
							LocalDate.now().getYear(), 
							allMonths[currentMonth.getValue() - 1],
							goalStub,								//<<TO DO>> fix once goal class is created
							debtStub								//<<TO DO>> fix once debt class is created
						);
			status = currentBreakdown.displayMonthlyBreakdown(input);
			
			while (status != 0)
			{
				currentBreakdown = 
						new MonthlyBreakdown(
								monthNames[status-1].name(), 
								LocalDate.now().getYear(),
								allMonths[status-1],
								goalStub,		//<<TO DO>> fix once goal class is created
								debtStub		//<<TO DO>> fix once debt class is created
							);
				
				status = currentBreakdown.displayMonthlyBreakdown(input);
			}
			
			break;
		
		case 2:
			// DO OPTION 2
			System.out.printf("\n%s\n","=".repeat(50));
			ExpenseUI.showExpenseMenu(input, allMonths);
			break;
		
		case 3:
			// DO OPTION 4
			System.out.printf("\n%s\n","=".repeat(50));
			System.out.printf("\n%s\n","Entering goals page...");
			GoalUi.goalMenu(input, goalStub);
			break;
		
		case 4:
			// DO OPTION 5
			System.out.printf("\n%s\n","=".repeat(50));
			System.out.printf("\n%s\n","Entering debt page...");
			
			break;
		}
		
		} while (userChoice !=0);
		
		// ONLY CLOSE SCANNER AT VERY END OR WILL THROW ERROR BECAUSE REMOVES OBJECT
		input.close();
	}
}
