import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.LinkedList;

class MonthlyBreakdownTests {

	// declare the output stream captor to test printing and the standard out to reset it
	private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
	private final PrintStream standardOut = System.out;
	
	@BeforeEach
	public void setUp()
	{
		// get java to write to the captor instead of console
		System.setOut(new PrintStream(outputCaptor));
	}
	
	@Test
	void testMonthlyBreakdownData() {
		// test constructors
		LinkedList<Expense> expenseList = new LinkedList<Expense>();

		Expense expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Hulu", 3.99f, 2026, 04, 29, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Disney+", 9.99f, 2026, 04, 28, true);
		expenseList.add(expenseNode);
		
		LinkedList<Goal> goalStub = new LinkedList<Goal>();	//<<TO DO>> actually build the list once class made
		LinkedList<Debt> debtStub = new LinkedList<Debt>();	//<<TO DO>> actually build the list once class made
		
		MonthlyBreakdown jan = new MonthlyBreakdown("January", 2026);
		MonthlyBreakdown may = new MonthlyBreakdown("May", 2026, expenseList, goalStub, debtStub);
	}
	
	
	@AfterEach
	public void reset()
	{
		System.setOut(standardOut);
	}

}
