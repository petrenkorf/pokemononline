/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.communication.SocketClient;
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
        setViewTitle("Testando");
        
        loadFXML();
        
        registerEvents();
    }

    public void registerEvents() {
        // Login
        buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeCurrentUI(new MainUI());
                
//                Player player = ClientRequest.getInstance().login(
//                    inputUsername.getText(), inputPassword.getText()
//                );
//                
//                if ( player != null ) {
//                    System.out.println("Logged");
//                    
//                    changeCurrentUI(new MainUI());
//                } else {
//                    System.out.println("Not Logged");
//                }
            }
        });
    }
}
