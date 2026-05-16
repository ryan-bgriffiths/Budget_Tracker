import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

// << TEST CASES DONE>>

class MonthlyBreakdownTests {

	// declare the input/output stream captor to test printing and the standard out to reset it
	private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
	private final PrintStream standardOut = System.out;
	ByteArrayInputStream in;
	Scanner inFile;
	
	// declared as global so can be used throughout testing
	MonthlyBreakdown jan;
	MonthlyBreakdown may;
	MonthlyBreakdown jun;
	LinkedList<Debt> debtList;
	LinkedList<Goal> goalList;
	LinkedList<Debt> debtStub = new LinkedList<Debt>();
	LinkedList<Goal> goalStub = new LinkedList<Goal>();
	LinkedList<Expense> expenseList;
	
	@BeforeEach
	public void setUp()
	{
		// get java to write to the captor instead of console
		System.setOut(new PrintStream(outputCaptor));
		
		// test constructors
		expenseList = new LinkedList<Expense>();

		// set up expense list
		Expense expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Hulu", 3.99f, 2026, 04, 29, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Disney+", 9.99f, 2026, 04, 28, true);
		expenseList.add(expenseNode);
		
		// set up goal list
		goalList = new LinkedList<Goal>();
		Goal goalNode = new Goal(true, 2026, 05, 14, 2026, 06, 29, "Air travel", "fly to hawaii", 2000f);
		goalList.add(goalNode);
		goalNode = new Goal(true, 2026, 05, 14, 2026, 06, 29, "Billiard", "get billiard table", 200f);
		goalList.add(goalNode);
		goalNode = new Goal (true, 2026, 05, 14, 2026, 06, 29, "Crossfit", "get crossfit class", 2000f);
		goalList.add(goalNode);
		
		// set up debt list
		debtList = new LinkedList<Debt>();
		Debt debtNode = new Debt("Car", 7000f, 2028, 06, 29, 0.05f, true, 7000f, true);
		debtList.add(debtNode);
		debtNode = new Debt("House", 14000f, 2026, 06, 29, 0.05f, true, 0f, false);
		debtList.add(debtNode);
		debtNode = new Debt("College", 10000f, 2027, 06, 29, 0.05f, false, 7000f, true);
		debtList.add(debtNode);
		
