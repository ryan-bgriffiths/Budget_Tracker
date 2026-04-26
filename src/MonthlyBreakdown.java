//TODO: import all dependencies
//import java.time.Month;
//import java.time.format.TextStyle;
//import java.util.Locale;
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
	}
	
	// Fully parameterized constructor
	public MonthlyBreakdown(String name, int year, LinkedList<Expense> monthlyExpenses) 
	{
		this.name = name;
		this.year = year;
		this.monthlyExpenses = monthlyExpenses;		
	}
	
	//Methods
	
	public float getTotalExpenses() 
	{
		float total = 0;
		
		for (Expense expense : monthlyExpenses)
		{
			total += expense.getAmount();
		}
		
		return total; 
	}
	
	public void displayMonthlyBreakdown()
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
			return;
		}
		
		System.out.printf("Total Expenses: $%.2f\n\n", getTotalExpenses());
		
		System.out.printf("%-10s %-10s %-12s %-10s\n", "Name", "Amount", "Date", "Paid");
		
		for (Expense expense : monthlyExpenses) 
		{
			expense.listExpense(); 
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