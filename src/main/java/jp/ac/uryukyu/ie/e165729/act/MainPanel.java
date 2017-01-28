package jp.ac.uryukyu.ie.e165729.act;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 * Created by e165729 on 2017/01/24.
 */
public class MainPanel extends JPanel implements KeyListener{
    // ウィンドウの大きさ
    private static final int width = 480;
    private static final int height = 480;

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
    private Image heroImage;

    // 勇者の座標
    private int x, y;


    public MainPanel(){
        // パネルの推奨サイズの設定
        setPreferredSize(new Dimension(width,height));

        // イメージをロード
        loadImage();

        // 勇者の位置の初期化
        x = 1;
        y = 1;

        // キー入力を受付
        setFocusable(true);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // マップを描く
        drawMap(g);

        // 勇者を描く
        drawChara(g);
    }

    public void keyPressed(KeyEvent e){
        // 押されたキーを取得
        int keyCode = e.getKeyCode();

        switch (keyCode){
            case KeyEvent.VK_LEFT :
                // 左キーの場合は勇者を１歩左へ
                x--;
                break;

            case KeyEvent.VK_RIGHT :
                // 右キーの場合は勇者を１歩右へ
                x++;
                break;

            case KeyEvent.VK_UP :
                // 上キーの場合は勇者を１歩上へ
                y--;
                break;

            case  KeyEvent.VK_DOWN :
                // 下キーの場合は勇者を１歩下へ
                y++;
                break;
        }

        // 勇者を動かしたので再描写する
        repaint();
    }

    public void keyReleased(KeyEvent e){
    }

    public void keyTyped(KeyEvent e){
    }


    private  void loadImage(){
        ImageIcon icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/hero.gif");
        heroImage = icon.getImage();

        icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/floor.gif");
        floorImage = icon.getImage();

        icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/wall.gif");
        wallImage = icon.getImage();
    }

    private void drawChara(Graphics g){
        g.drawImage(heroImage, x*cs, y*cs, this);
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
