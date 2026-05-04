

//TODO: import all dependencies
//import java.time.Month;
//import java.time.format.TextStyle;
//import java.util.Locale;
import java.util.Scanner;
import java.util.LinkedList;

public class MonthlyBreakdown {
	
	//Fields
	private String name;
	private int year;
	private LinkedList<Expense> monthlyExpenses;
	private LinkedList<Goal> goals;
	private LinkedList<Debt> debts;
	
	// Default constructor
	public MonthlyBreakdown(String name, int year) {
		this.name = name;
		this.year = year;
		monthlyExpenses = new LinkedList<>();
		goals = new LinkedList<>();
		debts = new LinkedList<>();

	}//End MonthlyBreakdown(String, int)
	
	// Fully parameterized constructor
	public MonthlyBreakdown(String name, int year, LinkedList<Expense> monthlyExpenses, LinkedList<Goal> goals, LinkedList<Debt> debts) {
		this.name = name;
		this.year = year;
		this.monthlyExpenses = monthlyExpenses;
		this.goals = goals;
		this.debts = debts;

	}//End MonthlyBreakdown(String, int, LinkedList)
	
	//Methods

	/*retrieve name */
	public String getName() {
		return name;
	}
	
	/*retrieve year */
	public int getYear() {
		return year;
	}
	
	/*retrieve expense list */
	public LinkedList<Expense> getExpenses() {
		return monthlyExpenses;
	}
	
	/*retrieve debt list */
	public LinkedList<Debt> getDebts() {
		return debts;
	}
	
	/*retrieve goal list */
	public LinkedList<Goal> getGoals() {
		return goals;
	}
	
	/*Method totals all current monthly expenses. */
	public float getTotalExpenses() {
		float total = 0;
		
		//For each expense in the list(monthlyExpenses), 
		//  add the amount to total.
		for (Expense expense : monthlyExpenses) {
			total += expense.getAmount();
		}
		
		return total;
	}//End getTotalEpenses()
<<<<<<< HEAD
	
