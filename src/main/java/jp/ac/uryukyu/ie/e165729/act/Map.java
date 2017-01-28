package jp.ac.uryukyu.ie.e165729.act;

import javax.swing.*;
import java.awt.*;

/**
 * Created by e165729 on 2017/01/29.
 */
public class Map implements Common {
    // 行と列の大きさ(ピクセル)
    private static final int row = 15;
    private static final int col = 15;

    // マップ: 0=床，1=壁
    private int [][] map = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,1,1,1,1,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,1,0,1,1,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

    // チップセット
    private Image floorImage;
    private Image wallImage;

    // メインパネルへの参照
    private MainPanel panel;

    public Map(MainPanel panel){
        // イメージをロード
        loadImage();
    }

    public void draw(Graphics g){
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                // mapの値に応じて絵を描く
                switch (map[i][j]){
                    case 0: // 床
                        g.drawImage(floorImage, j*cs, i*cs, panel);
                        break;

                    case 1: // 壁
                        g.drawImage(wallImage, j*cs, i*cs, panel);
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

    private void loadImage(){
        ImageIcon icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/floor.gif");
        floorImage = icon.getImage();

        icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/wall.gif");
        wallImage = icon.getImage();
    }
}
