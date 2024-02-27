import java.util.Scanner;

public class Menu {
    protected TransactionsService service;
    protected Scanner s;

    public void processUserInput() {
        showMenuItems();
        while (true) {
            callFunction(s.nextInt());
            showDelimiter();
            showMenuItems();
        }
    }

    Menu() {
        service = TransactionsService.getInstance();
        s = new Scanner(System.in);
    }

    protected void showMenuItems() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        System.out.println("5. Finish execution");
    }

    protected void showDelimiter() {
        System.out.println("-----------------------------" +
                "----------------------------");
    }

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
                finish();
                break;
            default:
                showDelimiter();
                showMenuItems();
                break;
        }
    }

    protected void addUserDialog() {
        System.out.println("Enter a user name and a balance");

        try {
            int id = service.addUser(s.next(), s.nextInt());
            System.out.println("User with id = " + id + " is added");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void showUserBalanceDialog() {
        System.out.println("Enter a user ID");

        try {
            int id = s.nextInt();
            System.out.println(service.getUserName(id) +
                    " - " + service.getUserBalance(id));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void performTransferDialog() {
        System.out.println("Enter a sender ID, " +
                "a recipient ID, " +
                "and a transfer amount");

        try {
            service.transfer(s.nextInt(), s.nextInt(), s.nextInt());
            System.out.println("The transfer is completed");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void viewUserTransactionsDialog() {
        System.out.println("Enter a user ID");

        try {
            int id = s.nextInt();
            Transaction[] userTransactions = service.getUserTransactions(id);
            for (Transaction t : userTransactions) {
                if (t.getCategory() == Transaction.TransferCategory.DEBIT) {
                    System.out.println("From " + t.getSender().getName() +
                            "(id = " + t.getSender().getId() + ") " +
                            t.getAmount() +
                            " with id = " + t.getId());
                } else {
                    System.out.println("To " + t.getRecipient().getName() +
                            "(id = " + t.getRecipient().getId() + ") " +
                            t.getAmount() +
                            " with id = " + t.getId());
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void finish() {
        s.close();
        System.exit(0);
    }

}
