package map;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

/**
 * @Author ISJINHAO
 * @Date 2022/3/22 18:16
 */
public class RPCClient {

    private Channel channel;

    private static final AtomicLong INVOKE_ID = new AtomicLong(0);

    private Bootstrap bootstrap;

    public RPCClient() {
        EventLoopGroup group = new NioEventLoopGroup();

        NettyClientHandler nettyClientHandler = new NettyClientHandler();

        try {
            bootstrap = new Bootstrap();

            bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            ByteBuf delimiter = Unpooled.copiedBuffer("|".getBytes());
                            pipeline.addLast(new DelimiterBasedFrameDecoder(1000, delimiter));

                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());

                            pipeline.addLast(nettyClientHandler);
                        }
                    });

            ChannelFuture sync = bootstrap.connect("127.0.0.1", 12800).sync();
            if (sync.isDone() && sync.isSuccess()) {
                this.channel = sync.channel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void sendMsg(String msg) {
        channel.writeAndFlush(msg);
    }

    public void close() {
        if (bootstrap != null) {
            bootstrap.config().group().shutdownGracefully();
        }
        if (channel != null) {
            channel.close();
        }
    }


    private String generateFrame(String msg, String reqId) {
        return msg + ":" + reqId + "|";
    }

    public CompletableFuture<String> rpcAsyncCall(String msg) {
        CompletableFuture<String> stringCompletableFuture = new CompletableFuture<>();
        String reqId = INVOKE_ID.getAndIncrement() + "";
        this.sendMsg(generateFrame(msg, reqId));
        FutureMapUtil.put(reqId, stringCompletableFuture);
        return stringCompletableFuture;
    }


    public String rpcSyncCall(String msg) throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringCompletableFuture = new CompletableFuture<>();
        String reqId = INVOKE_ID.getAndIncrement() + "";
        this.sendMsg(generateFrame(msg, reqId));
        FutureMapUtil.put(reqId, stringCompletableFuture);
        return stringCompletableFuture.get();
    }


    public static void main(String[] args) throws Exception {

        RPCClient rpcClient = new RPCClient();
        System.out.println(rpcClient.rpcSyncCall(" hello world"));

        CompletableFuture<String> hello_world = rpcClient.rpcAsyncCall("hello world");
        System.out.println(hello_world);

        hello_world.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println(s);
            }
        });

    }

}
