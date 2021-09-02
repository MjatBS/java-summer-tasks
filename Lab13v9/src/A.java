import org.postgresql.Driver;

import java.sql.*;
import java.util.Properties;

public class A {
    public static void main(String[] args) throws SQLException {
        try{
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            System.out.println("HaHa");
        }
        String url = "jdbc:postgresql:lab13v9";
        Properties props = new Properties();
        props.setProperty("user","test_user");
        props.setProperty("password","1234");
        props.setProperty("ssl","false");
        Connection conn = DriverManager.getConnection(url, props);

        Statement st = conn.createStatement();

       // System.out.println(st.execute("INSERT INTO producer VALUES('NoGo', 'NoRussia')"));
        ResultSet selAll = st.executeQuery("SELECT * FROM producer");
        while (selAll.next()) {
            String title = selAll.getString("title");
            String country = selAll.getString("country");
            System.out.println(title + " " + country);
        }
    }
}
