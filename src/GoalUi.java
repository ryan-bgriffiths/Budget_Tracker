import java.util.LinkedList;
import java.util.Scanner;

public class GoalUi {
	private LinkedList<Goal> goals; //All objects related to goals
	private Scanner input; //User input
	
	public GoalUi() {
		goals = new LinkedList<Goal>(); //List to store all goals
		input = new Scanner(System.in); //User input to type into console
	}
	
	private Goal goalInput() { //Method for user input and for goals
		System.out.println("Are you saving a goal? true or false? "); //Everything below collecting input
		boolean savingsOrBudget = input.nextBoolean();
		
		System.out.println("What year? ");
		int startYear = input.nextInt();
		
		System.out.println("What month? ");
		int startMonth = input.nextInt();
		
		System.out.println("What day? ");
		int startDay = input.nextInt();
		
		System.out.println("End year? ");
		int endYear = input.nextInt();
		
		System.out.println("End month? ");
		int endMonth = input.nextInt();
		
		System.out.println("End day? ");
		int endDay = input.nextInt();
		
		System.out.println("What is the name? ");
		String name = input.nextLine();
		
		System.out.println("Any description? ");
		String description = input.nextLine();
		
		System.out.println("Amount? ");
		float amount = input.nextFloat();
		
		System.out.println("Progress? ");
		float progress = input.nextFloat();
		
		return new Goal( //Calls from Goal.java and returns with new inputed values
				savingsOrBudget,
				startYear,
				startMonth,
				startDay,
				endYear,
				endMonth,
				endDay,
				name,
				description,
				amount,
				progress
			);
				
	}
	
	private void addGoal() { //Adding new goal
		Goal goal = goalInput();
		goals.add(goal);
		sortbyDate();
		System.out.println("Goal added");
	}
	
	private void deleteGoal() { //Delete goal
		System.out.println("Select what goal to delete");
		String name = input.nextLine();
		
		for(int i = 0; i < goals.size(); i++) {
			if(goals.get(i).getName().equalsIgnoreCase(name)) { //Check if goals name matched with user input
				goals.remove(i);
				System.out.println("Sucessfully removed. ");
				return;
			}
		}
	}
	
	private void sortbyDate() { //Sort goals by dates
		goals.sort((g1, g2) -> g1.getEndDate().compareTo(g2.getEndDate())); //Sort list from Earlier dates ---> later dates
	}
	
	public void run() { //Starts the UI
		int choice = 0; //Will store user choice
		while (choice != 5) {
			System.out.println("Menu");
			System.out.println("1. Add Goal");
			System.out.println("2. Delete Goal");
			System.out.println("3. Edit Goal");
			System.out.println("4. View Goals");
			System.out.println("5. Exit");
			System.out.print("Choose: ");
			
			choice = input.nextInt();
			input.nextLine();
			
			if (choice == 1) {
				addGoal();
			} else if (choice == 2) {
				deleteGoal();
			}
		}
	}
}
