package othertest;

import java.sql.*;

/**
 * @author yanchao
 * @date 2018/5/9 22:09
 */
public class HiveJdbcTest {

    private static String driverClassName = "org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://192.168.99.236:10000/default";

    public static void main(String[] args) {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try (Connection connection = DriverManager.getConnection(url, "root", "root");
                Statement statement = connection.createStatement()) {
            String sql = "show databases";
            statement.executeQuery(sql);
            ResultSet resultSet = null;
            try {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                }
                sql = "show tables";
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                }
            } finally {
                if (resultSet!= null) {
                    resultSet.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
