import java.time.LocalDate;
import java.util.LinkedList;

public class Goal {

    // Fields
    private boolean savingsOrBudget;	//True for savings, false for budget
    private LocalDate startDate;
    private LocalDate endDate;
    String name; 
    String description;
    float amount; // (amount>0)
    float progress; // Dollar amount towards goal
    LinkedList<Expense> expenses;
    boolean complete; 

    // Constructor
    public Goal(
    		boolean savingsOrBudget, 
    		int startYear,	//startDate
    		int startMonth,
    		int startDay,
    		int endYear,	//endDate
    		int endMonth,
    		int endDay,
    		String name, 
    		String description, 
    		float amount) {
    	this.savingsOrBudget = savingsOrBudget;
    	startDate = LocalDate.of(startYear, startMonth, startDay);
    	endDate = LocalDate.of(endYear, startMonth, endMonth);
    	this.name = name;
    	this.description = description;
    	this.amount = amount;
    	progress = 0;
    	expenses = new LinkedList<Expense>();
    	complete = false;
    }

    // Methods 
 
    //Name Methods
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    //Description Methods
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String desc) {
    	description = desc;
    }
    
    //Amount Methods
    public float getAmount() {
    	return amount;
    }
    
    public void setAmount(float amount) {
    	this.amount = amount;
    }
    
    //Type Methods (True for savings, false for budget)
    public boolean getType() {
    	return savingsOrBudget;
    }
    
    public void setType(boolean savingsOrBudget) {
    	this.savingsOrBudget = savingsOrBudget;
    }
    
    //Progress Methods
    public float getProgress() {
    	return progress;
    }

    public void setProgress(float progress) {
    	this.progress = progress;
    }
    
    //Complete methods
    public boolean isComplete()
    {
    	return complete;
    }
    
    public void setComplete(boolean complete) {
    	this.complete = complete;
    }
    
    //StartDate Method
    public LocalDate getStartDate() {
    	return startDate;
    }
    
    //EndDate Methods
    public LocalDate getEndDate() {
    	return endDate;
    }
    
    public void setEndDate(int year, int month, int day) {
    	endDate = LocalDate.of(year, month, day);
    }
    
    //ExpenseList Methods
    public LinkedList<Expense> getExpenseList() {
    	return expenses;
    }
    
    public void logExpense(Expense expense) {
    	this.expenses.add(expense);
    }
}
