

//TODO: import all dependencies

public class MonthlyBreakdown{
	public String name;
	public int year;
	public list<Expense> monthlyExpenses;
	
	public MonthlyBreakdown(String name, int year, list<Expense> monthlyExpenses) {
		this.name = name;
		this.year = year;
		this.monthlyExpenses = monthlyExpenses;
	}
	
	public void displayProgressToGoal(Goal currentGoal) {
		//TODO: method stub
	}
	
	public void displayProgressOfDebt(Debt currentDebt) {
		//TODO: method stub
	}
	
	public void displayUpdates(list<Debt> activeDebt, list<Debt> inactiveDebt, list<Goal> activeGoals, list<Goal> completedGoals) {
		//TODO: method stub
	}
}