	public int displayMonthlyBreakdown(Scanner inFile) {
		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.printf("%31s\n", "MONTHLY BREAKDOWN");
		System.out.printf("%s\n", "-".repeat(50));
		
		System.out.printf("Month: %s %d\n", name, year);
		
		//Display alert msg if no expenses have been entered.
		if (monthlyExpenses.isEmpty()) {
			System.out.println("[!] Alert: No Expenses Have Been Entered.");
			System.out.println("Total Expenses: $0.00");
			return 0;
		}
		
		System.out.printf("Total Expenses: $%.2f\n\n", getTotalExpenses());
		
		// Formatted headline
		System.out.println("Expenses (by item):");
		System.out.printf("%-10s %-10s %-12s %-10s\n", "Name", "Amount", "Date", "Paid");
		
		//For each expense in the list(monthlyExpenses),
		//  list out the expense.
		int index = 1;
		for(int i = 0; i < 5; i++) {
			if (monthlyExpenses.size() >= index) {
				monthlyExpenses.get(index - 1).listExpense(); 
				index++;
			}
			else
			{
				System.out.println("<<END OF EXPENSE LIST>>");
				break;
			}
		}
		
		//List out goals and debts
		displayUpdates();
=======


	// Prints the menu and gets user input
	public int monthlyBreakdownMenu(Scanner inFile, int index) {
>>>>>>> 9a5b98d3947c2423b031def19e85241146ab3ed4
		
		// it's an infinite loop but I swear the return statements give it an end
		while (true) {
			// display menu items
			System.out.printf("\n\t%s\n", "  1. Return to Home Page");
			System.out.printf("\t%s\n", "  2. List next 5 items");
			System.out.printf("\t%s\n", "  3. Change Month");
		
			// get user input
			int userChoice;
			userChoice = Driver.getMenuOption(3, inFile);
		
			switch (userChoice) {
				case 0:
					return 0;
			
				case 1:
					return 0;
				
				case 2:
					System.out.printf("\nExpenses (by item):\n");
					System.out.printf("%-10s %-10s %-12s %-10s\n", "Name", "Amount", "Date", "Paid");
					
					for(int i = 0; i < 10; i++) {
						if (monthlyExpenses.size() > index) {
							monthlyExpenses.get(index).listExpense(); 
							index++;
						}
						else
						{
							System.out.printf("\n<<END OF EXPENSE LIST>>\n");
							break;
						}
					}
				break;
				
				case 3: 
				// display month items
					System.out.printf("\n%s\n", "Enter the number of the month you would like to swap to (1=Jan, 2=Feb, ect)");
			
					return Driver.getMenuOption(12, inFile);
				}
			}	
		
	}
		
			
	public int displayMonthlyBreakdown(Scanner inFile) {
		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.printf("%31s\n", "MONTHLY BREAKDOWN");
		System.out.printf("%s\n", "-".repeat(50));
		
		System.out.printf("Month: %s %d\n", name, year);
		
		// index used to track how many expenses have been printed
		int index = 0;

		
		//Display alert msg if no expenses have been entered.
		if (monthlyExpenses.isEmpty()) {
			System.out.println("[!] Alert: No Expenses Have Been Entered.");
			System.out.println("Total Expenses: $0.00");
			return monthlyBreakdownMenu(inFile, index);	
		}
		
		System.out.printf("Total Expenses: $%.2f\n\n", getTotalExpenses());
		
		// Formatted headline
		System.out.println("Expenses (by item):");
		System.out.printf("%-10s %-10s %-12s %-10s\n", "Name", "Amount", "Date", "Paid");
		
		//For each expense in the list(monthlyExpenses),
		//  list out the expense.
		for(int i = 0; i < 5; i++) {
			if (monthlyExpenses.size() > index) {
				monthlyExpenses.get(index).listExpense(); 
				index++;
			}
			else
			{
				System.out.println("\n<<END OF EXPENSE LIST>>\n");
				break;
			}
		}
		
		//List out goals and debts
		displayUpdates();

		return monthlyBreakdownMenu(inFile, index);
		
	}//End displayMonthlyBreakdown()
	
	public void displayProgressToGoal(Goal currentGoal) {
		/*
		 * displayProgressToGoal() - Helper method for displayUpdates(), prints status of single goal
		 * Grabs boolean complete, if not true, then grab progress
		 * Input: Goal object, Output: Printed string
		 * Ex: <Goal #>	Save $500 | Progress: 30.75% Complete
		 */
		
		//Displaying:	<Name> | Progress:...
		System.out.print("\t" + currentGoal.getName() + " | Progress: ");
		
		//Displaying:			  			 ...<Progress>% Complete
		if(currentGoal.isComplete() == true)	//Check if goal is completed
			System.out.print("Complete\n");
		else {									//Print progress
			System.out.printf("%.2f", currentGoal.getProgress());	//Format printed float, round to 2 decimal places
			System.out.print("% Complete\n");
		}
	}
	
	public void displayProgressOfDebt(Debt currentDebt) {
		/*
		 * displayProgressOfDebt() - Helper method for displayUpdates(), prints status of single debt
		 * Grabs boolean status, if not false (inactive), then grab progress
		 * Input: Debt object, Output: Printed String
		 * Ex: <Debt #>	Car Loan | Principal: $10000 | Progress: 50.35% Paid Off
		 */
		
		//Displaying:	<Name> | Principal: <Amount> | Progress:...
		System.out.print("\t" + currentDebt.getName() + " | Principal: $" + 
			currentDebt.getPrincipalAmount() + " | Progress: ");
		
		//Displaying: 								 			   ...<Progress>% Paid Off
		if(currentDebt.getStatus() == false)	//Check if debt is inactive
			System.out.print("100% Paid Off\n");
		else {									//Print progress
			System.out.printf("%.2f", currentDebt.getProgress());	//Format printed float, round to 2 decimal places
			System.out.print("% Paid Off\n");
		}
	}
		
	public void displayUpdates() {
		/*
		 * displayUpdates() - Prints two lists of goals and debts, using helper methods for each item
		 * Example format --
		 * Goals:
		 * 1.)	Save $500 | Progress: 30.75% Complete
		 * 2.)	Invest $200 in Stocks | Progress: Complete
		 * 
		 * Debts:
		 * 	Total Debt -- $521200
		 * 1.)	Car Loan | Principal: $14000 | Progress: 20% Paid Off
		 * 2.)	Mortgage | Principal: $600000 | Progress: 15% Paid Off
		 */
		
		//Goals Display -------------------------------------------------------------------------------------------
		System.out.println("Goals:");
		
		if(goals.size() <= 0)	//Check for empty LinkedList
			System.out.println("[!] Alert - No goals added.\n");
		else {
			for(int i = 1; i <= goals.size(); i++) {	//int i is set to 1 for printing list 1.) -> n.)
				System.out.print(i + ".)");
				displayProgressToGoal(goals.get(i - 1));	//Get item at index i - 1 to account for offset above
			}
		}
		
		//Debts Display -------------------------------------------------------------------------------------------
		System.out.println("Debts:");
		
		if(debts.size() <= 0)	//Check for empty LinkedList
			System.out.println("[!] Alert - No debts added.\n");
		else {
			//Display total
			float sum = 0;
			for(Debt item : debts)		//Add up all the remaining debt for every Debt object in the list
				sum += item.getRemainingBalance();
			System.out.printf("\tTotal Debt -- $%.2f\n", sum);
			
			//Display individual debts and progress
			for(int i = 1; i <= debts.size(); i++) {	//int i is set to 1 for printing list 1.) -> n.)
				System.out.print(i + ".)");
				displayProgressOfDebt(debts.get(i - 1));	//Get item at index i - 1 to account for offset above
			}
		}
	}
}
