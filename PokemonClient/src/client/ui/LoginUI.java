/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.communication.ClientRequest;
import client.communication.SocketClient;
import client.game.Player;
import static client.ui.AbstractUI.changeCurrentUI;
import client.view.LoginView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author bruno.weig
 */
public class LoginUI extends LoginView {
    SocketClient s = new SocketClient();
    
    public LoginUI() {
        setViewTitle("Login");
        
        loadFXML();
        
        registerEvents();
    }

    public void registerEvents() {
        // Login
        buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                changeCurrentUI(new MainUI());
                
                Player player = ClientRequest.getInstance().login(
                    inputUsername.getText(), inputPassword.getText()
                );
                
                if ( player != null ) {
                    labelLoginMessage.setText("");
                    
                    changeCurrentUI(new MainUI());
                } else {
                    labelLoginMessage.setText("Invalid username or password!");
                    System.out.println("Not Logged");
                }
            }
        });
    }
}
