import java.util.UUID;

public class TransactionsService {
    private UsersList users;
    private static TransactionsService instance;

    public static TransactionsService getInstance() {
        if (instance == null) {
            instance = new TransactionsService();
        }
        return instance;
    }

    public int addUser(String name, int balance) {
        users.addUser(new User(name, balance));
        return users.getNumberOfUsers();
    }

    public String getUserName(int id) {
        return users.getUserById(id).getName();
    }

    public int getUserBalance(int id) {
        return users.getUserById(id).getBalance();
    }

    public Transaction[] getUserTransactions(int id) {
        return users.getUserById(id).getTransactions();
    }

    public UUID transfer(int senderId, int recipientId, int amount) {
        User sender = users.getUserById(senderId);
        User recipient = users.getUserById(recipientId);

        Transaction senderTransaction = new Transaction(
                recipient,
                sender,
                Transaction.TransferCategory.CREDIT,
                -amount);

        Transaction recipientTransaction = senderTransaction.getPair();

        sender.addTransaction(senderTransaction);
        recipient.addTransaction(recipientTransaction);

        return senderTransaction.getId();
    }

    public void removeTransaction(int userId, UUID transactionId) {
        users.getUserById(userId).removeTransaction(transactionId);
    }

    public Transaction[] getInvalidTransactions() {

        TransactionsList transactions = new TransactionsLinkedList();
        TransactionsList invalidTransactions = new TransactionsLinkedList();

        for (int i = 0; i < users.getNumberOfUsers(); i++) {
            Transaction[] userTransactions =
                    users.getUserByIndex(i).getTransactions();
            for (Transaction userTransaction : userTransactions) {
                transactions.addTransaction(userTransaction);
            }
        }

        Transaction[] tArray = transactions.toArray();

        if (tArray.length == 1) {
            return tArray;
        }

        for (int i = 0; i < tArray.length; i++) {
            if (tArray[i] == null) {
                continue;
            }
            for (int j = i + 1; j < tArray.length; j++) {
                if (tArray[j] != null
                        && tArray[i].getId().equals(tArray[j].getId())) {
                    tArray[i] = null;
                    tArray[j] = null;
                    break;
                }
                if (j == tArray.length - 1) {
                    invalidTransactions.addTransaction(tArray[i]);
                }
            }
        }

        return invalidTransactions.toArray();
    }

    private TransactionsService() {
        users = new UsersArrayList();
    }
}
