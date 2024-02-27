import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Node first;
    private Node last;
    private int size;

    private static class Node {
        Transaction transaction;
        Node prev;
        Node next;

        Node(Transaction transaction, Node prev, Node next) {
            this.transaction = transaction;
            this.prev = prev;
            this.next = next;
        }
    }

    public TransactionsLinkedList() {
        size = 0;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new NullPointerException();
        }
        if (first == null) {
            first = new Node(transaction, null, last);
            last = new Node(null, first, null);
        } else {
            last.prev.next = new Node(transaction, last.prev, last);
            last.prev = last.prev.next;
        }
        size++;
    }

    @Override
    public void removeTransactionByID(UUID id) {
        if (id == null) {
            throw new NullPointerException();
        }

        Node pointer = first;

        while (pointer != last) {
            if (pointer.transaction.getId().equals(id)) {
                removeTransaction(pointer);
                return;
            }
            pointer = pointer.next;
        }

        throw new TransactionNotFoundException(id);
    }

    private void removeTransaction(Node node) {
        if (node == first) {
            first = node.next;
            node.prev = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] result = new Transaction[size];

        Node pointer = first;
        for (int i = 0; i < size; i++) {
            result[i] = pointer.transaction;
            pointer = pointer.next;
        }

        return result;
    }
}
