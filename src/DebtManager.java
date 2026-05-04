import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

// DebtManager - Handles debt dashboard, add, delete, modify, past debt, and debt details
// dependencies: Debt, LinkedList, Scanner, LocalDate; sub-classes: none
public class DebtManager {

    // displayDashboard - Displays active debt entries and total active debt
    // LinkedList<Debt>; void
    public static void displayDashboard(LinkedList<Debt> debtList) {
        float totalDebt = 0;
        boolean foundActiveDebt = false;

        System.out.println("\n===== DEBT PANEL =====");
        System.out.println("\n--- Active Debt Entries ---");

        for (int i = 0; i < debtList.size(); i++) {
            Debt debt = debtList.get(i);

            if (debt.status == true) {
                foundActiveDebt = true;
                totalDebt += debt.remainingBalance;

                System.out.println("\nDebt #" + (i + 1));
                System.out.println("Name: " + debt.name);
                System.out.printf("Total Amount: $%,.2f\n", debt.remainingBalance);
                System.out.println("Status: Active");
            }
        }

        if (!foundActiveDebt) {
            System.out.println("No active debt entries found.");
        }

        System.out.printf("\nTotal Active Debt: $%,.2f\n", totalDebt);
    }

    // addDebt - Adds a new debt entry
    // LinkedList<Debt>, Scanner; void
    public static void addDebt(LinkedList<Debt> debtList, Scanner input) {
        Debt debt = new Debt();

        System.out.print("Enter debt name: ");
        debt.name = input.nextLine();

        System.out.print("Enter principal amount: ");
        debt.principle = Float.parseFloat(input.nextLine());

        System.out.print("Enter remaining balance: ");
        debt.remainingBalance = Float.parseFloat(input.nextLine());

        System.out.print("Enter interest rate: ");
        debt.interestRate = Float.parseFloat(input.nextLine());

        System.out.println("Interest Type:");
        System.out.println("1. Simple Interest");
        System.out.println("2. Compound Interest");
        System.out.print("Choose (1 or 2): ");
        int interestChoice = Integer.parseInt(input.nextLine());

        debt.compoundOrSimple = interestChoice == 2;

        System.out.print("Enter minimum payment due date (YYYY-MM-DD): ");
        debt.dueDate = LocalDate.parse(input.nextLine());

        debt.status = true;
        debt.paymentHistory = new LinkedList<>();

        debtList.add(debt);

        System.out.println("Debt added successfully.");
    }

    // deleteDebt - Deletes a debt entry
    // LinkedList<Debt>, Scanner; void
    public static void deleteDebt(LinkedList<Debt> debtList, Scanner input) {
        displayAllDebts(debtList);

        if (debtList.isEmpty()) {
            return;
        }

        System.out.print("Enter debt number to delete: ");
        int index = Integer.parseInt(input.nextLine()) - 1;

        if (index >= 0 && index < debtList.size()) {
            debtList.remove(index);
            System.out.println("Debt deleted successfully.");
        } else {
            System.out.println("Invalid debt number.");
        }
    }

    // modifyDebt - Modifies an existing debt entry
    // LinkedList<Debt>, Scanner; void
    public static void modifyDebt(LinkedList<Debt> debtList, Scanner input) {
        displayAllDebts(debtList);

        if (debtList.isEmpty()) {
            return;
        }

        System.out.print("Enter debt number to modify: ");
        int index = Integer.parseInt(input.nextLine()) - 1;

        if (index < 0 || index >= debtList.size()) {
            System.out.println("Invalid debt number.");
            return;
        }

        Debt debt = debtList.get(index);

        System.out.print("Enter new debt name: ");
        debt.name = input.nextLine();

        System.out.print("Enter new principal amount: ");
        debt.principle = Float.parseFloat(input.nextLine());

        System.out.print("Enter new remaining balance: ");
        debt.remainingBalance = Float.parseFloat(input.nextLine());

        System.out.print("Enter new interest rate: ");
        debt.interestRate = Float.parseFloat(input.nextLine());

        System.out.println("Interest Type:");
        System.out.println("1. Simple Interest");
        System.out.println("2. Compound Interest");
        System.out.print("Choose (1 or 2): ");
        int interestChoice = Integer.parseInt(input.nextLine());

        debt.compoundOrSimple = interestChoice == 2;

        System.out.print("Enter new minimum payment due date (YYYY-MM-DD): ");
        debt.dueDate = LocalDate.parse(input.nextLine());

        System.out.println("Debt Status:");
        System.out.println("1. Active");
        System.out.println("2. Inactive");
        System.out.print("Choose (1 or 2): ");
        int statusChoice = Integer.parseInt(input.nextLine());

        debt.status = statusChoice == 1;

        System.out.println("Debt modified successfully.");
    }

