import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.*;
import java.time.LocalDate;

// <<TEST CASES FINISHED>>

class ExpenseTest {

	// declare the output stream captor to test printing and the standard out to reset it
	private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
	private final PrintStream standardOut = System.out;
	
	// test objects to test all constructors
	Expense test1;
	Expense test2;
	Expense test3;
	
	// set up expected date objects
	LocalDate expectedDate1;
	LocalDate expectedDate2;
	LocalDate expectedDate3;

	
	@BeforeEach
	void setUp() {
		// output to captor instead of console
		System.setOut(new PrintStream(outputCaptor));
		
		// set up the test objects
		test1 = new Expense("Apple", 1000f, 2026, 05, 12); // false, false, false
		test2 = new Expense("Banana", 0f, 2026, 04, 1, true); // false, false
		test3 = new Expense("Croissant", -10f, 2026, 06, 29, true, true, true);
		
		expectedDate1 = LocalDate.of(2026, 05, 12);
		expectedDate2 = LocalDate.of(2026, 04, 1);
		expectedDate3 = LocalDate.of(2026, 06, 29);
	}
	
	
	@Test
	void testGetAmount() {
		assertEquals(1000f, test1.getAmount());
		assertEquals(0f, test2.getAmount());
		assertEquals(-10f, test3.getAmount());
	}
	
	
	@Test
	void testGetDate() {
		assertEquals(expectedDate1, test1.getDate());
		assertEquals(expectedDate2, test2.getDate());
		assertEquals(expectedDate3, test3.getDate());
	}
	
	
	@Test
	void testGetName() {
		assertEquals("Apple", test1.getName());
		assertEquals("Banana", test2.getName());
		assertEquals("Croissant", test3.getName());
	}
	
	
	@Test
	void testIsPaid() {
		assertEquals(false, test1.isPaid());
		assertEquals(true, test2.isPaid());
		assertEquals(true, test3.isPaid());
	}
	
	
	@Test
	void testIsIncome() {
		assertEquals(false, test1.isIncome());
		assertEquals(false, test2.isIncome());
		assertEquals(true, test3.isIncome());
	}
	
	
	@Test
	void testIsRecurring() {
		assertEquals(false, test1.isRecurring());
		assertEquals(false, test2.isRecurring());
		assertEquals(true, test3.isRecurring());
	}
	
	
	@Test
	void testSetAmount() {
		test2.setAmount(1000f);
		assertEquals(test1.getAmount(), test2.getAmount());
	}
	
	
	@Test
	void testSetDate() {
		test2.setDate(2026, 05, 12);
		assertEquals(test1.getDate(), test2.getDate());
	}

	
	@Test
	void testSetName() {
		test2.setName("Apple");
		assertEquals(test1.getName(), test2.getName());
	}
	
	
	@Test
	void testSetPaid() {
		test2.setPaid(false);
		assertEquals(false, test2.isPaid());
	}	
	
	
	@Test
	void testSetIncome() {
		test2.setIncome(true);
		assertEquals(true, test2.isIncome());
	}	
	
	
	@Test
	void testSetRecurring() {
		test2.setRecurring(true);
		assertEquals(true, test2.isRecurring());
	}
	
	
	@Test
	void testListExpense() {
		// testing all true
		String expected = "Croissant  -10.00     2026-06-29   Paid       [INCOME] [RECURRING]";
		test3.listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();

		
		// testing all false
		expected = "Apple      1000.00    2026-05-12   Unpaid";
		test1.listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();

	}

	
	@AfterEach
	void reset() {
		System.setOut(standardOut);
	}
	
}
