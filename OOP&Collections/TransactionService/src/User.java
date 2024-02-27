import java.util.UUID;

public class User {
    private final int id;
    private String name;
    private int balance;
    private TransactionsList transactionsList;

    public User(String name, int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("User balance " +
                    "can't be negative!");
        }

        this.id = UserIdsGenerator.getInstance().generateID();
        this.name = name;
        this.balance = balance;
        this.transactionsList = new TransactionsLinkedList();
    }

    public Transaction[] getTransactions() {
        return transactionsList.toArray();
    }

    public void addTransaction(Transaction transaction) {
        transactionsList.addTransaction(transaction);
        balance += transaction.getAmount();
    }

    public void removeTransaction(UUID id) {
        transactionsList.removeTransactionByID(id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("User balance " +
                    "can't be negative!");
        }
        balance = value;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    @Override
    public String toString() {
        return "User ID: " + id +
                "\nName: " + name +
                "\nBalance: " + balance + "\n";
    }
}
