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
import server.PlayerServer;
import server.PokemonSocket;
import server.view.ServerView;

/**
 *
 * @author bruno.weig
 */
public class ServerUI extends ServerView {
    SQLConnection db = null;
    Thread pokemonThread = null;
    
    PokemonSocket pokemonSocket = null;
    
    List<PlayerServer> playerOnlineList = new ArrayList<>();
    ObservableList<PlayerServer> observablePlayerList;
    
    public ServerUI() {
        setViewTitle("Server View");
        
        loadFXML();
        
        initDB();
        
        pokemonSocket = new PokemonSocket(playerOnlineList);
        pokemonThread = new Thread(pokemonSocket);
        pokemonThread.start();
        
        registerEvents();
    }

    /**
     * Registra os eventos
     */
    public void registerEvents() {
        // Mostra lista de jogadores (total ou somente online)
        checkOnlinePlayers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadPlayerList(checkOnlinePlayers.isSelected());
            }
        });
        
        listPlayers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        listPlayers.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<PlayerServer>() {
            @Override
            public void changed(ObservableValue<? extends PlayerServer> observable, 
                    PlayerServer oldValue, PlayerServer newValue) {
                if ( newValue != null ) {
                    System.out.println("Selected " + newValue.getUsername() + " (id=" +
                            newValue.getId() + ", verCod=" + newValue.getVerificationCode() + ")");
                }
            }
        });
    }
    
    /**
     * Carrega lista de jogadores
     * @param onlinePlayers 
     */
    public void loadPlayerList(boolean onlinePlayers) {
        List<PlayerServer> playerList = new ArrayList<>();
        PlayerServer player;
        
        // Somente jogadores online
        if ( onlinePlayers ) {
        } else {
            // Todos os jogadores 
            
            SQLConnection connection = SQLConnection.getInstance();
            
            try {
                connection.connect();
                
                SQLQuery query = connection.newQuery();
                query.select("*").from("users");
                
                ResultSet result = connection.execute(query);
                
                while ( result.next() ) {
                    player = new PlayerServer(result.getInt("id"), result.getString("username"), "0");
                    
                    playerList.add(player);
                }
                
                connection.disconnect();
                
                ObservableList<PlayerServer> myObservableList = FXCollections.observableList(playerList);
                listPlayers.setItems(myObservableList);
            } catch (SQLException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
        
        // Como cada item da lista é desenhado
        listPlayers.setCellFactory(new Callback<ListView<PlayerServer>, ListCell<PlayerServer>>(){
            @Override
            public ListCell<PlayerServer> call(ListView<PlayerServer> p) {
                ListCell<PlayerServer> cell = new ListCell<PlayerServer>() {
                    @Override
                    protected void updateItem(PlayerServer p, boolean empty) {
                        super.updateItem(p, empty);
                        
                        if (p != null) {
                            setText(p.getUsername());
                        }
                    }
                };
                 
                return cell;
            }
        });
    }
    
    /**
     * Inicializa o banco de dados
     */
    public void initDB() {
        db = SQLConnection.getInstance();
        
        db.setServer("localhost");
        db.setDbType(SQLConnection.SGBD.Postgre);
        db.setUsername("postgres");
        db.setPassword("5624123");
        db.setDatabase("pokemon");
    }
}
