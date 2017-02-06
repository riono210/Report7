package jp.ac.uryukyu.ie.e165729.act;

import javax.swing.*;


/**
 * Created by e165729 on 2017/02/01.
 */
public class Main extends JFrame{
    public static void main(String[] args) {
        Start start = new Start("タイトル");
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setVisible(true);
        start.setResizable(false);

        while (start.isFirst()){
            System.out.println("待機中");
        }
        System.out.println("開始");

        Rpg frame = new Rpg();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

