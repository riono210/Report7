package jp.ac.uryukyu.ie.e165729;

/**
 * Created by e165729 on 2017/01/18.
 */
import java.awt.*;

import javax.swing.JFrame;

/*
 * Created on 2005/10/09
 *
 */

/**
 * @author mori
 *
 */
public class Rpg extends JFrame {
    public Rpg() {
        // �^�C�g����ݒ�
        setTitle("ts");

        // �p�l�����쐬
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel, BorderLayout.CENTER);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        Rpg frame = new Rpg();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
