package personalPass;

public class User {
    private int userId;
    private String nameUser;
    private String password;
    private String  login;

    public User(int userId, String nameUser, String login, String password) {
        this.userId = userId;
        this.nameUser = nameUser;
        this.password = password;
        this.login = login;
    }
    public User (String nameUser, String login, String password) {
        this.nameUser = nameUser;
        this.password = password;
        this.login = login;
    }

    public User() {}

    public String getNameUser() {
        return nameUser;
    }

    public int getUserId() {
        return userId;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
