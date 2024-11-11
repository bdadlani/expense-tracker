package in.ac.adit.pwj.miniproject.expenses;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Locale;

public class ExpenseTracker {
    private final List<Expense> expenses = Collections.synchronizedList(new ArrayList<>());
    private final Map<String, Double> budgets = new HashMap<>();
    private JFrame frame;
    private JTextArea reportArea;

    // Generate a unique filename using timestamp
    private final String reportFilename = generateFilename();

    public ExpenseTracker() {
        budgets.put("Food", 1000.0);
        budgets.put("Travel", 2000.0);
        budgets.put("Other", 500.0);
        setupGUI();
    }

    private void setupGUI() {
        frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        JTextField descField = new JTextField();
        JTextField amountField = new JTextField();
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Food", "Travel", "Other"});
        JButton addButton = new JButton("Add Expense");
        JButton saveButton = new JButton("Save Report");
        reportArea = new JTextArea(10, 30);
        reportArea.setEditable(false);

        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Amount (in INR):"));
        panel.add(amountField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryBox);
        panel.add(addButton);
        panel.add(saveButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(reportArea), BorderLayout.CENTER);

        // Action listener for Add Expense button
        addButton.addActionListener(e -> {
            try {
                String desc = descField.getText().trim();
                double amount = Double.parseDouble(amountField.getText().trim());
                String category = (String) categoryBox.getSelectedItem();

                if (category == null) {
                    JOptionPane.showMessageDialog(frame, "Please select a category!");
                    return;
                }

                // Add the expense based on the selected category
                switch (category) {
                    case "Food":
                        addExpense(new Food(desc, amount, new Date()));
                        break;
                    case "Travel":
                        addExpense(new Travel(desc, amount, new Date()));
                        break;
                    case "Other":
                        addExpense(new Other(desc, amount, new Date()));
                        break;
                }

                descField.setText("");
                amountField.setText("");
                displayReport();

                // Save the report after adding an expense
                try {
                    saveReport();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving the report: " + ex.getMessage());
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid amount entered! Please enter a valid number.");
            }
        });

        // Action listener for Save Report button
        saveButton.addActionListener(e -> {
            try {
                saveReport();
                JOptionPane.showMessageDialog(frame, "Report saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving the report.");
            }
        });

        frame.setVisible(true);
    }

    // Generate a unique filename using the current timestamp
    private String generateFilename() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "expense_report_" + timestamp + ".txt";
    }

    // Add an expense to the list and update the budget
    private void addExpense(Expense expense) {
        double currentBudget = budgets.get(expense.getCategory());

        if (expense.getAmount() > currentBudget) {
            JOptionPane.showMessageDialog(frame, "Budget overrun for " + expense.getCategory() + "!");
        } else {
            expenses.add(expense);
            budgets.put(expense.getCategory(), currentBudget - expense.getAmount());
        }
    }

    // Display the report with formatted INR currency
    private void displayReport() {
        StringBuilder report = new StringBuilder();
        report.append("Expense Report\n------------------------\n");

        for (Expense expense : expenses) {
            String formattedAmount = formatCurrency(expense.getAmount());
            report.append(String.format("%s - %s: %s on %s\n",
                    expense.getCategory(), expense.getDescription(), formattedAmount, expense.getDate()));
        }

        reportArea.setText(report.toString());
    }

    // Format amount in INR
    private String formatCurrency(double amount) {
        NumberFormat inrFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        return inrFormat.format(amount);
    }

    // Save the report to a uniquely named file
    private void saveReport() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilename))) {
            writer.write(reportArea.getText());
        }
    }

    public static void main(String[] args) {
        new ExpenseTracker();
    }
}

