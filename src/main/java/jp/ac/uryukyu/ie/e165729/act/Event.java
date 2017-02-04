package jp.ac.uryukyu.ie.e165729.act;

/**
 * Created by e165729 on 2017/02/04.
 */
public abstract class Event {
    // x座標
    protected int x;
    // y座標
    protected int y;
    // チップ番号
    protected int chipNo;
    // ぶつかるか
    protected boolean isHit;

    /**
     * コンストラクタ
     * @param x x座標
     * @param y y座標
     * @param chipNo チップ番号
     * @param isHit ぶつかるか
     */
    public Event(int x, int y, int chipNo, boolean isHit){
        this.x = x;
        this.y = y;
        this.chipNo = chipNo;
        this.isHit = isHit;
    }



}
