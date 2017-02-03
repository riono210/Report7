package jp.ac.uryukyu.ie.e165729.act;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Created by e165729 on 2017/02/03.
 */
public class MassageWindow {
    // 白枠の幅
    private static final int EDGE_WIDTH = 2;

    // 一番外側の枠
    private Rectangle rect;
    // １つ内側の枠
    private Rectangle innerRect;

    // メッセージウィンドウを表示中か
    private boolean isVisible = false;

    public MassageWindow(Rectangle rect){
        this.rect = rect;

        innerRect = new Rectangle(
                rect.x + EDGE_WIDTH,
                rect.y + EDGE_WIDTH,
                rect.width - EDGE_WIDTH * 2,
                rect.height - EDGE_WIDTH * 2);
    }

    public void draw(Graphics g){
        if(!isVisible) return;

        // 枠をかく
        g.setColor(Color.white);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

        // 内側をかく
        g.setColor(Color.black);
        g.fillRect(innerRect.x, innerRect.y, innerRect.width, innerRect.height);
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
}
