

//TODO: import all dependencies
//import java.time.Month;
//import java.time.format.TextStyle;
//import java.util.Locale;
import java.util.Scanner;
import java.util.LinkedList;

public class MonthlyBreakdown {
	
	//Fields
	public String name;
	public int year;
	public LinkedList<Expense> monthlyExpenses;
	
	// Default constructor
	public MonthlyBreakdown(String name, int year)
	{
		this.name = name;
		this.year = year;
		this.monthlyExpenses = new LinkedList<>();

	}//End MonthlyBreakdown(String, int)
	
	// Fully parameterized constructor
	public MonthlyBreakdown(String name, int year, LinkedList<Expense> monthlyExpenses) 
	{
		this.name = name;
		this.year = year;
		this.monthlyExpenses = monthlyExpenses;		

	}//End MonthlyBreakdown(String, int, LinkedList)
	
	//Methods

	/*Method totals all current monthly expenses. */
	public float getTotalExpenses() 
	{
		float total = 0;
		
		//For each expense in the list(monthlyExpenses), 
		//  add the amount to total.
		for (Expense expense : monthlyExpenses)
		{
			total += expense.getAmount();
		}
		
		return total; 

	}//End getTotalEpenses()
	
	public int displayMonthlyBreakdown(Scanner inFile)
	{
		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.printf("%31s\n", "MONTHLY BREAKDOWN");
		System.out.printf("%s\n", "-".repeat(50));
		
		System.out.printf("Month: %s %d\n", name, year);
		
		//Display alert msg if no expenses have been entered.
		if (monthlyExpenses.isEmpty())
		{
			System.out.println("[!] Alert: No Expenses Have Been Entered.");
			System.out.println("Total Expenses: $0.00");
			return 0;
		}
		
		System.out.printf("Total Expenses: $%.2f\n\n", getTotalExpenses());
		
		// Formatted headline
		System.out.printf("%-10s %-10s %-12s %-10s\n", "Name", "Amount", "Date", "Paid");
		
		//For each expense in the list(monthlyExpenses),
		//  list out the expense.
		int index = 1;
		for(int i = 0; i < 5; i++)
		{
		if (monthlyExpenses.size() > index) 
		{
			monthlyExpenses.get(index).listExpense(); 
			index++;
		}
		}
		
		//  i know it's an infinite loop but I swear the return statements give it an end
		while (true)
		{
		// display menu items
		System.out.printf("\n\t%s\n", "  1. Return to Home Page");
		System.out.printf("\t%s\n", "  2. List next 10 items");
		System.out.printf("\t%s\n", "  3. Change Month");
		
		// get user input
		int userChoice;
		userChoice = Driver.getMenuOption(3, inFile);
		
		switch (userChoice)
		{
			case 0:
				return 0;
			
			case 1:
				return 0;
				
			case 2:
				for(int i = 0; i < 10; i++)
				{
				if (monthlyExpenses.size() > index) 
				{
					monthlyExpenses.get(index).listExpense(); 
					index++;
				}
				}
				break;
				
			case 3: 
				// display month items
				System.out.printf("\n\t%s\n", "  1. January");
				System.out.printf("\t%s\n", "  2. February");
				System.out.printf("\t%s\n", "  3. March");
				System.out.printf("\t%s\n", "  4. April");
				System.out.printf("\t%s\n", "  5. May");
				System.out.printf("\t%s\n", "  6. June");
				System.out.printf("\t%s\n", "  7. July");
				System.out.printf("\t%s\n", "  8. August");
				System.out.printf("\t%s\n", "  9. September");
				System.out.printf("\t%s\n", "  10. October");
				System.out.printf("\t%s\n", "  11. November");
				System.out.printf("\t%s\n", "  12. December");
				
				return Driver.getMenuOption(12, inFile);
		}
		}
		
		
	}//End displayMonthlyBreakdown()
	
	//public void displayProgressToGoal(Goal currentGoal)
	public void displayProgressToGoal() {
		//TODO: method stub
		System.out.println("TODO:Full implementation");
	}
	
	//public void displayProgressToGoal(Goal currentGoal)
	public void displayProgressOfDebt() {
		//TODO: method stub
		System.out.println("TODO:Full implementation");
	}	
		
	//displayUpdates(list<Debt> activeDebt, list<Debt> inactiveDebt, list<Goal> activeGoals, list<Goal> completedGoals) {
	public void displayUpdates() {
		//TODO: method stub
		System.out.println("TODO:Full implementation");
	}
}
