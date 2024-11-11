package in.ac.adit.pwj.miniproject.expenses;

import java.util.Date;

public class Other extends Expense {

    public Other(String description, double amount, Date date) {
        super(description, amount, date);
    }

    @Override
    public String getCategory() {
        return "Other";
    }
}
