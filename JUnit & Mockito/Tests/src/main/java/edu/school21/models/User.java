package edu.school21.models;

public class User {
    private Long id;
    private String login;
    private String password;
    private boolean isAuthenticated;

    public User() {}

    public User(Long id, String login, String password, boolean isAuthenticated) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.isAuthenticated = isAuthenticated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isAuthenticated != user.isAuthenticated) return false;
        if (!id.equals(user.id)) return false;
        if (!login.equals(user.login)) return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (isAuthenticated ? 1 : 0);
        return result;
    }
}
