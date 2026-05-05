import java.time.LocalDate;
import java.util.LinkedList;

// debt class allows you to delete, add, and modify debt, as well as 
// calculate remaining balance in debt using principle, progress to debt, and interest 
// NOTE: debt class, DOES NOT save debt, that is a different file

public class Debt {

	// Fields
	String name;
	float principle;
	LocalDate dueDate;
	LinkedList<Expense> paymentHistory;
	float interestRate;
	boolean compoundOrSimple; // true = compound, false = simple
	float remainingBalance;
	boolean activity; // active vs inactive

	// Constructor
	public Debt(String name, float principle, int year, int month, int day, float interestRate,
			boolean compoundOrSimple) {

		this.name = name;
		this.principle = principle;
		this.dueDate = LocalDate.of(year, month, day);
		this.interestRate = interestRate;
		this.compoundOrSimple = compoundOrSimple;
		this.remainingBalance = principle;
		this.paymentHistory = new LinkedList<>();
		this.activity = true;
	}

	// Constructor with remaining balance and status
	public Debt(String name, float principle, int year, int month, int day, float interestRate,
			boolean compoundOrSimple, float remainingBalance, boolean status) {

		this.name = name;
		this.principle = principle;
		this.dueDate = LocalDate.of(year, month, day);
		this.interestRate = interestRate;
		this.compoundOrSimple = compoundOrSimple;
		this.remainingBalance = remainingBalance;
		this.paymentHistory = new LinkedList<>();
		this.activity = status;
	}

	// Getters
	public String getName() {
		return name;
	}

	public float getPrincipalAmount() {
		return principle;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public float getInterestRate() {
		return interestRate;
	}

	public boolean getCompoundOrSimple() {
		return compoundOrSimple;
	}

	public boolean getStatus() {
		return activity;
	}

	public float getProgress() {
		calculateRemainingBalance();

		if (principle == 0) {
			return 100;
		}

		float progress = ((principle - remainingBalance) / principle) * 100;

		if (progress < 0) {
			return 0;
		}

		if (progress > 100) {
			return 100;
		}

		return progress;
	}

	public float getRemainingBalance() {
		return calculateRemainingBalance();
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setPrinciple(float principle) {
		this.principle = principle;
	}

	public void setDueDate(int year, int month, int day) {
		this.dueDate = LocalDate.of(year, month, day);
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public void setCompoundOrSimple(boolean compoundOrSimple) {
		this.compoundOrSimple = compoundOrSimple;
	}

	public void setRemainingBalance(float remainingBalance) {
		this.remainingBalance = remainingBalance;

		if (this.remainingBalance <= 0) {
			this.remainingBalance = 0;
			this.activity = false;
		} else {
			this.activity = true;
		}
	}

	public void setStatus(boolean status) {
		this.activity = status;
	}

	// Calculates remaining balance using principal, payments, and interest
	public float calculateRemainingBalance() {
		float totalPayments = 0;

		for (Expense payment : paymentHistory) {
			totalPayments += payment.getAmount();
		}

		float balanceBeforeInterest = principle - totalPayments;

		if (balanceBeforeInterest <= 0) {
			remainingBalance = 0;
			activity = false;
			return remainingBalance;
		}

		float interestAmount;

		if (compoundOrSimple) {
			// Compound interest for 1 year
			interestAmount = (float) (balanceBeforeInterest * Math.pow(1 + (interestRate / 100), 1)
					- balanceBeforeInterest);
		} else {
			// Simple interest for 1 year
			interestAmount = balanceBeforeInterest * (interestRate / 100);
		}

		remainingBalance = balanceBeforeInterest + interestAmount;
		activity = remainingBalance > 0;

		return remainingBalance;
	}

	// Adds a payment toward this debt
	public void addPayment(Expense payment) {
		paymentHistory.add(payment);
		calculateRemainingBalance();
	}

	// Add Debt
	public static void addDebt(LinkedList<Debt> debtList, Debt debt) {
		debtList.add(debt);
	}

	// Delete Debt
	public static boolean deleteDebt(LinkedList<Debt> debtList, String name) {
		for (int i = 0; i < debtList.size(); i++) {
			if (debtList.get(i).getName().equalsIgnoreCase(name)) {
				debtList.remove(i);
				return true;
			}
		}

		return false;
	}

	// Modify Debt
	public static boolean modifyDebt(LinkedList<Debt> debtList, String oldName, String newName, float newPrinciple,
			int newYear, int newMonth, int newDay, float newInterestRate, boolean newCompoundOrSimple,
			float newRemainingBalance) {

		for (Debt debt : debtList) {
			if (debt.getName().equalsIgnoreCase(oldName)) {
				debt.setName(newName);
				debt.setPrinciple(newPrinciple);
				debt.setDueDate(newYear, newMonth, newDay);
				debt.setInterestRate(newInterestRate);
				debt.setCompoundOrSimple(newCompoundOrSimple);
				debt.setRemainingBalance(newRemainingBalance);
				return true;
			}
		}

		return false;
	}

	// Find Debt
	public static Debt findDebt(LinkedList<Debt> debtList, String name) {
		for (Debt debt : debtList) {
			if (debt.getName().equalsIgnoreCase(name)) {
				return debt;
			}
		}

		return null;
	}

	// List Debt
	public void listDebt() {
		System.out.printf("%-15s %-10.2f %-12s %-10.2f %-10.2f %-10.2f %-10s\n", name, principle, dueDate.toString(),
				interestRate, remainingBalance, getProgress(), activity ? "Active" : "Inactive");
	}
}
