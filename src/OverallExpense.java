// OverallExpense - displays the overall expenses page in terminal format
// This class ONLY handles displaying data, not modifying or storing it

public class OverallExpense 
{
    // constant that limits how many expenses are shown per page
    // (showing 10 items at a time)
    private static final int MAX_ITEMS = 10;

    // displayOverallExpense - displays all expenses for a given month along with total spending
    // Month currentMonth: contains all expenses for that month
    // startIndex: determines where to start displaying expenses 
    public static void displayOverallExpense(MonthlyBreakdown currentMonth, int startIndex)
    {
        // ===== HEADER SECTION =====
        // prints a formatted title so the user knows what page they are on
        System.out.println();
        System.out.println("========================================");
        System.out.println("          OVERALL EXPENSE PAGE");
        System.out.println("========================================");
        System.out.println();

        // ===== MONTH DISPLAY =====
        // shows the current month and year using the Month object's attributes
        System.out.println("Month: " + currentMonth.name + " " + currentMonth.year);

        // ===== TOTAL SPENDING CALCULATION =====
        // initializes a variable to store total spending for the month
        float totalSpending = 0;

        // loops through all expenses stored in the month
        // and adds each expense amount to the total
        for (int expenseIndex = 0; expenseIndex < currentMonth.monthlyExpenses.size(); expenseIndex++)
        {
            totalSpending += currentMonth.monthlyExpenses.get(expenseIndex).getAmount();
        }

        // prints total spending formatted to 2 decimal places
        System.out.printf("Total Spending: $%.2f%n%n", totalSpending);

        // ===== EXPENSE LIST HEADER =====
        System.out.println("Expenses:");
        System.out.println("----------------------------------------");
        System.out.printf("%-5s %-15s %-10s%n", "#", "Name", "Amount");
        System.out.println("----------------------------------------");

        // ===== PAGINATION SETUP =====
        // keeps track of how many items have been displayed so far
        int itemsShown = 0;

        // adjusts index to match list position (lists start at 0, display starts at 1)
        int expenseIndex = startIndex - 1;

        // ===== DISPLAY EXPENSES =====
        // loops through the list and displays up to MAX_ITEMS (10 expenses)
        while (expenseIndex < currentMonth.monthlyExpenses.size() && itemsShown < MAX_ITEMS)
        {
            // gets the current expense object from the list
            Expense currentExpense = currentMonth.monthlyExpenses.get(expenseIndex);

            // prints formatted expense data:
            // index number, expense name, and amount
            System.out.printf("%-5d %-15s $%-10.2f%n",
                    expenseIndex + 1,              // display number (1-based)
                    currentExpense.getName(),      // expense name
                    currentExpense.getAmount());   // expense amount

            // move to next expense
            expenseIndex++;

            // increment count of displayed items
            itemsShown++;
        }

        // ===== EMPTY LIST CHECK =====
        // if no expenses were displayed, inform the user
        if (itemsShown == 0)
        {
            System.out.println("No expenses found for this month.");
        }

        // ===== FOOTER LINE =====
        System.out.println("----------------------------------------");

        // ===== USER OPTIONS =====
        // these options will be handled elsewhere (example Driver.java)
        System.out.println();
        System.out.println("Options:");
        System.out.println("1. Return to Home Page");     // exit or go back
        System.out.println("2. List Next 10 Items");      // show next page of expenses

        // prompt user to choose an option
        System.out.print("Enter your choice: ");
    }
}