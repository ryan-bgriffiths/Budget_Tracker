import java.time.LocalDate;
import java.util.LinkedList;

public class Goal {

    // Fields
    private boolean savingsOrBudget;
    private LocalDate startDate;
    private LocalDate endDate;
    String name; 
    String description;
    float amount; // (amount>0)
    float progress; // Dollar amount towards goal
    LinkedList<Expense> expenses;
    boolean complete; 

    // Constructors

    // Methods 
    // << METHOD STUB>> << FINISH LATER TO DO>> 
    public String getName()
    {
    	return "";
    }
    
    
    // << METHOD STUB>> << FINISH LATER TO DO>> 
    public float getProgress()
    {
    	return 0;
    }

    // << METHOD STUB>> << FINISH LATER TO DO>> 
    public boolean isComplete()
    {
    	return false;
    }
}
