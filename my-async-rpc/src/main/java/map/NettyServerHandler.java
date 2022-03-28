package map;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author ISJINHAO
 * @Date 2022/3/21 11:40
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES,
                    new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    public String generatorFrame(String msg, String reqId) {
        return msg + ":" + reqId + "|";
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        executor.submit(() -> {

            try {
                System.out.println(msg);

                String str = (String) msg;

                String reqId = str.split(":")[1];

                String resp = generatorFrame("i am addenda", reqId);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ctx.channel().writeAndFlush(Unpooled.copiedBuffer(resp.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        super.channelRead(ctx, msg);
    }
}