    // viewPastDebt - Displays inactive debt entries
    // LinkedList<Debt>; void
    public static void viewPastDebt(LinkedList<Debt> debtList) {
        boolean foundPastDebt = false;

        System.out.println("\n===== PAST DEBT =====");

        for (int i = 0; i < debtList.size(); i++) {
            Debt debt = debtList.get(i);

            if (debt.status == false) {
                foundPastDebt = true;

                System.out.println("\nDebt #" + (i + 1));
                System.out.println("Name: " + debt.name);
                System.out.printf("Total Amount: $%,.2f\n", debt.remainingBalance);
                System.out.println("Status: Inactive");
            }
        }

        if (!foundPastDebt) {
            System.out.println("No past debt entries found.");
        }
    }

    // viewDebtDetails - Displays details for one debt case
    // LinkedList<Debt>, Scanner; void
    public static void viewDebtDetails(LinkedList<Debt> debtList, Scanner input) {
        displayAllDebts(debtList);

        if (debtList.isEmpty()) {
            return;
        }

        System.out.print("Enter debt number to view details: ");
        int index = Integer.parseInt(input.nextLine()) - 1;

        if (index < 0 || index >= debtList.size()) {
            System.out.println("Invalid debt number.");
            return;
        }

        Debt debt = debtList.get(index);

        System.out.println("\n===== DEBT CASE DETAILS =====");
        System.out.println("Name: " + debt.name);
        System.out.printf("Principal Amount: $%,.2f\n", debt.principle);
        System.out.printf("Remaining Balance: $%,.2f\n", debt.remainingBalance);
        System.out.printf("Interest Rate: %.2f%%\n", debt.interestRate);
        System.out.println("Interest Type: " + (debt.compoundOrSimple ? "Compound" : "Simple"));
        System.out.println("Next Payment Due Date: " + debt.dueDate);
        System.out.println("Estimated Payoff Time: " + estimatePayoffTime(debt));
        System.out.println("Status: " + (debt.status ? "Active" : "Inactive"));
    }

    // displayAllDebts - Displays all debt entries
    // LinkedList<Debt>; void
    public static void displayAllDebts(LinkedList<Debt> debtList) {
        System.out.println("\n===== ALL DEBT ENTRIES =====");

        if (debtList.isEmpty()) {
            System.out.println("No debt entries found.");
            return;
        }

        for (int i = 0; i < debtList.size(); i++) {
            Debt debt = debtList.get(i);

            System.out.println("\nDebt #" + (i + 1));
            System.out.println("Name: " + debt.name);
            System.out.printf("Total Amount: $%,.2f\n", debt.remainingBalance);
            System.out.println("Status: " + (debt.status ? "Active" : "Inactive"));
        }
    }

    // estimatePayoffTime - Estimates payoff time
    // Debt; String
    public static String estimatePayoffTime(Debt debt) {
        float estimatedMonthlyPayment = 200.00f;

        if (debt.remainingBalance <= 0) {
            return "0 months";
        }

        int totalMonths = (int) Math.ceil(debt.remainingBalance / estimatedMonthlyPayment);

        int years = totalMonths / 12;
        int months = totalMonths % 12;

        if (years > 0 && months > 0) {
            return years + " years and " + months + " months";
        } else if (years > 0) {
            return years + " years";
        } else {
            return months + " months";
        }
    }
}