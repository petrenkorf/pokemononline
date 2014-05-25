/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.game;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * @author bruno.weig
 */
public class CloseGameEvent extends Event {
    public static final EventType<CloseGameEvent> CLOSE = new EventType<CloseGameEvent>(Event.ANY, "CLOSE");

    public static final EventType<CloseGameEvent> ANY = CLOSE;
    
    public CloseGameEvent() {
        super(CLOSE);
    }
    
    public CloseGameEvent(Object arg0, EventTarget arg1, EventType<? extends Event> arg2) {
        super(arg0, arg1, arg2);
    } 
}
