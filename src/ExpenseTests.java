import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
//<<DONE>>


class ExpenseTests {

	// declare the output stream captor to test printing and the standard out to reset it
	private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
	private final PrintStream standardOut = System.out;
	Expense test1;
	Expense test2;
	LocalDate expectedDate1;
	LocalDate expectedDate2;
	LocalDate expectedDate3;
	
	@BeforeEach
	public void setUp()
	{
		// get java to write to the captor instead of console
		System.setOut(new PrintStream(outputCaptor));
		
		test1 = new Expense("test", 1.5f, 2026, 4, 29);
		test2 = new Expense("test2", 2.5f, 2025, 3, 28, true);
		expectedDate1 = LocalDate.of(2026,4,29);
		expectedDate2 = LocalDate.of(2025,3,28);
		expectedDate3 = LocalDate.of(2025,3,21);
	}
	
	@Test
	public void testGetName()
	{
		assertEquals("test", test1.getName());
		assertEquals("test2", test2.getName());
	}
	
	@Test
	public void testGetDate()
	{
		assertEquals(expectedDate1, test1.getDate());
		assertEquals(expectedDate2, test2.getDate());
	}
	
	@Test 
	public void testGetAmount()
	{
		assertEquals(1.5f, test1.getAmount());
		assertEquals(2.5f, test2.getAmount());
	}
	
	@Test
	public void testIsPaid()
	{
		assertEquals(false, test1.isPaid());
		assertEquals(true, test2.isPaid());
	}
	
	@Test
	public void testSetAmount()
	{
		test1.setAmount(3.14f);
		assertEquals(3.14f, test1.getAmount());
	}

	@Test
	public void testSetDate()
	{
		test1.setDate(2025, 3, 21);
		assertEquals(expectedDate3, test1.getDate());
	}
	
	@Test
	public void testSetName()
	{
		test1.setName("test1");
		assertEquals("test1", test1.getName());
	}
	
	@Test
	public void testSetPaid()
	{
		assertEquals(false, test1.isPaid());
		test1.setPaid(true);
		assertEquals(true, test1.isPaid());
	}
	
	@Test
	public void testlistExpense()
	{
		// test printing the expense
		test1.listExpense();
		assertEquals("test       1.50       2026-04-29   Unpaid", outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
		
		// test printing the expense if paid is false
		test1.setPaid(true);
		test1.listExpense();
		assertEquals("test       1.50       2026-04-29   Paid", outputCaptor.toString().trim());
		outputCaptor.reset();
	}
	
	@AfterEach
	public void reset()
	{
		System.setOut(standardOut);
	}
}
