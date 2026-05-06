import java.time.LocalDate;

//Responsible for creating goal objects

public class GoalFactory {
	public static Goal createGoal(LocalDate startDate, String name, String description, float amount) {
		return new Goal(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfYear(), name, description,
				amount);
	}
}
