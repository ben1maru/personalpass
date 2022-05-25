package personalPass;


import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class progressMenu implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    ///////////////
    @FXML
    private TableView<DateForTable> tablePassword;
    @FXML
    private TableColumn<DateForTable, String> tableWebOrAppCol;
    @FXML
    private TableColumn<DateForTable, String> tableNameTypeCol;
    @FXML
    private TableColumn<DateForTable, String> tableLoginCol;
    @FXML
    private TableColumn<DateForTable, String> tablePasswordCol;
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
 ObservableList <DateForTable> listM;

    DateForTable selectedDateForTable = null;
    Connection conn = mySqlConnect.ConnectDb();
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    /**
     * Додавання даних в бд
     */
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
            System.out.println("add pass");
            JOptionPane.showMessageDialog(null, "Пароль доданий");
            UpdateTable();
            Clean();
        } catch (Exception e) {
            UpdateTable();

        }
    }

    /**
     * Ініціалізаці всіх методів
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typesChoiceBox.getItems().addAll(Types.getAllTypes());

        UpdateTable();
         //   search();


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

    /**
     * Оновлення і заповнення доних в таблицю TableView
     */
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

    /**
     * Оновлення даних в бд
     */
    public void Edit() {
        try {
            if (selectedDateForTable == null) {
                return;
            }
            conn = mySqlConnect.ConnectDb();
            String sql = "update data_password set website_or_app=?,login=?,password=?,name_type=? where  id_password=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, isWebOr.getText());
            pst.setString(2, isLoginTxt.getText());
            pst.setString(3, isPasswordTxt.getText());
            pst.setString(4, typesChoiceBox.getValue());
            pst.setInt(5, selectedDateForTable.id_pass.getValue());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Дані оновлено");
            UpdateTable();
            Clean();
        } catch (Exception e) {

            e.printStackTrace();
            UpdateTable();
        }
    }

    /**
     * Вибір і передавання даних в TextBox з TableView по кліку
     * @param mouseEvent по кліку миші
     */
    public void getSelect(MouseEvent mouseEvent) {
        selectedDateForTable = tablePassword.getSelectionModel().getSelectedItem();
        if (selectedDateForTable == null) {
            return;
        }
        isWebOr.setText(tableWebOrAppCol.getCellData(selectedDateForTable));
        isLoginTxt.setText(tableLoginCol.getCellData(selectedDateForTable));
        isPasswordTxt.setText(tablePasswordCol.getCellData(selectedDateForTable));
        typesChoiceBox.setValue(tableNameTypeCol.getCellData(selectedDateForTable));
    }

    /**
     * Видалення даних з бд
     */
    public void DeleteUser() {
        if (selectedDateForTable == null) {
            return;
        }
        String sql = "delete from data_password where id_password = ? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, selectedDateForTable.id_pass.getValue());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Видалено");
            UpdateTable();
            Clean();
            System.out.println("delete");
        } catch (Exception e) {
            e.printStackTrace();
            UpdateTable();
        }
    }

    /**
     * Пошук посимвольно
     */
    public void search(){
            FilteredList <DateForTable> filteredData = new FilteredList<>(listM, b -> true);
            filterField.textProperty().addListener((observable, oldValue, newValue) ->
            {
                System.out.println("search");
                filteredData.setPredicate(dateForTable -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null)
                    {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();

                    if(dateForTable.getWebsite_or_app().toLowerCase().indexOf(searchKeyword) > -1)
                    {
                        return true;
                    }
                    else if (dateForTable.getLogin().toLowerCase().indexOf(searchKeyword) > -1)
                    {
                        return true;
                    }
                    else
                        return false;
                });
            });
            SortedList<DateForTable> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tablePassword.comparatorProperty());
            tablePassword.setItems(sortedData);
        System.out.println("here");
            tablePassword.getItems().clear();
            tablePassword.getItems().addAll(listM);
        }

    /**
     * Очистка полів TextBox
     */
    public void Clean() {
        isWebOr.setText("");
        isLoginTxt.setText("");
        isPasswordTxt.setText("");
        typesChoiceBox.setValue("");
    }
}
