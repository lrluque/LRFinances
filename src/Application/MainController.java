package Application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private double balance = 0;
    private static double expenses = 0;
    private static double income = 0;

    private TransactionList transactionList;

    @FXML
    private TableColumn<?, ?> balanceCol;

    @FXML
    private TextField quantityTF;

    @FXML
    private TableColumn<?, ?> infoCol;


    @FXML
    private TableColumn<?, ?> dateCol;

    @FXML
    private TextField infoTF;

    @FXML
    private Button withdrawButton;

    @FXML
    private Label balanceLabel;

    @FXML
    private TableColumn<?, ?> expensesCol;

    @FXML
    private Button depositButton;

    @FXML
    private TableView<Transaction> table;

    @FXML
    private VBox vBox;


    @FXML
    private PieChart pieChart;



    public void deposit(ActionEvent actionEvent) throws IOException {
        balance += Double.parseDouble(quantityTF.getText());
        Transaction transaction = new Transaction(infoTF.getText(), getDate(), "+ " + quantityTF.getText(), round(balance));
        table.getItems().add(transaction);
        income += Double.parseDouble(quantityTF.getText());
        transactionList.getList()[transactionList.ocupation] = transaction;
        transactionList.ocupation++;
        writeTransactionsTextFile(transactionList);
        printBalance();
        printPieChart();
    }

    public void withdraw(ActionEvent actionEvent) throws IOException {
        balance -= Double.parseDouble(quantityTF.getText());
        Transaction transaction = new Transaction(infoTF.getText(), getDate(), "- " + quantityTF.getText(), round(balance));
        table.getItems().add(transaction);
        expenses += Double.parseDouble(quantityTF.getText());
        transactionList.getList()[transactionList.ocupation] = transaction;
        transactionList.ocupation++;
        writeTransactionsTextFile(transactionList);
        printBalance();
        printPieChart();
    }

    public void initialize(URL url, ResourceBundle rb){
        transactionList = new TransactionList();
        transactionList.createList();
        ObservableList<Transaction> observableList = FXCollections.observableArrayList(
        );
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            String line = reader.readLine();
            line = reader.readLine();
            int i = 0;
            while (line != null) {
                String[] parameter = line.split(";");
                Transaction transaction = new Transaction(parameter[0], parameter[1], parameter[2], Double.parseDouble(parameter[3]));
                balance = Double.parseDouble(parameter[3]);
                expenses = Double.parseDouble(parameter[4]);
                income = Double.parseDouble(parameter[5]);
                transactionList.getList()[i] = transaction;
                observableList.add(transaction);
                i++;
                transactionList.ocupation++;
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        expensesCol.setCellValueFactory(new PropertyValueFactory<>("outcomeIncome"));
        infoCol.setCellValueFactory(new PropertyValueFactory<>("info"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        printPieChart();
        printBalance();
        table.setItems(observableList);
    }

    public static void writeTransactionsTextFile(TransactionList transactionList) throws IOException {
        FileWriter transactions = new FileWriter("transactions.csv");
        try {
            transactions.write(  "Info;Date;Expenses/Income;Balance;Expenses;Income\n");
            for (int i = 0; transactionList.getList()[i] != null; i++) {
                transactions.write(transactionList.getList()[i].getInfo() + ";" + transactionList.getList()[i].getDate() + ";" + transactionList.getList()[i].getOutcomeIncome() + ";" + transactionList.getList()[i].getBalance() + ";" + expenses + ";" + income + "\n");
            }
            transactions.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getDate(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public Double round(Double num){
        return Math.round(num*100.0)/100.0;
    }

    public void printBalance() {
        balanceLabel.setText(Double.toString(round(balance)) + " €");
    }

    public void printPieChart() {
        ObservableList<PieChart.Data> pierChartData = FXCollections.observableArrayList(
                new PieChart.Data("Income", income),
                new PieChart.Data("Expenses", expenses)
        );

        pieChart.setData(pierChartData);
    }
}
