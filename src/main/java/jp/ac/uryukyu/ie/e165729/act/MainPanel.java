package jp.ac.uryukyu.ie.e165729.act;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;


/**
 * Created by e165729 on 2017/01/24.
 */
public class MainPanel extends JPanel {
    // ウィンドウの大きさ
    private static final int width = 480;
    private static final int height = 480;

    private Image heroImage;

    public MainPanel(){
        // パネルの推奨サイズの設定
        setPreferredSize(new Dimension(width,height));

        // イメージをロード
        loadImage();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // 勇者のイメージを入力した座標に表示
        g.drawImage(heroImage, 0, 0, this);
    }

    private void loadImage(){
        ImageIcon icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/Hero.png");
        heroImage = icon.getImage();
    }

}
