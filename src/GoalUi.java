import java.util.LinkedList;
import java.util.Scanner;

public class GoalUi {
	public static void goalMenu(Scanner inFile, LinkedList<Goal> goals) { // Menu
		int choice = -1;

		while (choice != 0) {
			System.out.printf("\n%s\n", "-".repeat(50));
			System.out.printf("%32s\n", "GOAL MENU");
			System.out.printf("%s\n", "-".repeat(50));
			System.out.printf("\n\t%s\n", "Options:");
			System.out.printf("\t%s\n", "  1. Add Goal");
			System.out.printf("\t%s\n", "  2. Modify Goal");
			System.out.printf("\t%s\n", "  3. Delete Goal");

			choice = Driver.getMenuOption(2, inFile);

			switch (choice) {
			case 1:
				addGoal(inFile, goals);
				break;
			case 2:
				deleteGoal(inFile, goals);
				break;
			}
		}
	}

	public static void addGoal(Scanner inFile, LinkedList<Goal> goals) { // Collect goal and goal information from the user and adds to list
		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.printf("%32s\n", "ADD GOAL");
		System.out.printf("%s\n\n", "-".repeat(50));

		boolean savingsOrBudget = false;
		boolean validType = false;

		while (!validType) {
			System.out.print("Is this a saving goal? (y/n); ");
			String typeInput = inFile.next().trim().toLowerCase();

			if (typeInput.equals("y")) {
				savingsOrBudget = true;
				validType = true;
			} else if (typeInput.equals("n")) {
				savingsOrBudget = false;
				validType = false;
			} else {
				return;
			}
		}
		System.out.print("Enter Start year?: ");
		int startYear = inFile.nextInt();

		System.out.print("Enter Start month?: ");
		int startMonth = inFile.nextInt();

		System.out.print("Enter Start day?: ");
		int startDay = inFile.nextInt();

		System.out.print("Enter end year: ");
		int endYear = inFile.nextInt();

		System.out.print("Enter end month: ");
		int endMonth = inFile.nextInt();

		System.out.print("Enter end day: ");
		int endDay = inFile.nextInt();

		System.out.print("Enter goal name: ");
		String name = inFile.nextLine().trim();

		System.out.print("Enter description: ");
		String description = inFile.nextLine().trim();

		System.out.print("Enter amount: ");
		float amount = inFile.nextFloat();

		System.out.print("Enter progress: ");
		float progress = inFile.nextFloat();

		Goal newGoal = new Goal(savingsOrBudget, startYear, startMonth, startDay, endYear, endMonth, endDay, name,
				description, amount, progress);
		goals.add(newGoal);
		sortByDate(goals);

		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.println("Goal added.");
	}

	public static void deleteGoal(Scanner inFile, LinkedList<Goal> goals) { //User picks one goal to delete
		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.printf("%32s\n", "DELETE GOAL");
		System.out.printf("%s\n\n", "-".repeat(50));

		if (goals.isEmpty()) {
			System.out.println("No goals have been entered yet.");
			return;
		}

		for (int i = 0; i < goals.size(); i++) {
			System.out.println((i + 1) + ". " + goals.get(i).getName());
		}

		System.out.print("\nEnter goal number to delete, or 0 to cancel: ");
		int choice = inFile.nextInt();

		if (choice == 0) {
			return;
		}

		if (choice < 1 || choice > goals.size()) {
			System.out.println("[!] ERROR: Invalid selection.");
			return;
		}

		goals.remove(choice - 1);

		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.println("Goal deleted successfully.");
	}

	public static void sortByDate(LinkedList<Goal> goals) { //Sort date from earliest to latest.
		goals.sort((g1, g2) -> g1.getEndDate().compareTo(g2.getEndDate()));
	}
}