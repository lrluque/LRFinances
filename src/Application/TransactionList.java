package Application;

public class TransactionList {
    Transaction[] transactions;
    int ocupation = 0;

    public void createList() {
        transactions = new Transaction[1000];
    }

    public Transaction[] getList() {
        return transactions;
    }





}
