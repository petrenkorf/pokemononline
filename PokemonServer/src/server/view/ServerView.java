/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.view;

import client.ui.AbstractUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author bruno.weig
 */
//public class LoginView extends AbstractUI  {
public class ServerView extends AbstractUI implements Initializable {
    @FXML
    protected TextField inputUsername;

    @FXML
    protected TableView tablePlayers;
    
    @FXML
    protected CheckBox checkOnlinePlayers;
    
    public ServerView() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
