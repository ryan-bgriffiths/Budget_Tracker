import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

// <<DONE WITH TEST CASES>>

class SaveGoalTXTTest {
	
	// set up list to write
	LinkedList<Goal> writeList = new LinkedList<Goal>();
	LinkedList<Goal> readList = new LinkedList<Goal>();
	
	String name1 = "Air travel";
	String name2 = "Billiard";
	String name3 = "Crossfit";

	
	@BeforeEach
	public void setUp()
	{

		// establish write list
		Goal goalNode = new Goal(true, 2026, 05, 14, 2026, 06, 29, name1, "fly to hawaii", 2000f);
		writeList.add(goalNode);
		goalNode = new Goal(true, 2026, 05, 14, 2026, 06, 29, name2, "get billiard table", 200f);
		writeList.add(goalNode);
	}
	
	
	@Test
	void testReadToFile() {
		// call save to file on new file
		SaveGoalTXT.saveToFile(writeList, "testGoal");
		
		// read new file
		readList = SaveGoalTXT.loadFromFile("testGoal");
		
		assertEquals(name1, readList.get(0).getName());
		assertEquals(name2, readList.get(1).getName());
		
		
		// test if save to pre-existing file
		Goal goalNode = new Goal (true, 2026, 05, 14, 2026, 06, 29, name3, "get crossfit class", 2000f);
		writeList.add(goalNode);
					
		SaveGoalTXT.saveToFile(writeList, "testGoal");
					
		// read from same file with modifications
		readList = SaveGoalTXT.loadFromFile("testGoal");
		
		assertEquals(name1, readList.get(0).getName());
		assertEquals(name2, readList.get(1).getName());
		assertEquals(name3, readList.get(2).getName());
	}

	
	@Test
	void testLoadFromFile() {
		// try to read from non-existant test file
		readList = SaveGoalTXT.loadFromFile("testGoal");

		assertEquals(true, readList.isEmpty());
		
		// try to read from empty file
		File file = new File("testGoal");
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.exit(-1);
		}
		
		readList = SaveGoalTXT.loadFromFile("testGoal");
		assertEquals(true, readList.isEmpty());
		
		// create test file
		SaveGoalTXT.saveToFile(writeList, "testGoal");
		
		// read pre-existing file
		readList = SaveGoalTXT.loadFromFile("testGoal");
		
		assertEquals(name1, readList.get(0).getName());
		assertEquals(name2, readList.get(1).getName());
	}
	
	
	@AfterEach
	public void reset()
	{
		File file = new File("testGoal");
		file.delete();
	}
}
