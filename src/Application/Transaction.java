package Application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction {
    private SimpleStringProperty info;
    private SimpleStringProperty expensesIncome;
    private SimpleStringProperty date;
    private SimpleDoubleProperty balance;

    public Transaction(String info, String date, String expensesIncome, Double balance) {
        this.info = new SimpleStringProperty(info);
        this.date = new SimpleStringProperty(date);
        this.expensesIncome = new SimpleStringProperty(expensesIncome);
        this.balance = new SimpleDoubleProperty(balance);
    }

    public String getInfo() {
        return info.get();
    }


    public String getOutcomeIncome() {
        return expensesIncome.get();
    }

    public double getBalance() {
        return balance.get();
    }

    public String getDate() {
        return date.get();
    }

}
