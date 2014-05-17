/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.map;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
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
    
    Image tileset;
    
    // em tiles
    int mapWidth;
    int mapHeight;
    
    public void loadMap() {
        SAXBuilder builder = new SAXBuilder();
        
        File mapFile = new File("src/resource/map/main.tmx");
        
        try {
            Document doc = (Document)builder.build(mapFile);
            Element rootNode = doc.getRootElement();
            
            // Dimensão Mapa
            mapWidth = rootNode.getAttribute("width").getIntValue();
            mapHeight = rootNode.getAttribute("height").getIntValue();
            
            Element tilesetElement = rootNode.getChild("tileset");
            
            // Tileset
            tileWidth = tilesetElement.getAttribute("tilewidth").getIntValue();
            tileHeight = tilesetElement.getAttribute("tileheight").getIntValue();
            
            System.out.println("Screen (" + mapWidth + ", " + mapHeight + ")" + 
                                ", Tile(" + tileWidth + ", " + tileHeight + ")");
            
            // Carrega tileset
            String filepath = tilesetElement.getChild("image").getAttributeValue("source");
            
            filepath = filepath.replace("../", "");
            
            System.out.println(filepath);
            
//            String[] filepathAux = filepath.split(filepath);

            tileset = new Image("/resource/image/tileset/tileset_day.png");
            
            int tilesetWidth = (int)tileset.getWidth() / tileWidth;
            int tilesetHeight = (int)tileset.getHeight()/ tileHeight;
            
            String mapString = rootNode.getChild("layer").getChild("data").getText();
            
            map = new Point2D[mapHeight][mapWidth];
             
            String[] mapRow = mapString.split("\n");
            String[] mapCol = null;
            int mapValue;
            int row, col;
            
            for (int i=0; i < mapHeight; i++) {
                mapCol = mapRow[i+1].trim().split(",");
                
                for (int j=0; j < mapWidth; j++) {
                    mapValue = Integer.parseInt(mapCol[j]);
                    
                    row = mapValue / mapWidth;
                    col = mapValue - (row * mapWidth);
                    
                    System.out.print("(" + row + "," + col + ") ");
                    
                    map[i][j] = new Point2D(col , row);
                }
                
                System.out.println();
            }
            
            rootNode.getChildren("");
            
        } catch (IOException e) {
            System.err.println("Problema na leitura do arquivo: " + e.getMessage());
        } catch (JDOMException e) {
            System.err.println("Problema na manipulação do xml: " + e.getMessage());
        }
    }
    
    public void draw(PixelWriter screen) {
        for (int i=0; i < mapHeight; i++) {
            for (int j=0; j < mapWidth; j++) {
                screen.setPixels(j*tileWidth, i * tileHeight, tileWidth, tileHeight, 
                                 tileset.getPixelReader(), j*tileWidth, i * tileHeight);
            }
        }
    }
}