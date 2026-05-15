import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;

// <<ALL TESTING DONE>>

class DebtTest {

	// declare the output stream captor to test printing and the standard out to reset it
		private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
		private final PrintStream standardOut = System.out;
		
		// test objects to test all constructors
		Debt test1;
		Debt test2;
		
		// set up expected date objects
		LocalDate expectedDate1;
		LocalDate expectedDate2;
	
		// test list of debts
		LinkedList<Debt> testlist = new LinkedList<Debt>();

		
		@BeforeEach
		void setUp() {
			// output to captor instead of console
			System.setOut(new PrintStream(outputCaptor));
			
			// set up the test objects
			test1 = new Debt("Automobile", 7000f, 2028, 7, 4, 0.05f, true);
			test2 = new Debt("Bursur", 240.50f, 2026, 8, 20, 0.20f, false, 0f, false);
			
			expectedDate1 = LocalDate.of(2028, 7, 04);
			expectedDate2 = LocalDate.of(2026, 8, 20);
			
		}
		
		
		@Test
		void testGetPrincipleAmount() {
			assertEquals(7000f, test1.getPrincipleAmount());
			assertEquals(240.50f, test2.getPrincipleAmount());
		}
		
		
		@Test
		void testGetDueDate() {
			assertEquals(expectedDate1, test1.getDueDate());
			assertEquals(expectedDate2, test2.getDueDate());
		}
		
		
		@Test
		void testGetInterestRate() {
			assertEquals(0.05f, test1.getInterestRate());
			assertEquals(0.20f, test2.getInterestRate());
		}
		
		
		@Test
		void testGetCompoundOrSimple() {
			assertEquals(true, test1.getCompoundOrSimple());
			assertEquals(false, test2.getCompoundOrSimple());
		}
		
		
		@Test
		void testGetStatus() {
			assertEquals(true, test1.getStatus());
			assertEquals(false, test2.getStatus());
		}
		
		
		@Test
		void testGetProgress() {
			assertEquals(0, test1.getProgress());
			assertEquals(100, test2.getProgress());
			
			Debt test3 = new Debt("Automobile", 0f, 2028, 7, 4, 0.05f, true);
			assertEquals(100, test3.getProgress());
			
			test3 = new Debt("Automobile", 10f, 2028, 7, 4, 0.05f, true, 20f, true);
			assertEquals(0, test3.getProgress());
			
			test3 = new Debt("Automobile", 10f, 2028, 7, 4, 0.05f, true, -20f, true);
			assertEquals(100, test3.getProgress());

			test3 = new Debt("Automobile", 10f, 2028, 7, 4, 0.05f, true, 5f, true);
			assertEquals(50, test3.getProgress());
		}
		
		
		@Test
		void testGetRemainingBalance() {
			assertEquals(test1.getPrincipleAmount(), test1.getRemainingBalance());
			assertEquals(0, test2.getRemainingBalance());
		}
		
		
		@Test
		void testGetName() {
			assertEquals("Automobile", test1.getName());
			assertEquals("Bursur", test2.getName());
		}
		
		
		@Test
		void testSetName() {
			test1.setName("Cruiser");
			assertEquals("Cruiser", test1.getName());
		}
		
		
		@Test
		void testSetPrinciple() {
			test1.setPrinciple(12000f);
			assertEquals(12000f, test1.getPrincipleAmount());
		}
		
		
		@Test
		void testSetDueDate() {
			test1.setDueDate(2026, 8, 20);
			assertEquals(expectedDate2, test1.getDueDate());
		}
		
		
		@Test
		void testSetInterestRate() {
			test1.setInterestRate(0.50f);
			assertEquals(0.50f, test1.getInterestRate());
		}
		
		
		@Test
		void testSetCompoundOrSimple() {
			test1.setCompoundOrSimple(false);
			assertEquals(false, test1.getCompoundOrSimple());
		}
		
		
		@Test
		void testSetRemainingBalance() {
			test1.setRemainingBalance(-1f);
			assertEquals(0f, test1.getRemainingBalance());
			assertEquals(false, test1.getStatus());

			test1.setRemainingBalance(10f);
			assertEquals(10f, test1.getRemainingBalance());
			assertEquals(true, test1.getStatus());
		}
		
		
		@Test
		void testSetStatus() {
			test1.setStatus(false);
			assertEquals(false, test1.getStatus());
		}
		
		
		@Test
		void testAddDebt() {
			Debt.addDebt(testlist, test2);
			assertEquals(testlist.get(0), test2);
		}
		
		
		@Test 
		void testModifyDebt() {
			Debt.addDebt(testlist, test2);
			boolean status = Debt.modifyDebt(testlist, "Bursur", "Damages", 1000f, 2026, 8, 20, 0.25f, false, 200);
			assertEquals(true, status);
			assertEquals(1000f, test2.getPrincipleAmount());
			assertEquals(expectedDate2, test2.getDueDate());
			assertEquals(0.25f, test2.getInterestRate());
			assertEquals(false, test2.getCompoundOrSimple());
			assertEquals(200, test2.getRemainingBalance());
			
			status = Debt.modifyDebt(testlist, "Bursur", "Damages", 1000f, 2026, 8, 20, 0.25f, false, 200);
			assertEquals(false, status);			
		}
		
		
		@Test
		void testDeleteDebt() {
			Debt.addDebt(testlist, test1);
			Debt.addDebt(testlist, test2);
			boolean status = Debt.deleteDebt(testlist,  "Bursur");
			assertEquals(true, status);
			assertEquals(1, testlist.size());

			status = Debt.deleteDebt(testlist,  "Bursur");
			assertEquals(false, status);
		}
		
		
		@Test
		void testFindDebt() {
			Debt.addDebt(testlist, test1);
			Debt.addDebt(testlist, test2);
			assertEquals(test2, Debt.findDebt(testlist, "Bursur"));
			
			assertEquals(null, Debt.findDebt(testlist, "Bob"));
		}
		
		
		@Test
		void testListDebt() {
			// testing all true
			String expected = "Automobile      7000.00    2028-07-04   0.05       7000.00    0.00       Active";
			test1.listDebt();
			assertEquals(expected, outputCaptor.toString().trim());
			outputCaptor.reset();

			
			// testing all false
			expected = "Bursur          240.50     2026-08-20   0.20       0.00       100.00     Inactive";
			test2.listDebt();
			assertEquals(expected, outputCaptor.toString().trim());
			outputCaptor.reset();

		}
		
		
		@AfterEach
		void reset() {
			System.setOut(standardOut);
		}
}
