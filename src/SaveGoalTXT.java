import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;

//SaveGoalTXT: Saves the data from linked lists to files, then can retrieve the data
//Has two functions, and one dependency. Dependent on linkedList from Goal
public class SaveGoalTXT {

	// saveToFile: Saves persistent data to files so it can be reused
	// Inputs, doubly linked list, and the filename
	public static void saveToFile(LinkedList<Goal> goalList, String fileName) {
		try {
		File file = new File(fileName);

		// Create file if it doesn't exist
		if (!file.exists()) {
			file.createNewFile();
		}

		// Overwrite file
		PrintWriter writer = new PrintWriter(new FileWriter(file, false));

		// Write each expense (same structure as SaveAsTXT)
		for (Goal goal : goalList) {
			writer.println(goal.getType() ? 0 : 1);
			writer.println(goal.getStartDate());
			writer.println(goal.getEndDate());
			writer.println(goal.getName());
			writer.println(goal.getDescription());
			writer.println(goal.getAmount());
			writer.println("---");
		}

		writer.close();
		} catch( IOException e) {
			System.out.println("Error saving file: " + fileName);
		}
	}

	// loadFromFile: creates a linkedList from the previously saved file data
	// input = files, Outputs = LinkedLists of goals
	public static LinkedList<Goal> loadFromFile(String fileName) {
		LinkedList<Goal> goals = new LinkedList<>();
		File file = new File(fileName);

		// Prevents most errors
		if (!file.exists() || file.length() == 0) {
			return goals;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
		String line;

		while ((line = reader.readLine()) != null) {
			int readBool = Integer.parseInt(reader.readLine());
			boolean savingsOrBudget = (readBool == 0) ? false : true;
			LocalDate startdate = LocalDate.parse(reader.readLine());
			LocalDate enddate = LocalDate.parse(reader.readLine());
			String name = reader.readLine();
			String description = reader.readLine();
			float amount = Float.parseFloat(line);
			reader.readLine(); // skip "---"
			
			Goal goal = new Goal(savingsOrBudget, startdate.getYear(), 
					startdate.getMonthValue(), startdate.getDayOfMonth(), enddate.getYear(), 
					enddate.getMonthValue(), enddate.getDayOfMonth(), name, description, amount);

			goals.add(goal);
		}

		reader.close();
		} catch (IOException e) {
			System.out.println("Error reading file: " + fileName);
		}
		
		return goals;
	}
}
