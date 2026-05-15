import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.time.LocalDate;
import java.util.LinkedList;

// <<DONE WITH TEST CASES>>

class GoalTest {

	// test objects to test all constructors
	Goal test1;
	Goal test2;
	
	// set up expected date objects
	LocalDate expectedDate1;
	LocalDate expectedDate2;
	LocalDate expectedDate3;
	LocalDate expectedDate4;

	
	
	@BeforeEach
	void setUp() {
		// set up the test objects
		test1 = new Goal(true, 2026, 05, 12, 2026, 06, 12, "Art Supplies", "Savings to purchase art supplies", 26.336f);
		test2 = new Goal(false, 2026, 06, 29, 2026, 07, 20, "Next Paycheck", "Amount left until recieve next paycheck", 1000f);
		
		expectedDate1 = LocalDate.of(2026, 05, 12);
		expectedDate2 = LocalDate.of(2026, 06, 12);
		
		expectedDate3 = LocalDate.of(2026, 06, 29);
		expectedDate4 = LocalDate.of(2026, 07, 20);
	}
	
	
	@Test
	void testGetName() {
		assertEquals("Art Supplies", test1.getName());
		assertEquals("Next Paycheck", test2.getName());
	}
	
	
	@Test
	void testGetDescription() {
		assertEquals("Savings to purchase art supplies", test1.getDescription());
		assertEquals("Amount left until recieve next paycheck", test2.getDescription());
	}

	
	@Test
	void testGetAmount() {
		assertEquals(26.336f, test1.getAmount());
		assertEquals(1000f, test2.getAmount());
	}
	
	
	@Test
	void testGetType() {
		assertEquals(true, test1.getType());
		assertEquals(false, test2.getType());
	}
	
	
	@Test
	void testGetProgress() {
		assertEquals(0f, test1.getProgress());
		assertEquals(0f, test2.getProgress());
	}
	
	
	@Test
	void testIsComplete() {
		assertEquals(false, test1.isComplete());
		assertEquals(false, test2.isComplete());
	}
	
	
	@Test
	void testGetStartDate() {
		assertEquals(expectedDate1, test1.getStartDate());
		assertEquals(expectedDate3, test2.getStartDate());
	}
	
	
	@Test
	void testGetEndDate() {
		assertEquals(expectedDate2, test1.getEndDate());
		assertEquals(expectedDate4, test2.getEndDate());
	}
	
	
	@Test
	void testGetExpenseList() {
		assertEquals(true, test1.getExpenseList().isEmpty());
		assertEquals(true, test2.getExpenseList().isEmpty());
	}
	
	
	@Test
	void testSetName() {
		test2.setName("Art Supplies");
		assertEquals("Art Supplies", test2.getName());
	}
	
	
	@Test
	void testSetDescription() {
		test2.setDescription("Savings to purchase art supplies");
		assertEquals("Savings to purchase art supplies", test2.getDescription());
	}
	
	@Test
	void testSetAmount() {
		test2.setAmount(26.336f);
		assertEquals(26.336f, test2.getAmount());
	}
	
	
	@Test
	void testSetType() {
		test2.setType(true);
		assertEquals(true, test2.getType());
	}
	
	
	@Test
	void testSetProgress() {
		test2.setProgress(1000f);
		assertEquals(test2.getAmount(), test2.getProgress());
	}
	
	
	@Test
	void testSetComplete() {
		test2.setComplete(true);
		assertEquals(true, test2.isComplete());
	}
	
	
	@Test
	void testSetEndDate() {
		test2.setEndDate(2026, 06, 29);
		assertEquals(expectedDate3, test2.getEndDate());
	}
	
	
	@Test
	void testLogExpense() {
		Expense testExpense = new Expense("Apple", 1000f, 2026, 05, 12);
		test2.logExpense(testExpense);
		LinkedList<Expense> testList = test2.getExpenseList();
		
		assertEquals(1000f, testList.get(0).getAmount());
		assertEquals(expectedDate1, testList.get(0).getDate());
		assertEquals("Apple", testList.get(0).getName());
		assertEquals(false, testList.get(0).isPaid());
		assertEquals(false, testList.get(0).isIncome());
		assertEquals(false, testList.get(0).isRecurring());
	}
	
}
