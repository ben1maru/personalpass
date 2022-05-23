package personalPass;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;

public class progressMenu implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    ///////////////
    @FXML
    private TableView<UserTable> tablePassword;
    @FXML
    private TableColumn<UserTable, String> tableWebOrAppCol;
    @FXML
    private TableColumn<UserTable, String> tableNameTypeCol;
    @FXML
    private TableColumn<UserTable, String> tableLoginCol;
    @FXML
    private TableColumn<UserTable, String> tablePasswordCol;
    ////////////
    @FXML
    private Button isBackBtn;
    @FXML
    private Button addPasswordBtn;
    @FXML
    private ChoiceBox<String> typesChoiceBox;
    @FXML
    private Button isUpdateBtn;
    @FXML
    private Button isDeleteBtn;
    //////////
    @FXML
    private TextField isWebOr;
    @FXML
    private TextField isLoginTxt;
    @FXML
    private TextField filterField;
    @FXML
    private TextField isPasswordTxt;
    @FXML
    private Button isBtnSearch;
    @FXML
    private Button addInfoForUser;
    //////////////////
  List<UserTable> listM;

    UserTable selectedUserTable = null;
    Connection conn = mySqlConnect.ConnectDb();
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    private void addPass() {
        if (isWebOr.getText().equals("") ||
                isLoginTxt.getText().equals("") ||
                isPasswordTxt.getText().equals("") ||
                typesChoiceBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ви не ввели всі потрібні дані");
            alert.show();
            return;
        }

        conn = mySqlConnect.ConnectDb();
        String sql = "insert into data_password (id_user,website_or_app,login,password, name_type)values(?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Const.user.getUserId());
            pst.setString(2, isWebOr.getText());
            pst.setString(3, isLoginTxt.getText());
            pst.setString(4, isPasswordTxt.getText());
            pst.setString(5, typesChoiceBox.getValue());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Пароль доданий");
            UpdateTable();
            Clean();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typesChoiceBox.getItems().addAll(Types.getAllTypes());
        UpdateTable();
        addInfoForUser.setOnAction(event -> {
            Stage stage = (Stage) addInfoForUser.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addInfoUser.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            System.out.println("login");
        });
        isBtnSearch.setOnAction(event -> {
            search(filterField.getText());
        });
        addPasswordBtn.setOnAction(actionEvent -> {
            addPass();
        });
        isUpdateBtn.setOnAction(actionEvent -> {
            Edit();
        });
        isDeleteBtn.setOnAction(actionEvent -> {
            DeleteUser();
        });
        isBackBtn.setOnAction(actionEvent -> {
            Stage stage = (Stage) isBackBtn.getScene().getWindow();
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

    public void UpdateTable() {
        tableWebOrAppCol.setCellValueFactory(param -> param.getValue().website_or_appProperty());
        tableLoginCol.setCellValueFactory(param -> param.getValue().loginProperty());
        tablePasswordCol.setCellValueFactory(param -> param.getValue().passwordProperty());
        tableNameTypeCol.setCellValueFactory(param -> param.getValue().nameTypeProperty());
        listM = mySqlConnect.getDataUsersTable();
        tablePassword.getItems().clear();
        tablePassword.getItems().addAll(listM);
        ////////////////////////////
    }
    public void Edit() {
        try {
            if (selectedUserTable == null) {
                return;
            }
            conn = mySqlConnect.ConnectDb();
            String sql = "update data_password set website_or_app=?,login=?,password=?,name_type=? where  id_password=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, isWebOr.getText());
            pst.setString(2, isLoginTxt.getText());
            pst.setString(3, isPasswordTxt.getText());
            pst.setString(4, typesChoiceBox.getValue());
            pst.setInt(5, selectedUserTable.id_pass.getValue());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Дані оновлено");
            UpdateTable();

            Clean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSelect(MouseEvent mouseEvent) {
        selectedUserTable = tablePassword.getSelectionModel().getSelectedItem();
        if (selectedUserTable == null) {
            return;
        }
        isWebOr.setText(tableWebOrAppCol.getCellData(selectedUserTable));
        isLoginTxt.setText(tableLoginCol.getCellData(selectedUserTable));
        isPasswordTxt.setText(tablePasswordCol.getCellData(selectedUserTable));
        typesChoiceBox.setValue(tableNameTypeCol.getCellData(selectedUserTable));
    }

    public void DeleteUser() {
        if (selectedUserTable == null) {
            return;
        }
        String sql = "delete from data_password where id_password = ? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, selectedUserTable.id_pass.getValue());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Видалено");
            UpdateTable();

            Clean();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
    }
       public void search(String text) {
           listM = mySqlConnect.getDataUsersTable();
           for (UserTable u:listM){
               if(u.getWebsite_or_app().equals(text)){
                   tablePassword.getItems().clear();
                   tablePassword.getItems().add(u);
               }else
                   UpdateTable();
           }
   }

    public void Clean() {
        isWebOr.setText("");
        isLoginTxt.setText("");
        isPasswordTxt.setText("");
        typesChoiceBox.setValue("");
    }
}
