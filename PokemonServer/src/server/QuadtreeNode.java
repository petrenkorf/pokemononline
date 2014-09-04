/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author bruno.weig
 */
public class QuadtreeNode {
    public static final int QUADTREE_PARTITIONS = 4;

    QuadtreeNode[] partition = new QuadtreeNode[QUADTREE_PARTITIONS];
    List<Object> itemList = new ArrayList<>();
    Rectangle bound;

    public QuadtreeNode(Rectangle bound) {
        this.bound = bound;
    }

    public QuadtreeNode getPartition(int partitionIndex) {
        return partition[partitionIndex];
    }

    public void split() {
        double halfWidth = bound.getWidth()/2.0;
        double halfHeight = bound.getHeight()/2.0;
        
        partition[0] = new QuadtreeNode(new Rectangle(bound.getX(), bound.getY(), halfWidth, halfHeight));
        partition[1] = new QuadtreeNode(new Rectangle(halfWidth, bound.getY(), halfWidth, halfHeight));
        partition[2] = new QuadtreeNode(new Rectangle(halfWidth, halfHeight, halfWidth, halfHeight));
        partition[3] = new QuadtreeNode(new Rectangle(bound.getX(), halfHeight, halfWidth, halfHeight));
    }
    
    public void addItem(Object p) {
        itemList.add(p);
    }
    
    public List<Object> getItemList() {
        return itemList;
    }
    
    public Rectangle getBound() {
        return bound;
    }
    
    public void clearItems() {
        itemList.clear();
    }
}
