package jp.ac.uryukyu.ie.e165729.act;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 * Created by e165729 on 2017/01/24.
 */
public class MainPanel extends JPanel {
    // ウィンドウの大きさ
    private static final int width = 480;
    private static final int height = 480;

    private Image heroImage;

    // 行と列の大きさ(ピクセル)
    private static final int row = 15;
    private static final int col = 15;

    // チップセットのサイズ(ピクセル)
    private static final int cs = 32;

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

    //　チップセット
    private Image floorImage;
    private Image wallImage;


    public MainPanel(){
        // パネルの推奨サイズの設定
        setPreferredSize(new Dimension(width,height));

        // イメージをロード
        loadImage();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // マップを描く
        drawMap(g);
    }

    private  void loadImage(){
        ImageIcon icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/floor.gif");
        floorImage = icon.getImage();

        icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/wall.gif");
        wallImage = icon.getImage();
    }

    private void drawMap(Graphics g){
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                // mapの値に応じて絵を描く
                switch (map[i][j]){
                    case 0: // 床
                        g.drawImage(floorImage, j*cs, i*cs, this);
                        break;

                    case 1: // 壁
                        g.drawImage(wallImage, j*cs, i*cs, this);
                        break;
                }
            }
        }
    }
}
