/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.util.Display;
import java.awt.DisplayMode;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author bruno.weig
 */
public class ConfigurationUI extends AbstractUI {
    Display d = Display.getInstance();
    
    public ConfigurationUI() {
        VBox vboxRoot = new VBox();
        vboxRoot.setAlignment(Pos.CENTER);
        
        // Resolução
        HBox hboxResolution = new HBox();
        Label labelResolution = new Label("Resolution:");
        
        DisplayMode[] resolutions = d.getResolutions();

        List<String> resolutionlist = new ArrayList<String>();
        
        for (int i=0; i < resolutions.length; i++)
           resolutionlist.add(resolutions[i].getWidth() + "x" + resolutions[i].getHeight());
        
        ObservableList<String> observableResolutions = FXCollections.observableArrayList(resolutionlist);
        ChoiceBox<String> choiceResolution = new ChoiceBox<String>(observableResolutions);
                
        hboxResolution.getChildren().addAll(labelResolution, choiceResolution);
        
        // Retorno
        HBox hboxReturn = new HBox();
        
        Button buttonReturn = new Button("Return");
        buttonReturn.setAlignment(Pos.BOTTOM_RIGHT);
        
        hboxReturn.getChildren().add(buttonReturn);
                
        vboxRoot.getChildren().addAll(hboxResolution, hboxReturn);
    
        setSceneContent(vboxRoot);
        
        /*
         *  EVENTOS 
         */
        buttonReturn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                previousUI();
            }
        });
    }
}
