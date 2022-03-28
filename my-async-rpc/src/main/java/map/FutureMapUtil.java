package map;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author ISJINHAO
 * @Date 2022/3/22 18:20
 */
public class FutureMapUtil {

    private static final ConcurrentHashMap<String, CompletableFuture<String>> futureMap = new ConcurrentHashMap<>();

    public static void put(String id, CompletableFuture<String> future) {
        futureMap.put(id, future);
    }

    public static CompletableFuture<String> remove(String id) {
        return futureMap.get(id);
    }

}
