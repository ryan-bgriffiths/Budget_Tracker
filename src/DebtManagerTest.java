import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

class DebtManagerTest {
	
	// declare the input/output stream captor to test printing and the standard out to reset it
	private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
	private final PrintStream standardOut = System.out;
	ByteArrayInputStream in;
	Scanner inFile;
	
	// set up test objects
	LinkedList<Debt> debtList;
	LinkedList<Debt> debtStub = new LinkedList<Debt>();
	
	@BeforeEach
	public void setUp()
	{
		// get java to write to the captor instead of console
		System.setOut(new PrintStream(outputCaptor));
		
		// set up debt list
		debtList = new LinkedList<Debt>();
		Debt debtNode = new Debt("Car", 7000f, 2028, 06, 29, 0.05f, true, 7000f, true);
		debtList.add(debtNode);
		debtNode = new Debt("House", 14000f, 2026, 06, 29, 0.05f, true, 0f, false);
		debtList.add(debtNode);
		debtNode = new Debt("College", 10000f, 2027, 06, 29, 0.05f, false, 7000f, true);
		debtList.add(debtNode);
	}


	@Test
	void testDisplayallDebts() {
		// if empty
		String expected = "\n===== ALL DEBT ENTRIES =====\n" + "No debt entries found.\n";
		
		DebtManager.displayAllDebts(debtStub);
		
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();

		// if not empty
		expected = "\n===== ALL DEBT ENTRIES =====\n";
		expected += "\nDebt #1\n" + "Name: Car\n" + "Total Amount: $7,000.00\n" + "Status: Active\n";
		expected += "\nDebt #2\n" + "Name: House\n" + "Total Amount: $0.00\n" + "Status: Inactive\n";
		expected += "\nDebt #3\n" + "Name: College\n" + "Total Amount: $7,000.00\n" + "Status: Active\n";

		DebtManager.displayAllDebts(debtList);
		
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();

	}
	
	
	@Test
	void testViewDebtDetails() {
		// if empty
		System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║  ****  DEBT DETAILS  ****    ║");
        System.out.println("\t╚══════════════════════════════╝\n");
        
		String expected = outputCaptor.toString();
		expected += "\n===== ALL DEBT ENTRIES =====\n" + "No debt entries found.\n";
		outputCaptor.reset();
		
		DebtManager.viewDebtDetails(debtStub, inFile);
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
		
		// try to view valid debt
		System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║  ****  DEBT DETAILS  ****    ║");
        System.out.println("\t╚══════════════════════════════╝\n");
        
		expected = outputCaptor.toString();
		expected += "\n===== ALL DEBT ENTRIES =====\n";
		expected += "\nDebt #1\n" + "Name: Car\n" + "Total Amount: $7,000.00\n" + "Status: Active\n";
		expected += "\nDebt #2\n" + "Name: House\n" + "Total Amount: $0.00\n" + "Status: Inactive\n";
		expected += "\nDebt #3\n" + "Name: College\n" + "Total Amount: $7,000.00\n" + "Status: Active\n";
		expected += "Enter debt number to view details: ";
		expected += "\n===== DEBT CASE DETAILS =====\n" + "Name: Car\n" + "Principal Amount: $7,000.00\n";
		expected += "Remaining Balance: $7,000.00\n" + "Interest Rate: 0.05%\n" + "Interest Type: Compound\n";
		expected += "Next Payment Due Date: 2028-06-29\n" + "Status: Active\n";

		String input = "1\n";
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);
		
		outputCaptor.reset();
		
		DebtManager.viewDebtDetails(debtList, inFile);
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
		
		debtList.get(0).setCompoundOrSimple(false);
		debtList.get(0).setStatus(false);
		
		System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║  ****  DEBT DETAILS  ****    ║");
        System.out.println("\t╚══════════════════════════════╝\n");
        
		expected = outputCaptor.toString();
		expected += "\n===== ALL DEBT ENTRIES =====\n";
		expected += "\nDebt #1\n" + "Name: Car\n" + "Total Amount: $7,000.00\n" + "Status: Inactive\n";
		expected += "\nDebt #2\n" + "Name: House\n" + "Total Amount: $0.00\n" + "Status: Inactive\n";
		expected += "\nDebt #3\n" + "Name: College\n" + "Total Amount: $7,000.00\n" + "Status: Active\n";
		expected += "Enter debt number to view details: ";
		expected += "\n===== DEBT CASE DETAILS =====\n" + "Name: Car\n" + "Principal Amount: $7,000.00\n";
		expected += "Remaining Balance: $7,000.00\n" + "Interest Rate: 0.05%\n" + "Interest Type: Simple\n";
		expected += "Next Payment Due Date: 2028-06-29\n" + "Status: Inactive\n";

