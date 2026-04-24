import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

//
// Driver - holds the main function as well as utilities directly used by the main function including a function to get menu options 
//			and the start menu itself
// N/A; N/A
//
public class Driver 
{
	// methods
	
	//
	// getMenuOption - Allows programmer to enter an end value. Will prompt user to enter number from 1 to end or to press 0 to exit the program. 
	// 				   Returns the value from 0 to end entered by user.
	// integer END (largest number user can enter), Scanner INFILE (the user input handle originally created in main); integer USERCHOICE (the menu item the user chose)
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
			System.out.printf("\n%s\n","-".repeat(50));
			System.out.printf("\n%s\n\n", "[!] ERROR: Invalid Selection [!]");
			System.out.printf("%s%d%s%d%s", "Please enter a selection (", 1, "-", end, ") or '0' to exit: " );
			
			
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
		}
		
		return choice;
	}
	
	//
	// startMenu - displays the start menu
	// Month CURRENTMONTH (the current month), Scanner INFILE (the user input handle originally created in main); integer USERCHOICE (the menu item the user chose)
	//
	public static int startMenu(Month currentMonth, Scanner inFile)
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
				
				System.out.printf("\n%s\n","-".repeat(50));
				System.out.printf("%32s\n","BUDGET TRACKER");
				
				// keep centered with different month names
				String label = "Current Month: " + currentMonth.getDisplayName(TextStyle.FULL, Locale.US);
				int padding = (50 - label.length())/2;
				
				for (int i=0; i < padding; i++)
				{
					System.out.print(" ");
				}
				System.out.printf("%s\n", label);
				
				System.out.printf("%s\n","-".repeat(50));
				
				System.out.printf("\n\t%s\n", "Start Menu Options:");
				System.out.printf("\t%s\n", "  1. Overall Expenses");
				System.out.printf("\t%s\n", "  2. Monthly Overview");
				System.out.printf("\t%s\n", "  3. Add Expense");
				System.out.printf("\t%s\n", "  4. Manage Goals");
				System.out.printf("\t%s\n", "  5. Manage Debt");
							
				// get user input for menu choice
				int choice = getMenuOption(5, inFile);
				
				return choice;
			}
	
	//
	// main - the driver which start the program and sets up the program via getting the linked list of expenses and the current month
	// N/A; N/A
	//
	public static void main() 
	{
		// get month
		Month currentMonth = LocalDate.now().getMonth();
		
		// << TO BE DONE>>
		// declare all needed classes
		int userChoice;
		Scanner input = new Scanner(System.in);

		
		// << TO BE DONE >>
		// get linked list data from that month on start 
		
		// call start menu
		do
		{
		userChoice = startMenu(currentMonth, input);
		
		// menu choices
		switch(userChoice) 
		{
		
		case 1:
			// DO OPTION 1
			System.out.printf("\n%s\n","-".repeat(50));
			System.out.printf("\n%s\n","Entering overall expenses page...");
			break;
		
		case 2:
			// DO OPTION 2
			System.out.printf("\n%s\n","-".repeat(50));
			System.out.printf("\n%s\n","Entering monthly overview page...");
			break;
		
		case 3:
			// DO OPTION 3
			System.out.printf("\n%s\n","-".repeat(50));
			System.out.printf("\n%s\n","Entering add expense page...");
			break;
		
		case 4:
			// DO OPTION 4
			System.out.printf("\n%s\n","-".repeat(50));
			System.out.printf("\n%s\n","Entering goals page...");
			break;
		
		case 5:
			// DO OPTION 5
			System.out.printf("\n%s\n","-".repeat(50));
			System.out.printf("\n%s\n","Entering debt page...");
			break;
		}
		
		} while (userChoice !=0);
		
		// ONLY CLOSE SCANNER AT VERY END OR WILL THROW ERROR BECAUSE REMOVES OBJECT
		input.close();
	}
}
