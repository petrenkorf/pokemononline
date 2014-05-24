/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author bruno.weig
 */
public class MainUI extends AbstractUI {
    public MainUI(Stage stage) {
        super(stage);
        
        Label labelMainMenu = new Label("Main Menu");
        labelMainMenu.setFont(new Font(20));
        
        Button buttonStartGame = new Button("Start Game");
        Button buttonQuitGame = new Button("Logout");
        Button buttonConfiguration = new Button("Configuration");
        
        VBox vboxRoot = new VBox();
        vboxRoot.setAlignment(Pos.CENTER);
        
        vboxRoot.getChildren().addAll(labelMainMenu, buttonStartGame, buttonConfiguration, buttonQuitGame);
        
        setSceneContent(vboxRoot);
        
        /*
            Eventos
        */ 
        // Iniciar jogo
        buttonStartGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeCurrentUI(new GameUI(stageRef));
            }
        });
        
        // Voltar para tela de login
        buttonQuitGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                previousUI();
            }
        });
        
        // Configuração
        buttonConfiguration.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Configuration");
                changeCurrentUI(new ConfigurationUI(stageRef));
            }
        });
    }
}