		input = "1\n";
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);
		
		outputCaptor.reset();
		
		DebtManager.viewDebtDetails(debtList, inFile);
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();

		
		// try to view invalid debt
		
		System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║  ****  DEBT DETAILS  ****    ║");
        System.out.println("\t╚══════════════════════════════╝\n");
        
		expected = outputCaptor.toString();
		expected += "\n===== ALL DEBT ENTRIES =====\n";
		expected += "\nDebt #1\n" + "Name: Car\n" + "Total Amount: $7,000.00\n" + "Status: Inactive\n";
		expected += "\nDebt #2\n" + "Name: House\n" + "Total Amount: $0.00\n" + "Status: Inactive\n";
		expected += "\nDebt #3\n" + "Name: College\n" + "Total Amount: $7,000.00\n" + "Status: Active\n";
		expected += "Enter debt number to view details: ";
		expected += "Invalid debt number.\n";

		input = "8\n";
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);
		
		outputCaptor.reset();
		
		DebtManager.viewDebtDetails(debtList, inFile);
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();

		
		System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║  ****  DEBT DETAILS  ****    ║");
        System.out.println("\t╚══════════════════════════════╝\n");
        
		expected = outputCaptor.toString();
		expected += "\n===== ALL DEBT ENTRIES =====\n";
		expected += "\nDebt #1\n" + "Name: Car\n" + "Total Amount: $7,000.00\n" + "Status: Inactive\n";
		expected += "\nDebt #2\n" + "Name: House\n" + "Total Amount: $0.00\n" + "Status: Inactive\n";
		expected += "\nDebt #3\n" + "Name: College\n" + "Total Amount: $7,000.00\n" + "Status: Active\n";
		expected += "Enter debt number to view details: ";
		expected += "Invalid debt number.\n";

		input = "-1\n";
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);
		
		outputCaptor.reset();
		
		DebtManager.viewDebtDetails(debtList, inFile);
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
		
	}
	
	
	@Test
	public void testViewPastDebt() {
		// test with past debts
		System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║    ****  PAST DEBT  ****     ║");
        System.out.println("\t╚══════════════════════════════╝\n");
        
		String expected = outputCaptor.toString();
		outputCaptor.reset();
		expected += "\nDebt #2\n" + "Name: House\n" + "Total Amount: $0.00\n" + "Status: Inactive\n";
				
		DebtManager.viewPastDebt(debtList);
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();
		
		// test with no debts
		System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║    ****  PAST DEBT  ****     ║");
        System.out.println("\t╚══════════════════════════════╝\n");
        
		expected = outputCaptor.toString();
		outputCaptor.reset();
		expected += "No past debt entries found.\n";
		
		DebtManager.viewPastDebt(debtStub);
		assertEquals(expected, outputCaptor.toString());
		outputCaptor.reset();

	}
	
	
	@Test
	public void testModifyDebt() {
        
		// if have debts and valid input
		System.out.printf("\n%s\n", "-".repeat(50));

        System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║   ****  MODIFY DEBT  ****    ║");
        System.out.println("\t╚══════════════════════════════╝\n");
        
		String expected = outputCaptor.toString();
		outputCaptor.reset();
		
		expected += "\n===== ALL DEBT ENTRIES =====\n";
		expected += "\nDebt #1\n" + "Name: Car\n" + "Total Amount: $7,000.00\n" + "Status: Active\n";
		expected += "\nDebt #2\n" + "Name: House\n" + "Total Amount: $0.00\n" + "Status: Inactive\n";
		expected += "\nDebt #3\n" + "Name: College\n" + "Total Amount: $7,000.00\n" + "Status: Active\n";
		expected += "Enter debt number to modify: ";
		
		String input = "1\n";
		in = new ByteArrayInputStream(input.getBytes());
		inFile = new Scanner(in);
	}
	
	
	@AfterEach
	public void reset()
	{
		System.setOut(standardOut);
	}

}
