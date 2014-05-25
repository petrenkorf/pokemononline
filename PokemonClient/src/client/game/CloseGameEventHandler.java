/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.game;

import javafx.event.EventHandler;

/**
 *
 * @author bruno.weig
 */
public class CloseGameEventHandler implements EventHandler<CloseGameEvent> {
    public CloseGameEventHandler() {
    }
    
    @Override
    public void handle(CloseGameEvent e) {
        System.out.println("It's Working");
    }
}
