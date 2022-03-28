package ac;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @Author ISJINHAO
 * @Date 2022/3/11 16:55
 */
public class ApiGenericConsumer {

    public static void main(String[] args) {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("api-generic-consumer");
        applicationConfig.setQosEnable(false);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("81.71.14.12");
        registryConfig.setPort(2181);
        registryConfig.setTimeout(30000);
        registryConfig.setUseAsConfigCenter(false);
        registryConfig.setUseAsMetadataCenter(false);

        ReferenceConfig<GenericService> referenceConfig1 = new ReferenceConfig<>();
        referenceConfig1.setInterface("esg.EchoService");
        referenceConfig1.setVersion("1.0.0");
        referenceConfig1.setGeneric("true");
        referenceConfig1.setId("esg");

        ReferenceConfig<GenericService> referenceConfig2 = new ReferenceConfig<>();
        referenceConfig2.setInterface("es.EchoService");
        referenceConfig2.setVersion("1.0.0");
        referenceConfig2.setGeneric("true");
        referenceConfig2.setId("es");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig)        // 应用配置
                .registry(registryConfig)               // 注册中心配置
                .reference(referenceConfig1)            // 添加ReferenceConfig
                .reference(referenceConfig2)            // 添加ReferenceConfig
                .start();                               // 启动Dubbo

        Object result1 = referenceConfig1.get().$invoke(
                "echo",
                new String[]{"java.lang.String"},
                new Object[]{"1234"});
        System.out.println(result1);

        Object result2 = referenceConfig2.get().$invoke(
                "echo",
                new String[]{"java.lang.String"},
                new Object[]{"1234"});
        System.out.println(result2);

    }

}
