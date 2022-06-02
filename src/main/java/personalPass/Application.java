package personalPass;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Application {
    /**
     * Заповнення ліста з таблиці тип
     * @return
     */
    public static List<String> getAllApplication() {
        List<String> types = new ArrayList<>();
        String sqlStatement = "SELECT * FROM applications";
        try {
            ResultSet resultSet = new DatabaseHandler().getDbConnection().prepareStatement(sqlStatement).executeQuery();
            while (resultSet.next()) {
                types.add(resultSet.getString("name_application"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return types;
    }
}
