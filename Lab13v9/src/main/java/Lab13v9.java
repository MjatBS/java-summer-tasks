package main.java;

import java.sql.SQLException;

public class Lab13v9 {
    public static void main(String[] args) {
        SqlConnector connector = new SqlConnector();
        try {
            System.out.println(connector.getBrandsByType("mech"));
            System.out.println(connector.getBrandsByCountry("USA"));
            System.out.println(connector.getClocksWithPriceSmallerThan(125));
            System.out.println(connector.getProducersWithPriceSmallerThan(1000));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
