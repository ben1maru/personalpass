package personalPass;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Types {


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
