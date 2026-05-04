import java.time.LocalDate;


/* class Expense:
 *	Dollar amount for the expense is stored in the amount field.
 *	Correlating date of the expense is stored in the date field (year, month, day).
 *	User defined name of the expense is stored in the name field.
 *	Paid status of the expense is stored in the paid field, True-paid, False-unpaid 
 */
public class Expense {
	
	private float amount; 
	private LocalDate date;
	private String name;
	private boolean paid; 
	private boolean isIncome;
	private boolean isRecurring;
	
	//Constructor
	public Expense(String name, float amount, int year, int month, int day)
	{
		this.name = name;
		this.amount = amount;
		this.date = LocalDate.of(year, month, day);
		this.paid = false;
		this.isIncome = false;
        this.isRecurring = false;
	}
	
	//Constructor (paid option)
	public Expense(String name, float amount, int year, int month, int day, boolean paid)
	{
		this.name = name;
		this.amount = amount;
		this.date = LocalDate.of(year, month, day);
		this.paid = paid;
		this.isIncome = false;
        this.isRecurring = false;
	}
	
	//New Constructor with all fields
	public Expense(String name, float amount, int year, int month, int day, boolean paid, boolean isIncome, boolean isRecurring)
	{
		this.name = name;
		this.amount = amount;
		this.date = LocalDate.of(year, month, day);
		this.paid = paid;
		this.isIncome = false;
        this.isRecurring = false;
	}
	
	//Getter methods
	public float getAmount(){
		return amount;
	}
	
	public LocalDate getDate(){
		return date;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isPaid(){
		return paid;
	}
	
	public boolean isIncome() {
		return isIncome;
	}
	
	public boolean isRecurring() {
		return isRecurring;
	}
	
	//Setter methods
	public void setAmount(float amount){
		this.amount = amount;
	}
	
	public void setDate(int year, int month, int day) {
		this.date = LocalDate.of(year, month, day);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	public void setIncome(boolean isIncome) { 
		this.isIncome = isIncome; 
	}
    public void setRecurring(boolean isRecurring) { 
    	this.isRecurring = isRecurring; 
    }
	//Method outputs formatted expense.
	public void listExpense() {
		String tag = "";
        if (isIncome) tag += "[INCOME] ";
        if (isRecurring) tag += "[RECURRING] ";
        System.out.printf("%-10s %-10.2f %-12s %-10s %s\n",
            name, amount, date.toString(),
            paid ? "Paid" : "Unpaid", tag);
	}
}
