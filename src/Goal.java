import java.time.LocalDate;
import java.util.LinkedList;

public class Goal {

    // Fields
    private LocalDate startDate;
    String name; 
    String description;
    float amount; // (amount>0)
    float progress; //Dollar amount towards goal
    boolean complete; 

    // Constructor
    public Goal(
    		int startYear,	//startDate
    		int startMonth,
    		int startDay,
    		String name, 
    		String description, 
    		float amount) {
    	startDate = LocalDate.of(startYear, startMonth, startDay);
    	this.name = name;
    	this.description = description;
    	this.amount = amount;
    	progress = 0;
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
    
    //Progress Methods (Convert from/to percentage)
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
    
    //StartDate Methods
    public LocalDate getStartDate() {
    	return startDate;
    }
    
    public void setStartDate(int year, int month, int day) {
    	startDate = LocalDate.of(year, month, day);
    }
}