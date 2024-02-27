public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super("User with Id " + id + " not found!");
    }
}
