import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ExpenseTests {

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
	void testExpenseClass() {
		// test both constructors
		Expense test1 = new Expense("test", 1.5f, 2026, 4, 29);
		Expense test2 = new Expense("test2", 2.5f, 2025, 3, 28, true);
		LocalDate expectedDate1 = LocalDate.of(2026,4,29);
		LocalDate expectedDate2 = LocalDate.of(2025,3,28);
		LocalDate expectedDate3 = LocalDate.of(2025,3,21);
		
		// test get functions
		assertEquals(1.5f, test1.getAmount());
		assertEquals(expectedDate1, test1.getDate());
		assertEquals("test", test1.getName());
		assertEquals(false, test1.isPaid());
		
		assertEquals(2.5f, test2.getAmount());
		assertEquals(expectedDate2, test2.getDate());
		assertEquals("test2", test2.getName());
		assertEquals(true, test2.isPaid());
		
		// test set functions
		test1.setAmount(3.14f);
		test1.setDate(2025, 3, 21);
		test1.setName("test1");
		test1.setPaid(true);
		
		assertEquals(3.14f, test1.getAmount());
		assertEquals(expectedDate3, test1.getDate());
		assertEquals("test1", test1.getName());
		assertEquals(true, test1.isPaid());
		
		
		// test printing the expense
		test1.listExpense();
		assertEquals("test1      3.14       2025-03-21   Paid", outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
		
		// test printing the expense if paid is false
		test1.setPaid(false);
		test1.listExpense();
		assertEquals("test1      3.14       2025-03-21   Unpaid", outputCaptor.toString().trim());
		outputCaptor.reset();

		
	}

	@AfterEach
	public void reset()
	{
		System.setOut(standardOut);
	}
}
