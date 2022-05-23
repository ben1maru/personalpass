package personalPass;

import javafx.beans.property.*;

public class UserTable {

    public StringProperty website_or_appProperty() {
        return website_or_app;
    }

    public StringProperty loginProperty() {
        return login;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty nameTypeProperty() {
        return name_type;
    }

    public String getWebsite_or_app() {
        return website_or_app.get();
    }

    public void setWebsite_or_app(String website_or_app) {
        this.website_or_app.set(website_or_app);
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getName_type() {
        return name_type.get();
    }

    public StringProperty name_typeProperty() {
        return name_type;
    }

    public int getId_pass() {
        return id_pass.get();
    }

    public IntegerProperty id_passProperty() {
        return id_pass;
    }

    public void setId_pass(int id_pass) {
        this.id_pass.set(id_pass);
    }

    public int getId_user() {
        return id_user.get();
    }

    public IntegerProperty id_userProperty() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user.set(id_user);
    }

    public StringProperty website_or_app = new SimpleStringProperty();
    public StringProperty login = new SimpleStringProperty();
    public StringProperty password = new SimpleStringProperty();
    public IntegerProperty id_pass = new SimpleIntegerProperty();
    public IntegerProperty id_user = new SimpleIntegerProperty();
    public StringProperty name_type = new SimpleStringProperty();

    public void setName_type(String name_type) {
        this.name_type.set(name_type);
    }

    public UserTable(int id_pass, int id_user, String website_or_app, String login, String name_type, String password) {
        this.id_pass.setValue(id_pass);
        this.id_user.setValue(id_user);
        this.website_or_app.setValue(website_or_app);
        this.login.setValue(login);
        this.name_type.setValue(name_type);
        this.password.setValue(password);

    }



}
