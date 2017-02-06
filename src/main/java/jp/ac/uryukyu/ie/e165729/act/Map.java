package jp.ac.uryukyu.ie.e165729.act;

import jp.ac.uryukyu.ie.e165729.evt.DoorEvent;
import jp.ac.uryukyu.ie.e165729.evt.MoveEvent;
import jp.ac.uryukyu.ie.e165729.evt.TreasureEvent;

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
    private int[][] map;
//            {      // デバッグ用デモマップ。ファイルの読み込みができない場合に出現する
//            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,0,0,0,0,1},
//            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
//            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
//            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
//            {1,0,0,0,0,1,1,0,1,1,0,0,0,0,1,0,0,0,0,0,1,1,0,1,1,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};


    // マップの行と列の大きさ(ピクセル)
    private int row;
    private int col;

    // マップ全体の大きさ(ピクセル)
    private int width;
    private int height;

    // チップセット
    private static Image chipImage;

    // このマップにいるキャラクターたち
    private Vector charas = new Vector();
    // このマップにあるイベント
    private Vector events = new Vector();

    // メインパネルへの参照
    private MainPanel panel;

    // BGM番号
    private String bgmName;

    public Map(String  mapFile, String eventFile, String bgmName, MainPanel panel){
        this.bgmName = bgmName;

        // マップをロード
        load(mapFile);

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

                // (j,i)にあるイベントを描写
                for(int n = 0; n < events.size(); n++){
                    Event event = (Event)events.get(n);
                    //  イベントが(j, i)にあれば描写
                    if(event.x == j && event.y == i){
                        mapChipNo = event.chipNo;
                        cx = (mapChipNo % 8) * CS;
                        cy = (mapChipNo / 8) * CS;
                        g.drawImage(chipImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY,
                                tilesToPixels(j) + offsetX + CS, tilesToPixels(i) + offsetY + CS,
                                cx, cy, cx + CS, cy + CS, panel);
                    }
                }

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
        // (x,y)に壁，王座，海，カウンターがあったらぶつかる
        if(map[y][x] == 1 || map[y][x] == 2 ||map[y][x] == 5 || map[y][x] == 8|| map[y][x] == 9|| map[y][x] == 7){
            return true;
        }

        // 他のキャラクターがいたらぶつかる
        for(int i = 0; i < charas.size(); i++){
            Chara chara = (Chara) charas.get(i);
            if(chara.getX() == x && chara.getY() == y){
                return true;
            }
        }

        // ぶつかるイベントがあるか
        for(int i = 0; i < events.size(); i++){
            Event event = (Event)events.get(i);
            if(event.x == x && event.y == y){
                return event.isHit;
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
     * キャラクターをこのマップから削除する
     * @param chara キャラクター
     */
    public void removeChara(Chara chara){
        charas.remove(chara);
    }
    /**
     * (x,y)にキャラがいるか調べる
     * @param x X座標
     * @param y Y座標
     * @return (x,y)にいるキャラ，いなければnull
     */
    public Chara charaCheck(int x, int y){
        for(int i = 0; i < charas.size(); i++){
            Chara chara = (Chara)charas.get(i);
            if(chara.getX() == 23 && chara.getY() == 4){

            }
            if(chara.getX() == x && chara.getY() == y){
                return chara;
            }
        }
        return null;
    }

    /**
     * (x,y)にイベントがあるかを調べる
     * @param x x座標
     * @param y y座標
     * @return イベント
     */
    public Event eventCheck(int x, int y){
        for(int i = 0;i < events.size(); i++){
            Event event = (Event)events.get(i);
            if(event.x == x && event.y == y){
                return event;
            }
        }
        return null;
    }

    /**
     * 登録されたイベントを削除
     * @param event 削除したいイベント
     */
    public void removeEvent(Event event){
        events.remove(event);
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
            show();
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
            ClassLoader cs = this.getClass().getClassLoader();
            BufferedReader br = new BufferedReader(new InputStreamReader(cs.getResourceAsStream(eventFile)));

//            FileInputStream fs = new FileInputStream( new File(eventFile).getAbsolutePath());
//            InputStreamReader in = new InputStreamReader(fs);
//            BufferedReader br = new BufferedReader(in);

            String line;
            while ((line = br.readLine()) != null){
                // 空行は読み飛ばす
                if(line.equals("")) continue;
                // コメント行は読み飛ばす
                if(line.startsWith("#")) continue;
                StringTokenizer st = new StringTokenizer(line, ",");
                // イベント情報を取得する
                // イベントタイプを取得してイベントごとに処理をする
                String eventType = st.nextToken();
                if(eventType.equals("CHARA")){      // キャラクターイベント
                    makeCharacter(st);
                }else if(eventType.equals("TREASURE")){  // 宝箱イベント
                    makeTreasure(st);
                }else if(eventType.equals("DOOR")){      // 扉イベント
                    makeDoor(st);
                }else if(eventType.equals("MOVE")){      // 移動イベント
                    makeMove(st);
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
        ClassLoader cl = this.getClass().getClassLoader();
        ImageIcon icon = new ImageIcon(cl.getResource("image/mapchip.gif"));
//        ImageIcon icon = new ImageIcon(new File("src/main/java/jp/ac/uryukyu/ie/e165729/image/mapchip.gif").getAbsolutePath());
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
        String message = st.nextToken();
        // キャラクターを作成
        Chara c = new Chara(x, y, charaNo, dir, moveType, this);
        // メッセージを登録
        c.setMassage(message);
        // キャラクターベクトルに登録
        charas.add(c);
    }

    /**
     * 宝箱イベントを作成
     */
    public void makeTreasure(StringTokenizer st){
        // 宝箱の座標
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        // アイテム名
        String itemName = st.nextToken();
        // 宝箱イベントの作成
        TreasureEvent t = new TreasureEvent(x, y, itemName);
        // 宝箱イベントを登録
        events.add(t);
    }

    /**
     * ドアイベントを作成
     */
    public void makeDoor(StringTokenizer st){
        // 扉の座標
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        // 扉イベントを作成
        DoorEvent d = new DoorEvent(x, y);
        // 扉イベントの追加
        events.add(d);
    }

    /**
     * 移動イベントを作成
     */
    public void makeMove(StringTokenizer st){
        // 移動イベントの座標
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        // チップ番号
        int chipNo = Integer.parseInt(st.nextToken());
        // 移動先のマップ番号
        int destMapNo = Integer.parseInt(st.nextToken());
        // 移動先のx,y座標
        int destX = Integer.parseInt(st.nextToken());
        int destY = Integer.parseInt(st.nextToken());
        // 移動イベントを作成
        MoveEvent m = new MoveEvent(x, y, chipNo, destMapNo, destX, destY);
        // 移動イベントを登録
        events.add(m);
    }

    /**
     * このマップのBGM番号を返す
     * @return BGM番号
     */
    public String getBgmName(){
        return bgmName;
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