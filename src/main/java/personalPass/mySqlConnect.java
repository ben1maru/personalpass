package personalPass;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class mySqlConnect {
    Connection conn = null;

    /**
     * конект до бд
     * @return
     */
    public static Connection ConnectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/personal_password_data?useUnicode=true&characterEncoding=utf8", "root", "");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    /**
     * заповнення TableView
     * @return
     */
    public static ObservableList<DateForTable> getDataUsersTable() {
        Connection conn = ConnectDb();
       ObservableList<DateForTable> list = FXCollections.observableArrayList();
        try {
            String statement = "SELECT id_password, id_user, website_or_app, login,password,name_type FROM `data_password` WHERE id_user = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, Const.user.getUserId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("add to table");
                list.add(
                        new DateForTable(
                                Integer.parseInt(resultSet.getString("id_password")),
                                Integer.parseInt(resultSet.getString("id_user")),
                                resultSet.getString("website_or_app"),
                                resultSet.getString("login"),
                                resultSet.getString("name_type"),
                                resultSet.getString("password")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Const.user.getUserId());
        System.out.println(list.size());
        return list;
    }
}
