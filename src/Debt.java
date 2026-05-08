import java.time.LocalDate;
import java.util.LinkedList;

public class Debt {

    // **Add Initial start date**
    // Fields
    String name; 
    float principle;
    LocalDate endDate;
    LinkedList<Expense> paymentHistory;
    float interestRate;
    boolean compoundOrSimple;
    float remainingBalance;
    boolean status; //active vs inactive
    // Constructors
    
    public Debt(
    		String name, 
    		float principle,
    		int year,
    		int month,
    		int day, 
    		float interest,
    		boolean type,
    		float remainingBalance, 
    		boolean status, 
    		LinkedList<Expense> payments) {
    	this.name = name;
    	this.principle = principle;
    	endDate = LocalDate.of(year, month, day);
    	interestRate = interest;
    	compoundOrSimple = type;
    	this.remainingBalance = remainingBalance;
    	this.status = status;
    	paymentHistory = payments;
    }
    
    
    public Debt() {
    	this.name = "";
    	this.principle = 0;
    	endDate = LocalDate.of(1, 1, 1);
    	interestRate = 0;
    	compoundOrSimple = true;
    	this.remainingBalance = 0;
    	this.status = true;
    	paymentHistory = new LinkedList<Expense>() ;
    }

    // Methods
    
    public String getName()
    {
    	return name;
    }
    
    public float getPrincipalAmount()
    {
    	return principle;
    }
    
    
    public boolean getStatus()
    {
    	return status;
    }
    
    
    public float getProgress()
    {
    	return (principle - remainingBalance);
    }
    
    
    public float getRemainingBalance()
    {
    	return remainingBalance;
    }
    
    
}
