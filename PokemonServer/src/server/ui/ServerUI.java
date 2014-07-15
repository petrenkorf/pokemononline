/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.ui;

import db.SQLConnection;
import db.SQLQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import server.PokemonSocket;
import server.view.ServerView;

/**
 *
 * @author bruno.weig
 */
public class ServerUI extends ServerView {
    SQLConnection db = null;
    Thread t = null;
    
    PokemonSocket s = null;
    
    public ServerUI() {
        setViewTitle("Server View");
        
        loadFXML();
        
        registerEvents();
    }

    public void registerEvents() {
        initDB();
//        test();
        
        s = new PokemonSocket();
        t = new Thread(s);
        t.start();
        
        
    }
    
    public void initDB() {
        db = SQLConnection.getInstance();
        
        db.setServer("localhost");
        db.setDbType(SQLConnection.SGBD.Postgre);
        db.setUsername("postgres");
        db.setPassword("5624123");
        db.setDatabase("pokemon");
    }
    
    public void test() {
        SQLQuery query;
        
        try {
            db.connect();
            
            query = db.newQuery();
            
            query.select("*").from("users");
            ResultSet rs = db.execute(query);
            
            while ( rs.next() ) {
                System.out.println(rs.getInt("id") + ", " + rs.getString("username"));
            }
            
            System.out.println("Database connected: " + rs.getRow() + 1 + " results!");
            
            rs.close();
            
            db.disconnect();
        } catch (SQLException e) {
            System.err.println("SQL Problem: " + e.getMessage());
        }
    }
}
