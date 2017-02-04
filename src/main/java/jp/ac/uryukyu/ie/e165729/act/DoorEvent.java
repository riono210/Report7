package jp.ac.uryukyu.ie.e165729.act;

/**
 * Created by e165729 on 2017/02/04.
 */
public class DoorEvent extends Event {

    /**
     * コンストラクタ
     * @param x x座標
     * @param y y座標
     */
    public DoorEvent(int x, int y){
        super(x, y, 18, true);
    }

    /**
     * イベントを文字列に変換（デバッグ用）
     */
    public String toString() {
        return "DOOR:" + super.toString();
    }
}
