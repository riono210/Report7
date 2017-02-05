package jp.ac.uryukyu.ie.e165729.evt;

import jp.ac.uryukyu.ie.e165729.act.Event;

/**
 * Created by e165729 on 2017/02/05.
 */
public class MoveEvent extends Event {
    // 移動先のマップの番号
    public int destMapNo;
    // 移動先のx座標
    public int destX;
    // 移動先のy座標
    public int destY;

    public MoveEvent(int x, int y, int chipNo, int destMapNo, int destX, int destY){
        // 上に乗ると移動するようにしたのでぶつからない
        super(x, y, chipNo, false);
        this.destMapNo = destMapNo;
        this.destX = destX;
        this.destY = destY;
    }
    // デバッグ用
    public String toString() {
        return "MOVE:" + super.toString() + ":" + destMapNo + ":" + destX + ":" + destY;
    }
}
