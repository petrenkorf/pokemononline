package client.map;

import client.game.ResourceManager;
import client.util.Camera;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javafx.geometry.Point2D;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author bruno.weig
 */
public class Map {
    Point2D[][] map = null;
    int tileWidth;
    int tileHeight;
    int firstTileID;
    
    // Id Textura
    IntBuffer textureId = IntBuffer.allocate(1);
    
    Camera camera = Camera.getInstance();
    
    Texture tileset;
    
    int mapWidthTiles;
    int mapHeightTiles;
    
    int mapWidth;
    int mapHeight;
    
    public void loadMap(String filename, GLAutoDrawable canvas) {
        SAXBuilder builder = new SAXBuilder();
        
        String mapFilename = "resource/map/" + filename;
        
        InputStream in = getClass().getClassLoader().getResourceAsStream(mapFilename);
//        URL url = getClass().getResource();
//        System.out.println("Map FileFile: " + url.getFile());
//        System.out.println("Map FileFile: " + url.getPath());
        
//        File mapFile = new File(mapFilename);
        
        try {
            Document doc = (Document)builder.build(in);
            Element rootNode = doc.getRootElement();
            
            // Dimensão Mapa
            mapWidthTiles = rootNode.getAttribute("width").getIntValue();
            mapHeightTiles = rootNode.getAttribute("height").getIntValue();
            
            Element tilesetElement = rootNode.getChild("tileset");
            
            // Tileset
            tileWidth = tilesetElement.getAttribute("tilewidth").getIntValue();
            tileHeight = tilesetElement.getAttribute("tileheight").getIntValue();
            firstTileID = tilesetElement.getAttribute("firstgid").getIntValue();
            
            mapWidth = mapWidthTiles * tileWidth;
            mapHeight = mapHeightTiles * tileHeight;
            
            System.out.println("Screen (" + mapWidthTiles + ", " + mapHeightTiles + ")" + 
                                ", Tile(" + tileWidth + ", " + tileHeight + ")");
            
            // Carrega tileset
            Element elemImage = tilesetElement.getChild("image");
            String filepath = elemImage.getAttributeValue("source");
            
            filepath = filepath.replace("../", "");
            String[] filepathAux = filepath.split("/");
            
            // Filtra o nome do tileset
            int n = filepathAux.length;
            String path = filepathAux[n-2] + "/" + filepathAux[n-1];
            System.out.println("Filepath " + filepath + " => " + path);
            
            // Carrega o tileset
            if ( canvas != null )
                loadTileset(path, canvas);
            
            int tilesetWidth = elemImage.getAttribute("width").getIntValue() / tileWidth;
            
            // Tiles
            String mapString = rootNode.getChild("layer").getChild("data").getText();
            
            map = new Point2D[mapHeightTiles][mapWidthTiles];
             
            String[] mapRow = mapString.split("\n");
            String[] mapCol;
            int mapValue;
            int row, col;
            
//            int lineTiles = tilesetWidth;
            
            // Lê cada linha do mapa
            for (int i=0; i < mapHeightTiles; i++) {
                // Pula primeira linha (sempre em branco)
                mapCol = mapRow[i+1].trim().split(",");
                
                for (int j=0; j < mapWidthTiles; j++) {
                    mapValue = Integer.parseInt(mapCol[j]) - firstTileID;
                    
                    row = mapValue / tilesetWidth;
                    col = mapValue - (row * tilesetWidth);
                    
                    map[i][j] = new Point2D(col, row);
                }
            }
            
            System.out.println(mapFilename + " loaded successfully!");
        } catch (IOException e) {
            System.err.println("Problema na leitura do arquivo: " + e.getMessage());
            System.exit(1);
        } catch (JDOMException e) {
            System.err.println("Problema na manipulação do xml: " + e.getMessage());
        }
    }
    
