import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;


public class Driver {
	
	
	public void startMenu()
	{
		
	}
	
	
	public static void main(String[] args) 
	{
		// get month
		Month currentMonth = LocalDate.now().getMonth();
		
		
		// << TO BE DONE >>
		// get linked list data from that month on start 
		
		
		// display startup UI - format:
		// --------------------------------------
		//            BUDGET TRACKER
		//		    Current Month: April
		//---------------------------------------
		
		// accounting for changing months to keep it centered
		
		System.out.printf("\n%60s\n","-".repeat(60));
		System.out.printf("%37s\n","BUDGET TRACKER");
		
		// keep centered with different month names
		String label = "Current Month: " + currentMonth.getDisplayName(TextStyle.FULL, Locale.US);
		int padding = (60 - label.length())/2;
		
		for (int i=0; i < padding; i++)
		{
			System.out.print(" ");
		}
		System.out.printf("%s\n", label);
		
		System.out.printf("%60s\n","-".repeat(60));
	}

}
