import java.util.UUID;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(UUID id) {
        super("Transaction with Id " + id.toString() + " not found!");
    }
}
