import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

// << DONE WITH TEST CASES >> 

class SaveDebtTXTTest {

	// redirect output stream for testing values
	private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
	private final PrintStream standardOut = System.out;
	
	// set up list to write
	LinkedList<Debt> writeList = new LinkedList<Debt>();
	LinkedList<Debt> readList = new LinkedList<Debt>();

	
	@BeforeEach
	public void setUp()
	{
		// get java to write to the captor instead of console
		System.setOut(new PrintStream(outputCaptor));	
		
		// establish write list
		Debt debtNode = new Debt("Car", 7000f, 2028, 06, 29, 0.05f, true, 7000f, true);
		writeList.add(debtNode);
		debtNode = new Debt("House", 14000f, 2026, 06, 29, 0.05f, true, 0f, false);
		writeList.add(debtNode);
		debtNode = new Debt("College", 10000f, 2027, 06, 29, 0.05f, false, 7000f, true);
		writeList.add(debtNode);
	}
	
	
	@Test
	void testReadToFile() {
		// call save to file on new file
		SaveDebtTXT.saveToFile(writeList, "testDebt");
		
		// read new file
		readList = SaveDebtTXT.loadFromFile("testDebt");
		
		writeList.get(0).listDebt();
		String expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(0).listDebt();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		writeList.get(1).listDebt();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(1).listDebt();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
		
		writeList.get(2).listDebt();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(2).listDebt();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		
		// test if save to pre-existing file
		Debt debtNode = new Debt("Medical", 5000f, 2027, 06, 29, 0.05f, false, 2000f, true);
		writeList.add(debtNode);
					
		SaveDebtTXT.saveToFile(writeList, "testDebt");
					
		// read from same file with modifications
		readList = SaveDebtTXT.loadFromFile("testDebt");
					
		writeList.get(0).listDebt();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(0).listDebt();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		writeList.get(1).listDebt();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(1).listDebt();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
					
		writeList.get(2).listDebt();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(2).listDebt();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
		
		writeList.get(3).listDebt();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(3).listDebt();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test		
	}

	
	@Test
	void testLoadFromFile() {
		// try to read from non-existant test file
		readList = SaveDebtTXT.loadFromFile("testDebt");

		assertEquals(true, readList.isEmpty());
		
		// try to read from empty file
		File file = new File("testDebt");
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.exit(-1);
		}
		
		readList = SaveDebtTXT.loadFromFile("testDebt");
		assertEquals(true, readList.isEmpty());

		
		// create test file
		SaveDebtTXT.saveToFile(writeList, "testDebt");
		
		// read pre-existing file
		readList = SaveDebtTXT.loadFromFile("testDebt");
		
		writeList.get(0).listDebt();
		String expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(0).listDebt();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		writeList.get(1).listDebt();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(1).listDebt();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
		
		writeList.get(2).listDebt();
		expected = outputCaptor.toString().trim();
		outputCaptor.reset();			// make empty so doesn't interfere with next test

		readList.get(2).listDebt();
		assertEquals(expected, outputCaptor.toString().trim());
		outputCaptor.reset();			// make empty so doesn't interfere with next test
	}
	
	
	@AfterEach
	public void reset()
	{
		System.setOut(standardOut);
		File file = new File("testDebt");
		file.delete();
	}

}
