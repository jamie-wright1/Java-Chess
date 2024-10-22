package chess;

import java.sql.*;
import java.util.Properties;

public class SQLConnections {
    public void setUp() throws Exception {
        //connect info
        String url = "jdbc:postgresql://cps-postgresql.gonzaga.edu/jwright9_db";
        Properties props = new Properties();
        props.setProperty("user", "jwright9");
        props.setProperty("password", "");
        props.setProperty("database", "jwright9_db");

        //connect
        Connection cn = DriverManager.getConnection(url, props);

        //Create a (non-prepared) statement
        Statement st = cn.createStatement();
        String q = "SELECT * FROM course";

        ResultSet rs = st.executeQuery(q);
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println(id + ", " + name);
        }
        //close
        rs.close();
        st.close();
        cn.close();
    }

}
