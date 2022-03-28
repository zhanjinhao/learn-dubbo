package es;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author ISJINHAO
 * @Date 2022/3/9 22:18
 */
@DubboService(protocol = "dubbo")
public class EchoServiceImpl implements EchoService {

    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public String echo(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "echo, " + msg;
    }

    @Override
    public CompletableFuture<String> echoAsync(String msg) {
        RpcContext rpcContext = RpcContext.getContext();
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(rpcContext.getUrl());
            return "echoAsync, " + msg;
        }, executorService);
    }

    @Override
    public String echoAsync2(String msg) {
        AsyncContext asyncContext = RpcContext.startAsync();

        executorService.execute(() -> {
            asyncContext.signalContextSwitch();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            asyncContext.write("echoAsync2, " + msg);

        });

        return null;
    }

}
