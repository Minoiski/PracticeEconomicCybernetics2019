package models;

public class User {
    private final int id;
    private final String login;
    private final String email;

    public User(final int id, final String login, final String email) {
        this.id = id;
        this.login = login;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return this.login;
    }

    public String getEmail() {
        return this.email;
    }
}
