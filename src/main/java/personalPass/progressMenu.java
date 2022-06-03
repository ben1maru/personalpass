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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
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
    @FXML
    private TableColumn<DateForTable, String> tableCategoryCol;
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
    private ChoiceBox<String> choiceCategory;
    //////////
    @FXML
    private AnchorPane panelForm;
    @FXML
    private AnchorPane panelTable;
    @FXML
    private AnchorPane panelBtn;
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
    public void changeColorPeach(){
        panelForm.setStyle("-fx-background-color: #FBE7B5");
        panelTable.setStyle("-fx-background-color: #F85C50;-fx-background-radius: 10;-fx-border-radius: 10;-fx-border-color: black");
        panelBtn.setStyle("-fx-background-color: #FE9E76;-fx-background-radius: 10;-fx-border-radius: 10;-fx-border-color: black");
    }
    public void changeColorViolet(){
        panelForm.setStyle("-fx-background-color: #FFBEED");
        panelTable.setStyle("-fx-background-color: #F375F3;-fx-background-radius: 10;-fx-border-radius: 10;-fx-border-color: black");
        panelBtn.setStyle("-fx-background-color: #7C3668;-fx-background-radius: 10;-fx-border-radius: 10;-fx-border-color: black");
    }
    public void changeColorRed(){
        panelForm.setStyle("-fx-background-color: #EE3D48");
        panelTable.setStyle("-fx-background-color: #BC0022;-fx-background-radius: 10;-fx-border-radius: 10;-fx-border-color: black");
        panelBtn.setStyle("-fx-background-color: #B40A1B;-fx-background-radius: 10;-fx-border-radius: 10;-fx-border-color: black");
    }
    public void changeColorGreen(){
        panelForm.setStyle("-fx-background-color: #5BFF62");
        panelTable.setStyle("-fx-background-color: #116315;-fx-background-radius: 10;-fx-border-radius: 10;-fx-border-color: black");
        panelBtn.setStyle("-fx-background-color: #4D8802;-fx-background-radius: 10;-fx-border-radius: 10;-fx-border-color: black");
    }
    public void changeColorYellow(){
        panelForm.setStyle("-fx-background-color: #FFFCBB");
        panelTable.setStyle("-fx-background-color: #F5E027;-fx-background-radius: 10;-fx-border-radius: 10;-fx-border-color: black");
        panelBtn.setStyle("-fx-background-color: #D2AA1B;-fx-background-radius: 10;-fx-border-radius: 10;-fx-border-color: black");
    }


    private void addPass() {
        if (isWebOr.getText().equals("") ||
                isLoginTxt.getText().equals("") ||
                isPasswordTxt.getText().equals("") ||
                typesChoiceBox.getValue() == null|| choiceCategory.getValue() ==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ви не ввели всі потрібні дані");
            alert.show();
            return;
        }

        conn = mySqlConnect.ConnectDb();
        String sql = "insert into data_password (id_user,website_or_app,login,password, name_type,name_applications)values(?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Const.user.getUserId());
            pst.setString(2, isWebOr.getText());
            pst.setString(3, isLoginTxt.getText());
            pst.setString(4, isPasswordTxt.getText());
            pst.setString(5, typesChoiceBox.getValue());
            pst.setString(6,choiceCategory.getValue());
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
        choiceCategory.getItems().addAll(Application.getAllApplication());
        UpdateTable();
         //   search();



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
        tableCategoryCol.setCellValueFactory(param->param.getValue().name_applicationProperty());
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
            if (isWebOr.getText().equals("") ||
                    isLoginTxt.getText().equals("") ||
                    isPasswordTxt.getText().equals("") ||
                    typesChoiceBox.getValue() == null|| choiceCategory.getValue() ==null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ви не ввели всі потрібні дані");
                alert.show();
                return;
            }
            conn = mySqlConnect.ConnectDb();
            String sql = "update data_password set website_or_app=?,login=?,password=?,name_type=?, name_applications=? where  id_password=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, isWebOr.getText());
            pst.setString(2, isLoginTxt.getText());
            pst.setString(3, isPasswordTxt.getText());
            pst.setString(4, typesChoiceBox.getValue());
            pst.setString(5, choiceCategory.getValue());
            pst.setInt(6, selectedDateForTable.id_pass.getValue());
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
        choiceCategory.setValue(tableCategoryCol.getCellData(selectedDateForTable));
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ви дійсно хочете видалити пароль");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK){
                pst.setInt(1, selectedDateForTable.id_pass.getValue());
            pst.execute();
                Alert alerts = new Alert(Alert.AlertType.INFORMATION);
                alerts.setContentText("Ви видалили пароль");
                alerts.showAndWait();
            UpdateTable();
            Clean();
            System.out.println("delete");
        }else if(option.get()==ButtonType.CANCEL){
                System.out.println("no delete");
            }
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
                    }       else if (dateForTable.getName_application().toLowerCase().indexOf(searchKeyword) > -1)
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
