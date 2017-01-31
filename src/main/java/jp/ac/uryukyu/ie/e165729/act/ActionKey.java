package jp.ac.uryukyu.ie.e165729.act;

/**
 * Created by e165729 on 2017/01/29.
 */
public class ActionKey {
    // キーのモード
    // 押されている時はisPressde()はtrueを返す
    public static final int NORMAL = 0;
    // キーが初めて押された時isPressed()はtrueを返す
    // キーが押され続けても2回目以降はfalseを返す
    public static final int DETECT_INITIAL_PRESS_ONLY = 1;

    // キーの状態
    // キーが離された
    private static final int STATE_RELEASED = 0;
    // キーが押されている
    private static final int STATE_PRESSED = 1;
    // キーが離されるのを待っている
    private static final int STATE_WAITING_FOR_RELEASE = 2;

    // キーのモードを保持
    private int mode;
    // キーが押された回数
    private int amount;
    // キーの状態
    private int state;

    /**
     * 通常のコンストラクタはノーマルモード
     */
    public ActionKey(){
        this(NORMAL);
    }

    /**
     *  モード指定できるコンストラクタ
     *
     *  @param mode キーのモード
     */
    public ActionKey(int mode){
        this.mode = mode;
        reset();
    }

    /**
     * キー状態をリセット
     */
    public void reset(){
        state = STATE_RELEASED;
        amount = 0;
    }

    /**
     * キーが押された時に呼び出される
     */
    public void press(){
        if(state != STATE_WAITING_FOR_RELEASE){
            amount++;
            state = STATE_PRESSED;
        }
    }

    /**
     * キーが離された時に呼び出される
     */
    public void release(){
        state = STATE_RELEASED;
    }

    /**
     * キーが押されたか
     *
     * @return 押されたらtrueを返す
     */
    public boolean isPressed(){
        if(amount != 0){
            if(state == STATE_RELEASED){
                amount = 0;
            }else if(mode == DETECT_INITIAL_PRESS_ONLY){
                // 最初の1回だけtrueを返して押されたことにする
                // 次回からはSTATE_WAITING_FOR_RELEASEになるため
                // キーを押し続けても押されたことにならない
                state = STATE_WAITING_FOR_RELEASE;
                amount = 0;
            }

            return true;
        }

        return false;
    }
}
