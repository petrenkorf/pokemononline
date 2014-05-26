/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.communication.ClientRequest;
import client.communication.SocketClient;
import client.game.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author bruno.weig
 */
public class LoginUI extends AbstractUI {
    SocketClient s = new SocketClient();
    
    TextField inputLogin = new TextField();
    PasswordField inputPassword = new PasswordField();
    
    public LoginUI(Stage stage) {
        super(stage);
        
        setStageTitle("Testando");
        
        Label labelLogin = new Label("Login:");
        inputLogin = new TextField();
        
        Label labelPassword = new Label("Password:");
        inputPassword = new PasswordField();
        
        HBox hboxLogin = new HBox(10);
        hboxLogin.getChildren().addAll(labelLogin, inputLogin);
        hboxLogin.setAlignment(Pos.CENTER);
        
        HBox hboxPassword = new HBox(10);
        hboxPassword.getChildren().addAll(labelPassword, inputPassword);
        hboxPassword.setAlignment(Pos.CENTER);
        
        Button buttonLogin = new Button("Login");
        buttonLogin.setAlignment(Pos.TOP_RIGHT);
        
        VBox pane = new VBox();
        
        pane.getChildren().addAll(hboxLogin, hboxPassword, buttonLogin);
        
        setSceneContent(pane);
        
        // Eventos
        buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Player player = ClientRequest.getInstance().login(
                    inputLogin.getText(), inputPassword.getText()
                );
                
                if ( player != null ) {
                    System.out.println("Logged");
//                    changeCurrentUI(new MainUI(stageRef));
                } else {
                    System.out.println("Not Logged");
                }
            }
        });
    }
}
