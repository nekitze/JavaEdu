public class UserIdsGenerator {
    private static int id;
    private static UserIdsGenerator instance;

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public int generateID() {
        return ++id;
    }

    private UserIdsGenerator() {
    }
}
