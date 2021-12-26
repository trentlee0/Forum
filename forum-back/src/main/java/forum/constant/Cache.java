package forum.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public final class Cache {
    private static final Map<String, Object> map = new HashMap<>();
    private static final int min = 24 * 60;

    public static void put(String key, Object value) {
        map.put(key, value);
        removeByTime(key);
    }

    public static Object get(String key) {
        return map.get(key);
    }

    public static Object remove(String key) {
        return map.remove(key);
    }

    public static void removeByTime(String key) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                remove(key);
                System.out.println(map);
            }
        }, min * 60 * 1000);
    }
}
