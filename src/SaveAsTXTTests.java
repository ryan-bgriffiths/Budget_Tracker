import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.nio.file.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SaveAsTXTTests {
	
	
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
			// delete the file if it exists
			File file = new File("januaryExpenses");
			
			file.delete();
			
			// set up a linked list
			LinkedList<Expense> expenseList = new LinkedList<Expense>();
			LinkedList<Expense> readList = new LinkedList<Expense>();


			Expense expenseNode = new Expense("Netlfix", 14.99f, 2026, 04, 30, true);
			expenseList.add(expenseNode);
			expenseNode = new Expense("Hulu", 3.99f, 2026, 04, 29, true);
			expenseList.add(expenseNode);
			expenseNode = new Expense("Disney+", 9.99f, 2026, 04, 28);
			expenseList.add(expenseNode);
			
			// call save to file on new file
			SaveAsTXT.saveToFile(expenseList, "januaryExpenses");
			
			// read new file
			readList = SaveAsTXT.loadFromFile("januaryExpenses");
			
			expenseList.get(0).listExpense();
			String expected = outputCaptor.toString().trim();
			outputCaptor.reset();			// make empty so doesn't interfere with next test

			readList.get(0).listExpense();
			assertEquals(expected, outputCaptor.toString().trim());
			outputCaptor.reset();			// make empty so doesn't interfere with next test

			expenseList.get(1).listExpense();
			expected = outputCaptor.toString().trim();
			outputCaptor.reset();			// make empty so doesn't interfere with next test

			readList.get(1).listExpense();
			assertEquals(expected, outputCaptor.toString().trim());
			outputCaptor.reset();			// make empty so doesn't interfere with next test
			
			expenseList.get(2).listExpense();
			expected = outputCaptor.toString().trim();
			outputCaptor.reset();			// make empty so doesn't interfere with next test

			readList.get(2).listExpense();
			assertEquals(expected, outputCaptor.toString().trim());
			outputCaptor.reset();			// make empty so doesn't interfere with next test

			
			
			// save to the same file with modification
			expenseNode = new Expense ("HBO Max", 16.99f, 2026, 04, 27, true);
			expenseList.add(expenseNode);
			
			SaveAsTXT.saveToFile(expenseList, "januaryExpenses");
			
			// read from same file with modifications
			readList = SaveAsTXT.loadFromFile("januaryExpenses");
			
			expenseList.get(0).listExpense();
			expected = outputCaptor.toString().trim();
			outputCaptor.reset();			// make empty so doesn't interfere with next test

			readList.get(0).listExpense();
			assertEquals(expected, outputCaptor.toString().trim());
			outputCaptor.reset();			// make empty so doesn't interfere with next test

			expenseList.get(1).listExpense();
			expected = outputCaptor.toString().trim();
			outputCaptor.reset();			// make empty so doesn't interfere with next test

			readList.get(1).listExpense();
			assertEquals(expected, outputCaptor.toString().trim());
			outputCaptor.reset();			// make empty so doesn't interfere with next test
			
			expenseList.get(2).listExpense();
			expected = outputCaptor.toString().trim();
			outputCaptor.reset();			// make empty so doesn't interfere with next test

			readList.get(2).listExpense();
			assertEquals(expected, outputCaptor.toString().trim());
			outputCaptor.reset();			// make empty so doesn't interfere with next test
		
			// try to read non-existant file}
			file.delete();
			readList = SaveAsTXT.loadFromFile("januaryExpenses");
			assertEquals(0, readList.size());
			
			// trip if file length is 0 read
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.exit(1);
			}
			
			readList = SaveAsTXT.loadFromFile("januaryExpenses");
			assertEquals(0, readList.size());
			file.delete();

			// trip the inability to read or write to file by making file a locked folder (no read permissions)
			Path tempDir = null;
			try {
				Path tempFolder = Files.createTempDirectory("testTrap");
				
				// variables to try to trip load from file
				File secretFile = new File(tempFolder.toFile(),"locked.txt");
				secretFile.createNewFile();

				// test invalid save to
				SaveAsTXT.saveToFile(expenseList, tempFolder.toString());
				SaveAsTXT.saveToFile(expenseList, secretFile.getAbsolutePath());
				assertEquals("Error saving file: "+ tempFolder.toString(), outputCaptor.toString().trim());
				outputCaptor.reset();			// make empty so doesn't interfere with next test
				
				
				// << I CANT FIGURE OUT HOW TO TRIP THE CATCH IN LOAD FROM FILE>>
				secretFile.setReadable(false);		// lock the folder so no reading
				SaveAsTXT.loadFromFile(secretFile.getAbsolutePath());
				assertEquals("Error reading file: "+ tempFolder.toString(), outputCaptor.toString().trim());
				outputCaptor.reset();			// make empty so doesn't interfere with next test
			
				tempFolder.toFile().delete();
			} catch (IOException e) {
				System.exit(1);
			}
			
			
				
		}

		
		@AfterEach
		public void reset()
		{
			System.setOut(standardOut);
		}


}
