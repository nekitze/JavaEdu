
public class UsersArrayList implements UsersList {
    private static final int DEFAULT_CAPACITY = 10;
    private User[] usersData;
    private int size;

    public UsersArrayList() {
        usersData = new User[DEFAULT_CAPACITY];
    }

    @Override
    public void addUser(User user) {
        if (user == null) {
            throw new NullPointerException();
        }

        size++;

        if (size > usersData.length) {
            grow();
        }

        for (int i = 0; ; i++) {
            if (usersData[i] == null) {
                usersData[i] = user;
                break;
            }
        }
    }

    @Override
    public User getUserById(int id) {
        for (User user : usersData) {
            if (user != null && user.getId() == id) {
                return user;
            }
        }
        throw new UserNotFoundException(id);
    }

    @Override
    public User getUserByIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new ArrayIndexOutOfBoundsException(index);
        }

        return usersData[index];
    }

    @Override
    public int getNumberOfUsers() {
        return size;
    }

    private void grow() {
        int growSize = usersData.length * 2;
        User[] newUsersData = new User[growSize];

        System.arraycopy(usersData,
                0,
                newUsersData,
                0,
                usersData.length);

        usersData = newUsersData;
    }
}
