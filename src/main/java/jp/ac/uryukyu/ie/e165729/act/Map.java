package jp.ac.uryukyu.ie.e165729.act;

import jp.ac.uryukyu.ie.e165729.Main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Created by e165729 on 2017/01/29.
 */
public class Map implements Common {
    // 行と列の大きさ(ピクセル)
    private static final int row = 20;
    private static final int col = 30;

    // マップ全体の大きさ(ピクセル)
    public static final int width = col * cs;
    public static final int height = row * cs;

    // マップ: 0=床，1=壁
    private int [][] map = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,1,0,1,1,0,0,0,0,1,0,0,0,0,0,1,1,0,1,1,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

    // チップセット
    private Image floorImage;
    private Image wallImage;

    // メインパネルへの参照
    private MainPanel panel;

    public Map(MainPanel panel){
        // イメージをロード
        loadImage();
    }

    public void draw(Graphics g, int offsetX, int offsetY){
        // オフセットを元に描写範囲を求める
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(MainPanel.width) + 1;
        // 描写範囲がマップの大きさより大きくならないように調整
        lastTileX = Math.min(lastTileX, col);

        int firstTileY = pixelsToTiles(-offsetY);
        int lastTileY = firstTileY + pixelsToTiles(MainPanel.height) + 1;
        // 描写範囲がマップの大きさより大きくならないように調整
        lastTileY = Math.min(lastTileY, row);

        for(int i = firstTileY; i < lastTileY; i++){
            for(int j = firstTileX; j < lastTileX; j++){
                // mapの値に応じて絵を描く
                switch (map[i][j]){
                    case 0: // 床
                        g.drawImage(floorImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;

                    case 1: // 壁
                        g.drawImage(wallImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                }
            }
        }
    }

    public boolean isHit(int x, int y){
        // (x,y)に壁があったら進まない
        if(map[y][x] == 1){
            return true;
        }

        // なければ進む
        return false;
    }

    // ピクセル単位をマス単位に変更する
    public static int pixelsToTiles(double pixels){
        return (int)Math.floor(pixels / cs);
    }

    // マス単位をピクセル単位に変更する
    public static int tilesToPixels(int tiles){
        return tiles * cs;
    }

    private void loadImage(){
        ImageIcon icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/floor.gif");
        floorImage = icon.getImage();

        icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/wall.gif");
        wallImage = icon.getImage();
    }
}
