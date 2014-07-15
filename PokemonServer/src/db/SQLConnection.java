package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author bruno.weig
 */
public class SQLConnection {
    public enum SGBD {
        Postgre
    }
    
    SGBD dbType;
    String dbTypeString;
    String server = "localhost";
    int port;
    String database;

    String user;
    String password;
    
    static SQLConnection sqlConnection = null;
    Connection con = null;
    
    private SQLConnection() {
    }
    
    static public SQLConnection getInstance() {
        if ( sqlConnection == null )
            sqlConnection = new SQLConnection();
        
        return sqlConnection;
    }
    
    public SQLQuery newQuery() {
        return SQLQueryFactory.factoryQuery(SGBD.Postgre);
    }
    
    public void connect() throws SQLException {
        String url = "jdbc:" + dbTypeString + "://" + server + ":" + port + "/" + database;
        
        System.out.println("URL: " + url);
        
        con = DriverManager.getConnection(url, user, password);
    }
    
    public ResultSet execute(SQLQuery transaction) throws SQLException {
        return con.createStatement().executeQuery(transaction.getQuery());
    }
    
    public void disconnect() throws SQLException {
        if ( !con.isClosed() )
            con.close();
    }
    
    /*
    Getters and Setters
     */
    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
    
    public SGBD getDbType() {
        return dbType;
    }

    public void setDbType(SGBD dbType) {
        this.dbType = dbType;
        
        switch (dbType) {
            case Postgre:
                dbTypeString = "postgresql";
                port = 5432;
                break;
            default:
        }
    }

    public String getUsername() {
        return user;
    }

    public void setUsername(String username) {
        this.user = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
