package jp.ac.uryukyu.ie.e165729.act;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileInputStream;



import javax.swing.*;
import javax.swing.ImageIcon;

/**
 * Created by e165729 on 2017/01/29.
 */
public class Map implements Common {
    // マップ
    private int[][] map ={
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,1,0,1,1,0,0,0,0,1,0,0,0,0,0,1,1,0,1,1,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};


    // マップの行と列の大きさ(ピクセル)
    private int row = 20;
    private int col = 30;

    // マップ全体の大きさ(ピクセル)
    private int width = col*CS;
    private int height = row*CS;

    // チップセット
    private Image floorImage;
    private Image wallImage;
    private Image throneImage;

    // メインパネルへの参照
    private MainPanel panel;

    public Map(String filename, MainPanel panel){
        // マップをロード
        load(filename);

        // イメージをロード
        loadImage();
    }

    public void draw(Graphics g, int offsetX, int offsetY){
        // オフセットを元に描写範囲を求める
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(MainPanel.WIDTH) + 1;
        // 描写範囲がマップの大きさより大きくならないように調整
        lastTileX = Math.min(lastTileX, col);

        int firstTileY = pixelsToTiles(-offsetY);
        int lastTileY = firstTileY + pixelsToTiles(MainPanel.HEIGHT) + 1;
        // 描写範囲がマップの大きさより大きくならないように調整
        lastTileY = Math.min(lastTileY, row);

        for(int i = firstTileY; i < lastTileY; i++){
            for(int j = firstTileX; j < lastTileX; j++){
                // mapの値に応じて絵を描く
                switch (map[i][j]){
                    case 0: // 床
                        g.drawImage(floorImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;

                    case 1: // 壁
                        g.drawImage(wallImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;

                    case 2: // 王座
                        g.drawImage(throneImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                }
            }
        }
    }

    public boolean isHit(int x, int y){
        // (x,y)に壁か王座があったらぶつかる
        if(map[y][x] == 1 || map[y][x] == 2){
            return true;
        }

        // なければ進む
        return false;
    }

    /**
     *  ピクセル単位をマス単位に変更する
     * @param pixels ピクセル単位
     * @return マス単位
     */
    public static int pixelsToTiles(double pixels){
        return (int)Math.floor(pixels / CS);
    }

    /**
     *  マス単位をピクセル単位に変更する
     * @param tiles マス単位
     * @return ピクセル単位
     */
    public static int tilesToPixels(int tiles){
        return tiles * CS;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    /**
     * ファイルからマップを読み込む
     * @param filename 読み込むマップデータのファイル名
     */
    private void load(String filename){
        try {
//            ビルド用
            ClassLoader cls = this.getClass().getClassLoader();
            BufferedReader br = new BufferedReader(new InputStreamReader(cls.getResourceAsStream(filename)));

//            FileInputStream fs = new FileInputStream( new File(filename).getAbsolutePath());
//            InputStreamReader in = new InputStreamReader(fs);
//            BufferedReader br = new BufferedReader(in);

            // rowを読み込む
            String line = br.readLine();
            row = Integer.parseInt(line);
            // colを読み込む
            line = br.readLine();
            col = Integer.parseInt(line);
            // マップサイズを設定
            width = col * CS;
            height = row * CS;
            // マップを作成
            map = new int [row][col];
            for(int i = 0; i < row; i++) {
                line = br.readLine();
                for (int j = 0; j < col; j++) {
                    map[i][j] = Integer.parseInt(line.charAt(j) + "");
                }
            }
          //show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * イメージをロード
     */
    private void loadImage(){
        ClassLoader cl = this.getClass().getClassLoader();
        ImageIcon icon = new ImageIcon(cl.getResource("image/floor.gif"));
        System.out.println(cl.getResource("image/floor.gif"));
        //ImageIcon icon = new ImageIcon(new File("src/main/java/jp/ac/uryukyu/ie/e165729/image/floor.gif").getAbsolutePath());
        floorImage = icon.getImage();

        icon = new ImageIcon(cl.getResource("image/wall.gif"));
        //icon = new ImageIcon(new File("src/main/java/jp/ac/uryukyu/ie/e165729/image/wall.gif").getAbsolutePath());
        wallImage = icon.getImage();

        icon = new ImageIcon(cl.getResource("image/throne.gif"));
        //icon = new ImageIcon(new File("src/main/java/jp/ac/uryukyu/ie/e165729/image/throne.gif").getAbsolutePath());
        throneImage = icon.getImage();
    }

    /**
     *
     */
    public void show(){
        for(int i = 0; i <row; i++){
            for (int j = 0; j < col; j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}