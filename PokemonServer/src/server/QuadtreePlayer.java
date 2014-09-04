/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import client.game.Player;
import java.util.Iterator;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 * Consiste em uma Quadtree com partições estáticas (geradas apenas uma vez) e com todos os níveis
 * contendo 4 partições. 
 * 
 * Essa Quadtree armazena uma referência dos jogadores em cada uma partições onde
 * um dos vértices do viewPort estiverem contidos.
 * 
 * @author bruno.weig
 */
public class QuadtreePlayer {
    final int MAX_DEPTH = 1;
    QuadtreeNode root;
    Point2D globalBound;
    Point2D playerViewPort;
    
    public void createTree(Rectangle bound, Point2D playerViewPort) {
        root = new QuadtreeNode(bound);
        this.playerViewPort = playerViewPort;
        
        globalBound = new Point2D(bound.getWidth(), bound.getHeight());
        splitTree(root, bound, 0);
    }
    
    /**
     * Método recursivo que cria as 4 partições de cada um dos níveis da QuadTree
     * 
     * @param node
     * @param bound
     * @param level 
     */
    private void splitTree(QuadtreeNode node, Rectangle bound, int level) {
        if ( level < MAX_DEPTH ) {
            node.split();
            
            for (int i=0; i < QuadtreeNode.QUADTREE_PARTITIONS; i++) {
                splitTree(node.getPartition(i), bound, level+1);
            }
        }
    }
    
    /**
     * Atualiza Quadtree com os personagens online
     * 
     * @param list 
     */
    public void update(List<PlayerServer> list) {
        // Limpa quadtree
        clear();
        
        Iterator<PlayerServer> it = list.iterator();
        
        // Insere cada jogador na quadtree
        while ( it.hasNext() ) {
            addPlayer(it.next());
        }
    }
    
    public void clear() {
        clearQuadTree(root, 0);
    }
    
    /**
     * Limpa items da Quadtree
     * 
     * @param node
     * @param level 
     */
    private void clearQuadTree(QuadtreeNode node, int level) {
        if ( node != null ) {
            if ( level < MAX_DEPTH ) {
                for (int i=0; i < QuadtreeNode.QUADTREE_PARTITIONS; i++) {
                    clearQuadTree(node.getPartition(i), level+1);
                }
            } else {
                // Limpa items do último nível da árvore
                node.clearItems();
            }
        }
    }
    
    /**
     * Insere um jogador na quadtree
     * 
     * @param player 
     */
    public void addPlayer(PlayerServer player) {
        int x = player.getPosX() + player.getWidth()/2;
        int y = player.getPosY() + player.getHeight()/2;
        
        addPlayerRec(root, player, 0, x, y);
    }
    
    /**
     * Insere uma referencia do jogador nos ramos do último nível da Quadtree, nas partições onde
     * a posição do viewPort do jogador está contido.
     * 
     * @param node
     * @param player
     * @param depth
     * @param x
     * @param y
     */
    private void addPlayerRec(QuadtreeNode node, PlayerServer player, int depth, int x, int y) {
        if ( depth < MAX_DEPTH ) {
            // Esquerda superior
            if ( insideBound(node.getPartition(0).getBound(), 
                    x - playerViewPort.getX(), y - playerViewPort.getY()) ) {
                addPlayerRec(node.getPartition(0), player, depth+1, x, y);
            }
            
            // Direita superior
            if ( insideBound(node.getPartition(1).getBound(), 
                    x + playerViewPort.getX(), y - playerViewPort.getY()) ) {
                addPlayerRec(node.getPartition(1), player, depth+1, x, y);
            }
            
            // Direita inferior
            if ( insideBound(node.getPartition(2).getBound(), 
                    x + playerViewPort.getX(), y + playerViewPort.getY()) ) {
                addPlayerRec(node.getPartition(2), player, depth+1, x, y);
            }
            
            // Esquerda inferior
            if ( insideBound(node.getPartition(3).getBound(), 
                    x - playerViewPort.getX(), y + playerViewPort.getY()) ) {
                addPlayerRec(node.getPartition(3), player, depth+1, x, y);
            }
        } else {
            node.addItem(player);
        }
    }
    
    private boolean insideBound(Rectangle p, double px, double py) {
        // Ajusta valores fora dos limites do mapa
        if ( px < 0.0 ) {
            px = 0.0;
        }
        
        if ( px >= globalBound.getX() ) {
            px = globalBound.getX();
        }
        
        if ( py < 0.0 ) {
            py = 0.0;
        }
        
        if ( py >= globalBound.getY() ) {
            py = globalBound.getY();
        }
        
        return (px >= p.getX() && px <= (p.getX() + p.getWidth()) && 
                py >= p.getY() && py <= (p.getY() + p.getHeight()));
    }
    
    public void print() {
        for (int i=0; i < QuadtreeNode.QUADTREE_PARTITIONS; i++) {
            _print(root.getPartition(i), 0);
        }
    }
    
    /**
     * Método recursivo que imprime os limites de cada quadrante e os jogadores 
     * armazenados em cada um destes.
     * 
     * @param node
     * @param depth 
     */
    private void _print(QuadtreeNode node, int depth) {
        if ( depth < MAX_DEPTH ) {
            Rectangle bound = node.getBound();
            
            for (int i=0; i < depth; i++) {
                System.out.print("  ");
            }
                
            System.out.println(bound.getX() + ", " + bound.getY() + ", " + 
                    (bound.getX() + bound.getWidth()) + ", " + (bound.getHeight() + bound.getHeight()));

            // Mostra lista de jogadores dentro de uma determinada partição
            List<Object> listPlayer = node.getItemList();

            if ( listPlayer != null ) {
                Iterator it = listPlayer.iterator();
                Player player;

                while ( it.hasNext() ) {
                    player = (Player)it.next();

                    System.out.println(" => " + player.getName());
                }
            }
            
            for (int p=0; p < QuadtreeNode.QUADTREE_PARTITIONS; p++) {
                _print(node.getPartition(p), depth+1);
            }
        }
    }
}