		jan = new MonthlyBreakdown("January", 2026);
		may = new MonthlyBreakdown("May", 2026, expenseList, goalStub, debtStub);
		jun = new MonthlyBreakdown("June", 2026, expenseList, goalList, debtList);
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
		assertEquals(goalList.get(0), jun.getGoals().get(0));
		assertEquals(goalList.get(0), jun.getGoals().get(0));
		assertEquals(goalList.get(0), jun.getGoals().get(0));
	}
	
	@Test
	public void testGetDebts()
	{
		assertEquals(0, jan.getDebts().size());
		assertEquals(debtList.get(0), jun.getDebts().get(0));
		assertEquals(debtList.get(0), jun.getDebts().get(0));
		assertEquals(debtList.get(0), jun.getDebts().get(0));
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
		for (int i = 0; i < 10; i++)
		{
			expenseList.get(i).listExpense();
			expected += outputCaptor.toString();
			outputCaptor.reset();
		}
		expected += "\n\t  1. Return to Home Page\n" + "\t  2. List next 5 items\n" + "\t  3. Change Month\n";
		expected += "Please enter a selection (1-3) or '0' to exit: ";
		
		assertEquals(0, may.monthlyBreakdownMenu(inFile, 0));
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
				
		// test case 3
		input = "3\n12\n"; 
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);
		
		expected = "\n\t  1. Return to Home Page\n" + "\t  2. List next 5 items\n" + "\t  3. Change Month\n";
		expected += "Please enter a selection (1-3) or '0' to exit: ";
		expected += "\nEnter the number of the month you would like to view (1=Jan, 2=Feb, ect)";
		expected += "\nPlease enter a selection (1-12) or '0' to exit: ";
		
		assertEquals(12, may.monthlyBreakdownMenu(inFile, 0));
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
	}
	
	@Test
	public void testDisplayMonthlyBreakdown()
	{
		
		System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║ ^^^^ MONTHLY BREAKDOWN ^^^^  ║");
        System.out.println("\t╚══════════════════════════════╝\n");
		
		// if no expenses & no goals
		String expected = outputCaptor.toString();
		expected += "Month: January 2026\n";
		expected += "[!] Alert: No Expenses Have Been Entered.\n";
		expected += "Total Expenses: $0.00\n";
		expected += "\n\t  1. Return to Home Page\n" + "\t  2. List next 5 items\n" + "\t  3. Change Month\n";
		expected += "Please enter a selection (1-3) or '0' to exit: ";
		outputCaptor.reset();
		
		String input = "0\n"; 
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);
		assertEquals(0, jan.displayMonthlyBreakdown(inFile));
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
		
		// if less than 5 expenses & no goals
		System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║ ^^^^ MONTHLY BREAKDOWN ^^^^  ║");
        System.out.println("\t╚══════════════════════════════╝\n");
        
		expected = outputCaptor.toString();
		expected += "Month: May 2026\n";
		expected += "Total Expenses: $28.97\n\n";
		expected += "Expenses (by item):\n";
		expected += "Name       Amount     Date         Paid      \n";
		outputCaptor.reset();
		
		for (int i = 0; i < expenseList.size(); i++)
		{
			expenseList.get(i).listExpense();
			expected += outputCaptor.toString();
			outputCaptor.reset();
		}
		
		expected += "\n\n<<END OF EXPENSE LIST>>\n\n";
		
		expected += "Goals:\n" + "[!] Alert - No goals added.\n\n";
		expected += "Debts:\n" + "[!] Alert - No debts added.\n\n";
		
		expected += "\n\t  1. Return to Home Page\n" + "\t  2. List next 5 items\n" + "\t  3. Change Month\n";
		expected += "Please enter a selection (1-3) or '0' to exit: ";
		
		input = "0\n"; 
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);
		
		assertEquals(0, may.displayMonthlyBreakdown(inFile));
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();

		
		// if more than 5 expenses & list with goals/debts
		// expand expense case to be 11 items long
		Expense expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);
		expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
		expenseList.add(expenseNode);

		
		System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║ ^^^^ MONTHLY BREAKDOWN ^^^^  ║");
        System.out.println("\t╚══════════════════════════════╝\n");
        
		expected = outputCaptor.toString();
		expected += "Month: June 2026\n";
		expected += "Total Expenses: $73.94\n\n";
		expected += "Expenses (by item):\n";
		expected += "Name       Amount     Date         Paid      \n";
		outputCaptor.reset();
		
		for (int i = 0; i < 5; i++)
		{
			expenseList.get(i).listExpense();
			expected += outputCaptor.toString();
			outputCaptor.reset();
		}
		
		expected += "Goals:\n";
		
		expected += "1.)\tAir travel | Progress: 0.00% Complete\n";
		expected += "2.)\tBilliard | Progress: 0.00% Complete\n";
		expected += "3.)\tCrossfit | Progress: 0.00% Complete\n";
		
		expected += "Debts:\n";
		
		expected += "\tTotal Debt -- $14000.00\n";

		expected += "1.)\tCar | Principal: $7000.0 | Progress: 0.00% Paid Off\n";
		expected += "2.)\tHouse | Principal: $14000.0 | Progress: 100% Paid Off\n";
		expected += "3.)\tCollege | Principal: $10000.0 | Progress: 30.00% Paid Off\n";

		
		
		expected += "\n\t  1. Return to Home Page\n" + "\t  2. List next 5 items\n" + "\t  3. Change Month\n";
		expected += "Please enter a selection (1-3) or '0' to exit: ";
		
		input = "0\n"; 
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);
		
		assertEquals(0, jun.displayMonthlyBreakdown(inFile));
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();

	}
	
	@Test
	public void testProgressToGoal()
	{
		// if complete is false
		String expected = "\tAir travel | Progress: 0.00% Complete\n";
		
		jun.displayProgressToGoal(goalList.get(0));
		
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
		
		// if progress made
		expected = "\tAir travel | Progress: 50.00% Complete\n";

		goalList.get(0).setProgress(1000f);
		
		jun.displayProgressToGoal(goalList.get(0));
		
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
		
		// if complete
				expected = "\tAir travel | Progress: Complete\n";

				goalList.get(0).setComplete(true);
				
				jun.displayProgressToGoal(goalList.get(0));
				
				assertEquals(expected, outputCaptor.toString());
				outputCaptor.reset();
	}
	
	@Test
	public void testProgressToDebt()
	{
		// if complete is false
				String expected = "\tCar | Principal: $7000.0 | Progress: 0.00% Paid Off\n";
				Debt debtNode = new Debt("Car", 7000f, 2028, 06, 29, 0.05f, true, 7000f, true);

				jun.displayProgressOfDebt(debtList.get(0));
				
				assertEquals(expected, outputCaptor.toString());
				outputCaptor.reset();
				
				// if progress made
				expected = "\tCar | Principal: $7000.0 | Progress: 50.00% Paid Off\n";

				debtList.get(0).setRemainingBalance(3500f);
				
				jun.displayProgressOfDebt(debtList.get(0));
				
				assertEquals(expected, outputCaptor.toString());
				outputCaptor.reset();
				
				// if complete
				expected = "\tCar | Principal: $7000.0 | Progress: 100% Paid Off\n";

						debtList.get(0).setRemainingBalance(0f);
						
						jun.displayProgressOfDebt(debtList.get(0));
						
						assertEquals(expected, outputCaptor.toString());
						outputCaptor.reset();
		
	}
	
	@Test
	public void testDisplayUpdates()
	{
		// if no goals or debts
		String expected = "Goals:\n" + "[!] Alert - No goals added.\n\n";
		expected += "Debts:\n" + "[!] Alert - No debts added.\n\n";
		
		jan.displayUpdates();
		
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
		
		
		// if goals and debts
		expected = "Goals:\n";
		
		expected += "1.)\tAir travel | Progress: 0.00% Complete\n";
		expected += "2.)\tBilliard | Progress: 0.00% Complete\n";
		expected += "3.)\tCrossfit | Progress: 0.00% Complete\n";
		
		expected += "Debts:\n";
		
		expected += "\tTotal Debt -- $14000.00\n";

		expected += "1.)\tCar | Principal: $7000.0 | Progress: 0.00% Paid Off\n";
		expected += "2.)\tHouse | Principal: $14000.0 | Progress: 100% Paid Off\n";
		expected += "3.)\tCollege | Principal: $10000.0 | Progress: 30.00% Paid Off\n";

		
		jun.displayUpdates();
		
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
	}
	
	
	@AfterEach
	public void reset()
	{
		System.setOut(standardOut);
	}

}
