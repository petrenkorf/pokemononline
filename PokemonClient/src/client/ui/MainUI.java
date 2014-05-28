/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author bruno.weig
 */
public class MainUI extends MainView {
    public MainUI() {
        setViewTitle("Main");
        
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
        btnQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                previousUI();
            }
        });
        
        // Configuração
        btnConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeCurrentUI(new ConfigurationUI());
            }
        });
    }
}
