package ac;

import es.EchoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;

/**
 * @Author ISJINHAO
 * @Date 2022/3/11 16:55
 */
public class ApiEchoAsync2Consumer {

    public static void main(String[] args) throws Exception {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("api-consumer");
        applicationConfig.setQosEnable(false);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("81.71.14.12");
        registryConfig.setPort(2181);
        registryConfig.setTimeout(30000);
        registryConfig.setUseAsConfigCenter(false);
        registryConfig.setUseAsMetadataCenter(false);

        ReferenceConfig<EchoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(EchoService.class);
        referenceConfig.setVersion("1.0.0");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig)        // 应用配置
                .registry(registryConfig)               // 注册中心配置
                .reference(referenceConfig)             // 添加ReferenceConfig
                .start();                               // 启动Dubbo

        EchoService echoService = bootstrap.getCache().get(EchoService.class);

        for (int i = 0; i < 1000000; i++) {
            System.out.println(echoService.echoAsync2("hello world!"));

            Future<Object> future = RpcContext.getContext().getFuture();
            System.out.println(future.get());

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
