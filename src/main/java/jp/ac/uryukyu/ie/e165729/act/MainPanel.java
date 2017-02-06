package jp.ac.uryukyu.ie.e165729.act;

import jp.ac.uryukyu.ie.e165729.evt.*;
import jp.ac.uryukyu.ie.e165729.sound.MidiEngine;
import jp.ac.uryukyu.ie.e165729.sound.WaveEngine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Rectangle;
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
    private Map maps[];
    // 現在のマップ番号
    private int mapNo;

    // 勇者
    private Chara hero;

    // アクションキー
    private ActionKey downKey;
    private ActionKey leftKey;
    private ActionKey rightKey;
    private ActionKey upKey;
    private ActionKey spaceKey;

    // ゲームループ
    private Thread  gameLoop;

    // 乱数生成
    private Random rand = new Random();

    // ウィンドウ
    private MessageWindow messageWindow;
    // ウィンドウを表示する領域
    private static Rectangle WND_RECT = new Rectangle(62, 324, 356, 140);

    // サウンドエンジン
    private MidiEngine midiEngine = new MidiEngine();
    private   static WaveEngine waveEngine = new WaveEngine();

    // BGM名（from TAM Music Factory: http://www.tam-music.com/）
    private static final String[] bgmNames = {"castle", "field"};
    private static final String[] bgmFiles = {"bgm/castle.mid", "bgm/field.mid"};

    // サウンド名
    private static final String[] soundNames = {"treasure", "door", "step", "talk"};

    private final String[] soundFiles = {"sound/treasure.wav", "sound/door.wav", "sound/step.wav", "sound/tm2_hit004.wav"};
    public MainPanel(){
        super();
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
        spaceKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);

        // マップを作成
        maps = new Map[2];
        // 王の間
        // ビルド時　     // src/main/java/jp/ac/uryukyu/ie/e165729/
        maps[0] = new Map("map/kingRoomMap.txt", "event/kingRoomEvt.txt", "castle", this);
        // フィールド
        maps[1] = new Map("map/fieldMap.txt", "event/fieldEvt.txt", "field", this);

        // 最初は王の間
        mapNo = 0;

        // 勇者を作成
        hero = new Chara(4, 4, 0, DOWN, 0, maps[mapNo]);

        // マップにキャラを登録
        // キャラクターはマップに属する
        maps[mapNo].addChara(hero);

        // ウィンドウを追加
        messageWindow = new MessageWindow(WND_RECT);

        // サウンドをロード
        loadSound();

        // マップに割り当てられたBGMを再生
        midiEngine.play(maps[mapNo].getBgmName());

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
        offsetX = Math.max(offsetX, MainPanel.WIDTH - maps[mapNo].getWidth());

        // Y方向のオフセットを計算(offsetの値はマイナス)
        int offsetY = (MainPanel.HEIGHT / 2) - (hero.getPy());
        // マップ端ではスクロールしないようにする
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainPanel.HEIGHT - maps[mapNo].getHeight());

        // マップを描く
        maps[mapNo].draw(g, offsetX, offsetY);

        // メッセージウィンドウを描写
        messageWindow.draw(g);

    }

    public void run(){
        while (true) {
            // キー入力をチェックする
            if (messageWindow.isVisible()) {   // メッセージウィンドウ表示中
                massageWindowChackInput();
            } else {
                mainWindowCheckInput();
            }

            if(!messageWindow.isVisible()){
                // 勇者の移動処理
                heroMove();
                // その他のキャラクターの移動処理
                charaMove();
            }

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
    private void mainWindowCheckInput(){
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
        if(spaceKey.isPressed()){
            // 移動中は表示できない
            if(hero.isMoving()) return;
            // 宝箱
            if(treasureCheck()) return;
            // 扉
            if(doorCheck()) return;
            // 話す
            if(!messageWindow.isVisible()){
                Chara chara = hero.talkWith();
                waveEngine.play("talk");
                if(chara != null){
                    // メッセージをセットする
                    messageWindow.setMassage(chara.getMassage());
                    // メッセージウィンドウを表示する
                    messageWindow.show();
                }else {
                    messageWindow.setMassage("ゆうしゃは　あしもとをしらべた！\\fしかし　なにもみつからなかった！");

                    messageWindow.show();
                }
            }
        }
    }

    /**
     * 宝箱を開ける処理
     * @return 宝箱を開けたらtrue,そもそも宝箱がなかったらfalse
     */
    public boolean treasureCheck(){
        TreasureEvent treasure = hero.search();
        if(treasure != null){
            // かちゃ
            waveEngine.play("treasure");
            // メッセージをセットする
            messageWindow.setMassage(treasure.getItemName() + "を　てにいれた！");
            // メッセージウィンドウを表示
            messageWindow.show();
            // ここにアイテム入手処理を入れる?
            // 宝箱を削除
            maps[mapNo].removeEvent(treasure);
            return true;  // しらべた場合は話さない
        }
        return false;
    }

    /**
     * ドアを開ける処理
     * @return ドアを開けたらtrue，そもそもドアがなかったらfalse
     */
    public boolean doorCheck(){
        DoorEvent door = hero.open();
        if(door != null){
            // ギー
            waveEngine.play("door");
            maps[mapNo].removeEvent(door);
            return true;
        }
        return false;
    }

    /**
     * メッセージウィンドウでのキー入力をチェックする
     */
    private void massageWindowChackInput(){
        if(spaceKey.isPressed()){
            if(messageWindow.nextMassage()) {
                messageWindow.hide();
            }
        }
    }

    private void heroMove(){
        // 移動(スクロール)中なら移動する
        if(hero.isMoving()){
            if(hero.move()){
                // 移動が完了した後の処理はここに

                // 移動イベント
                // イベントがあるかをテェック
                Event event = maps[mapNo].eventCheck(hero.getX(), hero.getY());
                if(event instanceof MoveEvent){   // 移動イベントなら
                    MoveEvent m = (MoveEvent)event;
                    // ザッザッザッ
                    waveEngine.play("step");
                    // 移動元の勇者を削除
                    maps[mapNo].removeChara(hero);
                    // 現在のマップ番号に移動先のマップ番号をセット
                    mapNo = m.destMapNo;
                    // 移動先のマップでの座標を取得して勇者を作り直す
                    hero = new Chara(m.destX, m.destY, 0, DOWN, 0 ,maps[mapNo]);
                    // 移動先マップに勇者を登録
                    maps[mapNo].addChara(hero);
                    // 移動先マップのBGMを再生
                    midiEngine.play(maps[mapNo].getBgmName());
                }

            }
        }
    }

    /**
     * 勇者以外の移動
     */
    private void charaMove(){
        // マップにいるキャラを取得
        Vector charas = maps[mapNo].getCharas();
        for(int i = 0; i < charas.size(); i++){
            Chara chara = (Chara)charas.get(i);
            //  キャラクターの移動タイプを調べる
            if(chara.getMoveType() == 1){     // 移動するタイプなら
                if(chara.isMoving()) {    // スクロール中なら
                    chara.move();         // 移動する
                } else if(rand.nextDouble() < Chara.PROB_MOVE) {
                    // 移動しないタイプの場合はChara.PROB_MOVEの確率で移動する
                    // 方向はランダムで決める
                    chara.setDirection(rand.nextInt(4));
                    chara.setMoving(true);
                }
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
        if(keyCode == KeyEvent.VK_SPACE){
            spaceKey.press();
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
        if(keyCode == KeyEvent.VK_SPACE){
            spaceKey.release();
        }
    }

    public void keyTyped(KeyEvent e){
    }

    /**
     * サウンドをロードする
     */
    private void loadSound() {
        // サウンドをロード
        for (int i = 0; i < soundNames.length; i++) {
            waveEngine.load(soundNames[i], soundFiles[i]);
        }
        // BGMをロード
        for (int i = 0; i < bgmNames.length; i++) {
            midiEngine.load(bgmNames[i], bgmFiles[i]);
        }
    }
}

