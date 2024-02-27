import java.util.UUID;

public class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException() {
        super("Illegal transaction! Not enough funds on the balance!");
    }

}
