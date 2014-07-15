package client.game;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.media.opengl.GL;
import static javax.media.opengl.GL2.*;
import javax.media.opengl.GLAutoDrawable;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public abstract class Sprite {
    static final int DIRECTIONS = 4;
    
    enum Direction {
        UP(0),
        DOWN(1), 
        LEFT(2),
        RIGHT(3);

        private final int value;

        private Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    class SpriteAnim {
        int sx;
        int sy;
        
        int time;
    }
    
    class SpriteState {
        String name;
        
        SpriteAnim[][] anim = new SpriteAnim[DIRECTIONS][];
    }
    
    protected int width;
    protected int height;
    
    int[] spacing = new int[4];
//    protected int width = 16;
//    protected int height = 24;

//    protected int frameTotal;
//    protected float transitionTime;
    
    List<SpriteState> animation = new ArrayList<SpriteState>();
    protected int currentState = 0;
    protected int currentFrame = 0;
    
    protected boolean repeat;
    protected long currentTime;
    protected long lastTime;
    protected Calendar timer = null;

    protected String filepath;
    protected transient Texture spritesheet;

    public static final String resourcePath = "/resource/image/sprite/";

    abstract public void draw(GLAutoDrawable drawable);

    /**
     * Carrega a animação
     * 
     * @param Nome do arquivo XML com as informações da animação
     */
    public void loadSpriteAnimation(String filename, GLAutoDrawable canvas) {
        SAXBuilder builder = new SAXBuilder();
        
        String mapFilename = "src/resource/animation/" + filename;
        
        File mapFile = new File(mapFilename);
        
        try {
            Document doc = (Document)builder.build(mapFile);
            Element rootNode = doc.getRootElement();
            
            System.out.println("Loading sprite file " + filename + "!");
            System.out.println(rootNode.getName());
            
            rootNode.getAttribute("name");
            
            Element xmlSprites = rootNode.getChild("sprites");
            
            // Carrega spritesheet
            String xmlFilepath = xmlSprites.getAttributeValue("filepath");
            spritesheet = loadTexture(xmlFilepath, TextureIO.PNG, canvas);
            
            width = xmlSprites.getAttribute("width").getIntValue();
            height = xmlSprites.getAttribute("height").getIntValue();
            
            Element xmlAnimations = rootNode.getChild("animations");
            
            // Estados (caminhando, bicicleta, pescando)
            List<Element> xmlStatesList = xmlAnimations.getChildren("state");
            
            // Espaçamento interno dos tiles
            Element _spacing = xmlAnimations.getChild("spacing");
            Iterator<Element> it = xmlStatesList.iterator();
            
            // Animação
            List<Element> xmlStatesAnimList;
            Iterator<Element> itAnim;
            
            // Caso seja definido cada sprite da animação
            List<Element> xmlAnimSpriteList;
            Iterator<Element> itAnimSprite;
            
            Element xmlState;
            Element xmlStateAnim;
            
            SpriteState _state;
            int currentDir = 0;
            
            Attribute spriteTimeAttribute;
            int spriteTime = 0;
            boolean staticTime;
            
            int beginX;
            int beginY;
            int spritesQty;
            
            // Espaçamento interno de cada sprite
            if ( _spacing != null && _spacing.hasAttributes() ) {
                spacing[Direction.UP.getValue()] = _spacing.getAttribute("up").getIntValue();
                spacing[Direction.DOWN.getValue()] = _spacing.getAttribute("down").getIntValue();
                spacing[Direction.LEFT.getValue()] = _spacing.getAttribute("left").getIntValue();
                spacing[Direction.RIGHT.getValue()] = _spacing.getAttribute("right").getIntValue();
            }
            
            // Estados
            while ( it.hasNext() ) {
                xmlState = it.next();
                
                System.out.println("State: " + xmlState.getAttributeValue("id"));
                
                _state = new SpriteState();
                _state.name = xmlState.getAttributeValue("id");
                
                // Tempo estático entre sprites
                spriteTimeAttribute = xmlState.getAttribute("static_time");
                
                if ( spriteTimeAttribute != null ) {
                    staticTime = true;
                    
                    spriteTime = spriteTimeAttribute.getIntValue();
                } else {
                    staticTime = false;
                }
                
                xmlStatesAnimList = xmlState.getChildren("anim");
                itAnim = xmlStatesAnimList.iterator();
                
                // Animação
                while ( itAnim.hasNext() ) {
                    xmlStateAnim = itAnim.next();
                    
                    xmlAnimSpriteList = xmlStateAnim.getChildren("sprite");
                    
                    switch ( xmlStateAnim.getAttributeValue("direction") ) {
                        case "right":
                            currentDir = Direction.RIGHT.getValue();
                            break;
                        case "left":
                            currentDir = Direction.LEFT.getValue();
                            break;
                        case "up":
                            currentDir = Direction.UP.getValue();
                            break;
                        case "down":
                            currentDir = Direction.DOWN.getValue();
                            break;
                    }
                    
                    // Caso a animação seja definida como um intervalo de sprites
                    if ( xmlAnimSpriteList.isEmpty() ) {
                        beginX = xmlStateAnim.getAttribute("beginX").getIntValue();
                        beginY = xmlStateAnim.getAttribute("beginY").getIntValue();
                        spritesQty = xmlStateAnim.getAttribute("sprites").getIntValue();
                        
                        if ( spritesQty > 0 && _state.anim[currentDir] == null ) {
                            _state.anim[currentDir] = new SpriteAnim[spritesQty];
                        }
                        
                        for (int i=0; i < spritesQty; i++) {
                            _state.anim[currentDir][i] = new SpriteAnim();
                            _state.anim[currentDir][i].sx = (beginX + i) * width;
                            _state.anim[currentDir][i].sy = beginY;
                            
                            // Tempo estático
                            _state.anim[currentDir][i].time = spriteTime;
                        }
                        
                        animation.add(_state);
                    } else {
                        // Caso a animação seja definida como um intervalo de sprites
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Problema na leitura do arquivo: " + e.getMessage());
        } catch (JDOMException e) {
            System.err.println("Problema na manipulação do xml: " + e.getMessage());
        }
    }
    
    static public Texture loadTexture(String filename, String filetype, GLAutoDrawable canvas) 
            throws IOException {
        Texture t = TextureIO.newTexture(Sprite.class.getResourceAsStream(
                resourcePath + filename), false, filetype);
        t.bind(canvas.getGL());

        GL gl = canvas.getGL();

//            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        return t;
    }
    
    public SpriteState getCurrentState() {
        return animation.get(currentState);
    }
    
    public void setCurrentState(int state) {
        currentState = state;
    }

    abstract protected void update();
    
    /**
     * Imprime na tela todos os dados de animação
     */
    public void debug() {
        
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public long getCurrentTime() {
        return currentTime;
    }
}
