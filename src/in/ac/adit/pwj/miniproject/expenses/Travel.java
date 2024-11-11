package in.ac.adit.pwj.miniproject.expenses;

import java.util.Date;

public class Travel extends Expense {
    public Travel(String description, double amount, Date date) {
        super(description, amount, date);
    }

    @Override
    public String getCategory() {
        return "Travel";
    }
}

