package cz.DBProjekt.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCreation {
    private static Connection conn = DBConnection.connect();
    public static boolean createTable() {
        if(conn == null) {
            return false;
        }
        String sql = "CREATE TABLE IF NOT EXISTS knihy (" + "nazev varchar(255) PRIMARY KEY," + "autor varchar(255) NOT NULL," + "rok_vydani integer NOT NULL," + "stav varchar(12) NOT NULL," + "zanr varchar(255)," + "pro_rocnik integer" + ");";
        try {
            Statement sttmnt = conn.createStatement();
            sttmnt.execute(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        if(createTable()) {
            System.out.println("Databaze byla uspesne vytvorena!");
        }
        else {
            System.out.println("Databazi se nepodarilo vytvorit!");
        }
    }


}
