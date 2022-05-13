package personalPass;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

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
    @FXML
    private Button isBackBtn;

    @FXML
    private Button addPasswordBtn;

    @FXML
    private Button isUpdateBtn;

    @FXML
    private Button isDeleteBtn;

    @FXML
    private TextField isWebOr;

    @FXML
    private TextField isLoginTxt;

    @FXML
    private TextField isPasswordTxt;



    //////////////////
    List<UserTable> listM;
    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    private void addPass(){
        conn=mySqlConnect.ConnectDb();
        String sql = "insert into data_password (id_user,website_or_app,login,password)values(?,?,?,?)";
        try{
            pst= conn.prepareStatement(sql);
            pst.setInt(1, Const.user.getUserId());
           pst.setString(2,isWebOr.getText());
            pst.setString(3,isLoginTxt.getText());
            pst.setString(4,isPasswordTxt.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null,"Пароль доданий");
            UpdateTable();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();
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
            try{
                loader.load();
            }catch (IOException e){
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
        });

    }
    public void UpdateTable(){
        tableWebOrAppCol.setCellValueFactory(param -> param.getValue().website_or_appProperty());
        tableLoginCol.setCellValueFactory(param -> param.getValue().loginProperty());
        tablePasswordCol.setCellValueFactory(param -> param.getValue().passwordProperty());
        listM =mySqlConnect.getDataUsersTable();
        tablePassword.getItems().clear();
        tablePassword.getItems().addAll(listM);
        ////////////////////////////
    }
public void Edit(){
        try {
            conn= mySqlConnect.ConnectDb();
            String value1 = isWebOr.getText();
            String value2 = isLoginTxt.getText();
            String value3 = isPasswordTxt.getText();

            String sql = "update data_password set website_or_app= '"+ value1 +"',login= '"+value2+"',password= '"+ value3+"' where  website_or_app= '"+ value1 +"' ";
            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null,"Дані оновлено");
            UpdateTable();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
}
    public void getSelect(javafx.scene.input.MouseEvent mouseEvent) {
        index = tablePassword.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        isWebOr.setText(tableWebOrAppCol.getCellData(index));
        isLoginTxt.setText(tableLoginCol.getCellData(index));
        isPasswordTxt.setText(tablePasswordCol.getCellData(index));
        typesChoiceBox.setValue(tableNameTypeCol.getCellData(index));

    }
    public void DeleteUser(){
        conn=mySqlConnect.ConnectDb();
        String sql = "delete from data_password where website_or_app = ? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1,isWebOr.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null,"Видалено");
            UpdateTable();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    public void Clean() {
        isWebOr.setText("");
        isLoginTxt.setText("");
        isPasswordTxt.setText("");
        typesChoiceBox.setValue("");
    }
}