    public void loadTileset(String path, GLAutoDrawable canvas) throws IOException {
        GL gl = canvas.getGL();
        
//        System.out.println("> Values");
//        
//        for (int i=0; i <= 1024; i+= 16) {
//            System.out.println(" " + i + " = " + (i / 1024.0));
//        }
        
        tileset = ResourceManager.loadTexture(canvas, path, TextureIO.PNG, true);
        
        GL2 gl2 = gl.getGL2();
        
        Buffer buffer = ByteBuffer.allocate(tileset.getWidth() * tileset.getHeight() * 4);
        
        tileset.bind(gl);
        gl2.glBindTexture(tileset.getTarget(), tileset.getTextureObject());
        gl2.glGetTexImage(tileset.getTarget(), 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, buffer);
        gl2.glBindTexture(GL.GL_TEXTURE_2D, 0);
        
        // Cria nova textura
//        gl.glGenTextures(1, textureId);
//        
//        System.out.println("Array Created: " + textureId.get(0));
//        
//        gl2.glBindTexture(GL_TEXTURE_2D_ARRAY, textureId.get(0));
//        System.out.println("Texture binded");
//        
//        // Reserva espaço para a textura
//        gl2.glTexStorage2D(GL_TEXTURE_2D_ARRAY, 1, GL_RGBA8, mapWidth, mapHeight);
//        System.out.println("TextureAtlas created!");
        
//        gl2.glTexSubImage2D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, 0, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        
//        for (int i=0; i < mapHeightTiles; i++) {
//            for (int j=0; j < mapWidthTiles; j++) {
//                gl2.glGetTex
//                
//                gl2.glTexSubImage2D(GL_TEXTURE_2D, 0, j * tileWidth, i * tileWidth, 
//                        tileWidth, tileHeight, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
////                gl2.glTexSubImage2D(GL_TEXTURE_2D_ARRAY, 0, j * tileWidth, i * tileWidth, 
////                        tileWidth, tileHeight, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
//            }
//        }
        
//        System.out.println("Map Created!");
//        
//        //Always set reasonable texture parameters
//        gl.glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//        gl.glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//        gl.glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//        gl.glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    }
    
    public boolean collision(int x, int y) {
        return false;
    }
    
    public int getTileWidth() {
        return tileWidth;
    }
    
    public int getTileHeight() {
        return tileHeight;
    }
    
    public int getMapWidth() {
        return mapWidth;
    }
    
    public int getMapHeight() {
        return mapHeight;
    }
    
    public void draw(GLAutoDrawable screen) {
        Point2D currentTile;
        
        // Fatores que são multiplicados pela posição de cada tile 
        // (OpenGL utiliado coordenadas de textura entre 0.0 e 1.0)
//        double factor_x = (double)tileWidth / (double)tileset.getWidth();
//        double factor_y = (double)tileHeight / (double)tileset.getHeight();
        
        int mapTileBeginX, mapTileBeginY;
        int mapTileEndX, mapTileEndY;
        
        mapTileBeginX = (int)camera.getX() / tileWidth;
        mapTileBeginY = (int)camera.getY() / tileHeight;
        
        // Limite superior esquerdo da tela
        if( mapTileBeginX < 0 ) {
            mapTileBeginX = 0;
        }
        
        if( mapTileBeginY < 0 ) {
            mapTileBeginY = 0;
        }
        
        mapTileEndX = 1 + ((int)camera.getX() + camera.getWidth()) / tileWidth;
        mapTileEndY = 1 + ((int)camera.getY() + camera.getHeight()) / tileHeight;
        
        // Limite inferior direto da tela
        if ( mapTileEndX >= mapWidthTiles ) {
            mapTileEndX = mapWidthTiles;
        }
        
        if ( mapTileEndY >= mapHeightTiles ) {
            mapTileEndY = mapHeightTiles;
        }
        
        int posGapX = (int)camera.getX() % tileWidth;
        int posGapY = (int)camera.getY() % tileHeight;
        
        GL gl = screen.getGL();
        GL2 gl2 = gl.getGL2();
        int tileY;
        int tileX;
        
        TextureCoords coords;
        
        tileset.enable(gl);
        tileset.bind(gl);
            
        gl2.glBegin(GL2.GL_QUADS);
            for (int i=mapTileBeginY, my = 0; i < mapTileEndY; i++, my += tileHeight) {
                for (int j=mapTileBeginX, mx = 0; j < mapTileEndX; j++, mx += tileWidth) {
                    currentTile = map[i][j];
                    
                    tileX = (int)currentTile.getX() * tileWidth;
                    tileY = (int)currentTile.getY() * tileHeight;
                    
                    coords = tileset.getSubImageTexCoords(
                            tileX, tileY + tileHeight, tileX + tileWidth, tileY);
                    
                    gl2.glTexCoord2d(coords.left(), coords.top());
                    gl2.glVertex2i(mx - posGapX, my - posGapY);
                    
                    gl2.glTexCoord2d(coords.right(), coords.top());
                    gl2.glVertex2i(mx + tileWidth - posGapX, my - posGapY);
                    
                    gl2.glTexCoord2d(coords.right(), coords.bottom());
                    gl2.glVertex2i(mx + tileWidth  - posGapX, my + tileHeight - posGapY);
                    
                    gl2.glTexCoord2d(coords.left(), coords.bottom());
                    gl2.glVertex2i(mx - posGapX, my + tileHeight - posGapY);
                }
            }
        gl2.glEnd();
        
        tileset.disable(gl);
    }
}
