package personalPass;

import java.sql.*;
import org.apache.commons.codec.digest.DigestUtils;



public class DatabaseHandler {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/personal_password_data?useUnicode=true&characterEncoding=utf8","root", "");
        System.out.println(dbConnection);
        return dbConnection;

    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.PPM_TABLE + "(" +
                Const.PPM_USERNAME + "," + Const.PPM_LOGIN + "," +
                Const.PPM_PASSWORD + ")" + "VALUES(?,?,?)";


        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getNameUser());
            prSt.setString(2, user.getLogin());
            prSt.setString(3, DigestUtils.sha1Hex(user.getPassword()));
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String login, String password) {
        User user = null;
        String hashedPassword = DigestUtils.sha1Hex(password);
        String sqlStatement = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?", Const.PPM_TABLE, Const.PPM_LOGIN, Const.PPM_PASSWORD);

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(sqlStatement);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, hashedPassword);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int userId = resultSet.getInt("id_user");
                String userName = resultSet.getString("username");
                String loginFromDatabase = resultSet.getString("login");
                String passwordFromDatabase = resultSet.getString("password");
                user = new User(userId, userName, loginFromDatabase, passwordFromDatabase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }
}




