import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class saveAsTXTTest {

	class dataStorageTest {
		
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
		void test() {
			// set up a linked list
			LinkedList<Expense> expenseList = new LinkedList<Expense>();

			Expense expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
			expenseList.add(expenseNode);
			expenseNode = new Expense("Hulu", 3.99f, 2026, 04, 29, true);
			expenseList.add(expenseNode);
			expenseNode = new Expense("Disney+", 9.99f, 2026, 04, 28, true);
			expenseList.add(expenseNode);
			
			// call save to file normally
			SaveAsTXT.saveToFile(expenseList, "januaryExpenses");
		}

		
		@AfterEach
		public void reset()
		{
			System.setOut(standardOut);
		}
	}


}
