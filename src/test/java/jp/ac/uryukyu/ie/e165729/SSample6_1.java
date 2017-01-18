package jp.ac.uryukyu.ie.e165729;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.*;

class SSample6_1 extends JFrame{
    public static void main(String args[]){
        SSample6_1 frame = new SSample6_1("タイトル");
        frame.setVisible(true);
    }

    public SSample6_1(String title){
        setTitle(title);
        setBounds(100, 100, 300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setPreferredSize(new Dimension(480,480));

        JPanel p = new JPanel();

        ImageIcon icon1 = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/hero.gif");

        JLabel label1 = new JLabel(icon1);


        p.add(label1);

        Container contentPane = getContentPane();
        contentPane.add(p, BorderLayout.CENTER);
    }
}
