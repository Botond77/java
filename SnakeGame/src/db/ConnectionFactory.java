package db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Botond
 */
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    private static MysqlConnectionPoolDataSource connection;
    
    private ConnectionFactory(){}
    
    public static Connection getConnection() 
        throws ClassNotFoundException, SQLException{
        if (connection == null){
            Class.forName("com.mysql.jdbc.Driver"); // Driver betöltése
            connection = new MysqlConnectionPoolDataSource();
            connection.setServerName("localhost");
            connection.setPort(3306);
            connection.setDatabaseName("sokoban");
            connection.setUser("tanulo");
            connection.setPassword("asd123");
        }
        return connection.getPooledConnection().getConnection();
    }
}