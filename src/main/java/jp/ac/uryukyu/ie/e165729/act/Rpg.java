package jp.ac.uryukyu.ie.e165729.act;

import java.awt.Container;

import javax.swing.JFrame;

/**
 * Created by e165729 on 2017/01/28.
 */
public class Rpg extends JFrame{
    public Rpg(){
        setTitle("マップの読み込み(うまくいけ...!)");

        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        //　パネルサイズに合わせてフレームサイズを自動設定
        pack();
    }

    public static void main(String[] args){
        Rpg frame = new Rpg();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
