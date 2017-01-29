package jp.ac.uryukyu.ie.e165729.act;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
/**
 * Created by e165729 on 2017/01/29.
 */
public class Chara implements Common {
    // キャラクターのイメージ
    private Image image;

    // キャラクターの座標
    private int x, y;

    // キャラクターの向いている方向
    private int direction;

    // キャラクターのアニメーションカウンター
    private int count;
    private int re;

    // キャラクターのアニメーション用スレッド
    private Thread threadAnime;

    // マップへの参照
    private Map map;

    // パネルへの参照
    private MainPanel panel;

    public Chara(int x, int y, String filename, Map map, MainPanel panel) {
        this.x = x;
        this.y = y;
        this.map = map;
        this.panel = panel;

        direction = down;
        count = 0;
        re = 0;

        // イメージをロード
        loadImage(filename);

        // キャラクターアニメーション用スレッドの開始
        threadAnime = new Thread(new AnimationThread());
        threadAnime.start();
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        // countの値に応じてキャラクターの画像を入れ替える
        g.drawImage(image, x * cs + offsetX, y * cs + offsetY, x * cs + offsetX + cs, y * cs + offsetY + cs,
                count * cs, direction * cs, cs + count * cs, direction * cs + cs, panel);
    }

    public void move(int dir) {
        // dirの方向でぶつからなければ移動する
        switch (dir) {
            case left:
                if (!map.isHit(x - 1, y)) x--;
                direction = left;
                break;

            case right:
                if (!map.isHit(x + 1, y)) x++;
                direction = right;
                break;

            case up:
                if (!map.isHit(x, y - 1)) y--;
                direction = up;
                break;

            case down:
                if (!map.isHit(x, y + 1)) y++;
                direction = down;
                break;
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    private void loadImage(String filename) {
        ImageIcon icon = new ImageIcon(filename);
        image = icon.getImage();
    }

    // アニメーションクラス
    private class AnimationThread extends Thread {
        public void run() {
            while (true) {
                // countを切り替える
                if (count == 0) {
                    count = 1;
                } else if (count == 1 && re == 1) {
                    count = 0;
                    re = 0;
                } else if (count == 1) {
                    count = 2;
                } else if (count == 2) {
                    count = 1;
                    re = 1;
                }

                panel.repaint();

                // 300ミリ秒停止，300ミリ秒ごとに絵を入れ替える
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
