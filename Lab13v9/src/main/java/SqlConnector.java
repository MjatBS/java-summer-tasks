package main.java;

import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;

public class SqlConnector {
    private Connection connection;
    private final String URL = "jdbc:postgresql:lab13v9";
    private final String USER = "test_user";
    private final String PASSWORD = "1234";
    public SqlConnector(){
        connectDriver();
        Properties props = getSettingsForConnection();
        setConnection(props);
    }
    private void connectDriver(){
        try{
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getException());
        }
    }
    private Properties getSettingsForConnection(){
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password",PASSWORD);
        props.setProperty("ssl","false");
        return props;
    }
    private void setConnection(Properties props){
        try {
            connection = DriverManager.getConnection(URL, props);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
/*\
TABLE clock(                                                               >>
brand varchar(40),                                                         >>
type varchar(40),                                                          >>
price int,                                                                 >>
quantity int,                                                              >>
producer varchar(64))'
 */

    public List<String> getBrandsByType(String clockType) throws SQLException {
        List<String> result = new ArrayList<>();
        String query = "SELECT brand FROM clock WHERE type="+clockType;
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resultSet =  st.executeQuery();
        while (resultSet.next()) {
            String brand = resultSet.getString("brand");
            result.add(brand);
        }
        return result;
    }

    public List<String[]> getClocksWithPriceSmallerThan(int price) throws SQLException {
        List<String[]> result = new ArrayList<>();
        String query = "SELECT * FROM clock WHERE price<"+price;
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resultSet =  st.executeQuery();
        while (resultSet.next()) {
            String[] clock = new String[5];
            for(int i=0; i<5; i++){
                clock[i] = resultSet.getString(i+1);
            }
            result.add(clock);
        }
        return result;
    }

    public List<String> getBrandsByCountry(String country) throws SQLException {
        List<String> result = new ArrayList<>();
        String query = "SELECT brand FROM clock JOIN producer ON clock.producer=producer.title WHERE producer.country="+country;
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resultSet =  st.executeQuery();
        while (resultSet.next()) {
            String brand = resultSet.getString("brand");
            result.add(brand);
        }
        return result;
    }

    public List<String> getProducersWithPriceSmallerThan(int price) throws SQLException {
        List<String> result = new ArrayList<>();
        String query = "SELECT producer FROM clock GROUP BY producer HAVING SUM(price*quantity) <= " + price;
        PreparedStatement st = connection.prepareStatement(query);

        ResultSet resultSet =  st.executeQuery();
        while (resultSet.next()) {
            String producer = resultSet.getString("producer");
            result.add(producer);
        }
        return result;
    }

}
