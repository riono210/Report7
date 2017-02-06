package jp.ac.uryukyu.ie.e165729;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import jp.ac.uryukyu.ie.e165729.act.Map;

/**
 * Created by e165729 on 2017/02/06.
 */
public class MapReadTest {
    public Map map;

    @Test
    public void Testmap()throws Exception{
        map = new Map("map/fieldMap.txt", "event/fieldEvt.txt", "field", null);

        assertEquals(20, map.getRow());
        assertEquals(32, map.getCol());
    }
}
