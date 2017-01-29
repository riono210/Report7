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
public class MainPanel extends JPanel implements KeyListener, Runnable, Common{
    // ウィンドウの大きさ
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    // マップ
    private Map map;

    // 勇者
    private Chara hero;

    // アクションキー
    private ActionKey downKey;
    private ActionKey leftKey;
    private ActionKey rightKey;
    private ActionKey upKey;

    // ゲームループ
    private Thread  gameLoop;

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
        map = new Map(this);
        // 勇者を作成
        hero = new Chara(1, 1, "/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/Hero.png", map);

        // ゲームループの開始
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // X方向のオフセットを計算(基本offsetXの値はマイナス)
        int offsetX = (MainPanel.WIDTH / 2) - (hero.getX()*CS);
        // マップ端ではスクロールしないようにする
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, MainPanel.WIDTH - Map.WIDTH);

        // Y方向のオフセットを計算(offsetの値はマイナス)
        int offsetY = (MainPanel.HEIGHT / 2) - (hero.getY()*CS);
        // マップ端ではスクロールしないようにする
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainPanel.HEIGHT - Map.HEIGHT);

        // マップを描く
        map.draw(g, offsetX, offsetY);

        // 勇者を描く
        hero.draw(g, offsetX, offsetY);
    }

    public void run(){
        while (true){
            // keyオブジェクトの状態を見て方向を決定する
            if (downKey.isPressed()){
                hero.move(DOWN);
            }else if(leftKey.isPressed()){
                hero.move(LEFT);
            }else if(rightKey.isPressed()){
                hero.move(RIGHT);
            }else if(upKey.isPressed()){
                hero.move(UP);
            }

            // 再描写
            repaint();

            // 休止
            try{
                Thread.sleep(200);
            }catch (InterruptedException e){
                e.printStackTrace();
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
