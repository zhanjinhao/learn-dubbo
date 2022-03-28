package es;

import java.util.concurrent.CompletableFuture;

/**
 * @Author ISJINHAO
 * @Date 2022/3/9 22:17
 */
public interface EchoService {

    String echo(String msg);

    CompletableFuture<String> echoAsync(String msg);

    String echoAsync2(String msg);

}
