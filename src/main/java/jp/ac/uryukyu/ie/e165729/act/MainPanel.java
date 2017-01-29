package jp.ac.uryukyu.ie.e165729.act;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.management.ManagementFactory;

import javax.swing.JPanel;


/**
 * Created by e165729 on 2017/01/24.
 */
public class MainPanel extends JPanel implements KeyListener, Common{
    // ウィンドウの大きさ
    public static final int width = 480;
    public static final int height = 480;

    private Map map;

    private Chara hero;

    public MainPanel(){
        // パネルの推奨サイズの設定
        setPreferredSize(new Dimension(width,height));

        // キー入力を受付
        setFocusable(true);
        addKeyListener(this);

        map = new Map(this);

        hero = new Chara(1, 1, "/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/Hero.png", map, this);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // X方向のオフセットを計算(基本offsetXの値はマイナス)
        int offsetX = (MainPanel.width / 2) - (hero.getX()*cs);
        // マップ端ではスクロールしないようにする
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, MainPanel.width - Map.width);

        // Y方向のオフセットを計算(offsetの値はマイナス)
        int offsetY = (MainPanel.height / 2) - (hero.getY()*cs);
        // マップ端ではスクロールしないようにする
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainPanel.height - Map.height);

        // マップを描く
        map.draw(g, offsetX, offsetY);

        // 勇者を描く
        hero.draw(g, offsetX, offsetY);
    }

    public void keyPressed(KeyEvent e){
        // 押されたキーを取得
        int keyCode = e.getKeyCode();

        switch (keyCode){
            case KeyEvent.VK_LEFT :
                // 左キーの場合は勇者を１歩左へ
                hero.move(left);
                break;

            case KeyEvent.VK_RIGHT :
                // 右キーの場合は勇者を１歩右へ
                hero.move(right);
                break;

            case KeyEvent.VK_UP :
                // 上キーの場合は勇者を１歩上へ
                hero.move(up);
                break;

            case  KeyEvent.VK_DOWN :
                // 下キーの場合は勇者を１歩下へ
                hero.move(down);
                break;
        }

        // 勇者を動かしたので再描写する
        repaint();
    }

    public void keyReleased(KeyEvent e){
    }

    public void keyTyped(KeyEvent e){
    }

}
