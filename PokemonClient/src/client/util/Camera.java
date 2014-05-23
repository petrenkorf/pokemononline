package client.util;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author bruno.weig
 */

public class Camera {
    static Camera camera = null;
    
    Display display = Display.getInstance();
    
    int boundWidth;
    int boundHeight;
    double x;
    double y;
    
    private Camera() {
    }
    
    static public Camera getInstance() {
        if ( camera == null ) {
            camera = new Camera();
        }
        
        return camera;
    }
    
    public void setEnvironmentBounds(int width, int height) {
        boundWidth = width;
        boundHeight = height;
    }
    
    public void update(Rectangle objectPosition) {
        int width = display.getCurrentDisplayMode().getWidth();
        int height = display.getCurrentDisplayMode().getHeight();
        
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        
        // Extremidade esquerda do mapa
        if ( objectPosition.getX() - halfWidth <= 0 ) {
            x = 0;
        } else if ( objectPosition.getX() + halfWidth >= boundWidth ) {
            // Extremidade direita do mapa
            x = boundWidth - (width < boundWidth ? width : boundWidth);
        } else {
            x = objectPosition.getX() - halfWidth;
        }
        
        if ( objectPosition.getY() - halfHeight <= 0 ) {
            y = 0;
        } else if ( objectPosition.getY() + halfHeight >= boundHeight ) {
            y = boundHeight - (height < boundHeight ? height : boundHeight);
        } else {
            y = objectPosition.getY() - halfHeight;
        }
        
        System.out.print("Position=" + objectPosition.getX() + ", " + objectPosition.getY() +
                        " (Camera=" + x + ", " + y + ")");
    }
    
    public Point2D getLocation() {
        return new Point2D(x, y);
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public int getWidth() {
        return display.getCurrentDisplayMode().getWidth();
    }
    
    public int getHeight() {
        return display.getCurrentDisplayMode().getHeight();
    }
}

