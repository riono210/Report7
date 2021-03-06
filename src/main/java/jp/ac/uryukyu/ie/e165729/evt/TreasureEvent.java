package jp.ac.uryukyu.ie.e165729.evt;

import jp.ac.uryukyu.ie.e165729.act.Event;

/**
 * Created by e165729 on 2017/02/04.
 */
public class TreasureEvent extends Event {
    private String itemName;

    /**
     * コンストラクタ
     * @param x x座標
     * @param y y座標
     * @param itemName アイテム名
     */
    public TreasureEvent(int x, int y, String itemName){
        // 宝箱のチップ番号は17でぶつかる
        super(x, y, 17, true);
        this.itemName = itemName;
    }

    public String getItemName(){
        return itemName;
    }

    // デバッグ用
    public String toString(){
        return "TREASURE" + super.toString() + ":" + itemName;
    }

}
