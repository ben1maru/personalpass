package personalPass;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AddInfoUser implements Initializable {
    @FXML
    private TextField PIBtxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField numberPhoneTxt;

    @FXML
    private Button addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addBtn.setOnAction(event -> {
            addInfo();
        });
    }
    ResultSet rs = null;
    PreparedStatement pst = null;
    Connection connection = mySqlConnect.ConnectDb();

    public void addInfo(){
        try {
            String sql = "insert into info_user (id_user,PIB,address,number_phone, name_type)values(?,?,?,?)";
            pst = connection.prepareStatement(sql);
            pst.setInt(1,Const.user.getUserId());
            pst.setString(2, PIBtxt.getText());
            pst.setString(3,addressTxt.getText());
            pst.setString(4, numberPhoneTxt.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Успіх");
            alert.setContentText("Дані було успішно додано");
            alert.show();
            Stage stage = (Stage) addBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PassworTable.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Похибка");
            alert.setContentText("Дані було успішно додано");
            alert.show();
        }

    }
}
