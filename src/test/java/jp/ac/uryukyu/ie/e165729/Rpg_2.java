package jp.ac.uryukyu.ie.e165729;

import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/10/09
 *
 */

/**
 * @author mori
 *
 */
public class Rpg_2 extends JFrame {
    public Rpg_2() {
        // �^�C�g����ݒ�
        setTitle("勇者降臨");

        // �p�l�����쐬
        MainPanel_2 panel = new MainPanel_2();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        Rpg_2 frame = new Rpg_2();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
