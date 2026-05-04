import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

class MonthlyBreakdownTests {

	// declare the input/output stream captor to test printing and the standard out to reset it
	private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
	private final PrintStream standardOut = System.out;
	ByteArrayInputStream in;
	Scanner inFile;
	
	// declared as global so can be used throughout testing
	MonthlyBreakdown jan;
	MonthlyBreakdown may;
	LinkedList<Debt> debtStub;
	LinkedList<Goal> goalStub;
	LinkedList<Expense> expenseList;
	
	@BeforeEach
	public void setUp()
	{
		// get java to write to the captor instead of console
		System.setOut(new PrintStream(outputCaptor));
		
		// test constructors
		expenseList = new LinkedList<Expense>();

		Expense expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Hulu", 3.99f, 2026, 04, 29, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Disney+", 9.99f, 2026, 04, 28, true);
		expenseList.add(expenseNode);
		
		goalStub = new LinkedList<Goal>();	//<<TO DO>> actually build the list once class made
		debtStub = new LinkedList<Debt>();	//<<TO DO>> actually build the list once class made
		
		jan = new MonthlyBreakdown("January", 2026);
		may = new MonthlyBreakdown("May", 2026, expenseList, goalStub, debtStub);
	}
	
	@Test
	public void testGetName()
	{
		assertEquals("January", jan.getName());
		assertEquals("May", may.getName());
	}
	
	@Test
	public void testGetYear()
	{
		assertEquals(2026, jan.getYear());
		assertEquals(2026, may.getYear());
	}
	
	@Test 
	public void getExpenses()
	{
		assertEquals(0, jan.getExpenses().size());
		assertEquals(expenseList.get(0), may.getExpenses().get(0));
		assertEquals(expenseList.get(1), may.getExpenses().get(1));
		assertEquals(expenseList.get(2), may.getExpenses().get(2));
	}
	
	@Test
	public void testGetGoals()
	{
		assertEquals(0, jan.getGoals().size());
		/* << TO DO ONCE CLASSES ARE CREATED >>
		 * assertEquals(goalStub.get(0), may.getGoals().get(0));
		assertEquals(goalStub.get(0), may.getGoals().get(0));
		assertEquals(goalStub.get(0), may.getGoals().get(0));*/
	}
	
	@Test
	public void testGetDebts()
	{
		assertEquals(0, jan.getDebts().size());
		/* << TO DO ONCE CLASSES ARE CREATED >>
		 * assertEquals(debtStub.get(0), may.getDebts().get(0));
		assertEquals(debtStub.get(0), may.getDebts().get(0));
		assertEquals(debtStub.get(0), may.getDebts().get(0));*/
	}
	
	@Test
	public void testGetTotalExpenses()
	{
		assertEquals(0f, jan.getTotalExpenses());
		assertEquals(28.97f, may.getTotalExpenses());
	}
	
	@Test
	public void testMonthlyBreakdownMenu()
	{		
		// test case 0
		// setup and test the input
		String input = "0\n"; 
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);
		assertEquals(0, jan.monthlyBreakdownMenu(inFile, 0));
		
		// gets output as an array of strings separated by newlines
		String expected = "\n\t  1. Return to Home Page\n" + "\t  2. List next 5 items\n" + "\t  3. Change Month\n";
		expected += "Please enter a selection (1-3) or '0' to exit: ";
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
		
		// test case 1
		// setup and test the input
		input = "1\n"; 
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);
		assertEquals(0, jan.monthlyBreakdownMenu(inFile, 0));
				
		// gets output as an array of strings separated by newlines
		expected = "\n\t  1. Return to Home Page\n" + "\t  2. List next 5 items\n" + "\t  3. Change Month\n";
		expected += "Please enter a selection (1-3) or '0' to exit: ";
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
		
		//<< TO DO FIX IN MONTHLY BREAKDOWN IT SEEMS TO HIT END OF LIST EACH TIME EVEN IF LIST IS LARGER>>
		// test case 2
		// setup and test the input
				input = "2\n1\n"; 
				in = new ByteArrayInputStream(input.getBytes());
				inFile = new Scanner(in);		
						
				// gets output as an array of strings separated by newlines
				expected = "\n\t  1. Return to Home Page\n" + "\t  2. List next 5 items\n" + "\t  3. Change Month\n";
				expected += "Please enter a selection (1-3) or '0' to exit: ";
				expected += "\nExpenses (by item):\n";
				expected += "Name       Amount     Date         Paid      \n";
				for (int i = 0; i < expenseList.size(); i++)
				{
					expenseList.get(i).listExpense();
					expected += outputCaptor.toString();
					outputCaptor.reset();
				}
				expected += "\n<<END OF EXPENSE LIST>>\n";
				expected += "\n\t  1. Return to Home Page\n" + "\t  2. List next 5 items\n" + "\t  3. Change Month\n";
				expected += "Please enter a selection (1-3) or '0' to exit: ";
				
				assertEquals(0, may.monthlyBreakdownMenu(inFile, 0));
				assertEquals(expected, outputCaptor.toString());
				outputCaptor.reset();
		
		
		// expand expense case to be 11 items long
		Expense expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);
		
		// setup and test the input
		input = "2\n1\n"; 
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);		
				
		// gets output as an array of strings separated by newlines
		expected = "\n\t  1. Return to Home Page\n" + "\t  2. List next 5 items\n" + "\t  3. Change Month\n";
		expected += "Please enter a selection (1-3) or '0' to exit: ";
		expected += "\nExpenses (by item):\n";
		expected += "Name       Amount     Date         Paid      \n";
		for (int i = 0; i < expenseList.size(); i++)
		{
			expenseList.get(i).listExpense();
			expected += outputCaptor.toString();
			outputCaptor.reset();
		}
		expected += "\n<<END OF EXPENSE LIST>>\n";
		expected += "\n\t  1. Return to Home Page\n" + "\t  2. List next 5 items\n" + "\t  3. Change Month\n";
		expected += "Please enter a selection (1-3) or '0' to exit: ";
		
		assertEquals(0, may.monthlyBreakdownMenu(inFile, 0));
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
				
		// test case 3
	
	}
	
	@Test
	public void testDisplayMonthlyBreakdown()
	{
		
	}
	
	// << TO DO WHEN ALL CLASSES FINISHED >>
	@Test
	public void testProgressToGoal()
	{
		
	}
	
	//<< TO DO WHEN ALL CLASSES FINISHED>>
	@Test
	public void testProgressToDebt()
	{
		
	}
	
	//<< TO DO WHEN ALL CLASSES FINISHED>>
	@Test
	public void testDisplayUpdates()
	{
		
	}
	
	@AfterEach
	public void reset()
	{
		System.setOut(standardOut);
	}

}
