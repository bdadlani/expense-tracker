package in.ac.adit.pwj.miniproject.expenses;

import java.util.Date;

public class Food extends Expense {
    public Food(String description, double amount, Date date) {
        super(description, amount, date);
    }

    @Override
    public String getCategory() {
        return "Food";
    }
}

