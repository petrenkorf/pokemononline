/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.view;

import client.ui.AbstractUI;
import client.util.Display;
import java.awt.DisplayMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author bruno.weig
 */
public class ConfigView extends AbstractUI implements Initializable {
    @FXML
    protected ChoiceBox<String> comboResolution;
    
    @FXML
    protected ChoiceBox<String> comboFullscreen;
    
    @FXML
    protected Button btnSave;
    
    @FXML
    protected Button btnCancel;
    
    public ConfigView() {
        
    }
    
    class ConfigResolution {
        int id;
        String value;
        
        public ConfigResolution(int id, String value) {
            this.id = id;
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    } 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> fullscreenValues = FXCollections.observableArrayList(
            "Windowed",
            "Fullscreen"
        );
        
        Display d = Display.getInstance();
        DisplayMode[] resolution = d.getResolutions();
        
        ObservableList<String> resolutionValues = FXCollections.observableArrayList();
        
        for (int i=0; i < resolution.length; i++) {
            resolutionValues.add(resolution[i].getWidth() + "x" + resolution[i].getHeight() + 
                    " (" + (double)resolution[i].getWidth() / (double)resolution[i].getHeight() + ")");
        }
        
        comboFullscreen.setItems(fullscreenValues);
        comboResolution.setItems(resolutionValues);
    }
}
