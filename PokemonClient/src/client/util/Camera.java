package client.util;

import javafx.geometry.Point2D;
import client.game.Character;

/**
 *
 * @author bruno.weig
 */
public class Camera {
    static Camera camera = null;
    
    // Limites da tela
    int boundWidth;
    int boundHeight;
    
    // Se está havendo scrolling
    boolean scrollingX = false;
    boolean scrollingY = false;
    
    double x;
    double y;
    
    // Controla
    double localX;
    double localY;
    
    // Dimensão da tela
    int width = 320;
    int height = 240;
    
    Character c = null;
    
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
    
    /**
     * Define um personagem como foco da câmera onde seu posicionamento
     * é utilizado no update
     * @param c 
     */
    public void setFocus(Character c) {
        this.c = c;
    }
    
    public void update() {
        if ( c != null ) {
            float halfWidth = width / 2;
            float halfHeight = height / 2;

            // Extremidade esquerda do mapa
            if ( c.getX() - halfWidth <= 0 ) {
                x = 0;
                scrollingX = false;

                localX = c.getX();
            } else if ( c.getX() + halfWidth >= boundWidth ) {
                scrollingX = true;
                // Scrolling
                localX = (halfWidth - (boundWidth - c.getX())) + halfWidth;

                x = boundWidth - (width < boundWidth ? width : boundWidth);
            } else {
                scrollingX = false;
                // Parte inferior do mapa
                localX = halfWidth;
                x = c.getX() - halfWidth;
            }

            // Início do mapa
            if ( c.getY() - halfHeight <= 0 ) {
                scrollingY = false;
                y = 0;
                localY = c.getY();
            } else if ( c.getY() + halfHeight >= boundHeight ) {
                scrollingY = true;
                // Início do mapa
                y = boundHeight - (height < boundHeight ? height : boundHeight);
                localY = (halfHeight - (boundHeight - c.getY())) + halfHeight;
            } else {
                scrollingY = false;
                // Fim do mapa
                y = c.getY() - halfHeight;
                localY = halfHeight;
            }
        }
        
//        System.out.print("Position=" + objectPosition.getX() + ", " + objectPosition.getY() +
//                        " (Camera=" + x + ", " + y + ")");
    }
    
    public boolean isScrolling() {
        return scrollingX || scrollingY;
    }
    
    public boolean isScrollingX() {
        return scrollingX;
    }
    
    public boolean isScrollingY() {
        return scrollingY;
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
    
    public double getLocalX() {
        return localX;
    }
    
    public double getLocalY() {
        return localY;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}

