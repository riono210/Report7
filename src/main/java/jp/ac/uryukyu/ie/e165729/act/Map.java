package jp.ac.uryukyu.ie.e165729.act;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.StringTokenizer;

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
    private static Image chipImage;

    // このマップにいるキャラクターたち
    private Vector charas = new Vector();

    // メインパネルへの参照
    private MainPanel panel;

    public Map(String filename, String eventFile, MainPanel panel){
        // マップをロード
        load(filename);

        // イベントをロード
        loadEvent(eventFile);

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
                int mapChipNo = map[i][j];
                // イメージ上の位置を求める
                // マップイメージの大きさは8*8を想定
                int cx = (mapChipNo % 8) * CS;
                int cy = (mapChipNo / 8) * CS;
                g.drawImage(chipImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY,
                        tilesToPixels(j) + offsetX + CS, tilesToPixels(i) + offsetY + CS,
                        cx, cy, cx + CS, cy + CS, panel);
            }
        }
        // このマップにいるキャラクターを描写
        for(int n = 0; n < charas.size(); n++){
            Chara chara = (Chara)charas.get(n);
            chara.draw(g, offsetX, offsetY);
        }
    }

    /**
     * (x,y)にぶつかるものがあるか調べる
     * @param x マップのx座標
     * @param y マップのy座標
     * @return (x,y)にぶつかるものがあったらtrueを返す
     */
    public boolean isHit(int x, int y){
        // (x,y)に壁か王座があったらぶつかる
        if(map[y][x] == 1 || map[y][x] == 2){
            return true;
        }

        // 他のキャラクターがいたらぶつかる
        for(int i = 0; i < charas.size(); i++){
            Chara chara = (Chara) charas.get(i);
            if(chara.getX() == x && chara.getY() == y){
                return true;
            }
        }

        // なければ進む
        return false;
    }

    /**
     * このマップにキャラクターを追加
     * @param chara　キャラクター
     */
    public void addChara(Chara chara){
        charas.add(chara);
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

    public Vector getCharas(){
        return charas;
    }

    /**
     * ファイルからマップを読み込む
     * @param filename 読み込むマップデータのファイル名
     */
    private void load(String filename){
        try {
//            ビルド用
//            ClassLoader cls = this.getClass().getClassLoader();
//            BufferedReader br = new BufferedReader(new InputStreamReader(cls.getResourceAsStream(filename)));

            FileInputStream fs = new FileInputStream( new File(filename).getAbsolutePath());
            InputStreamReader in = new InputStreamReader(fs);
            BufferedReader br = new BufferedReader(in);

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
     * イベントをロードする
     * @param eventFile イベントファイル
     */
    private void loadEvent(String eventFile){
        try{
            // ビルド用
//            ClassLoader cs = this.getClass().getClassLoader();
//            BufferedReader br = new BufferedReader(new InputStreamReader(cs.getResourceAsStream(eventFile), "Shift_JIS"));

            FileInputStream fs = new FileInputStream( new File(eventFile).getAbsolutePath());
            InputStreamReader in = new InputStreamReader(fs);
            BufferedReader br = new BufferedReader(in);

            String line;
            while ((line = br.readLine()) != null){
                // 空行は読み飛ばす
                if(line.equals("")) continue;
                // コメント行は読み飛ばす
                if(line.equals("#")) continue;
                StringTokenizer st = new StringTokenizer(line, ",");
                // イベント情報を取得する
                // イベントタイプを取得してイベントごとに処理をする
                String eventType = st.nextToken();
                if(eventType.equals("CHARA")){      // キャラクターイベント
                    makeCharacter(st);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * イメージをロード
     */
    private void loadImage(){
        // ビルド用
//        ClassLoader cl = this.getClass().getClassLoader();
//        ImageIcon icon = new ImageIcon(cl.getResource("image/mapchip.gif"));
        ImageIcon icon = new ImageIcon(new File("src/main/java/jp/ac/uryukyu/ie/e165729/image/mapchip.gif").getAbsolutePath());
        chipImage = icon.getImage();
    }

    /**
     * キャラクターとキャラクターイベントを作成
     */
    public void makeCharacter(StringTokenizer st){
        // イベントの座標
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        // キャラクター番号
        int charaNo = Integer.parseInt(st.nextToken());
        // 向き
        int dir = Integer.parseInt(st.nextToken());
        // 移動タイプ
        int moveType = Integer.parseInt(st.nextToken());
        // メッセージ
        String massage = st.nextToken();
        // キャラクターを作成
        Chara c = new Chara(x, y, charaNo, dir, moveType, this);
        // メッセージを登録
        c.setMassage(massage);
        // キャラクターベクトルに登録
        charas.add(c);
    }

    /**
     * デバッグ用マップ表示
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