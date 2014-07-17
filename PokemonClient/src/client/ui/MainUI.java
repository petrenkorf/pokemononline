/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.communication.ClientRequest;
import client.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author bruno.weig
 */
public class MainUI extends MainView {
    public MainUI() {
        setViewTitle("Main Menu");
        
        loadFXML();
        
        registerEvents();
    }
    
    public void registerEvents() {
        // Iniciar jogo
        btnStartGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeCurrentUI(new GameUI());
            }
        });
        
        // Voltar para tela de login
        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if ( ClientRequest.getInstance().logout() ) {
                    previousUI();
                } else {
                    System.out.println("Problem to leave game!");
                }
            }
        });
        
        // Configuração
        btnConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeCurrentUI(new ConfigUI());
            }
        });
    }
}
