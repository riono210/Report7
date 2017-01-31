package jp.ac.uryukyu.ie.e165729.act;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.print.DocFlavor;
import javax.swing.ImageIcon;
/**
 * Created by e165729 on 2017/01/29.
 */
public class Chara implements Common {
    // キャラクターの移動速度
    private static int SPEED = 4;

    // キャラクターのイメージ
    private Image image;

    // キャラクターの座標
    private int x, y; // 単位: マス
    private int px, py; // 単位: ピクセル

    // キャラクターの向いている方向
    private int direction;

    // キャラクターのアニメーションカウンター
    private int count;
    private int switchConut;

    // 移動中(スクロール)か
    private boolean isMoving;
    // 移動中の場合の移動ピクセル数
    private int movingLength;

    // キャラクターのアニメーション用スレッド
    private Thread threadAnime;

    // マップへの参照
    private Map map;

    URL ur = null;

    public Chara(int x, int y, String filename, Map map) {
        this.x = x;
        this.y = y;
        this.map = map;

        px = x * CS;
        py = y * CS;

        direction = DOWN;
        count = 0;
        switchConut = 0;

        // イメージをロード
        loadImage(filename);

        // キャラクターアニメーション用スレッドの開始
        threadAnime = new Thread(new AnimationThread());
        threadAnime.start();
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        // countの値に応じてキャラクターの画像を入れ替える
        g.drawImage(image, px + offsetX, py + offsetY, px + offsetX + CS, py + offsetY + CS,
                count * CS, direction * CS, CS + count * CS, direction * CS + CS, null);
    }

    /**
     * 移動処理
     *
     * @return 1マス移動が完了したらtrueを返す。移動中はfalseを返す
     */
    public boolean move(){
        switch (direction){
            case DOWN :
                if(moveDown()){
                    // 移動が完了した
                    return true;
                }
                break;
            case LEFT :
                if(moveLeft()){
                    // 移動が完了した
                    return true;
                }
                break;
            case RIGHT :
                if(moveRight()){
                    // 移動が完了した
                    return true;
                }
                break;
            case UP :
                if(moveUp()){
                    // 移動が完了した
                    return true;
                }
                break;
        }

        // 移動が完了していない
        return false;
    }

    /**
     * 下へ移動する
     *
     * @return 1マス移動が完了したらtrueを返す。移動中はfalseを返す
     */
    private boolean moveDown(){
        // 1マス先の座標
        int nextX = x;
        int nextY = y+1;
        // 障害物のないところでmap外に移動しないように
        if(nextY > map.getRow() - 1) nextY = map.getRow() - 1;

        // その場所に障害物がなければ移動を開始
        if(!map.isHit(nextX, nextY)){
            // SPEEDピクセル分移動
            py += Chara.SPEED;
            // map外にいかないように
            if(py > map.getHeight() - CS)
                py = map.getHeight() - CS;

            // 移動距離を加算
            movingLength += Chara.SPEED;
            // 移動が1マスを超えていたら
            if(movingLength >= CS){
                // 移動する
                y++;
                // map外にいかないように
                if(y > map.getRow() - 1) y = map.getRow() - 1;
                py = y * CS;

                // 移動が完了
                isMoving = false;
                return true;
            }
        }else {
            isMoving = false;
            // 元の位置に戻す
            px = x * CS;
            py = y * CS;
        }

        return false;
    }

    /**
     * 左へ移動する
     *
     * @return 1マス移動が完了したらtrueを返す。移動中はfalseを返す
     */
    private boolean moveLeft(){
        // 1マス先の座標
        int nextX = x-1;
        int nextY = y;
        // 障害物のないところでmap外に移動しないように
        if(nextX < 0) nextX = 0;

        // その場所に障害物がなければ移動開始
        if(!map.isHit(nextX, nextY)){
            // SPEEDピクセル分移動
            px -= SPEED;
            // map外にいかないように
            if (px < 0) px = 0;

            // 移動距離を加算
            movingLength += Chara.SPEED;
            // 移動が1マスを超えていたら
            if(movingLength >= CS){
                // 移動する
                x--;
                // map外にいかないように
                if(x < 0) x = 0;
                px = x * CS;

                // 移動が完了
                isMoving = false;
                return true;
            }
        }else {
            isMoving = false;
            // 元の位置に戻す
            px = x * CS;
            py = y * CS;
        }

        return false;
    }

    /**
     * 右へ移動
     *
     * @return 1マス移動が完了したらtrueを返す。移動中はfalse
     */
    private boolean moveRight(){
        // 1マス先の座標
        int nextX = x+1;
        int nextY = y;
        // 障害物のないところでmap外に移動しないように
        if(nextX > map.getCol() - 1) nextX = map.getCol() - 1;

        // その場所に障害物がなければ移動開始
        if(!map.isHit(nextX, nextY)){
            // SPEEDピクセル分移動
            px += Chara.SPEED;
            // map外にいかないように
            if(px > map.getWidth() - CS) px = map.getWidth() - CS;

            // 移動距離を加算
            movingLength += Chara.SPEED;
            // 移動が1マスを超えていたら
            if(movingLength >= CS){
                // 移動する
                x++;
                // map外にいかないように
                if(x > map.getCol() - 1) x = map.getCol() - 1;
                px = x * CS;

                // 移動が完了
                isMoving = false;
                return true;
            }
        }else {
            isMoving = false;
            // 元の座標に戻す
            px = x * CS;
            py = y * CS;
        }

        return false;
    }

    /**
     * 上に移動
     *
     * @return 1マス移動が完了したらtrueを返す。移動中はfalseを返す
     */
    private boolean moveUp(){
        // 1マス先の座標
        int nextX = x;
        int nextY = y-1;
        // 障害物のないところでmap外に移動しないように
        if(nextY < 0) nextY = 0;

        // その場所に障害物がなければ移動を開始する
        if(!map.isHit(nextX, nextY)){
            // SPEEDピクセル分移動する
            py -= Chara.SPEED;
            // map外にいかないように
            if(py < 0) py = 0;
            // 移動距離を加算
            movingLength += Chara.SPEED;

            // 移動が1マスを超えていたら
            if(movingLength >= CS){
                // 移動する
                y--;
                // map外にいかないように
                if(y < 0) y = 0;
                py = y * CS;

                // 移動が完了
                isMoving = false;
                return true;
            }
        }else {
            isMoving = false;
            // 元の座標に戻す
            px = x * CS;
            py = y * CS;
        }
        return false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getPx(){
        return px;
    }

    public int getPy(){
        return py;
    }

    public void setDirection(int dir){
        direction = dir;
    }

    public boolean isMoving(){
        return isMoving;
    }

    public void setMoving(boolean flag){
        isMoving = flag;
        // 移動距離を初期化
        movingLength = 0;
    }

    private void loadImage(String filename) {
        ClassLoader cl = this.getClass().getClassLoader();
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
                } else if (count == 1 && switchConut == 1) {
                    count = 0;
                    switchConut= 0;
                } else if (count == 1) {
                    count = 2;
                } else if (count == 2) {
                    count = 1;
                    switchConut = 1;
                }

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
