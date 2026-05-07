import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.LinkedList;

public class SaveDebtTXT {
	// saveToFile: Saves persistent data to files so it can be reused
		// Inputs, doubly linked list, and the filename
		public static void saveToFile(LinkedList<Debt> debtList, String fileName) {
			try {
			File file = new File(fileName);

			// Create file if it doesn't exist
			if (!file.exists()) {
				file.createNewFile();
			}

			// Overwrite file
			PrintWriter writer = new PrintWriter(new FileWriter(file, false));

			// Write each expense (same structure as SaveAsTXT)
			for (Debt debt : debtList) {
				writer.println(debt.getPrincipleAmount());
				writer.println(debt.getName());
				writer.println(debt.getDueDate());
				writer.println(debt.getInterestRate());
				writer.println(debt.getCompoundOrSimple() ? 0 : 1);
				writer.println(debt.getRemainingBalance());
				writer.println(debt.getStatus() ? 0 : 1);

				SaveAsTXT.saveToFile(debt.getPaymentHistory(), debt.getName() + " Expenses");

				writer.println("---");
			}

			writer.close();
			} catch( IOException e) {
				System.out.println("Error saving file: " + fileName);
			}
		}

		// loadFromFile: creates a linkedList from the previously saved file data
		// input = files, Outputs = LinkedLists of goals
		public static LinkedList<Debt> loadFromFile(String fileName) {
			LinkedList<Debt> debts = new LinkedList<>();
			File file = new File(fileName);

			// Prevents most errors
			if (!file.exists() || file.length() == 0) {
				return debts;
			}

			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;

			while ((line = reader.readLine()) != null) {
				float principle = Float.parseFloat(line);
				String name = reader.readLine();
				LocalDate dueDate = LocalDate.parse(reader.readLine());
				float interest = Float.parseFloat(reader.readLine());
				int readBool = Integer.parseInt(reader.readLine());
				boolean type = (readBool == 0) ? false : true;
				float remaining = Float.parseFloat(reader.readLine());
				readBool = Integer.parseInt(reader.readLine());
				boolean status = (readBool == 0) ? false : true;
				reader.readLine(); // skip "---";

				LinkedList<Expense> expenseList = SaveAsTXT.loadFromFile(name + " Expenses");
				
				Debt debt = new Debt(name, principle, dueDate.getYear(), dueDate.getMonthValue(), dueDate.getDayOfMonth(), interest, type, remaining, status, expenseList);

				debts.add(debt);
			}

			reader.close();
			} catch (IOException e) {
				System.out.println("Error reading file: " + fileName);
			}
			
			return debts;
		}
}
