import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

// <<DONE WITH TESTS>>

class SaveAsTXTTest {

	// redirect output stream for testing values
	private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
	private final PrintStream standardOut = System.out;
	
	// set up list to write
	LinkedList<Expense> writeList = new LinkedList<Expense>();
	LinkedList<Expense> readList = new LinkedList<Expense>();

	
	@BeforeEach
	public void setUp()
	{
		// get java to write to the captor instead of console
		System.setOut(new PrintStream(outputCaptor));	
		
		// establish write list
		Expense expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true, true, true);
		writeList.add(expenseNode);
		expenseNode = new Expense("Hulu", 3.99f, 2026, 04, 29, true, true, true);
		writeList.add(expenseNode);
		expenseNode = new Expense("Disney+", 9.99f, 2026, 04, 28, false, false, false);
		writeList.add(expenseNode);
	}
	
	
	@Test
	void testReadToFile() {
		// call save to file on new file
		SaveAsTXT.saveToFile(writeList, "testExpense");
		
		// read new file
		readList = SaveAsTXT.loadFromFile("testExpense");
		
		writeList.get(0).listExpense();
		String expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(0).listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		writeList.get(1).listExpense();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(1).listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
		
		writeList.get(2).listExpense();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(2).listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		
		// test if save to pre-existing file
		Expense expenseNode = new Expense ("HBO Max", 16.99f, 2026, 04, 27, true);
		writeList.add(expenseNode);
					
		SaveAsTXT.saveToFile(writeList, "testExpense");
					
		// read from same file with modifications
		readList = SaveAsTXT.loadFromFile("testExpense");
					
		writeList.get(0).listExpense();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(0).listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		writeList.get(1).listExpense();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(1).listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
					
		writeList.get(2).listExpense();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(2).listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
		
		writeList.get(3).listExpense();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(3).listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test		
	}

	
	@Test
	void testLoadFromFile() {
		// try to read from non-existant test file
		readList = SaveAsTXT.loadFromFile("testExpense");

		assertEquals(true, readList.isEmpty());
		
		// try to read from empty file
		File file = new File("testExpense");
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.exit(-1);
		}
		
		readList = SaveAsTXT.loadFromFile("testExpense");
		assertEquals(true, readList.isEmpty());
		
		// create test file
		SaveAsTXT.saveToFile(writeList, "testExpense");
		
		// read pre-existing file
		readList = SaveAsTXT.loadFromFile("testExpense");
		
		writeList.get(0).listExpense();
		String expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(0).listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		writeList.get(1).listExpense();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(1).listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
		
		writeList.get(2).listExpense();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(2).listExpense();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
	}
	
	
	@AfterEach
	public void reset()
	{
		System.setOut(standardOut);
		File file = new File("testExpense");
		file.delete();
	}
}
