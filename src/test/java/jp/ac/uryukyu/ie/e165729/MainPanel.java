package jp.ac.uryukyu.ie.e165729;

/**
 * Created by e165729 on 2017/01/18.
 */
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
class MainPanel extends JPanel {
    // �p�l���T�C�Y
    private static final int WIDTH = 480;
    private static final int HEIGHT = 480;

    // �s���i�P�ʁF�}�X�j
    private static final int ROW = 15;
    // �񐔁i�P�ʁF�}�X�j
    private static final int COL = 15;
    // �`�b�v�Z�b�g�̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int CS = 32;

    // �}�b�v 0:�� 1:��
    private int[][] map = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,1,1,1,1,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,1,0,1,1,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

    // �`�b�v�Z�b�g
    private Image floorImage;
    private Image wallImage;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // �C���[�W�����[�h
        loadImage();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �}�b�v��`��
        drawMap(g);
    }

    private void loadImage() {
        ImageIcon icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/floor.gif");
        floorImage = icon.getImage();

        icon = new ImageIcon("/Users/e165729/IdeaProjects/Report7/src/main/java/jp/ac/uryukyu/ie/e165729/image/wall.gif");
        wallImage = icon.getImage();
    }

    private void drawMap(Graphics g) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                // map�̒l�ɉ����ĉ摜��`��
                switch (map[i][j]) {
                    case 0 : // ��
                        g.drawImage(floorImage, j * CS, i * CS, this);
                        break;
                    case 1 : // ��
                        g.drawImage(wallImage, j * CS, i * CS, this);
                        break;
                }
            }
        }
    }
}