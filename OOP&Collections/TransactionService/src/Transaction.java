import java.util.UUID;

public class Transaction {
    public enum TransferCategory {
        CREDIT,
        DEBIT
    }

    private final UUID id;
    private final User recipient;
    private final User sender;
    private final TransferCategory category;
    private final int amount;


    public Transaction(User recipient,
                       User sender,
                       TransferCategory category,
                       int amount) {

        if (category == TransferCategory.CREDIT && amount >= 0) {
            throw new IllegalArgumentException("Credit amount must be negative");
        }

        if (category == TransferCategory.DEBIT && amount <= 0) {
            throw new IllegalArgumentException("Debit amount must be positive");
        }

        if (amount > sender.getBalance() || amount < -sender.getBalance()) {
            throw new IllegalTransactionException();
        }

        this.recipient = recipient;
        this.sender = sender;
        this.category = category;
        this.amount = amount;

        this.id = UUID.randomUUID();
    }

    private Transaction(User recipient,
                        User sender,
                        TransferCategory category,
                        int amount,
                        UUID id) {
        this.recipient = recipient;
        this.sender = sender;
        this.category = category;
        this.amount = amount;
        this.id = id;
    }

    public Transaction getPair() {
        return new Transaction(
                recipient,
                sender,
                category ==
                        TransferCategory.CREDIT ?
                        TransferCategory.DEBIT
                        : TransferCategory.CREDIT,
                -amount,
                id);
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public TransferCategory getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction ID : " + id +
                "\nRecipient: " + recipient.getId() +
                "\nSender: " + sender.getId() +
                "\nCategory: " + category +
                "\nAmount: " + amount + "\n";
    }
}
