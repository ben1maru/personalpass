package personalPass;

import javafx.fxml.FXML;
import java.util.Arrays;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.stage.Stage;

import javax.swing.*;

public class windowReg {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUp;

    @FXML
    private PasswordField returnPassword;

    @FXML
    private TextField userName;

    @FXML
    private Button isBack;
    @FXML
    void initialize() {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        signUp.setOnAction(event -> {
            signUpNewUser();
        });
        isBack.setOnAction(event-> {
            Stage stage = (Stage) isBack.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LogIn.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
        });
    }

    private void signUpNewUser () {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        String userNameReg = userName.getText();
        String passwordReg = password.getText();
        String loginReg = login.getText();
        if (!password.getText().equals(returnPassword.getText())) {
            JOptionPane.showMessageDialog(null, "Паролі не співпадають");
            return;
        } else {
            ArrayList<String> arrList = new ArrayList<String>();
            arrList.addAll(Arrays.asList(userNameReg, passwordReg, loginReg));

            if (arrList.stream().anyMatch(text -> text.equals(""))) {
                JOptionPane.showMessageDialog(null, "Введіть всі значення");
                return;
            }
            User user = new User(userNameReg, loginReg, passwordReg);
            databaseHandler.signUpUser(user);
            JOptionPane.showMessageDialog(null,"Користувача зареєстровано!");
            userName.setText("");
            password.setText("");
            returnPassword.setText("");
            login.setText("");
            Stage stage = (Stage) signUp.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LogIn.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
        }
    }
}
