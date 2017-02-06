package jp.ac.uryukyu.ie.e165729.act;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by e165729 on 2017/02/06.
 */
public class Start extends JFrame implements KeyListener {
    public JLabel label1;
    public JLabel label2;
    public JLabel label3;

    public JPanel p;

    private boolean first;

    public Start(String titel){
        setTitle(titel);
        setBounds(0 ,0 , 640, 480);

        setFocusable(true);
        addKeyListener(this);

        p = new JPanel();
        p.setLayout(null);
        first = true;

        // 開始イメージをロード
        ClassLoader clas = this.getClass().getClassLoader();
        ImageIcon icon = new ImageIcon(clas.getResource("image/start.jpg"));
        label1 = new JLabel(icon);
        label1.setBounds(0, 0, 640, 480);

        // Press any keyを追加
        label2 = new JLabel("Press any key");
        label2.setOpaque(true);
        label2.setBounds(220, 380, 231, 60);                    // 座標と大きさ
        label2.setForeground(new Color(10, 30, 250, 100));      // 文字の色
        label2.setBackground(new Color(250, 250, 250, 100));    // 背景の色
        label2.setFont(new Font("Arial", Font.PLAIN, 35));      // フォントと文字の大きさ

        // タイトルを追加
        label3 =new JLabel ("RPGツクーレール");
        label3.setOpaque(true);
        label3.setBackground(new Color(250, 250, 250, 200));
        label3.setBounds(130, 30, 400, 60);
        label3.setForeground(new Color(240, 120, 50, 255));
        label3.setFont(new Font("游ゴシック体", Font.PLAIN, 50));

        p.add(label3);
        p.add(label2);
        p.add(label1);



        Container contentPane = getContentPane();
        contentPane.add(p);

    }
    public boolean isFirst(){
        return first;
    }

    public void keyPressed(KeyEvent e){
        if(first) {
            first = false;

            this.dispose();
        }
    }

    public void keyReleased(KeyEvent e){
    }
    public void keyTyped(KeyEvent e){
    }
}
