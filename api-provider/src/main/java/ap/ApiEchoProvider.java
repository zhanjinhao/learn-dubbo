package ap;

import es.EchoService;
import es.EchoServiceImpl;
import org.apache.dubbo.config.*;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

/**
 * @Author ISJINHAO
 * @Date 2022/3/11 16:45
 */
public class ApiEchoProvider {

    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("api-provider");
        applicationConfig.setQosEnable(false);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://81.71.14.12:2181");
        registryConfig.setTimeout(3000000);
        registryConfig.setUseAsConfigCenter(false);
        registryConfig.setUseAsMetadataCenter(false);


        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setRetries(3);
        providerConfig.setTimeout(3000000);


        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(12346);
        protocol.setThreads(2);


        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
        // 服务提供者暴露服务配置
        ServiceConfig<EchoService> echoServiceConfig = new ServiceConfig<>();
        echoServiceConfig.setInterface(EchoService.class);
        echoServiceConfig.setRef(new EchoServiceImpl());
        echoServiceConfig.setVersion("1.0.0");
        echoServiceConfig.setProtocol(protocol);
//        echoServiceConfig.setAsync(true);


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
//
//    public void setPropertyValue(Object o, String n, Object v) {
//        es.EchoServiceImpl w;
//        try {
//            w = ((es.EchoServiceImpl) $1);
//        } catch (Throwable e) {
//            throw new IllegalArgumentException(e);
//        }
//        throw new org.apache.dubbo.common.bytecode.NoSuchPropertyException("Not found property \"" + $2 + "\" field or setter method in class es.EchoServiceImpl.");
//    }
//
//    public Object getPropertyValue(Object o, String n) {
//        es.EchoServiceImpl w;
//        try {
//            w = ((es.EchoServiceImpl) $1);
//        } catch (Throwable e) {
//            throw new IllegalArgumentException(e);
//        }
//        throw new org.apache.dubbo.common.bytecode.NoSuchPropertyException("Not found property \"" + $2 + "\" field or getter method in class es.EchoServiceImpl.");
//    }
//
//    public Object invokeMethod(Object o, String n, Class[] p, Object[] v) throws java.lang.reflect.InvocationTargetException {
//        es.EchoServiceImpl w;
//        try {
//            w = ((es.EchoServiceImpl) $1);
//        } catch (Throwable e) {
//            throw new IllegalArgumentException(e);
//        }
//        try {
//            if ("echo".equals($2) && $3.length == 1) {
//                return ($w) w.echo((java.lang.String) $4[0]);
//            }
//        } catch (Throwable e) {
//            throw new java.lang.reflect.InvocationTargetException(e);
//        }
//        throw new org.apache.dubbo.common.bytecode.NoSuchMethodException("Not found method \"" + $2 + "\" in class es.EchoServiceImpl.");
//    }

}
