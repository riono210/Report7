package jp.ac.uryukyu.ie.e165729;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Container;

import javax.swing.*;

/**
 * Created by e165729 on 2017/02/01.
 */
public class Main  extends JFrame implements KeyListener{
    public JLabel label1;
    public JLabel label2;

    public static void main(String[] args) {
        Main aa = new Main("タイトル");
        aa.setVisible(true);
    }
    public Main(String titel){
        setTitle(titel);
        setFocusable(true);
        addKeyListener(this);
        setBounds(100, 100, 300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel p = new JPanel();

        label1 = new JLabel("aa");
        label2 = new JLabel("ee");

        p.add(label1);
        p.add(label2);

        Container contentPane = getContentPane();
        contentPane.add(p);

    }

    public void keyPressed(KeyEvent e){
        label1.setText("押された");
        repaint();
    }

    public void keyReleased(KeyEvent e){
        label2.setText("離された");
        repaint();
    }
    public void keyTyped(KeyEvent e){
    }
}

