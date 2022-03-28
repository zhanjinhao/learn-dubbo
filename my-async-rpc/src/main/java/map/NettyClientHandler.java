package map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author ISJINHAO
 * @Date 2022/3/22 18:18
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES,
                    new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        executor.execute(() -> {
            String msgStr = (String) msg;
            CompletableFuture<String> remove = FutureMapUtil.remove(msgStr.split(":")[1]);
            if (remove != null) {
                remove.complete(msgStr.split(":")[0]);
            }
        });
    }

}
