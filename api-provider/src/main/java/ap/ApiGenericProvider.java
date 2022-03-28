package ap;

import org.apache.dubbo.config.*;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @Author ISJINHAO
 * @Date 2022/3/11 16:45
 */
public class ApiGenericProvider {

    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("api-generic-provider");
        applicationConfig.setQosEnable(false);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://81.71.14.12:2181");
        registryConfig.setTimeout(30000);
        registryConfig.setUseAsConfigCenter(false);
        registryConfig.setUseAsMetadataCenter(false);

        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setRetries(3);
        providerConfig.setTimeout(3000);

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(12347);
        protocol.setThreads(4);


        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
        // 服务提供者暴露服务配置
        ServiceConfig<GenericService> echoServiceConfig = new ServiceConfig<>();
        echoServiceConfig.setInterface("esg.EchoService");
        echoServiceConfig.setRef(new EchoGenericService());
        echoServiceConfig.setVersion("1.0.0");
        echoServiceConfig.setProtocol(protocol);
        echoServiceConfig.setGeneric("true");

        // 通过DubboBootstrap简化配置组装，控制启动过程
        DubboBootstrap.getInstance()
                .application(applicationConfig)         // 应用配置
                .registry(registryConfig)               // 注册中心配置
                .provider(providerConfig)
                .protocol(protocol)                     // 全局默认协议配置
                .service(echoServiceConfig)             // 添加ServiceConfig
                .start()                                // 启动Dubbo
                .await();                               // 挂起等待(防止进程退出）
    }

}
