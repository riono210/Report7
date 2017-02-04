package jp.ac.uryukyu.ie.e165729.act;


import java.awt.*;
import java.awt.Image;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
/**
 * Created by e165729 on 2017/02/03.
 */
public class MessageWindow {
    // 白枠の幅
    private static final int EDGE_WIDTH = 2;

    // 行間の大きさ
    protected static final int LINE_HEIGHT = 8;
    // 1行の最大文字数
    private static final int MAX_CHAR_IN_LINE = 20;
    // １ページに表示できる最大行数
    private static final int MAX_LINES = 3;
    // 1ページに表示できる最大文字数
    private static final int MAX_CHAR_IN_PAGE = MAX_CHAR_IN_LINE * MAX_LINES;

    // 一番外側の枠
    private Rectangle rect;
    // １つ内側の枠
    private Rectangle innerRect;
    // 実際にテキストを表示する枠
    private Rectangle textRect;

    // メッセージウィンドウを表示中か
    private boolean isVisible = false;

    // カーソルのアニメーションgif
    private Image cursorImage;

    // メッセージを格納した配列
    private char[] text = new char[128 * MAX_CHAR_IN_LINE];
    // 最大ページ
    private int maxPage;
    // 現在表示しているページ
    private int curPage = 0;
    // 表示した文字数
    private int curPos;
    // 次のページにいけるか (▼を表示していればtrue)
    private boolean nextFlag = false;

    // メッセージエンジン
    private MessageEngine messageEngine;

    // テキストを流すタイマータスク
    private Timer timer;
    private TimerTask task;

    public MessageWindow(Rectangle rect){
        this.rect = rect;

        innerRect = new Rectangle(
                rect.x + EDGE_WIDTH,
                rect.y + EDGE_WIDTH,
                rect.width - EDGE_WIDTH * 2,
                rect.height - EDGE_WIDTH * 2);

        textRect = new Rectangle(
                innerRect.x + 16,
                innerRect.y + 16,
                320,
                120);

        // メッセージエンジンを作成
        messageEngine = new MessageEngine();

        // カーソルイメージをロード
        // ビルドよう　
        ClassLoader classl = this.getClass().getClassLoader();
        ImageIcon icon = new ImageIcon(classl.getResource("image/cursor.gif"));
//        ImageIcon icon =  new ImageIcon(new File("src/main/java/jp/ac/uryukyu/ie/e165729/image/cursor.gif").getAbsolutePath());
        cursorImage = icon.getImage();

        timer = new Timer();
    }

    public void draw(Graphics g){
        if(!isVisible) return;

        // 枠をかく
        g.setColor(Color.WHITE);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

        // 内側をかく
        g.setColor(Color.BLACK);
        g.fillRect(innerRect.x, innerRect.y, innerRect.width, innerRect.height);

        // 現在のページ
        for(int i = 0; i < curPos; i++){
            char c = text[curPage * MAX_CHAR_IN_PAGE + i];
            int dx = textRect.x + MessageEngine.FONT_WIDTH * (i % MAX_CHAR_IN_LINE);
            int dy = textRect.y + (LINE_HEIGHT + MessageEngine.FONT_HEIGHT) * (i /MAX_CHAR_IN_LINE);
            messageEngine.drawCharacter(dx, dy, c, g);
        }

        // 最後のページではない時は矢印を表示する
        if(curPage < maxPage && nextFlag){
            int dx = textRect.x + (MAX_CHAR_IN_LINE/2) * MessageEngine.FONT_WIDTH -8;
            int dy = textRect.y + (LINE_HEIGHT + MessageEngine.FONT_HEIGHT) * 3;
            g.drawImage(cursorImage, dx, dy, null);
        }
    }

    /**
     * メッセージをセットする
     * @param msg メッセージ
     */
    public void setMassage(String msg) {
        curPage = 0;
        curPos = 0;
        nextFlag = false;

        // 全角スペースで初期化
        for (int i = 0; i < text.length; i++) {
            text[i] = '　';
        }

        int p = 0;     // 処理中の文字
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            if (c == '\\') {
                i++;
                if (msg.charAt(i) == 'n') {   // 改行
                    p += MAX_CHAR_IN_LINE;
                    p = (p / MAX_CHAR_IN_LINE) * MAX_CHAR_IN_LINE;
                } else if (msg.charAt(i) == 'f') {  // 改ページ
                    p += MAX_CHAR_IN_PAGE;
                    p = (p / MAX_CHAR_IN_PAGE) * MAX_CHAR_IN_PAGE;
                }

            } else {
                text[p++] = c;
            }
        }
        maxPage = p / MAX_CHAR_IN_PAGE;

        // 文字を流すタスクを起動
        task = new DrawingMesssageTask();
        timer.schedule(task, 0L, 30L);
    }

    /**
     * メッセージを先に進める
     * @return メッセージが終了したらtrueを返す
     */
    public boolean nextMassage(){
        // 今表示しているページが最後のページだったらメッセージを終了する
        if(curPage == maxPage){
            task.cancel();
            task = null;    // タスクは終了させないと動き続ける
            return true;
        }
        if(nextFlag) {
            curPage++;
            curPos = 0;
            nextFlag = false;

        }
        return false;
    }

    /**
     * ウィンドウを表示
     */
    public void show(){
        isVisible = true;
    }

    /**
     * ウィンドウを隠す
     */
    public void hide(){
        isVisible = false;
    }

    /**
     * ウィンドウを表示中か
     */
    public boolean isVisible(){
        return isVisible;
    }

    // メッセージを1文字つづ表示するタスク
    class DrawingMesssageTask extends TimerTask{
        public void run(){
            if(!nextFlag){
                curPos++;   // 1文字増やす
                // 1ページの文字数になったら▼を表示
                if(curPos % MAX_CHAR_IN_PAGE == 0){
                    nextFlag = true;
                }
            }
        }

    }
}
