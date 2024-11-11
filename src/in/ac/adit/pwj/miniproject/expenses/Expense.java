package in.ac.adit.pwj.miniproject.expenses;

import java.util.Date;

public abstract class Expense {
    private final String description;
    private final double amount;
    private final Date date;

    public Expense(String description, double amount, Date date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public Date getDate() { return date; }

    // Abstract method that must be implemented by subclasses (e.g., Food, Travel)
    public abstract String getCategory();
}



