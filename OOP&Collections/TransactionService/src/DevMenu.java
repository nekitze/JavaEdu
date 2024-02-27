import java.util.UUID;

public class DevMenu extends Menu {
    @Override
    protected void showMenuItems() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        System.out.println("5. DEV – remove a transfer by ID");
        System.out.println("6. DEV – check transfer validity");
        System.out.println("7. Finish execution");
    }

    @Override
    protected void callFunction(int id) {
        switch (id) {
            case 1:
                addUserDialog();
                break;
            case 2:
                showUserBalanceDialog();
                break;
            case 3:
                performTransferDialog();
                break;
            case 4:
                viewUserTransactionsDialog();
                break;
            case 5:
                removeTransferByIdDialog();
                break;
            case 6:
                checkTransferValidityDialog();
                break;
            case 7:
                finish();
            default:
                showDelimiter();
                showMenuItems();
                break;
        }
    }

    protected void removeTransferByIdDialog() {
        System.out.println("Enter a user ID and a transfer ID");

        try {
            int userId = s.nextInt();
            UUID transactionId = UUID.fromString(s.next());
            Transaction[] userTransactions = service.getUserTransactions(userId);

            for (Transaction t : userTransactions) {
                if (t.getId().equals(transactionId)) {
                    if (t.getCategory() == Transaction.TransferCategory.DEBIT) {
                        System.out.println("Transfer from " +
                                t.getSender().getName() +
                                "(id = " + t.getSender().getId() +
                                ") " + t.getAmount() + " removed");
                    } else {
                        System.out.println("Transfer to " +
                                t.getRecipient().getName() +
                                "(id = " + t.getRecipient().getId() +
                                ") " + t.getAmount() + " removed");
                    }
                }
            }
            service.removeTransaction(userId, transactionId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void checkTransferValidityDialog() {
        System.out.println("Check results:");
        Transaction[] invalidTransactions = service.getInvalidTransactions();
        for (Transaction t : invalidTransactions) {
            if (t.getCategory() == Transaction.TransferCategory.DEBIT) {
                System.out.println(t.getRecipient().getName() +
                        "(id = " + t.getRecipient().getId() +
                        ") has an unacknowledged transfer id = " +
                        t.getId() + " from " + t.getSender().getName() +
                        "(id = " + t.getSender().getId() +
                        ") for " + t.getAmount());
            } else {
                System.out.println(t.getSender().getName() +
                        "(id = " + t.getSender().getId() +
                        ") has an unacknowledged transfer id = " +
                        t.getId() + " to " + t.getRecipient().getName() +
                        "(id = " + t.getRecipient().getId() +
                        ") for " + t.getAmount());
            }
        }
        if (invalidTransactions.length == 0) {
            System.out.println("All transactions are valid!");
        }
    }
}
