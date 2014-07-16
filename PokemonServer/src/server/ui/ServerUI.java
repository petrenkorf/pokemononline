/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.ui;

import client.game.Player;
import db.SQLConnection;
import db.SQLQuery;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;
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
        
        checkOnlinePlayers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadPlayerList(checkOnlinePlayers.isSelected());
            }
        });
        
        listPlayers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        listPlayers.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Player>() {

            @Override
            public void changed(ObservableValue<? extends Player> observable, 
                    Player oldValue, Player newValue) {
//                System.out.println("ID " + oldValue.getId());
                System.out.println("Select");
            }
        });
    }
    
    public void loadPlayerList(boolean onlinePlayers) {
        List<Player> playerList = new ArrayList<Player>();
        Player player;
        
        ObservableList<Player> myObservableList = FXCollections.observableList(playerList);
        listPlayers.setItems(myObservableList);
        
        SQLConnection connection = SQLConnection.getInstance();
        
        if ( onlinePlayers ) {
        } else {
            try {
                connection.connect();
                
                SQLQuery query = connection.newQuery();
                query.select("*").from("users");
                
                ResultSet result = connection.execute(query);
                
                while ( result.next() ) {
                    player = new Player(result.getInt("id"), " ");
                    player.setName(result.getString("username"));
                    
                    playerList.add(player);
                }
                
                connection.disconnect();
            } catch (SQLException e) {
                System.err.println("Exception: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
        
        listPlayers.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>(){
            @Override
            public ListCell<Player> call(ListView<Player> p) {
                ListCell<Player> cell = new ListCell<Player>() {
                    @Override
                    protected void updateItem(Player p, boolean bln) {
                        super.updateItem(p, bln);
                        
                        if (p != null) {
                            setText(p.getName());
                        }
                    }
                };
                 
                return cell;
            }
        });
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
