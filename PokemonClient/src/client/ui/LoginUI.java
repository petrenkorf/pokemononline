/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author bruno.weig
 */
public class LoginUI extends AbstractUI {
    public LoginUI(Stage stage) {
        super(stage);
        
        System.out.println("Son");
        
        setStageTitle("Testando");
        
        Label labelLogin = new Label("Login:");
        TextField inputLogin = new TextField();
        
        Label labelPassword = new Label("Password:");
        PasswordField inputPassword = new PasswordField();
        
        HBox hboxLogin = new HBox(10);
        hboxLogin.getChildren().addAll(labelLogin, inputLogin);
        hboxLogin.setAlignment(Pos.CENTER);
        
        HBox hboxPassword = new HBox(10);
        hboxPassword.getChildren().addAll(labelPassword, inputPassword);
        hboxPassword.setAlignment(Pos.CENTER);
        
        Button buttonLogin = new Button("Login");
        buttonLogin.setAlignment(Pos.TOP_RIGHT);
        
        VBox pane = new VBox();
        
        pane.setMaxWidth(300);
        
        pane.getChildren().addAll(hboxLogin, hboxPassword, buttonLogin);
        
        setSceneContent(pane);
    }
}
