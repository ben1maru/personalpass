package personalPass;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Заповнення даних про тип паролів
 */
public class Types {

    /**
     * Заповнення ліста з таблиці тип
     * @return
     */
    public static List<String> getAllTypes() {
        List<String> types = new ArrayList<>();
        String sqlStatement = "SELECT * FROM type_pass";
        try {
            ResultSet resultSet = new DatabaseHandler().getDbConnection().prepareStatement(sqlStatement).executeQuery();
            while (resultSet.next()) {
                types.add(resultSet.getString("type_pass"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return types;
    }
}
