/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.view.ConfigView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author bruno.weig
 */
public class ConfigUI extends ConfigView {
    public ConfigUI() {
        setViewTitle("Configuration");
        
        loadFXML();
        
        /*
         *  EVENTOS 
         */
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                previousUI();
            }
        });
    }
}
