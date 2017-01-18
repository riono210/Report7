package jp.ac.uryukyu.ie.e165729;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/*
 * Created on 2005/10/09
 *
 */

/**
 * @author mori
 *
 */
class MainPanel_2 extends JPanel {
    // �p�l���T�C�Y
    private static final int WIDTH = 480;
    private static final int HEIGHT = 480;

    // �E�҂̃C���[�W
    private Image heroImage;

    public MainPanel_2() {
        // �p�l���̐����T�C�Y��ݒ�
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // �C���[�W�����[�h
        loadImage();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �E�҂̃C���[�W��\��
        g.drawImage(heroImage, 0, 0, this);
    }

    private void loadImage() {
        ImageIcon icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/hero.gif");
        heroImage = icon.getImage();
    }
}