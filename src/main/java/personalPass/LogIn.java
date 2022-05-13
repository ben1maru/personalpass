package personalPass;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogIn {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginIn;

    @FXML
    private PasswordField passTxt;

    @FXML
    private TextField logtxt;

    @FXML
    private Button registration;

    @FXML
    void initialize() {
        loginIn.setOnAction(event -> {
            String loginText = logtxt.getText().trim();
            String loginPassword = passTxt.getText().trim();

            if (!loginText.equals("") && !loginPassword.equals(""))
                loginUser(loginText, loginPassword);
            else
                System.out.println("login is empty ");
        });

        registration.setOnAction(event -> {
            Stage stage = (Stage) registration.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("windowReg.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
        });
    }

    public void loginUser(String loginText, String passwordTxt) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User userFromDatabase = dbHandler.getUser(loginText, passwordTxt);
        if (userFromDatabase != null) {
            Const.user = userFromDatabase;
            Stage stage = (Stage) loginIn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("progressMenu.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            System.out.println("login");
        }
    }

}

