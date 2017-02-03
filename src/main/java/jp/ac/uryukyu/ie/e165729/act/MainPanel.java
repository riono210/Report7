package jp.ac.uryukyu.ie.e165729.act;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;


/**
 * Created by e165729 on 2017/01/24.
 */
public class MainPanel extends JPanel implements KeyListener, Runnable, Common{
    // ウィンドウの大きさ
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    // マップ
    private Map map;
    // 勇者
    private Chara hero;
    // 王様
    private Chara king;
    // 兵士
    private Chara soldier;

    // アクションキー
    private ActionKey downKey;
    private ActionKey leftKey;
    private ActionKey rightKey;
    private ActionKey upKey;

    // ゲームループ
    private Thread  gameLoop;

    // 乱数生成
    private Random rand = new Random();

    public MainPanel(){
        // パネルの推奨サイズの設定
        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        // キー入力を受付
        setFocusable(true);
        addKeyListener(this);

        // アクションキーを作成
        downKey = new ActionKey();
        leftKey = new ActionKey();
        rightKey = new ActionKey();
        upKey = new ActionKey();

        // マップを作成
        // ビルド時　   map/map.txt  // src/main/java/jp/ac/uryukyu/ie/e165729/
        map = new Map("src/main/java/jp/ac/uryukyu/ie/e165729/map/map.txt", "src/main/java/jp/ac/uryukyu/ie/e165729/event/event.txt",this);
        // 勇者を作成
        hero = new Chara(4, 4, 0, DOWN, 0,map);

        // マップにキャラを登録
        // キャラクターはマップに属する
        map.addChara(hero);

        // ゲームループの開始
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // X方向のオフセットを計算(基本offsetXの値はマイナス)
        int offsetX = (MainPanel.WIDTH / 2) - (hero.getPx());
        // マップ端ではスクロールしないようにする
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, MainPanel.WIDTH - map.getWidth());

        // Y方向のオフセットを計算(offsetの値はマイナス)
        int offsetY = (MainPanel.HEIGHT / 2) - (hero.getPy());
        // マップ端ではスクロールしないようにする
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainPanel.HEIGHT - map.getHeight());

        // マップを描く
        map.draw(g, offsetX, offsetY);

    }

    public void run(){
        while (true){
            // キー入力をチェックする
            checkInput();

            // 勇者の移動処理
            heroMove();
            // その他のキャラクターの移動処理
            charaMove();

            // 再描写
            repaint();

            // 休止
            try{
                Thread.sleep(20);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * キー入力をチェックする
     */
    private void checkInput(){
        if(downKey.isPressed()){ //下
            if(!hero.isMoving()){        // 移動中でなければ
                hero.setDirection(DOWN); // 方向をセットして
                hero.setMoving(true);    // 移動(スクロール)開始
            }
        }
        if(leftKey.isPressed()){ // 左
            if(!hero.isMoving()){
                hero.setDirection(LEFT);
                hero.setMoving(true);
            }
        }
        if(rightKey.isPressed()){ // 右
            if(!hero.isMoving()){
                hero.setDirection(RIGHT);
                hero.setMoving(true);
            }
        }
        if(upKey.isPressed()){ // 上
            if(!hero.isMoving()){
                hero.setDirection(UP);
                hero.setMoving(true);
            }
        }
    }


    private void heroMove(){
        // 移動(スクロール)中なら移動する
        if(hero.isMoving()){
            if(hero.move()){
                // 移動が完了した後の処理はここに
            }
        }
    }



    /**
     * 勇者以外の移動
     */
    private void charaMove(){
        Vector charas = map.getCharas();
        for(int i = 0; i < charas.size(); i++){
            Chara chara = (Chara)charas.get(i);
            //  キャラクターの移動タイプを調べる
            if(chara.getMoveType() == 0){     // 移動するタイプなら
                if(chara.isMoving()) {    // スクロール中なら
                    chara.move();         // 移動する
                }
            }else if(rand.nextDouble() < Chara.PROB_MOVE){
                // 移動しないタイプの場合はChara.PROB_MOVEの確率で移動する
                // 方向はランダムで決める
                chara.setDirection(rand.nextInt(4));
                chara.setMoving(true);
            }
        }
    }


    /**
     * キーが押されたらキーの状態を「押された」にする
     *
     * @param e キーイベント
     */
    public void keyPressed(KeyEvent e){
        // 押されたキーを取得
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_DOWN){
            downKey.press();
        }
        if(keyCode == KeyEvent.VK_LEFT){
            leftKey.press();
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            rightKey.press();
        }
        if(keyCode == KeyEvent.VK_UP){
            upKey.press();
        }
    }

    /**
     * キーが離されたらキーの状態を「離された」にする
     *
     * @param e キーイベント
     */
    public void keyReleased(KeyEvent e){
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_DOWN){
            downKey.release();
        }
        if(keyCode == KeyEvent.VK_LEFT){
            leftKey.release();
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            rightKey.release();
        }
        if(keyCode == KeyEvent.VK_UP){
            upKey.release();
        }
    }

    public void keyTyped(KeyEvent e){
    }
}
