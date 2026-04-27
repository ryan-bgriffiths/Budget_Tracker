import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ExpenseTest {

    @Test
    void constructorSetsAllFieldsCorrectly() {
        Expense expense = new Expense(45.75, "Groceries", "2025-03-10");

        assertEquals(45.75, expense.amount);
        assertEquals("Groceries", expense.transDescription);
        assertEquals("2025-03-10", expense.transDate);
    }

    @Test
    void constructorAllowsZeroAmount() {
        Expense expense = new Expense(0.0, "No Spend Day", "2025-03-11");

        assertEquals(0.0, expense.amount);
        assertEquals("No Spend Day", expense.transDescription);
        assertEquals("2025-03-11", expense.transDate);
    }

    @Test
    void constructorAllowsNegativeAmount() {
        Expense expense = new Expense(-12.50, "Refund", "2025-03-12");

        assertEquals(-12.50, expense.amount);
        assertEquals("Refund", expense.transDescription);
        assertEquals("2025-03-12", expense.transDate);
    }

    @Test
    void constructorAllowsEmptyStrings() {
        Expense expense = new Expense(10.0, "", "");

        assertEquals(10.0, expense.amount);
        assertEquals("", expense.transDescription);
        assertEquals("", expense.transDate);
    }

    @Test
    void constructorAllowsNullStrings() {
        Expense expense = new Expense(15.0, null, null);

        assertEquals(15.0, expense.amount);
        assertEquals(null, expense.transDescription);
        assertEquals(null, expense.transDate);
    }
}