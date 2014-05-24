package client.map;

import client.util.Camera;
import client.util.Display;
import java.awt.DisplayMode;
import java.io.File;
import java.io.IOException;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
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
    
    Camera camera = Camera.getInstance();
    
    Image tileset;
    
    int mapWidthTiles;
    int mapHeightTiles;
    
    int mapWidth;
    int mapHeight;
    
    public void loadMap() {
        SAXBuilder builder = new SAXBuilder();
        
        String mapFilename = "src/resource/map/main.tmx";
        
        File mapFile = new File(mapFilename);
        
        try {
            Document doc = (Document)builder.build(mapFile);
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
            String filepath = tilesetElement.getChild("image").getAttributeValue("source");
            
            filepath = filepath.replace("../", "");
            
            System.out.println(filepath);
            
//            String[] filepathAux = filepath.split(filepath);

            // TODO: Utilizar nome da imagem que está no arquivo
            tileset = new Image("/resource/image/tileset/tileset_day.png");
            
            int tilesetWidth = (int)tileset.getWidth() / tileWidth;
            
            String mapString = rootNode.getChild("layer").getChild("data").getText();
            
            map = new Point2D[mapHeightTiles][mapWidthTiles];
             
            String[] mapRow = mapString.split("\n");
            String[] mapCol = null;
            int mapValue;
            int row, col;
            
            for (int i=0; i < mapHeightTiles; i++) {
                mapCol = mapRow[i+1].trim().split(",");
                
                for (int j=0; j < mapWidthTiles; j++) {
                    mapValue = Integer.parseInt(mapCol[j]) - firstTileID;
                    
                    row = mapValue / tilesetWidth;
                    col = mapValue - (row * tilesetWidth);
                    
                    map[i][j] = new Point2D(col , row);
                }
            }
            
            System.out.println(mapFilename + " loaded successfully!");
        } catch (IOException e) {
            System.err.println("Problema na leitura do arquivo: " + e.getMessage());
        } catch (JDOMException e) {
            System.err.println("Problema na manipulação do xml: " + e.getMessage());
        }
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
    
    public void draw(WritableImage screen, ImageView view) {
        Point2D currentTile;
        PixelWriter pw = screen.getPixelWriter();
        DisplayMode mode = Display.getInstance().getCurrentDisplayMode();

        int mapTileBeginX, mapTileBeginY;
        int mapTileEndX, mapTileEndY;
        
        mapTileBeginX = (int)camera.getX() / tileWidth;
        mapTileBeginY = (int)camera.getY() / tileHeight;
        
//        System.out.println("Zero1 ? = " + camera.getX() + ", " + tileWidth);
//        System.out.println("Zero2 ? = " + camera.getY() + ", " + tileHeight);
        
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
        
//        System.out.println("(Gap=" + posGapX + ", " + posGapY + ")");
        
        Rectangle2D viewport = new Rectangle2D(posGapX, posGapY, mode.getWidth(), mode.getHeight());
        view.setViewport(viewport);
        
        for (int i=mapTileBeginY, my = 0; i < mapTileEndY; i++, my++) {
            for (int j=mapTileBeginX, mx = 0; j < mapTileEndX; j++, mx++) {
                currentTile = map[i][j];
                
                pw.setPixels(mx * tileWidth, my * tileHeight, tileWidth, tileHeight,
                            tileset.getPixelReader(), (int)currentTile.getX() * tileWidth, (int)currentTile.getY() * tileHeight);
            }
        }
    }
}
