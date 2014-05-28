package client.game;

import client.util.Camera;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import com.sun.javafx.geom.Rectangle;
import java.io.Serializable;

public class Player extends Character implements Serializable {
    private long id;
    private String verificationCode;
    private String name;
    private String filepath = "/resource/image/sprite/ash.png";
    private transient Image spritesheet;

//    private WritableImage spritesheet;

    public Player(){
        rect = new Rectangle();
        spritesheet = new Image(filepath);
        
        rect.x = 20;
        rect.y = 20;
        rect.width = 20;
        rect.height = 20;
    }
    
    public Player(long id, String verificationCode){
        this.id = id;
        this.verificationCode = verificationCode;
        
        spritesheet = new Image("/resource/image/sprite/ash.png", false);
        
        rect = new Rectangle();
        rect.x = 20;
        rect.y = 20;
        rect.width = 20;
        rect.height = 20;
    }

    @Override
    public void draw(WritableImage _screen){
//            System.out.print("x=" + this.rect.x + ", y=" + this.rect.y + ", w= " + this.rect.width + ", h= " + this.rect.height);

        Camera c = Camera.getInstance();

        _screen.getPixelWriter().setPixels((int)c.getLocalX(), (int)c.getLocalY(), 
                rect.width, rect.height, spritesheet.getPixelReader(), 0, 0);
 }

    @Override
    public void update() {
    }

    public long getId() {
        return id;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
