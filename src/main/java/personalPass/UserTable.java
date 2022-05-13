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
    public IntegerProperty id_pass=new SimpleIntegerProperty();
    public IntegerProperty id_user = new SimpleIntegerProperty();

    public UserTable(int id_pass,int id_user,String website_or_app, String login, String password) {

        this.id_pass.setValue(id_pass);
        this.id_user.setValue(id_user);
        this.website_or_app.setValue(website_or_app);
        this.login.setValue(login);
        this.password.setValue(password);
    }


}
