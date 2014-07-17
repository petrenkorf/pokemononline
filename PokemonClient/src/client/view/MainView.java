/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.view;

import client.ui.AbstractUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author bruno.weig
 */
//public class LoginView extends AbstractUI  {
public class MainView extends AbstractUI implements Initializable {
    @FXML
    protected Button btnLogout;
    
    @FXML
    protected Button btnStartGame;
    
    @FXML
    protected Button btnConfig;
    
    public MainView() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
